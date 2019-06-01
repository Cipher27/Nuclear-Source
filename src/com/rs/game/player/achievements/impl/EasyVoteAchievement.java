package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;


public class EasyVoteAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7256341000159855147L;

	public EasyVoteAchievement() {
		super(Types.EASY, "Voter","Vote @TOTAL@ Times.");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,750000)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, getRewards());
	}

	@Override
	public int getTotalAmount() {
		return 2;
	}

	@Override
	public String getKey() {
		return "votes";
	}


}
