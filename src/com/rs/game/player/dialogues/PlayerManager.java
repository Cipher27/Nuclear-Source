package com.rs.game.player.dialogues;

import com.rs.game.player.content.security.RecoveryQuestions;
import com.rs.utils.Colors;


public class PlayerManager extends Dialogue {

	private int npcId = 7909;

	@Override
	public void start() {
	sendNPCDialogue(npcId,9827,"Hey "+ player.getDisplayName()+", what would you like to change?");
	stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Account settings.", 
					"Server messages.",
					"Change looks.",
					"Account security",
					"Nothing");
			stage = 1;
			
		} else if (stage == 1) {
			//account information.
		 if (componentId == OPTION_1) {
            player.getDialogueManager().startDialogue("News");
			//appearance
			} else if (componentId == OPTION_2) {
			player.getDialogueManager().startDialogue("Looks");	 
			stage = 2;
			//overrides interface
			//} else if (componentId == OPTION_4) {
			//player.getDialogueManager().startDialogue("CosmeticsD");	 
			} else if (componentId == OPTION_3) {
			sendOptionsDialogue("Account Security.", 
			        "Recovery questions.",
					"Bank Pin.",
				//	"Ip Lock.",
					"Account pin");
					//"Account email");
				stage = 2;
			} else
				end();
		} else if (stage == 2) {
		 if (componentId == OPTION_1) {	
				sendOptionsDialogue("Recovery questions",
				    "What is this?",
					"Back");
			stage = 20;
		} else if (componentId == OPTION_2) {
		  player.getBankPin().openPinSettings();
				end();
		}    else if (componentId == OPTION_3) {
			if(player.hasEnteredPin){
				sendOptionsDialogue("Account pin",
				    "What is this?",
					(player.hasAccountPinActive ? Colors.red+"Status: Enabled" : Colors.green+"Status: Disabled"),
					"Back");
			} else {
				sendOptionsDialogue("Account pin",
					    "What is this?",
						"Set account pin",
						"Back");
			}
			stage = 10;
		}   else if (componentId == OPTION_5) {
				sendOptionsDialogue("Account email",
     				"What is this?",
					"Enter email",
					"Enter code",
					"send mail",
					"Nothing");
			stage = 30;
			} 
	 } else if (stage == 10) { 
		 if (componentId == OPTION_1) {	
         sendNPCDialogue(npcId, 9827, "This is a code you need to enter every time you log in, when you enter your code wrong you get kicked and a message will be send to an administrator.");
		 stage = -1;
		 }	 else if (componentId == OPTION_2) {
			 if(!player.hasEnteredPin){
			 player.getTemporaryAttributtes().put("account_pin", Boolean.TRUE);
			player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
			 } else {
				 player.hasAccountPinActive = !player.hasAccountPinActive;
				 sendNPCDialogue(npcId, 9827, "Your account pin has succesfully been "+(!player.hasAccountPinActive ? Colors.red+"Disabled." : Colors.green+"Enabled."));
				 stage = -1;
			 }
		} 	 
	 }
	 else if (stage == 20) {
		 if (componentId == OPTION_1) {	
         RecoveryQuestions.loadRecoveryInfo(player);
		end();
		 }  
	 } else if (stage == 30) {
		 if (componentId == OPTION_1) {	
         sendNPCDialogue(npcId, 9827, "This is needed to recover your account, you can also put a login warning when someone logs in with your account from a different ip.");
		 stage = -1;
		 } else if (componentId == OPTION_2){
			 player.getAttributes().put("email_input", true);
			player.getPackets().sendInputLongTextScript("Enter your email address");
			end();
		 }else if (componentId == OPTION_3){
			 player.getAttributes().put("email_code", true);
			 player.getPackets().sendInputLongTextScript("Please enter the code we've send to your email address");
			end();
		 }else if (componentId == OPTION_4) {
			 if (player.hasEnteredEmail == false) {
			 sendNPCDialogue(npcId, 9827, "You need to enter en email address first, before we can send you a mail.");
		     stage = 2; 
			 } else {
			player.getEmailManager().sendSecurityEmail(player);
			 end();
			 }
		} 	 
	 }
	}
	@Override
	public void finish() {

	}
}