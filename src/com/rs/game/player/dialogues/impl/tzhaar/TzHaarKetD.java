package com.rs.game.player.dialogues.impl.tzhaar;

import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Colors;

/**
 * 
 * @author paolo
 *
 */
public class TzHaarKetD extends Dialogue {

	int npcId = 15194;

	@Override
	public void start() {
		sendNPCDialogue(npcId, 9840, "Welcome to the Fight Kiln "+player.getDisplayName()+", what can I do for you?");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int option) {
		if(stage == 1){
			sendOptions("Select an option", "what is this place?", "Transform Tokhaar-Kal-Ket","Open bank", "Nothing");
			stage = 2;
		} else if(stage == 2){
			if(option == OPTION_1){
				sendNPCDialogue(npcId, 9840, "In this mini-game you can fight against our best fighters ofcours you'll be rewarded if you succeed.");
				stage = 99;
			} else if(option == OPTION_2){
				if(player.getInventory().contains(23659)){
					sendOptions("Select an option."+Colors.red+" Note: You can't transform it back.", "Transform into TokHaar-Kal-Xil (Ranged)", "Transform into TokHaar-Kal-Mej (Magic)","Nothing");
					stage = 10;
				} else {
					sendNPCDialogue(npcId, 9840, "I couldn't find a Tokhaar-Kal-Ket in your inventory.");
					stage = 99;	
				}
			} else if(option == OPTION_3){
			    player.getBank().openBank();
				end();
			} else
				end();
		}else if(stage == 10){
			if(option == OPTION_1){
				player.getInventory().deleteItem(new Item(23659,1));
				player.getInventory().addItem(new Item(31610,1));
				sendItemDialogue(31610, 1, "TzHaar-ket transformed your Tokhaar-Kal-Ket into a TokHaar-Kal-Xil.");
				stage = 99;
			} else if(option == OPTION_2){
				player.getInventory().deleteItem(new Item(23659,1));
				player.getInventory().addItem(new Item(31611,1));
				sendItemDialogue(31611, 1, "TzHaar-ket transformed your Tokhaar-Kal-Mej into a TokHaar-Kal-Xil.");
				stage = 99;
			} else
				end();
		}	else if(stage == 99)
			end();
	}
	

	@Override
	public void finish() {

	}

}
