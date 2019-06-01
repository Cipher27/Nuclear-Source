package com.rs.game.player.robot.scripts.combat;

import com.rs.game.Entity;
import com.rs.game.WorldTile;
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
public class RegularTribrid extends Default {

	private final int MELEE = 0, RANGE = 1, MAGE = 2;

	private boolean checkCast(Player target) {
		final int mageAtt = robot.getCombatDefinitions().getBonuses()[3];
		final int rangeAtt = robot.getCombatDefinitions().getBonuses()[4];
		final int meleeAtt = robot.getCombatDefinitions().getBonuses()[0];
		final int attackType = mageAtt >= rangeAtt && mageAtt >= meleeAtt ? MAGE
				: (rangeAtt >= mageAtt && rangeAtt >= meleeAtt ? RANGE
						: (meleeAtt >= mageAtt && meleeAtt >= rangeAtt ? MELEE
								: MELEE));
		if (attackType == MAGE
				&& target.getTeleBlockDelay() < Utils.currentTimeMillis()) {
			wearItems("teleblock");
			robot.getCombatDefinitions().setAutoCastSpell(0);
			Magic.setCombatSpell(robot, 86);
			return true;
		} else if (attackType == MAGE
				&& robot.getCombatDefinitions().getAutoCastSpell() <= 0) {
			Magic.setCombatSpell(robot, 99);
		}
		return false;
	}

	public void dodge(final String type) {
		final Entity target = robot.getAttackedBy();
		wait(9);
		robot.stopAll(false);
		final int rand = Utils.getRandom(2);
		int addX = rand == 0 ? (2 + Utils.getRandom(1))
				: (rand == 2 ? (2 + Utils.getRandom(1)) : Utils.getRandom(1));
		int addY = rand == 1 ? (2 + Utils.getRandom(1))
				: (rand == 2 ? (2 + Utils.getRandom(1)) : Utils.getRandom(1));
		if (Utils.getRandom(1) == 0)
			addX *= -1;
		if (Utils.getRandom(1) == 0)
			addY *= -1;
		final int x = target.getX() + addX;
		int y = target.getY() + addY;
		if (!Wilderness.isAtWild(new WorldTile(x, y, 0))) {
			y += 3;
		}
		final WorldTile dodgeTile = new WorldTile(x, y, 0);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				robot.addWalkStepsInteract(dodgeTile.getX(), dodgeTile.getY(),
						30, 1, true);
			}
		}, 1);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				robot.addWalkStepsInteract(dodgeTile.getX(), dodgeTile.getY(),
						30, 1, true);
			}
		}, 2);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				final Entity entity = getAttackedBy();
				if (entity != null) {
					robot.getActionManager()
							.setAction(new PlayerCombat(entity));
					wearItems(type);
				}
			}
		}, 3);
	}

	@Override
	public int[][] getSetId() {//
		return new int[][] { { 60, 94, 299, 301, 303 }, { 305, 307, 309 } };
	}

	public boolean hasEnoughSpecial() {
		return robot.getCombatDefinitions().getSpecialAttackPercentage() >= PlayerCombat
				.getSpecialAmmount(robot.cheatSwitch.get(2)[0]);
	}

	@Override
	public void processCombat() {//
		final Entity target = robot.getAttackedBy();
		if (target instanceof Player) {
			final Player player = (Player) target;
			final int mageDef = player.getCombatDefinitions().getBonuses()[8];
			final int rangeDef = player.getCombatDefinitions().getBonuses()[9];
			final int meleeDef = player.getCombatDefinitions().getBonuses()[6];
			final int mageAtt = player.getCombatDefinitions().getBonuses()[3];
			final int rangeAtt = player.getCombatDefinitions().getBonuses()[4];
			int meleeAtt = player.getCombatDefinitions().getBonuses()[0];
			for (int i = 1; i < 3; i++) {
				if (player.getCombatDefinitions().getBonuses()[i] > meleeAtt)
					meleeAtt = player.getCombatDefinitions().getBonuses()[i];
			}
			final boolean withinDistance = robot.withinDistance(target, 1);
			final boolean frozen = robot.getFreezeDelay() > System
					.currentTimeMillis();
			final boolean targetFrozen = target.getFreezeDelay() > System
					.currentTimeMillis();
			final boolean busy = ((Player) target).getActionManager()
					.getActionDelay() >= 3;
			final int attackType = mageAtt >= rangeAtt && mageAtt >= meleeAtt ? MAGE
					: (rangeAtt >= mageAtt && rangeAtt >= meleeAtt ? RANGE
							: (meleeAtt >= mageAtt && meleeAtt >= rangeAtt ? MELEE
									: MELEE));
			final int defenceType = mageDef >= rangeDef && mageDef >= meleeDef ? MAGE
					: (rangeDef >= mageDef && rangeDef >= meleeDef ? RANGE
							: (meleeDef >= mageDef && meleeDef >= rangeDef ? MELEE
									: MELEE));
			final int prayerActive = (player.getPrayer().usingPrayer(0, 17) || player
					.getPrayer().usingPrayer(1, 7)) ? MAGE : (player
					.getPrayer().usingPrayer(0, 18) || player.getPrayer()
					.usingPrayer(1, 8)) ? RANGE
					: (player.getPrayer().usingPrayer(0, 19) || player
							.getPrayer().usingPrayer(1, 9)) ? MAGE : -1;
			final int specialHealth = 400 + (busy ? 200 : 0);
			switchPrayers(attackType, prayerActive);
			if (checkCast((Player) target))
				return;
			if (frozen && withinDistance) {
				if (defenceType != MELEE
						&& target.getHitpoints() <= specialHealth
						&& hasEnoughSpecial())
					wearItems("special");
				else if (!targetFrozen && (attackType != MELEE))
					wearItems("snare");
				else if (attackType == MAGE)
					wearItems("rangemelee");
				else
					wearItems("melee");
			} else if (!frozen && withinDistance) {
				if (defenceType != MELEE
						&& (target.getHitpoints() <= specialHealth)
						&& hasEnoughSpecial()) {
					wearItems("special");
				} else if (targetFrozen) {
					if (attackType == MAGE)
						dodge("rangemelee");
					else if (attackType == RANGE)
						dodge("melee");
					else if (defenceType == MELEE && attackType == MELEE)
						dodge("armadylstorm");
					else
						wearItems("range");
				} else {
					if (busy || defenceType == MELEE)
						dodge("snare");
					else if (attackType == RANGE)
						wearItems("melee");
					else if (attackType == MAGE)
						wearItems("rangemelee");
					else
						dodge("snare");
				}
			} else if (frozen && !withinDistance) {
				if (attackType == MAGE)
					wearItems("range");
				else if (!targetFrozen)
					wearItems("snare");
				else
					wearItems("armadylstorm");
			} else if (!frozen && !withinDistance) {
				if (defenceType != MELEE
						&& (target.getHitpoints() <= specialHealth)
						&& hasEnoughSpecial()) {
					wearItems("special");
				} else if (targetFrozen) {
					if (attackType == MAGE)
						dodge("rangemelee");
					else if (attackType == RANGE)
						dodge("melee");
					else if (defenceType == MELEE && attackType == MELEE)
						wearItems("armadylstorm");
					else
						wearItems("range");
				} else {
					if (busy || defenceType == MELEE)
						wearItems("snare");
					else if (attackType == RANGE)
						wearItems("melee");
					else if (attackType == MAGE)
						wearItems("rangemelee");
					else
						wearItems("snare");
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
