package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {
	CraftEssence plugin;

	public MeCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "me", false)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			String msg = plugin.message(args);
			plugin.getServer().broadcastMessage(
					ChatColor.GRAY + "* " + player.getName() + " " + msg + "*");
		}
		return true;
	}

}
