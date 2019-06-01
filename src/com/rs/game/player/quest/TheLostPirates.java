package com.rs.game.player.quest;

import com.rs.game.player.Player;
import com.rs.game.player.Skills;


public class TheLostPirates {
	
	/**
	 * The interface before the player has started the quest.
	 * 
	 * @param player
	 */
	public static void handleQuestStartInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 3);
		player.getPackets().sendIComponentText(275, 1, "The Lost Pirate");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to the</col> <col=660000>pirate</col> <col=330099>at the</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=660000>dock</col> <col=330099>on our home.");
		for (int i = 13; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	/**
	 * Sends the configuration to the quest tab and sets Cook's Assistant into progress.
	 * 
	 * @param player
	 */
	public static void handleProgressQuest(final Player player) {
		player.startedPirateQuest = true;
		player.progressPirateQuest = true;
	}
	
	/**
	 * The interface during the quest when the player gathers the ingredients.
	 * 
	 * @param player
	 */
	public static void handleProgressKillerQuestInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 10);
		player.getPackets().sendIComponentText(275, 1, "The Lost Pirate");
		player.getPackets().sendIComponentText(275, 10, "Finding the killers");
		if (player.pKillerFinding == 1) {
		player.getPackets().sendIComponentText(275, 11, "<str><col=330099>I should ask </col> <col=660000>Max</col> for more information at <col=330099>Home</col>.");
		} if (player.pKillerFinding > 1) {
		player.getPackets().sendIComponentText(275, 11, "<v><str><col=330099>I should ask </col> <col=660000>Max</col> for more information at <col=330099>Home</col>.</v>");
		} if (player.pKillerFinding == 1) {
		player.getPackets().sendIComponentText(275, 12, "");
		}if (player.pKillerFinding == 1) {
		player.getPackets().sendIComponentText(275, 12, "<str><col=660000>kitchen</col> <col=330099>on the ground floor of</col> <col=660000>Lumbridge Castle.</col>");
		}
		
		for (int i = player.pKillerFinding ==1 ? 12:player.pKillerFinding ==2 ? 13: 20; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	/**
	 * Handles the reward the player gets when completing the quest.
	 * 
	 * @param player
	 */
	public static void handleQuestComplete(final Player player) {
		player.inProgressCooksAssistant = false;
		player.completedCooksAssistantQuest = true;
		player.questPoints += 1;
		player.getSkills().addXp(Skills.COOKING, 300);
		if (player.getInventory().getFreeSlots() < 2) {
			player.getBank().addItem(328, 20, true);
			player.getPackets().sendGameMessage("You do not have enough inventory space. Your reward has been sent to your bank.");
		} else {
			player.getInventory().addItemMoneyPouch(995, 500);
			player.getInventory().addItem(328, 20);
		}
		player.getPackets().sendConfig(29, 2);
		player.getPackets().sendConfig(101, player.questPoints); // Quest Points
		player.getInterfaceManager().sendInterfaces();
		player.getPackets().sendUnlockIComponentOptionSlots(190, 15, 0, 201, 0, 1, 2, 3);
	}
	
	/**
	 * The interface shown after the player has completed the quest.
	 * 
	 * @param player
	 */
	public static void handleQuestCompletionTabInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 12);
		player.getPackets().sendIComponentText(275, 1, "Cook's Assistant");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<str><col=330099>I can start this quest by speaking to the</col> <col=660000>cook</col> <col=330099>in the</col>");
		player.getPackets().sendIComponentText(275, 12, "<str><col=660000>kitchen</col> <col=330099>on the ground floor of</col> <col=660000>Lumbridge Castle.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>It's the</col> <col=660000>Duke of Lumbridge's</col> <col=330099>birthday and I have to help</col>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>his</col> <col=660000>Cook</col> <col=330099>make him a</col> <col=660000>birthday cake.</col> <col=330099>To do this I need to</col>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>bring him the following ingredients:</col>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>I have found a</col> <col=660000>bucket of milk</col><col=330099> to give the cook.</col>");
		player.getPackets().sendIComponentText(275, 18, "<str><col=330099>I have found a</col> <col=660000>pot of flour</col> <col=330099>to give the cook.</col>");
		player.getPackets().sendIComponentText(275, 19, "<str><col=330099>I have found an</col> <col=660000>egg</col> <col=330099>to give the cook.</col>");
		player.getPackets().sendIComponentText(275, 20, "");
		player.getPackets().sendIComponentText(275, 21, "<col=660000>QUEST COMPLETE</col>");
	}
	
	/**
	 * The interface as the player gets the reward for completion of the quest.
	 * 
	 * @param player
	 */
	public static void handleQuestCompleteInterface(final Player player) {
		player.getInterfaceManager().sendInterface(277);
		player.getPackets().sendIComponentText(277, 4, "You have completed The lost pirates.");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "1 Quest Point");
		player.getPackets().sendIComponentText(277, 11, "Roctail soup recipe");
		player.getPackets().sendIComponentText(277, 12, "250 power tokens");
		player.getPackets().sendIComponentText(277, 13, "2 spin tickets");
		player.getPackets().sendIComponentText(277, 14, "");
		player.getPackets().sendIComponentText(277, 15, " ");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 26313, 1);
		player.getPackets().sendGameMessage("Congratulations! You have completed the The lost Pirates quest!");
	}
	
	

}
