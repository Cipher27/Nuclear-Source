package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class LumberJackAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468456336022909575L;

	public LumberJackAchievement() {
		super(Types.ELITE, "Lumber Jack","Cut a total of @TOTAL@ Ivy's.");
	}

	@Override
	public String getRewardInfo() {
		return "1x Achievement Points & 750K";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(10933,1),new Item(10939,1),new Item(10940,1),new Item(10941,1)};
		return new Item[] { new Item(10933,1),new Item(10939,1),new Item(10940,1),new Item(10941,1)};
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player,new Item(10933,1),new Item(10939,1),new Item(10940,1),new Item(10941,1));
		
	}

	@Override
	public int getTotalAmount() {
		return 2000;
	}

	@Override
	public String getKey() {
		return "ivys_cut";
	}


}
