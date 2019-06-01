package com.rs.game.player.dialogues;

import com.rs.game.player.Player;
import com.rs.game.player.Skills;



public class ProteanItem extends Dialogue {
	
	int itemId;
	private int ticks;

    @Override
    public void start() {
	itemId = (Integer) parameters[0];
	processWithDelay(player);
	//stage =1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	itemId = (Integer) parameters[0];
      if (stage == 1) {
		
	}
    }
    //bug with no animation
	public int processWithDelay(Player player) {
		int bars = 50;
		try {
		while(bars > 0 && player.getInventory().getAmountOf(itemId) > 0){
		player.getInventory().deleteItem(itemId, 1);
		bars --;
	    Thread.sleep(3 * 1000);
		
		player.getSkills().addXp(Skills.SMITHING, 500);
		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}

    @Override
    public void finish() {

    }
}
