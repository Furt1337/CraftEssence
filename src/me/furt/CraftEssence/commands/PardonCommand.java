package me.furt.CraftEssence.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.furt.CraftEssence.CraftEssence;

public class PardonCommand implements CommandExecutor {
	CraftEssence plugin;

	public PardonCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length < 1) {
			return false;
		} else {
			this.removeBan(args[0]);
			plugin.getServer().broadcastMessage(
					"�6" + args[0] + " has been pardoned.");
			CraftEssence.log.info(args[0] + " has been pardoned.");
			return true;
		}
	}
	
	public void removeBan(String pname) {
		try {
			String[] banList = plugin.getBans();
			ArrayList<String> arraylist = new ArrayList<String>();
			for (String p : banList) {
				if (p != pname) {
					arraylist.add(p);
				}
			}
			new File(plugin.getDataFolder() + File.separator + "bans.txt")
					.createNewFile();
			FileWriter fstream = new FileWriter(new File(plugin.getDataFolder()
					+ File.separator + "bans.txt"));
			BufferedWriter out = new BufferedWriter(fstream);
			for (String b : arraylist) {
				if (!b.equalsIgnoreCase(pname)) {
					out.write(b + "\n");
					CraftEssence.log.info("Banlist builder.");
					CraftEssence.log.info(b);
				}
			}
			out.close();
			fstream.close();
		} catch (IOException ex) {
			CraftEssence.log.info("[CraftEssence] " + pname
					+ "  could not be removed from ban list.");
		}
	}

}