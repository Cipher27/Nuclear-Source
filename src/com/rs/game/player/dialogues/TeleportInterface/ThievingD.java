package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;



public class ThievingD extends Dialogue {


    @Override
    public void start() {
	sendOptionsDialogue("Thieving Teleports",
	"Home stalls", 
	"Mans (level 1)", 
	"Ardougne market", 
	"Dwarf traders",
    "Master Thieves"	);
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2331,3675, 0));
				player.sm("Welcome to Thieving.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2694,3495, 0));
				player.sm("Welcome to the market, don't get caught!");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2662, 3303, 0));
			//	player.sm("Welcome to Pyramid plunder.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2880, 10198, 1));
				player.sm("Welcome to the Dwarf traders.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
	}else if (componentId == OPTION_5) {
	sendOptionsDialogue("Warning this will teleport you into the wilderness.",
	"Yes teleport me into the wilderness.", 
	"No thank you.");
	stage = 2;
	}
    } else if (stage == 2){
		if (componentId == OPTION_1) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3280, 3942, 0));
				player.sm("Enter the castle to find the master thiefs.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.getControlerManager().startControler("Wilderness");
		} else {
			end ();
		}
	}
    }
    @Override
    public void finish() {

    }
}
