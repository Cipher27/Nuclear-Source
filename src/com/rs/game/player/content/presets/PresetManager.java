package com.rs.game.player.content.presets;

import java.io.Serializable;
import java.util.HashMap;

import com.rs.game.item.Item;
import com.rs.game.npc.familiar.BeastOfBurden;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.player.Bank;
import com.rs.game.player.Player;
import com.rs.game.player.actions.summoning.Summoning;

public final class PresetManager implements Serializable {
	/**
	 * @Author Harrison / Hc747
	 */
	private static final long serialVersionUID = -2928476953478619103L;
	/**Instantiated variables below**/
	private Player player;
	private HashMap<String, Preset> PRESET_SETUPS;
	/**Configuration variables below**/
	private static final boolean SPAWN_ALLOWED = false;
	private static final int PRAYER_INDEX = 0, SPELLS_INDEX = 1;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public PresetManager() {
		PRESET_SETUPS = new HashMap<String, Preset>();
	}
	
	private final int getMaxSize() {
		return player.getRights() > 2 ? 25 : player.isExtremeDonator() || player.isExtremePermDonator() ? 10 : player.isDonator() ? 5 : 3;
	}
	
	public void reset() {
		PRESET_SETUPS.clear();
		player.getPackets().sendGameMessage("All of your sets have been cleared. You now have "+getMaxSize()+" available slots.");
	}
	
	public void removeSet(String name) {
		if (name == "")
			return;
		name = name.toLowerCase();
		player.getPackets().sendGameMessage((PRESET_SETUPS.remove(name) == null ? "No set was found for the query: "+name : "Successfully removed the set: "+name)+".");
	}
	
	public void StoreSet(String name) {
		final int size = PRESET_SETUPS.size(), max = getMaxSize();
		if (size >= max) {
			player.getPackets().sendGameMessage("You were unable to store the set "+name+" as your maximum capacity ("+max+") has been reached.", true);
			return;
		}
		if (name == "")
			return;
		name = name.toLowerCase();
		final Preset set = PRESET_SETUPS.get(name);
		if (set != null) {
			player.getPackets().sendGameMessage("You were unable to store the set "+name+" as it already exists.",true);
			return;
		}
		final Item[] inventory = player.getInventory().getItems().getItemsCopy(), equipment = player.getEquipment().getItems().getItemsCopy();
		Item[] familiar = null;
		final Familiar fam = player.getFamiliar();
		if (fam != null) {
			final BeastOfBurden bob = fam.getBob();
			if (bob != null) {
				familiar = bob.getBeastItems().getItemsCopy();
			}
		}
		PRESET_SETUPS.put(name, new Preset(inventory, equipment, familiar,  null, new int[] {player.getPrayer().getPrayerBook(), player.getCombatDefinitions().getSpellBook()}));
		player.getPackets().sendGameMessage("You've successfully stored the set "+name+".", true);
	}
	
	public void printAvailableSetups() {
		final int size = PRESET_SETUPS.size();
		player.getPackets().sendGameMessage("You have used "+size+"/"+getMaxSize()+" available setups.",true);
		if (size > 0) {
			player.getPackets().sendGameMessage("<col=ff0000>Your available setups are:",true);
			for (final String key : PRESET_SETUPS.keySet()) {
				player.getPackets().sendGameMessage(key,true);
			}
		}
	}
	
	public void loadSet(String name) {
		if (name == "")
			return;
		name = name.toLowerCase();
		final Preset set = PRESET_SETUPS.get(name);
		if (set == null) {
			player.getPackets().sendGameMessage("You were unable to load the set "+name+" as it does not exist.",true);
			return;
		}
		if (!SPAWN_ALLOWED) {
			Item[] data = player.getInventory().getItems().getItemsCopy();
			if (data != null && data.length > 0) {
				for (final Item item : data) {
					if (item == null)
						continue;
					if (Bank.MAX_BANK_SIZE - player.getBank().getBankSize() <= 0) {
						if (!player.getBank().containsItem(item.getId(), 1)) {
							player.getPackets().sendGameMessage("Not all of your possessed items were able to be stored, and hence, the operation has been cancelled.");
							return;
						}
					}
					player.getInventory().deleteItem(item);
					player.getBank().addItem(item, true);
				}
			}
			data = player.getEquipment().getItems().getItemsCopy();
			if (data != null && data.length > 0) {
				for (final Item item : data) {
					if (item == null)
						continue;
					if (Bank.MAX_BANK_SIZE - player.getBank().getBankSize() <= 0) {
						if (!player.getBank().containsItem(item.getId(), 1)) {
							player.getPackets().sendGameMessage("Not all of your worn items were able to be stored, and hence, the operation has been cancelled.");
							return;
						}
					}
					player.getEquipment().deleteItem(item);
					player.getBank().addItem(item, true);
				}
			}
			final Familiar fam = player.getFamiliar();
			if (fam != null) {
				final BeastOfBurden bob = fam.getBob();
				if (bob != null) {
					data = bob.getBeastItems().getItemsCopy();
					if (data != null && data.length > 0) {
						for (final Item item : data) {
							if (item == null)
								continue;
							if (Bank.MAX_BANK_SIZE - player.getBank().getBankSize() <= 0) {
								if (!player.getBank().containsItem(item.getId(), 1)) {
									player.getPackets().sendGameMessage("Not all of your familiar items were able to be stored, and hence, the operation has been cancelled.");
									return;
								}
							}
							//TODO store bob item in bank
						}
					}
				}
			}
		}
		player.getPrayer().setPrayerBook(set.getBooks()[PRAYER_INDEX] == 1);
		player.getCombatDefinitions().setSpellBook(set.getBooks()[SPELLS_INDEX]);
		Item[] data = set.getInventory();
		if (data != null && data.length > 0) {
			for (int i = 0; i < data.length; i++) {
				final Item item = data[i];
				if (item == null)
					continue;
				if (!SPAWN_ALLOWED) {
					final int amount = player.getBank().getAmountOfItem(item);
					if (amount <= 0)
						continue;
					if (amount > item.getAmount())
						item.setAmount(amount);
					player.getBank().removeItem(player.getBank().getItemSlot(item.getId()), amount, true, false);
				}
				player.getInventory().getItems().set(i, item);
			}
			player.getInventory().refresh();
		}
		data = set.getEquipment();
		if (data != null && data.length > 0) {
			skip: for (int i = 0; i < data.length; i++) {
				final Item item = data[i];
				if (item == null)
					continue;
				if (!SPAWN_ALLOWED) {
					final HashMap<Integer, Integer> requirements = item.getDefinitions().getWearingSkillRequiriments();
					if (requirements != null) {
						for (final int skillId : requirements.keySet()) {
							if (skillId > 24 || skillId < 0)
								continue;
							final int level = requirements.get(skillId);
							if (level < 0 || level > 120)
								continue;
							if (player.getSkills().getLevelForXp(skillId) < level) {
								player.getPackets().sendGameMessage("You were unable to equip your "+item.getName().toLowerCase()+", as you don't meet the requirements to wear them.", true);
								continue skip;
							}
						}
					}
					final int amount = player.getBank().getAmountOfItem(item);
					if (amount <= 0)
						continue;
					if (amount > item.getAmount())
						item.setAmount(amount);
					player.getBank().removeItem(player.getBank().getItemSlot(item.getId()), amount, true, false);
				}
				player.getEquipment().getItems().set(i, new Item(set.getEquipment()[i].getId(), item.getAmount()));
				player.getEquipment().refresh(i);
			}
			player.getAppearence().generateAppearenceData();
		}
		if (set.getPouch() != null) {
			if (player.getFamiliar() != null)
				player.getFamiliar().sendDeath(player);
			Summoning.spawnFamiliar(player, set.getPouch());
			data = set.getFamiliar();
			if (data != null && data.length > 0) {
				final Familiar fam = player.getFamiliar();
				if (fam != null) {
					if (fam.getBob() != null) {
						if (set.getFamiliar() != null)
							fam.getBob().setBeastItems(set.getFamiliar());
					}
				}
			}
		}
		player.getPackets().sendGameMessage("Loaded setup: "+name+".");
	}
	
}