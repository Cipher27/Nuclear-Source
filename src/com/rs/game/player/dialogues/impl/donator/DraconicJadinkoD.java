package com.rs.game.player.dialogues.impl.donator;

import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;


public class DraconicJadinkoD extends Dialogue {


	@Override
	public void start() {
		if(player.isDonator()){
			sendNPCDialogue(3122,NORMAL,"Have funn hunting them.");
			player.setNextWorldTile(new WorldTile(2978,2906,0));
			stage = 100;
		} else {
			sendNPCDialogue(3122,NORMAL,"I'm sorry but that island is only for Donators.");
			stage = 100;
		}
	}

	@Override
	public void run(int interfaceId, int option) {
		if(stage == 100)
			end();
	}
	

	@Override
	public void finish() {

	}

}
