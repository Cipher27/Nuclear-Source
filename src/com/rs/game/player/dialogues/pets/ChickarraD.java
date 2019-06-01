package com.rs.game.player.dialogues.pets;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class ChickarraD extends Dialogue {

    private int npcId;
    int random = Utils.random(4);
    @Override
    public void start() {
    	npcId = (Integer) parameters[0];
		if (random ==0){
	    sendPlayerDialogue(9775, "Do you remember Abbinah, your home world?"); //done
		stage = 1;
		}else if (random == 1){
		sendNPCDialogue(npcId, 9827, "Seeds?"); //done
        stage = 10;		
		}else if (random == 2){
		sendNPCDialogue(npcId, 9827, "What season is it?");	//done
		stage = 20;
		}else if (random == 3){
		sendNPCDialogue(npcId, 9827, "Where are we?"); //only in gwd normally
        stage = 30;		
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
     //first
   	 if(stage == 1) {
   		sendNPCDialogue(npcId, 9827, "Of course!");
   		stage = 2;
 	 } else if (stage == 2) {
  		sendPlayerDialogue(9775, "Can you tell me about it?");
		stage = 3;
  	} else if(stage == 3) {
   		sendNPCDialogue(npcId, 9827, "There is more than I can put into words.");
   		stage = 4;
 	 } else if (stage == 4) {
  		sendNPCDialogue(npcId, 9827, "From the high island tiers, rich in history, to the dangerous lower tiers hiding secrets from the past.");
		stage = 5;
  	} else if (stage == 5) {
  		sendNPCDialogue(npcId, 9827, "I would spend many a days exploring, scuffling with the Kasaran!");
		stage = 6;
  	} else if (stage == 6) {
  		sendPlayerDialogue(9775, "Kasaran?");
		stage = 7;
  	} else if(stage == 7) {
   		sendNPCDialogue(npcId, 9827, "Oh, small winged creatures. Not too dangerous, but they don't like to be disturbed!");
   		stage = 100;
 	 } //second
	 else if (stage == 10) {
  		sendPlayerDialogue(9775, "Yes? What do you want?");
		stage = 11;
  	} else if(stage == 11) {
   		sendNPCDialogue(npcId, 9827, "Seeds?");
   		stage = 12;
 	 } else if (stage == 12) {
  		sendPlayerDialogue(9775, "Oh, you're hungry!");
		stage = 13;
  	} else if(stage == 13) {
   		sendNPCDialogue(npcId, 9827, "Seeds!");
   		stage = 14;
 	 }else if (stage == 14) {
  		sendPlayerDialogue(9775, "I don't have any suitable seeds to give you...");
		stage = 15;
  	} else if(stage == 15) {
   		sendNPCDialogue(npcId, 9827, "...Worms? Snails? Slugs?");
   		stage = 16;
	 } else if (stage == 16) {
  		sendPlayerDialogue(9775, "Ew, definitely not! Don't you eat anything else?");
		stage = 17;
  	} else if(stage == 17) {
   		sendNPCDialogue(npcId, 9827, "Hm...");
   		stage = 18;
 	 }else if(stage == 18) {
   		sendNPCDialogue(npcId, 9827, "Well, back on Abbinah we had delicious spicy treats! Do you have any of those?");
   		stage = 19;
	 } else if (stage == 19) {
  		sendPlayerDialogue(9775, "I'm afraid not.");
		stage = 100;
  	}//3th
	 else if (stage == 20) {
  		sendPlayerDialogue(9775, "Season? What do you mean?");
		stage = 21;
  	}else if(stage == 21) {
   		sendNPCDialogue(npcId, 9827, "You know - the time of the year. Winter? Summer?");
   		stage = 22;
 	 } else if (stage == 22) {
  		sendPlayerDialogue(9775, "I have no idea what you're talking about. I don't think we have 'seasons' here...");
		stage = 23;
  	}else if(stage == 21) {
   		sendNPCDialogue(npcId, 9827, "Then how will I know when to fly south for the winter? I don't want to get too cold!");
   		stage = 100;
 	 } //4th
	else if (stage == 30) {
  		sendPlayerDialogue(9775, "We're home! Where you came from!");
		stage = 31;
  	}else if(stage == 31) {
   		sendNPCDialogue(npcId, 9827, "I came from here? I don't remember.");
   		stage = 32;
 	 } else if (stage == 32) {
  		sendNPCDialogue(npcId, 9827, "You're such a bird-brain!");
		stage = 100;
  	} 
	
	
	
	
	else if (stage == 100){
		end();
	}
	}

    @Override
    public void finish() {

    }

}
