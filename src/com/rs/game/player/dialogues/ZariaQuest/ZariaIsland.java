package com.rs.game.player.dialogues.ZariaQuest;

import com.rs.game.player.dialogues.Dialogue;

public class ZariaIsland extends Dialogue {
private int npcId = 14392;
	
	@Override
	public void start() {
		sendNPCDialogue(npcId, 9827,"Did you find anything?");
	stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
   sendPlayerDialogue(9827, "Yes, on the North-east side of the island is an old encrypted rock.");
		stage = 1;
	} else if (stage == 1){
		sendNPCDialogue(npcId, 9827,"Okay thanks for your help, I'll let my people look at it. Meet me back home.");
		player.ZariaQueststage = 4;
	stage = 100;
	}
	else if (stage == 100)
		end();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
}
