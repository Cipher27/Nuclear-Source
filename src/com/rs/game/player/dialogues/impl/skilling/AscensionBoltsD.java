package com.rs.game.player.dialogues.impl.skilling;


import com.rs.game.player.Player;
import com.rs.game.player.actions.Action;
import com.rs.game.player.actions.crafting.AscensionBoltsCrafting;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.dialogues.Dialogue;

public class AscensionBoltsD extends Dialogue {
private int itemId;

	@Override
	public void start() {
		SkillsDialogue.sendSkillsDialogue(player, SkillsDialogue.MAKE,
				"Choose how many you wish to make, then click on the chosen item to begin.", 1,
				new int[] {  28465 }, new SkillsDialogue.ItemNameFilter() {
					@Override
					public String rename(String name) {
						return name;
					}
				});
	}

	@Override
	public void run(int interfaceId, int componentId) {
		int option = SkillsDialogue.getItemSlot(componentId);
		itemId = SkillsDialogue.getItem(option);
		player.getActionManager().setAction(new Action() {
			int ticks;

			@Override
			public boolean start(Player player) {
				if(!player.getInventory().containsItem(28436, 15)){
					player.sm("You need atleast 15 Ascension shards.");
				}
				if (!player.getInventory().containsOneItem(946)) { 
					player.sendMessage("You do not have a knife for this action.");
					return false;
				}
				int moltenGlassQ = player.getInventory().getAmountOf(28436) / 15;
				int requestedAmount = SkillsDialogue.getQuantity(player);
				if (requestedAmount > moltenGlassQ)
					requestedAmount = moltenGlassQ;
				ticks = requestedAmount;
				return true;
			}

			@Override
			public boolean process(Player player) {
				return ticks > 0;
			}

			@Override
			public int processWithDelay(Player player) {
				ticks--;
				AscensionBoltsCrafting.canSmith(player, itemId);
				return 2;
			}

			@Override
			public void stop(Player player) {
				setActionDelay(player, 2);
			}
		});
		end();
	}

	@Override
	public void finish() {  }
}