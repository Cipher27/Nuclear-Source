package com.rs.game.player.dialogues.pets;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class NexterminatorD extends Dialogue {

    private int npcId;
    int random = Utils.random(4);
    @Override
    public void start() {
    	npcId = (Integer) parameters[0];
		if (player.getEquipment().getWeaponId() == 20171) { //special if the player wears a zaryte bow
		sendNPCDialogue(npcId, 9827, player.getDisplayName()+ " I'm particularly proud of you today."); //done
        stage = 40;			
		} else if (player.isAtNex()){ //should actually only be in the room not the whole room 
			sendNPCDialogue(npcId, 9827, player.getDisplayName()+ " I'm particularly proud of you today."); //done	
		} else if (player.isAtGodwars()){ //spec in gwd
			sendNPCDialogue(npcId, 9827, "Wow, look at that room! Can we go down there, "+player.getDisplayName()+"."); //done
	        stage = 50;	
		}else {
		if (random ==0){
	    sendPlayerDialogue(9775, "Can you tell me more about the Nihil?"); //done
		stage = 1;
		}else if (random == 1){
		sendNPCDialogue(npcId, 9827, "NOW, THE POWER OF ZAROS"); //done
        stage = 10;		
		}else if (random == 2){
		sendNPCDialogue(npcId, 9827, "Fill my soul with smoke!");	//done
		stage = 20;
		}else if (random == 3){
		sendNPCDialogue(npcId, 9827, "You know I taught K'ril everything he knows."); //only in gwd normally
        stage = 30;		
		}
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
  		sendPlayerDialogue(9775, "Oh yeah? This ought to be interesting.");
		stage = 11;
  	} else if(stage == 11) {
   		sendNPCDialogue(npcId, 9827, "Fumus! Umbra! Cruor! Glacies! Do not fail me!");
   		stage = 12;
 	 } else if (stage == 12) {
  		sendPlayerDialogue(9775, "...");
		stage = 13;
  	}else if (stage == 13) {
  		sendNPCDialogue(npcId, 9827, "...Fumus? ...Umbra?");
		stage = 14;
  	}else if(stage == 14) {
   		sendPlayerDialogue(9775, "Do you actually know where we are?");
   		stage = 16;
 	 }else if(stage == 16) {
   		sendNPCDialogue(npcId, 9827, "It was worth a try.");
   		stage = 100;
 	 } //3th
	 else if (stage == 20) {
  		sendPlayerDialogue(9775, "I don't think so. That's very bad for you!");
		stage = 21;
  	} else if(stage == 21) {
   		sendNPCDialogue(npcId, 9827, "In that case...");
   		stage = 22;
 	 } else if (stage == 22) {
  		sendNPCDialogue(npcId, 9827, "Darken my shadow!");
		stage = 23;
  	} else if(stage == 23) {
   		sendPlayerDialogue(9775, "Really? I mean, it's a shadow..It's already pretty dark.");
   		stage = 24;
 	}else if (stage == 24) {
  		sendNPCDialogue(npcId, 9827, "Fine. Flood my lungs with blood!");
		stage = 25;
  	}else if(stage == 25) {
   		sendPlayerDialogue(9775, "I'd really rather not.");
   		stage = 26;
 	 }else if(stage == 26) {
   		sendNPCDialogue(npcId, 9827, "Infuse me with the power of ice, at least?");
   		stage = 27;
 	 }else if(stage == 27) {
   		sendPlayerDialogue(9775, "You have some really weird requests.");
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
   	 //zaryte bow
  	else if (stage ==40) {
  		sendPlayerDialogue(9775, "Yeah? Why's that?");
		stage = 41;
  	} else if(stage == 41) {
   		sendNPCDialogue(npcId, 9827, "Well, I've been watching you and today of all days...");
   		stage = 42;
 	 } else if (stage == 42) {
 		sendNPCDialogue(npcId, 9827, "It's good to see you're using ZA-RYTE BOW!");
		stage = 43;
  	}else if (stage == 43) {
  		sendNPCDialogue(npcId, 9827, "Za-ryte! The right! Get it?!");
		stage =44;
  	}else if(stage == 44) {
   		sendPlayerDialogue(9775, "...");
   		stage = 45;
 	 }else if(stage == 45) {
   		sendNPCDialogue(npcId, 9827, "Hahahahaha!");
   		stage = 46;
 	 }else if(stage == 46) {
    		sendNPCDialogue(npcId, 9827, "That's not funny.");
       		stage = 100;
     	 }
	//nex room
 	else if (stage ==50) {
  		sendPlayerDialogue(9775, "Hmm... I'm not really sure you want to do that.");
		stage = 51;
  	} else if(stage == 51) {
   		sendNPCDialogue(npcId, 9827, "Why not! It looks like the best room ever. I'd love to go down there! I'd stay there for ever!");
   		stage = 52;
 	 }else if(stage == 52) {
   		sendPlayerDialogue(9775, "Ah, the irony");
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
