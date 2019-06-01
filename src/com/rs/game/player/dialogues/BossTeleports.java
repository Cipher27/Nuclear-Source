package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class BossTeleports extends Dialogue {
	
	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
		    sendOptionsDialogue("Bossing - Page 1", "Bandos entrance", "Armadyl entrance", "Saradomin entrance", "Zamorak entrance", "More Options...");
			stage = 1; 
		}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
	    if(componentId == OPTION_1) {
            Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2859, 5357, 0));
			player.getControlerManager().startControler("GodWars");
			end();
		}
		else if(componentId == OPTION_2) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2835, 5291, 0));
			player.getControlerManager().startControler("GodWars");
			end();
		}
        else	if(componentId == OPTION_3) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2923, 5262, 0));
			player.getControlerManager().startControler("GodWars");
			end();
		}
		else if(componentId == OPTION_4) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2925, 5336, 0));
			player.getControlerManager().startControler("GodWars");
			end();
		}
	    else if(componentId == OPTION_5) {
		    stage = 2;
		    sendOptionsDialogue("Bossing - Page 2", "King Black Dragon", "Bork", "Queen Black Dragon", "Corporeal Beast", "More options");
		}
		}
		else if (stage == 2) {
		 if(componentId == OPTION_1) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3067, 10254, 0));
			player.getPackets().sendGameMessage("Careful, make sure to have an anti-dragon shield, you're going to need it!");
			end();
		}
		else if(componentId == OPTION_2) {
		    player.getControlerManager().startControler("BorkControler", 0, null);
			end();
		}
        else 	if(componentId == OPTION_3) {
		    player.getControlerManager().startControler("QueenBlackDragonControler");
			end();
		}
		else if(componentId == OPTION_4) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966, 4383, 2));
			end();
		}
		else if(componentId == OPTION_5) {
		stage = 3;
		 sendOptionsDialogue("Bossing - Page 3", "Kalphite Queen", "Dungeoneering bosses","Dagganoth Kings", "Tormented Demons", "Next" );
	
		}
        }	else if (stage == 3) {
		 
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3466, 9497, 0));
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
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1908, 4367, 0));
			player.sendMessage("Welcome to the Dagganoth Lair, click on the ladder enter the dks.");
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
			sendOptionsDialogue("Bossing - Page 3", "Blink", "Prehistorical abyssal", 
					"Sea troll queen", "back" );
			stage = 4;
	}
	}
			
		 else if (stage == 4) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1368, 6623, 0));
				player.sendMessage("Welcome to Blink.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3809, 4727, 0));
			player.sendMessage("Welcome to Prehistorical abyssal lair.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2995, 3117, 0));
			player.sendMessage("Welcome to the sea troll queen.");
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();

	}	
	else if (componentId == OPTION_4) {
	sendOptionsDialogue("Bossing - Page 1", "Bandos", "Armadyl", "Saradomin", "Zamorak", "More Options...");
			stage = 1; 
	}
		 }
	  }
	  
	@Override
	public void finish() {

	}

}