package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.dialogues.Dialogue;



public class SlayerToolbeltOptiosD extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Select an item you would like to be able to add to your toolbelt", 
	               "Bonecrusher",
					"Gold accumulator",
					"Herbicide",
					"Rock hammer"
					);
					stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= 150) {
				 if(player.canAddBonecrusher){
				 player.getPackets().sendGameMessage("Your already unlocked this.");
				end();	 
				 } else {
                player.canAddBonecrusher = true;
                player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -150);
                player.getPackets().sendGameMessage("You can now add your bonecrusher to your toolbelt.");
				end();
					}
				 } else {
				player.getPackets().sendGameMessage("You need at least 150 points to unlock this!");
				end();
	     	 }
		}
	     	else if (componentId == OPTION_2) {
			 if (player.getPointsManager().getSlayerPoints() >= 150) {
				 if(player.canAddCoinAccumulator){
				 player.getPackets().sendGameMessage("Your already unlocked this.");
				end();	 
				 } else {
                player.canAddCoinAccumulator = true;
                player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -150);
                player.getPackets().sendGameMessage("You can now add your gold accumulator to your toolbelt.");
				end();
					}
				 } else {
				player.getPackets().sendGameMessage("You need at-least 150 points to unlock this!");
				end();
	     	 }
			}
			else if (componentId == OPTION_3) {
			 if (player.getPointsManager().getSlayerPoints() >= 150) {
				 if(player.canAddHerbicide == true){
				 player.getPackets().sendGameMessage("Your already unlocked this.");
				end();	 
				 } else {
                player.canAddHerbicide = true;
                player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -150);
                player.getPackets().sendGameMessage("You can now add your Herbicide to your toolbelt.");
				end();
					}
				 } else {
				player.getPackets().sendGameMessage("You need at-least 150 points to unlock this!");
				end();
	     	 }
			}else if (componentId == OPTION_4) {
			 if (player.getPointsManager().getSlayerPoints() >= 150) {
				 if(player.canAddRockhammer){ 
				 player.getPackets().sendGameMessage("Your already unlocked this.");
				end();	 
				 } else {
                player.canAddRockhammer = true;
                player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -150);
                player.getPackets().sendGameMessage("You can now add your Rock Hammer to your toolbelt.");
				end();
					}
				 } else {
				player.getPackets().sendGameMessage("You need at-least 150 points to unlock this!");
				end();
	     	 }
			}
		}
    }
	
    @Override
    public void finish() {

    }
}
