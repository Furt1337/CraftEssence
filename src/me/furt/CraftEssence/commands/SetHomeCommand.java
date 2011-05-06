package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.HomeTable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {
	CraftEssence plugin;

	public SetHomeCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender,
					"craftessence.sethome")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		} else {
			return false;
		}

		Player player = (Player) sender;
		HomeTable home = plugin.getDatabase().find(HomeTable.class).where()
				.ieq("playerName", player.getName())
				.ieq("worldName", player.getWorld().getName()).findUnique();
		
		if (home == null) {
			home = new HomeTable();
			home.setPlayerName(player.getName());
		}
		home.setLocation(player.getLocation());
		plugin.getDatabase().save(home);
		player.sendMessage(CraftEssence.premessage
				+ "Your home location is set.");
		return true;
	}
}
