package com.rs.game.player.dialogues;

public class BuyMastercapeD extends Dialogue {
	
	public static int capeId;

	@Override
	public void start() {
		capeId = (Integer) parameters[0];
		sendOptionsDialogue("Select a Option", "Yes I would like to pay 10mil for this master cape.","No thanks!");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if(player.getInventory().containsItem(995,10000000)) {
				if(player.getInventory().getFreeSlots() > 1){
			player.getInventory().removeItemMoneyPouch(995,10000000);
			player.getInventory().addItem(capeId,1);
			end();
				} else{
					player.sm("You need an empty inventory place in order to buy one.");
					end();
				}
			} else {
			player.sm("You need 10mil to buy this cape.");
			end();
	        }
	  }
		else if (componentId == OPTION_2) {
			end();
			}
	}

	@Override
	public void finish() {

	}

}
