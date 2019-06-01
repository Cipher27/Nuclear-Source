package com.rs.game.player.robot.scripts.combat;

import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.robot.scripts.Default;
import com.rs.game.route.pathfinder.PathFinder;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * @author Taht one guy
 */
public class AncientMage extends Default {

	private final int MELEE = 0, RANGE = 1, MAGE = 2;

	public void dodge() {
		final Entity target = robot.getAttackedBy();
		wait(4);
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
		PathFinder.simpleWalkTo(robot, dodgeTile);
		// robot.addWalkStepsInteract(dodgeTile.getX(), dodgeTile.getY(), 30, 1,
		// true);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				final Entity entity = getAttackedBy();
				if (entity != null) {
					robot.getActionManager()
							.setAction(new PlayerCombat(entity));
				}
			}
		}, 2);
	}

	@Override
	public int[][] getSetId() {
		return new int[][] {
				{ 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41,
						42, 43, 44, 45, 46, 47, 48 }, {} };
	}

	public int getSpecialId() {
		return 1215;
	}

	public int[] getWeapons() {
		return new int[] { 4151, 4587, 4886, 21371 };
	}

	@Override
	public void processCombat() {
		switchPrayers(robot.getAttackedBy());
		if (robot.getCombatDefinitions().getAutoCastSpell() != 23) {
			Magic.setCombatSpell(robot, 23);
		}
		final Entity target = robot.getAttackedBy();
		if (target instanceof Player) {
			final boolean withinDistance = robot.withinDistance(target, 1);
			final boolean frozen = robot.getFreezeDelay() > System
					.currentTimeMillis();
			if (withinDistance && !frozen)
				dodge();
		}
	}

	@Override
	public void processMovement() {
		// if frozen and food is less than 10 teleport
		// if teleblocked/20+wild attempt to run south
		// if 0 food attempt to run
	}

	private void switchPrayers(Entity target) {
		if (target instanceof Player) {
			final Player player = (Player) target;
			final int mageAtt = player.getCombatDefinitions().getBonuses()[3];
			final int rangeAtt = player.getCombatDefinitions().getBonuses()[4];
			int meleeAtt = player.getCombatDefinitions().getBonuses()[0];
			for (int i = 1; i < 3; i++) {
				if (player.getCombatDefinitions().getBonuses()[i] > meleeAtt)
					meleeAtt = player.getCombatDefinitions().getBonuses()[i];
			}
			final int attackType = mageAtt >= rangeAtt && mageAtt >= meleeAtt ? MAGE
					: (rangeAtt >= mageAtt && rangeAtt >= meleeAtt ? RANGE
							: (meleeAtt >= mageAtt && meleeAtt >= rangeAtt ? MELEE
									: MELEE));
			final int prayerActive = (player.getPrayer().usingPrayer(0, 17) || player
					.getPrayer().usingPrayer(1, 7)) ? MAGE : (player
					.getPrayer().usingPrayer(0, 18) || player.getPrayer()
					.usingPrayer(1, 8)) ? RANGE
					: (player.getPrayer().usingPrayer(0, 19) || player
							.getPrayer().usingPrayer(1, 9)) ? MELEE : -1;
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
}
