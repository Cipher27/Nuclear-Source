package com.rs.game.player.dialogues.pets;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class EllieD extends Dialogue {

    private int npcId;
    int random = Utils.random(3);
    @Override
    public void start() {
    	npcId = (Integer) parameters[0];
		if (random ==0){
	    sendPlayerDialogue(9775, "How's it going, Ellie?"); //done
		stage = 1;
		}else if (random == 1){
		sendNPCDialogue(npcId, 9827, "Porridge oats and chicken goats!"); //done
        stage = 10;		
		}else if (random == 2){
		sendNPCDialogue(npcId, 9827,"In Varrock but upside-down! Dead and demons in this town!");	//done
		stage = 22;
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
     //first
   	 if(stage == 1) {
   		sendNPCDialogue(npcId, 9827, "In the sky! On the earth! Lunch time, elevenses, what's it worth?");
   		stage = 2;
 	 } else if (stage == 2) {
  		sendPlayerDialogue(9775, "Mhm, fascinating. That's good to hear. And I'm fine, thanks.");
		stage = 3;
  	} else if(stage == 3) {
   		sendPlayerDialogue(9775, "Anything planned today?");
   		stage = 4;
 	 }else if(stage == 4) {
   		sendNPCDialogue(npcId, 9827, "Bloodveld, penguin, one, two, three! In the spinach, you shall see!");
   		stage = 5;
 	 } else if (stage == 5) {
  		sendPlayerDialogue(9775, "Well, that all sounds great.");
		stage = 100;
  	}  //second
	 else if (stage == 10) {
  		sendPlayerDialogue(9775, "Maybe it's trying to tell me something! A warning, perhaps?");
		stage = 11;
  	} else if(stage == 11) {
   		sendPlayerDialogue(9775, "Let's see. Porridge oats... something to do with food. Chickens and goats?");
   		stage = 12;
 	 } else if (stage == 12) {
  		sendNPCDialogue(npcId, 9827,  "Never ending, the wizard is sending!");
		stage = 13;
  	}else if (stage == 13) {
  	    sendPlayerDialogue(9775, "So how do goats and chickens relate to a wizard? And what is he sending?");
		stage = 14;
  	}else if(stage == 14) {
   		sendNPCDialogue(npcId, 9827,  "Roses, noses, books and toes-es!");
   		stage = 17;
 	 }else if(stage == 17) {
   		sendPlayerDialogue(9775, "You know, I don't think there's a message here at all...");
   		stage = 100;
 	 } //3th
     else if(stage == 22) {
   		sendPlayerDialogue(9775, "Oh, here we go again...");
   		stage = 23;
 	}else if (stage == 23) {
  		sendNPCDialogue(npcId, 9827, "Place a coin atop my hand. I'm the saviour of the land!");
		stage = 100;
  	}else if(stage == 25) {
   		sendPlayerDialogue(9775, "Of course you are.");
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
