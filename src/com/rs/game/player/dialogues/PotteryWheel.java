package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.content.SkillsDialogue.ItemNameFilter;

public class PotteryWheel extends Dialogue {

	private static final int[] PRODUCTS = { 1787, 1789, 1791, 5352 };
	private static final int[] LEVELS = { 1, 7, 8, 19 };
	private static final double[] EXPERIENCE = { 6.3, 15, 18, 20 };
	private static final int SOFT_CLAY = 1761;

	@Override
	public void finish() {

	}

	@Override
	public void run(int interfaceId, int componentId) {
		final int componentIndex = SkillsDialogue.getItemSlot(componentId);
		if (componentIndex > PRODUCTS.length) {
			end();
			return;
		}
		player.getActionManager().setAction(new Action() {
			int ticks;

			@Override
			public boolean process(Player player) {
				return ticks > 0;
			}

			@Override
			public int processWithDelay(Player player) {
				ticks--;
				player.setNextAnimation(new Animation(896));
				player.getSkills().addXp(Skills.CRAFTING,EXPERIENCE[componentIndex]);
				player.getInventory().deleteItem(SOFT_CLAY, 1);
				if (player.getDailyTask() != null)
					player.getDailyTask().incrementTask(player, 3,PRODUCTS[componentIndex], Skills.CRAFTING);
				player.getInventory().addItem(PRODUCTS[componentIndex], 1);
				return 4;
			}

			@Override
			public boolean start(Player player) {

				final int clayAmount = player.getInventory().getAmountOf(SOFT_CLAY);
				if (clayAmount == 0) {
					end();
					player.sm("You need soft clay in order to spin any pot.");
					return false;
				}
				final int levelReq = LEVELS[componentIndex];
				if (player.getSkills().getLevel(Skills.CRAFTING) < levelReq) {
					end();
					player.sm("You need a crafting level of " + levelReq
							+ " in order to spin this.");
					return false;
				}
				int requestedAmount = SkillsDialogue.getQuantity(player);
				if (requestedAmount > clayAmount)
					requestedAmount = clayAmount;
				ticks = requestedAmount;
				return true;
			}

			@Override
			public void stop(Player player) {
				setActionDelay(player, 3);
			}
		});
	}

	@Override
	public void start() {
		SkillsDialogue
				.sendSkillsDialogue(
						player,
						SkillsDialogue.MAKE,
						"How many hides would you like to tan?<br>Choose a number, then click the pot to begin.",
						28, PRODUCTS, new ItemNameFilter() {
							int count = 0;

							@Override
							public String rename(String name) {
								final int levelRequired = LEVELS[count++];
								if (player.getSkills()
										.getLevel(Skills.CRAFTING) < levelRequired)
									name = "<col=ff0000>" + name
											+ "<br><col=ff0000>Level "
											+ levelRequired;
								return name;
							}
						});
	}
}
