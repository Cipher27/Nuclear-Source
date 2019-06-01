package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.dialogues.Dialogue;



public class SlayerDropsD extends Dialogue {

    @Override
    public void start() {
		if (player.hasNewSlayerDrops == true){
		player.sm("You have already unlocked this feature.");
		end();	
		} else {
	sendOptionsDialogue("Would you like to unlock new slayer drops?", 
	               "yes, please.",
					"No thanks!");
					stage = 1;
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= 300) {
				 player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -300);
				player.hasNewSlayerDrops = true;
                player.getPackets().sendGameMessage("You unlocked the new items for slayer monsters, these items are only obtainable when killing monsters while in a slayer task.!");
				end();
				 } else {
				player.getPackets().sendGameMessage("You need at least 300 points to unlock this!");
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
