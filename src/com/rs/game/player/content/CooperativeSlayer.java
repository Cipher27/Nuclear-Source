package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class CooperativeSlayer implements Serializable {
			
	private static final long serialVersionUID = -5833463661237303707L;
	
	public static int bonusXP = Utils.getRandom(3) + 2;
	
	public void handleLogout(Player player) {
	
	}
	
	public void sendInvite(final Player player) {
	}

	public void handleInviteButtons(Player invited, int interfaceId, int componentId) {
		
	}
}