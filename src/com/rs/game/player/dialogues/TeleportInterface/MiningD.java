package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;



public class MiningD extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Mining", "Low level mining","Mining Guild", "Granite rocks","Gem rocks","More");
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	if (componentId == OPTION_1) { 
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3300,3304, 0));
		player.sm("Welcome to the skilling area.");
		end();
		}else if (componentId == OPTION_2) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3021, 9739, 0));
		player.sm("Welcome to the mining guild.");
		end();
		} else if (componentId == OPTION_3) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3170, 2913, 0));
		player.sm("Welcome to the granite quary!.");
		end();
		} else if (componentId == OPTION_4) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2823, 2996, 0));
		player.sm("Welcome to the gem rocks.");
		end();
		} else if (componentId == OPTION_5) {
		sendOptionsDialogue("Mining", "Living Rock Caverns", "Lava flow mine", "Seren stones");
		stage = 1;
		}
		}else if (stage == 1){
		if (componentId == OPTION_1) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3655, 5114, 0));
		player.sm("Welcome to the Living Rock Caverns.");
		end();
		} else if (componentId == OPTION_2) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2183,5664, 0));
		end();
		}else if (componentId == OPTION_3) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2221,3299, 1));
		end();
		}
		}
    }
   
    @Override
    public void finish() {

    }
}
