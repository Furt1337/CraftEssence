package me.furt.CraftEssence.commands;

import info.somethingodd.bukkit.OddItem.OddItem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.furt.CraftEssence.CraftEssence;

public class ItemCommand implements CommandExecutor {
	CraftEssence plugin;

	public ItemCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			Player player = (Player) sender;
			if (!CraftEssence.Permissions.has(player,
					"craftessence.item")) {
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
		int itemAmount = args.length < 2 ? 1 : Integer.parseInt(args[1]);
		ItemStack stack = null;
		try {
			stack = OddItem.getItemStack(args[0]);
		} catch (IllegalArgumentException iae) {
			sender.sendMessage("Item " + args[0] + " unknown. Closest match: "
					+ iae.getMessage());
			CraftEssence.log.info("[CraftEssence] Item " + args[0]
					+ " unknown. Closest match: " + iae.getMessage());
		}

		stack.setAmount(itemAmount);
		player.getInventory().addItem(new ItemStack[] { stack });
		player.sendMessage(CraftEssence.premessage + "Giving " + itemAmount
				+ " of " + stack.getType().toString() + ".");
		return true;
	}
}
