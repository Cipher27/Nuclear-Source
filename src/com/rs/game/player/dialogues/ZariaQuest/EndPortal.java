package com.rs.game.player.dialogues.ZariaQuest;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;

public class EndPortal extends Dialogue {
private int npcId = 14392;
	
	@Override
	public void start() {
	sendDialogue("It looks like there's a big soldier in the cave beneath this, are you sure you want to enter?");
	stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
   sendOptionsDialogue("Ready?", "Yes", "No");
   stage = 1;
	} else if (stage == 1){
		if(componentId == OPTION_1) {
			tutorialTeleport(2988,9644,0);
			fade(player);
			stage = 100;
		}
		else
			end();
	}
	else if (stage == 100)
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
