package com.rs.game.player.dialogues.impl.pop;


import com.rs.game.player.dialogues.Dialogue;

/**
 * Handles The Partner's (John Strum) dialogue.
 */
public class MegD extends Dialogue {
	// The NPC ID.
	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getPorts().firstTimer) {
			sendNPCDialogue(npcId, NORMAL, "You should talk to John first.");
			stage = 99;
		} else {
			sendNPCDialogue(npcId, NORMAL, "Hey "+player.getDisplayName()+" I'm such a big fun of you, I really need your help if you have some time for me.");
			stage = -1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			sendPlayerDialogue(NORMAL,"What can I do for you ?");
			stage = 0;
			break;
		case 0:
			sendOptions("Select an option", "Comming soon (sorry)");
			stage = 99;
			break;
		
		case 99:
			end();
			break;
		}
	}

	@Override
	public void finish() {
	}
}