package com.rs.game.player.dialogues.impl.dungeoneering;

import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;

public class DungFloorSelectD extends Dialogue {

	@Override
	public void finish() {

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Floor type", "Frozen", "Frozen Low",
					"Frozen Medium", "Frozen High");
			stage = 2;
		} else if (stage == 2) {
			switch (componentId) {
			case 11:
				// player.setDungeon(new Dungeon(player, 0));//
				end();
				break;
			case 13:
				if (player.getSkills().getLevelForXp(Skills.DUNGEONEERING) > 22) {
					// player.setDungeon(new Dungeon(player, 1));
					// player.dropstoBank = false;
				} else {
					player.sm("You need at least 23 dungeoneering to access the abandoned floors.");
				}
				end();
				break;
			case 14:
				if (player.getSkills().getLevelForXp(Skills.DUNGEONEERING) > 34) {
					// player.setDungeon(new Dungeon(player, 2));
					// player.dropstoBank = false;
				} else {
					player.sm("You need at least 35 dungeoneering to access the furnished floors.");
				}
				end();
				break;
			case 15:
				if (player.getSkills().getLevelForXp(Skills.DUNGEONEERING) > 70) {
					// player.setDungeon(new Dungeon(player, 3));
					// player.dropstoBank = false;
				} else {
					player.sm("You need at least 71 dungeoneering to access the occult floors.");
				}
				end();
				break;
			default:
				end();
			}
		}
	}

	@Override
	public void start() {
		sendDialogue("Please choose a floor type.");
		stage = 1;
	}
}