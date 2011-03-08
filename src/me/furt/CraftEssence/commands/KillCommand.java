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

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args[0] == "*") {
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if (plugin.isPlayer(sender)) {
					Player player = (Player) sender;
					if (player != p)
						p.setHealth(0);
				} else {
					p.setHealth(0);
				}
				plugin.getServer().broadcastMessage(
						ChatColor.RED + p.getName() + " has died by the gods.");
			}
			return true;
		} else {
			if (plugin.playerMatch(args[0]) != null) {
				Player p = plugin.getServer().getPlayer(args[0]);
				p.setHealth(0);
				if (plugin.isPlayer(sender)) {
					Player player = (Player) sender;
					if (player == p) {
						plugin.getServer().broadcastMessage(
								"§6" + player.getName()
										+ " has commited suicide, noob.");
					} else {
						plugin.getServer().broadcastMessage(
								"§6" + p.getName() + " has died by the gods.");
					}
				} else {
					plugin.getServer().broadcastMessage(
							"§6" + p.getName() + " has died by the gods.");
				}
			}
			return true;
		}
	}

}
