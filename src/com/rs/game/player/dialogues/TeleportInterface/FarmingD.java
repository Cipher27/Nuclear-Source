package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;



public class FarmingD extends Dialogue {


    @Override
    public void start() {
	sendOptionsDialogue("Farming Teleports", "Major Patches", "Wood Tree Patches", 
					"Fruit Tree Patches", "Bush Patches", "More" );
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	if (componentId == OPTION_1) { 
				sendOptionsDialogue("Major Patches", "Catherby", "Ardougne", 
						"Canifs", "Falador" );
				stage = 28;
		} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Wood Tree Patches", "Lumbridge", "Varrock", 
						 "Falador", "Tree Gnome", "Taverly" );
				stage = 29;
		} else if (componentId == OPTION_3) {
			sendOptionsDialogue("Fruit Tree Patches", "Gnome Stronghold", "Catherby", 
					 "Gnome Maze", "Brimhaven", "Lletya" );
				stage = 31;
		} else if (componentId == OPTION_4) {
			sendOptionsDialogue("Bush Patches", "Champion's Guild", "Rimmington", 
					 "Ardougne", "Etceteria");
				stage = 32;
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Farming Teleports", "Hop Patches", "Misc Patches");
				stage = 33;
	}
    } else if (stage == 28) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2806, 3463, 0));
				player.sm("Welcome to the Catherby Farming Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2664, 3374, 0));
				player.sm("Welcome to the Ardougne Farming Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3599, 3523, 0));
				player.sm("Welcome to the Canifs Farming Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3052, 3304, 0));
				player.sm("Welcome to the Falador Farming Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
	}
			
		} else if (stage == 29) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3228,3246, 0));
				player.sm("Welcome to the Lumbridge Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3229, 3456, 0));
				player.sm("Welcome to the Varrock Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3001, 3373, 0));
				player.sm("Welcome to the Falador Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2436, 3413, 0));
			player.sm("Welcome to the Tree Gnome Stronghold Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2927, 3429, 0));
			player.sm("Welcome to the Taverly Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 31) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2475, 3444, 0));
				player.sm("Welcome to the Gnome Stronghold Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2860, 3432, 0));
				player.sm("Welcome to the Catherby Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2498, 3178, 0));
				player.sm("Welcome to the Gnome Maze Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2764, 3211, 0));
			player.sm("Welcome to the Brimhaven Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2346, 3161, 0));
			player.sm("Welcome to the LLeyta Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}} else if (stage == 32) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3182, 3356, 0));
				player.sm("Welcome to the Champion's Guild Bush Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2940, 3220, 0));
				player.sm("Welcome to the Rimmington Bush Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2630, 3313, 0));
				player.sm("Welcome to the Ardougne Bush Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2593, 3864, 0));
			player.sm("Welcome to the Etceteria Bush Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 34) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3229, 3310, 0));
				player.sm("Welcome to the Lumbridge Hop Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2667, 3522, 0));
				player.sm("Welcome to the McGrubor Hop Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2576, 3101, 0));
				player.sm("Welcome to the Yanille Hop Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2810, 3333, 0));
			player.sm("Welcome to the Entrana Hop Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 35) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3088, 3363, 0));
				player.sm("Welcome to the Belladonna Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3316, 3201, 0));
				player.sm("Welcome to the Cactus Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3452, 3470, 0));
				player.sm("Welcome to the Mushroom Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2798, 3099, 0));
			player.sm("Welcome to the Calqut Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2813, 3679, 0));
			player.sm("Welcome to the Trollheim Herb Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 33) {
			if (componentId == OPTION_1) { 
				sendOptionsDialogue("Hop Patches", "Lumbridge", "McGrubor", 
						"Yanille", "Entrana" );
				stage = 34;
		} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Misc Patches", "Belladonna", "Cactus", 
						 "Mushroom", "Calqut Tree", "Trollheim Herb" );
				stage = 35;
	}
			
		} else if (stage == 27) {
			if (componentId == OPTION_1) { 
				sendOptionsDialogue("Major Patches", "Catherby", "Ardougne", 
						"Canifs", "Falador" );
				stage = 28;
		} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Wood Tree Patches", "Lumbridge", "Varrock", 
						 "Falador", "Tree Gnome", "Taverly" );
				stage = 29;
		} else if (componentId == OPTION_3) {
			sendOptionsDialogue("Fruit Tree Patches", "Gnome Stronghold", "Catherby", 
					 "Gnome Maze", "Brimhaven", "Lleyta" );
				stage = 31;
		} else if (componentId == OPTION_4) {
			sendOptionsDialogue("Bush Patches", "Champion's Guild", "Rimmington", 
					 "Ardougne", "Etceteria");
				stage = 32;
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Farming Teleports", "Hop Patches", "Misc Patches");
				stage = 33;
	}
    }
    }
    @Override
    public void finish() {

    }
}
