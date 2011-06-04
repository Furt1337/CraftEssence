package me.furt.CraftEssence.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

public class MsgCommand implements CommandExecutor {
	CraftEssence plugin;

	public MsgCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!plugin.hasPerm(sender, command)) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		} else {
			CraftEssence.log.info("[CraftEssence] Cannot be used in console.");
			return false;
		}
		
		if (args.length == 0) {
			return false;
		}

		Player player = (Player) sender;
		String msg = plugin.message(args).replace(args[0], "").trim();
		Player sendTo = plugin.playerMatch(args[0]);
		
		if (sendTo != null) {
			if (sendTo.getName().equals(player.getName())) {
				player.sendMessage(CraftEssence.premessage
						+ "You can't message yourself!");
			} else {
				String[] replyArray = CraftEssence.reply.toArray(new String[] {});
				for (String list : replyArray) {
					String[] split = list.split(":");
					if (split[1].equalsIgnoreCase(sendTo.getName().toLowerCase()))
						CraftEssence.reply.remove(split[0].toLowerCase() + ":"
								+ sendTo.getName().toLowerCase());

				}
				CraftEssence.reply.add(player.getName().toLowerCase() + ":"
						+ sendTo.getName().toLowerCase());
				sendTo.sendMessage(ChatColor.YELLOW + "[From -> "
						+ player.getDisplayName() + "] " + ChatColor.WHITE
						+ msg);
				player.sendMessage(ChatColor.YELLOW + "[To -> "
						+ sendTo.getDisplayName() + "] " + ChatColor.WHITE
						+ msg);
				UserTable ut = plugin.getDatabase().find(UserTable.class).where()
				.ieq("userName", sendTo.getName()).findUnique();
				if (ut.isAfk())
					player.sendMessage(ChatColor.YELLOW + sendTo.getName()
							+ " is currently afk.");
			}
		} else {
			player.sendMessage(CraftEssence.premessage + args[0]
					+ " is offline or not spelled correctly");
		}
		return true;
	}

}
