package com.rs.game.server.fameHall;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.rs.game.player.Player;
import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;


public class HallOfFame implements Serializable {
 

	/**
	 * ser number
	 */
	private static final long serialVersionUID = -1179105179721594850L;
	/**
	 * file path
	 */
	private final static String PATH = "data/hof/HallSaves.sd";
	/**
	 * used to get the data
	 */
	private static HallOfFame data;
	/**
	 * every achievement someone kan get
	 */
	/*
	 * PVM
	 */
	public static String firstFightCaves[] = new String[2];
	public static String firstFightKiln[] = new String[2];
	public static String firstNexKil[] = new String[2];
	public static String first100SlayerTasks[] = new String[2];
	public static String firstAvaryss[] = new String[2];
	public static String firstFinalBoss[] = new String[2];
	/*
	 * Skilling
	 */
	public static String first200mil[] = new String[2];
	public String firstComplete60Dung[] = new String[2];
	public String first120Everything[] = new String[2];
	
	/*
	 * MISC
	 */
	public static String firstMax[] = new String[2];
	public String firstComp[] = new String[2];
	public String firstAchievements[] = new String[2];
	public String firstDonator[] = new String[2];
	public String firstSpiritShieldCreated[] = new String[2];
	
	public static void sendPvmInterface(Player player){
		player.hofPage = 1;
		for(int i = 4; i <= 39; i ++)
			player.getPackets().sendIComponentText(3025, i, "");
		player.getInterfaceManager().sendInterface(3025);
		player.getPackets().sendIComponentText(3025, 4, "Achievement: Pvm");
		player.getPackets().sendIComponentText(3025, 6, "Player & date");
		player.getPackets().sendIComponentText(3025, 8, "First player to complete Fights Caves");
		player.getPackets().sendIComponentText(3025, 10, ""+firstFightCaves[0]+" "+firstFightCaves[1]);
		player.getPackets().sendIComponentText(3025, 12, "First player to complete Fights Kiln");
		player.getPackets().sendIComponentText(3025, 14, ""+firstFightKiln[0]+" "+firstFightKiln[1]);
		player.getPackets().sendIComponentText(3025, 16, "First player to kill Nex"); //12,16,20,24
		player.getPackets().sendIComponentText(3025, 18, ""+firstNexKil[0]+" "+firstNexKil[1]);
		player.getPackets().sendIComponentText(3025, 20, "First player to kill Avaryss");
		player.getPackets().sendIComponentText(3025, 22, ""+firstAvaryss[0]+" "+firstAvaryss[1]);
		player.getPackets().sendIComponentText(3025, 24, "First to achieve final boss title");
		player.getPackets().sendIComponentText(3025, 26, ""+firstFinalBoss[0]+" "+firstFinalBoss[1]);
		player.getPackets().sendIComponentText(3025, 28, "First with 100 slayer tasks");
		player.getPackets().sendIComponentText(3025, 30, ""+first100SlayerTasks[0]+" "+first100SlayerTasks[1]);
		player.getPackets().sendIComponentText(3025, 32, "");
	}
	public static void sendSkillingInterface(Player player){
		player.hofPage = 2;
		for(int i = 4; i <= 39; i ++)
			player.getPackets().sendIComponentText(3025, i, "");
		player.getInterfaceManager().sendInterface(3025);
		player.getPackets().sendIComponentText(3025, 4, "Achievement: Skilling");
		player.getPackets().sendIComponentText(3025, 6, "Player & date");
		player.getPackets().sendIComponentText(3025, 8, "First player to achieve 120 in every skill");
		player.getPackets().sendIComponentText(3025, 12, "First player to reach 200m EXP in a skill");
		player.getPackets().sendIComponentText(3025, 14, "TTwerty 2017-08-24");
		player.getPackets().sendIComponentText(3025, 16, "");
		player.getPackets().sendIComponentText(3025, 20, "");
		player.getPackets().sendIComponentText(3025, 24, "");
		player.getPackets().sendIComponentText(3025, 28, "");
	}
	public static void sendMiscInterface(Player player){
		player.hofPage = 3;
		for(int i = 4; i <= 39; i ++)
			player.getPackets().sendIComponentText(3025, i, "");
		player.getInterfaceManager().sendInterface(3025);
		player.getPackets().sendIComponentText(3025, 4, "Achievements: Misc");
		player.getPackets().sendIComponentText(3025, 6, "Player & date");
		player.getPackets().sendIComponentText(3025, 8, "First player to Max");
		player.getPackets().sendIComponentText(3025, 10, "Hunosaur 2017-08-26");
		player.getPackets().sendIComponentText(3025, 12, "First player to Comp");
		player.getPackets().sendIComponentText(3025, 16, "First player to complete all achievements");
		player.getPackets().sendIComponentText(3025, 20, "First player to Donate");
		player.getPackets().sendIComponentText(3025, 22, "Hanzo 2017-08-24");
		player.getPackets().sendIComponentText(3025, 24, "First player to dye 2 items third-age");
		player.getPackets().sendIComponentText(3025, 28, "");
	}
	/**
	 * creates file when the original is gone
	 */
	public HallOfFame() {
		try {
			File file = new File(PATH);
			file.createNewFile();
			SerializableFilesManager.storeSerializableClass(data, file);
 
		} catch (IOException ex) {
			Logger.log("Hellion", "Failed creating");
		} 
	}
   /**
    * Inits the whole file
    */
	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				data = (HallOfFame) SerializableFilesManager.loadSerializedFile(file);
				Logger.log("Hellion", "Successfully loaded HOF data.");
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		data = new HallOfFame();
		Logger.log("Hellion", "Failed to load HOF data.. creating new file.");
	}
   /**
    * Call this to save this
    */
	public static final void save() {
		try {
			SerializableFilesManager.storeSerializableClass(data, new File(PATH));
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}
}
