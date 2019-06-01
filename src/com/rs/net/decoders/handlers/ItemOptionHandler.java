package com.rs.net.decoders.handlers;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.MasterCapePerks;
import com.rs.game.player.content.SlayerMasks;
import com.rs.game.player.content.SlayerMasks.Masks;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.PestInvasion;
import com.rs.game.player.controlers.events.DeathEvent;
import com.rs.game.player.dialogues.Transportation;

public class ItemOptionHandler {
	
	/*
	 * Better than handling it on every item
	 */
	public static boolean canTeleport(Player player){
		if(player.isLocked()  || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion || World.isSinkArea(player.getTile())){
			player.getPackets().sendGameMessage("You can't use this item during this time.");
			return false;
		}
		return true;
	}
	
	/*
	 * Handles the option 2 for everything in the weapon slot
	 */
	public static void handleWeaponOption2(final Player player) {
		int itemId = player.getEquipment().getWeaponId();
		if(itemId == 7409){
			if(canTeleport(player))
			player.getDialogueManager().startDialogue("FarmingD");
		}else if (itemId == 15484){
			if(canTeleport(player))
				player.getDialogueManager().startDialogue("Orb");
			} 
	}
   /*
	* Handles the option 2 for everything in the amulet slot
	*/
	public static void handleAmuletOption2(final Player player) {
		int amuletId = player.getEquipment().getAmuletId();
		if (amuletId <= 1712 && amuletId >= 1706|| amuletId >= 10354 && amuletId <= 10361) {
			if (Magic.sendItemTeleportSpell(player, true,Transportation.EMOTE, Transportation.GFX, 4,new WorldTile(3087, 3496, 0))) {
				Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
					if (amulet != null) {
						amulet.setId(amulet.getId() - 2);
						player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
		}else if (amuletId == 32703) {
	       player.sm("Your amulet has "+player.getBloodcharges()+" charges.");
        }else if (amuletId == 1704 || amuletId == 10352)
		   player.getPackets().sendGameMessage("The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");			
	}
	/*
	* Handles the option 2 of everything in the helm slot
	*/
	public static void handleCapeOption2(final Player player){
		final int capeId = player.getEquipment().getCapeId();
			if (MasterCapePerks.isMasterCape(capeId)) {
				MasterCapePerks.handlePerk(capeId, player);
			}
			
	}
	/*
	* Handles the option 2 of everything in the boots slot
	*/
	public static void handleBootsOption2(final Player player){
		int bootsId = player.getEquipment().getBootsId();
		if(bootsId == 30920)
			player.sm("Your boots have "+player.getSilverhawkFeathers()+ " charges.");
			
	}/*
	* Handles the option 3 of everything in the boots slot
	*/
	public static void handleBootsOption3(final Player player){
		int bootsId = player.getEquipment().getBootsId();
		//sliverhawk emote
		if(bootsId == 30920){
			player.setNextAnimation(new Animation(22794));
			player.setNextGraphics(new Graphics(4604));
		}
			
	}
	/*
	* Handles the option  2 of everything in the helm slot
	*/
	
	public static void handleHelmOption2(Player player){
		int hatId = player.getEquipment().getHatId();
		Masks mask = Masks.forId(hatId);
		if(mask != null){
			player.setNextForceTalk(new ForceTalk(SlayerMasks.getKillsDone(mask, player)));
		}
		Masks helm = Masks.forHelm(hatId);
		if(helm != null){
			player.setNextForceTalk(new ForceTalk(SlayerMasks.getKillsDone(helm, player)));
		}
		else if(hatId == 24437 || hatId == 24439 || hatId == 24440 || hatId == 24441) 
		   player.getDialogueManager().startDialogue("FlamingSkull", player.getEquipment().getItem(Equipment.SLOT_HAT), -1);
		else if (hatId == 30722 ||hatId == 30686 ||hatId == 30656)
		   player.sm("You need to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
	}
	/**
	 * 
	 * @param player
	 */
	public static void handleHelmOption3(Player player){
		int hatId = player.getEquipment().getHatId();
		Masks mask = Masks.forId(hatId);
		if(mask != null)
			SlayerMasks.handleTeleport(mask, player);
		Masks helm = Masks.forHelm(hatId);
		if(helm != null)
			SlayerMasks.handleTeleport(helm, player);
	}
	public static void handleHelmOption4(Player player){
		int hatId = player.getEquipment().getHatId();
		Masks mask = Masks.forId(hatId);
		if(mask != null)
			SlayerMasks.transform(player,mask, false);
		Masks helm = Masks.forHelm(hatId);
		if(helm != null)
			SlayerMasks.transformHelm(player,helm,false);
	}
	public static void handleHelmOption5(Player player){
		player.sm("5");
		int hatId = player.getEquipment().getHatId();
		Masks mask = Masks.forId(hatId);
		if(mask != null)
			SlayerMasks.getKillsDone(mask, player);
	}
}