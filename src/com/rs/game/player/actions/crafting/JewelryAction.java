package com.rs.game.player.actions.crafting;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.game.player.actions.crafting.Jewelry.Bling;

public class JewelryAction extends Action {

	private final Bling bling;
	private int numberToMake;

	public JewelryAction(Bling bling, int number) {
		this.bling = bling;
		this.numberToMake = number;
	}

	private boolean checkAll(Player player) {
		if (bling == null || player == null)
			return false;
		if (!player.getInventory().containsItemToolBelt(bling.getMouldRequired().getId(), 1)) {
			player.sm("You need one " + ItemDefinitions.getItemDefinitions(bling.getMouldRequired().getId()).getName().toLowerCase() + " to make that.");
			return false;
		}
		if(!player.getInventory().containsItems(bling.getItemsRequired())){
			player.sm("You do not have the right items.");
			return false;
		}
		if (player.getSkills().getLevel(Skills.CRAFTING) < bling.getLevelRequired()) {
			player.sm("You need " + bling.getLevelRequired() + " crafting to make that.");
			return false;
		}
		return true;
	}

	@Override
	public boolean process(Player player) {
		if (checkAll(player)) {
			if (player.getTemporaryAttributtes().get("jewelryObject") != null)
				player.faceObject((WorldObject) player.getTemporaryAttributtes().get("jewelryObject"));
			return true;
		}
		return false;
	}

	@Override
	public int processWithDelay(Player player) {
		numberToMake--;
		player.setNextAnimation(new Animation(3243));
		player.getSkills().addXp(Skills.CRAFTING, bling.getExperience());
		for (final Item required : bling.getItemsRequired()) {
			player.getInventory().deleteItem(required.getId(), required.getAmount());
		}
		player.getInventory().addItemMoneyPouch(bling.getProduct());
		
		player.sm("You make a " + bling.getProduct().getDefinitions().getName().toLowerCase() + ".");
		
		if (numberToMake > 0) {
			return 2;
		}
		return -1;
	}

	@Override
	public boolean start(Player player) {
		return checkAll(player);
	}

	@Override
	public void stop(Player player) {

	}

}
