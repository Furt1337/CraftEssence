package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public MuteCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender,
					"craftessence.mute")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (args.length == 0)
			return false;
		if (plugin.playerMatch(args[0]) != null) {
			if (CraftEssence.muteList.contains(args[0].toLowerCase())) {
				CraftEssence.muteList.remove(args[0].toLowerCase());
				plugin.getServer().broadcastMessage(
						CraftEssence.premessage + args[0]
								+ " has been unmuted.");
				CraftEssence.log.info("[CraftEssence] " + args[0]
						+ " has been unmuted.");
			} else {
				CraftEssence.muteList.add(args[0].toLowerCase());
				plugin.getServer().broadcastMessage(
						CraftEssence.premessage + args[0] + " has been muted.");
				CraftEssence.log.info("[CraftEssence] " + args[0]
						+ " has been muted.");
			}
		} else {
			if (plugin.isPlayer(sender))
				sender.sendMessage("Player could not be found.");
			CraftEssence.log.info("Player could not be found.");
		}
		return true;
	}

}
