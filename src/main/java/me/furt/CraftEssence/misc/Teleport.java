package me.furt.CraftEssence.misc;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class Teleport {
	public Teleport(CraftEssence plugin) {
		// TODO Auto-generated constructor stub
	}

	public Location getDestination(Location location) {
		World world = location.getWorld();
		double x = location.getX() + 0.5D;
		double y = location.getY();
		double z = location.getZ() + 0.5D;

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
		return new Location(world, x, y, z, location.getYaw(),
				location.getPitch());
	}

	private boolean blockIsAboveAir(World world, double x, double y, double z) {
		return world.getBlockAt((int) Math.floor(x),
				(int) Math.floor(y - 1.0D), (int) Math.floor(z)).getType() == Material.AIR;
	}

	public boolean blockIsNotSafe(World world, double x, double y, double z) {
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
}
