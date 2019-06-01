package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class FarmingDummy extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468456336022909573L;

	public FarmingDummy() {
		super(Types.MEDIUM, "Farming dummy","Grow @TOTAL@ random trees.");
	}

	@Override
	public String getRewardInfo() {
		return "3x Achievement Points & 750K";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,750000), new Item(7409,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, new Item(995, 750000),new Item(7409,1));
		
	}

	@Override
	public int getTotalAmount() {
		return 10;
	}

	@Override
	public String getKey() {
		return "random_trees_grown";
	}


}
