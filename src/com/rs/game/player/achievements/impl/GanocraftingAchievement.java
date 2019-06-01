package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class GanocraftingAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7428456336022909572L;

	public GanocraftingAchievement() {
		super(Types.MEDIUM, "Crafting Dummy","Create a total of @TOTAL@ Ganodermic Armor");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] {new Item(22494,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player,new Item(22494,1));
		
	}

	@Override
	public int getTotalAmount() {
		return 1;
	}

	@Override
	public String getKey() {
		return "gano_create";
	}


}
