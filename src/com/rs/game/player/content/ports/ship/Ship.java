package com.rs.game.player.content.ports.ship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rs.game.player.content.ports.crew.Captain;
import com.rs.game.player.content.ports.crew.Crew;
import com.rs.utils.Utils;

/**
 * ship class
 * todo; crew, levels etc, defence Etc
 * @author paolo
 *
 */


public class Ship  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4986271619601511338L;
	private String name;
	private Captain captain;
	private List<Crew> crewMemebers;
	
	private int combat;
	private int moral;
	private int seafaring;
	
	public Ship(String name){
		this.setName(name);
		crewMemebers = new ArrayList<Crew>();
	}
	
	public Ship(){ 
		this.setName(GenerateName());
		crewMemebers = new ArrayList<Crew>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * adds a crew member to the list
	 * @param crew
	 */
	public boolean addCrew(Crew crew){
		if(crewMemebers.size() == 5)
			return false;
	   crewMemebers.add(crew);
	   return true;
	}
	/**
	 * for the random names
	 */
	private static String Prefix[] = {"Spirit of ","Storm ", "Shadow of ", "Thunder ", "Rainbow ", "Might of ", "Element of ", "Fear of ", "Hope ", "Moon ", " Saradomin's ", "Wisdom of ", "Cloud ", "Revenge of ", "Wave ", "Bandos's ","Defence of"};
	private static String ShipNames[] = {"Jack","Child","Killer","Explorer","Elie","Rose","Slayer","Queen", "Tuska", "Warrior", "Zaros", "Joker", "Lass", "Seren", "Girl"};
	
	public static String GenerateName(){
	return Prefix[Utils.random(Prefix.length)]+ShipNames[Utils.random(ShipNames.length)];
	}
	
    public int getTotalMoral(){
    	int total = 0;
    	for(Crew member : crewMemebers){
    		total += member.getMoral();
    	}
    	if(captain!= null)
    		total += captain.getMoral();
    	return  total;
    }
    
    public int getTotalCombat(){
    	int total = 0;
    	for(Crew member : crewMemebers){
    		total += member.getCombat();
    	}
    	if(captain!= null)
    		total += captain.getCombat();
    	return  total;
    }
    
    public int getTotalSeafaring(){
    	int total = 0;
    	for(Crew member : crewMemebers){
    		total += member.getSeafaring();
    	}
    	if(captain!= null)
    		total += captain.getSeafaring();
    	return  total;
    }
	/**
	 * @return the captain
	 */
	public Captain getCaptain() {
		return captain;
	}

	/**
	 * @param captain the captain to set
	 */
	public void setCaptain(Captain captain) {
		this.captain = captain;
	}

	/**
	 * @return the combat
	 */
	public int getCombat() {
		return combat;
	}

	/**
	 * @param combat the combat to set
	 */
	public void setCombat(int combat) {
		this.combat = combat;
	}

	/**
	 * @return the moral
	 */
	public int getMoral() {
		return moral;
	}

	/**
	 * @param moral the moral to set
	 */
	public void setMoral(int moral) {
		this.moral = moral;
	}

	/**
	 * @return the seafaring
	 */
	public int getSeafaring() {
		return seafaring;
	}

	/**
	 * @param seafaring the seafaring to set
	 */
	public void setSeafaring(int seafaring) {
		this.seafaring = seafaring;
	}
	
	public List<Crew> getCrewMemebers() {
		return crewMemebers;
	}

	public void setCrewMemebers(List<Crew> crewMemebers) {
		this.crewMemebers = crewMemebers;
	}

}