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

	public boolean setwarp(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		plugin.setWarp(player, player.getLocation(), args);
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

	public boolean tp(CommandSender sender, String[] args) {
		if (args.length < 1)
			return false;
		Player player = (Player) sender;
		if (plugin.playerMatch(args[0]) == null) {
			player.sendMessage(CraftEssence.premessage + "Player not found");
			return true;
		} else {
			Player p = this.plugin.getServer().getPlayer(args[0]);
			player.teleportTo(p);
			sender.sendMessage(CraftEssence.premessage + "Teleporting to "
					+ p.getDisplayName() + ".");
			return true;
		}
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

	public boolean top(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		int topX = player.getLocation().getBlockX();
		int topZ = player.getLocation().getBlockZ();
		int topY = player.getWorld().getHighestBlockYAt(topX, topZ);
		player.teleportTo(new Location(player.getWorld(), player.getLocation()
				.getX(), topY + 1, player.getLocation().getZ()));
		player.sendMessage(CraftEssence.premessage + "Teleported up.");
		return true;
	}

	public boolean support(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			isAdmin(player);
			player.sendMessage(CraftEssence.premessage
					+ "To request help from the staff");
			player.sendMessage(ChatColor.YELLOW
					+ "type /support, followed by your question.");
		} else {
			String msg = plugin.message(args);
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if (!isAdmin(p))
					continue;
				p.sendMessage(ChatColor.RED + "[Support]" + ChatColor.GRAY
						+ "<" + player.getDisplayName() + "> "
						+ ChatColor.WHITE + msg);
			}
		}
		return true;
	}

	public boolean spawn(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		player.teleportTo(player.getWorld().getSpawnLocation());
		player.sendMessage(CraftEssence.premessage + "Returned to spawn.");
		return true;
	}

	public boolean setspawn(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		// WorldServer ws = ((CraftWorld) player.getWorld()).getHandle();
		// ws.spawnX = player.getLocation().getBlockX();
		// ws.spawnY = player.getLocation().getBlockY();
		// ws.spawnZ = player.getLocation().getBlockZ();

		player.getWorld().getSpawnLocation().setX(player.getLocation().getX());
		player.getWorld().getSpawnLocation().setY(player.getLocation().getY());
		player.getWorld().getSpawnLocation().setZ(player.getLocation().getZ());
		player.getWorld().getSpawnLocation()
				.setYaw(player.getLocation().getYaw());
		player.getWorld().getSpawnLocation()
				.setPitch(player.getLocation().getPitch());

		player.sendMessage(CraftEssence.premessage + "Spawn position modified.");
		return true;
	}
}
