package com.rs.game.player.dialogues;

public class HouseTeleportModificationD extends Dialogue {
	
	/**
	  * Made by Paolo, modi of the house teletab
	  **/

	@Override
	public void start() {
		sendOptionsDialogue("Pick an option",
				"Home teletab",
				"Godwars teletab", 
				"No.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
	
		end();
		} else
			end();
	}

	@Override
	public void finish() {

	}

}
