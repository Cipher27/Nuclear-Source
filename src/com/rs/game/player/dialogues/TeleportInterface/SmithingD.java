package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;



public class SmithingD extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Smithing", "home furnance", "Artisan workshop");
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	if (componentId == OPTION_1) { 
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2318, 3682, 0));
				player.sm("Welcome to the skilling area.");
		} else if (componentId == OPTION_2) {
				player.sm("Welcome to the Artisan Workshop.");
				player.getControlerManager().startControler("ArtisanControler");
				player.closeInterfaces();
		}
    }
    }
    @Override
    public void finish() {

    }
}
