package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.dialogues.Dialogue;



public class EliteDungeonD extends Dialogue {
	

    @Override
    public void start() {
		if (player.eliteDungeon){
		player.sm("You have already unlocked this feature.");
		end();	
		} else {
	sendOptionsDialogue("Would you like to unlock the new creatures?", 
	               "yes, please.",
					"No thanks!");
					stage = 1;
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= 400) {
				 player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -400);
				player.eliteDungeon = true;
                player.getPackets().sendGameMessage("Succesfully unlocked");
				end();
				 } else {
				player.getPackets().sendGameMessage("You need at-least 400 points to unlock this!");
				end();
	       }
		}
	     	else if (componentId == OPTION_2) {
			 sendPlayerDialogue(NORMAL, "No thanks!");
			 end();
			}
	}
    }
	
    @Override
    public void finish() {

    }
}
