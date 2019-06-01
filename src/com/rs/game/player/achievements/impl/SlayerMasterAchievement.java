package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class SlayerMasterAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468456336022909571L;

	public SlayerMasterAchievement() {
		super(Types.HARD, "Slayer Master","Complete a total of @TOTAL@ slayer tasks.");
	}

	@Override
	public String getRewardInfo() {
		return "1x Achievement Points & 750K";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,50000000)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, new Item(995, 50000000));
		
	}

	@Override
	public int getTotalAmount() {
		return 250;
	}

	@Override
	public String getKey() {
		return "slayer_tasks_hard";
	}


}
