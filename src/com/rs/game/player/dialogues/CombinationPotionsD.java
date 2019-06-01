package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;
import com.rs.game.player.actions.herblore.CombinationPotions;
import com.rs.game.player.actions.herblore.CombinationPotions.Combinations;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.content.SkillsDialogue.ItemNameFilter;

/**
 * 
 * @author Paolo
 *
 */
public class CombinationPotionsD extends Dialogue {
	
	//private Combinations scale;
	private Combinations[] pots;

	@Override
	public void start() {
		pots = (Combinations[]) parameters[1];
		int count = 0;
		int[] ids = new int[pots.length];
		for (Combinations pot : pots)
			ids[count++] = pot.getProduct().getId();
		SkillsDialogue.sendSkillsDialogue(player, SkillsDialogue.MAKE, "Which armour piece would you like to create?", 
				1, ids, new ItemNameFilter() {
			int count = 0;

			@Override
			public String rename(String name) {
				Combinations combination = Combinations.values()[count++];
				if (player.getSkills().getLevel(Skills.CRAFTING) < combination.getLevelRequired())
					name = "<col=ff0000>" + name + "<br><col=ff0000>Level " + combination.getLevelRequired();
				return name;

			}
		});
	}

	@Override
	public void run(int interfaceId, int componentId) {
		int idx = SkillsDialogue.getItemSlot(componentId);
		if (idx > pots.length) {
			end();
			return;
		}
		player.getActionManager().setAction(new CombinationPotions(pots[idx], SkillsDialogue.getQuantity(player)));
		end();
	}

	@Override
	public void finish() {  }
}