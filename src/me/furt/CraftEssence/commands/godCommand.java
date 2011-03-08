package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {
	CraftEssence plugin;

	public GodCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (!plugin.isPlayer(sender)) {
				return false;
			}
			Player player = (Player) sender;
			CraftEssence.godmode.add(player.getName());
			player.setHealth(20);
			player.sendMessage(CraftEssence.premessage
					+ "You are now invincible!");
			return true;
		}

		if (plugin.playerMatch(args[0]) != null) {
			Player p = plugin.getServer().getPlayer(args[0]);
			if (CraftEssence.godmode.contains(args[0])) {
				CraftEssence.godmode.remove(args[0]);
				p.sendMessage(CraftEssence.premessage
						+ "You have returned to being mortal.");
			} else {
				CraftEssence.godmode.add(args[0]);
				p.setHealth(20);
				p.sendMessage(CraftEssence.premessage
						+ "You are now invincible!");
			}
			return true;
		} else {
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.sendMessage(CraftEssence.premessage
						+ "Player not found.");
			}
			CraftEssence.log.info("[CraftEssence] Player not found.");
			return true;
		}
	}

}
