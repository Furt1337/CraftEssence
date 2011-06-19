package me.furt.CraftEssence.sql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name="ce_warp")
public class WarpTable {
	@Id
    private int id;
	
	@NotEmpty
	@Length(max=30)
	private String name;
	
	@NotEmpty
	private String world;
	
	@NotNull
	private int x;
	
	@NotNull
	private int y;
	
	@NotNull
	private int z;
	
	@NotNull
	private int pitch;
	
	@NotNull
	private int yaw;
	
	public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getWorld() {
		return world;
	}
	
	public void setWorld(String world) {
		this.world = world;
	}
	
	public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getYaw() {
        return yaw;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }
    
    public void setLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = (int) location.getX();
        this.y = (int) location.getY();
        this.z = (int) location.getZ();
        this.yaw = (int) location.getYaw();
        this.pitch = (int) location.getPitch();
        
    }

    public Location getLocation() {
        World wrld = Bukkit.getServer().getWorld(world);
        return new Location(wrld, x, y, z, yaw, pitch);
    }

}
