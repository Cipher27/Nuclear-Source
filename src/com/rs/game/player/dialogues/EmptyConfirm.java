package com.rs.game.player.dialogues;

import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.LendingManager;
import com.rs.game.player.Player;
import com.rs.game.player.content.Lend;


/**
 * 
 * @author BoomScape
 *
 */

public class EmptyConfirm extends Dialogue {

	Item item;

 @Override
	public void start() {
		player.getInterfaceManager().sendChatBoxInterface(1183);
		player.getPackets().sendIComponentText(1183, 12, "Please Confrim that you want to empty your inventory.");
		player.getPackets().sendIComponentText(1183, 7, "Are you sure you want to empty your inventory?");
		player.getPackets().sendIComponentText(1183, 22, "Confirm Empty");
	}

@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == 9) {
			Lend lend = LendingManager.getLend(player);
			if (lend != null) {
				Player lender = World.getPlayer(lend.getLendee());
				if (lender != null
						&& lender.getInventory().containsOneItem(
								lend.getItem().getDefinitions().getLendId())) {
					LendingManager.unLend(lend);
				}
			}
			player.getInventory().reset();
		}
		end();
	}

@Override
	public void finish() {

	}

}