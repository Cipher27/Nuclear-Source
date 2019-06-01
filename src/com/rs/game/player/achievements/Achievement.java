package com.rs.game.player.achievements;

import java.io.Serializable;

import com.rs.game.item.Item;
import com.rs.game.player.Player;


/**
 * @author Tyluur <itstyluur@gmail.com> && Paolo
 * @since Mar 23, 2014
 */
public abstract class Achievement implements Serializable {

	public Achievement(Types type,String title,String description ) {
		this.type = type;
		this.title = title;
		this.description = description;
	}

	private final String description;
	private final Types type;
	private final String title;

	/**
	 * Gives the player their achievement points reward
	 * @param player The player
	 * @param amount The amount of points to give
	 */
	protected void addAchievementPoints(Player player, int amount) {
		player.getPointsManager().setAchievementPoints(player.getPointsManager().getAchievementPoints() + amount);
	}

	/**
	 * Gives the player the item reward. If their controler is null it will
	 * go to their inventory if they have space for it. If they don't have
	 * space for it it will go to their bank
	 *
	 * @param player
	 *            The player
	 * @param items
	 *            The items to give
	 */
	protected void addItem(Player player, Item... items) {
		boolean banked = false;
		if (player.getControlerManager().getControler() == null) {
			for (Item item : items) {
				if (player.getInventory().getFreeSlots() >= items.length) {
					player.getBank().addItem(item.getId(), item.getAmount(), true);
					banked = true;
				} else if (!player.getInventory().addItem(item)) {
					player.getBank().addItem(item.getId(), item.getAmount(), true);
					banked = true;
				}
			}
		} else {
			for (Item item : items) {
				player.getBank().addItem(item.getId(), item.getAmount(), true);
				banked = true;
			}
		}
		if (banked) {
			player.getDialogueManager().startDialogue("SimpleMessaage", "You have just received your achievements rewards in your bank!");
			player.sendMessage("You have just received your achievements rewards in your bank!");
		}
	}

	/**
	 * The reward information for this achievement
	 */
	public abstract String getRewardInfo();
	
	public abstract Item[] getRewards();

	/**
	 * Gives the reward to the player
	 * @param player The player
	 */
	public abstract void giveReward(Player player);

	/**
	 * Gets the total amount to be completed for this achievement
	 * @return
	 */
	public abstract int getTotalAmount();

	/**
	 * Gets the amount finished from this achievement
	 *
	 * @param player
	 *            The player
	 */
	public int getAmountFinished(Player player) {
		return player.getAchievementManager().getAmount(getKey());
	}

	/**
	 * The achievement key in the player data map
	 */
	public abstract String getKey();

	/**
	 * If it is complete
	 * @param player
	 */
	public boolean isComplete(Player player) {
		return getAmountFinished(player) == getTotalAmount();
	}

	public boolean unlocked(Player player) {
		return true;
	}

	public String getUnlockInfo() {
		return "";
	}

	/**
	 * @return the type
	 */
	public Types getType() {
		return type;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description.replaceAll("@TOTAL@", "" + getTotalAmount());
	}

	private static final long serialVersionUID = 8930744797859183503L;

}
