package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class ParcourMasterAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468456336022909576L;

	public ParcourMasterAchievement() {
		super(Types.HARD, "Parcour Master","Do a total of @TOTAL@ random laps.");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] {new Item(14936,1), new Item(14938,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, getRewards());
		
	}

	@Override
	public int getTotalAmount() {
		return 350;
	}

	@Override
	public String getKey() {
		return "random_laps";
	}


}
