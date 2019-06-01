package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
/**
 * 
 * @author Xiles
 *
 */
public class WelcomeBoard {
     
	
	public static int AchievementBoard = 205;
	//if (player.informmessages == false) {
	public static void WelcomeInterface(Player player) {
	
	}
	
	public static void sendMining(Player player) {
			player.closeInterfaces();
			player.getPackets().sendIComponentText(AchievementBoard, 61, "Updates");
			player.getPackets().sendIComponentText(AchievementBoard, 57, "Events");
			player.getPackets().sendIComponentText(AchievementBoard, 53, "About us");
			player.getPackets().sendIComponentText(AchievementBoard, 49, "Contact us");
			player.getPackets().sendIComponentText(AchievementBoard, 65, "Welcome to Hellion!");
			player.getPackets().sendIComponentText(AchievementBoard, 64, "Updates:" +
															 "");
			player.getInterfaceManager().sendInterface(AchievementBoard);
	} 
	public static void sendWoodcutting(Player player) {
		player.closeInterfaces();
		player.getPackets().sendIComponentText(AchievementBoard, 61, "Updates");
			player.getPackets().sendIComponentText(AchievementBoard, 57, "Events");
			player.getPackets().sendIComponentText(AchievementBoard, 53, "About us");
			player.getPackets().sendIComponentText(AchievementBoard, 49, "Contact us");
			player.getPackets().sendIComponentText(AchievementBoard, 65, "Welcome to Hellion!");
		player.getPackets().sendIComponentText(AchievementBoard, 64, "-Double exp weekends<br>" +
				"Look at the chat for Pvm/skilling events, those are randomly generated.");
		player.getInterfaceManager().sendInterface(AchievementBoard);
	} 
	
	public static void sendCooking(Player player) {
	player.closeInterfaces();
	player.getPackets().sendIComponentText(AchievementBoard, 61, "Updates");
			player.getPackets().sendIComponentText(AchievementBoard, 57, "Events");
			player.getPackets().sendIComponentText(AchievementBoard, 53, "About us");
			player.getPackets().sendIComponentText(AchievementBoard, 49, "Contact us");
			player.getPackets().sendIComponentText(AchievementBoard, 65, "Welcome to Hellion!");
	player.getPackets().sendIComponentText(AchievementBoard, 64, "Hellion is created by a team containing, Zero gravity with help of Apocalypse and Timelord" +
			"This is our second server, the first server did pretty well we had over 15 active players online everyday." +
			"Why did we quit? Well we changed the source because our old source got ruined and currupted, and now we are done with devolping enjoy. :D");
	player.getInterfaceManager().sendInterface(AchievementBoard);
	} 

	public static void sendFiremaking(Player player) {
	player.closeInterfaces();
	player.getPackets().sendIComponentText(AchievementBoard, 61, "Updates");
	player.getPackets().sendIComponentText(AchievementBoard, 57, "Events");
	player.getPackets().sendIComponentText(AchievementBoard, 53, "About us");
	player.getPackets().sendIComponentText(AchievementBoard, 49, "Contact us");
	player.getPackets().sendIComponentText(AchievementBoard, 65, "Welcome to Hellion!");
	player.getPackets().sendIComponentText(AchievementBoard, 64, "You can always contact us via the Forums." +
			"If you have a big problem you can add us on skype, don't abuse this!" +
			"Paolo skype:       Liam skype:     " );
	player.getInterfaceManager().sendInterface(AchievementBoard);
	} 
	 public static void handleButtons(Player player, int componentId) {
		  if (componentId == 49) {	
		        sendFiremaking(player);
		        }
		  if (componentId == 57) {	
		        sendWoodcutting(player);
		        }
		  if (componentId == 53) {	
		       sendCooking(player);
		        }
	        if (componentId == 61) {	
	        	sendMining(player);
	        }
	 }

}