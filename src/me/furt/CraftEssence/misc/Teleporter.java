package me.furt.CraftEssence.misc;

import java.util.ArrayList;

import org.bukkit.*;
import org.bukkit.entity.Player;

public class Teleporter {
	private Location destination;
	private ArrayList<Player> players;
	private boolean verbose;

	public Teleporter(Location location) {
		this.destination = location;
		this.players = new ArrayList<Player>();
		verbose = true;
	}

	public void teleport() {
		World world = destination.getWorld();
		double x = destination.getX() + 0.5D;
		double y = destination.getY();
		double z = destination.getZ() + 0.5D;

		while (blockIsAboveAir(world, x, y, z)) {
			y -= 1.0D;
		}
		while (blockIsNotSafe(world, x, y, z)) {
			y += 1.0D;
			if (y == 110.0D) {
				y = 1.0D;
				x += 1.0D;
			}
		}

		if (verbose) {
			if (destination.getY() != y) {
				for (Player player : players) {
					player.sendMessage("Supplied y location not safe.");
					player.sendMessage("Teleporting you to (" + (int) x + ", "
							+ (int) y + ", " + (int) z + ")");
				}
			} else {
				for (Player player : players)
					player.sendMessage("Teleporting you to (" + (int) x + ", "
							+ (int) y + ", " + (int) z + ")");
			}
		}
		for (Player player : players) {
			// TeleHistory.pushLocation(player, player.getLocation());
			player.teleportTo(new Location(world, x, y, z,
					destination.getYaw(), destination.getPitch()));
		}
	}

	private boolean blockIsNotSafe(World world, double x, double y, double z) {
		// TODO Auto-generated method stub
		if (world.getBlockAt((int) Math.floor(x), (int) Math.floor(y - 1.0D),
				(int) Math.floor(z)).getType() == Material.LAVA)
			return true;
		if (world.getBlockAt((int) Math.floor(x), (int) Math.floor(y - 1.0D),
				(int) Math.floor(z)).getType() == Material.FIRE)
			return true;
		if ((world.getBlockAt((int) Math.floor(x), (int) Math.floor(y),
				(int) Math.floor(z)).getType() != Material.AIR)
				|| (world.getBlockAt((int) Math.floor(x),
						(int) Math.floor(y + 1.0D), (int) Math.floor(z))
						.getType() != Material.AIR)) {
			return true;
		}
		return blockIsAboveAir(world, x, y, z);
	}

	private boolean blockIsAboveAir(World world, double x, double y, double z) {
		return (world.getBlockAt((int) Math.floor(x), (int) Math.floor(y - 1),
				(int) Math.floor(z)).getType() == Material.AIR);
	}

	public void addTeleportee(Player player) {
		players.add(player);

	}

	public boolean blockIsSafe(World world, double x, double y, double z) {
		return world.getBlockAt((int) Math.floor(x), (int) Math.floor(y),
				(int) Math.floor(z)).getType() == Material.AIR
				&& world.getBlockAt((int) Math.floor(x),
						(int) Math.floor(y + 1), (int) Math.floor(z)).getType() == Material.AIR;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public void addAll(Player[] playerList) {
		for (Player player : playerList) {
			addTeleportee(player);
		}
	}
}
