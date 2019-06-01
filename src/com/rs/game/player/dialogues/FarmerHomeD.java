package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

/**
 * @author Danny
 */


public class FarmerHomeD extends Dialogue {


	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Farming Shop", "Farming Tools");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			ShopsHandler.openShop(player, 133);
			player.getInterfaceManager().closeChatBoxInterface();
		} 
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}