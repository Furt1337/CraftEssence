package me.furt.CraftEssence.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.furt.CraftEssence.CraftEssence;

public class NewItemCommand implements CommandExecutor {
	CraftEssence plugin;

	public NewItemCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			Player player = (Player) sender;
			if (!CraftEssence.Permissions.has(player, "craftessence.item")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;

		if (args.length == 0)
			return false;

		Player player = (Player) sender;
		String item = args[0];
		int itemAmount = args.length < 2 ? 1 : Integer.parseInt(args[1]);
		ItemStack stack = null;
		String[] itemsplit = item.split(";");
		int itemid;
		try {
			if (item.contains("A-Za-Z")) {
				itemid = Material.valueOf(item).getId();
				stack = new ItemStack(itemid);
				stack.setAmount(itemAmount);
			} else {
				if (itemsplit.length == 2) {
					stack = new ItemStack(Integer.parseInt(itemsplit[0]));
					stack.setDurability(Short.parseShort(itemsplit[1]));
					stack.setAmount(itemAmount);
				}
			}
			
		} catch (Exception e) {
			sender.sendMessage("Item not found.");
			return true;
		}
		player.getInventory().addItem(stack);
		return true;
	}
}
