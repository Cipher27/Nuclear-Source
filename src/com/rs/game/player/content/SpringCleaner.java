package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.actions.smithing.Smelting.SmeltingBar;
import com.rs.utils.Utils;



public class SpringCleaner {
	
    /**
      @Paolo,this item turns armour drops into ores and smith exp. rechargeable with power tokes
	  */	
	  
	 private final static int SPRINGCLEANER = 31612;
	 
	
	/**
	  * Checks is the drop is a convertable
	  
	 * @return 
	  **/
	public static boolean isConvertable(Player player,Item item){
		String name = ItemDefinitions.getItemDefinitions(item.getId()).getName().toLowerCase();
	if(item.getId() >= 554 && item.getId() <= 566) //Because they contain the rune word xD
		return false;
	if(item.getId() >= 4694 && item.getId()<= 4699)
		return false;
	if(name.contains("ore"))
		return false;
	if (name.contains("rune") || name.contains("runite") ||name.contains("adamant") || name.contains("mithril")) {
          convertItem(player,item);
		return true;
	}
		return false;
	}
	
	
	//Get the id of the bar players should get in their bank, based on name also.
	public static int getBarsId(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName().toLowerCase();
		if (name.contains("rune") || name.contains("runite")) {
			return 451;
		}if (name.contains("adamant")) {
			return 449;
		}if(name.contains("mithril")){
		    return 447;
		}
		return id;
	}
	
	//Get the id of the bar players should get in their bank, based on name also.
	private static int getBasedAmount(Item item) {
		String def = item.getDefinitions().getName();
		 if (def.contains("dagger") || def.contains("knife") ||def.contains("hatchet") || def.contains("mace")
			||	 def.contains("2h sword")|| def.contains("med helm") ||def.contains("claws")|| def.contains("limbs") || def.contains("sword")|| def.contains("dart tip") ||def.contains("arrow") ||def.contains("longsword")  ) {
			return 1;
		}   else if ( def.contains("pickaxe")|| def.contains("scimitar") || def.contains("full helm")) {
			return 2;
		} else if (def.contains("kiteshield") ||def.contains("plateskirt") || def.contains("platelegs")|| def.contains("battleaxe") || def.contains("chainbody") || def.contains("sq shield") || def.contains("warhammer")) {
			return 3;
		}   else if (def.contains("platebody")) {
			return 5;
		}
		return 1;
	}
    public static void convertItem(Player player, Item item){
    	if(!player.getInventory().containsItem(SPRINGCLEANER, 1))
    		return;
		if(player.getPointsManager().springs <= 0)
			return;
		player.getPointsManager().springs--;
    	SmeltingBar bar = SmeltingBar.forOre(getBarsId(item.getId()));
    	player.sm("Your springcleaner converted "+Utils.formatAorAn(item.getName())+" "+item.getName()+" into :"+bar.getItemsRequired()[0].getAmount()*getBasedAmount(item) + "x "+bar.getItemsRequired()[0].getName() + ", "+bar.getItemsRequired()[1].getAmount()*getBasedAmount(item) +"x " +bar.getItemsRequired()[1].getName());
        for(int i = 0; i < getBasedAmount(item); i ++){
    	player.getBank().addItem(new Item(bar.getItemsRequired()[0].getId(),bar.getItemsRequired()[0].getAmount()), true);
        player.getBank().addItem(new Item(bar.getItemsRequired()[1].getId(),bar.getItemsRequired()[1].getAmount()), true);
        }
        }

}
