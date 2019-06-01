package com.rs.game.player.dialogues.interfaces;

import com.rs.game.player.InterfaceManager;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.dialogues.quests.CooksAssistant;
import com.rs.game.player.quest.impl.ImpCatcher;

/**
 * @author Danny
 */


public class Quests extends Dialogue {

	public Quests() {
	}

	@Override
	public void start() {


		stage = 1;
		sendOptionsDialogue("Hellion Quests", 
				"Home defender part I",
				"Missing toolkit");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) { //Dwarf Cannon
				player.getDialogueManager().startDialogue("SimpleMessage", "To start this quest, proceed to Franklin Caranos at the south-eastern end of home.");
			} 
			if (componentId == OPTION_2) { //Home def 1
				player.getDialogueManager().startDialogue("SimpleMessage", "To start this quest, proceed to Fame at the building south of home.");
			} 
		}
	}
			@Override
			public void finish() {
			}
	

}
