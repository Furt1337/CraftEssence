package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.ceConfig;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {
	CraftEssence plugin;

	public MeCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender,
					"craftessence.me")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;

		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			String msg = plugin.message(args);
			if (ceConfig.prayer) {
				if ((args[0].equalsIgnoreCase("prays"))
						&& (args[2].equalsIgnoreCase("day"))) {
					if (CraftEssence.prayList.contains(player.getName())) {
						int health = player.getHealth();
						health = health - 1;
						player.setHealth(health);
						player.sendMessage(ChatColor.RED
								+ "The gods strike you down");
					} else {
						CraftEssence.prayList.add(player.getName());
						int prayCount = 0;
						for (@SuppressWarnings("unused")
						String p : CraftEssence.prayList) {
							prayCount++;
						}
						if (prayCount == ceConfig.prayAmount) {
							World world = player.getWorld();
							long time = world.getTime();
							time = time - time % 24000L;
							world.setTime(time + 24000L);
							plugin.getServer().broadcastMessage(
									ChatColor.GRAY + "* " + player.getName()
											+ " " + msg + "*");
							plugin.getServer()
									.broadcastMessage(
											ChatColor.RED
													+ "The gods have answered your prayers.");
							CraftEssence.prayList.clear();
						} else {
							plugin.getServer().broadcastMessage(
									ChatColor.GRAY + "* " + player.getName()
											+ " " + msg + "*");
							player.sendMessage(ChatColor.RED
									+ "The gods are pleased with you.");
						}
					}
				} else {
					plugin.getServer().broadcastMessage(
							ChatColor.GRAY + "* " + player.getName() + " "
									+ msg + "*");
				}
			} else {
				plugin.getServer().broadcastMessage(
						ChatColor.GRAY + "* " + player.getName() + " " + msg
								+ "*");
			}
		}
		return true;
	}

}
