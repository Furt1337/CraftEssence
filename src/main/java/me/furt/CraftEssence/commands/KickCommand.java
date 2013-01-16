package me.furt.CraftEssence.commands;

import java.util.logging.Level;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {
	CraftEssence plugin;

	public KickCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "kick", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		if (args.length == 0)
			return false;

		String msg = plugin.message(args).replace(args[0], "");
		if (args.length < 2) {
			msg = " Dont be a noob!";
		}
		if (args[0].equalsIgnoreCase("*")) {
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if (plugin.isPlayer(sender)) {
					if (sender != p)
						p.kickPlayer("You have been kicked, reason:" + msg);
				} else {
					p.kickPlayer("You have been kicked, reason:" + msg);
				}
				plugin.getServer().broadcastMessage(
						ChatColor.RED + p.getName() + " has been kicked.");
				plugin.getLogger().log(Level.INFO,
						p.getName() + " has been kicked.");
			}
			return true;
		} else {
			if (plugin.playerMatch(args[0]) != null) {
				Player p = plugin.getServer().getPlayer(args[0]);
				p.kickPlayer("You have been kicked, reason:" + msg);
				plugin.getServer().broadcastMessage(
						"�6" + p.getName() + " was kicked.");
				plugin.logger(Level.INFO, p.getName() + " has been kicked.");
			}
			return true;
		}
	}
}
