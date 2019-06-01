package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class ExpertCapeAchievement extends Achievement {
	private static final long serialVersionUID = 2943510495124404945L;

	public ExpertCapeAchievement() {
		super(Types.ELITE, "Advanced Player","Claim a total of @TOTAL@ different expert capes");
	}

	@Override
	public String getRewardInfo() {
		return "";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(24,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, new Item(24,1));
	}

	@Override
	public int getTotalAmount() {
		return 4;
	}

	@Override
	public String getKey() {
		return "expert_cape";
	}


}
