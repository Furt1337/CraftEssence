package me.furt.CraftEssence.commands;

import java.sql.Connection;
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
		this.setSpawn(player, player.getLocation());
		player.sendMessage(CraftEssence.premessage + "Spawn position modified.");
		return true;
	}

	public void setSpawn(Player player, Location spawn) {
		Connection conn = null;
		Statement stmt = null;
		int count = 0;
		double x = spawn.getX();
		double y = spawn.getY();
		double z = spawn.getZ();
		float yaw = spawn.getYaw();
		float pitch = spawn.getPitch();
		String wname = spawn.getWorld().getName();
		try {
			conn = ceConnector.getConnection();
			stmt = conn.createStatement();
			count += stmt.executeUpdate("REPLACE INTO `warp`"
					+ " (`name`, `world`, `x`, `y`, `z`, `yaw`, `pitch`)"
					+ " VALUES ('spwn', '" + wname + "', '" + x
					+ "', '" + y + "', '" + z + "', '" + yaw + "', '" + pitch
					+ "')");
			stmt.close();
		} catch (SQLException ex) {
			CraftEssence.log.log(Level.SEVERE,
					"[CraftEssence]: Find SQL Exception", ex);
			player.sendMessage(CraftEssence.premessage + "Spawn did not save.");
		}

	}
}
