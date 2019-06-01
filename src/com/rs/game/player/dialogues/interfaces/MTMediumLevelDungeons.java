package com.rs.game.player.dialogues.interfaces;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;

public class MTMediumLevelDungeons extends Dialogue {
	
	

	@Override
	public void start() {
		sendOptionsDialogue("Medium Level Dungeons/Areas",
				"Taverly Dungeon",
				"Brimhaven Dungeon",
				"Waterfall Dungeon",
				"Grotworm Lair",
				"Other");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2886, 9797, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2706, 9566, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2574, 9864, 0));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1204, 6370, 0));
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Medium Level Dungeons/Area - Pg 2",
						"Zogre Infestation",
						"Lava Dungeon",
						"Iron Dragon",
						"Bronze Dragons",
						"More");
				stage = 1;
			}
		} else if (stage == 1) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2462, 3048, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3056, 10289, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2911, 3933, 0));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2421, 4690, 0));
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Medium Level Dungeons/Area - Pg 3",
						"Living Rock Caverns",
						"Tzhaar City",
						"Elite Knights",
						"Chaos Dwogre Battlefield",
						"More");
				stage = 2;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3653, 5115, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2845, 3170, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3026, 9953, 1));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1518, 4705, 0));
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Medium Level Dungeons/Area - Pg 4",
						"Pothole Dungeon",
						"None");
				stage = 3;
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2828, 9520, 0));
			} else if (componentId == OPTION_2) {
				end();
			}
		}
		 
	}

	@Override
	public void finish() {

	}
}
