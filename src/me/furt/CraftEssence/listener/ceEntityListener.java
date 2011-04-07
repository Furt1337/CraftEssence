package me.furt.CraftEssence.listener;

import java.io.File;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftChicken;
import org.bukkit.craftbukkit.entity.CraftCow;
import org.bukkit.craftbukkit.entity.CraftCreeper;
import org.bukkit.craftbukkit.entity.CraftGhast;
import org.bukkit.craftbukkit.entity.CraftGiant;
import org.bukkit.craftbukkit.entity.CraftPig;
import org.bukkit.craftbukkit.entity.CraftPigZombie;
import org.bukkit.craftbukkit.entity.CraftSheep;
import org.bukkit.craftbukkit.entity.CraftSkeleton;
import org.bukkit.craftbukkit.entity.CraftSlime;
import org.bukkit.craftbukkit.entity.CraftSpider;
import org.bukkit.craftbukkit.entity.CraftSquid;
import org.bukkit.craftbukkit.entity.CraftWolf;
import org.bukkit.craftbukkit.entity.CraftZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class ceEntityListener extends EntityListener implements Cancellable {

	CraftEssence plugin;

	public ceEntityListener(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		World world = event.getLocation().getWorld();
		if (!new File("plugins" + File.separator + "CraftEssence"
				+ File.separator + "MobBlackList", world.getName() + ".txt").exists()) {
			plugin.createMobBlacklist(world.getName());
		}
		String[] mobList = plugin.getMobs(world.getName());
		for (String creature : mobList) {
			if (creature.equalsIgnoreCase("Chicken")) {
				if (event.getEntity() instanceof CraftChicken)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Cow")) {
				if (event.getEntity() instanceof CraftCow)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Creeper")) {
				if (event.getEntity() instanceof CraftCreeper)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Ghast")) {
				if (event.getEntity() instanceof CraftGhast)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Giant")) {
				if (event.getEntity() instanceof CraftGiant)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Pig")) {
				if (event.getEntity() instanceof CraftPig)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("PigZombie")) {
				if (event.getEntity() instanceof CraftPigZombie)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Sheep")) {
				if (event.getEntity() instanceof CraftSheep)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Skeleton")) {
				if (event.getEntity() instanceof CraftSkeleton)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Slime")) {
				if (event.getEntity() instanceof CraftSlime)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Spider")) {
				if (event.getEntity() instanceof CraftSpider)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Squid")) {
				if (event.getEntity() instanceof CraftSquid)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Wolf")) {
				if (event.getEntity() instanceof CraftWolf)
					event.setCancelled(true);
			} else if (creature.equalsIgnoreCase("Zombie")) {
				if (event.getEntity() instanceof CraftZombie)
					event.setCancelled(true);
			}
		}
	}

	public void onEntityDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			if (CraftEssence.godmode.contains(player.getName().toLowerCase())) {
				int life = player.getHealth();
				if (life != 20)
					player.setHealth(20);
				event.setCancelled(true);
				return;
			}
		}
	}

	public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
		event.setCancelled(true);
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			if (CraftEssence.godmode.contains(player.getName().toLowerCase())) {
				int life = player.getHealth();
				if (life != 20)
					player.setHealth(20);
				event.setCancelled(true);
				return;
			}
		}
	}

	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			if (CraftEssence.godmode.contains(player.getName().toLowerCase())) {
				int life = player.getHealth();
				if (life != 20)
					player.setHealth(20);
				event.setCancelled(true);
				return;
			}
		}
	}

	public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			if (CraftEssence.godmode.contains(player.getName().toLowerCase())) {
				int life = player.getHealth();
				if (life != 20)
					player.setHealth(20);
				event.setCancelled(true);
				return;
			}
		}
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean arg0) {

	}

}
