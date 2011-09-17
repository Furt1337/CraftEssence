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
					+ "You to dont have proper permissions for that command.");
			return true;
		}

		Player player = (Player) sender;
		if (args.length < 1) {
			try {
				List<String> kits = plugin.kitList(player);
				StringBuilder list = new StringBuilder();
				for (String k : kits)
					list.append(" ").append(k);
				player.sendMessage(ChatColor.YELLOW + "Kits:" + ChatColor.WHITE
						+ list.toString());
			} catch (Exception ex) {
				player.sendMessage(CraftEssence.premessage
						+ "There are no valid kits.");
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
							+ "You do not have proper rank for that kit.");
				}
			} catch (Exception ex) {
				player.sendMessage("That kit does not exist");
				player.sendMessage(ex.getMessage());

			}
		}
		return true;
	}

}
