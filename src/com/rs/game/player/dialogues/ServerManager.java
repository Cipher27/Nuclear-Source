package com.rs.game.player.dialogues;

import com.rs.game.player.content.custom.WelcomeBoard;
/**
 * @author paolo
 **/



public class ServerManager extends Dialogue {

	public ServerManager() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Choose an Option", "Host a Server Event", "Server information","Commonly asked questions", "Polls","None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			player.getDialogueManager().startDialogue("EventHost");
		} else if(componentId == OPTION_2) {
			end();
			WelcomeBoard.WelcomeInterface(player);
		} else if(componentId == OPTION_3) {
			player.getDialogueManager().startDialogue("CommonQuestions");
		}
		else if(componentId == OPTION_4) {
		player.getDialogueManager().startDialogue("Polls");
		}
		else if(componentId == OPTION_5) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
		
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}