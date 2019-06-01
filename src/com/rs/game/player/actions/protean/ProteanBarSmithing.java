package com.rs.game.player.actions.protean;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
 
 
 
public class ProteanBarSmithing {


	public static final Animation SMITHING_ANIMATION = new Animation(898);

    public static final int PROTEAN_BAR = 31350;
	/**
	 * smelts the item
	 */
	private static void smelt(Player player) {
		player.setNextAnimation(SMITHING_ANIMATION);
		player.getSkills().addXp(Skills.SMITHING, 220);
		player.getInventory().deleteItem(new Item(PROTEAN_BAR));
	}


	public static boolean canBlow(Player player, int itemId) {
		int moltenGlassQ = player.getInventory().getAmountOf(ProteanBarSmithing.PROTEAN_BAR);
		if (moltenGlassQ == 0) {
			player.getPackets().sendGameMessage("You've ran out of protean bars to work with.", true);
			return false;
		}
		smelt(player);
		return true;
		
	}

}