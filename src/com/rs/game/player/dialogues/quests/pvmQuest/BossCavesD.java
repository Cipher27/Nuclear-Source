package com.rs.game.player.dialogues.quests.pvmQuest;

import com.rs.game.player.controlers.BossCaves;
import com.rs.game.player.dialogues.Dialogue;

public class BossCavesD extends Dialogue {

	
	@Override
	public void start() {
	sendNPCDialogue(14945, 9843,"Well I do have something special for you, but it's a lot harder than my previous task.");
	stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;	
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
        			"Tell me more about it.",
					"What are the rewards?",
					"What would you recommend ?",
					"Bring me there!",
					"Nothing");
		break;
		case 0:
			if(componentId == OPTION_1) {
			sendNPCDialogue(943, 9843, "You know the Fightcaves right? Me and my soldiers found a cave like that, but it's not an ordinary cave with some easy kiln creatures.");	
			stage = 1;
			}else if (componentId == OPTION_2) {
			sendNPCDialogue(943, 9843, "You get a ton of heart tokens, a tittle, 5spins and you can choose between sea singer/tetsu or death lotus armour set.");	
			stage = 9;
			}else if (componentId == OPTION_3) {
			sendNPCDialogue(943, 9843, "I would recommend you to first try to kill every boss that's on the server here with 1 inventory and a familiar. If that works fine, try it here .");	
			stage = 9;
		}else if (componentId == OPTION_4){
		sendOptionsDialogue("Are you sure that you want to start the fights?",
        			"Yes, bring me there",
					"No, I'm not ready.");
		stage = 5;
		}else if (componentId == OPTION_5){
		end();
		}
		break;
		case 1:
		sendPlayerDialogue(9814, "Mhhhh, what do you mean?");
		stage = 2;
	     break;
		case 2:
		sendNPCDialogue(943, 9843, "This cave contains almost every boss of the server,  and if you manage to defeat them you'll get a massive reward.");
		stage = 9;
	     break;
		 case 5:
		if(componentId == OPTION_1) {
		BossCaves.enterBossCaves(player);
		} else {
		end();
		}
	     break;
		 
		 
		case 9:
		stage = 0;	
		sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
        "Tell me more about it.",
	    "What are the rewards?",
		"What would you recommend ?",
		"Nothing");
	    break; 
		case 10:
		end();
	     break;
		default:
			end();
		break;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
