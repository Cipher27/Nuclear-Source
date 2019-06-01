package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;



public class PreHestoricalDoor extends Dialogue {


    @Override
    public void start() {
	sendOptionsDialogue("Pick an option",
		"Enter room.",
		"Check damage increase.",
		"What is this ?"); 
	stage = 2;
    }

    @Override
    public void run(int interfaceId, int componentId) {
 if (stage == 2) {
	if (componentId == OPTION_1) {
	Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3810,4719, 0));
	end();
	}  else if (componentId == OPTION_2) {
		sendDialogue("The Dark Lord his attacks deal  "+player.getPAdamage()+" extra damage against you.");
		stage = 3;
	}else if (componentId == OPTION_3) {
		sendDialogue("this mystical creature is captured and trained by Paolo, his power increases with every kill. The increased damage resets every day.");
	stage = 3;
	}
	} else if(stage == 3)
		end();
	
    }

    @Override
    public void finish() {

    }
}
