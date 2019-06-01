package com.rs.game.player.dialogues.home;

import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
//import com.rs.game.player.content.Trial;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;

public class ServerIntro2 extends Dialogue {
private int npcId = 18495;
	
	@Override
	public void start() {
		player.getHintIconsManager().removeUnsavedHintIcon();
		fade(player);
		stage = 1;
		sendNPCDialogue(npcId, 9827,"How dare you entering my castle. Who are you?");
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
	sendNPCDialogue(npcId, 9827,"How dare you entering my castle. Who are you?");
	stage = 1;	
	} else if(stage == 1){
	sendPlayerDialogue(9827,"My name is "+player.getDisplayName()+" and I'm here for that little girl.");	
	stage = 2;	
	} else if(stage == 2){
	sendNPCDialogue(npcId, 9827,"Hahahaha it's one of these humans again who thinks that he can fight us.");
	stage = 3;	
	}else if(stage == 3){
	sendPlayerDialogue(9827, "I'll ask you one more time, give me that girl.");	
	stage = 4;	
	} else if(stage == 4){
	sendNPCDialogue(npcId, 9827,"If you want the girl you've to go throu my soldier first.");
	World.spawnNPC(18496, new WorldTile(2912,3942, 0), -1, true);//ranger
	player.getHintIconsManager().addHintIcon(2912, 3942, 0, 100, 0, 0,-1, false);
	stage = 5;	
	}else if (stage == 5)
	{
		player.starter = 3;
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
					FadingScreen.unfade(player, time, new Runnable() {
						@Override
						public void run() {
							tutorialTeleport(2915,3941,0);
				}
					});
			} catch (Throwable e) {
				Logger.handle(e);
			}
			}
		
	});
	}
	
	
	
	
	
	
	
	
	
	
}
