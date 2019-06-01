package com.rs.game.player.actions.crafting;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author paolo
 *
 */
public class AscensionBoltsCrafting {

	public static final Animation BLOWING_ANIMATION = new Animation(1);

	public static boolean canSmith(Player player, int itemId) {
		for (TetsuItem item : TetsuItem.values()) {
			if (item.id == itemId) {
				smithTetsu(player, item);
				return true;
			}
		}
		return false;
	}


	private static void smithTetsu(Player player, TetsuItem item) {
		if (player.getSkills().getLevel(Skills.FLETCHING) < 90) {
			player.sendMessage("You need a fletching level of 90 to make this.");
			return;
		}
		player.getInventory().deleteItem(new Item (28436,15));
		player.setNextAnimation(new Animation(6702));
		player.getSkills().addXp(Skills.FLETCHING, item.exp);
		player.getInventory().addItem(new Item(item.id, item.amount));
	}

	public enum TetsuItem{
		BOLTS(28465,15,20);
		int id,amount,exp;
		
		private TetsuItem(int id, int am, int exp){
			this.id = id;
			this.amount = am;
			this.exp = exp;
			
		}
	}
}