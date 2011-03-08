package me.furt.CraftEssence.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.misc.Item;

public class ItemCommand implements CommandExecutor {
	CraftEssence plugin;

	public ItemCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.isPlayer(sender))
			return false;
		if (args.length == 0)
			return false;

		Player player = (Player) sender;
		int itemId = args[0].matches("[0-9]+") ? Integer.parseInt(args[0])
				: Item.getItem(args[0]).id;
		int itemAmount = args.length < 2 ? 1 : Integer.parseInt(args[1]);
		if (itemId < 356 || itemId == 2256 || itemId == 2257) {
			int slot = player.getInventory().firstEmpty();
			if (slot < 0) {
				player.getWorld().dropItem(player.getLocation(),
						new ItemStack(itemId, itemAmount));
			} else {
				player.getInventory()
						.addItem(new ItemStack(itemId, itemAmount));
			}
			player.sendMessage(CraftEssence.premessage + "Giving " + itemAmount
					+ " of item #" + itemId + ".");
			return true;
		} else {
			player.sendMessage(CraftEssence.premessage
					+ "Item id not found.");
			return true;
		}
	}

}
