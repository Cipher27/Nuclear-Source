package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Colors;
import com.rs.utils.Utils;


/**
  *
  @Paolo
  player who are online will receive a present with random rewards every hour. #credits to a rust server, 4got the name :(
  **/

public class PresentsManager {
	
	
	public static void givePresents(){
		for (Player player : World.getPlayers()) {
			player.getBank().addItem(6542, 1,true);
			player.sm(Colors.red+"A present has been added to your bank.");
		}
	}
   public static void Presents(Player player) {
        int random[][] = {
		{386, 100},
		{12163, 50},
		{12160, 80},
		{12159, 100},
		{29312, 1},
		{29309, 1},
		{31088, 1},
		{29299, 1},
		{2615, 1},
		{2617, 1},
		{2613, 1},
		{3025, 80},
		{2591,1},
		{2653,1},
		{5304,5},
		{5316,1},
		{2655,1},
		{2659,1},
		{3478,1},
		{1514,50},
		{535,30},
	    {987,2},
	    {985,2},
	    {989,3},
		{537,20},
		{2657,1}, 
		{2593,1},
		{2595,1},
		{3479,1},
		{2661,1},
		{2663,1},
		{2665,1},
		{2667,1},
		{995, 150000},
		{995, 500000},
		{995, 200000},
		{995, 100000}};
        int rand = Utils.random(random.length);
		int amount = Utils.random(200,400);
		player.getInventory().deleteItem(6542,1);
		int item = random[rand][0];
        player.getInventory().addItem(item,random[rand][1]);
		player.getPointsManager().setPowerTokens(player.getPointsManager().getPowerTokens() + amount );
		player.sm("You opened your present and received a "+ItemDefinitions.getItemDefinitions(item).getName()+" and also received "+amount+" power tokens.");
   }
	
      
	  
	
	


}