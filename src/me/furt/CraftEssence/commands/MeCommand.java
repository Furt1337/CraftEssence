package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {
	CraftEssence plugin;

	public MeCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!plugin.hasPerm(sender, "me")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		} else {
			CraftEssence.log.info("[CraftEssence] Cannot be used in console.");
			return false;
		}

		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			String msg = plugin.message(args);
			plugin.getServer().broadcastMessage(
					ChatColor.GRAY + "* " + player.getName() + " " + msg + "*");
		}
		return true;
	}

}
