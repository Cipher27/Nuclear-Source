package com.rs.game.player.dialogues;

import com.rs.game.player.content.perks.Perks.Perk;

public class PerkOptions extends Dialogue {

    private Perk perk;
    private int npcId = 20397;
    @Override
    public void start() {
	perk = (Perk) parameters[0];
	if(player.getPerkHandler().perks.contains(perk)){ 
		player.sm("You've already bought this perk.");
		end();
	} else {
	sendOptionsDialogue("Pick an option : "+perk.getName(),
		"More info",
		"Buy",
		"Nothing");
	stage = 1;
	}
    }

    @Override
    public void run(int interfaceId, int componentId) {
	 if (stage == 1) {
	if (componentId == OPTION_1) {
	sendNPCDialogue(npcId, NORMAL, ""+perk.getDescription()); 	
	stage = 0;
	}else if (componentId == OPTION_2){
		sendOptionsDialogue("Pick an option", "Are you sure you want to buy  the "+perk.getName()+" perk for "+perk.getPrice()+" perk points?", "Nothing");
		stage =2;
	}else if (componentId == OPTION_3)
	end();
	}  else if (stage == 0){
		sendOptionsDialogue("Pick an option"+perk.getName(),
				"More info",
				"Buy",
				"Nothing");
			stage = 1;
	} else if (stage == 2){
		if (componentId == OPTION_1){
			if(player.getPointsManager().getPerkPoints() >= perk.getPrice()){
				player.getPointsManager().setPerkPoints(player.getPointsManager().getPerkPoints() - perk.getPrice());
				player.getPerkHandler().perks.add(perk);
			    player.sm("You have succesfully bought the: " +perk.getName()+" perk.");
				end();
			} else {
				player.sm("You don't have enought points to buy this.");
				end();
			}
		} else {
			end();
		}
	}
	
    }

    @Override
    public void finish() {

    }
}
