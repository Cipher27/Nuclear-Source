package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;



public class HunterD extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Hunter", "Birds","Chins", "Grenwalls", "Salamanders","More");
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	if (componentId == OPTION_1) { 
	  sendOptionsDialogue("Hunter", "Crimson swift","Golden warbler", "Copper longtail","Tropical Wagtail","Wimpy bird");
		stage = 1;
	}else if (componentId == OPTION_2) {
			sendOptionsDialogue("Hunter", "Grey chins","Red chins", "Back");
			stage = 2;
	} else if (componentId == OPTION_3) {
			sendOptionsDialogue("Hunter", "spot1","spot2", "Back");
			stage = 3;
	}  else if (componentId == OPTION_4)  {
			sendOptionsDialogue("Hunter", "Swamp lizard","Orange Salamander", "Red Salamander", "Black Salamander");
			stage = 5;
		}
	else if (componentId == OPTION_5) {
		sendOptions("Select an option", "Puro Puro", "Herblore Habitat", "Back");
		stage = 20;
			/*player.setNextWorldTile(new WorldTile(2592,4318, 0));
			player.getControlerManager().startControler("PuroPuro");
			end();*/			
	}
    }else if(stage == 1){
		if (componentId == OPTION_1) { //k
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2608,2895, 0));
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3406,3089, 0));
		}else if (componentId == OPTION_3) { //k
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2725,3777, 0));
		}else if (componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2522,2941, 0));
		}else if (componentId == OPTION_5) { //k
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2591,2822, 0));
		} 
		
	} else if(stage == 2){
		if (componentId == OPTION_1) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2338,3599, 0));
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2556,2919, 0));
		} else {
			sendOptionsDialogue("Hunter", "Birds","Chins", "Grenwalls");
			stage = 0;
		}
		
	} else if(stage == 3){
		if (componentId == OPTION_1) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2265,3240, 0));
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2226,3142, 0));
		} else {
			sendOptionsDialogue("Hunter", "Birds","Chins", "Grenwalls");
			stage = 0;
		}
		
	} else if(stage == 4){
		sendOptionsDialogue("Hunter", "Swamp lizard","Orange Salamander", "Red Salamander", "Black Salamanders");
			stage = 5;
	} else if(stage == 5){
		if (componentId == OPTION_1) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3538,3452, 0));
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3406,3089, 0));
		}else if (componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2450,3220, 0));
		}else if (componentId == OPTION_4) {
			player.getDialogueManager().startDialogue("teleportWarningD", new WorldTile(3315,3658, 0), "Yes I want to teleport into the wilderness for Black Salamanders.");
		}
	} else if(stage == 20){
		if(componentId == OPTION_1){
			player.setNextWorldTile(new WorldTile(2592,4318, 0));
			player.getControlerManager().startControler("PuroPuro");
			end();
		} else if(componentId == OPTION_2){
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2951,2935, 0));
			end();
		} else {
			sendOptionsDialogue("Hunter", "Birds","Chins", "Grenwalls", "Salamanders","More");
			stage = 0;
		}
	}
    }
    @Override
    public void finish() {

    }
}
