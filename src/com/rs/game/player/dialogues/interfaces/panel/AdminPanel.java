package com.rs.game.player.dialogues.interfaces.panel;

import com.rs.game.World;
import com.rs.game.player.dialogues.Dialogue;



public class AdminPanel extends Dialogue {
	
	/**
	  * Created by Paolo, just a handy thing for admins to handle the server.
	  * Extra uitleg aub, wat doet dit gedeelte, voor wat wordt dit gebruikt, welke methodes,...?
	  **/
	
	//Begin van de dialogue
	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
		    sendOptionsDialogue("Server manager", "OP Heal", "Items spawn", "Events manager", "Teleports","Actions");
			stage = 1; 
		}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		//op heal
		if(componentId == OPTION_1){
		player.heal(10000);
		}
		//items spawns
		if(componentId == OPTION_2){
		 sendOptionsDialogue("Items spawn", "Ranged stuff", "Mage stuff", "Melee Stuff", "Misc","Back");
			stage = 2; 	  
		}
		//events manager
		if(componentId == OPTION_3){
		 sendOptionsDialogue("Events manager", "Vote message", "wildy event", "Ping event", "Boss spawn","Back");
			stage = 10; 	  
		   
		}
		//teleports
		if(componentId == OPTION_4) {
		  sendOptionsDialogue("Teleports", "(home)Portal teleports", "Force home", "all to me", "","Back");
			stage = 20;  
		}
		//controlers
		else if (componentId == OPTION_5) {
		player.getDialogueManager().startDialogue("ModPanel");
			
			}
		}
		
		/**
		  * Items spawning
		  **/
		else if (stage == 2){
			if(componentId == OPTION_1){
		sendOptionsDialogue("Ranged items", "90+", "70+", "40+", "arrows","Back");
			stage = 3; 	  
			}
          if(componentId == OPTION_2){
		sendOptionsDialogue("mage items", "90+", "70+", "40+", "Runes","Back");
			stage = 4; 	  
			}	
         if(componentId == OPTION_3){
		sendOptionsDialogue("Melee items", "90+", "70+", "40+", "","Back");
			stage = 5; 	  
			}	
          if(componentId == OPTION_4){
		sendOptionsDialogue("Misc", "Max cash", "all rares", "Food", "points","Back");
			stage = 6; 	  
			}			
		}
		//Ranged
		else if (stage == 3) {
			//90+
		if(componentId == OPTION_1){
		player.getInventory().addItem(20147, 1);
		player.getInventory().addItem(20151, 1);
		player.getInventory().addItem(20155, 1);
		player.getInventory().addItem(13742, 1);
		player.getInventory().addItem(9245, 100000);
		player.getInventory().addItem(22348, 1);
		player.getInventory().addItem(6585, 1);
		player.getInventory().addItem(21790, 1);
		end();
		  }	
         //70+
		if(componentId == OPTION_2){
		player.getInventory().addItem(11718, 1);
		player.getInventory().addItem(11720, 1);
		player.getInventory().addItem(11722, 1);
		player.getInventory().addItem(18357, 1);
		player.getInventory().addItem(9245, 100000);
		player.getInventory().addItem(10499, 1);
		player.getInventory().addItem(6585, 1);
		player.getInventory().addItem(21790, 1);
		end();
		  }	 
		}
		//melee
		//90+
		else if (stage == 5) {
		if(componentId == OPTION_1){
		player.getInventory().addItem(20135, 1);
		player.getInventory().addItem(20139, 1);
		player.getInventory().addItem(20143, 1);
		player.getInventory().addItem(13740, 1);
		player.getInventory().addItem(21787, 1);
		player.getInventory().addItem(6585, 1);
		player.getInventory().addItem(26579, 1);
		player.getInventory().addItem(20769, 1);
		end();
		  }	else if (componentId == OPTION_2){
	    player.getInventory().addItem(11724, 1);
		player.getInventory().addItem(11726, 1);
		player.getInventory().addItem(11732, 1);
		player.getInventory().addItem(18349, 1);
		player.getInventory().addItem(18353, 1);
		player.getInventory().addItem(6585, 1);
		player.getInventory().addItem(10828, 1);
		player.getInventory().addItem(23639, 1);
		
		  }
      else  if(componentId == OPTION_4){
       player.getInventory().addItem(9075, 50000);
	   player.getInventory().addItem(560, 50000);
	   player.getInventory().addItem(557, 50000);
	   player.getInventory().addItem(565, 250000);
	   player.getInventory().addItem(555, 250000);
	   }
		}
		//others
		
		else if (stage == 6) {
			//cash
		if(componentId == OPTION_1){
		player.getInventory().addItem(995, 2147000000);
	    }	//rares
      else  if(componentId == OPTION_2){
			//phat's
		for (int i = 1038; i <= 1050; i += 2) {
					player.getInventory().addItem(i, 1);
				}
		player.getInventory().addItem(962, 1); //Cracker
		player.getInventory().addItem(1053, 1); //Green hw
		player.getInventory().addItem(1055, 1);//blue hw
		player.getInventory().addItem(1057, 1);//red hw
		} //food
		else if(componentId == OPTION_3){
		player.getInventory().addItem(15272, 20);
		player.getInventory().addItem(15332, 1);
		player.getInventory().addItem(23399, 4);
		player.getInventory().addItem(23351, 3);
		  }  
		  //points
		 else if(componentId == OPTION_4){
		
		  }  
		}
		/**
		  *  Events
		  **/
		else if (stage == 10){
		if(componentId == OPTION_1){
		World.sendWorldMessage("<img=1><col=f36e4b><shad=000000>[Server] We need you, please vote for our server by doing ::vote and get some nice rewards!", true);
		end();
		}if(componentId == OPTION_2){
		 sendOptionsDialogue("Wildy event", "Bane ore", "Lava sharks", "Chest", "Stryke wyrm","Back");
		 stage = 11;
		}if(componentId == OPTION_3){
			
		  }
		}
		//wildy events
		else if (stage == 11) {
		if(componentId == OPTION_1){
        sendOptionsDialogue("Activate a bane ore?", "Yes", "No");
		stage = 12;
		}if(componentId == OPTION_2){
        sendOptionsDialogue("Activate a lava shark spot?", "Yes", "No");
		stage = 13;
		}if(componentId == OPTION_3){
        sendOptionsDialogue("Activate a rare chest?", "Yes", "No");
		stage = 14;
		}if(componentId == OPTION_4){
        sendOptionsDialogue("Activate a stryke wyrm?", "Yes", "No");
		stage = 15;
		}if(componentId == OPTION_5){
       player.getDialogueManager().startDialogue("AdminPanel");	
		}
		
		
		}
		/**
		  *  Teleports
		  **/
		else if (stage == 20) {
		if(componentId == OPTION_1){
		player.getDialogueManager().startDialogue("TeleportCrystal");	
		}if(componentId == OPTION_2){
		player.getControlerManager().forceStop();
		//player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		player.getPackets().sendGameMessage("You have force stoped yourself");
		end();
		}if(componentId == OPTION_3){
			
		  }
		}
		 
		 
}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	} 
		


	

}