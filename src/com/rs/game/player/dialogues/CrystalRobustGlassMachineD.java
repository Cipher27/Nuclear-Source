package com.rs.game.player.dialogues;

import com.rs.game.player.actions.CrystalRobustGlassMachine;
import com.rs.game.player.actions.CrystalRobustGlassMachine.MachineData2;
import com.rs.game.player.content.SkillsDialogue;

public class CrystalRobustGlassMachineD extends Dialogue {

	@Override
	public void start() {
		int[] items = new int[parameters.length];
		for (int i = 0; i < items.length; i++)
			items[i] = ((MachineData2) parameters[i]).getFinalProduct();

		SkillsDialogue
				.sendSkillsDialogue(
						player,
						SkillsDialogue.MAKE,
						"Choose how many you wish to make,<br>then click on the item to begin.",
						28, items, null);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		int option = SkillsDialogue.getItemSlot(componentId);
		if (option > parameters.length) {
			end();
			return;
		}
		MachineData2 data = (MachineData2) parameters[option];
		int quantity = SkillsDialogue.getQuantity(player);
		int invQuantity = player.getInventory().getItems()
				.getNumberOf(data.getIngredientId());
		if (quantity > invQuantity)
			quantity = invQuantity;
		player.getActionManager().setAction(new CrystalRobustGlassMachine(data, quantity));
		end();
	}

	@Override
	public void finish() {

	}

}
