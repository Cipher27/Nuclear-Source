package com.rs.game.player.actions;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

/**
 * 
 * @author "Raghav/Own4g3", made unshit by Hc747
 */

public class LeatherCrafting extends Action {

	public final Animation CRAFT_ANIMATION = new Animation(1249);
	public static final Item NEEDLE = new Item(1733);
	public static final Item THREAD = new Item(1734);
	private int quantity;
	private LeatherData data;
	private int removeThread = 5;

	public static final int LEATHER[] = { 
		1741, 	//leather
		25545,	//imphide
		25547,	//spider silk
		1743, 	//hard leather
		25551,	//carapace
		6287,	//snakeskin
		25549,	//batwing
		1745, 	//green
		2505, 	//blue
		2507, 	//red
		2509, 	//black
		24374, 	//royal
		29863, //sirenic
		33740 //protean
		};

	public static final int PRODUCTS[][] = { 
		{1061, 1059,1063, 1095, 1129, 1167, 25806, 1169},//leather 7 
		{25851, 25853, 25845, 25849, 25847, 25662, 25642, 25855},//imphide 8
		{25843, 25841, 25835, 25839, 25837, 25658, 25650},//spider silk 7
		{25875, 25821, 1131, 25808}, //hard leather 4 
		{25865, 25863, 25857, 25861, 25859},//carapace 5 
		{6328, 6330, 6326, 6324, 6322},//snakeskin 5
		{25829, 25833, 25825, 25831, 25827, 25802, 25660, 25648},//batwing 8
		{1065, 1099, 1135, 25794},//green 4
		{2487, 2493, 2499, 25796}, //blue 4 
		{2489, 2495, 2501, 25798}, //red 4 
		{2491, 2497, 2503, 25800} , //black 4 
		{24376, 24379, 24382}, //royal 3
		{29854, 29860, 29857}, //sirenic 3
		{33741} //protean 1
		};

	public enum LeatherData {
		LEATHER_GLOVES(1741, 1, 1059, 1, 13.75),
		LEATHER_BOOTS(1741, 1, 1061, 7, 16.25),
		LEATHER_COWL(1741, 1, 1167, 9, 18.5),
		LEATHER_VAMBS(1741, 1, 1063, 11, 22),
		LEATHER_CHAPS(1741, 1, 1095, 18, 27),
		LEATHER_BODY(1741, 1, 1129, 14, 25),
		LEATHER_SHIELD(1741, 1, 25806, 19, 30),
		LEATHER_COIF(1741, 1, 1169, 38, 37),
		
		IMPHIDE_GLOVES(25545, 1, 25851, 10, 10),
		IMPHIDE_BOOTS(25545, 1, 25853, 11, 10),
		IMPHIDE_HOOD(25545, 2, 25845, 12, 20),
		IMPHIDE_ROBE_BOTTOM(25545, 2, 25849, 13, 20),
		IMPHIDE_ROBE_TOP(25545, 3, 25847, 14, 30),
		IMPHIDE_BOOK(25545, 3, 25662, 15, 30),
		IMP_HORN_WAND(25545, 3, 25642, 16, 30),
		IMPHIDE_SHIELD(25545, 4, 25855, 17, 40),
		
		SPIDER_SILK_GLOVES(25547, 1, 25843, 20, 12.5),
		SPIDER_SILK_BOOTS(25547, 1, 25841, 21, 12.5),
		SPIDER_SILK_HOOD(25547, 2, 25835, 22, 25),
		SPIDER_SILK_ROBE_BOTTOM(25547, 2, 25839, 23, 25),
		SPIDER_SILK_ROBE_TOP(25547, 3, 25837, 24, 37.5),
		SPIDER_ORB(25547, 2, 25658, 25, 25),
		SPIDER_WAND(25547, 2, 25650, 26, 25),
		
		HARD_LEATHER_BOOTS(1743, 1, 25821, 27, 34),
		HARD_LEATHER_GLOVES(1743, 1, 25875, 25, 32),
		HARD_LEATHER_BODY(1743, 1, 1131, 27, 35),
		HARD_LEATHER_SHIELD(1743, 1, 25808, 28, 36),
		
		CARAPACE_GLOVES(25551, 1, 25865, 30, 12),
		CARAPACE_BOOTS(25551, 1, 25863, 31, 12),
		CARAPACE_HELM(25551, 2, 25857, 33, 24),
		CARAPACE_LEGS(25551, 2, 25861, 34, 24),
		CARAPACE_TORSO(25551, 3, 25859, 35, 36),
		
		SNAKESKIN_BOOTS(6287, 6, 6328, 45, 30),
		SNAKESKIN_VAMB(6287, 8, 6330, 47, 35),
		SNAKESKIN_BANDANA(6287, 5, 6326, 48, 45),
		SNAKESKIN_CHAPS(6287, 12, 6324, 50, 50),
		SNAKESIN_BODY(6287, 15, 6322, 53, 55),
		
		BATWING_GLOVES(25549, 1, 25829, 50, 50),
		BATWING_BOOTS(25549, 1, 25833, 50, 50),
		BATWING_HOOD(25549, 2, 25825, 54, 100),
		BATWING_LEGS(25549, 2, 25831, 55, 100),
		BATWING_TORSO(25549, 3, 25827, 56, 150),
		BATWING_SHIELD(25549, 4, 25802, 58, 200),
		BAT_BOOK(25549, 4, 25660, 59, 200),
		BAT_WAND(25549, 4, 25648, 61, 200),
		
		GREEN_D_HIDE_VAMBS(1745, 1, 1065, 57, 62),
		GREEN_D_HIDE_CHAPS(1745, 2, 1099, 60, 124),
		GREEN_D_HIDE_BODY(1745, 3, 1135, 63, 186),
		GREEN_D_HIDE_SHIELD(1745, 4, 25794, 64, 248),

		BLUE_D_HIDE_VAMBS(2505, 1, 2487, 66, 70),
		BLUE_D_HIDE_CHAPS(2505, 2, 2493, 68, 140),
		BLUE_D_HIDE_BODY(2505, 3, 2499, 71, 210),
		BLUE_D_HIDE_SHIELD(2505, 4, 25796, 72, 280),

		RED_D_HIDE_VAMBS(2507, 1, 2489, 73, 78),
		RED_D_HIDE_CHAPS(2507, 2, 2495, 75, 156),
		RED_D_HIDE_BODY(2507, 3, 2501, 77, 234),
		RED_D_HIDE_SHIELD(2507, 4, 25798, 78, 312),

		BLACK_D_HIDE_VAMBS(2509, 1, 2491, 79, 86),
		BLACK_D_HIDE_CHAPS(2509, 2, 2497, 82, 172),
		BLACK_D_HIDE_BODY(2509, 3, 2503, 84, 258),
		BLACK_D_HIDE_SHIELD(2509, 4, 25800, 85, 344),
	
		ROYAL_D_HIDE_VAMBS(24374, 1, 24376, 87, 94),
		ROYAL_D_HIDE_CHAPS(24374, 2, 24379, 89, 188),
		ROYAL_D_HIDE_BODY(24374, 3, 24382, 93, 282),
		
		SIRENIC_MASK(29863, 14, 29854, 91, 500),
		SIRENIC_LEGS(29863, 28, 29860, 92, 1000),
		SIRENIC_BODY(29863, 42, 29857, 93, 1500),
		
		PROTEAN_HIDE(33740, 1, 33741, 1, 234);
		

		private int leatherId, leatherAmount, finalProduct, requiredLevel;
		private double experience;
		private String name;

		private static Map<Integer, LeatherData> leatherItems = new HashMap<Integer, LeatherData>();

		public static LeatherData forId(int id) {
			return leatherItems.get(id);
		}

		static {
			for (LeatherData leather : LeatherData.values()) {
				leatherItems.put(leather.finalProduct, leather);
			}
		}

		private LeatherData(int leatherId, int leatherAmount, int finalProduct,
				int requiredLevel, double experience) {
			this.leatherId = leatherId;
			this.leatherAmount = leatherAmount;
			this.finalProduct = finalProduct;
			this.requiredLevel = requiredLevel;
			this.experience = experience;
			this.name = ItemDefinitions.getItemDefinitions(getFinalProduct())
					.getName().replace("d'hide", "");
		}

		public int getLeatherId() {
			return leatherId;
		}

		public int getLeatherAmount() {
			return leatherAmount;
		}

		public int getFinalProduct() {
			return finalProduct;
		}

		public int getRequiredLevel() {
			return requiredLevel;
		}

		public double getExperience() {
			return experience;
		}

		public String getName() {
			return name;
		}
	}

	public static boolean handleItemOnItem(Player player, Item itemUsed,
			Item usedWith) {
		for (int i = 0; i < LEATHER.length; i++) {
			if (itemUsed.getId() == LEATHER[i]
					|| usedWith.getId() == LEATHER[i]) {
				player.getTemporaryAttributtes().put("leatherType", LEATHER[i]);
				int index = getIndex(player);
				if (index == -1)
					return true;
				int leather = (Integer) player.getTemporaryAttributtes().get(
						"leatherType");
				if (leather == LEATHER[0] || leather == LEATHER[1] || leather == LEATHER[6]) {
				player.getDialogueManager().startDialogue("LeatherCraftingD",
						LeatherData.forId(PRODUCTS[index][0]),
						LeatherData.forId(PRODUCTS[index][1]),
						LeatherData.forId(PRODUCTS[index][2]),
						LeatherData.forId(PRODUCTS[index][3]),
						LeatherData.forId(PRODUCTS[index][4]),
						LeatherData.forId(PRODUCTS[index][5]),
						LeatherData.forId(PRODUCTS[index][6]),
						LeatherData.forId(PRODUCTS[index][7]));
				return true;
				} else if (leather == LEATHER[11] || leather == LEATHER[12]) {
					player.getDialogueManager().startDialogue("LeatherCraftingD",
							LeatherData.forId(PRODUCTS[index][0]),
							LeatherData.forId(PRODUCTS[index][1]),
							LeatherData.forId(PRODUCTS[index][2]));
					return true;
				} else if (leather == LEATHER[2]) {
					player.getDialogueManager().startDialogue("LeatherCraftingD",
							LeatherData.forId(PRODUCTS[index][0]),
							LeatherData.forId(PRODUCTS[index][1]),
							LeatherData.forId(PRODUCTS[index][2]),
							LeatherData.forId(PRODUCTS[index][3]),
							LeatherData.forId(PRODUCTS[index][4]),
							LeatherData.forId(PRODUCTS[index][5]),
							LeatherData.forId(PRODUCTS[index][6]));
					return true;
				} else if (leather == LEATHER[3]) {
					player.getDialogueManager().startDialogue("LeatherCraftingD",
							LeatherData.forId(PRODUCTS[index][0]),
							LeatherData.forId(PRODUCTS[index][1]),
							LeatherData.forId(PRODUCTS[index][2]),
							LeatherData.forId(PRODUCTS[index][3]));
					return true;
				} else if (leather == LEATHER[4] || leather == LEATHER[5]) {
					player.getDialogueManager().startDialogue("LeatherCraftingD",
							LeatherData.forId(PRODUCTS[index][0]),
							LeatherData.forId(PRODUCTS[index][1]),
							LeatherData.forId(PRODUCTS[index][2]),
							LeatherData.forId(PRODUCTS[index][3]),
							LeatherData.forId(PRODUCTS[index][4]));
					return true;
				} else if (leather == LEATHER[7] || leather == LEATHER[8] 
						|| leather == LEATHER[9] || leather == LEATHER[10]){
					player.getDialogueManager().startDialogue("LeatherCraftingD",
							LeatherData.forId(PRODUCTS[index][0]),
							LeatherData.forId(PRODUCTS[index][1]),
							LeatherData.forId(PRODUCTS[index][2]),
							LeatherData.forId(PRODUCTS[index][3]));
					return true;
				} else if (leather == LEATHER[13]){
					player.getDialogueManager().startDialogue("LeatherCraftingD",
							LeatherData.forId(PRODUCTS[index][0]));
					return true;
				}
			}
		}
		return false;
	}

	public static int getIndex(Player player) {
		int leather = (Integer) player.getTemporaryAttributtes().get(
				"leatherType");
		if (leather == LEATHER[0])
			return 0;
		if (leather == LEATHER[1])
			return 1;
		if (leather == LEATHER[2])
			return 2;
		if (leather == LEATHER[3])
			return 3;
		if (leather == LEATHER[4])
			return 4;
		if (leather == LEATHER[5])
			return 5;
		if (leather == LEATHER[6])
			return 6;
		if (leather == LEATHER[7])
			return 7;
		if (leather == LEATHER[8])
			return 8;
		if (leather == LEATHER[9])
			return 9;
		if (leather == LEATHER[10])
			return 10;
		if (leather == LEATHER[11])
			return 11;
		if (leather == LEATHER[12])
			return 12;
		if (leather == LEATHER[13])
			return 13;
		return -1;
	}

	public LeatherCrafting(LeatherData data, int quantity) {
		this.data = data;
		this.quantity = quantity;
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		setActionDelay(player, 1);
		player.setNextAnimation(CRAFT_ANIMATION);
		return true;
	}

	private boolean checkAll(Player player) {
		if (!player.getInventory().containsItemToolBelt(THREAD.getId())) {
			player.getPackets()
			.sendGameMessage("You need a thread to do this.");
			return false;
		}
		if (!player.getInventory().containsItemToolBelt(NEEDLE.getId())) {
			player.getPackets().sendGameMessage(
					"You need a needle to craft leathers.");
			return false;
		}
		if (!player.getInventory().containsOneItem(data.getLeatherId())) {
			player.getPackets().sendGameMessage(
					"You've ran out of "
							+ ItemDefinitions
							.getItemDefinitions(data.getLeatherId())
							.getName().toLowerCase() + ".");
			return false;
		}
		if (player.getInventory().getItems().getNumberOf(data.getLeatherId()) < data
				.getLeatherAmount()) {
			player.getPackets().sendGameMessage(
					"You don't have enough "+ ItemDefinitions
					.getItemDefinitions(data.getLeatherId())
					.getName().toLowerCase() +" to do make a "
					+ItemDefinitions.getItemDefinitions(data.getFinalProduct())
					.getName().toLowerCase()+
					".");
			return false;
		}
		if (player.getSkills().getLevel(
				Skills.CRAFTING) < data.getRequiredLevel()) {
			player.getPackets().sendGameMessage("You need a crafting level of " + data.getRequiredLevel()+ " to craft this.");
			return false;
		}
		return true;
	}

	@Override
	public boolean process(Player player) {
		return checkAll(player);
	}

	@Override
	public int processWithDelay(Player player) {
		player.getInventory().deleteItem(data.getLeatherId(),
				data.getLeatherAmount());
		if(data.getFinalProduct() != 33741)
		player.getInventory().addItem(data.getFinalProduct(), 1);
		player.getSkills().addXp(Skills.CRAFTING,
				data.getExperience());
		if (player.getDailyTask() != null)
			player.getDailyTask().incrementTask(player, 3, data.getFinalProduct(), Skills.CRAFTING);
		player.getPackets().sendGameMessage("You make "+Utils.formatAorAn(new Item(data.getFinalProduct())) + ItemDefinitions.getItemDefinitions(data.getFinalProduct()).getName().toLowerCase() + ".");	
		quantity--;
		removeThread--;
		if (removeThread == 0) {
			removeThread = 5;
			player.getInventory().removeItems(THREAD); // every 5 times, your
			// thread get deleted.
			player.getPackets().sendGameMessage(
					"You use up a reel of your thread.");
		}
		if (Utils.getRandom(30) <= 3) {
			player.getInventory().removeItems(NEEDLE);
			player.getPackets().sendGameMessage("Your needle has broken.");
		}
		if (quantity <= 0)
			return -1;
		player.setNextAnimation(CRAFT_ANIMATION);
		stop(player);
		return 0;
	}

	@Override
	public void stop(final Player player) {
		setActionDelay(player, 1);
	}

}
