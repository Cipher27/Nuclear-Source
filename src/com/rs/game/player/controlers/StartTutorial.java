package com.rs.game.player.controlers;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

public class StartTutorial extends Controler {

	@Override
	public void start() {
		player.setYellOff(true);
		player.unlock();
		player.setRun(true);
		player.getDialogueManager().startDialogue("ServerIntro");
		player.setNextWorldTile(new WorldTile(2294,3618,0));
		player.getPackets().sendMusicEffect(13);
	}

	@Override
	public void process() {
	
	}

	@Override
	public boolean processObjectClick1(WorldObject object) {
		int id = object.getId();
	if (id == 41900){
			if (player.starter == 2)
			player.getDialogueManager().startDialogue("ServerIntro2");
			else 
				player.getDialogueManager().startDialogue("ServerIntro");
			return true;
		}
		return false;
	}

	@Override
	public boolean processObjectClick2(WorldObject object) {
		return false;
	}

	@Override
	public boolean processObjectClick3(WorldObject object) {
		return false;
	}

	@Override
	public boolean processNPCClick1(NPC npc) {
	     if(npc.getId() == 6334){
			 player.getDialogueManager().startDialogue("ServerIntro");
			 return true;
		 }
		 
		return false;
	}


	/*
	 * return remove controler
	 */
	@Override
	public boolean login() {
		start();
		return false;
	}

	/*
	 * return remove controler
	 */
	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		return false;
	}


	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		return false;
	}
}
