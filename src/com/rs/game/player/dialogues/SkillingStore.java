package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class SkillingStore extends Dialogue {

	private int npcId;
	private int shopId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
	//	shopId = (Integer) parameters[1];
		sendNPCDialogue(npcId, 9827, "The best skilling items for the right price!" );
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;	
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Tools.",
					"Recourses.",
					"Nothing.");
		break;
		case 0:
			if(componentId == OPTION_1) {
				ShopsHandler.openShop(player, 58);
				end();
			}else if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 66);
				end();
			}else if (componentId == OPTION_3)
				end();
		break;
		case 1:
			stage = -2;	
			sendNPCDialogue(npcId, 9827, "You can also sell most items to the shop and the price", 
					"given will be based on the amount in stock.");
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
