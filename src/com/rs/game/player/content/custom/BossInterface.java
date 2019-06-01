package com.rs.game.player.content.custom;

import com.rs.game.player.Player;

/**
 * 
 * @author Paolo
 *
 */
public class BossInterface {
     
	
	public static int Inter = 3013;
    /**
	  * Use this methode to call up the interface
	  **/
	public static void sendInterface(Player player) {
			player.getInterfaceManager().sendInterface(Inter);
			player.getPackets().sendIComponentText(3013,26, "<u>Boss Information.</u>");		 
			player.getPackets().sendIComponentSprite(3013,1, 13919); 
			player.getPackets().sendIComponentSprite(3013,2, 13920); 
			player.getPackets().sendIComponentSprite(3013,3, 13921); 
			player.getPackets().sendIComponentSprite(3013,4, 13922); 
			player.getPackets().sendIComponentSprite(3013,5, 13923); 
			player.getPackets().sendIComponentSprite(3013,6, 13924); 
			player.getPackets().sendIComponentSprite(3013,7, 13925); 
			player.getPackets().sendIComponentSprite(3013,8, 13926); 
			player.getPackets().sendIComponentSprite(3013,9, 13927); 
			player.getPackets().sendIComponentSprite(3013,9, 13928); 
			player.getPackets().sendIComponentSprite(3013,9, 13929); 
	}
	/**
	  * Npc information
	  **/
	//zamorak
	public static void zamorakInfo(Player player) {

	
	}
	//armadyl
	public static void armadylInfo(Player player) {
	
	
	}
	//bandos
	public static void bandosInfo(Player player) {
    
	}
	/**
	  * Handler the buttons
	  **/
	 public static void handleButtons(Player player, int componentId) {
		  if (componentId == 1) {	
		   bandosInfo(player);
		        }
		  if (componentId == 2) {	
		   zamorakInfo(player);
		        }
		  if (componentId == 3) {	
         
		        }
	        if (componentId == 4) {	
	       armadylInfo(player);
	        }
			if (componentId == 5) {	
	        
	        }
			if (componentId == 6) {	
	       
	        }
			if (componentId == 7) {	
	        
	        }
			if (componentId == 8) {	

	        }
			if (componentId == 9) {	
	       
	        }
			if (componentId == 10) {	
	      
	        }
			if (componentId == 11) {	
	      
	        }
			if (componentId == 12) {	
	       
	        }
			if (componentId == 13) {	
	      
	        }
			if (componentId == 14) {	
	       
	        }
			if (componentId == 15) {	
	        
	        }
			if (componentId == 16) {	
	        	player.sm("Not done sorry.");
	        }
			if (componentId == 17) {	
	       
	        }
			if (componentId == 18) {	
	        
	        }
			if (componentId == 19) {	
	        	player.sm("Not done.");
	        }
			if (componentId == 20) {	
	        
	        }
			if (componentId == 21) {	
	      
	        }
			if (componentId == 22) {	
			
	        }
			if (componentId == 24) {	
	        
	        }
			if (componentId == 25) {	
	        player.closeInterfaces();	
	        }
			
			
	 }

}