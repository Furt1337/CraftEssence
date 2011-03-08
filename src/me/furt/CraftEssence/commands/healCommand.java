package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class healCommand implements CommandExecutor {
	CraftEssence plugin;

	public healCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.setHealth(20);
				player.sendMessage(CraftEssence.premessage
						+ "You are fully healed.");
				return true;
			}
			return false;
		}
		if (args.length == 1) {
			if (plugin.playerMatch(args[0]) != null) {
				Player p = plugin.getServer().getPlayer(args[0]);
				p.setHealth(20);
				p.sendMessage(CraftEssence.premessage + "You are fully healed!");
				if (plugin.isPlayer(sender)) {
					Player player = (Player) sender;
					player.sendMessage(CraftEssence.premessage + "You healed "
							+ args[0] + ".");
					return true;
				} else {
					if (plugin.isPlayer(sender)) {
						Player player = (Player) sender;
						player.sendMessage(CraftEssence.premessage
								+ "Player not found.");
					}
					CraftEssence.log.info("[CraftEssence] Player not found.");
					return true;
				}
			}
		}
		return false;
	}
}
