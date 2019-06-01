package com.rs.game.player.dialogues.home;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Starter;
import com.rs.game.player.content.FadingScreen;
//import com.rs.game.player.content.Trial;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;

public class ServerIntro3 extends Dialogue {
private int npcId = 14392;//18495;
	
	@Override
	public void start() {
	sendNPCDialogue(18495, 9827,"NOOOOOOOOO, my soldier! How did you kill him with one hit? You'll see me again "+player.getDisplayName()+". I will revange your race.");
	stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
	sendPlayerDialogue(9827,"Quick little girl, I'll bring you home!");	
	player.addWalkSteps(2911, 3942, 0, false);
	stage = 1;	
	} else if(stage == 1){
	fade(player);
	stage = 2;
	} else if(stage == 2){
	sendNPCDialogue(npcId, 9827,"Thank you for bringing my daughter back to me sir, what is your name if I may ask?");
	stage = 3;	
	}else if(stage == 3){
	sendPlayerDialogue(9827, ""+player.getDisplayName()+" but where am I and who are you?");	
	stage = 4;	
	} else if(stage == 4){
	sendNPCDialogue(npcId, 9827,"I'm Hellion, the major of this colony. We are the last human survivers of the dark war, we would be pleased if you would join us and help against the dark army.");
	stage = 5;	
	}else if(stage == 5){
		sendPlayerDialogue(9827, "I'll think about it.");	
		stage = 6;	
	}else if(stage == 6){
		sendNPCDialogue(npcId, 9827,"If you ever need anything , I'm here. Let me grab you some stuff.");
		stage = 7;	
		}else if (stage == 7){
			 Starter.appendStarter(player);
			 player.getInterfaceManager().sendInterface(3013);
			 player.getControlerManager().removeControlerWithoutCheck();
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
					FadingScreen.unfade(player, time, new Runnable() {
						@Override
						public void run() {
							tutorialTeleport(2330,3668,0);
				}
					});
			} catch (Throwable e) {
				Logger.handle(e);
			}
			}
		
	});
	}
	
	
	
	
	
	
	
	
	
	
}
