package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.misc.Item;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class giveCommand implements CommandExecutor {
	CraftEssence plugin;

	public giveCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length < 2)
			return false;
		if (plugin.playerMatch(args[0]) != null) {
			int itemId = args[1].matches("[0-9]+") ? Integer.parseInt(args[1])
					: Item.getItem(args[1]).id;
			int itemAmount = args.length < 3 ? 1 : Integer.parseInt(args[2]);
			if (itemId < 356 || itemId == 2256 || itemId == 2257) {
				Player giveTo = plugin.getServer().getPlayer(args[0]);
				int slot = giveTo.getInventory().firstEmpty();
				if (slot < 0) {
					giveTo.getWorld().dropItem(giveTo.getLocation(),
							new ItemStack(itemId, itemAmount));
				} else {
					giveTo.getInventory().addItem(
							new ItemStack(itemId, itemAmount));
				}
				if (plugin.isPlayer(sender)) {
					Player player = (Player) sender;
					player.sendMessage(CraftEssence.premessage + "Giving "
							+ itemAmount + " of item #" + itemId + " to "
							+ giveTo.getDisplayName() + ".");
					giveTo.sendMessage(ChatColor.GRAY + player.getName()
							+ " sent you a gift!");
				} else {
					CraftEssence.log.info("[CraftEssence] Giving " + itemAmount
							+ " of item #" + itemId + " to "
							+ giveTo.getDisplayName() + ".");
					giveTo.sendMessage(ChatColor.GRAY + "You got a gift!");
				}
				return true;
			} else {
				if (plugin.isPlayer(sender)) {
					Player player = (Player) sender;
					player.sendMessage(CraftEssence.premessage
							+ "Item id not found.");
				}
				CraftEssence.log.info("[CraftEssence] Item id not found.");
				return true;
			}
		} else {
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.sendMessage(CraftEssence.premessage
						+ "Player is offline or not found");
			}
			CraftEssence.log.info("[CraftEssence] Item id not found.");
			return true;
		}
	}

}
