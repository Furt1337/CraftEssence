package me.furt.CraftEssence;

import java.util.List;

import org.bukkit.util.config.Configuration;

public class ceConfig {
	public static boolean enableVote;
	public static int voteTimer;
	public static boolean autoKick;
	public static long afkTimer;
	public static long kickTimer;
	public static boolean deathMsg;
	public static String uniqueMsg;

	static boolean Load(Configuration config) {
		config.load();
		List<String> keys = config.getKeys(null);
		if (!keys.contains("ENABLE_VOTE"))
			config.setProperty("ENABLE_VOTE", true);
		if (!keys.contains("VOTE_TIMER"))
			config.setProperty("VOTE_TIMER", 30);
		if (!keys.contains("AUTO_KICK"))
			config.setProperty("AUTO_KICK", true);
		if (!keys.contains("AFK_TIMER"))
			config.setProperty("AFK_TIMER", 300);
		if (!keys.contains("KICK_TIMER"))
			config.setProperty("KICK_TIMER", 300);
		if(!keys.contains("DEATH_MSG"))
			config.setProperty("DEATH_MSG", true);
		if(!keys.contains("UNIQUE_MSG"))
			config.setProperty("UNIQUE_MSG", "A new player has joined the server!");
		if (!config.save()) {
			CraftEssence.log
					.severe("[CraftEssence] Error while writing to config.yml");
			return false;
		}

		enableVote = config.getBoolean("ENABLE_VOTE", true);
		voteTimer = config.getInt("VOTE_TIMER", 30);
		autoKick = config.getBoolean("AUTO_KICK", true);
		afkTimer = config.getInt("AFK_TIMER", 300);
		kickTimer = config.getInt("KICK_TIMER", 300);
		deathMsg = config.getBoolean("DEATH_MSG", true);
		uniqueMsg = config.getString("UNIQUE_MSG", "A new player has joined the server!");
		return true;
	}

}
