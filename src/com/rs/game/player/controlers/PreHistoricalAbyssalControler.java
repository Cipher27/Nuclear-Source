package com.rs.game.player.controlers;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class PreHistoricalAbyssalControler extends Controler {

	



	@Override
	public void start() {

	}

	

	/**
	 * return process normaly
	 */
	@Override
	public boolean processObjectClick1(WorldObject object) {
		return true;
	}


	/*
	 * return false so wont remove script
	 */
	@Override
	public boolean login() {
		return false;
	}


	@Override
	public boolean sendDeath() {
		player.lock(7);
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.getPackets().sendGameMessage("You have been defeated!");
				} else if (loop == 3) {
					player.reset();
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}

	/*
	 * return false so wont remove script
	 */
	@Override
	public boolean logout() {
		return false;
		
	}
    @Override
    public boolean processMagicTeleport(WorldTile toTile) {
	//World.playerAtPre = false;
	return true;
    }

    @Override
	public boolean processItemTeleport(WorldTile toTile) {
	//World.playerAtPre = false;
	return true;
    }

    @Override
	public boolean processObjectTeleport(WorldTile toTile) {
    //World.playerAtPre = false;
	return true;
	}

}
