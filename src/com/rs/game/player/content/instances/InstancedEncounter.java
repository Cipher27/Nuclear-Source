package com.rs.game.player.content.instances;

import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.GodWars;
import com.rs.utils.Utils;

@SuppressWarnings("unused")
public enum InstancedEncounter { //2863,5354, 2    2864, 5365,2
	//TODO chunks are most south-western area
	 BANDOS(new int[][] {{26425, 26289, 500_000}, {357,669},{13, 20}}, new InstanceTiles(new WorldTile(2863,5354, 2), new WorldTile( 2864, 5354,2)), new InstanceNPC(6260, 16, 20), new InstanceNPC(6261, 10, 10), new InstanceNPC(6263, 12, 17), new InstanceNPC(6265, 19, 15)) {
		@Override
		boolean enterInstance(final Player player, final boolean changeTile, final int price) {
		
			if (price != 0) {
				if (player.getInventory().getCoinsAmount() < price) {
					player.getPackets().sendGameMessage("You need atleast "+Utils.getFormattedNumber(price)+" coins to enter this instance.", true);
					return false;
				}
				if (!player.getInventory().removeItemMoneyPouch(new Item(995, price)))
					return false;
			}
			((GodWars)player.getControlerManager().getControler()).reduceBandosKC();
			((GodWars)player.getControlerManager().getControler()).sendInterfaces();
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}

		@Override
		public boolean checkTile(final Player player) {
			return player.getX() <= getExitTile().getX() && player.withinDistance(getExitTile(), 2);
		}
	},
	ZAMORAK(new int[][] {{26428, 26286, 500_000},{364, 664},{13, 20}}, new InstanceTiles(new WorldTile(2925, 5332, 0), new WorldTile(2925, 5333, 0)), new InstanceNPC(6203, 16, 8), new InstanceNPC(6204, 21, 17), new InstanceNPC(6206, 13, 10), new InstanceNPC(6208, 20,9)) {
		@Override
		public boolean enterInstance(final Player player, final boolean changeTile, final int price) {
			
			if (price != 0) {
				if (player.getInventory().getCoinsAmount() < price) {
					player.getPackets().sendGameMessage("You need atleast "+Utils.getFormattedNumber(price)+" coins to enter this instance.", true);
					return false;
				}
				if (!player.getInventory().removeItemMoneyPouch(new Item(995, price)))
					return false;
			}
			((GodWars)player.getControlerManager().getControler()).reduceZammyKC();
			((GodWars)player.getControlerManager().getControler()).sendInterfaces();
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}
		
		@Override
		public boolean checkTile(final Player player) {
			return player.getY() >= getExitTile().getY() && player.withinDistance(getExitTile(), 2);
		}
	},
	KALPHITE_KING(new int[][] {{82014, -1, 0},{368, 216},{31, 20}}, new InstanceTiles(new WorldTile(2974, 1746, 0), new WorldTile(2970, 1655, 0)), new InstanceNPC(16697, 31,40)) {
		@Override
		public boolean enterInstance(final Player player, final boolean changeTile, final int price) {
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}
		
		@Override
		public boolean checkTile(final Player player) {
			return player.getY() >= getExitTile().getY() && player.withinDistance(getExitTile(), 2);
		}
	}, 
	ARMADYL(new int[][] {{26426, 26288, 500_000},{352, 661},{19, 7}},new InstanceTiles(new WorldTile(2835, 5295, 0), new WorldTile(2835, 5294, 0)), new InstanceNPC(6222, 10, 17), new InstanceNPC(6223, 19, 14), new InstanceNPC(6225, 12, 12),new InstanceNPC(6227, 20, 10)) {
		@Override
		boolean enterInstance(final Player player, final boolean changeTile, final int price) {
			
			if (price != 0) {
				if (player.getInventory().getCoinsAmount() < price) {
					player.getPackets().sendGameMessage("You need atleast "+Utils.getFormattedNumber(price)+" coins to enter this instance.", true);
					return false;
				}
				if (!player.getInventory().removeItemMoneyPouch(new Item(995, price)))
					return false;
			}
			((GodWars)player.getControlerManager().getControler()).reduceArmaKC();
			((GodWars)player.getControlerManager().getControler()).sendInterfaces();
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}
		
		@Override
		public boolean checkTile(final Player player) {
			return player.getY() <= getExitTile().getY() && player.withinDistance(getExitTile(), 2);
		}
	},
	SARADOMIN(new int[][] {{26427, 26287, 500_000}, {363, 654}, {19, 24}}, new InstanceTiles(new WorldTile(2923, 5256, 0), new WorldTile(2923, 5257, 0)), new InstanceNPC(6247, 19, 13),new InstanceNPC(6248, 23, 17),new InstanceNPC(6250, 16, 21),new InstanceNPC(6252, 14, 14)) {
		@Override
		boolean enterInstance(final Player player, final boolean changeTile, final int price) {
			
			if (price != 0) {
				if (player.getInventory().getCoinsAmount() < price) {
					player.getPackets().sendGameMessage("You need atleast "+Utils.getFormattedNumber(price)+" coins to enter this instance.", true);
					return false;
				}
				if (!player.getInventory().removeItemMoneyPouch(new Item(995, price)))
					return false;
			}
			((GodWars)player.getControlerManager().getControler()).reduceSaraKC();
			((GodWars)player.getControlerManager().getControler()).sendInterfaces();
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}

		@Override
		public boolean checkTile(final Player player) {
			return player.getY() >= getExitTile().getY() && player.withinDistance(getExitTile(), 2);
		}
	},
	KING_BLACK_DRAGON(new int[][] {{77834, -1, 500_000}, {281, 584}, {24, 9}}, new InstanceTiles(new WorldTile(2273, 4681, 0), new WorldTile(3051, 3519, 0)), new InstanceNPC(50, 22, 29)) {

		@Override
		boolean enterInstance(final Player player, final boolean changeTile, final int price) {
			if (price != 0) {
				if (player.getInventory().getCoinsAmount() < price) {
					player.getPackets().sendGameMessage("You need atleast "+Utils.getFormattedNumber(price)+" coins to enter this instance.", true);
					return false;
				}
				if (!player.getInventory().removeItemMoneyPouch(new Item(995, price)))
					return false;
			}
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}

		@Override
		public boolean checkTile(final Player player) {
			return player.withinDistance(getExitTile(), 3);
		}
	},

	PRIMUS(new int[][] {
		{77834, -1, 500_000},  //entrance obj, exit obj, cost
		{281, 584}, {24, 9}},  //location
			new InstanceTiles(new WorldTile(1023, 632, 1), //inside
			new WorldTile(1025,632,1)), //outside
			new InstanceNPC(17149, 22,27)) {

		@Override
		boolean enterInstance(final Player player, final boolean changeTile, final int price) {
			if (price != 0) {
				if (player.getInventory().getCoinsAmount() < price) {
					player.getPackets().sendGameMessage("You need atleast "+Utils.getFormattedNumber(price)+" coins to enter this instance.", true);
					return false;
				}
				if (!player.getInventory().removeItemMoneyPouch(new Item(995, price)))
					return false;
			}
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}

		@Override
		public boolean checkTile(final Player player) {
			return player.withinDistance(getExitTile(), 3);
		}
	}/*HUNTER_AREA(new int[][] {{1, -1, 500_000}, {281, 584}, {24, 9}}, new InstanceTiles(new WorldTile(2273, 4681, 0), new WorldTile(3051, 3519, 0)), new InstanceNPC(5080, 22, 29),new InstanceNPC(5080, 22, 29),new InstanceNPC(5080, 22, 29),new InstanceNPC(5080, 22, 29),new InstanceNPC(5080, 22, 29),new InstanceNPC(5080, 22, 29)) {

		@Override
		boolean enterInstance(final Player player, final boolean changeTile, final int price) {
			if (price != 0) {
				if (player.getInventory().getCoinsAmount() < price) {
					player.getPackets().sendGameMessage("You need atleast "+Utils.getFormattedNumber(price)+" coins to enter this instance.", true);
					return false;
				}
				if (!player.getInventory().removeItemMoneyPouch(new Item(995, price)))
					return false;
			}
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}

		@Override
		public boolean checkTile(final Player player) {
			return player.withinDistance(getExitTile(), 3);
		}
		
	}*//*,
	KALPHITE_QUEEN(new int[][] {{48803, 3832, 500_000}, {433, 1185}, {10,10}}, new InstanceTiles(new WorldTile(3508, 9493, 0), new WorldTile(3446, 9496, 0))) {
		
		@Override
		boolean enterInstance(final Player player, final boolean changeTile, final int price) {
			if (changeTile)
				player.setNextWorldTile(getEntranceTile());
			return true;
		}
		
		@Override
		public boolean checkTile(final Player player) {
			return true;
		}
	}*/
;
	
	private final int[][] parameters;
	private final InstanceNPC[] npcs;
	private final InstanceTiles tiles;
	
	private static final int GODWARS_KILLCOUNT_THRESHOLD = 15;
	
	private InstancedEncounter(final int[][] parameters, final InstanceTiles tiles, final InstanceNPC... npcs) {
		this.parameters = parameters;
		this.tiles = tiles;
		this.npcs = npcs;
	}
	
	abstract boolean enterInstance(final Player player, final boolean changeTile, final int price);
	public abstract boolean checkTile(final Player player);
	
	public final int[] getRegionCoordinates() {
		return parameters[1];
	}
	
	public final InstanceNPC[] getNPCs() {
		return npcs;
	}
	
	public final int getEntranceObjectId() {
		return parameters[0][0];
	}
	
	public final int getExitObjectId() {
		return parameters[0][1];
	}
	
	public final int getCost() {
		return parameters[0][2];
	}
	
	public final int[] getLocation() {
		return parameters[2];
	}
	
	public final WorldTile getEntranceTile() {
		return tiles.getEntranceTile();
	}
	
	public final WorldTile getExitTile() {
		return tiles.getExitTile();
	}
}