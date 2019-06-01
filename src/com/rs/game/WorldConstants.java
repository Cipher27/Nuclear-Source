package com.rs.game;

import java.util.HashMap;

public class WorldConstants {
	public static int eliteClue;
	//120capes
	public static int agility120;
	public static int attack120;
	public static int defence120;
	public static int construction120;
	public static int magic120;
	public static int ranged120;
	public static int cooking120;
	public static int prayer120;
	public static int constitution120;
	public static int herblore120;
	public static int fletching120;
	public static int woodcutting120;
	public static int firemaking120;
	public static int summoning120;
	public static int dungeoneering120;
	public static int farming120;
	public static int fishing120;
	public static int mining120;
	public static int smithing120;
	public static int hunter120;
	public static int crafting120;
	public static int thieving120;
	public static int slayer120;
	public static int strength120;
	public static int runecrafting120;
	//special capes
	public static int maxedUsers;
	public static int compedUsers;
	public static int compedTUsers;
	private static int fireCapes;
	private static int tokharcalCapes;
	private static int nomadsCapes;
	
	/*
	* Drop count
	*/
	public static HashMap<String, Integer> dropCount = new HashMap<>();
	
	public static HashMap<String, Integer> getdropCount() {
		return dropCount;
	}
	//server info
	public static int votes;
	public static int bandosHelm;
	
	//records
	public static int mostBandosKills;
	public static String bandosRecordHolder = "";
	public static int mostTriviaPoints;
	public static String triviapointsRecordHolder ="";
	public static int mostCrystalKeysUsed;
	public static String crystalKeyHolder ="";
	public static int darklordPrestige;
	public static String darklordHolder ="";
	public static int mostSlayerTasksDone;
	public static String slayertaskHolder = "";
	
	
	//gethers & sethers
	public static int getMaxedUsers(){
		return maxedUsers;
	}
	
	public static int addMaxedUser(){
		return maxedUsers++;
	}
	
	public static int getCompedUsers(){
		return compedUsers;
	}
	
	public static int addCompedUser(){
		return compedUsers++;
	}
	
	public static int getCompedTUsers(){
		return compedTUsers;
	}
	
	public static int addCompedTUser(){
		return compedTUsers++;
	}
	public static int getFireCapes(){
		return fireCapes;
	}
	public static int addFireCape(){
		return fireCapes++;
	}
	public static int getTokCapes(){
		return tokharcalCapes;
	}
	public static int addTokCape(){
		return tokharcalCapes++;
	}

	
}
