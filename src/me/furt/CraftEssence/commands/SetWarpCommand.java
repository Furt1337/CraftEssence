package me.furt.CraftEssence.commands;

import java.sql.Connection;
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

public class SetWarpCommand implements CommandExecutor {
	CraftEssence plugin;

	public SetWarpCommand(CraftEssence instance) {
		this.plugin = instance;
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
			player.sendMessage(CraftEssence.premessage + args[0] + " warp set.");
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Warp did not save.");
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender,
					"craftessence.setwarp")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;

		Player player = (Player) sender;
		this.setWarp(player, player.getLocation(), args);
		return true;
	}

}
