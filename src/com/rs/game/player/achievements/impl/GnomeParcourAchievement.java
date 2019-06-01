package com.rs.game.player.achievements.impl;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.Achievement;
import com.rs.game.player.achievements.Types;

public class GnomeParcourAchievement extends Achievement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8115398307796521544L;

	public GnomeParcourAchievement() {
		super(Types.EASY, "Gnome Parcour","Do a total of @TOTAL@ normal gnome laps.");
	}

	@Override
	public String getRewardInfo() {
		return "1x Achievement Points & 750K";
	}
	
	@Override
	public Item[] getRewards() {
		Item rewards[] = new Item[] { new Item(995,750000),new Item(4024,1)};
		return rewards;
	}
	
	

	@Override
	public void giveReward(Player player) {
		this.addAchievementPoints(player, 3);
		this.addItem(player, new Item(995, 750000));
		this.addItem(player, new Item(4024,1));
	}

	@Override
	public int getTotalAmount() {
		return 15;
	}

	@Override
	public String getKey() {
		return "gnome_laps";
	}


}
