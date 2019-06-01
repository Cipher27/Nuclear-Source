package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
/**
 * 
 * @author Paolo
 *
 */
public class TitleInterface {
    /**
     * interface Id
     */
	static int interfaceId = 1412;
	
	/**
	 * sends the interface 
	 * @param player
	 */
	public static void sendInterface(Player player){
		
		
		player.getInterfaceManager().sendInterface(interfaceId);
		//General
		player.getPackets().sendIComponentText(interfaceId, 25, "Titles");
		//1
		player.getPackets().sendIComponentText(interfaceId, 36, "Yt 'Haar "+player.getDisplayName());
		//2
		player.getPackets().sendIComponentText(interfaceId, 44, "Record holder "+player.getDisplayName());
		//3
		player.getPackets().sendIComponentText(interfaceId, 52, "Pet lover "+player.getDisplayName());
		//4 
		player.getPackets().sendIComponentText(interfaceId,60, "Pet collector "+player.getDisplayName());
		//5
		player.getPackets().sendIComponentText(interfaceId, 68, "Grandmaster "+player.getDisplayName());
		//6
		player.getPackets().sendIComponentText(interfaceId, 76, "Final Boss " +player.getDisplayName());
		//7
		player.getPackets().sendIComponentText(interfaceId, 84, player.getDisplayName()+" the MVP");
		//8
		player.getPackets().sendIComponentText(interfaceId, 92, "Salty "+player.getDisplayName());
		//9
		player.getPackets().sendIComponentText(interfaceId,162, player.getDisplayName()+" the Billionaire");
		
		//second tab
		
		player.getPackets().sendIComponentText(interfaceId,100, player.getDisplayName()+" the Maxed");
		player.getPackets().sendIComponentText(interfaceId,108, player.getDisplayName()+" the Completionist");
		player.getPackets().sendIComponentText(interfaceId,116, player.getDisplayName()+" the Knowledgeable");
		player.getPackets().sendIComponentText(interfaceId,116, player.getDisplayName()+"? Who?");
		player.getPackets().sendIComponentText(interfaceId,124, "Community Helper "+player.getDisplayName());
		player.getPackets().sendIComponentText(interfaceId,132, "The Awesome "+player.getDisplayName());
		player.getPackets().sendIComponentText(interfaceId,140, player.getDisplayName()+" of Hellion");
		player.getPackets().sendIComponentText(interfaceId,148, player.getDisplayName()+" the Knowledgeable");
		player.getPackets().sendIComponentText(interfaceId,140, "The Famous "+player.getDisplayName());
		player.getPackets().sendIComponentText(interfaceId,156, "xXx_PookyLover_xXx "+player.getDisplayName());
		
	}
	/**
	 * handles the buttons
	 * @param buttonId
	 * @param player
	 */
	public static void handleButtons(int buttonId, Player player){
		switch(buttonId){
		case 3:
		if(player.hasCompletedFightCaves())
			TitleHandler.set(player, 25);
		else
			player.sm("You need to complete the fight caves before you can use this title.");
		break;
		case 4:
			if(player.brokeRecord)
				TitleHandler.set(player, 63);
			else
				player.sm("You need to broke a record for this title.");
			break;
		case 5:
			if(player.collectedPets.size() > 1)
				TitleHandler.set(player, 61);
			else
				player.sm("you need to collect one boss pet.");
			break;
		case 6:
			if(player.collectedPets.size() > 10)
				TitleHandler.set(player, 62);
			else
				player.sm("You need to collect 10 boss pets for this title.");
			break;
		case 8:
			if(player.isFinalBoss())
				TitleHandler.set(player, 60);
			else
				player.sm("You need to have the final boss achievement completed.");
			break;
		case 9:
			if(player.isInHOF)
				TitleHandler.set(player, 64);
				else 
			 player.sm("You need to be in the Hall Of Fame for this Title.");
			break;
		case 11:
			if(player.isMaxed())
				TitleHandler.set(player, 57);
			else
				player.sm("You need to be maxed.");
			break;
		case 7:
			player.sm("You need to complete all the Achievements.");
			break;
		case 12:
			if(player.isCompletedComp())
				TitleHandler.set(player, 58);
			else
				player.sm("You need to be be comped.");
			break;
		case 14: 
			if(player.isComunityHelper)
				TitleHandler.set(player, 65);
			else
				player.sm("This Title can only be given to players by an Admin.");
			break;
		case 17:
			if(player.getPointsManager().getTotalRiddles() > 1000)
			TitleHandler.set(player, 66);
			else 
				player.sm("You need more than 1000 riddle points for this Title.");
			break;
		case 18:
			TitleHandler.set(player, 59);
			break;
		}
	}
}