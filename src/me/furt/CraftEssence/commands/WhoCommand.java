package me.furt.CraftEssence.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

public class WhoCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public WhoCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "who", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}
		
		UserTable ut = plugin.getDatabase().find(UserTable.class).where()
				.ieq("displyName", args[0]).findUnique();
		if (ut != null) {
			Player p = plugin.getServer().getPlayer(ut.getUserName());
			sender.sendMessage(ChatColor.YELLOW + "-------- " + ChatColor.RED + "Stats" + ChatColor.YELLOW + " ---------");
			sender.sendMessage(ChatColor.YELLOW + "Account Name: " + ut.getUserName());
			sender.sendMessage(ChatColor.YELLOW + "Display Name: " + ut.getDisplyName());
			sender.sendMessage(ChatColor.YELLOW + "Health: " + p.getHealth() + "/20");
			sender.sendMessage(ChatColor.YELLOW + "IP: " + p.getAddress().getHostName());
			sender.sendMessage(ChatColor.YELLOW + "Logins: " + ut.getLogins());
			sender.sendMessage(ChatColor.YELLOW + "Is AFK: " + ut.isAfk());
			sender.sendMessage(ChatColor.YELLOW + "Is Muted: " + ut.isMuted());
		} else {
			sender.sendMessage(CraftEssence.premessage + "Player not found.");
		}
		return true;
	}
}
