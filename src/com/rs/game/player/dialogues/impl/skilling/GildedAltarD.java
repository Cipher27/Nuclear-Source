package com.rs.game.player.dialogues.impl.skilling;

import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.content.BonesOnAltar;
import com.rs.game.player.content.BonesOnAltar.Bones;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.dialogues.Dialogue;

public class GildedAltarD extends Dialogue {


	WorldObject object;
	int[] ids;
	@Override
	public void start() {
		object = (WorldObject) parameters[0];
		 ids = new int[Bones.values().length];
		int index = 0;
		for(Bones bone : Bones.values()){
			if(player.getInventory().contains(bone.getBone().getId())){
			ids[index] = bone.getBone().getId();
			index++;
			}
		}
		if(index == 0){
			player.sm("You have nothing to offer.");
				end();
				return;
		}
		
		SkillsDialogue.sendSkillsDialogue(player, SkillsDialogue.OFFER, "Which bones do you want to sacrifice?",
				-1, ids, null, false);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		int slot = SkillsDialogue.getItemSlot(componentId);
		if (slot >= Bones.values().length || slot < 0)
			return;
		Bones bone = BonesOnAltar.isGood(new Item(ids[slot]));
		player.getActionManager().setAction(new BonesOnAltar(object,bone.getBone()));
		end();
	}

	@Override
	public void finish() {

	}

}