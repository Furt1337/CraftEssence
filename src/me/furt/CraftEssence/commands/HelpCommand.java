package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class HelpCommand implements CommandExecutor {
	CraftEssence plugin;
	public final Yaml yaml = new Yaml(new SafeConstructor());

	public HelpCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return false;
	}
}
