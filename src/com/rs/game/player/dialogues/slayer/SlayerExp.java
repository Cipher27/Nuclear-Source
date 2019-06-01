package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;



public class SlayerExp extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Would you like to buy slayer exp?", 
	               "yes, please.",
					"No thanks!");
					stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= 200) {
				player.getSkills().addXp(Skills.SLAYER, 50000);
				player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -200);
                player.getPackets().sendGameMessage("You bought slayer exp !");
				end();
				 } else {
				player.getPackets().sendGameMessage("You need at least 200 points to buy this!");
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
