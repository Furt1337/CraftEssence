package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

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

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length < 2) {
			return false;
		}
		World world = plugin.getServer().getWorld(args[0]);
		long time = world.getTime();
		time -= time % 24000L;
		if (args.length == 2) {
			if ("day".equalsIgnoreCase(args[1])) {
				world.setTime(time + 24000L);
				plugin.getServer().broadcastMessage(
						CraftEssence.premessage + "Time is set to day.");
				CraftEssence.log.info("[CraftEssence] Time is set to day");
				return true;
			} else if ("night".equalsIgnoreCase(args[1])) {
				world.setTime(time + 37700L);
				plugin.getServer().broadcastMessage(
						CraftEssence.premessage + "Time is set to night.");
				CraftEssence.log.info("[CraftEssence] Time is set to night");
				return true;
			} else {
				if (plugin.isPlayer(sender)) {
					Player player = (Player) sender;
					player.sendMessage(CraftEssence.premessage
							+ "/time only supports day/night.");
				} else {
					CraftEssence.log
							.info("[CraftEssence] /time only supports day/night");
				}
				return true;
			}
		}
		return false;
	}

}
