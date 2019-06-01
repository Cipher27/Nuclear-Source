package com.rs.game.player.dialogues.impl.donator;

import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author paolo
 *
 */
public class BondD extends Dialogue {


	@Override
	public void start() {
		if(!player.isDonator()){
			sendOptions("Select an item", 
					"Regular donator (1)",
					"Extreme donator (2)",
					"Legendary donator (5)",
					"Divine donator (10)");
			stage = 1;
		} else {
			sendOptions("Select an option", "Rangs", "Misc");
			stage = 2;
		}
	}

	@Override
	public void run(int interfaceId, int option) {
		if(stage == 100)
			end();
		else if(stage == 1){
			if(option == OPTION_1){
			if(player.getInventory().containsItem(29492,1)){
				player.getInventory().deleteItem(new Item(29492,1));
				player.setDonator(true);
				player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 10);
				player.getDialogueManager().startDialogue("SimpleMessage", "You've succesfully claimed your regular donator status.");
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need 1 bond for this action, you can buy bonds at the webstore.");
				
				}
			} else if(option == OPTION_2){
				if(player.getInventory().containsItem(29492,2)){
					player.getInventory().deleteItem(new Item(29492,2));
					player.setExtremeDonator(true);
					player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 20);
					player.getDialogueManager().startDialogue("SimpleMessage", "You've succesfully claimed your Extreme donator status.");
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "You need 2 bonds for this action, you can buy bonds at the webstore.");
					
					}
				} else if(option == OPTION_3){
					if(player.getInventory().containsItem(29492,5)){
						player.getInventory().deleteItem(new Item(29492,5));
						player.setExtremeDonator(true);
						player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 50);
						player.getDialogueManager().startDialogue("SimpleMessage", "You've succesfully claimed your Legendary donator status.");
					} else {
						player.getDialogueManager().startDialogue("SimpleMessage", "You need 5 bonds for this action, you can buy bonds at the webstore.");
						
						}
					}
		}
	}
	

	@Override
	public void finish() {

	}

}
