package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.SlayerTabs.TaksTeleports;
import com.rs.game.player.actions.slayer.SlayerTasks;
import com.rs.utils.Utils;

/**
 * 
 * @author paolo
 *
 */

public class SlayerMasks {
	

	/**
	  * enum of all the masks and their tasks
	  **/
	
	public enum Masks{
		
		ABYSSAL(27624,27625, SlayerTasks.ABYSSAL_DEMON),
		AIRUT(35285, 35286,SlayerTasks.AIRUT),
		JELLY(28688, 28689,SlayerTasks.JELLY),
		DAGANNOTH(35277, 35278,SlayerTasks.DAGGANOTHS),
		BLOODVELD(31089, 31090,SlayerTasks.BLOODVELD),
		GARGOYLE(28692, 28693,SlayerTasks.GARGOYLES),
		BANSHEE(28686, 28687,SlayerTasks.BANSHEES),
		AUTOMATOMS(35283, 35284,SlayerTasks.AUTOMATOM),
		DEMONS(35287, 35288,SlayerTasks.BLACK_DEMON),
		GANO(35281, 35282,SlayerTasks.GCreatures2),
		DUST_DEVIL(28690, 28691,SlayerTasks.DUST_DEVIL),
		ABERRANT_SPECTRES(31091, 31092,SlayerTasks.ABBYSPEC),
		JADINKO(28694, 28694,SlayerTasks.MUTATED_JADINKO),
		BLACK_DEMON(35287, 35288,SlayerTasks.BLACK_DEMON),
		CRAWLING(27616, 27617,SlayerTasks.CRAWLING_HANDS),
		JUNGLE_STRYKE(31093, 31094,SlayerTasks.JS),
		DESERT_STRYKE(31095, 31096,SlayerTasks.DESERT_STRYKEWYRM),
		ICE_STRYKE(31097, 31098,SlayerTasks.ICE_STRYKEWYRM),
		GANODERMIC(35281, 35282,SlayerTasks.GCreatures2),
		KURA(27622, 27623,SlayerTasks.ABBYSPEC2), //TODO
		DARKB(31099, 31100,SlayerTasks.DARK_BEAST);
		
		
		public static Masks forId(int itemId) {
			for(Masks mask : Masks.values()){
				if(itemId == mask.maskId)
					return mask;
			}
			return null;
		}
		
		public static Masks hasMask(int itemId) {
			for(Masks mask : Masks.values()){
				if(itemId == mask.maskId || itemId == mask.helmId)
					return mask;
			}
			return null;
		}
		
		public static Masks forHelm(int itemId) {
			for(Masks mask : Masks.values()){
				if(itemId == mask.helmId)
					return mask;
			}
			return null;
		}
		
		int maskId, helmId;
		public SlayerTasks task;
		/**
		 * returns the item id of the mask
		 * @return
		 */
		public int getMaskId(){
			return maskId;
		}
		
		public int getHelmId(){
			return helmId;
		}
		
		Masks(int id,int helm ,SlayerTasks task){
			this.maskId = id;
			this.helmId = helm;
			this.task = task;
		}
	}
	
    /**
	  * assigns a slayer task which is bounded to the masks
	  **/
	public static void assignTask(Masks mask, Player player){
		if(player.slayerTasksGiven.contains(mask))
			return;
		player.currentSlayerTask.setTask(mask.task);
		player.currentSlayerTask.setMonstersLeft(Utils.random(player.currentSlayerTask.getTask().min,player.currentSlayerTask.getTask().max));
		player.hasTask = true;
		
	}
	
	/**
	  * Used for the teleport option of the mask
	  **/
	public static boolean handleTeleport(Masks mask, Player player){
		int amount = 0;
		for(Masks mask2 : player.slayerMasksTeleports){
			if(mask2 == mask)
				amount ++;
		}
		if(amount >= 2 ){//only 2 teleports a day
			player.sm("You have used your daily teleport.");
			return false;
		}
		final TaksTeleports tele= TaksTeleports.forTask(mask.task);
		if(tele != null){
			player.slayerMasksTeleports.add(mask);
			Magic.sendCustomTeleportSpell3(player, 0, 0, tele.getTeleTile());
			return true;
		}else // if the mask has no teleport :p
			player.sm("Report this to paolo or the forum, he will add a teleport for this creature as soon as possible.");
		return false;
	}
	/**
	 * todo
	 * @return
	 */
	public static String getKillsDone(Masks mask, Player player){
		int amount = (player.getSlayerMaskCreaturesCount().containsKey(mask.task.simpleName) ?player.getSlayerMaskCreaturesCount().get(mask.task.simpleName) : 0 );
		return "I've killed "+amount+" "+mask.task.simpleName+".";
	}
	
	/**
	  * converts the mask into his upgraded version (equipment spot)
	  **/
	public static boolean transform(Player player, Masks mask,boolean inventory){
//		/Masks mask = Masks.forId(player.getEquipment().getHatId());
		int amount = (player.getSlayerCreaturesCount().containsKey(mask.task.simpleName) ?player.getSlayerCreaturesCount().get(mask.task.simpleName) : 0 );
		if(amount >= 500){
			if(inventory){
				player.getInventory().deleteItem(new Item(mask.maskId));
				player.getInventory().addItem(new Item(mask.helmId));
			} else {
			player.getEquipment().deleteItem(new Item(mask.maskId,1));
			player.getEquipment().getItems().set(Equipment.SLOT_HAT, new Item(mask.helmId,1));
			player.getEquipment().refresh(Equipment.SLOT_HAT);
			player.getAppearence().generateAppearenceData();
			}
		} else {
			player.sm("You need "+(500-amount)+" more to upgrade this mask.");
		}
		return false;
	}
	
	public static boolean transformHelm(Player player, Masks mask, boolean inventory){
		int amount = (player.getSlayerCreaturesCount().containsKey(mask.task.simpleName) ?player.getSlayerCreaturesCount().get(mask.task.simpleName) : 0 );
		if(amount >= 500){
			if(inventory){
				player.getInventory().deleteItem(new Item(mask.helmId));
				player.getInventory().addItem(new Item(mask.maskId));
			} else {
			player.getEquipment().deleteItem(new Item(mask.helmId,1));
			player.getEquipment().getItems().set(Equipment.SLOT_HAT, new Item(mask.maskId,1));
			player.getEquipment().refresh(Equipment.SLOT_HAT);
			player.getAppearence().generateAppearenceData();
			}
		} else {
			player.sm("You need "+(500-amount)+" more to upgrade this mask.");
		}
		return false;
	}
	
	/**
	  * gets the name of creature
	  **/
	public static String getNpcName(Masks mask){
		return mask.task.simpleName;
	    
	}
	
}
