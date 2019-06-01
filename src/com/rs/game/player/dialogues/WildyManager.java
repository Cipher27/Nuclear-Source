package com.rs.game.player.dialogues;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.WildernessArtefacts;

public class WildyManager extends Dialogue {
	
	private int npcId = 4731;
	
	/**
	  * Created by Paolo, Handles the wildy events.
	  **/
	
	@Override
	public void start() {
		if (stage == -1) {
		if (World.haswildy_event()) {
			sendNPCDialogue(npcId, 9827, "There's a chest in the wildy, quick loot it!");
			stage = 0; 
		} else {
			 sendNPCDialogue(npcId, 9827, "What can I do for you ?");
			 stage = 0;
		}
		}
		
		
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == 0){
			sendOptionsDialogue("choose an option.","Tell me more about the wilderness events.","Exchange Artifacts", "Teleports", "Wilderness upgrades","Nothing");
			 stage = 1;
		}else if (stage == 1) {
		 if(componentId == OPTION_1){
       sendOptionsDialogue("choose an option.","Wildy chest","Wildy bow", "Artifacts","back");
	   stage = 2;
		 }else if(componentId == OPTION_2){
		WildernessArtefacts.trade(player);
		end();
		} else if(componentId == OPTION_3){
			sendOptionsDialogue("choose an option.","Elder tree","Crystal tree","Lava sharks", "Lava strykewyrms", "Edgville");
			 stage = 30;
		}else if(componentId == OPTION_4){
			sendOptionsDialogue("choose an option.","Increase damage against blink and revenants by 1.5 (15 points)", " Ice gloves(7)" , "Back");
		    stage = 5;
		}else if(componentId == OPTION_5){
			end();
		}
   } 
	 else if (stage == 2) {
		 if(componentId == OPTION_1){
	  sendNPCDialogue(npcId, 9827, "Every 2/3 hour you can find a chest with good rewards somewhere in the wilderness, you can find tips about the location at the noticeboard");
		stage = 20;
		} else  if(componentId == OPTION_2){
			sendNPCDialogue(npcId, 9827, "The wildy bow is the most powerfull bow ingame but can only be used in the wilderness, when you die with it you will lose everything on you. The bow can be found at a random location.");
		stage = 20;
		}else  if(componentId == OPTION_3){
			sendNPCDialogue(npcId, 9827, "Every monster(higher comat = higher chance)/player has a chance of dropping an artifact which you can exchange here for gold and wilderness points. ");
		stage = 20;
		} else {
			sendOptionsDialogue("choose an option.","Tell me more about the wilderness events.","Exchange Artifacts", "Teleports", "Wilderness upgrades","Nothing");
			 stage = 1;
		}
	 }  else if(stage == 5){
		 if(componentId == OPTION_1){
			 if(!player.increasedBlinkDamage){
			 if(player.getPointsManager().getWildernessTokens() >= 15){
				 sendNpc(9827,"Goodluck with fighting blink.");
				 player.increasedBlinkDamage = true;
				 player.getPointsManager().setWildernessTokens(player.getPointsManager().getWildernessTokens() - 15);	 
				 stage = 21;
			 } else {
				 sendNpc(9827,"You need more points to unlock this feature.");
				stage = 1;
			 }
		   } else {
			   sendNpc(9827,"You've already unlocked this feature.");
				stage = 1;
		   }
		 }
		 else if(componentId == OPTION_2){
			 if(player.getPointsManager().getWildernessTokens() >= 7){
				 sendNpc(9827,"Goodluck with fishing.");
		     //		 player.quickShred = true;
				 player.getBank().addItem(new Item(1580),true);
				 player.sm("The gloves have been added to your bank.");
				 player.getPointsManager().setWildernessTokens(player.getPointsManager().getWildernessTokens() - 7);	 
			     stage = 21;
			 } else {
				 sendNpc(9827,"You need more points to unlock this feature.");
				stage = 1;
			 }
		 }
	 }
	  else if (stage == 20) {
	   sendOptionsDialogue("choose an option.","Wildy chest","Wildy bow", "Artifacts","back");
	   stage = 2;
	  }  else if (stage == 21)
		  end();
	   else if (stage ==30) {
	 if(componentId == OPTION_1){
     Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3110, 3794, 0));
	 player.getControlerManager().startControler("Wilderness");
	 end();
	 }  else  if(componentId == OPTION_2){
     Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2975, 3903, 0));
	 player.sm("Run north-west to find the crystal tree");
	 player.getControlerManager().startControler("Wilderness");
	 end();
	 }  else  if(componentId == OPTION_3){
     Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3338, 3930, 0));
	 player.sm("Don't burn your hands!");
	 player.getControlerManager().startControler("Wilderness");
	 end();
	 }    else  if(componentId == OPTION_4){
     Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3033, 3836, 0));
	 player.sm("Tip: if you want to escape us the king black dragon dungeon, trust no one.");
	 player.getControlerManager().startControler("Wilderness");
	 end();
			}
			  else  if(componentId == OPTION_5){
     Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3087,3505, 0));
	 end();
			}
	   }
		
		
   
}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
	}
		
	} 		