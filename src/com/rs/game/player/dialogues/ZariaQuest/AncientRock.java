package com.rs.game.player.dialogues.ZariaQuest;

import com.rs.game.player.dialogues.Dialogue;

public class AncientRock extends Dialogue {
	
	@Override
	public void start() {
		if(player.ZariaQueststage < 2){
		sendPlayerDialogue(9827, "I should look for the portal");
	stage = 100;	
		} else{
	sendPlayerDialogue(9827, "It looks like it's written in an old ancient language, I should tell Zaria about this");
	stage = 100;
	player.ZariaQueststage = 3;
	}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	 if (stage == 100)
		end();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
}
