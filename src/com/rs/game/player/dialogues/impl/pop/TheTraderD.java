package com.rs.game.player.dialogues.impl.pop;

import com.rs.game.player.content.ports.npcs.Trader;
import com.rs.game.player.dialogues.Dialogue;

/**
 * Handles The Trader's dialogue.
 */
public class TheTraderD extends Dialogue {
    
	int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getPorts().firstTimer) {
			sendDialogue("The Trader has no goods for sale at the moment. Check bank once you have "
					+ "completed the Port tutorial.");
			stage = 99;
		} else if(player.getPorts().getMarket() == null){
			sendNPCDialogue(npcId, NORMAL, "I'm sorry but you need to unlock the Market first.");
			stage = 99;
		} else
			sendNPCDialogue(npcId, NORMAL, "Hello there, what do you need?");
		stage = 0;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage){
			case 0:
			sendOptions("Select an option", "View black market", "Sell goods", "Nothing");
			stage = 1;
			break;
			case 1:
				if(Trader.canTradeChrimes(player)){
				sendNPCDialogue(npcId, NORMAL, "I can offer you "+player.getPorts()+" chrimes today for a price of "+player.getPorts().getDailyChrimes() * Trader.CHRIME_PRICE+" gold coins.");
				stage = 2;
				} else {
					sendNPCDialogue(npcId, NORMAL, "I don't have any items left, come back tomorow.");
					stage = 100;
				}
			break;
			case 2:
				sendOptions("Select an option.","Yes I want to buy those chrimes","No thank you.");
				stage = 3;
				break;
			case 3:
				if(componentId == OPTION_1){
					Trader.buyChrimes(player);
					sendNPCDialogue(npcId, NORMAL, "Here you go, come back tomorow for more.");
					stage = 100;
				} else 
					end();
				break;
			case 100:
				end();
				break;
		}
	}

	@Override
	public void finish() {
	}
}