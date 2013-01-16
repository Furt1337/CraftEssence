package me.furt.CraftEssence.commands;

import java.util.logging.Level;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public TimeCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "time", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		World world = null;

		if (args.length == 1) {
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				world = player.getWorld();
			} else {
				return false;
			}
			long time = world.getTime();
			time -= time % 24000L;
			if ("day".equalsIgnoreCase(args[0])) {
				world.setTime(time + 24000L);
				plugin.getServer().broadcastMessage(
						CraftEssence.premessage + world.getName()
								+ "'s time is set to day.");
				plugin.logger(Level.INFO, world.getName()
						+ "'s time is set to day");
				return true;
			} else if ("night".equalsIgnoreCase(args[0])) {
				world.setTime(time + 37700L);
				plugin.getServer().broadcastMessage(
						CraftEssence.premessage + world.getName()
								+ "'s time is set to night.");
				plugin.logger(Level.INFO, world.getName()
						+ "'s time is set to night");
				return true;
			} else {
				if (plugin.isPlayer(sender)) {
					Player player = (Player) sender;
					player.sendMessage(CraftEssence.premessage
							+ "/time only supports day/night.");
				} else {
					plugin.logger(Level.INFO, "/time only supports day/night");
				}
				return true;
			}
		}
		world = plugin.getServer().getWorld(args[1]);
		long time = world.getTime();
		time -= time % 24000L;
		if (args.length == 2) {
			if ("day".equalsIgnoreCase(args[0])) {
				world.setTime(time + 24000L);
				plugin.getServer().broadcastMessage(
						CraftEssence.premessage + world.getName()
								+ "'s time is set to day.");
				plugin.logger(Level.INFO, world.getName()
						+ "'s time is set to day");
				return true;
			} else if ("night".equalsIgnoreCase(args[0])) {
				world.setTime(time + 37700L);
				plugin.getServer().broadcastMessage(
						CraftEssence.premessage + world.getName()
								+ "'s time is set to night.");
				plugin.logger(Level.INFO, world.getName()
						+ "'s time is set to night");
				return true;
			} else {
				if (plugin.isPlayer(sender)) {
					Player player = (Player) sender;
					player.sendMessage(CraftEssence.premessage
							+ "/time only supports day/night.");
				} else {
					plugin.logger(Level.INFO, "/time only supports day/night");
				}
				return true;
			}
		}
		return false;
	}

}
