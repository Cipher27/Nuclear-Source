package com.rs.game.player.actions.smithing;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.game.player.content.ArtisanWorkshop.Ingots;

/**
 * 
 * @author paolo
 *
 */

public class ArtisanSmithing extends Action {

	
	private static int HAMMER = 2347;
	private static int CRYSTAL_HAMMER = 32640;
	private Ingots bar;
	private int index;
	private int ticks;

	public ArtisanSmithing(int ticks, int index,Ingots ingnot) {
		this.index = index;
		this.ticks = ticks;
		this.bar = ingnot;
	}

	@Override
	public boolean process(Player player) {
		if (!player.getInventory().containsItemToolBelt(HAMMER) && !player.getInventory().containsItem(CRYSTAL_HAMMER,1) && player.getEquipment().getWeaponId() != CRYSTAL_HAMMER) {
		    player.getDialogueManager().startDialogue("SimpleMessage", "You need a hammer in order to work with a bar of " + new Item(bar.getBarId(), 1).getDefinitions().getName().toLowerCase().replace(" bar", "") + ".");
		    return false;
		}
		if (!player.getInventory().containsItem(bar.getBarId(),1)) {
			player.getDialogueManager().startDialogue("SimpleMessage",
					"You do not have sufficient bars!");
			return false;
		}
		if (player.getSkills().getLevel(Skills.SMITHING) < bar.getLevel()) {
			player.getDialogueManager().startDialogue("SimpleMessage","You need a Smithing level of "+bar.getLevel()+" to create this.");
			return false;
		}
		return true;
	}

	@Override
	public int processWithDelay(Player player) {
		ticks--;
		if(player.getInventory().containsItem(CRYSTAL_HAMMER,1) || player.getEquipment().getWeaponId() == CRYSTAL_HAMMER)
			player.setNextAnimation(new Animation(25189));
		else 
			player.setNextAnimation(new Animation(898));
		player.getInventory().deleteItem(bar.getBarId(),1);
		player.getInventory().addItem(bar.getProducts()[index].getId(),1);
		player.randomevent(player);
		player.getSkills().addXp(Skills.SMITHING, getExp(bar));
		player.getArtisanWorkshop().handelRespect(getExp(bar));
		if (ticks > 0) {
			return 7;
		}
		return -1;
		
	}
	/**
	 * todo based on worlds event.
	 * @param bar
	 * @return
	 */
	public double getExp(Ingots bar){
		ItemDefinitions defs = ItemDefinitions.getItemDefinitions(bar.getProduct(index).getId());
		if(defs.name.contains("helm") && World.artisanBonusExp == World.ARTISAN_TYPES.HELM ||
				defs.name.contains("chest") && World.artisanBonusExp == World.ARTISAN_TYPES.CHEST ||
					defs.name.contains("boots") && World.artisanBonusExp == World.ARTISAN_TYPES.BOOTS ||
						defs.name.contains("gauntlets") && World.artisanBonusExp == World.ARTISAN_TYPES.GLOVES)
			return bar.getExp() + bar.getExp()/10;
		return bar.getExp(); 
	}
	@Override
	public boolean start(Player player) {
		if (bar == null) {
			return false;
		}
		if (!player.getInventory().containsOneItem(HAMMER, bar.getBarId())) {
			player.getDialogueManager().startDialogue("SimpleMessage","You need a hammer in order to work with a bar of "+ new Item(bar.getBarId(), 1).getDefinitions().getName().replace("Bar ", "") + ".");
			return false;
		}
		if (player.getSkills().getLevel(Skills.SMITHING) < bar.getLevel()) {
			player.getDialogueManager().startDialogue("SimpleMessage","You need a Smithing level of "+ bar.getLevel()+ " to create this.");
			return false;
		}
		return true;
	}

	@Override
	public void stop(Player player) {
		this.setActionDelay(player, 3);
	}

	
}
