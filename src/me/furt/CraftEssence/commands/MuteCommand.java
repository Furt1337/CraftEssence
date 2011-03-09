package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuteCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public MuteCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0)
			return false;
		if (plugin.playerMatch(args[0]) != null) {
			if (CraftEssence.muteList.contains(args[0])) {
				CraftEssence.muteList.remove(args[0]);
				plugin.getServer().broadcastMessage(
						args[0] + " has been unmuted.");
				CraftEssence.log.info(args[0] + " has been unmuted.");
			} else {
				CraftEssence.muteList.add(args[0]);
				plugin.getServer().broadcastMessage(
						args[0] + " has been muted.");
				CraftEssence.log.info(args[0] + " has been muted.");
			}
		} else {
			if (plugin.isPlayer(sender))
				sender.sendMessage("Player could not be found.");
			CraftEssence.log.info("Player could not be found.");
		}
		return true;
	}

}
