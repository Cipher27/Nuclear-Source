package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;


public class SlayerHelmAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6426341050159855147L;

	public SlayerHelmAchievement() {
		super(Types.HARD, "Mask up","Upgrade your slayer helm to the max level @TOTAL@.");
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
		return "slayerhelm_upgrades";
	}


}
