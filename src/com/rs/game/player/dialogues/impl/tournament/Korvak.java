package com.rs.game.player.dialogues.impl.tournament;

import java.util.ArrayList;

import com.rs.game.WorldTile;
import com.rs.game.minigames.tournament.Information;
import com.rs.game.minigames.tournament.Tournament;
import com.rs.game.minigames.tournament.TournamentControler;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.robot.Robot;


public class Korvak extends Dialogue {

	int npcId;

	@Override
	public void finish() {

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue("What would you like to talk about?",
					"Tournaments", "Fun matches", "Combat settings", "Nothing");
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				stage = 20;
				boolean inTournament = false;
				if (inTournament) {
					sendOptionsDialogue("What would you like to say?",
							"I want to start a tournament", "I want to join a tournament",
							"What are tournaments?", "Go back");
				} else {
					sendOptionsDialogue("What would you like to say?",
							"I want to start a tournament", "I want to join a tournament",
							"What are tournaments?", "Go back");
				}
			} else if (componentId == OPTION_2) {
				stage = 21;// go back
				sendOptionsDialogue("What would you like to say?",
						"I want to start a fun match", "What are fun matches?",
						"How am I doing in fun matches?", "Go back");
			} else if (componentId == OPTION_3) {
				stage = 23;
				sendOptionsDialogue("What would you like to change?",
						"Autocasting after wearing items",
						"Stopping combat after wearing items",
						"Stopping combat after eating/drinking", "Go back");
			} else if (componentId == OPTION_4) {
				stage = -2;
				sendPlayerDialogue(9827, "I don't want to talk to you Goodbye!");
			} else
				end();
		} else if (stage == 20) {
			if (componentId == OPTION_1) {
				stage = -2;
				sendNPCDialogue(
						npcId,
						9827,
						"Hmm... Tournaments are nearly complete... Talk to me tomorrow to see if I can help.");
				// sendOptionsDialogue("What would you like to say?", "", "");
				// TODO - tournaments
			} else if (componentId == OPTION_2) {
				stage = -2;
				sendNPCDialogue(
						npcId,
						9827,
						"Hmm... Tournaments are nearly complete... Talk to me tomorrow to see if I can help.");
				// TODO - Join a tournament
			} else if (componentId == OPTION_3) {// go back
				stage = 14;
				sendNPCDialogue(npcId, 9827,
						"Tournaments consist of one-vs-one battles, or matches...");
			} else if (componentId == OPTION_4) {// go back
				stage = 0;
				sendOptionsDialogue("What would you like to talk about?",
						"Tournaments", "Fun matches", "Combat settings",
						"Nothing");
			} else
				end();
		} else if (stage == 21) {
			if (componentId == OPTION_1) {
				stage = 2;
				sendOptionsDialogue("What would you like to say?",
						"I want to use my own equipment",
						"I want to use the equipment provided",
						"Tell me more about equipment options");
			} else if (componentId == OPTION_2) {
				stage = 22;
				sendNPCDialogue(
						npcId,
						9827,
						"In a fun match, you will fight one person for one match. This player will have the same levels as you. If you win, you'll be rewarded by the gods.");
			} else if (componentId == OPTION_3) {
				stage = 0;
			//	final double n = (int) (((double) player.matchesWon / (double) player.matchesLost) * 100);
			//	final double ratio = (n / 100);
				sendNPCDialogue(
						npcId,
						9827,
						"It appears that you have "
							//	+ player.matchesWon
								+ " matches won and "
							//	+ player.matchesLost
								+ " matches lost. This gives you a win to lose ratio of ");
							//	+ ratio + ".");
			} else if (componentId == OPTION_4) {// go back
				stage = 0;
				sendOptionsDialogue("What would you like to talk about?",
						"Tournaments", "Fun matches", "Combat settings",
						"Nothing");
			} else
				end();
		} else if (stage == 22) {
			stage = 21;// go back
			sendOptionsDialogue("What would you like to say?",
					"I want to start a fun match", "What are fun matches?",
					"How am I doing in fun matches?", "Go back");
		} else if (stage == 23) {// combat options
			if (componentId == OPTION_1) {// Autocasting after wearing items
				stage = 31;
				sendOptionsDialogue("What would you like to say?",
						"Stop autocasting after wearing items",
						"Keep autocasting after wearing items", "Go back");
			} else if (componentId == OPTION_2) {
				stage = 32;
				sendOptionsDialogue("What would you like to say?",
						"Stop attacking after wearing items",
						"Keep attacking after wearing items", "Go back");
			} else if (componentId == OPTION_3) {
				stage = 34;
				sendOptionsDialogue("What would you like to say?",
						"Stop attacking after eating/drinking",
						"Keep attacking after eating/drinking", "Go back");
			} else if (componentId == OPTION_4) {// go back
				stage = 0;
				sendOptionsDialogue("What would you like to talk about?",
						"Tournaments", "Fun matches", "Combat settings",
						"Nothing");
			} else
				end();
		} else if (stage == 31) {
			if (componentId == OPTION_1) {// stop autocasting
				stage = 33;
				//player.setAutocastingAfterWearing(false);
				sendNPCDialogue(npcId, 9827,
						"You will stop autocasting after wearing items.");
			} else if (componentId == OPTION_2) {// keep autocasting
				stage = 33;
				//player.setAutocastingAfterWearing(true);
				sendNPCDialogue(npcId, 9827,
						"You will keep autocasting after wearing items.");
			} else if (componentId == OPTION_3) {// go back
				stage = 23;
				sendOptionsDialogue("What would you like to change?",
						"Autocasting after wearing items",
						"Stopping combat after wearing items",
						"Stopping combat after eating/drinking", "Go back");
			} else
				end();
		} else if (stage == 32) {
			if (componentId == OPTION_1) {// stop attacking
				stage = 33;
			//	player.setCombatAfterWearing(false);
				sendNPCDialogue(npcId, 9827,
						"You will stop attacking after wearing items.");
			} else if (componentId == OPTION_2) {// keep attacking
				stage = 33;
				//player.setCombatAfterWearing(true);
				sendNPCDialogue(npcId, 9827,
						"You will keep attacking after wearing items.");
			} else if (componentId == OPTION_3) {// go back
				stage = 23;
				sendOptionsDialogue("What would you like to change?",
						"Autocasting after wearing items",
						"Stopping combat after wearing items",
						"Stopping combat after eating/drinking", "Go back");
			} else
				end();
		} else if (stage == 34) {
			if (componentId == OPTION_1) {// stop attacking
				stage = 33;
			//	player.setCombatAfterConsuming(false);
				sendNPCDialogue(npcId, 9827,
						"You will stop attacking after eating/drinking.");
			} else if (componentId == OPTION_2) {// keep attacking
				stage = 33;
			//	player.setCombatAfterConsuming(true);
				sendNPCDialogue(npcId, 9827,
						"You will keep attacking after eating/drinking.");
			} else if (componentId == OPTION_3) {// go back
				stage = 23;
				sendOptionsDialogue("What would you like to change?",
						"Autocasting after wearing items",
						"Stopping combat after wearing items",
						"Stopping combat after eating/drinking", "Go back");
			} else
				end();

		} else if (stage == 33) {
			stage = 23;
			sendOptionsDialogue("What would you like to change?",
					"Autocasting after wearing items",
					"Stopping combat after wearing items",
					"Stopping combat after eating/drinking", "Go back");
			
		} else if (stage == 2) {
			if (componentId == OPTION_1) {// my own equipment
				useMyOwnEquipment();
				end();
			} else if (componentId == OPTION_2) {// equipment provided
				stage = 4;
				player.getDialogueManager().startDialogue("TournamentDialogue",
						true);
			} else if (componentId == OPTION_3) {// which one?
				stage = 3;
				sendNPCDialogue(
						npcId,
						9827,
						"If you use your own equipment, you'll have to bring your own food and drinks too.");
			} else
				end();
		} else if (stage == 3) {
			stage = 4;
			sendNPCDialogue(npcId, 9827,
					"However, if you use the equipment provided, you don't need to bring anything.");
		} else if (stage == 4) {
			stage = 5;
			sendNPCDialogue(
					npcId,
					9827,
					"If you bring your own equipment, I'll pair you up with someone using similar equipment...");
		} else if (stage == 5) {
			stage = 6;
			sendNPCDialogue(
					npcId,
					9827,
					"But if you use the equipment provided, the other player will use the same equipment.");
		} else if (stage == 6) {
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Did I mention there will be rewards?");
		} else if (stage == 7) {
			stage = 8;
			sendPlayerDialogue(9827, "I like rewards!");
		} else if (stage == 8) {
			stage = 9;
			sendNPCDialogue(
					npcId,
					9827,
					"Yeah, if you win, the gods will give you free stuff! Now who doesn't want free stuff?");
		} else if (stage == 9) {
			stage = 10;
			sendNPCDialogue(npcId, 9827,
					"Now that you're a master with fun battles, would you like to play?");
		} else if (stage == 10) {
			stage = 11;
			sendOptionsDialogue("What would you like to say?",
					"I want to start a fun match!", "No.. you're crazy");
		} else if (stage == 11) {
			if (componentId == OPTION_1) {// start a fun match
				stage = 2;
				sendOptionsDialogue("What would you like to say?",
						"I want to use my own equipment",
						"I want to use the equipment provided",
						"Tell me more about these fun matches");
			} else if (componentId == OPTION_2) {// no
				stage = -2;
				sendPlayerDialogue(9827, "No.. you're crazy");
			} else
				end();
		} else if (stage == 14) {
			stage = 15;
			sendNPCDialogue(
					npcId,
					9827,
					"You can start tournaments and invite people to them. The armor and weapons depend on which options you select.");
		} else if (stage == 15) {
			stage = 16;
			sendNPCDialogue(
					npcId,
					9827,
					"If you don't want to start a tournament, you can also play fun matches. Would you like to try?");
		} else if (stage == 16) {
			stage = 17;
			sendOptionsDialogue("What would you like to say?",
					"I want to try a fun match",
					"No, I want to go back to Tournaments",
					"I don't want anything to do with you",
					"Go back");
		} else if (stage == 17) {
			if (componentId == OPTION_1) {
				stage = 2;
				sendOptionsDialogue("What would you like to say?",
						"I want to use my own equipment",
						"I want to use the equipment provided",
						"Tell me more about these fun matches");
			} else if (componentId == OPTION_2) {
				stage = -2;
				sendNPCDialogue(
						npcId,
						9827,
						"Hmm... Tournaments are nearly complete... Talk to me tomorrow to see if I can help.");
				// sendOptionsDialogue("What would you like to say?", "", "");
				// TODO - tournaments
			} else if (componentId == OPTION_3) {
				stage = -2;
				sendPlayerDialogue(9827,
						"I don't want anything to do with you.");
			} else if (componentId == OPTION_4) {
				stage = 20;
				sendOptionsDialogue("What would you like to say?",
						"I want to start a tournament", "I want to join a tournament",
						"What are tournaments?", "Go back");
			} else
				end();
		} else
			end();
	}

	@Override
	public void start() {
		npcId = 1;
		if (5%2 == 154151445) {
			stage = -2;
			sendNPCDialogue(npcId, 9724,
					"They're trying.. they're all trying so hard...");
		} else {
			stage = -1;
			sendNPCDialogue(npcId, 9724, "Hello!");
		}
	}

	public void useMyOwnEquipment() {
		final int typeIndex = Information.getTypeIndex(player);
		final int combatIndex = Information.getCombatIndex(player, typeIndex);
		player.getControlerManager().startControler("TournamentControler",
				false);
		if (player.getControlerManager().getControler() instanceof TournamentControler
				&& !((TournamentControler) player.getControlerManager()
						.getControler()).canEnterMatch(combatIndex, typeIndex)) {
			player.getControlerManager().removeControlerWithoutCheck();
			end();
			return;
		}
		final String username = Robot.createName();
		final Player robot = new Robot("abc123", player, username, 2, 765, 545);
		final ArrayList<String> names = new ArrayList<String>();
		names.add(robot.getDisplayName());
		names.add(player.getDisplayName());
		robot.getSkills().passLevels(player);
		robot.setNextWorldTile(new WorldTile(player.getX(), player.getY(), player.getPlane()));
		robot.getControlerManager().startControler("TournamentControler", true);
		if (!(robot.getControlerManager().getControler() instanceof TournamentControler)
				|| (robot.getControlerManager().getControler() instanceof TournamentControler && !((TournamentControler) robot
						.getControlerManager().getControler()).canEnterMatch(
						combatIndex, typeIndex))) {
			player.sm("You can't play this match, try using the pre-set equipment sets.");
			robot.realFinish();
			player.getControlerManager().removeControlerWithoutCheck();
			end();
			return;
		}
		robot.getPrayer().setPrayerBook(
				Information.getPrayerBook(combatIndex, typeIndex, robot
						.getSkills().getLevel(Skills.PRAYER), robot.getSkills()
						.getLevel(Skills.DEFENCE)) == 1);
		robot.getAppearence().generateAppearenceData();
		new Tournament(names, combatIndex, typeIndex, true, true, false, 100);
		player.closeInterfaces();
	}

}
