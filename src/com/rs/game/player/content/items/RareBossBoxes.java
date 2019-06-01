package com.rs.game.player.content.items;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.Drop;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.utils.Colors;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

/**
 * 
 * @author paolo
 *
 */
public class RareBossBoxes {

	public static final int BOX = 34527;
	
	public static void openBox(Player player, int npcId){
		if(!player.getInventory().contains(BOX))
			return;
		Drop[] drops = NPCDrops.getDrops(npcId);
		Item[] possibleRares = new Item[20];
		int index = 0;
		double dropRate = 5;
		if(npcId == 8133)
			dropRate = 18;
		
		for(Drop d : drops){
			if(d.getRate() < dropRate){
				possibleRares[index] = new Item(d.getItemId(),1);
				index++;
			}
		}
		if(index > 0){
			Item item = possibleRares[Utils.random(0,index)];
			if(item != null){
				player.getInventory().deleteItem(new Item(BOX,1));
			player.getInventory().addItem(item);
			player.sm("[News] You received a "+ItemDefinitions.getItemDefinitions(item.getId()).name+ " .");
			World.sendWorldMessageAll("[News] "+Colors.red+player.getDisplayName()+"</col> just received a "+Colors.red+ItemDefinitions.getItemDefinitions(item.getId()).name+ "</col> from a rare boss lootbox.");
			}
		} else {
			player.sm("Error: this npc does not have rare drops.");
		}
	}
}