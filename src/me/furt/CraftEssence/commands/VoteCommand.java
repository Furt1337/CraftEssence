package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VoteCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public VoteCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if (plugin.isPlayer(sender)) {
			if ((!plugin.hasPerm(sender, command)) && (!sender.isOp())) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("kick")) {
			if(plugin.vote != null ) {
				sender.sendMessage("A vote has already been started.");
				return true;
			}
			return true;
		} else if(args[0].equalsIgnoreCase("day")) {
			if(plugin.vote != null ) {
				sender.sendMessage("A vote has already been started.");
				return true;
			}
			return true;	
		} else if(args[0].equalsIgnoreCase("night")) {
			if(plugin.vote != null ) {
				sender.sendMessage("A vote has already been started.");
				return true;
			}
			return true;		
		} else if(args[0].equalsIgnoreCase("yes")) {
			if(plugin.vote == null ) {
				sender.sendMessage("There is nothing to vote on.");
				return true;
			}
			return true;		
		} else if(args[0].equalsIgnoreCase("no")) {
			if(plugin.vote == null ) {
				sender.sendMessage("There is nothing to vote on.");
				return true;
			}
			return true;		
		} else {
			if(plugin.vote != null ) {
				sender.sendMessage("A vote has already been started.");
				return true;
			} else {
				return true;
			}	
		}
	}
}
