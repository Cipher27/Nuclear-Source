package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.dialogues.Dialogue;



public class HeartTokens extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Would you like to buy 4500 power tokens?", 
	               "yes, please.",
					"No thanks!");
					stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= 250) {
				player.getPointsManager().setPowerTokens(player.getPointsManager().getPowerTokens() + 4500);
				player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() - 250);
                player.getPackets().sendGameMessage("You bought power tokens");
				end();
				 } else {
				player.getPackets().sendGameMessage("You need at-least 250 points to buy this!");
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
