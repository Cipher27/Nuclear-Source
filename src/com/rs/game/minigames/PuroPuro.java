package com.rs.game.minigames;

import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Hunter.FlyingEntities;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public class PuroPuro extends Controler {

		private static final Item[][] REQUIRED = {
			{ new Item(11238, 3), new Item(11240, 2), new Item(11242, 1) },
			{ new Item(11242, 3), new Item(11244, 2), new Item(11246, 1) },
			{ new Item(11246, 3), new Item(11248, 2), new Item(11250, 1) },
			{ null } };

	private static final Item[] REWARD = { new Item(11262, 1),
			new Item(11259, 1), new Item(11258, 1), new Item(11260, 3) };
	
	public static final int JARS[] = {(11238),11240,11242,11244,11246,
			11248,11250,11252,11254,11256,15513,15517};

	@Override
	public void start() {
		player.getPackets().sendBlackOut(2);
	}
	/**
	 * checks if the players inv contains a jar
	 * @param player
	 * @return
	 */
	private boolean containsJars(Player player){
		for(int i = 11238; i < 11256; i+=2)
			if(player.getInventory().containsItem(i,1))
				return true;
		return false;
	}
	/*
	 * shuffles the array of jars
	 */
	public int[] shuffle(int[] array) {
	    for (int i=0; i<100; i++) {
	        int r1 = (int)(Math.random()*array.length);
	        int r2 = (int)(Math.random()*array.length);
	        int tmp = array[r1];
	        array[r1] = array[r2];
	        array[r2] = tmp;
	    }
	    return array;
	}
	/*
	 * removes a random jar from the player his inventory
	 */
	private boolean pickRandomJar(final Player player){
	int shuffled[] = shuffle(JARS);
	for(int jars :  shuffled){
		if(player.getInventory().contains(jars)){
		player.sm("An imp stole a jar from you and freed his friend.");
		player.getInventory().deleteItem(jars,1);
		World.addGroundItem(new Item(11260,1), player.getWorldTile());
		return true;
		}
	}
	return false;
	}
	int randomTime = 0;
	/**
	 * process during the controller
	 */
	@Override
	public void process(){
		if(randomTime == 0 ){
			randomTime = Utils.random(20,50) * (player.getSkills().getLevel(Skills.THIEVING)/10);
			if(containsJars(player)){
				pickRandomJar(player);
			}
			
		}
		randomTime--;
	}

	@Override
	public void forceClose() {
		player.getPackets().sendBlackOut(0);
	}

	@Override
	public void magicTeleported(int type) {
		player.getControlerManager().forceStop();
	}

	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean login() {
		start();
		return false;
	}

	@Override
	public boolean processObjectClick1(WorldObject object) {
		switch (object.getId()) {
		case 25014:
			player.getControlerManager().forceStop();
			Magic.sendTeleportSpell(player, 6601, -1, 1118, -1, 0, 0,
					new WorldTile(2427, 4446, 0), 9, false,
					Magic.OBJECT_TELEPORT);
			return true;
		}
		return true;
	}
	@Override
	public boolean processNPCClick1(NPC npc) {
		if (npc.getId() == 6070) {
			player.getDialogueManager().startDialogue("SimpleNPCMessage", 6070, 
					"Welcome to Puro Puro, you can trade me if you need jars.");
			return false;
		}
		return true;
	}
	@Override
	public boolean processNPCClick2(NPC npc) {
		if (npc.getId() == 6070) {
			ShopsHandler.openShop(player, 32);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean processNPCClick3(NPC npc) {
		if (npc.getId() == 6070) {
			openPuroInterface(player);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean processNPCClick4(NPC npc) {
		if (npc.getId() == 6070) {
			if (!player.getEquipment().containsOneItem(10010) && !player.getInventory().containsOneItem(10010)) {
				player.getInventory().addItemDrop(10010, 1);
				player.getInventory().addItemDrop(11260, 6);
				player.getDialogueManager().startDialogue("SimpleNPCMessage", 6070, 
						"I've given you a Butterfly net and 6 impling jars to start off with. "
						+ "If you need more simply buy some from my shop.");
				return false;
			}
			player.getDialogueManager().startDialogue("SimpleNPCMessage", 6070, 
					"You already have the right equipment; "
					+ "if you need more impling jars simply buy some from my shop.");
			return false;
		}
		return true;
	}

	public static void pushThrough(final Player player, WorldObject object) {
		int objectX = object.getX();
		int objectY = object.getY();
		int direction = 0;
		if (player.getX() == objectX && player.getY() < objectY) {
			objectY = objectY + 1;
			direction = ForceMovement.NORTH;
		} else if (player.getX() == objectX && player.getY() > objectY) {
			objectY = objectY - 1;
			direction = ForceMovement.SOUTH;
		} else if (player.getY() == objectY && player.getX() < objectX) {
			objectX = objectX + 1;
			direction = ForceMovement.EAST;
		} else if (player.getY() == objectY && player.getX() > objectX) {
			objectX = objectX - 1;
			direction = ForceMovement.WEST;
		} else {
			objectY = objectY - 1;
			objectX = objectX + 1;
			direction = ForceMovement.SOUTH | ForceMovement.EAST;
		}
		player.getPackets()
				.sendGameMessage(
						Utils.getRandom(2) == 0 ? "You use your strength to push through the wheat in the most efficient fashion."
								: "You use your strength to push through the wheat.");
		player.setNextFaceWorldTile(object);
		player.setNextAnimation(new Animation(6594));
		player.lock();
		final WorldTile tile = new WorldTile(objectX, objectY, 0);
		player.setNextFaceWorldTile(object);
		player.setNextForceMovement(new ForceMovement(tile, 6, direction));
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.unlock();
				player.setNextWorldTile(tile);
			}
		}, 6);
	}

	public static void initPuroImplings() {
		for (int i = 0; i < 5; i++) {
			for (int index = 0; index < 11; index++) {
				if (i > 2) {
					if (Utils.getRandom(1) == 0)
						continue;
				}
				World.spawnNPC(
						FlyingEntities.values()[index].getNpcId(),
						new WorldTile(Utils.random(2558 + 3, 2626 - 3), Utils
								.random(4285 + 3, 4354 - 3), 0), -1, false);
			}
		}
	}

	public static void openPuroInterface(final Player player) {
		player.getInterfaceManager().sendInterface(540); // puro puro
		for (int component = 60; component < 64; component++)
			player.getPackets().sendHideIComponent(540, component, false);
		player.setCloseInterfacesEvent(new Runnable() {

			@Override
			public void run() {
				player.getTemporaryAttributtes().remove("puro_slot");
			}
		});
	}

	public static void handlePuroInterface(Player player, int componentId) {
		player.getTemporaryAttributtes().put("puro_slot",
				(componentId - 20) / 2);
	}

	public static void confirmPuroSelection(Player player) {
		if (player.getTemporaryAttributtes().get("puro_slot") == null)
			return;
		int slot = (int) player.getTemporaryAttributtes().get("puro_slot");
		Item exchangedItem = REWARD[slot];
		Item[] requriedItems = REQUIRED[slot];
		if (slot == 3) {
			requriedItems = null;
			for (Item item : player.getInventory().getItems().getItems()) {
				if (item == null
						|| FlyingEntities.forItem((short) item.getId()) == null)
					continue;
				requriedItems = new Item[] { item };
			}
		}
		if (requriedItems == null
				|| !player.getInventory().containsItems(requriedItems)) {
			player.getPackets().sendGameMessage(
					"You don't have the required items.");
			return;
		}
		if (player.getInventory().addItemDrop(exchangedItem.getId(),
				exchangedItem.getAmount())) {
			player.getInventory().removeItems(requriedItems);
			player.closeInterfaces();
			player.getPackets().sendGameMessage(
					"You exchange the required items for: "
							+ exchangedItem.getName().toLowerCase() + ".");
		}
	}

}
