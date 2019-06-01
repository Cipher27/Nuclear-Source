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
public class SeasingersCrafting {

	public static final Animation ANIMATION = new Animation(1);

	public static boolean canCraft(Player player, int itemId) {
		for (SeaSingerItem item : SeaSingerItem.values()) {
			if (item.id == itemId) {
				craftSeaSinger(player, item);
				return true;
			}
		}
		return false;
	}


	private static void craftSeaSinger(Player player, SeaSingerItem item) {
		if (player.getSkills().getLevel(Skills.RUNECRAFTING) < 90) {
			player.sendMessage("You need a runecrafting level of 90 to make this.");
			return;
		}
		if(player.getPorts().chiGlobe  < item.amount){
			player.sm("You don't have enough lacquer to make this, you need "+item.amount+".");
			return;
		}
		player.setNextAnimation(ANIMATION);
		player.getSkills().addXp(Skills.RUNECRAFTING, item.exp);
		player.getPorts().chiGlobe -= item.amount;
		player.getInventory().addItem(new Item(item.id));
	}

	public enum SeaSingerItem{
		HELM(26449,30,10000),
		BODY(26500,80,30000),
		LEGS(26501,50,20000);
		int id,amount,exp;
		
		private SeaSingerItem(int id, int am, int exp){
			this.id = id;
			this.amount = am;
			this.exp = exp;
			
		}
	}
}