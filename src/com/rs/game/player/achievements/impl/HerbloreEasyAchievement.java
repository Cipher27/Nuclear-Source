package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;


public class HerbloreEasyAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5256341000159855147L;

	public HerbloreEasyAchievement() {
		super(Types.EASY, "Potion maker","Create a total of @TOTAL@ prayer potions.");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,750000) , new Item(212,50)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, getRewards());
	}

	@Override
	public int getTotalAmount() {
		return 100;
	}

	@Override
	public String getKey() {
		return "prayer_pots";
	}


}
