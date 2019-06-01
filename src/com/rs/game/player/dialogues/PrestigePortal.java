package com.rs.game.player.dialogues;



public class PrestigePortal extends Dialogue {

    private int npcId = 19455;

    @Override
    public void start() {
	sendPlayerDialogue(NORMAL, "Hello, can I get in ?"); 
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	sendNPCDialogue(npcId, NORMAL, "This room is only for the prestige players, I'm sorry but you are not allowed.");
   stage = 1;	
	}
	else if (stage == 1) {
		sendOptionsDialogue("Pick an option",
		"Leave.",
		"Ask for more information.");
			stage = 2;
	}
	else if (stage == 2) {
	if (componentId == OPTION_1) {
		end();
	}  else {
		player.getDialogueManager().startDialogue("PrestigeOne");
	}
	}
	
    }

    @Override
    public void finish() {

    }
}
