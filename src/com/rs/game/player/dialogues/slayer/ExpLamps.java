package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.dialogues.Dialogue;

// editted by TTwerty

public class ExpLamps extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Would you like to buy a exp lamp?", 
	               "Huge (250)",
					"Large (100)");
					stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= 250) {
				player.getInventory().addItem(23781, 1);
				player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -250);
                player.getPackets().sendGameMessage("You bought a huge slayer lamp!");
				end();
				 } else {
				player.getPackets().sendGameMessage("You need at least 250 points to buy one!");
				end();
	       }
		}
	     	else if (componentId == OPTION_2) {
			 if (player.getPointsManager().getSlayerPoints() >= 100) {
				player.getInventory().addItem(23780, 1);
				player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -100);
                player.getPackets().sendGameMessage("You bought a large slayer lamp!");
				end();
				 } else {
				player.getPackets().sendGameMessage("You need atleast 100 points to buy one!");
				end();
	       }
			}
	}
    }
	
    @Override
    public void finish() {

    }
}
