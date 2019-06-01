package com.rs.game.player.content;


import java.text.DecimalFormat;

import com.rs.game.item.Item;
import com.rs.game.player.Player;

public class NetWealth {
	
	private Player player;
	
	public NetWealth(Player player) {
		this.player = player;
	}
	
	public NetWealth() {
		
	}
	
	public String getNetWealth() {
		return getNetWealth(true);
	}
	
	public String getNetWealth(boolean c) {
		return getNetWealth(c, -1);
	}
	
	public String getNetWealth(boolean colourOn, double customAmount) {
		double total = customAmount != -1 ? customAmount : roundUpWealth();
	//	double total = getWCWealth();
		String mark = "";
		String colour = "";
		if(total < 0.001) {
			mark = "GP";
			colour = "";
			total = total * 1000000;
		} else if(total >= 0.001 && total < 1) {
			mark = "K";
			colour = "FFFFFF";
			total = total * 1000000;
		} else if(total >= 1 && total < 1000) {
			mark = "M";
			colour = "013ADF";
		} else if(total >= 1000 && total < 1000000) {
			mark = "B";
			colour = "01DF3A";
			total = total / 1000;
		} else if(total >= 1000000 && total < 1000000000) {
			mark = "T";
			colour = "8904B1";
			total = total / 1000000;
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		String formatted = df.format(total);
		double finished = Double.parseDouble(formatted);
		if(player != null) {
			if(player.isVeteran())
				colour = "B18904";
		}
		if(colourOn)
			return "<shad=000000><col="+colour+">"+finished+""+mark+"</col></shad>";
		return finished+""+mark;
	}
	
	
	public double roundUpWealth() {
	
		double inventory = roundInventory();
		double equipment = roundEquipment();
		double total = inventory + equipment;
		player.netWealth = total;//in mills
		return total;
	}
	
	
	
	public double roundInventory() {
		double round = 0;
		round += player.getInventory().getInventoryValue();
		return round;
	}
	
	public double roundEquipment() {
	//	Shop shop = new Shop();
		double round = 0;
		int weapon = player.getEquipment().getWeaponId();
		int hat = player.getEquipment().getHatId();
		int cape = player.getEquipment().getCapeId();
		int ammo = player.getEquipment().getAmmoId();
		int boots = player.getEquipment().getBootsId();
		int shield = player.getEquipment().getShieldId();
		int ammy = player.getEquipment().getAmuletId();
		int ring = player.getEquipment().getRingId();
		int gloves = player.getEquipment().getGlovesId();
		int chest = player.getEquipment().getChestId();
		int legs = player.getEquipment().getLegsId();
		double weaponWealth = Shop.getSellPrice(new Item(weapon, 1), -1) / 1000000;
		double hatWealth = Shop.getSellPrice(new Item(hat, 1), -1) / 1000000;
		double capeWealth = Shop.getSellPrice(new Item(cape, 1), -1) / 1000000;
		double ammoWealth = Shop.getSellPrice(new Item(ammo, 1), -1) / 1000000;
		double bootsWealth = Shop.getSellPrice(new Item(boots, 1), -1) / 1000000;
		double shieldWealth = Shop.getSellPrice(new Item(shield, 1), -1) / 1000000;
		double ammyWealth = Shop.getSellPrice(new Item(ammy, 1), -1) / 1000000;
		double ringWealth = Shop.getSellPrice(new Item(ring, 1), -1) / 1000000;
		double glovesWealth = Shop.getSellPrice(new Item(gloves, 1), -1) / 1000000;
		double chestWealth = Shop.getSellPrice(new Item(chest, 1), -1) / 1000000;
		double legsWealth = Shop.getSellPrice(new Item(legs, 1), -1) / 1000000;
		round = weaponWealth + hatWealth + capeWealth + ammoWealth + bootsWealth + shieldWealth +
					ammyWealth + ringWealth + glovesWealth + chestWealth + legsWealth;
		return round;
	}
}