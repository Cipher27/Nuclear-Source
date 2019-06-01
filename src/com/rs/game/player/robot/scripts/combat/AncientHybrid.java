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
public class AncientHybrid extends Default {
	
	@Override
	public int[][] getSetId() {
		return new int[][] {{135,138,140,142,144,146,148,150,152,158,160,162,164,166},
				{262,289,292}
			};
	}
	
	private int MELEE = 0, RANGE = 1, MAGE = 2;

	@Override
	public void processCombat() {
		Entity target = robot.getAttackedBy();
		if (target instanceof Player) {
			Player player = (Player) target;
			int mageDef = player.getCombatDefinitions().getBonuses()[8] * 2;
			int rangeDef = player.getCombatDefinitions().getBonuses()[9];
			int meleeDef = player.getCombatDefinitions().getBonuses()[6];
			int mageAtt = player.getCombatDefinitions().getBonuses()[3];
			int rangeAtt = player.getCombatDefinitions().getBonuses()[4];
			int meleeAtt = player.getCombatDefinitions().getBonuses()[0];
			for (int i = 1; i < 3; i++) {
				if (player.getCombatDefinitions().getBonuses()[i] > meleeAtt)
					meleeAtt = (int)(player.getCombatDefinitions().getBonuses()[i] * 1.3);
			}
			boolean withinDistance = robot.withinDistance(target, 1);
			boolean frozen = robot.getFreezeDelay() > System.currentTimeMillis();
			boolean targetFrozen = target.getFreezeDelay() > System.currentTimeMillis();
			boolean busy = ((Player) target).getActionManager().getActionDelay() >= 3;
			int attackType = mageAtt >= rangeAtt && mageAtt >= meleeAtt ? MAGE :
							(rangeAtt >= mageAtt && rangeAtt >= meleeAtt ? RANGE :
							(meleeAtt >= mageAtt && meleeAtt >= rangeAtt ? MELEE : MELEE));
			int defenceType = mageDef >= rangeDef && mageDef >= meleeDef ? MAGE :
				(rangeDef >= mageDef && rangeDef >= meleeDef ? RANGE :
				(meleeDef >= mageDef && meleeDef >= rangeDef ? MELEE : MELEE));
			if (defenceType == RANGE)
				defenceType = MELEE;//similar
			int prayerActive = (player.getPrayer().usingPrayer(0, 17) || player.getPrayer().usingPrayer(1, 7)) ? MAGE : 
				(player.getPrayer().usingPrayer(0, 18) || player.getPrayer().usingPrayer(1, 8)) ? RANGE :
					(player.getPrayer().usingPrayer(0, 19) || player.getPrayer().usingPrayer(1, 9)) ? MELEE : -1;
			int specialHealth = 500 + (busy ? 100 : 0);
			switchPrayers(attackType, prayerActive);
			checkCast();
			if (frozen && withinDistance) {
				if (prayerActive != MELEE && defenceType != MELEE && (target.getHitpoints() <= specialHealth) && hasEnoughSpecial())
					wearItems("special");
				else if (!targetFrozen && busy && defenceType != MAGE)
					wearItems("barrage");
				else if (attackType == MAGE)
					wearItems("rangemelee");
				else
					wearItems("melee");
			} else if (!frozen && withinDistance) {
				if (prayerActive != MELEE && defenceType != MELEE && (target.getHitpoints() <= specialHealth) && hasEnoughSpecial()) {
					wearItems("special");
				} else if (targetFrozen) {
					if (attackType == MAGE)
						dodge("rangemelee", true);//dodge
					else if (attackType == RANGE)
						dodge("melee", true);//wearItems("melee");
					else// if (defenceType == MELEE)
						dodge("barrage", false);
				} else {
					if (busy && defenceType != MAGE)//attackType could be melee
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
				//if (!targetFrozen && attackType == MELEE)
				//	wearItems("melee");
				wearItems("barrage");
			} else if (!frozen && !withinDistance) {
				if (prayerActive != MELEE && defenceType != MELEE && (target.getHitpoints() <= specialHealth) && hasEnoughSpecial()) {
					wearItems("special");
				} else if (targetFrozen) {
					if (attackType == MAGE)
						wearItems("rangemelee");
					else if (attackType == RANGE)
						wearItems("melee");
					else// if (defenceType == MELEE && attackType == MELEE)
						wearItems("barrage");
				} else {
					if (busy || defenceType == MELEE)
						wearItems("barrage");
					else if (attackType == RANGE)
						wearItems("melee");
					else if (attackType == MAGE)
						wearItems("rangemelee");
					else
						wearItems("barrage");
				}
			}
		}
	}
	
	private void switchPrayers(int attackType, int prayerActive) {
		//int robotPrayerActive = (robot.getPrayer().usingPrayer(0, 17) || robot.getPrayer().usingPrayer(1, 7)) ? MAGE : 
		//	(robot.getPrayer().usingPrayer(0, 18) || robot.getPrayer().usingPrayer(1, 8)) ? RANGE :
		//		(robot.getPrayer().usingPrayer(0, 19) || robot.getPrayer().usingPrayer(1, 9)) ? MELEE : -1;
		if (prayerActive >= 0) {
			int prayer = robot.getPrayer().isAncientCurses() ? 
					attackType == MAGE ? 7 : attackType == RANGE ? 8 : attackType == MELEE ? 9 : 9 : 
					attackType == MAGE ? 17 : attackType == RANGE ? 18 : attackType == MELEE ? 19 : 19;
			if (!robot.getPrayer().usingPrayer(prayer))
				robot.getPrayer().switchPrayer(prayer);
			return;
		}/* else if (robotPrayerActive >= 0) {
			int prayer = robot.getPrayer().isAncientCurses() ? 
					attackType == MAGE ? 7 : attackType == RANGE ? 8 : attackType == MELEE ? 9 : 9 : 
					attackType == MAGE ? 17 : attackType == RANGE ? 18 : attackType == MELEE ? 19 : 19;
			if (robot.getPrayer().usingPrayer(prayer))
				robot.getPrayer().switchPrayer(prayer);
		}*/
		int smiteSoulSplit = robot.getPrayer().isAncientCurses() ? 18 : 24;
		if (!robot.getPrayer().usingPrayer(smiteSoulSplit))
			robot.getPrayer().switchPrayer(smiteSoulSplit);
	}
	
	private void checkCast() {
		int mageAtt = robot.getCombatDefinitions().getBonuses()[3];
		int rangeAtt = robot.getCombatDefinitions().getBonuses()[4];
		int meleeAtt = robot.getCombatDefinitions().getBonuses()[0];
		int attackType = mageAtt >= rangeAtt && mageAtt >= meleeAtt ? MAGE :
			(rangeAtt >= mageAtt && rangeAtt >= meleeAtt ? RANGE :
			(meleeAtt >= mageAtt && meleeAtt >= rangeAtt ? MELEE : MELEE));
		if (attackType == MAGE && robot.getCombatDefinitions().getAutoCastSpell() <= 0) {
			Magic.setCombatSpell(robot, 23);
		}
	}

	public boolean hasEnoughSpecial() {
		return robot.getCombatDefinitions().getSpecialAttackPercentage() >= PlayerCombat.getSpecialAmmount(robot.cheatSwitch.get(4)[0]);
	}
	
	public void dodge(final String type, final boolean walkOnTarget) {
		final Entity target = robot.getAttackedBy();
		if (walkOnTarget) {
			robot.stopAll(false);
			wait(8);
			//robot.addWalkStepsInteract(target.getX(), target.getY(), 30, 1, true);
			//wearItems(type);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					robot.addWalkStepsInteract(target.getX(), target.getY(), 30, 1, true);
				}
			}, 1);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					int x = Utils.random(1) == 0 ? 1 : -1;
					int y = Utils.random(1) == 0 ? 1 : -1;
					robot.addWalkStepsInteract(target.getX()+x, target.getY()+y, 30, 1, true);
					wearItems(type);
					//robot.getActionManager().setAction(new PlayerCombat(target));
				}
			}, 2);
		} else {
			wait(9);
			robot.stopAll(false);
			WorldTile tile = null;
			while(tile == null || tile.withinDistance(target, 1)) {
				int rand = Utils.getRandom(2);
				int addX = rand == 0 ? (2+Utils.getRandom(1)) : (rand == 2 ? (2+Utils.getRandom(1)) : Utils.getRandom(1));
				int addY = rand == 1 ? (2+Utils.getRandom(1)) : (rand == 2 ? (2+Utils.getRandom(1)) : Utils.getRandom(1));
				if (Utils.getRandom(1) == 0)
					addX *= -1;
				if (Utils.getRandom(1) == 0)
					addY *= -1;
				int x = target.getX() + addX;
				int y = target.getY() + addY;
				if (Wilderness.isAtWild(robot) && !Wilderness.isAtWild(new WorldTile(x,y,0))) {
					y += 3;
				}
				tile = new WorldTile(x, y, target.getPlane());
			}
			final WorldTile dodgeTile = tile;
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					robot.addWalkStepsInteract(dodgeTile.getX(), dodgeTile.getY(), 30, 1, true);
				}
			}, 1);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					robot.addWalkStepsInteract(dodgeTile.getX(), dodgeTile.getY(), 30, 1, true);
					wearItems(type);
				}
			}, 2);
			/*WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					Entity entity = getAttackedBy();
					if (entity != null) {
						robot.getActionManager()
						.setAction(new PlayerCombat(entity));
				}
				}
			}, 3);*/
		}
	}
	
	@Override
	public void processMovement() {
		//if frozen and food is less than 10 teleport
		//if teleblocked/20+wild attempt to run south
		//if 0 food attempt to run
	}
}
