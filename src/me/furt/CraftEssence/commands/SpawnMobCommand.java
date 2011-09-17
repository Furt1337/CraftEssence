package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.misc.AimBlock;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
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
					+ "You to dont have proper permissions for that command.");
			return true;
		}

		Player player = (Player) sender;
		int[] ignore = { 8, 9 };
		Location loc = (new AimBlock(player, 300, 0.2, ignore))
				.getTargetBlock().getLocation();
		loc.setY(1.5 + loc.getY());

		if (args.length == 1) {
			CreatureType ct = this.getType(args[0]);
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
			CreatureType ct = this.getType(args[0]);
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

	private CreatureType getType(String s) {
		if (s.equalsIgnoreCase("chicken")) {
			return CreatureType.CHICKEN;
		} else if (s.equalsIgnoreCase("cow")) {
			return CreatureType.COW;
		} else if (s.equalsIgnoreCase("creeper")) {
			return CreatureType.CREEPER;
		} else if (s.equalsIgnoreCase("ghast")) {
			return CreatureType.GHAST;
		} else if (s.equalsIgnoreCase("giant")) {
			return CreatureType.GIANT;
		} else if (s.equalsIgnoreCase("monster")) {
			return CreatureType.MONSTER;
		} else if (s.equalsIgnoreCase("pig")) {
			return CreatureType.PIG;
		} else if (s.equalsIgnoreCase("pigzombie")) {
			return CreatureType.PIG_ZOMBIE;
		} else if (s.equalsIgnoreCase("sheep")) {
			return CreatureType.SHEEP;
		} else if (s.equalsIgnoreCase("skeleton")) {
			return CreatureType.SKELETON;
		} else if (s.equalsIgnoreCase("slime")) {
			return CreatureType.SLIME;
		} else if (s.equalsIgnoreCase("spider")) {
			return CreatureType.SPIDER;
		} else if (s.equalsIgnoreCase("squid")) {
			return CreatureType.SQUID;
		} else if (s.equalsIgnoreCase("wolf")) {
			return CreatureType.WOLF;
		} else if (s.equalsIgnoreCase("zombie")) {
			return CreatureType.ZOMBIE;
		} else {
			return null;
		}

	}
}
