package me.furt.CraftEssence.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.ceConnector;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {
	CraftEssence plugin;

	public SetHomeCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender,
					"craftessence.sethome")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;

		Player player = (Player) sender;
		this.setHome(player, player.getLocation());
		return true;
	}

	public boolean hasHome(Player player) {
		String world = player.getWorld().getName();
		String getname = player.getName();
		String homeq = "Select * FROM home WHERE `name` = '" + getname + "'";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String world1;

		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement(homeq);
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				world1 = rs.getString("world");
				if (world1.equalsIgnoreCase(world))
					return true;
			}
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			return false;
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
		return false;
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
			if (!this.hasHome(player)) {
				count += stmt.executeUpdate("INSERT INTO `home`"
						+ " (`name`, `world`, `x`, `y`, `z`, `yaw`, `pitch`)"
						+ " VALUES ('" + getname + "', '" + wname + "', '" + x
						+ "', '" + y + "', '" + z + "', '" + yaw + "', '"
						+ pitch + "')");
			} else {
				count += stmt.executeUpdate("UPDATE `home`" + "SET x = '" + x
						+ "', y = '" + y + "', z = '" + z + "', yaw = '" + yaw
						+ "', pitch = '" + pitch + "'" + "WHERE `name` = '"
						+ getname + "' AND `world` = '" + wname + "'");
			}
			stmt.close();
			player.sendMessage(CraftEssence.premessage + "Home set.");
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Home did not save.");
		}
	}

}
