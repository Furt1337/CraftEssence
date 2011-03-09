package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
	CraftEssence plugin;

	public SetSpawnCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if (!CraftEssence.Permissions.has((Player) sender, "craftessence.setspawn")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;
		
		Player player = (Player) sender;
		// WorldServer ws = ((CraftWorld) player.getWorld()).getHandle();
		// ws.spawnX = player.getLocation().getBlockX();
		// ws.spawnY = player.getLocation().getBlockY();
		// ws.spawnZ = player.getLocation().getBlockZ();

		player.getWorld().getSpawnLocation().setX(player.getLocation().getX());
		player.getWorld().getSpawnLocation().setY(player.getLocation().getY());
		player.getWorld().getSpawnLocation().setZ(player.getLocation().getZ());
		player.getWorld().getSpawnLocation()
				.setYaw(player.getLocation().getYaw());
		player.getWorld().getSpawnLocation()
				.setPitch(player.getLocation().getPitch());

		player.sendMessage(CraftEssence.premessage + "Spawn position modified.");
		return true;
	}
}
