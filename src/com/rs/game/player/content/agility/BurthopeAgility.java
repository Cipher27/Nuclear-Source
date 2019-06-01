package com.rs.game.player.content.agility;

import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.impl.ParcourMasterAchievement;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class BurthopeAgility {

	public static void walkLog(final Player player) {
		if (player.getX() != 2919 || player.getY() != (player.getY() >= 3558 ? 3558 : 3552))
			return;
		final boolean running = player.getRun();
		player.setRunHidden(false);
		player.lock(8);
		player.addWalkSteps(2919, player.getY() < 3558 ? 3558 : 3552, -1, false);
		player.sendMessage("You walk across the log beam...");
		WorldTasksManager.schedule(new WorldTask() {
			boolean secondloop;
			@Override
			public void run() {
				if (!secondloop) {
					secondloop = true;
					player.getAppearence().setRenderEmote(155);
				} else {
					player.getAppearence().setRenderEmote(-1);
					player.setRunHidden(running);
					setBurthorpeStage(player, 0);
					player.getSkills().addXp(Skills.AGILITY, 5);
					player.sendMessage("... and make it safely to the other side.");
					stop();
				}
			}
		}, 0, 5);
	}

	public static void climbWall(final Player player) {
		if (player.getY() != 3561)
			player.addWalkSteps(player.getX(), 3561);
		player.sendMessage("You climb the wall.");
		player.useStairs(828, new WorldTile(player.getX(), 3562, 1), 1, 2);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (getBurthorpeStage(player) == 0)
					setBurthorpeStage(player, 1);
				player.getSkills().addXp(Skills.AGILITY, 2);
			}
		}, 1);
	}

	public static void walkAcrossBalancingLedge(final Player player, final WorldObject object) {
		if (player.getY() != 3564 || player.getX() < 2916) {
			player.sendMessage("You cannot walk across from here!");
			return;
		}
		player.sendMessage("You put your foot on the ledge and try to edge across...");
		player.lock(5);
		player.setNextAnimation(new Animation(752));
		player.getAppearence().setRenderEmote(156);
		final WorldTile toTile = new WorldTile((player.getX() >= 2916 ? 2912 : 2916), object.getY(), object.getPlane());
		player.setRun(true);
		player.addWalkSteps(toTile.getX(), toTile.getY(), -1, false);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.setNextAnimation(new Animation(758));
				player.getAppearence().setRenderEmote(-1);
				player.getSkills().addXp(Skills.AGILITY, 5);
				player.sendMessage("You skillfully edge across the gap.");
				if (getBurthorpeStage(player) == 1)
					setBurthorpeStage(player, 2);
			}
		}, 4);
	}
	
	public static void climbOverObstacleWall(final Player player, WorldObject object) {
		if (player.getX() >= object.getX()) {
			player.getPackets().sendGameMessage("You cannot climb that from this side.");
			return;
		}
		if (player.getY() == 3562) {
			player.getPackets().sendGameMessage("You climb the low wall...");
			player.lock(3);
			player.setNextAnimation(new Animation(4853));
			final WorldTile toTile = new WorldTile(object.getX() + 1, 3562, object.getPlane());
			player.setNextForceMovement(new ForceMovement(player, 0, toTile, 2, ForceMovement.EAST));
			WorldTasksManager.schedule(new WorldTask() {
	
				@Override
				public void run() {
					player.setNextWorldTile(toTile);
					player.getSkills().addXp(Skills.AGILITY, 3);
					if (getBurthorpeStage(player) == 2)
						setBurthorpeStage(player, 3);
				}
	
			}, 1);
		} else
			player.addWalkSteps(2910, 3562);
	}
	
	public static void swingOnRopeSwing(final Player player, WorldObject object) {
		if (player.getX() > object.getX()) {
			player.getPackets().sendGameMessage("You cannot swing on that from this side.");
			return;
		}
		player.lock(4);
		player.setNextAnimation(new Animation(751));
		World.sendObjectAnimation(player, object, new Animation(497));
		final WorldTile toTile = new WorldTile(2916, 3562, object.getPlane());
		player.setNextForceMovement(new ForceMovement(player, 1, toTile, 2, ForceMovement.EAST));
		player.sendMessage("You skillfully swing on the rope.");
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.setNextWorldTile(toTile);
				player.getSkills().addXp(Skills.AGILITY, 10);
				if (getBurthorpeStage(player) == 3)
					setBurthorpeStage(player, 4);
			}

		}, 1);
	}
	
	public static void swingAcrossMonkeyBars(final Player player, WorldObject object) {
		if (player.getY() <= 3554) {
			player.sendMessage("You cannot swing across those from this side.");
			return;
		}
		if (player.getX() == 2917) {
			player.lock(7);
			final boolean running = player.getRun();
			player.setRunHidden(false);
			player.addWalkSteps(2917, 3554, -1, false);
			player.sendMessage("You swing across the monkey bars...");
			WorldTasksManager.schedule(new WorldTask() {
				boolean secondloop;
				@Override
				public void run() {
					if (!secondloop) {
						secondloop = true;
						player.getAppearence().setRenderEmote(662);
					} else {
						player.getAppearence().setRenderEmote(-1);
						player.setRunHidden(running);
						player.getSkills().addXp(Skills.AGILITY, 20);
						if (getBurthorpeStage(player) == 4)
							setBurthorpeStage(player, 5);
						player.sendMessage("... and make it safely to the other side.");
						stop();
					}
				}
			}, 0, 6);
		} else
			player.addWalkSteps(2917, 3561);
	}
	
	public static void jumpDownLedge(final Player player, WorldObject object) {
		if (player.getY() <= 3552) {
			player.sendMessage("You cannot jump down that ledge from this side.");
			return;
		}
		if (player.getY() >= 3553) {
			player.sendMessage("You jump down...");
			player.setNextAnimation(new Animation(7269));
			final WorldTile toTile = new WorldTile(player.getX(), 3552, 0);
			player.setNextForceMovement(new ForceMovement(player, 0, new WorldTile(toTile.getX(), toTile.getY() - 1, 1), 1, ForceMovement.SOUTH));
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					player.setNextWorldTile(toTile);
					player.sendMessage("...to the ground below.");
					if (getBurthorpeStage(player) == 5) {
						player.NormalBurthope++;
						player.getAchievementManager().notifyUpdate(ParcourMasterAchievement.class);
						player.laps++;
						player.sendMessage("You have completed a lap at the Burthorpe Agility Course!");
					}
					player.getSkills().addXp(Skills.AGILITY, 20);
					setBurthorpeStage(player, 0);
				}
			}, 0);
		}
	}

	public static void removeBurthorpeStage(Player player) {
		player.getTemporaryAttributtes().remove("BurthorpeCourse");
	}

	public static void setBurthorpeStage(Player player, int stage) {
		player.getTemporaryAttributtes().put("BurthorpeCourse", stage);
	}

	public static int getBurthorpeStage(Player player) {
		Integer stage = (Integer) player.getTemporaryAttributtes().get("BurthorpeCourse");
		if (stage == null)
			return -1;
		return stage;
	}
}