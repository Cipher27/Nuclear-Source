package com.rs.game.player.actions.slayer;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * @author Miles Black (bobismyname)
 */

public class SlayerHelmets {

	/**
	 * Checks if the player has requirements
	 * 
	 * @param player
	 *            - the player
	 * @return
	 */
	public static boolean hasRequirements(Player player) {
		if(!player.canCraftSlayerHelmtet){
			player.sm("You have not unlocked this feature yet.");
			return false;
		}
		if (player.getSkills().getLevel(Skills.CRAFTING) < 55) {
			player.getPackets().sendGameMessage(
					"You need a Crafting level of 55 to make a slayer helmet.");
			return false;
		}
		if (!player.getInventory().containsItem(4166, 1)
				|| !player.getInventory().containsItem(4164, 1)
				|| !player.getInventory().containsItem(4168, 1)
				|| !(player.getInventory().containsItem(8901, 1) || player.getInventory().containsItem(8901, 1) || player.getInventory().containsItem(8903, 1)
						|| player.getInventory().containsItem(8905, 1) || player.getInventory().containsItem(8907, 1) || player.getInventory().containsItem(8909, 1)
						|| player.getInventory().containsItem(8911, 1) || player.getInventory().containsItem(8913, 1) || player.getInventory().containsItem(8915, 1)
						|| player.getInventory().containsItem(8917, 1) || player.getInventory().containsItem(8919, 1))
				|| !player.getInventory().containsItem(4551, 1)) {
			player.getPackets()
					.sendGameMessage(
							"You don't have the required items to make a slayer helmet.");
			return false;
		}
		return true;
	}

	/**
	 * Handles the creation of the helmet
	 * 
	 * @param player
	 *            - the player
	 */
	private static void handleCreation(Player player) {
		if (!hasRequirements(player)) {
			return;
		}
		player.getInventory().deleteItem(4166, 1);
		player.getInventory().deleteItem(4168, 1);
		player.getInventory().deleteItem(4164, 1);
		player.getInventory().deleteItem(4551, 1);
		for (int i = 8901; i <= 8921; i += 2)
			player.getInventory().deleteItem(i, 1);
		player.getInventory().addItem(13263, 1);
		player.getPackets().sendGameMessage(
				"You add all of the items together and make a slayer helmet.");
	}

	/**
	 * Handles the item on item action
	 * 
	 * @param player
	 *            - the player
	 * @param usedOn
	 *            - the item used on
	 * @param usedWith
	 *            - the item used with
	 * @return boolean
	 */
	public static boolean handleItemOnItem(Player player, Item usedOn,
			Item usedWith) {
		if ((usedOn.getId() == 4166 || usedOn.getId() == 4168
				|| usedOn.getId() == 4164 || usedOn.getId() == 4551
				&& usedWith.getId() == 8901)
				|| (usedWith.getId() == 4166 || usedWith.getId() == 4168
						|| usedWith.getId() == 4164 || usedWith.getId() == 4551
						&& usedOn.getId() == 8901)) {
			handleCreation(player);
			return true;
		}
		if ((usedOn.getId() == 13263 || usedOn.getId() == 15488
				&& usedWith.getId() == 15490)
				|| (usedOn.getId() == 15488 || usedOn.getId() == 15490
						&& usedWith.getId() == 13263)
				|| (usedOn.getId() == 15490 || usedOn.getId() == 13263
						&& usedWith.getId() == 15488)) {
			createFullHelmet(player);
			return true;
		}
		return false;
	}

	/**
	 * Handles the disassembly of the helmet
	 * 
	 * @param player
	 */
	public static void disassemble(Player player) {
		if (player.getInventory().getFreeSlots() >= 5) {
			player.getInventory().deleteItem(13263, 1);
			player.getInventory().addItem(4166, 1);
			player.getInventory().addItem(4168, 1);
			player.getInventory().addItem(4164, 1);
			player.getInventory().addItem(4551, 1);
			player.getInventory().addItem(8901, 1);
		}
	}

	/**
	 * Handles the creation of the full slayer helmet
	 * 
	 * @param player
	 */
	public static void createFullHelmet(Player player) {
		if (!player.getInventory().containsItem(13263, 1)
				|| !player.getInventory().containsItem(15490, 1)
				|| !player.getInventory().containsItem(15488, 1)) {
			player.getPackets()
					.sendGameMessage(
							"You don't have the required items to make a full slayer helmet.");
			return;
		}
		player.getInventory().deleteItem(15488, 1);
		player.getInventory().deleteItem(15490, 1);
		player.getInventory().deleteItem(13263, 1);
		player.getInventory().addItem(15492, 1);
		player.getPackets()
				.sendGameMessage(
						"You add the hexcrest and the focus sight onto the slayer helmet.");
	}
}