package com.rs.game.player.dialogues.impl.instances;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.InstancedBossControler;
import com.rs.game.player.controlers.InstancedBossControler.Difficulty;
import com.rs.game.player.controlers.InstancedBossControler.Instance;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class InstancedLegionDialogue extends Dialogue {

	private Instance instance;
	private Difficulty difficulty;
	public static final int cost = 250000;

	@Override
	public void start() {
		instance = (Instance) parameters[0];
		player.instance = instance;
		sendOptionsDialogue("What would you like to do?", new String[] { "Enter boss room.", "Nevermind." });
		stage = START;
	}
	@Override
	public boolean run(String name) {
		joinBossSession(name);
		return true;
	}
	@Override
	public boolean run(int amount) {
		if (amount < 1 || amount > 100) {
			return true;
		}
		startBossSession(amount);
		end();
		return true;
	}
	
	public static final int START = 1, CHOOSE_TYPE = 2, PAY = 3;
	public static int MAXIMUM_PLAYERS = 4;
	public static final int CONFIRM_PAY = 5;
	private static final int MAXIMUMPLAYERS = 0;
	
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
			case START:
				if (componentId == OPTION_1) {
					if (instance == Instance.PRIMUS) {
						InstancedBossControler.startSession(player, Instance.PRIMUS, player.difficulty, 1);
						player.getInventory().deleteItem(28445, 1);
					} else if (instance == Instance.SECUNDUS) {
						InstancedBossControler.startSession(player, Instance.SECUNDUS, player.difficulty, 1);
						player.getInventory().deleteItem(28447, 1);
					} else if (instance == Instance.TERTIUS) {
						InstancedBossControler.startSession(player, Instance.TERTIUS, player.difficulty, 1);
						player.getInventory().deleteItem(28449, 1);
					} else if (instance == Instance.QUARTUS) {
			
						InstancedBossControler.startSession(player, Instance.QUARTUS, player.difficulty, 1);
						player.getInventory().deleteItem(28451, 1);
					} else if (instance == Instance.QUINTUS) {
						InstancedBossControler.startSession(player, Instance.QUINTUS, player.difficulty, 1);
						player.getInventory().deleteItem(28453, 1);
					} else if (instance == Instance.SEXTUS) {
						InstancedBossControler.startSession(player, Instance.SEXTUS, player.difficulty, 1);
						player.getInventory().deleteItem(28455, 1);
					} else
						player.setNextWorldTile(instance.getCoords());
					end();
				} else if (componentId == OPTION_2) {
					end();
					stage = CHOOSE_TYPE;
				}
				break;
			case CHOOSE_TYPE:
				if (componentId == OPTION_1) {
					difficulty = Difficulty.Normal;
				} else if (componentId == OPTION_2) {
					difficulty = Difficulty.Quick;
				} else if (componentId == OPTION_3) {
					difficulty = Difficulty.Hard;
				} else if (componentId == OPTION_4) {
					difficulty = Difficulty.QuickHard;
				} else if (componentId == OPTION_5) {
					difficulty = Difficulty.Practice;
				}
				player.difficulty = difficulty;
				if (player.isLegendaryDonator()) {// These donators don't pay
					player.getPackets().sendInputIntegerScript("Choose the maximum number of players in this battle. (1-100)");
					stage = MAXIMUMPLAYERS;
				} else {
					sendDialogue("Starting a new session against this foe cost gp."+ Utils.formatNumber(cost) +" Do you wish to pay?");
					stage = PAY;
				}
				break;
			case PAY:
				sendOptionsDialogue("Pay "+ Utils.formatNumber(cost) +" gp to start a new battle?", "Pay.", "Don't pay.");
				stage = CONFIRM_PAY;
				break;
			case CONFIRM_PAY:
				if (componentId == OPTION_1) {
					//player.sm("Legendary donators can create instanced boss session for free.");
					if (player.getInventory().getCoinsAmount() < cost) {
						player.sm("You don't have enough coins to start a new session.");
						sendDialogue("You don't have enough coins to start a new session.");
						end();
					} else {
						player.getPackets().sendInputIntegerScript("Choose the maximum number of players in this battle. (1-100)");
						player.getTemporaryAttributtes().put("PLAYER_AMOUNT", Boolean.TRUE);
						end();
					}
						
				} else if (componentId == OPTION_2) {
					end();
				}
				break;
		}
	}
	
	public void joinBossSession(String username) {
		Player target = World.getPlayerByDisplayName(username);
		end();
		if (target == null) {
			player.getDialogueManager().startDialogue("SimpleMessage", "Unable to find player.");
			return;
		}
		if (target.getControlerManager().getControler() == null) {

			return;
		}
		if (!(target.getControlerManager().getControler() instanceof InstancedBossControler)) {
			return;
		}
		InstancedBossControler.joinSession(player, target);
	}
	
	public void startBossSession(int maxPlayers) {
		InstancedBossControler.startSession(player, instance, difficulty, player.playerAmount);
		if (!player.isLegendaryDonator())
			player.getInventory().removeItemMoneyPouch(995, InstancedLegionDialogue.cost);
	}

	public void finish() {
		// TODO Auto-generated method stub

	}

}
