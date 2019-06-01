package com.rs.game.player.content.presets;

import java.io.Serializable;

import com.rs.game.item.Item;
import com.rs.game.player.actions.summoning.Summoning.Pouches;

public final class Preset implements Serializable {
	/**
	 * @Author Harrison / Hc747
	 */
	private static final long serialVersionUID = 1385575955598546603L;
	
	private final Item[] inventory, equipment, familiar;
	private final int[] books;
	private final Pouches pouch;
	
	public Preset(final Item[] inventory, final Item[] equipment, final Item[] familiar, final Pouches pouch, final int[] books) {
		this.inventory = inventory;
		this.equipment = equipment;
		this.familiar = familiar;
		this.pouch = pouch;
		this.books = books;
	}
	
	public Preset(final Item[] inventory, final Item[] equipment, final int[] books) {
		this(inventory,equipment, null, Pouches.WOLPERTINGER, books);
	}
	
	public final Item[] getInventory() {
		return inventory;
	}
	
	public final Item[] getFamiliar() {
		return familiar;
	}
	
	public final Pouches getPouch() {
		return pouch;
	}
	
	public final Item[] getEquipment() {
		return equipment;
	}
	
	public final int[] getBooks() {
		return books;
	}
	
}