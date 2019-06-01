package com.rs.game.player.dialogues;



public class homeJunglPortal extends Dialogue {

    private int npcId = 13932;

    @Override
    public void start() {
	sendOptionsDialogue("Select a Option", "Go through.",
	                "Ask the guard if He has seen something.",
					"Nothing.");
	stage =1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	sendOptionsDialogue("Select a Option", "Tell me more.",
					"No thanks!");
					stage = 1;
	}
	else if (stage == 1) {
		if (componentId == OPTION_1) {
			sendNPCDialogue(npcId, NORMAL, "Sorry you are not allowed to exit the colony."); 
	stage = 3;    
			}	else if (componentId == OPTION_2) {
			 sendNPCDialogue(npcId, NORMAL, "Well there was a girl here a couple days ago, who wanted to go through this.");
             stage = 2;
			}else if (componentId == OPTION_3) {
			end();
			}
	}
	else if (stage == 2) {
	sendPlayerDialogue(NORMAL, "Did you let her go ??"); 
	stage = 4;
	}
	else if (stage == 3) {
	end();
	}
	else if (stage == 4){
	sendNPCDialogue(npcId, NORMAL, "Ofcours not, but she was verry mad. She said something about her teddy bear which was lost and she had to find it.");
	stage = 5;
	}else if (stage == 5){
	sendPlayerDialogue(NORMAL, "Well thanks for the information."); 
	stage = 3;
	}
    }

    @Override
    public void finish() {

    }
}
