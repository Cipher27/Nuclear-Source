package com.rs.game.item;

import java.io.Serializable;

/**
 * handles degradeable items like barrows
 * @author paolo
 *
 */
public class DegradeAbleItem extends Item implements Serializable {

	private int charges;
	public DegradeAbleItem(int id, int charges) {
		super(id);
		this.charges = charges;
	}
	
	public void decreaseCharges(){
		charges--;
	}

	/**
	 * @return the charges
	 */
	@Override
	public int getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	@Override
	public void setCharges(int charges) {
		this.charges = charges;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9104364268229434110L;



}
