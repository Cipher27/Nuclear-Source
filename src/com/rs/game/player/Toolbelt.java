package com.rs.game.player;

import java.io.Serializable;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;

public class Toolbelt implements Serializable {


    private static final long serialVersionUID = -7244573478285647954L;

    private static final int[] TOOLBET_ITEMS = new int[] { 
    	946, 1735, 1595, 1755, 1599, 1597, 1733, //7
    	1592, 5523, 13431, 307, 309, 311, 301, 303, 1265, 2347, 1351, 590, -1, 8794, 4, 9434, 11065, //17
    	1785, 2976, 1594, 5343, 5325, 5341, 5329, 233, 952, 305, 975, 11323, 2575, 2576, 13153, 10150, //16
    	//end of default items
    	1267, 1269, 1273, 1271, 1275, 15259,32646, //pickaxes - normal //46
    	1349, 1353, 1355, 1357, 1359, 6739,32645, //hatchets
    	20780, 20781, 20782, 20784, 20783, 20785, 20786//pickaxes - upgraded
    	};
    private static final int[] CONFIG_IDS = new int[] { 2438, 2439 };
    private boolean[] items;
    private transient Player player;
    private boolean updated;

    public Toolbelt() {
    	items = new boolean[TOOLBET_ITEMS.length];
    }

    public void setPlayer(Player player) {
    	this.player = player;
    	if (items != null && !updated) {
    		boolean newToolbelt[] = items;
    		items = new boolean[TOOLBET_ITEMS.length];
    		for (int i = 0; i < newToolbelt.length; i++) {
    			items[i] = newToolbelt[i];
    		}
    		updated = true;
    	}
    }

    public void init() {
    	refreshConfigs();
    }

    private int getConfigIndex(int i) {
    	return i / 20;
    }

    private void refreshConfigs() {
    	/*int[] configValues = new int[CONFIG_IDS.length];
    	for (int i = 0; i < items.length - 20; i++) {
    		if (items[i]) {
    			int index = getConfigIndex(i);
    			configValues[index] |= 1 << (i - (index * 20));
    		}
    	}
    	for (int i = 0; i < CONFIG_IDS.length; i++)
    		player.getPackets().sendConfig(CONFIG_IDS[i], configValues[i]);*/
    }
    
    private int getItemSlot(int id) {
    	for (int i = 0; i < TOOLBET_ITEMS.length; i++)
    		if (TOOLBET_ITEMS[i] == id)
    			return i;
    	return -1;
    }
    /**
     * array for pickaxes
     */
	public int[] pickaxes = {1265,1267, 1269, 1273, 1271, 1275, 15259, 32646};
	public int[] axes  = { 1347,1349, 1353, 1355,1357, 1359, 6739, 32645};
	/**
	 * checks if the items is an pick/axe
	 * @param item
	 * @return
	 */
	public boolean isPickAxe(int item){
		for(int i = 0; i <pickaxes.length; i ++)
			if(item == pickaxes[i] || item == axes[i])
				return true;
		return false;
	}
	/**
	 * returns the current index
	 * @param item
	 * @return
	 */
	public int getPickIndex(int item){
		 for(int a = 0; a < pickaxes.length ; a++) {
		        if(item == pickaxes[a] || item == axes[a])
		            return a;
		    }
		 return -1;
	}

    public boolean addItem(int invSlot, Item item) {
    	int slot = getItemSlot(item.getId());
    	if (slot == -1)
    		return false;
    	if (items[slot])
    		player.getPackets().sendInventoryMessage(0, invSlot, "That is already on your tool belt.");
    	else {
    		items[slot] = true;
    		player.getInventory().deleteItem(invSlot, item);
    		refreshConfigs();
    		player.getPackets().sendGameMessage("You add the " + item.getDefinitions().getName() + " to your tool belt.");
    	}
    	return true;
    }

    public boolean containsItem(int id) {
    	int slot = getItemSlot(id);
    	return slot == -1 ? false : items[slot];
    }

}