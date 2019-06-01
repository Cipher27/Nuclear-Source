package com.rs.game.player.robot.methods;

import java.util.ArrayList;
import java.util.List;

import com.rs.game.item.Item;
import com.rs.game.player.robot.Robot;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.utils.Utils;



/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public class Equipping extends Method {
	
	public Equipping(Robot robot) {
		super(robot);
		stage = Stage.Finished;
		wears = new ArrayList<Wear>();
	}
	
	int chance;
	boolean spawn;
	List<Wear> wears;
	
	@Override
	public void process() {
		equip();
	}
	
	public void spawn(int slot, Item item) {
		spawn = true;
		wear(slot, item);
	}
	
	public void wear(int slot, Item item) {
		stage = Stage.Running;
		wears.add(new Wear(slot, item));
	}
	
	public void clear() {
		wears.clear();
	}
	
	public void equip() {
		//if (stage == Stage.Running) {
		if (wears.size() > 0) {
			Object[] array = wears.toArray();
			for(Object wear : array) {
				if (Utils.random(100) <= chance) {
					((Wear) wear).wear(robot, spawn);
					wears.remove(wear);
				}
			}
		}
		if (wears.size() == 0) {
			stage = Stage.Finished;
			spawn = false;
		}
		//}
	}
	
	public final class Wear {
		
		private int slot;
		private Item item;
		
		public Wear(int slot, Item item) {
			this.setSlot(slot);
			this.setItem(item);
		}
		
		public void wear(Robot robot, boolean spawn) {
			if (spawn) { // Equipment slot and id
				robot.getEquipment().wieldOneItem(slot, item);
			} else {
				int inventorySlot = robot.getInventory().getItems().lookupSlot(item.getId());
				if (inventorySlot != -1) { // Inventory slot and id
					ButtonHandler.sendWear(robot, inventorySlot, item.getId());
				}
			}
		}
		
		public int getSlot() {
			return slot;
		}
		
		public void setSlot(int slot) {
			this.slot = slot;
		}
		
		public Item getItem() {
			return item;
		}
		
		public void setItem(Item item) {
			this.item = item;
		}
	}
}
