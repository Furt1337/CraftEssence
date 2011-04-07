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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
	CraftEssence plugin;

	public SetSpawnCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			Player player = (Player) sender;
			if (!CraftEssence.Permissions.has(player, "craftessence.setspawn")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;

		Player player = (Player) sender;
		World world = player.getWorld();
		int x = (int) player.getLocation().getX();
		int y = (int) player.getLocation().getY();
		int z = (int) player.getLocation().getZ();
		world.setSpawnLocation(x, y, z);
		player.sendMessage(CraftEssence.premessage + "Spawn position modified.");
		//this.setSpawn(player, player.getLocation());
		return true;
	}

	private boolean hasSpawn(Player player) {
		String world = player.getWorld().getName();
		String spawnq = "Select * FROM `warp` WHERE `world` = '" + world + "'";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String spawn;

		try {
			conn = ceConnector.getConnection();
			ps = conn.prepareStatement(spawnq);
			rs = ps.executeQuery();
			conn.commit();
			while (rs.next()) {
				spawn = rs.getString("name");
				if (spawn.equalsIgnoreCase("spawn"))
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

	private void setSpawn(Player player, Location home) {
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
			if (!this.hasSpawn(player)) {
				count += stmt
						.executeUpdate("INSERT INTO `warp`"
								+ " (`name`, `world`, `x`, `y`, `z`, `yaw`, `pitch`)"
								+ " VALUES ('spawn', '" + wname + "', '" + x
								+ "', '" + y + "', '" + z + "', '" + yaw
								+ "', '" + pitch + "')");
			} else {
				count += stmt.executeUpdate("UPDATE `warp`" + "SET x = '" + x
						+ "', y = '" + y + "', z = '" + z + "', yaw = '" + yaw
						+ "', pitch = '" + pitch + "'"
						+ "WHERE `name` = 'spawn' AND `world` = '" + wname
						+ "'");
			}
			stmt.close();
			player.sendMessage(CraftEssence.premessage + "Spawn position modified.");
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Spawn did not save.");
		}
	}
}
