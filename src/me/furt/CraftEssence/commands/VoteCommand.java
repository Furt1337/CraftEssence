package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public VoteCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if (plugin.isPlayer(sender)) {
			if ((!plugin.hasPerm(sender, "vote")) && (!sender.isOp())) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		Player player = (Player) sender;
		Server srv = plugin.getServer();
		if (args[0].equalsIgnoreCase("kick")) {
			if(plugin.vote != null ) {
				sender.sendMessage("A vote has already been started.");
				return true;
			}
			plugin.vote = "kick";
			plugin.vuser.put(player.getName(), "yes");
			srv.broadcastMessage(CraftEssence.premessage + "A vote has started to kick the player" + args[2] +", to vote type '/vote yes' or '/vote no'");
			return true;
		} else if(args[0].equalsIgnoreCase("day")) {
			if(plugin.vote != null) {
				sender.sendMessage(CraftEssence.premessage + "A vote has already been started.");
				return true;
			}
			plugin.vote = "day";
			plugin.vuser.put(player.getName(), "yes");
			srv.broadcastMessage(CraftEssence.premessage + "A vote has started to make it day, to vote type '/vote yes' or '/vote no'");
			return true;	
		} else if(args[0].equalsIgnoreCase("night")) {
			if(plugin.vote != null) {
				sender.sendMessage(CraftEssence.premessage + "A vote has already been started.");
				return true;
			}
			plugin.vote = "night";
			plugin.vuser.put(player.getName(), "yes");
			srv.broadcastMessage(CraftEssence.premessage + "A vote has started to make it night, to vote type '/vote yes' or '/vote no'");
			return true;		
		} else if(args[0].equalsIgnoreCase("yes")) {
			if(plugin.vote == null) {
				sender.sendMessage(CraftEssence.premessage + "There is nothing to vote on.");
				return true;
			}
			if (plugin.vuser.containsKey(player.getName())) {
				sender.sendMessage(CraftEssence.premessage + "You have already voted.");
				return true;
			}
			plugin.vuser.put(player.getName(), "yes");
			// Get total votes
			int yesno = plugin.vuser.values().size();
			srv.broadcastMessage(CraftEssence.premessage + "There are " + yesno + " votes so far.");
			
			/* Needs to go in timer
			int yes = 0;
			int no = 0;
			Collection<?> c = plugin.vuser.values();
			Iterator<?> itr = c.iterator();
			while(itr.hasNext()) {
				String vte = itr.next().toString();
				if (vte.equalsIgnoreCase("yes")) {
					yes = yes + 1;
				} else {
					no = no + 1;
				}
			}
			int po = srv.getOnlinePlayers().length;
			int neededVotes = (65/100)*po;
			if (neededVotes <= (yes - (no/2))) {
				// trigger vote success
			} else {
			    // trigger vote fail
			} */
			return true;		
		} else if(args[0].equalsIgnoreCase("no")) {
			if(plugin.vote == null) {
				sender.sendMessage(CraftEssence.premessage + "There is nothing to vote on.");
				return true;
			}
			return true;		
		} else {
			if(plugin.vote != null ) {
				sender.sendMessage(CraftEssence.premessage + "A vote has already been started.");
				return true;
			} else {
				sender.sendMessage(CraftEssence.premessage + "The option you want to vote on is invalid.");
				return true;
			}	
		}
	}
}
