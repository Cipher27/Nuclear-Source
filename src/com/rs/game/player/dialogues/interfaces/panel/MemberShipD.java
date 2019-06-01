package com.rs.game.player.dialogues.interfaces.panel;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;



public class MemberShipD extends Dialogue {
	

	
	//Begin van de dialogue
	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
		    sendOptionsDialogue("Power token manager", "What are these tokens?", "Open Shop");
			stage = 1; 
		}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		if(componentId == OPTION_1){
		sendNpc(14392,"These tokens can be seen as a new currency, you can earn them by skilling,killing monsters or completing achievements.");
		stage  = 2;
		}
		else if(componentId == OPTION_2){
			if(player.isAtHome()){
				ShopsHandler.openShop(player,152);
				end();
			}else
					player.sm("You need to be at home to open this shop.");
			}
		}
		else if (stage == 2){
			 sendOptionsDialogue("Power token manager", "What are these tokens?", "Open Shop");
			 stage = 1; 
		}
		 
		 
}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	} 
		


	

}