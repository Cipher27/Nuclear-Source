package com.rs.game.player.dialogues.slayer;

import com.rs.game.player.achievements.impl.SlayerHelmAchievement;
import com.rs.game.player.dialogues.Dialogue;



public class SlayerhelmUpgradeD extends Dialogue {

    @Override
    public void start() {
		if (!player.getInventory().containsItem(15492,1)){
		player.sm("You need to have a Full Slayer Helmet before you can upgrade it.");	
		end();
		}else {
	sendOptionsDialogue("Would you like to upgrade your slayer helmet?", 
	               "yes, please. (250 points)",
					"No thanks!");
					stage = 1;
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= 250) {
				if( player.slayerhelmLevel ==1){
					if (player.getInventory().containsItem(15492,1)){
					player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() - 250);	
					player.getInventory().deleteItem(15492,1);
					player.getAchievementManager().notifyUpdate(SlayerHelmAchievement.class);
					player.sm("your slayer helm has been upgraded into a reinforced one.");
					player.getBank().addItem(30656,1,true);
					player.slayerhelmLevel = 2;
			        } else {
						player.sm("You need to put your slayer helmet in your inventory.");
					}
				}else if( player.slayerhelmLevel == 2){
					if (player.getInventory().containsItem(30656,1)){
						player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() - 250);
					player.getInventory().deleteItem(30656,1);
					player.getAchievementManager().notifyUpdate(SlayerHelmAchievement.class);
					player.sm("your reinforced slayer helm has been upgraded into a strong one.");
					player.getBank().addItem(30686,1,true);
					player.slayerhelmLevel = 3;
					}else {
						player.sm("You need to put your reinforced slayer helmet in your inventory.");
					}
				}else if( player.slayerhelmLevel ==3){
					if (player.getInventory().containsItem(30686,1)){
					player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() - 250);
					player.getInventory().deleteItem(30686,1);
					player.getAchievementManager().notifyUpdate(SlayerHelmAchievement.class);
					player.sm("your strong slayer helm has been upgraded into a mighty one.");
					player.getBank().addItem(30722,1,true);
					player.slayerhelmLevel = 4;
					}else {
						player.sm("You need to put your strong slayer helmet in your inventory.");
					}
				}else if( player.slayerhelmLevel ==4){
					player.sm("You have the highest upgrade yet.");
					end();
				}
				} else {
				player.getPackets().sendGameMessage("You need at least 250 points to upgrade this!");
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
