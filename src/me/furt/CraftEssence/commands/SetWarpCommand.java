package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.WarpTable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {
	CraftEssence plugin;

	public SetWarpCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if ((!plugin.hasPerm(sender, "setwarp")) && (!sender.isOp())) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		} else {
			CraftEssence.log.info("[CraftEssence] Cannot be used in console.");
			return false;
		}

		Player player = (Player) sender;
		String wname = player.getWorld().getName();
		WarpTable wt = plugin.getDatabase().find(WarpTable.class).where()
				.ieq("name", args[0]).ieq("world", wname).findUnique();
		if (wt != null) {
			player.sendMessage(CraftEssence.premessage
					+ "Your warp location is already set.");
			return true;
		}
		if (wt == null) {
			wt = new WarpTable();
			wt.setName(args[0]);
		}
		wt.setLocation(player.getLocation());
		plugin.getDatabase().save(wt);
		player.sendMessage(CraftEssence.premessage + args[0] + " warp set.");
		return true;
	}
}
