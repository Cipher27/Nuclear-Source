package com.rs.game.player.dialogues;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.cutscenes.Cutscene;
import com.rs.utils.Logger;



public class SpectatePanel extends Dialogue {
	
	
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
	
	/**
	  * Created by Paolo, just a handy thing so people can see if tthe boss room is empty.
	  *
	  **/
	

	@Override
	public void start() {
		    sendOptionsDialogue("SpectatePanel", "Bandos", "Zamorak", "Saradomin", "Armadyl","Nothing");
			stage = 1; 
	}
	
	public void spectateBandos(){
					player.getPackets().sendCameraPos(Cutscene.getX(player, 2866),Cutscene.getY(player, 5354), 11000);
		       player.getPackets().sendCameraLook(Cutscene.getX(player, 2869),Cutscene.getY(player, 5358), 1000);  
	}
	public void spectateZamorak(){
	       player.getPackets().sendCameraPos(Cutscene.getX(player, 2922),Cutscene.getY(player, 5314), 11000);
	       player.getPackets().sendCameraLook(Cutscene.getX(player, 2926),Cutscene.getY(player, 5324), 1000);  
}
	public void spectateArmadyl(){
	       player.getPackets().sendCameraPos(Cutscene.getX(player, 2819),Cutscene.getY(player, 5292), 11000);
	       player.getPackets().sendCameraLook(Cutscene.getX(player, 2829),Cutscene.getY(player, 5302), 1000);  
}
	public void spectateSaradomin(){
	       player.getPackets().sendCameraPos(Cutscene.getX(player, 2932),Cutscene.getY(player, 5254), 11000);
	       player.getPackets().sendCameraLook(Cutscene.getX(player, 2923),Cutscene.getY(player, 5251), 1000);  
}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		if(componentId == OPTION_1){
			 player.lock();
		fade(player);
		sendDialogue("<col=0000ff>Loading specate.</col>", "");
		tutorialTeleport(2862, 5358, 0);
			stage = 2;
		}
		if(componentId == OPTION_2){
			 player.lock();
			fade(player);
			sendDialogue("<col=0000ff>Loading specate.</col>", "");
			tutorialTeleport(2920, 5314, 0); 
		    stage = 3;
		}
	
		if(componentId == OPTION_3){
		 player.lock();
			fade(player);
			sendDialogue("<col=0000ff>Loading specate.</col>", "");
			tutorialTeleport(2914, 5254, 0); 
		    stage = 4; 
		   
		}
		
		if(componentId == OPTION_4) {
			 player.lock();
			fade(player);
			sendDialogue("<col=0000ff>Loading specate.</col>", "");
			tutorialTeleport(2842, 5310, 0); 
		    stage = 5;
		}
		
		else if (componentId == OPTION_5) {
			end();	
			
			}
		}
		
		else if (stage == 2){
			sendDialogue("<col=0000ff>Stop spectating.</col>", "");
			 spectateBandos();
			 stage = 21;
		}

		else if (stage == 3) {
			sendDialogue("<col=0000ff>Stop spectating.</col>", "");
			 spectateZamorak();
			 stage = 21;
		} else if (stage == 4) {
			sendDialogue("<col=0000ff>Stop spectating.</col>", "");
			 spectateArmadyl();
			 stage = 21;	
		}

		else if (stage == 5) {
		sendDialogue("<col=0000ff>Stop spectating.</col>", "");
			 spectateSaradomin();
			 stage = 21;	
		}

		
		else if (stage == 6) {
	
		}
	
	
		else if (stage == 10){
		
		}

		else if (stage == 11) {
		
		
		}
		/**
		  * end 
		  **/
		else if (stage == 20) {
		sendDialogue("<col=0000ff>Press next, if you want to stop spectating.</col>", "");
		stage = 21;
		}else if (stage == 21) {
		fade(player);
		tutorialTeleport(2348, 3693, 0); 
		player.unlock();
		player.getPackets().sendResetCamera();
		end();
		}
		 
		 
}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	} 
		


	

}