package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SpawnMobCommand implements CommandExecutor {
	CraftEssence plugin;

	public SpawnMobCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.isPlayer(sender)) {
			if (args.length != 3) {
				sender.sendMessage("");
				return true;
			}
		}
		return false;
	}
	public void test() {
	
	}
}
