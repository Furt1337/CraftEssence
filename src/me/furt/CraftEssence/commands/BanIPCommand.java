package me.furt.CraftEssence.commands;

import java.net.InetSocketAddress;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanIPCommand implements CommandExecutor {

	private CraftEssence plugin;

	public BanIPCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!plugin.hasPerm(sender, "ban.ip", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		Player p = plugin.getServer().getPlayer(args[0]);
		if (p != null) {
			UserTable ut = plugin.getDatabase().find(UserTable.class).where().ieq("userName", p.getName()).findUnique();
			InetSocketAddress targetIP = p.getAddress();
			sender.sendMessage("Saved IP: " + ut.getIp() + "Current IP: " + targetIP.getHostName());
			return true;
		} else {
			sender.sendMessage("Cant find player");
			return true;
		}
	}

}
