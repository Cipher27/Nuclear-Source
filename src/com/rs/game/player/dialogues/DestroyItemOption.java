package com.rs.game.player.dialogues;

import com.rs.game.item.Item;
import com.rs.game.player.content.wildyrework.WildyInfernalBow;

public class DestroyItemOption extends Dialogue {

	int slotId;
	Item item;

	@Override
	public void start() {
		slotId = (Integer) parameters[0];
		item = (Item) parameters[1];
		player.getInterfaceManager().sendChatBoxInterface(1183);
		player.getPackets().sendIComponentText(1183, 7, item.getName());
		player.getPackets().sendItemOnIComponent(1183, 13, item.getId(), item.getAmount());
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (interfaceId == 1183 && componentId == 9) {
			if(item.getId() == 24474){
				WildyInfernalBow.startEvent();
			}
			if(player.getPrizedPendants().isAmulet(item.getId())){
				player.getPrizedPendants().destroyPendant(item.getId());
			}
			player.getInventory().deleteItem(slotId, item);
			player.getCharges().degradeCompletly(item);
			player.getPackets().sendSound(4500, 0, 1);
		}
		end();
	}

	@Override
	public void finish() {

	}

}
