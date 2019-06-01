package com.rs.game.player.dialogues;


public class LootBeam extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Lootbeam settings", "Change lootbeam value", "Change lootbeam look","Nothing");
		stage = 0;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		 if (stage == 0) {
			if (componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Yes please!");
				stage = 1;
			}
			else if (componentId == OPTION_2) {
			sendOptionsDialogue("Look settings", "Default", "Orb","Rainbow");
		    stage = 4;
			}else if (componentId == OPTION_3) {
				end();
			}
		} else if (stage == 1) {
			sendOptionsDialogue("Pick A Loot Beam Price", "50K", "100K", "250K", "500K", "1 Million");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				sendDialogue("Your loot beam is now set to; 50K.");
				player.setLootBeam = 50000;
				stage = 3;
			}
			if (componentId == OPTION_2) {
				sendDialogue("Your loot beam is now set to; 100K.");
				player.setLootBeam = 100000;
				stage = 3;
			}
			if (componentId == OPTION_3) {
				sendDialogue("Your loot beam is now set to; 250K.");
				player.setLootBeam = 250000;
				stage = 3;
			}
			if (componentId == OPTION_4) {
				sendDialogue("Your loot beam is now set to; 500K.");
				player.setLootBeam = 500000;
				stage = 3;
			}
			if (componentId == OPTION_5) {
				sendDialogue("Your loot beam is now set to; 1 Million.");
				player.setLootBeam = 1000000;
				stage = 3;
			}
		} else if (stage == 3) {
			end();
		}else if (stage == 4) {
         if (componentId == OPTION_1) {
				player.lootbeamId = 4422;
			}
			else if (componentId == OPTION_2) {
			end();
			}else if (componentId == OPTION_3) {
			player.lootbeamId = 5052;
			end();
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	
}