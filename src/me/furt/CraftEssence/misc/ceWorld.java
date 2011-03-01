package me.furt.CraftEssence.misc;

import java.util.ArrayList;
import java.util.List;


import me.furt.CraftEssence.CraftEssence;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.util.config.Configuration;

public class ceWorld {
  private CraftEssence plugin;
  private Configuration configuration;
  private World world;
  private String name;
  private World.Environment environment;
  private Location spawn;
  private Boolean mobs;
  private Boolean animals;
  private Boolean pvp;
  private Double price;
  private int ratio;
  private String alias = "";
  public List<String> blockBlacklist;
  public List<String> joinWhitelist;
  public List<String> joinBlacklist;
  public List<String> editWhitelist;
  public List<String> editBlacklist;
  public List<String> worldBlacklist;

  public ceWorld(World world, Configuration config, CraftEssence instance)
  {
    this.plugin = instance;
    this.world = world;
    this.name = world.getName();
    this.environment = world.getEnvironment();
    this.alias = config.getString("worlds." + this.name + ".alias", "");
    this.mobs = Boolean.valueOf(config.getBoolean("worlds." + this.name + ".mobs", true));
    this.animals = Boolean.valueOf(config.getBoolean("worlds." + this.name + ".animals", true));
    this.pvp = Boolean.valueOf(config.getBoolean("worlds." + this.name + ".pvp", true));

    this.price = Double.valueOf(config.getDouble("worlds." + this.name + ".price", 0.0D));
    this.configuration = config;

    this.joinWhitelist = setupPermissions("playerWhitelist");
    this.joinBlacklist = setupPermissions("playerBlacklist");
    this.editWhitelist = setupPermissions("editWhitelist");
    this.editBlacklist = setupPermissions("editBlacklist");
    this.worldBlacklist = setupPermissions("worldBlacklist");
    this.blockBlacklist = setupPermissions("blockBlacklist");

    setupSpawn();
  }

  public String getAlias() {
    return this.alias;
  }

  private List<String> setupPermissions(String permission) {
    List<String> list = new ArrayList<String>();

    String test = this.configuration
      .getString("worlds." + this.name + "." + permission, "")
      .replace(']', ' ').replace('[', ' ');
    if (!test.equals("")) {
      String[] temp = test.split(",");
      if (temp.length > 0) {
        for (int i = 0; i < temp.length; i++) {
          list.add(temp[i].toString().trim());
        }
      }
    }
    return list;
  }

  private void setupSpawn() {
    String[] spawn = this.configuration.getString("worlds." + this.name + 
      ".spawn", "").split(":");
    if (spawn.length != 5)
      this.spawn = this.world.getSpawnLocation();
    else {
      this.spawn = createLocationFromString(this.world, spawn[0], spawn[1], 
        spawn[2], spawn[3], spawn[4]);
    }
    saveAll();
  }

  private Location createLocationFromString(World world, String xStr, String yStr, String zStr, String yawStr, String pitchStr)
  {
    double x = Double.parseDouble(xStr);
    double y = Double.parseDouble(yStr);
    double z = Double.parseDouble(zStr);
    float yaw = Float.valueOf(yawStr).floatValue();
    float pitch = Float.valueOf(pitchStr).floatValue();

    return new Location(world, x, y, z, yaw, pitch);
  }

  public World getWorld() {
    return this.world;
  }

  public String getName() {
    return this.name;
  }

  public World.Environment getEnvironment() {
    return this.environment;
  }

  public Location getSpawnLocation() {
    return this.spawn;
  }

  public void setSpawnLocation(Location loc) {
    this.spawn = loc;
    saveAll();
  }

  public Boolean getMobSpawn() {
    return this.mobs;
  }

  public void setMobSpawn(Boolean mobs) {
    this.mobs = mobs;
    ((CraftWorld)this.world).getHandle().D = mobs.booleanValue();
    saveAll();
  }

  public void setAnimalSpawn(Boolean animals) {
    this.animals = animals;
    ((CraftWorld)this.world).getHandle().E = animals.booleanValue();

    saveAll();
  }

  public Boolean getAnimalSpawn() {
    return this.animals;
  }

  public Boolean getPVP() {
    return this.pvp;
  }

  public void setPVP(Boolean pvp) {
    this.pvp = pvp;
  }

  public void setPrice(Double price) {
    this.price = price;
    saveAll();
  }

  public Double getPrice() {
    return this.price;
  }

  public int getRatio() {
    return this.ratio;
  }

  public void saveAll() {
    this.configuration.setProperty("worlds." + this.name + ".alias", 
      this.alias);
    this.configuration.setProperty("worlds." + this.name + ".spawn", 
      this.plugin.locationToString(this.spawn));
    this.configuration.setProperty("worlds." + this.name + ".environment", 
      this.environment.toString());
    this.configuration.setProperty("worlds." + this.name + ".mobs", 
      this.mobs);
    this.configuration.setProperty("worlds." + this.name + ".animals", 
      this.animals);
    this.configuration
      .setProperty("worlds." + this.name + ".pvp", this.pvp);
    this.configuration.setProperty("worlds." + this.name + ".price", 
      this.price);

    StringBuilder whitelist = new StringBuilder();
    for (String value : this.joinWhitelist) {
      whitelist.append(value + ",");
    }
    this.configuration.setProperty("worlds." + this.name + 
      ".playerWhitelist", whitelist.toString());

    StringBuilder blacklist = new StringBuilder();
    for (String value : this.joinBlacklist) {
      blacklist.append(value + ",");
    }
    this.configuration.setProperty("worlds." + this.name + 
      ".playerBlacklist", blacklist.toString());

    StringBuilder worldblacklist = new StringBuilder();
    for (String value : this.worldBlacklist) {
      worldblacklist.append(value + ",");
    }
    this.configuration.setProperty("worlds." + this.name + 
      ".worldBlacklist", worldblacklist.toString());

    this.configuration.save();
  }
}