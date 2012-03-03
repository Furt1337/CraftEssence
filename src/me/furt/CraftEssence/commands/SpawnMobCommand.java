package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.misc.AimBlock;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnMobCommand implements CommandExecutor {
	CraftEssence plugin;

	public SpawnMobCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.hasPerm(sender, "spawnmob", false)) {
			sender.sendMessage(ChatColor.YELLOW
					+ "You do not have permission to use /" + label);
			return true;
		}

		Player player = (Player) sender;
		int[] ignore = { 8, 9 };
		Location loc = (new AimBlock(player, 300, 0.2, ignore))
				.getTargetBlock().getLocation();
		loc.setY(1.5 + loc.getY());

		if (args.length == 1) {
			EntityType ct = this.getType(args[0], sender);
			if (ct != null) {
				player.getWorld().spawnCreature(loc, ct);
				player.sendMessage(CraftEssence.premessage
						+ "You have spawned a " + ct.getName());
			} else {
				sender.sendMessage(CraftEssence.premessage
						+ "creature not found.");
			}
			return true;
		}

		if (args.length == 2) {
			EntityType ct = this.getType(args[0], sender);
			int ammount = Integer.parseInt(args[1]);
			if (ct != null) {
				for (int i = 0; i < ammount; i++) {
					player.getWorld().spawnCreature(loc, ct);
				}
				player.sendMessage(CraftEssence.premessage
						+ "You have spawned " + ammount + " " + ct.getName()
						+ "'s");
			} else {
				sender.sendMessage(CraftEssence.premessage
						+ "creature not found.");
			}
			return true;
		}
		return false;
	}

	private EntityType getType(String s, CommandSender sender) {
		if (s.equalsIgnoreCase("cavespider")) {
			return EntityType.CAVE_SPIDER;
		} else if (s.equalsIgnoreCase("chicken")) {
			return EntityType.CHICKEN;
		} else if (s.equalsIgnoreCase("cow")) {
			return EntityType.COW;
		} else if (s.equalsIgnoreCase("creeper")) {
			return EntityType.CREEPER;
		} else if (s.equalsIgnoreCase("enderdragon")) {
			if (!plugin.hasPerm(sender, "spawnmob.enderdragon", false)) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You do not have permission to spawn enderdragon.");
				return null;
			} else {
				return EntityType.ENDER_DRAGON;
			}
		} else if (s.equalsIgnoreCase("enderman")) {
			return EntityType.ENDERMAN;
		} else if (s.equalsIgnoreCase("ghast")) {
			return EntityType.GHAST;
		} else if (s.equalsIgnoreCase("giant")) {
			return EntityType.GIANT;
		} else if (s.equalsIgnoreCase("magmacube")) {
			return EntityType.MAGMA_CUBE;
		} else if (s.equalsIgnoreCase("mushroomcow")) {
			return EntityType.MUSHROOM_COW;
		} else if (s.equalsIgnoreCase("pig")) {
			return EntityType.PIG;
		} else if (s.equalsIgnoreCase("pigzombie")) {
			return EntityType.PIG_ZOMBIE;
		} else if (s.equalsIgnoreCase("sheep")) {
			return EntityType.SHEEP;
		} else if (s.equalsIgnoreCase("silverfish")) {
			return EntityType.SILVERFISH;
		} else if (s.equalsIgnoreCase("skeleton")) {
			return EntityType.SKELETON;
		} else if (s.equalsIgnoreCase("slime")) {
			return EntityType.SLIME;
		} else if (s.equalsIgnoreCase("snowman")) {
			return EntityType.SNOWMAN;
		} else if (s.equalsIgnoreCase("spider")) {
			return EntityType.SPIDER;
		} else if (s.equalsIgnoreCase("squid")) {
			return EntityType.SQUID;
		} else if (s.equalsIgnoreCase("villager")) {
			return EntityType.VILLAGER;
		} else if (s.equalsIgnoreCase("wolf")) {
			return EntityType.WOLF;
		} else if (s.equalsIgnoreCase("zombie")) {
			return EntityType.ZOMBIE;
		} else {
			return null;
		}

	}
}
