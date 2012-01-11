package me.furt.CraftEssence.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.furt.CraftEssence.CraftEssence;

public class PardonCommand implements CommandExecutor {
	CraftEssence plugin;

	public PardonCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "pardon", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		if (args.length == 0) {
			return false;
		} else {
			this.removeBan(args[0]);
			plugin.getServer().broadcastMessage(
					CraftEssence.premessage + args[0] + " has been pardoned.");
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
				if (!b.equalsIgnoreCase(pname))
					out.write(b + "\n");
			}
			out.close();
			fstream.close();
		} catch (IOException ex) {
			CraftEssence.log.info("[CraftEssence] " + pname
					+ "  could not be removed from ban list.");
		}
	}

}
