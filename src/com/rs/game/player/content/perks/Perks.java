package com.rs.game.player.content.perks;


/**
 * 
 * @author paolo
 *
 */
public class Perks {
	/**
	 * enum of the types a perk could be
	 */
	public enum Type {SKILLING, COMBAT,MISC};
	/**
	 * 
	 * list of all the perks with their info and values
	 *
	 */
	 
	public enum Perk{
		/**
		 * combat perks here
		 */
		HEAL_PERK("Heal increaser","Food will heal for you 15% more",6,Type.COMBAT,1891), //done
		DEGRADE_PERK("No degrade","Your items won't degrade anymore.",10,Type.COMBAT,4720), //done
		BONE_CRUSHER_PERK("Bone crusher upgrade","Your bonecrusher will also convert ashes now",5,Type.COMBAT,18337),
		DRAGONFIRE_PERK("Dragon lover","You will be permenantly immume against dragonfire.",5,Type.COMBAT,11283), //done
		POUCH_PERK("Pouch increaser","Increases the duration of pouches significantly.",4,Type.COMBAT,12093), //done
		RUNE_PERK("Rune keeper","20% chance you save your runes while doing a magic spell.",4,Type.COMBAT,554), 
		RANGED_PERK("Ava's device", "You no longer need to equip an ava's device to have its effect",3,Type.COMBAT,26778),
		HIT_PERK("Vampyre blood", "10% chance of healing half of what you hit.",7,Type.COMBAT,27365),
		/**
		 * skilling perks
         **/
		FARMING_PERK("Green fingers"," 10% chance at not using a seed while planting and 5% chance of finding a seed while harvesting.",6,Type.SKILLING,5304), //done 
		AGILITY_PERK("Agility helper","A 10% chance of doubeling/trippeling your exp when you complete a lap.",4,Type.SKILLING,1),
		COOKING_PERK("Chef kok","You will never burn food again",3,Type.SKILLING,7948),
		HERBLORE_PERK("Quick cleaner","Cleans every herb of the same type instead of just one.",3,Type.SKILLING,219),
		CRYSTAL_KEY_PERK("Key finder","You can find crystal key parts while training gathering skills",5,Type.SKILLING,989), //done
		HUNTER_PERK("Trap extender","You can place 2 more traps while hunting",4,Type.SKILLING,10008), //done
		HIGH_ALCH_PERK("Alchemy increaser","You can alch 10 items instead of just 1.",6,Type.SKILLING,1401);
	 private String name;
		private String description;
	 private int price;
	 Type type;
	 int pictureId;
	 
	 private Perk(String name, String desc, int price, Type typ,int pic){
		 this.setName(name);
		 this.setDescription(desc);
		 this.setPrice(price);
		 this.type = typ;
		 this.pictureId = pic;
	 }

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	}

}

