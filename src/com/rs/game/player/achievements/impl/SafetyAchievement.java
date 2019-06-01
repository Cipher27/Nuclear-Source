package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;


public class SafetyAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6426341090159855147L;

	public SafetyAchievement() {
		super(Types.EASY, "Safety First","Set-up your recovery questions @TOTAL@.");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,1000000)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, getRewards());
	}

	@Override
	public int getTotalAmount() {
		return 3;
	}

	@Override
	public String getKey() {
		return "recovery_questions";
	}


}
