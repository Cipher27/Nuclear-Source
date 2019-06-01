package com.rs.game.player.content;

import com.rs.game.player.Player;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

/**
 * reputation system
 * @author paolo
 *
 */
public class Reputation {
	
	/**
	 * player stuff
	 */
	private Player player;
	
	public void setPlayer(Player p){
		this.player = p;
	}
	/**
	 * points
	 */
	private int reputationPoints;
	private int reputationGiven;
	
	private long lastRepGiven;
	
	private String[] reasons = {
		"A player helped you",
		"Gave me free items",
		"Was realy nice to me.",
		""
	};
	
	/*
	 *  send all the possible reasons
	 */
	public void sendReasons(){
		for(String r : reasons)
			player.sm(r);
	}
	/**
	 * checks if the player can get reputation
	 * @param giver player giving reputation
	 * TODO check for cooldown
	 */
	public boolean canGiveReputation(Player giver){
		if(player.getSession().getIP() == giver.getSession().getIP()){ //same  ip
			player.sm("You can't give yourself reputation");
			return false;
		}
		if(giver.getSkills().getTotalLevel() < 500){
			giver.sm("You need a total level higher than 500 to give someone reputation.");
			return false;
		}
		if(player == giver){ //same player (can't happen but to be sure)
			player.sm("You can't give yourself reputation");
			return false;
		}
		//if(getLastRep() >)
		return true;
	}
	/**
	 * gives the reputation
	 */
	public void giveReputation(Player giver){
		if(!canGiveReputation(giver)) //checks
				return;
		player.getRep().reputationPoints++;
		giver.getRep().reputationGiven++;
		giver.getRep().setLastRepGiven(Utils.currentTimeMillis());
	}
	/**
	 * logs the reps given
	 * @param target
	 */
	public void log(Player target){
		Logger.logMessage("Player: " +target+ " gave Player: "+player+ "an reputation comment.");
	}
	/**
	 * @return the reputationPoints
	 */
	public int getReputationPoints() {
		return reputationPoints;
	}
	/**
	 * @param reputationPoints the reputationPoints to set
	 */
	public void setReputationPoints(int reputationPoints) {
		this.reputationPoints = reputationPoints;
	}
	/**
	 * @return the reputationGiven
	 */
	public int getReputationGiven() {
		return reputationGiven;
	}
	/**
	 * @param reputationGiven the reputationGiven to set
	 */
	public void setReputationGiven(int reputationGiven) {
		this.reputationGiven = reputationGiven;
	}
	/**
	 * @return the lastRepGiven
	 */
	private long getLastRepGiven() {
		return lastRepGiven;
	}
	/**
	 * @param lastRepGiven the lastRepGiven to set
	 */
	private void setLastRepGiven(long lastRepGiven) {
		this.lastRepGiven = lastRepGiven;
	}

}
