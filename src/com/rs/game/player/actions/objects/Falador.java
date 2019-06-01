package com.rs.game.player.actions.objects;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.others.Leeuni;
import com.rs.game.player.Player;

/*
 * @Author Danny
 * Falador City
 */

public class Falador {
	
	
	public static void MagicChest(Player player,
			final WorldObject object) {
			player.getInventory().addItem(1536, 1);
			player.getPackets().sendGameMessage("You find the second piece of the map in the chest!");
	}
	
	public static void TrainCart(Player player,
			final WorldObject object) {
		player.getDialogueManager().startDialogue("Conductor", 2180);
	}
	
	public static void IngotTray(Player player,
			final WorldObject object) {
		//player.getInterfaceManager().sendInterface(ArtisanWorkshop.INGOTWITH);
	}
	
	public static void Chute(Player player,
			final WorldObject object) {
		//ArtisanWorkshop.DepositArmour(player);
		player.getInventory().refresh();
	}
	
	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 25115:
		case 2588:
		case 7029:
		case 29395:
		case 29394:
		case 29396:
		case 24821:
		case 24822:
		case 24823:
		return true;
		default:
		return false;
		}
	}

	
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 25115) { 
				if(player.getInventory().contains(24262)){
					player.getInventory().deleteItem(24262,1);
					player.setNextWorldTile(new WorldTile(3051,9840,0));
				} else {
					player.getDialogueManager().startDialogue("SimplePlayerMessage", "I need something hot to open this door, maybe there's something in this cave...");
				}
			
		}
		if (id == 2588) { 
			if(player.getInventory().contains(new Item(1))){
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "Mhhhh the chest looks empty.");
				return;
			}
			player.getInventory().addItem(new Item(1,1));
			player.getDialogueManager().startDialogue("SimpleMessage", "You found a toolkit.");
		}
		if (id == 7029) { 
			Falador.TrainCart(player, object); 
		}
		if (id == 29395 || id == 29394) {
			Falador.IngotTray(player, object);
		}
		if (id == 29396) {
			Falador.Chute(player, object);
		}
		/*if (id == 24821) {
			ArtisanWorkshop.GiveBronzeIngots(player);
		}
		if (id == 24822) {
			ArtisanWorkshop.GiveIronIngots(player);
		}
		if (id == 24823) {
			ArtisanWorkshop.GiveSteelIngots(player);
		}*/
		
	}

}
