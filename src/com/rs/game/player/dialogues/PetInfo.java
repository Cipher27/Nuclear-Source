package com.rs.game.player.dialogues;

public class PetInfo extends Dialogue {
	
	//Begin van de dialogue
	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
		    sendOptionsDialogue("Pet Information", "Suggest new pet", "Suggest pet options", "See the chance to get a pet.", "Nothing");
			stage = 1; 
		}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			
		if(componentId == OPTION_1){
		player.getTemporaryAttributtes().put("enterpetsuggestion",Boolean.TRUE);
		player.getPackets().sendInputLongTextScript("Enter your suggestion");
		end();
		}
		if(componentId == OPTION_2){
		player.getTemporaryAttributtes().put("enterpetbuff",Boolean.TRUE);
		player.getPackets().sendInputLongTextScript("Enter your suggestion");
		end(); 
		}
		if(componentId == OPTION_3){
		sendDialogue("Pet info","Every 100 kills you get 1% extra chance to get one .");
		stage = 2;  
		}
		if(componentId == OPTION_4) {
		end();
		}
	
		}	else if (stage == 2) {
			end();
		}
		 
		 
}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	} 
		


	

}