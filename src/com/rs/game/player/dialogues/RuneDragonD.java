package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;


public class RuneDragonD extends Dialogue {

	@Override
	public void start() {
		if(player.isDonator() == false){
			end();
		}
	sendDialogue("In this cave you can find the runite dragons, are you sure that you want to enter?");
	stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Select an option", 
			        "Yes",
					"No");
			stage = 1;
			
		} else if (stage == 1) {
			player.setNextWorldTile(new WorldTile(2720,9774,0));
			end();
			} else if (componentId == OPTION_2) {
				end();
			} 
		} 
 
	@Override
	public void finish() {

	}
}