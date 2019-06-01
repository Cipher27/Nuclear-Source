package com.rs.game.player.content.items;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Colors;
import com.rs.utils.Utils;

/**
 * 
 * @author paolo
 *
 */
public class RareCosmeticBoxes {

	public static final int BOX_ID = 18768;
	
	public static Item[] possibleRares  = {
		new Item(1038,1),
		new Item(1040,1),
		new Item(1042,1),
		new Item(1044,1),
		new Item(1046,1),
		new Item(1048,1),
		new Item(34356,1),
	};
	/**
	 * opens the box and sends worldmessage
	 * @param player
	 */
	public static void openBox(Player player){
		if(!player.getInventory().contains(BOX_ID))
			return;
		player.getInventory().deleteItem(new Item(BOX_ID));
		Item item = possibleRares[Utils.random(possibleRares.length)];
		player.getInventory().addItem(item);
		player.sm("You received a "+ItemDefinitions.getItemDefinitions(item.getId()).name+ " .");
		World.sendWorldMessageAll(player.getDisplayName()+" just received a "+ItemDefinitions.getItemDefinitions(item.getId()).name+ " from a rare cosmetic lootbox.");
	}
}