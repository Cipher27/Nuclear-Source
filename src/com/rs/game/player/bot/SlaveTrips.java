package com.rs.game.player.bot;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.npc.Drop;
import com.rs.game.player.Player;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

/**
 * handles slave trips
 * @author paolo
 *
 */

public class SlaveTrips implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5035713018547635720L;
	/**
	 * sets the player
	 */
	
	 private transient Player player;
	 
	 public void setPlayer(Player player) {
			this.player = player;
	 }
	 /**
	  * enum of all the types of armor
	  * @author paolo
	  *
	  */
	public SlaveTrips(){
		setMonsters();
	}
	public enum SETUP {BRONZE,RUNE,DRAGON,NEX};
	/**
	 * variables
	 */
	private int button;
	 private int lootId;
	 public int monsterTaskId;
	 public boolean hasTask = false;
	 public boolean hasSlave = false;
	 public int slaveAmounts = 0;
	 public SETUP setup;
	 public static int PRICE = 1000000000;
	 public static int MAX_SLAVES = 3;
	 
		
	public int hunterLoot[] =   {10033,10034,12539};
	 
	 /**
		 * Longs representing task voyage times.
		 */
	 public long tripTime;
	 /**
	* Integers representing taks voyage times that were set.
		*/
	 public int tripDurage;
	 
	 /**
	  * for combat trips
	  */
	 //stores loot
	 private static final Map<Integer, Integer> loot = new HashMap<>();
	 /**
	  * npcs that can be set as a task
	  */
	 static int normalNpc[] = {55,2783,1615,1,1610,9463,54,5361,1591,1592,1610,4699};
	/**
	 * rare boss tasks
	 */
	 static int SpecialNpc[] = {6260, 50, 14301};
	 
	 public int monsters[] = new int[3];
	 /**
	  * gives the available options
	  */
	 public void setMonsters(){
		 for(int i =0; i < 3; i ++)
	   monsters[i] = normalNpc[Utils.random(0,normalNpc.length)];
	 }
	 /**
	  * sets the task
	  * @param id
	  */
	 public void setTask(int id){
		 if(!hasSlave) //never know
			 return;
		 if(hasTask){
			 player.getDialogueManager().startDialogue("SimpleMessage","Your slave can only do one task a time.");
			 return;
		 }
		 tripTime = Utils.currentTimeMillis();
		 tripDurage =  60 * 60 * 1000; //1hour 
		 hasTask = true;
		 lootId = id;
		 player.sm("lootId: "+lootId);
		 player.getDialogueManager().startDialogue("SimpleMessage","Your slave has been succesfully deployed.");
		 
	 }
	 public boolean handelTrip(){
		 long timeVariation = Utils.currentTimeMillis() - tripTime;
		 if (timeVariation < tripDurage)
	    		return false;
			return true;
	}
	 /**
		 * Checks how much minutes left until voyage for ship Alpha ends.
		 * @return minutes as String.
		 */
		public String getTripTimeLeft() {
			long toWait = tripDurage - (Utils.currentTimeMillis() - tripTime);
			return Integer.toString(Utils.millisecsToMinutes(toWait));
		}
	 /**
	  * gives the player his loot after the task
	  */
	 public void returnTask(){
		 if(!hasTask)
			 return;
		 hasTask = false;
		 player.getBank().addItem(lootId,getAmount() * Utils.random(100,200), true);
		 player.sm("Your slave has returned from it's trip, the loot has been added to your bank");
	 }
	 /**
	  * gets the amount
	  * @return
	  */
	 public int getAmount(){
		 switch(lootId){
		 //fishing
		 case 7944:
			 return Utils.random(2,5);
		 case 377:
			 return Utils.random(4,6);
		 case 371:
			 return Utils.random(1,3);
		 case 383:
			 return Utils.random(1,2);
		 case 15270:
			 return Utils.random(1,2);
		//mining
		 case 449:
			 return Utils.random(2,5);
		 case 451:
			 return Utils.random(1,2);
		 case 440:
			 return Utils.random(8,12);
		 case 453:
			 return Utils.random(5,11);
		case 447:
			return Utils.random(5,11);
		//woodcutting
		 case 1515:
			 return Utils.random(2,5);
		 case 1513:
			 return Utils.random(1,2);
		 case 1517:
			 return Utils.random(8,10);
		 case 1519:
			 return Utils.random(8,13);
		//hunter
		 case 10033:
		 	return Utils.random(2,5);
		 case 10034:
			 return Utils.random(2,3);
		 case 12539:
			 return Utils.random(8,10);
	    default:
	    	return generateCombatQuantity();
		 }
	 }
	 /**
	  * returns amount of monsters killed
	  * @return
	  */
	 public int generateCombatQuantity(){
		int combatlevel = NPCDefinitions.getNPCDefinitions(monsterTaskId).combatLevel;
		if (combatlevel < 50) 
			return Utils.random(70, 200);
		if (combatlevel > 50 && combatlevel <100) 
			return Utils.random(80,105);
		if (combatlevel >= 100 && combatlevel < 150)
			return Utils.random(40, 80);
		if (combatlevel >= 150)
			return Utils.random(10, 28);
		 return 0;
	 }
	 /**
	  * gets the loot of our npc id
	  * @param player
	  * @param npc
	  * @param amount
	  * @throws IOException
	  */
	 public void monsterLoot() throws IOException {
			Cache.init();
			NPCDrops.init();
			int npcId = lootId;
			int kills = getAmount();
			for (int i = 0; i < kills; i++) {
				Drop[] drops = NPCDrops.getDrops(npcId);
				if (drops == null) {
					player.sm("This npc has no drops.");
				    return;
				}
				Drop[] possibleDrops = new Drop[drops.length];
				int possibleDropsCount = 0;
				for (Drop drop : drops) {
					if (drop.getRate() == 100) {
						int previousValue = loot.get(drop.getItemId()) != null ?  loot.get(drop.getItemId()) : 0;
						loot.put(drop.getItemId(), drop.getMinAmount() + Utils.getRandom(drop.getExtraAmount()) + previousValue);
					} else {
						if ((Utils.getRandomDouble(99) + 1) <= drop.getRate()) {
							possibleDrops[possibleDropsCount++] = drop;
						}
					}
				}
				if (possibleDropsCount > 0) {
					Drop drop2 = possibleDrops[Utils.getRandom(possibleDropsCount - 1)];
					int previousValue = loot.get(drop2.getItemId()) != null ?  loot.get(drop2.getItemId()) : 0;
					loot.put(drop2.getItemId(), drop2.getMinAmount() + Utils.getRandom(drop2.getExtraAmount()) + previousValue);
				}
			}
			for (Entry<Integer, Integer> entry : loot.entrySet()) {
					player.getBank().addItem(ItemDefinitions.getItemDefinitions(entry.getKey()).id, entry.getValue(),true);	
			}
		loot.clear();
		}
		/**
		  * Interface part here
		  **/
	    boolean pickedLootId = false;
		public void sendHunterInterface(){
		pickedLootId = false;
			int length = SlaveGather.hunterLoot.length;
			//hides all the icons
			for (int i =0; i <5; i++){
				player.getPackets().sendHideIComponent(3046,15+i,false);
				player.getPackets().sendHideIComponent(3046,5+i,false); //so players can click again
			}
			for (int i = 0; i < length; i++)
				player.getPackets().sendItemOnIComponent(3046, 15+i,SlaveGather.hunterLoot[i],1);
			for(int i = length; i < 5; i ++){ 
				player.getPackets().sendHideIComponent(3046,15+i,true);
				player.getPackets().sendHideIComponent(3046,5+i,true);
			}
		}
		
		public void sendFishingInterface(){
		pickedLootId = false;
			int length = SlaveGather.fishingLoot.length;
			//hides all the icons
			for (int i =0; i <5; i++){
				player.getPackets().sendHideIComponent(3046,15+i,false);
				player.getPackets().sendHideIComponent(3046,5+i,false); //so players can click again
			}for (int i = 0; i < length; i++)
				player.getPackets().sendItemOnIComponent(3046, 15+i,SlaveGather.fishingLoot[i],1);
			for(int i = length; i < 5; i ++){ 
				player.getPackets().sendHideIComponent(3046,15+i,true);
				player.getPackets().sendHideIComponent(3046,5+i,true);
			}
		}
		public void sendWoodcuttingInterface(){
		pickedLootId = false;
			int length = SlaveGather.woodcuttingLoot.length;
			//hides all the icons
			for (int i =0; i <5; i++){
				player.getPackets().sendHideIComponent(3046,15+i,false);
				player.getPackets().sendHideIComponent(3046,5+i,false); //so players can click again
			}
			for (int i = 0; i < length; i++)
				player.getPackets().sendItemOnIComponent(3046, 15+i,SlaveGather.woodcuttingLoot[i],1);
			for(int i = length; i < 5; i ++){ 
				player.getPackets().sendHideIComponent(3046,15+i,true);
				player.getPackets().sendHideIComponent(3046,5+i,true);
			}
		}
		public void sendMiningInterface(){
			//player.getInterfaceManager().sendInterface(3046);
			pickedLootId = false;
			int length = SlaveGather.miningLoot.length;
			//hides all the icons
			for (int i =0; i <5; i++){
				player.getPackets().sendHideIComponent(3046,15+i,false);
				player.getPackets().sendHideIComponent(3046,5+i,false); //so players can click again
			}
			for (int i = 0; i < length; i++)
				player.getPackets().sendItemOnIComponent(3046, 15+i,SlaveGather.miningLoot[i],1);
			for(int i = length; i < 5; i ++) {
				player.getPackets().sendHideIComponent(3046,15+i,true);
				player.getPackets().sendHideIComponent(3046,5+i,true);
			}
		}
		/**
		 * handles the button clicking
		 * @param player
		 * @param componentId
		 */
	
		public void handleButtons(Player player, int componentId) {
				 switch(componentId){
				 case 1:
					 sendFishingInterface();
					 clearMarkLoot();
					 sendCheckMark(componentId);
					 button = componentId;
					 break;
				 case 2:
					 sendWoodcuttingInterface();
					 sendCheckMark(componentId);
					 clearMarkLoot();
					 button = componentId;
					 break;
				 case 3:
					 sendHunterInterface();
					 sendCheckMark(componentId);
					 clearMarkLoot();
					 button = componentId;
					 break;
				 case 4:
					 sendMiningInterface();
					 sendCheckMark(componentId);
					 clearMarkLoot();
					 button = componentId;
					 break;	 
				 case 5:
				 case 6:
				 case 7:
				 case 8:
				 case 9:
					 handelLootPicking(componentId); 
					 pickedLootId = true;
					 break;
				 case 20:
					 if(pickedLootId)
					 setTask(lootId);
					 else
						 player.getDialogueManager().startDialogue("SimpleMessage","Pick an item that your slave should gather.");
					 break;
				 case 21:
					 player.closeInterfaces(); 
					 break;
				 }
		}
		public void handelLootPicking(int compId){
			int index  = compId - 5;
			//if(compId== 5){
				switch(button){
				case 1:
					 sendCheckMarkLoot(compId);
					 lootId = SlaveGather.fishingLoot[index];
					 break;
				case 2:
					 sendCheckMarkLoot(compId);
					 lootId = SlaveGather.woodcuttingLoot[index];
					 break;
				case 3:
					 sendCheckMarkLoot(compId);
					 lootId = SlaveGather.hunterLoot[index];
					 break;
				case 4:
					 sendCheckMarkLoot(compId);
					 lootId = SlaveGather.miningLoot[index];
					 break;
				}
			//}
		}
		public void sendCheckMark(int compId){
			for(int i =1; i < 5; i ++){
				player.getPackets().sendIComponentSprite(3046,i,27247);
			}
			player.getPackets().sendIComponentSprite(3046,compId,27256);
		}
		public void sendCheckMarkLoot(int compId){
			for(int i =0; i < 5; i ++){
				player.getPackets().sendIComponentSprite(3046,5+i,27247);
			}
			player.getPackets().sendIComponentSprite(3046,compId,27256);
		}
		public void clearMarkLoot(){
			for(int i =0; i < 5; i ++)
				player.getPackets().sendIComponentSprite(3046,5+i,27247);
		}
 /**
  * 
  * @return
  */
	public int getLootId() {
		return lootId;
	}
/**
 * 
 * @param lootId
 */
	public void setLootId(int lootId) {
		this.lootId = lootId;
	}
	 
	
}