package com.rs.game.item;

import java.io.Serializable;

import com.rs.cache.loaders.ItemDefinitions;

/**
 * Represents a single item.
 * <p/>
 * 
 * @author Graham / edited by Dragonkk(Alex)
 */
public class Item implements Serializable {

	private static final long serialVersionUID = -6485003878697568087L;

	@Deprecated
	private short id;
	private int newId;
	protected int amount;
	private int charges;

	public int getId() {
		return newId != 0 ? newId : id;
	}

	@Override
	public Item clone() {
		return new Item(newId, amount);
	}

	public Item(int id, int amount, int charges){
		this(id, amount, false);
		this.charges = charges;
	}
	public Item(int id) {
		this(id, 1);
	}

	public Item(int id, int amount) {
		this(id, amount, false);
	}

	public Item(int id, int amount, boolean amt0) {
		this.newId = id;
		this.amount = amount;
		this.setCharges(-1);
		if (this.amount <= 0 && !amt0) {
				this.amount = 1;
		}
	}
	/*
	 * xd?
	 */
	public Item(Item item) {
		this(item.getId(),item.getAmount(),false);
	}

	public ItemDefinitions getDefinitions() {
		return ItemDefinitions.getItemDefinitions(newId);
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setId(int id) {
		this.newId = id;
	}

	public int getAmount() {
		return amount;
	}

	public String getName() {
		return getDefinitions().getName();
	}
	private String name;
	public void setName(String string) {
		this.name = getDefinitions().name.equals(string) ? null : string;
	//	changedName = true;
	}

	/**
	 * @return the charges
	 */
	public int getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(int charges) {
		this.charges = charges;
	}

	

}
