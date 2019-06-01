package com.rs.game.player.dialogues.ZariaQuest;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;

public class EndQuest extends Dialogue {
private int npcId = 14392;
	
	@Override
	public void start() {
		tutorialTeleport(2331,3668,0);
		sendNPCDialogue(npcId, 9827,"You did it again "+player.getDisplayName()+", thank you.");
	stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
   sendPlayerDialogue(9827, "It's a honor to work for you.");
		stage = 1;
	} else if (stage == 1){
		sendNPCDialogue(npcId, 9827,"Thank you, we'll leave it here for today. We killed his right hand so that's a start.");
	stage = 2;
	} else if(stage == 2){
			sendNPCDialogue(npcId, 9827,"But here take this as a reward.");
			player.questPoints++;
			player.completedZariaPartI = true;
			player.ZariaQueststage = 7;
			player.getInventory().addItem(23716,1);
			player.getInventory().addItem(24155,2);
			player.getInterfaceManager().sendEndZariaPart1();
	stage = 100;
	}else if (stage == 100)
		end();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	
	public void tutorialTeleport(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
	} 
	
	public void fade (final Player player) {
		final long time = FadingScreen.fade(player);
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					FadingScreen.unfade(player, time/2, new Runnable() {
						@Override
						public void run() {
				}
					});
			} catch (Throwable e) {
				Logger.handle(e);
			}
			}
		
	});
	}
	
	
}
