package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.item.Item;
import com.rs.game.player.Player;


public class SkillingCrates {

	public enum Crate {
		SMALL_DUMMY(31785, new Item[] { new Item(31770, 1),new Item(31771, 1),new Item(31772, 1)},3 , "a melee,range and magic dummy."),
		MEDIUM_DUMMY(31780, new Item[] { new Item(31770, 2),new Item(31771, 2),new Item(31772, 2)},3 , "a melee,range and magic dummy."),
		LARGE_DUMMY(31773, new Item[] { new Item(31770, 4),new Item(31771, 4),new Item(31772, 4)},3 , "a melee,range and magic dummy."),
		SMALL_CONSTRUCTION(25299, new Item[] { new Item(8783, 72), new Item(4824,20)},2, "teak planks and rune nails."),
		LARGE_CONSTRUCTION(25300, new Item[] { new Item(8783, 150), new Item(4824,40)},2, "teak planks and rune nails."),
		SMALL_CRAFTING(25301, new Item[] { new Item(2508, 50), new Item(1733,1),new Item(1734,40)},3, "red dragon hide leather."),
		LARGE_CRAFTING(25302, new Item[] { new Item(2508, 120), new Item(1733,1),new Item(1734,40)},3, "red dragon hide leather."),
		SMALL_FARMING(25303, new Item[] { new Item(5304, 1), new Item(5303,3),new Item(5302,3)},3, "torstol,landtadyme and dwarfweed seeds."),
		LARGE_FARMING(25304, new Item[] { new Item(5304, 3), new Item(5303,9),new Item(5302,9)},3, "torstol,landtadyme and dwarfweed seeds."),
		SMALL_HERBLORE(25305, new Item[] { },0, " "),
		LARGE_HERBLORE(25306, new Item[] {},0, " "),
		SMALL_SMITHING(25307, new Item[] { new Item(450, 50), new Item(454,300),new Item(2347,1)},3, " adamant and coal ore."),
		LARGE_SMITHING(25308, new Item[] { new Item(450, 100), new Item(454,600),new Item(2347,1)},3, " adamant and coal ore."),
		SMALL_SUMMONING(25309, new Item[] { },0,""),
		LARGE_SUMMONING(25310, new Item[] { },0,"");
		private int crateId;
		private Item[] loot;
		private int space;
		private String message;

		private static Map<Integer, Crate> Crates = new HashMap<Integer, Crate>();

		static {
			for (Crate Crate : Crate.values()) {
				Crates.put(Crate.getcrateId(), Crate);
			}
		}

		public static Crate forCrateId(int crateId) {
			return Crates.get(crateId);
		}

		private Crate(int crateId, Item loot[], int space, String message) {
			this.crateId = crateId;
			this.loot = loot;
			this.space = space;
			this.message = message;
		}

		public int getcrateId() {
			return crateId;
		}

		public Item[] getLoot() {
			return loot;
		}
		
		public int getSpace() {
			return space;
		}
		public String getLootName(){
		   return message;
		}
		
		public void open(Player player,Item item){
			 player.getInventory().deleteItem(item);
			 player.getBank().addItems(getLoot(),true);
			 player.sm("The loot of the crate has been send to your bank. Loot: "+getLootName());
			 
			}
			
		public void getCrateMessage(Player player){
			player.sm("This crate contains " +getLootName());
		}
		
		
	}
	
}
