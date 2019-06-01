package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class PowerCrystal extends Dialogue {

	
	@Override
	public void start() {
	sendPlayerDialogue(9814, "Please tell the admins how you got this.");
	stage = -1;
	}
/*
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;	
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What can I do with these tokens?",
					"How can I obtain them ?",
					"Which items can I upgrade ?",
					"Open the shop.",
					"Nothing.");
		break;
		case 0:
			if(componentId == OPTION_1) {
			sendNPCDialogue(943, 9843, "These tokens are used as an secondary currency here in Zaria, you can use them to upgrade tools,weapons and armour. You can also learn new methodes (in the shop) as example: rocktails soup cooking or learn how to heal yourself from death enemies");
			stage = 1;
			}else if (componentId == OPTION_2) {
			sendNPCDialogue(943, 9843, "You can obtain these tokens, by killing monsters and training your skills.");	
			stage = 1;
			}else if (componentId == OPTION_3) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You can upgrade: Archer,warrior,brezeker and seers rings, helm of neitzot, whip, godswords, dragon pickaxe, Saradomin sword, Dark bows. ");	
			stage = -1;
		}else if (componentId == OPTION_4){
			  	sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, 
				    "Combat store",
					"Skilling store",
					"Back.");
					stage = 2;
		}  else if (componentId == OPTION_5){
				end();
		}
		break;
		case 1:
		sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What can I do with these tokens?",
					"How can I obtain them ?",
					"Which items can I upgrade ?",
					"Open the store.",
					"Nothing.");
		stage = 0;
	     break;
		case 2:
		if(componentId == OPTION_1) {
		ShopsHandler.openShop(player, 153);
		end();
		} else if (componentId == OPTION_2) {
	    ShopsHandler.openShop(player, 152);
		end();
		} else if (componentId == OPTION_2) {
		end();
		}
	     break;
		default:
			end();
		break;
		}

	}*/

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(int interfaceId, int componentId) {
		// TODO Auto-generated method stub
		
	}

}
