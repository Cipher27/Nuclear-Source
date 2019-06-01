package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;



public class Seedicider {
	
    /**
      @Paolo, This items turns seed drops into farming exp for those who don't like to wait with farming. Not tested yet, wrote it quick.
     */	
	
	/**
	  * Checks is the drop is a seed.
	 * @return 
	  **/
	public static boolean isSeed(int id){
		String name = ItemDefinitions.getItemDefinitions(id).getName().toLowerCase();
		if (name.contains("seed") || name.contains("Seed"))
			return true;
		return false;
	}
	
	
	/**
	  * Gets the seed exp
	 * @return 
	  */
    public static int getSeedXp(int id){
	switch (id) {
		case 5283: //apple tree
		return 50;
		//herb seeds
		case 5291: //guam seed
		return 9;
		case 5292: //marrentill seed
		return 14;
		case 5293: //tattomin seed
		return 19;
		case 5294: //harralander seed
		return 26;
		case 5295: //ranarr seed
		return 35;
		case 5296: //toadflax seed
		return 38;
		case 5297: //irit seed
		return 44;
		case 5298: //avantoe seed
		return 50;
		case 5299: //kwuarm seed
		return 56;
		case 5300: //snapdragon seed
		return 62;
		case 5301: //cadantine seed
		return 67;
		case 5302: //lantadyme seed
		return 73;
		case 5303: //dwarf weed seed
		return 79;
		case 5304: //torstol seed
		return 85;
		//trees
		case 5312: //oak seed
		return 22;
		case 5313: //willow seed
		return 33;
		case 5314: //mmaple seed
		return 49;
		case 5315: //yew seed
		return 87;
		case 5316: //magic seed
		return 138;
	  }
	return id;
    }
    /**
     * *
     * @param player
     * @param item
     * does the actual convert
     */
    public static void convertXp(Player player,Item item) {
	if (!player.sdSeeds.contains(item.getName()))
    player.sm("Your Seedicider converter: "+item.getAmount()+"x "+item.getName()+" into "+getSeedXp(item.getId())+" exp.");
    player.getSkills().addXp(Skills.FARMING, getSeedXp(item.getId()));
    }
}
