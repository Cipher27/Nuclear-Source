package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.utils.Utils;

public class EnchantedGemDialouge extends Dialogue {

    private int npcId = 9085;

    @Override
    public void start() {
	sendNPCDialogue(npcId, 9827, "'Ello and what are you after then?");
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == -1) {
	    stage = 0;
	    sendOptionsDialogue("Chose an option", "How many monsters do I have left?", "Give me a tip.", "Nothing, Nevermind.");
	} else if (stage == 0) {
	    if (componentId == OPTION_1) {
		 if (player.hasTask == true) {
                    sendEntityDialogue((short) 242, new String[]{NPCDefinitions.getNPCDefinitions(9085).name, "You have a short memory, don't you?", "You need to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName}, (byte) 1, 9085, 9827);
               stage = 1;
			   } else {
                    sendEntityDialogue((short) 241, new String[]{NPCDefinitions.getNPCDefinitions(9085).name, "Foolish warrior. You don't have a slayer task!"}, (byte) 1, 9085, 9827);
               stage = 1;
			   }
	    } else if (componentId == OPTION_2) {
		stage = 1;
		if (player.getSlayerManager().getCurrentTask() == null) {
		    sendNPCDialogue(npcId, 9827, "You currently don't have a task.");
		    return;
		}
		String[] tipDialouges = player.getSlayerManager().getCurrentTask().getTips();
		if (tipDialouges != null && tipDialouges.length != 0) {
		    String chosenDialouge = tipDialouges[Utils.random(tipDialouges.length)];
		    if (chosenDialouge == null || chosenDialouge.equals(""))
			sendNPCDialogue(npcId, 9827, "I don't have any tips for you currently.");
		    else
			sendNPCDialogue(npcId, 9827, chosenDialouge);
		} else
		    sendNPCDialogue(npcId, 9827, "I don't have any tips for you currently.");
	    } else
		end();
	} else if (stage == 1) {
	    end();
	}
    }

    @Override
    public void finish() {

    }
}
