package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class clearInventoryCommand implements CommandExecutor {
	private final CraftEssence plugin;

	public clearInventoryCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0)
			return false;
		if (plugin.playerMatch(args[0]) != null) {
			Player p = plugin.getServer().getPlayer(args[0]);
			p.getInventory().clear();
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.sendMessage(CraftEssence.premessage + p.getName()
						+ "'s inventory is cleared.");
				p.sendMessage(ChatColor.GRAY + player.getName()
						+ " cleared your inventory");
			} else {
				CraftEssence.log.info("[CraftEssence] " + p.getName()
						+ "'s inventory is cleared.");
				p.sendMessage(ChatColor.GRAY
						+ "Your inventory has been cleared.");
			}

		} else {
			if (plugin.isPlayer(sender)) {
				Player player = (Player) sender;
				player.sendMessage(CraftEssence.premessage
						+ "Player is offline or not found");
			} else {

			}
		}
		return true;
	}

}
