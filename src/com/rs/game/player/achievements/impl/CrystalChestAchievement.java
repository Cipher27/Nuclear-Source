package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;


public class CrystalChestAchievement extends Achievement {
	private static final long serialVersionUID = 2943500495124404945L;

	public CrystalChestAchievement() {
		super(Types.EASY, "Chest lover","Open the crystal chest @TOTAL@ times");
	}

	@Override
	public String getRewardInfo() {
		return "3x Achievement Points & 750K";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,750000)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, new Item(995, 750000));
	}

	@Override
	public int getTotalAmount() {
		return 20;
	}

	@Override
	public String getKey() {
		return "crystal_chest_open";
	}


}
