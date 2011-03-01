package me.furt.CraftEssence.listener;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ceBlockListener extends BlockListener {
	CraftEssence plugin;
	public ceBlockListener(CraftEssence plugin) {
		this.plugin = plugin;
	}

	public void onBlockDamage(BlockDamageEvent event) {
		Player player = event.getPlayer();
		//String world = event.getPlayer().getWorld().getName();
		String group = plugin.Security.getGroup(player.getName());

		if ((group != null) && (!plugin.Security.canGroupBuild(group)))
		event.setCancelled(true);
	}

	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		//String world = event.getPlayer().getWorld().getName();
		String group = plugin.Security.getGroup(player.getName());

		if ((group != null) && (!plugin.Security.canGroupBuild(group)))
		event.setCancelled(true);
	}

	public void onBlockInteract(BlockInteractEvent event) {
		Player player = null;
		if (event.isPlayer()) {
			player = (Player) event.getEntity();
			}

			if (player != null) {
			String group = plugin.Security.getGroup(player.getName());

			if ((group != null) && (!plugin.Security.canGroupBuild(group)))
			event.setCancelled(true);
			}
	}
}
