package com.rs.game.player.dialogues.pets;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class CorporealPuppyD extends Dialogue {

    private int npcId;
    int random = Utils.random(3);
    @Override
    public void start() {
    	npcId = (Integer) parameters[0];
		 if (random == 0){
		sendNPCDialogue(npcId, 9827, "I'm having so much fun! I'm tired! This is the best!"); //done
        stage = 10;		
		}else if (random == 1){
		sendNPCDialogue(npcId, 9827, "This is so awesome! Everything is great! This is the most amazing day ever!");	//done
		stage = 20;
		}else if (random == 2){
		sendNPCDialogue(npcId, 9827, "What are we doing now?!");
        stage = 30;		
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
     //first
   	 if(stage == 1) {
   		sendNPCDialogue(npcId, 9827, "Why do you ask me?");
   		stage = 2;
 	 } else if (stage == 2) {
  		sendNPCDialogue(npcId, 9827, "I do not wish to speak of those primitive creatures.");
		stage = 3;
  	} else if(stage == 3) {
   		sendNPCDialogue(npcId, 9827, "You humans associate Nex with them, but Zaros chose her!");
   		stage = 4;
 	 }else if(stage == 4) {
   		sendNPCDialogue(npcId, 9827, "She chose to rise as one of his most trusted servants. It is an insult to compare her to them!");
   		stage = 100;
 	 } //second
	 else if (stage == 10) {
  		sendPlayerDialogue(9775, "Maybe you should rest a bit.");
		stage = 11;
  	} else if(stage == 11) {
   		sendNPCDialogue(npcId, 9827, "No time for rest! Got to explore-");
   		stage = 12;
 	 } else if (stage == 12) {
  	sendNPCDialogue(npcId, 9827, "No time for rest! Got to explore-");
		stage = 13;
  	}else if (stage == 13) {
  		sendNPCDialogue(npcId, 9827, "Zzzzz...");
		stage = 14;
  	}else if(stage == 14) {
   		sendPlayerDialogue(9775, "That's probably for the best.");
   		stage = 100;
 	 } //3th
	 else if (stage == 20) {
  		sendPlayerDialogue(9775, "You said that yesterday.");
		stage = 21;
  	} else if(stage == 21) {
   		sendNPCDialogue(npcId, 9827, "Yesterday was amazing! I'm having the best time!");
   		stage = 22;
 	 } else if (stage == 22) {
  		sendNPCDialogue(npcId, 9827, "Normal days are great! Everything is great with you!");
		stage = 23;
  	} else if(stage == 23) {
   		sendPlayerDialogue(9775, "You said that to a butterfly earlier.");
   		stage = 24;
 	}else if (stage == 24) {
  		sendNPCDialogue(npcId, 9827, "It's my best friend! You're my best friend! This is awesome!");
		stage = 100;
  	}//4th
	else if (stage == 30) {
  		sendPlayerDialogue(9775, "Well I thought we'd go for a walk, maybe craft some stuff, perhaps visit the bank...");
		stage = 31;
  	} else if(stage == 31) {
   		sendNPCDialogue(npcId, 9827, ""+player.getDisplayName()+"! That sounds like the best!");
   		stage = 32;
 	 } else if (stage == 32) {
  		sendPlayerDialogue(9775, "Well it's kind of just a normal day, to be honest.");
		stage = 33;
  	} else if(stage == 33) {
   		sendNPCDialogue(npcId, 9827, "Normal days are great! Everything is great with you!");
   		stage = 34;
 	 } else if (stage == 34) {
  		sendPlayerDialogue(9775, "That's really nice. I have a question though...");
		stage = 35;
  	} else if (stage == 35) {
  		sendPlayerDialogue(9775, "Do you ever calm down?");
		stage = 36;
  	}else if(stage == 36) {
   		sendNPCDialogue(npcId, 9827, "Calming down is great!");
   		stage = 37;
 	 }else if (stage == 37) {
  		sendPlayerDialogue(9775, "I'll take that as a no!");
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
