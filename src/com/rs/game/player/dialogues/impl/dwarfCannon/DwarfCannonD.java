package com.rs.game.player.dialogues.impl.dwarfCannon;

import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class DwarfCannonD extends Dialogue {

	private int npcId = 3823;

	@Override
	public void start() {
	if(player.completedDwarfCannonQuest){
		sendOptions("Select an option", "Open shop", "ask how you can help more.");
		stage = 11;
	}
	else if(player.getInventory().contains(new Item(1)) && !player.getInventory().contains(new Item(2354,150))){
		sendNPCDialogue(npcId,9827,"Nice you've found the toolkit, now I still need the 150 steel bars.");
	    stage = 100;
	}else if(!player.getInventory().contains(new Item(1)) && player.getInventory().contains(new Item(2354,150))){
		sendNPCDialogue(npcId,9827,"Nice you got the steel bars, now I only need the toolkit to finish this.");
	    stage = 100;
	}
	else if(player.getInventory().contains(new Item(1)) && player.getInventory().contains(new Item(2354,150))){
		sendNPCDialogue(npcId,9827,"Ooh you found the toolkit, thank you so much "+player.getDisplayName()+"! I'll now fix my cannons and you can buy them!");
	    stage = 10;
	} else {
	sendPlayerDialogue(9827,"Wow that's a nice cannon, does It work?");
	stage = -1;
	}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendNPCDialogue(npcId,9827,"Sadly not, I'm trying to fix it.");	
			stage = 1;
		} else if (stage == 1) {
			sendOptionsDialogue("Select an option", "Ask if you can help", "Ask why the cannon is called 'Dwarf cannon'", "Teleport me to the dwarven mines.");
			stage = 2;
		} 
	else if (stage == 2) {
		if(componentId == OPTION_1) {
		sendPlayerDialogue(9827,"Can I help fixing it ?");
		stage = 3;	
		} else if(componentId == OPTION_2){
			sendNPCDialogue(npcId,9827,"It originaly came from the dwarfs, we took their cannons after the war for our protection.");	
			stage = 100;
		} else if(componentId == OPTION_3){
			player.setNextWorldTile(new WorldTile(3049,9840,0));
			end();
		}
	 } else if (stage == 3){
		 sendNpc(3823,"Yeah, maybe we can rapair it together. But I  need some tools first I need about 150 steel bars and a toolkit.");
		 stage = 4;
	 }else if(stage == 4) {
		sendPlayerDialogue(9827,"Where can I find this toolkit?");
		stage = 5;	
	}else if (stage == 5){
		 sendNpc(3823,"You might find one in the old dwarven mine.");
		 stage = 6;
	 }else if (stage == 6){
		 sendPlayerDialogue(9827,"Okay I'll take a look.");
		stage = 100;	
	 }else if(stage == 10){
		player.completedDwarfCannonQuest = true;
		player.getInventory().deleteItem(new Item (1));
		player.getInventory().deleteItem(new Item(2354,150));
		sendPlayerDialogue(9827,"Okay nice, and no problem I would do anything for this colony!");
		stage = 100;
	 } else if (stage == 11) {
			if(componentId == OPTION_1) {
				ShopsHandler.openShop(player, 222);
				end();
				} else if(componentId == OPTION_2){
					sendNPCDialogue(npcId,9827,"No I'm fine thanks, maybe in the past.");	
					stage = 100;
				} 
			 }else if (stage == 100)
		end();
  }
	@Override
	public void finish() {

	}
}