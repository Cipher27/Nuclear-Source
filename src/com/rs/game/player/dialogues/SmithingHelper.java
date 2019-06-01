package com.rs.game.player.dialogues;

public class SmithingHelper extends Dialogue {

	private int npcId = 14643;

	@Override
	public void start() {
	sendNPCDialogue(npcId,9827,"Hey "+ player.getDisplayName()+", what can I do for you ?");
	stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("options.", 
			        "Ask him about pickaxes (g)",
					"Ask him about the Imcando pickaxe ",
					"Compliment him about his area.");
			stage = 1;
			
		} else if (stage == 1) {
			 if (componentId == OPTION_1) {
			sendPlayerDialogue(9827,"Do you know how I can get a gilded pickaxe?");
			stage = 2;
			} else if (componentId == OPTION_2) {
				if(player.getInventory().containsItem(29524,1)&& player.getInventory().containsItem(29525,1) &&player.getInventory().containsItem(29526,1) &&player.getInventory().containsItem(29527,1)){ //29527,29526,29525){
				sendNPCDialogue(npcId,9827,"Wow you got all pieces, took you probably some time right?");	
				stage = 30;
				} else {
				sendPlayerDialogue(9827,"What do you know about the Imcando pickaxe?");
							stage = 10;
				}
			} else if (componentId == OPTION_3) {
			sendPlayerDialogue(9827,"Nice area you got here!");
			stage = 20;
			}
		} 
	//gilded
	else if (stage == 2) {
		sendNPCDialogue(npcId,9827,"You can smith one by using a gold bar on your pickaxe.");
	     stage = 3;
	 } else if (stage == 3) {
		sendPlayerDialogue(9827,"Really?? I didn't known that it was so easy.");
		stage = 4;
	 } else if (stage == 4) {
		sendNPCDialogue(npcId,9827,"It was a joke, of cours it's not that easy. You need a perfect gold bar for that.");
	     stage = 5;
	 }  else if (stage == 5) {
		sendPlayerDialogue(9827,"And how do I receive a perfect gold bar?");
		stage = 6;
	 } else if (stage == 6) {
		sendNPCDialogue(npcId,9827,"I have some in stock, whenever you have the imcando pickaxe. Use your pickaxe that you want to (g) on me and I'll do it for you.");
	     stage = 7;
	 } else if (stage == 7) {
		sendPlayerDialogue(9827,"Well, I'll let you know if I find it.");
		stage = 100;
	 }
	  //imcando pickaxe
	 else if (stage == 10){
		sendNPCDialogue(npcId,9827,"Do you mean the legendary pickaxe? You got it???");
	     stage = 11; 
	 }else if (stage == 11) {
		sendPlayerDialogue(9827,"No, but I wondered if you know how I could get one.");
		stage = 12;
	 }else if (stage == 12){
		sendNPCDialogue(npcId,9827,"I got myself a piece by mining, so I guess you can get all 4 pieces from mining. Come back to me if you found all 4 of them!");
	     stage = 13; 
	 }else if (stage == 13) {
		sendPlayerDialogue(9827,"Okay, I'll do!");
		stage = 100;
	 }
	//compliment
	else if (stage == 20){
	sendNPCDialogue(npcId,9827,"Thank you.");
	stage = 100;
	}else if (stage == 30){
		if(player.getInventory().containsItem(29524,1)&& player.getInventory().containsItem(29525,1) &&player.getInventory().containsItem(29526,1) &&player.getInventory().containsItem(29527,1)){
			player.getInventory().deleteItem(29524,1);
			player.getInventory().deleteItem(29525,1);
			player.getInventory().deleteItem(29526,1);
			player.getInventory().deleteItem(29527,1);
			player.getInventory().addItem(29523,1);
			sendNPCDialogue(npcId,9827,"Here you go, have fun with it. altough it has not much use.");
			stage = 100;
		}
	}else if (stage == 100)
		end();
  }
	@Override
	public void finish() {

	}
}