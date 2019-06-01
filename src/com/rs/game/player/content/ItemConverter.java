package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.player.Player;

public class ItemConverter {
	
	
	public enum Convertables {
		BANDOS_GODSWORD(11696,31240),
		ARMADYL_GODSWORD(11694,31241),
		SARADOMIN_GODSWORD(11698,31242),
		ZAMORAK_GODSWORD(11700,31243),
		DRAGON_FULLHELM(11335,30191),
		DRAGON_PLATEBODY(14479,30193),
		DRAGON_PLATELEGS(4087,30194),
		DRAGON_GAUNTLETS(13006,30195),
		DRAGON_PLATESKIRT(4585,30632),
		DRAGON_CHAINBODY(3140,30635),
		DRAGON_BOOTS(11732,30196),
		RUNE_PLATEBODY(1127, 33860),
		RUNE_PLATELEGS(1079, 33861),
		RUNE_FULLHELM(1163, 33862),
		RUNE_KITESHIELD(1201, 33863),
		ADAMANT_PLATEBODY(1123, 33864),
		ADAMANT_PLATELEGS(1073, 33865),
		ADAMANT_FULLHELM(1161, 33866),
		ADAMANT_KITESHIELD(1199, 33867),
		MITHRIL_PLATEBODY(1110, 33868),
		MITHRIL_PLATELEGS(1071, 33869),
		MITHRIL_FULLHELM(1159, 33870),
		MITHRIL_KITESHIELD(1197, 33871),
		BRONZE_PLATEBODY(1117, 33876),
		BRONZE_PLATELEGS(1075, 33877),
		BRONZE_FULLHELM(1155, 33878),
		BRONZE_KITESHIELD(1189, 33879),
		FIGHTER_TORSO(10551, 33880);
		private static Map<Integer, Convertables> items = new HashMap<Integer, Convertables>();

		static {
			for (Convertables convertable : Convertables.values()) {
				items.put(convertable.getnewLook(),convertable);
			}
		}

		public static Convertables forId(int id) {
			return items.get(id);
		}
		private static Map<Integer, Convertables> oldItems = new HashMap<Integer, Convertables>();

		static {
			for (Convertables convertable : Convertables.values()) {
				oldItems.put(convertable.getoldLook(),convertable);
			}
		}

		public static Convertables forId2(int id) {
			return oldItems.get(id);
		}
		private int newLook, oldLook;
		
		
		public int getnewLook() {
			return newLook;
		}
		
		public int getoldLook() {
			return oldLook;
		}
		
		private Convertables(int newLook, int oldLook) {
			this.newLook = newLook;
			this.oldLook = oldLook;
		}
	}
	
	public static void convert(Player player, int itemId){
		final Convertables conv = Convertables.forId(itemId);
		player.getInventory().deleteItem(itemId,1);
		player.getInventory().addItem(conv.getoldLook(),1);
		player.sm("You succesfully converted your item");
	}
	public static void convertOldNew(Player player, int itemId){
		final Convertables conv = Convertables.forId2(itemId);
		player.getInventory().deleteItem(itemId,1);
		player.getInventory().addItem(conv.getnewLook(),1);
		player.sm("You succesfully converted your item");
	}
	
}
