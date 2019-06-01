package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;



public class WoodcuttingD extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Woodcutting", "Low level training", "Ivy","Maple trees","Yew trees","Magic trees");
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	if (componentId == OPTION_1) { 
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3100,3241, 0));
		player.sm("Welcome to the skilling area, you can find normal,oak and willow trees here!");
		end();
		} else if (componentId == OPTION_2) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2624, 3307, 0));
		player.sm("Welcome to the ivy's!");
		end();
		} else if (componentId == OPTION_3) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2723, 3498, 0));
		player.sm("Welcome to the maple trees.");
		end();
		} else if (componentId == OPTION_4) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2747,3431, 0));
		player.sm("Welcome to yew trees, we are aware of the texture glitch and do everything to fix it as soon as possible.");
		end();
		}else if (componentId == OPTION_5) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2701,3392, 0));
		player.sm("Welcome to magic trees, we are aware of the texture glitch and do everything to fix it as soon as possible.");
		end();
		}
    }
    }
    @Override
    public void finish() {

    }
}
