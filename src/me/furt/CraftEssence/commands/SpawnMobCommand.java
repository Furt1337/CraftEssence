package me.furt.CraftEssence.commands;

import me.furt.CraftEssence.CraftEssence;
import me.furt.CraftEssence.misc.AimBlock;
import me.furt.CraftEssence.misc.Mob;
import net.minecraft.server.EntitySlime;
import net.minecraft.server.World;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Player;

public class SpawnMobCommand implements CommandExecutor {
	CraftEssence plugin;

	public SpawnMobCommand(CraftEssence instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (plugin.isPlayer(sender)) {
			if ((!plugin.hasPerm(sender, "spawnmob")) && (!sender.isOp())) {
				sender.sendMessage(ChatColor.YELLOW
						+ "You to dont have proper permissions for that command.");
				return true;
			}
		} else {
			CraftEssence.log.info("[CraftEssence] Cannot be used in console.");
			return false;
		}
		
		int[] ignore = { 8, 9 };
		Player player = (Player) sender;
		if (0 < args.length && args.length < 3) {
			String[] split1 = args[0].split(":");
			String[] split0 = new String[1];
			CraftEntity spawned1 = null;
			Mob mob2 = null;
			if (split1.length == 1 && !split1[0].equalsIgnoreCase("Slime")) {
				split0 = args[0].split(";");
				split1[0] = split0[0];
			}
			if (split1.length == 2) {
				args[0] = split1[0] + "";
			}
			Mob mob = Mob
					.fromName(split1[0].equalsIgnoreCase("PigZombie") ? "PigZombie"
							: capitalCase(split1[0]));
			if (mob == null) {
				player.sendMessage("Invalid mob type.");
				return true;
			}
			World world = ((org.bukkit.craftbukkit.CraftWorld) player
					.getWorld()).getHandle();
			CraftEntity spawned = null;
			try {
				spawned = mob.spawn(player, plugin);
			} catch (Mob.MobException e) {
				player.sendMessage("Unable to spawn mob.");
				return true;
			}
			Location loc = (new AimBlock(player, 300, 0.2, ignore))
					.getTargetBlock().getLocation();
			loc.setY(1.5 + loc.getY());
			spawned.teleportTo(loc);
			world.a(spawned.getHandle());
			if (split0.length == 2) {
				mob2 = Mob
						.fromName(split0[1].equalsIgnoreCase("PigZombie") ? "PigZombie"
								: capitalCase(split0[1]));
				if (mob2 == null) {
					player.sendMessage("Invalid mob type.");
					return true;
				}
				try {
					spawned1 = mob2.spawn(player, plugin);
				} catch (Mob.MobException e) {
					player.sendMessage("Unable to spawn mob.");
					return true;
				}
				spawned1.teleportTo(spawned);
				spawned1.getHandle().setPassengerOf(spawned.getHandle());
				world.a(spawned1.getHandle());
				return true;
			}
			if (split1.length == 2 && mob.name == "Slime") {
				try {
					((EntitySlime) spawned.getHandle()).b(Integer
							.parseInt(split1[1]));
					return true;
				} catch (Exception e) {
					player.sendMessage("Malformed size.");
					return true;
				}
			}
			if (args.length == 2) {
				try {
					for (int i = 1; i < Integer.parseInt(args[1]); i++) {
						spawned = mob.spawn(player, plugin);
						spawned.teleportTo(loc);
						if (split1.length > 1 && mob.name == "Slime") {
							try {
								((EntitySlime) spawned.getHandle()).b(Integer
										.parseInt(split1[1]));
							} catch (Exception e) {
								player.sendMessage("Malformed size.");
								return true;
							}
						}
						world.a(spawned.getHandle());
						if (split0.length == 2) {
							if (mob2 == null) {
								player.sendMessage("Invalid mob type.");
								return true;
							}
							try {
								spawned1 = mob2.spawn(player, plugin);
							} catch (Mob.MobException e) {
								player.sendMessage("Unable to spawn mob.");
								return true;
							}
							spawned1.teleportTo(spawned);
							spawned1.getHandle().setPassengerOf(
									spawned.getHandle());
							world.a(spawned1.getHandle());
						}
					}
					player.sendMessage(args[1]
							+ " "
							+ mob.name.toLowerCase()
							+ mob.s
							+ (split0.length == 2 ? " riding "
									+ mob.name.toLowerCase() + mob.s : "")
							+ " spawned.");
				} catch (Mob.MobException e1) {
					player.sendMessage("Unable to spawn mobs.");
					return true;
				} catch (java.lang.NumberFormatException e2) {
					player.sendMessage("Malformed integer.");
					return true;
				}
			} else {
				player.sendMessage(mob.name
						+ (split0.length == 2 ? " riding a "
								+ mob.name.toLowerCase() : "") + " spawned.");
			}
			return true;
		} else {
			player.sendMessage("/spawnmob <Mob Name> (Amount)");
			player.sendMessage("/spawnmob kill <all-animals-monsters>");
		}
		return false;
	}

	private static String capitalCase(String s) {
		return s.toUpperCase().charAt(0) + s.toLowerCase().substring(1);
	}
}
