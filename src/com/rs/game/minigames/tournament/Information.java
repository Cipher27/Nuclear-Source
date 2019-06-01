package com.rs.game.minigames.tournament;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.robot.RobotScript;
import com.rs.game.player.robot.scripts.combat.AncientHybrid;
import com.rs.game.player.robot.scripts.combat.AncientMage;
import com.rs.game.player.robot.scripts.combat.AncientTribrid;
import com.rs.game.player.robot.scripts.combat.Melee;
import com.rs.game.player.robot.scripts.combat.Range;
import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;


public class Information implements Serializable {

	public static List<Item> addItem(List<Item> items, int section) {
		final Item item = getItem(section);
		for (final Item item2 : items) {
			if (item2.getId() == item.getId()) {
				item.setAmount(item2.getAmount() + item.getAmount());
				final int index = items.indexOf(item2);
				items.set(index, item);
				return items;
			}
		}
		items.add(item);
		return items;
	}

	public static void addLose(int combatIndex, int typeIndex) {
		information.losses[combatIndex][typeIndex] += 1;
		save();
	}

	public static void addWin(int combatIndex, int typeIndex) {
		information.wins[combatIndex][typeIndex] += 1;
		save();
	}

	public static int getCombatIndex(Player player, int typeIndex) {
		for (int combatIndex = MASTER; PURE <= combatIndex; combatIndex--) {
			if (player.getSkills().getLevelForXp(Skills.ATTACK) < Information.ATTACK_REQUIREMENT[combatIndex][typeIndex]) {
				continue;
			}
			if (player.getSkills().getLevelForXp(Skills.DEFENCE) < Information.DEFENCE_REQUIREMENT[combatIndex][typeIndex]) {
				continue;
			}
			if (player.getSkills().getLevelForXp(Skills.MAGIC) < Information.MAGIC_REQUIREMENT[combatIndex][typeIndex]) {
				continue;
			}
			if (player.getSkills().getLevelForXp(Skills.RANGE) < Information.RANGE_REQUIREMENT[combatIndex][typeIndex]) {
				continue;
			}
			return combatIndex;
		}
		return PURE;
	}

	public static Item getItem(int section) {
		final int index = Utils.random(ITEMS[section].length);
		final int index2 = section == 0 ? 2 + Utils
				.random(ITEMS[section][index].length - 2) : Utils
				.random(ITEMS[section][index].length);
				final int amount = section == 0 ? ITEMS[section][index][0]
				+ Utils.random(ITEMS[section][index][1]
						- ITEMS[section][index][0]) : 1;
						return new Item(ITEMS[section][index][index2], amount);
	}

	public static String getMessage(int combatIndex, int typeIndex) {
		final double wins = (information.wins[combatIndex][typeIndex]);
		final double losses = (information.losses[combatIndex][typeIndex]);
		final double ratio = wins == 0 ? 1 : (losses == 0 ? (wins / 1)
				: (wins / losses));
		final String impressedBy = IMPRESSED_BY[Utils
				.random(IMPRESSED_BY.length - 1)] + ".";
		if (ratio >= 4) {
			return "The gods are "
					+ ADJECTIVES[3][Utils.random(ADJECTIVES[3].length - 1)]
					+ " impressed with your " + impressedBy;
		} else if (ratio >= 3) {
			return "The gods are "
					+ ADJECTIVES[3][Utils.random(ADJECTIVES[3].length - 1)]
					+ " impressed with your " + impressedBy;
		} else if (ratio >= 2) {
			return "The gods are "
					+ ADJECTIVES[3][Utils.random(ADJECTIVES[3].length - 1)]
					+ " impressed with your " + impressedBy;
		} else if (ratio >= 1.25) {
			return "The gods are "
					+ ADJECTIVES[2][Utils.random(ADJECTIVES[2].length - 1)]
					+ " impressed with your " + impressedBy;
		} else if (ratio >= 1) {
			return "The gods are "
					+ ADJECTIVES[1][Utils.random(ADJECTIVES[1].length - 1)]
					+ " impressed with your " + impressedBy;
		} else if (ratio < 1) {
			return "The gods are "
					+ ADJECTIVES[0][Utils.random(ADJECTIVES[0].length - 1)]
					+ " impressed with your " + impressedBy;
		}
		return "The god are impressed with your fighting style.";
	}

	public static int getPrayerBook(int combatIndex, int typeIndex,
			int prayerLevel, int defenceLevel) {
		if (defenceLevel < 30)
			return REGULAR;
		if (typeIndex == RANGE || typeIndex == MAGE)
			return REGULAR;
		if (prayerLevel < 95)
			return REGULAR;
		return ANCIENT;
	}

	public static int getReward(int combatIndex, int typeIndex) {
		final double wins = (information.wins[combatIndex][typeIndex]);
		final double losses = (information.losses[combatIndex][typeIndex]);
		final double ratio = wins == 0 ? 1 : (losses == 0 ? (wins / 1)
				: (wins / losses));
		final int max = 100000;
		final int min = 30000;
		int winAmount = (int) (ratio * 20000);
		if (winAmount > max)
			winAmount = max;
		if (winAmount < min)
			winAmount = min;
		return 20000 + Utils.getRandom(winAmount);
	}

	public static List<Item> getRewardItems(int combatIndex, int typeIndex) {
		List<Item> items = new ArrayList<Item>();
		final double wins = (information.wins[combatIndex][typeIndex]);
		final double losses = (information.losses[combatIndex][typeIndex]);
		double ratio = wins == 0 ? 1 : (losses == 0 ? (wins / 1)
				: (wins / losses));
		if (ratio < 0.5)
			ratio = 0.5;
		else if (ratio > 3)
			ratio = 3;
		ratio = 0.15 + ratio / 10;// 0.233 - 0.65
		int section = 0;
		if (Math.random() < ratio)//food and stuff
			items = addItem(items, section);
		if (Math.random() < ratio)
			items = addItem(items, section);
		if (Math.random() < ratio && Utils.getRandom(3) > 0)
			items = addItem(items, section);
		if (Math.random() < ratio && Utils.getRandom(3) > 0)
			items = addItem(items, section);
		section = 1;
		items = addItem(items, section);
		if (Math.random() < ratio && Utils.getRandom(2) > 0)//rune and random
			items = addItem(items, section);
		if (Math.random() < ratio && Utils.getRandom(2) > 0)
			items = addItem(items, section);
		if (Math.random() < ratio && Utils.getRandom(2) > 0)
			items = addItem(items, section);
		section = 2;
		if (Math.random() < ratio && Utils.getRandom(2) == 0) {//infinity and barrows
			items = addItem(items, section);
			if (Math.random() < ratio && Utils.getRandom(2) == 0)
				items = addItem(items, section);
		}
		//section = 3;
		//if (Math.random() < ratio && Utils.getRandom(20) == 0) {//staff of light
		//	items = addItem(items, section);
		//}
		return items;
	}

	public static int getTypeIndex(Player player) {
		boolean willUseRange = false;
		boolean willUseMage = false;
		final boolean usingLunar = player.getCombatDefinitions().getSpellBook() == 430;
		final boolean hasRunes = player.getInventory().containsOneItem(554,
				555, 556, 557, 560, 562, 565);
		boolean hasRangeWeap = PlayerCombat.isRangeWeapon(player.getEquipment()
				.getWeaponId(),player);
		if (!hasRangeWeap) {
			for (int i = 0; i < player.getInventory().getItemsContainerSize(); i++) {
				if (player.getInventory().getItem(i) == null)
					continue;
				if (PlayerCombat.isRangeWeapon(player.getInventory().getItem(i)
						.getId(),player)) {
					hasRangeWeap = true;
					break;
				}
			}
		}
		if (!usingLunar && hasRunes) {
			willUseMage = true;
		}
		if (hasRangeWeap) {
			willUseRange = true;
		}
		if (willUseMage) {
			if (willUseRange) {
				return TRIBRID;
			} else {
				return HYBRID;
			}
		}
		if (willUseRange) {
			return RANGE;
		}
		return MELEE;
	}

	public static void init() {
		final File file = new File(PATH);
		if (file.exists())
			try {
				information = (Information) SerializableFilesManager
						.loadSerializedFile(file);
				print();
				return;
			} catch (final Throwable e) {
				Logger.handle(e);
			}
		information = new Information();
	}

	public static void print() {
		for (int combatIndex = 0; combatIndex < information.wins.length; combatIndex++) {
			for (int typeIndex = 0; typeIndex < information.wins[combatIndex].length; typeIndex++) {
				System.out.println(COMBAT_NAMES[combatIndex] + " "
						+ TYPE_NAMES[typeIndex] + " has wins: "
						+ information.wins[combatIndex][typeIndex]
						+ " and losses: "
						+ information.losses[combatIndex][typeIndex] + ", "
						+ getReward(combatIndex, typeIndex));
			}
		}
	}

	public static void save() {
		try {
			if (World.getPlayers().size() < 5)
				return;// if it's not the actual server
			SerializableFilesManager.storeSerializableClass(information,
					new File(PATH));
		} catch (final Throwable e) {
			Logger.handle(e);
		}
	}

	private static final long serialVersionUID = -49823907673313221L;

	private static Information information;

	private static final String PATH = "data/information.ser";

	public int[][] wins;

	public int[][] losses;

	public static final String[][] ADJECTIVES = new String[][] {
			{ "mildly", "somewhat", "kind of", "moderately", "adequately" },
			{ "quite", "surely", "definitely", "certainly" },
			{ "unquestionably", "undoubtedly", "undeniably", "pleasantly",
					"genuinly", "delightfully" },
			{ "very", "exceptionally", "severely", "terrifically", "terribly",
					"utterly", "extremely" }, };

	public static final String[] IMPRESSED_BY = new String[] { "fighting",
			"fighting style", "fighting ability", "warrior like attributes" };

	public static final String[] GOLD_MESSAGE = new String[] {
			"As a reward, they've decided to shower you with gold.",
			"As a reward, some of their gold will now be yours.",
			"Zues has granted you some of his riches.",
			"Guthix has granted you some of his riches.",
			"Saradomin has granted you some of his riches.",
			"The audience throws what they have left from their coin pouches.",
			"Before you know it, gold falls from the sky.",
			"Glimmering figures fall to you like rain.",
			"Some of the leaves from the tree of gold fall to you.", };

	private static final int[][][] ITEMS = { {// Stackables
					{ 50, 300, 554, 555, 556, 557, 558, 559, 560, 561, 562,
							563, 564, 566 },// Runes
					{ 50, 150, 805, 811, 830, 868, 892, 11212, 11230, 9241,
							9242, 9243, 9244, 9245, 5627, 5641, 5653, 5667,
							6522 },// Arrows/Thrown
					{ 10, 30, 7947, 386, 15273 },// Food(noted)
					{ 10, 30, 3025, 2437, 2441, 2445, 3041, 6686 },// Potions(noted)
				// {5, 10, 13957, 13879, 13883}, // Morrigans
			}, {// Tier 1
					{ 1079, 1093, 1127, 1163, 1201, 4131, 1079, 1093, 1113,
							1127, 1147, 1163, 1185, 1201, 1213, 1247, 1275,
							1289, 1303, 1319, 1333, 1347, 1359, 1373, 1432,
							3101, 3202, 9185 },// Rune
					{ 3122, 4153, 6809, 10564, 10589, 1704, 6585, 1725, 1381,
							1383, 1385, 1387, 14479, 1149, 1187, 1249, 1305,
							1377, 3204, 4087, 4585, 4151, 14490,
							14492, 14494, 14497, 14499, 14501, 1215, 861, 2491,
							2497, 2503, 6322, 6324, 6326, 6328, 6330, 3753,
							3749, 11335 },// Random(dragon/granite)
			}, {// Tier 2
					{ 6916, 6918, 6920, 6922, 6924, 10605, 15600, 15602, 15604,
							15606, 15608, 15610, 15612, 15614, 15616, 15618,
							15620, 15622, 23108, 23109, 23110, 23111, 23112 },// Infinity
					{ 4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4745,
							4747, 4749, 4751, 4732, 4734, 4736, 4738 },// Barrows
					{ 13858, 13861, 13864, 13867, 13932, 13935, 13938, 13941,
							13870, 13873, 13876, 13944, 13947, 13950, 13887,
							13893, 13899, 13905, 13911, 13917, 13923, 13929,
							13884, 13890, 13896, 13902, 13908, 13914, 13920,
							13926 },// Rev armor
			}, {// Tier 3
			{ 22207, 22208, 22209, 22210, 22211, 22212, 22213, 22214 },// Staffs
																		// of
																		// light
			}, };

	public transient static final int PURE = 0, TURMOIL_PURE = 1,
			BERSERKER = 2, MASTER = 3;// Defence types

	public transient static final int MELEE = 0, RANGE = 1, MAGE = 2,
			HYBRID = 3, TRIBRID = 4; // Attack styles

	public transient static final int DHAROK = 0, GUTHAN = 1, VERAC = 2,
			TORAG = 3; // Armour types under melee

	public transient static final int REGULAR = 0, ANCIENT = 1, LUNAR = 2;// Spellbook
																			// types

	public transient static final RobotScript[][] CHEAT_SWITCH_TYPE = new RobotScript[][] {
			{ new Melee(), new Range(), new AncientMage(), new AncientHybrid(),
					new AncientTribrid() },// Pure
			{ new Melee(), new Range(), new AncientMage(), new AncientHybrid(),
					new AncientTribrid() },// Turmoil pure
			{ new Melee(), new Range(), new AncientMage(), new AncientHybrid(),
					new AncientTribrid() },// Berserker
			{ new Melee(), new Range(), new AncientMage(), new AncientHybrid(),
					new AncientTribrid() },// Master
	};

	public transient static final String[] COMBAT_NAMES = new String[] {
			"Pure", "Turmoil pure", "Berserker", "Master" };

	public transient static final String[] TYPE_NAMES = new String[] { "Melee",
			"Range", "Mage", "Hybrid", "Tribrid" };

	public transient static final int[][] CHEAT_SWITCH_SET_IDS = new int[][] {
			{ 85, 86, 87, 194, 195 },// Pure
			{ 73, 74, 76, 180, 188 },// Turmoil pure
			{ 63, 72, 70, 196, 197 },// Berserker
			{ 38, 68, 65, 191, 184 },// Master
	};

	public transient static final int[][] SET_IDS = new int[][] {
			{ 85, 86, 87, 182, 193 },// Pure
			{ 73, 74, 76, 179, 187 },// Turmoil pure
			{ 63, 72, 70, 176, 185 },// Berserker
			{ 38, 68, 65, 192, 66 },// Master
	};

	public transient static final int[][] DEFENCE_REQUIREMENT = new int[][] {
			{ 1, 1, 1, 1, 1 },// Pure
			{ 30, 30, 30, 30, 30 },// Turmoil pure
			{ 40, 40, 40, 40, 40 },// Berserker
			{ 70, 75, 70, 70, 70 },// Master
	};

	public transient static final int[][] ATTACK_REQUIREMENT = new int[][] {
			{ 60, 1, 50, 60, 60 },// Pure
			{ 60, 1, 75, 75, 75 },// Turmoil pure
			{ 70, 1, 75, 75, 75 },// Berserker
			{ 70, 1, 75, 75, 75 },// Master
	};

	public transient static final int[][] MAGIC_REQUIREMENT = new int[][] {
			{ 0, 1, 94, 94, 94 },// Pure
			{ 0, 1, 94, 94, 94 },// Turmoil pure
			{ 0, 1, 94, 94, 94 },// Berserker
			{ 0, 1, 94, 94, 94 },// Master
	};

	public transient static final int[][] RANGE_REQUIREMENT = new int[][] {
			{ 1, 70, 1, 78, 70 },// Pure
			{ 1, 70, 1, 30, 70 },// Turmoil pure
			{ 1, 70, 1, 70, 70 },// Berserker
			{ 1, 70, 1, 70, 70 },// Master
	};

	public transient static final int[] SPELLBOOK = new int[] { LUNAR,// Melee
			LUNAR,// Range
			ANCIENT,// Mage
			ANCIENT,// Hybrid
			ANCIENT,// Tribrid
	};

	public Information() {
		wins = new int[][] { { 0, 0, 0, 0, 0 },// Pure
				{ 0, 0, 0, 0, 0 },// Turmoil pure
				{ 0, 0, 0, 0, 0 },// Berserker
				{ 0, 0, 0, 0, 0 },// Master
		};
		losses = new int[][] { { 0, 0, 0, 0, 0 },// Pure
				{ 0, 0, 0, 0, 0 },// Turmoil pure
				{ 0, 0, 0, 0, 0 },// Berserker
				{ 0, 0, 0, 0, 0 },// Master
		};
	}

	/*
	 * public static enum Type {
	 * 
	 * Zerker_Melee(1, 17.5, 436, 10, 1, 11552, 5, 0), Zerker_Hybrid(1, 17.5,
	 * 436, 10, 1, 11552, 5, 0), Zerker_Tribrid(1, 17.5, 436, 10, 1, 11552, 5,
	 * 0), Copper_Ore(1, 17.5, 436, 10, 1, 11552, 5, 0);
	 * 
	 * private int setId; private String type; private int oreId; private int
	 * oreBaseTime; private int oreRandomTime; private int emptySpot; private
	 * int respawnDelay; private int randomLifeProbability;
	 * 
	 * private Type(int setId) { this.setId = setId; }
	 * 
	 * public int getLevel() { return level; } }
	 */

	/*
	 * public static enum Area {
	 * 
	 * Practice(new int[] {10, 10}, new WorldTile(1, 1, 1)), Tournament(new
	 * int[] {10, 10}, new WorldTile(1, 1 ,1));
	 * 
	 * private int[] ratio; private WorldTile tile;
	 * 
	 * private Area(int[] ratio, WorldTile tile) { this.ratio = ratio; this.tile
	 * = tile; }
	 * 
	 * public int[] getRatio() { return ratio; }
	 * 
	 * public WorldTile getTile() { return tile; } }
	 */

}