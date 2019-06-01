package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;
/**
 * 
 * @author paolo
 *
 */
public class TheRealFishermanAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468456331022909598L;

	public TheRealFishermanAchievement() {
		super(Types.ELITE, "The Real Fisherman","Fish a total of @TOTAL@ random fishes.");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(24427,1),new Item(24428,1),new Item(24429,1),new Item(24430,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player,new Item(24427,1),new Item(24428,1),new Item(24429,1),new Item(24430,1));
		
	}

	@Override
	public int getTotalAmount() {
		return 2000;
	}

	@Override
	public String getKey() {
		return "random_fishes";
	}


}
