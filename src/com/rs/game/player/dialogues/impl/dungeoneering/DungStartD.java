package com.rs.game.player.dialogues.impl.dungeoneering;

import com.rs.game.player.dialogues.Dialogue;

public class DungStartD extends Dialogue {

	@Override
	public void start() {
		sendDialogue("Welcome to Dungeoneering, please chose a game mode.");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Pick a Mode.", "New Dungeoneering (Solo)",
					"Classic Dungeoneering (Solo/Multi)", "None");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == 11) {
				/*
				 * if (player.getSkills().getCombatLevelWithSummoning() == 138)
				 * player.setDungeon(new Dungeon(player, 3)); else if
				 * (player.getSkills().getCombatLevelWithSummoning() > 125)
				 * player.setDungeon(new Dungeon(player, 2)); else if
				 * (player.getSkills().getCombatLevelWithSummoning() > 85)
				 * player.setDungeon(new Dungeon(player, 1)); else if
				 * (player.getSkills().getCombatLevelWithSummoning() > 3)
				 * player.setDungeon(new Dungeon(player, 0));
				 */
			} else if (componentId == OPTION_2) {
				/*
				 * if (player.canUseOldDung == false) { player.sm(
				 * "This game mode is currently disabled. Please use the new one."
				 * ); } else {
				 * player.getInterfaceManager().closeChatBoxInterface();
				 * player.getBank().depositAllEquipment(false);
				 * player.getBank().depositAllInventory(false);
				 * player.getControlerManager().startControler("RuneDungLobby",
				 * 1); }
				 */
			} else if (componentId == OPTION_3) {
				end();
			}
			end();
		}
	}

	@Override
	public void finish() {

	}
}