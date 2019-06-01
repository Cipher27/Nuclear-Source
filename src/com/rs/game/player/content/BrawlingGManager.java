package com.rs.game.player.content;

import java.io.Serializable;
import java.util.Arrays;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 *
 * @author _Hassan <https://www.rune-server.ee/members/_hassan/> Created: 22 Jul
 *         2017 ~ Zaria 718-830
 *
 */

public class BrawlingGManager implements Serializable {

	private static final long serialVersionUID = -4894902212647407225L;

	private Player player;

	public int[] GLOVE_EXP;

	public BrawlingGManager(Player player) {
		this.player = player;

		/*
		 * I set it to 24 incase we want to add custom brawlings in the future
		 */
		GLOVE_EXP = new int[24];
	}

	public void dropItem(BrawlingGloves gloves, NPC npc) {
		if (gloves.hasGlove(player))
			return;

		World.addGroundItem(new Item(gloves.itemID), npc, player, true, 180, true);

		GLOVE_EXP[gloves.skillID] = player.getSkills().getLevel(gloves.skillID) * 5000;

	}
	
	public void dropItem(BrawlingGloves gloves, Player player) {
		if (gloves.hasGlove(player))
			return;

		World.addGroundItem(new Item(gloves.itemID), player, player, true, 180, true);

		GLOVE_EXP[gloves.skillID] = player.getSkills().getLevel(gloves.skillID) * 5000;

	}

	public BrawlingGloves getGloves() {
		return Arrays.stream(BrawlingGloves.values()).filter(item -> player.getEquipment().getGlovesId() == item.itemID).findAny().orElse(null);
	}

	public enum BrawlingGloves {

		ATTACK(13845, Skills.ATTACK), STRENGTH(13845, Skills.STRENGTH), DEFENCE(13845, Skills.DEFENCE),

		RANGE(13846, Skills.RANGE), MAGIC(13847, Skills.MAGIC),

		PRAYER(13848, Skills.PRAYER),

		FIREMAKING(13851, Skills.FIREMAKING),

		WOODCUTTING(13850, Skills.WOODCUTTING),

		MINING(13852, Skills.MINING),

		HUNTER(13853, Skills.HUNTER),

		THIEVING(13854, Skills.THIEVING),

		SMITHING(13855, Skills.SMITHING),

		FISHING(13856, Skills.FISHING),

		COOKING(13857, Skills.COOKING)

		;

		int itemID, skillID;

		BrawlingGloves(int itemID, int skillID) {
			this.itemID = itemID;
			this.skillID = skillID;
		}

		public int getSkillID() {
			return skillID;
		}

		public int getItemID() {
			return itemID;
		}

		public String getItemName() {
			return ItemDefinitions.getItemDefinitions(itemID).getName();
		}

		public boolean hasGlove(Player player) {
			/* Checks if player is wearing the glove */
			if (player.getEquipment().getGlovesId() == itemID
					/* Checks if it is in the inventory */
					|| player.getInventory().containsOneItem(itemID)
					/* Checks if they have it in the bank */
					|| player.getBank().getItem(itemID) != null
					/*
					 * Checks if they have it in the BOB, shouldn't be possible
					 * but still better be safe than sorry
					 */
					|| (player.getFamiliar() != null && player.getFamiliar().getBob().containsOneItem(itemID)))
				return true;
			return false;
		}

	}
}
