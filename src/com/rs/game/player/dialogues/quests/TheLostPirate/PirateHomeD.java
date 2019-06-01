package com.rs.game.player.dialogues.quests.TheLostPirate;

import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;



public class PirateHomeD extends Dialogue {

    private int npcId = 7856;
	
    @Override
    public void start() {
	if(player.getSkills().getLevel(Skills.FISHING) < 90 && player.getSkills().getLevel(Skills.COOKING) < 90){
		sendNPCDialogue(npcId, SAD, "You need 90 fishing and cooking before we can talk further.");
		return;
	}
		if(player.pirateTalk == 0){
	sendPlayerDialogue(NORMAL, "Wow, a pirate. What are you doing here Sir?"); 
	stage = 2;
		}else if (player.pirateTalk == 1 && (!player.getInventory().containsItem(995,10000000) || !player.getInventory().containsItem(384,200))){
	sendNPCDialogue(npcId, SAD, "You need to bring me 10mil and 200 raw sharks before you can join us.");
	stage = 10;
	} else if (player.pirateTalk == 1 && (player.getInventory().containsItem(995,10000000) && player.getInventory().containsItem(384,200))){
		player.getInventory().deleteItem(995,10000000);
		player.getInventory().deleteItem(384,200);
		player.pirateTalk = 2;
		sendNPCDialogue(npcId, NORMAL, "Thank you for the gesture, lets get started.");
		stage = 11;
	} else if (player.pirateTalk == 2){
			player.getPorts().enterPorts();
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
	 if (stage == 2) {
	sendNPCDialogue(npcId,NORMAL, "I'm not a real pirate, I'm here for the transport between the colony and the port."); 
	stage = 3;
	}
	else if (stage == 3) {
	sendPlayerDialogue(NORMAL, "But are you allowed to leave the colony?"); 
	stage = 4;
	}
	else if (stage == 4){
	sendNPCDialogue(npcId, SAD, "Ofcours, we need supplies for everyone right ?");
	stage = 5;
	}else if (stage == 5){
	sendPlayerDialogue(NORMAL, "That's true... Would you mind if I come with you ?"); 
	stage =6;
	}else if (stage == 6){
	sendNPCDialogue(npcId, SAD, "Mhhh, bring me 10mil and 200 raw sharks and you have yourself a deal.");
	player.startedPirateQuest = true;
	stage = 7;
	}else if (stage == 7){
	sendPlayerDialogue(NORMAL, "Okay I'll be back soon!"); 
	player.pirateTalk = 1;
	stage = 10;
	}else if (stage == 10){
	end();
	}else if (stage == 11){
		player.getPorts().enterPorts();
	end();
	}
    }

    @Override
    public void finish() {

    }
}
