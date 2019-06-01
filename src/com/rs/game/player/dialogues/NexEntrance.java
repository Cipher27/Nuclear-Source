package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.minigames.ZarosGodwars;
import com.rs.game.player.cutscenes.Cutscene;

public final class NexEntrance extends Dialogue {

	@Override
	public void start() {
		sendDialogue(
				"The room beyond this point is a prison!",
				"There is no way out other than death or teleport.",
				"Only those who endure dangerous encounters should proceed.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue(
					"There are currently " + ZarosGodwars.getPlayersCount()
							+ " people fighting",
					"Enter Prison.","Specate", "Stay here.");
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				player.setNextWorldTile(new WorldTile(2911, 5203, 0));
				player.getControlerManager().startControler("ZGDControler");
			} else if (componentId == OPTION_2) {
				sendDialogue("Make sure you are completely zoomed out, click continou to leave spectate.");
				player.getPackets().sendCameraLook(Cutscene.getX(player, 2925),Cutscene.getY(player, 5202), -3500);
				player.getPackets().sendCameraPos(Cutscene.getX(player, 2911),Cutscene.getY(player, 5203), 8000);
			    stage = 1;
			} else 
			end();
		} else if (stage == 1){
			 player.getPackets().sendResetCamera();
			 end();
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
