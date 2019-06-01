package com.rs.game.player.actions.hunter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;


public class NetTrapAction extends Action {

	public enum HunterNPC {

		PET_SQUIRREL(15602, new Item[] { new Item(29931, 5) }, 29, 152,
				HunterEquipment.NET_TRAP, 28567, 19192, new Animation(5191),
				new Animation(5192)),

		PENGUIN(15602, new Item[] { new Item(29931, 5) }, 50, 250,
				HunterEquipment.NET_TRAP, 28567, 19192, new Animation(5191),
				new Animation(5192)),

		SWAMP_LIZARD(5117, new Item[] { new Item(10149) }, 29, 152,
				HunterEquipment.NET_TRAP, 19675, 19677, new Animation(-1),
				new Animation(-1)),

		ORANGE_SALAMANDER(5114, new Item[] { new Item(10146) }, 47, 224,
				HunterEquipment.NET_TRAP_ORANGE, 19654, 19656,
				new Animation(-1), new Animation(-1)),

		RED_SALAMANDER(5115, new Item[] { new Item(10147) }, 59, 272,
				HunterEquipment.NET_TRAP_RED, 19659, 19661, new Animation(-1),
				new Animation(-1)),

		BLACK_SALAMANDER(5116, new Item[] { new Item(10148) }, 67, 304,
				HunterEquipment.NET_TRAP_BLACK, 19667, 19669,
				new Animation(-1), new Animation(-1));

		private int npcId, level, successfulTransformObjectId,
				failedTransformObjectId;
		private Item[] item;
		private double xp;
		private HunterEquipment hunter;
		private Animation successCatchAnim, failCatchAnim;

		static final Map<Integer, HunterNPC> npc = new HashMap<Integer, HunterNPC>();
		static final Map<Integer, HunterNPC> object = new HashMap<Integer, HunterNPC>();

		public static HunterNPC forId(int id) {
			return npc.get(id);
		}

		static {
			for (HunterNPC npcs : HunterNPC.values())
				npc.put(npcs.npcId, npcs);
			for (HunterNPC objets : HunterNPC.values())
				object.put(objets.successfulTransformObjectId, objets);
		}

		public static HunterNPC forObjectId(int id) {
			return object.get(id);
		}

		HunterNPC(int npcId, Item[] item, int level, double xp,
				  HunterEquipment hunter,
				  int successfulTransformObjectId,
				  int failedTransformObjectId, Animation successCatchAnim,
				  Animation failCatchAnim) {
			this.npcId = npcId;
			this.item = item;
			this.level = level;
			this.xp = xp;
			this.hunter = hunter;
			this.successfulTransformObjectId = successfulTransformObjectId;
			this.failedTransformObjectId = failedTransformObjectId;
			this.successCatchAnim = successCatchAnim;
			this.failCatchAnim = failCatchAnim;
		}

		public int getLevel() {
			return level;
		}

		public int getNpcId() {
			return npcId;
		}

		public double getXp() {
			return xp;
		}

		public Item[] getItems() {
			return item;
		}

		public HunterEquipment getEquipment() {
			return hunter;
		}

		public int getSuccessfulTransformObjectId() {
			return successfulTransformObjectId;
		}

		public int getFailedTransformObjectId() {
			return failedTransformObjectId;
		}

		public HunterEquipment getHunter() {
			return hunter;
		}

		public Animation getSuccessCatchAnim() {
			return successCatchAnim;
		}

		public Animation getFailCatchAnim() {
			return failCatchAnim;
		}
	}

	public enum HunterEquipment {
		
		NET_TRAP(new int[] {303, 954}, 19673,new Animation(5208), 27),
		NET_TRAP_ORANGE(new int[] {303, 954}, 19651,new Animation(5208), 47),
		NET_TRAP_BLACK(new int[] {303, 954}, 19665,new Animation(5208), 67), 
		NET_TRAP_RED(new int[] {303, 954}, 19681,new Animation(5208), 59);

		private int[] itemIds;
		private int replaceObjectId, baseLevel;
		private Animation pickUpAnimation;
		
		HunterEquipment(int[] itemIds, int replaceObjectId,Animation pickUpAnimation, int baseLevel) {
			this.itemIds = itemIds;
			this.replaceObjectId = replaceObjectId;
			this.pickUpAnimation = pickUpAnimation;
			this.baseLevel = baseLevel;
		}

		public int getId() {
			return itemIds[0];
		}
		
		public int[] getIds() {
			return itemIds;
		}

		public int getObjectId() {
			return replaceObjectId;
		}

		public Animation getPickUpAnimation() {
			return pickUpAnimation;
		}

		public int getBaseLevel() {
			return baseLevel;
		}
	}

	private HunterEquipment hunt;

	public int getTrapAmount(Player player) {
		int level = 20;
		int trapAmount = 2;
		for (int i = 0; i < 3; i++) {
			if (player.getSkills().getLevel(Skills.HUNTER) >= level) {
				trapAmount++;
				level += 20;
			}
		}
		return trapAmount;
	}

	public NetTrapAction(HunterEquipment hunt) {
		this.hunt = hunt;
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		player.sm("You start setting up the trap..");
		player.setNextAnimation(new Animation(5208));
		player.lock(3);
		setActionDelay(player, 2);
		return true;
	}

	@Override
	public boolean process(Player player) {
		return true;
	}

	@Override
	public int processWithDelay(Player player) {
		if (!player.addWalkSteps(player.getX() - 1, player.getY(), 1))
			if (!player.addWalkSteps(player.getX() + 1, player.getY(), 1))
				if (!player.addWalkSteps(player.getX(), player.getY() + 1, 1))
					player.addWalkSteps(player.getX(), player.getY() - 1, 1);
		for(int itemId : hunt.getIds())
			player.getInventory().deleteItem(itemId, 1);
		OwnedObjectManager.addOwnedObjectManager(player,
				new WorldObject[] { new WorldObject(hunt.getObjectId(), 10, 0,
						player.getX(), player.getY(), player.getPlane()) },
				600000);
		return -1;
	}

	@Override
	public void stop(final Player player) {
		setActionDelay(player, 3);
	}

	private boolean checkAll(Player player) {
		if (player.getSkills().getLevel(Skills.HUNTER) < hunt.getBaseLevel()) {
			player.getDialogueManager().startDialogue(
					"SimpleMessage",
					"You need a Hunter level of " + hunt.getBaseLevel()
							+ " to use this.");
			return false;
		}
		int trapAmt = getTrapAmount(player);
	/*	if (OwnedObjectManager.getObjectsforValue(player, hunt.getPlaceObjectId()) == trapAmt) {
			player.sm("You can't setup more than " + trapAmt + " traps.");
			return false;
		}*/
		if (!World.canMoveNPC(0, player.getX(), player.getY(),
				player.getPlane())) {
			player.sm("You can't setup your trap here.");
			return false;
		}
		List<WorldObject> objects = World.getRegion(player.getRegionId())
				.getSpawnedObjects();
		if (objects != null) {
			for (WorldObject object : objects) {
				if (object.getX() == player.getX()
						&& object.getY() == player.getY()
						&& object.getPlane() == player.getPlane()) {
					player.sm("You can't setup your trap here.");
					return false;
				}
			}
		}
		return true;
	}

}