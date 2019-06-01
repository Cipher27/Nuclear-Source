package com.rs.game.player.dialogues;

/**
 * @author paolo
 */



public class CommonQuestions extends Dialogue {

 private int npcId = 14392;

	public CommonQuestions() {
	}

	@Override
	public void start() {
		sendOptionsDialogue("Common asked questions.", "How do I become a staff member?", "Is this server P2W?","How do I make starter money?", "How do I report bugs?","Why are some textures so messed up?" );
			stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
		sendNPCDialogue(npcId, NORMAL, "By helping other player's around Hellion and being and active polite player who contributes to the server.");
		stage = 6;
		} else if(componentId == OPTION_2) {
		sendNPCDialogue(npcId, NORMAL, "Definitly not, there's no way you can buy op armour when you are a donator. But you do get some extra benefits");
		stage = 6;
		} else if(componentId == OPTION_3) {
			sendNPCDialogue(npcId, NORMAL, "You can make easy cash by skilling around and selling the resources to the general store.");
			stage = 6;
		}
		else if(componentId == OPTION_4) {
			sendNPCDialogue(npcId, NORMAL, "You can report bugs by telling a member of staff or you can visit our forums and report in the bug section. Or do ::report and then your message.");
			stage = 6;
		}
		else if(componentId == OPTION_5) {
		sendNPCDialogue(npcId, NORMAL, "This is a problem every player has due to the client which needs to be worked on. Please be patient, it will be fixed later on");
			stage = 6;
		}
	 }
	 else if(stage == 6) {
		 end();
	 }	
	 
	 if(stage == 10) {
     end();
	 }	
	}

	@Override
	public void finish() {
		
	}
	
}