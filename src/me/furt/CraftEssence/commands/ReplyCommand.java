package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public ReplyCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Player player = (Player) sender;
		String msg = plugin.message(args);
		String[] replyArray = CraftEssence.reply.toArray(new String[] {});
		for (String list : replyArray) {
			String[] split = list.split(":");
			if (split[1].equalsIgnoreCase(player.getName().toLowerCase())) {
				Player sendTo = plugin.playerMatch(split[0]);
				CraftEssence.reply.add(player.getName().toLowerCase() + ":"
						+ sendTo.getName().toLowerCase());
				sendTo.sendMessage(ChatColor.YELLOW + "[From -> "
						+ player.getDisplayName() + "] " + ChatColor.WHITE + msg);
				player.sendMessage(ChatColor.YELLOW + "[To -> "
						+ sendTo.getDisplayName() + "] " + ChatColor.WHITE + msg);
				if (CraftEssence.afk.contains(sendTo.getName().toLowerCase()))
					player.sendMessage(ChatColor.YELLOW + sendTo.getName()
							+ " is currently afk.");
				//CraftEssence.reply.remove(sendTo.getName().toLowerCase() + ":" + player.getName().toLowerCase());
			}

		}

		return true;
	}

}
