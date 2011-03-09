package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpComand implements CommandExecutor {
	CraftEssence plugin;

	public TpComand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0)
			return false;

		if (args.length == 1) {
			if (!plugin.isPlayer(sender))
				return false;

			Player player = (Player) sender;
			if (plugin.playerMatch(args[0]) == null) {
				player.sendMessage(CraftEssence.premessage + "Player not found");
				return true;
			} else {
				Player p = this.plugin.getServer().getPlayer(args[0]);
				player.teleportTo(p);
				sender.sendMessage(CraftEssence.premessage + "Teleporting to "
						+ args[0] + ".");
				return true;
			}
		}
		if (args.length == 2) {
			Player a = this.plugin.getServer().getPlayer(args[0]);
			Player b = this.plugin.getServer().getPlayer(args[1]);
			a.teleportTo(b);
			if (plugin.isPlayer(sender))
				sender.sendMessage(CraftEssence.premessage + "Teleporting "
						+ args[0] + "to " + args[1] + ".");

			CraftEssence.log.info("Teleporting " + args[0] + "to " + args[1]
					+ ".");
		}
		return false;
	}

}
