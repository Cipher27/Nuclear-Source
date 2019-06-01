package com.rs.game.player.dialogues;



public class NormalMode extends Dialogue {

    private int npcId = 7892;

    @Override
    public void start() {
	sendOptionsDialogue("Select a Option", "Play in normal mode",
					"No thanks!");
					stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	stage++;
	if (stage == 0)
	sendOptionsDialogue("Select a Option", "Tell me more.",
					"No thanks!");
					stage = 1;
	if (stage == 1) {
		if (componentId == OPTION_1) {
			player.unlock();
			player.closeInterfaces();
			player.sm("Talk to the server manager to begin your adventure.");
			end();
			
			}
	
		else if (componentId == OPTION_1) {
			 sendNPCDialogue(npcId, NORMAL, "No thanks!");
			 end();
			}
	}
	else if (stage == 2) {
	sendNPCDialogue(npcId, NORMAL, "Well, the basics of beeing an iron man are: Not able to trade,drop,lootshare,use the ge,not drops at pvp."); 
	stage = 3;
	}
	else if (stage == 3) {
	sendPlayerDialogue(NORMAL, "mhhhhh, are there also adventages about this mode ?");
	}
	else if (stage == 4){
	sendNPCDialogue(npcId, NORMAL, "You get a 1.5x higher droprate and players will see you as a expert player.");
	stage = 5;
	}
	else if (stage == 5){
	sendOptionsDialogue("Would you like to become an iron man?", "Yes I would like to become a iron man.",
					"No, I don''t want to be an iron man.");
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
	
	end();
	}
    }

    @Override
    public void finish() {

    }
}
