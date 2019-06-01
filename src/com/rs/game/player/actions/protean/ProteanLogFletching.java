package com.rs.game.player.actions.protean;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
 
 
 
public class ProteanLogFletching {

	public static final Animation ANIM = new Animation(26267);

    public static final int PROTEAN_LOG = 34528;
	/**
	 * smelts the item
	 */
	private static void smelt(Player player) {
		player.setNextAnimation(ANIM);
		player.getSkills().addXp(Skills.FLETCHING, 220);
		player.getInventory().deleteItem(new Item(PROTEAN_LOG));
	}


	public static boolean canBlow(Player player, int itemId) {
		int moltenGlassQ = player.getInventory().getAmountOf(PROTEAN_LOG);
		if (moltenGlassQ == 0) {
			player.getPackets().sendGameMessage("You've ran out of protean logs to work with.", true);
			return false;
		}
		smelt(player);
		return true;
		
	}

}