package com.rs.game.player.content;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;

public class TeleportSystem {
	
	public enum Teleports{
		
		Agility(new TeleportLocation[] {
			new TeleportLocation("Gnome agility",new WorldTile(0,0,0)),
			new TeleportLocation("Barbarian agility",new WorldTile(0,0,0)),
			new TeleportLocation("Wildernes agility",new WorldTile(0,0,0)),
			new TeleportLocation("Prifdinas agility",new WorldTile(0,0,0))
		}),
		Mining(new TeleportLocation[] {
				new TeleportLocation("Starter",new WorldTile(0,0,0)),
				new TeleportLocation("Granite mine",new WorldTile(0,0,0)),
				new TeleportLocation("Lrc",new WorldTile(0,0,0)),
				new TeleportLocation("Seren stones",new WorldTile(0,0,0))
			});
		
		
		private TeleportLocation locations[];
		
		Teleports(TeleportLocation[] loc){
			this.setLocations(loc);
		}

		/**
		 * @return the locations
		 */
		public TeleportLocation[] getLocations() {
			return locations;
		}

		/**
		 * @param locations the locations to set
		 */
		public void setLocations(TeleportLocation locations[]) {
			this.locations = locations;
		}
		
	}
	
	public static void handleButtons(int buttonId, Player player){
		if(buttonId == 27){
			sendTeleports(Teleports.Agility,player);
		} else if(buttonId == 61){
			sendTeleports(Teleports.Mining,player);
		}
	}
	
	public static void sendTeleports(Teleports tele, Player player){
		cleanTeleports(player);
		int counter = 84;
		for(TeleportLocation loc : tele.getLocations()){
			player.getPackets().sendIComponentText(3056, counter, counter - 83+"."+loc.getName());
			counter++;
		}
	}
	
	public static void cleanTeleports(Player player){
		for(int i = 84; i <= 90 ;i ++){
			player.getPackets().sendIComponentText(3056, i, "");
		}
	}
	/**
	 * small class represnting a location
	 * @author paolo
	 *
	 */
	public static class TeleportLocation{
		
		private String name;
		private WorldTile location;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public WorldTile getLocation() {
			return location;
		}

		public void setLocation(WorldTile location) {
			this.location = location;
		}

		public TeleportLocation(String name, WorldTile location) {
			this.name = name;
			this.location = location;
		}
		
		
	}

}


