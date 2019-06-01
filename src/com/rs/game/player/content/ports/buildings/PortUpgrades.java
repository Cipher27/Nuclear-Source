package com.rs.game.player.content.ports.buildings;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.ports.buildings.PortUpgrades.MATERIALS;
import com.rs.game.player.content.ports.ship.Ship;
/**
 * 
 * @author paolo
 *
 */
public class PortUpgrades {
	
	public enum MATERIALS {BAMBOO};
	
	public static WorldTile TOTEM_TILE = new WorldTile(4053,7273,0);
 
	public enum Totems{
		TELESCOPE(81834,"Increases the chance of receiving Receipe scroll voyages.",1500),
		PARROT(81835,"Increases the chance of receiving XP voyages.",1500),
		CHERRY_TREE(81836,"Increases the amount of resources rewarded from a voyage. Does not apply to trade goods.",1500),
		PANDORAS_BOX(81837,"Increases the chance of receiving a random event in the port.",1500),
		JADE_STATIE(81838,"Increases the chance of receiving trade good voyages.",1500);
		int objectId, cost;
		String bonus;
		
		Totems(int object, String bonus, int cost){
			this.objectId = object;
			this.bonus = bonus;
			this.cost = cost;
		}
		public int getObjectId(){
			return objectId;
		}
		
		public int getCost(){
			return cost;
		}
	}
	/**
	 * enum of the workplace
	 */
	public enum workShop{
		TIER_1(1500,81756,81751,1,81736,81728,81724),
		TIER_2(5000,81757,81752,1,81737,81729,81725),
		TIER_3(20000,81753,81738,1,81742,81730,81726),
		TIER_4(50000,81754,81754,1,81743,81731,81727);
		public int price,tier,bankId,workbenchId,workbenchId2,cookingStationId,ScrimshawCraterId,anvilId;
		
		private workShop(int price,int bank,int work,int work2, int cook, int scrim, int anv){
			this.price = price;
			this.bankId = bank;
			this.workbenchId = work;
			this.workbenchId2 = work2;
			this.cookingStationId = cook;
			this.ScrimshawCraterId = scrim;
			this.anvilId = anv;
		}
		
		private static workShop[] vals = values();
		/**
		 * gets the next value
		 * @return
		 */
		 public workShop next()
		    {
		        return vals[(this.ordinal()+1) % vals.length];
		    }
			public static int getCost(workShop shop){
				if(shop == null)
					return TIER_1.price;
				else return shop.price;
			}
	}
	
	
	public enum Office{
		
		TIER_1(1500),
		TIER_2(5000),
		TIER_3(20000),
		TIER_4(50000);	
		
		int price;
		private Office(int price){
			this.price =price;
		}
		
		private static Office[] vals = values();
		/**
		 * gets the next value
		 * @return
		 */
		 public Office next()
		    {
		        return vals[(this.ordinal()+1) % vals.length];
		    }
		
		public static int getCost(Office shop){
				if(shop == null)
					return TIER_1.price;
				else return shop.price;
			}
	}
	
	public enum Market{
		//kast id
		TIER_1(5000,81792,1,"You unlock the Warehouse and the trader npc."),
		TIER_2(10000,81793,1,"Increaed amount of chrimes."),
		TIER_3(20000,81793,1,"The trade can now sell you trade goods aswell."),
		TIER_4(50000,81793,1,"The trader can now also trade you recipes.");
		int price,reck,barrel;
		public String extra;
		
		Market(int price,int id,int bar,String extra){
			this.price = price;
			this.barrel = bar;
			this.reck = id;
			this.extra = extra;
		}
		private static Market[] vals = values();
		/**
		 * gets the next value
		 * @return
		 */
		 public Market next(){
		        return vals[(this.ordinal()+1) % vals.length];
		 }
		 
		 public String getMessage(Player player){
			 if(player.getPorts().getMarket() == null)
				 return TIER_1.extra;
			 return player.getPorts().getMarket().extra;
		 }
		 public static int getCost(Market shop){
				if(shop == null)
					return TIER_1.price;
				else return shop.price;
			}
	}
	
	public static void UpgradeMarket(Player player){
		if(player.getPorts().getMarket() == null)
			player.getPorts().setMarket(Market.TIER_1);
		else if(player.getPorts().getMarket() != Market.TIER_4)
			player.getPorts().setMarket(player.getPorts().getMarket().next());
		else
			player.sm("You can't upgrade anymore.");
	}
	
	public enum Bar{
		
		TIER_1(1),
		TIER_4(1);
		
		int barId;
		
		private Bar(int bar){
			this.barId = bar;
		}
		
		private static Bar[] vals = values();
		/**
		 * gets the next value
		 * @return
		 */
		 public Bar next()
		    {
		        return vals[(this.ordinal()+1) % vals.length];
		    }
	}
	/**
	 * upgrades the bar
	 * @param player
	 */
	public static void UpgradeBar(Player player){
		if(player.getPorts().getBar() == null)
			player.getPorts().setBar(Bar.TIER_1);
		else if(player.getPorts().getBar() != Bar.TIER_4)
			player.getPorts().setBar(player.getPorts().getBar().next());
		else
			player.sm("You can't upgrade anymore.");
	}
	/**
	 * spawns the totem
	 * @param totem
	 * @param player
	 */
	public static void spawnTotem(Totems totem, Player player){
		player.getPackets().sendSpawnedObject(new WorldObject(totem.getObjectId(),10,0,TOTEM_TILE));
	}
	/**
	 * spaawns objects of the workshop
	 * @param ws
	 * @param player
	 */
	public static void spawnWorkShop(workShop ws,Player player){
		player.getPackets().sendSpawnedObject(new WorldObject(ws.bankId,10,0,new WorldTile(4087,7285,0)));
		player.getPackets().sendSpawnedObject(new WorldObject(ws.workbenchId,10,2,new WorldTile(4084,7281,0)));
		player.getPackets().sendSpawnedObject(new WorldObject(ws.workbenchId2,10,2,new WorldTile(4091,7278,0)));
		player.getPackets().sendSpawnedObject(new WorldObject(ws.ScrimshawCraterId,10,0,new WorldTile(4092,7283,0)));
		player.getPackets().sendSpawnedObject(new WorldObject(ws.cookingStationId,10,2,new WorldTile(4086,7279,0)));
		player.getPackets().sendSpawnedObject(new WorldObject(ws.anvilId,10,0,new WorldTile(4081,7277,0)));
	}
	/*
	 * 
	 */
	public static void spawnMarket(Market mt, Player player){
		for(int i =0; i < 4;i++) //kasten
		player.getPackets().sendSpawnedObject(new WorldObject(mt.reck,10,0,new WorldTile(4042,7286,0)));
	}
	/**
	 * upgrades the current state
	 * @param player
	 */
	public static void upgradeWorkshop(Player player){
		if(player.getPorts().getWorkShop() != workShop.TIER_4){
			if(player.getPorts().getWorkShop() == null)
				player.getPorts().setWorkShop(workShop.TIER_1);
			else 
				player.getPorts().setWorkShop(player.getPorts().getWorkShop().next());
			player.getPorts().chime -= player.getPorts().getWorkShop().price;
		} else {
			player.sm("You have reached the max tier.");
		}
	}
	
	public static void upgradeOffice(Player player){
		if(player.getPorts().getOffice() != Office.TIER_4){
			if(player.getPorts().getOffice() == null)
				player.getPorts().setOffice(Office.TIER_1);
			else
			player.getPorts().setOffice(player.getPorts().getOffice().next());
			player.getPorts().getShips().add(new Ship(Ship.GenerateName()));
			player.sm("You have unlocked a new ship.");
		} else {
			player.sm("You have reached the max tier.");
		}
	}
	
}
/**
 * class for materials, atm not used
 * @author paolo
 *
 */
class Tokens {
	
	private MATERIALS material;
	private int amount;
	
	public Tokens(MATERIALS material, int amount){
		this.material = material;
		this.amount = amount;
	}
}
