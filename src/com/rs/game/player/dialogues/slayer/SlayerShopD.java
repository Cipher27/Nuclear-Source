package com.rs.game.player.dialogues.slayer;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.dialogues.Dialogue;



public class SlayerShopD extends Dialogue {
	
	int id;
	int amount;
	ItemDefinitions def;
	

    @Override
    public void start() {
    id = (Integer) parameters[0];
    amount = (Integer) parameters[1];
     def = ItemDefinitions.getItemDefinitions(id);
	sendOptionsDialogue("Would you like to buy a "+def.getName()+"?", 
	               "yes, please.",
					"No thanks!");
					stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 1) {
		if (componentId == OPTION_1) {
			 if (player.getPointsManager().getSlayerPoints() >= amount) {
				player.getInventory().addItem(def.getId(), 1);
				player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() -amount);
                player.getPackets().sendGameMessage("You bought a "+def.getName()+"!");
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
