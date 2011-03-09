package me.furt.CraftEssence.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.ceConnector;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
	CraftEssence plugin;

	public WarpCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender, "craftessence.warp")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;

		if (args.length == 0)
			return false;
		Player player = (Player) sender;
		player.teleportTo(this.getWarp(player, args));
		player.sendMessage(CraftEssence.premessage + "Warping...");
		return true;
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
			return new Location(plugin.getServer().getWorld(wname), x, y, z, yaw,
					pitch);

		return player.getWorld().getSpawnLocation();
	}

}
