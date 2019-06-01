package com.rs.game.player.dialogues.impl.dwarfCannon;

import com.rs.game.player.dialogues.Dialogue;

public class DevinD extends Dialogue {

	private int npcId = 3823;

	@Override
	public void start() {
	sendPlayerDialogue(9827,"Hey dude, do you have any idea where I can find a toolkit here?");
	stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendNPCDialogue(npcId,9827,"You see that door over there, with the vines. I think it's inthere.");	
			stage = 4;
		} 
	else if(stage == 4) {
		sendPlayerDialogue(9827,"Okay let's open it.");
		stage = 5;	
	}else if (stage == 5){
		 sendNpc(3823,"I wise it was that easy, you need some special flame I think. The dwarves always protect their tools with special inventions.");
		 stage = 6;
	 }else if (stage == 6){
		 sendPlayerDialogue(9827,"Mhhh okay, I'll look around.");
		stage = 100;	
	 }else if (stage == 100)
		end();
  }
	@Override
	public void finish() {

	}
}