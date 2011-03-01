package me.furt.CraftEssence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

import me.furt.CraftEssence.listener.ceBlockListener;
import me.furt.CraftEssence.listener.ceEntityListener;
import me.furt.CraftEssence.listener.cePlayerListener;
import me.furt.CraftEssence.sql.ceConnector;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.anjocaido.groupmanager.GroupManager;
//import org.anjocaido.groupmanager.dataholder.DataHolder;
import com.nijiko.permissions.PermissionHandler;


public class CraftEssence extends JavaPlugin {
	public static ArrayList<String> godmode = new ArrayList<String>();
	public static ArrayList<String> prayList = new ArrayList<String>();
	public final static String premessage = ChatColor.RED + "[CraftEssence] "
			+ ChatColor.YELLOW;
	public static final Logger log = Logger.getLogger("Minecraft");
	public cePlayerListener cepl = new cePlayerListener(this);
	public ceBlockListener cebl = new ceBlockListener(this);
	public ceEntityListener ceel = new ceEntityListener(this);
	public ceCommands ceComm = new ceCommands(this);
	//private GroupManager groupManager;
	public PermissionHandler Security;

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " Disabled");

	}

	public void onEnable() {
		setupPermissions();
		checkFiles();
		registerEvents();
		sqlConnection();
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " v" + pdfFile.getVersion()
				+ " is enabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		String[] trimmedArgs = args;
		String commandName = command.getName().toLowerCase();

		if (commandName.equals("tp")) {
			return ceComm.tp(sender, trimmedArgs);
		} else if (commandName.equals("alert")) {
			return ceComm.alert(sender, trimmedArgs);
		} else if (commandName.equals("support")) {
			return ceComm.support(sender, trimmedArgs);
		} else if (commandName.equals("msg")) {
			return ceComm.msg(sender, trimmedArgs);
		} else if (commandName.equals("me")) {
			return ceComm.me(sender, trimmedArgs);
		} else if (commandName.equals("setspawn")) {
			return ceComm.setspawn(sender, trimmedArgs);
		} else if (commandName.equals("spawn")) {
			return ceComm.spawn(sender, trimmedArgs);
		} else if (commandName.equals("top")) {
			return ceComm.top(sender, trimmedArgs);
		} else if (commandName.equals("clear")) {
			return ceComm.clearinventory(sender, trimmedArgs);
		} else if (commandName.equals("give")) {
			return ceComm.give(sender, trimmedArgs);
		} else if (commandName.equals("item")) {
			return (ceComm.item(sender, trimmedArgs));
		} else if (commandName.equals("tphere")) {
			return ceComm.tphere(sender, trimmedArgs);
		} else if (commandName.equals("time")) {
			return ceComm.time(sender, trimmedArgs);
		} else if (commandName.equals("kill")) {
			return ceComm.kill(sender, trimmedArgs);
		} else if (commandName.equals("home")) {
			return ceComm.home(sender, trimmedArgs);
		} else if (commandName.equals("sethome")) {
			return ceComm.sethome(sender, trimmedArgs);
		} else if (commandName.equals("jump")) {
			return ceComm.jump(sender);
		} else if (commandName.equals("ban")) {
			return ceComm.ban(sender, trimmedArgs);
		} else if (commandName.equals("pardon")) {
			return ceComm.unban(sender, trimmedArgs);
		} else if (commandName.equals("compass")) {
			return ceComm.compass(sender);
		} else if (commandName.equals("getpos")) {
			return ceComm.getpos(sender, trimmedArgs);
		} else if (commandName.equals("god")) {
			return ceComm.god(sender, trimmedArgs);
		} else if (commandName.equals("heal")) {
			return ceComm.heal(sender, trimmedArgs);
		} else if (commandName.equals("kick")) {
			return ceComm.kick(sender, trimmedArgs);
		} else if (commandName.equals("kickall")) {
			return ceComm.kickAll(sender, trimmedArgs);
		} else if (commandName.equals("kit")) {
			return ceComm.kit(sender, trimmedArgs);
		} else if (commandName.equals("mail")) {
			return ceComm.mail(sender, trimmedArgs);
		} else if (commandName.equals("modify")) {
			return ceComm.modify(sender, trimmedArgs);
		} else if (commandName.equals("playerlist")) {
			return ceComm.playerlist(sender, trimmedArgs);
		}
		return false;
	}
	
	private void setupPermissions() {
		Plugin p = this.getServer().getPluginManager().getPlugin("GroupManager");
        if (p != null) {
            if (!p.isEnabled()) {
                this.getServer().getPluginManager().enablePlugin(p);
            }
            GroupManager gm = (GroupManager) p;
            Security = gm.getPermissionHandler();
        } else {
            this.getPluginLoader().disablePlugin(this);
        }

	}

	private void checkFiles() {
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdirs();

		ceSettings.initialize(getDataFolder());
		
		if (!new File(getDataFolder(), "motd.properties").exists()) {
			this.createMotdConfig();
			log.info("motd.properties not found, creating.");
		}
		if (!new File(getDataFolder(), "bans.txt").exists()) {
			this.createBansConfig();
			log.info("bans.txt not found, creating.");
		}

	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, this.cepl,
				Event.Priority.High, this);
		pm.registerEvent(Event.Type.PLAYER_LOGIN, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_CHAT, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_ITEM, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_RESPAWN, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_KICK, this.cepl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_DAMAGED, this.cebl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACED, this.cebl,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGED, this.ceel,
				Event.Priority.Highest, this);
	}

	public void sqlConnection() {
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

	public void createMotdConfig() {
		try {
			new File(this.getDataFolder(), "motd.properties").createNewFile();
			FileWriter fstream = new FileWriter(new File(getDataFolder(),
					"motd.properties"));
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("§4Welcome to our §9Minecraft Server§4,§f +d§4!\n");
			out.write("§4There are +online players online!\n");
			out.close();
			fstream.close();
		} catch (IOException ex) {
			setEnabled(false);
		}
	}

	public void createBansConfig() {
		try {
			new File(this.getDataFolder(), "bans.txt").createNewFile();
			FileWriter fstream = new FileWriter(new File(getDataFolder(),
					"bans.txt"));
			BufferedWriter out = new BufferedWriter(fstream);
			out.close();
			fstream.close();
		} catch (IOException ex) {
			setEnabled(false);
		}

	}

	public String[] getBans() {
		ArrayList<String> banlist = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					getDataFolder() + File.separator + "bans.txt"));
			String str;
			while ((str = in.readLine()) != null) {
				banlist.add(str);
			}
			in.close();
		} catch (IOException e) {
			log.info("[CraftEssence] Could not get ban list");
		}

		return banlist.toArray(new String[] {});
	}

	public String locationToString(Location location) {
		StringBuilder test = new StringBuilder();
		test.append(location.getBlockX() + ":");
		test.append(location.getBlockY() + ":");
		test.append(location.getBlockZ() + ":");
		test.append(location.getYaw() + ":");
		test.append(location.getPitch());
		return test.toString();
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
		String wname = home.getWorld().getName();
		try {
			conn = ceConnector.getConnection();
			stmt = conn.createStatement();
			count += stmt.executeUpdate("REPLACE INTO `home`"
					+ " (`name`, `world`, `x`, `y`, `z`, `yaw`, `pitch`)"
					+ " VALUES ('" + getname + "', '" + wname + "', '" + x
					+ "', '" + y + "', '" + z + "', '" + yaw + "', '" + pitch
					+ "')");
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
		String wname = null;

		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement(homeq);
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				wname = rs.getString("world");
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
			return new Location(this.getServer().getWorld(wname), x, y, z, yaw,
					pitch);

		return player.getWorld().getSpawnLocation();
	}

	public boolean kitRank(Player player, String[] args) {
		//String world = player.getWorld().getName();
		//String rank = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement("Select * FROM kit WHERE `name` = '"
					+ args[0] + "'");
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				//rank = rs.getString("rank");
				return true;
			}

		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			return false;
		}
		return false;
	}

	public int kitID(Player player, String[] args) {
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
		String wname = null;

		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement(homeq);
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				wname = rs.getString("world");
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
			return new Location(this.getServer().getWorld(wname), x, y, z, yaw,
					pitch);

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
		String wname = home.getWorld().getName();
		try {
			conn = ceConnector.getConnection();
			stmt = conn.createStatement();
			count += stmt.executeUpdate("REPLACE INTO `warp`"
					+ " (`name`, `world`, `x`, `y`, `z`, `yaw`, `pitch`)"
					+ " VALUES ('" + args[0] + "', '" + wname + "', '" + x
					+ "', '" + y + "', '" + z + "', '" + yaw + "', '" + pitch
					+ "')");
			stmt.close();
			player.sendMessage(CraftEssence.premessage + "Warp '" + args[0]
					+ "' set.");
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Warp did not save.");
		}

	}

	public void addBan(String pname, Player player) {
		try {
			String[] banList = this.getBans();
			ArrayList<String> arraylist = new ArrayList<String>();
			for (String p : banList) {
				if (p != pname) {
					arraylist.add(p);
				}
			}
			new File(getDataFolder() + File.separator + "bans.txt")
					.createNewFile();
			FileWriter fstream = new FileWriter(new File(getDataFolder()
					+ File.separator + "bans.txt"));
			BufferedWriter out = new BufferedWriter(fstream);
			for (String b : arraylist) {
				if (!b.equalsIgnoreCase(pname)) {
					out.write(b + "\n");
				}
			}
			out.write(pname + "\n");
			out.close();
			fstream.close();
		} catch (IOException ex) {
			log.info("[CraftEssence] Player ban did not save");
			setEnabled(false);
		}

	}

	public void removeBan(Player player, String pname) {
		try {
			String[] banList = this.getBans();
			ArrayList<String> arraylist = new ArrayList<String>();
			for (String p : banList) {
				if (p != pname) {
					arraylist.add(p);
				}
			}
			new File(getDataFolder() + File.separator + "bans.txt")
					.createNewFile();
			FileWriter fstream = new FileWriter(new File(getDataFolder()
					+ File.separator + "bans.txt"));
			BufferedWriter out = new BufferedWriter(fstream);
			for (String b : arraylist) {
				if (!b.equalsIgnoreCase(pname)) {
					out.write(b + "\n");
					log.info("Banlist builder.");
					log.info(b);
				}
			}
			out.close();
			fstream.close();
		} catch (IOException ex) {
			log.info("[CraftEssence] " + pname
					+ "  could not be removed from ban list.");
			setEnabled(false);
		}
	}
}
