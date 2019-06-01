package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
import com.rs.game.player.content.BossPetHandler.BossPets;

/**
 * 
 * @author Paolo
 *
 */
public class PetsInterface {
     
	
	
	public static int Inter = 3008;
    /**
	  * Use this methode to call up the interface
	  **/
	public static void sendInterface(Player player) {
		player.getPackets().sendIComponentText(Inter, 27, "Pet Selection");
			player.getInterfaceManager().sendInterface(3008);
				player.getPackets().sendIComponentSprite(3008,2,14041); 
				player.getPackets().sendIComponentSprite(3008,3,14044); 
				player.getPackets().sendIComponentSprite(3008,4,14063); 
				player.getPackets().sendIComponentSprite(3008,5,14052); 
				player.getPackets().sendIComponentSprite(3008,6,14048); 
				player.getPackets().sendIComponentSprite(3008,7,14049); 
				player.getPackets().sendIComponentSprite(3008,8,14050); 
				player.getPackets().sendIComponentSprite(3008,9,14051); 
				player.getPackets().sendIComponentSprite(3008,10,14046); //14046
				player.getPackets().sendIComponentSprite(3008,11,14053); 
				player.getPackets().sendIComponentSprite(3008,12,14054); 
				player.getPackets().sendIComponentSprite(3008,13,14055); 
				player.getPackets().sendIComponentSprite(3008,14,14057); 
				player.getPackets().sendIComponentSprite(3008,15,14059); 
				player.getPackets().sendIComponentSprite(3008,16,14045); 
				player.getPackets().sendIComponentSprite(3008,17,1); 
				player.getPackets().sendIComponentSprite(3008,18,14047); 
								//end bosses
			    player.getPackets().sendIComponentSprite(3008,19,14043);  //abysal
				player.getPackets().sendIComponentSprite(3008,20,14056); //Frost
				player.getPackets().sendIComponentSprite(3008,21,14058); //prototype
				player.getPackets().sendIComponentSprite(3008,22,14060); 
				player.getPackets().sendIComponentSprite(3008,23,14061); 
				player.getPackets().sendIComponentSprite(3008,24,14064); 
				player.getPackets().sendIComponentSprite(3008,25,14065); 		
	}
	
	
	/**
	* handles buttons
	*/ 
	public static void handleButtons(Player player, int componentId) {
		 if (componentId == 2) {	
			 if (player.collectedPets.contains(BossPets.BANDOS)) {
		  if (player.getPetManager().spawnPet(33806, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Bandos.");
		  }
		}
		  else if (componentId == 3) {	
			  if (player.collectedPets.contains(BossPets.ARMADYL)) {
		  if (player.getPetManager().spawnPet(33778, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Armadyl.");
		  }
		}
	        else if (componentId == 4) {	
	        	if (player.collectedPets.contains(BossPets.ZAMORAK)) {
		  if (player.getPetManager().spawnPet(33805, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Zamorak.");
		  }
		}
		   else if (componentId == 5) {	
			   if (player.collectedPets.contains(BossPets.SARADOMIN)) {
		  if (player.getPetManager().spawnPet(33807, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Saradomin.");
		  }
		}
		   else if (componentId == 6) {	
	      player.getDialogueManager().startDialogue("DksOption");
		}
		   else if (componentId == 7) {	
			   if (player.collectedPets.contains(BossPets.KBD)) {
		  if (player.getPetManager().spawnPet(33792, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing the King Black Dragon.");
		  }
		} else if (componentId == 8) {	
			if (player.collectedPets.contains(BossPets.NEX)) {
		  if (player.getPetManager().spawnPet(33808, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Nex.");
		  }
		}
		   else if (componentId == 9) {	
			   if (player.collectedPets.contains(BossPets.QBD)) {
		  if (player.getPetManager().spawnPet(33799, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing the Queen Black Dragon.");
		  }
		}   else if (componentId == 10) {	
			if (player.collectedPets.contains(BossPets.CORP)) {
		  if (player.getPetManager().spawnPet(33786, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing the Corporeal Beast.");
		  }
		}   else if (componentId == 11) {	
			if (player.collectedPets.contains(BossPets.STQ)) {
		  if (player.getPetManager().spawnPet(31795, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Sea Troll Queen.");
		  }
		}   else if (componentId == 12) {	
			if (player.collectedPets.contains(BossPets.KALPHITE_QUEEN)) {
		  if (player.getPetManager().spawnPet(33790, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Kalphite Queen.");
		  }
		}   else if (componentId == 13) {	
			if (player.collectedPets.contains(BossPets.CHAOS)) {
		  if (player.getPetManager().spawnPet(33785, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing the Chaos Elemental.");
		  }
		}   else if (componentId == 14) {	
			if (player.collectedPets.contains(BossPets.KILN)) {
		  if (player.getPetManager().spawnPet(33813, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from kiln.");
		  }
		}   else if (componentId == 15) {	
	        if (player.collectedPets.contains(BossPets.DARK_LORD)) {
		  if (player.getPetManager().spawnPet(31765, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing the Dark Lord.");
		  }
		}   else if (componentId == 16) {	
			if (player.collectedPets.contains(BossPets.BLINK)) {
		  if (player.getPetManager().spawnPet(31457, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Blink.");
		  }
		} else if (componentId == 17) {	
	       
			 player.sm("Coming soon.");
		}else if (componentId == 18) {	
	   /*     if (player.hasDarkBPet) {
		  if (player.getPetManager().spawnPet(7927, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from killing Dark beasts.");
		  }*/
		}else if (componentId == 19) {	
	     /*   if (player.hasAbyssalPet) {
		  if (player.getPetManager().spawnPet(7927, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from Abyssal Demons.");
		  }*/
		}else if (componentId == 20) {	
	     /*  if (player.hasFrostPet) {
		  if (player.getPetManager().spawnPet(31459, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from Frost Dragons.");
		  }*/
		}else if (componentId == 21) {	
	        if (player.isDivineDonator()) {
		  if (player.getPetManager().spawnPet(31559, true)) 
			return;
		  } else {
			 player.sm("This legendary pet can only be obtained by Divine donators.");
		  }
		}else if (componentId == 22) {	
	     /*   if (player.hasFluffyPet) {
		  if (player.getPetManager().spawnPet(31570, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from an elite task.");
		  }*/
		}else if (componentId == 23) {	
			 player.sm("Cat feature will be added soon.");
		}else if (componentId == 24) {	
	   /*     if (player.hasTrollPet) {
		  if (player.getPetManager().spawnPet(23030, true)) 
			return;
		  } else {
			 player.sm("You can unlock this pet from the troll game.");
		  }*/
		}else if (componentId == 25) {	
	     player.getDialogueManager().startDialogue("PetInfo");
		}else if (componentId == 26) {	
		}
	}
	
}