package com.rs.game.player.dialogues.impl.misc;

import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;
//import com.rs.game.player.content.GrandExchange.Offer;

public class SimpleItemMessage extends Dialogue {
	Item item;
	String message;
	@Override
	public void start() {
		item = (Item)parameters[0];
		message = (String)parameters[1];
		sendItemDialogue(item.getId(), item.getAmount(), message);
		stage = 0;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 0) {
		end();
		}
	}

	@Override
	public void finish() {

	}

}
