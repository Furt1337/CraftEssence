package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
	CraftEssence plugin;

	public SpawnCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.isPlayer(sender))
			return false;
		
		Player player = (Player) sender;
		player.teleportTo(player.getWorld().getSpawnLocation());
		player.sendMessage(CraftEssence.premessage + "Returned to spawn.");
		return true;
	}

}
