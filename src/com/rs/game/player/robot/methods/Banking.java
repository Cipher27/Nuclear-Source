package com.rs.game.player.robot.methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rs.cache.Cache;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.Equipment;
import com.rs.game.player.Skills;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.RouteEvent;
import com.rs.game.player.robot.Robot;
import com.rs.game.player.robot.Set;
import com.rs.game.route.pathfinder.PathFinder;
import com.rs.utils.ItemBonuses;
import com.rs.utils.Utils;

/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public class Banking extends Method {
	
	/**
	 * TODO - Getting farcasted
	 * TODO - Rings
	 * TODO - Stop eating animation during special(test)
	 * TODO - Fighter torso and various helmets
	 * TODO - Masters should use weaker armor
	 * TODO - Iron armor for pures
	 * TODO - Corrupt pvp/rune armor for zerkers
	 * TODO - Include teletabs in inv
	 * TODO - Praying
	 * TODO - Protect item
	 */
	
	public Banking(Robot robot) {
		super(robot);
		stage = Stage.Looking;
	}
	
	private WorldObject bank;
	private NPC npc;
	private boolean banker;
	private boolean walkingAtHome;
	
	public List<Style> styles;
	
	@Override
	public void process() {
		switch(stage) {
			case Looking:
				if (Utils.getRandom(10) == 0) {
					walkingAtHome = true;
				}
				if (walkingAtHome) {
					if (!robot.withinDistance(robot.getHome(), 30)) {
						Magic.sendNormalTeleportSpell(robot, 0, 0, robot.getHome());
						pause(5 + Utils.random(5));
						return;
					}
					if (Utils.random(2) == 0) {
						walkingAtHome = false;
					}
					npc = findNearNPC("");
					if (npc == null) {
						PathFinder.simpleWalkTo(robot, new WorldTile(robot, 5));
					} else {
						int x = Utils.getRandom(1);
						int y = x == 1 ? 0 : 1;
						PathFinder.simpleWalkTo(robot, npc.transform(x, y, 0));	
						robot.setRouteEvent(new RouteEvent(npc, new Runnable() {
							@Override
							public void run() {
								npc.resetWalkSteps();
								robot.faceEntity(npc);
								npc.faceEntity(robot);
							}

						}, true));
					}
					pause(40+Utils.random(40));
					return;
				}
				bank = findNearObject("bank booth");
				if (bank == null) {
					npc = findNearNPC("banker");
					if (npc == null) {
						int teletab = 8007 + Utils.random(4);
							Magic.sendNormalTeleportSpell(robot, 0, 0, Combat.EDGEVILLE);
					} else {
						banker = true;
						stage = Stage.Running;
					}
				} else {
					banker = false;
					stage = Stage.Running;
				}
				pause(5);
				break;
			case Waiting:
				getScript().equipping.chance = 60;
				getScript().prayer.stage = Stage.Running;
				stage = Stage.Finished;
				getScript().combat.setStage(Stage.Looking);
				break;
			case Running:
				if (robot.withinDistance(bank, 2) || robot.withinDistance(npc, 2)) {
					// use bank
					stage = Stage.Waiting;
					getScript().foodsAndPotions.stage = Stage.Running;
					getScript().equipping.chance = 8;
					useBank();
					pause(60 + Utils.random(50));
				} else {
					// run to bank
					/*if (!robot.withinDistance(bank, 50)) {
						stage = Stage.Looking;
						return;
					}*/
					if (!robot.hasWalkSteps()) {
						PathFinder.simpleWalkTo(robot, banker ? npc : bank);
					} else {
						//randomly stop
						if (Utils.getRandom(1) == 0) {
							robot.resetWalkSteps();
						}
					}
				}
				pause(5 + Utils.getRandom(4));
				break;
			default:
				break;
		}
	}
	
	public void useBank() {
		//int setId = Utils.random(300);
		final Set set = makeSet();//SetManager.list.get(setId);
		Item[] items = set.getEquip();
		robot.getCombatDefinitions().setAutoRelatie(true);
		getScript().equipping.chance = 5;
		for (int o = 0; o < items.length; o++) {
			if (items[o] == null || items[o].getId() <= 1)
				continue;
			getScript().equipping.spawn(o, items[o]);
			//robot.getEquipment().wieldOneItem(o, items[o]);
		}
		items = set.getInv();
		for (int o = 0; o < items.length; o++) {
			if (items[o] == null || items[o].getId() <= 1)
				continue;
			robot.getInventory().getItems().set(o, items[o]);
			robot.getInventory().refresh(o);
		}
		//if (Utils.getRandom(2) != 0)
		//	robot.getPrayer().setPrayerBook(true);
		robot.reset();
	}
	
	public WorldObject findNearObject(String name) {
		return findNearObject(name, robot.getPlane());
	}

	public WorldObject findNearObject(String name, int height) {
		/*int lastDistance = 0;
		Entity target = null;
		for (Entity e : getPossibleTargets()) {
			int currentDistance = Utils.getDistance(this, e);
			if (lastDistance <= currentDistance) {
				lastDistance = currentDistance;
				target = e;
			}
			return target;
		}
		int lastDistance = 0;*/
		final List<WorldObject> objects = new ArrayList<WorldObject>();
		for (final int i : robot.getMapRegionsIds())
			if (World.getRegion(i).getAllObjects() != null) {
				for (final WorldObject object : World.getRegion(i).getAllObjects()) {
					if (object.withinDistance(robot, 50)
							&& object.getPlane() == height
							&& object.getDefinitions().name.toLowerCase().contains(
									name.toLowerCase())) {
						objects.add(object);
					}
				}
			}
		if (!objects.isEmpty())
			return objects.get(Utils.random(objects.size()));
		return null;
	}
	
	public NPC findNearNPC(String name) {
		return findNearNPC(name, robot.getPlane());
	}
	
	public NPC findNearNPC(String name, int height) {
		final List<NPC> npcs = new ArrayList<NPC>();
		for (final int i : robot.getMapRegionsIds()) {
			if (World.getRegion(i).getNPCsIndexes() != null) {
				final List<Integer> npcsIndexes = World.getRegion(i)
						.getNPCsIndexes();
				if (npcsIndexes != null) {
					for (@SuppressWarnings("unused") int npcIndex : npcsIndexes) {
					if (npc.withinDistance(robot, 15)
							&& npc.getPlane() == height
							&& npc.getDefinitions().name.toLowerCase().contains(
									name.toLowerCase())) {
						npcs.add(npc);
					}
				}
				}
			}
		}
		if (!npcs.isEmpty())
			return npcs.get(Utils.random(npcs.size()));
		return null;
	}
	
	/*
	 * SET CREATION
	 */
	
	public static void main(String[] blabla) {
		try {
			Cache.init();
			ItemBonuses.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int itemId = 0; itemId < Utils.getItemDefinitionsSize(); itemId++) {
			if (Equipment.getItemSlot(itemId) == Equipment.SLOT_LEGS
					|| Equipment.getItemSlot(itemId) == Equipment.SLOT_CHEST
					|| Equipment.getItemSlot(itemId) == Equipment.SLOT_HAT) {
				int[] bonuses = ItemBonuses.getItemBonuses(itemId);
				if (bonuses[CombatDefinitions.MAGIC_ATTACK] < 0) {
					System.out.println(itemId +" - "+ new Item(itemId).getName());
				}
			}
		}
	}
	
	public enum Style {
		MELEE, RANGE, MAGE
	}
	
	public int getCape(Style style) {
		int combatLevel = robot.getSkills().getCombatLevel();
		switch(style) {
			case MELEE:
				if (combatLevel >= 100) {
					if (Utils.random(100) < 10) {
						return 23659;
					}
				}
				if (combatLevel >= 60) {
					int chance = combatLevel - 40;
					if (Utils.random(100) < chance) {
						return 6570;
					}
				}
				int skillcape = 9747 + Utils.random(22) * 3 + Utils.getRandom(1);
				if (canWear(skillcape)) {
					return skillcape;
				}
				if (Utils.getRandom(1) == 0) {
					switch(Utils.random(4)) {
						case 0:
							return 6568;
						case 1:
							return 1052;
						case 2:
							return 4315 + Utils.random(49) * 2;
						case 3:
							return 6568;
					}
				}
				break;
			case RANGE:
				if (combatLevel >= 40) {
					return 10499;
				}
				return 4315 + Utils.random(49) * 2;
			case MAGE:
				if (Utils.random(3) == 0) {
					return 10446 + Utils.random(2) * 2;
				}
				return 2412 + Utils.random(2);
		}
		return -1;
	}
	
	/**
	 * @return platelegs, platebody, helm
	 */
	public int[] getOutfit(Style style) {
		switch(style) {
			case MELEE:
				for(int[] outfit : meleeOutfits) {
					int testId = outfit[0];
					int chance = outfit[3];
					if (Utils.random(100) < chance && canWear(testId)) {
						return new int[] {
								outfit[0], 
								outfit[1], 
								outfit[2]};
					}
				}
				break;
			case RANGE:
				for(int[] outfit : rangeOutfits) {
					int testId = outfit[2];
					int chance = outfit[3];
					if (Utils.random(100) < chance && canWear(testId)) {
						return new int[] {
								outfit[0], 
								outfit[1], 
								outfit[2]};
					}
				}
				break;
			case MAGE:
				for(int[][] outfit : magicOutfits) {
					int testId = outfit[0][0];
					if (canWear(testId) && Utils.random(100) < 60) {
						int index = Utils.random(outfit.length);
						if (Utils.getRandom(1) == 0)
							return new int[] {
								outfit[index][0], 
								outfit[index][1], 
								outfit[index][2]};
						// different colors
						return new int[] {
								outfit[Utils.random(outfit.length)][0], 
								outfit[Utils.random(outfit.length)][1], 
								outfit[Utils.random(outfit.length)][2]};
					}
				}
				return new int[] {6107, 6108, 6109};
		}
		return null;
	}
	
	public int getBoots(Style style) {
		switch(style) {
			case MELEE:
				for(int i = boots[0].length - 1; i > 0; i--) {
					int itemId = boots[0][i];
					int chance = 20;
					if (Utils.random(100) < (chance += 100) && canWear(itemId))
						return itemId;
				}
				break;
			case RANGE:
				for(int i = boots[1].length - 1; i > 0; i--) {
					int itemId = boots[1][i];
					if (canWear(itemId) && Utils.random(100) < 90)
						return itemId;
				}
				break;
			case MAGE:
				for(int i = boots[2].length - 1; i > 0; i--) {
					int itemId = boots[2][i];
					if (canWear(itemId) && Utils.random(100) < 90)
						return itemId;
				}
		}
		return -1;
	}
	
	public int getBody(Style style) {
		if (robot.getSkills().getLevelForXp(Skills.DEFENCE) >= 70)
			return -1;
		if (style == Style.MELEE) {
			if (canWear(10551))
				return 10551;
		}
		return -1;
	}
	
	public int getHat(Style style) {
		if (style == Style.MELEE) {
			if (Utils.getRandom(2) > 0) {
				if (canWear(10828))
					return 10828;
				if (canWear(3751))
					return 3751;
			}
			if (canWear(3753))
				return 3753;
		} else if (style == Style.MAGE) {
			if (Utils.getRandom(1) == 0) {
				if (canWear(3755))
					return 3755;
			}
			if (canWear(6918))
				return 6918;
		} else if (style == Style.RANGE) {
			if (Utils.getRandom(1) == 0) {
				if (canWear(3749))
					return 3749;
			}
			if (canWear(2581))
				return 2581;
		}
		return -1;
	}
	
	public int getGloves(Style style) {
		for(int i = gloves.length - 1; i > 0; i--) {
			int itemId = gloves[i];
			if (canWear(itemId))
				return itemId;
		}
		return -1;
	}
	
	public int getAmulet(Style style) {
		if (Utils.random(10) == 0) {
			return 6585;
		}
		if (style == Style.MELEE && Utils.random(2) == 0) {
			return 1725;
		}
		return 1712;
	}
	
	public int getWeapon(Style style) {
		for(int[] weapon : weapons) {
			int itemId = weapon[0];
			int chance = weapon[1];
			if (Utils.random(100) < chance && canWear(itemId))
				return itemId;
		}
		return -1;
	}
	
	public int[] getRangeWeapons(Style style) {
		for(int[][] info : rangeWeapons) {
			int itemId = info[0][0];
			int[] ammo = Arrays.copyOfRange(info[0], 1, info[0].length);
			int chance = info[1][0];
			if (Utils.random(100) < chance && canWear(itemId))
				return new int[] { itemId, ammo[Utils.random(ammo.length)] };
		}
		return new int[] {-1, -1};
	}
	
	public int getShield(Style style) {
		/*for(int itemId : shields) {
			if (canWear(itemId))
				return itemId;
		}*/
		switch(style) {
			case MELEE:
				for(int i = 0; i < shields[0].length; i++) {
					int itemId = shields[0][i];
					int chance = 100;//20;
					if (Utils.random(100) < (chance += 100) && canWear(itemId))
						return itemId;
				}
				break;
			case RANGE:
				for(int i = 0; i < shields[1].length; i++) {
					int itemId = shields[1][i];
					int chance = 10;
					if (Utils.random(100) < (chance += 100/*30*/) && canWear(itemId))
						return itemId;
				}
				break;
			case MAGE:
				for(int i = 0; i < shields[2].length; i++) {
					int itemId = shields[2][i];
					int chance = 100;//20;
					if (Utils.random(100) < (chance += 40) && canWear(itemId))
						return itemId;
				}
		}
		return -1;
	}
	
	public int getSpecialWeapon(Style style) {
		for(int[] weapon : specials) {
			int itemId = weapon[0];
			int chance = weapon[1];
			if (Utils.random(100) < chance && canWear(itemId))
				return itemId;
		}
		return -1;
	}
	
	/**
	 * best to worst
	 * hat, top, bottom
	 */
	public static final int[][][] magicOutfits = new int[][][] {
			{{4708, 4712, 4714}},//ahrim
			{{6916, 6918, 6924},//infinity
			{15600, 15602, 15604},
			{15606, 15608, 15610},
			{15612, 15614, 15616},
			{15618, 15620, 15622}},
			{{4089, 4091, 4093},//mystic
			{4099, 4101, 4103},
			{4109, 4111, 4133}},
			{{7398, 7399, 7340}},//enchanted
			{{3385, 3387, 3389}},//splitbark
			{{6137, 6139, 6141}},//skeletal
			{{6107, 6108, 6109}},//ghostly
			{{579, 577, 1011}},//wizard
	};
	
	/**
	 * best to worst
	 * hat, top, bottom
	 */
	public static final int[][] meleeOutfits = new int[][] {
			//{26737, 26743, 26741, 3}, //white torva
			{13896, 13884, 13890, 8}, //statius
			{10828, 11724, 11726, 8}, //bandos
			{10828, 13887, 13893, 8}, //vesta
			{4716, 4720, 4722, 25}, //dharok
			{4745, 4749, 4751, 15}, //torag
			{1149, 3140, 4087, 10}, //dragon
			{1163, 1127, 1079, 80}, //rune
			{14494, 14492, 14490, 50}, //elite black
			{10589, 10564, 6809, 100}, //granite
			{1161, 1123, 1073, 100}, //adamant
			{5574, 5575, 5576, 100}, //initiate
			{1159, 1121, 1071, 100}, //mithril
			{1157, 1119, 1069, 100}, //steel
			{1153, 1115, 1067, 100}, //iron
			{1155, 1117, 1075, 100}, //bronze
	};
	
	public static final int[][] rangeOutfits = new int[][] {
		{10828, 2503, 2497, 100}, //black
		{-1, 2501, 2495, 100}, //red
		{-1, 2499, 2493, 100}, //blue
		{-1, 2497, 2491, 100}, //green
		{6326, 6322, 6324, 100}, //snakeskin
		{-1, 1129, 1095, 100}, //leather
	};
	
	public static final int[][] capes = new int[][] {
			{1052, 6570}, // Melee
			{10498, 10499}, // Range
			{104}, // Mage
	};
	
	public static final int[][] boots = new int[][] {
			{9006, 3105, 4119, 4121, 4123, 4125, 4127, 4129, 4131, 11728, 11732}, // Melee
			{1061, 2577, 6666, }, // Range
			{3107, 3393, 2579, 6920 }, // Mage
	};
	
	public static final int[][] rings = new int[][] {
			{6737, 15220}, // Melee
			{6733, 15019}, // Range
			{6731, 9104, 15018}, // Mage
	}; //24926 summer sun ring
	
	public static final int[] gloves = new int[] {
		7453, 7454, 7455, 7456, 7457, 7458, 7459, 7460, 7461, 7462, 
	};
	
	public static final int[][] weapons = new int[][] {
		{ 13902, 2 }, { 13899, 2 }, { 11698, 5 }, { 11696, 5 }, { 18349, 5 } , { 18351, 3 } , { 18353, 3 } , 
		{ 4151, 90 }, { 4587, 100 }, { 1333, 100 }, { 6528, 100}, 
	};
	
	public static final int[][][] rangeWeapons = new int[][][] {
		// crossbows
		{ {18357, 9144, 9244 }, { 5 } }, { { 9185, 9144, 9244 }, { 80 } }, 
		// bows
		{ { 861, 892, 5627 }, { 100 } }, { { 857, 892, 890 }, { 100 } }, { { 853, 890 }, { 100 } }, 
	};
	
	public static final int[][] specials = new int[][] {
		{ 11694, 5 }, { 26705, 5 }, { 1215, 100 }, 
	};
	
	public static final int[][] shields = new int[][] {
		{20072, 8850, 8849, 8848, 8847, 8846, 8845, 8844}, // Melee
		{/*13740, 18359, */11283, 1201, 1199, 1197, 1195, 1193, 1191}, // Range
		{/*13740, 13738, */6889}, // Mage
	};
	
	public List<Integer> mainWeapons = new ArrayList<Integer>();
	public List<Integer> specialWeapons = new ArrayList<Integer>();
	public List<Integer> meleeArmor = new ArrayList<Integer>();
	public List<Integer> rangeArmor = new ArrayList<Integer>();
	public List<Integer> mageArmor = new ArrayList<Integer>();
	
	public Set makeSet() {
		boolean range = false, mage = false, melee = true;
		styles = new ArrayList<Style>();
		styles.clear();
		if (Utils.getRandom(2) == 0) {
			range = true;
			melee = false;
			styles.add(Style.RANGE);
		} else {
			styles.add(Style.MELEE);
		}
		Set set = new Set();
		int invSlot = 0;
		Style style = styles.get(0);
		set.addEquip(new Item(getGloves(style)), Equipment.SLOT_HANDS);
		set.addEquip(new Item(getAmulet(style)), Equipment.SLOT_AMULET);
		set.addEquip(new Item(getCape(style)), Equipment.SLOT_CAPE);
		if (melee) {
			set.addEquip(new Item(getWeapon(style)), Equipment.SLOT_WEAPON);
		} else if (range) {
			int[] weapons = getRangeWeapons(style);
			set.addEquip(new Item(weapons[0]), Equipment.SLOT_WEAPON);
			set.addEquip(new Item(weapons[1], 50), Equipment.SLOT_ARROWS);
		}
		set.addEquip(new Item(getBoots(style)), Equipment.SLOT_FEET);
		if (set.getEquip()[Equipment.SLOT_WEAPON] == null) {
			set.addEquip(new Item(6528), Equipment.SLOT_WEAPON);
		}
		boolean twoHanded = Equipment.isTwoHandedWeapon(new Item(set.getEquip()[Equipment.SLOT_WEAPON].getId()));
		if (!twoHanded) {
			set.addEquip(new Item(getShield(style)), Equipment.SLOT_SHIELD);
		} else {
			robot.getEquipment().getItems().set(Equipment.SLOT_SHIELD, null);
		}
		int[] outfit = getOutfit(style);
		set.addEquip(new Item(outfit[0]), Equipment.SLOT_HAT);
		set.addEquip(new Item(outfit[1]), Equipment.SLOT_CHEST);
		set.addEquip(new Item(outfit[2]), Equipment.SLOT_LEGS);
		if (melee && Utils.random(3) == 0) {
			if (Utils.getRandom(1) == 0) {//body
				int body = getBody(style);
				if (body != -1)
					set.addEquip(new Item(getBody(style)), Equipment.SLOT_CHEST);
			}
			if (Utils.getRandom(1) == 0) {//helm
				int hat = getHat(style);
				if (hat != -1)
					set.addEquip(new Item(getHat(style)), Equipment.SLOT_HAT);
			}
		} else {
			if (set.getEquip()[Equipment.SLOT_CHEST].getId() == 4720) {
				set.addInv(new Item(4718), invSlot++);
			}
		}
		mainWeapons.add(set.getEquip()[Equipment.SLOT_WEAPON].getId());
		if (set.getEquip()[Equipment.SLOT_SHIELD] != null) {
			mainWeapons.add(set.getEquip()[Equipment.SLOT_SHIELD].getId());
		}
		if (mage) {
			int runeId = 0;
			set.addInv(new Item(runeId), invSlot++);
		}
		boolean overload = Utils.random(2) == 0;
		//donator flasks
		if (overload) {
			set.addInv(new Item(15332), invSlot++);
		} else {
			if (mage)
				set.addInv(new Item(3040), invSlot++);
			if (melee) {//if attack isn't 1
				set.addInv(new Item(2436), invSlot++);
				set.addInv(new Item(2440), invSlot++);
			}
			if (range)
				set.addInv(new Item(2444), invSlot++);
		}
		set.addInv(new Item(6685), invSlot++);//sara brew
		set.addInv(new Item(6685), invSlot++);//sara brew
		set.addInv(new Item(3024), invSlot++);//sup restore
		set.addInv(new Item(3024), invSlot++);//sup restore
		set.addInv(new Item(8007 + Utils.random(4)), invSlot++);
		if (!mage) {
			if (robot.getSkills().getLevel(Skills.MAGIC) >= 94) {
				set.addInv(new Item(557, 150), invSlot++);
				set.addInv(new Item(9075, 60), invSlot++);
				set.addInv(new Item(560, 30), invSlot++);
			}
		}
		if (!melee) {
			int shield = getShield(Style.MELEE);
			set.addInv(new Item(shield), invSlot++);
			specialWeapons.add(shield);
		}
		boolean wearingSpecialWeapon = set.getEquip()[Equipment.SLOT_WEAPON].getId() == 26781
				|| set.getEquip()[Equipment.SLOT_WEAPON].getId() == 26780
				|| set.getEquip()[Equipment.SLOT_WEAPON].getId() == 11696
				|| set.getEquip()[Equipment.SLOT_WEAPON].getId() == 11698;
		if (!wearingSpecialWeapon) {
			int specialId = getSpecialWeapon(style);
			if (specialId != -1) {
				specialWeapons.add(specialId);
				set.addInv(new Item(specialId), invSlot++);
			}
		} else {
			specialWeapons.add(set.getEquip()[Equipment.SLOT_WEAPON].getId());
		}
		boolean brid = false;
		if (brid) {
			
		} else {
			
		}
		int foodId = 15272;
		for(int i = invSlot; i < 28; i++) {
			set.addInv(new Item(foodId), i);
		}
		return set;
	}
	
	public Set makeEquipment(Set set) {
		return set;
	}
	
	public Set makeInventory(Set set) {
		return set;
	}
	
	public boolean canWear(int itemId) {
		return ItemConstants.canWear(new Item(itemId), robot);
	}
	
}
