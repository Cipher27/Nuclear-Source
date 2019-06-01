package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;


public class EasyDungeoneeringAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6556341000159855147L;

	public EasyDungeoneeringAchievement() {
		super(Types.EASY, "Dungeon explorer","Complete a total of @TOTAL@ dungeon.");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,750000) , new Item(32750,5)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, getRewards());
	}

	@Override
	public int getTotalAmount() {
		return 1;
	}

	@Override
	public String getKey() {
		return "dungeons";
	}


}
