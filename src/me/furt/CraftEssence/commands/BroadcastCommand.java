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

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "broadcast", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		if (args.length == 0)
			return false;

		String msg = plugin.message(args);
		plugin.getServer().broadcastMessage(
				ChatColor.RED + "[Broadcast] " + ChatColor.YELLOW
						+ plugin.colorizeText(msg));
		return true;
	}

}
