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
	@Length(max=30)
	private String world;
	
	@NotNull
	private double x;
	
	@NotNull
	private double y;
	
	@NotNull
	private double z;
	
	@NotNull
	private float pitch;
	
	@NotNull
	private float yaw;
	
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
	
	public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    
    public void setLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        
    }

    public Location getLocation() {
        World wrld = Bukkit.getServer().getWorld(world);
        return new Location(wrld, x, y, z, yaw, pitch);
    }

}
