package me.furt.CraftEssence.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public BanCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "ban", true)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		if (args.length == 0) {
			return false;
		}

		String msg = plugin.message(args).replace(args[0], "");
		if (args.length < 2) {
			msg = " No Grief!";
		}
		Player p = plugin.getServer().getPlayer(args[0]);
		if (p == null || !p.isOnline()) {
			this.addBan(args[0]);
		} else {
			this.addBan(args[0]);
			p.kickPlayer("You have been banned, reason:" + msg);
		}
		plugin.getServer().broadcastMessage(
				CraftEssence.premessage + args[0] + " has been banned.");
		return true;
	}

	public void addBan(String pname) {
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
				}
			}
			out.write(pname + "\n");
			out.close();
			fstream.close();
		} catch (IOException ex) {
			CraftEssence.log.info("[CraftEssence] Player ban did not save");
		}

	}

}
