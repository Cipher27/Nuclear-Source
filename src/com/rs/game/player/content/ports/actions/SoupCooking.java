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
public class SoupCooking {

	public static final Animation ANIMATION = new Animation(1);

	public static void craftDeathLotus(Player player) {
		if (player.getSkills().getLevel(Skills.COOKING) < 93) {
			player.sendMessage("You need a cooking level of 93 to make this.");
			return;
		}
		if(player.getPorts().spice < 1){
			player.sm("You don't have enough spices to cook this.");
			return;
		}
		if(!player.getInventory().contains(15272)){
			player.sm("You need a rocktail to cook this.");
			return;
		}
		player.setNextAnimation(ANIMATION);
		player.getSkills().addXp(Skills.COOKING, 525);
		player.getPorts().spice--;
		player.getInventory().addItem(new Item(26313));
	}

}