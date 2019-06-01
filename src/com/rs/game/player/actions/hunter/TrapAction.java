package com.rs.game.player.actions.hunter;

import com.rs.game.Animation;
import com.rs.game.Region;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.impl.TrapSpecialistAchievement;
import com.rs.game.player.actions.Action;
import com.rs.game.player.content.Burying;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;
/**
 * 
 * @author def need to write this. done so badly , no information, etc
 *
 */
public class TrapAction extends Action {

	public enum Traps {

		/* itemid, objectid, fail obj id, set emote, remove emote */
		BOX(new int[] { 10008, 19187, 19192, 5208, 5208 }, 27),
		
	    MARASAMAW_PLANT(new int[] { 19965, 56806, 56818, 5208, 5208 }, 27),
		
		SNARE(new int[] { 10006, 19175, 19174, 5208, 5207 }, 1),

		BOULDER_TRAP(new int[] { 19205, 19206, 19219, 1511, 1, 5208, 5208 }, 23);


		private final int[] ids;
		private int requirementLevel;

		private Traps(int[] ids, int requirementLevel) {
			this.ids = ids;
			this.requirementLevel = requirementLevel;
		}

		public int[] getIds() {
			return ids;
		}

		public int getRequirementLevel() {
			return requirementLevel;
		}

		public boolean isItem() {
			return ids.length == 5;
		}
	}

	public enum HunterNPC {
    //catch id, npc trap emote
		BARB_TAILED_KEBBIT(Traps.BOULDER_TRAP, 23, 168, 19215,new int[] {  5275, 5277}, new Item[] {new Item(526,1), new Item(10129)}),

		/**
		 * boxes
		 */
		GREY_CHINCHOMPA(Traps.BOX, 53, 198.4, 28557 ,new int[] {  5184, -1 }, new Item[] {new Item(10033)}),

		RED_CHINCHOMPA(Traps.BOX, 63, 265, 28558,new int[] {  5184, -1}, new Item[] {new Item(10034)}),
		
		GRENWALL(Traps.BOX, 77, 500, 28567,new int[] { 5184, -1}, new Item[] {new Item(12539, Utils.random(7, 11)), new Item(526,1)}),
		
		PAWYA(Traps.BOX, 77, 350, 28906,new int[] { 5184, -1}, new Item[] {new Item(12535, 1), new Item(526,1)}),

		FERRET(Traps.BOX, 27, 115, 19189,new int[] {  5191, 5192 }, new Item[] {}), //Todo

		GECKO(Traps.BOX, 27, 100, 19190,new int[] {  8362, 8361 }, new Item[] {}), //todo

		RACCOON(Traps.BOX, 27, 100, 19191,new int[] {  7726, 7727 }, new Item[] {}), //todo

		MONKEY(Traps.BOX, 27, 100, 28557,new int[] {  8343, 8345 }, new Item[] {}), //todo
		
		/**
		 * birds
		 */

		CRIMSON_SWIFT(Traps.SNARE, 1, 34, 19180,new int[] {  5171, 5172}, new Item[] {new Item(10088), new Item(526), new Item(9978)}),

		GOLDEN_WARBLER(Traps.SNARE, 5, 48, 19184,new int[] {  5171, 5172}, new Item[] {new Item(1583), new Item(526), new Item(9978)}),

		COPPER_LONGTAIL(Traps.SNARE, 9, 61, 19186,new int[] {5171, 5172}, new Item[] {new Item(10091), new Item(526), new Item(9978)}),

		CERULEAN_TWITCH(Traps.SNARE, 11, 64.67, 19182,new int[] { 5171, 5172}, new Item[] {new Item(10089), new Item(526), new Item(9978)}),

		TROPICAL_WAGTAIL(Traps.SNARE, 19, 95.2, 19178,new int[] { 5171, 5172}, new Item[] {new Item(10087), new Item(526), new Item(9978)}),

		WIMPY_BIRD(Traps.SNARE, 39, 167,28930, new int[] { 5171, 5172}, new Item[] {new Item(11525), new Item(526), new Item(9978)}),
		
		/**
		 * plants
		 */
		COMMON_JADINKO(Traps.MARASAMAW_PLANT, 70,350,56819, new int[] {-1,-1}, new Item[] {}),
		
		INGEOUS_JADINKO(Traps.MARASAMAW_PLANT, 74,465,56820, new int[] {-1,-1}, new Item[] {}),
		
		CANNIBAL_JADINKO(Traps.MARASAMAW_PLANT, 75,475,56821, new int[] {-1,-1}, new Item[] {}),
		
		AQUATIC_JADINKP(Traps.MARASAMAW_PLANT, 76,475,56822, new int[] {-1,-1}, new Item[] {}),
		
		AMPHIBIOUS_JADINKP(Traps.MARASAMAW_PLANT, 77,485,56823, new int[] {-1,-1}, new Item[] {}),
		
		CARRION_JADINKP(Traps.MARASAMAW_PLANT, 78,505,56824, new int[] {-1,-1}, new Item[] {}),
		
		DRACONIC_JADINKP(Traps.MARASAMAW_PLANT, 80,525,56825, new int[] {-1,-1}, new Item[] {});
		
		private final Traps trap;
		private final int lureLevel,objectSucces;
		private final double exp;
		private final int[] ids; // npc sit animation, npc failed animation
		private final Item[] loot;

		private HunterNPC(Traps trap, int lureLevel, double exp, int succes,int[] ids, Item[] loot) {
			this.trap = trap;
			this.lureLevel = lureLevel;
			this.objectSucces = succes;
			this.exp = exp;
			this.ids = ids;
			this.loot = loot;
		}

		public Traps getTrap() {
			return trap;
		}

		public int getLureLevel() {
			return lureLevel;
		}

		public double getExp() {
			return exp;
		}

		public int[] getIds() {
			return ids;
		}
		
		public int getSuccesId(){
			return objectSucces;
		}
	}

	private Traps trap;
	private WorldTile tile;

	public TrapAction(Traps trap, WorldTile tile) {
		this.trap = trap;
		this.tile = tile;
	}

	@Override
	public boolean start(Player player) {
		boolean is_item = trap.isItem();
		int levelRequirement = trap.getRequirementLevel(), currentLevel = player.getSkills().getLevel(Skills.HUNTER);
		if (currentLevel < levelRequirement) {
			player.getPackets().sendGameMessage(
					"You need a Hunter level of " + levelRequirement + " in order to place this trap.");
			return false;
		} else {
			if (is_item) {
				if (World.getObjectWithSlot(tile, Region.OBJECT_SLOT_FLOOR) != null) {
					player.getPackets().sendGameMessage("You cannot place a trap here!");
					return false;
				}
			} else {
				int[] ids = trap.getIds();
				Item item = new Item(ids[3], ids[4]);
				if (!player.getInventory().containsItem(item.getId(), item.getAmount())) {
					player.getPackets().sendGameMessage("You don't have the neccessary supplies to place this trap.");
					return false;
				}
			}
			int maxAmount = (player.getPerkHandler().perks.contains(Perk.HUNTER_PERK) ? getMaximumTrap(trap, currentLevel) +2  : getMaximumTrap(trap, currentLevel)) ;
			if (getTrapsCount(player, is_item) == maxAmount) {
				player.getPackets().sendGameMessage("You cannot place more than " + maxAmount + " traps at once.");
				return false;
			}
		}
		player.lock(3);
		player.setNextAnimation(new Animation(trap.getIds()[is_item ? 3 : 5]));
		player.getPackets().sendGameMessage("You begin setting up the trap.", true);
		if (is_item)
			World.addGroundItem(new Item(trap.getIds()[0], 1), tile, player, true, 180);
		player.getInventory().deleteItem(is_item ? trap.getIds()[0] : trap.getIds()[3], is_item ? 1 : trap.getIds()[4]);
		setActionDelay(player, 4);
		return true;
	}

	@Override
	public boolean process(Player player) {
		return true;
	}

	@Override
	public int processWithDelay(Player player) {
		boolean is_item = trap.isItem();
		int[] ids = trap.getIds();
		if (is_item) {
			if (!player.addWalkSteps(player.getX() - 1, player.getY(), 1))
				if (!player.addWalkSteps(player.getX() + 1, player.getY(), 1))
					if (!player.addWalkSteps(player.getX(), player.getY() + 1, 1))
						player.addWalkSteps(player.getX(), player.getY() - 1, 1);
			final FloorItem item = World.getRegion(tile.getRegionId()).getGroundItem(ids[0], tile, player);
			if (item == null)
				return -1;
			else if (!World.removeGroundItem(player, item, false))
				return -1;
		}
		int time = player.getEquipment().getCapeId() == 31283 ? 6000000 : 600000;
		OwnedObjectManager.addOwnedObjectManager(player, new WorldObject[] { new WorldObject(ids[1], 10, 0, player.getX(), player.getY(), player.getPlane()) }, time);
		return -1;
	}

	@Override
	public void stop(Player player) {
		setActionDelay(player, 3);
	}

	private static int getMaximumTrap(Traps trap, int currentLevel) {
		
		if (trap.isItem())
			return 1 + (currentLevel / 20) ;
		return 3;
	}

	public static boolean isTrap(Player player, WorldTile tile, int id) {
		for (Traps trap : Traps.values()) {
			if (trap.getIds()[0] != id)
				continue;
			player.getActionManager().setAction(new TrapAction(trap, tile));
			return true;
		}
		return false;
	}

	public static boolean isTrap(Player player, WorldObject o) {
		Traps trap = null;
		for (Traps t : Traps.values()) {
			if ((t.isItem() && (o.getId() == t.getIds()[1] || o.getId() == t.getIds()[2]))
					|| (!t.isItem() && (o.getId() == t.getIds()[2] || o.getId() == t.getIds()[1]))) {
				trap = t;
				break;
			}
		}
		HunterNPC captured = null;
		if (trap == null) {
			for (HunterNPC npc : HunterNPC.values()) {
				if (o.getId() == npc.getSuccesId()) {
					captured = npc;
					trap = captured.trap;
					break;
				}
			}
		}
		if (trap == null)
			return false;
		else if (!OwnedObjectManager.isPlayerObject(player, o)) {
			player.getPackets().sendGameMessage("This isn't your trap!");
			return true;
		}
		sendTrapAction(player, o, trap, captured);
		return true;
	}

	private static void sendTrapAction(final Player player, final WorldObject o, final Traps trap,
			final HunterNPC captured) {
		if (player.isLocked())
			return;
	    int amount = player.getEquipment().getWeaponId() == 19808 ? 2 :  player.getEquipment().getWeaponId() == 28084 ? 3 : 1;
		player.lock(3);
		player.trapCatches++;
		player.getAchievementManager().notifyUpdate(TrapSpecialistAchievement.class);
		player.setNextAnimation(new Animation(trap.getIds()[trap.isItem() ? 4 : 6]));
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				if (trap.isItem())
					player.getInventory().addItemDrop(trap.getIds()[0], 1);
				else
					World.spawnObject(new WorldObject(trap.getIds()[0], o.getType(), o.getRotation(),
							new WorldTile(o.getTileHash())));
				if (captured != null) {
					if(captured == HunterNPC.DRACONIC_JADINKP){
						int random = Utils.random(80);
						if(random == 0){
						player.getInventory().addItem(new Item(28267));
						player.sm("The jadinko had a Wishin well seed on him. ");
						}
					} else if(captured == HunterNPC.COMMON_JADINKO){
						int random = Utils.random(150);
						if(random == 0){
						player.getInventory().addItem(new Item(28267));
						player.sm("The jadinko had a Wishin well seed on him. ");
						}
					}
						
					for (int i = 0; i < captured.loot.length; i++){
					
						if (captured.loot[i].getName().toLowerCase().contains("bones")
								&& player.getInventory().containsItem(18337, 1)) {
								player.getSkills().addXp(Skills.PRAYER,Burying.Bone.forId(captured.loot[1].getId()).getExperience());
						} else
						player.getInventory().addItemDrop(captured.loot[i].getId(),captured.loot[i].getAmount());
					}
					player.getSkills().addXp(Skills.HUNTER, captured.getExp());
				}
				OwnedObjectManager.removeObject(player, o);
			}
		}, 1);
	}

	private static int getTrapsCount(Player player, boolean item) {
		int trapsCount = 0;
		for (Traps t : Traps.values()) {
			if (t.isItem() != item)
				continue;
			if (item) {
				trapsCount += OwnedObjectManager.getObjectsforValue(player, t.getIds()[1]);
				trapsCount += OwnedObjectManager.getObjectsforValue(player, t.getIds()[2]);
			} else
				trapsCount += OwnedObjectManager.getObjectsforValue(player, t.getIds()[0]);
		}
		for (HunterNPC npc : HunterNPC.values()) {
			if (npc.getTrap().isItem() != item)
				continue;
			trapsCount += OwnedObjectManager.getObjectsforValue(player, npc.getSuccesId());
			//trapsCount += OwnedObjectManager.getObjectsforValue(player, npc.getIds()[1]);
			//trapsCount += OwnedObjectManager.getObjectsforValue(player, npc.getIds()[2]);
		}
		return trapsCount;
	}
}