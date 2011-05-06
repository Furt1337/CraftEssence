package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.sql.WarpTable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
			Player player = (Player) sender;
			if (!CraftEssence.Permissions.has(player, "craftessence.setspawn")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		}
		if (!plugin.isPlayer(sender))
			return false;

		Player player = (Player) sender;
		World world = player.getWorld();
		int x = (int) player.getLocation().getX();
		int y = (int) player.getLocation().getY();
		int z = (int) player.getLocation().getZ();
		world.setSpawnLocation(x, y, z);
		this.setSpawn(player.getLocation());
		player.sendMessage(CraftEssence.premessage + "Spawn position modified.");
		return true;
	}

	public void setSpawn(Location spawn) {
		// double x = spawn.getX();
		// double y = spawn.getY();
		// double z = spawn.getZ();
		// float yaw = spawn.getYaw();
		// float pitch = spawn.getPitch();
		String wname = spawn.getWorld().getName();

		WarpTable wt = plugin.getDatabase().find(WarpTable.class).where()
				.ieq("name", "spwn").ieq("world", wname).findUnique();
		if (wt == null) {
			wt = new WarpTable();
			wt.setName("spwn");
		}
		wt.setLocation(spawn);
		plugin.getDatabase().save(wt);
	}
}
