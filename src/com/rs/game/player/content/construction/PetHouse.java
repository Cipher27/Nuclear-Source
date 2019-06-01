package com.rs.game.player.content.construction;

import java.io.Serializable;

import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;
import com.rs.game.player.content.pet.Pets;

public class PetHouse implements Serializable {

	private static final long serialVersionUID = 5398361987854065145L;
	
	private static final int ITEMS_KEY = 541;
	
	private transient Player player;
	
	private ItemsContainer<Item> pets;

	public PetHouse() {
		pets = new ItemsContainer<Item>(72, false);
	}
	
	public void open() {
		player.getInterfaceManager().sendInterface(879);
		player.getInterfaceManager().sendInventoryInterface(665);
		player.getPackets().sendUnlockIComponentOptionSlots(665, 0, 0, 27, 0, 1);
		player.getPackets().sendInterSetItemsOptionsScript(665, 0, 93, 4, 7, "Store", "Examine");
		player.getPackets().sendUnlockIComponentOptionSlots(879, 0, 0, ITEMS_KEY, 0, 1);
		player.getPackets().sendInterSetItemsOptionsScript(879, 0, ITEMS_KEY, 12, 6, "Withdraw", "Examine");
		refresh();
	}
	
	public void refresh() {
		player.getPackets().sendItems(ITEMS_KEY, pets);
		player.getPackets().sendItems(93, player.getInventory().getItems());	
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void removeItem(int slot) {
		Item item = pets.get(slot);
		if (item == null) {
			return;
		}
		item = new Item(item.getId(), 1);
		int freeSpace = player.getInventory().getFreeSlots();
		if (!item.getDefinitions().isStackable()) {
			if (freeSpace == 0) {
				player.getPackets().sendGameMessage("Not enough space in your inventory.");
				return;
			}
			if (freeSpace < item.getAmount()) {
				item.setAmount(freeSpace);
				player.getPackets().sendGameMessage("Not enough space in your inventory.");
			}
		} else {
			if (freeSpace == 0 && !player.getInventory().containsItem(item.getId(), 1)) {
				player.getPackets().sendGameMessage("Not enough space in your inventory.");
				return;
			}
		}
		player.getHouse().removePet(item, true);
		pets.remove(slot, item);
		pets.shift();
		player.getInventory().addItem(item);
		refresh();
	}

	public void addItem(int slot) {
		int usedSlots = pets.getSize()-pets.freeSlots();
		Item item = player.getInventory().getItem(slot);
		if (item == null) {
			return;
		}
		if (Pets.forId(item.getId()) == null) {
			player.getPackets().sendGameMessage("You cannot store this item.");
			return;
		}
		item = new Item(item.getId(), 1);
		int freeSpace = pets.getFreeSlots();
		if (!item.getDefinitions().isStackable()) {
			if (freeSpace == 0) {
				player.getPackets().sendGameMessage("Not enough space in your Familiar Inventory.");
				return;
			}

			if (freeSpace < item.getAmount()) {
				item.setAmount(freeSpace);
				player.getPackets().sendGameMessage("Not enough space in your Familiar Inventory.");
			}
		} else {
			if (freeSpace == 0 && !pets.containsOne(item)) {
				player.getPackets().sendGameMessage("Not enough space in your Familiar Inventory.");
				return;
			}
		}
		player.getHouse().addPet(item, true);
		pets.add(item);
		pets.shift();
		player.getInventory().deleteItem(slot, item);
		refresh();
	}
	
	public ItemsContainer<Item> getPets() {
		return pets;
	}
	
	public boolean contains(int itemId) {
		if (pets.containsOne(new Item(itemId, 1))) {
			return true;
		}
		return false;
	}

}
