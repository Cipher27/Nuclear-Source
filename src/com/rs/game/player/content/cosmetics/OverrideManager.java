package com.rs.game.player.content.cosmetics;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;

/**
  * Players can pick an item which they can use as an overhide
  @Paolo
  **/
  

public class OverrideManager {
	
	
	public enum CosmeticItems {
		
		SNOWMAN("Snowman", 33593, 33594, 33595, 0, 0, 0),
		SAMURAI("Samurai", 33637, 33638, 33639, 33640, 33641, 33642),
		DARK_LORD("Dark Lord", 34222, 34223, 34224, 34225, 34226, 0),
		NOMAD("Nomad", 34099, 34100, 34101,34102,34103, 34104),
		LAVA("Lava", 0, 34811, 34812,34814,34813, 0);
		
		private String name;
		private int helm, chest, legs, boots, gloves, cape;
		
		CosmeticItems(String name, int helm, int chest, int legs, int boots, int gloves, int cape) {
			this.name = name;
			this.helm = helm;
			this.chest = chest;
			this.legs = legs;
			this.boots = boots;
			this.gloves = gloves;
			this.cape = cape;
		}
		
		public String getName() {
			return name;
		}
		
		public int getHelmId() {
			return helm;
		}
		
		public int getChestId() {
			return chest;
		}
		
		public int getLegsId() {
			return legs;
		}
		
		public int getBootsId() {
			return boots;
		}
		
		public int getGlovesId() {
			return gloves;
		}
		
		public int getCapeId() {
			return cape;
		}
	}
	public static void setOutfit(CosmeticItems outfit, Player player) {
	if(player.getEquipment().getHatId() == 1149){
		player.sm("There is a bug with the dragon med helm while wearing cosmetics, do not wear it. It will null ur account");
		return;
	}
		setItem(new Item(outfit.getHelmId()), player);
		setItem(new Item(outfit.getChestId()), player);
		setItem(new Item(outfit.getLegsId()), player);
	}
	
	public static void setItem(Item item, Player player) {
	if (item.getDefinitions().getEquipSlot() == 0) { //helmet
	player.cosmeticHelm = ItemDefinitions.getItemDefinitions(item.getId()).maleEquip1;
	} else if (item.getDefinitions().getEquipSlot() == 4) { //body
	player.cosmeticBody = ItemDefinitions.getItemDefinitions(item.getId()).maleEquip1;	
	} else if (item.getDefinitions().getEquipSlot() == 7) { //legs
	player.cosmeticLegs = ItemDefinitions.getItemDefinitions(item.getId()).maleEquip1;	
	} 
	}
	
	public static void DarklordOutfit(Player player){
		setOutfit(CosmeticItems.DARK_LORD,player);
	}
	
	
	public static void NomadOutfit(Player player){
		setOutfit(CosmeticItems.NOMAD,player);
	}
	
	public static void SnowmanOutfit(Player player){
		setOutfit(CosmeticItems.SNOWMAN,player);
	}
	
	public static void LavaOutfit(Player player){
		setOutfit(CosmeticItems.LAVA,player);
	}
	
	public static void SamuraiOufit(Player player){
		setOutfit(CosmeticItems.SAMURAI,player);
	}

	
    private static int get(int slot) {
			return ItemDefinitions.getItemDefinitions(slot).getEquipSlot();
		}
	
	public static int getHelmModel(int slot, boolean male, Player player) {
		switch (slot) {
		 
			case Equipment.SLOT_HAT:
				return male ? player.cosmeticHelm : player.cosmeticHelm;
		}
		return get(slot);
	}
	public static int getBodyModel(int slot, boolean male, Player player) {
		switch (slot) {
			case Equipment.SLOT_CHEST:
				return male ? player.cosmeticBody : player.cosmeticBody;
		}
		return get(slot);
	}
	public static int getLegModel(int slot, boolean male, Player player) {
		switch (slot) {
			case Equipment.SLOT_LEGS:
				return male ? player.cosmeticLegs : player.cosmeticLegs;
		}
		return get(slot);
	}
	
	


}
