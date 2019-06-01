package com.rs.game.player.dialogues.pets;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class KrilTinyrothD extends Dialogue {

    private int npcId;
    int random = Utils.random(4);
    @Override
    public void start() {
    	npcId = (Integer) parameters[0];
		if (random ==0){
	    sendPlayerDialogue(9775, "Aren't you a bit disappointed with Zamorak's defeat at the Battle of Lumbridge?"); //done
		stage = 1;
		}else if (random == 1){
		sendNPCDialogue(npcId, 9827, "Did I ever tell I'm the best fighter in this land?");
        stage = 10;		
		}else if (random == 2){
		sendNPCDialogue(npcId, 9827, "YARRR! I will rain death upon you!");	
		stage = 20;
		}else if (random == 3){
		sendNPCDialogue(npcId, 9827, "You know I taught K'ril everything he knows."); //only in gwd normally
        stage = 30;		
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
     //first
   	 if(stage == 1) {
   		sendNPCDialogue(npcId, 9827, "We will never doubt Zamorak. This is just a temporary respite.");
   		stage = 2;
 	 } else if (stage == 2) {
  		sendNPCDialogue(npcId, 9827, "Zamorak will not be idle, though his followers have much to do to gain his favour again.");
		stage = 3;
  	} else if(stage == 3) {
   		sendNPCDialogue(npcId, 9827, "You will see his wrath once more.");
   		stage = 100;
 	 } //second
	 else if (stage == 10) {
  		sendPlayerDialogue(9775, "No! You must have some amazing stories about the enemies you have overcome!");
		stage = 11;
  	} else if(stage == 11) {
   		sendNPCDialogue(npcId, 9827, "Oh many stories of great battle. Let me tell you about the rat I defeated just last week...");
   		stage = 12;
 	 } else if (stage == 12) {
  		sendNPCDialogue(npcId, 9827, "It came at me, whiskers in a frenzy, fur on end! The most vicious creature you've ever seen!");
		stage = 13;
  	}else if (stage == 13) {
  		sendPlayerDialogue(9775, "This was just a normal sized rat?");
		stage = 14;
  	}else if(stage == 14) {
   		sendNPCDialogue(npcId, 9827, "Well perhaps slightly larger than average...");
   		stage = 15;
 	 }else if (stage == 15) {
  		sendPlayerDialogue(9775, "Haven't you fought anything bigger?");
		stage = 16;
  	} else if(stage == 16) {
   		sendNPCDialogue(npcId, 9827, "A slightly bigger rat?");
   		stage = 100;
 	 } //3th
	 else if (stage == 20) {
  		sendPlayerDialogue(9775, "Maybe you should pick on someone your own size. Think smaller.");
		stage = 21;
  	} else if(stage == 21) {
   		sendNPCDialogue(npcId, 9827, "You mock me? I will tear you limb from limb!");
   		stage = 22;
 	 } else if (stage == 22) {
  		sendPlayerDialogue(9775, "Maybe I'd be worried about my fingers, but certainly not my limbs.");
		stage = 23;
  	} else if(stage == 23) {
   		sendNPCDialogue(npcId, 9827, "You know you could just humour me.");
   		stage = 100;
 	 }//4th
	else if (stage == 30) {
  		sendPlayerDialogue(9775, "Hmm...is that true?");
		stage = 31;
  	} else if(stage == 31) {
   		sendNPCDialogue(npcId, 9827, "You dare doubt me?");
   		stage = 32;
 	 } else if (stage == 32) {
  		sendPlayerDialogue(9775, "Oh no, of course not, your...er...mightiness. It's just I thought that if you had taught K'ril, he would be better.");
		stage = 33;
  	} else if(stage == 33) {
   		sendNPCDialogue(npcId, 9827, "Well he wasn't a very good student.");
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
