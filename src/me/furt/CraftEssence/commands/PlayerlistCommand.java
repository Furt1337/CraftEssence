package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerlistCommand implements CommandExecutor {
	CraftEssence plugin;

	public PlayerlistCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		StringBuilder online = new StringBuilder();
		int intonline = 0;
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if ((p == null) || (!p.isOnline())) {
				continue;
			}
			++intonline;
		}

		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if ((p == null) || (!p.isOnline())) {
				continue;
			}
			online.append(online.length() == 0 ? ChatColor.YELLOW
					+ "Connected players (" + intonline + "/"
					+ plugin.getServer().getMaxPlayers() + "): "
					+ ChatColor.WHITE : ", ");
			online.append(p.getDisplayName());
		}
		if (plugin.isPlayer(sender)) {
			Player player = (Player) sender;
			player.sendMessage(online.toString());
		} else {
			CraftEssence.log.info(online.toString());
		}
		return true;
	}

}
