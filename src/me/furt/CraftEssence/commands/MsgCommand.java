package me.furt.CraftEssence.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.furt.CraftEssence.CraftEssence;

public class MsgCommand implements CommandExecutor {
	CraftEssence plugin;

	public MsgCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender,
					"craftessence.msg")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;

		Player player = (Player) sender;
		if (args.length == 0) {
			return false;
		} else {
			String msg = plugin.message(args).replace(args[0], "");
			Player sendto = plugin.playerMatch(args[0]);
			if (sendto != null) {
				if (sendto.getName().equals(player.getName())) {
					player.sendMessage(CraftEssence.premessage
							+ "You can't message yourself!");
				} else {
					sendto.sendMessage(ChatColor.GRAY + "[MSG]<"
							+ player.getName() + ">" + msg);
					player.sendMessage(ChatColor.GRAY + "[MSG]<"
							+ player.getName() + ">" + msg);
				}
			} else {
				player.sendMessage(CraftEssence.premessage
						+ "Couldn't find player " + args[0]);
			}
		}
		return true;
	}

}
