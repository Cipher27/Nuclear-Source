package com.rs.game.player;

import java.io.Serializable;

import com.rs.utils.ChatColors;
import com.rs.utils.Utils;

/**
 *
 * @author _Hassan <https://www.rune-server.ee/members/_hassan/> Created: 23 Jul
 *         2017 ~ Zaria 718-830
 *
 */

public class LoyaltyPointsManager implements Serializable {

	private static final long serialVersionUID = -2031436460894647315L;

	private Player player;

	private int LOYALTY_PTS;

	final int BASE_POINTS = 13000;

	private double MULTIPLIER = 0.1;

	public LoyaltyPointsManager(Player player) {
		this.player = player;
	}

	/* adds a multiplier after the points has been received. Resets at 2.0 */
	public double addMultiplier() {
		if (MULTIPLIER == 2.0)
			return MULTIPLIER = 0.1;
		return MULTIPLIER += 0.1;
	}

	public void addPoints() {

		int pts = (int) (MULTIPLIER * BASE_POINTS);

		setLOYALTY_PTS(getLOYALTY_PTS() + pts);
		addMultiplier();

		player.sm("<col=" + ChatColors.GREEN + ">You have received " + Utils.getFormattedNumber(pts) + " loyalty points! Next hour you will receive: " + Utils.getFormattedNumber((int) (BASE_POINTS * MULTIPLIER)) + ".");

	}

	public int getLOYALTY_PTS() {
		return LOYALTY_PTS;
	}

	public void setLOYALTY_PTS(int lOYALTY_PTS) {
		LOYALTY_PTS = lOYALTY_PTS;
	}

}
