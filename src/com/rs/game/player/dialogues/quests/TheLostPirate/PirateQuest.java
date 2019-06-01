package com.rs.game.player.dialogues.quests.TheLostPirate;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.quest.TheLostPirates;




public class PirateQuest extends Dialogue {

    @Override
    public void start() {
	if (player.startedPirateQuest == false) {
	TheLostPirates.handleQuestStartInterface(player);
	end();
	} else if (player.startedPirateQuest == true) {
	sendOptionsDialogue("Select a Option", 
	                "Finding the killer(s)",
					"Finding the other pirates",
					"Nothing");
					stage = 1;
	  }
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
     end();
	} else if (stage == 1) {
		if (componentId == OPTION_1) {
			TheLostPirates.handleProgressKillerQuestInterface(player);	
		} else if (componentId == OPTION_2) {
			
		} else if (componentId == OPTION_3) {
			end();
		}
	}
    }

    @Override
    public void finish() {

    }
}
