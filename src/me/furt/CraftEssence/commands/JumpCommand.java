package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.misc.AimBlock;
import me.furt.CraftEssence.misc.Teleport;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JumpCommand implements CommandExecutor {
	CraftEssence plugin;

	public JumpCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!plugin.isPlayer(sender))
			return false;

		Player player = (Player) sender;
		AimBlock aiming = new AimBlock(player);
		Block block = aiming.getTargetBlock();
		if (block == null) {
			player.sendMessage(ChatColor.RED + "Not pointing to valid block");
		} else {
			int x = block.getX();
			int y = block.getY() + 1;
			int z = block.getZ();
			World world = block.getWorld();
			Location location = new Location(world, x, y, z, player
					.getLocation().getYaw(), player.getLocation().getPitch());
			Location tp = new Teleport(plugin).getDestination(location);
			player.teleportTo(tp);
		}
		return true;
	}
}
