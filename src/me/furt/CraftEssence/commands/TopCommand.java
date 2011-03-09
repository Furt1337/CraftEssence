package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

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

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.isPlayer(sender))
			return false;
		
		Player player = (Player) sender;
		int topX = player.getLocation().getBlockX();
		int topZ = player.getLocation().getBlockZ();
		int topY = player.getWorld().getHighestBlockYAt(topX, topZ);
		player.teleportTo(new Location(player.getWorld(), player.getLocation()
				.getX(), topY + 1, player.getLocation().getZ()));
		player.sendMessage(CraftEssence.premessage + "Teleported up.");
		return true;
	}

}
