package me.furt.CraftEssence.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import org.bukkit.plugin.PluginDescriptionFile;

import me.furt.CraftEssence.CraftEssence;

public class Updatr {
	private CraftEssence plugin;
	private double internalVersion;
	private double lastestVersion;
	private final String versionFile = "http://dl.dropbox.com/u/17392489/CraftEssence/Release/VERSION";
	private final String pluginFile = "http://dl.dropbox.com/u/17392489/CraftEssence/Release/CraftEssence.jar";

	public Updatr(CraftEssence plugin) {
		this.plugin = plugin;
	}

	public void Load() {
		String updateFolder = plugin.getServer().getUpdateFolder();
		File folder = new File(updateFolder);
		if (!folder.exists())
			folder.mkdir();

		if (!isUpToDate()) {
			try {
				this.getUpdate(updateFolder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getUpdate(String updateFolder) throws Exception {
		URL url = new URL(pluginFile);
		File file = new File(updateFolder + File.separator + "CraftEssence.jar");

		if (file.exists()) {
			file.delete();
		}

		InputStream inputStream = url.openStream();
		OutputStream outputStream = new FileOutputStream(file);

		saveTo(inputStream, outputStream);

		inputStream.close();
		outputStream.close();
		//if (ceConfig.autoUpdate) {
			//plugin.getServer().reload();
		//}

	}

	public boolean isUpToDate() {
		PluginDescriptionFile pdfFile = plugin.getDescription();
		internalVersion = Double.parseDouble(pdfFile.getVersion());
		try {
			URL url = new URL(versionFile);
			InputStream inputStream = url.openStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			lastestVersion = Double.parseDouble(bufferedReader.readLine());
		} catch (Exception e) {
			CraftEssence.log
					.severe("[CraftEssence] Could not read version file.");
			return true;
		}
		if (lastestVersion > internalVersion) {
			return false;
		}
		return true;
	}

	private void saveTo(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;

		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
	}

}
