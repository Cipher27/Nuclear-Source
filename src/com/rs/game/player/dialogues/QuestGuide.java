package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.controlers.StartTutorial;

public class QuestGuide extends Dialogue {

	int npcId;
	StartTutorial controler;

	@Override
	public void start() {
	
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"You have already learned the first thing needed to",
							"succeed in this world talking to other people!" },
					IS_NPC, npcId, 9827);
		} else if (stage == 0) {
			stage = 1;
			sendEntityDialogue(
					SEND_4_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"This is a dangerous world where people kill themselves",
							"to increase their honor. Those people get their",
							"dangerous artefacts by spawning taking use of",
							"commands as ::item ITEMID AMMOUNT." }, IS_NPC,
					npcId, 9827);
			player.getCutscenesManager().play("EdgeWilderness");
		} else if (stage == 1) {
			stage = 2;
			sendEntityDialogue(
					SEND_3_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"They also pray their gods on altars to get boosts.",
							"Why don't you try too? Click on the zaros altar there and",
							"try switching your prayers." }, IS_NPC, npcId,
					9827);
			//controler.updateProgress();
		} else if (stage == 5) {
			stage = 6;
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"To continue the tutorial head to the north",
							"and click on the wilderness ditch!" }, IS_NPC,
					npcId, 9827);
		} else {

			end();
		}

	}

	@Override
	public void finish() {

	}

}
