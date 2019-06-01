package com.rs.game.player.dialogues.slayer;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.dialogues.Dialogue;



public class SlayerHelmCraftD extends Dialogue {
	
	int id;
	int amount = 300;
	ItemDefinitions def;
	

    @Override
    public void start() {
	sendOptionsDialogue("Would you like to unlock this feature?", 
	               "yes, please.",
					"No thanks!");
					stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= amount) {
				player.canCraftSlayerHelmtet = true;
				player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -amount);
                player.getPackets().sendGameMessage("You can now create slayer helmets.");
				end();
				 } else {
				player.getPackets().sendGameMessage("You need at least "+amount+" points to buy one!");
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
