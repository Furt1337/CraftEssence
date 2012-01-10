package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeatherCommand implements CommandExecutor {
	
	private final CraftEssence plugin;

	public WeatherCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!plugin.hasPerm(sender, "weather", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You to dont have proper permissions for that command.");
			return true;
		}
		
		Player player = (Player) sender;
		World world = player.getWorld();
		if (args[0].equalsIgnoreCase("sunny")) {
			world.setStorm(false);
			return true;
		} else if (args[0].equalsIgnoreCase("rainy")) {
			world.setStorm(true);
			return true;
		} else {
			return true;
		}
	}

}
