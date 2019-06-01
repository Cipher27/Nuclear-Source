package com.rs.game.player;

import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.rs.Gui;
import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.npc.Drop;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.world.GlobalCapeCounter;
import com.rs.game.world.GlobalItemCounter;
import com.rs.game.world.RecordHandler;
import com.rs.utils.Colors;
import com.rs.utils.Logger;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

@SuppressWarnings("unused")
public class InterfaceManager {

	public static final int FIXED_WINDOW_ID = 548;
	public static final int RESIZABLE_WINDOW_ID = 746;
	public static final int CHAT_BOX_TAB = 13;
	public static final int FIXED_SCREEN_TAB_ID = 27;
	public static final int RESIZABLE_SCREEN_TAB_ID = 28;
	public static final int FIXED_INV_TAB_ID = 166; 
	public static final int RESIZABLE_INV_TAB_ID = 108;
	private Player player;

	private final ConcurrentHashMap<Integer, int[]> openedinterfaces = new ConcurrentHashMap<Integer, int[]>();
	private final ConcurrentHashMap<Integer, Integer> openedinterfacesb = new ConcurrentHashMap<Integer, Integer>();

	public boolean questScreen;
	public boolean resizableScreen;
	private int windowsPane;
	
	public void sendCountDown() {
		player.getInterfaceManager().sendOverlay(57, false);
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 34 : 0, 57);
	}
	
	public boolean isResizableScreen() {
		return resizableScreen;
	}

	public InterfaceManager(Player player) {
		this.player = player;
	}
	
    public void sendSquealOverlay() {
	setWindowInterface(resizableScreen ? 0 : 10, 1252); // TODO not working for fixed
    }
    
    public void closeSquealOverlay() {
    	player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 11 : 0);
    }
	
	public void sendThankyouInterface(Player player){
		player.getInterfaceManager().sendInterface(712);
		player.getPackets().sendIComponentText(712, 1, "Dear "+player.getDisplayName()+",");
		player.getPackets().sendIComponentText(712, 2, "Thank you for the support, we really appriciate it. Your money will be wisely spend back into the server, if you have any ideas to improve the server just let us know");
		player.getPackets().sendIComponentText(712, 3, "Hellion Team");
	}

    
    private void clearChilds(int parentInterfaceId) {
	for (int key : openedinterfaces.keySet()) {
	    if(key >> 16 == parentInterfaceId) 
		openedinterfaces.remove(key);
	}
    }
	public void sendDungeoneeringTab() {
			player.getInterfaceManager().setWindowInterface(player.getInterfaceManager().hasRezizableScreen() ? 114 : 174, 939);
		player.getPackets().sendGlobalConfig(234, 3);
		player.getPackets().sendGlobalConfig(168, 3);
		
		player.getPackets().sendGlobalConfig(1183, 6); // Complexity
		player.getPackets().sendGlobalConfig(1180,1/* DungeonPartyManager.getFloor()*/); // Floor
		player.getPackets().sendGlobalConfig(1181, 10); // current progress
		player.getPackets().sendGlobalConfig(1182, 10); // previous progress
		
		player.getPackets().sendHideIComponent(939, 31, false); // shows invite
		player.getPackets().sendHideIComponent(939, 38, false); // shows leave
	}
    
    private void clearChildsB(int parentInterfaceId) {
    	for (int key : openedinterfaces.keySet()) {
    	    if(key >> 16 == parentInterfaceId) 
    		openedinterfaces.remove(key);
    	}
        }
    

	public void sendTab(int tabId, int interfaceId) {
		player.getPackets().sendInterface(true,
				resizableScreen ? RESIZABLE_WINDOW_ID : FIXED_WINDOW_ID, tabId,
				interfaceId);
	}

	public void sendChatBoxInterface(int interfaceId) {
		player.getPackets().sendInterface(true, 752, CHAT_BOX_TAB, interfaceId);
	}
	
	public void sendHealth(Player player) {
		if(player.shownPvmInfo)
		player.getInterfaceManager().sendOverlay(3020, false);
	}
	
	public void closeHealth(boolean fullScreen) {
		player.getInterfaceManager().closeOverlay(false);
	}

	public void closeChatBoxInterface() {
		player.getPackets().closeInterface(CHAT_BOX_TAB);
	}

	public void sendOverlay(int interfaceId, boolean fullScreen) {
		sendTab(resizableScreen ? fullScreen ? 1 : 11 : 0, interfaceId);
	}
	
	public void closeOverlay(boolean fullScreen) {
		player.getPackets().closeInterface(resizableScreen ? fullScreen ? 1 : 11 : 0);
	}
	
	public void sendInterface(int interfaceId) {
		player.getPackets()
				.sendInterface(
						false,
						resizableScreen ? RESIZABLE_WINDOW_ID : FIXED_WINDOW_ID,
						resizableScreen ? RESIZABLE_SCREEN_TAB_ID
								: FIXED_SCREEN_TAB_ID, interfaceId);
	}
	
    public void setInterface(boolean clickThrought, int parentInterfaceId, int parentInterfaceComponentId, int interfaceId) {
	int parentUID = getComponentUId(parentInterfaceId, parentInterfaceComponentId);
	Integer oldInterface = openedinterfacesb.get(parentUID);
	if(oldInterface != null)
	    clearChildsB(oldInterface);
	openedinterfacesb.put(parentUID, interfaceId); //replaces inter if theres one in that component already
	player.getPackets().sendInterface(clickThrought, parentUID, interfaceId);
    }
 
    
    public static int getComponentUId(int interfaceId, int componentId) {
	return interfaceId << 16 | componentId;
    }
   /**
     * Poll system.
	 **/
	public void Poll1() {
    player.getInterfaceManager().sendInterface(570);
	player.getPackets().sendIComponentText(570,5, "Hellion polls.");
	player.getPackets().sendIComponentText(570,14, ""+Settings.POLL_1+","+Settings.POLL_1_INFO+".");
	player.getPackets().sendIComponentText(570,15, "Yes!");
	player.getPackets().sendIComponentText(570,16, "No.");
	}
	public void Poll2() {
    player.getInterfaceManager().sendInterface(569);
	player.getPackets().sendIComponentText(569,5, "Hellion polls.");
	player.getPackets().sendIComponentText(569,14, ""+Settings.POLL_2+","+Settings.POLL_2_INFO+".");
	player.getPackets().sendIComponentText(569,15, "Yes!");
	player.getPackets().sendIComponentText(569,16, "No.");
	}
	public void Poll3() {
    player.getInterfaceManager().sendInterface(568);
	player.getPackets().sendIComponentText(568,8, ""+Settings.POLL_3+","+Settings.POLL_3_INFO+".");
	player.getPackets().sendIComponentText(568,3, "Yes!");
	player.getPackets().sendIComponentText(568,4, "No.");
	}
	public void Poll4() {
    player.getInterfaceManager().sendInterface(567);
	player.getPackets().sendIComponentText(567,5, "Hellion polls.");
	player.getPackets().sendIComponentText(567,14, ""+Settings.POLL_4+","+Settings.POLL_4_INFO+".");
	player.getPackets().sendIComponentText(567,15, "Yes!");
	player.getPackets().sendIComponentText(567,16, "No.");	
	}
	/**
	  * Start boss information
	  **/
	
	/**
	  * UpdatesInterface
	  **/
	 public void updateInfo(Player player) {
	 player.getInterfaceManager().sendInterface(1245);
player.getPackets().sendIComponentText(1245, 330, "<img=1><u>Hellion Update Board</u><img=1>");
player.getPackets().sendIComponentText(1245, 14, "if you have a idea feel free to say it!");
player.getPackets().sendIComponentText(1245, 15, "New Achievements have been added.");
player.getPackets().sendIComponentText(1245, 16, "4 New records to break.");
player.getPackets().sendIComponentText(1245, 17, "You can now create rocktailsoup/sharksoup");
player.getPackets().sendIComponentText(1245, 18, "Soul amulet specs has been added (increase soulsplit heal)");
player.getPackets().sendIComponentText(1245, 19, "Battleaxes now have the right animation");
player.getPackets().sendIComponentText(1245, 20, "Changed the home banks from place)");
player.getPackets().sendIComponentText(1245, 21, "There's now a board at home where you can see which activity is going onn.");
player.getPackets().sendIComponentText(1245, 22, "New admin consol.");
player.getPackets().sendIComponentText(1245, 23, "Void upgrade kit + coin magnet and charming imp added.");
player.getPackets().sendIComponentText(1245, 24, "Loyalty tokens bug fixed.");

	 }
	 /**
	   * Rules
	   **/
	public void sendRules(Player player) {
	
	player.getInterfaceManager().sendInterface(116);
	player.getPackets().sendHideIComponent(116, 0, true); //so they can't close it
	player.getPackets().sendHideIComponent(116, 4, true); //so they can't close it
	player.getPackets().sendIComponentText(116, 2, "Hellion Rules");
	player.getPackets().sendIComponentText(116, 5,"These rules must be strictly followed: <br><br>" +
	"1)Advertising on/about other servers is not allowed<br>" +
	"2)Spamming in the fc or abusing yell command is not allowed<br>" +
	"3)Don't abuse bugs/glitches<br>" +
	"4)Don't scam/lure others<br>" +
	"5)Don't flame in local/fc/yell<br>" +
	"6)Respect the staff members<br>" +
	"7)Respect the other players<br>" +
	"8)Don't impersonate staff members<br>" +
	"9)Don't use macro's and third party software<br>" +
	"10)Account sharing is not allowed<br>" +
	"11)Obvious trolling can be punished<br>" +
	"12)Enjoy the game<br>");
	}
	/**
	  * custom record interface
	  **/
	 public static void sendRecordInterface(Player player) {
	 player.getInterfaceManager().sendInterface(3009);
	 player.getPackets().sendIComponentSprite(3009,23,27247);
	 player.getPackets().sendIComponentText(3009, 19, "Hellion Record's");
	 //
	 player.getPackets().sendIComponentText(3009,20, "Record");		 
	 player.getPackets().sendIComponentText(3009,21, "Amount");	
	 player.getPackets().sendIComponentText(3009,22, "Holder");	
	 //bandos kills
     player.getPackets().sendIComponentText(3009,1, "Most boss kills.");		 
	 player.getPackets().sendIComponentText(3009,13, ""+RecordHandler.getRecord().bossKills+"");	
	 player.getPackets().sendIComponentText(3009,7, ""+RecordHandler.getRecord().bossKillsHolder+"");	
	 //crystal keys used
	 player.getPackets().sendIComponentText(3009,2, "Most crystal chests opened.");		 
	 player.getPackets().sendIComponentText(3009,14, ""+RecordHandler.getRecord().getCrystalKeys()+"");	
	 player.getPackets().sendIComponentText(3009,8, ""+RecordHandler.getRecord().getCrystalKeyHouder()+"");	
	 //slayer tasks
	 player.getPackets().sendIComponentText(3009,3, "Most slayer tasks done.");		 
	 player.getPackets().sendIComponentText(3009,15, ""+RecordHandler.getRecord().slayerTasks+"");	
	 player.getPackets().sendIComponentText(3009,9, ""+RecordHandler.getRecord().slayerTasksHolder+"");	
	 //trivia points
	 player.getPackets().sendIComponentText(3009,4, "Most riddle points.");		 
	 player.getPackets().sendIComponentText(3009,16, ""+RecordHandler.getRecord().getRiddlePoints()+"");	
	 player.getPackets().sendIComponentText(3009,10, ""+RecordHandler.getRecord().getRiddleHolder()+"");	
	 //prestigeTokens
	 player.getPackets().sendIComponentText(3009,5, "Darklord prestige kill.");		 
	 player.getPackets().sendIComponentText(3009,17, RecordHandler.getRecord().darklordPrestige+"");	
	 player.getPackets().sendIComponentText(3009,11, RecordHandler.getRecord().darklordHolder+"");	
	 
	 //
	 player.getPackets().sendIComponentText(3009,6, "   ");		 
	 player.getPackets().sendIComponentText(3009,18, "   ");	
	 player.getPackets().sendIComponentText(3009,12, "   ");	
	 //
	 
	 }
	  public void sendTimerInterface(Player player) {
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 10 : 8, 3004);
		player.getPackets().sendIComponentText(3004, 5, "");
	    player.getPackets().sendIComponentText(3004, 6, "");
		player.getPackets().sendIComponentText(3004, 7, "");
		player.getPackets().sendIComponentText(3004, 8, "");
		player.getPackets().sendIComponentText(3004, 9, "");
		}
	/**
	  * Double exp ticket
	  **/
	 public void sendDoubleExpToken(Player player) {
     player.getInterfaceManager().sendInterface(1339);
	 player.getPackets().sendIComponentText(1339, 4, "With this ticket you'll get double exp for one hour.");
	 }
	/**
	  * Account information
	  **/
	public void PlayerInfo(Player player) {
	 player.getInterfaceManager().sendInterface(636);
	 player.getPackets().sendIComponentText(636,11, "Personal information.");
     player.getPackets().sendIComponentText(636,41, "Original name:");
     player.getPackets().sendIComponentText(636,43, "Account recovery settings: ");
     player.getPackets().sendIComponentText(636,44, "Bank pin set:");	
     player.getPackets().sendIComponentText(636,45, "Join date:");	 
	 player.getPackets().sendIComponentText(636,46, "Status:");
	 player.getPackets().sendIComponentText(636,47, "Personal information.");
	}
   /**
     * Duel slay option.
	 **/
	public void sendMod() {
        player.getInterfaceManager().sendInterface(1310);
		player.getPackets().sendIComponentText(1310,5, "You have been invited to a slayer group"); //main title
		player.getPackets().sendIComponentText(1310,6, "");
		player.getPackets().sendIComponentText(1310,7, "");
		player.getPackets().sendIComponentText(1310,8, "");
		player.getPackets().sendIComponentText(1310,9, "");
		player.getPackets().sendIComponentText(1310,10, "");
	}
	/**
	  * Achievements interface.
	  **/
	public void sendEasyTasks() {
	player.getInterfaceManager().sendInterface(1158);
		player.getPackets().sendIComponentText(1158, 74, "<img=7>Easy Achievements<img=7>"); 
		player.getPackets().sendIComponentText(1158, 8, "<col=CCFF00>#"); 
		player.getPackets().sendIComponentText(1158, 9, "<col=CCFF00>Task"); 
		player.getPackets().sendIComponentText(1158, 13, "<col=CCFF00>1");
		player.getPackets().sendIComponentText(1158, 18, "<col=CCFF00>2");
		player.getPackets().sendIComponentText(1158, 23, "<col=CCFF00>3");
		player.getPackets().sendIComponentText(1158, 28, "<col=CCFF00>4");
		player.getPackets().sendIComponentText(1158, 33, "<col=CCFF00>5");
		player.getPackets().sendIComponentText(1158, 38, "<col=CCFF00>6");
		player.getPackets().sendIComponentText(1158, 43, "<col=CCFF00>7");
		player.getPackets().sendIComponentText(1158, 48, "<col=CCFF00>8");
		player.getPackets().sendIComponentText(1158, 53, "<col=CCFF00>9");
		player.getPackets().sendIComponentText(1158, 11, "<col=CCFF00>Status"); 
		player.getPackets().sendIComponentText(1158, 10, "<col=CCFF00>Description"); 
		player.getPackets().sendIComponentText(1158, 15, "Cook at least 500 creatures."); 
		player.getPackets().sendIComponentText(1158, 16, ""+player.cookedFish+"/500");
		player.getPackets().sendIComponentText(1158, 21,  ""+player.MithrilOre+"/300");
		player.getPackets().sendIComponentText(1158, 20, "Mine at least 300 Mithril ores."); 
		player.getPackets().sendIComponentText(1158, 26, "");
		player.getPackets().sendIComponentText(1158, 25, ""); 
		player.getPackets().sendIComponentText(1158, 31, "");
		player.getPackets().sendIComponentText(1158, 30, ""); 
		player.getPackets().sendIComponentText(1158, 36, "");		
		player.getPackets().sendIComponentText(1158, 35, ""); 
		player.getPackets().sendIComponentText(1158, 19, "Gem finder"); 
		player.getPackets().sendIComponentText(1158, 14, "Cooking dummy"); 
		player.getPackets().sendIComponentText(1158, 24, ""); 
		player.getPackets().sendIComponentText(1158, 29, "");
		player.getPackets().sendIComponentText(1158, 34, "");
		player.getPackets().sendIComponentText(1158, 39, "");
		player.getPackets().sendIComponentText(1158, 40, "");
		player.getPackets().sendIComponentText(1158, 41, "");
		player.getPackets().sendIComponentText(1158, 44, "");
		player.getPackets().sendIComponentText(1158, 45, "");
		player.getPackets().sendIComponentText(1158, 46, "");
		player.getPackets().sendIComponentText(1158, 49, "");
		player.getPackets().sendIComponentText(1158, 50, "");
		player.getPackets().sendIComponentText(1158, 51, "");
		player.getPackets().sendIComponentText(1158, 54, "");
		player.getPackets().sendIComponentText(1158, 55, "");
		player.getPackets().sendIComponentText(1158, 56, "");
	}
	
	public void sendHouseLoading(final Player player) {
		player.lock();
		player.getPackets().sendWindowsPane(399, 0);
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.getInterfaceManager().sendFadingInterface(170);
				CoresManager.fastExecutor.schedule(new TimerTask() {
					@Override
					public void run() {
						try {
							player.unlock();
							sendInterfaces();
						} catch (Throwable e) {
							Logger.handle(e);
						}
					}
				}, 3500);
			}
			
		});
	}
	/** 
	  * Achievements interface.
	  **/

	/**
	  * A small box that appears when you complete a task.
	  **/
	public void doAchievementInterface(String achievementName) {
		handleAchievement(achievementName);
		player.getInterfaceManager().sendOverlay(1073, false);
		player.getPackets().sendIComponentText(1073, 10, "<col=00ff00>Achievement unlocked");
		player.getPackets().sendIComponentText(1073, 11, "<col=00ff00>" + Utils.formatPlayerNameForDisplay(achievementName));
		try {
			Thread.sleep(2000);
			player.getInterfaceManager().closeOverlay(false);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	public void handleAchievement(String Achievement) {
	  
		switch (Achievement.toLowerCase()) {
		}
	}
	
	public void sendHelp() {
		player.getInterfaceManager().sendInterface(1225);
		player.getPackets().sendIComponentText(1225, 5, 
				"Welcome! We are delighted you are here.");
		player.getPackets().sendIComponentText(1225, 21, 
				"If you are new to the area and would like to train, there are plenty of monsters around here!");
		player.getPackets().sendIComponentText(1225, 22, 
				"There are goblins, cows, and more around here! If you travel north of the castle and head down the trap door, you will find a sweet training area filled with Rock Crabs and more!");
		player.getPackets().sendIComponentText(1225, 20, "Onwards!");
	}
	public void sendTitels() {
	player.getInterfaceManager().sendInterface(583);
	player.getPackets().sendIComponentText(583, 14, "Title Menu. ");
	player.getPackets().sendIComponentText(583, 50, "The Bandos ");
	player.getPackets().sendIComponentText(583, 51, "The Armadyl ");
	player.getPackets().sendIComponentText(583, 52, "The Zamorak ");
	player.getPackets().sendIComponentText(583, 53, "The Saradomin ");
	player.getPackets().sendIComponentText(583, 54, "The Nex ");
	player.getPackets().sendIComponentText(583, 55, "The Corp ");
	player.getPackets().sendIComponentText(583, 56, "The Qbd ");
	player.getPackets().sendIComponentText(583, 57, "The Kbd ");
	player.getPackets().sendIComponentText(583, 58, "The nub ");    //500 boss kills
	player.getPackets().sendIComponentText(583, 59, "Started from the bottem still there");  //1000 boss kills
	player.getPackets().sendIComponentText(583, 71, "<col=298A08>Roflmatic ");   //1500 boss kills
	player.getPackets().sendIComponentText(583, 60, "<col=4169E1>No-Life ");   //2000 boss kills.
	player.getPackets().sendIComponentText(583, 61, "The Pvm Master "); //completed the pvm master quest
	player.getPackets().sendIComponentText(583, 62, "Started from the bottem still there ");
	player.getPackets().sendIComponentText(583, 64, "It's easy ");
	player.getPackets().sendIComponentText(583, 63, "Bug Abuser ");
	player.getPackets().sendIComponentText(583, 65, "#1 Pker ");
	player.getPackets().sendIComponentText(583, 70, "The 'skillhere' master ");
	player.getPackets().sendIComponentText(583, 66, "<shad=B00000><col=FF0000>R<col=FF8000>i<col=FFFF00>d<col=00FF00>e<col=00FFFF> M<col=0000FF>e<col=8000FF>e<col=FF0000>");
	player.getPackets().sendIComponentText(583, 67, "<col=4169E1>Sexy ");
	}

	public void sendRuneMysteries() {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Rune Mysteries");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to the</col> <col=660000>Duke</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=330099>who can be found in the </col><col=660000>Lumbridge Castle.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		if (player.RM == 1) {
		player.getPackets().sendIComponentText(275, 14, "<col=330099>I should take this talisman to the head wizard who</col>");
		player.getPackets().sendIComponentText(275, 15, "<col=330099>can be found at the </col><col=660000>Wizard's Guild.</col>");
		} else if (player.RM == 2 || player.RM == 3) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should take this talisman to the head wizard who</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>can be found at the </col><col=660000>Wizard's Guild.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<col=330099>I have given the talisman to the head wizard. Now I must deliver </col>");
		player.getPackets().sendIComponentText(275, 17, "<col=330099>this parcel to </col><col=660000>Aubury</col><col=330099> in Varrock.</col>");
		} else if (player.RM == 4) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should take this talisman to the head wizard who</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>can be found at the </col><col=660000>Wizard's Guild.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>I have given the talisman to the head wizard. Now I must deliver </col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>this parcel to </col><col=660000>Aubury</col><col=330099> in Varrock.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "");
		player.getPackets().sendIComponentText(275, 19, "QUEST COMPLETE!");
		}
		for (int i = 20; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	
	public void CompReq(){
	player.getInterfaceManager().sendInterface(987);
    player.getPackets().sendIComponentSprite(987,19, 13920);	
	player.getPackets().sendIComponentSprite(987,18, 13920);	
	player.getPackets().sendIComponentSprite(987,20, 13920);	
	player.getPackets().sendIComponentSprite(987,28, 13920);	
	//player.getPackets().sendIComponentText(623, 66, "Completionist Requirements");
	//player.getPackets().sendConfig(2396, 2);
	//player.getPackets().sendConfig(2396, 2);
	for (int i = 21; i < 310; i++) {
					player.getPackets().sendConfig(2396, i);
			}
	}
	
	
	public void sendErnestChicken() {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Ernest the Chicken");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to the</col> <col=660000>Veronica</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=330099>outside of the </col><col=660000>Draynor Manor.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		if (player.EC == 1) {
		player.getPackets().sendIComponentText(275, 14, "<col=330099>Veronica has told me her fiance has been lost</col>");
		player.getPackets().sendIComponentText(275, 15, "<col=330099>in the manor. I should expore the manor.</col>");
		} else if (player.EC == 2) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>Veronica has told me her fiance has been lost</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>in the manor. I should expore the manor.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<col=330099>Professor Oddenstein told me about Ernest </col>");
		player.getPackets().sendIComponentText(275, 17, "<col=330099>The gremlins stole his equipment and I</col>");
		player.getPackets().sendIComponentText(275, 18, "<col=330099>must retrieve the following:</col>");
		player.getPackets().sendIComponentText(275, 19, "<col=330099>1x Pressure Gauge</col>");
		player.getPackets().sendIComponentText(275, 20, "<col=330099>1x Rubber Tube</col>");
		player.getPackets().sendIComponentText(275, 21, "<col=330099>1x Oil Can</col>");
		player.getPackets().sendIComponentText(275, 22, "");
		player.getPackets().sendIComponentText(275, 23, "<col=330099>He also told me that the items will be scattered in the manor.</col>");
		} else if (player.EC == 3) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>Veronica has told me her fiance has been lost</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>in the manor. I should expore the manor.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>Professor Oddenstein told me about Ernest </col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>The gremlins stole his equipment and I</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<str><col=330099>must retrieve the following:</col></str>");
		player.getPackets().sendIComponentText(275, 19, "<str><col=330099>1x Pressure Gauge</col></str>");
		player.getPackets().sendIComponentText(275, 20, "<str><col=330099>1x Rubber Tube</col></str>");
		player.getPackets().sendIComponentText(275, 21, "<str><col=330099>1x Oil Can</col></str>");
		player.getPackets().sendIComponentText(275, 22, "");
		player.getPackets().sendIComponentText(275, 23, "<str><col=330099>He also told me that the items will be scattered in the manor.</col></str>");
		player.getPackets().sendIComponentText(275, 24, "");
		player.getPackets().sendIComponentText(275, 25, "QUEST COMPLETE!");
		}
		for (int i = 26; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	public void sendSkype() {
		player.getPackets().sendIComponentText(72, 55, "Skype 24/7 Support");
		player.getPackets().sendIComponentText(72, 31, "Create Skype");
		player.getPackets().sendIComponentText(72, 32, ""+Settings.SERVER_NAME+"' Group");
		player.getPackets().sendIComponentText(72, 33, "Mod: Apocalypse");
		player.getPackets().sendIComponentText(72, 34, "Admin: ");
		player.getPackets().sendIComponentText(72, 35, "Owner: Zero_gravity");
		player.getPackets().sendIComponentText(72, 36, "Download Skype");
		player.getPackets().sendIComponentText(72, 37, "Support: ");
		player.getPackets().sendIComponentText(72, 38, "Mod: ");
		player.getPackets().sendIComponentText(72, 39, "Admin: ");
		player.getPackets().sendIComponentText(72, 40, "Co-Owner: ");
		player.getInterfaceManager().sendInterface(72);
		player.getInventory().refresh();
	}
	
	public void sendDragonSlayer() {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Dragon Slayer");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to the</col> <col=660000>Guild Master</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=330099>in the </col><col=660000>Champion's Guild.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		if (player.DS == 1) {
		player.getPackets().sendIComponentText(275, 14, "<col=330099>I was told I should speak to</col> <col=660000>Oziach</col>");
		player.getPackets().sendIComponentText(275, 15, "<col=330099>located in the north-west of Edgeville.</col>");
		} else if (player.DS == 2) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I was told I should speak to</col> <col=660000>Oziach</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>located in the north-west of Edgeville.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<col=330099>I should speak with the <col=660000>Guildmaster</col><col=330099> again and find out about </col>");
		player.getPackets().sendIComponentText(275, 17, "<col=330099>how I can kill this dragon.</col>");
		} else if (player.DS == 3 || player.DS == 4) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I was told I should speak to</col> <col=660000>Oziach</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>located in the north-west of Edgeville.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>I should speak with the <col=660000>Guildmaster</col><col=330099> again and find out about </col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>how I can kill this dragon.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<col=330099>I must find the pieces of the map, once I place them together I should </col>");
		player.getPackets().sendIComponentText(275, 19, "<col=330099>find and purchase a ship. Lastly I need to find a captain that will </col>");
		player.getPackets().sendIComponentText(275, 20, "<col=330099>sail me to the island of <col=660000>Crandor</col><col=330099>.</col>");
		} else if (player.DS == 5 || player.DS == 6) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I was told I should speak to</col> <col=660000>Oziach</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>located in the north-west of Edgeville.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>I should speak with the <col=660000>Guildmaster</col><col=330099> again and find out about </col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>how I can kill this dragon.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<str><col=330099>I must find the pieces of the map, once I place them together I should </col></str>");
		player.getPackets().sendIComponentText(275, 19, "<str><col=330099>find and purchase a ship. Lastly I need to find a captain that will </col></str>");
		player.getPackets().sendIComponentText(275, 20, "<str><col=330099>sail me to the island of <col=660000>Crandor</col><col=330099>.</col></str>");
		player.getPackets().sendIComponentText(275, 21, "<col=330099>I should meet with <col=660000>Ned</col> <col=330099>on the ship.</col>");
		player.getPackets().sendIComponentText(275, 22, "<col=330099>I should find and slay Elvarg then bring his head to <col=660000>Oziach</col><col=330099>.<col=330099>.</col>");
		} else if (player.DS == 7) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I was told I should speak to</col> <col=660000>Oziach</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>located in the north-west of Edgeville.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>I should speak with the <col=660000>Guildmaster</col><col=330099> again and find out about </col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>how I can kill this dragon.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<str><col=330099>I find the pieces of the map, once I place them together I should </col></str>");
		player.getPackets().sendIComponentText(275, 19, "<str><col=330099>find and purchase a ship. Lastly I need to find a captain that will </col></str>");
		player.getPackets().sendIComponentText(275, 20, "<str><col=330099>sail me to the island of <col=660000>Crandor</col><col=330099>.</col></str>");
		player.getPackets().sendIComponentText(275, 21, "<str><col=330099>I should meet with <col=660000>Ned</col> <col=330099>on the ship.</col></str>");
		player.getPackets().sendIComponentText(275, 22, "<str><col=330099>I should find and slay Elvarg then bring his head to <col=660000>Oziach</col><col=330099>.<col=330099>.</col></str>");
		player.getPackets().sendIComponentText(275, 23, "");
		player.getPackets().sendIComponentText(275, 24, "QUEST COMPLETE!");
		}
		for (int i = 25; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	public void sendVampyreSlayer() {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Vampyre Slayer");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to </col> <col=660000>Morgan</col> <col=330099>in the </col><col=660000>Draynor Village.</col>");
		player.getPackets().sendIComponentText(275, 12, "");
		player.getPackets().sendIComponentText(275, 13, "");
		if (player.VS == 1) {
		player.getPackets().sendIComponentText(275, 14, "<col=330099>I should find</col> <col=660000>Dr. Harlow</col> <col=330099>who is located in the Blue Moon Inn.</col>");
		player.getPackets().sendIComponentText(275, 15, "<col=330099>He should have some information on getting rid of this vampyre.</col>");
		} else if (player.VS == 2) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should find</col> <col=660000>Dr. Harlow</col> <col=330099>who is located in the Blue Moon Inn.</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>He should have some information on getting rid of this vampyre.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<col=330099>Dr. Harlow requested a beer, I should go bring one to him.</col>");
		} else if (player.VS == 3) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should find</col> <col=660000>Dr. Harlow</col> <col=330099>who is located in the Blue Moon Inn.</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>He should have some information on getting rid of this vampyre.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>Dr. Harlow requested a beer, I should go bring one to him.</col></str>");
		player.getPackets().sendIComponentText(275, 17, "<col=330099>He has given me a stake and a stake hammer, now I can slay this vampyre.</col>");
		} else if (player.VS == 4) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should find</col> <col=660000>Dr. Harlow</col> <col=330099>who is located in the Blue Moon Inn.</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>He should have some information on getting rid of this vampyre.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>Dr. Harlow requested a beer, I should go bring one to him.</col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>He has given me a stake and a stake hammer, I can now find and slay this vampyre.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<col=330099>I should report back to Morgan and tell him the news.</col>");
		} else if (player.VS == 5) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should find</col> <col=660000>Dr. Harlow</col> <col=330099>who is located in the Blue Moon Inn.</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>He should have some information on getting rid of this vampyre.</col></str>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>Dr. Harlow requested a beer, I should go bring one to him.</col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>He has given me a stake and a stake hammer, I can now find and slay this vampyre.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<str><col=330099>I should report back to Morgan and tell him the news.</col></str>");
		player.getPackets().sendIComponentText(275, 19, "");
		player.getPackets().sendIComponentText(275, 20, "QUEST COMPLETE!");
		}
		
		for (int i = 21; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	public void sendLostCity() {
	player.getInterfaceManager().sendInterface(275);
	player.getPackets().sendIComponentText(275, 1, "Lost-City Quest");
	player.getPackets().sendIComponentText(275, 10, "");
	if (player.spokeToWarrior == false) {
	player.getPackets().sendIComponentText(275, 11, "Speak to the Warrior East of Draynor");
	player.getPackets().sendIComponentText(275, 12, "<u>Requirements</u>");
	player.getPackets().sendIComponentText(275, 13, "<col=ffff00>31 Crafting, 36 Woodcutting</col>");
	} else if (player.spokeToWarrior == true && player.spokeToShamus == false) {
	player.getPackets().sendIComponentText(275, 11, "Shamus appears to be in one of the trees around this location.");
	player.getPackets().sendIComponentText(275, 12, "The Warrior told me the tree displays 'Chop Tree'");
	player.getPackets().sendIComponentText(275, 13, "");
	} else if (player.spokeToWarrior == true && player.spokeToShamus == true) {
	player.getPackets().sendIComponentText(275, 11, "<str>Shamus appears to be in one of the trees around this location.</str>");
	player.getPackets().sendIComponentText(275, 12, "<str>The Warrior told me the tree displays 'Chop Tree'</str>");
	player.getPackets().sendIComponentText(275, 13, "");	
	}
	if (player.spokeToWarrior == true && player.spokeToShamus == true && player.spokeToMonk == false) {
	player.getPackets().sendIComponentText(275, 14, "I should go find the Monk of Entrana, Who is located at Port Sarim.");
	} else if (player.spokeToWarrior == true && player.spokeToShamus == true && player.spokeToMonk == true) {
	player.getPackets().sendIComponentText(275, 14, "<str>I should go find the Monk of Entrana, Who is located at Port Sarim.</str>");
	} else if (player.spokeToWarrior == false || player.spokeToShamus == false){
	player.getPackets().sendIComponentText(275, 14, "");
	}
	player.getPackets().sendIComponentText(275, 15, "");
	if (player.spokeToWarrior == true && player.spokeToShamus == true && player.spokeToMonk == true && player.lostCity == 0) {
	player.getPackets().sendIComponentText(275, 16, "The other side of Entrana is a ladder which leads to a cave");
	player.getPackets().sendIComponentText(275, 17, "I should go down the ladder and search for the dramen tree.");
	player.getPackets().sendIComponentText(275, 18, "In order to chop the dramen tree I must have a axe.");
	player.getPackets().sendIComponentText(275, 19, "The zombies must drop a axe of some sort.");
	player.getPackets().sendIComponentText(275, 20, "");
	player.getPackets().sendIComponentText(275, 21, "");
	} else if (player.lostCity == 1) {
	player.getPackets().sendIComponentText(275, 16, "<str>The other side of Entrana is a ladder which leads to a cave</str>");
	player.getPackets().sendIComponentText(275, 17, "<str>I should go down the ladder and search for the dramen tree.</str>");
	player.getPackets().sendIComponentText(275, 18, "<str>In order to chop the dramen tree I must have a axe.</str>");
	player.getPackets().sendIComponentText(275, 19, "<str>The zombies must drop a axe of some sort.</str>");
	player.getPackets().sendIComponentText(275, 20, "");
	player.getPackets().sendIComponentText(275, 21, "Congratulations Quest Complete!");
	} else {
	player.getPackets().sendIComponentText(275, 16, "");
	player.getPackets().sendIComponentText(275, 17, "");
	player.getPackets().sendIComponentText(275, 18, "");
	player.getPackets().sendIComponentText(275, 19, "");	
	player.getPackets().sendIComponentText(275, 20, "");
	player.getPackets().sendIComponentText(275, 21, "");
	for (int i = 22; i < 300; i++) {
		player.getPackets().sendIComponentText(275, i, "");
	}
	}
	}
	public static void sendPvmMasterComplete(final Player player) {
			player.questPoints += 3;
			player.getInterfaceManager().sendInterface(275);
			player.getPackets().sendIComponentText(275, 1, "Quest Complete!");
			player.getPackets().sendIComponentText(275, 10, "");
			player.getPackets().sendIComponentText(275, 11, "Congratulations you have completed the quest: Pvm Master");
			player.getPackets().sendIComponentText(275, 12, "Rewards: You can now do a challenge from the General.");
			player.getPackets().sendIComponentText(275, 13, "         You can now access the Celestial dragons.");
			player.getPackets().sendIComponentText(275, 14, "         A new title.");
			player.getPackets().sendIComponentText(275, 15, "");
			player.getPackets().sendIComponentText(275, 16, "You are awarded 3 Quest Points.");
			player.getPackets().sendIComponentText(275, 17, "Well Done!");
			player.getPackets().sendIComponentText(275, 18, "");
			player.getPackets().sendIComponentText(275, 19, "");
			player.getPackets().sendIComponentText(275, 20, "");
			player.getPackets().sendGameMessage(
					"<col=ff0000>You have completed quest: Pvm Master");
		}
	
	public static void sendLostCityComplete(final Player player) {
		if (player.lostCity == 0) {
			player.questPoints += 3;
			player.getInterfaceManager().sendInterface(275);
			player.getPackets().sendIComponentText(275, 1, "Quest Complete!");
			player.getPackets().sendIComponentText(275, 10, "");
			player.getPackets().sendIComponentText(275, 11, "Congratulations you have completed the quest: Lost City");
			player.getPackets().sendIComponentText(275, 12, "You may now purchase the dragon longsword,");
			player.getPackets().sendIComponentText(275, 13, "dragon dagger, and many other items.");
			player.getPackets().sendIComponentText(275, 14, "You may now access the lost city of Zanaris.");
			player.getPackets().sendIComponentText(275, 15, "You may now use the slayer master Chaeldar.");
			player.getPackets().sendIComponentText(275, 16, "You are awarded 3 Quest Points.");
			player.getPackets().sendIComponentText(275, 17, "Well Done!");
			player.getPackets().sendIComponentText(275, 18, "");
			player.getPackets().sendIComponentText(275, 19, "");
			player.getPackets().sendIComponentText(275, 20, "");
			player.lostCity = 1;
			player.getPackets().sendGameMessage(
					"<col=ff0000>You have completed quest: Lost City.");
		}
	}
	
	public static void sendRestlessStart(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 3);
		player.getPackets().sendIComponentText(275, 1, "Restless Ghost");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to </col> <col=660000>Father Aereck</col> <col=330099>in the</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=660000>church</col> <col=330099>outside of the</col> <col=660000>Lumbridge Castle.</col>");
		for (int i = 13; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	public static void handleRestlessCompletedInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 10);
		player.getPackets().sendIComponentText(275, 1, "Restless Ghost");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to </col> <col=660000>Father Aereck</col> <col=330099>in the</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=660000>church</col> <col=330099>outside of the</col> <col=660000>Lumbridge Castle.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should find</col> <col=660000>Father Urhney</col> <col=330099>who is located in Lumbridge Swamp.</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>He should have some information on getting rid of this ghost.</col></str>");	
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>Now that I have this ghost amulet, I should check back in with </col> <col=660000>Father Aereck</col><col=330099>.</col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>I should try checking out this ghost Father Aereck is talking about.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<str><col=330099>I now need to find this so called skull. I should look around the Lumbridge Swamp.</col></str>");
		player.getPackets().sendIComponentText(275, 19, "<str><col=330099>When I find this I should place the skull on the coffin.</col></str>");
		player.getPackets().sendIComponentText(275, 20, "");
		player.getPackets().sendIComponentText(275, 21, "<col=660000>QUEST COMPLETE</col>");
	}
	
	public void handleVampyreQuestInterface() {
		player.getInterfaceManager().sendInterface(277);
		player.questPoints += 2;
		player.getInventory().addItem(24155, 1);
		player.getInventory().addItemMoneyPouch(new Item(995, 15000));
		player.getSkills().addXp(Skills.SLAYER, 250);
		player.getSkills().addXp(Skills.STRENGTH, 450);
		player.getPackets().sendIComponentText(277, 4, "You have completed Vampyre Slayer Quest.");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "2 Quest Points");
		player.getPackets().sendIComponentText(277, 11, "Slayer XP");
		player.getPackets().sendIComponentText(277, 12, "Strength XP");
		player.getPackets().sendIComponentText(277, 13, "15,000 Coins");
		player.getPackets().sendIComponentText(277, 14, "");
		player.getPackets().sendIComponentText(277, 15, "Two spins on the Squeal of Fortune");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 1549, 1);
		player.getPackets().sendGameMessage("Congratulations! You have completed the Vampyre Slayer quest!");
	}
	
	public void sendRuneComplete() {
		player.getInterfaceManager().sendInterface(277);
		player.questPoints += 2;
		player.getInventory().addItem(24155, 1);
		player.getInventory().addItemMoneyPouch(new Item(995, 20000));
		player.getSkills().addXp(Skills.RUNECRAFTING, 300);
		player.getPackets().sendIComponentText(277, 4, "You have completed Rune Mysteries Quest.");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "2 Quest Points");
		player.getPackets().sendIComponentText(277, 11, "20,000 Coins");
		player.getPackets().sendIComponentText(277, 12, "Runecrafting XP");
		player.getPackets().sendIComponentText(277, 13, "Ability to mine Rune and Pure Essence");
		player.getPackets().sendIComponentText(277, 14, "");
		player.getPackets().sendIComponentText(277, 15, "Two spins on the Squeal of Fortune");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 1436, 1);
		player.getPackets().sendGameMessage("Congratulations! You have completed the Rune Mysteries quest!");
	}
	
	public void sendErnestComplete() {
		player.getInterfaceManager().sendInterface(277);
		player.questPoints += 1;
		player.getInventory().addItem(24155, 1);
		player.getInventory().addItemMoneyPouch(new Item(995, 10000));
		player.getPackets().sendIComponentText(277, 4, "You have completed Ernest the Chicken Quest.");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "1 Quest Point");
		player.getPackets().sendIComponentText(277, 11, "10,000 Coins");
		player.getPackets().sendIComponentText(277, 12, "");
		player.getPackets().sendIComponentText(277, 13, "");
		player.getPackets().sendIComponentText(277, 14, "");
		player.getPackets().sendIComponentText(277, 15, "Two spins on the Squeal of Fortune");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 314, 1);
		player.getPackets().sendGameMessage("Congratulations! You have completed the Ernest the Chicken quest!");
	}
	
	public void handleDragonQuestInterface() {
		player.getInterfaceManager().sendInterface(277);
		player.questPoints += 4;
		player.getInventory().addItem(24155, 1);
		player.getInventory().addItemMoneyPouch(new Item(995, 125000));
		player.getSkills().addXp(Skills.STRENGTH, 750);
		player.getSkills().addXp(Skills.DEFENCE, 600);
		player.getPackets().sendIComponentText(277, 4, "You have completed Dragon Slayer Quest.");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "4 Quest Points");
		player.getPackets().sendIComponentText(277, 11, "Strength XP");
		player.getPackets().sendIComponentText(277, 12, "Defence XP");
		player.getPackets().sendIComponentText(277, 13, "125,000 Coins");
		player.getPackets().sendIComponentText(277, 14, "");
		player.getPackets().sendIComponentText(277, 15, "Two spins on the Squeal of Fortune");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 11279, 1);
		player.getPackets().sendGameMessage("Congratulations! You have completed the Dragon Slayer quest!");
	}
	
	public static void handleRestlessProgress(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 10);
		player.getPackets().sendIComponentText(275, 1, "Restless Ghost");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to </col> <col=660000>Father Aereck</col> <col=330099>in the</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=660000>church</col> <col=330099>outside of the</col> <col=660000>Lumbridge Castle.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		if (player.RG <= 2) {
		player.getPackets().sendIComponentText(275, 14, "<col=330099>I should find</col> <col=660000>Father Urhney</col> <col=330099>who is located in Lumbridge Swamp.</col>");
		player.getPackets().sendIComponentText(275, 15, "<col=330099>He should have some information on getting rid of this ghost.</col>");
		} else if (player.RG == 3) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should find</col> <col=660000>Father Urhney</col> <col=330099>who is located in Lumbridge Swamp.</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>He should have some information on getting rid of this ghost.</col></str>");	
		player.getPackets().sendIComponentText(275, 16, "<col=330099>Now that I have this ghost amulet, I should check back in with </col> <col=660000>Father Aereck</col><col=330099>.</col>");
		player.getPackets().sendIComponentText(275, 17, "<col=330099>I should try checking out this ghost Father Aereck is talking about.</col>");
		} else if (player.RG == 4) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should find</col> <col=660000>Father Urhney</col> <col=330099>who is located in Lumbridge Swamp.</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>He should have some information on getting rid of this ghost.</col></str>");	
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>Now that I have this ghost amulet, I should check back in with </col> <col=660000>Father Aereck</col><col=330099>.</col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>I should try checking out this ghost Father Aereck is talking about.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<col=330099>I now need to find this so called skull. I should look around the Lumbridge Swamp.</col>");
		player.getPackets().sendIComponentText(275, 19, "<col=330099>When I find this I should talk to the ghost again.</col>");
		} else if (player.RG == 5) {
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>I should find</col> <col=660000>Father Urhney</col> <col=330099>who is located in Lumbridge Swamp.</col></str>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>He should have some information on getting rid of this ghost.</col></str>");	
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>Now that I have this ghost amulet, I should check back in with </col> <col=660000>Father Aereck</col><col=330099>.</col></str>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>I should try checking out this ghost Father Aereck is talking about.</col></str>");
		player.getPackets().sendIComponentText(275, 18, "<str><col=330099>I now need to find this so called skull. I should look around the Lumbridge Swamp.</col></str>");
		player.getPackets().sendIComponentText(275, 19, "<col=330099>When I find this I should place the skull on the coffin.</col>");
		}
		
		for (int i = 20; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	public static void handleRestlessCompleted(final Player player) {
		player.questPoints += 2;
		player.getSkills().addXp(Skills.PRAYER, 1500);
			player.getInventory().addItemMoneyPouch(new Item(995, 15000));
		player.getPackets().sendConfig(29, 2);
		player.getPackets().sendConfig(101, player.questPoints); // Quest Points
		player.getInterfaceManager().sendInterfaces();
		player.getPackets().sendUnlockIComponentOptionSlots(190, 15, 0, 201, 0, 1, 2, 3);
	}
	
	public static void handleRestlessCompleteInterface(final Player player) {
		player.getInterfaceManager().sendInterface(277);
		player.getPackets().sendIComponentText(277, 4, "You have completed Restless Ghost.");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "2 Quest Points");
		player.getPackets().sendIComponentText(277, 11, "1500 Cooking XP");
		player.getPackets().sendIComponentText(277, 12, "15000 Coins");
		player.getPackets().sendIComponentText(277, 13, "A Ghost Speak Amulet");
		player.getPackets().sendIComponentText(277, 14, "Two spins on the Squeal of Fortune");
		player.getPackets().sendIComponentText(277, 15, "");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 552, 1);
		player.getPackets().sendGameMessage("Congratulations! You have completed the Restless Ghost quest!");
	}
	
	public void sendItem(String name, String guide, int id, String street, String uses) {
		player.getInterfaceManager().sendInterface(277);
		player.getPackets().sendIComponentText(277, 3, "Item: "+name+"");
		player.getPackets().sendIComponentText(277, 4, "Item Information");
		player.getPackets().sendIComponentText(277, 6, "Item ID:");
		player.getPackets().sendIComponentText(277, 7, ""+id+"");
		player.getPackets().sendIComponentText(277, 9, "How to Obtain:");
		player.getPackets().sendIComponentText(277, 10, guide);
		player.getPackets().sendIComponentText(277, 11, "");
		player.getPackets().sendIComponentText(277, 12, "Uses:");
		player.getPackets().sendIComponentText(277, 13, uses);
		player.getPackets().sendIComponentText(277, 14, "");
		player.getPackets().sendIComponentText(277, 15, "Street Price (average):");
		player.getPackets().sendIComponentText(277, 16, ""+street+"gp");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, id, 1);
	}
	
	/*public void sendSlayerShop() {
		player.getInterfaceManager().sendInterface(164);
			player.getPackets().sendItemOnIComponent(164, 27, 22530, 1); //Full slayer helm
			player.getPackets().sendItemOnIComponent(164, 30, 15220, 1); //bers ring
			player.getPackets().sendItemOnIComponent(164, 38, 10551, 1); //Fighter torso
			player.getPackets().sendItemOnIComponent(164, 40, 12862, 1); //Drag Slayer Gloves
			player.getPackets().sendItemOnIComponent(164, 29, 15520, 1);
		        player.getPackets().sendIComponentText(164, 20,  " "+ player.slayerPoints +" ");
		        player.getPackets().sendIComponentText(164, 26,  "Full Slayer Helm (e)");
		        player.getPackets().sendIComponentText(164, 28,  "Berserker Ring (i)");
		        player.getPackets().sendIComponentText(164, 37,  "Fighter Torso");
		        player.getPackets().sendIComponentText(164, 39,  "Dragon Slayer Gloves");
		        player.getPackets().sendIComponentText(164, 31,  "Slayer Points:");
		        player.getPackets().sendIComponentText(164, 32,  "50 Points");
		        player.getPackets().sendIComponentText(164, 33,  "4000 Points");
		        player.getPackets().sendIComponentText(164, 36,  "3500 Points");
		        player.getPackets().sendIComponentText(164, 34,  "3200 Points");
		        player.getPackets().sendIComponentText(164, 35,  "3000 Points");
		        player.getPackets().sendIComponentText(164, 22,  "More");
		        player.getPackets().sendIComponentText(164, 23,  "Coming Soon");
		    }

		public void sendSlayerShop2() {
		player.getInterfaceManager().sendInterface(378);
			player.getPackets().sendItemOnIComponent(378, 92, 22374, 1); //gorilla
			player.getPackets().sendItemOnIComponent(378, 93, 995, 1);
			player.getPackets().sendItemOnIComponent(378, 94, 995, 1);
			player.getPackets().sendItemOnIComponent(378, 95, 995, 1);
			player.getPackets().sendItemOnIComponent(378, 96, 995, 1);
			player.getPackets().sendItemOnIComponent(378, 102, 24317, 1); //Monkey Cape
			player.getPackets().sendItemOnIComponent(378, 103, 18337, 1); //bonecrusher
			player.getPackets().sendItemOnIComponent(378, 104, 18337, 1);
			player.getPackets().sendItemOnIComponent(378, 105, 19675, 1); //herbicide
			player.getPackets().sendItemOnIComponent(378, 101, 4212, 1); // crystal bow
		        player.getPackets().sendIComponentText(378, 79,  " "+ player.slayerPoints +" ");
		        player.getPackets().sendIComponentText(378, 82,  "Coming Soon.");
		        player.getPackets().sendIComponentText(378, 81,  "More");
		        player.getPackets().sendIComponentText(378, 83,  "BoneCrusher.");
		        player.getPackets().sendIComponentText(378, 90,  "4500 Points.");
		        player.getPackets().sendIComponentText(378, 87,  "Herbicide.");
		        player.getPackets().sendIComponentText(378, 99,  "4500 Points.");
		        player.getPackets().sendIComponentText(378, 88,  "Crystal Bow.");
		        player.getPackets().sendIComponentText(378, 100,  "3700 Points.");
		        player.getPackets().sendIComponentText(378, 84,  "Gorilla Mask.");
		        player.getPackets().sendIComponentText(378, 91,  "2500 Points.");
		        player.getPackets().sendIComponentText(378, 85,  "1mil Coins.");
		        player.getPackets().sendIComponentText(378, 97,  "150 Points.");
		        player.getPackets().sendIComponentText(378, 86,  "Monkey Cape.");
		        player.getPackets().sendIComponentText(378, 98,  "5000 Points.");
		    }*/
		
		public void sendQuestTab() {
			sendTab(resizableScreen ? 114 : 190, 190);
			player.getPackets().sendIComponentText(190, 1, "Free Quests");
			player.getPackets().sendIComponentText(190, 2, "Quest 1");
			}


	public void sendInventoryInterface(int childId) {
		player.getPackets().sendInterface(false,
				resizableScreen ? RESIZABLE_WINDOW_ID : FIXED_WINDOW_ID,
				resizableScreen ? RESIZABLE_INV_TAB_ID : FIXED_INV_TAB_ID,
				childId);
	}
	
	public void sendInvasionShop() {
		player.getInterfaceManager().sendInterface(267);
			player.getPackets().sendIComponentText(267, 93, "Dark Invasion Rewards (Items will be banked)");
			player.getPackets().sendHideIComponent(267, 101, true);
			player.getPackets().sendIComponentText(267, 105, "You have: " + player.getPlayerData().getInvasionPoints() + " points.");
			player.getPackets().sendHideIComponent(267, 104, true);
		/*
	    OPTION NAMES
		*/
			player.getPackets().sendIComponentText(267, 10, "Attack Xp");
			player.getPackets().sendIComponentText(267, 11, "Strength Xp");
			player.getPackets().sendIComponentText(267, 12, "Defence Xp");
			player.getPackets().sendIComponentText(267, 13, "Range Xp");
			player.getPackets().sendIComponentText(267, 14, "Magic Xp");
			player.getPackets().sendIComponentText(267, 15, "Const. Xp");
			player.getPackets().sendIComponentText(267, 16, "Prayer Xp");
			player.getPackets().sendIComponentText(267, 32, "Herbs");
			player.getPackets().sendIComponentText(267, 47, "Ores");
			player.getPackets().sendIComponentText(267, 33, "Seeds");
			player.getPackets().sendIComponentText(267, 28, "Void Knight Mace");
			player.getPackets().sendIComponentText(267, 29, "Void Knight Top");
			player.getPackets().sendIComponentText(267, 30, "Void Knight Bottom");
			player.getPackets().sendIComponentText(267, 31, "Void Knight Gloves");
			player.getPackets().sendIComponentText(267, 63, "Void Mage Helm");
			player.getPackets().sendIComponentText(267, 64, "Void Ranger Helm");
			player.getPackets().sendIComponentText(267, 65, "Void Melee Helm");
			player.getPackets().sendIComponentText(267, 66, "Void Commendations");
			
		/*
            ATTACK XP
		*/
			player.getPackets().sendIComponentText(267, 34, "1X");
			player.getPackets().sendIComponentText(267, 49, "5X");
			player.getPackets().sendIComponentText(267, 56, "10X");
		/*
             STRENGTH XP
		*/
			player.getPackets().sendIComponentText(267, 35, "1X");
			player.getPackets().sendIComponentText(267, 50, "5X");
			player.getPackets().sendIComponentText(267, 57, "10X");
		/*
             DEFENCE XP
		*/
			player.getPackets().sendIComponentText(267, 36, "1X");
			player.getPackets().sendIComponentText(267, 51, "5X");
			player.getPackets().sendIComponentText(267, 58, "10X");
		
		/*
              RANGE XP
		*/
			player.getPackets().sendIComponentText(267, 37, "1X");
			player.getPackets().sendIComponentText(267, 52, "5X");
			player.getPackets().sendIComponentText(267, 59, "10X");
		
		/*
              MAGIC XP
		*/
			player.getPackets().sendIComponentText(267, 38, "1X");
			player.getPackets().sendIComponentText(267, 53, "5X");
			player.getPackets().sendIComponentText(267, 60, "10X");
		
		/*
            HITPOINTS XP
		*/
			player.getPackets().sendIComponentText(267, 39, "1X");
			player.getPackets().sendIComponentText(267, 54, "5X");
			player.getPackets().sendIComponentText(267, 61, "10X");
		/*
             PRAYER XP
		*/
			player.getPackets().sendIComponentText(267, 40, "1X");
			player.getPackets().sendIComponentText(267, 55, "5X");
			player.getPackets().sendIComponentText(267, 62, "10X");
		/*
	       HERB
		*/
			player.getPackets().sendIComponentText(267, 45, "Buy");
		/*
		ORE
		*/
			player.getPackets().sendIComponentText(267, 46, "Buy");
		/*
	       SEEDS
		*/
			player.getPackets().sendIComponentText(267, 48, "Buy");
		/*
	       VOID
		*/
			player.getPackets().sendIComponentText(267, 41, "Buy(500)");//Void Knight Mace
			player.getPackets().sendIComponentText(267, 42, "Buy(500)");//Void Knight Top
			player.getPackets().sendIComponentText(267, 43, "Buy(500)");//Void Knight Robe
			player.getPackets().sendIComponentText(267, 44, "Buy(500)");//Void Knight Gloves
			player.getPackets().sendIComponentText(267, 67, "Buy(500)");//Void Mage Helm
			player.getPackets().sendIComponentText(267, 68, "Buy(500)");//Void Range Helm
			player.getPackets().sendIComponentText(267, 69, "Buy(500)");//Void Melee Helm
			player.getPackets().sendIComponentText(267, 70, "Buy");//Void Commendations
		/*
	   CHARMS NAME		
	        */
			player.getPackets().sendIComponentText(267, 71, "Not Available");
			player.getPackets().sendIComponentText(267, 72, "Not Available");
			player.getPackets().sendIComponentText(267, 73, "Not Available");
			player.getPackets().sendIComponentText(267, 74, "Not Available");
	}

	public final void sendInterfaces() {
		if (player.getDisplayMode() == 2 || player.getDisplayMode() == 3) {
			resizableScreen = true;
			sendFullScreenInterfaces();
		} else {
			resizableScreen = false;
			sendFixedInterfaces();
		}
		player.getSkills().sendInterfaces();
		player.getCombatDefinitions().sendUnlockAttackStylesButtons();
		player.getMusicsManager().unlockMusicPlayer();
		player.getEmotesManager().unlockEmotesBook();
		player.getInventory().unlockInventoryOptions();
		player.getPrayer().unlockPrayerBookButtons();
		ClansManager.unlockBanList(player);
		if (player.getFamiliar() != null && player.isRunning())
			player.getFamiliar().unlock();
		player.getControlerManager().sendInterfaces();
	}

	public void replaceRealChatBoxInterface(int interfaceId) {
		player.getPackets().sendInterface(true, 752, 11, interfaceId);
	}

	public void closeReplacedRealChatBoxInterface() {
		player.getPackets().closeInterface(752, 11);
	}

	public void sendWindowPane() {
		player.getPackets().sendWindowsPane(resizableScreen ? 746 : 548, 0);
	}
	
	public void sendFullScreenInterfaces() {
		player.getPackets().sendWindowsPane(746, 0);
		/*if (World.getboughtExp() > 0) {
		player.getPackets().sendIComponentText(746, 17, "Double exp" /*+(World.getboughtExp() - Utils.currentTimeMillis()) / 60000 +min.");
		player.getPackets().sendIComponentText(746, 183, "by:"+World.getEventPlayer()+"");
		} else {
		player.getPackets().sendIComponentText(746, 17, "No active event atm.");	
		}*/
		
		sendTab(21, 752);
		sendTab(22, 751);
		sendTab(15, 745);
		sendTab(25, 754);
		sendTab(195, 748); 
		sendTab(196, 749);
		sendTab(197, 750);
		sendTab(198, 747); 
		player.getPackets().sendInterface(true, 752, 9, 137);
		sendCombatStyles();
		sendPlayerSupport();
		sendSkills();
		sendPlayerCustom();
		sendTimerInterface(player);
		sendInventory();
		sendEquipment();
		sendPrayerBook();
		sendMagicBook();
		sendTab(120, 550); // friend list
		sendTab(121, 1109); // 551 ignore now friendchat
		sendTab(122, 1110); // 589 old clan chat now new clan chat
		sendSettings();
		sendEmotes();
		sendTab(125, 187); // music
		sendTab(126, 34); // notes
		
		//sendPlayerCustom1();
		sendTab(129, 182); // logout*/
		sendSquealOfFortuneTab();
	}
	
	public void sendFixedInterfaces() {
		player.getPackets().sendWindowsPane(548, 0);
		sendTab(161, 752);
		sendTab(37, 751);
		sendTab(23, 745);
		sendTab(25, 754);
		sendTab(155, 747); 
		//sendPlayerCustom1();
		sendTab(151, 748);
		sendTab(152, 749);
		sendTab(153, 750);
		player.getPackets().sendInterface(true, 752, 9, 137);
		player.getPackets().sendInterface(true, 548, 9, 167);
		sendMagicBook();
		sendPrayerBook();
		sendEquipment();
		sendInventory();
		//InfoTab.sendInterface(player);
		sendPlayerCustom();
		sendTimerInterface(player);
		sendTab(181, 1109);// 551 ignore now friendchat
		sendTab(182, 1110);// 589 old clan chat now new clan chat
		sendTab(180, 550);// friend list
		sendTab(185, 187);// music
		sendTab(186, 34); // notes
		sendTab(189, 182);
		sendSkills();
		sendEmotes();
		sendSettings();
		sendPlayerSupport();
		sendCombatStyles();
		sendSquealOfFortuneTab();
	}
	
    public void sendSquealOfFortuneTab() {
	player.getSquealOfFortune().sendSpinCounts();
	player.getPackets().sendGlobalConfig(823, 1); // this config used in cs2 to tell current extra tab type (0 - none, 1 - sof, 2 - armies minigame tab)
	setWindowInterface(resizableScreen ? 119 : 179, 1139);
    }

    public void closeSquealOfFortuneTab() {
	removeWindowInterface(resizableScreen ? 119 : 179);
	player.getPackets().sendGlobalConfig(823, 0); // this config used in cs2 to tell current extra tab type (0 - none, 1 - sof, 2 - armies minigame tab)
    }

	public void sendXPPopup() {
		sendTab(resizableScreen ? 38 : 10, 1213); //xp 
	}
	
	public void sendXPDisplay() {
		sendXPDisplay(1215);  //xp counter
	}
	
	public void sendXPDisplay(int interfaceId) {
		sendTab(resizableScreen ? 27 : 29, interfaceId);  //xp counter
	}
	
	public void closeXPPopup() {
		player.getPackets().closeInterface(resizableScreen ? 38 : 10);
	}
	
	public void closeXPDisplay() {
		player.getPackets().closeInterface(resizableScreen ? 27 : 29);
	}
	
	public void sendEquipment() {
		sendTab(resizableScreen ? 116 : 176, 387);
	}
	
	public void closeInterface(int one, int two) {
		player.getPackets().closeInterface(resizableScreen ? two : one);
	}

	public void closeEquipment() {
		player.getPackets().closeInterface(resizableScreen ? 116 : 176);
	}

	public void sendInventory() {
		sendTab(resizableScreen ? 115 : 175, Inventory.INVENTORY_INTERFACE);
	}

	public void closeInventory() {
		player.getPackets().closeInterface(resizableScreen ? 115 : 175);
	}
	
	public void closeSkills() {
		player.getPackets().closeInterface(resizableScreen ? 113 : 173);
	}
	
	public void closeCombatStyles() {
		player.getPackets().closeInterface(resizableScreen ? 111 : 171);
	}
	
	public void closePlayerSupport() {
		player.getPackets().closeInterface(resizableScreen ? 112 : 205);
	}
	
	public void sendCombatStyles() {
		sendTab(resizableScreen ? 111 : 171, 884);
	}
	//tzst the wealth shit

	public void sendPlayerSupport() {
	sendTab(resizableScreen ? 112 : 172, 3032); //3018
	}
	
	//custom teleports
	
	
	public void sendTrainingTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Training Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 55,  "Bosses");
		player.getPackets().sendIComponentText(813, 56,  "Slayer/Dungeons");
		player.getPackets().sendIComponentText(813, 60,  "Training");
		player.getPackets().sendIComponentText(813, 57,  "Skilling");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendGeneralTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Bossing");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 52,  "Godwars");
		player.getPackets().sendIComponentText(813, 53,  "sunfreet");
		player.getPackets().sendIComponentText(813, 54,  "blink");
		player.getPackets().sendIComponentText(813, 55,  "yk");
		player.getPackets().sendIComponentText(813, 56,  "leeuni");
		player.getPackets().sendIComponentText(813, 60,  "darklord");
		player.getPackets().sendIComponentText(813, 57,  "QBD");
		player.getPackets().sendIComponentText(813, 58,  "KBD");
		player.getPackets().sendIComponentText(813, 59,  "Back to menu...");
		player.getPackets().sendIComponentText(813, 61,  "Next Page...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendGeneralTeleports2() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "bosses page 2");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 52,  "KQ");
		player.getPackets().sendIComponentText(813, 53,  "STQ");
		player.getPackets().sendIComponentText(813, 54,  "Daganoth Kings");
		player.getPackets().sendIComponentText(813, 55,  "Nex");
		player.getPackets().sendIComponentText(813, 56,  "World Gorger");
		player.getPackets().sendIComponentText(813, 60,  "Rise of six");
		player.getPackets().sendIComponentText(813, 57,  "avaryss");
		player.getPackets().sendIComponentText(813, 58,  "coming soon");
		player.getPackets().sendIComponentText(813, 59,  "coming soon");
		player.getPackets().sendIComponentText(813, 61,  "Back...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendDungeonTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Slayer/Dungeon");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 52,  "fremmnik dungeon");
		player.getPackets().sendIComponentText(813, 53,  "slayer tower");
		player.getPackets().sendIComponentText(813, 54,  "kuradels dung");
		player.getPackets().sendIComponentText(813, 55,  "pollypore dung");
		player.getPackets().sendIComponentText(813, 56,  "smoke dungeon");
		player.getPackets().sendIComponentText(813, 60,  "Celestial dragons");
		player.getPackets().sendIComponentText(813, 57,  "Ascension");
		player.getPackets().sendIComponentText(813, 58,  "Runeite dragons");
		player.getPackets().sendIComponentText(813, 59,  "more to come");
		player.getPackets().sendIComponentText(813, 61,  "Back to menu...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendSlayernTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Slayer Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 52,  "Option");
		player.getPackets().sendIComponentText(813, 53,  "Option");
		player.getPackets().sendIComponentText(813, 54,  "Option");
		player.getPackets().sendIComponentText(813, 55,  "Option");
		player.getPackets().sendIComponentText(813, 56,  "Option");
		player.getPackets().sendIComponentText(813, 60,  "Option");
		player.getPackets().sendIComponentText(813, 57,  "Option");
		player.getPackets().sendIComponentText(813, 58,  "Option");
		player.getPackets().sendIComponentText(813, 59,  "Option");
		player.getPackets().sendIComponentText(813, 61,  "Back to menu...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendWildernessTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Wilderness Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 52,  "Rock crabs");
		player.getPackets().sendIComponentText(813, 53,  "Yaks");
		player.getPackets().sendIComponentText(813, 54,  "Cows");
		player.getPackets().sendIComponentText(813, 55,  "Skeletal wyverns");
		player.getPackets().sendIComponentText(813, 56,  "Adamant dragons");
		player.getPackets().sendIComponentText(813, 60,  "frost dragons");
		player.getPackets().sendIComponentText(813, 57,  "brimhaven dungeon");
		player.getPackets().sendIComponentText(813, 58,  "automations");
		player.getPackets().sendIComponentText(813, 59,  "more coming!");
		player.getPackets().sendIComponentText(813, 61,  "Back to menu...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendBossingTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Training Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 45,  "High Level");
		player.getPackets().sendIComponentText(813, 46,  "Medium Level");
		player.getPackets().sendIComponentText(813, 47,  "Low Level");
		player.getPackets().sendIComponentText(813, 48,  "Team");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendHighBossTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "High Boss Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 43,  "Option");
		player.getPackets().sendIComponentText(813, 44,  "Option");
		player.getPackets().sendIComponentText(813, 45,  "Option");
		player.getPackets().sendIComponentText(813, 46,  "Option");
		player.getPackets().sendIComponentText(813, 47,  "Option");
		player.getPackets().sendIComponentText(813, 48,  "Option");
		player.getPackets().sendIComponentText(813, 49,  "Option");
		player.getPackets().sendIComponentText(813, 50,  "Option");
		player.getPackets().sendIComponentText(813, 51,  "Back to menu...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendMedBossTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Med Boss Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 43,  "Option");
		player.getPackets().sendIComponentText(813, 44,  "Option");
		player.getPackets().sendIComponentText(813, 45,  "Option");
		player.getPackets().sendIComponentText(813, 46,  "Option");
		player.getPackets().sendIComponentText(813, 47,  "Option");
		player.getPackets().sendIComponentText(813, 48,  "Option");
		player.getPackets().sendIComponentText(813, 49,  "Option");
		player.getPackets().sendIComponentText(813, 50,  "Option");
		player.getPackets().sendIComponentText(813, 51,  "Back to menu...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendLowBossTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Low Boss Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 43,  "Option");
		player.getPackets().sendIComponentText(813, 44,  "Option");
		player.getPackets().sendIComponentText(813, 45,  "Option");
		player.getPackets().sendIComponentText(813, 46,  "Option");
		player.getPackets().sendIComponentText(813, 47,  "Option");
		player.getPackets().sendIComponentText(813, 48,  "Option");
		player.getPackets().sendIComponentText(813, 49,  "Option");
		player.getPackets().sendIComponentText(813, 50,  "Option");
		player.getPackets().sendIComponentText(813, 51,  "Back to menu...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendTeamBossTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Team Boss Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 43,  "Option");
		player.getPackets().sendIComponentText(813, 44,  "Option");
		player.getPackets().sendIComponentText(813, 45,  "Option");
		player.getPackets().sendIComponentText(813, 46,  "Option");
		player.getPackets().sendIComponentText(813, 47,  "Option");
		player.getPackets().sendIComponentText(813, 48,  "Option");
		player.getPackets().sendIComponentText(813, 49,  "Option");
		player.getPackets().sendIComponentText(813, 50,  "Option");
		player.getPackets().sendIComponentText(813, 51,  "Back to menu...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendSkillingTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Skilling Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 37,  "Option");
		player.getPackets().sendIComponentText(813, 38,  "Option");
		player.getPackets().sendIComponentText(813, 39,  "Option");
		player.getPackets().sendIComponentText(813, 40,  "Option");
		player.getPackets().sendIComponentText(813, 41,  "Option");
		player.getPackets().sendIComponentText(813, 42,  "Option");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendRunecraftingTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Runecrafting Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 37,  "Option");
		player.getPackets().sendIComponentText(813, 38,  "Option");
		player.getPackets().sendIComponentText(813, 39,  "Option");
		player.getPackets().sendIComponentText(813, 40,  "Option");
		player.getPackets().sendIComponentText(813, 41,  "Option");
		player.getPackets().sendIComponentText(813, 42,  "Option");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendAgilityTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Agility Teleports");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 37,  "Option");
		player.getPackets().sendIComponentText(813, 38,  "Option");
		player.getPackets().sendIComponentText(813, 39,  "Option");
		player.getPackets().sendIComponentText(813, 40,  "Option");
		player.getPackets().sendIComponentText(813, 41,  "Option");
		player.getPackets().sendIComponentText(813, 42,  "Option");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendMinigameTeleports() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Minigame Teleports | Page 1");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 64,  "Option");
		player.getPackets().sendIComponentText(813, 65,  "Option");
		player.getPackets().sendIComponentText(813, 66,  "Option");
		player.getPackets().sendIComponentText(813, 67,  "Option");
		player.getPackets().sendIComponentText(813, 68,  "Option");
		player.getPackets().sendIComponentText(813, 69,  "Option");
		player.getPackets().sendIComponentText(813, 70,  "Option");
		player.getPackets().sendIComponentText(813, 71,  "Next Page...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	public void sendMinigameTeleports2() {
		player.getInterfaceManager().sendInterface(813);
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(813); i++) {
			player.getPackets().sendIComponentText(813, i, "");
		}
		player.getPackets().sendIComponentText(813, 15,  "Where would you like to go, " + player.getDisplayName());
		player.getPackets().sendIComponentText(813, 80,  "Minigame Teleports | Page 1");
		player.getPackets().sendIComponentText(813, 29,  "Training");
		player.getPackets().sendIComponentText(813, 30,  "Bossing");
		player.getPackets().sendIComponentText(813, 31,  "Skilling");
		player.getPackets().sendIComponentText(813, 32,  "Minigames");
		
		player.getPackets().sendIComponentText(813, 64,  "Option");
		player.getPackets().sendIComponentText(813, 65,  "Option");
		player.getPackets().sendIComponentText(813, 66,  "Option");
		player.getPackets().sendIComponentText(813, 67,  "Option");
		player.getPackets().sendIComponentText(813, 68,  "Option");
		player.getPackets().sendIComponentText(813, 69,  "Option");
		player.getPackets().sendIComponentText(813, 70,  "Option");
		player.getPackets().sendIComponentText(813, 71,  "Next Page...");
		
		player.getPackets().sendIComponentText(813, 36,  "My Stats");
		player.getPackets().sendIComponentText(813, 99,  "Mode: ");
		player.getPackets().sendIComponentText(813, 101,  "Rank: ");
		player.getPackets().sendIComponentText(813, 103,  "Prestige: ");
		player.getPackets().sendIComponentText(813, 105,  "Total: ");
		player.getPackets().sendIComponentText(813, 107,  "Cmb Lvl: ");
	}
	
	
	//end of custom teleports
	
	
	
	
	public void sendIntroHelp() {
	player.getInterfaceManager().sendInterface(76);
	player.getPackets().sendIComponentText(76, 3,   "To do list <br> 1)Make a fire by collecting wood and a fire rock 2) Try to find some food for the fight.");
	}
	
	public void sendBarcrawl() {
		
	player.getInterfaceManager().sendInterface(220); 
	player.getPackets().sendIComponentText(220, 1, "<col=24246B>The Official "+Settings.SERVER_NAME+"' Barcrawl!"); //Title
	
	if (player.BlueMoonInn == 0) {
	player.getPackets().sendIComponentText(220, 3, "<col=E60000>BlueMoon Inn - Not Completed..."); 
	}
	else if (player.BlueMoonInn == 1) {
		player.getPackets().sendIComponentText(220, 3, "<col=19FF19>BlueMoon Inn - Completed!"); 
		}
	
	if (player.BlurberrysBar == 0) {
	player.getPackets().sendIComponentText(220, 4, "<col=E60000>Blurberry's Bar - Not Completed..."); 
	}
	else if (player.BlurberrysBar == 1) {
	player.getPackets().sendIComponentText(220, 4, "<col=19FF19>Blurberry's Bar - Completed!");
	}
	
	if (player.DeadMansChest == 0) {
		player.getPackets().sendIComponentText(220, 5, "<col=E60000>Dead Man's Chest - Not Completed..."); 
		}
		else if (player.DeadMansChest == 1) {
		player.getPackets().sendIComponentText(220, 5, "<col=19FF19>Dead Man's Chest - Completed!");
		}
	
	if (player.DragonInn == 0) {
		player.getPackets().sendIComponentText(220, 6, "<col=E60000>Dragon Inn - Not Completed..."); 
		}
		else if (player.DragonInn == 1) {
		player.getPackets().sendIComponentText(220, 6, "<col=19FF19>Dragon Inn - Completed!");
		}
	
	if (player.FlyingHorseInn == 0) {
		player.getPackets().sendIComponentText(220, 7, "<col=E60000>Flying Horse Inn - Not Completed..."); 
		}
		else if (player.FlyingHorseInn == 1) {
		player.getPackets().sendIComponentText(220, 7, "<col=19FF19>Flying Horse Inn - Completed!");
		}
	
	if (player.ForestersArms == 0) {
		player.getPackets().sendIComponentText(220, 8, "<col=E60000>Foresters Arms - Not Completed..."); 
		}
		else if (player.ForestersArms == 1) {
		player.getPackets().sendIComponentText(220, 8, "<col=19FF19>Foresters Arms - Completed!");
		}
	
	if (player.JollyBoarInn == 0) {
		player.getPackets().sendIComponentText(220, 9, "<col=E60000>Jolly Boar Inn - Not Completed..."); 
		}
		else if (player.JollyBoarInn == 1) {
		player.getPackets().sendIComponentText(220, 9, "<col=19FF19>Jolly Boar Inn - Completed!");
		}
	
	if (player.KaramjaSpiritsBar == 0) {
		player.getPackets().sendIComponentText(220, 10, "<col=E60000>Karamja Spirits Bar - Not Completed..."); 
		}
		else if (player.KaramjaSpiritsBar == 1) {
		player.getPackets().sendIComponentText(220, 10, "<col=19FF19>Karamja Spirits Bar - Completed!");
		}
	
	if (player.RisingSun == 0) {
		player.getPackets().sendIComponentText(220, 11, "<col=E60000>Rising Sun Inn - Not Completed..."); 
		}
		else if (player.RisingSun == 1) {
		player.getPackets().sendIComponentText(220, 11, "<col=19FF19>Rising Sun Inn - Completed!");
		}
	
	if (player.RustyAnchor == 0) {
		player.getPackets().sendIComponentText(220, 12, "<col=E60000>Rusty Anchor Inn - Not Completed..."); 
		}
		else if (player.RustyAnchor == 1) {
		player.getPackets().sendIComponentText(220, 12, "<col=19FF19>Rusty Anchor Inn - Completed!");
		}
		
		
  
	
	}//end of bar crawl
	
	/**
	 * Help Manual
	 */
	
	public void sendHelpBook() {
		
		
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "<col=0099FF>"+Settings.SERVER_NAME+"' Help");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "How can I start to make money?");
		player.getPackets().sendIComponentText(275, 12, "<col=FF6600>For an Extensive Guide on Money Making for both");
		player.getPackets().sendIComponentText(275, 13, "<col=FF6600>Skillers and Warriors, type the command ::guide");
		player.getPackets().sendIComponentText(275, 14, "");
		player.getPackets().sendIComponentText(275, 15, "Don't know the commands or where anything is?");
		player.getPackets().sendIComponentText(275, 16, "<col=00CC00>Type ::commands for a List of Commands.");
		player.getPackets().sendIComponentText(275, 17, "<col=00CC00>Type ::crystal for your Teleportation Device.");
		player.getPackets().sendIComponentText(275, 18, "");
		player.getPackets().sendIComponentText(275, 19, "Why should I donate and what are the prices?");
		player.getPackets().sendIComponentText(275, 20, "<col=D1A319>Go to the Quest Tab and click Donator Information.");
		player.getPackets().sendIComponentText(275, 21, "<col=D1A319>There you can find a list of the Benefits and Payment Methods.");
		player.getPackets().sendIComponentText(275, 22, "");
		player.getPackets().sendIComponentText(275, 23, "How can I recieve help?");
		player.getPackets().sendIComponentText(275, 24, "<col=990099>You can Submit a Ticket by typing ::ticket.");
		player.getPackets().sendIComponentText(275, 25, "<col=990099>You can ask for help in "+Settings.SERVER_NAME+"''s Friendchat, help");
		player.getPackets().sendIComponentText(275, 26, "");
		player.getPackets().sendIComponentText(275, 27, "Need more help? Check out our forums at www."+Settings.SERVER_NAME+"'.com");
		
		for (int i = 27; i < 310; i++)
		player.getPackets().sendIComponentText(275, i, "");
		}
	
	
	public void sendDwarfCannon() {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Dwarf Cannon Quest");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "Speak to the Captain Lawgof at the Kingdom of Kandarin");
		player.getPackets().sendIComponentText(275, 12, "I have fixed "+player.fixedRailings+"/6 of the railings.");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 14, "");
		player.getPackets().sendIComponentText(275, 15, "");
		player.getPackets().sendIComponentText(275, 16, "");
		player.getPackets().sendIComponentText(275, 17, "");
		if (player.fixedRailings >= 6) {
		player.getPackets().sendIComponentText(275, 12, "<str>I have fixed "+player.fixedRailings+"/6 of the railings.");
		}
		if (player.completedRailingTask == true) {
		player.getPackets().sendIComponentText(275, 14, "I should find 'Nulodion' who is located at the Dwarven Mine.");
		}
		if (player.completedDwarfCannonQuest == true) {
		player.getPackets().sendIComponentText(275, 11, "<str>Speak to the Captain Lawgof at the Kingdom of Kandarin");
		player.getPackets().sendIComponentText(275, 14, "<str>I should find 'Nulodion' who is located at the Dwarven Mine.");
		player.getPackets().sendIComponentText(275, 15, "");
		player.getPackets().sendIComponentText(275, 16, "<u>Quest Complete.</u>");
		player.getPackets().sendIComponentText(275, 17, "Use a steel bar on any furnace to make cannonballs.");
		player.getPackets().sendIComponentText(275, 18, "You can now use Dwarf Cannons.");
		}
		player.getPackets().sendIComponentText(275, 18, "");
		player.getPackets().sendIComponentText(275, 19, "");
		player.getPackets().sendIComponentText(275, 20, "");
		}
	public void sendSmokingKills() {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Smoking Kills");
		player.getPackets().sendIComponentText(275, 10, "");
		if (player.sKQuest == 0) {
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to </col> <col=660000>Sumona</col> <col=330099>In</col> <col=330099>Pollnivneach </col>");
		player.getPackets().sendIComponentText(275, 12, "");
		player.getPackets().sendIComponentText(275, 13, "<col=33099>Requirements: </col>");
		player.getPackets().sendIComponentText(275, 14, "35 Slayer");
		player.getPackets().sendIComponentText(275, 15, "25 Crafting");
		player.getPackets().sendIComponentText(275, 16, "Must have completed Restless Ghost Quest");
		player.getPackets().sendIComponentText(275, 17, "Must be able to fight off multiple high level monsters at once");
		for (int i = 18; i < 310; i++)
			player.getPackets().sendIComponentText(275, i, "");
		}
	
		}
		
	public void sendEndZariaPart1(){
		player.getInterfaceManager().sendInterface(1244);
		player.getPackets().sendIComponentText(1244, 25, "You have completed the Home defender part I !");
		player.getPackets().sendIComponentText(1244, 26, "You are awarded with the ability to the curses prayer book.");
		player.getPackets().sendIComponentText(1244, 27, "Quest Points <col=FF0000>"+player.questPoints);
		player.getPackets().sendGlobalString(359, "<br>Curses prayers</br> <br>Two spin tickets</br> <br>Huge exp lamp</br>");
	}
	public void sendGertrudesCat() {
		player.getInterfaceManager().sendInterface(275);
		
		player.getPackets().sendIComponentText(275, 1, "Gertrude's Cat");
		player.getPackets().sendIComponentText(275, 10, "");
		if (player.gertCat == 0){
			player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to</col> <col=660000>Gertrude</col> <col=330099>In</col> <col=330099>Varrock City </col>");
			player.getPackets().sendIComponentText(275, 12, "");
			player.getPackets().sendIComponentText(275, 13, "<col=33099>Requirements: </col>");
			player.getPackets().sendIComponentText(275, 14, "There are no requirements for this quest.");
			player.getPackets().sendIComponentText(275, 15, "");
			for (int i = 16; i < 310; i++)
				player.getPackets().sendIComponentText(275, i, "");
		} else if (player.gertCat == 1){
			player.getPackets().sendIComponentText(275, 11, "Gertrude said I should talk to <col=660000>Shilop and Wilough</col> In <col=330099>Varrock square </col>");
			player.getPackets().sendIComponentText(275, 12, "to find out where her lost cat, <col=660000>Fluffs</col>Might be.");
			player.getPackets().sendIComponentText(275, 13, "");
			player.getPackets().sendIComponentText(275, 14, "");
			player.getPackets().sendIComponentText(275, 15, "");
			for (int i = 16; i < 310; i++)
				player.getPackets().sendIComponentText(275, i, "");
		} else if (player.gertCat == 2){
			player.getPackets().sendIComponentText(275, 11, "<str>Gertrude said I should talk to <col=660000>Shilop and Wilough</col> In <col=330099>Varrock square </col>");
			player.getPackets().sendIComponentText(275, 12, "<str>to find out where her lost cat, <col=660000>Fluffs</col>Might be.");
			player.getPackets().sendIComponentText(275, 13, "Gertrude's sons said that <col=660000>Fluffs</col> is in the <col=330099>Lumber Yard </col>");
			player.getPackets().sendIComponentText(275, 14, "Which is <col=660000>east of Varrock</col>.");
			player.getPackets().sendIComponentText(275, 15, "");
			for (int i = 16; i < 310; i++)
				player.getPackets().sendIComponentText(275, i, "");
		} else if (player.gertCat == 3){
			player.getPackets().sendIComponentText(275, 11, "<str>Gertrude said I should talk to <col=660000>Shilop and Wilough</col> In <col=330099>Varrock square </col>");
			player.getPackets().sendIComponentText(275, 12, "<str>to find out where her lost cat, <col=660000>Fluffs</col>Might be.");
			player.getPackets().sendIComponentText(275, 13, "<str>Gertrude's sons said that <col=660000>Fluffs</col> is in the <col=330099>Lumber Yard </col>");
			player.getPackets().sendIComponentText(275, 14, "<str>Which is <col=660000>east of Varrock</col>.");
			player.getPackets().sendIComponentText(275, 15, "<col=660000>Fluffs</col> refuses to move. Perhaps she's <col=660000>thirsty</col>?");
			for (int i = 16; i < 310; i++)
				player.getPackets().sendIComponentText(275, i, "");
		} else if (player.gertCat == 4){
			player.getPackets().sendIComponentText(275, 11, "<str>Gertrude said I should talk to <col=660000>Shilop and Wilough</col> In <col=330099>Varrock square </col>");
			player.getPackets().sendIComponentText(275, 12, "<str>to find out where her lost cat, <col=660000>Fluffs</col>Might be.");
			player.getPackets().sendIComponentText(275, 13, "<str>Gertrude's sons said that <col=660000>Fluffs</col> is in the <col=330099>Lumber Yard </col>");
			player.getPackets().sendIComponentText(275, 14, "<str>Which is <col=660000>east of Varrock</col>.");
			player.getPackets().sendIComponentText(275, 15, "<str><col=660000>Fluffs</col> refuses to move. Perhaps she's <col=660000>thirsty</col>?");
			player.getPackets().sendIComponentText(275, 16, "<col=660000>Fluffs</col> still refuses to move. Perhaps she's <col=660000>hungry</col>?");
			
			for (int i = 17; i < 310; i++)
				player.getPackets().sendIComponentText(275, i, "");
		} else if (player.gertCat == 5){
			player.getPackets().sendIComponentText(275, 11, "<str>Gertrude said I should talk to <col=660000>Shilop and Wilough</col> In <col=330099>Varrock square </col>");
			player.getPackets().sendIComponentText(275, 12, "<str>to find out where her lost cat, <col=660000>Fluffs</col>Might be.");
			player.getPackets().sendIComponentText(275, 13, "<str>Gertrude's sons said that <col=660000>Fluffs</col> is in the <col=330099>Lumber Yard </col>");
			player.getPackets().sendIComponentText(275, 14, "<str>Which is <col=660000>east of Varrock</col>.");
			player.getPackets().sendIComponentText(275, 15, "<str><col=660000>Fluffs</col> refuses to move. Perhaps she's <col=660000>thirsty</col>?");
			player.getPackets().sendIComponentText(275, 16, "<str><col=660000>Fluffs</col> still refuses to move. Perhaps she's <col=660000>hungry</col>?");
			player.getPackets().sendIComponentText(275, 17, "<col=660000>Fluffs</col> still refuses to move. You need to find her nearby <col=660000>kittens</col>.");
			for (int i = 18; i < 310; i++)
				player.getPackets().sendIComponentText(275, i, "");
		} else if (player.gertCat == 6){
			player.getPackets().sendIComponentText(275, 11, "<str>Gertrude said I should talk to <col=660000>Shilop and Wilough</col> In <col=330099>Varrock square </col>");
			player.getPackets().sendIComponentText(275, 12, "<str>to find out where her lost cat, <col=660000>Fluffs</col>Might be.");
			player.getPackets().sendIComponentText(275, 13, "<str>Gertrude's sons said that <col=660000>Fluffs</col> is in the <col=330099>Lumber Yard </col>");
			player.getPackets().sendIComponentText(275, 14, "<str>Which is <col=660000>east of Varrock</col>.");
			player.getPackets().sendIComponentText(275, 15, "<str><col=660000>Fluffs</col> refuses to move. Perhaps she's <col=660000>thirsty</col>?");
			player.getPackets().sendIComponentText(275, 16, "<str><col=660000>Fluffs</col> still refuses to move. Perhaps she's <col=660000>hungry</col>?");
			player.getPackets().sendIComponentText(275, 17, "<str><col=660000>Fluffs</col> still refuses to move. You need to find her nearby <col=660000>kittens</col>.");
			player.getPackets().sendIComponentText(275, 18, "<col=660000>Fluffs</col> has gone home. You should report back to <col=660000>Gertrude</col>.");
			for (int i = 19; i < 310; i++)
				player.getPackets().sendIComponentText(275, i, "");
		}
		 else if (player.gertCat == 7){
				player.getPackets().sendIComponentText(275, 11, "<str>Gertrude said I should talk to <col=660000>Shilop and Wilough</col> In <col=330099>Varrock square </col>");
				player.getPackets().sendIComponentText(275, 12, "<str>to find out where her lost cat, <col=660000>Fluffs</col>Might be.");
				player.getPackets().sendIComponentText(275, 13, "<str>Gertrude's sons said that <col=660000>Fluffs</col> is in the <col=330099>Lumber Yard </col>");
				player.getPackets().sendIComponentText(275, 14, "<str>Which is <col=660000>east of Varrock</col>.");
				player.getPackets().sendIComponentText(275, 15, "<str><col=660000>Fluffs</col> refuses to move. Perhaps she's <col=660000>thirsty</col>?");
				player.getPackets().sendIComponentText(275, 16, "<str><col=660000>Fluffs</col> still refuses to move. Perhaps she's <col=660000>hungry</col>?");
				player.getPackets().sendIComponentText(275, 17, "<str><col=660000>Fluffs</col> still refuses to move. You need to find her nearby <col=660000>kittens</col>.");
				player.getPackets().sendIComponentText(275, 18, "<str><col=660000>Fluffs</col> has gone home. You should report back to <col=660000>Gertrude</col>.");
				player.getPackets().sendIComponentText(275, 19, "");
				player.getPackets().sendIComponentText(275, 20, "<u>Quest Complete.</u>");
				for (int i = 21; i < 310; i++)
					player.getPackets().sendIComponentText(275, i, "");
			}
	
	}
	public void handleGertrudesCatFinish() {
		player.getInterfaceManager().sendInterface(277);
		player.getPackets().sendIComponentText(277, 4, "You have completed Gertrude's Cat");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "1 Quest Point");
		player.getPackets().sendIComponentText(277, 11, "1525 Cooking XP");
		player.getPackets().sendIComponentText(277, 12, "A chocolate cake");
		player.getPackets().sendIComponentText(277, 13, "A bowl of stew");
		player.getPackets().sendIComponentText(277, 14, "Ability to Raise Cats");
		player.getPackets().sendIComponentText(277, 15, "Two spins on the Squeal of Fortune");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 1561, 1);
		player.getPackets().sendGameMessage("Congratulations! You have completed the Gertrude's Cat quest!");
	}
	
	public void sendJewelryCrafting() {
		player.getInterfaceManager().sendInterface(446); //THE INTERFACE IT OPENS
		player.getPackets().sendIComponentText(446, 98, "<col=CC9900>------   <col=0000FFO>------   <col=00FF00>------   <col=FF0000>------   <col=FFFFFF>------   <col=CC33FF>------   <col=000000>------   <col=9966FF>------"); //Title

		player.getPackets().sendIComponentText(446, 22, "<col=CC9900>------   <col=0000FFO>------   <col=00FF00>------   <col=FF0000>------   <col=FFFFFF>------   <col=CC33FF>------   <col=000000>------"); 
		player.getPackets().sendIComponentText(446, 66, "<col=CC9900>------   <col=0000FFO>------   <col=00FF00>------   <col=FF0000>------   <col=FFFFFF>------   <col=CC33FF>------   <col=000000>------"); 
		player.getPackets().sendIComponentText(446, 51, "<col=CC9900>------   <col=0000FFO>------   <col=00FF00>------   <col=FF0000>------   <col=FFFFFF>------   <col=CC33FF>------   <col=000000>------"); 
		
		}
	
	public void sendSpinningWheel() {
		player.getInterfaceManager().sendInterface(438); //THE INTERFACE IT OPENS
		player.getPackets().sendIComponentText(438, 13, "What would you like to spin?"); //Title
		player.getPackets().sendItemOnIComponent(438, 30, 1759, 1);
		//player.getPackets().sendItems(30, 1759);
		player.getPackets().sendItemOnIComponent(438, 44, 1777, 1);
		player.getPackets().sendItemOnIComponent(438, 59, 6038, 1);
		player.getPackets().sendIComponentText(438, 32, "Ball Of Wool"); 
		player.getPackets().sendIComponentText(438, 46, "Bow String"); 
		player.getPackets().sendIComponentText(438, 61, "Magic String"); 
		player.getPackets().sendItemOnIComponent(438, 37, 9438, 1);
		player.getPackets().sendItemOnIComponent(438, 52, 9438, 1);
		player.getPackets().sendItemOnIComponent(438, 66, 954, 1);
		player.getPackets().sendIComponentText(438, 39, "C'Bow String"); 
		player.getPackets().sendIComponentText(438, 54, "C'Bow String"); 
		player.getPackets().sendIComponentText(438, 68, "Rope"); 
		
		player.getPackets().sendIComponentText(438, 16, ""); 
		player.getPackets().sendIComponentText(438, 18, "");
		player.getPackets().sendIComponentText(438, 23, "");
		player.getPackets().sendIComponentText(438, 25, "");
		player.getPackets().sendIComponentText(438, 73, "");
		player.getPackets().sendIComponentText(438, 75, "");
		player.getPackets().sendIComponentText(438, 79, "");
		player.getPackets().sendIComponentText(438, 81, "");
		}
	

	
	public void dungAchievements(Player player) {
	player.getInterfaceManager().sendInterface(275);
	player.getPackets().sendIComponentText(275, 1, "Dungeoneering Achievements"); 
	player.getPackets().sendIComponentText(275, 10, ""); 
	player.getPackets().sendIComponentText(275, 11, "All these achievements can be achievied within this area."); 
	player.getPackets().sendIComponentText(275, 12, "1)Make a blood necklace. "); 
	player.getPackets().sendIComponentText(275, 13, "2)Make a empower staff.  "); 
	player.getPackets().sendIComponentText(275, 14, "3)Bury 500 ancient ashes."); 
	player.getPackets().sendIComponentText(275, 15, "4)Complete 20 dungeons.  "); 
	
	}
	
	public void donatorCommands(){
     player.getInterfaceManager().sendInterface(275);
	 player.getPackets().sendIComponentText(275, 1, "Donator commands");
	 player.getPackets().sendIComponentText(275, 11, "<col=FFFFFF><shad=FFF00>Regular donator");
	 player.getPackets().sendIComponentText(275, 11, "<col=FFFFFF><shad=000000>::dz/::donatorzone");
	 player.getPackets().sendIComponentText(275, 18, "<col=FFFFFF><shad=000000>::arest, sets the arcane resting.");
	 player.getPackets().sendIComponentText(275, 12, "<col=FFFFFF><shad=000000>::zrest, sets the zen resting.");
	 player.getPackets().sendIComponentText(275, 13, "<col=FFFFFF><shad=000000>::yell");
	 player.getPackets().sendIComponentText(275, 14, "<col=FFFFFF><shad=000000>::settitle");
	 player.getPackets().sendIComponentText(275, 15, "<col=FFFFFF><shad=000000>::settitlecolor");
	 player.getPackets().sendIComponentText(275, 16, "<col=FFFFFF><shad=000000>::greenskin/blueskin/hideskin");
	 player.getPackets().sendIComponentText(275, 17, "<col=FFFFFF><shad=000000>::yell");
	 player.getPackets().sendIComponentText(275, 19, "");
	 player.getPackets().sendIComponentText(275, 20, "");
	 player.getPackets().sendIComponentText(275, 21, "<col=FFFFFF><shad=FFF00>Extreme donator");
	 player.getPackets().sendIComponentText(275, 22, "<col=FFFFFF><shad=000000>::bank");
	 player.getPackets().sendIComponentText(275, 23, "<col=FFFFFF><shad=000000>::setdisplay");
	 player.getPackets().sendIComponentText(275, 24, "<col=FFFFFF><shad=000000>::yellcolor");
	 player.getPackets().sendIComponentText(275, 28, "<col=FFFFFF><shad=FFF00>Legendary donator");
	 player.getPackets().sendIComponentText(275, 29, "<col=FFFFFF><shad=FFF00>::fly");
	 player.getPackets().sendIComponentText(275, 29, "<col=FFFFFF><shad=FFF00>::land");
	}
	
	public void sendPlayerExamineArmor(Player player){
		String name = Player.getExamined();
		//player.getInterfaceManager().openGameTab(14);
		Player p2 = World.getPlayerByDisplayName(name); 
		player.getInterfaceManager().sendTab(resizableScreen ? 125 : 185, 954);
		player.getPackets().sendHideIComponent(954, 50, true);
		player.getPackets().sendHideIComponent(954, 56, true);
		player.getPackets().sendIComponentText(954, 5, "" + p2.getDisplayName() + "");
		player.getPackets().sendItemOnIComponent(954, 21, p2.getEquipment().getCapeId(), 1);
		player.getPackets().sendItemOnIComponent(954, 18, p2.getEquipment().getHatId(), 1);
		player.getPackets().sendItemOnIComponent(954, 30, p2.getEquipment().getChestId(), 1);
		player.getPackets().sendItemOnIComponent(954, 27, p2.getEquipment().getWeaponId(), 1);
		player.getPackets().sendItemOnIComponent(954, 33, p2.getEquipment().getShieldId(), 1); 
		player.getPackets().sendItemOnIComponent(954, 36, p2.getEquipment().getLegsId(), 1);
		player.getPackets().sendItemOnIComponent(954, 24, p2.getEquipment().getAmuletId(), 1);
		player.getPackets().sendItemOnIComponent(954, 39, p2.getEquipment().getGlovesId(), 1);
		player.getPackets().sendItemOnIComponent(954, 42, p2.getEquipment().getBootsId(), 1);
		player.getPackets().sendItemOnIComponent(954, 45, p2.getEquipment().getRingId(), 1);
		player.getPackets().sendItemOnIComponent(954, 49, p2.getEquipment().getAmmoId(), p2.getEquipment().getAmmoAmount());
	}
	
	
	
	public void sendPlayerExamine(Player player) {	
		player.getInterfaceManager().sendTab(resizableScreen ? 125 : 185, 936);
		//sendTab(125, 936); 
		String name = Player.getExamined();
		Player p2 = World.getPlayerByDisplayName(name);
		player.getInterfaceManager().openGameTab(14);
		player.getPackets().sendHideIComponent(936, 134, true);
		player.getPackets().sendHideIComponent(936, 140, true);
		//player.getInterfaceManager().sendTab(114, 936);
		player.getPackets().sendIComponentText(936, 132, "Stats viewer :" + Player.getExamined());
		player.getPackets().sendIComponentText(936, 7, +p2.getSkills().getLevel(Skills.ATTACK) + "");
		player.getPackets().sendIComponentText(936, 8, "99");
		player.getPackets().sendIComponentText(936, 12, +p2.getSkills().getLevel(Skills.HITPOINTS) + "");
		player.getPackets().sendIComponentText(936, 13, "99");
		player.getPackets().sendIComponentText(936, 17, +p2.getSkills().getLevel(Skills.MINING) + "");
		player.getPackets().sendIComponentText(936, 18, "99");
		player.getPackets().sendIComponentText(936, 22, +p2.getSkills().getLevel(Skills.STRENGTH) + "");
		player.getPackets().sendIComponentText(936, 23, "99");
		player.getPackets().sendIComponentText(936, 27, +p2.getSkills().getLevel(Skills.AGILITY) + "");
		player.getPackets().sendIComponentText(936, 28, "99");
		player.getPackets().sendIComponentText(936, 32, +p2.getSkills().getLevel(Skills.SMITHING) + "");
		player.getPackets().sendIComponentText(936, 33, "99");
		player.getPackets().sendIComponentText(936, 37, +p2.getSkills().getLevel(Skills.DEFENCE) + "");
		player.getPackets().sendIComponentText(936, 38, "99");
		player.getPackets().sendIComponentText(936, 42, +p2.getSkills().getLevel(Skills.HERBLORE) + "");
		player.getPackets().sendIComponentText(936, 43, "99");
		player.getPackets().sendIComponentText(936, 47, +p2.getSkills().getLevel(Skills.FISHING) + "");
		player.getPackets().sendIComponentText(936, 48, "99");
		player.getPackets().sendIComponentText(936, 52, +p2.getSkills().getLevel(Skills.RANGE) + "");
		player.getPackets().sendIComponentText(936, 53, "99");
		player.getPackets().sendIComponentText(936, 57, +p2.getSkills().getLevel(Skills.THIEVING) + "");
		player.getPackets().sendIComponentText(936, 8, "99");
		player.getPackets().sendIComponentText(936, 62, +p2.getSkills().getLevel(Skills.COOKING) + "");
		player.getPackets().sendIComponentText(936, 63, "99");
		player.getPackets().sendIComponentText(936, 67, +p2.getSkills().getLevel(Skills.PRAYER) + "");
		player.getPackets().sendIComponentText(936, 68, "99");
		player.getPackets().sendIComponentText(936, 72, +p2.getSkills().getLevel(Skills.CRAFTING) + "");
		player.getPackets().sendIComponentText(936, 73, "99");
		player.getPackets().sendIComponentText(936, 77, +p2.getSkills().getLevel(Skills.FIREMAKING) + "");
		player.getPackets().sendIComponentText(936, 78, "99");
		player.getPackets().sendIComponentText(936, 82, +p2.getSkills().getLevel(Skills.MAGIC) + "");
		player.getPackets().sendIComponentText(936, 83, "99");
		player.getPackets().sendIComponentText(936, 87, +p2.getSkills().getLevel(Skills.FLETCHING) + "");
		player.getPackets().sendIComponentText(936, 88, "99");
		player.getPackets().sendIComponentText(936, 92, +p2.getSkills().getLevel(Skills.WOODCUTTING) + "");
		player.getPackets().sendIComponentText(936, 93, "99");
		player.getPackets().sendIComponentText(936, 97, +p2.getSkills().getLevel(Skills.RUNECRAFTING) + "");
		player.getPackets().sendIComponentText(936, 98, "99");
		player.getPackets().sendIComponentText(936, 102, +p2.getSkills().getLevel(Skills.SLAYER) + "");
		player.getPackets().sendIComponentText(936, 103, "99");
		player.getPackets().sendIComponentText(936, 107, +p2.getSkills().getLevel(Skills.FARMING) + "");
		player.getPackets().sendIComponentText(936, 108, "99");
		player.getPackets().sendIComponentText(936, 112, +p2.getSkills().getLevel(Skills.CONSTRUCTION) + "");
		player.getPackets().sendIComponentText(936, 113, "99");
		player.getPackets().sendIComponentText(936, 117, +p2.getSkills().getLevel(Skills.HUNTER) + "");
		player.getPackets().sendIComponentText(936, 118, "99");
		player.getPackets().sendIComponentText(936, 122, +p2.getSkills().getLevel(Skills.SUMMONING) + "");
		player.getPackets().sendIComponentText(936, 123, "99");
		player.getPackets().sendIComponentText(936, 127, +p2.getSkills().getLevel(Skills.DUNGEONEERING) + "");
		player.getPackets().sendIComponentText(936, 128, "120");
	
	}
	public void sendPlayerCustom() {
	sendTab(resizableScreen ? 114 : 174, 930);
	player.getPackets().sendIComponentText(930, 10, "Zaria info panel");
	player.getPackets().sendIComponentText(930, 16, "..::<col=FFFFFF><u>General server info</u></col>::..<br><br>"+
    "Owner: <col=FFFFFF>zero gravity </col><br>" +
	"Players Online: <col=FFFFFF>"+World.getPlayers().size()+" </col><br>" +
	""+Gui.Uptime()+" </col><br>" +
	"Account information"+
	"Time played:"+Utils.formatMinutes(player.onlinetime)+
	"Days active:"+player.daysOnline+
	"Daily Task: <col=ffffff>"+(player.getDailyTask() != null ? player.getDailyTask().reformatTaskName(player.getDailyTask().getName()) : ".")+"</col><br>"+
	"Amount: <col=ffffff>"+(player.getDailyTask() != null ? player.getDailyTask().getAmountCompleted()+"/"+player.getDailyTask().getTotalAmount() : ".")+"</col><br>"+
	""+(player.hasTask ? "Slayer task:<col=ffffff> "+player.currentSlayerTask.getTask().simpleName+"</col><br>" : "" )+
	""+(player.hasTask ? "Amount: <col=ffffff>"+player.currentSlayerTask.getTaskMonstersLeft() +"</col><br>" : "" )+
	"<br><br>..::<col=FFFFFF><u>Event information</u></col>::..<br><br>"+
	"Spotlight: <col=FFFFFF>"+World.getSpotLightSkillName()+", "+World.getSpotLightCombatSkillName()+" </col><br>"+
	"Double exp: <col=FFFFFF>"+Settings.doubleExp+"</col><br>"+
	"<br><br>..::<col=FFFFFF><u>Commands</u></col>::..<br><br>"+
	"::home<br>"+
	"::qhome<br>"+
	"::npclookup [npcName]<br>"+
	"::itemlookup [dropName] <br>"+
	"::empty<br>"+
	"::askbot [question]<br>"+
	"::event<br>"+
	"::spotlight<br>"+
	"::vote<br>"+
	"::donate<br>"+
	"::discord<br>"+
	"");
	}
	

	public void sendSkills() {
		sendTab(resizableScreen ? 113 : 173, 320);
	}

	public void sendSettings() {
		sendSettings(261);
	}
	
	public void closeSettings() {
		player.getPackets().closeInterface(resizableScreen ? 123 : 183);
	}

	public void sendSettings(int interfaceId) {
		sendTab(resizableScreen ? 123 : 183, interfaceId);
	}

	public void sendPrayerBook() {
		sendTab(resizableScreen ? 117 : 177, 271);
	}
	
	public void closePrayerBook() {
		player.getPackets().closeInterface(resizableScreen ? 117 : 177);
	}
	
	public void closePlayerCustom() {
		player.getPackets().closeInterface(resizableScreen ? 114 : 174);
	}

	public void sendMagicBook() {
		sendTab(resizableScreen ? 118 : 178, player.getCombatDefinitions()
				.getSpellBook());
	}
	
	public void closeMagicBook() {
		player.getPackets().closeInterface(resizableScreen ? 118 : 178);
	}
	
	public void sendEmotes() {
		sendTab(resizableScreen ? 124 : 184, 590);
	}
	
	public void closeEmotes() {
		player.getPackets().closeInterface(resizableScreen ? 124 : 184);
	}

	public boolean addInterface(int windowId, int tabId, int childId) {
		if (openedinterfaces.containsKey(tabId))
			player.getPackets().closeInterface(tabId);
		openedinterfaces.put(tabId, new int[] { childId, windowId });
		return openedinterfaces.get(tabId)[0] == childId;
	}

	public boolean containsInterface(int tabId, int childId) {
		if (childId == windowsPane)
			return true;
		if (!openedinterfaces.containsKey(tabId))
			return false;
		return openedinterfaces.get(tabId)[0] == childId;
	}

	public int getTabWindow(int tabId) {
		if (!openedinterfaces.containsKey(tabId))
			return FIXED_WINDOW_ID;
		return openedinterfaces.get(tabId)[1];
	}

	public boolean containsInterface(int childId) {
		if (childId == windowsPane)
			return true;
		for (int[] value : openedinterfaces.values())
			if (value[0] == childId)
				return true;
		return false;
	}

	public boolean containsTab(int tabId) {
		return openedinterfaces.containsKey(tabId);
	}

	public void removeAll() {
		openedinterfaces.clear();
	}

	public boolean containsScreenInter() {
		return containsTab(resizableScreen ? RESIZABLE_SCREEN_TAB_ID
				: FIXED_SCREEN_TAB_ID);
	}
	
    public void removeScreenInterface() {
	removeWindowInterface(resizableScreen ? RESIZABLE_SCREEN_TAB_ID : FIXED_SCREEN_TAB_ID);
    }
    
    public void removeWindowInterface(int componentId) {
	removeInterface(resizableScreen ? RESIZABLE_WINDOW_ID : FIXED_WINDOW_ID, componentId);
    }

	public void closeScreenInterface() {
		player.getPackets()
				.closeInterface(
						resizableScreen ? RESIZABLE_SCREEN_TAB_ID
								: FIXED_SCREEN_TAB_ID);
	}

	public boolean containsInventoryInter() {
		return containsTab(resizableScreen ? RESIZABLE_INV_TAB_ID
				: FIXED_INV_TAB_ID);
	}

	public void closeInventoryInterface() {
		player.getPackets().closeInterface(
				resizableScreen ? RESIZABLE_INV_TAB_ID : FIXED_INV_TAB_ID);
	}

	public boolean containsChatBoxInter() {
		return containsTab(CHAT_BOX_TAB);
	}

	public boolean removeTab(int tabId) {
		return openedinterfaces.remove(tabId) != null;
	}

	public boolean removeInterface(int tabId, int childId) {
		if (!openedinterfaces.containsKey(tabId))
			return false;
		if (openedinterfaces.get(tabId)[0] != childId)
			return false;
		return openedinterfaces.remove(tabId) != null;
	}

	public void sendFadingInterface(int backgroundInterface) {
		if (hasRezizableScreen()) 
			player.getPackets().sendInterface(true, RESIZABLE_WINDOW_ID, 12,backgroundInterface);
		else
			player.getPackets().sendInterface(true, FIXED_WINDOW_ID, 11,backgroundInterface);
	}
	
	public void closeFadingInterface() {
		if (hasRezizableScreen()) 
			player.getPackets().closeInterface(12);
		else
			player.getPackets().closeInterface(11);
	}
	
	public void sendScreenInterface(int backgroundInterface, int interfaceId) {
		player.getInterfaceManager().closeScreenInterface();

		if (hasRezizableScreen()) {
			player.getPackets().sendInterface(false, RESIZABLE_WINDOW_ID, 40,
					backgroundInterface);
			player.getPackets().sendInterface(false, RESIZABLE_WINDOW_ID, 41,
					interfaceId);
		} else {
			player.getPackets().sendInterface(false, FIXED_WINDOW_ID, 200,
					backgroundInterface);
			player.getPackets().sendInterface(false, FIXED_WINDOW_ID, 201,
					interfaceId);
			
		}

		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				if (hasRezizableScreen()) {
					player.getPackets().closeInterface(40);
					player.getPackets().closeInterface(41);
				} else {
					player.getPackets().closeInterface(200);
					player.getPackets().closeInterface(201);
				}
			}
		});
	}

	public boolean hasRezizableScreen() {
		return resizableScreen;
	}

	public void setWindowsPane(int windowsPane) {
		this.windowsPane = windowsPane;
	}

	public int getWindowsPane() {
		return windowsPane;
	}
	
	public void closeSqueal() {
		player.getPackets().closeInterface(resizableScreen ? 119 : 179);
	}
	
	public void gazeOrbOfOculus() {
		player.getPackets().sendWindowsPane(475, 0);
		player.getPackets().sendInterface(true, 475, 57, 751);
		player.getPackets().sendInterface(true, 475, 55, 752);
		player.setCloseInterfacesEvent(new Runnable() {

			@Override
			public void run() {
				player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, 0);
				player.getPackets().sendResetCamera();
			}
			
		});
	}

	/*
	 * returns lastGameTab
	 */
	public int openGameTab(int tabId) {
		player.getPackets().sendGlobalConfig(168, tabId);
		int lastTab = 4; // tabId
		// tab = tabId;
		return lastTab;
	}
	
	public void sendSquealOfFortune() {
		sendTab(resizableScreen ? 119 : 179, 1139);
		player.getPackets().sendGlobalConfig(823, 1);
	}
	
    public void setWindowInterface(int componentId, int interfaceId) {
    	player.getPackets().sendInterface(true, resizableScreen ? RESIZABLE_WINDOW_ID : FIXED_WINDOW_ID, componentId, interfaceId);
    }

	public void sendItemDrops(ItemDefinitions defs) {
		int i = 0;
		String dropEntry = "";
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Drops: <col=9900FF><shad=000000>" + defs.name + "</col></shad>");
		for (i = 10; i < 310; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
		i = 0;
		for (int n = 0; n < Utils.getNPCDefinitionsSize(); n++) {
			NPCDefinitions def = NPCDefinitions.getNPCDefinitions(n);
			Drop[] drops = NPCDrops.getDrops(def.getId());
			if (drops != null) {
			for (Drop drop : drops) {
				if (drop.getItemId() == 0)
					continue;
				ItemDefinitions itemDefs = ItemDefinitions.getItemDefinitions(drop.getItemId());
				if (itemDefs.getId() != defs.getId()
						|| !itemDefs.name.contains(defs.name)
						|| !itemDefs.name.equalsIgnoreCase(defs.name))
					continue;
				StringBuilder sb = new StringBuilder("").append(def.name)
						.append(": ").append(itemDefs.name)
						.append(drop.getMaxAmount() == 1 ? 
								("") : drop.getMinAmount() == drop.getMaxAmount() ? (" (" + drop.getMaxAmount() + ")") :
									(" (" + drop.getMinAmount() + "-" + drop.getMaxAmount() + ")"))
						//.append(" {").append((int) drop.getRate()).append("% Chance}"); //old percentage
						.append(" {"+getDropRate(drop.getRate())+"</col>}");
				dropEntry = sb.toString();
				if (i < 300)
				player.getPackets().sendIComponentText(275, 10 + i, dropEntry);
				dropEntry = "";
				i++;
				}
			}
		}
	}
	/**
	 * Sorts the array into highest rate to the lowest.
	 * @param array
	 */
	public static void bubbleSort(Drop[] array) {
	    boolean swapped = true;
	    //null check for empty drops
		if(array == null || array.length == 0)
			return;
	    int j = 0;
	    Drop tmp;
	    while (swapped) {
	        swapped = false;
	        j++;
	        for (int i = 0; i < array.length - j; i++) {
	            if (array[i].getRate() < array[i + 1].getRate()) {
	                tmp = array[i];
	                array[i] = array[i + 1];
	                array[i + 1] = tmp;
	                swapped = true;
	            }
	        }
	    }
	}
	/**
	 * sends the drops of a certain npc
	 * @param defs def of the npc 
	 */
	public void sendNPCDrops(NPCDefinitions defs) {
		int i = 0;
		String dropEntry = "";
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Drops: <col=9900FF><shad=000000>" + defs.name + "</col></shad>");
		for (i = 10; i < 310; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
		i = 0;
		Drop[] drops = NPCDrops.getDrops(defs.getId());
		bubbleSort(drops);
		if (drops != null) {
		for (Drop drop : drops) {
			if (drop.getItemId() == 0)
				continue;
			ItemDefinitions itemDefs = ItemDefinitions.getItemDefinitions(drop.getItemId());
			StringBuilder sb = new StringBuilder("").append(itemDefs.name)
					.append(drop.getMaxAmount() == 1 ? 
							("") : drop.getMinAmount() == drop.getMaxAmount() ? (" (" + drop.getMaxAmount() + ")") :
								(" (" + drop.getMinAmount() + "-" + drop.getMaxAmount() + ")"))
					//.append(" {").append((int) drop.getRate()).append("% Chance}"); //old percentage 
					.append(" {"+getDropRate(drop.getRate())+"</col>}");
			dropEntry = sb.toString();
			if (i < 300)
			player.getPackets().sendIComponentText(275, 10 + i, dropEntry);
			dropEntry = "";
			i++;
			}
		}
	}
	
	public static String getDropRate(double amount){
		if(amount == 100)
			return Colors.green+"Always";
		else if(amount >= 50)
			return Colors.green+"Common";
		else if(amount < 50  && amount >= 20)
			return Colors.orange+"Uncommon";
		else if(amount < 20 && amount >= 5)
			return Colors.red+"Rare";
		else if(amount <  5) 
			return Colors.purple+"Very rare";
		return "";
	}
	/**
	 * sends the cape counter interface (global)
	 */
	public void sendCapeCounting(){
		player.getInterfaceManager().sendInterface(3014);
		player.getPackets().sendIComponentText(3014, 1, ""+GlobalCapeCounter.getCapes().getAgility120());
		player.getPackets().sendIComponentText(3014, 2, ""+GlobalCapeCounter.getCapes().getAttack120());
		player.getPackets().sendIComponentText(3014, 3, ""+GlobalCapeCounter.getCapes().getConstitution120());
		player.getPackets().sendIComponentText(3014, 4, ""+GlobalCapeCounter.getCapes().getConstruction120());
		player.getPackets().sendIComponentText(3014, 5, ""+GlobalCapeCounter.getCapes().getCooking120());
		player.getPackets().sendIComponentText(3014, 6, ""+GlobalCapeCounter.getCapes().getCrafting120());
		player.getPackets().sendIComponentText(3014, 7, ""+GlobalCapeCounter.getCapes().getDefence120());
		player.getPackets().sendIComponentText(3014, 8, ""+GlobalCapeCounter.getCapes().getDungeoneering120());
		player.getPackets().sendIComponentText(3014, 9, ""+GlobalCapeCounter.getCapes().getFarming120());
		player.getPackets().sendIComponentText(3014, 10, ""+GlobalCapeCounter.getCapes().getFiremaking120());
		player.getPackets().sendIComponentText(3014, 11, ""+GlobalCapeCounter.getCapes().getFishing120());
		player.getPackets().sendIComponentText(3014, 12, ""+GlobalCapeCounter.getCapes().getFletching120());
		player.getPackets().sendIComponentText(3014, 13, ""+GlobalCapeCounter.getCapes().getHerblore120());
		player.getPackets().sendIComponentText(3014, 14, ""+GlobalCapeCounter.getCapes().getHunter120());
		player.getPackets().sendIComponentText(3014, 15, ""+GlobalCapeCounter.getCapes().getMagic120());
		player.getPackets().sendIComponentText(3014, 16, ""+GlobalCapeCounter.getCapes().getMining120());
		player.getPackets().sendIComponentText(3014, 17, ""+GlobalCapeCounter.getCapes().getPrayer120());
		player.getPackets().sendIComponentText(3014, 18, ""+GlobalCapeCounter.getCapes().getRanged120());
		player.getPackets().sendIComponentText(3014, 19, ""+GlobalCapeCounter.getCapes().getRunecrafting120());
		player.getPackets().sendIComponentText(3014, 20, ""+GlobalCapeCounter.getCapes().getSlayer120());
		player.getPackets().sendIComponentText(3014, 21, ""+GlobalCapeCounter.getCapes().getSmithing120());
		player.getPackets().sendIComponentText(3014, 22, ""+GlobalCapeCounter.getCapes().getStrength120());
		player.getPackets().sendIComponentText(3014, 23, ""+GlobalCapeCounter.getCapes().getSummoning120());
		player.getPackets().sendIComponentText(3014, 24, ""+GlobalCapeCounter.getCapes().getThieving120());
		player.getPackets().sendIComponentText(3014, 25, ""+GlobalCapeCounter.getCapes().getWoodcutting120());
		player.getPackets().sendIComponentText(3014, 26, ""+GlobalCapeCounter.getCapes().getMaxedUsers());
		player.getPackets().sendIComponentText(3014, 27, ""+GlobalCapeCounter.getCapes().getCompedUsers());
		player.getPackets().sendIComponentText(3014, 28, ""+GlobalCapeCounter.getCapes().getCompedTUsers());
	}
	/**
	 * global counting
	 */
	public void sendGlobalItemCounting(){

		 player.getInterfaceManager().sendInterface(3028);
			player.getPackets().sendIComponentText(3028, 30, "Global Drop Counting");
		  	player.getPackets().sendIComponentText(3028, 1, (GlobalItemCounter.getdropCount().containsKey(25022) ? GlobalItemCounter.getdropCount().get(25022).toString() : "0"));
		  	player.getPackets().sendIComponentText(3028, 2, (GlobalItemCounter.getdropCount().containsKey(11724) ? GlobalItemCounter.getdropCount().get(11724).toString() : ""+0));
			player.getPackets().sendIComponentText(3028, 3, (GlobalItemCounter.getdropCount().containsKey(11726) ?GlobalItemCounter.getdropCount().get(11726).toString() : ""+0));
			player.getPackets().sendIComponentText(3028, 4, (GlobalItemCounter.getdropCount().containsKey(11704) ?GlobalItemCounter.getdropCount().get(11704).toString() : ""+0));
			//armadyl
			player.getPackets().sendIComponentText(3028, 5, (GlobalItemCounter.getdropCount().containsKey(11718) ?GlobalItemCounter.getdropCount().get(11718).toString() : ""+0));
			player.getPackets().sendIComponentText(3028, 6, (GlobalItemCounter.getdropCount().containsKey(11720) ?GlobalItemCounter.getdropCount().get(11720).toString() : ""+0));
			player.getPackets().sendIComponentText(3028, 7, (GlobalItemCounter.getdropCount().containsKey(11722) ?GlobalItemCounter.getdropCount().get(11722).toString() : ""+0));
			player.getPackets().sendIComponentText(3028, 8, (GlobalItemCounter.getdropCount().containsKey(11702) ?GlobalItemCounter.getdropCount().get(11702).toString() : ""+0));
			//Zamorak
			player.getPackets().sendIComponentText(3028, 9,  (GlobalItemCounter.getdropCount().containsKey(24992) ?GlobalItemCounter.getdropCount().get(24992).toString() : ""+0));
			player.getPackets().sendIComponentText(3028, 10, (GlobalItemCounter.getdropCount().containsKey(24995) ?GlobalItemCounter.getdropCount().get(24995).toString() : ""+0));
			player.getPackets().sendIComponentText(3028, 11, (GlobalItemCounter.getdropCount().containsKey(24998) ?GlobalItemCounter.getdropCount().get(24998).toString() : ""+0));
			player.getPackets().sendIComponentText(3028, 12, (GlobalItemCounter.getdropCount().containsKey(11708) ?GlobalItemCounter.getdropCount().get(11708).toString() : ""+0));
			//armadyl
			player.getPackets().sendIComponentText(3028, 13, "");//Todo
			player.getPackets().sendIComponentText(3028, 14, (GlobalItemCounter.getdropCount().containsKey(25037) ?GlobalItemCounter.getdropCount().get(25037).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 15, (GlobalItemCounter.getdropCount().containsKey(11730) ?GlobalItemCounter.getdropCount().get(11730).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 16, (GlobalItemCounter.getdropCount().containsKey(11706) ?GlobalItemCounter.getdropCount().get(11706).toString(): ""+0));
			//torva
			player.getPackets().sendIComponentText(3028, 17, (GlobalItemCounter.getdropCount().containsKey(20135) ?GlobalItemCounter.getdropCount().get(20135).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 18, (GlobalItemCounter.getdropCount().containsKey(20139) ?GlobalItemCounter.getdropCount().get(20139).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 19, (GlobalItemCounter.getdropCount().containsKey(20143) ?GlobalItemCounter.getdropCount().get(20143).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 20,  ""); //todo
			//pernix
			player.getPackets().sendIComponentText(3028, 21, (GlobalItemCounter.getdropCount().containsKey(20147) ?GlobalItemCounter.getdropCount().get(20147).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 22, (GlobalItemCounter.getdropCount().containsKey(20151) ?GlobalItemCounter.getdropCount().get(20151).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 23, (GlobalItemCounter.getdropCount().containsKey(20155) ?GlobalItemCounter.getdropCount().get(20155).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 24, ""); //todo
			//virtus
			player.getPackets().sendIComponentText(3028, 25, (GlobalItemCounter.getdropCount().containsKey(20159) ?GlobalItemCounter.getdropCount().get(20159).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 26, (GlobalItemCounter.getdropCount().containsKey(20163) ?GlobalItemCounter.getdropCount().get(20163).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 27, (GlobalItemCounter.getdropCount().containsKey(20167) ?GlobalItemCounter.getdropCount().get(20167).toString(): ""+0));
			player.getPackets().sendIComponentText(3028, 28, "0"); //todo
	}
	
	public void sendGlobalItemCounting2(){
	      player.getInterfaceManager().sendInterface(3029);
	      player.getPackets().sendIComponentText(3029, 30, "Global Drop Counting");
	      //corp
		  	player.getPackets().sendIComponentText(3029, 1, (GlobalItemCounter.getdropCount().containsKey(13746) ? GlobalItemCounter.getdropCount().get(13746).toString() : "0"));
		  	player.getPackets().sendIComponentText(3029, 2, (GlobalItemCounter.getdropCount().containsKey(13750) ? GlobalItemCounter.getdropCount().get(13750).toString() : ""+0));
			player.getPackets().sendIComponentText(3029, 3, (GlobalItemCounter.getdropCount().containsKey(13748) ?GlobalItemCounter.getdropCount().get(13748).toString() : ""+0));
			player.getPackets().sendIComponentText(3029, 4, (GlobalItemCounter.getdropCount().containsKey(13752) ?GlobalItemCounter.getdropCount().get(13752).toString() : ""+0));
			//dks rings
			player.getPackets().sendIComponentText(3029, 5, (player.getdropCount().containsKey(6733) ?GlobalItemCounter.getdropCount().get(6733).toString() : ""+0));
			player.getPackets().sendIComponentText(3029, 6, (GlobalItemCounter.getdropCount().containsKey(6737) ?GlobalItemCounter.getdropCount().get(6737).toString() : ""+0));
			player.getPackets().sendIComponentText(3029, 7, (GlobalItemCounter.getdropCount().containsKey(6731) ?GlobalItemCounter.getdropCount().get(6731).toString() : ""+0));
			player.getPackets().sendIComponentText(3029, 8, (GlobalItemCounter.getdropCount().containsKey(6735) ?GlobalItemCounter.getdropCount().get(6735).toString() : ""+0));
			//tools and dragon stuff
			player.getPackets().sendIComponentText(3029, 9,  (GlobalItemCounter.getdropCount().containsKey(6739) ?GlobalItemCounter.getdropCount().get(6739).toString() : ""+0));
			player.getPackets().sendIComponentText(3029, 10, (GlobalItemCounter.getdropCount().containsKey(15259) ?GlobalItemCounter.getdropCount().get(15259).toString() : ""+0));
			player.getPackets().sendIComponentText(3029, 11, (GlobalItemCounter.getdropCount().containsKey(11286) ?GlobalItemCounter.getdropCount().get(11286).toString() : ""+0));
			player.getPackets().sendIComponentText(3029, 12, (GlobalItemCounter.getdropCount().containsKey(6585) ?GlobalItemCounter.getdropCount().get(6585).toString() : ""+0));
			//glacors
			player.getPackets().sendIComponentText(3029, 13, (GlobalItemCounter.getdropCount().containsKey(21787) ?GlobalItemCounter.getdropCount().get(21787).toString(): ""+0));
			player.getPackets().sendIComponentText(3029, 14, (GlobalItemCounter.getdropCount().containsKey(21790) ?GlobalItemCounter.getdropCount().get(21790).toString(): ""+0));
			player.getPackets().sendIComponentText(3029, 15, (GlobalItemCounter.getdropCount().containsKey(21793) ?GlobalItemCounter.getdropCount().get(21793).toString(): ""+0));
			player.getPackets().sendIComponentText(3029, 16, "0");
			//drygores
			player.getPackets().sendIComponentText(3029, 17, (GlobalItemCounter.getdropCount().containsKey(26587) ?GlobalItemCounter.getdropCount().get(26587).toString(): ""+0));
			player.getPackets().sendIComponentText(3029, 18, (GlobalItemCounter.getdropCount().containsKey(26595) ?GlobalItemCounter.getdropCount().get(26595).toString(): ""+0));
			player.getPackets().sendIComponentText(3029, 19, (GlobalItemCounter.getdropCount().containsKey(26579) ?GlobalItemCounter.getdropCount().get(26579).toString(): ""+0));
			player.getPackets().sendIComponentText(3029, 20,  ""); //todo
			//dyes
			player.getPackets().sendIComponentText(3029, 21, ""+0);
			player.getPackets().sendIComponentText(3029, 22,  ""+0);
			player.getPackets().sendIComponentText(3029, 23, ""+0);
			player.getPackets().sendIComponentText(3029, 24, "0"); //todo
			//virtus
			player.getPackets().sendIComponentText(3029, 25, (GlobalItemCounter.getdropCount().containsKey(13734) ?GlobalItemCounter.getdropCount().get(13734).toString(): ""+0));
			player.getPackets().sendIComponentText(3029, 26, (GlobalItemCounter.getdropCount().containsKey(13754) ?GlobalItemCounter.getdropCount().get(13754).toString(): ""+0));
			player.getPackets().sendIComponentText(3029, 27, "0");
			player.getPackets().sendIComponentText(3029, 28, (GlobalItemCounter.getdropCount().containsKey(28437) ?GlobalItemCounter.getdropCount().get(28437).toString(): ""+0));
		}
	
	public void sendGlobalItemCounting3(){
		
	      player.getInterfaceManager().sendInterface(3030);
	      player.getPackets().sendIComponentText(3030, 30, "Global Drop Counting");
	      //ahrim
		  	player.getPackets().sendIComponentText(3030, 1, (GlobalItemCounter.getdropCount().containsKey(4708) ? GlobalItemCounter.getdropCount().get(4708).toString() : "0"));
		  	player.getPackets().sendIComponentText(3030, 2, (GlobalItemCounter.getdropCount().containsKey(4712) ? GlobalItemCounter.getdropCount().get(4712).toString() : ""+0));
			player.getPackets().sendIComponentText(3030, 3, (GlobalItemCounter.getdropCount().containsKey(4714) ?GlobalItemCounter.getdropCount().get(4714).toString() : ""+0));
			player.getPackets().sendIComponentText(3030, 4, (GlobalItemCounter.getdropCount().containsKey(4710) ?GlobalItemCounter.getdropCount().get(4710).toString() : ""+0));
			//dharok
			player.getPackets().sendIComponentText(3030, 5, (GlobalItemCounter.getdropCount().containsKey(4716) ?GlobalItemCounter.getdropCount().get(4716).toString() : ""+0));
			player.getPackets().sendIComponentText(3030, 6, (GlobalItemCounter.getdropCount().containsKey(4720) ?GlobalItemCounter.getdropCount().get(4720).toString() : ""+0));
			player.getPackets().sendIComponentText(3030, 7, (GlobalItemCounter.getdropCount().containsKey(4722) ?GlobalItemCounter.getdropCount().get(4722).toString() : ""+0));
			player.getPackets().sendIComponentText(3030, 8, (GlobalItemCounter.getdropCount().containsKey(4718) ?GlobalItemCounter.getdropCount().get(4718).toString() : ""+0));
			//gutha
			player.getPackets().sendIComponentText(3030, 9,  (GlobalItemCounter.getdropCount().containsKey(4724) ?GlobalItemCounter.getdropCount().get(4724).toString() : ""+0));
			player.getPackets().sendIComponentText(3030, 10, (GlobalItemCounter.getdropCount().containsKey(4728) ?GlobalItemCounter.getdropCount().get(4728).toString() : ""+0));
			player.getPackets().sendIComponentText(3030, 11, (GlobalItemCounter.getdropCount().containsKey(4730) ?GlobalItemCounter.getdropCount().get(4730).toString() : ""+0));
			player.getPackets().sendIComponentText(3030, 12, (GlobalItemCounter.getdropCount().containsKey(4726) ?GlobalItemCounter.getdropCount().get(4726).toString() : ""+0));
			//karils
			player.getPackets().sendIComponentText(3030, 13, (GlobalItemCounter.getdropCount().containsKey(4732) ?GlobalItemCounter.getdropCount().get(4732).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 14, (GlobalItemCounter.getdropCount().containsKey(4736) ?GlobalItemCounter.getdropCount().get(4736).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 15, (GlobalItemCounter.getdropCount().containsKey(4738) ?GlobalItemCounter.getdropCount().get(4738).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 16, (GlobalItemCounter.getdropCount().containsKey(4734) ?GlobalItemCounter.getdropCount().get(4734).toString(): ""+0));
			//verac
			player.getPackets().sendIComponentText(3030, 17, (GlobalItemCounter.getdropCount().containsKey(4753) ?GlobalItemCounter.getdropCount().get(4753).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 18, (GlobalItemCounter.getdropCount().containsKey(4757) ?GlobalItemCounter.getdropCount().get(4757).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 19, (GlobalItemCounter.getdropCount().containsKey(4759) ?GlobalItemCounter.getdropCount().get(4759).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 20, (GlobalItemCounter.getdropCount().containsKey(4755) ?GlobalItemCounter.getdropCount().get(4755).toString(): ""+0));
			//torag
			player.getPackets().sendIComponentText(3030, 21, (GlobalItemCounter.getdropCount().containsKey(4745) ?GlobalItemCounter.getdropCount().get(4745).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 22, (GlobalItemCounter.getdropCount().containsKey(4749) ?GlobalItemCounter.getdropCount().get(4749).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 23, (GlobalItemCounter.getdropCount().containsKey(4751) ?GlobalItemCounter.getdropCount().get(4751).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 24, (GlobalItemCounter.getdropCount().containsKey(4747) ?GlobalItemCounter.getdropCount().get(4747).toString(): ""+0));
			//misc
			player.getPackets().sendIComponentText(3030, 25, (GlobalItemCounter.getdropCount().containsKey(25652) ?GlobalItemCounter.getdropCount().get(25652).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 26, (GlobalItemCounter.getdropCount().containsKey(25672) ?GlobalItemCounter.getdropCount().get(25672).toString(): ""+0));
			player.getPackets().sendIComponentText(3030, 27, "0");
			player.getPackets().sendIComponentText(3030, 28, "");
		}
		
		public void sendGlobalItemCounting4(){
	      player.getInterfaceManager().sendInterface(3031);
	      player.getPackets().sendIComponentText(3031, 30, "Global Drop Counting");
	      
		  	player.getPackets().sendIComponentText(3031, 1, (GlobalItemCounter.getdropCount().containsKey(4151) ? GlobalItemCounter.getdropCount().get(4151).toString() : "0"));
		  	player.getPackets().sendIComponentText(3031, 2, (GlobalItemCounter.getdropCount().containsKey(4712) ? GlobalItemCounter.getdropCount().get(4712).toString() : ""+0));
			player.getPackets().sendIComponentText(3031, 3, (GlobalItemCounter.getdropCount().containsKey(4714) ?GlobalItemCounter.getdropCount().get(4714).toString() : ""+0));
			player.getPackets().sendIComponentText(3031, 4, (GlobalItemCounter.getdropCount().containsKey(4710) ?GlobalItemCounter.getdropCount().get(4710).toString() : ""+0));
		
			player.getPackets().sendIComponentText(3031, 5, (GlobalItemCounter.getdropCount().containsKey(4716) ?GlobalItemCounter.getdropCount().get(4716).toString() : ""+0));
			player.getPackets().sendIComponentText(3031, 6, (GlobalItemCounter.getdropCount().containsKey(4720) ?GlobalItemCounter.getdropCount().get(4720).toString() : ""+0));
			player.getPackets().sendIComponentText(3031, 7, (GlobalItemCounter.getdropCount().containsKey(4722) ?GlobalItemCounter.getdropCount().get(4722).toString() : ""+0));
			player.getPackets().sendIComponentText(3031, 8, (GlobalItemCounter.getdropCount().containsKey(4718) ?GlobalItemCounter.getdropCount().get(4718).toString() : ""+0));

			player.getPackets().sendIComponentText(3031, 9,  (GlobalItemCounter.getdropCount().containsKey(4724) ?GlobalItemCounter.getdropCount().get(4724).toString() : ""+0));
			player.getPackets().sendIComponentText(3031, 10, (GlobalItemCounter.getdropCount().containsKey(4728) ?GlobalItemCounter.getdropCount().get(4728).toString() : ""+0));
			player.getPackets().sendIComponentText(3031, 11, (GlobalItemCounter.getdropCount().containsKey(4730) ?GlobalItemCounter.getdropCount().get(4730).toString() : ""+0));
			player.getPackets().sendIComponentText(3031, 12, (GlobalItemCounter.getdropCount().containsKey(4726) ?GlobalItemCounter.getdropCount().get(4726).toString() : ""+0));
		
			player.getPackets().sendIComponentText(3031, 13, (GlobalItemCounter.getdropCount().containsKey(4732) ?GlobalItemCounter.getdropCount().get(4732).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 14, (GlobalItemCounter.getdropCount().containsKey(4736) ?GlobalItemCounter.getdropCount().get(4736).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 15, (GlobalItemCounter.getdropCount().containsKey(4738) ?GlobalItemCounter.getdropCount().get(4738).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 16, (GlobalItemCounter.getdropCount().containsKey(4734) ?GlobalItemCounter.getdropCount().get(4734).toString(): ""+0));
			
			player.getPackets().sendIComponentText(3031, 17, (GlobalItemCounter.getdropCount().containsKey(4753) ?GlobalItemCounter.getdropCount().get(4753).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 18, (GlobalItemCounter.getdropCount().containsKey(4757) ?GlobalItemCounter.getdropCount().get(4757).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 19, (GlobalItemCounter.getdropCount().containsKey(4759) ?GlobalItemCounter.getdropCount().get(4759).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 20, (GlobalItemCounter.getdropCount().containsKey(4755) ?GlobalItemCounter.getdropCount().get(4755).toString(): ""+0));
		
			player.getPackets().sendIComponentText(3031, 21, (GlobalItemCounter.getdropCount().containsKey(4745) ?GlobalItemCounter.getdropCount().get(4745).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 22, (GlobalItemCounter.getdropCount().containsKey(4749) ?GlobalItemCounter.getdropCount().get(4749).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 23, (GlobalItemCounter.getdropCount().containsKey(4751) ?GlobalItemCounter.getdropCount().get(4751).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 24, (GlobalItemCounter.getdropCount().containsKey(4747) ?GlobalItemCounter.getdropCount().get(4747).toString(): ""+0));
		
			player.getPackets().sendIComponentText(3031, 25, (GlobalItemCounter.getdropCount().containsKey(25652) ?GlobalItemCounter.getdropCount().get(25652).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 26, (GlobalItemCounter.getdropCount().containsKey(25672) ?GlobalItemCounter.getdropCount().get(25672).toString(): ""+0));
			player.getPackets().sendIComponentText(3031, 27, "0");
			player.getPackets().sendIComponentText(3031, 28, "");
		}
	/**
	 * start personal counting
	 */
	public void sendPersonalItemCounting1(){
      player.getInterfaceManager().sendInterface(3028);
      player.getPackets().sendIComponentText(3028, 30, "Personal Drop Counting");
	  	player.getPackets().sendIComponentText(3028, 1, (player.getdropCount().containsKey(25022) ? player.getdropCount().get(25022).toString() : "0"));
	  	player.getPackets().sendIComponentText(3028, 2, (player.getdropCount().containsKey(11724) ? player.getdropCount().get(11724).toString() : ""+0));
		player.getPackets().sendIComponentText(3028, 3, (player.getdropCount().containsKey(11726) ?player.getdropCount().get(11726).toString() : ""+0));
		player.getPackets().sendIComponentText(3028, 4, (player.getdropCount().containsKey(11704) ?player.getdropCount().get(11704).toString() : ""+0));
		//armadyl
		player.getPackets().sendIComponentText(3028, 5, (player.getdropCount().containsKey(11718) ?player.getdropCount().get(11718).toString() : ""+0));
		player.getPackets().sendIComponentText(3028, 6, (player.getdropCount().containsKey(11720) ?player.getdropCount().get(11720).toString() : ""+0));
		player.getPackets().sendIComponentText(3028, 7, (player.getdropCount().containsKey(11722) ?player.getdropCount().get(11722).toString() : ""+0));
		player.getPackets().sendIComponentText(3028, 8, (player.getdropCount().containsKey(11702) ?player.getdropCount().get(11702).toString() : ""+0));
		//Zamorak
		player.getPackets().sendIComponentText(3028, 9,  (player.getdropCount().containsKey(24992) ?player.getdropCount().get(24992).toString() : ""+0));
		player.getPackets().sendIComponentText(3028, 10, (player.getdropCount().containsKey(24995) ?player.getdropCount().get(24995).toString() : ""+0));
		player.getPackets().sendIComponentText(3028, 11, (player.getdropCount().containsKey(24998) ?player.getdropCount().get(24998).toString() : ""+0));
		player.getPackets().sendIComponentText(3028, 12, (player.getdropCount().containsKey(11708) ?player.getdropCount().get(11708).toString() : ""+0));
		//armadyl
		player.getPackets().sendIComponentText(3028, 13, "");//Todo
		player.getPackets().sendIComponentText(3028, 14, (player.getdropCount().containsKey(25037) ?player.getdropCount().get(25037).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 15, (player.getdropCount().containsKey(11730) ?player.getdropCount().get(11730).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 16, (player.getdropCount().containsKey(11706) ?player.getdropCount().get(11706).toString(): ""+0));
		//torva
		player.getPackets().sendIComponentText(3028, 17, (player.getdropCount().containsKey(20135) ?player.getdropCount().get(20135).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 18, (player.getdropCount().containsKey(20139) ?player.getdropCount().get(20139).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 19, (player.getdropCount().containsKey(20143) ?player.getdropCount().get(20143).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 20,  ""); //todo
		//pernix
		player.getPackets().sendIComponentText(3028, 21, (player.getdropCount().containsKey(20147) ?player.getdropCount().get(20147).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 22, (player.getdropCount().containsKey(20151) ?player.getdropCount().get(20151).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 23, (player.getdropCount().containsKey(20155) ?player.getdropCount().get(20155).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 24, ""); //todo
		//virtus
		player.getPackets().sendIComponentText(3028, 25, (player.getdropCount().containsKey(20159) ?player.getdropCount().get(20159).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 26, (player.getdropCount().containsKey(20163) ?player.getdropCount().get(20163).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 27, (player.getdropCount().containsKey(20167) ?player.getdropCount().get(20167).toString(): ""+0));
		player.getPackets().sendIComponentText(3028, 28, "0"); //todo
	}
	/**
	 * send the second page of the drop counter
	 */
	public void sendPersonalItemCounting2(){
      player.getInterfaceManager().sendInterface(3029);
      player.getPackets().sendIComponentText(3029, 30, "Personal Drop Counting");
      //corp
	  	player.getPackets().sendIComponentText(3029, 1, (player.getdropCount().containsKey(13746) ? player.getdropCount().get(13746).toString() : "0"));
	  	player.getPackets().sendIComponentText(3029, 2, (player.getdropCount().containsKey(13750) ? player.getdropCount().get(13750).toString() : ""+0));
		player.getPackets().sendIComponentText(3029, 3, (player.getdropCount().containsKey(13748) ?player.getdropCount().get(13748).toString() : ""+0));
		player.getPackets().sendIComponentText(3029, 4, (player.getdropCount().containsKey(13752) ?player.getdropCount().get(13752).toString() : ""+0));
		//dks rings
		player.getPackets().sendIComponentText(3029, 5, (player.getdropCount().containsKey(6733) ?player.getdropCount().get(6733).toString() : ""+0));
		player.getPackets().sendIComponentText(3029, 6, (player.getdropCount().containsKey(6737) ?player.getdropCount().get(6737).toString() : ""+0));
		player.getPackets().sendIComponentText(3029, 7, (player.getdropCount().containsKey(6731) ?player.getdropCount().get(6731).toString() : ""+0));
		player.getPackets().sendIComponentText(3029, 8, (player.getdropCount().containsKey(6735) ?player.getdropCount().get(6735).toString() : ""+0));
		//tools and dragon stuff
		player.getPackets().sendIComponentText(3029, 9,  (player.getdropCount().containsKey(6739) ?player.getdropCount().get(6739).toString() : ""+0));
		player.getPackets().sendIComponentText(3029, 10, (player.getdropCount().containsKey(15259) ?player.getdropCount().get(15259).toString() : ""+0));
		player.getPackets().sendIComponentText(3029, 11, (player.getdropCount().containsKey(11286) ?player.getdropCount().get(11286).toString() : ""+0));
		player.getPackets().sendIComponentText(3029, 12, (player.getdropCount().containsKey(6585) ?player.getdropCount().get(6585).toString() : ""+0));
		//glacors
		player.getPackets().sendIComponentText(3029, 13, (player.getdropCount().containsKey(21787) ?player.getdropCount().get(21787).toString(): ""+0));
		player.getPackets().sendIComponentText(3029, 14, (player.getdropCount().containsKey(21790) ?player.getdropCount().get(21790).toString(): ""+0));
		player.getPackets().sendIComponentText(3029, 15, (player.getdropCount().containsKey(21793) ?player.getdropCount().get(21793).toString(): ""+0));
		player.getPackets().sendIComponentText(3029, 16, "0");
		//drygores
		player.getPackets().sendIComponentText(3029, 17, (player.getdropCount().containsKey(26587) ?player.getdropCount().get(26587).toString(): ""+0));
		player.getPackets().sendIComponentText(3029, 18, (player.getdropCount().containsKey(26595) ?player.getdropCount().get(26595).toString(): ""+0));
		player.getPackets().sendIComponentText(3029, 19, (player.getdropCount().containsKey(26579) ?player.getdropCount().get(26579).toString(): ""+0));
		player.getPackets().sendIComponentText(3029, 20,  ""); //todo
		//dyes
		player.getPackets().sendIComponentText(3029, 21, ""+0);
		player.getPackets().sendIComponentText(3029, 22,  ""+0);
		player.getPackets().sendIComponentText(3029, 23, ""+0);
		player.getPackets().sendIComponentText(3029, 24, "0"); //todo
		//virtus
		player.getPackets().sendIComponentText(3029, 25, (player.getdropCount().containsKey(13734) ?player.getdropCount().get(13734).toString(): ""+0));
		player.getPackets().sendIComponentText(3029, 26, (player.getdropCount().containsKey(13754) ?player.getdropCount().get(13754).toString(): ""+0));
		player.getPackets().sendIComponentText(3029, 27, "0");
		player.getPackets().sendIComponentText(3029, 28, (player.getdropCount().containsKey(28437) ?player.getdropCount().get(28437).toString(): ""+0));
	}
	
	public void sendPersonalItemCounting3(){
      player.getInterfaceManager().sendInterface(3030);
      player.getPackets().sendIComponentText(3030, 30, "Personal Drop Counting");
      //ahrim
	  	player.getPackets().sendIComponentText(3030, 1, (player.getdropCount().containsKey(4708) ? player.getdropCount().get(4708).toString() : "0"));
	  	player.getPackets().sendIComponentText(3030, 2, (player.getdropCount().containsKey(4712) ? player.getdropCount().get(4712).toString() : ""+0));
		player.getPackets().sendIComponentText(3030, 3, (player.getdropCount().containsKey(4714) ?player.getdropCount().get(4714).toString() : ""+0));
		player.getPackets().sendIComponentText(3030, 4, (player.getdropCount().containsKey(4710) ?player.getdropCount().get(4710).toString() : ""+0));
		//dharok
		player.getPackets().sendIComponentText(3030, 5, (player.getdropCount().containsKey(4716) ?player.getdropCount().get(4716).toString() : ""+0));
		player.getPackets().sendIComponentText(3030, 6, (player.getdropCount().containsKey(4720) ?player.getdropCount().get(4720).toString() : ""+0));
		player.getPackets().sendIComponentText(3030, 7, (player.getdropCount().containsKey(4722) ?player.getdropCount().get(4722).toString() : ""+0));
		player.getPackets().sendIComponentText(3030, 8, (player.getdropCount().containsKey(4718) ?player.getdropCount().get(4718).toString() : ""+0));
		//gutha
		player.getPackets().sendIComponentText(3030, 9,  (player.getdropCount().containsKey(4724) ?player.getdropCount().get(4724).toString() : ""+0));
		player.getPackets().sendIComponentText(3030, 10, (player.getdropCount().containsKey(4728) ?player.getdropCount().get(4728).toString() : ""+0));
		player.getPackets().sendIComponentText(3030, 11, (player.getdropCount().containsKey(4730) ?player.getdropCount().get(4730).toString() : ""+0));
		player.getPackets().sendIComponentText(3030, 12, (player.getdropCount().containsKey(4726) ?player.getdropCount().get(4726).toString() : ""+0));
		//karils
		player.getPackets().sendIComponentText(3030, 13, (player.getdropCount().containsKey(4732) ?player.getdropCount().get(4732).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 14, (player.getdropCount().containsKey(4736) ?player.getdropCount().get(4736).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 15, (player.getdropCount().containsKey(4738) ?player.getdropCount().get(4738).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 16, (player.getdropCount().containsKey(4734) ?player.getdropCount().get(4734).toString(): ""+0));
		//verac
		player.getPackets().sendIComponentText(3030, 17, (player.getdropCount().containsKey(4753) ?player.getdropCount().get(4753).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 18, (player.getdropCount().containsKey(4757) ?player.getdropCount().get(4757).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 19, (player.getdropCount().containsKey(4759) ?player.getdropCount().get(4759).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 20, (player.getdropCount().containsKey(4755) ?player.getdropCount().get(4755).toString(): ""+0));
		//torag
		player.getPackets().sendIComponentText(3030, 21, (player.getdropCount().containsKey(4745) ?player.getdropCount().get(4745).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 22, (player.getdropCount().containsKey(4749) ?player.getdropCount().get(4749).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 23, (player.getdropCount().containsKey(4751) ?player.getdropCount().get(4751).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 24, (player.getdropCount().containsKey(4747) ?player.getdropCount().get(4747).toString(): ""+0));
		//misc
		player.getPackets().sendIComponentText(3030, 25, (player.getdropCount().containsKey(25652) ?player.getdropCount().get(25652).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 26, (player.getdropCount().containsKey(25672) ?player.getdropCount().get(25672).toString(): ""+0));
		player.getPackets().sendIComponentText(3030, 27, "0");
		player.getPackets().sendIComponentText(3030, 28, "");
	}
	
	public void sendPersonalItemCounting4(){
      player.getInterfaceManager().sendInterface(3031);
      player.getPackets().sendIComponentText(3031, 30, "Personal Drop Counting");
      
	  	player.getPackets().sendIComponentText(3031, 1, (player.getdropCount().containsKey(4151) ? player.getdropCount().get(4151).toString() : "0"));
	  	player.getPackets().sendIComponentText(3031, 2, (player.getdropCount().containsKey(4712) ? player.getdropCount().get(4712).toString() : ""+0));
		player.getPackets().sendIComponentText(3031, 3, (player.getdropCount().containsKey(4714) ?player.getdropCount().get(4714).toString() : ""+0));
		player.getPackets().sendIComponentText(3031, 4, (player.getdropCount().containsKey(4710) ?player.getdropCount().get(4710).toString() : ""+0));
	
		player.getPackets().sendIComponentText(3031, 5, (player.getdropCount().containsKey(4716) ?player.getdropCount().get(4716).toString() : ""+0));
		player.getPackets().sendIComponentText(3031, 6, (player.getdropCount().containsKey(4720) ?player.getdropCount().get(4720).toString() : ""+0));
		player.getPackets().sendIComponentText(3031, 7, (player.getdropCount().containsKey(4722) ?player.getdropCount().get(4722).toString() : ""+0));
		player.getPackets().sendIComponentText(3031, 8, (player.getdropCount().containsKey(4718) ?player.getdropCount().get(4718).toString() : ""+0));

		player.getPackets().sendIComponentText(3031, 9,  (player.getdropCount().containsKey(4724) ?player.getdropCount().get(4724).toString() : ""+0));
		player.getPackets().sendIComponentText(3031, 10, (player.getdropCount().containsKey(4728) ?player.getdropCount().get(4728).toString() : ""+0));
		player.getPackets().sendIComponentText(3031, 11, (player.getdropCount().containsKey(4730) ?player.getdropCount().get(4730).toString() : ""+0));
		player.getPackets().sendIComponentText(3031, 12, (player.getdropCount().containsKey(4726) ?player.getdropCount().get(4726).toString() : ""+0));
	
		player.getPackets().sendIComponentText(3031, 13, (player.getdropCount().containsKey(4732) ?player.getdropCount().get(4732).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 14, (player.getdropCount().containsKey(4736) ?player.getdropCount().get(4736).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 15, (player.getdropCount().containsKey(4738) ?player.getdropCount().get(4738).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 16, (player.getdropCount().containsKey(4734) ?player.getdropCount().get(4734).toString(): ""+0));
		
		player.getPackets().sendIComponentText(3031, 17, (player.getdropCount().containsKey(4753) ?player.getdropCount().get(4753).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 18, (player.getdropCount().containsKey(4757) ?player.getdropCount().get(4757).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 19, (player.getdropCount().containsKey(4759) ?player.getdropCount().get(4759).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 20, (player.getdropCount().containsKey(4755) ?player.getdropCount().get(4755).toString(): ""+0));
	
		player.getPackets().sendIComponentText(3031, 21, (player.getdropCount().containsKey(4745) ?player.getdropCount().get(4745).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 22, (player.getdropCount().containsKey(4749) ?player.getdropCount().get(4749).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 23, (player.getdropCount().containsKey(4751) ?player.getdropCount().get(4751).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 24, (player.getdropCount().containsKey(4747) ?player.getdropCount().get(4747).toString(): ""+0));
	
		player.getPackets().sendIComponentText(3031, 25, (player.getdropCount().containsKey(25652) ?player.getdropCount().get(25652).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 26, (player.getdropCount().containsKey(25672) ?player.getdropCount().get(25672).toString(): ""+0));
		player.getPackets().sendIComponentText(3031, 27, "0");
		player.getPackets().sendIComponentText(3031, 28, "");
	}

	public void closeDungPartyInterface() {
		sendPlayerCustom();
		player.getPackets().sendGlobalConfig(234, 0);
	}

	public void setDefaultRootInterface() {
		
	}
	
	


}
