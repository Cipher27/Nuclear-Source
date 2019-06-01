package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;


public class CraftingD extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Crafting", "Flax field", "Pottery wheel", "Orb enchatings");
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	if (componentId == OPTION_1) { 
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2742,3444, 0));
		end();
		} else if (componentId == OPTION_2) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3085, 3409, 0));
		end();
		} else if (componentId == OPTION_3){
			sendOptionsDialogue("Select a location", "Air", "Water", "Earth", "Fire");
			stage = 1;
		}
    }else if (stage == 1) {
	if (componentId == OPTION_1) { 
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3087,3569, 0));
		end();
		} else if (componentId == OPTION_2) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2845,3424, 0));
		end();
		} else if (componentId == OPTION_3){
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3087,9935, 0));
			end();
		} else {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2822,9828, 0));
			end();
		}
    }
    }
    @Override
    public void finish() {

    }
}
