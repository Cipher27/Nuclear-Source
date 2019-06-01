package com.rs.game.player.robot;

import com.rs.game.item.Item;

/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public class Set {

	private final Item[] invItems;
	private final Item[] equipItems;

	public Set() {
		invItems = new Item[28];
		equipItems = new Item[15];
	}

	public Set(Item[] equipItems, Item[] invItems) {
		this.invItems = invItems;
		this.equipItems = equipItems;
	}

	public void addEquip(Item item, int slot) {
		equipItems[slot] = item;
	}

	public void addInv(Item item, int slot) {
		invItems[slot] = item;
	}

	public Item[] getEquip() {
		return equipItems;
	}

	public Item[] getInv() {
		return invItems;
	}
}