package com.rs.game.player.dialogues.quests.TheLostPirate;

import com.rs.game.player.dialogues.Dialogue;



public class PirateIntro extends Dialogue {

    private int npcId = 7856;
	
	/** @Paolo
	  * a story about a pirate who was the leader of a colony, but his friends got killed and some were gone. You need to help to find the killers and his lost friends
	  
	  wounded pirate 14345
	  *
	  */

    @Override
    public void start() {
	sendPlayerDialogue(NORMAL, "Wow, a pirate. What are you doing here Sir?"); 
	stage = 2;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	sendOptionsDialogue("Select a Option", 
	                "Help the pirate with finding the killer.",
					"Help the pirate with finding is other friends.","Ask for his story",
					"Nothing");
					stage = 1;
	}
	else if (stage == 1) {
		if (componentId == OPTION_1) {
			sendNPCDialogue(npcId, NORMAL, "Thank you for helping me my friend, I heard that Max knows more about the killers.");
			player.pFriendsLooking = 1;
            stage = 10;			
			}	else if (componentId == OPTION_2) {
			sendNPCDialogue(npcId, NORMAL, "They ran into the jungle, that's all I know. Start looking there.");
			player.pFriendsLooking = 1;
			stage = 10;
			}else if (componentId == OPTION_3) {
			sendPlayerDialogue(NORMAL, "This was my land.."); 
	        stage = 3;
			}else if (componentId == OPTION_4) {
			end();
			}
	}
	else if (stage == 2) {
	sendNPCDialogue(npcId,NORMAL, "The question is, what are you doing here? This was my land."); 
	stage = 3;
	}
	else if (stage == 3) {
	sendPlayerDialogue(NORMAL, "What do you mean with 'your land'?"); 
	stage = 4;
	}
	else if (stage == 4){
	sendNPCDialogue(npcId, SAD, "Before the settles arrived, me and my pirate friends ruled this place. We had build such a beautiful community...");
	stage = 5;
	}else if (stage == 5){
	sendPlayerDialogue(NORMAL, "But where are your other pirate friends now ?"); 
	stage =6;
	}else if (stage == 6){
	sendNPCDialogue(npcId, SAD, "I had 5 friends, 2 of them got killed by the settles, and the others disappeared.");
	player.startedPirateQuest = true;
	stage = 0;
	}else if (stage == 10){
	end();
	}
    }

    @Override
    public void finish() {

    }
}
