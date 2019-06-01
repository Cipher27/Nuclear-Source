package com.rs.game.player.dialogues.home;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
//import com.rs.game.player.content.Trial;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;

public class ServerIntro extends Dialogue {
private int npcId = 6334;//14392;
	
	@Override
	public void start() {
		stage = 0;
		player.lock();
		sendOptionsDialogue("Do you have a blackscreen ?",
					"Yes",
					"No.");
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
	sendNPCDialogue(npcId, 9827,"Please someone help me!!");
	stage = 1;	
	} else if(stage == 1){
	sendPlayerDialogue(9827,"What's up little man?");	
	stage = 2;	
	} else if(stage == 2){
	sendNPCDialogue(npcId, 9827,"They took my sister, please sir get her back!");
	stage = 3;	
	}else if(stage == 3){
	sendPlayerDialogue(9827,"Who's they?");	
	stage = 4;	
	} else if(stage == 4){
	sendNPCDialogue(npcId, 9827,"The dark soldiers!");
	stage = 5;	
	}else if(stage == 5){
	sendPlayerDialogue(9827,"What are they exactly ?");	
	stage = 6;	
	} else if(stage == 6){
	sendNPCDialogue(npcId, 9827,"They are the rulers of many worlds including ours, we tried fighting them but with no luck.");
	stage = 7;	
	} else if(stage == 7){
	sendNPCDialogue(npcId, 9827,"They took my sister to show us that we don't have to mess with them, but there's no more time to explain. Please can you get here?");
	stage = 8;	
	} else if(stage == 8){
	sendPlayerDialogue(9827,"I'll do my best, Where did they go ?");
	stage = 9;	
	} else if(stage == 9){
	sendNPCDialogue(npcId, 9827,"They went into this cave, thank you very much and goodluck sir.");
	stage = 10;	
	} else if (stage == 10){
		player.getHintIconsManager().addHintIcon(2293, 3627, 0, 100, 0, 0,-1, false);
		player.unlock();
		player.starter = 2;
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
