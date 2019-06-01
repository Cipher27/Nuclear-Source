package com.rs.game.player.content.custom;

import com.rs.game.player.Player;

/**
 * 
 * @author Paolo
 *
 */
public class ArmourOverride {
     
	
	public static int Inter = 3012;
    /**
	  * Use this methode to call up the interface
	  **/
	public static void CapesInterface(Player player) {
			player.getInterfaceManager().sendInterface(Inter);
	}
	private static void ResetCapes(Player player) {
	
	}
	/**
	  * Handler the buttons
	  **/
	 public static void handleButtons(Player player, int componentId) {
			
	 }

}