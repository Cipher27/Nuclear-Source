package com.rs.game.player.dialogues.quests.pvmQuest;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;

public class PvmMasterQuest3 extends Dialogue {

	private int npcId = 14850;
	
	@Override
	public void start() {
		//npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"My Men and I captured some of the beasts eggs, they are a good training spot!" }, IS_NPC, npcId, 9827);

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Do you have another challenge for me?",
					"I want to go to the Vorago cave.",
					"Nothing special.");
		}else if (stage == 0) {
			if(componentId == OPTION_1) {
				player.getDialogueManager().startDialogue("BossCavesD");
			}if(componentId == OPTION_2) {
			  Magic.sendCustomTeleportSpell2(player, 0, 0, new WorldTile(2770, 3821, 0));
	          end();
			}if(componentId == OPTION_3) {
				end();
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
