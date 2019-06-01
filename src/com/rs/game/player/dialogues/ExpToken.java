package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.game.Animation;

public class ExpToken extends Dialogue {
	
	/**
	  * Made by Paolo, activates a token that will give you an hour double exp.
	  **/

	@Override
	public void start() {
		if(Settings.doubleExp){
		 sendDialogue("You can't activate this while double exp is active.");
		 stage = 2;
		} else {
		sendOptionsDialogue("Would you like to activate this ticket? <col=ff0000>note</col>: if you logout while this is active it will count to.",
				"Yes, give me 1 hour double exp. ", "No.");
		stage = 1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == 1){
		if (componentId == OPTION_1) {
		player.getDoubleExpTime().start(3600);
		player.getInventory().deleteItem(24952, 1);
		player.setNextAnimation(new Animation(26400));
		player.sm("Your double exp is now: <col=ff0000>activated</col>.");
		end();
		
		} else
			end();
		} else {
			end();
		}
	}

	@Override
	public void finish() {

	}

}
