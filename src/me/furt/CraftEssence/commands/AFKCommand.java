package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public AFKCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender,
					"craftessence.afk")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}

		if (!plugin.isPlayer(sender)) {
			CraftEssence.log.info("[CraftEssence] Cannot be used in console.");
			return false;
		}

		Player player = (Player) sender;
		if (CraftEssence.afk.contains(player.getName().toLowerCase())) {
			CraftEssence.afk.remove(player.getName().toLowerCase());
			plugin.getServer().broadcastMessage(
					ChatColor.GRAY + "* " + player.getDisplayName()
							+ " is no longer afk *");
		} else {
			CraftEssence.afk.add(player.getName().toLowerCase());
			plugin.getServer().broadcastMessage(
					ChatColor.GRAY + "* " + player.getDisplayName()
							+ " is afk *");
		}
		return true;

	}

}
