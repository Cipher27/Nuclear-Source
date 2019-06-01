package com.rs.game.player.dialogues.impl.shops;


import com.rs.game.player.content.ports.SpecialRewards.RECIPES;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

/**
 * Handles The Partner's (John Strum) dialogue.
 */
public class HerbloreShopD extends Dialogue {
	// The NPC ID.
	int npcId = 578;

	@Override
	public void start() {

			sendNPCDialogue(npcId, SAD, "Hallo "+player.getDisplayName()+", what can I do for you ?");
			stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			sendOptions("Select an option", "Open herbs shop", "Open ingredients");
			stage = 0;
			break;
		case 0:
			if(componentId == OPTION_1){
				ShopsHandler.openShop(player,224);
				end();
			} else if(componentId == OPTION_2){
				ShopsHandler.openShop(player,57);
				end();
			} 
			break;
		}
	}

	@Override
	public void finish() {
	}
}