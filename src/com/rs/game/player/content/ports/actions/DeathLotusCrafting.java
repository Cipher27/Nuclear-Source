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
public class DeathLotusCrafting {

	public static final Animation BLOWING_ANIMATION = new Animation(1);

	public static boolean canCraft(Player player, int itemId) {
		for (DeathLotusItem item : DeathLotusItem.values()) {
			if (item.id == itemId) {
				craftDeathLotus(player, item);
				return true;
			}
		}
		return false;
	}


	private static void craftDeathLotus(Player player, DeathLotusItem item) {
		if (player.getSkills().getLevel(Skills.SMITHING) < 90) {
			player.sendMessage("You need a smithing level of 90 to make this.");
			return;
		}
		if(player.getPorts().lacquer < item.amount){
			player.sm("You don't have enough lacquer to make this, you need "+item.amount+".");
			return;
		}
		player.setNextAnimation(BLOWING_ANIMATION);
		player.getSkills().addXp(Skills.CRAFTING, item.exp);
		player.getPorts().lacquer -= item.amount;
		player.getInventory().addItem(new Item(item.id));
	}

	public enum DeathLotusItem{
		HELM(26346,30,10000),
		BODY(26347,80,30000),
		LEGS(26348,50,20000);
		int id,amount,exp;
		
		private DeathLotusItem(int id, int am, int exp){
			this.id = id;
			this.amount = am;
			this.exp = exp;
			
		}
	}
}