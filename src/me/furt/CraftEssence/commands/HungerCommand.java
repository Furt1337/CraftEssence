package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HungerCommand implements CommandExecutor {
	private CraftEssence plugin;

	public HungerCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (!plugin.hasPerm(sender, "hunger", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You to dont have proper permissions for that command.");
			return true;
		}

		if (args.length == 0) {
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.setFoodLevel(20);
				player.setSaturation(20);
				player.sendMessage(CraftEssence.premessage
						+ "You are no longer hungry!.");
				return true;
			}
			return false;
		} else if (args.length == 1) {
			if (plugin.playerMatch(args[0]) != null) {
				Player p = plugin.getServer().getPlayer(args[0]);
				p.setFoodLevel(20);
				p.setSaturation(20);
				p.sendMessage(CraftEssence.premessage + "You are no longer hungry!");
				sender.sendMessage(CraftEssence.premessage + "You fed "
						+ args[0] + ", they are no longer hungry.");
				return true;
			} else {
				sender.sendMessage(CraftEssence.premessage
						+ "Player not found.");
				return true;
			}
		} else {
			sender.sendMessage(CraftEssence.premessage
					+ "Invalid hunger command parameter.");
			return true;
		}
	}

}
