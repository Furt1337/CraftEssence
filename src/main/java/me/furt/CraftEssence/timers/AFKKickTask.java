package me.furt.CraftEssence.timers;

import java.util.TimerTask;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.UserTable;

import org.bukkit.entity.Player;

public class AFKKickTask extends TimerTask {
	private final CraftEssence plugin;

	public AFKKickTask(CraftEssence instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		Player[] players = plugin.getServer().getOnlinePlayers();
		Player player;
		long configTime = plugin.getConfig().getInt("KICK_TIMER") * 60 * 1000;
		for (int i = 0; (players.length - 1) >= i; i++) {
			long lastMoved = 0;
			player = players[i];
			UserTable ut = plugin.getDatabase().find(UserTable.class).where()
					.ieq("userName", player.getName()).eq("afk", true)
					.findUnique();
			if (ut == null) {
				return;
			}
			lastMoved = System.currentTimeMillis() - ut.getAfkTime();
			if (player.isOp()) {
				if (plugin.getConfig().getBoolean("KICK_OP")) {
					if (lastMoved > configTime) {
						player.kickPlayer("Auto-Kick: You were afk too long...");
					}
				}
			} else {
				if (lastMoved > configTime) {
					player.kickPlayer("Auto-Kick: You were afk too long...");
				}
			}
		}
	}
}