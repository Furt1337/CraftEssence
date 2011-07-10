package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.WarpTable;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
	CraftEssence plugin;

	public SetSpawnCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if ((!plugin.hasPerm(sender, "setspawn")) && (!sender.isOp())) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		} else {
			CraftEssence.log.info("[CraftEssence] Cannot be used in console.");
			return false;
		}

		Player player = (Player) sender;
		World world = player.getWorld();
		int x = (int) player.getLocation().getX();
		int y = (int) player.getLocation().getY();
		int z = (int) player.getLocation().getZ();
		world.setSpawnLocation(x, y, z);
		
		String wname = player.getWorld().getName();
		WarpTable wt = plugin.getDatabase().find(WarpTable.class).where().ieq("name", wname + "-spwn").findUnique();
		if (wt != null) {
			player.sendMessage(CraftEssence.premessage
					+ "Spawn location is already set.");
			return true;
		}
		if (wt == null) {
			wt = new WarpTable();
			wt.setName(wname + "-spwn");
		}
		
		wt.setLocation(player.getLocation());
		plugin.getDatabase().save(wt);
		player.sendMessage(CraftEssence.premessage + "Spawn position modified.");
		return true;
	}
}
