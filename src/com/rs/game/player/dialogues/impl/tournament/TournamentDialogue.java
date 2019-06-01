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


public class TournamentDialogue extends Dialogue {

	private int combatIndex;
	private boolean spawnedEquipment;

	@Override
	public void finish() {
	}

	@Override
	public void run(int interfaceId, int componentId) {
		stage++;
		componentId = componentId == Dialogue.OPTION_1 ? 1
				: componentId == Dialogue.OPTION_2 ? 2
						: componentId == Dialogue.OPTION_3 ? 3
								: componentId == Dialogue.OPTION_4 ? 4
										: componentId == Dialogue.OPTION_5 ? 5
												: 1;
		switch (stage) {
		case 0:
			combatIndex = componentId - 1;
			if (combatIndex == 4) {
				end();
				return;
			}
			sendTypeOptions();
			break;
		case 1:
			int typeIndex = componentId - 1;
			final int spellbook = Information.SPELLBOOK[typeIndex];
			if (spellbook != player.getCombatDefinitions().getSpellBook()) {
				switch (spellbook) {
				case Information.REGULAR:
					player.sm("Your mind clears and you switch back to the regular spellbook.");
					break;
				case Information.ANCIENT:
					player.sm("Your mind clears and you switch to the ancient spellbook.");
					break;
				case Information.LUNAR:
					player.sm("Your mind clears and you switch to the lunar spellbook.");
					break;
				}
			}
			player.getCombatDefinitions().setSpellBook(spellbook);
			player.getControlerManager().startControler("TournamentControler",
					true);
			if (player.getControlerManager().getControler() instanceof TournamentControler
					&& !((TournamentControler) player.getControlerManager()
							.getControler()).canEnterMatch(combatIndex,
					typeIndex)) {
				player.getControlerManager().removeControlerWithoutCheck();
				end();
				return;
			}
			final String username = Robot.createName();
			final Player robot = new Robot("abc123", player, username, 2, 765,
					545);
			final ArrayList<String> names = new ArrayList<String>();
			names.add(robot.getDisplayName());
			names.add(player.getDisplayName());
			robot.getSkills().passLevels(player);
			robot.setNextWorldTile(new WorldTile(player.getX(), player.getY(), player.getPlane()));
			robot.getControlerManager().startControler("TournamentControler",
					true);
			robot.getPrayer().setPrayerBook(
					Information.getPrayerBook(combatIndex, typeIndex, robot
							.getSkills().getLevel(Skills.PRAYER), robot
							.getSkills().getLevel(Skills.DEFENCE)) == 1);
			robot.getAppearence().generateAppearenceData();
			new Tournament(names, combatIndex, typeIndex, true, true, true, 100);
			player.closeInterfaces();
			end();
			break;
		}
	}

	public void sendCombatOptions() {
		sendOptionsDialogue("Which type of defence?", "Pure", "Turmoil pure",
				"Zerker", "Master", "Nevermind");
	}

	public void sendTypeOptions() {
		sendOptionsDialogue("Which type of combat style?", "Melee", "Range",
				"Mage", "Hybrid", "Tribrid");
	}

	@Override
	public void start() {
		if (parameters.length > 0) {
			spawnedEquipment = (boolean) parameters[0];
		}
		if (player.isCanPvp()
				/*|| player.getControlerManager().getControler() != null*/) {
			player.sendMessage("You can't start a match here.");
			end();
			return;
		}
		if (player.getFamiliar() != null) {
			player.sendMessage("You cannot start a match until your familiar is dismissed.");
			end();
			return;
		}
		if (player.getPet() != null) {
			player.sendMessage("You cannot start a match until your pet is dismissed.");
			end();
			return;
		}
		if (spawnedEquipment) {
			if (player.getInventory().getItems().getUsedSlots()
					+ player.getEquipment().getItems().getUsedSlots() > 0) {
				player.sendMessage("Please bank any items you're holding or wearing before you start a match.");
				end();
				return;
			}
		}
		sendCombatOptions();
	}

}
