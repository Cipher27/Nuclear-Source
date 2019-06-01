package com.rs.game.player.dialogues.interfaces.panel;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.DungeonControler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

/**
 * @author paolo
 */


public class DonatorTab extends Dialogue {

	

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Donator panel","Teleport to the Donator zone", "Show commands", "Open bank", "Donator Benefits");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			if(Wilderness.isAtWild(player)){
					player.sm("You can't teleport.");
					return ;
				}
			if(player.getControlerManager().getControler() instanceof DungeonControler){
					player.sm("You can't teleport.");
					return;
				}
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1813, 5844, 0));
		} else if(componentId == OPTION_2) {
			player.getInterfaceManager().donatorCommands();
		} else if(componentId == OPTION_3) {
			if(player.canbank){
				long currentTime = Utils.currentTimeMillis();
				if (player.getAttackedByDelay() + 10000 > currentTime) {
					player.getPackets().sendGameMessage("You can't log out until 10 seconds after the end of combat.");
					return ;
				}
				if (player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
					player.getPackets().sendGameMessage("You can't log out while performing an emote.");
					return ;
				}
				if (player.lockDelay >= currentTime) {
					player.getPackets().sendGameMessage("You can't log out while performing an action.");
					return ;
				}if(Wilderness.isAtWild(player)){
					player.sm("You can't open your bank here.");
					return ;
				}if(player.getControlerManager().getControler() instanceof DungeonControler){
					player.sm("You can't open your bank here.");
					return;
				}
				player.getBank().openBank();
			} else {
				player.sm("You can unlock this action in the donator shop.");
			}
		} else if(componentId == OPTION_4) {
			player.getInterfaceManager().sendInterface(275);
			player.getPackets().sendIComponentText(275, 1, "Donator info");
			player.getPackets().sendIComponentText(275, 10, "<img=11> <u>Regular Donator");
			player.getPackets().sendIComponentText(275, 11, "Reduced godwars killcount.");
			player.getPackets().sendIComponentText(275, 12, "Start at wave 30 in fightcaves.");
			player.getPackets().sendIComponentText(275, 13, "Start at wave 11 in fightkiln.");
			player.getPackets().sendIComponentText(275, 14, "::yell command.");
			player.getPackets().sendIComponentText(275, 15, "Acces to the donator zone.");
			player.getPackets().sendIComponentText(275, 16, "1 special donator token each day.");
			player.getPackets().sendIComponentText(275, 17, "10 Donator points.");
			player.getPackets().sendIComponentText(275, 18, "Increase daily spins by 1.");
			player.getPackets().sendIComponentText(275, 19, "Daily quickteleports : 100");
			/**
			 * Extreme
			 */
			player.getPackets().sendIComponentText(275, 20, "<img=8><u>Extreme Donator");
			player.getPackets().sendIComponentText(275, 21, "Even more reduced killcount at godwars.");
			player.getPackets().sendIComponentText(275, 21, "Custom legendary pets.");
			player.getPackets().sendIComponentText(275, 22, "25 Donator points");
			player.getPackets().sendIComponentText(275, 23, "Increase daily spins by 3.");
			player.getPackets().sendIComponentText(275, 24, "Daily quickteleports: 250");
			player.getPackets().sendIComponentText(275, 25, "");
			player.getPackets().sendIComponentText(275, 26, "");
			/**
			 * legendary
			 */
			player.getPackets().sendIComponentText(275, 27, "<img=10> <u>Legendary Donator");
			player.getPackets().sendIComponentText(275, 28, "50 Donator points");
			player.getPackets().sendIComponentText(275, 29, "Increase daily spins by 5.");
			player.getPackets().sendIComponentText(275, 30, "Daily quickteleports : unlimited.");
			player.getPackets().sendIComponentText(275, 31, "5 special donator tokens each day.");
			player.getPackets().sendIComponentText(275, 32, "Increased drop rate");
			/**
			 * divine
			 */
			player.getPackets().sendIComponentText(275, 33, "<img=8><u>Divine Donator");
			player.getPackets().sendIComponentText(275, 34, "100 Donator points");
			player.getPackets().sendIComponentText(275, 35, "Increase daily spins by 10.");
			player.getPackets().sendIComponentText(275, 37, "10 special donator tokens each day.");
			player.getPackets().sendIComponentText(275, 38, "Acces to the legendary pet.");
			player.getPackets().sendIComponentText(275, 39, "Acces to a new Donator zone.");
			player.getPackets().sendIComponentText(275, 40, "Even more increased drop rate.");
			player.getPackets().sendIComponentText(275, 41, "No killcount required.");
			/**
			 * Ultimate
			 */
			player.getPackets().sendIComponentText(275, 42, "<img=8><u>Ultimate Donator");
			player.getPackets().sendIComponentText(275, 43, "200 Donator points");
			player.getPackets().sendIComponentText(275, 44, "Increase daily spins by 20.");
			player.getPackets().sendIComponentText(275, 45, "20 special donator tokens each day.");
			player.getPackets().sendIComponentText(275, 46, "Double barrows loot");
			player.getPackets().sendIComponentText(275, 47, "");
		   /**
		    * Sponser , advertise on our website
		    */
			
			for(int i = 46; i < 100; i ++)
				player.getPackets().sendIComponentText(275, i, "");
			
		
		} else if(componentId == OPTION_5) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}