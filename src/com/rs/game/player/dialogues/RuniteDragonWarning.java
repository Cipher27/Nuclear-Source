package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;


public class RuniteDragonWarning extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Warning!", "Teleport me to runite dragons (high wildy level)", "Don't teleport");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3038,3847, 0));
		player.getControlerManager().startControler("Wilderness");
            player.closeInterfaces();  
		} else if(componentId == OPTION_2) {
			end();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}