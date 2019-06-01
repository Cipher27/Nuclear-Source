package com.rs.game.player.content;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.io.InputStream;


public class PlayerKeyBinds {

	public static void getKeyBinds(InputStream stream, Player player) {
		int key = stream.readByte();
		switch(key) {
		case 101:
			player.getDialogueManager().startDialogue("EmptyConfirm");
			break;
		case 102:
			Magic.sendNormalTeleportSpell(player, 0, 0, (new WorldTile(3676, 2978, 0)));
		break;
		case 13:
			if (player.getInterfaceManager().containsScreenInter()) {
            player.closeInterfaces();
            return;
            }
			break;

		}

	}
}
