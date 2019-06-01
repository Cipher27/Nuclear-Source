package com.rs.game.player.dialogues;

import com.rs.game.player.content.RecipesHandler;

public class PerkManagerD extends Dialogue {

    private int npcId = 20397;

    @Override
    public void start() {
	sendNPCDialogue(npcId, NORMAL, "Hello stranger, can I help you ?"); 
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	sendOptionsDialogue("Pick an option",
		"What do you offer me?",
		"Show me the perks.",
		"Show me the recipes",
		"How can I convert my items ?",
		"Nothing");
	stage =1;
	} else if (stage == 1) {
	if (componentId == OPTION_1) {
	sendNPCDialogue(npcId, NORMAL, "I can offer you some in game benefits, which you can buy with perk points."); 	
	stage = 0;
	}else if (componentId == OPTION_2){
		sendOptionsDialogue("Pick an option", "Skilling perks", "Combat perks");
		stage =2;
	}else if (componentId == OPTION_3){
	RecipesHandler.sendInterface(player);
	end();
	}else if (componentId == OPTION_4){
		sendNPCDialogue(npcId, NORMAL, "Simpely by using your items at me."); 	
	stage = 0;
	} else
		end();
	} else if (stage == 2){
		if (componentId == OPTION_1){
			player.getPerkHandler().sendSkillingPerks();
			end();
		} else {
			player.getPerkHandler().sendCombatPerks();
			end();
		}
	}
	
    }

    @Override
    public void finish() {

    }
}
