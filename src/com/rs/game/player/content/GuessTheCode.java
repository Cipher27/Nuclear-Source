package com.rs.game.player.content;

import java.util.UUID;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.ChatColors;
import com.rs.utils.Utils;

/**
 *
 * @author _Hassan <https://www.rune-server.ee/members/_hassan/> Created: 21 Jul
 *         2017 ~ Zaria 718-830
 *
 */

public class GuessTheCode {

	public int GAME_POT;

	private final int ENTRY_FEE = 10000;
	//start price
	private final int START_PRICE = 1000000;


	private String RANDOM_CODE;

	public GuessTheCode() {
		setRandomCode(UUID.randomUUID().toString().substring(0, 3));
		GAME_POT = START_PRICE;
	}

	void enter(Player player) {
		if (!player.getInventory().containsItem(995, ENTRY_FEE)) {
			player.sm("You must pay the entry free of " + Utils.getFormattedNumber(ENTRY_FEE, ',') + " coins to enter.");
			return;
		}

		/* just a security check to see if the game pot went over max integer */
		if (GAME_POT >= Integer.MAX_VALUE) {
			GAME_POT = Integer.MAX_VALUE;
			player.sm("You cannot enter at this time. The Game pot is currenly full.");
			return;
		}

		player.getInventory().deleteItem(995, ENTRY_FEE);
		GAME_POT += ENTRY_FEE;

		player.getPackets().sendInputNameScript("ENTER YOUR GUESS, HINT: (TODO)");
		player.getTemporaryAttributtes().put("GuessTheCode", Boolean.TRUE);
	}

	void checkAnswer(Player player, String input) {
		if (!isWinner(input)) {
			player.sm("You unfortunately guessed wrong. You can try again by re-entering.");
			return;
		}
		World.sendWorldMessage("<img=7><col=" + ChatColors.RED + ">News: " + player.getDisplayName() + " has guessed the code correctly (" + RANDOM_CODE + ") and won the pot of " + Utils.getFormattedNumber(GAME_POT, ',') + " GP!", false);

		// generates new code
		new GuessTheCode();

		if (player.getInventory().hasFreeSlots())
			player.getInventory().addItem(995, GAME_POT);
		else
			player.getBank().addItem(995, GAME_POT, true);

		player.sm("<col=" + ChatColors.GREEN + ">" + "Your prize has been added to your " + (player.getInventory().hasFreeSlots() ? "inventory" : "bank") + ".");

		/*
		 * TODO, CHECK IF BANK AND INVENTORY ALREADY CONTAINS MAX CASH, IF IT
		 * DOES, MAKE IT SO THEY CAN CLAIM THEIR PRIZE LATER AT THE NPC
		 */

	}

	boolean isWinner(String guess) {
		return guess.equalsIgnoreCase(RANDOM_CODE);
	}

	public String getRANDOM_CODE() {
		return RANDOM_CODE;
	}

	public String setRandomCode(String code) {
		return this.RANDOM_CODE = code;
	}

}
