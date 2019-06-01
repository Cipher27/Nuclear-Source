package com.rs.game.player.actions.crafting;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.impl.CrystalChestAchievement;
import com.rs.game.player.achievements.impl.GanocraftingAchievement;

/**
 * 
 * @author paolo
 *
 */
public class GanodermicCrafting {

	public static final int FLAKES = 22451;
	public static final Animation CRAFTING_ANIMATION = new Animation(1249);

	public static boolean canCraft(Player player, int itemId) {
		for (GanoItem item : GanoItem.values()) {
			if (item.id == itemId) {
				craftGano(player, item);
				return true;
			}
		}
		return false;
	}


	private static void craftGano(Player player, GanoItem item) {
		if (player.getSkills().getLevel(Skills.CRAFTING) < item.level) {
			player.sendMessage("You need a crafting level of "+item.level+" to make this.");
			return;
		}
		if(player.getInventory().getAmountOf(FLAKES) < item.amount){
			player.sm("You don't have enough Ganodermic flakes to make this, you need "+item.amount+".");
			return;
		}
		if(!player.getInventory().contains(item.id2)){
			player.sm("You need a "+ItemDefinitions.getItemDefinitions(item.id2).getName()+" in order to craft an "+ItemDefinitions.getItemDefinitions(item.id).getName()+".");
			return;
			
		}
		player.setNextAnimation(CRAFTING_ANIMATION);
		player.getSkills().addXp(Skills.CRAFTING, item.exp);
		player.getInventory().deleteItem(new Item(FLAKES,item.amount));
		player.getInventory().deleteItem(new Item(item.id2));
		player.getInventory().addItem(new Item(item.id));
		player.getAchievementManager().notifyUpdate(GanocraftingAchievement.class);
		player.sendMessage("You crafted a "+ItemDefinitions.getItemDefinitions(item.id).getName()+".");
	}

	public enum GanoItem{
		VISOR(22482,22452,500,86,1000),
		PONCHO(22490,22456,5000,98,1000),
		LEGS(22486,22454,1000,92,300);
		int id,id2,amount,exp,level;
		
		private GanoItem(int id,int clothId, int am,int level, int exp){
			this.id = id;
			this.id2 = clothId;
			this.amount = am;
			this.level = level;
			this.exp = exp;
			
		}
	}
}