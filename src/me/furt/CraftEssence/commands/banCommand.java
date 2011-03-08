package me.furt.CraftEssence.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public BanCommand(CraftEssence instance) {
		this.plugin = instance;
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

	public void removeBan(Player player, String pname) {
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

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
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
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				plugin.getServer().broadcastMessage(
						"§6" + player.getName() + " has banned " + args[0]
								+ ".");
			} else {
				plugin.getServer().broadcastMessage(
						"§6" + args[0] + " has been banned.");
			}
			CraftEssence.log.info("[CraftEssence] " + args[0]
					+ " has been banned.");
			return true;
		} else {
			this.addBan(args[0]);
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				p.kickPlayer("You have been banned by " + player.getName()
						+ ", reason:" + msg);
				plugin.getServer().broadcastMessage(
						"§6" + player.getName() + " has banned " + args[0]
								+ ".");
			} else {
				p.kickPlayer("You have been banned by Console, reason:" + msg);
				plugin.getServer().broadcastMessage(
						"§6" + args[0] + " has been banned.");
			}
			CraftEssence.log.info("[CraftEssence] " + args[0]
			        + " has been banned.");
			return true;
		}
	}

}
