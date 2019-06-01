package com.rs.game.player.dialogues.impl.donator;

import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Colors;

/**
 * 
 * @author Thomas	
 *
 */
public class DonationDivinePlusTokenManager extends Dialogue {

	int npcId = 5987;
	@Override
	public void start() {
			sendNPCDialogue(npcId, 9827, "Welcome "+player.getDisplayName()+", what may I do for you?");
	}
	

	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == -1){
			sendOptions("Select an option","Token shops","Donator settings", "Teleports");
			stage = 0;
		}
		else if (stage == 0) {
			if(componentId == OPTION_1){
			sendOptions("Select an option", "Daily donator tokens: "+player.dailyDonatorTokens+"", "Bought donator points: "+player.getPointsManager().getDonatorTokens());
			stage = 1;
			}else if(componentId == OPTION_2){
				sendOptions("Select an option", 
						"Set the donatorzone as spawn place "+(player.donatorDivineRespawn ? Colors.green+"Enabled" : Colors.red+"Disabled")+"", 
						"Donator icon "+(player.showIcon ? Colors.green+"Enabled" : Colors.red+"Disabled")+"",
						"Yell color : <col="+player.getYellColor()+">click to change");
				stage = 10;
			} else {
				sendOptions("Select an option", "Donator grenwall area", "Celestial dragons");
				stage = 5;
			}
		} else if (stage == 1) {
			if(componentId == OPTION_1){
			sendOptions("Options", 
					"Double exp token (1)", 
					"10 mil gold (1)",
					"5 Presents (1)",
					"Cosmetic point (1)",
					"5 spins (1)");
			stage = 20;
			} else {
				sendOptionsDialogue("Choose an option. current points:"+player.getPointsManager().getDonatorTokens(),
						"Bank command (20 points)",
						"50k power tokens (10 points)",
						"10 perk points (10 points)");
						stage = 2;	
			}
		}
		else if(stage == 2){
			if(componentId == OPTION_1){
				if(player.canbank){
					sendNPCDialogue(npcId, 9827, "You already unlocked this.");
					stage = 99;
				}
				else if(player.getPointsManager().getDonatorTokens() >= 20){
					player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() -20);
					sendNPCDialogue(npcId, 9827, "Have fun banking.");
					player.canbank = true;
					stage = 99;
				} else {
					sendNPCDialogue(npcId, 9827, "You don't enough points for that.");
					stage = 99;
				}
			} else if(componentId == OPTION_2){
				if(player.getPointsManager().getDonatorTokens() >= 10){
					player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() -10);
				    player.getPointsManager().setPowerTokens(player.getPointsManager().getPowerTokens() + 50000);
					sendNPCDialogue(npcId, 9827, "Here you go.");
				} else {
					sendNPCDialogue(npcId, 9827, "You don't enough points for that.");
					stage = 99;
				}
			}else if(componentId == OPTION_3){
				if(player.getPointsManager().getDonatorTokens() >= 10){
					player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() -10);
				    player.getPointsManager().setPerkPoints(player.getPointsManager().getPerkPoints() + 10);
					sendNPCDialogue(npcId, 9827, "Here you go.");
				} else {
					sendNPCDialogue(npcId, 9827, "You don't enough points for that.");
					stage = 99;
				}
			}
		}
		/**
		 * teleports
		 */
		else if(stage == 5){
			if(componentId == OPTION_1){
				player.setNextWorldTile(new WorldTile(1439,5913,0));
				end();
			} else if(componentId == OPTION_2){
				player.setNextWorldTile(new WorldTile(2292,5970,0));
				end();
			}
		}
		else if (stage == 99)
			end();
		/**
		 * donator settings
		 */
		else if(stage == 10){
			if(componentId == OPTION_1){
				if(player.donatorDivineRespawn){
					player.donatorDivineRespawn = false;
					sendOptions("Select an option", 
							"Set the donatorzone as spawn place "+(player.donatorDivineRespawn ? Colors.green+"Enabled" : Colors.red+"Disabled")+"", 
							"Donator icon "+(player.showIcon ? Colors.green+"Enabled" : Colors.red+"Disabled")+"",
							"Yell color : <col="+player.getYellColor()+">click to change");
					stage = 10;
				} else {
					player.donatorDivineRespawn = true;
					sendOptions("Select an option", 
							"Set the donatorzone as spawn place "+(player.donatorDivineRespawn ? Colors.green+"Enabled" : Colors.red+"Disabled")+"", 
							"Donator icon "+(player.showIcon ? Colors.green+"Enabled" : Colors.red+"Disabled")+"",
							"Yell color : <col="+player.getYellColor()+">click to change");
					stage = 10;
				}
			} else if(componentId == OPTION_2){
				if(player.showIcon){
					
					player.showIcon = false;
					sendOptions("Select an option", 
							"Set the donatorzone as spawn place "+(player.donatorDivineRespawn ? Colors.green+"Enabled" : Colors.red+"Disabled")+"", 
							"Donator icon "+(player.showIcon ? Colors.green+"Enabled" : Colors.red+"Disabled")+"",
							"Yell color : <col="+player.getYellColor()+">click to change");
					stage = 10;
				} else {
					player.showIcon = true;
					sendOptions("Select an option", 
							"Set the donatorzone as spawn place "+(player.donatorDivineRespawn ? Colors.green+"Enabled" : Colors.red+"Disabled")+"", 
							"Donator icon "+(player.showIcon ? Colors.green+"Enabled" : Colors.red+"Disabled")+"",
							"Yell color : <col="+player.getYellColor()+">click to change");
					stage = 10;
				}
					
			} else if(componentId == OPTION_3){
				player.getPackets().sendInputNameScript("Enter here your hex color code");
				player.getTemporaryAttributtes().put("yellcolor", Boolean.TRUE);
			}
		} 
		/*
		 * daily tokens
		 */
		else if(stage == 20){
			if(componentId == OPTION_1){
				if(player.dailyDonatorTokens >= 1){
					player.getInventory().addItem(new Item(24952,1));
					sendNPCDialogue(npcId, 9827, "Here you go.");
					player.dailyDonatorTokens--;
					stage = 21;
				} else {
					sendNPCDialogue(npcId, 9827, "You don't have enough tokens for this, tomorow you'll receive more.");
					stage = 21;
				}
			} else if(componentId == OPTION_2){
				if(player.dailyDonatorTokens >= 1){
					player.getInventory().addItem(new Item(995,10000000));
					sendNPCDialogue(npcId, 9827, "Here you go.");
					player.dailyDonatorTokens--;
					stage = 21;
				} else {
					sendNPCDialogue(npcId, 9827, "You don't have enough tokens for this, tomorow you'll receive more.");
					stage = 21;
				}
			}if(componentId == OPTION_3){
				if(player.dailyDonatorTokens >= 1){
					player.getInventory().addItem(new Item(6542,5));
					player.dailyDonatorTokens--;
					sendNPCDialogue(npcId, 9827, "Here you go.");
					stage = 21;
				} else {
					sendNPCDialogue(npcId, 9827, "You don't have enough tokens for this, tomorow you'll receive more.");
					stage = 21;
				}
			}if(componentId == OPTION_4){
				if(player.dailyDonatorTokens >= 1){
					sendNPCDialogue(npcId, 9827, "Not yet added, will come soon.");
					stage = 21;
				} else {
					sendNPCDialogue(npcId, 9827, "You don't have enough tokens for this, tomorow you'll receive more.");
					stage = 21;
				}
			}if(componentId == OPTION_5){
				if(player.dailyDonatorTokens >= 1){
					player.getInventory().addItem(new Item(24154,5));
					player.dailyDonatorTokens--;
					sendNPCDialogue(npcId, 9827, "Here you go.");
					stage = 21;
				} else {
					sendNPCDialogue(npcId, 9827, "You don't have enough tokens for this, tomorow you'll receive more.");
					stage = 21;
				}
			}
		} else if(stage == 21){
			sendOptions("Options : tokens : "+player.dailyDonatorTokens+"", 
					"Double exp token (1)", 
					"10 mil gold (1)",
					"5 Presents (1)",
					"Cosmetic point (1)",
					"5 spins (1)");
			stage = 20;
		}
	}

	@Override
	public void finish() {

	}

}
