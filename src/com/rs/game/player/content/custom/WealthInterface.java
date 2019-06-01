package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
import com.rs.utils.Utils;

/**
 * 
 * @author Paolo
 *
 */
public class WealthInterface {
	
	private static boolean checked1;     
	private static boolean checked2;     
	public static int Inter = 3006;
	
	           
			/*	long grandexchangeValue = GrandExchange.getTotalOfferValues(player);
				long collectionValue = GrandExchange.getTotalCollectionValue(player);*/
    /**
	  * Use this methode to call up the interface
	  **/
	public static void sendInterface(Player player) {
		long moneyPouch = player.getMoneyPouch().getTotal();;
		long bankValue = player.getBank().getBankValue();
	    long inventoryValue = player.getInventory().getInventoryValue();
	    long equipmentValue = player.getEquipment().getEquipmentValue();
	   // long BobValue = player.getFamiliar().grabValue();
	    long totalValue = 0;
	    player.closeInterfaces();
	    player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 34 : 0, Inter);
			//player.getInterfaceManager().sendInterface(Inter);
	        player.getPackets().sendIComponentText(Inter, 19, "      Include donator");
	        player.getPackets().sendIComponentText(Inter, 20, "   Include untradeable");
            player.getPackets().sendItemOnIComponent(Inter, 15, 1038, 1);
	        player.getPackets().sendItemOnIComponent(Inter, 16, 1040, 1);	
			player.getPackets().sendIComponentText(Inter, 4, Utils.formatDoubledAmount(moneyPouch)); //
			player.getPackets().sendIComponentText(Inter, 3, Utils.formatDoubledAmount(bankValue));
			player.getPackets().sendIComponentText(Inter, 2, Utils.formatDoubledAmount(inventoryValue));
			player.getPackets().sendIComponentText(Inter, 1, Utils.formatDoubledAmount(equipmentValue)); //getBobValue
		 //   player.getPackets().sendIComponentText(Inter, 5, Utils.formatDoubledAmount(BobValue)); 
		/*	player.getPackets().sendIComponentText(Inter, 59, Utils.formatDoubledAmount(grandexchangeValue));
			player.getPackets().sendIComponentText(Inter, 60, Utils.formatDoubledAmount(collectionValue));*/
			 totalValue = bankValue + inventoryValue + equipmentValue + moneyPouch /*+ collectionValue + grandexchangeValue*/;
			player.getPackets().sendIComponentText(Inter, 11, Utils.formatDoubledAmount(totalValue));	//		

	}
	
	/**
	  * Handler the buttons
	  	player.getPackets().sendHideIComponent(Inter, 27, false); //unchecks the button
	player.getPackets().sendHideIComponent(Inter, 26, false); //unchecks the button
	  **/
	 public static void handleButtons(Player player, int componentId) {
		  if (componentId == 14) {	
			 if( checked1 == false) {
				 player.getPackets().sendHideIComponent(Inter, 27, false);	  
				 checked1 = true;
			  } else {
				  player.getPackets().sendHideIComponent(Inter, 27, true);	 
				  checked1 = false;
			  }
		        }
		  if (componentId == 12) {	
			  if( checked2 == false) {
					 player.getPackets().sendHideIComponent(Inter, 26, false);	
					 checked2 = true;
				  } else {
					  player.getPackets().sendHideIComponent(Inter, 26, true);	 
					  checked2 = false;
				  }
		        }
		  if (componentId == 3) {	
         
		        }
	        if (componentId == 4) {	
	       
	        }
			if (componentId == 5) {	
	        
	        }
			if (componentId == 6) {	
	       
	        }
			if (componentId == 7) {	
	        
	        }
			if (componentId == 8) {	

	        }
			if (componentId == 9) {	
	       
	        }
			if (componentId == 10) {	
	      
	        }
			if (componentId == 11) {	
	      
	        }
			if (componentId == 12) {	
	       
	        }
			if (componentId == 13) {	
	      
	        }
			if (componentId == 14) {	
	       
	        }
			if (componentId == 15) {	
	        
	        }
			if (componentId == 16) {	
	        	player.sm("Not done sorry.");
	        }
			if (componentId == 17) {	
	       
	        }
			if (componentId == 18) {	
	        
	        }
			if (componentId == 19) {	
	        	player.sm("Not done.");
	        }
			if (componentId == 20) {	
	        
	        }
			if (componentId == 21) {	
	      
	        }
			if (componentId == 22) {	
			
	        }
			if (componentId == 24) {	
	        
	        }
			if (componentId == 25) {	
	        player.closeInterfaces();	
	        }
			
			
	 }

}