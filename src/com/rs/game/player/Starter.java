package com.rs.game.player;

import com.rs.game.player.content.FriendChatsManager;

public class Starter {

	public static final int MAX_STARTER_COUNT = 1;
	
	//private static int amount = 100000;

	public static void appendStarter(Player player) {
		String ip = player.getSession().getIP();
		int count = StarterMap.getSingleton().getCount(ip);
		player.getStarter = true;
		if (count >= MAX_STARTER_COUNT) {
			player.sendMessage("You have already recieved your starter kit!");
			return;
		}
	    player.getInventory().addItem(11814, 1);//Bronze Armour Set
	    player.getInventory().addItem(11834, 1);//addy Armour Set
	    player.getInventory().addItem(6568, 1);//Obby Cape
	    player.getInventory().addItem(1725, 1);//Amulet of strength
	    player.getInventory().addItem(1321, 1);//Bronze Scimitar
	    player.getInventory().addItem(1333, 1);//Rune Scimitar
	   // player.getInventory().addItem(4587, 1);//Dragon Scimitar
	    player.getInventory().addItem(386, 250);//Shark
	    player.getInventory().addItem(15273, 100);//Rocktail
	    player.getInventory().addItem(2435, 15);//Prayer Potions
	    player.getInventory().addItem(2429, 10);//Attack Potions
	    player.getInventory().addItem(114, 10);//strength Potions
	    player.getInventory().addItem(2433, 10);//Defence Potions
	    player.getInventory().addItemMoneyPouch(995, 2500000);//2.5m cash 
	    player.getInventory().addItem(841, 1); // short bow
	    player.getInventory().addItem(882, 1000);  // bronze arrows
	    player.getInventory().addItem(1381, 1);  // staff
	    player.getInventory().addItem(558, 1000);  // mind rune
	    player.getInventory().addItem(554, 1000);  // fire rune
	    player.getInventory().addItem(562, 1000); // chaos rune
		/*player.getBank().addItems(new Item{2347},true); //tools
		player.getBank().addItem(1755,true);
		player.getBank().addItem(946,true);
		player.getBank().addItem(1265,true);
		player.getBank().addItem(1351,true);
		player.getBank().addItem(303,true);*/
        FriendChatsManager.joinChat("Help", player);
		FriendChatsManager.refreshChat(player);
		//player.getPackets().sendGameMessage("Welcome to Zaria!");
		player.getHintIconsManager().removeUnsavedHintIcon();
		player.getMusicsManager().reset();
		player.getCombatDefinitions().setAutoRelatie(false);
		player.getCombatDefinitions().refreshAutoRelatie();
		StarterMap.getSingleton().addIP(ip);
	}
}