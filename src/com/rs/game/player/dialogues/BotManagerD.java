package com.rs.game.player.dialogues;


public class BotManagerD extends Dialogue {
	
	
	/**
	  @Paolo
	  just a dialogue where players can buy bots and let them do shit. could have be done a lot better prob, improve it. Idc it works ;)
	  
	  **/
    private int npcId = 4988;

    @Override
    public void start() {
    sendNPCDialogue(npcId, NORMAL, "Heeey psst  "+player.getDisplayName()+", want to make some money without doing anything?"); 
	stage =0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	sendOptionsDialogue("Select a Option",
                 	"Ask her for more information.",
	                "Buy a slave",
					"Manage slave(s)",
					"No thanks!");
					stage = 1;
	}
	/**
	 * frist options
	 */
	else if (stage == 1) {
		if (componentId == OPTION_1) {
			sendNPCDialogue(npcId, NORMAL, "I can sell you a slave, which can gather resources for you or even kill monster if you give him the right stuff."); 
          	stage = 4;    
			}	else if (componentId == OPTION_2) {
			sendNPCDialogue(npcId, NORMAL, "A slave will cost you 500mil."); 
          	stage = 6;    
			}else if (componentId == OPTION_3) {
			if (!player.getSlaveTripHandler().hasSlave) {
			sendNPCDialogue(npcId, NORMAL, "You need a slave first before you can send him to work."); 
          	stage = 10;  
			}else {
				sendOptionsDialogue("Select a Option",
                 	    "Give Task",
	                    " Check time",
						
						"Nothing");
					stage = 21;
	           }
		  } else if (componentId == OPTION_4) {
			  end();
		  }
	} 
	/**
	 * first option talking about slaves
	 */
	else if (stage == 4) {
	sendPlayerDialogue(NORMAL, "Mhhhh, sounds interesting. But something tells me that it's not for free"); 
	stage = 5;
	}
	else if (stage == 5){
	sendNPCDialogue(npcId, NORMAL, "That's right, the cost is only 500m. But I promise you that you earn it back.");
	stage = 6;
	}else if (stage == 6){
	sendOptionsDialogue("Select a Option",
                 	"Pay him for a slave",
	                "Don't buy a slave");
					stage = 7;
	}
	/**
	 * buying the slave
	 */
	else if (stage == 7) {
		if (componentId == OPTION_1) {
			if (player.getSlaveTripHandler().slaveAmounts >2 && !player.isDonator()) {
			sendNPCDialogue(npcId, NORMAL, "You need to be a donator to buy a third slave."); 
            stage = 10;			
			}if (player.getInventory().containsItem(995, 500000000)){
			player.getInventory().deleteItem(995, 500000000);
			player.getSlaveTripHandler().hasSlave = true;
			player.getSlaveTripHandler().slaveAmounts++;
			sendNPCDialogue(npcId, NORMAL, "Here you go, you are now the owner of a slave!"); 
			stage = 10;
			} else {
		    sendNPCDialogue(npcId, NORMAL, "You need 500m to buy a slave from me."); 
			stage = 10;
			}			
			}	else if (componentId == OPTION_2) {
			 sendNPCDialogue(npcId, NORMAL, "Think about it!");
             stage = 10;
			}
	}
	/**
	 * ends
	 */
	else if (stage == 10) {
	end();
	}
	/**
	 * manage your slaves
	 */
	else if (stage == 20){
		 if (componentId == OPTION_1) {
	sendOptionsDialogue("Select a Option",
                 	    "Give Task",
	                    " Check time",
						
						"Nothing");
					stage = 21;
	 }
		 /**
		  * goes back to the normal 
		  */
	else if (componentId == OPTION_2) {
		sendOptionsDialogue("Select a Option",
                 	"Ask him for more information.",
	                "Buy a slave",
					"Manage slave(s)",
					"No thanks!");
					stage = 1;	 
		 }
	}else if (stage == 21) {
		/**
		 * slave managing
		 */
	 if (componentId == OPTION_1) {
		 if (!player.getSlaveTripHandler().hasTask){
			 player.getInterfaceManager().sendInterface(3046);
			player.getSlaveTripHandler().sendFishingInterface();
			end();
		 } 
		 else {
			sendNPCDialogue(npcId, NORMAL, "Your slave is still working, you can give him another task when he's back. You have to wait "+player.getSlaveTripHandler().getTripTimeLeft()+"m.");
             stage = 10;
		 }
	 } else if (componentId == OPTION_2) {
		 if(player.getSlaveTripHandler().hasTask)
			sendNPCDialogue(npcId, NORMAL, "Your slave is still working, you can give him another task when he's back. You have to wait "+player.getSlaveTripHandler().getTripTimeLeft()+"m.");
		 else
			 sendNPCDialogue(npcId,NORMAL, "Your slave has no task yet.");
		 stage = 10;
	 } else if (componentId == OPTION_3) {
		 end();
		/*	sendOptionsDialogue("Select a Option",
                 	    "Upgrade pickaxe",
	                    "Upgrade axe",
						"Upgrase fishing tools",
						"Buy gear & setup");
	stage = 70;		*/		
	 }
	 } else if (stage == 22) {
		  if (componentId == OPTION_1) {
		sendOptionsDialogue("Select a Option",
                 	    "Lobsters",
	                    "Monkfish",
						"sharks",
						"Rocktails");
		stage = 50;
	   } else   if (componentId == OPTION_2) {
		sendOptionsDialogue("Select a Option",
                 	    "Willow trees",
	                    "Maple trees",
						"Yew trees",
						"Magic trees");
		stage = 51;
	   } else   if (componentId == OPTION_3) {
		sendOptionsDialogue("Select a Option",
                 	    "Iron ore",
	                    "Coal ore",
						"Adamant ore",
						"Runite ore");
		stage = 52;
	   }else   if (componentId == OPTION_4) {
		sendOptionsDialogue("Select a Option",
                 	    "Pick a monster",
	                    "Check supplies",
						"Check armour",
						"Back");
		stage = 60;
	   }else   if (componentId == OPTION_5) {
		sendOptionsDialogue("Select a Option",
                 	    "Grey chins",
	                    "Red chins",
						"Grenwall",
						"Back");
						stage = 150;
	   }//fishing pick
	 }else if (stage == 50) {
		  if (componentId == OPTION_1) {
		  player.getSlaveTripHandler().setTask(377);
		  sendNPCDialogue(npcId, NORMAL, "Your slave will now fish for an hour."); 
		  stage = 10;
     } else if (componentId == OPTION_2) {
    	 player.getSlaveTripHandler().setTask(7944);
		   sendNPCDialogue(npcId, NORMAL, "Your slave will now fish for an hour."); 
			stage = 10;
	 }
	  else if (componentId == OPTION_3) {
		  player.getSlaveTripHandler().setTask(383);
		    sendNPCDialogue(npcId, NORMAL, "Your slave will now fish for an hour."); 
			stage = 10;
	 }
	  else if (componentId == OPTION_4) {
		  player.getSlaveTripHandler().setTask(15270);
		    sendNPCDialogue(npcId, NORMAL, "Your slave will now fish for an hour.");  //message aanpassen
			stage = 10;
	 } //woodcutting
      }else if (stage == 51) {
		  if (componentId == OPTION_1) {
		player.getSlaveTripHandler().setTask(1519);
		  sendNPCDialogue(npcId, NORMAL, "Your slave will now cute trees for an hour."); 
		  stage = 10;
     } else if (componentId == OPTION_2) {
    	 player.getSlaveTripHandler().setTask(1517);
		   sendNPCDialogue(npcId, NORMAL, "Your slave will now cute trees for an hour."); 
			stage = 10;
	 }
	  else if (componentId == OPTION_3) {
		  player.getSlaveTripHandler().setTask(1515);
		    sendNPCDialogue(npcId, NORMAL, "Your slave will now cute trees for an hour."); 
			stage = 10;
	 }
	  else if (componentId == OPTION_4) {
		  player.getSlaveTripHandler().setTask(1513);
		    sendNPCDialogue(npcId, NORMAL, "Your slave will now cute trees for an hour."); 
			stage = 10;
	 }//mining
      }else if (stage == 52) {
		  if (componentId == OPTION_1) {
			  player.getSlaveTripHandler().setTask(440);
		  sendNPCDialogue(npcId, NORMAL, "Your slave will now mine for an hour."); 
		  stage = 10;
     } else if (componentId == OPTION_2) {
    	 player.getSlaveTripHandler().setTask(453);
		   sendNPCDialogue(npcId, NORMAL, "Your slave will now mine for an hour."); 
			stage = 10;
	 }
	  else if (componentId == OPTION_3) {
		  player.getSlaveTripHandler().setTask(449);
		    sendNPCDialogue(npcId, NORMAL, "Your slave will now mine for an hour."); 
			stage = 10;
	 }
	  else if (componentId == OPTION_4) {
		  player.getSlaveTripHandler().setTask(451);
		    sendNPCDialogue(npcId, NORMAL, "Your slave will now mine for an hour."); 
			stage = 10;
	 }
      }
	  //hunter
	  else if (stage == 150) {
		  if (componentId == OPTION_1) {
			  player.getSlaveTripHandler().setTask(10033);
		  sendNPCDialogue(npcId, NORMAL, "Your slave will now hunt for an hour."); 
		  stage = 10;
     } else if (componentId == OPTION_2) {
    	 player.getSlaveTripHandler().setTask(10034);
		   sendNPCDialogue(npcId, NORMAL, "Your slave will now hunt for an hour."); 
			stage = 10;
	 }
	  else if (componentId == OPTION_3) {
		  player.getSlaveTripHandler().setTask(12539);
		    sendNPCDialogue(npcId, NORMAL, "Your slave will now hunt for an hour."); 
			stage = 10;
	 }
	  }
	else if (stage == 60) {
		  if (componentId == OPTION_1) {
		sendOptionsDialogue("Select a Option",
                 	/*    ""+ player.getSlaveTripHandler().,
	                    ""+player.botPickName2,
						""+player.botPickName3,*/
						"re-roll (100m)");
		  stage = 100;
     } else if (componentId == OPTION_2) {
		sendNPCDialogue(npcId, NORMAL, "nog te doen!!!!"); 
			stage = 22;
	 }
	  else if (componentId == OPTION_3) {
		
	 }
	  else if (componentId == OPTION_4) {
		 
	 }
      }
	}


    @Override
    public void finish() {

    }
}
