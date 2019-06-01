package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.minigames.rfd.RecipeforDisaster;
import com.rs.game.player.Skills;
import com.rs.game.player.content.LavaFlowMines;
import com.rs.game.player.content.LividFarmHandler;
import com.rs.game.player.content.magic.Magic;

/**
 * @author paolo
 * @author Xiles
 * @author Plato
 * @author Adam
 */


public class TeleportCrystal extends Dialogue {

	public TeleportCrystal() {
	}

	@Override
	public void start() {
		if(player.isLocked()){
			player.getPackets().sendGameMessage("no.");
			end();
			} else {

		stage = 1;
		sendOptionsDialogue("Teleport Options", "Home",
				"Minigames", "Skilling Locations", "Boss/Monster Teleports", "Cities");
			}

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) { //home
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1632, 6176, 0));
				player.sm("Welcome home, "+ player.getDisplayName() + ".");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				stage = 2;
			} else if (componentId == OPTION_2) {//Minigames
				sendOptionsDialogue("Minigames", "Barrows", "The RuneSpan", 
						"LividFarm", "Rise of the six", "Next Page" );
				stage = 7;
			} else if (componentId == OPTION_3) {//Skilling locations
				player.getInterfaceManager().sendInterface(3010);
				end();
			} else if (componentId == OPTION_4) {//Bossing Teleports
			    player.getInterfaceManager().sendInterface(3022);
				end();
			} else if (componentId == OPTION_5) {//page 2 of teleport options
				sendOptionsDialogue("Cities",
						
						"Ardougne",
						"Falador",
						"Varrock",
						"Yanille",
						"Seers' Village");
				stage = 6;
			}
			
			
			/**
			 * Free space
			 */
			
			
		} else if (stage == 3) { 
			if (componentId == OPTION_1) {
				
		} else if (componentId == OPTION_2) {
				
		} else if (componentId == OPTION_3) {
			
		} else if (componentId == OPTION_4) {
			
		} else if (componentId == OPTION_5) {
				
		}
			
			
			/**
			 * Skilling Teleports
			 */
			
			
		} else if (stage == 4) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2725, 3491, 0));
				player.sm("Welcome to Woodcutting.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2662, 3303, 0));
				player.sm("Welcome to Thieving.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2851, 3428, 0));
			player.sm("Welcome to Fishing.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			sendOptionsDialogue("Hunter", "Birds/Chins", "Grenwalls");
			stage = 98;
			
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Skilling Teleports - Page 2", "Agility", "Mining", 
					"Smithing", "Farming", "More" );
			stage = 10;
		}
			
		} else if (stage == 98) {
			if (componentId == OPTION_1) { 
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2525, 2915, 0));
				player.sm("Welcome to the red Chins hunter area.");
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2267, 3241, 0));
			player.sm("Welcome to the Grenwall hunter area.");
		}
			
		} else if (stage == 28) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2806, 3463, 0));
				player.sm("Welcome to the Catherby Farming Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2664, 3374, 0));
				player.sm("Welcome to the Ardougne Farming Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3599, 3523, 0));
				player.sm("Welcome to the Canifs Farming Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3052, 3304, 0));
				player.sm("Welcome to the Falador Farming Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
	}
			
		} else if (stage == 29) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3196, 3231, 0));
				player.sm("Welcome to the Lumbridge Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3229, 3456, 0));
				player.sm("Welcome to the Varrock Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3001, 3373, 0));
				player.sm("Welcome to the Falador Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2436, 3413, 0));
			player.sm("Welcome to the Tree Gnome Stronghold Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2927, 3429, 0));
			player.sm("Welcome to the Taverly Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 31) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2475, 3444, 0));
				player.sm("Welcome to the Gnome Stronghold Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2860, 3432, 0));
				player.sm("Welcome to the Catherby Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2498, 3178, 0));
				player.sm("Welcome to the Gnome Maze Tree Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2764, 3211, 0));
			player.sm("Welcome to the Brimhaven Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2346, 3161, 0));
			player.sm("Welcome to the LLeyta Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 32) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3182, 3356, 0));
				player.sm("Welcome to the Champion's Guild Bush Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2940, 3220, 0));
				player.sm("Welcome to the Rimmington Bush Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2630, 3313, 0));
				player.sm("Welcome to the Ardougne Bush Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2593, 3864, 0));
			player.sm("Welcome to the Etceteria Bush Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 34) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3229, 3310, 0));
				player.sm("Welcome to the Lumbridge Hop Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2667, 3522, 0));
				player.sm("Welcome to the McGrubor Hop Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2576, 3101, 0));
				player.sm("Welcome to the Yanille Hop Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2810, 3333, 0));
			player.sm("Welcome to the Entrana Hop Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 35) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3088, 3363, 0));
				player.sm("Welcome to the Belladonna Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3316, 3201, 0));
				player.sm("Welcome to the Cactus Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3452, 3470, 0));
				player.sm("Welcome to the Mushroom Patch.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2798, 3099, 0));
			player.sm("Welcome to the Calqut Tree Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2813, 3679, 0));
			player.sm("Welcome to the Trollheim Herb Patch.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		}
			
		} else if (stage == 33) {
			if (componentId == OPTION_1) { 
				sendOptionsDialogue("Hop Patches", "Lumbridge", "McGrubor", 
						"Yanille", "Entrana" );
				stage = 34;
		} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Misc Patches", "Belladonna", "Cactus", 
						 "Mushroom", "Calqut Tree", "Trollheim Herb" );
				stage = 35;
	}
			
		} else if (stage == 27) {
			if (componentId == OPTION_1) { 
				sendOptionsDialogue("Major Patches", "Catherby", "Ardougne", 
						"Canifs", "Falador" );
				stage = 28;
		} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Wood Tree Patches", "Lumbridge", "Varrock", 
						 "Falador", "Tree Gnome", "Taverly" );
				stage = 29;
		} else if (componentId == OPTION_3) {
			sendOptionsDialogue("Fruit Tree Patches", "Gnome Stronghold", "Catherby", 
					 "Gnome Maze", "Brimhaven", "Lleyta" );
				stage = 31;
		} else if (componentId == OPTION_4) {
			sendOptionsDialogue("Bush Patches", "Champion's Guild", "Rimmington", 
					 "Ardougne", "Etceteria");
				stage = 32;
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Farming Teleports", "Hop Patches", "Misc Patches");
				stage = 33;
	}
			
		} else if (stage == 24) {
			if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2468, 3437, 0));
				player.sm("Welcome to the Gnome Agility Course.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2552, 3557, 0));
				player.sm("Welcome to the Barbarian Agility Course.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2998, 3933, 0));
				player.sm("Welcome to the Wilderness Agility Course.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.getControlerManager().startControler("Wilderness");
		} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3352, 2827, 0));
				player.sm("Welcome to the Agility Pyramid.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
	}
			
			/**
			 * Skilling Telepoerts p2
			 */
			
		} else if (stage == 10) {
			if (componentId == OPTION_1) { 
				sendOptionsDialogue("Agility Teleports", "Gnome", "Barbarian", 
						"Wilderness", "Agility Pyramid" );
				stage = 24;
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3298, 3283, 0));
				player.sm("Welcome to Mining.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2614, 3082, 0));
				player.sm("Welcome to Smithing.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			sendOptionsDialogue("Farming Teleports", "Major Patches", "Wood Tree Patches", 
					"Fruit Tree Patches", "Bush Patches", "More" );
			stage = 27;
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Skilling Teleports - Page 3", "Abyssal Runecrafting", "Dungeoneering", 
					 "Summoning","Back" );
			stage = 81;
	}
		} else if (stage == 81) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3039,
						4834, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.getControlerManager().startControler("Abyss");
		} else if (componentId == OPTION_2) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3450,
					3719, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2209, 5343, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			
		}else if (componentId == OPTION_4) {
			stage = 1;
			
		}
		
			
			
			/**
			 * Bossing Teleports
			 */
			
			
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3067, 10255, 0));
				player.sendMessage("Welcome to the King Black Dragon.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
			player.getControlerManager().startControler("BorkControler", 0, null);
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevelForXp(Skills.SUMMONING) < 60) {
				player.getPackets().sendGameMessage("You need a summoning level of 60 to go to this monster.");
				player.getControlerManager().removeControlerWithoutCheck();
			} else {
			player.lock();
			player.getControlerManager().startControler("QueenBlackDragonControler");
			}
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2965, 4381, 2));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to the Corporeal Beast.");
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Bossing - Page 2", "Kalphite Queen", "Dungeoneering bosses", 
					"Dagganoth Kings", "Tormented Demons", "Next" );
			stage = 11;
		}
			
		} else if (stage == 11) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3226, 3108, 0));
				player.sendMessage("Welcome to the Kalphite Queen Lair.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3448, 3725, 0));
			player.sendMessage("Welcome to Domheim.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2545, 10143, 0));
			player.sendMessage("Welcome to the Dagganoth Lair.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2564, 5740, 0));
			player.sendMessage("Welcome to the Tormented Demons.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Bossing - Page 3", "Blink", "Yk'Lagor - DANGEROUS", 
					"None" );
			stage = 30;
	}
			
		} else if (stage == 30) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1368, 6623, 0));
				player.sendMessage("Welcome to Blink.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2521, 5232, 0));
			player.sendMessage("Welcome to Yk'Lagor the Thunderous.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();

	}	
			
			//PvP
		} else if(stage == 17) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3081, 3523, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to the Edgeville Ditch.");
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3128, 3719, 0));
				player.sm("Welcome to the Wilderness Volcano.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.getControlerManager().startControler("Wilderness");
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2539, 4716, 0));
				player.sm("Welcome to the Mage Bank.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3240, 3611, 0));
				player.sm("Welcome to the Multi PvP Area.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.getControlerManager().startControler("Wilderness");
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("PvP Teleports - Page 2", "Easts", "Wests"
					 );
				stage = 18;
			
			}
			
		} else if(stage == 18) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3360, 3658, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to the East Dragons.");
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3000, 3623, 0));
				player.sm("Welcome to the West Dragons.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} 


			/**
			 * Godwars Dungeon Teleport
			 */
			 
			 /**
			   *
			   "Ardougne",
						"Falador",
						"Varrock",
						"Yanille",
						"Seers' Village"
			   **/
			
			
		} else if (stage == 6) {
			if (componentId == OPTION_1) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2661,3303, 0));
				player.sm("Welcome to Ardougne.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			 }
			 else if (componentId == OPTION_2) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3013,3357, 0));
				player.sm("Welcome to Falador.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			 }
			 else if (componentId == OPTION_3) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3188,3439, 0));
				player.sm("Welcome to Varrock.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			 }
			 else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2610,3092, 0));
				player.sm("Welcome to Yanille.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			 }
			 else if (componentId == OPTION_5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2697, 3484, 0));
				player.sm("Welcome to Seers Village.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			 }
		
					

			
			//monsters
		} else if(stage == 20) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1313, 4527, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to Frost Dragons.");
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2914, 3922, 0));
				player.sm("Welcome to Iron Dragons.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2420, 4689, 0));
				player.sm("Welcome to Bronze Dragons.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3056, 10289, 0));
				player.sm("Welcome to the Lava Dungeon.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Monsters - Page 2", "Forinthry Dungeon", "Taverly Dungeon", 
						"Brimhaven Dungeon", "Wilderness Dungeon", "More" );
				stage = 21;
			
			}
			
		} else if(stage == 21) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3080, 10057, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to the Forinthry Dungeon.");
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2884, 9798, 0));
				player.sm("Welcome to Taverly Dungeon.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2710, 9466, 0));
				player.sm("Welcome to Brimhaven Dungeon.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3039, 3765, 0));
				player.sm("Welcome to the Wilderness Dungeon.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.getControlerManager().startControler("Wilderness");
				
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Monsters - Page 3", "Chaos Druids", "Elite Knights", 
						"Lumbridge Swamp", "Living Rock Caverns", "More" );
				stage = 23;
			
			}
			
		} else if(stage == 23) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2932, 9848, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to Chaos Druids.");
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3025, 9951, 1));
				player.sm("Welcome to Elite Knights.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3168, 9570, 0));
				player.sm("Welcome to Lumbridge Swamp.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3655, 5114, 0));
				player.sm("Welcome to the Living Rock Caverns.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Monsters - Page 4", "Grotworm Lair", "Ancient Cavern", 
						"Fire Giants", "More to come.." );
				stage = 26;
			}
		} else if(stage == 26) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1206, 6372, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to the Grotworm Lair.");
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1763, 5365, 1));
				player.sm("Welcome to the Ancient Cavern.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2575, 9862, 0));
				player.sm("Welcome to the waterfall dungeon.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			
			} else if(componentId == OPTION_4) {
				player.sm("More will be added, suggest something on the forums.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			
			}
			
			
			/**
			 * Barrows
			 */
			
			
			
			
		} else if (stage == 7) {
			if (componentId == OPTION_1) {
				
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3565, 3289, 0));
				player.sm("Welcome to Barrows.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
						
			
			/**
			 * The RuneSpan
			 */
			
			
		} else if (componentId == OPTION_2) {
			sendOptionsDialogue("The RuneSpan", "1st Level", "2nd Level", 
					"3rd Level" );
			stage = 69;
		} else if (componentId == OPTION_3) {
			LividFarmHandler.enterLividFarm(player);
			end();
		} else if (componentId == OPTION_4) {
			player.getDialogueManager().startDialogue("SimpleMessage", "This teleport has been disabled.");
				/*Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2240, 3358, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck(); */
		} else if (componentId == OPTION_5) { 
			sendOptionsDialogue("Minigames - Page 2", "Fight Caves", "Fight Kiln", 
					"Castle Wars", "Clan Wars", "More" );
			stage = 13;
		}
			
		} else if(stage == 13) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4612, 5129, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to Fight Caves");
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4743, 5161, 0));
				player.sm("Welcome to Fight Kiln.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2442, 3090, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to Castle Wars");
				
			} else if(componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2993, 9679, 0));
				player.sm("Welcome to Clan Wars.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Minigames - Page 3", "Warrior guild", "Warrior guild 2", "Back");
				stage = 14;
			
			}
			
		} else if(stage == 14) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2846,3536,2));
				player.sm("Welcome to the Warrior guild.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();	
			/*} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2852,3533,3));
				player.sm("Welcome to the roof of the Warrior guild.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();	*/
			} else if(componentId == OPTION_3){
					sendOptionsDialogue("Minigames - Page 2", "Fight Caves", "Fight Kiln", 
					"Castle Wars", "Clan Wars", "More" );
			stage = 13;
			}
			
		} else if(stage == 15) {
			if(componentId == OPTION_1) {
	         player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			//	player.getDialogueManager().startDialogue("BossTeleports", true);
			} else if(componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();	
			//	 player.getDialogueManager().startDialogue("TrainingTeleports", true);
			} else if(componentId == OPTION_3) {
				player.getInventory().addItem(6950, 1);
				player.sm("Here is your Livid Farm Crystal.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			} else if(componentId == OPTION_4) {
            RecipeforDisaster.enterRfd(player);
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Minigames - Page 5", "Zombies", "Dark Invasion", "Lava Flow Mines", "Pest Invasion",
						"Next" );
				stage = 58;
			}
				
			} else if(stage == 58) {
				if(componentId == OPTION_1) { 
		            Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3701, 3461, 0));
					player.sm("Welcome to Zombies.");
					player.getInterfaceManager().closeChatBoxInterface();
					player.getInterfaceManager().closeOverlay(true);
					player.getControlerManager().forceStop();
					player.getControlerManager().removeControlerWithoutCheck();
		} else if(componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
			player.getControlerManager().startControler("DarkInvasion");
			
		} else if (componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		LavaFlowMines.Entering(player);
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4520, 5516, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Minigames - Page 6", "Puro Puro", "Pest Control", "Rise of the six", "None" );
			stage = 59;
		}
		
	} else if(stage == 59) {
		if(componentId == OPTION_1) { 
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2592, 4317, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if(componentId == OPTION_2) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2659, 2649, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();	
		}
		else if(componentId == OPTION_3) {
			player.getControlerManager().startControler("RiseOfTheSix");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
		}
		else if(componentId == OPTION_4) {
			end();
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();	
		}
			
			
			
			
		
			
			
			
			/**
			 * Godwars Dungeon Teleports
			 */
			
			
		} else if(stage == 8) {
			if (componentId == OPTION_1) { // Nex
				teleportPlayer(2904, 5203, 0);
				player.getInterfaceManager().closeChatBoxInterface();

			} else if (componentId == OPTION_2) { // GWD
				teleportPlayer(2881, 5311, 0);
				player.getInterfaceManager().closeChatBoxInterface();
			}

			
			/**
			 * RuneSpan Teleports
			 */
			
		} else if(stage == 69) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3993, 6108, 1));
				player.getInterfaceManager().closeChatBoxInterface();
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4137, 6089, 1));
				player.getInterfaceManager().closeChatBoxInterface();
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4295, 6038, 1));
				player.getInterfaceManager().closeChatBoxInterface();
			}
			
		} else if(stage == 25) {
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3424, 5667, 0));
				player.sm("<col=FF0000>Welcome to the Ice Strykwyrms!");
				player.getInterfaceManager().closeChatBoxInterface();
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3373, 3160, 0));
				player.sm("<col=FF0000>Welcome to the Desert Strykwyrms!");
				player.getInterfaceManager().closeChatBoxInterface();
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2459, 2901, 0));
				player.sm("<col=FF0000>Welcome to the Jungle Strykwyrms!");
				player.getInterfaceManager().closeChatBoxInterface();
			}else if(componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3314, 3815, 0));
			player.sm("<col=FF0000>Welcome to the Wildy Strykwyrms, walk south to find them.");
			player.getInterfaceManager().closeChatBoxInterface();
		}
			
			
			/**
			 * Slayer Teleports
			 */
			
		} else if (stage == 12) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Strykewyrms", "Ice Strykewyrms", "Desert Strykewyrms", "Jungle Strykewyrms","Wildy Strykewyrms" );
				stage = 25;
		} else if (componentId == OPTION_2) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2808, 10002, 0));
			player.sendMessage("Welcome to the Fremmenik Slayer Dungeon.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1736, 5313, 1));
			player.sendMessage("Welcome to Kuradel's Slayer Dungeon.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_4) {
			player.sm("You teleport to the skeletal wyverns dungeon!");
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3044, 9557, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
	}   else if (componentId == OPTION_5) {
		sendOptionsDialogue("pick an option.", "Taverley dungeon", "Crimson dragon");
				stage = 96;
	}

			
		} else if(stage == 9) {
			if(componentId == OPTION_1) {//Slayer Tower
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3429, 3534, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to the Slayer Tower");
				
			} else if(componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4646, 5405, 0));
				player.sm("Welcome to the Polypore Dungeon.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4182, 5731, 0));
				player.sm("Welcome to Glacors.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if(componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3023, 9224, 0));
				player.sm("Welcome to Jadinkos.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Slayer - Page 2", "Strykewyrms", "Fremmenik Slayer Dungeon", "Kuradel's Slayer Dungeon",
						"skeletal wyverns","more" );
				stage = 12;
			
			}
		}
	 else if (stage == 96) {
		  if(componentId == OPTION_1) {
	player.sm("You teleport to the Taverley Dungeon!");
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2885,9801, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();	
		  } else if(componentId == OPTION_2) {
			//  if (player.crimsonDragons == true) {
		    player.sm("Welcome to the Crimson dragon pit.");
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3275,4389, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();	
		//	  } else {
			 player.sm("You haven't unlocked this feature at Kuradel.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();	 
		//	  }
		}
	}
		else if(stage == 105) {
		 if(componentId == OPTION_1) {
		player.getDialogueManager().startDialogue("BossTeleports", true);
		}
		else if(componentId == OPTION_2) {
		player.getDialogueManager().startDialogue("TrainingTeleports", true);
		}
		else if(componentId == OPTION_3) {
		sendOptionsDialogue("Slayer - Page 1", "Slayer Tower", "Polypore Dungeon.", "Glacors", "Jadinkos liar",
						"more" );
				stage = 9;
		}
		}
	}
			@Override
			public void finish() {
			}
	
	private void teleportPlayer(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
		player.getControlerManager().startControler("GodWars");
	}

	private void teleportPlayer2(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
		player.getControlerManager().startControler("RunespanControler");
	}

	private void teleportPlayer3(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
	}

}
