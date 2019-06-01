package com.rs.game.player;


import java.util.HashMap;
import java.util.Map;

import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.actions.slayer.SlayerTasks;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Slayer.SlayerTask;

/**
 * Handles the teleportation of slayer tablets
 * @author paolo
 *
 */
public class SlayerTabs {
	
	public enum TaksTeleports{
		
		ABYSSAL_DEMONS(SlayerTasks.ABYSSAL_DEMON,  new WorldTile(1645,5303, 0)),
		WATERFIENDS(SlayerTasks.WATERFIEND,  new WorldTile(1741,5344, 0)),
		BLACK_DEMONS(SlayerTasks.BLACK_DEMON,  new WorldTile(2863,9777, 0)),
		BLACK_DRAGONS(SlayerTasks.BLACK_DRAGON,  new WorldTile(2823,9827, 0)),
		BLUE_DRAGONS(SlayerTasks.BLUE_DRAGON,  new WorldTile(1612,5275, 0)),
		DARK_BEASTS(SlayerTasks.DARK_BEAST,  new WorldTile(1650,5291, 0)),
		DUST_DEVIL(SlayerTasks.DUST_DEVIL,  new WorldTile(3234,9365, 0)),
		BLOOD_VELDS(SlayerTasks.BLOODVELD,  new WorldTile(3418, 3562, 1)),
		DAGGANOTHES(SlayerTasks.DAGGANOTHS,  new WorldTile(2443,10146, 0)),
		FIRE_GIANTS(SlayerTasks.FIRE_GIANT,  new WorldTile(1250, 4582, 0)),
		BANSHEE(SlayerTasks.BANSHEES,  new WorldTile(3433,3550,0)),
		BANSHEE2(SlayerTasks.BANSHEES3,  new WorldTile(3433,3550,0)),
		KALPHITE(SlayerTasks.Kalphite2,  new WorldTile(3435,9517,0)),
		ABYSSAL_SPECTERN(SlayerTasks.ABBYSPEC,  new WorldTile(3435, 3549, 1)),
		ABYSSAL_SPECTERN2(SlayerTasks.ABBYSPEC2,  new WorldTile(3435, 3549, 1)),
		DESSERT_STRYKEWYRM(SlayerTasks.DESERT_STRYKEWYRM,  new WorldTile(3373,3160, 0)),
		GARGOYLES(SlayerTasks.GARGOYLES,  new WorldTile(3438,3541, 2)),
		GARGOYLES2(SlayerTasks.Garg,  new WorldTile(3438,3541, 2)),
		GANODERMIC_BEASTS(SlayerTasks.GCreatures2, new WorldTile(4646,5405, 0)),
		GREATER_DEMONS(SlayerTasks.GREATER_DEMON,  new WorldTile(1627,5257, 0)),
		GROTWORMS(SlayerTasks.GROTWORM,  new WorldTile(1184,6500, 0)),
		GRIFALAROOS(SlayerTasks.GRIFALOROO,  new WorldTile(4654,5423, 1)),
		JUNGLE(SlayerTasks.JS,  new WorldTile(2458,2927, 0)),
		INFERNAL_MAGE(SlayerTasks.STEEL_DRAGON,  new WorldTile(3414,3566,1)),
		STEEL_DRAGONS(SlayerTasks.STEEL_DRAGON,  new WorldTile(1625,5275, 0)),
		IRON_DRAGONS(SlayerTasks.IRON_DRAGON,  new WorldTile(1643,5281, 0)),
		MITHRIL_DRAGONS(SlayerTasks.MITHRIL_DRAGON,  new WorldTile(1741,5344, 0)),
		LIVING_ROCK_CREATURES(SlayerTasks.LIVING_ROCK_CREATURE,  new WorldTile(3660,5094, 0)),
		ANKOUS(SlayerTasks.ANKOU,  new WorldTile(2360,5241, 0)),
		ANKOUS2(SlayerTasks.ANKOU2,  new WorldTile(2360,5241, 0)),
		AIRUT(SlayerTasks.AIRUT,  new WorldTile(1654,5307,0)),
		EDIMMU(SlayerTasks.EDIMMU,  new WorldTile(1365,4637,0)),
		SPIRITUAL_MAGES(SlayerTasks.SPIRITUAL_MAGE,  new WorldTile(1365,4637,0)),
		HELL_HOUNDS(SlayerTasks.HELLHOUNDS,  new WorldTile(1644,5255, 0)),
		NECHREALS(SlayerTasks.NECHRYEALS,  new WorldTile(3440,3565, 2)),
		BASILISK(SlayerTasks.Basilisks,  new WorldTile(2745,10000,0)),
		SKELETAL_WYVERNS(SlayerTasks.SKELETAL_WYVERN,  new WorldTile(3044,9557, 0)),
		ICE_STRYKEWYRMS(SlayerTasks.ICE_STRYKEWYRM,  new WorldTile(3423,5669, 0)),
		MUTADED_JADINKOS(SlayerTasks.MUTATED_JADINKO,   new WorldTile(3039,9265, 0));
		
		private static Map<SlayerTasks, TaksTeleports> taskTeleports = new HashMap<SlayerTasks, TaksTeleports>();

		static {
			for (TaksTeleports taskTeleport : TaksTeleports.values()) {
				taskTeleports.put(taskTeleport.getTask(), taskTeleport);
			}
		}

		public static TaksTeleports forTask(SlayerTasks task) {
			return taskTeleports.get(task);
		}
		
		private SlayerTasks task;
		private WorldTile teleTile;
		
		private TaksTeleports(SlayerTasks task, WorldTile tile){
			this.task = (task);
			this.teleTile = tile;
		}
		
		public WorldTile getTeleTile(){
			return teleTile;
		}

		/**
		 * @return the task
		 */
		public SlayerTasks getTask() {
			return task;
		}
		/**
		 * Actual teleport
		 * todo remove item
		 * @param player
		 */
		public static void handleTeleport(Player player,Item item){
			if(player.currentSlayerTask.getTask() == null){
				player.sm("You need a slayer task first before you can teleport");
				return;
			}
			final TaksTeleports teletab = forTask(player.currentSlayerTask.getTask());
			if(teletab != null){
				Magic.sendCustomTeleportSpell3(player, 0, 0, teletab.getTeleTile());
				player.getInventory().deleteItem(item);
			}else 
				player.sm("Report this to an admin or the forum, he will add a teleport for this creature as soon as possible.");
		}
	}
}
