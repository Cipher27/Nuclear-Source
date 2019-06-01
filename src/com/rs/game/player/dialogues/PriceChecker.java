package com.rs.game.player.dialogues;



public class PriceChecker extends Dialogue {
	
	/**
	  * Idk what is love, another stupid dialogue ime
	  
	  **/
	
	//Begin van de dialogue
	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
		    sendOptionsDialogue("Select an option", "Open full price check", "Open inventory price check", "Nothing");
			stage = 1; 
		}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		 if (player.getInterfaceManager().containsScreenInter() || player.isLocked()) {
		player.getPackets().sendGameMessage("Please finish what you're doing before opening the price checker.");
		return;
	    }player.sm("This interface needs an update, will be released later on.");
	   // player.stopAll();
		//WealthInterface.sendInterface(player);
		}
		if(componentId == OPTION_2){
		    if (player.getInterfaceManager().containsScreenInter() || player.isLocked()) {
		player.getPackets().sendGameMessage("Please finish what you're doing before opening the price checker.");
		return;
	    }
	    player.stopAll();
	    player.getPriceCheckManager().openPriceCheck();
	}

		if(componentId == OPTION_3){
	    end(); 
		   
		}
		
		}
		
		 
		 

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	} 
		


	

}