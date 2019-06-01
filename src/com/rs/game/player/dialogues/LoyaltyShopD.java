package com.rs.game.player.dialogues;

import com.rs.game.item.Item;
import com.rs.utils.ShopsHandler;

public class LoyaltyShopD extends Dialogue {
	
	private int npcId = 13727;
	
	@Override
	public void start() {
		sendNPCDialogue(npcId, 9827, "Hey " + player.getUsername() + ", I'm Xuan. Well what would you like?" );
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
		sendOptionsDialogue("Xuan", "Show me your Aura Shop.", "Show me your Item shop please.", "How do i get Loyalty Points?","Nothing");
		stage = 2;
		} else if (stage == 2) {
		if(componentId == OPTION_1) {
			ShopsHandler.openShop(player, 154);
			end();
		}
		if(componentId == OPTION_2) {
		sendOptionsDialogue("Xuan",
		"Loyalty tiara's",
		"Recolour dyes",
		"Nothing");
		stage = 5;
		}
        if(componentId == OPTION_3) {
		    sendNPCDialogue(npcId, 9827, "When you are playing Hellion for longer than 30mins you will automatic get these loyalty tokens. " );
			stage = 3;
		} if(componentId == OPTION_4) {
		    end();
		}
		} else if (stage == 3) {
		end();
	  }else if (stage == 5) {
		if(componentId == OPTION_1) {
		sendNPCDialogue(npcId, 9827, "You have been active for "+player.daysOnline+" days in total." );
		stage = 6;
		} else if(componentId == OPTION_2) {
		sendOptions("Select an option", "Whip recolour", "Staff of light recolour");
		stage = 20;
		}
	  }else if (stage == 6) {
			if (player.daysOnline <7 ) {
			sendNPCDialogue(npcId, 9827, "You need to be active for atleast 7 days before you can claim the first crown." );	
			stage = 3;
			} else {
			sendNPCDialogue(npcId, 9827, "Your crown has been added to your inventory." );
			player.checkLoyaltyCrown();
			stage = 3;	
			}
	  } else if(stage == 20){
		  if(componentId == OPTION_1){
			  if(player.getInventory().containsItem(4151,1)){
				  sendOptions("Select an option","White whip", "Green whip", "Yellow whip", "Blue whip", "Nothing");
				  stage = 21;
			  } else {
				sendNPCDialogue(npcId, 9827, "You need a whip for this action." );  
				stage = 99;
			  }
		  } else if(componentId == OPTION_2){
			  if(player.getInventory().containsItem(15486,1)){
				  sendOptions("Select an option","Red Staff of light", "Green Staff of light", "Yellow Staff of light", "Blue Staff of light", "Nothing");
				  stage = 25;
			  } else {
				sendNPCDialogue(npcId, 9827, "You need a Staff of light for this action." );  
				stage = 99;
			  }
		  }
	  } else if(stage == 21){
			if(componentId == OPTION_1){
				if(player.getPointsManager().getLoyaltyTokens() >= 250){
					player.getInventory().deleteItem(new Item(4151,1));
					player.getInventory().addItem(new Item(15443,1));
					player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - 250);
					sendItemDialogue(15443, 1, "Xuan gave you a white whip.");
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You need 250 loyalty tokens for this action." );  
				stage = 99;
				}
			} else if(componentId == OPTION_2){
				if(player.getPointsManager().getLoyaltyTokens() >= 250){
					player.getInventory().deleteItem(new Item(4151,1));
					player.getInventory().addItem(new Item(15444,1));
					player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - 250);
					sendItemDialogue(15444, 1, "Xuan gave you a green whip.");
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You need 250 loyalty tokens for this action." );  
				stage = 99;
				}
			} else if(componentId == OPTION_3){
				if(player.getPointsManager().getLoyaltyTokens() >= 250){
					player.getInventory().deleteItem(new Item(4151,1));
					player.getInventory().addItem(new Item(15441,1));
					player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - 250);
					sendItemDialogue(15441, 1, "Xuan gave you a Yellow whip.");
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You need 250 loyalty tokens for this action." );  
				stage = 99;
				}
			}if(componentId == OPTION_4){
				if(player.getPointsManager().getLoyaltyTokens() >= 250){
					player.getInventory().deleteItem(new Item(4151,1));
					player.getInventory().addItem(new Item(15442,1));
					player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - 250);
					sendItemDialogue(15442, 1, "Xuan gave you a blue whip.");
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You need 250 loyalty tokens for this action." );  
				stage = 99;
				}
			} else 
				end();
	  } else if(stage == 25){
			if(componentId == OPTION_1){
				if(player.getPointsManager().getLoyaltyTokens() >= 250){
					player.getInventory().deleteItem(new Item(15486,1));
					player.getInventory().addItem(new Item(22207,1));
					player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - 250);
					sendItemDialogue(22207, 1, "Xuan gave you a red Staff of light.");
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You need 250 loyalty tokens for this action." );  
				stage = 99;
				}
			} else if(componentId == OPTION_2){
				if(player.getPointsManager().getLoyaltyTokens() >= 250){
					player.getInventory().deleteItem(new Item(15486,1));
					player.getInventory().addItem(new Item(22213,1));
					player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - 250);
					sendItemDialogue(22213, 1, "Xuan gave you a green Staff of light.");
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You need 250 loyalty tokens for this action." );  
				stage = 99;
				}
			} else if(componentId == OPTION_3){
				if(player.getPointsManager().getLoyaltyTokens() >= 250){
					player.getInventory().deleteItem(new Item(15486,1));
					player.getInventory().addItem(new Item(22209,1));
					player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - 250);
					sendItemDialogue(22209, 1, "Xuan gave you a yellow Staff of light.");
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You need 250 loyalty tokens for this action." );  
				stage = 99;
				}
			}if(componentId == OPTION_4){
				if(player.getPointsManager().getLoyaltyTokens() >= 250){
					player.getInventory().deleteItem(new Item(15486,1));
					player.getInventory().addItem(new Item(22211,1));
					player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() - 250);
					sendItemDialogue(22211, 1, "Xuan gave you a blue Staff of light.");
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You need 250 loyalty tokens for this action." );  
				stage = 99;
				}
			} else 
				end();
	  } else if(stage == 99)
		  end();
	}

	@Override
	public void finish() {

	}

}