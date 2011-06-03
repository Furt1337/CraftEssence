package me.furt.CraftEssence.sql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity
@Table(name = "ce_jail")
public class JailTable {
	@Id
	private int id;

	@NotNull
	private String playerName;
	
	@NotNull
	private String worldName;
	
	@NotNull
	private boolean timed;
	
	@NotNull
	private long startTime;
	
	@NotNull
	private long endTime;
	
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getPlayerName() {
		return this.playerName;
	}
	
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	public String getWorldName() {
		return this.worldName;
	}
	
	public void setWorldName(String name) {
		this.worldName = name;
	}

	public void setTimed(boolean timed) {
		this.timed = timed;
	}

	public boolean isTimed() {
		return timed;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getEndTime() {
		return endTime;
	}
}