package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

/**
 * @author Danny
 */


public class Orb extends Dialogue {
	

	public Orb() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What would you like to do?", "Save Location (10 charges)", "Teleport to Location (1charge)", "Purchase Charges", "Check Charges", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			player.getInterfaceManager().closeChatBoxInterface();
			if (player.orbCharges > 10) {
				player.orbCharges -= 10;
				player.orbLocation = new WorldTile(player.getTile());
				player.sm("You have successfully set your location.");
			} else {
				player.sm("You do not have enough charges to teleport to save your location.");
			}
		} else if(componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			if (player.orbCharges > 0) {
				if (player.orbLocation == null) {
					player.sm("You currently do not have a location set.");
				} else {
				player.orbCharges--;
				Magic.sendNormalTeleportSpell(player, 0, 0, player.orbLocation);
				player.sm("You teleport to your location.");
				}
			} else {
				player.sm("You do not have enough charges to teleport to this area.");
			}
		} else if(componentId == OPTION_3) {
			sendOptionsDialogue("Pick an amount", "1 charge (100k)", "5 charges (500k)", "10 charges (1mil)");
			stage = 2;
		} else if(componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("You currently have "+player.orbCharges+" charges.");
		} else if(componentId == OPTION_5) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 } else if(stage == 2){
		 if(componentId == OPTION_1) {
			 if(player.getInventory().containsItem(995,100000)){
				 player.getInventory().removeItemMoneyPouch(995,100000);
				 player.orbCharges++;
				 player.sm("You successfully bought it.");
				 end();
			 } else {
				 player.sm("You need 100k to buy this.");
				 end();
			 }
		 } else  if(componentId == OPTION_2) {
			 if(player.getInventory().containsItem(995,500000)){
				 player.getInventory().removeItemMoneyPouch(995,500000);
				 player.sm("You successfully bought it.");
				 player.orbCharges+=5;
				 end();
			 } else {
				 player.sm("You need 500k to buy this.");
				 end();
			 }
		 } else  if(componentId == OPTION_3) {
			 if(player.getInventory().containsItem(995,1000000)){
				 player.getInventory().removeItemMoneyPouch(995,1000000);
				 player.sm("You successfully bought it.");
				 player.orbCharges+=10;
				 end();
			 } else {
				 player.sm("You need 1mil to buy this.");
				 end();
			 }
		 }
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}