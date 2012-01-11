package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillCommand implements CommandExecutor {
	CraftEssence plugin;

	public KillCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "kill", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		if (args.length == 0)
			return false;

		if (args[0].equalsIgnoreCase("*")) {
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if (plugin.isPlayer(sender)) {
					if (sender != p)
						p.setHealth(0);
				} else {
					p.setHealth(0);
				}
				plugin.getServer().broadcastMessage(
						ChatColor.RED + p.getName() + " has died by the gods.");
				CraftEssence.log.info(p.getName() + " has died by command.");
			}
			return true;
		} else {
			if (plugin.playerMatch(args[0]) != null) {
				Player p = plugin.getServer().getPlayer(args[0]);
				p.setHealth(0);
				if (plugin.isPlayer(sender)) {
					if (sender == p) {
						plugin.getServer().broadcastMessage(
								ChatColor.GOLD + ((Player) sender).getName()
										+ " has commited suicide, noob.");
					} else {
						plugin.getServer().broadcastMessage(
								ChatColor.GOLD + p.getName()
										+ " has died by the gods.");
					}
				} else {
					plugin.getServer().broadcastMessage(
							ChatColor.GOLD + p.getName()
									+ " has died by the gods.");
				}
				CraftEssence.log.info("[CraftEssence] " + p.getName()
						+ " has died by command.");
			}
			return true;
		}
	}

}
