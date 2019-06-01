package com.rs.game.player.dialogues.impl.dungeoneering;

import com.rs.game.player.dialogues.Dialogue;

public class DungeonExitD extends Dialogue {

	@Override
	public void start() {
		sendDialogue("You will receive no experience for leaving the dungeon early.");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Are you sure you want to leave?", "Yes", "No");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == 11) {
				end();
			} else if (componentId == OPTION_2) {
				end();
			}
			end();
		}
	}

	@Override
	public void finish() {

	}
}