package com.rs.game.player.dialogues;

import com.rs.game.item.Item;
import com.rs.game.player.content.BossPetHandler.BossPets;
import com.rs.game.player.content.CosmeticsHandler;
import com.rs.game.player.content.custom.TitleInterface;
import com.rs.utils.ShopsHandler;

public class CosmeticsD extends Dialogue {
	
	/**
	  * Made by Paolo, activates a token that will give you an hour double exp.
	  **/

	 int npcId = 18808;
	@Override
	public void start() {
		sendOptionsDialogue("Cosmetics options",
				"Cosmetics overrides", "Keepsake keys", "Titles", "Pack yak override", "Cosmetic Shop");
				stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == 1){
		if (componentId == OPTION_1) {
		CosmeticsHandler.openCosmeticsHandler(player);
		end();
		} else if (componentId == OPTION_2) {
			sendOptionsDialogue("Keepsake keys",
				"What are those keys?", "Buy one.","reclaim keepsake items");
				stage = 5;
		} else if (componentId == OPTION_3) {
		   TitleInterface.sendInterface(player);
		   end();
		}
		else  if (componentId == OPTION_4) {
		sendOptionsDialogue("Pack yak overrides",
				"Chick'arra", 
				"K'ril tinyroth",
				"General Awwdor",
				"Commander miniana",
				"Next");
				stage = 15;
		} else{
			ShopsHandler.openShop(player,225);
			end();
		}
		} else if(stage == 5){
			if (componentId == OPTION_1) {
			sendNPCDialogue(npcId, NORMAL, "These keys can be used on times to get their looks. Example: you use the the key on a dragon platebody, whenever you wear a random platebody it has the appearance of an dragonplatebody but not that stats of it.");
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("This will cost you 200mil.",
				"Buy a key", "Don't buy a key.");
				stage = 6;
			}else if (componentId == OPTION_3) {
				player.getDialogueManager().startDialogue("ClaimKeepSake");
			}
		} else if(stage == 6){
			if (componentId == OPTION_1) {
			if(player.getInventory().containsItem(995,200000000)){
				 player.getInventory().deleteItem(995,200000000);
				 player.getInventory().addItem(new Item(25430,1));
				 sendNPCDialogue(npcId, NORMAL, "Here you go.");
				 stage = 99;
			} else {
				player.sm("You don't have enough money.");
				end();
			}
			} else if (componentId == OPTION_2) {
				end();
			}
		} else if (stage == 15){
		if (componentId == OPTION_1) {
			if (player.collectedPets.contains(BossPets.ARMADYL)) 
			player.setNpcOverrideId(22550);
		else { player.sm("You need to unlock this boss pet first");                            end(); }
		} else if (componentId == OPTION_2) {
			if (player.collectedPets.contains(BossPets.ZAMORAK)) 
			player.setNpcOverrideId(22551);
		else { player.sm("You need to unlock this boss pet first");                            end(); }
		} else if (componentId == OPTION_3) {
			if (player.collectedPets.contains(BossPets.BANDOS)) 
			player.setNpcOverrideId(22552);
		else { player.sm("You need to unlock this boss pet first");                            end(); }
		}else if (componentId == OPTION_4) {
			if (player.collectedPets.contains(BossPets.SARADOMIN)) 
			player.setNpcOverrideId(22553);
		else { player.sm("You need to unlock this boss pet first");                            end(); }
		}else if (componentId == OPTION_5) {
		  sendOptionsDialogue("Select an option",
				"Nexterminator", 
				"Ellie", 
				"Corporeal puppy",
				"Molly",
				"Next");
				stage = 16;
		}
		}else if (stage == 16){
				if (componentId == OPTION_1) {
		player.sm("Todo");
		} else if (componentId == OPTION_2) {
			if (player.collectedPets.contains(BossPets.CHAOS))
			player.setNpcOverrideId(22556);
		else { player.sm("You need to unlock this boss pet first");                            end(); }
		} else if (componentId == OPTION_3) {
			if (player.collectedPets.contains(BossPets.CORP))
			player.setNpcOverrideId(22557);
		else { player.sm("You need to unlock this boss pet first");                            end(); }
		}else if (componentId == OPTION_4) {
			if (player.collectedPets.contains(BossPets.MOLE))
			player.setNpcOverrideId(22558);
		else { player.sm("You need to unlock this boss pet first");                            end(); }
		}else if (componentId == OPTION_5) {
		  sendOptionsDialogue("Select an option",
				"Kalphite grublet", 
				"King Black Dragonling", 
				"Queen Black Dragonling",
				"Prime Hatchling");
				stage = 17;
		}
		}else if (stage == 17){
		  if (componentId == OPTION_1) {
			  if (player.collectedPets.contains(BossPets.KALPHITE_QUEEN))
							player.setNpcOverrideId(22562);
						else 
							player.sm("You need to unlock this boss pet first");
		} else if (componentId == OPTION_2) {
			if (player.collectedPets.contains(BossPets.KBD))
					player.setNpcOverrideId(22563);
				else 
					player.sm("You need to unlock this boss pet first");
		} else if (componentId == OPTION_3) {
			if (player.collectedPets.contains(BossPets.QBD))
					player.setNpcOverrideId(22570);
				else 
					player.sm("You need to unlock this boss pet first");
		}else if (componentId == OPTION_4) {
			if (player.collectedPets.contains(BossPets.DKS_PRIME))
				player.setNpcOverrideId(22571);
			else 
				player.sm("You need to unlock this boss pet first");
		}else if (componentId == OPTION_5) {
		  sendOptionsDialogue("Select an option",
				"Rex hatchling", 
				"Supreme hatchling");
				stage = 18;
		}
		}else if (stage == 18){
			  if (componentId == OPTION_1) {
				  if (player.collectedPets.contains(BossPets.DKS_REX))
						player.setNpcOverrideId(22572);
					else 
						player.sm("You need to unlock this boss pet first");
	} else if (componentId == OPTION_2) {
		if (player.collectedPets.contains(BossPets.DKS_SUPREME))
				player.setNpcOverrideId(22573);
			else 
				player.sm("You need to unlock this boss pet first");
			}
		} else if (stage == 99)
			end();
	}

	@Override
	public void finish() {

	}

}
