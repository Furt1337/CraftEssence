package me.furt.CraftEssence.sql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "ce_user")
public class UserTable {
	@Id
	private int id;
	
	@NotNull
	private String userName;
	
	@NotNull
	private String displyName;
	
	@NotNull
	private boolean online;
	
	@NotNull
	private boolean afk;
	
	@NotNull
	private long afkTime;
	
	@NotNull
	private boolean muted;
	
	@NotNull
	private int logins;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setDisplyName(String displyName) {
		this.displyName = displyName;
	}

	public String getDisplyName() {
		return displyName;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isOnline() {
		return online;
	}

	public void setAfk(boolean afk) {
		this.afk = afk;
	}

	public boolean isAfk() {
		return afk;
	}

	public void setAfkTime(long afkTime) {
		this.afkTime = afkTime;
	}

	public long getAfkTime() {
		return afkTime;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setLogins(int logins) {
		this.logins = logins;
	}

	public int getLogins() {
		return logins;
	}

}
