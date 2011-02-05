package com.bukkit.furt.CraftEssence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.bukkit.furt.CraftEssence.cePlayerListener.Commands;
import com.bukkit.furt.CraftEssence.data.ceConnector;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.nijiko.permissions.PermissionHandler;

public class CraftEssence extends JavaPlugin {
	public static Permissions Permissions = null;
	public static PermissionHandler ph = null;
	public final static String premessage = ChatColor.RED + "[CraftEssence] "
			+ ChatColor.YELLOW;
	public static final Logger log = Logger.getLogger("Minecraft");
	public cePlayerListener cepl = new cePlayerListener(this);
	public ceBlockListener cebl = new ceBlockListener(this);

	public CraftEssence(PluginLoader pluginLoader, Server instance,
			PluginDescriptionFile desc, File folder, File plugin,
			ClassLoader cLoader) {
		super(pluginLoader, instance, desc, folder, plugin, cLoader);
	}

	public boolean onCommand(Player player, Command cmd, String commandLabel,
			String[] args) {
		return super.onCommand(player, cmd, commandLabel, args);
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " Disabled");

	}

	@Override
	public void onEnable() {
		ceSettings.initialize(getDataFolder());
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " v" + pdfFile.getVersion()
				+ " is enabled!");
		registerEvents();
		setupPermissions();

		Connection conn = ceConnector.createConnection();
		if (conn == null) {
			log.log(Level.SEVERE,
					"[CraftEssence] Could not establish SQL connection. Disabling CraftEssence");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setupPermissions() {
		Plugin test = this.getServer().getPluginManager()
				.getPlugin("Permissions");

		if (CraftEssence.Permissions == null) {
			if (test != null) {
				CraftEssence.Permissions = (Permissions) test;
			} else {
				log.info("[CraftEssence] Permission system not enabled. Disabling plugin.");
				this.getServer().getPluginManager().disablePlugin(this);
			}
		}
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_COMMAND, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_CHAT, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_ITEM, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_DAMAGED, this.cebl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACED, this.cebl,
				Event.Priority.Normal, this);
	}

	public static String string(int i) {
		return String.valueOf(i);
	}

	public String argument(String original, String[] arguments, String[] points) {
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i].contains(",")) {
				for (String arg : arguments[i].split(",")) {
					original = original.replace(arg, points[i]);
				}
			} else {
				original = original.replace(arguments[i], points[i]);
			}
		}

		return original;
	}

	public String[] getMotd() {
		ArrayList<String> motd = new ArrayList<String>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(
					getDataFolder() + File.separator + "motd.properties"));
			String str;
			while ((str = in.readLine()) != null) {
				motd.add(str);
			}
			in.close();
		} catch (IOException e) {
		}

		return motd.toArray(new String[] {});
	}

	public List<String> readMail(Player player) {
		return readMail(player.getName());
	}

	public List<String> readMail(String player) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String reciever = player;
		ArrayList<String> mailarray = new ArrayList<String>();

		try {
			conn = ceConnector.getConnection();
			ps = conn
					.prepareStatement("Select * FROM mail WHERE `reciever` = '"
							+ reciever + "'");
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				mailarray.add(rs.getString("sender") + ": "
						+ rs.getString("text"));
			}
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				CraftEssence.log.log(Level.SEVERE,
						"[CraftEssence]: Find SQL Exception (on close)");
			}
		}
		return mailarray;
	}

	public void sendMail(Player player, String string, String string2) {
		Connection conn = null;
		Statement stmt = null;
		int count = 0;
		try {
			conn = ceConnector.getConnection();
			stmt = conn.createStatement();
			count += stmt.executeUpdate("INSERT INTO `mail`"
					+ " (`sender`, `reciever`, `text`)" + " VALUES ('"
					+ player.getName() + "', '" + string + "', '" + string2
					+ "')");
			stmt.close();
			player.sendMessage(CraftEssence.premessage + "Mail sent");
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Mail error");
		}
	}

	public void clearMail(Player player) {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "DELETE FROM `mail` WHERE `reciever` = '"
				+ player.getName() + "'";
		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.execute();
			ps.close();
			player.sendMessage(CraftEssence.premessage + "Mail deleted");
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Mail error");
		}

	}

	public void setHome(Player player, Location home) {
		Connection conn = null;
		Statement stmt = null;
		int count = 0;
		String getname = player.getName();
		double x = home.getX();
		double y = home.getY();
		double z = home.getZ();
		float yaw = home.getYaw();
		float pitch = home.getPitch();
		try {
			conn = ceConnector.getConnection();
			stmt = conn.createStatement();
			count += stmt.executeUpdate("REPLACE INTO `home`"
					+ " (`name`, `x`, `y`, `z`, `yaw`, `pitch`)" + " VALUES ('"
					+ getname + "', '" + x + "', '" + y + "', '" + z + "', '"
					+ yaw + "', '" + pitch + "')");
			stmt.close();
			player.sendMessage(CraftEssence.premessage + "Home set.");
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Home did not save.");
		}
	}

	public Location getHome(Player player) {
		String getname = player.getName();
		String homeq = "Select * FROM home WHERE `name` = '" + getname + "'";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		double x = 0;
		double y = 0;
		double z = 0;
		float pitch = 0;
		float yaw = 0;

		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement(homeq);
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				x = rs.getDouble("x");
				y = rs.getDouble("y");
				z = rs.getDouble("z");
				yaw = rs.getFloat("yaw");
				pitch = rs.getFloat("pitch");

			}
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				CraftEssence.log.log(Level.SEVERE,
						"[CraftEssence]: Find SQL Exception (on close)");
			}
		}
		if (x != 0)
			return new Location(player.getWorld(), x, y, z, yaw, pitch);

		return player.getWorld().getSpawnLocation();
	}

	public boolean check(Player player, Commands cmd) {
		try {
			Class<?> Permissions = getClassLoader().loadClass(
					"com.nijikokun.bukkit.Permissions.Permissions");
			Class<?> PermissionHandler = getClassLoader().loadClass(
					"com.nijiko.permissions.PermissionHandler");
			Object security = Permissions.getField("Security").get(Permissions);
			return (Boolean) PermissionHandler.getMethod("permission",
					Player.class, String.class).invoke(security, player,
					cmd.permNode);
		} catch (Exception ex) {
			return !getConfiguration().getBoolean(
					"restrict-" + cmd.toString().toLowerCase(), false);
		}
	}

	public int kitID(Player player, String[] args) {
		// TODO kitID function
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement("Select * FROM kit WHERE `name` = '"
					+ args[0] + "'");
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
		}
		return id;
	}

	public ArrayList<String> getKit(Player player, Object kitID) {
		// TODO getKit function
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> itemarray = new ArrayList<String>();
		try {
			conn = ceConnector.getConnection();
			ps = conn
					.prepareStatement("Select * FROM `kit_items` WHERE `id` = '"
							+ kitID + "'");
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				itemarray.add(rs.getString("item") + " "
						+ rs.getString("quanity"));
			}
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				CraftEssence.log.log(Level.SEVERE,
						"[CraftEssence]: Find SQL Exception (on close)");
			}
		}
		return itemarray;
	}

	public List<String> kitList(Player player) {
		return kitList(player.getName());
	}

	public List<String> kitList(String player) {
		// TODO kitList function
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> namearray = new ArrayList<String>();
		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement("Select * FROM `kit`");
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				namearray.add(rs.getString("name"));
			}
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				CraftEssence.log.log(Level.SEVERE,
						"[CraftEssence]: Find SQL Exception (on close)");
			}
		}
		return namearray;
	}

	public Location getWarp(Player player, String[] args) {
		String homeq = "Select * FROM warp WHERE `name` = '" + args[0] + "'";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		double x = 0;
		double y = 0;
		double z = 0;
		float pitch = 0;
		float yaw = 0;

		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement(homeq);
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				x = rs.getDouble("x");
				y = rs.getDouble("y");
				z = rs.getDouble("z");
				yaw = rs.getFloat("yaw");
				pitch = rs.getFloat("pitch");

			}
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				CraftEssence.log.log(Level.SEVERE,
						"[CraftEssence]: Find SQL Exception (on close)");
			}
		}
		if (x != 0)
			return new Location(player.getWorld(), x, y, z, yaw, pitch);

		return player.getWorld().getSpawnLocation();
	}

	public void setWarp(Player player, Location home, String[] args) {
		Connection conn = null;
		Statement stmt = null;
		int count = 0;
		double x = home.getX();
		double y = home.getY();
		double z = home.getZ();
		float yaw = home.getYaw();
		float pitch = home.getPitch();
		try {
			conn = ceConnector.getConnection();
			stmt = conn.createStatement();
			count += stmt.executeUpdate("REPLACE INTO `warp`"
					+ " (`name`, `x`, `y`, `z`, `yaw`, `pitch`)" + " VALUES ('"
					+ args[0] + "', '" + x + "', '" + y + "', '" + z + "', '"
					+ yaw + "', '" + pitch + "')");
			stmt.close();
			player.sendMessage(CraftEssence.premessage + "Warp '" + args[0]
					+ "' set.");
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Warp did not save.");
		}

	}
}
