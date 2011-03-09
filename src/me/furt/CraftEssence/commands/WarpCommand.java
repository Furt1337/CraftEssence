package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
	CraftEssence plugin;

	public WarpCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.isPlayer(sender))
			return false;

		if (args.length == 0)
			return false;
		Player player = (Player) sender;
		player.teleportTo(plugin.getWarp(player, args));
		player.sendMessage(CraftEssence.premessage + "Warping...");
		return true;
	}

}
