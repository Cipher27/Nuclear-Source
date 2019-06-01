package com.rs.game.world;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;



public class GlobalCapeCounter implements Serializable {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 4497003849850500131L;

	private final static String PATH = "data/CapeCounting.sd";
	
	private static GlobalCapeCounter data;

	//120capes
		private int agility120= 0;
		private int attack120= 0;
		private int defence120= 0;
		private int construction120= 0;
		private int magic120= 0;
		private int ranged120= 0;
		private int cooking120= 0;
		private int prayer120= 0;
		private int constitution120= 0;
		private int herblore120= 0;
		private int fletching120= 0;
		private int woodcutting120= 0;
		private int firemaking120= 0;
		private int summoning120= 0;
		private int dungeoneering120= 0;
		private int farming120= 0;
		private int fishing120= 0;
		private int mining120= 0;
		private int smithing120= 0;
		private int hunter120= 0;
		private int crafting120= 0;
		private int thieving120= 0;
		private int slayer120 = 0;
		private int strength120 = 0;
		private int runecrafting120= 0;
		//special capes
		private int maxedUsers=  0;
		private int compedUsers= 0;
		private int compedTUsers= 0;
		private int fireCapes= 0;
		private int tokharcalCapes= 0;
		private int nomadsCapes= 0;		

	public void reset(){
		agility120= 0;
		attack120= 0;
		defence120= 0;
		construction120= 0;
		magic120= 0;
		ranged120= 0;
		cooking120= 0;
		prayer120= 0;
		constitution120= 0;
		herblore120= 0;
		fletching120= 0;
		woodcutting120= 0;
		firemaking120= 0;
		summoning120= 0;
		dungeoneering120= 0;
		farming120= 0;
		fishing120= 0;
		mining120= 0;
		smithing120= 0;
		hunter120= 0;
		crafting120= 0;
		thieving120= 0;
		slayer120 = 0;
		strength120 = 0;
		runecrafting120= 0;
		//special capes
		maxedUsers=  0;
		compedUsers= 0;
		compedTUsers= 0;
		fireCapes= 0;
		tokharcalCapes= 0;
		nomadsCapes= 0;	
	}

	public GlobalCapeCounter() {
		try {
			File file = new File(PATH);
			file.createNewFile();
			SerializableFilesManager.storeSerializableClass(data, new File(PATH));
 
		} catch (IOException ex) {
			Logger.log("Hellion", "Failed creating");
		} 
	}

	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				data = (GlobalCapeCounter) SerializableFilesManager.loadSerializedFile(file);
				Logger.log("Hellion", "Successfully loaded cape count.");
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		data = new GlobalCapeCounter();
		Logger.log("Hellion", "Failed to load cape data.. creating new file.");
	}

	public static final void save() {
		try {
			SerializableFilesManager.storeSerializableClass(data, new File(PATH));
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}
	

	public static GlobalCapeCounter getCapes() {
		return data;
	}

	/**
	 * @return the agility120
	 */
	public int getAgility120() {
		return agility120;
	}

	/**
	 * @param agility120 the agility120 to set
	 */
	public void setAgility120() {
		this.agility120++;
	}

	/**
	 * @return the attack120
	 */
	public int getAttack120() {
		return attack120;
	}

	/**
	 * @param attack120 the attack120 to set
	 */
	public void setAttack120() {
		this.attack120++;
	}

	/**
	 * @return the defence120
	 */
	public int getDefence120() {
		return defence120;
	}

	/**
	 * @param defence120 the defence120 to set
	 */
	public void setDefence120() {
		this.defence120++;
	}

	/**
	 * @return the construction120
	 */
	public int getConstruction120() {
		return construction120;
	}

	/**
	 * @param construction120 the construction120 to set
	 */
	public void setConstruction120() {
		this.construction120++;
	}

	/**
	 * @return the magic120
	 */
	public int getMagic120() {
		return magic120;
	}

	/**
	 * @param magic120 the magic120 to set
	 */
	public void setMagic120() {
		this.magic120++;
	}

	/**
	 * @return the ranged120
	 */
	public int getRanged120() {
		return ranged120;
	}

	/**
	 * @param ranged120 the ranged120 to set
	 */
	public void setRanged120() {
		this.ranged120++;
	}

	/**
	 * @return the cooking120
	 */
	public int getCooking120() {
		return cooking120;
	}

	/**
	 * @param cooking120 the cooking120 to set
	 */
	public void setCooking120() {
		this.cooking120++;
	}

	/**
	 * @return the prayer120
	 */
	public int getPrayer120() {
		return prayer120;
	}

	/**
	 * @param prayer120 the prayer120 to set
	 */
	public void setPrayer120() {
		this.prayer120++;
	}

	/**
	 * @return the constitution120
	 */
	public int getConstitution120() {
		return constitution120;
	}

	/**
	 * @param constitution120 the constitution120 to set
	 */
	public void setConstitution120() {
		this.constitution120++;
	}

	/**
	 * @return the herblore120
	 */
	public int getHerblore120() {
		return herblore120;
	}

	/**
	 * @param herblore120 the herblore120 to set
	 */
	public void setHerblore120() {
		this.herblore120++;
	}

	/**
	 * @return the fletching120
	 */
	public int getFletching120() {
		return fletching120;
	}

	/**
	 * @param fletching120 the fletching120 to set
	 */
	public void setFletching120() {
		this.fletching120++;
	}

	/**
	 * @return the woodcutting120
	 */
	public int getWoodcutting120() {
		return woodcutting120;
	}

	/**
	 * @param woodcutting120 the woodcutting120 to set
	 */
	public void setWoodcutting120() {
		this.woodcutting120++;
	}

	/**
	 * @return the firemaking120
	 */
	public int getFiremaking120() {
		return firemaking120;
	}

	/**
	 * @param firemaking120 the firemaking120 to set
	 */
	public void setFiremaking120() {
		this.firemaking120++;
	}

	/**
	 * @return the summoning120
	 */
	public int getSummoning120() {
		return summoning120;
	}

	/**
	 * @param summoning120 the summoning120 to set
	 */
	public void setSummoning120() {
		this.summoning120++;
	}

	/**
	 * @return the dungeoneering120
	 */
	public int getDungeoneering120() {
		return dungeoneering120;
	}

	/**
	 * @param dungeoneering120 the dungeoneering120 to set
	 */
	public void setDungeoneering120() {
		this.dungeoneering120++;
	}

	/**
	 * @return the farming120
	 */
	public int getFarming120() {
		return farming120;
	}

	/**
	 * @param farming120 the farming120 to set
	 */
	public void setFarming120() {
		this.farming120++;
	}

	/**
	 * @return the fishing120
	 */
	public int getFishing120() {
		return fishing120;
	}

	/**
	 * @param fishing120 the fishing120 to set
	 */
	public void setFishing120() {
		this.fishing120++;
	}

	/**
	 * @return the mining120
	 */
	public int getMining120() {
		return mining120;
	}

	/**
	 * @param mining120 the mining120 to set
	 */
	public void setMining120() {
		this.mining120++;
	}

	/**
	 * @return the smithing120
	 */
	public int getSmithing120() {
		return smithing120;
	}

	/**
	 * @param smithing120 the smithing120 to set
	 */
	public void setSmithing120() {
		this.smithing120++;
	}

	/**
	 * @return the hunter120
	 */
	public int getHunter120() {
		return hunter120;
	}

	/**
	 * @param hunter120 the hunter120 to set
	 */
	public void setHunter120() {
		this.hunter120++;
	}

	/**
	 * @return the crafting120
	 */
	public int getCrafting120() {
		return crafting120;
	}

	/**
	 * @param crafting120 the crafting120 to set
	 */
	public void setCrafting120() {
		this.crafting120++;
	}

	/**
	 * @return the thieving120
	 */
	public int getThieving120() {
		return thieving120;
	}

	/**
	 * @param thieving120 the thieving120 to set
	 */
	public void setThieving120() {
		this.thieving120++;
	}

	/**
	 * @return the slayer120
	 */
	public int getSlayer120() {
		return slayer120;
	}

	/**
	 * @param slayer120 the slayer120 to set
	 */
	public void setSlayer120() {
		this.slayer120++;
	}

	/**
	 * @return the strength120
	 */
	public int getStrength120() {
		return strength120;
	}

	/**
	 * @param strength120 the strength120 to set
	 */
	public void setStrength120() {
		this.strength120++;
	}

	/**
	 * @return the runecrafting120
	 */
	public int getRunecrafting120() {
		return runecrafting120;
	}

	/**
	 * @param runecrafting120 the runecrafting120 to set
	 */
	public void setRunecrafting120() {
		this.runecrafting120++;
	}

	/**
	 * @return the maxedUsers
	 */
	public int getMaxedUsers() {
		return maxedUsers;
	}

	/**
	 * @param maxedUsers the maxedUsers to set
	 */
	public void setMaxedUsers() {
		this.maxedUsers++;
	}

	/**
	 * @return the compedUsers
	 */
	public int getCompedUsers() {
		return compedUsers;
	}

	/**
	 * @param compedUsers the compedUsers to set
	 */
	public void setCompedUsers() {
		this.compedUsers++;
	}

	/**
	 * @return the compedTUsers
	 */
	public int getCompedTUsers() {
		return compedTUsers;
	}

	/**
	 * @param compedTUsers the compedTUsers to set
	 */
	public void setCompedTUsers() {
		this.compedTUsers++;
	}

	/**
	 * @return the fireCapes
	 */
	public int getFireCapes() {
		return fireCapes;
	}

	/**
	 * @param fireCapes the fireCapes to set
	 */
	public void setFireCapes() {
		this.fireCapes++;
	}

	/**
	 * @return the tokharcalCapes
	 */
	public int getTokharcalCapes() {
		return tokharcalCapes;
	}

	/**
	 * @param tokharcalCapes the tokharcalCapes to set
	 */
	public void setTokharcalCapes() {
		this.tokharcalCapes++;
	}

	/**
	 * @return the nomadsCapes
	 */
	public int getNomadsCapes() {
		return nomadsCapes;
	}

	/**
	 * @param nomadsCapes the nomadsCapes to set
	 */
	public void setNomadsCapes() {
		this.nomadsCapes++;
	}

}
