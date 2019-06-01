package com.rs.game.player.content;



import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;


public class RunecraftingTeletabs {
	
	public enum AltarLocations {
		AIR(13599,new WorldTile(2841, 4829, 0)),
		MIND(13600,new WorldTile(2793, 4828, 0)),
		WATER(13601,new WorldTile(2725, 4832, 0)),
		EARTH(13602,new WorldTile(2655, 4830, 0)),
		FIRE(13603,new WorldTile(2574, 4848, 0)),
		BODY(13604,new WorldTile(2523, 4826, 0)),
		COSMIC(13605,new WorldTile(2162, 4833, 0)),
		CHAOS(13606,new WorldTile(2281, 4837, 0)),
		NATURE(13607,new WorldTile(2400, 4835, 0)),
		LAW(13608,new WorldTile(2464, 4818, 0)),
		DEATH(13609,new WorldTile(2208, 4830, 0)),
		BLOOD(13610,new WorldTile(2468,4889, 1)),
		ASTRAL(13611,new WorldTile(2153, 3868, 0));
		
		private static Map<Integer, AltarLocations> altars = new HashMap<Integer, AltarLocations>();

		static {
			for (AltarLocations altar : AltarLocations.values()) {
				altars.put(altar.getId(), altar);
			}
		}

		public static AltarLocations forId(int id) {
			return altars.get(id);
		}
	
		
		private int id;
		private WorldTile tile;
	
		private AltarLocations(int id,WorldTile tile) {
			this.id = id;
			this.tile = tile;
			
		}
		
		public WorldTile getTile() {
			return tile;
		}
		
		public int getId() {
			return id;
		}
	
	
	public static void Teleport(final Player p, int inventorySlot){
		final Item item = p.getInventory().getItem(inventorySlot);
		final AltarLocations altar = AltarLocations.forId(item.getId());
		if (item == null || AltarLocations.forId(item.getId()) == null)
			return;
		if (!p.getInventory().containsItem(item.getId(),1)) {
			p.sm("You need a tab before you can teleport.");
			return;
		}
		p.getInventory().deleteItem(item.getId(), 1);
		p.lock();
		p.setNextAnimation(new Animation(9597));
		p.setNextGraphics(new Graphics(1680));
		p.sm("The tablet broke while you where teleporting.");
		WorldTasksManager.schedule(new WorldTask() {
			int stage;

			@Override
			public void run() {
				if (stage == 0) {
					p.setNextAnimation(new Animation(4731));
					stage = 1;
				} else if (stage == 1) {
					p.setNextWorldTile(new WorldTile (altar.getTile().getX(),altar.getTile().getY(),altar.getTile().getPlane()));
					p.setNextFaceWorldTile(new WorldTile(altar.getTile().getX(), altar.getTile().getY() - 1, altar.getTile().getPlane()));
					p.setDirection(6);
					p.setNextAnimation(new Animation(-1));
					stage = 2;
				} else if (stage == 2) {
					p.resetReceivedDamage();
					p.unlock();
					stop();
				}

			}
		}, 2, 1);
		return;
	}
		
		
	}
	
}
