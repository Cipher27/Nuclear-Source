package com.rs.game.player.dialogues;

import com.rs.game.item.Item;
import com.rs.game.player.Skills;

public class TheMaxer extends Dialogue {
	
    /**
	  * Made by Paolo, dialogue from the guy who sells the 5bil cape
	  **/

	@Override
	public void start() {
		sendPlayerDialogue(9775, "Euuuuu... excuse me sir.");
		stage = -1;
	}

@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
		sendNPCDialogue(3373, 9827, "MUST NOT WASTE EXP!");
		stage = 0;
		} else if (stage == 0) {
		sendPlayerDialogue(9775, "Okay man, I just want to ask how you get that cape..");
		stage = 1;
		} else if (stage == 1) {
		sendNPCDialogue(3373, 9827, "Oooh, you need 99 in every skill.");	
		stage = 2;
		} else if (stage == 2) {
		sendPlayerDialogue(9775, "oow nice.");
		stage = 3;
		} else if (stage == 3) {
			sendOptionsDialogue("Choose an option.", "give me the cape, i'm worth it.", "Ask about the Expert capes of Accomplishment ");
			stage = 4;
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
			 sendNPCDialogue(3373, 9827, "You can get one from the stand next to the dwarven cannon.");	
			stage = 5;
			 }
		else if (componentId == OPTION_2) {
				sendOptions("Select an option", "Artisan's cape", "Combatant's cape", "Gatherer's cape", "Support cape", "Master expert cape");
			stage = 10;
			}
		} else if (stage == 5 ){
		end();
	} else if (stage == 10) {
		if(componentId == OPTION_1){
			if(canClaimArtisan()){
				
			} else {
				sendNPCDialogue(3373, 9827, "I'm sorry but you don't have the required expierence for this cape.");	
				player.sm("You need 120 cooking, construction, crafting, firemaking, fletching, herblore, runecrafting and smithing.");
			stage = 5;
			}
		} else if(componentId == OPTION_2){
			if(canClaimCombatant()){
				
			} else {
				sendNPCDialogue(3373, 9827, "I'm sorry but you don't have the required expierence for this cape.");	
				player.sm("You need 120 attack, constitution, defence, magic, prayer, ranged, strength and summoning.");
			stage = 5;
			}
		}else if(componentId == OPTION_3){
			if(canClaimGather()){
				
			} else {
				sendNPCDialogue(3373, 9827, "I'm sorry but you don't have the required expierence for this cape.");	
				player.sm("You need 120 farming, fishing, hunter, mining and woodcutting.");
			stage = 5;
			}
		}else if(componentId == OPTION_4){
			if(canClaimSupport()){
				
			} else {
				sendNPCDialogue(3373, 9827, "I'm sorry but you don't have the required expierence for this cape.");	
				player.sm("You need 120 agility, dungeoneering, slayer and thieving.");
			stage = 5;
			}
		}else if(componentId == OPTION_5){
			if(canClaimExpert()){
				sendNPCDialogue(3373, 9827, "Here you go.");	
				player.getInventory().addItem(new Item(24,1));
				stage = 5;
			} else {
				sendNPCDialogue(3373, 9827, "I'm sorry but you don't have the required expierence for this cape.");	
				player.sm("You need 120 in every skill to claim this cape.");
			stage = 5;
			}
		}
	}
		
	}
	
	public boolean canClaimArtisan(){
		if(player.getSkills().getXp(Skills.COOKING) >= 104273167 
				&& player.getSkills().getXp(Skills.CONSTRUCTION) >= 104273167
					&& player.getSkills().getXp(Skills.CRAFTING) >= 104273167
					&& player.getSkills().getXp(Skills.FIREMAKING) >= 104273167
					&& player.getSkills().getXp(Skills.FLETCHING) >= 104273167
					&& player.getSkills().getXp(Skills.HERBLORE) >= 104273167
					&& player.getSkills().getXp(Skills.RUNECRAFTING) >= 104273167
					&& player.getSkills().getXp(Skills.SMITHING) >= 104273167)
			return true;
		return false;
	}
	public boolean canClaimCombatant(){
		if(player.getSkills().getXp(Skills.ATTACK) >= 104273167 
				&& player.getSkills().getXp(Skills.DEFENCE) >= 104273167
					&& player.getSkills().getXp(Skills.STRENGTH) >= 104273167
					&& player.getSkills().getXp(Skills.MAGIC) >= 104273167
					&& player.getSkills().getXp(Skills.RANGE) >= 104273167
					&& player.getSkills().getXp(Skills.PRAYER) >= 104273167
					&& player.getSkills().getXp(Skills.HITPOINTS) >= 104273167
					&& player.getSkills().getXp(Skills.SUMMONING) >= 104273167)
			return true;
		return false;
	}
	public boolean canClaimGather(){
		if(player.getSkills().getXp(Skills.FARMING) >= 104273167 
				&& player.getSkills().getXp(Skills.FISHING) >= 104273167
					&& player.getSkills().getXp(Skills.HUNTER) >= 104273167
					&& player.getSkills().getXp(Skills.MINING) >= 104273167
					&& player.getSkills().getXp(Skills.WOODCUTTING) >= 104273167)
			return true;
		return false;
	}
	public boolean canClaimSupport(){
		if(player.getSkills().getXp(Skills.AGILITY) >= 104273167 
				&& player.getSkills().getXp(Skills.SLAYER) >= 104273167
					&& player.getSkills().getXp(Skills.THIEVING) >= 104273167
					&& player.getSkills().getXp(Skills.DUNGEONEERING) >= 104273167)
			return true;
		return false;
	}
	public boolean canClaimExpert(){
	for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
		if (player.getSkills().getXp(i) < 104273167) {
			return false;
			}
		}
		return true;
	}

	@Override
	public void finish() {

	}

}
