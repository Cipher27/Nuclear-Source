package com.rs.game.player.dialogues.quests.pvmQuest;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;

public class PvmMasterQuest2 extends Dialogue {

	private int npcId = 14850;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Aight, are you ready to fight the monster?" }, IS_NPC, npcId, 9827);

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Let's go!",
					"Give me some tips please.",
					"No, I'm not ready.");
		}else if (stage == 0) {
			if(componentId == OPTION_1) {
				stage = 2;
				sendPlayerDialogue( 9827, "Let's go!" );
			}if(componentId == OPTION_2) {
				stage = 3;
				sendPlayerDialogue( 9827, "Give me some tips please." );
			}if(componentId == OPTION_3) {
				stage = 4;
				sendPlayerDialogue( 9827, "No, I'm not ready." );
			}
		}else if (stage == 2) {
			 Magic.sendCustomTeleportSpell2(player, 0, 0, new WorldTile(2772, 3837, 0));
			 player.setForceMultiArea(true);
			end();
		}else if (stage == 3) {
			stage = -2;
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Tips, eeeeuuu.... fight I guees?" }, IS_NPC, npcId, 9827);
							
		}else if (stage == 4) {
			stage = -2;
		/*	sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"The only ores and bars I sell are those sold to me." }, IS_NPC, npcId, 9827);
			
		*/
		end();
		} else 
			end();

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
