package com.rs.game.player;

import java.util.TimerTask;

import com.rs.cores.CoresManager;

public class LoyaltyManager {
	
	
	//todo interface

	private static final int INTERFACE_ID = 1143;
	private transient Player player;

	public LoyaltyManager(Player player) {
		this.player = player;
	}

	public void openLoyaltyStore(Player player) {
		player.getPackets().sendWindowsPane(INTERFACE_ID, 0);
	}

	public void startTimer() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int timer = 1800;

			@Override
			public void run() {
				if (timer == 1) {
						player.getPointsManager().setLoyaltyTokens(player.getPointsManager().getLoyaltyTokens() + 100);
						player.getPackets().sendGameMessage("<col=008000>You have received 100 Loyalty Tokens for playing for 30 minutes!");
					timer = 1800;
						}
				if (timer > 0) {
					timer--;
				}
			}
		}, 0L, 1000L);
	}
}