package me.furt.CraftEssence;

import java.util.List;

import me.furt.CraftEssence.misc.AimBlock;
import me.furt.CraftEssence.misc.Item;
import me.furt.CraftEssence.misc.Teleport;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ceCommands {
	private CraftEssence plugin;

	public ceCommands(CraftEssence instance) {
		this.plugin = instance;
		new Teleport(this.plugin);
	}

	public boolean kickAll(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String pname = player.getName();

		String msg = plugin.message(args);
		if (args.length < 2) {
			msg = " Dont be a noob!";
		}
		int online = 0;
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if (p == null || p.isOnline()) {
				continue;
			}
			online++;
		}
		if (online < 2) {
			player.sendMessage(CraftEssence.premessage
					+ "Your the only one on.");
			return false;
		}
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if (p.getName() != pname) {
				p.kickPlayer("You have been kicked by " + player.getName()
						+ ". Reason:" + msg);
				plugin.getServer().broadcastMessage(
						"§6" + p.getName() + " was kicked by "
								+ player.getName() + "!");
			}
		}
		return true;
	}

	public boolean kill(CommandSender sender, String[] args) {
		// TODO kill finished needs console support
		Player player = (Player) sender;
		String killer = player.getName();
		if (args.length < 1) {
			return false;
		} else {
			if (args.length < 2) {
				if (!args[0].equalsIgnoreCase("*")) {
					if (playerMatch(args[0]) != null) {
						Player p = plugin.getServer().getPlayer(args[0]);
						p.setHealth(0);
						if (killer != p.getName()) {
							plugin.getServer().broadcastMessage(
									ChatColor.RED + p.getName()
											+ " was killed by " + killer);
							CraftEssence.log.info(p.getName()
									+ " was killed by " + killer);
						} else {
							plugin.getServer()
									.broadcastMessage(
											ChatColor.RED
													+ p.getName()
													+ " has commited suicide, dumbass!");
						}
					} else {
						player.sendMessage(CraftEssence.premessage
								+ "Player does not exist");
					}
				} else {
					// TODO kill all
				}
			}
		}
		return true;
	}

	public boolean unban(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			plugin.removeBan(player, args[0]);
			plugin.getServer().broadcastMessage(
					"§6" + player.getName() + " has pardoned " + args[0] + ".");
			return true;
		}
	}

	public boolean kick(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			if (playerMatch(args[0]) == null) {
				player.sendMessage(CraftEssence.premessage
						+ "Could not find player");
			} else {
				String msg = plugin.message(args).replace(args[0], "");
				if (args.length < 2) {
					msg = " Dont be a noob!";
				}
				Player p = plugin.getServer().getPlayer(args[0]);
				p.kickPlayer("You have been kicked by " + player.getName()
						+ ", reason:" + msg);
				plugin.getServer().broadcastMessage(
						"§6" + p.getName() + " was kicked by "
								+ player.getName() + "!");
			}
		}
		return true;

	}

	public boolean listWorld(CommandSender sender) {
		Player player = (Player) sender;
		player.sendMessage(ChatColor.YELLOW + "Worlds running on this Server");
		for (int i = 0; i < plugin.getServer().getWorlds().size(); i++) {
			ChatColor color;
			if (((World) plugin.getServer().getWorlds().get(i))
					.getEnvironment() == World.Environment.NETHER)
				color = ChatColor.RED;
			else {
				color = ChatColor.GREEN;
			}
			player.sendMessage(color
					+ ((World) plugin.getServer().getWorlds().get(i)).getName());
		}
		return true;
	}
	
	public boolean msg(CommandSender sender, String[] args) {
		// TODO msg finished
		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			String msg = plugin.message(args).replace(args[0], "");
			Player sendto = playerMatch(args[0]);
			if (sendto != null) {
				if (sendto.getName().equals(player.getName())) {
					player.sendMessage(CraftEssence.premessage
							+ "You can't message yourself!");
				} else {
					sendto.sendMessage(ChatColor.GRAY + "[MSG]<"
							+ player.getName() + "> " + msg);
				}
			} else {
				player.sendMessage(CraftEssence.premessage
						+ "Couldn't find player " + args[0]);
			}
		}
		return true;
	}

	public boolean jump(CommandSender sender) {
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
			/*
			 * Teleporter tp = new Teleporter(location); tp.setVerbose(false);
			 * tp.addTeleportee(player); tp.teleport();
			 */
		}
		return true;

	}

	public boolean setwarp(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		plugin.setWarp(player, player.getLocation(), args);
		return true;
	}

	public boolean warp(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			player.teleportTo(plugin.getWarp(player, args));
			player.sendMessage(CraftEssence.premessage + "Warping...");
		}
		return true;
	}

	public boolean me(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			String msg = plugin.message(args);
			if (ceSettings.prayer) {
				if ((args[0].equalsIgnoreCase("prays"))
						&& (args[2].equalsIgnoreCase("day"))) {
					if (CraftEssence.prayList.contains(player.getName())) {
						int health = player.getHealth();
						health = health - 1;
						player.setHealth(health);
						player.sendMessage(ChatColor.RED
								+ "The gods strike you down");
					} else {
						CraftEssence.prayList.add(player.getName());
						int prayCount = 0;
						for (@SuppressWarnings("unused")
						String p : CraftEssence.prayList) {
							prayCount++;
						}
						if (prayCount == ceSettings.prayAmount) {
							World world = player.getWorld();
							long time = world.getTime();
							time = time - time % 24000;
							world.setTime(time + 24000);
							plugin.getServer().broadcastMessage(
									ChatColor.GRAY + "* " + player.getName()
											+ " " + msg + "*");
							plugin.getServer()
									.broadcastMessage(
											ChatColor.RED
													+ "The gods have answered your prayers.");
							CraftEssence.prayList.clear();
						} else {
							plugin.getServer().broadcastMessage(
									ChatColor.GRAY + "* " + player.getName()
											+ " " + msg + "*");
							player.sendMessage(ChatColor.RED
									+ "The gods are pleased with you.");
						}
					}
				} else {
					plugin.getServer()
							.broadcastMessage(
									ChatColor.GRAY + "*" + player.getName()
											+ msg + "*");
				}
			} else {
				plugin.getServer().broadcastMessage(
						ChatColor.GRAY + "*" + player.getName() + msg + "*");
			}
		}
		return true;
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public boolean tp(CommandSender sender, String[] args) {
		// TODO tp finished
		if (args.length < 1)
			return false;
		Player player = (Player) sender;
		if (this.playerMatch(args[0]) == null) {
			player.sendMessage(CraftEssence.premessage + "Player not found");
			return true;
		} else {
			Player p = this.plugin.getServer().getPlayer(args[0]);
			player.teleportTo(p);
			sender.sendMessage(CraftEssence.premessage + "Teleporting to "
					+ p.getDisplayName() + ".");
			return true;
		}
	}

	public boolean tphere(CommandSender sender, String[] args) {
		// TODO tphere finished
		if (args.length < 1)
			return false;
		Player player = (Player) sender;
		if (this.playerMatch(args[0]) == null) {
			player.sendMessage("Player not found");
			return false;
		} else {
			Player p = this.plugin.getServer().getPlayer(args[0]);
			p.teleportTo(player);
			sender.sendMessage(CraftEssence.premessage + "Teleporting "
					+ player.getDisplayName() + " to " + p.getName() + ".");
			return true;
		}
	}

	public boolean item(CommandSender sender, String[] args) {
		// TODO add wool color and tree types

		Player player = (Player) sender;
		if (args.length < 1) {
			player.sendMessage(CraftEssence.premessage
					+ "Usage: /item [item] <amount> <type>");
		} else {
			int itemId = args[0].matches("[0-9]+") ? Integer.parseInt(args[0])
					: Item.getItem(args[0]).id;
			int itemAmount = args.length < 2 ? 1 : Integer.parseInt(args[1]);
			if (itemId > 400) {
				player.sendMessage(CraftEssence.premessage + "Invalid item id.");
			} else {
				int slot = player.getInventory().firstEmpty();
				if (slot < 0) {
					player.getWorld().dropItem(player.getLocation(),
							new ItemStack(itemId, itemAmount));
				} else {
					player.getInventory().addItem(
							new ItemStack(itemId, itemAmount));
				}
				player.sendMessage(CraftEssence.premessage + "Giving "
						+ itemAmount + " of item #" + itemId + ".");
				return true;
			}
		}
		return false;
	}

	public boolean playerlist(CommandSender sender, String[] args) {
		// TODO command finished needs some touchups for console
		Player player = null;
		String pname;
		player = (Player) sender;
		pname = player.getName();
		StringBuilder online = new StringBuilder();
		int intonline = 0;
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if ((p == null) || (!p.isOnline())) {
				continue;
			}
			++intonline;
		}

		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if ((p == null) || (!p.isOnline())) {
				continue;
			}
			online.append(online.length() == 0 ? ChatColor.YELLOW
					+ "Connected players (" + intonline + "/"
					+ plugin.getServer().getMaxPlayers() + "): "
					+ ChatColor.WHITE : ", ");
			online.append(p.getDisplayName());
		}
		if (pname.equalsIgnoreCase("Console")) {
			CraftEssence.log.info(online.toString());
			return true;
		} else {
			player.sendMessage(online.toString());
			return true;
		}
	}

	public boolean home(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		player.teleportTo(plugin.getHome(player));
		player.sendMessage(CraftEssence.premessage + "Teleporting home...");
		return true;
	}

	public boolean top(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		int topX = player.getLocation().getBlockX();
		int topZ = player.getLocation().getBlockZ();
		int topY = player.getWorld().getHighestBlockYAt(topX, topZ);
		player.teleportTo(new Location(player.getWorld(), player.getLocation()
				.getX(), topY + 1, player.getLocation().getZ()));
		player.sendMessage(CraftEssence.premessage + "Teleported up.");
		return true;
	}

	public boolean support(CommandSender sender, String[] args) {
		// TODO support updated
		Player player = (Player) sender;
		if (args.length < 1) {
			isAdmin(player);
			player.sendMessage(CraftEssence.premessage
					+ "To request help from the staff");
			player.sendMessage(ChatColor.YELLOW
					+ "type /support, followed by your question.");
		} else {
			String msg = plugin.message(args);
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if (!isAdmin(p))
					continue;
				p.sendMessage(ChatColor.RED + "[Support]" + ChatColor.GRAY
						+ "<" + player.getDisplayName() + "> "
						+ ChatColor.WHITE + msg);
			}
		}
		return true;
	}

	private boolean isAdmin(Player player) {
		if (!player.isOp())
			return false;
		else
			return true;
	}

	public boolean sethome(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		plugin.setHome(player, player.getLocation());
		return true;
	}

	public boolean mail(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			return false;
		} else {
			if (args.length == 1 && "read".equalsIgnoreCase(args[0])) {
				List<String> mail = plugin.readMail(player);
				if (mail.isEmpty())
					player.sendMessage(CraftEssence.premessage
							+ "You do not have any mail!");
				else
					for (String s : mail)
						player.sendMessage(s);
			}
			String msg = plugin.message(args);
			if (args.length >= 3 && "send".equalsIgnoreCase(args[0])) {
				plugin.sendMail(player, args[1], msg.split(" +", 3)[2]);
			}
			if (args.length >= 1 && "delete".equalsIgnoreCase(args[0])) {
				plugin.clearMail(player);
			}
		}
		return true;
	}

	public boolean spawn(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		player.teleportTo(player.getWorld().getSpawnLocation());
		player.sendMessage(CraftEssence.premessage + "Returned to spawn.");
		return true;
	}

	public boolean setspawn(CommandSender sender, String[] args) {
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

	public boolean kit(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length < 1) {
			try {
				List<String> kits = plugin.kitList(player);
				StringBuilder list = new StringBuilder();
				for (String k : kits)
					list.append(" ").append(k);
				player.sendMessage("Kits:" + list.toString());
			} catch (Exception ex) {
				player.sendMessage(CraftEssence.premessage
						+ "There are no valid kits.");
			}
		} else {
			try {
				if (plugin.kitRank(player, args) == true) {
					for (String d : plugin.getKit(player,
							plugin.kitID(player, args))) {
						String[] parts = d.split("[^0-9]+", 2);
						int id = Integer.parseInt(parts[0]);
						int amount = parts.length > 1 ? Integer
								.parseInt(parts[1]) : 1;
						player.getWorld().dropItem(player.getLocation(),
								new ItemStack(id, amount));
					}
					player.sendMessage(CraftEssence.premessage + "Giving kit "
							+ args[0].toLowerCase() + ".");
				}
			} catch (Exception ex) {
				player.sendMessage("Either the kit does not exist");
				player.sendMessage("or you do not have proper permmissions");
				player.sendMessage(ex.getMessage());

			}
		}
		return true;
	}

	public boolean motd(CommandSender sender, String[] args) {
		Player player = (Player) sender;
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
		return true;
	}
}
