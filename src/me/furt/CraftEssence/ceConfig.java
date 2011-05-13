package me.furt.CraftEssence;

import java.util.List;

import org.bukkit.util.config.Configuration;

public class ceConfig {
	public static boolean prayer;
	public static int prayAmount;
	public static long afkTimer;

	static boolean Load(Configuration config) {
		config.load();
		List<String> keys = config.getKeys(null);
		if (!keys.contains("prayer"))
			config.setProperty("prayer", true);
		if (!keys.contains("prayerAmount"))
			config.setProperty("prayerAmount", 3);
		if(!keys.contains("afkTimer"))
			config.setProperty("afkTimer", 5);
		if (!config.save()) {
			CraftEssence.log
					.severe("[CraftEssence] Error while writing to config.yml");
			return false;
		}

		prayer = config.getBoolean("prayer", true);
		prayAmount = config.getInt("prayAmount", 3);
		afkTimer = config.getInt("afkTimer", 30000);
		return true;
	}

}
