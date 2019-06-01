package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;

public class ItemUpgradeKits {
	
	private static final int helm = 11335, plate = 14479, legs = 4087, skirt = 4585, boots = 11732, gloves = 7461, sq = 1187, kite = 24365;//dragon items
	private static final int hat = 6918, top = 6916, bottoms = 6924, mage_boots = 6920, mage_gloves = 6922;//infinity
	private static final int fury = 6585;//misc items
	private static final int dbone = 24352;
	
	public enum Upgradables {
		
		D_FULL_HELM_GOLD(19346, helm, 19336),
		D_FULL_HELM_SPIKE(19354, helm, 19341),
		
		D_PLATE_GOLD(19350, plate, 19337),
		D_PLATE_SPIKE(19358, plate, 19342),
		
		D_PLATELEGS_GOLD(19348, legs, 19338),
		D_PLATELEGS_SPIKE(19356, legs, 19343),
		
		D_PLATESKIRT_GOLD(19348, skirt, 19339),
		D_PLATESKIRT_SPIKE(19356, skirt, 19344),
		
		D_SQ_GOLD(19352, sq, 19340),
		D_SQ_SPIKE(19360, sq, 19345),
		
		D_KITE_GOLD(25312, kite, 25320),
		D_KITE_SPIKE(25314, kite, 25321),
		
		DBONE_FULL_HELM(dbone, helm, 24359),
		DBONE_PLATEBODY(dbone, plate, 24360),
		DBONE_PLATELEGS(dbone, legs, 24363),
		DBONE_PLATESKIRT(dbone, skirt, 24364),
		DBONE_BOOTS(dbone, boots, 24362),
		DBONE_GLOVES(dbone, gloves, 24361),
		
		DBONE_HAT(dbone, hat, 24354),
		DBONE_TOP(dbone, top, 24355),
		DBONE_BOTTOMS(dbone, bottoms, 24356),
		DBONE_MAGE_BOOTS(dbone, mage_boots, 24358),
		DBONE_MAGE_GLOVES(dbone, mage_gloves, 24357),
		
		FURY(19333, fury, 19335)
		;
		
		private int kit, item, product;
		
		public int getId() {
			return item;
		}
		
		public int getKit() {
			return kit;
		}
		
		public int getProduct() {
			return product;
		}
		
		public static Upgradables byItems(int used, int usedWith) {
			for (Upgradables upgradable : Upgradables.values()) {
				if (upgradable.item == used && upgradable.kit == usedWith || upgradable.item == usedWith && upgradable.kit == used)
					return upgradable;
			}
			return null;
		}
		
		public static Upgradables byProduct(int used) {
			for (Upgradables upgradable : Upgradables.values()) {
				if (upgradable.product == used)
					return upgradable;
			}
			return null;
		}
		
		private Upgradables(int kit, int item, int product) {
			this.kit = kit;
			this.item = item;
			this.product = product;
		}
	}
	
	public static boolean AttachKitToItem(Player player, int used, int usedWith) {
		if (!player.getInventory().containsItem(used, 1) || !player.getInventory().containsItem(usedWith, 1)) {
			return false;
		}
		player.getInventory().deleteItem(used, 1);
		player.getInventory().deleteItem(usedWith, 1);
		player.getInventory().addItem(Upgradables.byItems(used, usedWith).getProduct(), 1);
		player.getPackets().sendGameMessage("You attach the kit to your "
		+(ItemDefinitions.getItemDefinitions(Upgradables.byItems(used, usedWith).getId()).getName().toLowerCase())+".");
		return true;
	}
	
	public static boolean RemoveKitFromItem(Player player, int item) {
		if (!player.getInventory().containsItem(item, 1)) {
			return false;
		}
		if (player.getInventory().getFreeSlots() < 2) {
			player.getPackets().sendGameMessage("You do not have enough space in your inventory to do this.");
			return false;
		}
		player.getInventory().deleteItem(item, 1);
		player.getInventory().addItem(Upgradables.byProduct(item).getId(), 1);
		player.getInventory().addItem(Upgradables.byProduct(item).getKit(), 1);
		player.getPackets().sendGameMessage("You seperate the kit from your "+(ItemDefinitions.getItemDefinitions(Upgradables.byProduct(item).getId()).getName().toLowerCase())+".");
		return true;
	}
	
}