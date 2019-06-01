package com.rs.game.minigames.warbands;

import java.util.HashMap;
import java.util.List;

import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public final class Warbands {
	
	/**
	  *
	  83301 - Supply tent (Herblore) saradomin
83302 - Supply tent (Herblore) armadyl
83303 - Supply tent (Herblore) zamorak
83304 - Supply tent (Herblore) bandos
83310 - Supply tent (Construction)
83311 - Supply tent (Construction)
83312 - Supply tent (Construction)
83313 - Supply tent (Construction)
83319 - Supply tent (Farming)
83320 - Supply tent (Farming)
83321 - Supply tent (Farming)
83322 - Supply tent (Farming)
83328 - Supply tent (Mining)
83329 - Supply tent (Mining)
83330 - Supply tent (Mining)
83331 - Supply tent (Mining)
83336 - Supply tent (Smithing)
83337 - Supply tent (Smithing)
83338 - Supply tent (Smithing)
83339 - Supply tent (Smithing)
83358 - Beam
83359 - Beam
83360 - Beam
83361 - Beam
83362 - Beam
83363 - Beam
83364 - Beam
83365 - Beam
	  **/
	  
	/**
	 * enum of the different camps
	 * @author paolo
	 *
	 */
	public enum Camp {
		
		DARK_WARRIORS_FORTRESS(new WorldTile(3040,3592,0), new WorldTile(3030,3589,0), new WorldTile(3037,3597,0), new WorldTile(3037,3585,0), new WorldTile(3044,3587,0), new WorldTile(3043,3596,0)),
		LIZARDS(new WorldTile(3310,3771,0),new WorldTile(3300,3768,0), new WorldTile(3307,3776,0), new WorldTile(3313,3775,0),  new WorldTile(3314,3767,0), new WorldTile(3307,3764,0)),
		RED_DRAGONS(new WorldTile(3134,3839),new WorldTile(3124,3836,0), new WorldTile(3131,3844,0), new WorldTile(3137,3843,0), new WorldTile(3138,3835,0), new WorldTile(3131,3832,0));
		
		private WorldTile beamCoords,bigTent,smallTent_1,smallTent_2,smallTent_3,smallTent_4;
		
		private Camp(WorldTile beam,WorldTile big, WorldTile small1, WorldTile small2, WorldTile small3,WorldTile small4){
			this.setBeamCoords(beam);
			this.setBigTent(big);
			this.smallTent_1 = small1;
			this.smallTent_2 = small2;
			this.smallTent_3 = small4;
			this.smallTent_4 = small4;
		}
		
		public static Camp getCamp(int i ){
			for(int i1 = 0; i1 < Camp.values().length; i1++){
				if(i == i1)
				return Camp.values()[i1];
			}
			return null;
			
		}
		/**
		 * @return the beamCoords
		 */
		public WorldTile getBeamCoords() {
			return beamCoords;
		}
		/**
		 * @param beamCoords the beamCoords to set
		 */
		public void setBeamCoords(WorldTile beamCoords) {
			this.beamCoords = beamCoords;
		}
		/**
		 * @return the bigTent
		 */
		public WorldTile getBigTent() {
			return bigTent;
		}
		/**
		 * @param bigTent the bigTent to set
		 */
		public void setBigTent(WorldTile bigTent) {
			this.bigTent = bigTent;
		}
	}
	/**
	 * different callouts
	 * @author paolo
	 *
	 */
	public enum CallOut {
		DWF();
	}
	public enum WarbandEvent {
		
		CAMP_1(new int[] {3026, 3040, 3586, 3599}, 0, new WorldTile(3042,3592, 0)),
		CAMP_2(new int[] {3296, 3313, 3762, 3777}, 1, new WorldTile(3307,3771, 0)),
		CAMP_3(new int[] {3128, 3141, 3836, 3852}, 2, new WorldTile(3135, 3843, 0))
		;
		
		private int[] base;
		private int event;
		public WorldTile tile;
		
		private WarbandEvent(int[] base, int event, WorldTile tile) {
			this.base = base;
			this.event = event;
			this.tile = tile;
		}
		
		public static WarbandEvent getEvent(int event) {
			for (WarbandEvent e : values()) {
				if (e.event == event)
					return e;
			}
			return null;
		}
	}
	
	public Camp camp;
	public WarbandsMinion[] minions;
	public WarbandsChief[] chief;
	public WarbandsReinforcement[] reinforcements;

	/**
	 * spanws the tents on the death camp
	 */
	public void spawnTents(){
		if (warband == null) //shouldn't happen
			return;
		World.spawnObjectTemporary(new WorldObject(83358 + type,10,0,camp.getBeamCoords()), 600000);
		World.spawnObjectTemporary(new WorldObject(83301 + type,10,0,camp.bigTent), 600000);
		World.spawnObjectTemporary(new WorldObject(83310 + type,10,0,camp.smallTent_1), 600000);
		World.spawnObjectTemporary(new WorldObject(83319 + type,10,0,camp.smallTent_2), 600000);
		World.spawnObjectTemporary(new WorldObject(83328 + type,10,0,camp.smallTent_2), 600000);
		World.spawnObjectTemporary(new WorldObject(83336 + type,10,0,camp.smallTent_3), 600000);
		
	}
	public void spawnCombatants() {
		if (warband == null)
			return;
		WorldTile tile = WarbandEvent.getEvent(warband.warbandEvent).tile;
		WorldTile[] tiles = new WorldTile[8];
		for (int i = 0; i < tiles.length; i++) {
			int tryCount = 0;
			while (tiles[i] == null) {
				WorldTile spawn = new WorldTile(tile.getX() + Utils.random(-2, 2), tile.getY() + Utils.random(-2, 2), 0);
				if (!World.canMoveNPC(spawn.getPlane(),spawn.getX(), spawn.getY(), 1) && tryCount < 10) {
					tryCount++;
					continue;
				}
				tiles[i] = spawn;
				break;
			}
		}
		int count = 0;
		for (int i = 0; i < WarbandsConstants.MINIONS[warband.type].length; i++) {
			for (int x = 0; x < (i == WarbandsConstants.MINIONS[warband.type].length - 1 ? 3 : 2); x++) {
				minions[i] = new WarbandsMinion(WarbandsConstants.MINIONS[warband.type][i], new WorldTile(tiles[count++]), -1, true, true);
				minions[i].warband = this;
				minions[i].attackable = true;
				minions[i].setNextGraphics(new Graphics(3032));
				minions[i].setRandomWalk(false);
				minions[i].setNextFaceWorldTile(tile);
			}
		}
		chief[0] = new WarbandsChief(WarbandsConstants.CHIEFTAINS[warband.type], new WorldTile(tiles[count++]), -1, true, true);
		chief[0].warband = this;
		chief[0].setNextGraphics(new Graphics(3032));
		chief[0].setRandomWalk(false);
		chief[0].setNextFaceWorldTile(tile);
		this.remainingOccupants = count;
	}
	
	public void spawnReinforcements() {
		if (warband == null)
			return;
		WorldTile tile = WarbandEvent.getEvent(warband.warbandEvent).tile;
		WorldTile[] tiles = new WorldTile[6];
		for (int i = 0; i < tiles.length; i++) {
			int tryCount = 0;
			while (tiles[i] == null) {
				WorldTile spawn = new WorldTile(tile.getX() + Utils.random(-3, 3), tile.getY() + Utils.random(-3, 3), 0);
				if (!World.canMoveNPC(spawn.getPlane(),spawn.getX(), spawn.getY(), 1) && tryCount < 10) {
					tryCount++;
					continue;
				}
				tiles[i] = spawn;
				break;
			}
		}
		int count = 0;
		skip: for (int i = 0; i < WarbandsConstants.MINIONS[warband.type == 0 ? 1 : 0].length; i++) {
			for (int x = 0; x < 2; x++) {
				reinforcements[i] = new WarbandsReinforcement(WarbandsConstants.MINIONS[warband.type == 0 ? 1 : 0][i], new WorldTile(tiles[count++]), -1, true, true);
				reinforcements[i].warband = this;
				reinforcements[i].setNextGraphics(new Graphics(3032));
				if (count >= tiles.length)
					break skip;
			}
		}
	}
	/**
	 * Time at which event was started
	 */
	public long time;
	/**
	 * Awarded the player a rare reward
	 */
	public boolean awarded;
	/**
	 * The warband event; used to be called in other classes; static so only once instance of it exists
	 */
	public static Warbands warband;
	/**
	 * warbandEvent - Determines which event is taking place (0, 1, 2) each in different wilderness levels
	 * type - the NPC index to be spawned
	 */
	public int warbandEvent, type;
	/**
	 * The minx, maxx, miny, maxy of the event.
	 */
	public int base[] = new int[4];
	/**
	 * The base amount of resources
	 */
	public int resources, charges, remainingOccupants;
	/**
	 * Determines whether or not the object has resources to collect; used in the decreaseRemainingResources method.
	 */
	public boolean objectHasResources(WorldObject object) {
		if(object.getId() >= 83301 && object.getId() <= 83339)
			return true;
				return false;
	}
	/**
	 * Name of warbands event
	 */
	public String getWarbandsEventType() {
		switch (type) {
		case 0:
			return "Armadylean";
		case 1:
		default:
			return "Bandosian";
		}
	}
	/**
	 * Determines whether or not the player is carrying resources from warbands; will be used in the wilderness controller to skull them.
	 */
	public static boolean isCarryingResources(Player player) {
		for (int i = 0; i < 28; i++) {
			if (player.getInventory().getItem(i) == null)
				continue;
				if (player.getInventory().getItem(i).getId() >= 27636 && player.getInventory().getItem(i).getId() <= 27640 )
					return true;
		}
			return false;
	}
	
	/**
	 * Determines whether or not the player is in the designated warbands area; used to handle the collection of resources.
	 */
	public boolean isInArea(WorldObject object) {
		if (warband == null || object == null)
			return false;
		return true;
	}
	/**
	 * Tracks the players who have collected resources
	 */
	public HashMap<String, Integer> collectedResources;
	/**
	 * The method used in object handler to decrease the remaining amount of resources from the game and hence finish it
	 */
	public boolean decreaseRemainingResources(Player player, WorldObject object) {
		if (object == null || player == null || warband == null) {
			return false;
		}
		//Integer collected = collectedResources.remove(player.getUsername());
		Integer collected = collectedResources.remove(player.getSession().getIP());
		if (collected == null) {
			//collected = 25;
			collected = 50;
			//collectedResources.put(player.getUsername(), collected);
			collectedResources.put(player.getSession().getIP(), collected);
		} else {
			collected--;
			collectedResources.put(player.getSession().getIP(), collected);
			if (collected <= 0) {
				player.getPackets().sendGameMessage("You're only able to collect a total of 50 resources per warband event!");
				return false;
			}
		}
		if (warband.resources >= 1) {
			warband.resources--;
			player.setNextAnimation(new Animation(881));
			Item item = new Item(getLootItem(object));
			if(item != null)
			if (!player.getInventory().addItem(item))
				World.addGroundItem(item, player, player, true, 180, true);
			if (Utils.random(250) == 0 && !warband.awarded) {
				sendLocalPlayerMessage(player, "<col=ff8c38><img=4>Warbands: "+player.getDisplayName()+" received "+Utils.formatAorAn(new Item(27641))+" "+item.getName().toLowerCase()+"!");
				if (!player.getInventory().addItem(new Item(27641)))
					World.addGroundItem(new Item(27641), player, player, true, 180, true);
				awarded = true;
			}
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param obj the object you loot
	 * @return item you get from a certain tent
	 */
	public Item getLootItem(WorldObject obj){
		String name = ObjectDefinitions.getObjectDefinitions(obj.getId()).name.toLowerCase();
		if(name.contains("farming"))
			return new Item(27639);
		else if(name.contains("herblore"))
			return new Item(27637);
		else if(name.contains("mining"))
			return new Item(27640);
		else if(name.contains("smithing"))
			return new Item(27638);
		else if(name.contains("construction"))
			return new Item(27636);
		return null;
	}
	/**
	 * Finishes the warband event and removes the current one, then sets the next one
	 */
	public void finish() {
		if (warband == null)
			return;
		final int prev = warband.warbandEvent;
		warband = null;
		int i = prev;
		int count = 0;
		while (i == prev) {
			int random = Utils.random(WarbandEvent.values().length);
			if (random == i && count < 10) {
				count++;
				continue;
			}
			warband = new Warbands(random);
			break;
		}
	}
	/**
	 * A new warband event
	 * @param event determines the location of the warband event (0, 1, 2)
	 */
	public Warbands(int event) {
		if (WarbandEvent.getEvent(event) == null)
			return;
		this.warbandEvent = event;
		for (int i = 0; i < WarbandEvent.getEvent(event).base.length; i++) {
			this.base[i] = WarbandEvent.getEvent(event).base[i];
		}
		camp = Camp.getCamp(event);
		this.type = Utils.random(WarbandsConstants.MINIONS.length);
		this.resources = 400;
		this.charges = 150;
		this.minions = new WarbandsMinion[7];
		this.chief = new WarbandsChief[1];
		this.reinforcements = new WarbandsReinforcement[6];
		this.collectedResources = new HashMap<String,Integer>(resources);
		World.sendWorldMessage("<col=0080FF><img=4>Warbands: A new "+getWarbandsEventType()+" warband has been spotted somewhere between levels "+getWildLevel(base[2])+" and "+getWildLevel(base[3])+" of the wilderness!", false);
		warband = this;
		spawnCombatants();
		spawnTents();
		this.time = Utils.currentTimeMillis() + 3000 * 3600;
	}
	
	/**
	 * The wilderness level of the warband event
	 */
	public static int getWildLevel(int y) {
		if(y > 9900)
			return (y - 9912) / 8 + 1;
		return (y - 3520) / 8 + 1;
	}
	
	public static void sendLocalPlayerMessage(Player player, String message) {
		for (int regionId : player.getMapRegionsIds()) {
			List<Integer> playersIndexes = World.getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null) {
				continue;
			}
			for (Integer playerIndex : playersIndexes) {
				Player p = World.getPlayers().get(playerIndex);
				if (p == null
						|| !p.hasStarted()
						|| p.hasFinished()
						|| p.getLocalPlayerUpdate().getLocalPlayers()[player.getIndex()] == null) {
					continue;
				}
				p.getPackets().sendGameMessage(message);
			}
		}
	}
}
