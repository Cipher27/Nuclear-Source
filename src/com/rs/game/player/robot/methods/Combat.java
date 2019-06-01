package com.rs.game.player.robot.methods;

import java.util.ArrayList;
import java.util.List;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceMovement;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.magic.Lunars;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.robot.Robot;
import com.rs.game.route.pathfinder.PathFinder;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.utils.Misc;
import com.rs.utils.Utils;



/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public class Combat extends Method {
	
	public Entity target;
	
	public WorldTile deathTile;
	
	public int area, combatLevel;
	
	public static final WorldTile EDGEVILLE = new WorldTile(3087, 3502, 0);
	
	public String[] startFight = { "gl", "no food", "lol", "gf", "gf lol", "gl lol", "rip", "get rekt", "get shrekt", "get reekktt", "smd fgt", "lol smd", "lol gl",
			"those loots", "prepare to die", "get good", "*fight starts*", "ur done", "lol ur done", "gf m8", "rip m8", "get rekt m8", "hahaha", "haha", "lets go" };
	
	public String[] fightPhrases = { "looking to 1v1", "skulling", "im recording", "anyone want to fight?", "high risk!", "can someone help me with this clue?", "cb levels?",
			"looking to form a team", "lol nubs", "fucking revanents", "trying to get to kbd", "current chaos elemental WorldTiles?", "anyone doing warbands?", "looking for high loots",
			"streaming on twitch rn", "wtf is everyone?", "i love this server", "wanna boss later?", "i think im gonna teleport home", "no safing", "omg i hate pjers",
			"this is some bullshit", "stop it!", "leave me alone...", "fuck off!", "hey wanna fight?", "let's fight", "1v1?", "no pots/food", "lame", "wimp", "anyone get a boss pet",
			"looking to risk t80+", "stop teleblocking!", "someone help me!!", "lol noob", "i hate this", "farcasting lol", "anyone looking for the div spot?", "fuck this clue",
			"just have to finish this comp cape", "sick kill streak", "i just got this crazy loot", "fuck off", "wooooooot", "lol gf", "danny is awesome", "i love elveron",
			};
	
	public Combat(Robot robot) {
		super(robot);
		stage = Stage.Finished;
	}
	
	@Override
	public void process() {
		combat();
	}
	
	public void combat() {
		if (getScript().banking.stage != Stage.Finished)
			return;
		if (getScript().pickup.stage != Stage.Finished)
			return;
		if (getScript().combat.stage == Stage.Finished)
			return;
		boolean inCombatZone = robot.isCanPvp();
		boolean teleblocked = robot.getTeleBlockDelay() > Utils.currentTimeMillis();
		boolean frozen = robot.getFreezeDelay() >= Utils.currentTimeMillis();
		boolean inCombat = robot.getAttackedByDelay() > Utils.currentTimeMillis();
		boolean withinDistance = robot.withinDistance(target, 1);
		boolean hasTarget = target != null;
		boolean needsToRun = teleblocked
				|| Wilderness.getWildLevel(robot) > 16 
				|| World.isMultiArea(robot)
				|| stage == Stage.Running
				|| (!inCombat && robot.getInventory().getFreeSlots() > 4)
				|| getScript().foodsAndPotions.stage == Stage.Finished
				|| (frozen && !withinDistance && Utils.random(5) == 0/* && getScript().banking.styles.*/);
		boolean standingOnPlayer = isOnPlayer();
		if (inCombatZone) {
			if (needsToRun) {
				//robot.ft("needsToRun");
				// getScript().banking.stage = Stage.Looking;
				setStage(Stage.Running);
				if (!teleblocked && Wilderness.getWildLevel(robot) <= 30) {
					// teletab
					int teletab = 8007 + Utils.random(4);
					robot.stopAll();
						Magic.sendNormalTeleportSpell(robot, 0, 0, EDGEVILLE);
					pause(5 + Utils.random(10));
				} else {
					if (frozen)
						return;
					// run to edge if not frozen
					robot.getCombatDefinitions().setAutoRelatie(false);
					if (!robot.hasWalkSteps())
						PathFinder.simpleWalkTo(robot, robot.transform(0, -20, 0));
				}
				//robot.ft("Running away!");
				//pause(5 + Utils.random(10));
				return;
			}
			if (standingOnPlayer) {
				PathFinder.simpleWalkTo(robot, robot.transform(Utils.getRandom(3), Utils.getRandom(3), 0));
				pause(2);
				return;
			}
			if (hasTarget) {
				if (target != robot.getAttackedBy()) {
					if (canAttack(robot.getAttackedBy())) {
						target = robot.getAttackedBy();
					}
				}
				if (target != null) {
					if (target.isDead() && target instanceof Player) {
						pause(6 + Utils.random(3));
						deathTile = target.getWorldTile();
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								getScript().pickup.stage = Stage.Looking;
							}
						}, 9);
						return;
					}
					if (!robot.withinDistance(target, 30) || !canAttack(target)) {
						setStage(Stage.Looking);
						return;
					}
					if (robot.getActionManager().getAction() instanceof PlayerCombat) {
						setStage(Stage.Fighting);
						//robot.ft("PlayerCombat" + target.toString());
					} else if (robot.getActionManager().getAction() == null) {
						startCombat(target);
						//robot.ft("No PlayerCombat" + target.toString());
					}
					//robot.ft("Attacking");
					processCombat(target);
					return;
				}
			}
			if (inCombat) {
				//robot.ft("In combat");
				// decide to attack or pray or w/e
				Entity attackedBy = robot.getAttackedBy();
				if (attackedBy == null)
					return;
				@SuppressWarnings("unused")
				boolean multi = World.isMultiArea(robot) && World.isMultiArea(attackedBy);
				startCombat(attackedBy);
			} else {
				// look for target or run around
				setStage(Stage.Looking);
				switch (Utils.random(8)) {
					case 0:
						Player player = findAttackablePlayer();
						//robot.ft("Finding target...");
						if (player == null)
							return;
						robot.ft(startFight[Misc.random(0, startFight.length - 1)]);
						startCombat(player);
						break;
					case 1:
					case 2:
					case 3:
						//robot.ft("Random walking...");
						if (Misc.random(10) == 1)
							robot.ft(fightPhrases[Misc.random(0, fightPhrases.length - 1)]);
						int x = Utils.getRandom(10);
						if (Utils.getRandom(1) == 0)
							x = -x;
						int y = Utils.getRandom(6);
						if (Wilderness.getWildLevel(robot) > 13)
							y = -y;
						WorldTile walkTile = new WorldTile(robot, 8).transform(x, y, 0);
						if (!World.isMultiArea(walkTile)) {
							PathFinder.simpleWalkTo(robot, walkTile);	
						}
						break;
				}
				pause(5 + Utils.random(10));
			}
			// RANDOM
			robot.setNextFaceEntity(target);
		} else {
			boolean needsToBank = robot.getInventory().getFreeSlots() > 0;
			if (needsToBank || needsToRun) {
				// run to bank
				getScript().banking.stage = Stage.Looking;
				setStage(Stage.Finished);
			} else {
				// go back to wildy
				if (robot.hasWalkSteps())
					return;
				WorldTile wildyTile = new WorldTile(3055 + Utils.random(60), 3555,0);
				if (!robot.withinDistance(wildyTile, 100)) {
					//robot.ft("Teleporting now.");
					Magic.sendNormalTeleportSpell(robot, 0, 0, new WorldTile(3081, 3520, 0));
				} else {
					final WorldObject wall = findNearObject("wilderness wall");
					if (wall != null && robot.withinDistance(wall, 5) && !Wilderness.isAtWild(robot)) {
						robot.stopAll();
						robot.lock(4);
						robot.setNextAnimation(new Animation(6132));
						final WorldTile toTile = new WorldTile(wall.getRotation() == 3
								|| wall.getRotation() == 1 ? wall.getX() - 1
										: robot.getX(), wall.getRotation() == 0
										|| wall.getRotation() == 2 ? wall.getY() + 2
												: robot.getY(), wall.getPlane());
						robot.setNextForceMovement(new ForceMovement(
								new WorldTile(robot),
								1,
								toTile,
								2,
								wall.getRotation() == 0 || wall.getRotation() == 2 ? ForceMovement.NORTH
										: ForceMovement.WEST));
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								robot.setNextWorldTile(toTile);
								robot.faceObject(wall);
								robot.getControlerManager().startControler("Wilderness");
								robot.resetReceivedDamage();
							}
						}, 2);
					} else
						PathFinder.simpleWalkTo(robot, wildyTile);
				}
			}
			pause(5 + Utils.random(10));
		}
	}
	
	public void startCombat(Entity target) {
		this.target = target;
		robot.stopAll(true);
		robot.getActionManager().setAction(new PlayerCombat(target));
	}
	
	public int useSpecial;
	public boolean turnedSpecialOn;
	
	public void processCombat(Entity target) {
		Lunars.handleVengeance(robot);
		// MELEE
		if (getScript().banking.specialWeapons.isEmpty())
			return;
		//robot.ft("Target Hitpoints: "+target.getHitpoints());
		
		
		int MELEE = 0, RANGE = 1, MAGE = 2;
		final int prayerActive = (robot.getPrayer().usingPrayer(0, 17) || robot
				.getPrayer().usingPrayer(1, 7)) ? MAGE : (robot
				.getPrayer().usingPrayer(0, 18) || robot.getPrayer()
				.usingPrayer(1, 8)) ? RANGE
				: (robot.getPrayer().usingPrayer(0, 19) || robot
						.getPrayer().usingPrayer(1, 9)) ? MELEE : -1;
		boolean removeArmor = stage == Stage.Running && prayerActive == MAGE
				|| (robot.isCastVeng() && robot.getHitpoints() > 500);
		if (removeArmor) {
			removed.add(robot.getEquipment().getChestId());
			removed.add(robot.getEquipment().getLegsId());
			removed.add(robot.getEquipment().getHatId());
			ButtonHandler.sendRemove(robot, Equipment.SLOT_CHEST);
			ButtonHandler.sendRemove(robot, Equipment.SLOT_LEGS);
			ButtonHandler.sendRemove(robot, Equipment.SLOT_HAT);
		} else {
			for (int id : removed) {
				ButtonHandler.sendWear(robot, robot.getInventory().getItems().lookupSlot(id), id);
			}
			removed.clear();
		}
		
		boolean shouldUseSpecial = useSpecial > 0 || robot.getCombatDefinitions().getSpecialAttackPercentage() >= PlayerCombat.getSpecialAmmount(getScript().banking.specialWeapons.get(0));
		if (shouldUseSpecial && useSpecial == 0) {
			int hitpoints = 400;
			if (target instanceof Player) {
				Player player = (Player) target;
				int meleeDef = player.getCombatDefinitions().getBonuses()[5];// 5,6,7
				for (int i = 6; i < 8; i++) {
					if (player.getCombatDefinitions().getBonuses()[i] > meleeDef)
						meleeDef = player.getCombatDefinitions().getBonuses()[i];
				}
				int meleeAtt = (robot.getCombatDefinitions().getBonuses()[0]/* * 1.3*/);
				for (int i = 1; i < 3; i++) {
					if ((robot.getCombatDefinitions().getBonuses()[i]/* * 1.3*/) > meleeAtt)
						meleeAtt = (robot.getCombatDefinitions()
								.getBonuses()[i]/* * 1.3*/);
				}
				if (meleeAtt > (meleeDef * 2)) {
					hitpoints += 400;
				}
				if (player.getFoodDelay() > Utils.currentTimeMillis()) {
					hitpoints += 100;
				}
				if (removeArmor) {
					hitpoints += 200;
				}
			}
			shouldUseSpecial = target.getHitpoints() <= hitpoints;
		}
		boolean wearingSpecialWeapon = getScript().banking.specialWeapons.contains(robot.getEquipment().getWeaponId());
		@SuppressWarnings("unused")
		boolean usedSpecial = turnedSpecialOn && robot.getCombatDefinitions().isUsingSpecialAttack();
		if (shouldUseSpecial) {
			if (!wearingSpecialWeapon) {
				for (int itemId : getScript().banking.specialWeapons) {
					ButtonHandler.sendWear(robot, robot.getInventory().getItems().lookupSlot(itemId), itemId);
					useSpecial = 4;
				}
			} else {
				boolean usingSpecial = robot.getCombatDefinitions().isUsingSpecialAttack();
				if (!usingSpecial) {
					turnedSpecialOn = true;
					robot.getCombatDefinitions().switchUsingSpecialAttack();
					pause(3);
				}
			}
		} else {
			boolean usingSpecial = robot.getCombatDefinitions().isUsingSpecialAttack();
			if (usingSpecial) {
				robot.getCombatDefinitions().switchUsingSpecialAttack();
			}
			if (wearingSpecialWeapon) {
				for (int itemId : getScript().banking.mainWeapons) {
					ButtonHandler.sendWear(robot, robot.getInventory().getItems().lookupSlot(itemId), itemId);
				}
			} else {
				// Dharok
				if (robot.getEquipment().getChestId() == 4720) {
					boolean wearingDharokGreatAxe = robot.getEquipment().getWeaponId() == 4718;
					if (robot.getHitpoints() <= 500) {
						if (!wearingDharokGreatAxe)
							ButtonHandler.sendWear(robot, robot.getInventory().getItems().lookupSlot(4718), 4718);
					} else {
						if (wearingDharokGreatAxe) {
							for (int itemId : getScript().banking.mainWeapons) {
								ButtonHandler.sendWear(robot, robot.getInventory().getItems().lookupSlot(itemId), itemId);
							}
						}
					}
				}
			}
		}
		if (useSpecial > 0)
			useSpecial--;
	}
	
	public List<Integer> removed = new ArrayList<Integer>();
	
	public void endCombat() {
		target = null;
		// robot.stopAll(true);
	}
	
	public void setStage(Stage stage) {
		if (stage != Stage.Fighting && stage != Stage.Running) {
			endCombat();
		}
		/*if (stage == Stage.Running) {
			Thread.dumpStack();
		}*/
		this.stage = stage;
	}
	
	public Player findAttackablePlayer() {
		final List<Player> players = new ArrayList<Player>();
		final List<Player> teleblockedPlayers = new ArrayList<Player>();
		final List<Integer> playerIndexes = World.getRegion(robot.getRegionId()).getPlayerIndexes();
		final long robotWealth = Player.getDropWealth(robot);
		if (playerIndexes == null)
			return null;
		for (final int playerIndex : playerIndexes) {
			Player player = World.getPlayers().get(playerIndex);
			if (player == null || !canAttack(player) || robot.getDisplayName().equals(player.getDisplayName()) || !robot.withinDistance(player, 15) || player.getLockDelay() > Utils.currentTimeMillis()
					/*|| robotWealth > player.getDropWealth()*/)
				continue;
			if (!player.isRobot() && (robotWealth * 2) > player.getDropWealth())
				continue;
			if (robotWealth > 2000000)
				continue;
			if (player.getTeleBlockDelay() >= Utils.currentTimeMillis())
				teleblockedPlayers.add(player);
			players.add(player);
		}
		if (!teleblockedPlayers.isEmpty())
			return teleblockedPlayers.get(Utils.random(teleblockedPlayers.size()));
		if (!players.isEmpty())
			return players.get(Utils.random(players.size()));
		return null;
	}
	
	public boolean isOnPlayer() {
		List<Integer> playersIndexes = World.getRegion(robot.getRegionId()).getPlayerIndexes();
		if (playersIndexes != null) {
			for (int playerIndex : playersIndexes) {
				Player player = World.getPlayers().get(playerIndex);
				if (player == null || robot == player || player.hasWalkSteps())
					continue;
				if (robot.matches(player)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean canAttack(Entity target) {
		if (target == null || target.isDead() || target.hasFinished() || ((!robot.isAtMultiArea() || !target.isAtMultiArea()) && target.getAttackedBy() != robot && target.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) || !robot.getControlerManager().getControler().canHit(target) || !robot.getControlerManager().canAttack(target) || !robot.withinDistance(target))
			return false;
		return true;
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
}
