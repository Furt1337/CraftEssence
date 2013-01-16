package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public WhoCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "who", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		if (args.length == 1) {
			UserTable ut = plugin.getDatabase().find(UserTable.class).where()
					.ieq("displyName", args[0]).findUnique();
			if (ut != null) {
				Player p = plugin.getServer().getPlayer(ut.getUserName());
				String mute;
				if (ut.isMuted()) {
					mute = "yes";
				} else {
					mute = "no";
				}
				String afk;
				if (ut.isAfk()) {
					afk = "yes";
				} else {
					afk = "no";
				}
				sender.sendMessage(ChatColor.YELLOW + "-------- "
						+ ChatColor.RED + "Stats" + ChatColor.YELLOW
						+ " ---------");
				sender.sendMessage(ChatColor.YELLOW + "Account Name: "
						+ ut.getUserName());
				sender.sendMessage(ChatColor.YELLOW + "Display Name: "
						+ ut.getDisplyName());
				sender.sendMessage(ChatColor.YELLOW + "Health: "
						+ p.getHealth() + "/20");
				sender.sendMessage(ChatColor.YELLOW + "Hunger: "
						+ p.getFoodLevel() + "/20");
				sender.sendMessage(ChatColor.YELLOW + "IP: "
						+ p.getAddress().getHostName());
				sender.sendMessage(ChatColor.YELLOW + "Logins: "
						+ ut.getLogins());
				sender.sendMessage(ChatColor.YELLOW + "AFK: " + afk);
				sender.sendMessage(ChatColor.YELLOW + "Muted: " + mute);
			} else {
				sender.sendMessage(CraftEssence.premessage
						+ "Player not found.");
			}
			return true;
		} else {
			UserTable ut1 = plugin.getDatabase().find(UserTable.class).where()
					.ieq("displyName", sender.getName()).findUnique();
			Player p = plugin.getServer().getPlayer(ut1.getUserName());
			String mute;
			if (ut1.isMuted()) {
				mute = "yes";
			} else {
				mute = "no";
			}
			String afk;
			if (ut1.isAfk()) {
				afk = "yes";
			} else {
				afk = "no";
			}
			sender.sendMessage(ChatColor.YELLOW + "-------- " + ChatColor.RED
					+ "Stats" + ChatColor.YELLOW + " ---------");
			sender.sendMessage(ChatColor.YELLOW + "Account Name: "
					+ ut1.getUserName());
			sender.sendMessage(ChatColor.YELLOW + "Display Name: "
					+ ut1.getDisplyName());
			sender.sendMessage(ChatColor.YELLOW + "Health: " + p.getHealth()
					+ "/20");
			sender.sendMessage(ChatColor.YELLOW + "Hunger: " + p.getFoodLevel()
					+ "/20");
			sender.sendMessage(ChatColor.YELLOW + "IP: "
					+ p.getAddress().getHostName());
			sender.sendMessage(ChatColor.YELLOW + "Logins: " + ut1.getLogins());
			sender.sendMessage(ChatColor.YELLOW + "AFK: " + afk);
			sender.sendMessage(ChatColor.YELLOW + "Muted: " + mute);
			return true;
		}
	}
}
