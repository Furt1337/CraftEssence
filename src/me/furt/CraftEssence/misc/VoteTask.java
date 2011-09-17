package me.furt.CraftEssence.misc;

import java.util.Collection;
import java.util.Iterator;
import java.util.TimerTask;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import me.furt.CraftEssence.CraftEssence;

public class VoteTask extends TimerTask {
	CraftEssence plugin;

	public VoteTask(CraftEssence instance) {
		plugin = instance;
	}

	public void run() {
		if (plugin.vote == null) {
			return;
		}
		Server srv = plugin.getServer();
		int yes = 0;
		int no = 0;
		Collection<?> c = plugin.vuser.values();
		Iterator<?> itr = c.iterator();
		while (itr.hasNext()) {
			String vte = itr.next().toString();
			if (vte.equalsIgnoreCase("yes")) {
				yes = yes + 1;
			} else {
				no = no + 1;
			}
		}
		int po = srv.getOnlinePlayers().length;
		int undecided = po - (yes + no);
		if (undecided > yes + no) {
			plugin.getServer().broadcastMessage(
					CraftEssence.premessage
							+ "Vote ended, not enough players voted.");
			return;
		}
		int yesperc = yes / po * 100;
		if (yesperc > 51) {
			String[] args = plugin.vote.split(":");

			if (args[0].equalsIgnoreCase("day")) {
				long time = srv.getWorld(args[1]).getTime();
				time -= time % 24000L;
				srv.getWorld(args[1]).setTime(time + 24000L);
				plugin.getServer()
						.broadcastMessage(
								CraftEssence.premessage
										+ "Vote ended for setting time to day, You won the vote.");
			}
			if (args[0].equalsIgnoreCase("night")) {
				long time = srv.getWorld(args[1]).getTime();
				time -= time % 37700L;
				srv.getWorld(args[1]).setTime(time + 37700L);
				plugin.getServer()
						.broadcastMessage(
								CraftEssence.premessage
										+ "Vote ended for setting time to night, You won the vote.");
			}
			if (args[0].equalsIgnoreCase("kick")) {
				Player p = plugin.getServer().getPlayer(args[1]);
				if (p == null) {
					plugin.getServer().broadcastMessage(
							CraftEssence.premessage + "Vote ended for kicking "
									+ args[1] + ", user is no longer online.");
				} else {
					p.kickPlayer("You have been voted to be kicked from the server");
					plugin.getServer().broadcastMessage(
							CraftEssence.premessage + "Vote ended for kicking "
									+ args[1] + ", You won the vote.");
				}
			}
			plugin.vote = null;
			plugin.vuser.clear();
		} else {
			String[] args = plugin.vote.split(":");
			plugin.getServer().broadcastMessage(
					CraftEssence.premessage + "Vote ended for " + args[0]
							+ ", You lost the vote.");
			plugin.vote = null;
			plugin.vuser.clear();
		}
	}

}
