package me.furt.CraftEssence.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.furt.CraftEssence.CraftEssence;

public class KitCommand implements CommandExecutor {
	CraftEssence plugin;

	public KitCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "kit", false)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		Player player = (Player) sender;
		if (args.length < 1) {
			try {
				List<String> kits = plugin.kitList(player.getName());
				StringBuilder list = new StringBuilder();
				for (String k : kits)
					list.append(", ").append(k);
				player.sendMessage(ChatColor.YELLOW + "Kits:" + ChatColor.WHITE
						+ list.toString());
			} catch (Exception ex) {
				player.sendMessage(CraftEssence.premessage
						+ "There are no valid kits.");
			}
		} else if (args.length == 3) {
			String[] parts = args[2].split(":");
			int id = 0;
			short durability = 0;
			int quanity = 1;
			if (parts.length == 3) {
				id = Integer.parseInt(parts[0]);
				durability = Short.parseShort(parts[1]);
				quanity = Integer.parseInt(parts[2]);
			} else if (parts.length == 2) {
				id = Integer.parseInt(parts[0]);
				quanity = Integer.parseInt(parts[2]);
			} else if (parts.length == 1) {
				id = Integer.parseInt(parts[0]);
			} else {
				player.sendMessage("Invalid kit command format.");
			}

			if (args[0].equalsIgnoreCase("addkit")) {
				if (!plugin.hasPerm(sender, "kit.create", false)) {
					sender.sendMessage(ChatColor.YELLOW
							+ "You to dont have permission to use that command.");
					return true;
				}
				plugin.addKit(player, args[1], id, durability, quanity);
				return true;
			} else if (args[0].equalsIgnoreCase("removekit")) {
				if (!plugin.hasPerm(sender, "kit.delete", false)) {
					sender.sendMessage(ChatColor.YELLOW
							+ "You to dont have permission to use that command.");
					return true;
				}
				plugin.removeKit(player, args[1]);
				return true;
			} else if (args[0].equalsIgnoreCase("additem")) {
				if (!plugin.hasPerm(sender, "kit.create", false)) {
					sender.sendMessage(ChatColor.YELLOW
							+ "You to dont have permission to use that command.");
					return true;
				}
				plugin.addItem(player, args[1], id, durability, quanity);
				return true;
			} else if (args[0].equalsIgnoreCase("removeitem")) {
				if (!plugin.hasPerm(sender, "kit.remove", false)) {
					sender.sendMessage(ChatColor.YELLOW
							+ "You to dont have permission to use that command.");
					return true;
				}
				plugin.removeItem(player, args[1], id, durability);
			} else {
				player.sendMessage("Invalid kit parameter.");
			}

		} else {
			try {
				if (plugin.hasKitRank(player, args[0])) {
					for (String d : plugin.getKit(player, args)) {
						String[] parts = d.split("[^0-9]+", 2);
						int id = Integer.parseInt(parts[0]);
						int amount = parts.length > 1 ? Integer
								.parseInt(parts[1]) : 1;
						player.getInventory()
								.addItem(new ItemStack(id, amount));
					}
					player.sendMessage(CraftEssence.premessage + "Giving "
							+ args[0].toLowerCase() + " kit.");
				} else {
					player.sendMessage(CraftEssence.premessage
							+ "You do not have permission for that kit.");
				}
			} catch (Exception ex) {
				player.sendMessage("That kit does not exist");
				player.sendMessage(ex.getMessage());

			}
		}
		return true;
	}

}
