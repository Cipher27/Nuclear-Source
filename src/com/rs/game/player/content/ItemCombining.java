package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;

public class ItemCombining {
	
	private static final int barrows = 33294, third = 33298, shadows = 33296;
	private static final int rapier = 26579, ohrapier = 26583, mace = 26595, ohmace = 26599, longsword = 26587, ohlongsword = 26591;
	private static final int ascension = 28441, ohascension = 28437, wand = 28617, singularity = 28621;
	private static final int longbow = 31733, scythe = 31725, staff = 31729;
	private static final int mask = 29854, hauberk = 29857, chaps = 29860;
	private static final int helm = 30005, cuirass = 30008, greaves = 30011;
	private static final int tmask = 28608, robetop = 28611, robebottom = 28614;
	private static final int cloth = 3188;
	private static final int white = 9913, red = 9914, blue = 9915, green = 9916, yellow = 9917, ywhip = 15441, bwhip = 15442, wwhip = 15443, gwhip = 15444;
	private static final int robin = 2581, whip = 4151, ranger = 2577, dark = 11235, sol = 15486, gnome = 9470, vine = 21371;
	private static final String add = "You add a new colour to your item.", remove = "You remove the colour from your item, returning it to its original state.";
	
	public enum Combinings { 
		
		BARROW_RAPIER(barrows, rapier, 33306, ""),
		BARROW_OH_RAPIER(barrows, ohrapier, 33309, ""),
		BARROW_MACE(barrows, mace, 33300, ""),
		BARROW_OH_MACE(barrows, ohmace, 33303, ""),
		BARROW_LONGSWORD(barrows, longsword, 33312, ""),
		BARROW_OH_LONGSWORD(barrows, ohlongsword, 33315, ""),
		
		SHADOW_RAPIER(shadows, rapier, 33372, ""),
		SHADOW_OH_RAPIER(shadows, ohrapier, 33375, ""),
		SHADOW_MACE(shadows, mace, 33366, ""),
		SHADOW_OH_MACE(shadows, ohmace, 33369, ""),
		SHADOW_LONGSWORD(shadows, longsword, 33378, ""),
		SHADOW_OH_LONGSWORD(shadows, ohlongsword, 33381, ""),
		
		THIRD_RAPIER(third, rapier, 33438, ""),
		THIRD_OH_RAPIER(third, ohrapier, 33441, ""),
		THIRD_MACE(third, mace, 33432, ""),
		THIRD_OH_MACE(third, ohmace, 33435, ""),
		THIRD_LONGSWORD(third, longsword, 33444, ""),
		THIRD_OH_LONGSWORD(third, ohlongsword, 33447, ""),
		
		BARROW_ASCENSION(barrows, ascension, 33318, ""),
		BARROW_OH_ASCENSION(barrows, ohascension, 33321, ""),
		SHADOW_ASCENSION(shadows, ascension, 33384, ""),
		SHADOW_OH_ASCENSION(shadows, ohascension, 33387, ""),
		THIRD_ASCENSION(third, ascension, 33450, ""),
		THIRD_OH_ASCENSION(third, ohascension, 33453, ""),
		
		BARROW_WAND(barrows, wand, 33324, ""),
		BARROW_SINGULARITY(barrows, singularity, 33327, ""),
		SHADOW_WAND(shadows, wand, 33390, ""),
		SHADOW_SINGULARITY(shadows, singularity, 33393, ""),
		THIRD_WAND(third, wand, 33456, ""),
		THIRD_SINGULARITY(third, singularity, 33459, ""),
		
		BARROW_LONG(barrows, longbow, 33336, ""),
		SHADOW_LONG(shadows, longbow, 33402, ""),
		THIRD_LONG(third, longbow, 33468, ""),
		
		BARROW_SCYTHE(barrows, scythe, 33330, ""),
		SHADOW_SCYTHE(shadows, scythe, 33396, ""),
		THIRD_SCYTHE(third, scythe, 33462, ""),
		
		BARROW_STAFF(barrows, staff, 33333, ""),
		SHADOW_STAFF(shadows, staff, 33399, ""),
		THIRD_STAFF(third, staff, 33465, ""),
		
		BARROW_HELM(barrows, helm, 33357, ""),
		BARROW_CUIRASS(barrows, cuirass, 33360, ""),
		BARROW_GREAVES(barrows, greaves, 33363, ""),
		SHADOW_HELM(shadows, helm, 33423, ""),
		SHADOW_CUIRASS(shadows, cuirass, 33426, ""),
		SHADOW_GREAVES(shadows, greaves, 33429, ""),
		THIRD_HELM(third, helm, 33489, ""),
		THIRD_CUIRASS(third, cuirass, 33492, ""),
		THIRD_GREAVES(third, greaves, 33495, ""),
		
		BARROW_TMASK(barrows, tmask, 33339, ""),
		BARROW_ROBETOP(barrows, robetop, 33342, ""),
		BARROW_ROBEBOTTOM(barrows, robebottom, 33345, ""),
		SHADOW_TMASK(shadows, tmask, 33405, ""),
		SHADOW_ROBETOP(shadows, robetop, 33408, ""),
		SHADOW_ROBEBOTTOM(shadows, robebottom, 33411, ""),
		THIRD_TMASK(third, tmask, 33471, ""),
		THIRD_ROBETOP(third, robetop, 33474, ""),
		THIRD_ROBEBOTTOM(third, robebottom, 33477, ""),
		
		BARROW_MASK(barrows, mask, 33348, ""),
		BARROW_HAUBERK(barrows, hauberk, 33351, ""),
		BARROW_CHAPS(barrows, chaps, 33354, ""),
		SHADOW_MASK(shadows, mask, 33414, ""),
		SHADOW_HAUBERK(shadows, hauberk, 33417, ""),
		SHADOW_CHAPS(shadows, chaps, 33420, ""),
		THIRD_MASK(third, mask, 33480, ""),
		THIRD_HAUBERK(third, hauberk, 33483, ""),
		THIRD_CHAPS(third, chaps, 33486, ""),
		
		YELLOW_ROBIN(yellow, robin, 20950, add),
		RED_ROBIN(red, robin, 20949, add),
		BLUE_ROBIN(blue, robin, 20951, add),
		WHITE_ROBIN(white, robin, 20952, add),
		
		YELLOW_RANGERS(yellow, ranger, 22558, add),
		RED_RANGER(red, ranger, 22552, add),
		BLUE_RANGER(blue, ranger, 22554, add),
		WHITE_RANGER(white, ranger, 22556, add),
		
		YELLOW_WHIP(yellow, whip, 15441, add),
		BLUE_WHIP(blue, whip, 15442, add),
		WHITE_WHIP(white, whip, 15443, add),
		GREEN_WHIP(green, whip, 15444, add),
		
		YELLOW_WHIP_VINE(ywhip, vine, 21372, add),
		BLUE_WHIP_VINE(bwhip, vine, 21373, add),
		WHITE_WHIP_VINE(wwhip, vine, 21374, add),
		GREEN_WHIP_VINE(gwhip, vine, 21375, add),
		
		YELLOW_DARKBOW(yellow, dark, 15701, add),
		BLUE_DARKBOW(blue, dark, 15702, add),
		WHITE_DARKBOW(white, dark, 15703, add),
		GREEN_DARKBOW(green, dark, 15704, add),
		
		RED_SOL(red, sol, 22207, add),
		YELLOW_SOL(yellow, sol, 22209, add),
		BLUE_SOL(blue, sol, 22211, add),
		GREEN_SOL(green, sol, 22213, add),
		
		RED_GNOME(red, gnome, 22215, add),
		YELLOW_GNOME(yellow, gnome, 22216, add),
		BLUE_GNOME(blue, gnome, 22217, add),
		GREEN_GNOME(green, gnome, 22217, add),
		
		STEADFAST(21787, 34972, 34978, "You combined the 2 items."),
		RAGEFIRE(21793, 34976, 34984, "You combined the 2 items."),
		GLAIVEN(21790, 34974, 34981, "You combined the 2 items."),
		
		HOODEN_MAX(20768, 20767, 32151, "You created a hooded cape."),
		HOODEN_COMP(20770, 20769, 32152, "You created a hooded cape."),
		HOODEN_COMPT(20772, 20771, 32153,"You created a hooded cape."),
		
		
		BLOOD_FURRY(6585, 32692, 32703, "You created a Blood furry."),
		BLOOD_KNOCKOUT(31449, 32692, 32700,add),
		BLOOD_ARCANE(18335, 32692, 32694,add),
		BLOOD_FARSIGHT(31445, 32692, 32697,add),
		/**
		 * chaotic remnant
		 */
		BARWLER_KNOCKOUT(6585, 32692, 31449, add),
		FARSIGHT_SNIPER(25034, 32692, 31445, add),
		ARCANE_STREAM(25031,32692,18335,add),
		
		SOULS_FURRY(25028, 31449, 31875, "You created a Soul amulet, this amulet will get you increased soulsplit healing."),
		
		
		;
		
		private int dye, item, product;
		private String message;
		
		/**
		 * Used to obtain the value based off of original item and dye
		 */
		public static Combinings forItem(int item, int dye) {
			for (Combinings product : Combinings.values()) {
				if (product.item == item && product.dye == dye || product.item == dye && product.dye == item) {
					return product;
				}
			}
			return null;
		}
		
		/**
		 * Used to obtain the value based off of the product
		 */
		public static Combinings forProduct(int item) {
			for (Combinings product : Combinings.values()) {
				if (product.product == item) {
					return product;
				}
			}
			return null;
		}
		
		public int getId() {
			return item;
		}
		
		public int getDye() {
			return dye;
		}
		
		public int getProduct() {
			return product;
		}
		
		public String getMessage() {
			return message;
		}
		
		private Combinings(int dye, int item, int product, String message) {
			this.dye = dye;
			this.item = item;
			this.product = product;
			this.message = message;
		}
	}
	
	public static boolean CombineItems(Player player, int used, int usedWith) {
		if (!player.getInventory().containsItem(used, 1) || !player.getInventory().containsItem(usedWith, 1)) {
			return false;
		}
		player.getInventory().deleteItem(used, 1);
		player.getInventory().deleteItem(usedWith, 1);
		player.getInventory().addItem(Combinings.forItem(used, usedWith).getProduct(), 1);
		if (Combinings.forItem(used,  usedWith).getMessage().equals(""))
			player.getPackets().sendGameMessage("You carefully coat your "
					+(ItemDefinitions.getItemDefinitions(Combinings.forItem(used, usedWith).getId()).getName().toLowerCase())+
					" with the "+(ItemDefinitions.getItemDefinitions(Combinings.forItem(used, usedWith).getDye()).getName().toLowerCase())+".", true);
		else
			player.getPackets().sendGameMessage(Combinings.forItem(used, usedWith).getMessage(), true);
		return true;
	}
	
	public static boolean RemoveDyeFromItems(Player player, int used, int usedWith) {
		if (!player.getInventory().containsItem(used, 1) || !player.getInventory().containsItem(usedWith, 1)) {
			return false;
		}
		if (used != cloth && usedWith != cloth)
			return false;
		if (used == cloth)
			player.getInventory().deleteItem(usedWith, 1);
		else
			player.getInventory().deleteItem(used, 1);
		player.getInventory().addItem(Combinings.forProduct(used).getId(), 1);
		if (Combinings.forProduct(used).getMessage().equals(""))
			player.getPackets().sendGameMessage("You carefully remove the dye from your "+(ItemDefinitions.getItemDefinitions(Combinings.forProduct(used).getId()).getName().toLowerCase())+".", true);
		else
			player.getPackets().sendGameMessage(remove, true);
		return true;
	}
	
	
	
}
