package com.rs.game.player.dialogues.ZariaQuest;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;

public class StartQuest extends Dialogue {
private int npcId = 14392;
	
	@Override
	public void start() {
		sendNPCDialogue(npcId, 9827,"The first thing we need to do is find his portal, so we can do further investigation.");
	stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
   sendPlayerDialogue(9827, "Do you have any idea where to look ?");
		stage = 1;
	} else if (stage == 1){
		sendNPCDialogue(npcId, 9827,"My guardians found an old portal of them on the Dragontooth island, maybe start there");
	stage = 2;
	} else if(stage == 2){
			sendNPCDialogue(npcId, 9827,"Do you want me to bring you?");
	stage = 3;
		} else if (stage == 3){
		sendOptionsDialogue("Quick travel to Dragontooth Island?", "Yes", "No");
		stage = 4;
	}  else if (stage == 4){
		if(componentId == OPTION_1) {
			fade(player);
			tutorialTeleport(3790,3561,0);
			player.ZariaQueststage = 1;
			sendNPCDialogue(npcId, 9827,"Come and find me at the portal");
	        stage = 100;
		} else if(componentId == OPTION_2) {
			stage = 100;
		}
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
