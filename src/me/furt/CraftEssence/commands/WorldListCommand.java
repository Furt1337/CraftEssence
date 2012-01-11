package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WorldListCommand implements CommandExecutor {
	CraftEssence plugin;

	public WorldListCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "worldlist", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		sender.sendMessage(ChatColor.YELLOW + "Worlds running on this Server");
		for (int i = 0; i < plugin.getServer().getWorlds().size(); i++) {
			ChatColor color;
			if (((World) plugin.getServer().getWorlds().get(i))
					.getEnvironment() == World.Environment.NETHER)
				color = ChatColor.RED;
			else if (((World) plugin.getServer().getWorlds().get(i))
					.getEnvironment() == World.Environment.NORMAL) {
				color = ChatColor.GREEN;
			} else {
				color = ChatColor.BLACK;
			}
			sender.sendMessage(color
					+ ((World) plugin.getServer().getWorlds().get(i)).getName());
		}
		return true;
	}

}
