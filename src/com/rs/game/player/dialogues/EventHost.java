package com.rs.game.player.dialogues;

import com.rs.game.World;
import com.rs.utils.Utils;

/**
 * @author paolo
 */



public class EventHost extends Dialogue {

 private int npcId = 7892;


	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Hostable Events", "Buy 1 hour double exp or drops.", "Pvm event","Skilling event", "back.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
	  if(componentId == OPTION_1) {
		sendOptionsDialogue("Choose an option", "1 hour double exp.", "1 hour double drops.", "back.");
		stage = 6;
		} else if(componentId == OPTION_2) {
		sendOptionsDialogue("Choose an option", "Spawns bosses", "Spawn training monsters", "back.");
		stage = 20;
		} else if(componentId == OPTION_3) {
			player.getDialogueManager().startDialogue("TournamentsD");
			/*sendOptionsDialogue("Choose an option", "Hunter area", "Fishing/Cooking area", "Mining/Smithing area","");
			stage = 6;*/
		}
		else if(componentId == OPTION_4) {
			player.getDialogueManager().startDialogue("ServerManager");
		}
	 }
	 /**
	   * Double exp and drops.
	   **/
	 else if(stage == 6) {
	 if(componentId == OPTION_1) {
	sendNPCDialogue(npcId, NORMAL, "Just to be clear, you are going to buy double exp for the hole server for 20mil.");	
	stage = 7;
	} else if(componentId == OPTION_2) {
	sendNPCDialogue(npcId, NORMAL, "Just to be clear, you are going to buy double drop rates for the hole server for 25mil.");
    stage = 9;	
	} else if(componentId == OPTION_3) {
	player.getDialogueManager().startDialogue("EventHost");	
	}
 }	
 //confirm the double exp
 else   if(stage == 7) {
	sendOptionsDialogue("Choose an option", "Buy the double exp (20mil).", "No thank you.");	
	stage = 8;
	}
 else if (stage == 8) {
	 if(componentId == OPTION_1) {
	 if ((player.getInventory().containsItem(995, 20000000)) && (World.getboughtExp() <= 0)){
	World.setboughtExp(Utils.currentTimeMillis() + 3600000);
	World.sendWorldMessage("<img=7><col=FF0000>All say thank you to "+ player.getDisplayName() +" for buying double exp for everyone!</col></img>",false);
	player.getInventory().removeItemMoneyPouch(995, 20000000);
	World.EventPlayer = player.getDisplayName();
    end();	 
	 }else if ((!player.getInventory().containsItem(995, 20000000)) && (World.getboughtExp() <= 0)){
		player.sm("You don't have enough money in your inventory.");
		end();
	 }else if ((player.getInventory().containsItem(995, 20000000)) && (!(World.getboughtExp() <= 0))){
	player.sm("there's still an event going on, keep skilling man!");
		end();	 
		 }
	} else if (componentId == OPTION_2){
		end();
	}
	}
	//double drops confirming
	else if(stage == 9) {
	sendOptionsDialogue("Choose an option", "Buy the double drop rates (25mil).", "No thank you.");	
	stage = 10;
	}
	else if (stage == 10) {
	 if(componentId == OPTION_1) {
	//	World.setboughtExp(Utils.currentTimeMillis() + 3600000);
	} else if (componentId == OPTION_2){
		end();
	}
	}
	 /**
	   * Pvm events
		**/
	else  if(stage == 10) {
     end();
	 }	
	}

	@Override
	public void finish() {
		
	}
	
}