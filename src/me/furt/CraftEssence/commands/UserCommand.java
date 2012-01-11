package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UserCommand implements CommandExecutor {

	private final CraftEssence plugin;

	public UserCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "user", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}
		
		if ((args.length == 3) && (args[1].equalsIgnoreCase("rename"))) {
			UserTable ut = plugin.getDatabase().find(UserTable.class).where()
					.ieq("userName", args[0]).findUnique();
			if (ut != null) {
				UserTable ut1 = plugin.getDatabase().find(UserTable.class)
						.where().ieq("displyName", args[2]).findUnique();
				//UserTable ut2 = plugin.getDatabase().find(UserTable.class).where().ieq("userName", args[2]).findUnique();
				if (ut1 == null) {
					String oldName = ut.getDisplyName();
					ut.setDisplyName(args[2]);
					plugin.getDatabase().save(ut);
					plugin.getServer().broadcastMessage(CraftEssence.premessage + oldName + " is now known as " + args[2]);
				} else {
					sender.sendMessage(CraftEssence.premessage + "That name is already in use.");
				}
				return true;
			} else {
				sender.sendMessage(CraftEssence.premessage + "Player not found.");
				return true;
			}
		}
		return false;
	}

}
