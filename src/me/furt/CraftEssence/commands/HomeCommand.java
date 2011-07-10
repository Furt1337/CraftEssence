package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.HomeTable;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {
	CraftEssence plugin;

	public HomeCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!plugin.hasPerm(sender, "home")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		} else {
			CraftEssence.log.info("[CraftEssence] Cannot be used in console.");
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			HomeTable home = plugin.getDatabase().find(HomeTable.class).where()
					.ieq("playerName", player.getName())
					.ieq("worldName", player.getWorld().getName()).findUnique();
			if (home == null) {
				sender.sendMessage(CraftEssence.premessage
						+ "Home location not set.");
				return true;
			}
			player.teleport(home.getLocation());
			player.sendMessage(CraftEssence.premessage + "Teleporting home...");
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("accept")) {
				if (!player.hasPermission("craftessence.home.accept")) {
					sender.sendMessage(ChatColor.YELLOW
							+ "You to dont have proper permissions for that command.");
					return true;
				}
				String[] homeArray = CraftEssence.homeInvite
						.toArray(new String[] {});
				for (String list : homeArray) {
					String[] homeSplit = list.split(":");
					if (homeSplit[1].equalsIgnoreCase(player.getName()
							.toLowerCase())) {
						Player p = plugin.getServer().getPlayer(homeSplit[0]);
						HomeTable home = plugin.getDatabase()
								.find(HomeTable.class).where()
								.ieq("playerName", p.getName())
								.ieq("worldName", p.getWorld().getName())
								.findUnique();
						player.teleport(home.getLocation());
						sender.sendMessage(CraftEssence.premessage
								+ "Teleporting to " + p.getDisplayName()
								+ "'s home...");
						CraftEssence.homeInvite.remove(list);
						return true;
					}
				}
				sender.sendMessage(CraftEssence.premessage
						+ "Must be invited to a home to use this command.");
				return true;
			} else {
				if (plugin.getServer().getPlayer(args[0]) != null) {
					if (!player.hasPermission("craftessence.home.admin")) {
						sender.sendMessage(ChatColor.YELLOW
								+ "You to dont have proper permissions for that command.");
						return true;
					}
					Player p = plugin.getServer().getPlayer(args[0]);
					HomeTable home = plugin.getDatabase().find(HomeTable.class)
							.where().ieq("playerName", p.getName())
							.ieq("worldName", p.getWorld().getName())
							.findUnique();
					player.teleport(home.getLocation());
					sender.sendMessage(CraftEssence.premessage
							+ "Teleporting to " + p.getDisplayName()
							+ "'s home...");
					return true;
				} else {
					sender.sendMessage(CraftEssence.premessage
							+ "Player name could not be found.");
					return true;
				}
			}
		}

		if (args.length == 2) {
			if ((args[0].equalsIgnoreCase("invite"))
					&& (plugin.getServer().getPlayer(args[1]) != null)) {
				if (!player.hasPermission("craftessence.home.invite")) {
					sender.sendMessage(ChatColor.YELLOW
							+ "You to dont have proper permissions for that command.");
					return true;
				}
				Player p = plugin.getServer().getPlayer(args[1]);
				CraftEssence.homeInvite.add(player.getName().toLowerCase()
						+ ":" + p.getName().toLowerCase());
				p.sendMessage(CraftEssence.premessage
						+ "You have been invited to " + player.getDisplayName()
						+ "'s home type /home accept to teleport there.");
				sender.sendMessage(CraftEssence.premessage
						+ "Home invite sent to " + p.getDisplayName());
				return true;
			} else {
				sender.sendMessage(CraftEssence.premessage
						+ "Player name could not be found.");
				return true;
			}
		}
		return false;
	}
}
