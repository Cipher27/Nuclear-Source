package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.content.PlayerLook;
import com.rs.utils.Logger;
/**
 * 
 * @author Paolo
 *
 */

public class NewPlayerTutorial extends Dialogue {
	
	/**
	 * Starts The Tutorial.
	 */
	
	@Override
	public void start() {
		player.lock();
		sendNPCDialogue(
						7892,
						9827,
						"Welcome to "+Settings.SERVER_NAME+", " + player.getUsername() + ". I'm glad that you joined my server, would you like to get an introduction ?");
		stage = 2;  
		
	}
	
	/**
	 * Second Stage of Tutorial.
	 */

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 2) {
		 sendOptionsDialogue("Would you like to have a introduction to the server?","Yes .","No.");
		 stage = 3;
		}
		
		/**
		 * Third Stage of Tutorial.
		 */
		else if (stage == 3) {
		if(componentId == OPTION_1){
		player.getDialogueManager().startDialogue("HomeTour");
		}else if(componentId == OPTION_2){
		player.getHintIconsManager().removeUnsavedHintIcon();
		fade(player);
		player.unlock();
		PlayerLook.openCharacterCustomizing(player);
		//Trial.sendActivation(player);
		end();
		  }
		}
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
