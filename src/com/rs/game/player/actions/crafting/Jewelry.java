package com.rs.game.player.actions.crafting;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.net.decoders.WorldPacketsDecoder;



public class Jewelry {

	public enum Bling {

		GOLD_RING(5, 15, new Item[] { new Item(RING_MOULD), new Item(GOLD_BAR) }, new Item(1635), 82), 
		SAPP_RING(20, 40, new Item[] { new Item(RING_MOULD), new Item(GOLD_BAR), new Item(SAPPHIRE) }, new Item(1637), 84), 
		EMER_RING(27, 55, new Item[] { new Item(RING_MOULD), new Item(GOLD_BAR), new Item(EMERALD) }, new Item(1639), 86), 
		RUBY_RING(34, 70, new Item[] { new Item(RING_MOULD), new Item(GOLD_BAR), new Item(RUBY) }, new Item(1641), 88), 
		DIAM_RING(43, 85, new Item[] { new Item(RING_MOULD), new Item(GOLD_BAR), new Item(DIAMOND) }, new Item(1643), 90), 
		DRAG_RING(55, 100, new Item[] { new Item(RING_MOULD), new Item(GOLD_BAR), new Item(DRAGONSTONE) }, new Item(1645), 92), 
		ONYX_RING(67, 115, new Item[] { new Item(RING_MOULD), new Item(GOLD_BAR), new Item(ONYX) }, new Item(6575), 94), 
		HYDRIX_RING(79, 130, new Item[] { new Item(RING_MOULD), new Item(GOLD_BAR), new Item(HYDRIX) }, new Item(31857), 96),

		GOLD_NECK(6, 20, new Item[] { new Item(NECKLACE_MOULD), new Item(GOLD_BAR) }, new Item(1654), 68), 
		SAPP_NECK(22, 55, new Item[] { new Item(NECKLACE_MOULD), new Item(GOLD_BAR), new Item(SAPPHIRE) }, new Item(1656), 70), 
		EMER_NECK(29, 60, new Item[] { new Item(NECKLACE_MOULD), new Item(GOLD_BAR), new Item(EMERALD) }, new Item(1658), 72), 
		RUBY_NECK(40, 75, new Item[] { new Item(NECKLACE_MOULD), new Item(GOLD_BAR), new Item(RUBY) }, new Item(1660), 74), 
		DIAM_NECK(56, 90, new Item[] { new Item(NECKLACE_MOULD), new Item(GOLD_BAR), new Item(DIAMOND) }, new Item(1662), 76), 
		DRAG_NECK(72, 105, new Item[] { new Item(NECKLACE_MOULD), new Item(GOLD_BAR), new Item(DRAGONSTONE) }, new Item(1664), 78), 
		ONYX_NECK(82, 120, new Item[] { new Item(NECKLACE_MOULD), new Item(GOLD_BAR), new Item(ONYX) }, new Item(6577), 80), 
		//HYDRIX_NECK(90, 135, new Item[] { new Item(NECKLACE_MOULD), new Item(GOLD_BAR), new Item(HYDRIX) }, new Item(31859), 82),

		GOLD_AMMY(8, 30, new Item[] { new Item(AMULET_MOULD), new Item(GOLD_BAR) }, new Item(1673), 53), 
		SAPP_AMMY(24, 65, new Item[] { new Item(AMULET_MOULD), new Item(GOLD_BAR), new Item(SAPPHIRE) }, new Item(1675), 55), 
		EMER_AMMY(31, 70, new Item[] { new Item(AMULET_MOULD), new Item(GOLD_BAR), new Item(EMERALD) }, new Item(1677), 57), 
		RUBY_AMMY(50, 85, new Item[] { new Item(AMULET_MOULD), new Item(GOLD_BAR), new Item(RUBY) }, new Item(1679), 59), 
		DIAM_AMMY(70, 100, new Item[] { new Item(AMULET_MOULD), new Item(GOLD_BAR), new Item(DIAMOND) }, new Item(1681), 61), 
		DRAG_AMMY(80, 150, new Item[] { new Item(AMULET_MOULD), new Item(GOLD_BAR), new Item(DRAGONSTONE) }, new Item(1683), 63), 
		ONYX_AMMY(90, 165, new Item[] { new Item(AMULET_MOULD), new Item(GOLD_BAR), new Item(ONYX) }, new Item(6579), 65), 
		HYDRIX_AMMY(99, 180, new Item[] { new Item(AMULET_MOULD), new Item(GOLD_BAR), new Item(HYDRIX) }, new Item(31861), 67),

		GOLD_BRACE(7, 25, new Item[] { new Item(BRACELET_MOULD), new Item(GOLD_BAR) }, new Item(11069), 33), 
		SAPP_BRACE(23, 60, new Item[] { new Item(BRACELET_MOULD), new Item(GOLD_BAR), new Item(SAPPHIRE) }, new Item(11072), 35), 
		EMER_BRACE(30, 65, new Item[] { new Item(BRACELET_MOULD), new Item(GOLD_BAR), new Item(EMERALD) }, new Item(11076), 37), 
		RUBY_BRACE(42, 80, new Item[] { new Item(BRACELET_MOULD), new Item(GOLD_BAR), new Item(RUBY) }, new Item(11085), 39), 
		DIAM_BRACE(58, 95, new Item[] { new Item(BRACELET_MOULD), new Item(GOLD_BAR), new Item(DIAMOND) }, new Item(11092), 41), 
		DRAG_BRACE(74, 110, new Item[] { new Item(BRACELET_MOULD), new Item(GOLD_BAR), new Item(DRAGONSTONE) }, new Item(11115), 43), 
		ONYX_BRACE(84, 125, new Item[] { new Item(BRACELET_MOULD), new Item(GOLD_BAR), new Item(ONYX) }, new Item(11130), 45), 
		HYRDIX_BRACE(94, 140, new Item[] { new Item(BRACELET_MOULD), new Item(GOLD_BAR), new Item(HYDRIX) }, new Item(31865), 47);

		public static Bling forId(int buttonId) {
			return rings.get(buttonId);
		}

		private static Map<Integer, Bling> rings = new HashMap<Integer, Bling>();

		static {
			for (final Bling ring : Bling.values()) {
				rings.put(ring.getButtonId(), ring);
			}
		}

		private final int levelRequired;
		private final double experience;
		private final Item[] itemsRequired;
		private final int buttonId;
		private final Item product;

		Bling(int levelRequired, double experience, Item[] itemsRequired, Item producedBar, int buttonId) {
			this.levelRequired = levelRequired;
			this.experience = experience;
			this.itemsRequired = itemsRequired;
			this.product = producedBar;
			this.buttonId = buttonId;
		}

		public int getButtonId() {
			return buttonId;
		}

		public double getExperience() {
			return experience;
		}

		public Item[] getItemsRequired() {
			final Item[] req = new Item[itemsRequired.length - 1];
			System.arraycopy(itemsRequired, 1, req, 0, itemsRequired.length - 1);
			return req;
		}

		public int getLevelRequired() {
			return levelRequired;
		}

		public Item getMouldRequired() {
			return itemsRequired[0];
		}

		public Item getProduct() {
			return product;
		}
	}
    /**
     * enum with data of unstrung amulets
     * @author paolo
     *
     */
	public static enum amuletData {
		GOLD(1673, 1692),
		SAPPHIRE(1675, 1694),
		EMERALD(1677, 1696),
		RUBY(1679, 1698),
		DIAMOND(1681, 1700),
		DRAGONSTONE(1683, 1702),
		ONYX(6579, 6581);
		
		private int amuletId, product;
		
		private amuletData(final int amuletId, final int product) {
			this.amuletId = amuletId;
			this.product = product;
		}
		
		public static amuletData forId(Item item){
			for(amuletData data : amuletData.values()){
				if(data.getAmuletId() == item.getId())
					return data;
			}
			return null;
		}
		
		public int getAmuletId() {
			return amuletId;
		}
		
		public int getProduct() {
			return product;
		}
	}
	/**
	 * handles amulet stringing
	 * @param player
	 * @param item
	 */
	public static void stringAmulet(Player player, Item item){
		amuletData amulet = amuletData.forId(item);
		if(amulet != null){
			player.getInventory().deleteItem((item));
			player.getInventory().addItem(amulet.product, 1);
			player.getInventory().deleteItem(new Item(1759,1));
			player.sm("You succesfully made a "+ItemDefinitions.getItemDefinitions(amulet.product).name+".");
		}
	}
	static int getNumberToMake(int packetId) {
		switch (packetId) {
		case WorldPacketsDecoder.ACTION_BUTTON1_PACKET:
			return 1;
		case WorldPacketsDecoder.ACTION_BUTTON2_PACKET:
			return 5;
		case WorldPacketsDecoder.ACTION_BUTTON3_PACKET:
			return 28;
		default:
			return 1;
		}
	}

	public static void handleButtons(Player player, int componentId, int packetId) {
		player.closeInterfaces();
		final Bling bling = Bling.forId(componentId);
		final int numberToMake = getNumberToMake(packetId);
		if (bling != null)
			player.getActionManager().setAction(new JewelryAction(bling, numberToMake));
		else {
			if (player.getRights() == 2 || player.getRights() == 4)
				player.sm("JEWELRY: component: " + componentId + " packetId: " + packetId);
		}
	}

	public static void openJewelryInterface(Player player) {
		player.getInterfaceManager().sendInterface(446);
		player.getPackets().sendHideIComponent(446, 17, true);
		player.getPackets().sendHideIComponent(446, 21, true);
		player.getPackets().sendHideIComponent(446, 26, true);
		player.getPackets().sendHideIComponent(446, 30, true);

		// Rings
		player.getPackets().sendItemOnIComponent(446, 81, 1635, 75);
		player.getPackets().sendItemOnIComponent(446, 83, 1637, 75);
		player.getPackets().sendItemOnIComponent(446, 85, 1639, 75);
		player.getPackets().sendItemOnIComponent(446, 87, 1641, 75);
		player.getPackets().sendItemOnIComponent(446, 89, 1643, 75);
		player.getPackets().sendItemOnIComponent(446, 91, 1645, 75);
		player.getPackets().sendItemOnIComponent(446, 93, 6575, 75);
		player.getPackets().sendItemOnIComponent(446, 96, 31857, 75);

		// Necklaces
		player.getPackets().sendItemOnIComponent(446, 67, 1654, 75);
		player.getPackets().sendItemOnIComponent(446, 69, 1656, 75);
		player.getPackets().sendItemOnIComponent(446, 71, 1658, 75);
		player.getPackets().sendItemOnIComponent(446, 73, 1660, 75);
		player.getPackets().sendItemOnIComponent(446, 75, 1662, 75);
		player.getPackets().sendItemOnIComponent(446, 77, 1664, 75);
		player.getPackets().sendItemOnIComponent(446, 79, 6577, 75);
		//player.getPackets().sendItemOnIComponent(446, 81, 31859, 75);

		// Amulets
		player.getPackets().sendItemOnIComponent(446, 52, 1673, 75);
		player.getPackets().sendItemOnIComponent(446, 54, 1675, 75);
		player.getPackets().sendItemOnIComponent(446, 56, 1677, 75);
		player.getPackets().sendItemOnIComponent(446, 58, 1679, 75);
		player.getPackets().sendItemOnIComponent(446, 60, 1681, 75);
		player.getPackets().sendItemOnIComponent(446, 62, 1683, 75);
		player.getPackets().sendItemOnIComponent(446, 64, 6579, 75);
		player.getPackets().sendItemOnIComponent(446, 66, 31863, 75);

		// Bracelets
		player.getPackets().sendItemOnIComponent(446, 32, 11069, 75);
		player.getPackets().sendItemOnIComponent(446, 34, 11071, 75);
		player.getPackets().sendItemOnIComponent(446, 36, 11076, 75);
		player.getPackets().sendItemOnIComponent(446, 38, 11085, 75);
		player.getPackets().sendItemOnIComponent(446, 40, 11092, 75);
		player.getPackets().sendItemOnIComponent(446, 42, 11115, 75);
		player.getPackets().sendItemOnIComponent(446, 44, 11130, 75);
		player.getPackets().sendItemOnIComponent(446, 46, 31865, 75);
	}

	private static int RING_MOULD = 1592;

	private static int AMULET_MOULD = 1595;
	private static int NECKLACE_MOULD = 1597;
	private static int BRACELET_MOULD = 11065;
	public static int GOLD_BAR = 2357;
	private static int SAPPHIRE = 1607;
	private static int EMERALD = 1605;
	private static int RUBY = 1603;
	private static int DIAMOND = 1601;
	private static int DRAGONSTONE = 1615;
	private static int ONYX = 6573;
	private static int HYDRIX = 31855;
}
