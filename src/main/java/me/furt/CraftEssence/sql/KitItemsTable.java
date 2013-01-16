package me.furt.CraftEssence.sql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.inventory.ItemStack;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "ce_kit_items")
public class KitItemsTable {
	@Id
	private int id;
	
	@NotNull
	private int itemid;

	@NotNull
	private int item;
	
	@NotNull
	private short durability;

	@NotNull
	private int quanity;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public int getItem() {
		return item;
	}
	
	public void setItem(int item) {
		this.item = item;
	}
	
	public short getDurability() {
		return durability;
	}
	
	public void setDurability(short durability) {
		this.durability = durability;
	}
	
	public int getQuanity() {
		return quanity;
	}
	
	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}
	
	public ItemStack getItemstack() {
		return new ItemStack(item, quanity, durability);
	}

}
