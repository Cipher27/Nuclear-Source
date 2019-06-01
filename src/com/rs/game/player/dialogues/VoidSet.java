package com.rs.game.player.dialogues;

import com.rs.game.World;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public class VoidSet extends Dialogue {

	
	@Override
	public void start() {
		player.lock();
	sendNPCDialogue(3801, 9843, "You even defeated the Pest Queen, I am most impressed! Please accept this gift as a reward.");
	stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;	
			sendOptionsDialogue("Choose a style", "Melee void",
					"Mage void",
					"Range void",
					"I want nothing.");
		break;
		case 0:
			if(componentId == OPTION_1) {
				if (Utils.random(5) == 1) {
			World.sendWorldMessage("<col=ff8c38><img=7>News: "+ player.getDisplayName() + " has just recieved a void mace from Pest Invastion!"+ "</col> ", false);
			player.getBank().addItem(8841 , 1, true);
			player.sm("You recieved a rare staff!");
				}
			player.getBank().addItem(8839 , 1, true);
			player.getBank().addItem(8840 , 1, true);
			player.getBank().addItem(8842, 1, true);
			player.getBank().addItem(11665 , 1, true);
			player.sm("The set is sent to your bank.");
			end();
			player.unlock();
			}else if (componentId == OPTION_2) {
			if (Utils.random(5) == 1) {
			World.sendWorldMessage("<col=ff8c38><img=7>News: "+ player.getDisplayName() + " has just recieved a void mace from Pest Invastion!"+ "</col> ", false);
			player.getBank().addItem(8841 , 1, true);
			player.sm("You recieved a rare staff!");
				}
			player.getBank().addItem(8839 , 1, true);
			player.getBank().addItem(8840 , 1, true);
			player.getBank().addItem(8842, 1, true);
			player.getBank().addItem(11663 , 1, true);
			player.sm("The set is sent to your bank.");
			end();
			player.unlock();
			}else if (componentId == OPTION_3) {
			if (Utils.random(5) == 1) {
			player.getBank().addItem(8841 , 1, true);
			World.sendWorldMessage("<col=ff8c38><img=7>News: "+ player.getDisplayName() + " has just recieved a void mace from Pest Invastion!"+ "</col> ", false);
			player.sm("You recieved a rare staff!");
				}
			player.getBank().addItem(8839 , 1, true);
			player.getBank().addItem(8840 , 1, true);
			player.getBank().addItem(8842, 1, true);
			player.getBank().addItem(11664 , 1, true);
			player.sm("The set is sent to your bank.");
			end();
			player.unlock();
		}else if (componentId == OPTION_4){
		end();
		}
		break;
		case 1:
		sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What can I do with these tokens?",
					"How can I obtain them ?",
					"Which items can I upgrade ?",
					"Open the store.",
					"Nothing.");
		stage = 0;
	     break;
		case 2:
		if(componentId == OPTION_1) {
		ShopsHandler.openShop(player, 211);
		end();
		} else if (componentId == OPTION_2) {
	    ShopsHandler.openShop(player, 212);
		end();
		} else if (componentId == OPTION_2) {
		end();
		}
	     break;
		default:
			end();
		break;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
