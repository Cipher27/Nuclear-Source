package com.rs.game.player.dialogues;

import com.rs.game.player.content.TicketSystem;
/**
 * 
 * @author Andre | 423 Million
 * Purpose: Detailed ticket system
 *
 */
public class TicketHelp extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("What type of help do you need?",
				"Password",
				"Donations",
				"Account Recovery/Appeal",
				"Other");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				stage = 1;
				sendOptionsDialogue("Password Help",
						"I've Forgotten my password",
						"My Account has been Stolen!",
						"My password is not working.");
			} else if (componentId == OPTION_2) {
				stage = 2;
				sendOptionsDialogue("Donation Help",
						"I've donated, what now?",
						"I have not received my donation.",
						"Other question about donating.");
			} else if (componentId == OPTION_3) {
				stage = 3;
				sendOptionsDialogue("Account Recovery/Appeal",
						"I cannot login with my account.",
						"I've made an appeal for my account. What now?",
						"Account help (general)");
			} else if (componentId == OPTION_4) {
				stage = 4;
				sendOptionsDialogue("Other",
						"Account help.",
						"Player report/help.",
						"Forums help.",
						"Urgent/Staff needed.");
			} else
				end();
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				TicketSystem.requestTicket(player, "Passwords - forgotten password.");
				end();
			} else if (componentId == OPTION_2) {
				TicketSystem.requestTicket(player, "Passwords - stolen password.");
				end();
			} else if (componentId == OPTION_3) {
				TicketSystem.requestTicket(player, "Passwords - not working.");
				end();
			} else
				end();
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				TicketSystem.requestTicket(player, "Donations - Donated.");
				end();
			} else if (componentId == OPTION_2) {
				TicketSystem.requestTicket(player, "Donations - Not received.");
				end();
			} else if (componentId == OPTION_2) {
				TicketSystem.requestTicket(player, "Donations - General");
				end();
			} else
				end();
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				TicketSystem.requestTicket(player, "Acc. Recovery/Appeal - can't login.");
				end();
			} else if (componentId == OPTION_2) {
				TicketSystem.requestTicket(player, "Acc. Recovery/Appeal - acc. appeal made.");
				end();
			} else if (componentId == OPTION_3) {
				TicketSystem.requestTicket(player, "Acc. Recovery/Appeal - General account help.");
				end();
			} else
				end();
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				TicketSystem.requestTicket(player, "Other - Account help.");
				end();
			} else if (componentId == OPTION_2) {
				TicketSystem.requestTicket(player, "Other - Player report/help.");
				end();
			} else if (componentId == OPTION_3) {
				TicketSystem.requestTicket(player, "Other - Forum help.");
				end();
			} else if (componentId == OPTION_4) {
				TicketSystem.requestTicket(player, "Other - Urgent/Staff needed.");
				end();
			} else
				end();
		} else
			end();
	}

	@Override
	public void finish() {
		player.getPackets().sendGameMessage("Ticket Request Done/Finished.");

	}

}