package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCommand implements CommandExecutor {
	CraftEssence plugin;

	public TopCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "top", false)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		Player player = (Player) sender;
		int topX = player.getLocation().getBlockX();
		int topZ = player.getLocation().getBlockZ();
		int topY = player.getWorld().getHighestBlockYAt(topX, topZ);
		player.teleport(new Location(player.getWorld(), player.getLocation()
				.getX(), topY + 1, player.getLocation().getZ()));
		player.sendMessage(CraftEssence.premessage + "Teleported up.");
		return true;
	}

}
