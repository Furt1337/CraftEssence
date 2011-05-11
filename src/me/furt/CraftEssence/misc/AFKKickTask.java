package me.furt.CraftEssence.misc;

import java.util.TimerTask;

import org.bukkit.entity.Player;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

public class AFKKickTask extends TimerTask {
	private final CraftEssence plugin;

	public AFKKickTask(CraftEssence instance) {
		plugin = instance;
	}

	public void run() {
		Player[] players = plugin.getServer().getOnlinePlayers();
		Player player;
		String playerName;
		long kickTime = System.currentTimeMillis() - 80000;
		for (int i = 0; (players.length - 1) >= i; i++) {
			long lastMoved = 0;
			player = players[i];
			playerName = player.getName();
			UserTable ut = plugin.getDatabase().find(UserTable.class).where()
					.ieq("userName", playerName).eq("afk", true).findUnique();
			if (ut != null) {
				lastMoved = ut.getAfkTime();
				if (lastMoved < kickTime)
					player.kickPlayer("AFK-Kick! You were away for too long...");
			}
		}
	}
}