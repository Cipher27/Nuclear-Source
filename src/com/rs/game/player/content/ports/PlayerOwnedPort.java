package com.rs.game.player.content.ports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.content.ports.SpecialRewards.RECIPES;
import com.rs.game.player.content.ports.Voyages.Regions;
import com.rs.game.player.content.ports.Voyages.RewardTypes;
import com.rs.game.player.content.ports.Voyages.Voyage;
import com.rs.game.player.content.ports.buildings.PortUpgrades;
import com.rs.game.player.content.ports.buildings.PortUpgrades.Bar;
import com.rs.game.player.content.ports.buildings.PortUpgrades.Market;
import com.rs.game.player.content.ports.buildings.PortUpgrades.Office;
import com.rs.game.player.content.ports.buildings.PortUpgrades.Totems;
import com.rs.game.player.content.ports.buildings.PortUpgrades.workShop;
import com.rs.game.player.content.ports.crew.Captain;
import com.rs.game.player.content.ports.crew.Crew;
import com.rs.game.player.content.ports.npcs.Trader;
import com.rs.game.player.content.ports.ship.Ship;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Colors;
import com.rs.utils.Utils;

/**
 * Handles everything related to Player owned ports.
 * 
 * @author Paolo
 */
public class PlayerOwnedPort implements Serializable {

	/**
	 * The generated serial UID.
	 */
	private static final long serialVersionUID = 5060924202351034976L;
    /**
     * vars
     */
	private List<Voyage> completedVoyages;
	private List<Voyage> possibleVoyages;
	private List<Voyage> specialVoyages;
	private List<Crew> crewMemebers;
	private List<Crew> avaidableCrewMemebers;
	private List<Captain> captains;
	private List<Captain> AvaidableCaptains;
	private List<Crew> hireAbleCrew;
	private List<Ship> ships;
	private HashMap<Ship, Voyage> currentTrips;
	private int currentIndex = 0;
	private int regionIndex = 0;
	/**
	 * black trader
	 */
	private int dailyChrimes;
	private Item specialLoot;
	/**
	 * crew options
	 */
	private static final int MAX_CAPTAINS = 5;
	private static final int MAX_CREWMEMBERS = 25;
	/**
	 * ship options
	 */
	private static final int MAX_SHIPS = 5;
	/**
	 * constructor
	 */
	
	/**
	 * player stuff
	 */
	private transient Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}
    /**
     * daily rerolls
     */
	private int rerolls = 5;
	/**
	 * If the player is entering ports for the first time.
	 */
	public boolean firstTimer = true;

	
	/**
	 * Check if we meet the requirements to start PoP.
	 * 
	 * @return if we meet requirements.
	 * TODO no check atm
	 */
	public boolean meetRequirements() {
		return true;
	}

	/**
	 * Integers holding the resource amounts.
	 */
	public int plate, chiGlobe, lacquer, chime, spice,portScore;

	/**
	 * Booleans representing if the player has a ship.
	 */
	public boolean hasFirstShip, hasSecondShip, hasThirdShip, hasFourthShip, hasFifthShip;
	
	/**
	 * Longs representing ships voyage times.
	 */
	public long firstShipTime, secondShipTime, thirdShipTime, fourthShipTime, fifthShipTime;
	
	/**
	 * Integers representing ships voyage times that were set.
	 */
	public int firstShipVoyage, secondShipVoyage, thirdShipVoyage, fourthShipVoyage, fifthShipVoyage;
	
	/**
	 * Booleans representing if the player has claimed his rewards of voyage yet.
	 */
	public boolean firstShipReward, secondShipReward, thirdShipReward, fourthShipReward, fifthShipReward;
	
	/*
	 * upgrades
	 */
	private Totems totem = null;
	private workShop workshop = null;
	private Bar bar = null;
	private Office office = null;
	private Market market = null;
	/**
	 * recipes
	 */
    private RECIPES currentRecipe = null;
    private List<RECIPES> foundRecipes;
	/**
	 * current region
	 */
	private Regions currentRegion = null;
	/**
	 * constructor
	 */
	public PlayerOwnedPort(){
		setVoyages(new ArrayList<Voyage>());
		setShips(new ArrayList<Ship>());
		setCurrentTrips(new HashMap<Ship,Voyage>());
		setPossibleVoyages(new ArrayList<Voyage>());
		setCrewMemebers(new ArrayList<Crew>());
		setAvaidableCrewMemebers(new ArrayList<Crew>());
		setCaptains(new ArrayList<Captain>());
		setAvaidableCaptains(new ArrayList<Captain>());
		setHireAbleCrew(new ArrayList<Crew>());
		setFoundRecipes(new ArrayList<RECIPES>());
		setSpecialVoyages(new ArrayList<Voyage>());
		addFirstTrips();
		currentRegion = Regions.THE_ARC;
		ships.add(new Ship());
		addFirstCrew();
	}
	/**
	 * sets the first 5 trips
	 */
	private void addFirstTrips(){
		for(int i = 0; i <	4; i ++){
		possibleVoyages.add(Voyage.values()[currentIndex]);
		currentIndex++;
		}
	}
	/**
	 * adds starter crew
	 */
	public void addFirstCrew(){
		getAvaidableCrewMemebers().add(Regions.THE_ARC.possibleCrew[0]);
		getAvaidableCrewMemebers().add(Regions.THE_ARC.possibleCrew[1]);
		getAvaidableCrewMemebers().add(Regions.THE_ARC.possibleCrew[2]);
		getCrewMemebers().add(Regions.THE_ARC.possibleCrew[0]);
		getCrewMemebers().add(Regions.THE_ARC.possibleCrew[1]);
		getCrewMemebers().add(Regions.THE_ARC.possibleCrew[2]);
		getHireAbleCrew().add(generateRandomCrewMemeber());
		getHireAbleCrew().add(generateRandomCrewMemeber());
		getHireAbleCrew().add(generateRandomCrewMemeber());
		getHireAbleCrew().add(generateRandomCrewMemeber());
		getCaptains().add(generateRandomCaptain(50));
	}
	/**
	 * Enters the ports area.
	 */
	public void enterPorts() {
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				FadingScreen.fade(player, new Runnable() {

					@Override
					public void run() {
						player.unlock();
						player.getControlerManager().startControler("PlayerPortsController");
					}
				});
			}
		}, 0);
	}

	/**
	 * leaves the ports area.
	 */
	public void leavePorts() {
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				FadingScreen.fade(player, new Runnable() {

					@Override
					public void run() {
						player.unlock();
						player.getControlerManager().forceStop();
						player.setNextAnimation(new Animation(-1));
						player.setNextWorldTile(new WorldTile(2344,3691,0));
					}
				});
			}
		}, 0);
	}
	/**
	 * daily resets for port
	 */
	public void dailyReset(){
		setRerolls(5);
		Trader.generateDailyShop(player);
	}

	/**
	 * spawns the objects that the player has unlocked
	 */
	public void spawnObjects() {
		if(getTotem() != null)
			PortUpgrades.spawnTotem(totem, player);
		if(getWorkShop() != null)
			PortUpgrades.spawnWorkShop(workshop, player);
		
	}
	
	/**
	 * adds a ship
	 * @param ship
	 */
	public void addShip(Ship ship){
		if(ships.size() >= MAX_SHIPS){
			player.sm("You can only have 5 ships.");
			return;
		}
		ships.add(ship);
		
	}
	/**
	 * send the ship into their voyage
	 */
	public void sendShipVoyage(Ship ship, Voyage voyage){
		if(voyage.getLand() != getCurrentRegion() && voyage.getLand() != Regions.SPECIAL){
			player.sm("You have to complete all the missions of "+getCurrentRegion()+" first.");
			return;
		}
		if(currentTrips.containsKey(ship)){
			player.sm("This ship is on a trip atm.");
			return;
		}
		if(voyage.getLand() == Regions.SPECIAL)
			specialVoyages.remove(voyage);
		else
		possibleVoyages.remove(voyage);
		currentTrips.put(ship, voyage);
		addNewMission(voyage);
	}
	/**
	 * handles what happens when the ship arrives
	 * @param succes
	 * @param ship
	 */
	public void shipArrived(boolean succes,Ship ship){
		Voyage voy = currentTrips.get(ship);
		if(succes){ //only removes if the ship is succesfull, idk hwo it is in runescape thou
			//possibleVoyages.remove(voy);
			chime += voy.getChrimes();
			player.sm("Your ship has arrived and got you "+voy.getChrimes()+", you now have a total of:"+chime+" chrimes.");
			if(voy.getType() == RewardTypes.RECIPE)
				handelRecips();
			else if(voy.getType() == RewardTypes.TRADE_GOODS)
				handelTradeGoods(voy);
		}
		currentTrips.remove(ship);
	}
	/**
	 * adds the recipe to the found recipes
	 */
	public void handelRecips(){
		if(countRecipeParts(getCurrentRecipe()) == 4) //just in case
			return;
		if(countRecipeParts(getCurrentRecipe()) == 3){
			player.sm("You unlocked all 4 parts and can new use the wisedom.");
			setCurrentRecipe(null);
			getFoundRecipes().add(getCurrentRecipe());
			return;
		}
		getFoundRecipes().add(getCurrentRecipe());
		
	}
	/**
	 * gives the tradegoods
	 * @param voy
	 */
	public void handelTradeGoods(Voyage voy){
		if(voy == Voyage.CAN_I_EAT_IT)
			spice += 10 * (1+getCurrentRegion().ordinal());
		else if(voy == Voyage.TRANSMUTE_THE_SALVAGE)
			plate += 4 * (1+getCurrentRegion().ordinal());
		else if(voy == Voyage.A_ARCANE_BOUNTY)
			chiGlobe += 4 * (1+getCurrentRegion().ordinal());
		else if(voy == Voyage.A_JOINT_ACQUISITION)
			lacquer += 4* (1+getCurrentRegion().ordinal());
	}
	/**
	 * counts amount of parts the player already have
	 * @param recipe
	 * @return
	 */
	public int countRecipeParts(RECIPES recipe){
		int count = 0;
		for(RECIPES xd : getFoundRecipes()){
			if(xd == recipe)
				count++;
		}
		return count;
	}
	/**
	 * adds a new region and check if the next voyage is in the same voyage
	 * @param voy
	 */
	public void addNewMission(Voyage voy){
		if(Utils.random(10) == 0){ // 10 % on receiving a special voyage
			addSpecialVoyage();
			return;
		}
		if(voy.getLand()  == Voyage.values()[currentIndex++].getLand()){
		possibleVoyages.add(Voyage.values()[currentIndex++]);
		} else if (countMissionsInRegion(getCurrentRegion()) == Voyages.countRegionVoyages(getCurrentRegion()) ){
			nextRegion();
			addFirstTrips();
			player.sm("Congratz, you have unlocked a new Region to travel in.");
			
		}
		completedVoyages.add(voy);
	}
	/**
	 * adds a special voyage
	 * @param voy
	 */
	public void addSpecialVoyage(){
		int random = Utils.random(4);
		switch(random){
		case 0:
			possibleVoyages.add(Voyage.THE_FORGOTTEN_SCROLlS);	
			break;
		case 1:
			possibleVoyages.add(Voyage.CAN_I_EAT_IT);	
			break;
		case 2:
			possibleVoyages.add(Voyage.A_ARCANE_BOUNTY);	
			break;
		case 3:
			possibleVoyages.add(Voyage.A_JOINT_ACQUISITION);	
			break;
		case 4:
			possibleVoyages.add(Voyage.TRANSMUTE_THE_SALVAGE);	
			break;
		}
			
			
	}
	/**
	 * counts the amount of missions(voyages) in a certain region
	 * @param region
	 * @return
	 */
	public int countMissionsInRegion(Regions region){
		int count = 0;
		for(Voyage voy : completedVoyages){
			if(voy.getLand() == region)
				count++;
		}
		return count;
	}
	/**
	 * set next region + check
	 * @return
	 */
	public boolean nextRegion(){
		if(currentRegion == Regions.THE_SKULL)
			return false;
		currentRegion = Regions.values()[regionIndex++];
		return true;
	}
	
	/**
	 * Checks if ship Alpha has returned from a voyage yet.
	 * @return if the ship has returned.
	 */
	public boolean hasFirstShipReturned() {
		long timeVariation = Utils.currentTimeMillis() - firstShipTime;
		if (timeVariation < firstShipVoyage)
    		return false;
		return true;
	}
	
	/**
	 * Checks how much minutes left until voyage for ship Alpha ends.
	 * @return minutes as String.
	 */
	public String getFirstVoyageTimeLeft() {
		long toWait = firstShipVoyage - (Utils.currentTimeMillis() - firstShipTime);
		return Integer.toString(Utils.millisecsToMinutes(toWait));
	}
	
	/**
	 * Checks if ship Beta has returned from a voyage yet.
	 * @return if the ship has returned.
	 */
	public boolean hasSecondShipReturned() {
		long timeVariation = Utils.currentTimeMillis() - secondShipTime;
		if (timeVariation < secondShipVoyage)
    		return false;
		return true;
	}
	
	/**
	 * Checks how much minutes left until voyage for ship Beta ends.
	 * @return minutes as String.
	 */
	public String getSecondVoyageTimeLeft() {
		long toWait = secondShipVoyage - (Utils.currentTimeMillis() - secondShipTime);
		return Integer.toString(Utils.millisecsToMinutes(toWait));
	}
	
	/**
	 * Checks if ship Gamma has returned from a voyage yet.
	 * @return if the ship has returned.
	 */
	public boolean hasThirdShipReturned() {
		long timeVariation = Utils.currentTimeMillis() - thirdShipTime;
		if (timeVariation < thirdShipVoyage)
    		return false;
		return true;
	}
	
	/**
	 * Checks how much minutes left until voyage for ship Gamma ends.
	 * @return minutes as String.
	 */
	public String getThirdVoyageTimeLeft() {
		long toWait = thirdShipVoyage - (Utils.currentTimeMillis() - thirdShipTime);
		return Integer.toString(Utils.millisecsToMinutes(toWait));
	}
	
	/**
	 * Checks if ship Delta has returned from a voyage yet.
	 * @return if the ship has returned.
	 */
	public boolean hasFourthShipReturned() {
		long timeVariation = Utils.currentTimeMillis() - fourthShipTime;
		if (timeVariation < fourthShipVoyage)
    		return false;
		return true;
	}
	
	/**
	 * Checks how much minutes left until voyage for ship Delta ends.
	 * @return minutes as String.
	 */
	public String getFourthVoyageTimeLeft() {
		long toWait = fourthShipVoyage - (Utils.currentTimeMillis() - fourthShipTime);
		return Integer.toString(Utils.millisecsToMinutes(toWait));
	}
	
	/**
	 * Checks if ship Epsilon has returned from a voyage yet.
	 * @return if the ship has returned.
	 */
	public boolean hasFifthShipReturned() {
		long timeVariation = Utils.currentTimeMillis() - fifthShipTime;
		if (timeVariation < fifthShipVoyage)
    		return false;
		return true;
	}
	
	/**
	 * Checks how much minutes left until voyage for ship Epsilon ends.
	 * @return minutes as String.
	 */
	public String getFifthVoyageTimeLeft() {
		long toWait = fifthShipVoyage - (Utils.currentTimeMillis() - fifthShipTime);
		return Integer.toString(Utils.millisecsToMinutes(toWait));
	}
	/**
	 * Handles ship Alpha's returning.
	 * @return if returned successfully.
	 */
	public boolean handleFirstShipReward(Ship ship) {
		firstShipReward = true;
		firstShipVoyage = 0;
		Voyage voy = currentTrips.get(ship);
		if(Utils.random(100) <= calculateVoyageSucces(ship,voy)){
			shipArrived(true,ship);
			return true;
		}else 
			shipArrived(false,ship);
		return false;
	}
	/**
	 * 
	 * @param ship
	 * @param voy
	 * @return total moral %
	 */
	public double calcMoral(Ship ship, Voyage voy){
		if(voy.getMoral() == 0 || ship.getTotalMoral() == 0)
			return 0;
		if (((double)ship.getTotalMoral()/(double)voy.getMoral() *100d) > 100)
		return 100; 
		else 
		return ( (double)ship.getTotalMoral()/(double)voy.getMoral() *100d);
	}
	/**
	 * 
	 * @param ship
	 * @param voy
	 * @return total combat %
	 */
	public double calcCombat(Ship ship, Voyage voy){
		if(voy.getCombat() == 0 || ship.getTotalCombat() == 0){
			return 0;
		}
		if (((double)ship.getTotalCombat()/(double)voy.getCombat() *100d) > 100)
			return 100; 
			else 
		return ( (double)ship.getTotalCombat()/(double)voy.getCombat()  *100d);
	}
	/**
	 * 
	 * @param ship
	 * @param voy
	 * @return total seafarin %
	 */
	public double calcSeafaring(Ship ship, Voyage voy){
		if(voy.getSeafaring() == 0 || ship.getTotalSeafaring() == 0)
			return 0;
		if (((double)ship.getTotalCombat()/(double)voy.getSeafaring() *100d) > 100)
			return 100; 
			else 
		return ((double)ship.getTotalSeafaring()/(double)voy.getSeafaring()  *100d);
	}
	/**
	 * 
	 * @param ship
	 * @param voyage
	 * @return the total succes rate of the ship
	 */
	public int calculateVoyageSucces(Ship ship,Voyage voyage){
		if(ship.getTotalCombat() == 0 && ship.getTotalMoral() == 0 && ship.getTotalSeafaring() == 0)
			return 0;
		int count = 3;
		if(voyage.getMoral() == 0)
			count--;
		if(voyage.getCombat() == 0)
			count--;
		if(voyage.getSeafaring() == 0)
			count--;
		int amount = (int) ((calcMoral(ship,voyage) + calcCombat(ship,voyage) + calcSeafaring(ship,voyage))/count);
		if(amount > 100)
			return 100;
		else
		return amount;
	}
	/**
	 * Handles ship Beta's returning.
	 * @return if returned successfully.
	 */
	public boolean handleSecondShipReward(Ship ship) {
		Voyage voy = currentTrips.get(ship);
		secondShipReward = true;
		secondShipVoyage = 0;
		if(Utils.random(100) <= calculateVoyageSucces(ship,voy)) {
			shipArrived(true,ship);
			return true;
		}else 
			shipArrived(false,ship);
		return false;
	}
	
	/**
	 * Handles ship Gamma's returning.
	 * @return if returned successfully.
	 */
	public boolean handleThirdShipReward(Ship ship) {
		thirdShipReward = true;
		thirdShipVoyage = 0;
		Voyage voy = currentTrips.get(ship);
		if(Utils.random(100) <= calculateVoyageSucces(ship,voy)){
			shipArrived(true,ship);
			return true;
		}else 
			shipArrived(false,ship);
		return false;
	}
	
	/**
	 * Handles ship Delta's returning.
	 * @return if returned successfully.
	 */
	public boolean handleFourthShipReward(Ship ship) {
		fourthShipReward = true;
		fourthShipVoyage = 0;
		Voyage voy = currentTrips.get(ship);
		if(Utils.random(100) <= calculateVoyageSucces(ship,voy)){
			shipArrived(true,ship);
			return true;
		}else 
			shipArrived(false,ship);
		return false;
	}
	
	/**
	 * Handles ship Delta's returning.
	 * @return if returned successfully.
	 */
	public boolean handleFifthShipReward(Ship ship) {
		Voyage voy = currentTrips.get(ship);
		fifthShipReward = true;
		fifthShipVoyage = 0;
		if(Utils.random(100) <= calculateVoyageSucces(ship,voy)){
			shipArrived(true,ship);
			return true;
		}else 
			shipArrived(false,ship);
		return false;
	}
	
	public void addNewHireCrew(){
		
		//getHireAbleCrew().add(arg0)
	}
	/**
	 * Opens the noticeboard interface.
	 */
	public void openNoticeboard() {
		player.getInterfaceManager().sendInterface(1331);
		player.getPackets().sendIComponentText(1331, 2, "Plate: "+Utils.getFormattedNumber(plate)+"; "
				+ "Lacquer: "+Utils.getFormattedNumber(lacquer)
				+ "<br>Chi Globe: "+Utils.getFormattedNumber(chiGlobe)+ "; "
				+ "Chime: "+Utils.getFormattedNumber(chime)
				+ "<br>Spices: "+Utils.getFormattedNumber(spice)+ "; "
				+ "<br>Ship 'Alpha' - "+(!player.getPorts().hasFirstShip ? Colors.red+"Locked." : (!player.getPorts().hasFirstShipReturned() ? Colors.red+"Minutes Left: "+player.getPorts().getFirstVoyageTimeLeft()+"</col>." : (!player.getPorts().firstShipReward ? Colors.green+"Ready to Claim</col>." : Colors.green+"Ready to Deploy.")))
				+ "<br>Ship 'Beta' - "+(!player.getPorts().hasSecondShip ? Colors.red+"Locked." : (!player.getPorts().hasSecondShipReturned() ? Colors.red+"Minutes Left: "+player.getPorts().getSecondVoyageTimeLeft()+"</col>." : (!player.getPorts().secondShipReward ? Colors.green+"Ready to Claim</col>." : Colors.green+"Ready to Deploy.")))
				+ "<br>Ship 'Gamma' - "+(!player.getPorts().hasThirdShip ? Colors.red+"Locked." : (!player.getPorts().hasThirdShipReturned() ? Colors.red+"Minutes Left: "+player.getPorts().getSecondVoyageTimeLeft()+"</col>." : (!player.getPorts().thirdShipReward ? Colors.green+"Ready to Claim</col>." : Colors.green+"Ready to Deploy.")))
				+ "<br>Ship 'Delta' - "+(!player.getPorts().hasFourthShip ? Colors.red+"Locked." : (!player.getPorts().hasFourthShipReturned() ? Colors.red+"Minutes Left: "+player.getPorts().getSecondVoyageTimeLeft()+"</col>." : (!player.getPorts().fourthShipReward ? Colors.green+"Ready to Claim</col>." : Colors.green+"Ready to Deploy.")))
				+ "<br>Ship 'Epsilon' - "+(!player.getPorts().hasFifthShip ? Colors.red+"Locked." : (!player.getPorts().hasFifthShipReturned() ? Colors.red+"Minutes Left: "+player.getPorts().getSecondVoyageTimeLeft()+"</col>." : (!player.getPorts().fifthShipReward ? Colors.green+"Ready to Claim</col>." : Colors.green+"Ready to Deploy."))));
	}

	
	public void openCollectedRecipes() {
		player.getInterfaceManager().sendInterface(1331);
		player.getPackets().sendIComponentText(1331, 2, 
		"Rocktail Soup: "+(countRecipeParts(RECIPES.ROCKTAIL_SOUP))+"/4"
		+"<br><u>Tetsu</u> :<br> helmet : "+(countRecipeParts(RECIPES.TESTU_HELM))+"/4 "
		+" body : "+(countRecipeParts(RECIPES.TETSU_BODY))+"/4 "
		+" legs : "+(countRecipeParts(RECIPES.TETSU_LEGS))+"/4 "
		+"<u>Death Lotus</u> :<br> hood : "+(countRecipeParts(RECIPES.DEATH_LOTUS_HOOD))+"/4 "
		+"Chest : "+(countRecipeParts(RECIPES.DEATH_LOTUS_CHEST))+"/4 "
		+"Chaps: "+(countRecipeParts(RECIPES.DEATH_LOTUS_CHAPS))+"/4"
		+"<br><u>Seasingers</u> :<br> hood :"+(countRecipeParts(RECIPES.SEASINGERS_HOOD))+"/4 "
		+"Top : "+(countRecipeParts(RECIPES.SEASINGERS_ROBE_TOP))+"/4 "
		+"Bottom : "+(countRecipeParts(RECIPES.SEASINGERS_ROBE_BOTTOM))+"/4 ");
	}
	public void sendCrewInterface(){
		player.getInterfaceManager().sendInterface(3033);
		for(int i = 21; i <= 50 ;i++){ //Empty
			player.getPackets().sendIComponentSprite(3033, i, 27247);
		}
		int count = 26;
	    for(Crew crew : getCrewMemebers()){ //send sprites
	    	 player.getPackets().sendHideIComponent(3033, count, false);
	    	player.getPackets().sendIComponentSprite(3033, count, crew.getSprite());
	    	count++;
	    }
	    for(int i = count ; i <= 50; i ++){
			 player.getPackets().sendHideIComponent(3033, i, true);
		 }
	    int count2 = 21;
	    for(Captain captain : getCaptains()){
	    	player.getPackets().sendHideIComponent(3033, count2, false);
	    	player.getPackets().sendIComponentSprite(3033, count2,27279);
	    	count2++;
	    }for(int i = count2 ; i <= 25; i ++){
	    	
			 player.getPackets().sendHideIComponent(3033, i, true);
		 }
	    int count3 = 1;
	    int count4 = 59;
	    int xd = 17;
	    for(Crew crew : getHireAbleCrew()){
	    	player.getPackets().sendIComponentSprite(3033, count4 , crew.getSprite());
	    	player.getPackets().sendIComponentText(3033, xd, ""+crew.getPrice());
	    	player.getPackets().sendIComponentText(3033, count3, ""+crew.getMoral());
	    	player.getPackets().sendIComponentText(3033, count3+1, ""+crew.getCombat());
	    	player.getPackets().sendIComponentText(3033, count3+2, ""+crew.getSeafaring());
	    	player.getPackets().sendIComponentText(3033, count3+3, "0");
	    	count3+= 4;
	    	xd++;
	    	count4++;
	    }
	    player.getPackets().sendIComponentText(3033, 71, "");
		player.getPackets().sendIComponentText(3033, 58, "");
    	player.getPackets().sendIComponentText(3033, 57, "");
    	player.getPackets().sendIComponentText(3033, 56, "");
    	player.getPackets().sendIComponentText(3033, 55, "");
    	player.getPackets().sendIComponentText(3033, 65, ""+getRerolls());
	    	
	}
	private Crew selected;
	private Crew selectedCaptain;
	private Crew hireAbleSelect;
	int buttonclicked;
	/**
	 * handles the buttons of the crew interface
	 * @param buttonId
	 */
	public void handelButtons(int buttonId){
		selected = null;
		switch(buttonId){
		case 52:
			player.closeInterfaces();
			break;
		case 51:
			if(selected !=null)
				player.getDialogueManager().startDialogue("RemoveCrewD", selected);
			break;
		case 54:
			if(hireAbleSelect != null)
			player.getDialogueManager().startDialogue("HireCrewD", hireAbleSelect, buttonclicked);
			break;
		case 66:
			reroll(0);
			break;
		case 67:
			reroll(1);
			break;
		case 68:
			reroll(2);
			break;
		case 69:
			reroll(3);
			break;
		case 70:
			player.sm("Comming soon.");
			break;
		
		}
		if(buttonId >= 21 && buttonId <= 25 ){
			selectedCaptain = getCaptains().get(buttonId - 21);
			sendCaptain();
			sendCrewInfo(selectedCaptain);
		} else if(buttonId >= 26 && buttonId <= 50 ){
			selected = getCrewMemebers().get(buttonId - 26);
			sendCrewInfo(selected);
		}
		else if(buttonId >= 72 && buttonId <= 75){
			hireAbleSelect = getHireAbleCrew().get(buttonId - 72);
			resetSelected(buttonId -72);
			buttonclicked = buttonId - 72;
			player.getPackets().sendIComponentSprite(3033, buttonId, 27277);
		}
	}
	//27277 selected
	private void sendCrewInfo(Crew crew){
		player.getPackets().sendIComponentText(3033, 71, ""+crew.getName());
		player.getPackets().sendIComponentText(3033, 58, ""+crew.getMoral());
    	player.getPackets().sendIComponentText(3033, 57, ""+crew.getCombat());
    	player.getPackets().sendIComponentText(3033, 56, ""+crew.getSeafaring());
    	player.getPackets().sendIComponentText(3033, 55, ""+crew.getCombat());
	}
	
	public void resetSelected(int buttonId){
		for(int i =0; i <4; i ++)
		{
			if(i != buttonId)
				player.getPackets().sendIComponentSprite(3033, 72 +i, 27247);
		}
	}
	
	public void resetSelected2(int buttonId){
		for(int i =0; i <4; i ++)
		{
			if(i != buttonId){
				//player.sm(buttonId+" "+i);
				player.getPackets().sendIComponentSprite(3037, 78 +i, 27247);
			}
		}
	}
	
	public boolean reroll(int index){
		if(getRerolls() <= 0){
			player.sm("You have no rerolls left for today");
			return false;
		}
	Crew change =	getHireAbleCrew().get(index);
	Crew[] possible = getPossibleCrew();
	change = possible[Utils.random(possible.length)];
	getHireAbleCrew().set(index, change);
	setRerolls(getRerolls() - 1);
	sendCrewInterface();
	return true;
	}
	/*
	 * *sends voyage interface
	 */
	public Ship selectedShip;
	public void sendVoyageInterface(){
		player.getInterfaceManager().sendInterface(3037);
		for(int i = 21; i <= 50 ;i++){ //Empty
			player.getPackets().sendIComponentSprite(3037, i, 27247);
		}
		for(int i = 72; i <=81; i ++)
			player.getPackets().sendIComponentSprite(3037, i, 27247);
		sendAviadableCrew();
	    sendShipCrew();
	    sendCaptain();
	    sendMissions();
	    updateShipStats();
	    player.getPackets().sendIComponentText(3037, 65, "");
	    player.getPackets().sendIComponentText(3037, 71, "");
	    if(selectedShip.getCaptain() != null)
	    	player.getPackets().sendIComponentSprite(3037, 72, 27279);
	    
		for(int i =0 ;i <4; i++)
			player.getPackets().sendItemOnIComponent(3037, 59+i, 4254, 1);
	}
	public Voyage selectedVoy;
	
	public void handelShipButton(int id){
		switch(id){
		case 52:
			player.closeInterfaces();
			break;
		case 54:
			if(selectedShip.getCaptain() == null){
				player.getDialogueManager().startDialogue("SimpleMessage", "Your ship must have a captain assigned before you can send it on a voyage.");
				break;
			} else if(selectedVoy == Voyage.THE_FORGOTTEN_SCROLlS && getCurrentRecipe() == null){
				player.getDialogueManager().startDialogue("SimpleMessage", "You have not picked a receipe yet, talk to the Barmeid for more information.");
				break;
			} else if(calculateVoyageSucces(selectedShip,selectedVoy) < 50){
				player.getDialogueManager().startDialogue("shipWarningD", selectedShip,selectedVoy);
				break;
			} else {
				player.getDialogueManager().startDialogue("SendVoyageD", selectedShip,selectedVoy);
			}
				
				break;
		case 51:
			player.getDialogueManager().startDialogue("SelectedShipD");
			break;
		case 72:
			removeShipCaptain(selectedShip.getCaptain(),selectedShip);
			updateShipStats();
			if(selectedVoy != null)
				changeSuccesRate();
			break;
		}
		if(id >= 21 && id <= 25){
			setShipCaptain(getAvaidableCaptains().get(id-21),selectedShip);
			updateShipStats();
			if(selectedVoy != null)
				changeSuccesRate();
		} else if(id >= 26 && id <= 50){
			addCrewToShip(selectedShip,getAvaidableCrewMemebers().get(id-26));
			updateShipStats();
			if(selectedVoy != null)
				changeSuccesRate();
		} else if(id >= 73 && id <= 77){
			removeShipCrew(selectedShip.getCrewMemebers().get(id - 73));
			updateShipStats();
			if(selectedVoy != null)
				changeSuccesRate();
		}  else if(id >= 78 && id <= 81){
			selectedVoy = getPossibleVoyages().get(id-78);
			resetSelected2(id-78);
			player.getPackets().sendIComponentSprite(3037, id, 27277);
			changeSuccesRate();
		}
	}
	/**
	 * changes succes rate when refreshing a crew
	 */
	public void changeSuccesRate(){
		player.getPackets().sendIComponentText(3037, 65, ""+calculateVoyageSucces(selectedShip,selectedVoy));
	}
	
	public void sendCaptain(){
		int count = 21;
		if(getAvaidableCaptains() != null){
		  for(Captain captain : getAvaidableCaptains()){
			    player.getPackets().sendHideIComponent(3037, count, false);
		    	player.getPackets().sendIComponentSprite(3037, count, 27279);
		    	count++;
		    }
		}
		  for(int i = count; i <= 25; i ++){
				 player.getPackets().sendHideIComponent(3037, i, true);
			 }
		 
	}
	public void sendShipCrew(){
		int shipIndex = 73;
		for(int i = 73; i <= 76 ;i++){ //Empty
			player.getPackets().sendIComponentSprite(3037, i, 27247);
		}
	    for(Crew crew : selectedShip.getCrewMemebers()){
	    	player.getPackets().sendHideIComponent(3037, shipIndex, false);
	    	player.getPackets().sendIComponentSprite(3037, shipIndex, crew.getSprite());
	    	shipIndex++;
	    }
	    for(int i = shipIndex; i <= 77; i ++){
			 player.getPackets().sendHideIComponent(3037, i, true);
		 }
	    
	}
	
	public void sendAviadableCrew(){
		int count = 26;
		for(int i = 26; i <= 50 ;i++){ //Empty
			player.getPackets().sendIComponentSprite(3037, i, 27247);
		}
		 for(Crew crew : getAvaidableCrewMemebers()){ //send sprites
			 player.getPackets().sendHideIComponent(3037, count, false);
		    	player.getPackets().sendIComponentSprite(3037, count, crew.getSprite());
		    	count++;
		    }
		 for(int i = count ; i <= 50; i ++){
			 player.getPackets().sendHideIComponent(3037, i, true);
		 }
	}
	
	public void sendMissions(){
		  int count = 1;
		  int count3 = 17;
		  int count2 = 82;
		    for(Voyage voy : getPossibleVoyages()){
		     	player.getPackets().sendIComponentText(3037, count2, ""+voy.getName());
		    	player.getPackets().sendIComponentText(3037, count, ""+voy.getMoral());
		    	player.getPackets().sendIComponentText(3037, count+1, ""+voy.getCombat());
		    	player.getPackets().sendIComponentText(3037, count+2, ""+voy.getSeafaring());
		    	player.getPackets().sendIComponentText(3037, count+3, ""+Voyages.getTime(voy));
		    	player.getPackets().sendIComponentText(3037, count3, ""+voy.getChrimes());
		    	count+= 4;
		    	count2++;
		    	count3++;
		    }
	}
	
	public void setShipCaptain(Captain cap, Ship ship){
		getAvaidableCaptains().remove(cap);
		player.getPackets().sendHideIComponent(3037, 72, false);
		player.getPackets().sendIComponentSprite(3037, 72, cap.getSprite());
		
		ship.setCaptain(cap);
		sendCaptain();
	}
	
	public void removeShipCaptain(Captain cap, Ship ship){
		ship.setCaptain(null);
		player.getPackets().sendIComponentSprite(3037, 72, 27247);
		player.getPackets().sendHideIComponent(3037, 72, true);
		getAvaidableCaptains().add(cap);
		sendCaptain();
	}
	
	public void removeShipCrew(Crew crew){
		getAvaidableCrewMemebers().add(crew);
		selectedShip.getCrewMemebers().remove(crew);
		sendShipCrew();
		sendAviadableCrew();
	}
	
	public boolean addCrewToShip(Ship ship, Crew crew){
		if(ship.addCrew(crew)){
			getAvaidableCrewMemebers().remove(crew);
			sendShipCrew();
			sendAviadableCrew();
			return true;
		}
		return false;
	}
	/**
	 * resend the ship stats
	 */
	public void updateShipStats(){
		player.getPackets().sendIComponentText(3037, 58, ""+selectedShip.getTotalMoral());
		player.getPackets().sendIComponentText(3037, 57, ""+selectedShip.getTotalCombat());
		player.getPackets().sendIComponentText(3037, 56, ""+selectedShip.getTotalSeafaring());
		player.getPackets().sendIComponentText(3037, 55, "0");
		
	}
	/**
	 * 
	 * @return all the crew you can get in that region
	 */
	public Crew[] getPossibleCrew(){
		return getCurrentRegion().possibleCrew;
		
	}
	public Crew generateRandomCrewMemeber(){
		return getCurrentRegion().possibleCrew[Utils.random( getCurrentRegion().possibleCrew.length)];
		
	}
	
	public Captain generateRandomCaptain(int base){
		int rand = Utils.random(2);
		switch(rand){
		case 0: // moral
			getAvaidableCaptains().add(new Captain("Captain",base*2,base,base,27279));
			return new Captain("Captain",base*2,base,base,27279);
		case 1: // moral
			getAvaidableCaptains().add(new Captain("Captain",base,base*2,base,27279));
			return new Captain("Captain",base,base*2,base,27279);
		case 2: // moral
			getAvaidableCaptains().add(new Captain("Captain",base,base,base*2,27279));
			return new Captain("Captain",base,base,base*2,27279);
		
		}
		return null;
	}
	
	/**
	 * gets the current totem
	 * @return
	 */
	public Totems getTotem(){
		return totem;
	}
	/**
	 * sets the totem
	 * @param tomem2
	 */
	public void setTotem(Totems tomem2){
		this.totem = tomem2;
		spawnObjects();
	}
	
	public workShop getWorkShop(){
		return workshop;
	}
	public void setWorkShop(workShop work){
		this.workshop = work;
		spawnObjects();
	}

	/**
	 * @return the voyages
	 */
	public List<Voyage> getVoyages() {
		return completedVoyages;
	}

	/**
	 * @param voyages the voyages to set
	 */
	public void setVoyages(List<Voyage> voyages) {
		this.completedVoyages = voyages;
	}

	/**
	 * @return the ships
	 */
	public List<Ship> getShips() {
		return ships;
	}

	/**
	 * @param ships the ships to set
	 */
	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	/**
	 * @return the currentTrips
	 */
	public HashMap<Ship, Voyage> getCurrentTrips() {
		return currentTrips;
	}

	/**
	 * @param currentTrips the currentTrips to set
	 */
	public void setCurrentTrips(HashMap<Ship, Voyage> currentTrips) {
		this.currentTrips = currentTrips;
	}

	/**
	 * @return the possibleVoyages
	 */
	public List<Voyage> getPossibleVoyages() {
		return possibleVoyages;
	}

	/**
	 * @param possibleVoyages the possibleVoyages to set
	 */
	public void setPossibleVoyages(List<Voyage> possibleVoyages) {
		this.possibleVoyages = possibleVoyages;
	}
	public Regions getCurrentRegion() {
		return currentRegion;
	}
	public void setCurrentRegion(Regions currentRegion) {
		this.currentRegion = currentRegion;
	}
	public Bar getBar() {
		return bar;
	}
	public void setBar(Bar bar) {
		this.bar = bar;
	}
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	/**
	 * @return the currentRecipe
	 */
	public RECIPES getCurrentRecipe() {
		return currentRecipe;
	}
	/**
	 * @param currentRecipe the currentRecipe to set
	 */
	public void setCurrentRecipe(RECIPES currentRecipe) {
		this.currentRecipe = currentRecipe;
	}
	/**
	 * @return the foundRecipes
	 */
	public List<RECIPES> getFoundRecipes() {
		return foundRecipes;
	}
	/**
	 * @param foundRecipes the foundRecipes to set
	 */
	public void setFoundRecipes(List<RECIPES> foundRecipes) {
		this.foundRecipes = foundRecipes;
	}
	/**
	 * @return the specialVoyages
	 */
	public List<Voyage> getSpecialVoyages() {
		return specialVoyages;
	}
	/**
	 * @param specialVoyages the specialVoyages to set
	 */
	public void setSpecialVoyages(List<Voyage> specialVoyages) {
		this.specialVoyages = specialVoyages;
	}
	/**
	 * @return the crewMemebers
	 */
	public List<Crew> getCrewMemebers() {
		return crewMemebers;
	}
	/**
	 * @param crewMemebers the crewMemebers to set
	 */
	public void setCrewMemebers(List<Crew> crewMemebers) {
		this.crewMemebers = crewMemebers;
	}
	/**
	 * @return the hireAbleCrew
	 */
	public List<Crew> getHireAbleCrew() {
		return hireAbleCrew;
	}
	/**
	 * @param hireAbleCrew the hireAbleCrew to set
	 */
	public void setHireAbleCrew(List<Crew> hireAbleCrew) {
		this.hireAbleCrew = hireAbleCrew;
	}
	/**
	 * @return the captains
	 */
	public List<Captain> getCaptains() {
		return captains;
	}
	/**
	 * @param captains the captains to set
	 */
	public void setCaptains(List<Captain> captains) {
		this.captains = captains;
	}
	/**
	 * adds a captain to the list
	 * @param cap
	 * @return true if can add
	 */
	public boolean addCaptain(Captain cap){
		if(getCaptains().size() == 5)
			return false;
		getCaptains().add(cap);
		return true;
	}
	/**
	 * @return the avaidableCrewMemebers
	 */
	public List<Crew> getAvaidableCrewMemebers() {
		return avaidableCrewMemebers;
	}
	/**
	 * @param avaidableCrewMemebers the avaidableCrewMemebers to set
	 */
	public void setAvaidableCrewMemebers(List<Crew> avaidableCrewMemebers) {
		this.avaidableCrewMemebers = avaidableCrewMemebers;
	}
	/**
	 * @return the avaidableCaptains
	 */
	public List<Captain> getAvaidableCaptains() {
		return AvaidableCaptains;
	}
	/**
	 * @param avaidableCaptains the avaidableCaptains to set
	 */
	public void setAvaidableCaptains(List<Captain> avaidableCaptains) {
		AvaidableCaptains = avaidableCaptains;
	}
	/**
	 * @return the rerolls
	 */
	public int getRerolls() {
		return rerolls;
	}
	/**
	 * @param rerolls the rerolls to set
	 */
	public void setRerolls(int rerolls) {
		this.rerolls = rerolls;
	}
	/**
	 * @return the market
	 */
	public Market getMarket() {
		return market;
	}
	/**
	 * @param market the market to set
	 */
	public void setMarket(Market market) {
		this.market = market;
	}
	/**
	 * @return the dailyChrimes
	 */
	public int getDailyChrimes() {
		return dailyChrimes;
	}
	/**
	 * @param dailyChrimes the dailyChrimes to set
	 */
	public void setDailyChrimes(int dailyChrimes) {
		this.dailyChrimes = dailyChrimes;
	}
	/**
	 * @return the specialLoot
	 */
	public Item getSpecialLoot() {
		return specialLoot;
	}
	/**
	 * @param specialLoot the specialLoot to set
	 */
	public void setSpecialLoot(Item specialLoot) {
		this.specialLoot = specialLoot;
	}
	
}