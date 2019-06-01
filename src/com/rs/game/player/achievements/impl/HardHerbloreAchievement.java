package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;
import com.rs.game.player.content.RecipesHandler.Recipes;


public class HardHerbloreAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6426311090159855147L;

	public HardHerbloreAchievement() {
		super(Types.HARD, "Decant Master","Brew a total of @TOTAL@ overloads.");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,1000000), new Item(33198,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		player.getRecipeHandler().recipes.add(Recipes.OVERLOAD_SALVE);
		player.sm("You can now create the Overload Salve, talk to Xenia to find out how.");
		this.addItem(player, getRewards());
	}

	@Override
	public int getTotalAmount() {
		return 250;
	}

	@Override
	public String getKey() {
		return "overloads";
	}


}
