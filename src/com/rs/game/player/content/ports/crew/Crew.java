package com.rs.game.player.content.ports.crew;

import java.io.Serializable;

/**
 * 
 * @author paolo
 *
 */
public class Crew implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7976684463019236122L;
	private String Name;
	private int Moral;
	private int Combat;
	private int Seafaring;
	private int Speed;
	private int Sprite;
	private int price;
	
    public Crew(String name,int moral,int combat,int seafaring,int speed, int sprite) {
    	this.setName(name);
    	this.setMoral(moral);
    	this.setCombat(combat);
    	this.setSeafaring(seafaring);
    	this.setSpeed(speed);
    	this.setSprite(sprite);
    	this.price = 0;
    }
    
    public Crew(String name,int moral,int combat,int seafaring,int speed, int sprite, int price) {
    	this.setName(name);
    	this.setMoral(moral);
    	this.setCombat(combat);
    	this.setSeafaring(seafaring);
    	this.setSpeed(speed);
    	this.setSprite(sprite);
    	this.price = price;
    }

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return Speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		Speed = speed;
	}

	/**
	 * @return the seafaring
	 */
	public int getSeafaring() {
		return Seafaring;
	}

	/**
	 * @param seafaring the seafaring to set
	 */
	public void setSeafaring(int seafaring) {
		Seafaring = seafaring;
	}

	/**
	 * @return the combat
	 */
	public int getCombat() {
		return Combat;
	}

	/**
	 * @param combat the combat to set
	 */
	public void setCombat(int combat) {
		Combat = combat;
	}

	/**
	 * @return the moral
	 */
	public int getMoral() {
		return Moral;
	}

	/**
	 * @param moral the moral to set
	 */
	public void setMoral(int moral) {
		Moral = moral;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * 
	 * @return a new crew member
	 */
	public Crew generateRandomCrew(){
		return new Crew("Test",0,0,0,0,0);
	}

	/**
	 * @return the sprite
	 */
	public int getSprite() {
		return Sprite;
	}

	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(int sprite) {
		Sprite = sprite;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}
}
