package com.rs.game.player.dialogues.farming;

import com.rs.game.player.dialogues.Dialogue;


public class HerbSeeds extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What would you like to plant?", "Guam Seeds",
				"Irit Seeds", "Kwuarm Seeds", "Snapdragon Seeds",
				"Next Page");
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		
	}

	@Override
	public void finish() {
	}
}