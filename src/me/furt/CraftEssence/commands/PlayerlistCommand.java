package me.furt.CraftEssence.commands;

import java.util.List;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerlistCommand implements CommandExecutor {
	CraftEssence plugin;

	public PlayerlistCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!plugin.hasPerm(sender, "list")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}

		int intonline = 0;
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if ((p == null) || (!p.isOnline())) {
				continue;
			}
			++intonline;
		}
		sender.sendMessage(ChatColor.YELLOW + "Connected players (" + intonline
				+ "/" + plugin.getServer().getMaxPlayers() + "):");

		for (int i = 0; i < plugin.getServer().getWorlds().size(); i++) {
			World world = plugin.getServer().getWorlds().get(i);
			List<Player> wplayer = world.getPlayers();
			StringBuilder online = new StringBuilder();
			online.append(online.length() == 0 ? ChatColor.GOLD
					+ world.getName() + ChatColor.WHITE + ": " : ", ");
			for (Player p : wplayer) {
				String color = plugin.getPrefix(p);
				if (color == null) {
					color = ChatColor.WHITE.toString();
				} else {
					color = color.replaceAll("(&([a-f0-9]))", "�$2");
				}

				UserTable ac = plugin.getDatabase().find(UserTable.class)
						.where().ieq("userName", p.getName()).findUnique();

				String afk = "";
				if ((ac != null) && (ac.isAfk())) {
					afk = "(AFK)";
				}

				online.append(ChatColor.GRAY + afk + color + p.getDisplayName()
						+ ChatColor.WHITE + ", ");
			}
			sender.sendMessage(online.toString());

		}
		return true;
	}

}
