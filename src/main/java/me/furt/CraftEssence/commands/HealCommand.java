package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
	CraftEssence plugin;

	public HealCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "heal", false)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		if (args.length == 0) {
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.setHealth(20);
				player.sendMessage(CraftEssence.premessage
						+ "You are fully healed.");
				return true;
			}
			return false;
		} else if (args.length == 1) {
			if (plugin.playerMatch(args[0]) != null) {
				Player p = plugin.getServer().getPlayer(args[0]);
				p.setHealth(20);
				p.sendMessage(CraftEssence.premessage + "You are fully healed!");
				sender.sendMessage(CraftEssence.premessage + "You healed "
						+ args[0] + ".");
				return true;
			} else {
				sender.sendMessage(CraftEssence.premessage
						+ "Player not found.");
				return true;
			}
		} else {
			sender.sendMessage(CraftEssence.premessage
					+ "Invalid heal command parameter.");
			return true;
		}
	}
}
