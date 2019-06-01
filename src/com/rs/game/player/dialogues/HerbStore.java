package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class HerbStore extends Dialogue {

	private int npcId = 587;

	
	@Override
	public void start() {
		sendNPCDialogue(npcId, 9827, "It's all about the potions!" );
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;	
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Store 1",
					"Store 2");
		break;
		case 0:
			if(componentId == OPTION_1) {
				ShopsHandler.openShop(player, 207);
				end();
			}else if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 208);
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
