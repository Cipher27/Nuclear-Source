package com.rs.game.player.content;

import java.io.Serializable;
import java.util.Arrays;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.ChatColors;
import com.rs.utils.Utils;

/**
 *
 * @author _Hassan <https://www.rune-server.ee/members/_hassan/> Created: 25 Jul
 *         2017 ~ server
 *
 */

public class SkillingUrnsManager implements Serializable {

	/*
	 * TODO:
	 * 
	 * - Teleporting + giving EXP after 100% [DONE]
	 * 
	 * - Adding rest of the URNS
	 * 
	 * - Find a way to make it add material to the urn you've got in inventory
	 * 
	 * - Find a way to fix progress messages
	 * 
	 * - Find a way to save the % for each individual urn
	 * 
	 * - Handle the "Check level" option on an urn
	 */

	private static final long serialVersionUID = 1169647064741309950L;

	private Player player;

	private final int PUTTING_RUNE_IN = 8652;

	private double[] URN_PERCENTAGE;

	public SkillingUrnsManager(Player player) {
		this.player = player;

		/* temp until finished */
		URN_PERCENTAGE = new double[Utils.getItemDefinitionsSize()];
	}

	public void runeOnUrn(Item item, Item usedOn) {
		SkillingUrns skill_urns = urns(item.getId(), usedOn.getId());
		if (skill_urns.hasUrn(player)) {
			player.sm("You should finish the " + skill_urns.getItemName(2).toLowerCase() + " urn you have started before starting a new one.");
			return;
		}
		if (item.getId() == skill_urns.runeID && usedOn.getId() == skill_urns.itemID[0] || item.getId() == skill_urns.itemID[0] && usedOn.getId() == skill_urns.runeID) {
			player.lock(5);
			player.setNextAnimation(new Animation(PUTTING_RUNE_IN));
			player.getSkills().addXp(Skills.CRAFTING, 500);
			player.getInventory().deleteItem(skill_urns.runeID, 1);
			player.getInventory().deleteItem(skill_urns.itemID[0], 1);
			player.getInventory().addItem(skill_urns.itemID[1], 1);
		}
	}

	public void addMaterial(SkillingUrns urn, double exp, int skill) {
		if (urn.skill != skill || (!player.getInventory().containsOneItem(urn.itemID[1]) && !urn.hasUrn(player)))
			return;

		replaceUrn(urn);

		player.sm(getProgressMsg(urn,exp));
		URN_PERCENTAGE[urn.itemID[2]] += (20 * exp) / urn.exp;

		if (URN_PERCENTAGE[urn.itemID[2]] >= 100)
			teleportUrn(urn);

	}

	final void teleportUrn(SkillingUrns urn) {
		URN_PERCENTAGE[urn.itemID[2]] = 0.0;
		player.setNextAnimation(new Animation(urn.animationID));
		player.getInventory().deleteItem(urn.itemID[2], 1);
		player.getSkills().addXp(urn.skill, urn.exp);
		player.sm("You activate the rune on the urn and it is teleported away. You gain " + Utils.getFormattedNumber(urn.exp, ',') + " " + Skills.SKILL_NAME[urn.skill] + " XP.");
	}

	void replaceUrn(SkillingUrns urn) {
		if (urn.hasUrn(player))
			return;

		player.sm(ChatColors.ACHIEVEMENT + "You start a new " + urn.getItemName(2) + " urn.");
		player.getInventory().deleteItem(urn.itemID[1], 1);
		player.getInventory().addItem(urn.itemID[2], 1);
	}

	public static SkillingUrns urns(int itemID, int usedOn) {
		return Arrays.stream(SkillingUrns.values()).filter(urn -> itemID == urn.itemID[0] || usedOn == urn.itemID[0]).findAny().orElse(null);
	}

	private String getProgressMsg(SkillingUrns urns, double exp) {
		String name = urns.getItemName(2);
		if (URN_PERCENTAGE[urns.itemID[2]] >= 25)
			return ChatColors.ACHIEVEMENT + "Your " + name + " is quarter full.";
		else if (URN_PERCENTAGE[urns.itemID[2]] >= 50)
			return ChatColors.ACHIEVEMENT + "Your " + name + " is half full.";
		else if (URN_PERCENTAGE[urns.itemID[2]] >= 75)
			return ChatColors.ACHIEVEMENT + "Your " + name + " is three-quarters full.";
		return null;
	}

	public enum SkillingUrns {

		/*
		 * Items IDS sorted by: (nr), (r), normal (normal = in progress), (full)
		 */

		CRACKED_COOKING(400, 10, 6794, Magic.FIRE_RUNE, Skills.COOKING, new int[] { 20350, 20352, 20353, 20354 }),

		CRACKED_FISHING(150, 10, 6472, Magic.WATER_RUNE, Skills.FISHING, new int[] { 20320, 20322, 20323, 20324 }),

		;

		int[] itemID;
		int materialLevelLimit, animationID, runeID, skill;

		double exp;

		SkillingUrns(double exp, int materialLevelLimit, int animationID, int runeID, int skill, int... itemID) {
			this.exp = exp;
			this.materialLevelLimit = materialLevelLimit;
			this.animationID = animationID;
			this.runeID = runeID;
			this.skill = skill;
			this.itemID = itemID;
		}

		public String getItemName(int type) {
			return ItemDefinitions.getItemDefinitions(itemID[type]).name;
		}

		public boolean hasUrn(Player player) {
			if (player.getInventory().containsOneItem(itemID[2]) || (player.getBank().getItem(itemID[2]) != null) || (player.getFamiliar() != null && player.getFamiliar().getBob().containsOneItem(itemID[2])))
				return true;
			return false;
		}

	}

}
