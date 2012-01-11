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

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "sethome", false)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		Player player = (Player) sender;
		String wname = player.getWorld().getName();
		HomeTable ht = plugin.getDatabase().find(HomeTable.class).where()
				.ieq("name", player.getName()).ieq("world", wname).findUnique();
		if (ht == null) {
			ht = new HomeTable();
			ht.setName(player.getName());
		}

		ht.setX(player.getLocation().getX());
		ht.setY(player.getLocation().getY());
		ht.setZ(player.getLocation().getZ());
		ht.setYaw(player.getLocation().getYaw());
		ht.setPitch(player.getLocation().getPitch());
		ht.setWorld(player.getLocation().getWorld().getName());
		plugin.getDatabase().save(ht);

		player.sendMessage(CraftEssence.premessage
				+ "Your home location is set.");
		return true;
	}
}
