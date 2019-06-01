package com.rs.game.player.content.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rs.Settings;
import com.rs.cache.Cache;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.npc.Drop;
import com.rs.game.player.Player;
import com.rs.game.world.GlobalItemCounter;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;


public class BossLootBoxes {
    
	private static final Map<Integer, Integer> map = new HashMap<>();	
	
	private final static ItemsContainer<Item> rewards = new ItemsContainer<>(50, true);
	public List<Item> items = new ArrayList<Item>();
	
	public static void handelSmallBox(Player player, int npcId){
		try {
			handelDrops(player,1,npcId);
			player.getInventory().deleteItem(34927, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void handelBigBox(Player player, int npcId){
		try {
			handelDrops(player,5,npcId);
			player.getInventory().deleteItem(34928, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void handelDrops(Player player, int amount, int npcId2) throws IOException {
		Cache.init();
		NPCDrops.init();
		int npcId = npcId2;
		int kills = amount;
		for (int i = 0; i < kills; i++) {
			Drop[] drops = NPCDrops.getDrops(npcId);
			if (drops == null) {
				player.sm("This npc has no drops.");
			    return;
			}
			Drop[] possibleDrops = new Drop[drops.length];
			int possibleDropsCount = 0;
			for (Drop drop : drops) {
				if (drop.getRate() == 100) {
					int previousValue = map.get(drop.getItemId()) != null ?  map.get(drop.getItemId()) : 0;
					map.put(drop.getItemId(), drop.getMinAmount() + Utils.getRandom(drop.getExtraAmount()) + previousValue);
					rewards.add(new Item(drop.getItemId(), drop.getMinAmount() + Utils.getRandom(drop.getExtraAmount())));
					
				} else {
					if ((Utils.getRandomDouble(99) + 1) <= drop.getRate()) {
						possibleDrops[possibleDropsCount++] = drop;
					}
				}
			}
			if (possibleDropsCount > 0) {
				Drop drop2 = possibleDrops[Utils.getRandom(possibleDropsCount - 1)];
				int previousValue = map.get(drop2.getItemId()) != null ?  map.get(drop2.getItemId()) : 0;
				map.put(drop2.getItemId(), drop2.getMinAmount() + Utils.getRandom(drop2.getExtraAmount()) + previousValue);
				for (int itemId : Settings.RARE_DROP_IDS) {
					if (drop2.getItemId() == itemId) {
						if(player.getRights() != 2)
						GlobalItemCounter.handleDropCount(new Item(drop2.getItemId()));
						int i1 = 1;
						if (player.getdropCount().containsKey(drop2.getItemId())) {
							i1 = player.getdropCount().get(drop2.getItemId()) + 1;
						}
						player.getdropCount().put(drop2.getItemId(), i1);
					}
				}
			   rewards.add(new Item(drop2.getItemId(), drop2.getMinAmount() + Utils.getRandom(drop2.getExtraAmount())));
			}
		}
		if(rewards.getSize() == 0)
			handelDrops(player,amount,npcId2);
		player.getInterfaceManager().sendInterface(1284);
		player.getPackets().sendIComponentText(1284, 28, "Loot box");
		player.getPackets().sendInterSetItemsOptionsScript(1284, 7, 100, 8, 3, "", "", "", "");
		player.getPackets().sendUnlockIComponentOptionSlots(1284, 7, 0, 10, 0, 1, 2, 3);
		player.getPackets().sendHideIComponent(1284,8,true);
		player.getPackets().sendHideIComponent(1284,10,true);
		player.getPackets().sendHideIComponent(1284,9,true);
		player.getPackets().sendItems(100, rewards);
		map.forEach((k,v) -> player.getBank().addItem(new Item(k,v),true));
		
		rewards.clear();
		map.clear();
	}
	
 
}