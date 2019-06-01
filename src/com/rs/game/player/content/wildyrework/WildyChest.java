package com.rs.game.player.content.wildyrework;

import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.content.BrawlingGManager.BrawlingGloves;
import com.rs.game.player.content.custom.ActivityHandler;
import com.rs.utils.Utils;

/**
  * Made by ¨Paolo, handles the rewards of the wildy chest
  **/

public class WildyChest {
	
	public static void Chest(Player player, final WorldObject object) {
        int random[][] = 
		{
		{23716, 1},//Huge lamp
		{23715, 2},//large lamp
		{1580, 1}, //ice gloves
		{990,12},
		//{14639,2000},
		{1514,250},
		{450,250},
		{454,1500},
			{995,10000000},
		{15259,1},
		{6585, 1}, //fury
		{19333, 1},//fury orn kit.
		
		
		 
		 
		 };
        int rand = Utils.random(random.length);
		if(Utils.random(10) == 0)
			player.getBGloves().dropItem(BrawlingGloves.values()[Utils.random(BrawlingGloves.values().length)], player);
        player.getInventory().addItem(random[rand][0], random[rand][1]);
		ActivityHandler.setCurrentWildyEvent("The chest has been looted.");
		player.sm("You have looted the chest and received a reward. You also get rewarded with 1000 power tokens.");
		player.getPointsManager().setPowerTokens(player.getPointsManager().getPowerTokens() + 1000);
  
	}
    
	

}
