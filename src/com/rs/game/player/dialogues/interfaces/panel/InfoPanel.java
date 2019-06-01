package com.rs.game.player.dialogues.interfaces.panel;

import com.rs.game.player.dialogues.Dialogue;
/**
 * @author paolo
 */


public class InfoPanel extends Dialogue {

	

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Info panel","Cape counter", "Drop counter", "send ticket to staff", "message settings", "more");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
		player.getInterfaceManager().sendCapeCounting();
		end();
		} else if(componentId == OPTION_2) {
		sendOptionsDialogue("Drop counter","Personal drop counter", "Global drop counter");	
		stage = 25;
		} else if(componentId == OPTION_3) {
		player.getDialogueManager().startDialogue("TicketHelp");
		} else if(componentId == OPTION_4) {
		  player.getDialogueManager().startDialogue("News");
		} else if(componentId == OPTION_5) {
		sendOptionsDialogue("Info panel","Private mode", "Key binds", "Interfaces", "Drop options", "Lootbeam options");
		stage = 10;
		}
	 } else if(stage == 2) {
		 if(componentId == OPTION_1) {
		 player.privateMode = false;
		 player.sm("You have Disabled private mode.");
		 end();
		 } else if(componentId == OPTION_2) {
			sendDialogue("When private mode is active players can't view your stats and spectate you."); 
			stage = 1;
		 }
	 }  else if(stage == 3) {
		 if(componentId == OPTION_1) {
		 player.privateMode = true;
		 player.sm("You have enabled private mode.");
		 end();
		 } else if(componentId == OPTION_2) {
			sendDialogue("When private mode is active players can't view your stats and spectate you."); 
			stage = 100;
		 }
	 } else if( stage == 10) {
		 if(componentId == OPTION_1) {
		 if (player.privateMode == true) {
	      sendOptionsDialogue("Private mode","Disable private mode", "What is private mode?", "Nothing.");
	      stage = 2;
		} else {
		sendOptionsDialogue("Private mode","Enable private mode", "What is private mode?", "Nothing.");	
		stage = 3;
		}
	 } else if(componentId == OPTION_2) {
		 if(player.useKeyBinds == true) {
		sendOptionsDialogue("Key binds","Remove key binds", "Custom key binds");
		stage = 11;
		 } else if(player.useKeyBinds == false) {
		sendOptionsDialogue("Key binds","Activate key binds", "Custom key binds");
		stage = 12;	 
		 }
	 }else if(componentId == OPTION_3) {
		sendOptionsDialogue("Interface settings","Potion timers", "Monster info", "Back");
		stage = 13;
	 }else if(componentId == OPTION_4) {
		 if(player.showDropWarning == true){
		sendOptionsDialogue("Drop options","Disable drop warnings", "Set warning value", "Back");
		stage = 20;
		 }else {
		sendOptionsDialogue("Drop options","Enable drop warnings", "Set warning value", "Back");
		stage = 21;	
		}
	 } else if (componentId == OPTION_5)
		 player.getDialogueManager().startDialogue("LootBeam");
	 }
	 //keybinds
	 else if (stage == 11) {
		if(componentId == OPTION_1) { 
		player.useKeyBinds = false;
		player.sm("Key binds are now disabled.");
		end();
		}
	 }else if (stage == 12) {
		if(componentId == OPTION_1) { 
		player.useKeyBinds = true;
		player.sm("Key binds are now enabled.");
		end();
		}
	 }
	 //potion timers & monstyer info
	 else if (stage == 13) {
		if(componentId == OPTION_1) { 
		if(player.showPotionTimers == true)
		sendOptionsDialogue("Potion timers","Disable potion timers", "back");
	    else 
		sendOptionsDialogue("Potion timers","Enable potion timers", "back");	
		stage = 14;
		} else if(componentId == OPTION_2) { 
		if(player.shownPvmInfo == true)
		sendOptionsDialogue("Monser info","Disable monster info", "back");
	    else 
		sendOptionsDialogue("Potion timers","Enable monster info", "back");	
		stage = 15;
		}else if(componentId == OPTION_3) { 
		sendOptionsDialogue("Info panel","Private mode", "Key binds", "Interfaces");
		stage = 10;
		}
	 } 
	 //potion timers
	 else if (stage == 14) {
		if(componentId == OPTION_1) { 
		if(player.showPotionTimers == true){
		 player.showPotionTimers = false;
		 player.sm("Potion timers are now disabled.");
		 end();
	    }else {
		 player.showPotionTimers = true;
		 player.sm("Potion timers are now enabled.");
		 end();
		}
		} else if(componentId == OPTION_2) {  
		sendOptionsDialogue("Interface settings","Potion timers", "Monster info");
		stage = 13;
		}
	 }
	 //monster info
	 else if (stage == 15) {
		if(componentId == OPTION_1) { 
		if(player.shownPvmInfo == true){
		 player.shownPvmInfo = false;
		 player.sm("Monser info interface is now disabled.");
		 end();
	    }else {
		 player.shownPvmInfo = true;
		 player.sm("Monster info interface is now enabled.");
		 end();
		}
		} else if(componentId == OPTION_2) {  
		sendOptionsDialogue("Interface settings","Potion timers", "Monster info");
		stage = 13;
		}
	 }
	 //drop warnings
	  else if (stage == 20) {
		if(componentId == OPTION_1) { 
		player.showDropWarning = false;
		player.sm("Drop warnings disabled.");
		end();
		} else if(componentId == OPTION_2) { 
		player.getTemporaryAttributtes().put("dropValue", Boolean.TRUE);
			player.getPackets().sendRunScript(108, new Object[] { "Enter your value"});
		}
	 }else if (stage == 21) {
		if(componentId == OPTION_1) { 
		player.showDropWarning = true;
		player.sm("Drop warnings enabled.");
		end();
		}
		 else  if(componentId == OPTION_2) { 
		player.getTemporaryAttributtes().put("dropValue", Boolean.TRUE);
			player.getPackets().sendRunScript(108, new Object[] { "Enter your value"});
		}
	 }
	 else if (stage == 25){
		if(componentId == OPTION_1) { 
		player.viewGlobalCount = false;
		player.getInterfaceManager().sendPersonalItemCounting1();
		end();
		}
		 else  if(componentId == OPTION_2) { 
		 player.viewGlobalCount = true;
		player.getInterfaceManager().sendGlobalItemCounting();	
        end();		
		 }		 
	 } else if(stage == 100)
		 end();
		
	}

	@Override
	public void finish() {
		
	}
	
}