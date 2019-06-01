package com.rs.game.player.content;

import com.rs.game.player.Player;

public class Wildstalker {

	public static final int[] helmets = { 20801, 20802, 20803, 20804, 20805, 20806 };
	public static int killCount;

	public static int getId() {
	for (int i = 0; i < helmets.length;)
		return helmets[i];
	return -1;
	}

	public static void sendPoints() {
		setKillCount(getKills() + 1);
	}
	
	public static int getKills() {
		return killCount;
	}
	
	public static void setKillCount(int killCount) {
		Wildstalker.killCount = killCount;
	}

	public static void sendKillsMsg(Player player) {
		player.getPackets().sendGameMessage("You have <col=ff0000>"+killCount+"</col> with this helmet.");
	}
	
	public static void handleHelmets(Player player, int itemId) {
		if (itemId == 20801) {
			if (getKills() != 10) {
				player.getPackets().sendGameMessage("<col=ff0000>You need 10 kills with this helmet equiped to "
						+ "do this.</col>");
				return;
			}
			player.getInventory().deleteItem(20801, 1);
			player.getInventory().addItem(20802, 1);
			player.getPackets().sendGameMessage("<col=00ff00>You have upgraded your tier of this helmet.</col>");
		} else if (itemId == 20802) {
			if (getKills() != 100) {
				player.getPackets().sendGameMessage("<col=ff0000>You need 100 kills with this helmet equiped to "
						+ "do this.</col>");
				return;
			}
			player.getInventory().deleteItem(20802, 1);
			player.getInventory().addItem(20803, 1);
			player.getPackets().sendGameMessage("<col=00ff00>You have upgraded your tier of this helmet.</col>");
		} else if (itemId == 20803) {
			if (getKills() != 500) {
				player.getPackets().sendGameMessage("<col=ff0000>You need 500 kills with this helmet equiped to "
						+ "do this.</col>");
				return;
			}
			player.getInventory().deleteItem(20803, 1);
			player.getInventory().addItem(20804, 1);
			player.getPackets().sendGameMessage("<col=00ff00>You have upgraded your tier of this helmet.</col>");
		} else if (itemId == 20804) {
			if (getKills() != 2000) {
				player.getPackets().sendGameMessage("<col=ff0000>You need 2000 kills with this helmet equiped to "
						+ "do this.</col>");
				return;
			}
			player.getInventory().deleteItem(20804, 1);
			player.getInventory().addItem(20805, 1);
			player.getPackets().sendGameMessage("<col=00ff00>You have upgraded your tier of this helmet.</col>");
		} else if (itemId == 20805) {
			if (getKills() != 5000) {
				player.getPackets().sendGameMessage("<col=ff0000>You need 5000 kills with this helmet equiped to "
						+ "do this.</col>");
				return;
			}
			player.getInventory().deleteItem(20805, 1);
			player.getInventory().addItem(20806, 1);
			player.getPackets().sendGameMessage("<col=00ff00>You have upgraded your tier of this helmet.</col>");
		}
	}

	public static boolean isHelmet(int itemId) {
		switch (itemId) {
		case 20801:
		case 20802:
		case 20803:
		case 20804:
		case 20805:
		case 20806:
			return true;
		}
		return false;
	}

	
	
}
