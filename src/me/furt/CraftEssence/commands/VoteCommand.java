package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public VoteCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (plugin.isPlayer(sender)) {
			Player player = (Player) sender;
			if (!CraftEssence.Permissions.has(player, "craftessence.vote")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("kick")) {
			return true;
		} else if(args[0].equalsIgnoreCase("day")) {
			return true;	
		} else if(args[0].equalsIgnoreCase("night")) {
			return true;		
		} else {
			return true;	
		}
	}
}
