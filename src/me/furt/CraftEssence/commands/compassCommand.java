package me.furt.CraftEssence.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.furt.CraftEssence.CraftEssence;

public class CompassCommand implements CommandExecutor{
	CraftEssence plugin;
	public CompassCommand(CraftEssence instance) {
		this.plugin = instance;
	}
	
	private String getDirection(double degrees) {
		if (0 <= degrees && degrees < 22.5) {
			return "N";
		} else if (22.5 <= degrees && degrees < 67.5) {
			return "NE";
		} else if (67.5 <= degrees && degrees < 112.5) {
			return "E";
		} else if (112.5 <= degrees && degrees < 157.5) {
			return "SE";
		} else if (157.5 <= degrees && degrees < 202.5) {
			return "S";
		} else if (202.5 <= degrees && degrees < 247.5) {
			return "SW";
		} else if (247.5 <= degrees && degrees < 292.5) {
			return "W";
		} else if (292.5 <= degrees && degrees < 337.5) {
			return "NW";
		} else if (337.5 <= degrees && degrees < 360.0) {
			return "N";
		} else {
			return "ERROR";
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender) == false) {
			CraftEssence.log.info("[CraftEssence] /compass is not a console command.");
			return true;
		}
		Player player = (Player) sender;
		double degreeRotation = ((player.getLocation().getYaw() - 90) % 360);

		if (degreeRotation < 0) {
			degreeRotation += 360.0;
		}
		player.sendMessage(ChatColor.RED + "Compass: "
				+ this.getDirection(degreeRotation));
		return true;
	}

}
