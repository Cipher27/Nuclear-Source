package com.rs.game.player.dialogues;

public class PotteryWheelOption extends Dialogue {


	@Override
	public void start() {
	sendOptionsDialogue("Pottery options.", 
	                "Normal pottery",
					"Urns");
			stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		
		if (stage == 1) {
		 if (componentId == OPTION_1) {
			 player.getDialogueManager().startDialogue("PotteryWheel");
			} else if (componentId == OPTION_2) {
           sendOptionsDialogue("Urn crafting options", 
	                "Fishing",
					"Cooking",
					"Mining",
					"Smithing",
					"Woodcutting");
					stage = 2;
			}
	} else if (stage == 2) {
		 if (componentId == OPTION_1) {
		 player.getDialogueManager().startDialogue("PotteryWheelFishing");	 
		 }
	}
	}
	@Override
	public void finish() {

	}
}