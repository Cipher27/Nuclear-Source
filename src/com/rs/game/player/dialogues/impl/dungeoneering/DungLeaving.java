package com.rs.game.player.dialogues.impl.dungeoneering;

import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author Jae
 * 
 *         Handles leaving the instance.
 * 
 */

public class DungLeaving extends Dialogue {

	@Override
	public void finish() {

	}

	public void handleLeaving() {
		player.getControlerManager().forceStop();
		player.getControlerManager().removeControlerWithoutCheck();
		player.lock(10);
		for (final Item item : player.getInventory().getItems().toArray()) {
			if (item == null)
				continue;
			player.getInventory().deleteItem(item);
			player.setNextWorldTile(new WorldTile(3450, 3718, 0));
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.lock(10);
				for (final Item item : player.getInventory().getItems()
						.toArray()) {
					if (item == null)
						continue;
					player.getInventory().deleteItem(item);
					player.setNextWorldTile(new WorldTile(3450, 3718, 0));
				}
				for (final Item item : player.getEquipment().getItems()
						.toArray()) {
					if (item == null)
						continue;
					player.getEquipment().deleteItem(item.getId(),
							item.getAmount());
				}
				if (player.getFamiliar() != null) {
					if (player.getFamiliar().getBob() != null)
						player.getFamiliar().getBob().getBeastItems().clear();
					player.getFamiliar().dismissFamiliar(false);
				}
				player.setInDung(false);
				player.getPackets().closeInterface(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548,
								player.getInterfaceManager().hasRezizableScreen() ? 11
										: 27);
				player.getInterfaceManager().closeChatBoxInterface();
			}
			if (componentId == OPTION_2) {
				stage = 2;
			}
		}
		if (stage == 2) {
			end();
			player.getInterfaceManager().closeChatBoxInterface();
		}
	}

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Are you sure you want to leave?",
					"Yes please.", "No thanks.");
			stage = 1;
		}
	}

}