package com.rs.game.player.content;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.actions.divination.DivinePlacing;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;



/**
 * 
 * @author Paolo
 *credits  to "Geelong  Cats" for the idea.
 *
 */

public class MasterCapePerks {
     

    /**
     * Checks if the item is an master cape
     * @return 
     **/	 
	public static boolean isMasterCape(int id) {
		if (id >= 31268 && id <= 31292) //way shorter
			return true;
		return false;
	}
	/* Does the perk
	**/
	public static void handlePerk(int id, final Player player) {
		if (id == 31277){ //agility
			player.setRunEnergy(100);
		}else if (id == 31272){ //prayer
		if (player.prayerMasterCape >0) {
			player.getPrayer().restorePrayer(990);
			player.prayerMasterCape--;
			player.sm("Your cape recharged your prayer points. You have "+player.prayerMasterCape+" charges left today.");
		} else 
			player.sm("You can only use this 5 times a day.");
		}else if (id == 31276){ //hp
		 if (player.hpMasterCape >0) {
			player.hpMasterCape--;
			player.heal(990);
			player.sm("You can use this "+player.hpMasterCape+" more times today.");
		 } else if (player.hpMasterCape <= 0){
			 player.sm("You can only us this ability 10 times day.");
		 }
		}else if (id == 31275){ //construction
		player.getDialogueManager().startDialogue("HousePortal");
		}else if (id == 31282){ //slayer
		 if (player.hasTask == false) {
		player.sm("you don't have an active task.");
		 }
		}else if (id == 31274){ //rc
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3039,4834, 0));
		player.getControlerManager().startControler("Abyss");
		}else if (id == 31288){ // cook
		 if (player.cookMasterCape >0) {
			player.cookMasterCape = player.cookMasterCape -1;
		WorldTasksManager.schedule(new WorldTask() {
			    int ticks;
			    @Override
			    public void run() {
				ticks++;
			if(ticks == 1){
				player.getInventory().deleteItem(31042, 1);
				final WorldObject portablerange = new WorldObject(89768,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
				player.faceObject(portablerange); // forces player to face where he is putting the object
				World.spawnTemporaryObject(portablerange, 60000); // dont touch
				stop();
			}
			    return;
			    }  
		}, 0, 0);
			player.sm("You can use this "+player.cookMasterCape+" more times today.");
		 }  if (player.cookMasterCape <= 0){
			 player.sm("You can only us this ability 4 times day.");
		 }
		}else if (id == 31286){ // smith
		 if (player.smithMasterCape >0) {
			player.smithMasterCape = player.smithMasterCape -1;
		WorldTasksManager.schedule(new WorldTask() {
			    int ticks;
			    @Override
			    public void run() {
				ticks++;
			if(ticks == 1){
				player.getInventory().deleteItem(31042, 1);
				final WorldObject portablerange = new WorldObject(89767,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
				player.faceObject(portablerange); // forces player to face where he is putting the object
				World.spawnTemporaryObject(portablerange, 60000); // dont touch
				stop();
			}
			    return;
			    }  
		}, 0, 0);
			player.sm("You can use this "+player.smithMasterCape+" more times today.");
		 }  if (player.smithMasterCape <= 0){
			 player.sm("You can only us this ability 4 times day.");
		 }
		}else if (id == 31287){ //fishing
		if (player.fishMasterCape >0) {
			DivinePlacing.placeDivine(player, 31088, 90240, 90231, 90, 10);
			player.fishMasterCape--;
			player.sm("You can use this "+player.fishMasterCape+" more times today.");
		} else if (player.fishMasterCape <= 0)  {
			player.sm("You can only use this 4 times a day.");
		}
		}else if (id == 31285){ //mining
		if (player.mineMasterCape >0) {
			DivinePlacing.placeDivine(player, 29299, 87290, 87269, 85, 14);
			player.mineMasterCape = player.mineMasterCape -1;
			player.sm("You can use this "+player.mineMasterCape+" more times today.");
		} else if (player.mineMasterCape <= 0)  {
			player.sm("You can only use this 4 times a day.");
		}
		}else if (id == 31290){ //woodcutting
		if (player.wcMasterCape > 0) {
			DivinePlacing.placeDivine(player, 29309, 87300, 87279, 75, 8);
			player.wcMasterCape = player.wcMasterCape -1;
			player.sm("You can use this "+player.wcMasterCape+" more times today.");
		} else if (player.wcMasterCape <= 0)  {
			player.sm("You can only use this 4 times a day.");
		}
		}else if (id == 31270){ //defence
		player.sm("This cape is works as a ring of life, this does not work in pvp.");
		}else if (id == 19709){ //dungeoneering
		player.sm("When you wear this cape while killing monsters in domhein you get double the amout of tokens.");
		}else if (id == 31268){ //attack
		player.sm("While wearing this cape your minimum hit is set to 10.");
		}else if (id == 31269){ //str
		player.sm("While wearing this cape your maximum hit is increased bij 10%.");
		}else if (id == 31279){ //thieving
		player.sm("While thieving your chance of failing is reduced by 25%");
		}else if (id == 31289){ //firemaking
		player.sm("While wearing this perk you have a 0.5 chance of burning your whole inventory with each log you burn at a bonfire.");
		}else if (id == 31291){ //farming
		player.sm("Where this cape while harvesting will grand you noted items.");
		}else if (id == 31283){ //hunter
		player.sm("Wearing this cape while setting up a box trap will increase it's time before collapse 10 times.");
		}else {
			player.sm("This cape has no perk yet, if you have a good idea suggest it to Paolo!");
		}
	}
}
