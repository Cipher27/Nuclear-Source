package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class SoManyRunesAchievement extends Achievement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468456336022909594L;

	public SoManyRunesAchievement() {
		super(Types.HARD, "So many runes","Craft a total of @TOTAL@ blood runes.");
	}

	@Override
	public String getRewardInfo() {
		return "1x Achievement Points & 750K";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(32347,1),new Item(32348,1),new Item(32349,1),new Item(32350,1),new Item(32351,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, new Item(32347,1),new Item(32348,1),new Item(32349,1),new Item(32350,1),new Item(32351,1));
		
	}

	@Override
	public int getTotalAmount() {
		return 2500;
	}

	@Override
	public String getKey() {
		return "blood_runes_crafter";
	}


}
