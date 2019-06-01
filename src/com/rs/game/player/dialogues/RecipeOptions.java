package com.rs.game.player.dialogues;

import com.rs.game.player.content.RecipesHandler.Recipes;

public class RecipeOptions extends Dialogue {

    private Recipes recipe;
    private int npcId = 20397;
    @Override
    public void start() {
	recipe = (Recipes) parameters[0];
	if(player.getPerkHandler().perks.contains(recipe)){
		player.sm("You have already bought this perk.");
		end();
	} else {
	sendOptionsDialogue("Pick an option : "+recipe.name,
		"View recipe",
		"Description",
		"Unlocking method",
		"Nothing");
	stage = 1;
	}
    }

    @Override
    public void run(int interfaceId, int componentId) {
	 if (stage == 1) {
	if (componentId == OPTION_1) {
	sendNPCDialogue(npcId, NORMAL, ""+recipe.recipe); 	
	stage = 0;
	}else if (componentId == OPTION_2){
		sendNPCDialogue(npcId, NORMAL, ""+recipe.descriprion); 	
		stage = 0;
	}else if (componentId == OPTION_3)
	 sendNPCDialogue(npcId, NORMAL, ""+recipe.getObtainMethod()); 	
		stage = 0;
	}  else if (stage == 0){
		sendOptionsDialogue("Pick an option"+recipe.name,
		"View recipe",
		"Description",
		"Unlocking method",
		"Nothing");
	stage = 1;
	} 
	
    }

    @Override
    public void finish() {

    }
}
