package com.rs.game.npc.familiar;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.actions.crafting.Jewelry;
import com.rs.game.player.actions.summoning.Summoning.Pouches;
import com.rs.game.player.content.JewelryTransformation;

public class Pyrelord extends Familiar {

		/**
	 * 
	 */
	private static final long serialVersionUID = -5514524709014168988L; 

		public Pyrelord(Player owner, Pouches pouch, WorldTile tile,
	                      int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
	        super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	    }

	    @Override
	    public String getSpecialName() {
	        return "Immense Heat";
	    }

	    @Override
	    public String getSpecialDescription() {
	        return "Craft jewellery without the need for a furnace.";
	    }

	    @Override
	    public int getBOBSize() {
	        return 0;
	    }

	    @Override
	    public int getSpecialAmount() {
	        return 6;
	    }

	    @Override
	    public SpecialAttack getSpecialAttack() {
	        return SpecialAttack.ITEM;
	    }

	    @Override
	    public boolean submitSpecial(Object object) {
	    	Integer slotId = (Integer) object;
	    	if (slotId == null)
	    		return false;
	    	if (getOwner().getInventory().getItem(slotId).getId() != 2357) {
	    		getOwner().sm("This can only be used upon gold bars.");
	    		return false;
	    	}
                Jewelry.openJewelryInterface(getOwner());
	        return true;
	    }
	}

