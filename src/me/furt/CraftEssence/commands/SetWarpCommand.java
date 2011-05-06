package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.WarpTable;

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

	public void setWarp(Player player, Location warp, String[] args) {
		// double x = warp.getX();
		// double y = warp.getY();
		// double z = warp.getZ();
		// float yaw = warp.getYaw();
		// float pitch = warp.getPitch();
		String wname = warp.getWorld().getName();

		WarpTable wt = plugin.getDatabase().find(WarpTable.class).where()
				.ieq("name", args[0]).ieq("world", wname).findUnique();
		if (wt == null) {
			wt = new WarpTable();
			wt.setName(args[0]);
		}
		wt.setLocation(warp);
		plugin.getDatabase().save(wt);
		player.sendMessage(CraftEssence.premessage + args[0] + " warp set.");

	}
}
