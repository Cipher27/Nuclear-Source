package com.rs.game.player.content.items;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;

public final class CrystalItemCreation {
	
	final static int HARMONIC_DUST = 32622;

	public enum Tools {
		
		CRYSTAL_AXE(6739,32645,1000),
		CRYSTAL_PICKAXE(15259,32646,1000),
		CRYSTAL_TINDERBOX(590,32637,1000),
		CRYSTAL_KNIFE(946,32635,1000),
		CRYSTAL_CHISEL(1755,32642,1000),
		CRYSTAL_SAW(8794,32633,1000),
		CRYSTAL_HAMMER(2347,32640,1000);
		
		int originalId;
		int crystalToolId;
		int dustAmount;
		
		private static Map<Integer, Tools> tools = new HashMap<Integer, Tools>();

		static {
			for (Tools tool : Tools.values()) {
				tools.put(tool.getOriginalId(), tool);
			}
		}

		public static Tools forId(int id) {
			return tools.get(id);
		}
		
		public int getOriginalId(){
			return originalId;
		}
		
		public int getCrystalToolId(){
			return crystalToolId;
		}
		
		public int getDustAmount(){
			return dustAmount;
		}
		Tools(int originalId,int crystalToolId,int dustAmount){
			this.originalId = originalId;
			this.crystalToolId = crystalToolId;
			this.dustAmount = dustAmount;
		}
	}
	
	public static void convertTool(Player player, int itemId){
		final Tools tools = Tools.forId(itemId);
		final ItemDefinitions itemDef = new ItemDefinitions(itemId);
		if(player.getInventory().containsItem(HARMONIC_DUST, tools.getDustAmount())){ //for comp cape reqs
			if(!player.crystalItems.contains(tools.getCrystalToolId()))
				player.crystalItems.add(tools.getCrystalToolId());
		player.getInventory().deleteItem(tools.getOriginalId(),1);
		player.getInventory().deleteItem(HARMONIC_DUST, tools.getDustAmount());
		player.getInventory().addItem(tools.getCrystalToolId(),1);
		player.sm("You succesfully made a "+itemDef.getName()+".");
		} else 
			player.sm("You need "+tools.getDustAmount()+" harmonic dust to create a crystal tool.");
	}
}
