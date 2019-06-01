package com.rs.game.player.actions.divination;

import com.rs.game.item.Item;

public class EnergyCreation {

public enum Products {
	
	//incandescent energy
	PORTENT_OF_RESTORATION_X2(29218, 97, 60, 23.4, new Item (15272,1) ,29324),
	PORTENT_OF_RESTORATION_X(29218, 97, 60, 23.4, new Item (15272,1) ,29324);

	private final int productId, levelRequired, energyCost;
	private final double exp;
	private final Item item;
	private final int energyType;

	private Products(int productId, int levelRequired,int energyCost,double exp, Item item,int energyType) {
		this.productId = productId;
		this.levelRequired = levelRequired;
		this.energyCost = energyCost;
		this.exp = exp;
		this.item = item;
		this.energyType = energyType;
	}

	public int getproductId() {
		return productId;
	}

	public int getLevelRequired() {
		return levelRequired;
	}

	public double getexp() {
		return exp;
	}


	public int getenergyCost() {
		return energyCost;
	}

	public Item getItems() {
		return item;
	}
	
}
}