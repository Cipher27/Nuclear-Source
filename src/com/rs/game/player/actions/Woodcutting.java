package com.rs.game.player.actions;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.impl.LumberJackAchievement;
import com.rs.game.player.content.items.BirdNests;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.utils.Utils;

public final class Woodcutting extends Action {
	
	public enum HatchetDefinitions {
		NOVITE(16361, 1, 1, 13118),

		BATHUS(16363, 10, 4, 13119),

		MARMAROS(16365, 20, 5, 13120),

		KRATONITE(16367, 30, 7, 13121),

		FRACTITE(16369, 40, 10, 13122),

		ZEPHYRIUM(16371, 50, 12, 13123),

		ARGONITE(16373, 60, 13, 13124),

		KATAGON(16373, 70, 15, 13125),

		GORGONITE(16375, 80, 17, 13126),

		PROMETHIUM(16379, 90, 19, 13127),

		PRIMAL(16381, 99, 21, 13128),

		BRONZE(1351, 1, 1, 879),

		IRON(1349, 5, 2, 877),

		STEEL(1353, 5, 3, 875),

		BLACK(1361, 11, 4, 873),

		MITHRIL(1355, 21, 5, 871),

		ADAMANT(1357, 31, 7, 869),

		RUNE(1359, 41, 10, 867),

		DRAGON(6739, 61, 13, 2846),

		INFERNO(13661, 61, 13, 10251),
		
		CRYSTAL(32645, 71, 15, 25003)
		;
	
		private int itemId, levelRequried, axeTime, emoteId, axeHead;
	
		private HatchetDefinitions(int itemId, int levelRequried, int axeTime, int emoteId) {
		    this.itemId = itemId;
		    this.levelRequried = levelRequried;
		    this.axeTime = axeTime;
		    this.emoteId = emoteId;
		}

		public int getItemId() {
		    return itemId;
		}

		public int getLevelRequried() {
		    return levelRequried;
		}

		public int getAxeTime() {
		    return axeTime;
		}

		public int getEmoteId() {
		    return emoteId;
		}
	   }

	public static enum TreeDefinitions {
		/*
		int level, double xp, int logsId,int logBaseTime, int logRandomTime, int stumpId,int respawnDelay, int randomLifeProbabilit*/
		VINES(1, 0, -1, 20, 4, 77372, 4, 0),

		NORMAL(1, 25, 1511, 20, 4, 1341, 8, 0), // TODO
		
		JUNGLE(1, 25, 1511, 20, 4, 1341, 8, 0), // TODO
		
		EVERGREEN(1, 25, 1511, 20, 4, 	57931, 8, 0),
		
		DEAD(1, 25, 1511, 20, 4, 12733, 8, 0),

		OAK(15, 37.5, 1521, 30, 4, 1341, 15, 15), // TODO

		WILLOW(30, 67.5, 1519, 60, 4, 1341, 51, 15), // TODO

		MAPLE(45, 100, 1517, 83, 16, 31057, 72, 10),

		YEW(60, 175, 1515, 120, 17, 1341, 94, 10), // TODO

		IVY(68, 332.5, -1, 120, 17, 46319, 58, 10),

		MAGIC(75, 250, 1513, 150, 21, 37824, 121, 10),

		CURSED_MAGIC(82, 250, 1513, 150, 21, 37822, 121, 10),
		
		ELDER(90, 385, 29556, 170, 25, -1, 1, 50000000),
		
		CRYSTAL_SHARD(94, 520, 32622, 130, 21, -1, 1, 120),
		
		//divination
		DIVINE_MAGIC(75, 250, 1513, 10, 5, 37824, 121, 0),
		
		DIVINE_YEW(60, 175, 1515, 10, 5, 1341, 94, 10),
		
		DIVINE_MAPLE(45, 100, 1517, 10, 5, 31057, 72, 10),
		
		DIVINE_WILLOW(30, 67.5, 1519, 10, 5, 1341, 51, 15),
		
		DIVINE_OAK(15, 37.5, 1521, 10, 5, 1341, 15, 15),
		
		DIVINE_NORMAL(1, 25, 1511, 10, 5, 1341, 8, 0),
		
		DRAMEN(36, 0, 771, 20, 4, -1, 8, 0),
		
		BLOODWOOD(85, 320, 24121, 25, 21, 16266, 140, 12),
		
		//jadinko's
		MUTATED_VINE(83, 140, 21358, 83, 16, -1, 72, 0),

		CURLY_VINE(83, 140, -1, 83, 16, 12279, 72, 0),
		
		EVIL_ROOT(1, 25, 14666, 20, 4, 11426, 51, 30),

		CURLY_VINE_COLLECTABLE(83, 140, -1, 83, 16, 12283, 72, 0),

		STRAIT_VINE(83, 140,-1, 83, 16, 12277, 72, 0),
		STRAIT_VINE_QUEEN(83, 140,-1, 83, 16, 12277, 72, 0),
		
		FRUIT_TREES(1, 25, -1, 20, 4, 1341, 8, 0),

		STRAIT_VINE_COLLECTABLE(83, 140, -1, 83, 16, 12283, 72, 0),
		/**
		 * dungeoneering;
		 */
		TANGLE_GUM_VINE(1, 35, 17682, 20, 4, 49706, 8, 5),

		SEEPING_ELM_TREE(10, 60, 17684, 25, 4, 49708, 12, 5),

		BLOOD_SPINDLE_TREE(20, 85, 17686, 35, 4, 49710, 16, 5),

		UTUKU_TREE(30, 115, 17688, 60, 4, 49712, 51, 5),

		SPINEBEAM_TREE(40, 145, 17690, 76, 16, 49714, 68, 5),

		BOVISTRANGLER_TREE(50, 175, 17692, 85, 16, 49716, 75, 5),

		THIGAT_TREE(60, 210, 17694, 95, 16, 49718, 83, 10),

		CORPESTHORN_TREE(70, 245, 17696, 111, 16, 49720, 90, 10),

		ENTGALLOW_TREE(80, 285, 17698, 120, 17, 49722, 94, 10),

		GRAVE_CREEPER_TREE(90, 330, 17700, 150, 21, 49724, 121, 10);
		
		private int level;
		private double xp;
		private int logsId;
		private int logBaseTime;
		private int logRandomTime;
		private int stumpId;
		private int respawnDelay;
		private int randomLifeProbability;

		private TreeDefinitions(int level, double xp, int logsId,
				int logBaseTime, int logRandomTime, int stumpId,
				int respawnDelay, int randomLifeProbability) {
			this.level = level;
			this.xp = xp;
			this.logsId = logsId;
			this.logBaseTime = logBaseTime;
			this.logRandomTime = logRandomTime;
			this.stumpId = stumpId;
			this.respawnDelay = respawnDelay;
			this.randomLifeProbability = randomLifeProbability;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}

		public int getLogsId() {
			return logsId;
		}

		public int getLogBaseTime() {
			return logBaseTime;
		}

		public int getLogRandomTime() {
			return logRandomTime;
		}

		public int getStumpId() {
			return stumpId;
		}

		public int getRespawnDelay() {
			return respawnDelay;
		}

		public int getRandomLifeProbability() {
			return randomLifeProbability;
		}
		
	
	}

	private WorldObject tree;
	private TreeDefinitions definitions;

	private int emoteId;
	private boolean usingBeaver = false;
	private int axeTime;

	public Woodcutting(WorldObject tree, TreeDefinitions definitions) {
		this.tree = tree;
		this.definitions = definitions;
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		player.getPackets()
				.sendGameMessage(
						usingBeaver ? "Your beaver uses its strong teeth to chop down the tree..."
								: "You swing your hatchet at the "
										+ (TreeDefinitions.IVY == definitions ? "ivy"
												: "tree") + "...", true);
		player.faceObject(tree);										
		setActionDelay(player, getWoodcuttingDelay(player));
		return true;
	}

	private int getWoodcuttingDelay(Player player) {
		int summoningBonus = player.getFamiliar() != null ? (player
				.getFamiliar().getId() == 6808 || player.getFamiliar().getId() == 6807) ? 10
				: 0
				: 0;
		int wcTimer = definitions.getLogBaseTime()
				- (player.getSkills().getLevel(8) + summoningBonus)
				- Utils.getRandom(axeTime);
		if (wcTimer < 1 + definitions.getLogRandomTime())
			wcTimer = 1 + Utils.getRandom(definitions.getLogRandomTime());
		wcTimer /= player.getAuraManager().getWoodcuttingAccurayMultiplier();
		return wcTimer;
	}

	private boolean checkAll(Player player) {
		if (!hasAxe(player)) {
			player.getPackets().sendGameMessage(
					"You need a hatchet to chop down this tree.");
			return false;
		}
		if (!setAxe(player)) {
			player.getPackets().sendGameMessage(
					"You dont have the required level to use that axe.");
			return false;
		}
		if (!hasWoodcuttingLevel(player))
			return false;
		if (!player.getInventory().hasFreeSlots() && definitions != TreeDefinitions.VINES) {
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			return false;
		}
		return true;
	}

	private boolean hasWoodcuttingLevel(Player player) {
		if (definitions.getLevel() > player.getSkills().getLevel(8)) {
			player.getPackets().sendGameMessage(
					"You need a woodcutting level of " + definitions.getLevel()
							+ " to chop down this tree.");
			return false;
		}
		return true;
	}
	
	public static int getAxeAnim(Player player) {
		int level = player.getSkills().getLevel(8);
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId != -1) {
			
			
			switch (weaponId) {
			case 32645: //crystal axe
				if (level >= 71) {
					return 25003;
				}
				break;
			case 6739: // dragon axe
				if (level >= 61) {
					return 2846;
				}
				break;
			case 1359: // rune axe
				if (level >= 41) {
					return 867;
				}
				break;
			case 1357: // adam axe
				if (level >= 31) {
					return 869;
				}
				break;
			case 1355: // mit axe
				if (level >= 21) {
					return 871;
				}
				break;
			case 1361: // black axe
				if (level >= 11) {
					return 873;
				}
				break;
			case 1353: // steel axe
				if (level >= 6) {
					return 875;
				}
				break;
			case 1349: // iron axe
				return 877;
				
			case 1351: // bronze axe
				return 879;
				
			case 13661: // Inferno adze
				if (level >= 61) {
					return 10251;
				}
				break;
			}
		}
		if (player.getInventory().containsOneItem(32645)) {
			if (level >= 71) {
				return 25003;
			}
		}if (player.getInventory().containsOneItem(6739)) {
			if (level >= 61) {
				return 2846;
			}
		}
		if (player.getInventory().containsOneItem(1359)) {
			if (level >= 41) {
				return 867;
			}
		}
		if (player.getInventory().containsOneItem(1357)) {
			if (level >= 31) {
				return 869;
			}
		}
		if (player.getInventory().containsOneItem(1355)) {
			if (level >= 21) {
				return 871;
			}
		}
		if (player.getInventory().containsOneItem(1361)) {
			if (level >= 11) {
				return 873;
			}
		}
		if (player.getInventory().containsOneItem(1353)) {
			if (level >= 6) {
				return 875;
			}
		}
		if (player.getInventory().containsOneItem(1349)) {
			return 877;
		}
		if (player.getInventory().containsOneItem(1351)) {
			return 879;
		}
		if (player.getInventory().containsOneItem(13661)) {
			if (level >= 61) {
				return 10251;
			}
		}
		return -1;
	}

	private boolean setAxe(Player player) {
		int level = player.getSkills().getLevel(8);
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId != -1) {
			switch (weaponId) {
			case 32645: // crystal axe
				if (level >= 71) {
					emoteId = 25003;
					axeTime = 15;
					return true;					
				}
				break;case 6739: // dragon axe
				if (level >= 61) {
					emoteId = 2846;
					axeTime = 13;
					return true;					
				}
				break;
			case 1359: // rune axe
				if (level >= 41) {
					emoteId = 867;
					axeTime = 10;
					return true;
				}
				break;
			case 1357: // adam axe
				if (level >= 31) {
					emoteId = 869;
					axeTime = 7;
					return true;
				}
				break;
			case 1355: // mit axe
				if (level >= 21) {
					emoteId = 871;
					axeTime = 5;
					return true;
				}
				break;
			case 1361: // black axe
				if (level >= 11) {
					emoteId = 873;
					axeTime = 4;
					return true;
				}
				break;
			case 1353: // steel axe
				if (level >= 6) {
					emoteId = 875;
					axeTime = 3;
					return true;
				}
				break;
			case 1349: // iron axe
				emoteId = 877;
				axeTime = 2;
				return true;
			case 1351: // bronze axe
				emoteId = 879;
				axeTime = 1;
				return true;
			case 13661: // Inferno adze
				if (level >= 61) {
					emoteId = 10251;
					axeTime = 13;
					return true;
				}
				break;
			}
		}
		////CRYSTAL(32645, 71, 15, 25003, 0)
		if (player.getInventory().containsOneItem(32645)|| player.getInventory().containsItemToolBelt(32645)) {
			if (level >= 71) {		
				emoteId = 25003;
				axeTime = 15;
				return true;
			}
		}if (player.getInventory().containsOneItem(6739)|| player.getInventory().containsItemToolBelt(6739)) {
			if (level >= 61) {
				if(player.DIVINECHOPPING) {
						emoteId = 21279;
						axeTime = 13;
				} else {
				emoteId = 2846;
				axeTime = 13;
				return true;
				}
			}
		}
		if (player.getInventory().containsOneItem(1359)|| player.getInventory().containsItemToolBelt(1359)) {
			if (level >= 41) {
				emoteId = 867;
				axeTime = 10;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1357)|| player.getInventory().containsItemToolBelt(1357)) {
			if (level >= 31) {
				emoteId = 869;
				axeTime = 7;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1355)|| player.getInventory().containsItemToolBelt(1355)) {
			if (level >= 21) {
				emoteId = 871;
				axeTime = 5;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1361)|| player.getInventory().containsItemToolBelt(1361)) {
			if (level >= 11) {
				emoteId = 873;
				axeTime = 4;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1353)|| player.getInventory().containsItemToolBelt(1353)) {
			if (level >= 6) {
				emoteId = 875;
				axeTime = 3;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1349)|| player.getInventory().containsItemToolBelt(1349)) {
			emoteId = 877;
			axeTime = 2;
			return true;
		}
		if (player.getInventory().containsOneItem(1351)|| player.getInventory().containsItemToolBelt(1351)) {
			emoteId = 879;
			axeTime = 1;
			return true;
		}
		if (player.getInventory().containsOneItem(13661)|| player.getInventory().containsItemToolBelt(13661)) {
			if (level >= 61) {
				emoteId = 10251;
				axeTime = 13;
				return true;
			}
		}if (player.getInventory().containsOneItem(32645)|| player.getInventory().containsItemToolBelt(32645)) {
			if (level >= 80) {
				emoteId = 25003;
				axeTime = 15;
				return true;
			}
		}
		return false;

	}

	private boolean hasAxe(Player player) {
		if (player.getInventory().containsItemToolBelt(1351) || player.getInventory().containsItemToolBelt(1349)
				 || player.getInventory().containsItemToolBelt(1353) || player.getInventory().containsItemToolBelt(1355)
				 || player.getInventory().containsItemToolBelt(1357) || player.getInventory().containsItemToolBelt(1361)
				 || player.getInventory().containsItemToolBelt(1359) || player.getInventory().containsItemToolBelt(6739)
				 || player.getInventory().containsItemToolBelt(13661)|| player.getInventory().containsItemToolBelt(32645)) {
			return true;
		}
		if (player.getInventory().containsOneItem(1351, 1349, 1353, 1355, 1357,
				1361, 1359, 6739, 13661, 32645))
			return true;
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId == -1)
			return false;
		switch (weaponId) {
		case 1351:// Bronze Axe
		case 1349:// Iron Axe
		case 1353:// Steel Axe
		case 1361:// Black Axe
		case 1355:// Mithril Axe
		case 1357:// Adamant Axe
		case 1359:// Rune Axe
		case 6739:// Dragon Axe
		case 32645:// crystal axe
		case 13661: // Inferno adze
			return true;
		default:
			return false;
		}

	}

	@Override
	public boolean process(Player player) {
		player.setNextAnimation(new Animation(usingBeaver ? 1 : emoteId));
		return checkTree(player);
	}

	private boolean usedDeplateAurora;

	@Override
	public int processWithDelay(Player player) {
		addLog(player);
		if (player.getDailyTask() != null)
			player.getDailyTask().incrementTask(player, 3, definitions.getLogsId(), Skills.FIREMAKING);
		if (!usedDeplateAurora && (1 + Math.random()) < player.getAuraManager().getChanceNotDepleteMN_WC()) {
			usedDeplateAurora = true;
		} else if (Utils.getRandom(definitions.getRandomLifeProbability()) == 0 && definitions != TreeDefinitions.DIVINE_MAGIC && definitions != TreeDefinitions.DIVINE_YEW && definitions != TreeDefinitions.DIVINE_MAPLE && definitions != TreeDefinitions.DIVINE_WILLOW && definitions != TreeDefinitions.DIVINE_OAK && definitions != TreeDefinitions.DIVINE_NORMAL) {
			long time = definitions.respawnDelay * 600;
			if(tree.getX() == 1828 && tree.getY() == 5851 ||tree.getX() == 1829 && tree.getY() == 5848||tree.getX() == 1828 && tree.getY() == 5846)
				return getWoodcuttingDelay(player);
			else 
			World.spawnTemporaryObject(new WorldObject(definitions.getStumpId(), tree.getType(),tree.getRotation(), tree.getX(), tree.getY(), tree.getPlane()), time);
			if (tree.getPlane() < 3 && definitions != TreeDefinitions.IVY) {
				WorldObject object = World.getObject(new WorldTile(tree.getX() - 1, tree.getY() - 1, tree.getPlane() + 1));

				if (object == null) {
					object = World.getObject(new WorldTile(tree.getX(), tree.getY() - 1, tree.getPlane() + 1));
					if (object == null) {
						object = World.getObject(new WorldTile(tree.getX() - 1,tree.getY(), tree.getPlane() + 1));
						if (object == null) {
							object = World.getObject(new WorldTile(tree.getX(),tree.getY(), tree.getPlane() + 1));
						}
					}
				}

				if (object != null)
					World.removeTemporaryObject(object, time, false);
			}
			player.setNextAnimation(new Animation(-1));
			return -1;
		}
		if (!player.getInventory().hasFreeSlots()) {
			player.setNextAnimation(new Animation(-1));
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			return -1;
		}
		return getWoodcuttingDelay(player);
		
	}
	
	private static int infAdze;

	private void addLog(Player player) {
		String logName = ItemDefinitions.getItemDefinitions(definitions.getLogsId()).getName().toLowerCase();
		double xpBoost = 1.00;
		if(definitions == TreeDefinitions.VINES){
			handelVines(player);
			return;
		}
		if (player.getEquipment().getChestId() == 10939)
			xpBoost += 0.008;
		if (player.getEquipment().getLegsId() == 10940)
			xpBoost += 0.006;
		if (player.getEquipment().getHatId() == 10941)
			xpBoost += 0.004;
		if (player.getEquipment().getBootsId() == 10933)
			xpBoost += 0.002;
		if (player.getEquipment().getChestId() == 10939
				&& player.getEquipment().getLegsId() == 10940
				&& player.getEquipment().getHatId() == 10941
				&& player.getEquipment().getBootsId() == 10933)
			xpBoost += 0.005;
		player.getSkills().addXp(8, definitions.getXp() * xpBoost);
		//player.getInventory().addItem(definitions.getLogsId(), 1);
		if (!(definitions == TreeDefinitions.CURLY_VINE || definitions == TreeDefinitions.CURLY_VINE_COLLECTABLE))
			BirdNests.dropNest(player);
		if (player.getEquipment().getWeaponId() == 13661)
				infAdze = Utils.random(3);
			else
				infAdze = 0;
			if (infAdze == 1 && player.getEquipment().getWeaponId() == 13661 && !(definitions == TreeDefinitions.IVY)) {
				player.getSkills().addXp(Skills.FIREMAKING,Firemaking.increasedExperience(player, definitions.getXp()));
				player.getPackets().sendGameMessage("The adze's heat instantly incinerates the " + logName);
				World.sendProjectile(player, player, new WorldTile(player.getX(), player.getY() - 3, 0), 1776, 30, 0,15, 0, 0, 0);
				infAdze = 0;
			} else
			 player.getInventory().addItem(definitions.getLogsId(), 1);	
		if (definitions == TreeDefinitions.IVY) {
			player.getPackets().sendGameMessage("You succesfully cut an ivy vine.", true);
			player.getAchievementManager().notifyUpdate(LumberJackAchievement.class);
			player.choppedIvy++;
			}		else 	
			player.getPackets().sendGameMessage("You get some " + logName + ".", true);
		if(player.getPerkHandler().perks.contains(Perk.CRYSTAL_KEY_PERK))
			player.getPerkHandler().handelKeys();
	}

    private void handelVines(Player player) {
    	//player.sm("dir: "+player.getDirection());
		if(player.getDirection() == 8192){
			player.addWalkSteps(player.getX(), player.getY() + 2, 0, false);
		} else if(player.getDirection() == 12288){
			player.addWalkSteps(player.getX() + 2, player.getY(), 0, false);
		}else if(player.getDirection() == 0){
			player.addWalkSteps(player.getX(), player.getY() -2, 0, false);
		}else if(player.getDirection() == 4096){
			player.addWalkSteps(player.getX() -2, player.getY(), 0, false);
		} else {
			player.sm("???");
		}
		
	}

	private boolean checkTree(Player player) {
	return World.containsObjectWithId(tree, tree.getId());
    }

	@Override
	public void stop(Player player) {
		setActionDelay(player, 3);
	}

	public static HatchetDefinitions getHatchet(Player player, boolean dungeoneering) {
		for (int i = dungeoneering ? 10 : HatchetDefinitions.values().length - 1; i >= (dungeoneering ? 0 : 11); i--) { // from
																// best
																// to
																// worst
		    HatchetDefinitions def = HatchetDefinitions.values()[i];
		    if (player.getInventory().containsItemToolBelt(def.itemId)
			    || player.getEquipment().getWeaponId() == def.itemId) {
			if (player.getSkills().getLevel(Skills.WOODCUTTING) >= def.levelRequried)
			    return def;
		    }
		}
		return null;
	    }

}
