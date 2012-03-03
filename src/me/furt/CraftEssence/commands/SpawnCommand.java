package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.WarpTable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
		if (!plugin.hasPerm(sender, "spawn", false)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		Player player = (Player) sender;
		String world = player.getWorld().getName();
		Location loc;
		WarpTable sl = plugin.getDatabase().find(WarpTable.class).where()
				.ieq("name", world + "-spwn").findUnique();
		if (sl != null) {
			loc = this.getLocation(sl);
		} else {
			loc = player.getWorld().getSpawnLocation();
		}

		player.teleport(loc);
		sender.sendMessage(CraftEssence.premessage + "Returned to spawn.");
		return true;
	}

	private Location getLocation(WarpTable sl) {
		World world = Bukkit.getServer().getWorld(sl.getWorld());
		return new Location(world, sl.getX(), sl.getY(), sl.getZ(),
				sl.getYaw(), sl.getPitch());
	}
}
