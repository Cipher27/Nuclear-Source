package com.rs.game.player.content.custom;

import com.rs.game.World;
import com.rs.game.player.Player;
/**
 * 
 * @author Paolo, with this people can see which event is going onn.
 *
 */
public class ActivityHandler {
     
	//the interface id
	public static int Interface = 1158;
	/**
	  * To get the current wildy event
	  **/
	public static String CurrentWildyEvent = "Wait for the chest to Spawn.";
	
	public static String getCurrentWildyEvent() {
		return  CurrentWildyEvent;
	}
	public static void setCurrentWildyEvent(String  CurrentWildyEvent) {
		ActivityHandler.CurrentWildyEvent =  CurrentWildyEvent;
	}
	//skilling event
	public static String WildySkillingEvent = "";
	
	public static String getWildySkillingEvent() {
		return  WildySkillingEvent;
	}
	public static void setWildySkillingEvent(String  WildySkillingEvent) {
		ActivityHandler.WildySkillingEvent =  WildySkillingEvent;
	}
	//evil tree
	public static String EvilTree = "";
	
	public static String getEvilTree() {
		return  EvilTree;
	}
	public static void setEvilTree(String  EvilTree) {
		ActivityHandler.EvilTree =  EvilTree;
	}
	//World event
	public static String worldEvent = "";
	
	public static String getworldEvent() {
		return  worldEvent;
	}
	public static void setworldEvent(String  worldEvent) {
		ActivityHandler.worldEvent =  worldEvent;
	}
	//shooting star
	public static String ShootingStar = "";
	
	public static String getShootingStar() {
		return  ShootingStar;
	}
	public static void setShootingStar(String  ShootingStar) {
		ActivityHandler.ShootingStar =  ShootingStar;
	}
	//home raids
	public static String HomeRaids = "";
	
	public static String getHomeRaids() {
		return  HomeRaids;
	}
	public static void setHomeRaids(String  HomeRaids) {
		ActivityHandler.HomeRaids =  HomeRaids;
	}
	/**
	  * call this method to send the interface 
	  */
	public static void sendInterface(Player player) {
	player.getInterfaceManager().sendInterface(Interface);
	player.getPackets().sendIComponentText(Interface, 74, "Event Checker");
	player.getPackets().sendIComponentText(Interface, 9, "<img=7>Name.");
	player.getPackets().sendIComponentText(Interface, 10, "<img=7>Description");
	player.getPackets().sendIComponentText(Interface, 11, "<img=7>Type.");
	//wildy chest event.
	player.getPackets().sendIComponentText(Interface, 14, "Wildy Chest");	
	player.getPackets().sendIComponentText(Interface, 15, "Tip: "+getCurrentWildyEvent()+"");
	player.getPackets().sendIComponentText(Interface, 16, "Loot");
	//Current wildy event.
	player.getPackets().sendIComponentText(Interface, 19, "Wildy Bow");	
	player.getPackets().sendIComponentText(Interface, 20, ""+getWildySkillingEvent()+"");
	player.getPackets().sendIComponentText(Interface, 21, "PVP");
	//evil tree
	player.getPackets().sendIComponentText(Interface, 24, "Double exp");	
	player.getPackets().sendIComponentText(Interface, 25, "False");
	player.getPackets().sendIComponentText(Interface, 26, "Skilling");
	//shooting star
	player.getPackets().sendIComponentText(Interface, 29, "Evil Tree");	
	player.getPackets().sendIComponentText(Interface, 30, ""+getEvilTree()+"");
	player.getPackets().sendIComponentText(Interface, 31, "Skilling");
	//World event
	player.getPackets().sendIComponentText(Interface, 29, "World Event");	
	player.getPackets().sendIComponentText(Interface, 30, ""+getworldEvent()+"");
	player.getPackets().sendIComponentText(Interface, 31, "Random");
	//home raids
	//home skilling events
	player.getPackets().sendIComponentText(Interface, 34, "Spotlight skill");	
	player.getPackets().sendIComponentText(Interface, 35, ""+World.getSpotLightSkillName()+"");
	player.getPackets().sendIComponentText(Interface, 36, "Skilling");
	
	player.getPackets().sendIComponentText(Interface, 39, "Combat skill");	
	player.getPackets().sendIComponentText(Interface, 40, ""+World.getSpotLightCombatSkillName()+"");
	player.getPackets().sendIComponentText(Interface, 41, "Pvm");
	
	player.getPackets().sendIComponentText(Interface, 44, "Shooting star");	
	player.getPackets().sendIComponentText(Interface, 45, ""+getShootingStar()+"");
	player.getPackets().sendIComponentText(Interface, 46, "Skilling");
	}
	

}