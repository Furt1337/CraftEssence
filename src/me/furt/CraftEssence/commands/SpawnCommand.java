package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.WarpTable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

	private CraftEssence plugin;

	public SpawnCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!plugin.hasPerm(sender, command)) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		} else {
			CraftEssence.log.info("[CraftEssence] Cannot be used in console.");
			return false;
		}

		Player player = (Player) sender;
		String world = player.getWorld().getName();
		Location loc = null;
		WarpTable wt = plugin.getDatabase().find(WarpTable.class).where()
				.ieq("name", "spwn").ieq("world", world).findUnique();
		if (wt != null)
			loc = wt.getLocation();

		if (loc == null)
			loc = player.getWorld().getSpawnLocation();

		player.teleport(loc);
		sender.sendMessage(CraftEssence.premessage + "Returned to spawn.");
		return true;
	}
}
