package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;


public class TriskelionKeyAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6422341000159855147L;

	public TriskelionKeyAchievement() {
		super(Types.HARD, "Key looter","Create a total of @TOTAL@ Triskelion keys.");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,10000000)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, getRewards());
	}

	@Override
	public int getTotalAmount() {
		return 10;
	}

	@Override
	public String getKey() {
		return "tris_keys";
	}


}
