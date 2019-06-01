package com.rs.game.player.dialogues;


import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.controlers.DungeonControler;

public class Smuggler extends Dialogue {

	private int npcId;
	private DungeonControler controler;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		controler = (DungeonControler) parameters[1];
		sendEntityDialogue(SEND_3_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
			"Welcome to Dungeoneering, "+player.getDisplayName()+"!",
			"Here you make your way to the boss room for great prizes.",
			"You can check out my shop for the available rewards."
		},
		IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
				"So what can i do for you today "+player.getDisplayName()+"?"
			},
			IS_NPC, npcId, 9827);
			stage = 0;
		} else if (stage == 0) {
			sendOptionsDialogue("What do you want to do?", "View Dungeoneering Shop",  "Exit Dungeoneerong");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				player.sm("Comming soon.");
				end();
			}	else if (componentId == OPTION_2) {
				controler.leaveDungeon();
				end();
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
