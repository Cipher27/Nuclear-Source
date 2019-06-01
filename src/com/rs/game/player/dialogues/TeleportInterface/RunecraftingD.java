package com.rs.game.player.dialogues.TeleportInterface;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;



public class RunecraftingD extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("Rnecrafting teleports",
	"Abyssal runecrafting", 
	"Low level Runespan",
	"Mid level Runespan",
	"High level Runespan");
	stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == 0) {
	if (componentId == OPTION_1) { 
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3039,
						4834, 0));
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().startControler("Abyss");
		} else if (componentId == OPTION_2) {
			WorldTasksManager.schedule(new WorldTask() {
				boolean secondloop;
				@Override
				public void run() {
				if (!secondloop) {
				secondloop = true;
				player.getInterfaceManager().closeChatBoxInterface();
			    player.getInterfaceManager().closeOverlay(true);
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3994, 6108, 1));
				player.lock();
				} else {
				player.unlock();
				player.getControlerManager().startControler("RunespanControler");
				stop();
								}
							}
						}, 0, 4);
		} else if (componentId == OPTION_3) {
			/*if (!player.achievementsDone.contains(Achievements.SIPHON_I)){
				player.sm("You have not unlocked this ability yet, siphon 250 creatures to unlock this feature.");
				end();
			} else {*/
				WorldTasksManager.schedule(new WorldTask() {
				boolean secondloop;
				@Override
				public void run() {
				if (!secondloop) {
				secondloop = true;
				player.getInterfaceManager().closeChatBoxInterface();
			    player.getInterfaceManager().closeOverlay(true);
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4149, 6105, 1));
				player.lock();
				} else {
				player.unlock();
				player.getControlerManager().startControler("RunespanControler");
				stop();
								}
							}
						}, 0, 4);
			//}
		} else if (componentId == OPTION_4) {
			/* if(!player.achievementsDone.contains(Achievements.SIPHON_I)){
			player.sm("You have not unlocked this ability yet, siphon 250 creatures to unlock this feature.");
				end();	
			} else {
				*/
				WorldTasksManager.schedule(new WorldTask() {
				boolean secondloop;
				@Override
				public void run() {
				if (!secondloop) {
				secondloop = true;
				player.getInterfaceManager().closeChatBoxInterface();
			    player.getInterfaceManager().closeOverlay(true);
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4297, 6040, 1));
				player.lock();
				} else {
				player.unlock();
				player.getControlerManager().startControler("RunespanControler");
				stop();
								}
							}
						}, 0, 4);
			}
	//	} 
    }
    }
    @Override
    public void finish() {

    }
}
