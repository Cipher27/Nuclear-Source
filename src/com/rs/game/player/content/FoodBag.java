package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;

public class FoodBag implements Serializable {

	private static final int ITEMS_KEY = 530;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2090871604834210257L;

	private transient Player player;

	private ItemsContainer<Item> bagItems;

	public FoodBag() {
		bagItems = new ItemsContainer<Item>(30, false);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void open() {
		player.getInterfaceManager().sendInterface(671);
		player.getPackets().sendIComponentText(671, 14,
				"Food Bag");
		player.getInterfaceManager().sendInventoryInterface(665);
		sendInterItems();
		sendOptions();
	}

	public void takeBag() {
		Item[] itemsBefore = bagItems.getItemsCopy();
		for (int i = 0; i < bagItems.getSize(); i++) {
			Item item = bagItems.get(i);
			if (item != null) {
				if (!player.getInventory().addItem(item))
					break;
				bagItems.remove(i, item);
			}
		}
		bagItems.shift();
		refreshItems(itemsBefore);
	}

	public void removeItem(int slot, int amount) {
		Item item = bagItems.get(slot);
		if (item == null)
			return;
		Item[] itemsBefore = bagItems.getItemsCopy();
		int maxAmount = bagItems.getNumberOf(item);
		if (amount < maxAmount)
			item = new Item(item.getId(), amount);
		else
			item = new Item(item.getId(), maxAmount);
		int freeSpace = player.getInventory().getFreeSlots();
		if (!item.getDefinitions().isStackable()) {
			if (freeSpace == 0) {
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
				return;
			}
			if (freeSpace < item.getAmount()) {
				item.setAmount(freeSpace);
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
			}
		} else {
			if (freeSpace == 0
					&& !player.getInventory().containsItem(item.getId(), 1)) {
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
				return;
			}
		}
		bagItems.remove(slot, item);
		bagItems.shift();
		player.getInventory().addItem(item);
		refreshItems(itemsBefore);
	}

	public boolean isFood(String food) {
		if (food.contains("rocktail") || food.contains("shark")
				|| food.contains("lobs") || food.contains("monk")
				|| food.contains("swordfish") || food.contains("brew")
				|| food.contains("restor"))
			return true;
		return false;
	}
	
	public void addItem(int slot, int amount) {
		Item item = player.getInventory().getItem(slot);
		if (item == null)
			return;
		if (!isFood(item.getName().toLowerCase())) {
			player.getPackets().sendGameMessage(
					"You can't store this item in your food bag!");
			return;
		}
		Item[] itemsBefore = bagItems.getItemsCopy();
		int maxAmount = player.getInventory().getItems().getNumberOf(item);
		if (amount < maxAmount)
			item = new Item(item.getId(), amount);
		else
			item = new Item(item.getId(), maxAmount);
		int freeSpace = bagItems.getFreeSlots();
		if (!item.getDefinitions().isStackable()) {
			if (freeSpace == 0) {
				player.getPackets().sendGameMessage("Your food bag is full!");
				return;
			}

			if (freeSpace < item.getAmount()) {
				item.setAmount(freeSpace);
				player.getPackets().sendGameMessage("Your food bag is full!");
			}
		} else {
			if (freeSpace == 0 && !bagItems.containsOne(item)) {
				player.getPackets().sendGameMessage("Your food bag is full!");
				return;
			}
		}
		bagItems.add(item);
		bagItems.shift();
		player.getInventory().deleteItem(slot, item);
		refreshItems(itemsBefore);
	}

	public void refreshItems(Item[] itemsBefore) {
		int[] changedSlots = new int[itemsBefore.length];
		int count = 0;
		for (int index = 0; index < itemsBefore.length; index++) {
			Item item = bagItems.getItems()[index];
			if (itemsBefore[index] != item) {
				changedSlots[count++] = index;
			}

		}
		int[] finalChangedSlots = new int[count];
		System.arraycopy(changedSlots, 0, finalChangedSlots, 0, count);
		refresh(finalChangedSlots);
	}

	public void refresh(int... slots) {
		player.getPackets().sendUpdateItems(ITEMS_KEY, bagItems, slots);
	}

	public void sendOptions() {
		player.getPackets().sendUnlockIComponentOptionSlots(665, 0, 0, 27, 0,
				1, 2, 3, 4, 5);
		player.getPackets().sendInterSetItemsOptionsScript(665, 0, 93, 4, 7,
				"Store", "Store-5", "Store-10", "Store-All", "Store-X",
				"Examine");
		player.getPackets().sendUnlockIComponentOptionSlots(671, 27, 0,
				ITEMS_KEY, 0, 1, 2, 3, 4, 5);
		player.getPackets().sendInterSetItemsOptionsScript(671, 27, ITEMS_KEY,
				6, 5, "Withdraw", "Withdraw-5", "Withdraw-10", "Withdraw-All",
				"Withdraw-X", "Examine");
	}

	public boolean containsOneItem(int... itemIds) {
		for (int itemId : itemIds) {
			if (bagItems.containsOne(new Item(itemId, 1)))
				return true;
		}
		return false;
	}

	public void sendInterItems() {
		player.getPackets().sendItems(ITEMS_KEY, bagItems);
		player.getPackets().sendItems(93, player.getInventory().getItems());
	}

	public ItemsContainer<Item> getbagItems() {
		return bagItems;
	}
}