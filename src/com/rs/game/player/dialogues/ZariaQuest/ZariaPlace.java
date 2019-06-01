package com.rs.game.player.dialogues.ZariaQuest;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.cutscenes.Cutscene;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;

public class ZariaPlace extends Dialogue {
private int npcId = 14392;
	
	@Override
	public void start() {
		tutorialTeleport(3503,3559, 0);
		player.getPackets().sendCameraPos(Cutscene.getX(player, 3504),Cutscene.getY(player, 3566), 9000);
		player.getPackets().sendCameraLook(Cutscene.getX(player, 3500),Cutscene.getY(player, 3575), 1000);  
		sendPlayerDialogue(9827, "What is this place?");
		player.lock();
	stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
   sendNPCDialogue(npcId, 9827,"This is the graveyard where all the leaders are burried.");
		stage = 1;
	} else if (stage == 1){
		 sendPlayerDialogue(9827, "Why do you bring me here?");
	stage = 2 ;
	} else if(stage == 2){
   sendNPCDialogue(npcId, 9827,"One of my guys decrypted the rock, the message was :'Seek our portal where we seek their death.'");
		stage = 3;
	} else if (stage == 3){
		 sendPlayerDialogue(9827, "So do you think it's here?");
	stage =  4;
	} else if(stage == 4){
   sendNPCDialogue(npcId, 9827,"We think so, but it's to dangerous for us to go. You've to go solo here.");
		stage =5;
	} else if (stage == 5){
		 sendPlayerDialogue(9827, "Okay I'll see what I can find.");
	stage =  8;
	} else if(stage == 8){
	player.getPackets().sendResetCamera();
   sendNPCDialogue(npcId, 9827,"Close your eyes, I'll teleport you away.");
		stage = 9;
	}
	else if(stage == 9){
	fade(player);
	tutorialTeleport(3502,3573,0);
	stage = 10;
	} else if (stage == 10){
		 sendPlayerDialogue(9827, "It's so foggy here.");
	stage =  100;
	player.ZariaQueststage = 5;
	}
	else if (stage == 100){
		player.unlock();
		end();
	}
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
