package me.furt.CraftEssence.misc;

import java.util.TimerTask;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AFKMarkerTask extends TimerTask {
	private final CraftEssence plugin;

	public AFKMarkerTask(CraftEssence instance) {
		plugin = instance;
	}

	public void run() {
		Player[] players = plugin.getServer().getOnlinePlayers();
		Player player;
		String playerName;
		long kickTime = System.currentTimeMillis() - 120000;
		for (int i = 0; (players.length - 1) >= i; i++) {
			long afkTime = 0;
			player = players[i];
			playerName = player.getName();
			UserTable ut = plugin.getDatabase().find(UserTable.class).where()
					.ieq("userName", playerName).findUnique();
			afkTime = (long)plugin.users.get(playerName);
			if (afkTime < kickTime) {
				if (!ut.isAfk()) {
					ut.setAfk(true);
					ut.setAfkTime(System.currentTimeMillis());
					plugin.getDatabase().save(ut);
					plugin.getServer().broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + " has been flaged afk");
				}
			}
		}
	}
}
