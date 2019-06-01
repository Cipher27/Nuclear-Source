package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class RedStonerAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468456336022909571L;

	public RedStonerAchievement() {
		super(Types.EASY, "Redstoner","Mine a total of @TOTAL@ redstone blocks.");
	}

	@Override
	public String getRewardInfo() {
		return "1x Achievement Points & 750K";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,750000), new Item(23748,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, new Item(995, 750000),new Item(23748,1));
		player.redStoner = true;
		player.sm("You can now find special ores while mining this rock");
		
	}

	@Override
	public int getTotalAmount() {
		return 120;
	}

	@Override
	public String getKey() {
		return "mined_redstones";
	}


}
