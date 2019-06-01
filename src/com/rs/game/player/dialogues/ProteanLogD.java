package com.rs.game.player.dialogues;


import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Action;
import com.rs.game.player.actions.protean.ProteanLogFletching;
import com.rs.game.player.content.SkillsDialogue;

public class ProteanLogD extends Dialogue {
private int itemId;

	@Override
	public void start() {
		SkillsDialogue.sendSkillsDialogue(player, SkillsDialogue.MAKE,
				"Choose how many you wish to make, then click on the chosen item to begin.", 28,
				new int[] {34528}, new SkillsDialogue.ItemNameFilter() {
					@Override
					public String rename(String name) {
						if (name.equalsIgnoreCase(ItemDefinitions.getItemDefinitions(34528).getName())) {
							return "Protean log";
						} 
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
				if((!player.getInventory().contains(946) && !player.getToolbelt().containsItem(946))){
					player.sm("You need a knife to perform this action.");
					return false;
				}
				int moltenGlassQ = player.getInventory().getAmountOf(ProteanLogFletching.PROTEAN_LOG);
				if (moltenGlassQ == 0) {
					player.getPackets().sendGameMessage("You've ran out of protean logs to work with.", true);
					end();
					return false;
				}
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
				ProteanLogFletching.canBlow(player, itemId);
				return 4;
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