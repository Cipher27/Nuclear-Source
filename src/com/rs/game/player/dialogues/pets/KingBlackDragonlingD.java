package com.rs.game.player.dialogues.pets;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class KingBlackDragonlingD extends Dialogue {

    private int npcId;
    int random = Utils.random(3);
    @Override
    public void start() {
    	npcId = (Integer) parameters[0];
		if (random ==0){
	    sendPlayerDialogue(9775, "What was it like living in the King Black Dragon's lair? You must have seen some exciting stuff!"); //done
		stage = 1;
		}else if (random == 1){
		sendNPCDialogue(npcId, 9827, "I'm the biggest, meanest dragon around!"); //done
        stage = 10;		
		}else if (random == 2){
		sendPlayerDialogue(9775,"Awwww, who's a cute little dragon?");	//done
		stage = 20;
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
     //first
   	 if(stage == 1) {
   		sendNPCDialogue(npcId, 9827, "To be honest, it was quite repetitive.");
   		stage = 2;
 	 } else if (stage == 2) {
  		sendNPCDialogue(npcId, 9827, "Although one visitor does stand out. A strange creature, it reminded me of a human crossed with a dragon.");
		stage = 3;
  	} else if(stage == 3) {
   		sendNPCDialogue(npcId, 9827, "A ghostly white colour.");
   		stage = 4;
 	 }else if(stage == 4) {
   		sendNPCDialogue(npcId, 9827, "Listen here, "+player.getDisplayName()+", not much scares me. But he did.");
   		stage = 100;
 	 } //second
	 else if (stage == 10) {
  		sendPlayerDialogue(9775, "No you're not.");
		stage = 11;
  	} else if(stage == 11) {
   		sendNPCDialogue(npcId, 9827, "What do you mean? No one is more ferocious than me!");
   		stage = 12;
 	 } else if (stage == 12) {
  		sendPlayerDialogue(9775, "Well, for a start you're half my height. Secondly, even if you were full size you'd still be toast if you went up against the Queen Black Dragon!");
		stage = 13;
  	}else if (stage == 13) {
  		sendNPCDialogue(npcId, 9827, "The Queen Black Dragon has nothing on me!");
		stage = 14;
  	}else if(stage == 14) {
   		sendPlayerDialogue(9775, "How would you like to say that to her face?");
   		stage = 16;
 	 }else if(stage == 16) {
   		sendNPCDialogue(npcId, 9827, "Oh, I was just speaking hypothetically. I need time to practice, of course, and the conditions need to be right....");
   		stage = 17;
 	 }else if(stage == 17) {
   		sendPlayerDialogue(9775, "That's what I thought.");
   		stage = 100;
 	 } //3th
	else if(stage == 20) {
   		sendNPCDialogue(npcId, 9827, "Do not speak to me like that! I am royalty!");
   		stage = 21;
 	 } else if (stage == 21) {
  		sendNPCDialogue(npcId, 9827, "I am a living weapon! A creature of incredible strength and ferocity! I bring death to all who oppose me!");
		stage = 22;
  	} else if(stage == 22) {
   		sendPlayerDialogue(9775, "Soooo cute!");
   		stage = 23;
 	}else if (stage == 23) {
  		sendNPCDialogue(npcId, 9827, "No! I am the night!");
		stage = 100;
  	}else if(stage == 25) {
   		sendPlayerDialogue(9775, "I'd really rather not.");
   		stage = 26;
 	 }else if(stage == 26) {
   		sendNPCDialogue(npcId, 9827, "Infuse me with the power of ice, at least?");
   		stage = 27;
 	 }else if(stage == 27) {
   		sendPlayerDialogue(9775, "You have some really weird requests.");
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
