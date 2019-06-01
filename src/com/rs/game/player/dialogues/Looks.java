package com.rs.game.player.dialogues;

import com.rs.game.player.content.PlayerLook;

/**
 * @author Danny
 * 
 * 
 */


public class Looks extends Dialogue {

	public Looks() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Appearance Options", "Change Hairstyle",
				"Change Clothes", "Change Looks",
				"Nothing");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if(componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				PlayerLook.openHairdresserSalon(player);
			} else if(componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				PlayerLook.openThessaliasMakeOver(player);
			} else if(componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				PlayerLook.openMageMakeOver(player);
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		}
					
		}
	}
			@Override
			public void finish() {
			}
	


}
