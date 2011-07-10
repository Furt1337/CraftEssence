package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public BroadcastCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if ((!plugin.hasPerm(sender, "broadcast")) && (!sender.isOp())) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (args.length == 0)
			return false;

		String msg = plugin.message(args);
		plugin.getServer().broadcastMessage(
				ChatColor.RED + "[Broadcast] " + ChatColor.YELLOW + msg);
		CraftEssence.log.info("[Broadcast] " + msg);
		return true;
	}

}
