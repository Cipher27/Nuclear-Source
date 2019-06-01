package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
/**
 * 
 * @author Paolo
 *
 */
public class MusicHandler {
     
	
	
	public static void WelcomeInterface(Player player) {
	            player.getInterfaceManager().sendInterface(1312);
				player.getPackets().sendIComponentText(1312, 27, "");
				player.getPackets().sendIComponentText(1312, 38, "");
				player.getPackets().sendIComponentText(1312, 46, "");
				player.getPackets().sendIComponentText(1312, 62, "");
				player.getPackets().sendIComponentText(1312, 54, "");
				player.getPackets().sendIComponentText(1312, 70, "");
				player.getPackets().sendIComponentText(1312, 78, "");
				player.getPackets().sendIComponentText(1312, 86, "");
				player.getPackets().sendIComponentText(1312, 94, "");
				player.getPackets().sendIComponentText(1312, 102, "");
				}
	
	 public static void handleButtons(Player player, int componentId) {
		  if (componentId == 35) {	
		       
		        }
		  if (componentId == 43) {	
		       
		        }
		  if (componentId == 51) {	
		      
		        }
	        if (componentId == 59) {	
	        	
	        }
		if (componentId == 67) {	
	        	
			}
			if (componentId == 75) {	
	        	
	        }
			if (componentId == 83) {	
	        	
	        }
	   if (componentId == 91) {	
	        	
	        }
			if (componentId == 99) {	
	        	
	        }
	 }

}