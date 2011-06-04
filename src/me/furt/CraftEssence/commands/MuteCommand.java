package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public MuteCommand(CraftEssence instance) {
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
		
		if (args.length == 0)
			return false;
		
		if (plugin.playerMatch(args[0]) != null) {
			Player player = plugin.getServer().getPlayer(args[0]);
			String pName = player.getName();
			UserTable ut = plugin.getDatabase().find(UserTable.class).where()
					.ieq("userName", pName).findUnique();

			if (ut.isMuted()) {
				ut.setMuted(false);
				plugin.getServer().broadcastMessage(
						ChatColor.YELLOW + player.getDisplayName() + " has been unmuted.");
			} else {
				ut.setMuted(true);
				plugin.getServer().broadcastMessage(
						ChatColor.YELLOW + player.getDisplayName() + " has been muted.");
			}
			plugin.getDatabase().save(ut);

		} else {
			sender.sendMessage("Player could not be found.");
		}
		return true;
	}

}
