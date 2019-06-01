package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class PyromanicAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468456336022909581L;

	public PyromanicAchievement() {
		super(Types.HARD, "Pyromanic","Burn a total of @TOTAL@ random logs.");
	}

	@Override
	public String getRewardInfo() {
		return "1x Achievement Points & 750K";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,750000), new Item(13661,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, new Item(995, 750000),new Item(13661,1));
		
	}

	@Override
	public int getTotalAmount() {
		return 2500;
	}

	@Override
	public String getKey() {
		return "logs_burned";
	}


}
