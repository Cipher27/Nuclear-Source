package com.rs.game.player.content.construction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.DynamicRegion;
import com.rs.game.GameEngine;
import com.rs.game.Region;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.map.MapUtils;
import com.rs.game.npc.NPC;
import com.rs.game.npc.house.Guard;
import com.rs.game.npc.house.ServantNPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
//import com.rs.game.player.content.MoneyPouch;
import com.rs.game.player.content.construction.HouseConstants.Builds;
import com.rs.game.player.content.construction.HouseConstants.HObject;
//import com.rs.game.player.content.construction.HouseConstants.POHLocation;
import com.rs.game.player.content.construction.HouseConstants.Room;
import com.rs.game.player.content.construction.HouseConstants.Servant;
import com.rs.game.player.content.pet.Pets;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.controlers.HouseControler;
import com.rs.game.task.Task;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

/*
 * House class only contains house data + support methods to change that data
 * HouseController provides support between player interaction inside house and housemanager
 * HouseConstants handles the constants such as existing rooms, builds, roofs
 */
public class House implements Serializable {
	
	
	private PetHouse petHouse;
	private transient CopyOnWriteArrayList<NPC> pets;

	public static class ObjectReference implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -22245200911725426L;
		private int slot;
		private Builds build;

		public ObjectReference(Builds build, int slot) {
			this.setBuild(build);
			this.slot = slot;
		}

		public HObject getPiece() {
			return getBuild().getPieces()[slot];
		}

		public int getId() {
			return getBuild().getPieces()[slot].getId();
		}

		public int[] getIds() {
			return getBuild().getPieces()[slot].getIds();
		}

		public int getId(int slot2) {
			return getIds()[slot2];
		}

		public Builds getBuild() {
			return build;
		}

		public void setBuild(Builds build) {
			this.build = build;
		}

		public int getSlot() {
			return slot;
		}

	}

	public static class RoomReference implements Serializable {

		private static final long serialVersionUID = 4000732770611956015L;

		public RoomReference(HouseConstants.Room room, int x, int y, int plane, int rotation) {
			this.room = room;
			this.x = (byte) x;
			this.y = (byte) y;
			this.plane = (byte) plane;
			this.rotation = (byte) rotation;
			objects = new ArrayList<ObjectReference>();
			if (room == Room.PORTAL_CHAMBER)
				directedPortals = new byte[3];
			if (isDungeon(room))
				guardians = new ArrayList<Guard>();
		}

		private HouseConstants.Room room;
		private byte x, y, plane, rotation;
		private List<ObjectReference> objects;
		private byte[] directedPortals;
		private transient List<Guard> guardians;

		public int getStaircaseSlot() {
			for (ObjectReference object : objects) {
				if (object.getBuild().toString().contains("STAIRCASE") || object.getBuild().toString().contains("CENTREPEICE"))
					return object.slot;
			}
			return -1;
		}

		public int getTrapdoorSlot() {
			for (ObjectReference object : objects) {
				if (object.getBuild() == Builds.TRAPDOOR)
					return object.slot;
			}
			return -1;
		}

		public boolean isStaircaseDown() {
			for (ObjectReference object : objects) {
				if (object.getBuild().toString().contains("STAIRCASE_DOWN"))
					return true;
			}
			return false;
		}

		/*
		 * x,y inside the room chunk
		 */
		public ObjectReference addObject(Builds build, int slot) {
			ObjectReference ref = new ObjectReference(build, slot);
			objects.add(ref);
			return ref;
		}

		public ObjectReference getObject(WorldObject object) {
			for (final ObjectReference o : objects) {
				for (final int id : o.getIds())
					if (object.getId() == id)
						return o;
			}
			return null;
		}

		public int getHObjectSlot(HObject hObject) {
			for (int index = 0; index < objects.size(); index++) {
				ObjectReference o = objects.get(index);
				if (o == null)
					continue;
				if (hObject.getId() == o.getPiece().getId())
					return o.getSlot();
			}
			return -1;
		}

		public boolean containsHObject(HObject hObject) {
			return getHObjectSlot(hObject) != -1;
		}

		public int getBuildSlot(Builds build) {
			for (int index = 0; index < objects.size(); index++) {
				ObjectReference o = objects.get(index);
				if (o == null)
					continue;
				if (o.getBuild() == build)
					return o.getSlot();
			}
			return -1;
		}

		public boolean containsBuild(Builds build) {
			return getBuildSlot(build) != -1;
		}

		public ObjectReference removeObject(WorldObject object) {
			ObjectReference r = getObject(object);
			if (r != null) {
				objects.remove(r);
				return r;
			}
			return null;
		}

		public void setRotation(int rotation) {
			this.rotation = (byte) rotation;
		}

		public byte getRotation() {
			return rotation;
		}

		public Room getRoom() {
			return room;
		}

		public int getPlane() {
			return plane;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public List<ObjectReference> getObjects() {
			return objects;
		}

		public byte[] getDirectedPortals() {
			return directedPortals;
		}

		public void setDirectedPortals(byte[] directedPortals) {
			this.directedPortals = directedPortals;
		}

		public List<Guard> getGuardians() {
			if (isDungeon(room) && guardians == null)
				guardians = new ArrayList<Guard>();
			return guardians;
		}
	}
	
	

	public void switchLock(Player player) {
		if (!isOwner(player)) {
			player.getPackets().sendGameMessage("You can only lock your own house.");
			return;
		}
		locked = !locked;
		if (locked)
			player.getDialogueManager().startDialogue("SimpleMessage", "Your house is now locked to all visistors.");
		else if (buildMode)
			player.getDialogueManager().startDialogue("SimpleMessage", "Visitors will be able to enter your house once you leave building mode.");
		else
			player.getDialogueManager().startDialogue("SimpleMessage", "Visistors can now enter your house.");
	}

	public static void enterHouse(Player player, String displayname) {
		if (player.isLocked()) {
			//players could enter friends house while using things like home tele which teleports you out when stepping off lodestone
			return;
		}
		Player owner = World.getPlayerByDisplayName(displayname);
		
		if(owner != player) {
			if (owner == null || !owner.isRunning() || owner.getHouse().locked) {
				player.getPackets().sendGameMessage("That player is offline, or has privacy mode enabled.");
				return;
			}
		}
		owner.getHouse().joinHouse(player);
	}

	public boolean joinHouse(final Player player) {
		if (!isOwner(player)) { //not owner
			if (!isOwnerInside() || !loaded) {
				player.getPackets().sendGameMessage("That player is offline, or has privacy mode enabled."); //TODO message
				return false;
			}
			if (buildMode) {
				player.getPackets().sendGameMessage("The owner currently has build mode turned on.");
				return false;
			}
		}
		players.add(player);
		sendStartInterface(player);
		player.getControlerManager().startControler("HouseControler", this);
		if (loaded) {
			teleportPlayer(player);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					player.lock(1);
					player.getInterfaceManager().removeAll();
				}
			}, 4);
		} else {
			GameEngine.get().getSlowExecutor().execute(new Runnable() {
				@Override
				public void run() {
					try { //sets bounds before finishing load therefore the load boolean
						boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8);
						createHouse(true);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			});
		}
		return true;
	}

	public static int LOGGED_OUT = 0, KICKED = 1, TELEPORTED = 2;
	private static final long serialVersionUID = 8111719490432901786L;

	// dont name it rooms or it will null server
	private List<RoomReference> roomsR;
	private byte look;
	//private POHLocation location;
	private Servant servant;

	// private POHLocation location;
	private transient int challengeMode; //0 disabled,  1 - challenge method, 2 - pvp challenge method
	private transient int burnerCount;
	private boolean buildMode;
	private boolean arriveInPortal;
	private transient Player player;
	private transient boolean locked;
	private boolean doorsOpen;
	private byte paymentStage;

	// house loaded datas
	private transient List<Player> players;

	private transient static List<Player> guests;

	private transient int[] boundChuncks;

	private transient boolean loaded;
	
	private transient ServantNPC servantInstance;
	private transient List<WorldObject> dungeonTraps;

	private byte build;

	// Used for inter 396
	private static final int[] BUILD_INDEXES = { 0, 2, 4, 6, 1, 3, 5 };

	private static final int[] DOOR_DIR_X = { -1, 0, 1, 1 };

	private static final int[] DOOR_DIR_Y = { 0, 1, 0, -1 };
	
	public void build(int slot) {
		if (player.getInterfaceManager().containsInterface(396)) {
			for (int i = 0; i < BUILD_INDEXES.length; i++)
				if (slot == BUILD_INDEXES[i]) {
					slot = i;
					break;
				}
		}
		final Builds build = (Builds) player.getTemporaryAttributtes().get("OpenedBuild");
		final WorldObject object = (WorldObject) player.getTemporaryAttributtes().get("OpenedBuildObject");
		if (build == null || object == null || build.getPieces().length <= slot)
			return;
		final int roomX = object.getChunkX() - boundChuncks[0];
		final int roomY = object.getChunkY() - boundChuncks[1];
		final RoomReference room = getRoom(roomX, roomY, object.getPlane());
		if (room == null)
			return;
		final HObject piece = build.getPieces()[slot];
		int level = (player.getInventory().containsItemToolBelt(32633) || player.getEquipment().getWeaponId() == 32633) ? player.getSkills().getLevel(Skills.CONSTRUCTION) + 5 : player.getSkills().getLevel(Skills.CONSTRUCTION);
		if (level < piece.getLevel()) {
			player.sm("Your level of construction is too low for this build.");
			return;
		}
		//if (Settings.isDebugMode())
			//for (final Item item : piece.getRequirements())
				//Utils.print(item.getId() + ", " + item.getAmount());
		if (!player.getInventory().containsItems(piece.getRequirements())) {
			player.sm("You dont have the right materials.");
			return;
		}
		if (build.isWater() ? !hasWaterCan() : (!(player.getInventory().containsItemToolBelt(HouseConstants.HAMMER) || player.getInventory().containsItemToolBelt(HouseConstants.SAW) || player.getInventory().containsItemToolBelt(32633) || player.getEquipment().getWeaponId() == 32633))) {
			player.sm(build.isWater() ? "You will need a watering can with some water in it instead of hammer and saw to build plants." : "You will need a hammer and saw to build furniture.");
			return;
		}
		final ObjectReference oref = room.addObject(build, slot);
		player.closeInterfaces();
		player.lock();
		player.setNextAnimation(new Animation(build.isWater() ? 2293 : 3683));
		for (final Item item : piece.getRequirements())
			player.getInventory().deleteItem(item);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getSkills().addXp(Skills.CONSTRUCTION, piece.getXP());
				if (build.isWater())
					player.getSkills().addXp(Skills.FARMING, piece.getXP());
				refreshObject(room, oref, false);
				player.lock(1);
			}
		}, 2); 
	}

	public void climbStaircase(WorldObject object, boolean up) {
		final int roomX = object.getChunkX() - boundChuncks[0];
		final int roomY = object.getChunkY() - boundChuncks[1];
		final RoomReference room = getRoom(roomX, roomY, object.getPlane());
		if (room == null)
			return;
		if (room.plane == (up ? 2 : 0)) {
			player.sm("You are on the " + (up ? "highest" : "lowest") + " possible level so you cannot add a room " + (up ? "above" : "under") + " here.");
			return;
		}
		final RoomReference roomTo = getRoom(roomX, roomY, room.plane + (up ? 1 : -1));
		if (roomTo == null) {
			if (buildMode)
				player.getDialogueManager().startDialogue("CreateRoomStairsD", room, up);
			else
				player.sm("These stairs do not lead anywhere.");
			// start dialogue
			return;
		}
		if (roomTo.getStaircaseSlot() == -1) {
			player.sm("These stairs do not lead anywhere.");
			return;
		}
		player.useStairs(-1, new WorldTile(player.getX(), player.getY(), player.getPlane() + (up ? 1 : -1)), 0, 1);

	}

	public void createHouse(final boolean tp) {
		final Object[][][][] data = new Object[4][8][8][];
		// sets rooms data
		for (final RoomReference reference : roomsR)
			data[reference.plane][reference.x][reference.y] = new Object[] { reference.room.getChunkX(), reference.room.getChunkY(), reference.rotation, reference.room.isShowRoof() };
		// sets roof data
		if (!buildMode) { // construct roof
			for (int x = 1; x < 7; x++) {
				skipY: for (int y = 1; y < 7; y++) {
					for (int plane = 2; plane >= 1; plane--) {
						if (data[plane][x][y] != null) {
							final boolean hasRoof = (boolean) data[plane][x][y][3];
							if (hasRoof) {
								final byte rotation = (byte) data[plane][x][y][2];
								// TODO find best Roof
								data[plane + 1][x][y] = new Object[] { HouseConstants.Roof.ROOF1.getChunkX(), HouseConstants.Roof.ROOF1.getChunkY(), rotation, true };
								continue skipY;
							}
						}
					}
				}
			}
		}
		// builds data
		for (int plane = 0; plane < data.length; plane++) {
			for (int x = 0; x < data[plane].length; x++) {
				for (int y = 0; y < data[plane][x].length; y++) {
					if (data[plane][x][y] != null)
						RegionBuilder.copyChunk((int) data[plane][x][y][0] + (look >= 4 ? 8 : 0), (int) data[plane][x][y][1], look & 0x3, boundChuncks[0] + x, boundChuncks[1] + y, plane, (byte) data[plane][x][y][2]);
					else if ((x == 0 || x == 7 || y == 0 || y == 7) && plane == 1)
						RegionBuilder.copyChunk(HouseConstants.BLACK[0], HouseConstants.BLACK[1], 0, boundChuncks[0] + x, boundChuncks[1] + y, plane, 0);
					else if (plane == 1)
						RegionBuilder.copyChunk(HouseConstants.LAND[0] + (look >= 4 ? 8 : 0), HouseConstants.LAND[1], look & 0x3, boundChuncks[0] + x, boundChuncks[1] + y, plane, 0);
					else if (plane == 0)
						RegionBuilder.copyChunk(HouseConstants.DUNGEON[0] + (look >= 4 ? 8 : 0), HouseConstants.DUNGEON[1], look & 0x3, boundChuncks[0] + x, boundChuncks[1] + y, plane, 0);
					else
						RegionBuilder.cutChunk(boundChuncks[0] + x, boundChuncks[1] + y, plane);
				}
			}
		}
		final int[] regionPos = MapUtils.convert(MapUtils.Structure.CHUNK, MapUtils.Structure.REGION, boundChuncks);
		final Region region = World.getRegion(MapUtils.encode(MapUtils.Structure.REGION, regionPos), true);
		final List<WorldObject> spawnedObjects = region.getSpawnedObjects();
		if (spawnedObjects != null) {
			for (final WorldObject object : spawnedObjects){
				if(object != null) //you never know
				World.removeObject(object);
			}
		}

		final List<WorldObject> removedObjects = region.getRemovedOriginalObjects();

		if (removedObjects != null) {
			for (final WorldObject object : removedObjects){
				World.spawnObject(object);
			//	System.out.println("Ids:" +object.getId());
			}
		}

		World.executeAfterLoadRegion(region.getRegionId(), 2400, new Runnable() {
			@Override
			public void run() {
				if (boundChuncks == null) {
					// force kicks
					return;
				}
				for (final RoomReference reference : roomsR) {
					int boundX = reference.x * 8;
					int boundY = reference.y * 8;
					for (int x = 0; x < 8; x++) {
						for (int y = 0; y < 8; y++) {
							final WorldObject[] objects = region.getAllObjects(reference.plane, boundX + x, boundY + y);
							
							if (objects != null) {
								skip: for (WorldObject object : objects) {
									if (object == null) {
										continue;
									}
									//System.out.println(object.getId());
									if (object.getDefinitions().containsOption(1, "Build") ||object.getDefinitions().containsOption(0, "Build")
											|| object.getDefinitions().name.equals("Habitat space")) {
										if (isDoor(object)) {
											if (!buildMode && object.getPlane() == 2
													&& getRoom(
															object.getX() / 8 - boundChuncks[0]
															+ Utils.ROTATION_DIR_X[object.getRotation()],
															object.getY() / 8 - boundChuncks[1]
															+ Utils.ROTATION_DIR_Y[object.getRotation()],
															object.getPlane()) == null) {
												WorldObject objectR = new WorldObject(object);
												objectR.setId(HouseConstants.WALL_IDS[look]);
												World.spawnObject(objectR);
												continue;
											}
										} else {
											for (ObjectReference o : reference.objects) {
												int slot = o.getBuild().getIdSlot(object.getId());
												if (slot != -1) {
													WorldObject objectR = new WorldObject(object);
													if (!buildMode && (o.getBuild() == Builds.PORTAL_1
															|| o.getBuild() == Builds.PORTAL_2
															|| o.getBuild() == Builds.PORTAL_3)) {
														int portal = o.getBuild() == Builds.PORTAL_1 ? 0
																: o.getBuild() == Builds.PORTAL_2 ? 1 : 2;
														if (reference.directedPortals[portal] != 0) {
															int type = o.getId(slot) - 13636;
															objectR.setId(13614 + reference.directedPortals[portal]
																	+ type * 7);
															World.spawnObject(objectR);
															continue skip;
														}
													}
													objectR.setId(o.getId(slot));
													if (!buildMode && (o.getBuild() == Builds.TRAP_SPACE_1
															|| o.getBuild() == Builds.TRAP_SPACE_2)) {
														dungeonTraps.add(objectR);
														World.removeObject(object);
														continue skip;
													}
													World.spawnObject(objectR);
													continue skip;
												}
											}
											if (!buildMode && isWindowSpace(object)) {
												object = new WorldObject(object);
												object.setId(reference.plane == 0 ? HouseConstants.WALL_IDS[look]
														: HouseConstants.WINDOW_IDS[look]);
												World.spawnObject(object);
												continue;
											}
										}
										if (!buildMode) {
											World.removeObject(object);
										}
									} else if (object.getId() == HouseConstants.WINDOW_SPACE_ID) {
										object = new WorldObject(object);
										object.setId(reference.plane == 0 ? HouseConstants.WALL_IDS[look]
												: HouseConstants.WINDOW_IDS[look]);
										World.spawnObject(object);
									} else if (isDoorSpace(object)) {
										// does
										World.removeObject(object);
									}
								}
							}
						}
					}
				}
				player.setForceNextMapLoadRefresh(true);
				player.loadMapRegions();
				player.lock(2);
				/*so interfaces don't quit working */
				player.getInterfaceManager().sendInterfaces();
			    /* Pet spawns in a house*/
				if (!buildMode) {
					if (getMenagerie() != null) {
						for (Item item : petHouse.getPets().getItems()) {
							if (item != null) {
								addPet(item, false);
							}
						}

					}
				}
				if (tp) {
					teleportPlayer(player);
					refreshServant();
				}
				loaded = true;
				//this.stop();
			}
		});
	}
	
	/**
	 * checks if the object is a window space
	 * @param object
	 * @return
	 */
	public boolean isWindowSpace(WorldObject object) {
		return object.getDefinitions().name.equalsIgnoreCase("Window space");
	}
	/**
	 * checks if there's a pet house inside the menagerie (for the pets spawn)
	 * @return
	 */
	public RoomReference getMenagerie() {
		for (RoomReference room : roomsR) {
			if (room.room == HouseConstants.Room.MENAGERIE) {
				for (ObjectReference o : room.objects) {
					if (o.getPiece() == HouseConstants.HObject.OAK_PET_HOUSE || 
							o.getPiece() == HouseConstants.HObject.TEAK_PET_HOUSE || 
							o.getPiece() == HouseConstants.HObject.MAHOGANY_PET_HOUSE ||
							o.getPiece() == HouseConstants.HObject.CONSECRATED_PET_HOUSE ||
							o.getPiece() == HouseConstants.HObject.DESECRATED_PET_HOUSE ||
							o.getPiece() == HouseConstants.HObject.NATURAL_PET_HOUSE) {
						return room;
					}
				}
			}
		}
		return null;
	}
	/**
	 * removes a pet from the player house instance
	 * @param item
	 * @param update
	 */
	public void removePet(Item item, boolean update) {
		if (update && !isOwnerInside()) {
			return;
		}
		if (!buildMode) {
			if (getMenagerie() != null) {
				Pets pet = Pets.forId(item.getId());
				if (pet == null) {
					return;
				}
				;
				int npcId = 0;
				if (pet.getGrownItemId() == item.getId()) {
					npcId = pet.getGrownNpcId();
				} else {
					npcId = pet.getBabyNpcId();
				}
				for (NPC npc : pets) {
					if (npc != null && npc.getId() == npcId) {
						npc.finish();
						pets.remove(npc);
						break;
					}
				}
			}
		}
	}
	/**
	 * adds a pet the to player house instance
	 * @param item
	 * @param update
	 */
	public void addPet(Item item, boolean update) {
		if (update && !isOwnerInside()) {
			return;
		}
		if (!buildMode) {
			if (getMenagerie() != null) {
				RoomReference men = getMenagerie();
				WorldTile spawn = new WorldTile(boundChuncks[0] * 8 + men.x * 8 + 3,
						boundChuncks[1] * 8 + men.y * 8 + 3, men.plane);

				Pets pet = Pets.forId(item.getId());
				if (pet == null) {
					return;
				}
				;
				NPC npc = new NPC(1, spawn, -1, true);
				if (pet.getGrownItemId() == item.getId()) {
					npc.setNPC(pet.getGrownNpcId());
				} else {
					npc.setNPC(pet.getBabyNpcId());
				}
				pets.add(npc);
				npc.setRandomWalk(true);
			}
		}
	}
	/**
	 * creates a room
	 * @param slot
	 */
	public void createRoom(int slot) {
		final Room[] rooms = HouseConstants.Room.values();
		if (slot >= rooms.length)
			return;
		final int[] position = (int[]) player.getTemporaryAttributtes().get("CreationRoom");
		player.closeInterfaces();
		if (position == null)
			return;
		final Room room = rooms[slot];
		if ((room == Room.DUNGEON_CORRIDOR || room == Room.DUNGEON_JUNCTION || room == Room.DUNGEON_PIT || room == Room.DUNGEON_STAIRS) && position[2] != 0) {
			player.sm("That room can only be built underground.");
			return;
		}
		if ((room == Room.GARDEN || room == Room.FORMAL_GARDEN || room == Room.MENAGERIE) && position[2] != 1) {
			player.sm("That room can only be built on ground.");
			return;
		}
		if (room.getLevel() > player.getSkills().getLevel(Skills.CONSTRUCTION)) {
			player.sm("You need a Construction level of " + room.getLevel() + " to build this room.");
			return;
		}
		if (player.getInventory().getCoinsAmount() < room.getPrice()) {
			player.sm("You don't have enough coins to build this room.");
			return;
		}
		player.getDialogueManager().startDialogue("CreateRoomD", new RoomReference(room, position[0], position[1], position[2], 0));
	}

	public void createRoom(RoomReference room) {
		if (player.getInventory().getCoinsAmount() < room.room.getPrice()) { 
			player.sm("You don't have enough coins to build this room.");
			return;
		}
		player.getInventory().removeItemMoneyPouch(new Item(995, room.room.getPrice()));
	//	player.houseMoney += room.room.getPrice();
		roomsR.add(room);
		refreshNumberOfRooms();
		refreshHouse();
	}

	public void destroyHouse() {
		final int[] boundChunksCopy = boundChuncks;
		loaded = false;
		boundChuncks = null;
		removeServant();
		GameEngine.get().getSlowExecutor().schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChunksCopy[0], boundChunksCopy[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);
	}

	public void enterGuest() {
		guests.add(player);
	}

	public void enterMyHouse() {
		joinHouse(player);
		players.add(player);
	}

	public void expelGuests() {
		if (!isOwnerInside()) {
			player.sm("You can only expel guests when you are in your own house.");
		}
		 kickGuests();
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	/*
	 * refers to logout
	 */
	public void finish() {
		kickGuests();
		// no need to leavehouse for owner, controler does that itself
	}

	public Player getPlayer() {
		return player;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public WorldTile getPortal() {
		for (RoomReference room : roomsR) {
			if (room.room == HouseConstants.Room.GARDEN || room.room == HouseConstants.Room.FORMAL_GARDEN) {
				for (ObjectReference o : room.objects)
					if (o.getPiece() == HouseConstants.HObject.EXIT_PORTAL)
						return getCenterTile(room);
			}
		}
		//shouldnt happen
		int[] xyp = MapUtils.convert(MapUtils.Structure.CHUNK, MapUtils.Structure.TILE, boundChuncks);
		return new WorldTile(xyp[0] + 32, xyp[1] + 32, 0);
	}

	public RoomReference getRoom(int x, int y, int plane) {
		for (final RoomReference room : roomsR)
			if (room.x == x && room.y == y && room.plane == plane)
				return room;
		return null;
	}

	public boolean hasWaterCan() {
		for (int id = 5333; id <= 5340; id++)
			if (player.getInventory().containsItemToolBelt(id))
				return true;
		return false;
	}

	public int getPortalCount() {
		int count = 0;
		for (RoomReference room : roomsR) {
			if (room.room == HouseConstants.Room.GARDEN || room.room == HouseConstants.Room.FORMAL_GARDEN) {
				for (ObjectReference o : room.objects)
					if (o.getPiece() == HouseConstants.HObject.EXIT_PORTAL)
						count++;
			}
		}
		return count;
	}

	public RoomReference getPortalRoom() {
		for (RoomReference room : roomsR) {
			if (room.room == HouseConstants.Room.GARDEN || room.room == HouseConstants.Room.FORMAL_GARDEN) {
				for (ObjectReference o : room.objects)
					if (o.getPiece() == HouseConstants.HObject.EXIT_PORTAL)
						return room;
			}
		}
		return null;
	}

	public House() {
		build = 4;
		buildMode = true;
		roomsR = new ArrayList<RoomReference>();
		/*first toom is a garden */
		addRoom(HouseConstants.Room.GARDEN, 3, 3, 1, 0);
		/* */ 
		getRoom(3, 3, 1).addObject(Builds.CENTREPIECE, 1);
	}

	public boolean addRoom(HouseConstants.Room room, int x, int y, int plane, int rotation) {
		return roomsR.add(new RoomReference(room, x, y, plane, rotation));
	}

	/*
	 * temporary
	 */
	public void reset() {
		build = 4;
		look = 0;
		buildMode = true;
		doorsOpen = true;
		roomsR = new ArrayList<RoomReference>();
		addRoom(HouseConstants.Room.GARDEN, 3, 3, 1, 0);
		getRoom(3, 3, 1).addObject(Builds.CENTREPIECE, 0);
	}

	public void init() {
		if (build != 4)
			reset();
		players = new ArrayList<Player>();
		dungeonTraps = new ArrayList<WorldObject>();
		refreshBuildMode();
		refreshArriveInPortal();
		refreshDoorsOpen();
		refreshNumberOfRooms();
	}

	public void refreshNumberOfRooms() {
		player.getPackets().sendGlobalConfig(944, roomsR.size());
	}

	//TODO
	public void setDoorsOpen(boolean doorsOpen) {
		this.doorsOpen = doorsOpen;
		refreshDoorsOpen();

	}

	public void refreshDoorsOpen() {
		player.getVarsManager().sendVarBit(1553, doorsOpen ? 1 : 0);
	}

	public void setArriveInPortal(boolean arriveInPortal) {
		this.arriveInPortal = arriveInPortal;
		refreshArriveInPortal();

	}

	public void refreshArriveInPortal() {
		player.getVarsManager().sendVarBit(1552, arriveInPortal ? 1 : 0);
	}
	
	public void setBuildMode(boolean buildMode) {
		if (this.buildMode == buildMode)
			return;
		this.buildMode = buildMode;
		if (loaded) {
			expelGuests();
			if (isOwnerInside()) { //since it expels all guests no point in refreshing if owner not inside
				player.stopAll();
				if (player.isCanPvp())
					player.setCanPvp(false);
				refreshHouse();
			}
		}
		refreshBuildMode();
	}

	public void refreshBuildMode() {
		player.getVarsManager().sendVarBit(1537, buildMode ? 1 : 0);
	}

	public boolean isDoor(WorldObject object) {
		return object.getDefinitions().name.equalsIgnoreCase("Door hotspot");
	}

	public boolean isDoorSpace(WorldObject object) {
		return object.getDefinitions().name.equalsIgnoreCase("Door space");
	}

	public boolean isOwner(Player player) {
		return this.player == player;
	}
	
	public boolean containsPlayer(Player player) {
		return players.contains(player);
	}

	private boolean isOwnerInside() {
		return players.contains(player);
	}

	public boolean isSky(int x, int y, int plane) {
		return buildMode && plane == 2 && getRoom((x / 8) - boundChuncks[0], (y / 8) - boundChuncks[1], plane) == null;
	}

	public boolean isWindow(int id) {
		return id == 13830;
	}

	public void kickGuests() {
		/*for (final Player player : new ArrayList<Player>(guests)) {
			if (isOwner(player))
				continue;
			leaveHouse(player, KICKED);
		}*/
	}
	
	public static void leaveHouse(Player player) {
		Controler controller = player.getControlerManager().getControler();
		if (controller == null || !(controller instanceof HouseControler)) {
			player.getPackets().sendGameMessage("You're not in a house.");
			return;
		}
		((HouseControler) controller).getHouse().leaveHouse(player, KICKED);
	}

	/*
	 * 0 - logout, 1 kicked/tele outside outside, 2 tele somewhere else
	 */
	public void leaveHouse(Player player, int type) {
		player.getControlerManager().removeControlerWithoutCheck();
		if (type == LOGGED_OUT)
			player.setNextWorldTile(new WorldTile(2335,3665,0));
		else if (type == KICKED)
			player.setNextWorldTile(new WorldTile(2335,3665,0));
		if (player.isCanPvp())
			player.setCanPvp(false);
		if (player.getAppearence().getRenderEmote() != -1)
			player.getAppearence().setRenderEmote(-1);
		if (isOwner(player) && servantInstance != null)
			servantInstance.setFollowing(false);
		players.remove(player);
		if (players.size() == 0)
			destroyHouse();
	}

	public void openBuildInterface(WorldObject object, final Builds build) {
		if (!buildMode) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You can only do that in building mode.");
			return;
		}
		final int roomX = object.getChunkX() - boundChuncks[0];
		final int roomY = object.getChunkY() - boundChuncks[1];
		final RoomReference room = getRoom(roomX, roomY, object.getPlane());
		if (room == null)
			return;
		final int interId = build.getPieces().length > 3 ? 396 : 394;
		final Item[] itemArray = new Item[interId == 396 ? 7 : 3];
		for (int index = 0; index < build.getPieces().length; index++) {
			final HObject piece = build.getPieces()[index];
			itemArray[interId == 396 ? BUILD_INDEXES[index] : index] = new Item(piece.getItemId(), 1);
			player.getPackets().sendConfig(1485 + index, player.getSkills().getLevel(Skills.CONSTRUCTION) >= piece.getLevel() && player.getInventory().containsItems(piece.getRequirements()) ? 1 : 0);
		}
		player.getPackets().sendItems(8, itemArray);
		player.getPackets().sendInterSetItemsOptionsScript(interId, 11, 8, interId == 396 ? 2 : 1, 4, "Build");
		player.getPackets().sendUnlockIComponentOptionSlots(interId, 11, 0, interId == 396 ? 7 : 3, 0);
		player.getInterfaceManager().sendInterface(interId);
		for (int i = 0; i < (interId == 396 ? 7 : 3); i++) {
			if (i >= build.getPieces().length) {
				player.getPackets().sendHideIComponent(interId, (interId == 394 ? 29 : 49) + i, true);
				player.getPackets().sendIComponentText(interId, (interId == 394 ? 32 : 56) + i, "");
				player.getPackets().sendIComponentText(interId, 14 + (5 * i), "");
				for (int i2 = 0; i2 < 4; i2++)
					player.getPackets().sendIComponentText(interId, 15 + i2 + (5 * i), "");
			} else {
				player.getPackets().sendIComponentText(interId, (interId == 394 ? 32 : 56) + i, "Lvl " + build.getPieces()[i].getLevel());
				player.getPackets().sendIComponentText(interId, 14 + (5 * i), ItemDefinitions.getItemDefinitions(build.getPieces()[i].getItemId()).getName());
				for (int i2 = 0; i2 < 4; i2++)
					player.getPackets().sendIComponentText(interId, 15 + i2 + (5 * i), build.getPieces()[i].getRequirements().length <= i2 ? "" : build.getPieces()[i].getRequirements()[i2].getName() + ": " + build.getPieces()[i].getRequirements()[i2].getAmount());
			}
		}
		player.getTemporaryAttributtes().put("OpenedBuild", build);
		player.getTemporaryAttributtes().put("OpenedBuildObject", object);
		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				player.getTemporaryAttributtes().remove("OpenedBuild");
				player.getTemporaryAttributtes().remove("OpenedBuildObject");
			}

		});
	}

	public void openRemoveBuild(WorldObject object) {
		if (!buildMode) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You can only do that in building mode.");
			return;
		}
		if (object.getId() == HouseConstants.HObject.EXIT_PORTAL.getId() && getPortalCount() <= 1) {
			player.getDialogueManager().startDialogue("SimpleMessage", "Your house must have at least one exit portal.");
			return;
		}
		final int roomX = object.getChunkX() - boundChuncks[0];
		final int roomY = object.getChunkY() - boundChuncks[1];
		final RoomReference room = getRoom(roomX, roomY, object.getPlane());
		if (room == null)
			return;
		final ObjectReference ref = room.getObject(object);
		if (ref != null) {
			if (ref.build.toString().contains("STAIRCASE")) {
				if (object.getPlane() != 1) {
					final RoomReference above = getRoom(roomX, roomY, 2);
					final RoomReference below = getRoom(roomX, roomY, 0);
					if ((above != null && above.getStaircaseSlot() != -1) || (below != null && below.getStaircaseSlot() != -1))
						player.getDialogueManager().startDialogue("SimpleMessage", "You cannot remove a building that is supporting this room.");
					return;
				}
			}
			player.getDialogueManager().startDialogue("RemoveBuildD", object);
		}
	}

	/*
	 * door used to calculate where player facing to create
	 */
	public void openRoomCreationMenu(int roomX, int roomY, int plane) {
		if (!buildMode) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You can only do that in building mode.");
			return;
		}
		final RoomReference room = getRoom(roomX, roomY, plane);
		if (room != null) {
			if (room.plane == 1 && getRoom(roomX, roomY, room.plane + 1) != null) {
				player.getDialogueManager().startDialogue("SimpleMessage", "You can't remove a room that is supporting another room.");
				return;
			}
			if ((room.room == Room.GARDEN || room.room == Room.FORMAL_GARDEN) && getPortalCount() < 2) {
				if (room == getPortalRoom()) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Your house must have at least one exit portal.");
					return;
				}
			}
			player.getDialogueManager().startDialogue("RemoveRoomD", room);
		} else {
			if (roomX == 0 || roomY == 0 || roomX == 7 || roomY == 7) {
				player.getDialogueManager().startDialogue("SimpleMessage", "You can't create a room here.");
				return;
			}
			if (plane == 2) {
				final RoomReference r = getRoom(roomX, roomY, 1);
				if (r == null || (r.room == Room.GARDEN || r.room == Room.FORMAL_GARDEN || r.room == Room.MENAGERIE)) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You can't create a room here.");
					return;
				}

			}
			for (int index = 0; index < HouseConstants.Room.values().length; index++) {
				final Room refRoom = HouseConstants.Room.values()[index];
				if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= refRoom.getLevel() && player.getInventory().getCoinsAmount() >= refRoom.getPrice())
					player.getPackets().sendIComponentText(402, index + 68, "<col=008000> " + Utils.formatNumber(refRoom.getPrice()) + " coins");
			}
			player.getInterfaceManager().sendInterface(402);
			player.getTemporaryAttributtes().put("CreationRoom", new int[] { roomX, roomY, plane });
			player.setCloseInterfacesEvent(new Runnable() {
				@Override
				public void run() {
					player.getTemporaryAttributtes().remove("CreationRoom");
				}
			});
		}
	}

	public void openRoomCreationMenu(WorldObject door) {
		int roomX = player.getChunkX() - boundChuncks[0]; // current room
		int roomY = player.getChunkY() - boundChuncks[1]; // current room
		final int xInChunk = player.getXInChunk();
		final int yInChunk = player.getYInChunk();
		if (xInChunk == 7)
			roomX += 1;
		else if (xInChunk == 0)
			roomX -= 1;
		else if (yInChunk == 7)
			roomY += 1;
		else if (yInChunk == 0)
			roomY -= 1;
		openRoomCreationMenu(roomX, roomY, door.getPlane());
	}

	public void previewRoom(RoomReference reference, boolean remove) {
		final int boundX = boundChuncks[0] * 8 + reference.x * 8;
		final int boundY = boundChuncks[1] * 8 + reference.y * 8;
		final int realChunkX = reference.room.getChunkX();
		final int realChunkY = reference.room.getChunkY();
		final Region region = World.getRegion(MapUtils.encode(MapUtils.Structure.REGION, realChunkX / 8, realChunkY / 8));
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				final WorldObject[] objects = region.getAllObjects(reference.plane, (realChunkX & 0x7) * 8 + x, (realChunkY & 0x7) * 8 + y);
				if (objects != null) {
					for (final WorldObject object : objects) {
						if (object == null)
							continue;
						final ObjectDefinitions defs = object.getDefinitions();
						if (defs.containsOption(4, "Build")) {
							final WorldObject objectR = new WorldObject(object);
							final int[] coords = DynamicRegion.translate(x, y, reference.rotation, defs.sizeX, defs.sizeY, object.getRotation());
							objectR.setWorldTile(new WorldTile(boundX + coords[0], boundY + coords[1], reference.plane));
							objectR.setRotation((object.getRotation() + reference.rotation) & 0x3);
							// just a preview. they're not realy there.
							if (remove)
								World.removeObject(objectR);
							else
								World.spawnObject(objectR);
						}
					}
				}
			}
		}
	}
	
	public void refreshHouse() {
		loaded = false;
		sendStartInterface(player);
		createHouse(false);
	}
	
	public RoomReference getRoom(Player player) {
		int roomX = player.getChunkX() - boundChuncks[0]; //current room
		int roomY = player.getChunkY() - boundChuncks[1]; //current room
		RoomReference room = getRoom(roomX, roomY, player.getPlane());
		if (room == null)
			return null;
		return room;
	}

	public RoomReference getRoom(Room room) {
		for (RoomReference roomR : roomsR) {
			if (room == roomR.getRoom())
				return roomR;
		}
		return null;
	}

	private void refreshObject(RoomReference rref, ObjectReference oref, boolean remove) {
		final int boundX = rref.x * 8;
		final int boundY = rref.y * 8;
		if (boundChuncks == null)
			return;
		final int[] regionPos = MapUtils.convert(MapUtils.Structure.CHUNK, MapUtils.Structure.REGION, boundChuncks);
		final Region region = World.getRegion(MapUtils.encode(MapUtils.Structure.REGION, regionPos), true);
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				final WorldObject[] objects = region.getAllObjects(rref.plane, boundX + x, boundY + y);
				if (objects != null) {
					for (final WorldObject object : objects) {
						if (object == null)
							continue;
						final int slot = oref.build.getIdSlot(object.getId());
						if (slot == -1)
							continue;
						//Utils.print(remove + ", " + object.getX() + ", " + object.getY());
						if (remove)
							World.spawnObject(object);
						else {
							final WorldObject objectR = new WorldObject(object);
							objectR.setId(oref.getId(slot));
							World.spawnObject(objectR);
						}
					}
				}
			}
		}
	}

	public void removeBuild(final WorldObject object) {
		if (!buildMode) { // imagine u use settings to change while dialogue
			// open, cheater :p
			player.getDialogueManager().startDialogue("SimpleMessage", "You can only do that in building mode.");
			return;
		}
		final int roomX = object.getChunkX() - boundChuncks[0];
		final int roomY = object.getChunkY() - boundChuncks[1];
		final RoomReference room = getRoom(roomX, roomY, object.getPlane());
		if (room == null)
			return;
		final ObjectReference oref = room.removeObject(object);
		if (oref == null)
			return;
		player.lock();
		player.setNextAnimation(new Animation(3685));
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				World.removeObject(object);
				refreshObject(room, oref, true);
				player.lock(1);
			}
		}, 1);
	}

	public void removeRoom() {
		final int roomX = player.getChunkX() - boundChuncks[0]; // current room
		final int roomY = player.getChunkY() - boundChuncks[1]; // current room
		final RoomReference room = getRoom(roomX, roomY, player.getPlane());
		if (room == null)
			return;
		if (room.getRoom() == Room.GARDEN) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You cannot remove the garden, what are you thinking!");
			return;
		}
		if (room.getPlane() != 1) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You cannot remove a building that is supporting this room.");
			return;
		}

		final RoomReference above = getRoom(roomX, roomY, 2);
		final RoomReference below = getRoom(roomX, roomY, 0);

		final RoomReference roomTo = above != null && above.getStaircaseSlot() != -1 ? above : below != null && below.getStaircaseSlot() != -1 ? below : null;
		if (roomTo == null) {
			player.getDialogueManager().startDialogue("SimpleMessage", "These stairs do not lead anywhere.");
			return;
		}
		openRoomCreationMenu(roomTo.getX(), roomTo.getY(), roomTo.getPlane());
	}

	public void removeRoom(RoomReference room) {
		if (roomsR.remove(room)) {
			refreshNumberOfRooms();
			refreshHouse();
		}
	}

	public void sendStartInterface(Player player) {
		player.lock();
		// player.getPackets().sendRootInterface(399, 0);
		player.getMusicsManager().playMusic(454);
		player.getPackets().sendMusicEffect(22);
	}

	public void setPlayer(Player player) {
		this.player = player;
		look = 5;
		if (petHouse == null) {
			petHouse = new PetHouse();
		}
		if (pets == null) {
			pets = new CopyOnWriteArrayList<NPC>();
		}
		petHouse.setPlayer(player);
	}

	public void teleportPlayer(Player player) {
		player.setNextWorldTile(getPortal());
		if (isPVPMode())
			player.setCanPvp(true);
	}
	

	public static boolean isDungeon(Room room) {
		for (Room dungeon : HouseConstants.DUNGEON_ROOMS) {
			if (room == dungeon)
				return true;
		}
		return false;
	}

	public boolean isArriveInPortal() {
		return arriveInPortal;
	}

	public byte getBuild() {
		return build;
	}

	public byte getLook() {
		return look;
	}

	public int getBurnerCount() {
		return burnerCount;
	}

	public void setBurnerCount(int burnerCount) {
		this.burnerCount = burnerCount;
	}

	public void redecorateHouse(int look) {
		this.look = (byte) look;
	}

	public void setServantOrdinal(byte ordinal) {
		if (ordinal == -1) {
			removeServant();
			servant = null;
			return;
		}
		this.servant = HouseConstants.Servant.values()[ordinal];
	}

	public boolean hasServant() {
		return servant != null;
	}

	public static void enterHousePortal(Player player) {
		player.getDialogueManager().startDialogue("EnterHouseD");
	}

	private void removeServant() {
		if (servantInstance != null) {
			servantInstance.finish();
			servantInstance = null;
		}
	}

	private void addServant() {
		if (servantInstance == null && servant != null)
			servantInstance = new ServantNPC(this);
	}

	/*
	 * when switching from modes
	 */
	private void refreshServant() {
		removeServant();
		addServant();
	}

	public Servant getServant() {
		return servant;
	}

	public void callServant(boolean bellPull) {
		if (bellPull) {
			player.setNextAnimation(new Animation(3668));
			player.lock(2);
		}
		if (servantInstance == null)
			player.getPackets().sendGameMessage("The house has no servant.");
		else {
			servantInstance.setFollowing(true);
			servantInstance.setNextWorldTile(Utils.getFreeTile(player, 1));
			servantInstance.setNextAnimation(new Animation(858));
			player.getDialogueManager().startDialogue("ServantD", servantInstance);
		}
	}

	public ServantNPC getServantInstance() {
		return servantInstance;
	}
	
	public boolean isBuildMode() {
		return buildMode;
	}

	public void switchChallengeMode(boolean pvp) {
		if (isBuildMode())
			return;
		if (isPVPMode()) {
			for (Player player : new ArrayList<Player>(players))
				player.setCanPvp(false);
		}
		challengeMode = isChallengeMode() ? 0 : pvp ? 2 : 1;
		refreshChallengeMode();
		player.lock(2);
	}

	public boolean isChallengeMode() {
		return challengeMode != 0;
	}

	public boolean isPVPMode() {
		return challengeMode == 2;
	}

	public void pullLeverChallengeMode(WorldObject object) {
		if (isChallengeMode()) {
			player.getPackets().sendGameMessage("You turn off " + (isPVPMode() ? "pvp" : "challenge") + " mode.");
			switchChallengeMode(false);
			sendPullLeverEmote(object);
		} else {
			player.getDialogueManager().startDialogue("ChallengeModeLeverD", object);
		}
	}

	public void sendPullLeverEmote(WorldObject object) {
		player.lock(2);
		player.setNextAnimation(new Animation(3611));
		World.sendObjectAnimation(object, new Animation(3612));
	}

	public void leverEffect(WorldObject object) {
		sendPullLeverEmote(object);
		player.getPackets().sendGameMessage("Nothing interesting happens hehe.");
	}

	public WorldObject getDungeonTrap(WorldTile toTile) {
		for (WorldObject dungeonTrap : dungeonTraps) {
			if (dungeonTrap.matches(toTile))
				return dungeonTrap;
		}
		return null;
	}

	public WorldTile getCenterTile(RoomReference rRef) {
		if (boundChuncks == null || rRef == null)
			return null;
		return new WorldTile(boundChuncks[0] * 8 + rRef.x * 8 + 3, boundChuncks[1] * 8 + rRef.y * 8 + 3, rRef.getPlane());
	}

	public int getPaymentStage() {
		return paymentStage;
	}

	public void resetPaymentStage() {
		paymentStage = 0;
	}

	public void incrementPaymentStage() {
		paymentStage++;
	}
	
	/*
	 * called when switching challenge mode
	 */
	private void refreshChallengeMode() {
		boolean remove = !isChallengeMode();
		for (RoomReference reference : roomsR) {
			if (reference.plane != 0)
				continue;
			for (ObjectReference o : reference.objects) {
				if (o.getPiece().getNPCId() != -1)
					refreshObject(reference, o, !remove, true);
			}
		}
		if (remove)
			clearChallengeNPCs();
		else {
			boolean pvp = isPVPMode();
			for (Player player : new ArrayList<Player>(players)) {
				player.getPackets().sendGameMessage((pvp ? "PVP" : "Challenge") + " mode is now activated.");
				if (pvp)
					player.setCanPvp(true);
			}
		}
	}

	/*
	 * called when turning off challenge mode / switching to buildmode
	 */
	private void clearChallengeNPCs() {
		for (RoomReference rref : roomsR) {
			if (rref.getGuardians() == null)
				continue;
			List<Guard> challengeModeNPCs = rref.getGuardians();
			for (Guard n : challengeModeNPCs)
				n.finish();
			challengeModeNPCs.clear();
		}
	}
	
	private void refreshObject(RoomReference rref, ObjectReference oref, boolean remove, boolean challengeMode) {
		int boundX = rref.x * 8;
		int boundY = rref.y * 8;
		final Region region = getRegion();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				WorldObject[] objects = region.getAllObjects(rref.plane, boundX + x, boundY + y);
				if (objects != null) {
					for (WorldObject object : objects) {
						if (object == null)
							continue;
						int slot = oref.getBuild().getIdSlot(object.getId());
						if (slot == -1)
							continue;
						if (remove) {
							if (challengeMode) {
								WorldObject objectR = new WorldObject(object);
								objectR.setId(HouseConstants.EMPTY_SPACE_ID);
								World.spawnObject(objectR);
								List<Guard> guardians = rref.getGuardians();
								if (guardians != null)
									guardians.add(new Guard(oref.getPiece().getNPCId(), this, object));
							} else {
								World.spawnObject(object);
							}
						} else {
							WorldObject objectR = new WorldObject(object);
							objectR.setId(oref.getId(slot));
							World.spawnObject(objectR);
						}
					}
				}
			}
		}
	}
	
	private Region getRegion() {
		int[] regionPos = MapUtils.convert(MapUtils.Structure.CHUNK, MapUtils.Structure.REGION, boundChuncks);
		return World.getRegion(MapUtils.encode(MapUtils.Structure.REGION, regionPos), true);
	}
	
	public void climbStaircase(Player player, WorldObject object, boolean up, boolean dungeonEntrance) {
		int roomX = object.getChunkX() - boundChuncks[0];
		int roomY = object.getChunkY() - boundChuncks[1];
		RoomReference room = getRoom(roomX, roomY, object.getPlane());
		if (room == null)
			return;
		if (buildMode && room.plane == (up ? 2 : 0)) {
			player.getPackets().sendGameMessage("You are on the " + (up ? "highest" : "lowest") + " possible level so you cannot add a room " + (up ? "above" : "under") + " here.");
			return;
		}
		RoomReference roomTo = getRoom(roomX, roomY, room.plane + (up ? 1 : -1));
		if (roomTo == null) {
			if (buildMode && isOwner(player))
				player.getDialogueManager().startDialogue("CreateRoomStairsD", room, up, dungeonEntrance);
			else
				player.getPackets().sendGameMessage((dungeonEntrance ? "This entrance does " : "These stairs do") + " not lead anywhere.");
			//start dialogue
			return;
		}
		if ((roomTo.room != Room.GARDEN && roomTo.room != Room.FORMAL_GARDEN) && roomTo.getStaircaseSlot() == -1) {
			player.getPackets().sendGameMessage((dungeonEntrance ? "This entrance does " : "These stairs do") + " not lead anywhere.");
			return;
		}
		player.useStairs(-1, new WorldTile(player.getX(), player.getY(), player.getPlane() + (up ? 1 : -1)), 0, 2);

	}
	
	public void climbTrapdoor(Player player, WorldObject object, boolean up) {
		int roomX = object.getChunkX() - boundChuncks[0];
		int roomY = object.getChunkY() - boundChuncks[1];
		RoomReference room = getRoom(roomX, roomY, object.getPlane());
		if (room == null)
			return;
		if (!up && buildMode && room.plane != 1) {
			player.getPackets().sendGameMessage("You cannot add a oubliette here.");
			return;
		}
		RoomReference roomTo = getRoom(roomX, roomY, room.plane + (up ? 1 : -1));
		if (roomTo == null) {
			if (buildMode && isOwner(player) && !up)
				player.getDialogueManager().startDialogue("CreateOublietteD", room);
			else
				player.getPackets().sendGameMessage("This " + (up ? "ladder" : "trapdoor") + " does not lead anywhere.");
			//start dialogue
			return;
		}
		if (roomTo.room != Room.THRONE_ROOM && roomTo.room != Room.OUTBLIETTE) {
			player.getPackets().sendGameMessage("This " + (up ? "ladder" : "trapdoor") + " does not lead anywhere.");
			return;
		}
		player.useStairs(up ? 828 : 827, new WorldTile(player.getX(), player.getY(), player.getPlane() + (up ? 1 : -1)), 0, 2);
	}
	
	public WorldObject getWorldObjectForBuild(RoomReference reference, Builds build) {
		int boundX = boundChuncks[0] * 8 + reference.x * 8;
		int boundY = boundChuncks[1] * 8 + reference.y * 8;
		for (int x = -1; x < 8; x++) {
			for (int y = -1; y < 8; y++) {
				for (HObject piece : build.getPieces()) {
					WorldObject object = World.getObjectWithId(new WorldTile(boundX + x, boundY + y, reference.plane), piece.getId());
					if (object != null) {
						return object;
					}
				}
			}
		}
		return null;
	}
	
	public PetHouse getPetHouse() {
		return petHouse;
	}

	public void setPetHouse(PetHouse petHouse) {
		this.petHouse = petHouse;
	}
}
