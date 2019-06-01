package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class TrainingTeleports extends Dialogue {
	
	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
		    sendOptionsDialogue("Monsters - Page 1", "Rock Crabs (Low-Mid)", "Bandit camp(Medium Level)", "Chaos dwarves (High Level)", "Green Dragons (Dragon Bones)","more");
			stage = 1; 
		}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		if(componentId == OPTION_1)
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2410, 3853, 0));
		if(componentId == OPTION_2)
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3173, 2980, 0));
		if(componentId == OPTION_3)
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1493, 4704, 0));
		if(componentId == OPTION_4) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2987, 3615, 0));
			player.getControlerManager().startControler("Wilderness");
		} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Monsters - Page 2", "Frost Dragons", "Iron Dragons", 
						"Bronze Dragons", "Lava Dungeon", "More" );
				stage = 21;
			
			}
		}
	else if(stage == 20) {
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
						"Fire Giants", "Revenants(wildy)", "More to come.." );
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
			
			}
              else if(componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3077, 10058, 0));
				player.sm("Welcome to the fority dungeon.");
				player.getControlerManager().startControler("Wilderness");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
			
			}			else if(componentId == OPTION_5) {
				player.sm("More will be added, suggest something on the forums.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			
			}
					
			
		}
		
		}

	@Override
	public void finish() {

	}

}