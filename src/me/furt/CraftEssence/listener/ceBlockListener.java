package me.furt.CraftEssence.listener;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.event.block.BlockListener;

public class ceBlockListener extends BlockListener {
	CraftEssence plugin;

	public ceBlockListener(CraftEssence plugin) {
		this.plugin = plugin;
	}
}
