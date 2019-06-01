package com.rs.game.player.robot.scripts.combat;

import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.robot.scripts.Default;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;
/**
 * @author Taht one guy
 */
public class AncientTribrid extends Default {

	private final int MELEE = 0, RANGE = 1, MAGE = 2;

	private void checkCast() {
		final int mageAtt = robot.getCombatDefinitions().getBonuses()[3];
		final int rangeAtt = robot.getCombatDefinitions().getBonuses()[4];
		final int meleeAtt = robot.getCombatDefinitions().getBonuses()[0];
		final int attackType = mageAtt >= rangeAtt && mageAtt >= meleeAtt ? MAGE
				: (rangeAtt >= mageAtt && rangeAtt >= meleeAtt ? RANGE
						: (meleeAtt >= mageAtt && meleeAtt >= rangeAtt ? MELEE
								: MELEE));
		if (attackType == MAGE
				&& robot.getCombatDefinitions().getAutoCastSpell() <= 0) {
			Magic.setCombatSpell(robot, 23);
		}
	}

	public void dodge(final String type, final boolean walkOnTarget) {
		final Entity target = robot.getAttackedBy();
		if (walkOnTarget) {
			robot.stopAll(false);
			wait(8);
			// robot.addWalkStepsInteract(target.getX(), target.getY(), 30, 1,
			// true);
			// wearItems(type);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					robot.addWalkStepsInteract(target.getX(), target.getY(),
							30, 1, true);
				}
			}, 1);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					final int x = Utils.random(1) == 0 ? 1 : -1;
					final int y = Utils.random(1) == 0 ? 1 : -1;
					robot.addWalkStepsInteract(target.getX() + x, target.getY()
							+ y, 30, 1, true);
					wearItems(type);
					robot.getActionManager()
							.setAction(new PlayerCombat(target));
				}
			}, 2);
		} else {
			wait(9);
			robot.stopAll(false);
			WorldTile tile = null;
			while (tile == null || tile.withinDistance(target, 1)) {
				final int rand = Utils.getRandom(2);
				int addX = rand == 0 ? (2 + Utils.getRandom(1))
						: (rand == 2 ? (2 + Utils.getRandom(1)) : Utils
								.getRandom(1));
				int addY = rand == 1 ? (2 + Utils.getRandom(1))
						: (rand == 2 ? (2 + Utils.getRandom(1)) : Utils
								.getRandom(1));
				if (Utils.getRandom(1) == 0)
					addX *= -1;
				if (Utils.getRandom(1) == 0)
					addY *= -1;
				final int x = target.getX() + addX;
				int y = target.getY() + addY;
				if (Wilderness.isAtWild(robot)
						&& !Wilderness.isAtWild(new WorldTile(x, y, 0))) {
					y += 3;
				}
				tile = new WorldTile(x, y, target.getPlane());
			}
			final WorldTile dodgeTile = tile;
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					robot.addWalkStepsInteract(dodgeTile.getX(),
							dodgeTile.getY(), 30, 1, true);
				}
			}, 1);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					robot.addWalkStepsInteract(dodgeTile.getX(),
							dodgeTile.getY(), 30, 1, true);
					wearItems(type);
				}
			}, 2);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					final Entity entity = getAttackedBy();
					if (entity != null) {
						robot.getActionManager().setAction(
								new PlayerCombat(entity));
					}
				}
			}, 3);
		}
	}

	/*
	 * public int getHighest(int mage, int range, int melee) { if (mage >= range
	 * && mage >= melee) return MAGE; if (mage >= range && mage >= melee) return
	 * MAGE; if (mage >= range && mage >= melee) return MAGE; }
	 */

	@Override
	public int[][] getSetId() {
		return new int[][] {
				{ 55, 96, 98, 100, 102,/* 173, */201, 293, 295 },
				{ 232, 234, 236, 238, 240, 242, 244, 246, 248, 250, 252, 254,
						256, 258, 260, 275, 277, 279, 281, 283, 285, 287 } // zerker
		};
	}

	public boolean hasEnoughSpecial() {
		return robot.getCombatDefinitions().getSpecialAttackPercentage() >= PlayerCombat
				.getSpecialAmmount(robot.cheatSwitch.get(4)[0]);
	}

	@Override
	public void processCombat() {
		final Entity target = robot.getAttackedBy();
		if (target instanceof Player) {
			final Player player = (Player) target;
			final int mageDef = player.getCombatDefinitions().getBonuses()[CombatDefinitions.MAGIC_DEF] * 2;
			final int rangeDef = player.getCombatDefinitions().getBonuses()[CombatDefinitions.RANGE_DEF];
			int meleeDef = player.getCombatDefinitions().getBonuses()[5];// 5,6,7
			for (int i = 6; i < 8; i++) {
				if (player.getCombatDefinitions().getBonuses()[i] > meleeDef)
					meleeDef = player.getCombatDefinitions().getBonuses()[i];
			}
			final int mageAtt = player.getCombatDefinitions().getBonuses()[CombatDefinitions.MAGIC_ATTACK];
			final int rangeAtt = player.getCombatDefinitions().getBonuses()[CombatDefinitions.RANGE_ATTACK];
			int meleeAtt = (int) (player.getCombatDefinitions().getBonuses()[0] * 1.3);
			for (int i = 1; i < 3; i++) {
				if ((int) (player.getCombatDefinitions().getBonuses()[i] * 1.3) > meleeAtt)
					meleeAtt = (int) (player.getCombatDefinitions()
							.getBonuses()[i] * 1.3);
			}
			final boolean withinDistance = robot.withinDistance(target, 1);
			final boolean frozen = robot.getFreezeDelay() > System
					.currentTimeMillis();
			final boolean targetFrozen = target.getFreezeDelay() > System
					.currentTimeMillis();
			final boolean busy = player.getActionManager().getActionDelay() >= 3;
			final int attackType = (mageAtt >= rangeAtt && mageAtt >= meleeAtt) ? MAGE
					: ((rangeAtt >= mageAtt && rangeAtt >= meleeAtt) ? RANGE
							: ((meleeAtt >= mageAtt && meleeAtt >= rangeAtt) ? MELEE
									: MELEE));
			int defenceType = (mageDef >= rangeDef && mageDef >= meleeDef) ? MAGE
					: ((rangeDef >= mageDef && rangeDef >= meleeDef) ? RANGE
							: ((meleeDef >= mageDef && meleeDef >= rangeDef) ? MELEE
									: MELEE));
			if (defenceType == RANGE)
				defenceType = MELEE;// similar
			final int prayerActive = (player.getPrayer().usingPrayer(0, 17) || player
					.getPrayer().usingPrayer(1, 7)) ? MAGE : (player
					.getPrayer().usingPrayer(0, 18) || player.getPrayer()
					.usingPrayer(1, 8)) ? RANGE
					: (player.getPrayer().usingPrayer(0, 19) || player
							.getPrayer().usingPrayer(1, 9)) ? MELEE : -1;
			final int specialHealth = 500 + (busy ? 100 : 0);
			switchPrayers(attackType, prayerActive);
			checkCast();
			/*
			 * System.out.println("2frozen: "+frozen);
			 * System.out.println("2tafrozen: "+targetFrozen);
			 * System.out.println("2busy: "+busy);
			 * System.out.println("2attackType: "+((attackType == MAGE) ? "mage"
			 * : ((attackType == RANGE) ? "range" : "melee")));
			 * System.out.println("2defenceType: "+((defenceType == MAGE) ?
			 * "mage" : ((defenceType == RANGE) ? "range" : "melee")));
			 */
			if (frozen && withinDistance) {
				if (prayerActive != MELEE && defenceType != MELEE
						&& (target.getHitpoints() <= specialHealth)
						&& hasEnoughSpecial())
					wearItems("special");
				else if (!targetFrozen && busy && defenceType != MAGE)
					wearItems("barrage");
				else if (attackType == MAGE)
					if (defenceType == MELEE)
						wearItems("range");
					else
						wearItems("rangemelee");
				else
					wearItems("melee");
			} else if (!frozen && withinDistance) {
				if (prayerActive != MELEE && defenceType != MELEE
						&& (target.getHitpoints() <= specialHealth)
						&& hasEnoughSpecial()) {
					wearItems("special");
				} else if (targetFrozen) {
					if (attackType == MAGE)
						dodge("rangemelee", true);// dodge
					else if (attackType == RANGE)
						dodge("melee", true);// wearItems("melee");
					else if (defenceType == MELEE)
						dodge("barrage", false);
					else
						dodge("range", false);
				} else {
					if (busy && defenceType != MAGE)// attackType could be melee
						dodge("barrage", false);
					else if (attackType == RANGE)
						wearItems("melee");
					else if (attackType == MAGE)
						wearItems("rangemelee");
					else if (defenceType == MAGE)
						wearItems("melee");
					else
						dodge("barrage", false);
				}
			} else if (frozen && !withinDistance) {
				// if (!targetFrozen && attackType == MELEE)
				// wearItems("melee");
				if (targetFrozen) {
					if (defenceType == MELEE)
						wearItems("barrage");
					else
						wearItems("range");
				} else {
					wearItems("barrage");
				}
			} else if (!frozen && !withinDistance) {
				if (prayerActive != MELEE && defenceType != MELEE
						&& (target.getHitpoints() <= specialHealth)
						&& hasEnoughSpecial()) {
					wearItems("special");
				} else if (targetFrozen) {
					if (attackType == MAGE)
						dodge("rangemelee", false);
					else if (attackType == RANGE)
						wearItems("melee");
					else if (defenceType == MELEE)
						wearItems("barrage");
					else
						wearItems("range");
				} else {
					if (busy || defenceType == MELEE)
						wearItems("barrage");
					else if (attackType == RANGE)
						wearItems("melee");
					else if (attackType == MAGE)
						wearItems("rangemelee");
					else
						wearItems("melee");
				}
			}
		}
	}

	@Override
	public void processMovement() {
		// if frozen and food is less than 10 teleport
		// if teleblocked/20+wild attempt to run south
		// if 0 food attempt to run
	}

	private void switchPrayers(int attackType, int prayerActive) {
		if (prayerActive >= 0) {
			final int prayer = robot.getPrayer().isAncientCurses() ? attackType == MAGE ? 7
					: attackType == RANGE ? 8 : attackType == MELEE ? 9 : 9
					: attackType == MAGE ? 17 : attackType == RANGE ? 18
							: attackType == MELEE ? 19 : 19;
			if (!robot.getPrayer().usingPrayer(prayer))
				robot.getPrayer().switchPrayer(prayer);
			return;
		}
		final int smiteSoulSplit = robot.getPrayer().isAncientCurses() ? 18
				: 24;
		if (!robot.getPrayer().usingPrayer(smiteSoulSplit))
			robot.getPrayer().switchPrayer(smiteSoulSplit);
	}
}
