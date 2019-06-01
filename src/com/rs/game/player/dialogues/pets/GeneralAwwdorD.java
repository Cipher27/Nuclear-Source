package com.rs.game.player.dialogues.pets;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class GeneralAwwdorD extends Dialogue {

    private int npcId ;
    int random = Utils.random(3);
    @Override
    public void start() {
    	npcId = (Integer) parameters[0];
		 if (player.isAtGodwars()){
		sendNPCDialogue(npcId, 9827, "Quite barbaric if you ask me."); //only in gwd normally
        stage = 30;		
		}
		else if (random ==0){
	    sendPlayerDialogue(9775, "Rarr! All hail the Big High War God!"); //done
		stage = 1;
		}else if (random == 1){
		sendNPCDialogue(npcId, 9827, "Do you know why Graardor received his name?");
        stage = 10;		
		}else if (random == 2){
		sendNPCDialogue(npcId, 9827, "Good day, Player.");	
		stage = 20;
		}
    }

    @Override
    public void run(int interfaceId, int componentId) {
     //first
   	 if(stage == 1) {
   		sendNPCDialogue(npcId, 9827, "Are you making fun of me?");
   		stage = 2;
 	 } else if (stage == 2) {
  		sendPlayerDialogue(9775, "Only a little bit.");
		stage = 3;
  	} else if(stage == 3) {
   		sendNPCDialogue(npcId, 9827, "Quite insensitive, if you ask me. You do know he's dead, don't you?");
   		stage = 4;
 	 } else if (stage == 4) {
  		sendPlayerDialogue(9775, "Sorry.");
		stage = 100;
  	} //second
	 else if (stage == 10) {
  		sendPlayerDialogue(9775, "No?");
		stage = 11;
  	} else if(stage == 11) {
   		sendNPCDialogue(npcId, 9827, "Well you see, back home we had these massive doors, and when Graardor was younger he'd never be able to open them. He'd yell and cry pushing them, like 'Graaaarrr!'. So we called him Graardor!");
   		stage = 12;
 	 } else if (stage == 12) {
  		sendPlayerDialogue(9775, "Is that true?");
		stage = 13;
  	} else if(stage == 13) {
   		sendNPCDialogue(npcId, 9827, "No...");
   		stage = 100;
 	 }//3th
	 else if (stage == 20) {
  		sendPlayerDialogue(9775, "You're quite eloquent for an ourg...Not like Graardor at all.");
		stage = 21;
  	} else if(stage == 21) {
   		sendNPCDialogue(npcId, 9827, "Ah, yes. I suppose I am somewhat of an anomaly. Due to my small stature I was of no use to the ourgs. I was never trained to fight, so I chose to learn instead.");
   		stage = 22;
 	 } else if (stage == 22) {
  		sendPlayerDialogue(9775, "I think I like you more.");
		stage = 100;
  	} //4th
	else if (stage == 30) {
  		sendPlayerDialogue(9775, "What is?");
		stage = 31;
  	} else if(stage == 31) {
   		sendNPCDialogue(npcId, 9827, "All this fighting. Quite unnecessary.");
   		stage = 32;
 	 } else if (stage == 32) {
  		sendNPCDialogue(npcId, 9827, "Humans come in, they fight. When Graardor wins, it's not like he goes anywhere. He just stands there basking in his victory!");
		stage = 33;
  	} else if(stage == 33) {
   		sendNPCDialogue(npcId, 9827, "It all seems very strange to me.");
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
