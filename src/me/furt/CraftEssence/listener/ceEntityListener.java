package me.furt.CraftEssence.listener;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
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

	public void onEntityDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			if (CraftEssence.godmode.contains(player.getName())) {
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
			if (CraftEssence.godmode.contains(player.getName())) {
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
			if (CraftEssence.godmode.contains(player.getName())) {
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
			if (CraftEssence.godmode.contains(player.getName())) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub

	}

}