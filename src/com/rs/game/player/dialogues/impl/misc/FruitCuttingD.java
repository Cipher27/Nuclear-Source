package com.rs.game.player.dialogues.impl.misc;

import com.rs.game.player.actions.FruitCutting;
import com.rs.game.player.actions.FruitCutting.CuttableFruit;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.dialogues.Dialogue;

public class FruitCuttingD extends Dialogue {

	CuttableFruit fruit;

	@Override
	public void start() {
		fruit = (CuttableFruit) parameters[0];
		SkillsDialogue
				.sendSkillsDialogue(
						player,
						SkillsDialogue.SELECT,
						"Choose how many you wish to cut,<br>then click on the item to begin.",
						player.getInventory().getItems()
								.getNumberOf(fruit.getFruitId()),
						fruit.getProductIds(), null);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		int option = SkillsDialogue.getItemSlot(componentId);
		if (option > fruit.getProductIds().length) {
			end();
			return;
		}
		int quantity = SkillsDialogue.getQuantity(player);
		int invQuantity = player.getInventory().getItems()
				.getNumberOf(fruit.getFruitId());
		if (quantity > invQuantity)
			quantity = invQuantity;
		player.getActionManager().setAction(
				new FruitCutting(fruit, option, quantity));
		end();
	}

	@Override
	public void finish() {

	}

}
