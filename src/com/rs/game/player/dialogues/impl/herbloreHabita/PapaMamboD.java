package com.rs.game.player.dialogues.impl.herbloreHabita;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

/**
 * 
 * @author paolo
 *
 */
public class PapaMamboD extends Dialogue {

	int npcId = 3122;

	@Override
	public void start() {
		
		sendNPCDialogue(npcId, 9840, "Boom ba boom me boom!!");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int option) {
		if(stage == 1){
			sendOptions("Select an option", "what is this place?", "Open shop","Private Jadinko Room", "Nothing");
			stage = 2;
		} else if(stage == 2){
			if(option == OPTION_1){
				sendNPCDialogue(npcId, 9840, "This is herblore habitat, the best place to train your hunter/farming and herblore ofcourse.");
				stage = 99;
			} else if(option == OPTION_2){
				ShopsHandler.openShop(player, 223);
				end();
			} else {
				sendNPCDialogue(npcId, 9840, "Will come soon.");
				stage = 99;
			}
		} else if(stage == 99)
			end();
	}
	

	@Override
	public void finish() {

	}

}
