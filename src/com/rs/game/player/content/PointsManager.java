package com.rs.game.player.content;


import java.io.Serializable;

import com.rs.game.player.Player;


/**
 * @paolo
 */
public final class PointsManager implements Serializable {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 8523500738169428603L;
	/*
	 * tokens
	 */
	private int voteTokens;
	private int riddelTokens;
	private int totalRiddles;
	private int donatorTokens;
	private int dungeoneeringTokens;
	private int wildernessTokens;
	private int powerTokens;
	private int perkPoints;
	private int slayerPoints;
	private int loyaltyTokens;
	public int runespanStore;
	private int lividPoints;
	private int achievementPoints;
	
	//total
	public long totalPowerTokens;
	//random stuff xd
	public int springs = 100;
	/**
	 * The player.
	 */
	private transient Player player;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void sendInterface(){
		player.getInterfaceManager().sendInterface(3038);
		player.getPackets().sendIComponentSprite(3038,8,27247);
		  player.getPackets().sendIComponentText(3038,1, ""+format(dungeoneeringTokens));		 
		  player.getPackets().sendIComponentText(3038,2, ""+format(riddelTokens));		 
		  player.getPackets().sendIComponentText(3038,3, ""+format(getLoyaltyTokens()));
		  player.getPackets().sendIComponentText(3038,4, ""+format(perkPoints));
		  player.getPackets().sendIComponentText(3038,5, ""+format(powerTokens));		
		  player.getPackets().sendIComponentText(3038,6, ""+format(wildernessTokens));		
		  player.getPackets().sendIComponentText(3038,7, ""+format(slayerPoints));		
		  player.getPackets().sendIComponentText(3038,9, ""+format(voteTokens));		  
	}
	
	public String format(int value){
		if (value < 100)
			return "  "+value;
		else if (value > 100 && value < 1000)
			return " "+value;
		return ""+value;
	}

	/**
	 * @return the voteTokens
	 */
	public int getVoteTokens() {
		return voteTokens;
	}

	/**
	 * @param voteTokens the voteTokens to set
	 */
	public void setVoteTokens(int voteTokens) {
		this.voteTokens = voteTokens;
	}

	/**
	 * @return the riddelTokens
	 */
	public int getRiddelTokens() {
		return riddelTokens;
	}
    
	public void addTotelRiddles(){
	   totalRiddles++;
	}
	public int getTotalRiddles(){
		return totalRiddles;
	}
	
	/**
	 * @param riddelTokens the riddelTokens to set
	 */
	public void setRiddelTokens(int riddelTokens) {
		this.riddelTokens = riddelTokens;
	}

	/**
	 * @return the donatorTokens
	 */
	public int getDonatorTokens() {
		return donatorTokens;
	}

	/**
	 * @param donatorTokens the donatorTokens to set
	 */
	public void setDonatorTokens(int donatorTokens) {
		this.donatorTokens = donatorTokens;
	}

	/**
	 * @return the dungeoneeringTokens
	 */
	public int getDungeoneeringTokens() {
		return dungeoneeringTokens;
	}

	/**
	 * @param dungeoneeringTokens the dungeoneeringTokens to set
	 */
	public void setDungeoneeringTokens(int dungeoneeringTokens) {
		this.dungeoneeringTokens = dungeoneeringTokens;
	}

	/**
	 * @return the wildernessTokens
	 */
	public int getWildernessTokens() {
		return wildernessTokens;
	}

	/**
	 * @param wildernessTokens the wildernessTokens to set
	 */
	public void setWildernessTokens(int wildernessTokens) {
		this.wildernessTokens = wildernessTokens;
	}

	/**
	 * @return the powerTokens
	 */
	public int getPowerTokens() {
		return powerTokens;
	}

	/**
	 * @param powerTokens the powerTokens to set
	 */
	public void setPowerTokens(int powerTokens) {
		int total = (int) (totalPowerTokens + powerTokens);
		totalPowerTokens = total;
		this.powerTokens = powerTokens;
	}

	/**
	 * @return the perkPoints
	 */
	public int getPerkPoints() {
		return perkPoints;
	}

	/**
	 * @param perkPoints the perkPoints to set
	 */
	public void setPerkPoints(int perkPoints) {
		this.perkPoints = perkPoints;
	}

	/**
	 * @return the slayerPoints
	 */
	public int getSlayerPoints() {
		return slayerPoints;
	}

	/**
	 * @param slayerPoints the slayerPoints to set
	 */
	public void setSlayerPoints(int slayerPoints) {
		this.slayerPoints = slayerPoints;
	}

	public int getLoyaltyTokens() {
		return loyaltyTokens;
	}

	public void setLoyaltyTokens(int loyaltyTokens) {
		this.loyaltyTokens = loyaltyTokens;
	}

	/**
	 * @return the lividPoints
	 */
	public int getLividPoints() {
		return lividPoints;
	}

	/**
	 * @param lividPoints the lividPoints to set
	 */
	public void setLividPoints(int lividPoints) {
		this.lividPoints = lividPoints;
	}
	
	public void addLividPoint() {
		this.lividPoints++;
	}
	public void addLividPoints( int amount) {
		this.lividPoints+= amount;
	}
	
	public void removeLividPoint(int amount) {
		this.lividPoints-= amount;
	}

	/**
	 * @return the achievementPoints
	 */
	public int getAchievementPoints() {
		return achievementPoints;
	}

	/**
	 * @param achievementPoints the achievementPoints to set
	 */
	public void setAchievementPoints(int achievementPoints) {
		this.achievementPoints = achievementPoints;
	}
	
}