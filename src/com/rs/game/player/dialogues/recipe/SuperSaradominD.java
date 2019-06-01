package com.rs.game.player.dialogues.recipe;

import com.rs.game.player.dialogues.Dialogue;

public class SuperSaradominD extends Dialogue {
	
	@Override
	public void start() {
		sendOptionsDialogue("Would you like to learn this recipe?",
				"Yes", "No.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
		if(player.getInventory().containsItem(14464,1)){
			player.getInventory().deleteItem(14464,1);
			player.sm("You can now make super saradomin brews by combining a saradomin brew (3) with saradomin wine.");
			//player.canMakeSuperSaradominBrew = true;
			end();
		}
		} else
			end();
	}

	@Override
	public void finish() {

	}

}
