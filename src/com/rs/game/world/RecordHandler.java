package com.rs.game.world;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;



public class RecordHandler implements Serializable {

	private static final long serialVersionUID = -3070895682771959444L; 

	private final static String PATH = "data/RecordSaves.sd";
	private static RecordHandler data;

	private  long crystalKeys = 0 ;
	private String CrystalKeyHouder = "unknown";
	
	 public long bossKills = 0;
	 public String bossKillsHolder = "unknown";
	
	private long riddlePoints = 0;
	private String riddleHolder = "Unknown";
	
	public long slayerTasks = 0;
	public String slayerTasksHolder = "unknown";
	
	public long darklordPrestige = 0;
	public String darklordHolder = "unknown";

	/**
	 * creates file when the original is gone
	 */
	public RecordHandler() {
		try {
			File file = new File(PATH);
			file.createNewFile();
			SerializableFilesManager.storeSerializableClass(data, file);
 
		} catch (IOException ex) {
			Logger.log("Hellion", "Failed creating");
		} 
	}
	/**
	 * 
	 */
	public void reset(){
		crystalKeys = 0 ;
		CrystalKeyHouder = "unknown";
		
		bossKills = 0;
		bossKillsHolder = "unknown";
		
		riddlePoints = 0;
		riddleHolder = "Unknown";
		
		slayerTasks = 0;
		slayerTasksHolder = "unknown";
		
		darklordPrestige = 0;
		darklordHolder = "unknown";
	}
   /**
    * Inits the whole file
    */
	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				data = (RecordHandler) SerializableFilesManager.loadSerializedFile(file);
				Logger.log("Hellion", "Successfully loaded Persistent data.");
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		data = new RecordHandler();
		Logger.log("Hellion", "Failed to load persistent data.. creating new file.");
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
	/**
	 * checks if the player has more crystals chests opened 
	 * @param player
	 */
	public void handleCrystalKey(Player player){
		if (player.crystalChest > getCrystalKeys()) {
			player.brokeRecord = true;
			if (player.getDisplayName().equals(getCrystalKeyHouder())){
				setCrystalKeys(player.crystalChest);
			}else {
				setCrystalKeys(player.crystalChest);
				setCrystalKeyHouder(player.getDisplayName());
				World.sendWorldMessage("<img=7><col=ff0000>News:"+player.getDisplayName()+" has broken the record of the crystal keys.", false);
			}
		} 
	}
	/**
	 * handles the action of the prestige kills
	 * @param player
	 */
	public void handelDarkLord(Player player){
		if(player.getPAdamage() > darklordPrestige){
			player.brokeRecord = true;
			darklordPrestige = player.getPAdamage();
			setDarklordHolder(player.getDisplayName());
		}
	}
	/**
	 * Handles the boss record
	 * @param player
	 */
	public void handelBossKills(Player player){
		long amount = 0;
		for (Integer key: player.getBossCount().keySet()) {
		amount += player.getBossCount().get(key);
		}
		if(amount > bossKills){
			player.brokeRecord = true;
			bossKills = amount + 1;
		    bossKillsHolder = player.getDisplayName();
		}
	}
	/**
	 * handles trivia record 
	 * @param player
	 */
	public void handelTrivia(Player player){
       if(player.getPointsManager().getRiddelTokens() >riddlePoints){
    	   player.brokeRecord = true;
    	   riddlePoints = player.getPointsManager().getRiddelTokens();
           setRiddleHolder(player.getDisplayName());
       }
	}
	/**
	 * handles the slayer record 
	 * @param player
	 */
	public void handelSlayerTasks(Player player){
		if(player.slayerTasks > slayerTasks){
			player.brokeRecord = true;
			slayerTasks = player.slayerTasks;
			slayerTasksHolder = player.getDisplayName();
		}
	}

	/*
	 * gets the data
	 */
	public static RecordHandler getRecord() {
		return data;
	}

	public long getCrystalKeys() {
		return crystalKeys;
	}

	public void setCrystalKeys(long crystalKeys) {
		this.crystalKeys = crystalKeys;
	}

	public String getCrystalKeyHouder() {
		return CrystalKeyHouder;
	}

	public void setCrystalKeyHouder(String crystalKeyHouder) {
		CrystalKeyHouder = crystalKeyHouder;
	}
	public String getDarklordHolder() {
		return darklordHolder;
	}
	public void setDarklordHolder(String darklordHolder) {
		this.darklordHolder = darklordHolder;
	}
	public String getRiddleHolder() {
		return riddleHolder;
	}
	public void setRiddleHolder(String riddleHolder) {
		this.riddleHolder = riddleHolder;
	}
	public long getRiddlePoints(){
		return this.riddlePoints; 
	}

}
