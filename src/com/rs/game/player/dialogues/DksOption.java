package com.rs.game.player.dialogues;

import com.rs.game.player.content.BossPetHandler.BossPets;

public class DksOption extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Rex", "Prime", "Supreme");
		stage = 0;
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if (stage == 0) { 
			if (componentId == OPTION_1) {
				if (player.collectedPets.contains(BossPets.DKS_REX)) {
					if (player.getPetManager().spawnPet(33801, true)) 
						return;
					} else {
						player.sm("You can unlock this pet from killing Rex.");
						end();
				}
			} else if (componentId == OPTION_2) {
				if (player.collectedPets.contains(BossPets.DKS_PRIME)) {
					if (player.getPetManager().spawnPet(33800, true)) 
						return;
					} else {
						player.sm("You can unlock this pet from killing Prime.");
						end();
				}
			} else if (componentId == OPTION_3) {
				if (player.collectedPets.contains(BossPets.DKS_SUPREME)) {
					if (player.getPetManager().spawnPet(33802, true)) 
						return;
					} else {
						player.sm("You can unlock this pet from killing Supreme.");
						end();
				}
			}
		}		
	}

	@Override
	public void finish() {

	}

}
