package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;



public class AgilityD extends Dialogue {

    private int npcId = 19455;

    @Override
    public void start() {
	sendOptionsDialogue("Agility Teleports",
	"Gnome agility area", 
	"Barbarian agility area", 
	"Wilderness agility area", 
	"Agility Pyramid",
    "Next page"	);
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
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
			if(player.getSkills().getLevel(Skills.AGILITY) >= 52){
				player.getDialogueManager().startDialogue("teleportWarningD",new WorldTile(2998, 3933, 0), "Yes teleport me into the wilderness");
			} else {
				player.sm("You need 52 agility to use this teleport");
			}
		} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3352, 2827, 0));
				player.sm("Welcome to the Agility Pyramid.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
	}else if (componentId == OPTION_5) {
	sendOptionsDialogue("Agility Teleports",
	"Burthope agility area", 
	"Apetoll agility area",
	"Hefin agility area");
	stage = 1 ;
	}
    } else if (stage == 1){
		if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2919, 3551, 0));
				player.sm("Welcome to the Burthope.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
	} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2756, 2745, 0));
				player.sm("Welcome to the Apetoll.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
	} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2184,3397, 1));
				player.sm("Welcome to the Apetoll.");
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
