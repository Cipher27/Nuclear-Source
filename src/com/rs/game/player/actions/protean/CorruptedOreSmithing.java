package com.rs.game.player.actions.protean;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
 
 
 
public class CorruptedOreSmithing {

	public static final Animation SMITHING_ANIMATION = new Animation(3243);

    public static final int CORRUPTED_ORE = 32262;
	/**
	 * smelts the item
	 */
	private static void smelt(Player player) {
		player.setNextAnimation(SMITHING_ANIMATION);
		player.getSkills().addXp(Skills.SMITHING, 105);
		player.getInventory().deleteItem(new Item(CORRUPTED_ORE));
	}


	public static boolean canBlow(Player player, int itemId) {
		int moltenGlassQ = player.getInventory().getAmountOf(CORRUPTED_ORE);
		if (moltenGlassQ == 0) {
			player.getPackets().sendGameMessage("You've ran out of corrupted ores to work with.", true);
			return false;
		}
		smelt(player);
		return true;
		
	}

}