package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

import org.bukkit.ChatColor;
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
		if (plugin.isPlayer(sender)) {
			if ((!plugin.hasPerm(sender, command)) && (!sender.isOp())) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		
		if (!plugin.isPlayer(sender)) {
			CraftEssence.log
						.info("[CraftEssence] Cannot be used in console without argument.");
			return false;
		}
		Player player = null;
		if (args.length == 0) {
			player = (Player) sender;
		} else {
			if (plugin.playerMatch(args[0]) != null) {
				player = plugin.getServer().getPlayer(args[0]);
			} else {
				sender.sendMessage(CraftEssence.premessage
							+ "Player not found.");
				return true;
			}
		}
		String pName = player.getName();
		UserTable ut = plugin.getDatabase().find(UserTable.class).where()
					.ieq("userName", pName).findUnique();

		if (ut.isGod()) {
			ut.setGod(false);
			sender.sendMessage(ChatColor.YELLOW
					+ "You have returned to being mortal.");
		} else {
			ut.setGod(true);
			sender.sendMessage(ChatColor.YELLOW
					+ "You are now invincible!");
		}
		plugin.getDatabase().save(ut);
		return true;
	}

}
