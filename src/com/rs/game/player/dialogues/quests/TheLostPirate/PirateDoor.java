package com.rs.game.player.dialogues.quests.TheLostPirate;

import com.rs.game.player.dialogues.Dialogue;



public class PirateDoor extends Dialogue {

    private int npcId = 7856;
	
	

    @Override
    public void start() {
	if (player.pFriendsLooking == 1) {
	 player.addWalkSteps(3803, 3015, 0, false);
	 end();
	} else if (player.pFriendsLooking > 1) {
	sendPlayerDialogue(NORMAL, "I don't think it's a good idea to look twice at the same place..."); 	
	stage = 0;
	}else if (player.pFriendsLooking < 1) {
	sendPlayerDialogue(NORMAL, "Why should I go in here?"); 	
	stage = 0;
	}
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
     end();
	}
    }

    @Override
    public void finish() {

    }
}
