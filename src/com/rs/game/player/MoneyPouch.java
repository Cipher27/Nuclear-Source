package com.rs.game.player;

import java.io.Serializable;

import com.rs.utils.Utils;

/**
 * 
 * @author Josh'
 * @code from my old source fixed up a tad
 *
 */
public class MoneyPouch implements Serializable {
	
	private static final long serialVersionUID = -7551351100284866755L;

	private transient Player player;

    public void setPlayer(Player player) {
    	this.player = player;
    }

    public void examine() {
    	player.getPackets().sendGameMessage("Your money pouch contains " + Utils.getFormattedNumber(coins) + " coin"+(coins > 1 ? "s" : "")+".");
    }
    
    private void refreshCoins() {
    	player.getPackets().sendRunScript(5560, coins);
    }
    
    private boolean hasPouchOpen;
    
    public boolean sendAddOrRemove(int amount, boolean remove) {
    	return sendAddOrRemove(amount, remove, 2);
    }
    
    public void switchPouch() {
    	hasPouchOpen = !hasPouchOpen;
    	switchP();
    }

    public void init() {
		if (hasPouchOpen)
			switchP();
		refreshCoins();
    }

    private void switchP() {
    	player.getPackets().sendRunScript(5557, 1);
    }

    private int coins;

    public void withdrawPouch() {
		player.getPackets().sendInputIntegerScript(true, "Your money pouch contains " + Utils.getFormattedNumber(coins) + " coin"+(coins > 1 ? "s" : "")+".<br>How many would you like to withdraw?");
		player.getTemporaryAttributtes().put("withdrawingPouch", Boolean.TRUE);
    }
    
     public boolean sendAddOrRemove(int amount, boolean remove, int type) {
    	if (amount <= 0)
    	    return false;
    	if (remove) {
    	    if (type == 0) {
    		if (amount > coins)
    		    amount = coins;
    		int invAmt = player.getInventory().getNumerOf(995);
    		if (coins != 0 && invAmt + amount <= 0) {
    		    amount = Integer.MAX_VALUE - invAmt;
    		    player.getPackets().sendGameMessage("Not enough space in your inventory.");
    		}
    	} else if (type == 2 && amount > coins) {
    		int removeAmt = amount - coins;
    		if (player.getInventory().getNumerOf(995) < removeAmt || !player.getInventory().containsItem(995, removeAmt))
    		    return false;
    			player.getInventory().deleteItem(995, removeAmt);
    			amount -= removeAmt;
    	    }
    	} else if (!remove && amount + coins <= 0) {
    	    if (type == 2)
    	    	player.getInventory().addItem(995, amount - (Integer.MAX_VALUE - coins));
    	    else
    	    	player.getPackets().sendGameMessage("Your money pouch can't hold anymore coins.");
    	    amount = Integer.MAX_VALUE - coins;
    	}
    	if (amount == 0)
    	    return true;
    	player.getPackets().sendGameMessage(Utils.getFormattedNumber(amount) + " coin"+(coins > 1 ? "s" : "")+" ha"+(coins > 1 ? "ve" : "s")+" been " + (remove ? "removed" : "added") + " "+(remove ? "from" : "to")+" your money pouch.", true);
    	if (type == 0) {
    	    if (remove) {
    		if (!player.getInventory().addItem(995, amount))
    		    return false;
    	    } else
    		player.getInventory().deleteItem(995, amount);
    	}
    	setAmount(amount, remove);
    	return true;
    }
    
    public void setAmount(int amt, boolean type) {
		if (type)
		    coins -= amt;
		else
		    coins += amt;
		player.getPackets().sendRunScript(5561, type ? 0 : 1, amt);
		refreshCoins();
    }

    public int getCoinsAmount() {
    	return coins;
    }

	public int getTotal() {
		return coins;
	}



	
}