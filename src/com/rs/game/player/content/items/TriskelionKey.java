package com.rs.game.player.content.items;

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
public class TriskelionKey {

	private Item[] possibleLoot = 
		{
			new Item(995,20000000), //20mil
			new Item(995,50000000), //50mil
			new Item(995,70000000), //70mil
			new Item(34927,5), //small boss loot boxes
			new Item(34928,2), //big boss loot boxes
			new Item(24155,10), //double spîn ticket
			new Item(18779,1), //effigy
			
		};
}