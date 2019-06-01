package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class Shop extends Dialogue {
	
	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Good day " + player.getUsername() + ". I found quite some items on my journey, would you like to buy some of them?");
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
		sendOptionsDialogue("What Shop would you like to open?", "melee Stuff", "Ranged stuff", "Mage stuff","soul fragment shop","Nothing");
		stage = 2;
		} else if (stage == 2) {
		if(componentId == OPTION_1) {
			sendOptionsDialogue("What Shop would you like to open?", "Melee weapons", "Melee armour", "Back.");
			stage = 3;
		}
		if(componentId == OPTION_2) {
			sendOptionsDialogue("What Shop would you like to open?", "Range weapons", "Range ammo", "Back.");
			stage = 4;
		}
		if(componentId == OPTION_3) {
			sendOptionsDialogue("What Shop would you like to open?", "Talismans", "Mage equipment", "Mage supplies.","Back.");
			stage = 5;
		}
		if(componentId == OPTION_4) {
		ShopsHandler.openShop(player, 82);
		player.sm("<img=7>You can get soul fragment from killing revenants.");
		end();	
		}
		if(componentId == OPTION_5) {
		end();	
		}
		} else if (stage == 3) {
		if(componentId == OPTION_1) {
		ShopsHandler.openShop(player, 64);
		end();
		}if(componentId == OPTION_2) {
		ShopsHandler.openShop(player, 63);
		end();
		}
	  } else if (stage == 4) {
		if(componentId == OPTION_1) {
		ShopsHandler.openShop(player, 61);
		end();
		}if(componentId == OPTION_2) {
		ShopsHandler.openShop(player, 62);
		end();
		}
	  }
	  else if (stage == 5) {
		if(componentId == OPTION_1) {
		ShopsHandler.openShop(player, 59);
		end();
		}if(componentId == OPTION_2) {
		ShopsHandler.openShop(player, 60);
		end();
		}
		if(componentId == OPTION_3) {
		ShopsHandler.openShop(player, 76);
		end();
		}
	  }
	}

	@Override
	public void finish() {

	}

}