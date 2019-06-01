package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.dialogues.Dialogue;



public class SlayerTabsD extends Dialogue {

    @Override
    public void start() {
		if (player.hasSlayerTabs == true){
		player.sm("You have already unlocked this feature. Use a slayer gem on a chisel to create a teleport tablet.");
		end();	
		} else {
	sendOptionsDialogue("Would you like to unlock new slayer drops?", 
	               "Yes, please.",
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
				player.hasSlayerTabs = true;
                player.getPackets().sendGameMessage("Use a slayer gem on a chisel to create a slayer teleport tablet.");
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
