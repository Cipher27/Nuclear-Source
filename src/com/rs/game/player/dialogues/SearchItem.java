package com.rs.game.player.dialogues;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class SearchItem extends Dialogue {

	String key;
	int potentialItem;

	@Override
	public void start() {
		stage = 0;
		key = (String) parameters[0];
		search();
	}
	
	public void search() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			@Override
			public void run() {
				if(loop == 0)
				sendEntityDialogue2(SEND_2_TEXT_CHAT,
								new String[] { "Item Search",
									"Searching for inventory items...", "Keyword: "+key}, IS_NPC, -1, 9827);
				if(loop == 3) {
					if(player.getInventory().getItemForKeyword(key) != 0) {
						potentialItem = player.getInventory().getItemForKeyword(key);
						stage = 1;
						sendEntityDialogue2(SEND_4_TEXT_CHAT,
								new String[] { "Item Search",
									"Searching....", "Keyword: "+key, "Item Found: "+ItemDefinitions.getItemDefinitions(potentialItem).getName(), ""}, IS_NPC, -1, 9827);
						stop();
					} else {
						stage = 6;
						sendEntityDialogue2(SEND_2_TEXT_CHAT,
								new String[] { "Item Search",
									"No inventory items found for keyword: "+key, "Searching your bank account..."}, IS_NPC, -1, 9827);
					}
				}
				if(loop == 6) {
					if(player.getBank().getItemForKey(key) != 0) {
						potentialItem = player.getBank().getItemForKey(key);
						stage = 1;
						sendEntityDialogue2(SEND_4_TEXT_CHAT,
								new String[] { "Item Search",
									"Searching....", "Keyword: "+key, "Item Found: "+ItemDefinitions.getItemDefinitions(potentialItem).getName(), ""}, IS_NPC, -1, 9827);
						stop();
					} else {
						stage = 6;
						sendEntityDialogue2(SEND_2_TEXT_CHAT,
								new String[] { "Item Search",
									"No bank account items found for keyword: "+key, "Please try again."}, IS_NPC, -1, 9827);
					}
				}
				if(loop == 7)
					stop();
				loop++;
			}
		}, 0, 1);
	}

	@Override
	public void run(int interfaceId, int c) {
		if(stage == 0) {
			search();
		} else if(stage == 1) {
			stage = 2;
			sendDialogue2(SEND_4_OPTIONS, new String[] { "<col=ff0000>Confirmation</col>",
				ItemDefinitions.getItemDefinitions(potentialItem).getName()+" x1", "Choose Different Amount", "No"});
		} else if(stage == 2) {
			if(c == 1) {
				player.giveItemId = potentialItem;
				player.giveItemAmount = 1;
				player.getDialogueManager().startDialogue("TournamentsD");
			} else if(c == 2) {
				player.giveItemId = potentialItem;
				player.getTemporaryAttributtes().put("tourn_item_amount", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] { ItemDefinitions.getItemDefinitions(potentialItem).getName()+" x" });
			} else if(c == 3) {
				player.getDialogueManager().startDialogue("TournamentsD");
			}
		} else if(stage == 6) {
			player.getDialogueManager().startDialogue("TournamentsD");
		}
	}

	@Override
	public void finish() {
		
	}

}