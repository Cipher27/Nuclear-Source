package com.rs.game.player.actions.mining;

import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public abstract class MiningBase extends Action {


public enum PickAxeDefinitions {

	NOVITE(16295, 1, 13074, 1),

	BATHUS(16297, 10, 13075, 3),

	MARMAROS(16299, 20, 13076, 5),

	KRATONITE(16301, 30, 13077, 7),

	FRACTITE(16303, 40, 13078, 10),

	ZEPHYRIUM(16305, 50, 13079, 12),

	ARGONITE(16307, 60, 13080, 13),

	KATAGON(16309, 70, 13081, 15),

	GORGONITE(16311, 80, 13082, 16),

	PROMETHIUM(16313, 90, 13083, 17),

	PRIMAL(16315, 99, 13084, 20),

	BRONZE(1265, 1, 625, 1),

	IRON(1267, 1, 626, 2),

	STEEL(1269, 6, 627, 3),

	MITHRIL(1273, 21, 629, 5),

	ADAMANT(1271, 31, 628, 7),

	RUNE(1275, 41, 624, 10),

	DRAGON(15259, 61, 12189, 13),

	ADZ(13661, 61, 10222, 13),

	CRYSTAL(32646, 71, 25159, 15),

	IMCANDO(29522, 81, 10222, 13); // TODO: need to get the right anim id

	//10222
		int pickAxeId;
		int levelRequried;
		private int animationId;
		private int pickAxeTime;

		private PickAxeDefinitions(int pickAxeId, int levelRequried, int animationId, int pickAxeTime) {
			this.pickAxeId = pickAxeId;
			this.levelRequried = levelRequried;
			this.animationId = animationId;
			this.pickAxeTime = pickAxeTime;
		}

		public int getPickAxeId() {
			return pickAxeId;
		}

		public int getLevelRequried() {
			return levelRequried;
		}

		public int getAnimationId() {
			return animationId;
		}

		public int getPickAxeTime() {
			return pickAxeTime;
		}
	}
	protected int emoteId;
	protected int pickaxeTime;
	
	public static void propect(final Player player, final String endMessage) {
		propect(player, "You examine the rock for ores....", endMessage);
	}
	
	public static void propect(final Player player, String startMessage, final String endMessage) {
		player.getPackets().sendGameMessage(startMessage, true);
		player.lock(5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getPackets().sendGameMessage(endMessage);
			}
		}, 4);
	}
	
	protected boolean setPickaxe(Player player) {
		int level = player.getSkills().getLevel(Skills.MINING);
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId != -1) {
			switch (weaponId) {
			case 32646: // crystal pick
			if (level >= 61) {
				emoteId = 25159;
				pickaxeTime = 15;
				return true;
			}
		      break;
			case 13661: // Inferno adze
				if (level >= 61) {
					emoteId = 10222;
					pickaxeTime = 13;
					return true;
					
				}
				break;
			case 20786: // dragon (g) pickaxe
				if (level >= 61) {
					emoteId = 250;
					pickaxeTime = 13;
					return true;
				}
				break;
			case 15259: // dragon pickaxe
				if (level >= 61) {
					emoteId = 12190;
					pickaxeTime = 13;
					return true;
				}
				break;
			case 1275: // rune pickaxe
				if (level >= 41) {
					emoteId = 624;
					pickaxeTime = 10;
					return true;
				}
				break;
			case 1271: // adam pickaxe
				if (level >= 31) {
					emoteId = 628;
					pickaxeTime = 7;
					return true;
				}
				break;
			case 1273: // mith pickaxe
				if (level >= 21) {
					emoteId = 629;
					pickaxeTime = 5;
					return true;
				}
				break;
			case 1269: // steel pickaxe
				if (level >= 6) {
					emoteId = 627;
					pickaxeTime = 3;
					return true;
				}
				break;
			case 1267: // iron pickaxe
				emoteId = 626;
				pickaxeTime = 2;
				return true;
				
			case 1265: // bronze axe
				emoteId = 625;
				pickaxeTime = 1;
				return true;
			
			}
		}
		if (player.getInventory().containsOneItem(20786)) {
				if (level >= 61) {
					emoteId = 250;
					pickaxeTime = 13;
					return true;
				}
			}
		if (player.getInventory().containsOneItem(15259)) {
			if (level >= 61) {
				emoteId = 12190;
				pickaxeTime = 13;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1275)) {
			if (level >= 41) {
				emoteId = 624;
				pickaxeTime = 10;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1271)) {
			if (level >= 31) {
				emoteId = 628;
				pickaxeTime = 7;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1273)) {
			if (level >= 21) {
				emoteId = 629;
				pickaxeTime = 5;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1269)) {
			if (level >= 6) {
				emoteId = 627;
				pickaxeTime = 3;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1267)) {
			emoteId = 626;
			pickaxeTime = 2;
			return true;
		}
		if (player.getInventory().containsOneItem(1265)) {
			emoteId = 625;
			pickaxeTime = 1;
			return true;
		}
		if (player.getInventory().containsOneItem(32646)) {
			emoteId = 25159;
			pickaxeTime = 15;
			return true;
		}
		if (player.getInventory().containsOneItem(13661)) {
			if (level >= 61) {
				emoteId = 10222;
				pickaxeTime = 13;
				return true;
			}

		}
		return false;

	}

	protected boolean hasPickaxe(Player player) {
		if (player.getInventory().containsItemToolBelt(15259) || player.getInventory().containsItemToolBelt(1275)
				 || player.getInventory().containsItemToolBelt(1271) || player.getInventory().containsItemToolBelt(1273)
				 || player.getInventory().containsItemToolBelt(1269) || player.getInventory().containsItemToolBelt(1267)
				 || player.getInventory().containsItemToolBelt(1265) || player.getInventory().containsItemToolBelt(13661)
				 || player.getInventory().containsItemToolBelt(32646)|| player.getInventory().containsItemToolBelt(20786)) {
			return true;
		}
		if (player.getInventory().containsOneItem(15259, 1275, 1271, 1273,
				1269, 1267, 1265, 13661, 32646, 20786))
			return true;
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId == -1)
			return false;
		switch (weaponId) {
		case 1265:// Bronze PickAxe
		case 1267:// Iron PickAxe
		case 1269:// Steel PickAxe
		case 1273:// Mithril PickAxe
		case 1271:// Adamant PickAxe
		case 1275:// Rune PickAxe
		case 15259:// Dragon PickAxe
		case 20786://dragon (g) pick
		case 13661: // Inferno adze
		case 32646: // crystal pickaxe
			return true;
		default:
			return false;
		}
	} 

	@Override
	public void stop(Player player) {
		setActionDelay(player, 3);
	}

	public static PickAxeDefinitions getPickAxeDefinitions(Player player, boolean dungeoneering) {
		for (int i = dungeoneering ? 10 : PickAxeDefinitions.values().length - 1; i >= (dungeoneering ? 0 : 11); i--) {
			PickAxeDefinitions def = PickAxeDefinitions.values()[i];
			if (player.getInventory().containsItemToolBelt(def.pickAxeId) || player.getEquipment().getWeaponId() == def.pickAxeId) {
				if (player.getSkills().getLevel(Skills.MINING) >= def.levelRequried)
					return def;
			}
		}
		return null;
	}
	
}
