package me.furt.CraftEssence.commands;

import info.somethingodd.bukkit.OddItem.OddItem;
import me.furt.CraftEssence.CraftEssence;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand implements CommandExecutor {
	CraftEssence plugin;

	public GiveCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender,
					"craftessence.give")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}

		if (args.length < 2)
			return false;

		if (plugin.playerMatch(args[0]) != null) {
			int itemAmount = args.length < 3 ? 1 : Integer.parseInt(args[2]);
			Player giveTo = plugin.getServer().getPlayer(args[0]);
			ItemStack stack = null;
			try {
				stack = OddItem.getItemStack(args[1]);
			} catch (IllegalArgumentException iae) {
				sender.sendMessage("Item " + args[1] + " unknown. Closest match: "
						+ iae.getMessage());
				CraftEssence.log.info("[CraftEssence] Item " + args[1]
						+ " unknown. Closest match: " + iae.getMessage());
			}

			stack.setAmount(itemAmount);
			giveTo.getInventory().addItem(new ItemStack[] { stack });
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.sendMessage(CraftEssence.premessage + "Giving "
						+ itemAmount + " of " + stack.getType().toString() + " to "
						+ giveTo.getDisplayName() + ".");
				giveTo.sendMessage(ChatColor.GRAY + player.getName()
						+ " sent you a gift!");
			} else {
				CraftEssence.log.info("[CraftEssence] Giving " + itemAmount
						+ " of of " + stack.getType().toString() + " to "
						+ giveTo.getDisplayName() + ".");
				giveTo.sendMessage(ChatColor.GRAY + "You got a gift!");
			}
			return true;
		} else {
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.sendMessage(CraftEssence.premessage
						+ "Player is offline or not found.");
			}
			CraftEssence.log
					.info("[CraftEssence] Player is offline or not found.");
			return true;
		}
	}

}
