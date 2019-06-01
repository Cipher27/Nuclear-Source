package com.rs.game.player.dialogues;



public class SkillerMode extends Dialogue {

    private int npcId =  7892;

    @Override
    public void start() {
	sendNPCDialogue(npcId, NORMAL, "If you don't like pvm this is probably the mode for you!"); 
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	sendOptionsDialogue("Select a Option", "Tell me more.",
					"No thanks!");
					stage = 1;
	} else if (stage == 1) {
		if (componentId == OPTION_1) {
			sendPlayerDialogue(NORMAL, "Ofcours I would like to know more!");
			 stage = 2;
			}
	
		else if (componentId == OPTION_2) {
			 sendNPCDialogue(npcId, NORMAL, "No thanks!");
			 end();
			}
	}
	else if (stage == 2) {
	sendNPCDialogue(npcId, NORMAL, "Well, the basics of being an skiller are that you can't train any combat skill, so no attack,defence,strength,hp,magic,ranged,prayer and summoning."); 
	stage = 3;
	}
	else if (stage == 3) {
	sendPlayerDialogue(NORMAL, "And what do you mean I can't?");
	stage = 4;
	}
	else if (stage == 4){
	sendNPCDialogue(npcId, NORMAL, "So you can attakc npc's and players, but you can't earn exp in those combat skills.");
	stage = 5;
	}
	else if (stage == 5){
	sendOptionsDialogue("Would you like to become a skiller?", "Yes I would like to become a skiller.",
					"No, I don't want to be a skiller.");
	stage = 6;
	}
	else if (stage == 6){
		if (componentId == OPTION_1) {
	sendNPCDialogue(npcId, NORMAL, "Nice to see that you are interested!");
	stage = 7;
	} else if (componentId == OPTION_2) {
		end ();
	}
	}
	else if (stage == 7){
	player.isSkiller = true;
	player.unlock();
	player.closeInterfaces();
	player.sm("Talk to the server manager to begin your adventure.");
	end();
	}
    }

    @Override
    public void finish() {

    }
}
