package com.rs.game.player.content.wildyrework;

import com.rs.game.player.Player;

public class WildyAchievements {

	public void sendInterface(Player player){
	player.getInterfaceManager().sendInterface(275);
	player.getPackets().sendIComponentText(275, 1, "Wilderness Achievements");
	player.getPackets().sendIComponentText(275, 10, "");
	player.getPackets().sendIComponentText(275, 11, "Repair all the broken portals in the wilderness");
	player.getPackets().sendIComponentText(275, 12, "Reward: Use the wilderness teleports things.");
	player.getPackets().sendIComponentText(275, 13, "");
	player.getPackets().sendIComponentText(275, 14, "Shred 200 lava sharks");
	player.getPackets().sendIComponentText(275, 15, "Reward: Every monster will have a chance on dropping a statue");
    player.getPackets().sendIComponentText(275, 16, "");
    player.getPackets().sendIComponentText(275, 17, "Pray at the Zamorak altar 150mil in your inventory");
	player.getPackets().sendIComponentText(275, 18, "Reward: Use home teleport at level 30 instead of 15");
	player.getPackets().sendIComponentText(275, 19, "");
	player.getPackets().sendIComponentText(275, 20, "Kill 100 revenant orks");
	player.getPackets().sendIComponentText(275, 21, "Reward: Revenants now have a chance of dropping brawling gloves");
	}
	
}