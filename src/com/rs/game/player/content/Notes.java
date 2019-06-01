package com.rs.game.player.content;


import java.io.Serializable;

import com.rs.game.player.Player;


/**
 * @author `Discardedx2, edited by KingFox
 */
public final class Notes implements Serializable {
	private static final long serialVersionUID = 5564620907978487391L;


	/**
	 * The player.
	 */
	private transient Player player;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * Unlocks the note interface
	 * 
	 * @param player
	 *            The player to unlock the note interface for.
	 */
	public void unlock() {		 
		player.getPackets().sendIComponentSettings(34, 9, 0, 30, 2621470);
		player.getPackets().sendHideIComponent(34, 3, true);
		player.getPackets().sendHideIComponent(34, 44, true);
		
		
	}
}
		 
	