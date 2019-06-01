package com.rs.game.player.dialogues.ZariaQuest;

import com.rs.cores.CoresManager;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;

public class PortalFound extends Dialogue {
private int npcId = 14392;
	
	@Override
	public void start() {
	sendPlayerDialogue(9827, "This portal doesn't look that broken");
	stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
   sendNPCDialogue(npcId, 9827,"Believe me when I say it's broken, don't try touching it, I already lost 2 soldiers...");
		stage = 1;
	} else if (stage == 1){
		player.ZariaQueststage = 2;
		sendPlayerDialogue(9827, "I'll look around for clues how we can make this thing work.");
		stage = 100;
	} 
	else if (stage == 100)
		end();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
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
