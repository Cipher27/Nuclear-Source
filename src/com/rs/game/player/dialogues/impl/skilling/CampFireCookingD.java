package com.rs.game.player.dialogues.impl.skilling;

import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.actions.Cooking;
import com.rs.game.player.actions.Cooking.Cookables;
import com.rs.game.player.content.BonesOnAltar;
import com.rs.game.player.content.BonesOnAltar.Bones;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.dialogues.Dialogue;

public class CampFireCookingD extends Dialogue {


	private Cookables cooking;
	private WorldObject object;
	int[] ids;
	int test[];

	@Override
	public void start() {
		ids = new int[Cookables.values().length];
		int index = 0;
		for(Cookables cook : Cookables.values()){
			if(player.getInventory().contains(cook.getRawItem())){
				ids[index] = cook.getRawItem().getId();
				index++;
			}
		}
	if(index == 0){
		player.sm("You have nothing to cook.");
			end();
			return;
	}
	 test = new int[index]; 
		for(int i = 0; i < index; i++){
			test[i] = ids[i];
		}
		this.object = (WorldObject) parameters[0];

		SkillsDialogue.sendSkillsDialogue(player, SkillsDialogue.OFFER, "What Would you like to cook?",
				-1, test, null, false);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		int slot = SkillsDialogue.getItemSlot(componentId);
		Cookables cook = Cooking.isCookingSkill(new Item(test[slot]));
		player.getActionManager().setAction(
				new Cooking(object, cook.getRawItem(), player.getInventory().getAmountOf(cook.getRawItem().getId())));
		end();
	}

	@Override
	public void finish() {

	}
}