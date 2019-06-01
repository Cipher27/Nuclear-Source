package com.rs.game.player.dialogues;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;



public class DropValue extends Dialogue {
	
	int slotId;
	Item item;

    @Override
    public void start() {
	sendOptionsDialogue("This is an valuable item, are you sure you want to drop it?", 
	                "Yes I want to drop this.",
	                "No I don't want to drop it.",
					"Don't show me this message again.");
	stage =1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	slotId = (Integer) parameters[0];
    item = (Item) parameters[1];
      if (stage == 1) {
		if (componentId == OPTION_1) {
           player.getInventory().deleteItem(slotId, item);
		  if (player.getCharges().degradeCompletly(item))
			  return;
		  World.addGroundItem(item, new WorldTile(player), player, true, 60);
		  	player.getPackets().sendSound(2739, 0, 1);
		  end();
			}	else if (componentId == OPTION_2) {
			end();
			}else if (componentId == OPTION_3) {
            sendDialogue("Be carefull now, you can reanable this option at the info tab."); 
			player.showDropWarning = false;
	       stage =2;
			}
	} else if (stage == 2){
		end();
	}
    }

    @Override
    public void finish() {

    }
}
