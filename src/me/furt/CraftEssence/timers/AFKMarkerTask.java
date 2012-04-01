package me.furt.CraftEssence.timers;

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

	@Override
	public void run() {
		Player[] players = plugin.getServer().getOnlinePlayers();
		Player player;
		long MarkerTime = System.currentTimeMillis() - (plugin.getConfig().getInt("AFK_TIMER") * 60 * 1000);
		for (int i = 0; (players.length - 1) >= i; i++) {
			long afkTime = 0;
			player = players[i];
			UserTable ut = plugin.getDatabase().find(UserTable.class).where()
					.ieq("userName", player.getName()).findUnique();
			afkTime = plugin.users.get(player.getName());
			if (afkTime < MarkerTime) {
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
