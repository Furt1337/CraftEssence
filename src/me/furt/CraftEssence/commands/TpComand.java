package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpComand implements CommandExecutor {
	CraftEssence plugin;

	public TpComand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "tp", false)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You to dont have proper permissions for that command.");
			return true;
		}

		if (args.length == 0) {
			plugin.getServer().broadcastMessage("--Teleport Help--");
			plugin.getServer().broadcastMessage("/tp [player]");
			plugin.getServer().broadcastMessage("/tp [playera] [playerb]");
			return true;
		}

		if (args.length == 1) {
			Player player = (Player) sender;
			if (plugin.playerMatch(args[0]) == null) {
				player.sendMessage(CraftEssence.premessage + "Player not found");
				return true;
			} else {
				Player p = this.plugin.getServer().getPlayer(args[0]);
				player.teleport(p);
				sender.sendMessage(CraftEssence.premessage + "Teleporting to "
						+ args[0] + ".");
				return true;
			}
		}
		if (args.length == 2) {
			Player a = this.plugin.getServer().getPlayer(args[0]);
			Player b = this.plugin.getServer().getPlayer(args[1]);
			a.teleport(b);
			if (plugin.isPlayer(sender))
				sender.sendMessage(CraftEssence.premessage + "Teleporting "
						+ args[0] + "to " + args[1] + ".");

			CraftEssence.log.info("Teleporting " + args[0] + "to " + args[1]
					+ ".");
		}
		return false;
	}

}
