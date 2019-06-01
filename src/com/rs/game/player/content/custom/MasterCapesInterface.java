package com.rs.game.player.content.custom;

import com.rs.game.player.Player;

/**
 * 
 * @author Paolo
 *
 */
public class MasterCapesInterface {
     
	
	public static int Inter = 3007;
    /**
	  * Use this methode to call up the interface
	  **/
	public static void CapesInterface(Player player) {
			player.getInterfaceManager().sendInterface(Inter);
	}
	/**
	  * Handler the buttons
	  **/
	 public static void handleButtons(Player player, int componentId) {
		  if (componentId == 1) 
            player.getDialogueManager().startDialogue("BuyMastercapeD",31289);			  
		 else if (componentId == 2) 
		 player.getDialogueManager().startDialogue("BuyMastercapeD",31269);	  
		 else if (componentId == 3) 	
         player.getDialogueManager().startDialogue("BuyMastercapeD",31268);	
	     else if (componentId == 4) 	
	     player.getDialogueManager().startDialogue("BuyMastercapeD",31270);	
		 else if (componentId == 5) 
	     player.getDialogueManager().startDialogue("BuyMastercapeD",31276);	
		else if (componentId == 6)	
	      player.getDialogueManager().startDialogue("BuyMastercapeD",31272);	
		else if (componentId == 7) 	
	     player.getDialogueManager().startDialogue("BuyMastercapeD",31285);	
		else if (componentId == 8) 	
			player.getDialogueManager().startDialogue("BuyMastercapeD",31292);	
		else	if (componentId == 9) 
	        player.getDialogueManager().startDialogue("BuyMastercapeD",31278);	
			
			else if (componentId == 10) 	
	      player.getDialogueManager().startDialogue("BuyMastercapeD",31271);	
			
			else if (componentId == 11) 	
	        player.getDialogueManager().startDialogue("BuyMastercapeD",31273);	
			
			else if (componentId == 12) 	
	       player.getDialogueManager().startDialogue("BuyMastercapeD",31286);	
			
			else if (componentId == 13) 	
	       player.getDialogueManager().startDialogue("BuyMastercapeD",31288);	
			
			else if (componentId == 14) 	
	        player.getDialogueManager().startDialogue("BuyMastercapeD",31290);	
			
			else if (componentId == 15) 	
	      player.getDialogueManager().startDialogue("BuyMastercapeD",31281);	
			
			else if (componentId == 16) 	
	        player.getDialogueManager().startDialogue("BuyMastercapeD",19709);	
			
			else if (componentId == 17) 	
	        player.getDialogueManager().startDialogue("BuyMastercapeD",31287);	

			else if (componentId == 18) 	
	       player.getDialogueManager().startDialogue("BuyMastercapeD",31291);	
			
			else if (componentId == 19) 	
	       player.getDialogueManager().startDialogue("BuyMastercapeD",31275);	
			
			 else if (componentId == 20) 
	        player.getDialogueManager().startDialogue("BuyMastercapeD",31279);	
			
			else if (componentId == 21) 	
	        player.getDialogueManager().startDialogue("BuyMastercapeD",31274);	
			
			else if (componentId == 22) 	
			player.getDialogueManager().startDialogue("BuyMastercapeD",31283);	
			
			else if (componentId == 23) 
	        player.getDialogueManager().startDialogue("BuyMastercapeD",31282);	
			
			else if (componentId == 24) 	
	       player.getDialogueManager().startDialogue("BuyMastercapeD",31277);	
			else if (componentId == 25) 
	        player.closeInterfaces();	
	        
			
			
	 }

}