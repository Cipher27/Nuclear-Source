package com.rs.game.player.content.ports.actions;

import com.rs.game.player.Player;

/**
 * 
 * @author paolo
 *
 */
public class BlackMarket {
	
   private static int PRICE = 200;
   /**
    * checks if the player has already cliamed daily market
    * @param player
    * @return returns true if claimed
    */
   public static boolean checkClaimed(Player player){
	   	 //  if(player.getPorts().claimedDailyMarket)
		//   return true;
	   //else 
		   return false;
   }
   
   public static void setDailyClaim(Player player){
	   
   }
}