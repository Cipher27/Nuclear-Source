package com.rs.game.player.content;

import com.rs.game.player.Player;

/**
 * simple class for pickaxe (g)
 * @author paolo
 *
 */
public class PickAxeGilding {
	
	/**
	 * enum of pickaxes
	 * @author paolo
	 *
	 */
	public enum PickaxesG { 
		
		BRONZE(1265,20780),
		IRON(1267,20781),
		STEEL(1269,20782),
		MITHRIL(1273,20784),
		ADAMANT(1271,20783),
		RUNE(1276,20785),
		DRAGON(15259,20786);
		
		private int pickAxe, gilded;
		
		/**
		 * Used to obtain the value based off of original item and dye
		 */
		public static PickaxesG forItem(int item) {
			for (PickaxesG product : PickaxesG.values()) {
				if (product.pickAxe == item) {
					return product;
				}
			}
			return null;
		}
		/**
		 * 
		 * @return pick id
		 */
		public int getBaseId() {
			return gilded;
		}
		/**
		 * 
		 * @return gilded pick id
		 */
		public int getGildedId() {
			return pickAxe;
		}
		/**
		 * 
		 * @param dye
		 * @param item
		 */
		private PickaxesG(int dye, int item) {
			this.pickAxe = dye;
			this.gilded = item;
			
		}
	}
	/**
	 * call this to gild
	 * @param player
	 * @param used
	 * @return
	 */
	public static boolean GildPickaxe(Player player, int used) {
		if (!player.getInventory().containsItem(used, 1)) {
			return false;
		}
		if(!player.getInventory().contains(29522)){
			player.sm("You need an imcando pickaxe for gilding your pickaxe.");
			return false;
		}
		player.getInventory().deleteItem(29552,1);
		player.getInventory().deleteItem(used, 1);
		player.getInventory().addItem(PickaxesG.forItem(used).gilded, 1);
		return true;
	}
	
}
