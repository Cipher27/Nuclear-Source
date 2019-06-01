package com.rs.game.player.content.ports.actions;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author paolo
 *
 */
public class TetsuSmithing {

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
		if (player.getSkills().getLevel(Skills.SMITHING) < 90) {
			player.sendMessage("You need a smithing level of 90 to make this.");
			return;
		}
		if(player.getPorts().plate < item.amount){
			player.sm("You don't have enough plate to make this, you need "+item.amount+".");
			return;
		}
		player.setNextAnimation(BLOWING_ANIMATION);
		player.getSkills().addXp(Skills.SMITHING, item.exp);
		player.getPorts().plate -= item.amount;
		player.getInventory().addItem(new Item(item.id));
		player.sendMessage("You form a figure out of the glass. ");
	}

	public enum TetsuItem{
		HELM(26325,30,10000),
		BODY(26326,80,30000),
		LEGS(26327,50,20000);
		int id,amount,exp;
		
		private TetsuItem(int id, int am, int exp){
			this.id = id;
			this.amount = am;
			this.exp = exp;
			
		}
	}
}