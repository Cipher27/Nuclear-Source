package com.rs.game.player.content.rots;

import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.game.player.Player;
/**
 * rise of the six party, handles joining removing & starting rots
 * @author paolo
 *
 */
public final class RotsParty {

	private String leader; // username
	private RiseOfTheSix rots;
	private int MAX_PLAYERS = 4;

	private CopyOnWriteArrayList<Player> team;

	public RotsParty() {
		team = new CopyOnWriteArrayList<Player>();
	}

	public void leaveParty(Player player, boolean logout) {
		if (rots != null){
     //TODO remove if they are ingame
		}else {
			player.setForceMultiArea(false);
			player.stopAll();
			remove(player);
		}
	}
    /**
     * removes the player from the party
     * TODO destroy instance if group size is 0 after leaving
     * @param player
     * @param logout
     */
	public void remove(Player player) {
		synchronized (this) {
			team.remove(player);
			player.getPackets().sendGameMessage("You left the party.");
				for (Player p2 : team) //Everyone in the team gets the message
					p2.getPackets().sendGameMessage(player.getDisplayName() + " has left the party.");
				if (isLeader(player) && team.size() > 0) //if the leaders leaves assign new leader
					setLeader(team.get(0));
		}
	}

    /**
     * adds a player to the party
     * @param player
     */
	public void add(Player player) {
		if(team.size() == MAX_PLAYERS){
			player.sm("This party if full.");
			return;
		}
		synchronized (this) {
			for (Player p2 : team)
				p2.getPackets().sendGameMessage(player.getDisplayName() + " has joined the party.");
			team.add(player);
			if (team.size() == 1) {
				setLeader(player);
			} else
				player.getPackets().sendGameMessage("You joined the party.");
		}
	}
   /**
    * checks if given player is leader
    * @param player
    * @return
    */
	public boolean isLeader(Player player) {
		return player.getUsername().equals(leader);
	}
    /**
     * sets the leader of the party.
     * @param player
     */
	public void setLeader(Player player) {
		leader = player.getUsername();
		if (team.size() > 1) {
			Player positionZero = team.get(0);
			team.set(0, player);
			team.remove(player);
			team.add(positionZero);
		}
		player.getPackets().sendGameMessage("You have been set as the party leader.");
	}
    /**
     * locks the party
     */
	public void lockParty() {
		for (Player player : team) {
			player.stopAll();
			player.lock();
		}
	}
    /**
     * starts rotss
     */
	public void start() {
		synchronized (this) {
			//TODO start instance
		}
	}
    /**
     * returns leaders name
     * @return
     */
	public String getLeader() {
		return leader;
	}
    /**
     * returns the leader as player
     * @return
     */
	public Player getLeaderPlayer() {
		for (Player player : team)
			if (player.getUsername().equals(leader))
				return player;
		return null;
	}

    /**
     * returns all the players
     * @return
     */
	public CopyOnWriteArrayList<Player> getTeam() {
		return team;
	}
    /**
     * gets the index of the player
     * @param player
     * @return
     */
	public int getIndex(Player player) {
		int index = 0;
		for (Player p2 : team) {
			if (p2 == player)
				return index;
			index++;
		}
		return 0;
	}
}
