package com.rs.game.player.dialogues.impl.pop;


import com.rs.game.player.content.ports.SpecialRewards.RECIPES;
import com.rs.game.player.dialogues.Dialogue;

/**
 * Handles The Partner's (John Strum) dialogue.
 */
public class BarmaidD extends Dialogue {
	// The NPC ID.
	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getPorts().firstTimer) {
			sendNPCDialogue(npcId, NORMAL, "We can talk later, you should really yalk to John.");
		} else if(player.getPorts().getBar() == null) {
			sendNPCDialogue(npcId, SAD, "I hope that one day I can reopen this bar..");
			stage = 99;
		} else {
			sendNPCDialogue(npcId, SAD, "Hallo "+player.getDisplayName()+", what can I do for you ?");
			stage = -1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			sendOptions("Select an option", "Recipe selecting", "Show progress");
			stage = 0;
			break;
		case 0:
			if(componentId == OPTION_1){
			sendOptions("Select an option", "Rocktail soup", "Tetsu", "Death Lotus", "Seasingers");
			stage = 1;
			} else if(componentId == OPTION_2){
				player.getPorts().openCollectedRecipes();
				stage = 99;
			}  else if(componentId == OPTION_3){
				sendNPCDialogue(npcId, SAD, "TODO");
				stage = 99;
			}
			break;
		case 1:
			if(componentId == OPTION_1){
				if(player.getPorts().countRecipeParts(RECIPES.ROCKTAIL_SOUP) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.ROCKTAIL_SOUP);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to rocktail soup.");
				stage = 99;
			} else if(componentId == OPTION_2){
				sendOptions("Select an option", "Tetsu helmet", "Tetsu body", "Tetsu platelegs");
				stage = 2;
			}else if(componentId == OPTION_3){
				sendOptions("Select an option", "Death Lotus Hood", "Death Lotus Chestplate", "Death Lotus Chaps");
				stage = 3;
			}else if(componentId == OPTION_4){
				sendOptions("Select an option", "Seasingers Hood", "Seasingers Robe Top", "Seasingers Robe Bottom");
				stage = 4;
			}
			break;
		/**
		 * tetsu
		 */
		case 2:
			if(componentId == OPTION_1){
				if(player.getPorts().countRecipeParts(RECIPES.TESTU_HELM) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.TESTU_HELM);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Tetsu Helmet.");
				stage = 99;
			} else if(componentId == OPTION_2){
				if(player.getPorts().countRecipeParts(RECIPES.TETSU_BODY) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.TETSU_BODY);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Tetsu Body.");
				stage = 99;
			}else if(componentId == OPTION_3){
				if(player.getPorts().countRecipeParts(RECIPES.TETSU_LEGS) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.TETSU_LEGS);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Tetsu Platelegs.");
				stage = 99;
			}
			break;
		/**
		 * death lotus
		 */
		case 3:
			if(componentId == OPTION_1){
				if(player.getPorts().countRecipeParts(RECIPES.DEATH_LOTUS_HOOD) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.DEATH_LOTUS_HOOD);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Death Lotus hood.");
				stage = 99;
			} else if(componentId == OPTION_2){
				if(player.getPorts().countRecipeParts(RECIPES.DEATH_LOTUS_CHEST) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.DEATH_LOTUS_CHEST);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Death Lotus Body.");
				stage = 99;
			}else if(componentId == OPTION_3){
				if(player.getPorts().countRecipeParts(RECIPES.DEATH_LOTUS_CHAPS) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.DEATH_LOTUS_CHAPS);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Death Lotus Chaps.");
				stage = 99;
			}
			break;
		/**
		 * seasingers
		 */
		case 4:
			if(componentId == OPTION_1){
				if(player.getPorts().countRecipeParts(RECIPES.SEASINGERS_HOOD) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.SEASINGERS_HOOD);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Seasingers hood.");
				stage = 99;
			} else if(componentId == OPTION_2){
				if(player.getPorts().countRecipeParts(RECIPES.SEASINGERS_ROBE_TOP) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.SEASINGERS_ROBE_TOP);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Seasingers Robe Top.");
				stage = 99;
			}else if(componentId == OPTION_3){
				if(player.getPorts().countRecipeParts(RECIPES.SEASINGERS_ROBE_BOTTOM) == 4){
					sendNPCDialogue(npcId, NORMAL, "You already found all the parts for this recipe.");
					stage = 99;
				}
				player.getPorts().setCurrentRecipe(RECIPES.SEASINGERS_ROBE_BOTTOM);
				sendNPCDialogue(npcId, NORMAL, "I've set the current recipe to the Seasingers Robe Bottom.");
				stage = 99;
			}
			break;
		case 99:
			end();
			break;
		}
	}

	@Override
	public void finish() {
	}
}