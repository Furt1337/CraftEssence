package com.bukkit.furt.CraftEssence;

import java.io.File;

public class ceSettings {

	public static String mysqlUser = "user";
	public static String mysqlPass = "pass";
	public static String mysqlDB = "jdbc:mysql://localhost:3306/craftessence";
	
	public static void initialize(File dataFolder) {
		loadPropertiesFiles(dataFolder);
	}

	private static void loadPropertiesFiles(File dataFolder) {
		// Use Configuration once it's finished.
		ceProperties cep = new ceProperties(new File(dataFolder,
				"craftessence.properties"));
		mysqlUser = cep.getString("mysqlUser", "user",
				"Username for MySQL db (if applicable)");
		mysqlPass = cep.getString("mysqlPass", "pass",
				"Password for MySQL db (if applicable)");
		mysqlDB = cep.getString("mysqlDB",
				"jdbc:mysql://localhost:3306/craftessence",
				"DB for MySQL (if applicable)");
		cep.save();
	}

}
