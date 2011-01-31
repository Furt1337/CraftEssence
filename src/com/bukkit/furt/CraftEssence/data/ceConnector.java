package com.bukkit.furt.CraftEssence.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bukkit.furt.CraftEssence.ceSettings;

public class ceConnector {
	public static ResultSet result;

	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:jdc:jdcpool");
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException e) {
			Logger.getLogger("Minecraft").log(Level.SEVERE,
					"[CraftEssence] Error getting connection", e);
			e.printStackTrace();
			return null;
		}
	}

	public static Connection createConnection() {
		try {
			new JDCConnectionDriver("com.mysql.jdbc.Driver",
					ceSettings.mysqlDB, ceSettings.mysqlUser,
					ceSettings.mysqlPass);
			Connection ret = DriverManager.getConnection("jdbc:jdc:jdcpool");
			ret.setAutoCommit(false);
			return ret;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
