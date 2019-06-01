package com.rs.game.player.dialogues.home;

//import com.rs.game.player.content.Trial;
import com.rs.game.player.dialogues.Dialogue;

public class RulesD extends Dialogue {
	
	@Override
	public void start() {
	sendDialogue(" ", "Before you can enter Hellion you need to agree with our rules.");
	stage = -1;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == -1){
		sendOptionsDialogue("Pick an option", "Read the rules", "Accept the rules");
		stage = 0;
	}
	else if (stage == 0) {
			if(componentId == OPTION_1) {
				player.getInterfaceManager().sendRules(player);
				player.readRules = true;
				sendDialogue(" ", "Before you can enter Hellion you need to agree with our rules.");
				stage = 2;
			} else if(componentId == OPTION_2) {
			    if(!player.readRules){
					sendDialogue(" ", "You need to read the rules first before you can accept them.");
					stage = 2;
				} else {
					player.acceptedRules = true;
					player.closeInterfaces();
					player.getDialogueManager().startDialogue("HomeTour");
				}
					
			}
	} else if (stage == 2){
		sendOptionsDialogue("Pick an option", "Read the rules", "Accept the rules");
		stage = 0;
	}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	
}
