package com.rs.game.minigames.tournament;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class Match {
	
	private transient Tournament tournament;
	
	private transient Map map;
	
	private transient Player player1;
	private transient Player player2;
	
	private transient boolean started = false;
	
	private transient boolean ended = false;
	
	public void start() {
		map.createArena();
		reset(player1, false);
		reset(player2, false);
		map.teleportToArena(player1, false, tournament.isSpawnedEquipment(), tournament.getSetId(), tournament.getSwitchSetId(), this);
		map.teleportToArena(player2, true, tournament.isSpawnedEquipment(), tournament.getSetId(), tournament.getSwitchSetId(), this);
	}
	
	public boolean hasStarted() {
		return started;
	}
	
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public void end(WorldTile startTile, String loser, String loggedOut) {
		if (ended)
			return;
		ended = true;
		reset(player1, true);
		reset(player2, true);
		if (!loggedOut.equalsIgnoreCase(player1.getDisplayName()))
			map.teleportFromArena(player1, startTile);
		if (!loggedOut.equalsIgnoreCase(player2.getDisplayName()))
			map.teleportFromArena(player2, startTile);
		boolean loserIsOne = loser.equals(player1.getDisplayName());
		tournament.sendWinner(
				loserIsOne ? player2 : player1,
				loserIsOne ? player1 : player2,
				loserIsOne ? player2.getDisplayName() : player1.getDisplayName(), 
				loserIsOne ? player1.getDisplayName() : player2.getDisplayName());
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				map.destroyArena();
			}
		}, 2);
	}
	
	public boolean hasEnded() {
		return ended;
	}
	
	public void reset(Player player, boolean end) {
		if (end) {
			if (tournament.isSpawnedEquipment()) {
				player.getInventory().reset();
				player.getEquipment().reset();
			}
		}
		player.reset();
	}
	
	public Player getOtherPlayer(boolean second) {
		if (second)
			return player1;
		return player2;
	}
	
	public Player getOtherPlayer(Player player) {
		if (player.getDisplayName().equals(player2.getDisplayName()))
			return player1;
		return player2;
	}
	
	public Map getMap() {
		return map;
	}
	
	public Match(Player player1, Player player2, Tournament tournament) {
		this.player1 = player1;
		this.player2 = player2;
		this.tournament = tournament;
		map = new Map();
		start();
	}
}