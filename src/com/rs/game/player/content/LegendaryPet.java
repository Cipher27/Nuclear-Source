package com.rs.game.player.content;

import java.io.Serializable;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.utils.Colors;
import com.rs.utils.Utils;

public class LegendaryPet implements Serializable {

	


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9205130126124081087L;

	/**
	 * player stuff
	 */
	private transient Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * variables
	 */
	private static int LEVEL_EXP = 100;//50000;
	/**
	 * player info
	 */
	public boolean boughtPet;
	public boolean hasLegendaryPetSpawned;
	/**
	 * pet info
	 */
	private int petLevel;
	private double petTotalExp;
	private double petCurrentExp;
	private int petId;
	private int envolveStage = 0;
	private int itemSpawnId = 31559;
	/**
	 * ability timers
	 */
	private int legendaryPetBankCooldown;
	/**
	 * constructor
	 */
	public LegendaryPet(int pet,Player player){
		setPetLevel(1000);
		setPetExp(0);
		this.petId = pet;
		setPlayer(player);
	}
	/**
	 * spawns the pet
	 */
	public boolean spawnPet(){
		 if (player.getPetManager().spawnPet(itemSpawnId, false)) 
				return true;
		 return false;
	}
	/**
	 * envolves the pet
	 */
	public void envolve(){
		petId++;
		envolveStage++;
		player.getPet().setNextGraphics(new Graphics(4700));
		player.getPetManager().setNpcId(-1);
		player.getPetManager().setItemId(-1);
		player.getPetManager().removeDetails(player.getPet().getItemId());
		player.getPet().switchOrb(false);
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 98 : 212);
		player.getPackets().sendIComponentSettings(747, 17, 0, 0, 0);
		player.getPet().finish();
		player.setPet(null);
		itemSpawnId++;
		spawnPet();
		player.getPet().setNextGraphics(new Graphics(4700));
	}
	/**
	 * checks if the pet has the right level to envolve
	 * @return
	 */
	public boolean canEnvolve(){
		if(petLevel == 32 && envolveStage == 0)
			return true;
		if(petLevel ==  85 && envolveStage == 1)
			return true;
		return false;
	}
	/**
	 * adds the exp to the pet
	 * @param exp
	 */
	public void addExp(double exp){
	checkLevelUp();
	addPetExp(exp);
	}
	/**
	 * checks levelup
	 * @return
	 */
	public void checkLevelUp(){
		if(player.getPet() == null)
			return;
		if(getExpForNextLevel() <= (int)petCurrentExp){ //TODO pet animation and gfx for level up
			if(canEnvolve())
				envolve();
			petCurrentExp = 0;
			petLevel++;
			player.getPet().setNextGraphics(new Graphics(5170));
			player.sm(Colors.darkRed+"Your pet has leveled up! He is now level : "+Colors.white+""+player.getLegendaryPet().getLevel()+" .");		
		}
	}
	/**
	 * gets the level of the pet
	 * @return
	 */
	public int getLevel(){
		return petLevel;
	}
	/**
	 * gets the exp for the next level
	 * @return
	 */
	public int getExpForNextLevel(){
		int level = getLevel();
		int exp = level * LEVEL_EXP;
		return (int) (exp - petCurrentExp);
	}
	
	
	/**
	 * sends the interface
	 * @param player
	 */
	public void sendLegendaryPetInter(){
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Legendary pet skill information. Current level: "+getLevel()+".");
		for (int i = 10; i < 310; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
		player.getPackets().sendIComponentText(275, 10, "Your pet is leveled based and can unlock more abilitys the higher level he becomes.");
		player.getPackets().sendIComponentText(275, 11, "Your pet envolves at level 32 and envolves again at level 85.");
		player.getPackets().sendIComponentText(275, 12, "--------------------------------------------------------------");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 17, "More coming soon.");
	}
	/**
	 * gets random sentences to say
	 * @return
	 */
	public String getShout(){
		int id = Utils.random(10);
		switch(id){
		case 1:
			return "When do I envolve master?";
		case 2:
			return "Can you pet me ?";
		case 3:
			return "";
		}
		return "";
	}
	/**
	 * gets and sets
	 * @return
	 */
	public int getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(int petLevel) {
		this.petLevel = petLevel;
	}

	public double getPetExp() {
		return getPetTotalExp();
	}

	public void setPetExp(double petExp) {
		this.setPetTotalExp(petExp);
	}
	
	public void addPetExp(double exp) {
		this.petCurrentExp += exp;
		this.setPetTotalExp(this.getPetTotalExp() + exp);
	}
	
	public int getlegendaryPetBankCooldown() {
		return legendaryPetBankCooldown;
	}

	public void setlegendaryPetBankCooldown(int legendaryPetBankCooldown) {
		this.legendaryPetBankCooldown = legendaryPetBankCooldown;
	}
	public double getPetTotalExp() {
		return petTotalExp;
	}
	public void setPetTotalExp(double petTotalExp) {
		this.petTotalExp = petTotalExp;
	}

}