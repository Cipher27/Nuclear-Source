package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class VoteStore extends Dialogue {

	
	@Override
	public void start() {
	sendNPCDialogue(14945, 9843,"Hallo "+player.getDisplayName()+", what can I do for you ?");
	stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;	
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
        			"Open the vote link.",
					"Open the store",
					"Why should I vote?",
					"What is the vote raffle ?",
					"Nothing.");
		break;
		case 0:
			if(componentId == OPTION_1) {
			sendNPCDialogue(943, 9843, "Here you go!");
			player.getPackets().sendOpenURL("not availble yet");		
			stage = 1;
			}else if (componentId == OPTION_2) {
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
        			"Vote store",
					"Riddle store",
					"Go back");
					stage = 4;
			
			}else if (componentId == OPTION_3) {
			player.getDialogueManager().startDialogue("SimpleMessage", "We need players for our community, and you need friends so on what are you waiting?? ");	
			stage = 1;
		}else if (componentId == OPTION_4){
		player.getDialogueManager().startDialogue("SimpleMessage", "Every day there will be one lucky voter who'll recieve an extra reward, so make sure you vote!");	
			stage = 1;
		}else if (componentId == OPTION_5){
		end();
		}
		break;
		case 1:
		end();
	     break;
		 case 2:
		if(componentId == OPTION_1)
			ShopsHandler.openShop(player, 26);
		 else{
			 sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
        			"Double spin token.",
					"Exp lamp",
					"Power crystals(100)",
					"What is the vote raffle ?",
					"Nothing.");
			stage = 3;
		 }
	     break;
		 case 4:
		 if(componentId == OPTION_1) {
			player.getInterfaceManager().sendInterface(3026);
			end();
		 } else if(componentId == OPTION_2) {
			 ShopsHandler.openShop(player, 219);
			 end();
		 } else {
			stage = 0;	
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
        			"Open the vote link.",
					"Open the store",
					"Why should I vote?",
					"What is the vote raffle ?",
					"Nothing."); 
		 }			 
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
