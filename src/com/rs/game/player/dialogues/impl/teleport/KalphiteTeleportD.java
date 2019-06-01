package com.rs.game.player.dialogues.impl.teleport;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;

/**
 * Handles The Trader's dialogue.
 */
public class KalphiteTeleportD extends Dialogue {

	// The NPC ID.
	int npcId;

	@Override
	public void start() {
		sendOptions("Select an option","Kalphite King cave ","Normal Kalphite cave");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			if(componentId == OPTION_1){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2950, 1658, 0));
				player.closeInterfaces();
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3420,9510,0));
				player.closeInterfaces();
			}
			break;
		case 0:
		
		
		case 99:
			end();
			break;
		}
	}

	@Override
	public void finish() {
	}
}