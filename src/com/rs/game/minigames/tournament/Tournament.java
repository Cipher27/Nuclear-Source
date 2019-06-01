package com.rs.game.minigames.tournament;

import java.util.ArrayList;
import java.util.List;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;


public class Tournament {

	private final List<String> players;
	private final int setId;
	private boolean allOrNothing;
	private final boolean funMatch;
	private boolean spawnedEquipment;
	private final int startPlayers;
	// private int entryFee;
	private final int prizeAmount;
	private final int switchSetId;
	private int combatIndex;
	private final int typeIndex;

	public final static String[] FIGHT = { "fight", "match", "battle" };

	public final static String[] AGAINST = { "against", "with" };

	public Tournament(ArrayList<String> players, int combatIndex,
			int typeIndex, boolean allOrNothing, boolean funMatch,
			boolean spawnedEquipment, int entryFee) {
		this.players = new ArrayList<String>();
		this.players.addAll(players);
		this.setId = Information.SET_IDS[combatIndex][typeIndex];
		this.switchSetId = Information.CHEAT_SWITCH_SET_IDS[combatIndex][typeIndex];
		this.combatIndex = combatIndex;
		this.typeIndex = typeIndex;
		this.combatIndex = combatIndex;
		this.funMatch = funMatch;
		this.spawnedEquipment = spawnedEquipment;
		this.startPlayers = players.size();
		// this.entryFee = entryFee;
		this.prizeAmount = entryFee * startPlayers;
		updateTournament();
	}

	private int calculateWinnings(int index) {
		int winning = 1000000;
		for (int i = startPlayers; i > 0; i--) {
			final int win = (int) (winning / (i * 0.50));
			winning -= win;
			if (winning <= 0)
				winning = 0;
			if (i == index)
				return win;
		}
		return 0;
	}

	public int getSetId() {
		return setId;
	}

	public int getSwitchSetId() {
		return switchSetId;
	}

	private void giveWinnings(Player player) {
		if (player == null)
			return;
		if (funMatch) {
			if (Utils.getRandom(1) == 0)
				player.sm(Information.getMessage(combatIndex, typeIndex));
			else
				player.sm(Information.GOLD_MESSAGE[Utils
						.random(Information.GOLD_MESSAGE.length - 1)]);
			// player.sm(Utils.random(1) == 0 ?
			// "As a reward, they've decided to shower you with gold." :
			// "As a reward, some of their gold will now be yours.");
			player.getInventory().addItemMoneyPouch(995,
					Information.getReward(combatIndex, typeIndex));
			for (final Item item : Information.getRewardItems(combatIndex,
					typeIndex)) {
				if (player.isDonator() || Utils.getRandom(1) == 0)
					player.getInventory().addItemMoneyPouch(item);
				else
					player.sm("The gods couldn't give you your "
							+ ItemDefinitions.getItemDefinitions(item.getId())
									.getName()
							+ (item.getAmount() > 1 ? " X " + item.getAmount()
									: "") + ".");
			}
			return;
		}
		int amount = prizeAmount;
		final int currentSize = players.size();
		if (!allOrNothing) {// Less players the more money
			amount = calculateWinnings(currentSize);
		}
		if (amount > 0 && ((allOrNothing && currentSize == 1) || !allOrNothing)) {
			player.getInventory().addItem(995, amount);
		}
	}

	public boolean isSpawnedEquipment() {
		return spawnedEquipment;
	}

	private void removePlayer(String loser) {
		players.remove(loser);
	}

	/**
	 * Sends a message to all players in the tournament.
	 */

	public void sendTournamentMessage(String message) {
		for (final String name : players) {
			final Player player = World.getPlayerByDisplayName(name);
			if (player == null)
				continue;
			player.sendMessage(message);
		}
	}

	/**
	 * Called after player dies or logs out.
	 */

	public void sendWinner(Player winner, Player loser, String winnerName,
			String loserName) {
		sendTournamentMessage(Utils.getRandom(2) == 1 ? (loserName
				+ " lost the " + FIGHT[Utils.random(2)] + " "
				+ AGAINST[Utils.random(2)] + " " + winnerName + ".")
				: ("It appears in the " + FIGHT[Utils.random(2)] + " "
						+ AGAINST[Utils.random(2)] + " " + loserName + ", "
						+ winnerName + " has won!"));
		removePlayer(loser.getDisplayName());
		giveWinnings(winner);
		updateTournament();
		if (winner.isRobot())
			Information.addWin(combatIndex, typeIndex);
		else if (loser.isRobot())
			Information.addLose(combatIndex, typeIndex);
		//winner.addMatchWon();
		//loser.addMatchLost();
	}

	public void setSpawnedEquipment(boolean spawnedEquipment) {
		this.spawnedEquipment = spawnedEquipment;
	}

	/**
	 * Sends the matches for waiting players.
	 */

	public void updateTournament() {
		final List<Player> waitingPlayers = new ArrayList<Player>();
		for (final String name : players) {
			final Player player = World.getPlayerByDisplayName(name);
			if (player == null)// player has logged out
				continue;
			if (player.isRobot()) {
				(player).script = Information.CHEAT_SWITCH_TYPE[combatIndex][typeIndex];
				(player).script.init(player);
			} else if (player.isScriptingEnabled()) {
				player.script = Information.CHEAT_SWITCH_TYPE[combatIndex][typeIndex];
				player.script.init(player);
			}
			if (player.getControlerManager().getControler() instanceof TournamentControler
					&& ((TournamentControler) player.getControlerManager()
							.getControler()).canEnterMatch(combatIndex,
							typeIndex))
				waitingPlayers.add(player);
		}
		for (int index = 0; index < waitingPlayers.size(); index += 2) {
			if ((index + 1) >= waitingPlayers.size()) // odd
				return;
			new Match(waitingPlayers.get(index), waitingPlayers.get(index + 1),
					this);
		}
		/*
		 * for (Player player : waitingPlayers) { if (player.isRobot() &&
		 * player.getControlerManager().getControler() instanceof
		 * TournamentControler) { player.realFinish();
		 * removePlayer(player.getDisplayName()); } }
		 */
	}
}