package com.rs.game.player.content;

import java.util.ArrayList;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.Controler;
import com.rs.utils.Utils;

/**
 * 
 * @author paolo
 *
 */
public class LividFarmHandler  extends Controler{
	
	/**
	 * calls this to start livid farm
	 * @param player
	 */
	public static void enterLividFarm(Player player) {
		player.getControlerManager().startControler("LividFarmHandler"); 
		player.sm("Your plants will spawn soon.");
	}
	/**
	 * enum of all the plants and their transformable ids
	 */
	public enum Plants{
		PLANT_1(40448,40452,40456),
		PLANT_2(40449,40453,40457),
		PLANT_3(40450,40454,40458),
		PLANT_4(40451,40455,40459);
		
		int normalId,sickId,curedId;
		
		Plants(int normal,int sick,int cured){
			normalId = normal;
			sickId = sick;
			curedId = cured;
		}
		
		public static Plants forId(int id) {
			for (Plants plant : Plants.values()) {
				if (plant.sickId == id) {
					return plant;
				}
			}
			return null;
		}
	}

   /**
    * ids
    */
	private int ticks;
	private int plantIds[] = {40446,40448,40452,40449,40453,40450,40454,40451,40455};
	public  int EMPTY_SPOT= 40446,FERTILISE_PATCH = 40447;
	private WorldTile spawnTiles[] =  {new WorldTile(2098,3949,0),new WorldTile(2100,3949,0),new WorldTile(2102,3949,0),new WorldTile(2104,3949,0),new WorldTile(2106,3949,0)};
	/**
	 * 
	 */
	public  void cureDiseas(WorldObject object, Player player){
		if(!Magic.checkRunes(player, true, Magic.ASTRAL_RUNE,1,Magic.EARTH_RUNE,8))
			return;
		if(player.getSkills().getLevel(Skills.MAGIC) < 66){
			player.sm("You need 66 magic befure you can cast this spell.");
			return;
		}
		Plants plant = Plants.forId(object.getId());
		player.setNextAnimation(new Animation(4432));
		player.setNextGraphics(new Graphics(748));
		player.getSkills().addSkillXpRefresh(Skills.MAGIC, 60);
		player.getSkills().addSkillXpRefresh(Skills.FARMING, 91.8);
		player.getPackets().sendDestroyObject(object);
		player.getPackets().sendSpawnedObject(new WorldObject(plant.curedId,10,0,object.getTile()));
		player.getPointsManager().addLividPoints(2);
	}
	/**
	 * fertilises the patch
	 */
	public void Fertilise(WorldObject object, Player player){
		if(!Magic.checkRunes(player, true, Magic.ASTRAL_RUNE,3,Magic.EARTH_RUNE,15,Magic.NATURE_RUNE,2))
			return;
		if(player.getSkills().getLevel(Skills.MAGIC) < 83){
			player.sm("You need 66 magic befure you can cast this spell.");
			return;
		}
		WorldObject patch = new WorldObject(FERTILISE_PATCH,10,0,object.getTile());
		player.getSkills().addSkillXpRefresh(Skills.MAGIC, 87);
		player.getSkills().addSkillXpRefresh(Skills.FARMING, 91.8);
		player.setNextAnimation(new Animation(4413));
		player.getPackets().sendGraphics(new Graphics(724), patch);
		player.getPackets().sendDestroyObject(object);
		player.getPackets().sendSpawnedObject(patch);
		player.getPointsManager().addLividPoints(3);
	}
	/**
	 * takes a log? xD
	 * 
	 * @param player
	 */
	public void takeLog(Player player, int amount){
		if(!player.getInventory().hasFreeSlots()){
			player.sm("you have no free inventory spaces.");
			return;
		}
		player.getInventory().addItem(20702,amount);
	}
	
	public static void makePlank(Player player){
		player.getInventory().deleteItem(20702,1);
		player.getInventory().addItem(20703,1);
		player.setNextAnimation(new Animation(6298));
		player.setNextGraphics(new Graphics(1063));
		player.getPointsManager().addLividPoint();
	}
	public ArrayList<WorldObject> plantList = new ArrayList<WorldObject>();
	/**
	 * spawns new plants
	 */
	public void Spawn(){
		for(WorldTile tiles : spawnTiles){
			WorldObject object = new WorldObject(plantIds[Utils.random(0,plantIds.length)],10,0,tiles);
			WorldObject object2 =new WorldObject(plantIds[Utils.random(0,plantIds.length)],10,0,new WorldTile(tiles.getX(),tiles.getY() -3,0));
			WorldObject object3 = new WorldObject(plantIds[Utils.random(0,plantIds.length)],10,0,new WorldTile(tiles.getX(),tiles.getY() -6,0));
			plantList.add(object);
			plantList.add(object2);
			plantList.add(object3);
			player.getPackets().sendSpawnedObject(object);
			player.getPackets().sendSpawnedObject(object2);
			player.getPackets().sendSpawnedObject(object3);
		}
	}
	/**
	 * sends the plant picking interface
	 * @param player
	 */
	public void sendPlantInterface(Player player){
		player.getInterfaceManager().sendTab(player.getInterfaceManager().resizableScreen ? 114 : 190, 1081);
		//player.getPackets().sendIComponentSettings(1081, 3, 3, 6, 1154);
		//player.getPackets().sendUnlockIComponentOptionSlots(1081,3,0,1,2);
	}
	/**
	 * handles button clicking, todo get the interface working
	 */
	@Override
	public boolean processButtonClick(int interfaceId, int componentId, int slotId, int packetId) {
		player.sm(""+componentId);
		if(interfaceId == 1081){
			player.sm(""+componentId);
		}
		return true;
	}
	/*
	 * handles the objects
	 */
	@Override
	public boolean processObjectClick1(final WorldObject object) {
		for(WorldObject obj : plantList){
			if((obj.getX()== object.getX() && obj.getY() == object.getY())){
				switch(obj.getId()){
				case 40452:
				case 40453:
				case 40454:
				case 40455:
					//sendPlantInterface(player); need to fix the interface first
					cureDiseas(obj,player);
					break;
				case 40446:
					Fertilise(obj,player);
					break;
				}	
			}
		}
		if(object.getId() == 40444)
			takeLog(player,1);
		return true;
	}
	/**
	 * handles the second option of the objects
	 */
	@Override
	public boolean processObjectClick2(final WorldObject object) {
		if(object.getId() == 40444)
			takeLog(player,5);
		return true;
	}
	/**
	 * whenever the controler starts
	 */
	@Override
	public void start() {
		player.setNextWorldTile(new WorldTile(2110,3946, 0));
		Spawn();	
	}
	@Override
	public boolean logout() {
		plantList.clear();
		return false;
	}

	@Override
	public boolean login() {
		start();
		Spawn();
		return false;
	}
	
	@Override
	public void magicTeleported(int teleType) {
		plantList.clear();
		player.getControlerManager().forceStop();
	}
	
	
	@Override
	public void process(){
		if(ticks >= 60){
			ticks = 0;
			plantList.clear();
			Spawn();
			//player.sm("The plants are re-spawned.");
		}
		ticks++;
	}
}
