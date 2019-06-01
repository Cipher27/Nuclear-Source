package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class SkillingTeleports extends Dialogue {
	
	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
		    sendOptionsDialogue("Skilling Teleports", "Fishing", "Mining", "Agility", "Farming", "More Options...");
			stage = 1; 
		}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		if(componentId == OPTION_1) {
            Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2599, 3421, 0));
			end();
		}
		if(componentId == OPTION_2) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3300, 3312, 0));
			end();
		}
        	if(componentId == OPTION_3) {
			sendOptionsDialogue("Agility Teleports", "Gnome Agility", "Barbarian Outpost");
			stage = 5;
		}
		if(componentId == OPTION_4) {
		   Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2814, 3462, 0));
			end();
		}
		if(componentId == OPTION_5) {
		    stage = 2;
		    sendOptionsDialogue("Skilling Teleports", "Runecrafting", "Summoning", "Hunter", "Woodcutting", "More options...");
		}
		} else if (stage == 2) {
		if(componentId == OPTION_1) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2601, 3162, 0));
			end();
		}
		if(componentId == OPTION_2) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2209, 5343, 0));
			end();
		}
		if(componentId == OPTION_3) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2526, 2916, 0));
			end();
		}
		if(componentId == OPTION_4) {
			 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2724, 3491, 0));
				end();
			}
		if(componentId == OPTION_5) {
		    stage = 3;
		    sendOptionsDialogue("Skilling Teleports", "Dungeoneering", "Construction", "Smithing", "Back");
		}
		} else if (stage == 3) {
		if(componentId == OPTION_1) {
            Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3450, 3730, 0));
			end();
		}
		if(componentId == OPTION_2) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2544, 3094, 0));
			end();
		}
		if(componentId == OPTION_3) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3187, 3425, 0));
			end();
		}
		 }else if (stage == 5) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2470,
						3436, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2552,
						3563, 0));
						}
		if (stage == 4) {
		    sendOptionsDialogue("Skilling Teleports", "Fishing", "Mining", "Agility", "Farming", "More Options...");
			stage = 1; 
		}
	  }
	}

	@Override
	public void finish() {

	}

}