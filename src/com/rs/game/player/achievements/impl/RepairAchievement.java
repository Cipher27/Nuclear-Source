package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;


public class RepairAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6422341690159855147L;

	public RepairAchievement() {
		super(Types.ELITE, "The Repair","Repair a total of @TOTAL@ nex items.");
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
		return 3;
	}

	@Override
	public String getKey() {
		return "nex_repair";
	}


}
