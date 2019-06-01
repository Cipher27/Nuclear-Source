package com.rs.game.player.content;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

/**
 * 
 * @author Paolo
 * needs to be tested 
 */

public class HolidayEvents {
	
	static int LOVENOTE = 30831;
	
	final int [] VALENTINE = {30831,30832,30839,30840,30841,30847,29642,29643};
	final static int [] CHRISTMAS = {29641, 26493, 26518, 26517,30412,14600,14602,14603,14605, 1051,11949,10501};
    final int [] HALLOWEEN = {29640};
	final static int [] PRESENTS = {26485,26486,26487,26488,26489};
	
	//rewards items
	public static boolean isHolidayItem(int id){
		switch(id){
			//xmas
			case 29641:
			case 26518:
			case 26517:
			case 26493:
			case 30412://black santa
			case 14600:
			case 14602:
			case 14603:
			case 14605:
			case 1051:
			case 11949:
			case 10501: //snowball
			//halloween
			case 29640:
			//valentine
			case 30831:
			case 30832:
			case 30839:
			case 30840:
			case 30841:
			case 30847:
			case 29642:
			case 29643:
			return true;
		}
		return false;
	}
	
	/**
	 * spawns the presents around zaria
	 */
	
	public static void spawnChristmasPresents(){
		World.sendWorldMessage("New presents have been spawned in Hellion, happy christmas!", false);
		//home
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 2351, 3696, 0), 150000, true);
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 2324, 3687, 0), 150000, true);
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 2313, 3677, 0), 150000, true);
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 2311, 3701, 0), 150000, true);
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 2351, 3696, 0), 150000, true);
		//gnome agility
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 2475, 3418, 2), 150000, true);
		//kuradel dungeon
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 1741, 5309, 1), 150000, true);
		//lrc
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 3649, 5092, 0), 150000, true);
		//dungeoneering
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 3446, 3715, 0), 150000, true);
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 3457, 3718, 1), 150000, true);
		//abys
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 3043, 4845, 0), 150000, true);
		//magic trees 
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 2701, 3388, 0), 150000, true);
		//Ardougne marked
		World.spawnTemporaryObject(new WorldObject(88921, 10, 0, 2663, 3309, 0), 150000, true);
	}
	
	/**
	 * 
	 * @param player
	 * @param object
	 */
	public static void openChristmasPresents(Player player, WorldObject object){
		player.getInventory().addItem(Utils.random(PRESENTS.length),1);
		player.getInventory().addItem(Utils.random(PRESENTS.length),1);
		player.getInventory().addItem(Utils.random(PRESENTS.length),1);
		World.spawnTemporaryObject(new WorldObject(123, 10, 0, object.getX(), object.getY(), object.getPlane()), 150000, true);
		player.sm("You have received 3 small presents from these bigger presents, you can combine these presents for a Christmas reward.");
	}
	/**
	 * handles the opening of the presents, atm only a lamp and cosmetic items
	 * @param item
	 * @param player
	 */
	public static void handlePresents(Item item, Player player){
		int id = item.getId();
		if (player.getInventory().containsItem(id, 5) && player.getInventory().hasFreeSlots()){
		player.getInventory().deleteItem(id,5);
		player.getInventory().addItem(Utils.random(CHRISTMAS.length),1);
		player.getInventory().deleteItem(id,5);
		player.getInventory().addItem(30327,1);
		player.getInventory().addItem(744,100);
		player.sm("You openend your present.");
		} else if (!player.getInventory().hasFreeSlots()){
		 player.sm("You need free alteast 3 free inventory spaces.");
		 } else {
			 player.sm("You need 5 presents to claim your rewards."); 
		 }
	}
	/**
	 *  handles items
	 * @param player
	 * @param item
	 */
	public static void HandleItem(Player player, final Item item) {
		final int id = item.getId();
		switch(id){
		case 30831:
         sendLoveNote(player);
		case 26485:
		case 26486:
		case 26487:
		case 26488:
		case 26489:
	    handlePresents(item, player);
		}
	}
	/**
	 * handles objects
	 * @param player
	 * @param object
	 */
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		switch(id){
		case 88921:
	     openChristmasPresents(player, object);		
		}
	}
	
	public static void sendLoveNote(Player player){
	  player.getAttributes().put("lovenote_player", true);
	  player.getPackets().sendInputLongTextScript("Enter the name of the player who you like to send a special note.");	
	}
	
	/**
	 * sends the massge to the lover
	 * @param player
	 * @param message
	 * @param name
	 */
	public static void receiveLoveNote(Player player, String message, String name){
		Player lover = World.getPlayerByDisplayName(name);
		player.sm("Your note has been send to "+name);
		player.getInventory().deleteItem(LOVENOTE,1);
		lover.sm(player.getDisplayName()+" has send you a love note with the following message:<col=FFFFF> "+message);
		if (lover.getDisplayName().equals(player.getDisplayName())){
			player.sm("Its about giving and taking, you won't receive money when you send this letter 2 yourself.");
		} else {
			lover.getInventory().addItem(995, 100000);
			lover.sm("You also got a valentine gift of 100k coins.");
		}
	}
}
