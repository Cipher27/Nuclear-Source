package com.rs.game.player.content;
 
import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;


 

public class AshesScattering {
 
  
    public enum AshesData {
        IMPIOUS(20264, 4, 56),
        ACCURSED(20266, 6, 47),
        INFERNAL(20268, 8, 40);
         
        private int itemId, gfx;
         
        private double exp;
         
        public int getItemId() {
            return itemId;
        }
         
        public double getExp() {
            return exp;
        }
         
        public int getGFX() {
            return gfx;
        }
         
        private static Map<Integer, AshesData> ashes = new HashMap<Integer, AshesData>();
 
        static {
            for (AshesData ash : AshesData.values()) {
                ashes.put(ash.getItemId(), ash);
            }
        }
        
 
        public static AshesData forId(int id) {
            return ashes.get(id);
        }
         
        private AshesData(int itemId, double exp, int gfx) {
            this.itemId = itemId;
            this.exp = exp;
            this.gfx = gfx;
        }

		public void scatter(final Player player, int slotId) {
			final Item item = player.getInventory().getItem(slotId);
	        if (item == null || AshesData.forId(item.getId()) == null)
	            return;
			if (player.getBoneDelay() > Utils.currentTimeMillis())
				return;
	        final AshesData ashesData = AshesData.forId(item.getId());
	    	final ItemDefinitions itemDef = new ItemDefinitions(item.getId());
	        player.getInventory().deleteItem(item.getId(), 1);
	        player.setNextAnimation(new Animation(445));
	    	WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					player.getPackets().sendGameMessage(
							"You bury the " + itemDef.getName().toLowerCase());
					player.unlock();
					player.getSkills().addXp(Skills.PRAYER,
							ashesData.getExp());
					stop();
					player.isBurying = false;
				}

			}, 2);	// TODO Auto-generated method stub
			
		}

		
    }
     
   
    public static void scatter(final Player player, int inventorySlot) {
    	final Item item = player.getInventory().getItem(inventorySlot);
        if (item == null || AshesData.forId(item.getId()) == null)
            return;
		if (player.getBoneDelay() > Utils.currentTimeMillis())
			return;
        final AshesData ashesData = AshesData.forId(item.getId());
    	final ItemDefinitions itemDef = new ItemDefinitions(item.getId());
        player.getInventory().deleteItem(item.getId(), 1);
        player.setNextAnimation(new Animation(445));
    	WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getPackets().sendGameMessage(
						"You bury the " + itemDef.getName().toLowerCase());
				player.unlock();
				player.getSkills().addXp(Skills.PRAYER,
						ashesData.getExp());
				stop();
				player.isBurying = false;
			}

		}, 2);
        
    }
     
}