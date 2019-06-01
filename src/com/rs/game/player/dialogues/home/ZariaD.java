package com.rs.game.player.dialogues.home;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;
import com.rs.utils.ShopsHandler;

public class ZariaD extends Dialogue {
private int npcId = 14392;
	
	@Override
	public void start() {
		if(player.ZariaQueststage == 3 ){
		player.getDialogueManager().startDialogue("ZariaIsland");
	} else if(player.ZariaQueststage == 1){
		player.getDialogueManager().startDialogue("PortalFound");
	} else if(player.ZariaQueststage == 5){
		fade(player);
		tutorialTeleport(3502,3573,0);
	
	}  else if(player.ZariaQueststage == 6){
		player.getDialogueManager().startDialogue("EndQuest");
	} else if(player.ZariaQueststage == 4){
		player.getDialogueManager().startDialogue("ZariaPlace");
	} else if(player.ZariaQueststage == 2){
		sendNPCDialogue(npcId, 9827,"There must be something on this island that can help us fixing this portal.");
		stage = 100;
	}else {
		sendNPCDialogue(npcId, 9827,"Welcome back "+player.getDisplayName()+", what can I do for you?");
	stage = 0;
	}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
	if(stage == 0){
		
	sendOptionsDialogue("Pick an option",
					"I would like to help defend the colony.",
					"Tell me your story again.",
					"Server information.",
					"Common asked questions.", 
					"Open power token store");
		stage = 1;
	} else if (stage == 1){
		if(componentId == OPTION_1) {
			if(player.ZariaQueststage ==0)
			player.getDialogueManager().startDialogue("StartQuest");
		else if(player.ZariaQueststage ==7){
			sendNPCDialogue(npcId, 9827,"We have to wait until I find more information, but thanks anyway.");
			stage = 100;
		}
		else
			end();
		} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827,"We are in war with the dark soldiers, I shall do everything to protect my colony. We need to find their location.");
			stage = 100;
		} else if(componentId == OPTION_3) {
			sendOptionsDialogue("Pick an option",
					"Server events",
					"About the server");
		stage = 2;
		}else if(componentId == OPTION_4) {
		player.getDialogueManager().startDialogue("CommonQuestions");
		}
		else if(componentId == OPTION_5) {
		ShopsHandler.openShop(player, 152);
		end();
		}
	} else if(stage == 2){
		if(componentId == OPTION_1) {
			sendNPCDialogue(npcId, 9827,"Every 2nd weekend there's double exp on saturday, also during the week are there special events. Check the forum for more information.");
	stage = 100;
		} else{
				sendNPCDialogue(npcId, 9827,"The original name of the server was Thatscape which was also hosted (in 2014), now the server is back with more content than ever before.");
	stage = 100;
		}
	}
	else if(stage == 20){
		player.getDialogueManager().startDialogue("StartQuest");
	}
	else if (stage == 100)
		end();
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

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	
}
