package com.bukkit.furt.CraftEssence;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.nijikokun.bukkit.Permissions.Permissions;

public class ceBlockListener extends BlockListener {
	public ceBlockListener(CraftEssence instance) {
	}

	public void onBlockDamage(BlockDamageEvent event) {
		Player player = event.getPlayer();
		String group = Permissions.Security.getGroup(player.getName());

		if ((group != null) && (!Permissions.Security.canGroupBuild(group)))
			event.setCancelled(true);
	}

	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String group = Permissions.Security.getGroup(player.getName());

		if ((group != null) && (!Permissions.Security.canGroupBuild(group)))
			event.setCancelled(true);
	}

	public void onBlockInteract(BlockInteractEvent event) {
		Player player = null;

		if (event.isPlayer()) {
			player = (Player) event.getEntity();
		}

		if (player != null) {
			String group = Permissions.Security.getGroup(player.getName());

			if ((group != null) && (!Permissions.Security.canGroupBuild(group)))
				event.setCancelled(true);
		}
	}
}
