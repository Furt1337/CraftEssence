package me.furt.CraftEssence.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import me.furt.CraftEssence.CraftEssence;
//import me.furt.CraftEssence.ceSettings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

public class cePlayerListener extends PlayerListener {
	private final CraftEssence plugin;
	private HashMap<Player, Long> playerCooldown = new HashMap<Player, Long>();
	private HashMap<Player, Long> playerAlertCooldown = new HashMap<Player, Long>();

	public cePlayerListener(CraftEssence instance) {
		this.plugin = instance;
	}

	public void onPlayerLogin(PlayerLoginEvent event) {
		String player = event.getPlayer().getName();
		String[] banList = plugin.getBans();
		for (String p : banList) {
			if (p.equalsIgnoreCase(player)) {
				event.getResult();
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED,
						"You are banned from this server!");
			}
		}
	}

	public void onPlayerQuit(PlayerEvent event) {
		if (CraftEssence.godmode.contains(event.getPlayer().getName()))
			CraftEssence.godmode.remove(event.getPlayer().getName());
	}

	public void sendAlert(Player player, String msg) {
		Long time = Long.valueOf(new Date().getTime());
		if (this.playerAlertCooldown.containsKey(player)) {
			this.playerAlertCooldown.remove(player);
		}
		player.sendMessage(msg);
		this.playerAlertCooldown.put(player, time);
	}

	public boolean getAlertable(Player player) {
		Long time = Long.valueOf(new Date().getTime());
		if (this.playerAlertCooldown.containsKey(player)) {
			Long old = Long.valueOf(((Long) this.playerAlertCooldown
					.get(player)).longValue());

			return time.longValue() - old.longValue() > 10000L;
		}

		return true;
	}

	/*public boolean getTeleportable(Player player) {
		Long time = Long.valueOf(new Date().getTime());
		if (this.playerCooldown.containsKey(player)) {
			Long old = Long.valueOf(((Long) this.playerCooldown.get(player))
					.longValue());

			return time.longValue() - old.longValue() > ceSettings.cooldown;
		}

		return true;
	}*/

	public void setCooldown(Player player) {
		Long time = Long.valueOf(new Date().getTime());
		if (this.playerCooldown.containsKey(player)) {
			this.playerCooldown.remove(player);
		}
		this.playerCooldown.put(player, time);
	}

	public void onPlayerChat(PlayerChatEvent event) {
		// TODO onPlayerChat
		Player player = event.getPlayer();
		String world = event.getPlayer().getWorld().getName();
		String group = CraftEssence.Permissions.getGroup(world,
				player.getName());

		String prefix = CraftEssence.Permissions.getGroupPrefix(world, group);
		String suffix = CraftEssence.Permissions.getGroupSuffix(world, group);

		if ((prefix == null) || (prefix.trim().length() != 1)) {
			return;
		}

		String format = event.getFormat();
		event.setFormat("[" + ChatColor.getByCode(Integer.parseInt(prefix, 16))
				+ suffix + ChatColor.WHITE + "]" + format);
		// event.setFormat(event.getFormat().replace("%1$s",
		// ChatColor.getByCode(Integer.parseInt(prefix, 16)) + "%1$s" +
		// ChatColor.WHITE));
		if (CraftEssence.muteList.contains(player.getName())) {
			plugin.getServer().broadcastMessage(
					ChatColor.YELLOW + player.getName()
							+ " tried to chat but they are muted.");
			event.setCancelled(true);
		}
	}

	public void onPlayerJoin(PlayerEvent event) {
		Player player = event.getPlayer();
		String[] motd = plugin.getMotd();
		if (motd == null || motd.length < 1) {
			player.sendMessage(ChatColor.GRAY + "No Motd set.");
		} else {
			int intonline = 0;
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if ((p == null) || (!p.isOnline())) {
					continue;
				}
				++intonline;
			}
			String online = intonline + "/"
					+ plugin.getServer().getMaxPlayers();

			String location = (int) player.getLocation().getX() + "x, "
					+ (int) player.getLocation().getY() + "y, "
					+ (int) player.getLocation().getZ() + "z";
			String ip = player.getAddress().getAddress().getHostAddress();

			for (String line : motd) {
				player.sendMessage(plugin.argument(line, new String[] {
						"+dname,+d", "+name,+n", "+location,+l", "+ip",
						"+online" }, new String[] { player.getDisplayName(),
						player.getName(), location, ip, online }));
			}
		}

		List<String> mail = plugin.readMail(player);
		if (mail.isEmpty())
			player.sendMessage(ChatColor.GRAY + "You have no new mail.");
		else
			player.sendMessage(ChatColor.YELLOW + "You have " + mail.size()
					+ " messages! Type /mail read to view your mail.");
	}

	public Player playerMatch(String name) {
		if (plugin.getServer().getOnlinePlayers().length < 1) {
			return null;
		}

		Player[] online = plugin.getServer().getOnlinePlayers();
		Player lastPlayer = null;

		for (Player player : online) {
			String playerName = player.getName();
			String playerDisplayName = player.getDisplayName();

			if (playerName.equalsIgnoreCase(name)) {
				lastPlayer = player;
				break;
			} else if (playerDisplayName.equalsIgnoreCase(name)) {
				lastPlayer = player;
				break;
			}

			if (playerName.toLowerCase().indexOf(name.toLowerCase()) != -1) {
				if (lastPlayer != null) {
					return null;
				}

				lastPlayer = player;
			} else if (playerDisplayName.toLowerCase().indexOf(
					name.toLowerCase()) != -1) {
				if (lastPlayer != null) {
					return null;
				}

				lastPlayer = player;
			}
		}

		return lastPlayer;
	}
}
