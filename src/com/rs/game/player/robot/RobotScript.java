package com.rs.game.player.robot;

import java.util.ArrayList;
import java.util.List;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.PublicChatMessage;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.HomeTeleport;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.robot.scripts.combat.AncientHybrid;
import com.rs.game.player.robot.scripts.combat.AncientTribrid;
import com.rs.game.player.robot.scripts.combat.RegularTribrid;
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

public abstract class RobotScript {

	protected boolean debug = false;

	protected int BANK = 1, PRAYER = 2, RANDOM = 3;

	protected int LUMBRIDGE = 0, EDGEVILLE = 1;

	protected int characterize = 0;

	public int[][][][] place = {
			{
					{ { 3170, 3150, 3260, 3300, 0 } },// dimensions
					{ { 3219, 3214, 3225, 3227, 0 },
							{ 3218, 3218, 3218, 3219, 0 },
							{ 3213, 3217, 3215, 3219, 0 },
							{ 3214, 3214, 3216, 3216, 0 },
							{ 3215, 3211, 3215, 3211, 0 },
							{ 3211, 3209, 3213, 3211, 0 },
							{ 3206, 3209, 3208, 3211, 0 },
							{ 3205, 3209, 3205, 3209, 0 },
							{ 3205, 3209, 3205, 3209, 1 },
							{ 3205, 3209, 3205, 3209, 2 },
							{ 3205, 3210, 3207, 3212, 2 },
							{ 3206, 3218, 3206, 3218, 2 },
							{ 3208, 3218, 3210, 3220, 2 } },// banking
					{ { 3219, 3214, 3225, 3227, 0 },
							{ 3226, 3217, 3228, 3219, 0 },
							{ 3229, 3218, 3229, 3218, 0 },
							{ 3232, 3218, 3232, 3218, 0 },
							{ 3234, 3213, 3236, 3215, 0 },
							{ 3237, 3210, 3237, 3210, 0 },
							{ 3240, 3209, 3240, 3209, 0 },
							{ 3240, 3207, 3240, 3207, 0 },
							{ 3242, 3206, 3242, 3206, 0 } },// prayer
					{ { 3228, 3218, 3228, 3219, 0 },
							{ 3232, 3218, 3232, 3219, 0 },
							{ 3234, 3219, 3236, 3221, 0 },
							{ 3236, 3224, 3238, 3226, 0 },
							{ 3238, 3226, 3238, 3226, 0 },
							{ 3244, 3225, 3244, 3225, 0 },
							{ 3252, 3224, 3254, 3226, 0 },
							{ 3258, 3225, 3262, 3229, 0 },
							{ 3258, 3233, 3260, 3235, 0 },
							{ 3260, 3235, 3260, 3235, 0 },
							{ 3260, 3236, 3260, 3236, 0 },
							{ 3258, 3239, 3260, 3241, 0 },
							{ 3257, 3244, 3259, 3246, 0 },
							{ 3254, 3249, 3256, 3251, 0 },
							{ 3248, 3258, 3250, 3260, 0 },
							{ 3243, 3260, 3245, 3262, 0 },
							{ 3241, 3262, 3241, 3262, 0 },
							{ 3235, 3260, 3237, 3262, 0 },
							{ 3228, 3261, 3230, 3263, 0 },
							{ 3224, 3260, 3224, 3260, 0 },
							{ 3218, 3259, 3220, 3261, 0 },
							{ 3216, 3250, 3220, 3254, 0 },
							{ 3220, 3246, 3222, 3248, 0 },
							{ 3222, 3239, 3224, 3241, 0 },
							{ 3230, 3229, 3232, 3231, 0 },
							{ 3232, 3221, 3234, 3223, 0 },
							{ 3232, 3219, 3232, 3219, 0 },
							{ 3220, 3217, 3222, 3219, 0 } },// walking
			},// lumbridge
			{
					{ { 3050, 3465, 3130, 3500, 0 } },// dimensions
					{ { 3079, 3474, 3083, 3478, 0 },
							{ 3079, 3479, 3079, 3479, 0 },
							{ 3078, 3483, 3080, 3485, 0 },
							{ 3086, 3483, 3088, 3485, 0 },
							{ 3087, 3489, 3089, 3491, 0 },
							{ 3091, 3491, 3091, 3491, 0 },
							{ 3093, 3491, 3093, 3491, 0 },
							{ 3092, 3493, 3094, 3495, 0 } },// banking
					{ { 3079, 3474, 3083, 3478, 0 },
							{ 3078, 3480, 3080, 3482, 0 },
							{ 3080, 3484, 3082, 3486, 0 },
							{ 3086, 3488, 3088, 3490, 0 },
							{ 3086, 3502, 3088, 3504, 0 },
							{ 3079, 3502, 3081, 3504, 0 },
							{ 3076, 3500, 3078, 3502, 0 },
							{ 3075, 3501, 3075, 3501, 0 },
							{ 3075, 3503, 3075, 3503, 0 },
							{ 3070, 3502, 3072, 3504, 0 },
							{ 3068, 3507, 3068, 3507, 0 },
							{ 3062, 3505, 3064, 3507, 0 },
							{ 3059, 3510, 3061, 3512, 0 },
							{ 3052, 3510, 3052, 3510, 0 },
							{ 3052, 3507, 3052, 3507, 0 },
							{ 3054, 3507, 3054, 3507, 0 },
							{ 3054, 3505, 3054, 3505, 0 },
							{ 3051, 3499, 3053, 3501, 0 },
							{ 3051, 3489, 3053, 3491, 0 },
							{ 3055, 3490, 3055, 3490, 0 },
							{ 3058, 3490, 3058, 3490, 0 },
							{ 3058, 3483, 3058, 3483, 0 },
							{ 3051, 3496, 3053, 3498, 1 } },// prayer
					{ { 3079, 3474, 3083, 3478, 0 } },// walking
			},// edgeville
	};

	public long fightTime = 0;

	public Player robot;

	public boolean reverseRoute = false;

	public int fixedPathIndex;

	public int waitTimer = 0;

	private long messageDelay;

	public WorldTile toTile;

	public String objective;

	public String lastType;

	public List<WorldTile> areas;

	public int fixGlitches = 0;

	protected boolean needsToBank;

	protected boolean needsToPray;

	public abstract void bank();

	public abstract void buttons();

	public void checkGlitches() {
		if (fixGlitches++ > 30) {
			if (robot.withinDistance(new WorldTile(3203, 3235, 0), 4)
					|| robot.withinDistance(new WorldTile(3206, 3228, 0), 4)) {
				startObjective("banking");
				robot.setNextWorldTile(getHome());
			}
			fixGlitches = 0;
		}
	}

	public abstract void combat();

	public void debugMessage(String string) {
		// if (debug && !robot.isRobot())
		if (!robot.isRobot())
			System.out.println(string);
	}

	public boolean fightLastedFor(int seconds) {
		return fightTime == 0 ? false
				: (((Utils.currentTimeMillis() - fightTime) / 1000) > seconds);
	}

	public Player findAttackablePlayer() {
		final List<Player> players = new ArrayList<Player>();
		final List<Player> teleblockedPlayers = new ArrayList<Player>();
		final List<Integer> playerIndexes = World
				.getRegion(robot.getRegionId()).getPlayerIndexes();
		final long robotWealth = robot.getDropWealth();
		if (playerIndexes == null)
			return null;
		for (final int playerIndex : playerIndexes) {
			Player player = World.getPlayers().get(playerIndex);
			if (player == null
					|| player.isDead()
					|| player.hasFinished()
					|| ((!robot.isAtMultiArea() || !player.isAtMultiArea())
							&& player.getAttackedBy() != robot && player
							.getAttackedByDelay() + 10000 > Utils
							.currentTimeMillis())
					|| !player.isCanPvp()
					|| robot.getDisplayName().equals(player.getDisplayName())
					|| !robot.getControlerManager().getControler()
							.canHit(player)
					|| !robot.withinDistance(player, 15)
					|| !robot.getControlerManager().canAttack(player)
					|| player.getLockDelay() > Utils.currentTimeMillis()
					|| robotWealth > player.getDropWealth())
				continue;
			if (player.getTeleBlockDelay() >= Utils.currentTimeMillis())
				teleblockedPlayers.add(player);
			players.add(player);
		}
		if (!teleblockedPlayers.isEmpty())
			return teleblockedPlayers.get(Utils.random(teleblockedPlayers
					.size()));
		if (!players.isEmpty())
			return players.get(Utils.random(players.size()));
		return null;
	}

	// private String combatType;

	public int findIndex() {
		debugMessage("[RobotScript] FindIndex: ");
		for (int i = 0; i < place.length; i++) {
			if (robot.getX() >= place[i][0][0][0]
					&& robot.getX() <= place[i][0][0][2]
					&& robot.getY() >= place[i][0][0][1]
					&& robot.getY() <= place[i][0][0][3]) {
				return i;
			}
		}
		return -1;
	}

	public NPC findNearNPC(String name) {
		final List<NPC> npcs = new ArrayList<NPC>();
		final List<Integer> npcsIndexes = World.getRegion(robot.getRegionId())
				.getNPCsIndexes();
		if (npcsIndexes != null) {
			for (int npcIndex : npcsIndexes) {
			NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null
					|| !npc.getName().toLowerCase()
							.contains(name.toLowerCase()))
				continue;
			npcs.add(npc);
			}
		}
		if (!npcs.isEmpty())
			return npcs.get(Utils.random(npcs.size()));
		return null;
	}

	public WorldObject findNearObject(String name) {
		final List<WorldObject> objects = new ArrayList<WorldObject>();
		for (final int i : robot.getMapRegionsIds()) {
			if (World.getRegion(i) == null
					|| World.getRegion(i).getAllObjects() == null)
				continue;
			for (final WorldObject object : World.getRegion(i).getAllObjects()) {
				if (object.getDefinitions().name.toLowerCase().contains(
						name.toLowerCase())) {
					objects.add(object);
				}
			}
		}
		if (!objects.isEmpty())
			return objects.get(Utils.random(objects.size()));
		return null;
	}

	public WorldObject findNearObject(String name, int height) {
		final List<WorldObject> objects = new ArrayList<WorldObject>();
		for (final int i : robot.getMapRegionsIds())
			for (final WorldObject object : World.getRegion(i).getAllObjects()) {
				if (object.getPlane() == height
						&& object.getDefinitions().name.toLowerCase().contains(
								name.toLowerCase())) {
					objects.add(object);
				}
			}
		if (!objects.isEmpty())
			return objects.get(Utils.random(objects.size()));
		return null;
	}

	private WorldTile findNextTile(int index, int secondIndex) {
		for (int i = 0; i < place[index][secondIndex].length; i++) {
			if (robot.getX() >= place[index][secondIndex][i][0]
					&& robot.getX() <= place[index][secondIndex][i][2]
					&& robot.getY() >= place[index][secondIndex][i][1]
					&& robot.getY() <= place[index][secondIndex][i][3]
					&& robot.getPlane() == place[index][secondIndex][i][4]) {
				if (i == (place[index][secondIndex].length - 2)
						|| (i == 0 && reverseRoute)) {
					if (secondIndex == PRAYER)
						startObjective("pray");
					else if (secondIndex == BANK)
						startObjective("bank");
					else if (objective.equals("max"))
						startObjective("edgeville");
				}
				if (i == 0)
					reverseRoute = false;
				i += (reverseRoute ? -1 : 1);
				if (i >= place[index][secondIndex].length) {
					objective = "";
					return null;
				}
				return getTile(place[index][secondIndex][i][0],
						place[index][secondIndex][i][1],
						place[index][secondIndex][i][2],
						place[index][secondIndex][i][3],
						place[index][secondIndex][i][4]);
			}
		}
		return null;
	}

	public void finishObjective() {
		robot.getTemporaryAttributtes().put("lastObjective", objective);
		objective = "";
	}

	public Entity getAttackedBy() {
		final Entity attackedBy = (Entity) robot.getTemporaryAttributtes().get(
				"attackedBy");
		debugMessage("[RobotScript] GetAttackedBy: " + attackedBy);
		if (robot.getAttackedBy() != null)
			return robot.getAttackedBy();
		if (attackedBy == null)
			return null;
		if (!attackedBy.withinDistance(robot, 15)
				|| (robot.getAttackedByDelay() + 10000 < Utils
						.currentTimeMillis() && attackedBy.getAttackedByDelay() + 10000 < Utils
						.currentTimeMillis())) {
		//	if (!(robot.getControlerManager().getControler() instanceof ArenaControler))
				setAttackedBy(null);
			// robot.getTemporaryAttributtes().remove("attackedBy");
			return null;
		}
		return attackedBy;
	}

	public abstract int[][][] getFixedPath();

	public WorldTile getHome() {
		return getHomes()[Utils.getRandom(getHomes().length - 1)];
	}

	public abstract WorldTile[] getHomes();

	public int getPathIndex() {
		final List<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < getFixedPath().length; i++) {
			for (int i2 = 0; i2 < getFixedPath()[i].length; i2++) {
				if (robot.getX() >= getFixedPath()[i][i2][0]
						&& robot.getX() <= getFixedPath()[i][i2][2]
						&& robot.getY() >= getFixedPath()[i][i2][1]
						&& robot.getY() <= getFixedPath()[i][i2][3]
						&& robot.getPlane() == getFixedPath()[i][i2][4]) {
					indexes.add(i);
				}
			}
		}
		if (!indexes.isEmpty()) {
			final int index = indexes.get(Utils.getRandom(indexes.size() - 1));
			debugMessage("[RobotScript] GetPathIndex: " + index);
			return index;
		}
		return -1;
	}

	public abstract int[][] getSetId();

	private WorldTile getTile(int x, int y, int x2, int y2, int plane) {
		final WorldTile min = new WorldTile(x, y, 0);
		final WorldTile max = new WorldTile(x2, y2, 0);
		return new WorldTile(min.getX()
				+ Utils.getRandom(max.getX() - min.getX()), min.getY()
				+ Utils.getRandom(max.getY() - min.getY()), plane);
	}

	public void homeTeleport() {
		if (!(robot.getControlerManager().getControler() instanceof Wilderness)) {
			wait(25);
			robot.getActionManager().setAction(new HomeTeleport(getHome()));
		} else {
			wait(5);
			if (!Magic.useTeleTab(robot, getHome())) {
				PathFinder.simpleWalkTo(robot, new WorldTile(robot.getX(),
						robot.getY() - 10, robot.getPlane()));
				// robot.addWalkSteps(robot.getX(), robot.getY()-10);
				if (!robot.hasWalkSteps())
					robot.addWalkSteps(
							robot.getX() - Misc.getRandom(5)
									+ Misc.getRandom(5), robot.getY());
			} else {
				startObjective("banking");
			}
		}
	}

	public void init(Player player) {
		this.robot = player;
		this.fixedPathIndex = -1;
		this.objective = "";
		this.areas = new ArrayList<WorldTile>();
		this.lastType = "";
		characterize = Misc.random(4);
		player.getCombatDefinitions().setSpellBook(
				this instanceof RegularTribrid ? 0 : 1);
		if (this instanceof AncientHybrid || this instanceof AncientTribrid) {
			player.getCombatDefinitions().setAutoRelatie(false);
		}
	}

	public abstract void inventory();

	public String lastObjective() {
		if (robot.getTemporaryAttributtes().get("lastObjective") != null)
			return (String) robot.getTemporaryAttributtes()
					.get("lastObjective");
		return "";
	}

	public abstract void pickup();

	public void pickUpStuff(final WorldTile tile) {
		needsToPray = true;
		robot.getPrayer().switchPrayer(9);
		if (tile == null)
			return;
		if (!tile.withinDistance(robot, 10)) {
			if (tile.getY() < 3510 && !Magic.useTeleTab(robot, tile))
				return;
		}
		robot.lock(15);
		robot.setAttackedByDelay(Utils.currentTimeMillis() + 13000);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				robot.setFreezeDelay(0);
				robot.stopAll(true);
				PathFinder.simpleWalkTo(robot, tile);
				// robot.addWalkSteps(tile.getX(), tile.getY(),
				// tile.getPlane());
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						robot.stopAll(true);
						PathFinder.simpleWalkTo(robot, tile);
						// robot.addWalkSteps(tile.getX(), tile.getY(),
						// tile.getPlane());
					}
				}, 2);
				/*
				 * if (robot.getX() != tile.getX() || robot.getY() !=
				 * tile.getY()) { robot.addWalkSteps(tile.getX(), tile.getY(),
				 * tile.getPlane()); WorldTasksManager.schedule(new WorldTask()
				 * {
				 * 
				 * @Override public void run() { robot.setNextAnimation(new
				 * Animation(833)); } }, 3/4); }
				 */
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						final List<FloorItem> items = World.getRegion(
								tile.getRegionId()).getGroundItems();
						if (items != null) {
							for (final FloorItem item : items) {
								if (item == null)
									continue;
								if (item.getTile().getX() != tile.getX()
										|| item.getTile().getY() != tile.getY())
									continue;
								if (!item.getOwner()
										.equals(robot.getUsername())) {
									continue;
								}
								if ((item.getDefinitions().getValue() * item
										.getAmount()) <= 38000
								// && Food.forId(item.getId()) == null
								) {// if it's not worth a lot and it's not food
									continue;
								}
								boolean hasFreeSpace = true;
								if (robot.getInventory().getFreeSlots() == 0) {
									hasFreeSpace = false;
									for (int slot = 0; slot < robot
											.getInventory().getItems()
											.getItems().length; slot++) {
										final Item toDrop = robot
												.getInventory().getItem(slot);
										if ((toDrop.getDefinitions().getValue() * toDrop
												.getAmount()) <= 12000) {
											World.addGroundItem(toDrop,
													new WorldTile(robot),
													robot, false, 180, true);
											robot.getInventory().deleteItem(
													slot, toDrop);
											hasFreeSpace = true;
											break;
										}
									}
								}
								if (!hasFreeSpace)
									break;
								World.removeGroundItem(robot, item);
							}
						}
						needsToPray = false;
						needsToBank = true;
						startObjective("getoutofwild");
					}
				}, 4);
			}
		}, 10);
	}

	public abstract void prayer();

	public void process() {
		if (robot.isDead() || robot.getLockDelay() > Utils.currentTimeMillis())
			return;
		checkGlitches();
		inventory();
		speak();
		prayer();
		buttons();
		if (waiting()) {
			return;
		}
		if (messageDelay > 0)
			messageDelay--;
		bank();
		combat();
		summoning();
		transportation();
		pickup();
		debugMessage("[RobotScript] Process: " + objective + ", "
				+ robot.getCombatDefinitions().getAutoCastSpell());
	}

	public void publicMessage(final String message, final String... message2) {
		if (messageDelay < Utils.currentTimeMillis()) {
			messageDelay = Utils.currentTimeMillis() + 20000;
			robot.sendPublicChatMessage(new PublicChatMessage(Utils
					.fixChatMessage(message), 0));
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					robot.setNextForceTalk(new ForceTalk(Utils
							.fixChatMessage(message)));
				}
			}, 1);
			if (message2.length == 1) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						robot.sendPublicChatMessage(new PublicChatMessage(Utils
								.fixChatMessage(message2[0]), 0));
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								robot.setNextForceTalk(new ForceTalk(Utils
										.fixChatMessage(message2[0])));
							}
						}, 1);
					}
				}, 5);
			}
		}
	}

	public void setAttackedBy(Entity attackedBy) {
		debugMessage("[RobotScript] SetAttackedBy: " + attackedBy);
		if (attackedBy == null) {
			robot.getTemporaryAttributtes().remove("attackedBy");
			fightTime = 0;
		} else {
			if (robot instanceof Robot)
				((Robot) robot).setAttackedBy2(attackedBy);
			robot.getTemporaryAttributtes().put("attackedBy", attackedBy);
			fightTime = Utils.currentTimeMillis();
		}
	}

	public abstract void speak();

	public void startObjective(String objective) {
		this.objective = objective;
		fixedPathIndex = -1;
		System.out.println(objective);
		if (objective.equals("combat") || objective.equals("attack")) {
			robot.getCombatDefinitions().resetSpells(true);
			if (robot.script instanceof RegularTribrid)
				Magic.setCombatSpell(robot, 99);
			if (robot.script instanceof AncientTribrid
					|| robot.script instanceof AncientHybrid)
				Magic.setCombatSpell(robot, 23);
			debugMessage("[RobotScript] Set the combat spell");
		}
	}

	public abstract void summoning();

	public abstract void transportation();

	public boolean underCombat(String name) {
		return false;
	}

	public void useAlter() {
		robot.setNextAnimation(new Animation(645));
		final int maxPrayer = robot.getSkills().getLevelForXp(Skills.PRAYER) * 10;
		robot.getPrayer().restorePrayer(maxPrayer);
		finishObjective();
		wait(5);
	}

	public void useBank(WorldTile tile) {
		/*
		 * final boolean cheatSwitch = (this instanceof RegularTribrid || this
		 * instanceof AncientTribrid || this instanceof AncientHybrid);
		 * needsToBank = false; needsToPray = false; //if (Settings.LOCAL) //
		 * wait(20); //else wait(10+Utils.getRandom(20));
		 * PathFinder.simpleWalkTo(robot, tile);
		 * //robot.addWalkSteps(tile.getX(), tile.getY()); if (robot.getX() >=
		 * 3091 && robot.getX() <= 3098 && robot.getY() >= 3488 && robot.getY()
		 * <= 3499) { tile = robot; } //robot.setCoordsEvent(new
		 * CoordsEvent(robot, new WorldTile( // tile.getX(), tile.getY(),
		 * tile.getPlane()), 3, new Runnable() { // @Override // public void
		 * run() { robot.reset(); WorldTasksManager.schedule(new WorldTask() {
		 * 
		 * @Override public void run() { final int length =
		 * SetManager.getSingleton().list.size()-1; int combatLevel = 0; if
		 * (robot instanceof Robot) { combatLevel = ((Robot)robot).combatLevel;
		 * } //fix shield 2 hand glitch
		 * robot.getEquipment().wieldOneItem(Equipment.SLOT_SHIELD, null); final
		 * int setId =
		 * getSetId()[combatLevel][Utils.getRandom(getSetId()[combatLevel
		 * ].length-1)]; if (setId < 0 || setId > length) { return; } Set set =
		 * SetManager.getSingleton().list.get(setId); Item[] items =
		 * set.getInv(); Item[] items2 = set.getEquip(); for (int i = 0; i <
		 * items.length; i++) { if (items[i] == null || items[i].getId() <= 1)
		 * continue; robot.getInventory().getItems().set(i, items[i]);
		 * robot.getInventory().refresh(i); } for (int o = 0; o < 6; o++) { if
		 * (items2[o] == null || items2[o].getId() <= 1) continue;
		 * robot.getEquipment().wieldOneItem(o, items2[o]); }
		 * WorldTasksManager.schedule(new WorldTask() {
		 * 
		 * @Override public void run() { Set set =
		 * SetManager.getSingleton().list.get(setId); Item[] items2 =
		 * set.getEquip(); for (int o = 6; o < items2.length; o++) { if
		 * (items2[o] == null || items2[o].getId() <= 1) continue;
		 * robot.getEquipment().wieldOneItem(o, items2[o]); }
		 * robot.getAppearence().generateAppearenceData(); if (cheatSwitch) {
		 * robot.enableCheatSwitch(); Set set2 =
		 * SetManager.getSingleton().list.get(setId+1); if (setId+1 > length) {
		 * return; } Item[] cheatItems = set2.getInv(); for (int i = 0; i <
		 * cheatItems.length; i++) { if (cheatItems[i] == null ||
		 * cheatItems[i].getId() <= 1) continue;
		 * robot.getInventory().getItems().set(i, cheatItems[i]);
		 * robot.getInventory().refresh(i); } } } }, 3);
		 * robot.getAppearence().generateAppearenceData(); finishObjective(); }
		 * }, waitTimer - 5); //} // }); // } //}));
		 */
	}

	public void useMax() {
		/*
		 * wait(10); final NPC npc = findNearNPC("max"); if (npc == null) { if
		 * (retryMax) { retryMax = false; return; } homeTeleport(); return; }
		 * retryMax = true; PathFinder.simpleWalkTo(robot, new
		 * WorldTile(npc.getX(), npc.getY()+1, npc.getPlane()));
		 * //robot.addWalkSteps(npc.getX(), npc.getY()+1); if
		 * (!robot.hasWalkSteps()) { //Magic.sendNormalTeleportSpell(robot, 1,
		 * 0, // getHome()); homeTeleport(); return; } robot.setCoordsEvent(new
		 * CoordsEvent(new WorldTile( npc.getX(), npc.getY(), npc.getPlane()),
		 * 1, new Runnable() {
		 * 
		 * @Override public void run() { finishObjective();
		 * robot.setNextFaceWorldTile(npc); npc.setNextFaceWorldTile(robot);
		 * npc.setNextAnimation(new Animation(832));
		 * Magic.sendItemTeleportSpell(robot, true, 832, -1, 1, new
		 * WorldTile(3080, 3475, 0)); WorldTasksManager.schedule(new WorldTask()
		 * {
		 * 
		 * @Override public void run() { fixedPathIndex = getPathIndex(); } },
		 * 5); } }));
		 */
	}

	public void wait(int time) {
		waitTimer = time;
	}

	public boolean waiting() {
		return waitTimer-- > 0;
	}

	public void walkFixedPath() {
		debugMessage("[RobotScript] walkFixedPath.");
		if (robot.hasWalkSteps())
			return;
		for (int i = getFixedPath()[fixedPathIndex].length - 1; i >= 0; i--) {
			if (robot.getX() >= getFixedPath()[fixedPathIndex][i][0]
					&& robot.getX() <= getFixedPath()[fixedPathIndex][i][2]
					&& robot.getY() >= getFixedPath()[fixedPathIndex][i][1]
					&& robot.getY() <= getFixedPath()[fixedPathIndex][i][3]
					&& robot.getPlane() == getFixedPath()[fixedPathIndex][i][4]) {
				i += 1;
				if (i >= getFixedPath()[fixedPathIndex].length) {
					final int index = getPathIndex();
					fixedPathIndex = index;
					return;
				}
				toTile = getTile(getFixedPath()[fixedPathIndex][i][0],
						getFixedPath()[fixedPathIndex][i][1],
						getFixedPath()[fixedPathIndex][i][2],
						getFixedPath()[fixedPathIndex][i][3],
						getFixedPath()[fixedPathIndex][i][4]);
				PathFinder.simpleWalkTo(robot, toTile);
				// robot.addWalkSteps(toTile.getX(), toTile.getY());
				if (!robot.hasWalkSteps()) {
					homeTeleport();
				}
				return;
			}
		}
		final int index = getPathIndex();
		fixedPathIndex = index;
		if (index == -1) {
			debugMessage(robot.getX() + "," + robot.getY());
			debugMessage("[RobotScript] Error!!! Couldn't find fixedpathindex!!!! #"
					+ fixedPathIndex);
			homeTeleport();
			startObjective("max");
		} else {
			fixedPathIndex = index;
		}
	}

	public void walkToObjective(int secondIndex) {// add teleblock method
		debugMessage("[RobotScript] walkToObjective: " + secondIndex);
		if (!robot.hasWalkSteps()) {
			WorldTile toTile = null;
			if (findIndex() != -1)
				toTile = findNextTile(findIndex(), secondIndex);
			if (toTile == null) {
				// Magic.sendNormalTeleportSpell(robot, 1, 0,
				// getHome());//Utils.getRandom(8)
				homeTeleport();
			} else if (toTile.getPlane() != robot.getPlane())
				robot.setNextWorldTile(toTile);
			else {
				PathFinder.simpleWalkTo(robot, toTile);
				// robot.addWalkSteps(toTile.getX(), toTile.getY());
				if (!robot.hasWalkSteps()) {
					// Magic.sendNormalTeleportSpell(robot, 1, 0,
					// getHome());
					homeTeleport();
				}
			}
		}
	}

	public boolean wearingSpecialWeapon() {
		final boolean cheatSwitch = (this instanceof RegularTribrid
				|| this instanceof AncientTribrid || this instanceof AncientHybrid);
		final int weaponId = robot.getEquipment().getWeaponId();
		if (weaponId == 1215 || weaponId == 11235)
			return true;
		if (cheatSwitch)
			if (robot.cheatSwitch == null)
				System.out.println("WTF robot.cheatSwitch == null");
			else
				for (final int itemId : robot.cheatSwitch.get(4))
					if (weaponId == itemId)
						return true;
		return false;
	}

	public void wearItems(final int[] switchitems, boolean... special) {
		// wait((special.length > 0 && special[0]) ? 5 : 3);
		// wait((special.length > 0 && special[0]) ? 2 : 3);
		// final int[][] indexes =
		// {{0,4},{4,8},{8,robot.getInventory().getItems().getItemsCopy().length}};
		int[][] temps = null;
		final int random = Utils.getRandom(4);
		switch (random) {
		case 0:
			temps = new int[][] {
					{ 0, 4 },
					{ 5, robot.getEquipment().getItems().getItemsCopy().length } };
			break;
		case 1:
			temps = new int[][] {
					{ 0, 6 },
					{ 7, robot.getEquipment().getItems().getItemsCopy().length } };
			break;
		case 2:
			temps = new int[][] {
					{ 0, 3 },
					{ 4, 7 },
					{ 8, robot.getEquipment().getItems().getItemsCopy().length } };
			break;
		case 3:
			temps = new int[][] {
					{ 8, robot.getEquipment().getItems().getItemsCopy().length },
					{ 4, 7 }, { 0, 3 } };
			break;
		case 4:
			temps = new int[][] { { 0,
					robot.getEquipment().getItems().getItemsCopy().length } };
			break;
		}
		final int delay = Utils.random(2);
		final int period = 1;
		final int[][] indexes = temps;
		wait(delay + (period * indexes.length) + 1);
		// wearItems(switchitems, indexes[0][0], indexes[0][1]);
		if (indexes.length > 1) {
			WorldTasksManager.schedule(new WorldTask() {
				int i = 0;

				@Override
				public void run() {
					wearItems(switchitems, indexes[i][0], indexes[i][1]);
					i++;
					if (i == indexes.length) {
						final Entity entity = getAttackedBy();
						if (entity != null)
							robot.getActionManager().setAction(
									new PlayerCombat(entity));
						stop();
					}
				}
			}, delay, period);
		}
	}

	public void wearItems(int[] switchitems, int start, int end) {
		final Item[] items2 = robot.getInventory().getItems().getItemsCopy();
		/*
		 * for (int i = 0; i < items2.length; i++) { if (items2[i] == null)
		 * continue; for(int id : switchitems) for(int switchitem :
		 * robot.cheatSwitch.get(id)) if (items2[i].getId() == switchitem) { int
		 * slot = ItemDefinitions.getItemDefinitions(switchitem).getEquipSlot();
		 * if (ItemDefinitions.getItemDefinitions(switchitem).getEquipSlot())
		 * ButtonHandler.sendWear(robot, i, switchitem); } if
		 * (Food.forId(items2[i].getId()) != null) break; }
		 */
		for (final int id : switchitems) {
			for (final int switchitem : robot.cheatSwitch.get(id)) {
				final int slot = ItemDefinitions.getItemDefinitions(switchitem)
						.getEquipSlot();
				if (slot >= start && slot <= end) {
					for (int i = 0; i < items2.length; i++) {
						if (items2[i] == null)
							continue;
						if (items2[i].getId() == switchitem) {
							ButtonHandler.sendWear(robot, i, switchitem);
							break;
						}
						// if (Food.forId(items2[i].getId()) != null)
						// break;
					}
				}
			}
		}
	}

	public boolean wearItems(String type) {
		// this.combatType = type;
		if (lastType.equals(type) && !type.equals("special")) {// avoid constant
																// switching
			final Entity entity = getAttackedBy();
			if (entity != null)
				robot.getActionManager().setAction(new PlayerCombat(entity));
			return false;
		}
		lastType = type;
		robot.getCombatDefinitions().resetSpells(true);
		// robot.setNextFaceEntity(null);
		if (type.equalsIgnoreCase("barrage")) {
			wearItems(new int[] { 1 });
			Magic.setCombatSpell(robot, 23);
		} else if (type.equalsIgnoreCase("teleblock")) {
			wearItems(new int[] { 1 });
			Magic.setCombatSpell(robot, 86);
		} else if (type.equalsIgnoreCase("armadylstorm")) {
			wearItems(new int[] { 1 });
			Magic.setCombatSpell(robot, 99);
		} else if (type.equalsIgnoreCase("snare")) {
			wearItems(new int[] { 1 });
			Magic.setCombatSpell(robot, 81);
		} else if (type.equalsIgnoreCase("rangemelee")) {
			wearItems(new int[] { 2, 3, 6 });
		} else if (type.equalsIgnoreCase("melee")) {
			wearItems(new int[] { 2, 3, 7 });
		} else if (type.equalsIgnoreCase("special")) {
			// if (!lastType.equals(type))
			wearItems(new int[] { 2, 4 }, true);
			// robot.getCombatDefinitions().switchUsingSpecialAttack();
		} else if (type.equalsIgnoreCase("range")) {
			wearItems(new int[] { 5, 6 });
		}
		return true;
	}
}
