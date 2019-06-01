package com.rs.game.player.content;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.npc.Drop;
import com.rs.game.player.InterfaceManager;
import com.rs.game.player.Player;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

public class DropSimulator {
	
/*
 * @Author Kethsi www.kethsi.com
 * Modified by Paolo, but a big thanks to Ketshi!!
 * 
 */
	
	private static final Map<Integer, Integer> map = new HashMap<>();
	
	private static void log(String string) {
	
	}
	
	
	private final static ItemsContainer<Item> rewards = new ItemsContainer<>(50, true);
	/**
	 * sends interface with the possible drops
	 * @param player
	 * @throws IOException
	 */
	public static void main(Player player) throws IOException {
		Cache.init();
		NPCDrops.init();
		int npcId = player.simulatorId;
		int kills = player.simulatorAmount;
		for (int i = 0; i < kills; i++) {
			Drop[] drops;
			try{
			drops = NPCDrops.getDrops(npcId);
			} catch(Exception e){
				return;
			}
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
			   rewards.add(new Item(drop2.getItemId(), drop2.getMinAmount() + Utils.getRandom(drop2.getExtraAmount())));
			}
		}
		player.getInterfaceManager().sendInterface(1284);
		player.getPackets().sendIComponentText(1284, 28, "Drop simulator");
		player.getPackets().sendInterSetItemsOptionsScript(1284, 7, 100, 8, 3, "", "", "", "");
		player.getPackets().sendUnlockIComponentOptionSlots(1284, 7, 0, 10, 0, 1, 2, 3);
		player.getPackets().sendHideIComponent(1284,8,true);
		player.getPackets().sendHideIComponent(1284,10,true);
		player.getPackets().sendHideIComponent(1284,9,true);
		player.getPackets().sendItems(100, rewards);
	rewards.clear();
	map.clear();
	}
	/*
	 * used for the discor dbot
	 */
	public static String sendNPCDrops(NPCDefinitions defs) {
		int i = 0;
		String dropEntry = "";
		i = 0;
		Drop[] drops = NPCDrops.getDrops(defs.getId());
		InterfaceManager.bubbleSort(drops);
		if (drops != null) {
		for (Drop drop : drops) {
			if (drop.getItemId() == 0)
				continue;
			ItemDefinitions itemDefs = ItemDefinitions.getItemDefinitions(drop.getItemId());
			StringBuilder sb =  new StringBuilder("").append(itemDefs.name)
					.append(drop.getMaxAmount() == 1 ? 
							("") : drop.getMinAmount() == drop.getMaxAmount() ? (" (" + drop.getMaxAmount() + ")") :
								(" (" + drop.getMinAmount() + "-" + drop.getMaxAmount() + ")"))
					.append(" {"+InterfaceManager.getDropRate(drop.getRate())+"} \n");
			dropEntry += sb.toString();
			
			i++;
			}
		}
		return "```"+dropEntry+"```";
	}

}