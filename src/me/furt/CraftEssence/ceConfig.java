package me.furt.CraftEssence;

import java.util.List;

import org.bukkit.util.config.Configuration;

public class ceConfig {
	public static String mysqlUser;
	public static String mysqlPass;
	public static String mysqlDB;
	public static boolean prayer;
	public static int prayAmount;
	public static List<String> worldSpawn;

	static boolean Load(Configuration config) {
		config.load();
		List<String> keys = config.getKeys(null);
		if (!keys.contains("mysqlDB"))
			config.setProperty("mysqlDB", "jdbc:mysql://localhost:3306/CraftBukkit");
		if (!keys.contains("mysqlUser"))
			config.setProperty("mysqlUser", "root");
		if (!keys.contains("mysqlPass"))
			config.setProperty("mysqlPass", "pass");
		if (!keys.contains("prayer"))
			config.setProperty("prayer", true);
		if (!keys.contains("prayerAmount"))
			config.setProperty("prayerAmount", 3);
		if (!keys.contains("worldSpawn"))
			config.setProperty("worldSpawn", "");
		if (!config.save()) {
			CraftEssence.log
					.severe("[CraftEssence] Error while writing to config.yml");
			return false;
		}

		mysqlDB = config.getString("mysqlDB");
		mysqlUser = config.getString("mysqlUser");
		mysqlPass = config.getString("mysqlPass");
		prayer = config.getBoolean("prayer", true);
		prayAmount = config.getInt("prayAmount", 3);
		worldSpawn = config.getStringList("worldSpawn", null);
		return true;
	}

}
