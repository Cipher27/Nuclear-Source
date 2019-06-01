package com.rs.game.player.dialogues.impl.donator;

import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;

public class DivineDonorzoneD extends Dialogue {
	
	@Override
	public void start() {
		if(player.isDivineDonator()){
			sendNPCDialogue(5987,NORMAL,"Welcome to the floating donator island.");
			player.setNextWorldTile(new WorldTile(2140,5524,3));
			stage = 100;
		} else {
			sendNPCDialogue(5987,NORMAL,"I'm sorry but that island is only for Divine Donators, Ultimate Donators and Sponsors.");
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
		// TODO Auto-generated method stub
		
	}
	
}
