package com.rs.game.player.dialogues.impl;

import java.text.DecimalFormat;

import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since Aug 12, 2013
 */
public class Player_Shop_Manager extends Dialogue {

 @Override
	public void start() {
		npcId = 13295;
		sendNPCDialogue(npcId, 9827, "Hello, I am the player shop manager. I handle all interactions with the shops of all players. Now, what would you like?");
	}
	
	public static String formatNumber(long count) {
		return new DecimalFormat("#,###,##0").format(count).toString();
	}
	
 @Override
	public void run(int interfaceId, int option) {
		switch(stage) {
		case -1:
			sendOptionsDialogue("Select an Option", "What are player shops?", "I want to manage my shop.", "I would like to manage my Vault.");
			stage = 0;
			break;
		case 0:
			switch(option) {
			case OPTION_1:
				sendNPCDialogue(npcId, 9827, "Player shops are shops set up by players themselves. They can add and remove items at any time with me. The player can also set the price of any item in their shop.");
				stage = -1;
				break;
			case OPTION_2:
				player.getPlayerShop().sendOwnShop();
				end();
				break;
			case OPTION_3:
				player.getAttributes().replace("editing_shop_item", Boolean.FALSE);
    			player.getAttributes().put("remove_money1", Boolean.TRUE);
    			player.getPackets().sendRunScript(108, new Object[] { "How much would you like to withdraw? Your Vault contains <col=FF0000>" + formatNumber(player.getVault()) + "</col> coins."});
				end();
				break;
			}
			break;
		}
	}

 @Override
	public void finish() {

	}

	int npcId;

}