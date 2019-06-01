package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.achievements.impl.ConstructionDummyAchievement;
import com.rs.utils.ShopsHandler;

public class EstateAgent extends Dialogue {
	
	public static int SKILLCAPE = 9748;
	public static int SKILLHOOD = 9749;
	public static int ONE = 1;

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId,9760,"Hello " + player.getUsername() + " how can I help you?");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Select a Option", "May I purchase a house?",
					"Can I see your construction shop?","Can I change the look of my house?","Noting");
			stage = 1;
		} else if (stage == 1) {
		if (componentId == OPTION_1) {
			if (player.hasHouse) {
				sendNPCDialogue(npcId,9760,"You already own a house!");
				stage = 99;
			} else {
				sendNPCDialogue(npcId,9760,"That will be 500,000gp.");
				stage = 2; 
			}
	  } else if (componentId == OPTION_2) {
		  ShopsHandler.openShop(player, 83); 
			}
		/**
		  * To change the look of the walls
		  **/
			else if (componentId == OPTION_3) {
			player.getInterfaceManager().sendInterface(3005);
			player.getPackets().sendIComponentText(3005, 7, "Construction Menu");
			end();
			
			}
		//closes the interface
		else if (componentId == OPTION_4) {
		  end();
			}
		} else if (stage == 2) {
			sendOptionsDialogue("Select a Option", "That's a deal!",
					"No way!");
			stage = 3;
		} 
		//house looks
		else if (stage == 10) {
			 if (componentId == OPTION_1) {
				player.setHouseLook(1);
				sendNPCDialogue(4247, 9843,  "Relog to activate this feature.");
			}else if (componentId == OPTION_2) {
			   player.setHouseLook(2);
				sendNPCDialogue(4247, 9843,  "Relog to activate this feature.");
			}else if (componentId == OPTION_3) {
			   player.setHouseLook(3);
				sendNPCDialogue(4247, 9843,  "Relog to activate this feature.");
			}else if (componentId == OPTION_4) {
				player.setHouseLook(5);
				sendNPCDialogue(4247, 9843,  "Relog to activate this feature.");
			}else if (componentId == OPTION_5) {
				end();
			}
		}
		
		else if(stage == 99)
			end();
		
		
		
		else if (stage == 3) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(995, 500000)) {
					sendEntityDialogue(
							SEND_2_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name,
									"Congratulations, you now own a house!"}, IS_NPC, npcId,
							9760);
					player.getInventory().removeItemMoneyPouch(995, 500000);
					player.hasHouse = true;
					player.getAchievementManager().notifyUpdate(ConstructionDummyAchievement.class);
					end();
				} else {
					sendEntityDialogue(
							SEND_2_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name,
									"I'm sorry but you do not have enough money."}, IS_NPC, npcId,
							9760);
				}
		  } else if (componentId == OPTION_2) {
			  sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name,
								"You are truely missing out."}, IS_NPC, npcId,
						9760);
						end();
				}
		}
	}

	@Override
	public void finish() {

	}

}
