package me.furt.CraftEssence;

import java.util.List;

import me.furt.CraftEssence.misc.AimBlock;
import me.furt.CraftEssence.misc.Item;
import me.furt.CraftEssence.misc.Teleport;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ceCommands {

	public boolean unban(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			this.removeBan(player, args[0]);
			plugin.getServer().broadcastMessage(
					"§6" + player.getName() + " has pardoned " + args[0] + ".");
			return true;
		}
	}

	public boolean worldList(CommandSender sender) {
		Player player = (Player) sender;
		player.sendMessage(ChatColor.YELLOW + "Worlds running on this Server");
		for (int i = 0; i < plugin.getServer().getWorlds().size(); i++) {
			ChatColor color;
			if (((World) plugin.getServer().getWorlds().get(i))
					.getEnvironment() == World.Environment.NETHER)
				color = ChatColor.RED;
			else {
				color = ChatColor.GREEN;
			}
			player.sendMessage(color
					+ ((World) plugin.getServer().getWorlds().get(i)).getName());
		}
		return true;
	}

	public boolean warp(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			player.teleportTo(plugin.getWarp(player, args));
			player.sendMessage(CraftEssence.premessage + "Warping...");
		}
		return true;
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public boolean tphere(CommandSender sender, String[] args) {
		if (args.length < 1)
			return false;
		Player player = (Player) sender;
		if (plugin.playerMatch(args[0]) == null) {
			player.sendMessage("Player not found");
			return false;
		} else {
			Player p = this.plugin.getServer().getPlayer(args[0]);
			p.teleportTo(player);
			sender.sendMessage(CraftEssence.premessage + "Teleporting "
					+ player.getDisplayName() + " to " + p.getName() + ".");
			return true;
		}
	}
}
