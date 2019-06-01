package com.rs.game.player.dialogues;

public class Banker extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Good day, How may I help you?" );
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (player.isLendingItem == true) {
				sendNPCDialogue(npcId, 9827, "Before we go any further, I'd like",
						"to remind you that you currently have an " 
								+ player.getTrade().getLendedItem() + " on loan.",
								"Would you like to claim it?");
			stage = 6;
			} else {
			stage = 0;
			sendOptionsDialogue("What would you like to say?",
					"I'd like to acess my bank account, please.",
					"I'd like to check my PIN settings.",
					"I'd like to see my collection box.", "What is this place?",
					"I would like the manage my presets.");
			}
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				player.getBank().openBank();
				end();
			} else if (componentId == OPTION_2) {
				player.getBankPin().openPinSettings();
				end();
			} else if (componentId == OPTION_3) {
				// TODO collection boss
				end();
			} else if (componentId == OPTION_4) {
				stage = 1;
				sendPlayerDialogue( 9827, "What is this place?" );
			} else if (componentId == OPTION_5) {
				player.getDialogueManager().startDialogue("PresetD");
		}
		}
	}

	@Override
	public void finish() {

	}

}
