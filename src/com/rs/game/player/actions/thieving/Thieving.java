package com.rs.game.player.actions.thieving;

import java.util.List;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.npc.NPC;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.decoders.handlers.ObjectHandler;
import com.rs.utils.Utils;

/**
 * Handles the Thieving Skill
 * 
 * @author Dragonkk
 * 
 */
public class Thieving {

	public enum Stalls {
		
				VEGETABAL(0, 2, new int[] { 1957, 1965, 1942, 1982, 1550 }, 1, 2, 10,
				34381), 
				CAKE(34384, 5, new int[] { 1891, 1897, 2309 }, 1, 2.5,
				16, 34381), 
				CRAFTING(4874, 1, new int[] {1733, 1734, 1755, 1625, 1627,
				1629, 1623, 1621, 1619, 1617, 1759, 1592, 1597, 4020, 11065}, 1,
				1, 8, 0), 
				MONKEY_FOOD(4875, 25, new int[] { 1963, 4012, 6883, 2114, 397, 385, 15266, 7946, 373  }, 1, 1, 15,
				0), 
				LE_COIN(2620, 120, new int[] {995  }, 1, 1, 15,
						0), 
				MONKEY_GENERAL(4876, 50, new int[] { 4024, 4006, 590, 436, 438, 410, 442 }, 1,
				1, 22, 0), 
				TEA_STALL(0, 5, new int[] { 712 }, 1, 7, 16,
				34381), 
				SILK_STALL(34383, 20, new int[] { 950 }, 1, 8, 24,
				34381), 
				WINE_STALL(14011, 22, new int[] { 1937, 1993, 1987,
				1935, 7919 }, 1, 16, 27, 2046), 
				SEED_STALL(7053, 27, new int[] {
				5096, 5097,5097,5097,5097,5098,5098,5098,5098,5099,5099,5099,5099,5096,5096,
				5096,5096,5102,5102,5102,5100,5100,5100,5100, 5098, 5099, 5100, 5101, 5102, 5103, 
				5105,5313,5314,5315,5292,5280,5291,5292,5293,5294,5295,5293,5292,5291,5300,5298,5281,5285,5106,5313,5309,5310,5311,5322,5323,5324,5318,5319,5319,6453,5291,5100,5100,5100,5100,5104,5104,5104,5280,5280,
				5103,5103,5103,5318,5318,5318,5319,5319,5319,5319,5321,5320,5320,5322,5322,5322,5322,
				5324,5324,5324,5323,5323,5323,5306,5306,5306,5306}, 5, 11,
				27, 2047), 
				FUR_STALL(34387, 35, new int[] { 6814, 958 }, 1, 15,
				36, 34381), 
				FISH_STALL(0, 42, new int[] { 331, 359, 377 }, 1,
				16, 42, 34381), 
				CROSSBOW_STALL(0, 49, new int[] { 877, 9420,
				9440 }, 1, 11, 52, 34381), 
				SPICE_STALL(34386, 65,
				new int[] { 2007 }, 1, 80, 81, 34381), 
				MAGIC_STALL(4877, 65,new int[] { 554, 555, 556, 557, 558, 559, 560, 565, 566, 564, 561, 562, 563  }, 5, 1, 30, 0), 
				SCIMITAR_STALL(4878, 80, new int[] { 1321, 1323, 1325, 1327, 1329, 1331, 1333, 6721 }, 1, 1, 40, 0), 
				SILVER_STALL(34382, 50, new int[] { 442,2355 }, 1, 30, 54, 34381),
				GEM_STALL(34385,75, new int[] { 1623, 1621, 1619, 1617 }, 1, 180, 115, 34381);

		private int[] item;
		private int level;
		private int amount;
		private int objectId;
		private int replaceObject;
		private double experience;
		private double seconds;

		Stalls(int objectId, int level, int[] item, int amount, double seconds,
				double experience, int replaceObject) {
			this.objectId = objectId;
			this.level = level;
			this.item = item;
			this.amount = amount;
			this.seconds = seconds;
			this.experience = experience;
			this.replaceObject = replaceObject;
		}

		public int getReplaceObject() {
			return replaceObject;
		}

		public int getObjectId() {
			return objectId;
		}

		public int getItem(int count) {
			return item[count];
		}

		public int getAmount() {
			return amount;
		}

		public int getLevel() {
			return level;
		}

		public double getTime() {
			return seconds;
		}

		public double getExperience() {
			return experience;
		}
	}

	public static boolean isGuard(int npcId) {
		if (npcId == 32 || npcId == 21 || npcId == 2256 || npcId == 23 || npcId == 1317
				|| npcId == 2236 || npcId == 2571 || npcId == 9122 || npcId == 11269)
			return true;
		else
			return false;
	}
	


	public static void handleStalls(final Player player,
			final WorldObject object) {
		if (player.getAttackedBy() != null
				&& player.getAttackedByDelay() > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage(
					"You can't do this while you're under combat.");
			return;
		}
		
		for (final Stalls stall : Stalls.values()) {
			if (stall.getObjectId() == object.getId()) {
				final WorldObject emptyStall = new WorldObject(
						stall.getReplaceObject(), 10, object.getRotation(),
						object.getX(), object.getY(), object.getPlane());
				if (player.getSkills().getLevel(Skills.THIEVING) < stall
						.getLevel()) {
					player.getPackets().sendGameMessage(
							"You need a thieving level of " + stall.getLevel()
									+ " to steal from this.", true);
					return;
				}
				if (player.getInventory().getFreeSlots() <= 0) {
					player.getPackets().sendGameMessage(
							"Not enough space in your inventory.", true);
					return;
				}
				
				player.setNextAnimation(new Animation(881));
				player.lock(2);
				WorldTasksManager.schedule(new WorldTask() {
					boolean gaveItems;

					@Override
					public void run() {
						if (!World.containsObjectWithId(object, object.getId())) {
						    stop();
						    return;
						}
						if (!gaveItems) {
							if (player.getDailyTask() != null)
								player.getDailyTask().incrementTask(player, 2, stall.getObjectId(), Skills.THIEVING);
							player.getInventory().addItem(stall.getItem(Utils.getRandom(stall.item.length - 1)),Utils.getRandom(stall.getAmount()));
							player.randomevent(player);
							player.getSkills().addXp(Skills.THIEVING,stall.getExperience());
							gaveItems = true;
							checkGuards(player);
						} else {
							//World.spawnTemporaryObject(emptyStall,(int) (1 * stall.getTime()));
							//stop();
						}
					}
				}, 0, 0);
			}
		}
	}

	public static void checkGuards(Player player) {
		NPC guard = null;
		int lastDistance = -1;
		for (int regionId : player.getMapRegionsIds()) {
			List<Integer> npcIndexes = World.getRegion(regionId)
					.getNPCsIndexes();
			if (npcIndexes == null)
				continue;
			for (int npcIndex : npcIndexes) {
				NPC npc = World.getNPCs().get(npcIndex);
				if (npc == null)
					continue;
				if (!isGuard(npc.getId()) || npc.isUnderCombat()
						|| npc.isDead() || !npc.withinDistance(player, 4)
						|| !npc.clipedProjectile(player, true))
					continue;
				int distance = Utils.getDistance(npc.getX(), npc.getY(),
						player.getX(), player.getY());
				if (lastDistance == -1 || lastDistance > distance) {
					guard = npc;
					lastDistance = distance;
				}
			}
		}
		if (guard != null) {
			guard.setNextForceTalk(new ForceTalk(
					"Hey, what do you think you are doing!"));
			guard.setTarget(player);
		}
	}

	public static boolean pickDoor(Player player, WorldObject object) {
		if (player.getTemporaryAttributtes().get("numbFingers") == null)
			player.getTemporaryAttributtes().put("numbFingers", 0);
		int thievingLevel = player.getSkills().getLevel(Skills.THIEVING);
		int increasedChance = getIncreasedChance(player);
		int decreasedChance = (Integer) player.getTemporaryAttributtes().get(
				"numbFingers");
		int level = Utils.getRandom(thievingLevel
				+ (increasedChance - decreasedChance)) + 1;
		double ratio = level / (Utils.getRandom(45 + 5) + 1);
		if (Math.round(ratio * thievingLevel) < (player.getAttackedByDelay() > 0 ? 50
				: 40)
				/ player.getAuraManager().getThievingAccurayMultiplier()) {
			player.getPackets()
					.sendGameMessage(
							"You fail to unlock the door and your hands begin to numb down.");
			player.getTemporaryAttributtes().put("numbFingers",
					decreasedChance + 1);
			return false;
		}
		player.getPackets()
				.sendGameMessage("You successfully unlock the door.");
		ObjectHandler.handleDoor(player, object, 1500 + Utils.getRandom(1000));
		return true;
	}

	private static int getIncreasedChance(Player player) {
		int chance = 0;
		if (Equipment.getItemSlot(Equipment.SLOT_HANDS) == 10075)
			chance += 12;
		player.getEquipment();
		if (Equipment.getItemSlot(Equipment.SLOT_CAPE) == 15349)
			chance += 15;
		return chance;
	}

}
