package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.dialogues.Dialogue;



public class SlayerRing extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Would you like to buy a slayer ring?", 
	               "yes, please.",
					"No thanks!");
					stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints()>= 50) {
				player.getInventory().addItem(13281, 1);
				player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -50);
                player.getPackets().sendGameMessage("You bought a slayer ring!");
				end();
				 } else {
				player.getPackets().sendGameMessage("You need at least 50 points to buy one!");
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
