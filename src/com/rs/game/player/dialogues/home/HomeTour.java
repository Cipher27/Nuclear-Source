package com.rs.game.player.dialogues.home;

import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Starter;
import com.rs.game.player.content.FadingScreen;
//import com.rs.game.player.content.Trial;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;

public class HomeTour extends Dialogue {
private int npcId = 14392;
	
	@Override
	public void start() {
		sendOptionsDialogue("Would you like a tour at the colony?", "Yes I would love to see the colony", "No thank you, I'll figure it out by myself");
	stage = -1;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
if(stage == -1){
	if(componentId == OPTION_1) {
		player.lock();
		Starter.appendStarter(player);
		player.setRun(true);
		sendNPCDialogue(npcId, 9827,"I'm glad you choose for the tour!");	
		stage = 0;
	} else {
		player.unlock();
		Starter.appendStarter(player);
		end();
	}
		
}
	if (stage == 0) {
		fade(player);
		tutorialTeleport(2326,3672,0);
	sendNPCDialogue(npcId, 9827, "This is our lovely home, where you can find almost every thing you need to continue your adventure.");	
	stage = 1;									 						
	} else if (stage == 1) {
	WorldTasksManager.schedule(new WorldTask() {
								int loop;
								@Override
								public void run() {
									if (loop == 0) {
										player.getInterfaceManager().closeChatBoxInterface();
                                     player.addWalkSteps(2326,3675, 0, false);
									} else if (loop == 1) {
										player.addWalkSteps(2337, 3675, 0, false);
										sendNPCDialogueNoContinue(player,npcId, 9827, "In the building in front of you, you'll find every shop you need.");	
									} else if (loop == 8) {
										closeNoContinueDialogue(player);
										sendNPCDialogue(npcId, 9827, "Let's continue to the next building.");	
										stage = 2;									 
									} 
									loop++;
								}
							}, 0, 1);
	
	}else if (stage == 2) {
		player.getInterfaceManager().closeChatBoxInterface();
		WorldTasksManager.schedule(new WorldTask() {
								int loop;
								@Override
								public void run() {
									if (loop == 0) {	
										
                                     player.addWalkSteps(2336,3689, 0, false);
									} else if (loop == 3) {
										player.addWalkSteps(2330, 3689, 0, false);
										sendNPCDialogueNoContinue(player,npcId, 9827, "In here you find our bank where you can store your items.");	
									} else if (loop == 8) {
									// fade(player);
									sendNPCDialogue(npcId, 9827, "You also find the crystal chest, the player manager and bob the decanter here.");	
								    stage = 3;									 
									} 
									loop++;
								}
							}, 0, 1);
	}else if (stage == 3) {
		closeNoContinueDialogue(player);
		player.getInterfaceManager().closeChatBoxInterface();
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {	
					sendNPCDialogueNoContinue(player,npcId, 9827, "Let's walk further.");	
                 player.addWalkSteps(2336,3689, 0, false);
				} else if (loop == 2) {
					player.addWalkSteps(2341, 3699, 0, false);
					//sendNPCDialogueNoContinue(player,npcId, 9827, "");	
				} else if (loop == 6) {
				// fade(player);
				sendNPCDialogue(npcId, 9827, "The portal in front of you can be used to teleport around the world.");	
			    stage = 4;									 
				} 
				loop++;
			}
		}, 0, 1);
		  }
	//portal
    else if (stage == 4) {
    	closeNoContinueDialogue(player);
		player.getInterfaceManager().closeChatBoxInterface();
    	WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {	
                 player.addWalkSteps(2350,3682, 0, false);
				} else if (loop == 5) {
					sendNPCDialogueNoContinue(player, npcId, 9827, "This cape stand is used to hold the most powerful cape of our Colony, come back later to check their requirements.");	
				} else if (loop == 9) {
				// fade(player);
				sendNPCDialogue(npcId, 9827, "The building behind the stand is for the people that own a max cape, it's called the max guild. Later you'll see what's inside.");	
			    stage =6;									 
				} 
				loop++;
			}
		}, 0, 1);
	}
	//shops
	//end
   else if (stage == 6) {
	   closeNoContinueDialogue(player);
		player.getInterfaceManager().closeChatBoxInterface();
	   fade(player);
	  tutorialTeleport(2341,3683, 0); 
     
	 sendNPCDialogue(npcId, 9827,"Ofcourse there's a lot more, but I don't want to spoil everything. One more thing, while skilling and monster slaying you can earn power tokens. ");	
	 stage = 7;
		  }
	  else if (stage == 7) {
	 sendPlayerDialogue(9827,"Ow that's nice to know thanks.");	
	 stage = 8;
		  }
	
		  
		  
	//end ;)
	else if (stage ==8 ) {
	 sendNPCDialogue(npcId, 9827,"Those tokens can be very useful, right click your money pouch and click the currency pouch to see your tokens.");	
	 stage = 20;
		  }  
	else if (stage == 20) {
sendPlayerDialogue(9827,"Okay I'll keep that in mind, and thanks for the introduction!");	
		 stage = 21;
		  } 
	else if (stage == 21) {
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
