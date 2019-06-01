package com.rs.game.player.dialogues.impl.dungeoneering;

import com.rs.Settings;
import com.rs.game.player.content.dungeoneering.Dungeon;
import com.rs.game.player.dialogues.Dialogue;

public class DungeonCompleteD extends Dialogue {

	Dungeon dungeon;

	@Override
	public void start() {
		dungeon = (Dungeon) parameters[0];
		
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Move on to the next dungeon?", "Yes", "No");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == 11) {
				end();
			}
		}
	}

	@Override
	public void finish() {

	}
}