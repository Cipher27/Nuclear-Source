package com.rs.game.player.content.ports.npcs;

import java.io.Serializable;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.ports.buildings.PortUpgrades.Market;
import com.rs.game.player.content.ports.crew.Crew;
import com.rs.utils.Utils;

/**
 * handles the voyages
 * @author paolo
 *
 */
public class Trader implements Serializable {

	/**
	 * ser stuff
	 */
	private static final long serialVersionUID = 1454L;
	
	public static final int CHRIME_PRICE = 200;
	public static final int TRADE_GOOD_PRICE = 1000000;
	private static final int BASE_CHRIMES = 350;
	/**
	 * generates daily black market loot
	 * @param player
	 */
	public static void generateDailyShop(Player player){
		if(player.getPorts().getMarket() == Market.TIER_3 || player.getPorts().getMarket() == Market.TIER_4){
			player.getPorts().setSpecialLoot(new Item(Utils.random(26317,26321),Utils.random(1,5)));
		}
		int regionIndex = player.getPorts().getCurrentRegion().ordinal();
		player.getPorts().setDailyChrimes(Utils.random(300,BASE_CHRIMES * (regionIndex +1)));
	}
	
	public static boolean canTradeChrimes(Player player){
		int price = player.getPorts().getDailyChrimes() * CHRIME_PRICE;
		if(player.getPorts().getDailyChrimes() == 0)
			return false;
		if(!player.getInventory().contains(new Item(995,price)))
			return false;
		return true;
	}
	
	public static boolean canTradeGoods(Player player){
		int price = player.getPorts().getSpecialLoot().getAmount() * TRADE_GOOD_PRICE;
		if(player.getPorts().getMarket() != Market.TIER_3 && player.getPorts().getMarket() != Market.TIER_4)
			return false;
		if(player.getPorts().getSpecialLoot() == null)
			return false;
		if(!player.getInventory().contains(new Item(995,price)))
			return false;
		return true;
		
	}
	
	public static void buyChrimes(Player player){
		int price = player.getPorts().getDailyChrimes() * CHRIME_PRICE;
		player.getPorts().chime += player.getPorts().getDailyChrimes();
		player.getPorts().setDailyChrimes(0);
		player.getInventory().deleteItem(new Item(995,price));
	}
	
	public static void buyTradegoods(Player player){
		int price = player.getPorts().getSpecialLoot().getAmount() * TRADE_GOOD_PRICE;
		player.getInventory().deleteItem(new Item(995,price));
		switch(player.getPorts().getSpecialLoot().getId()){
		case 26317: //ancient bones
			break;
		case 26318:
			player.getPorts().spice += player.getPorts().getSpecialLoot().getAmount();
			break;
		case 26319:
			player.getPorts().lacquer += player.getPorts().getSpecialLoot().getAmount();
			break;
		case 26320:
			player.getPorts().plate += player.getPorts().getSpecialLoot().getAmount();
			break;
		case 26321:
			player.getPorts().chiGlobe += player.getPorts().getSpecialLoot().getAmount();
			break;
		}
		player.getPorts().setSpecialLoot(null);
	}
	
	
}