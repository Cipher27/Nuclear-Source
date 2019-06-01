package com.rs.game.player.dialogues.auto;

import com.rs.game.player.dialogues.Dialogue;

/**
 *
 */
public class TriviaRewards extends Dialogue {


	@Override
	public void start() {
		sendOptionsDialogue("Select an Option", "How many points do I have?", "I would like to exchange my points.", "How do I get points?", "I would like to access my bank account.", "Never mind.");
		stage = -1;
		return;
	}

	@Override
	public void run(int interfaceId, int option) {
    }

	@Override
	public void finish() {

	}

}
