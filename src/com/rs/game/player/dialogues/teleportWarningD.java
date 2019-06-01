package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class teleportWarningD extends Dialogue {
	
	private WorldTile tile;
	private String message;

	@Override
	public void start() {
		tile = (WorldTile) parameters[0];
		message = (String) parameters[1];
		sendOptionsDialogue("Pick an option", ""+message, "Nevermind");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			Magic.sendNormalTeleportSpell(player, 0, 0, tile);
			player.getControlerManager().startControler("Wilderness");
			end();
		}
		else if (componentId == OPTION_2) {
			end();
			}
	}

	@Override
	public void finish() {

	}

}
