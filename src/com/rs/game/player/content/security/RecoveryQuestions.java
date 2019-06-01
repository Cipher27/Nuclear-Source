package com.rs.game.player.content.security;

import com.rs.game.player.Player;

public final class RecoveryQuestions {

	public static void loadRecoveryInfo(Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Recovery Information");
		player.getPackets().sendIComponentText(275, 10, "Having recovery questions set ensures that your account will");
		player.getPackets().sendIComponentText(275, 11, "remain inaccessible to account hijackers. So setting them");
		player.getPackets().sendIComponentText(275, 12, "as soon as possible is very important.");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 14, "Setting your recoveries is very easy. Execute the command");
		player.getPackets().sendIComponentText(275, 15, "give to you for each question.");
		player.getPackets().sendIComponentText(275, 16, "");
		if(player.recov1Set) {
			player.getPackets().sendIComponentText(275, 17, "<col=ff0000>SET</col>");
			player.getPackets().sendIComponentText(275, 18, "<str><col=ff0000>Date of Birth</col>");
			player.getPackets().sendIComponentText(275, 19, "<str>Using a six-digit numeric format, enter your date of birth.");
			player.getPackets().sendIComponentText(275, 20, "<str>Command: ::recov1");
			player.getPackets().sendIComponentText(275, 21, "<str>Here's an example - ::recov1 12/12/2013");
		} else {
			player.getPackets().sendIComponentText(275, 17, "");
			player.getPackets().sendIComponentText(275, 18, "<col=ff0000>Date of Birth</col>");
			player.getPackets().sendIComponentText(275, 19, "Using a six-digit numeric format, enter your date of birth.");
			player.getPackets().sendIComponentText(275, 20, "Command: ::recov1");
			player.getPackets().sendIComponentText(275, 21, "Here's an example - ::recov1 12/12/2013");
		}
		player.getPackets().sendIComponentText(275, 22, "");
		if(player.recov2Set) {
			player.getPackets().sendIComponentText(275, 23, "<col=ff0000>SET</col>");
			player.getPackets().sendIComponentText(275, 24, "<str><col=ff0000>Name of first pet</col>");
			player.getPackets().sendIComponentText(275, 25, "<str>Command: ::recov2");
			player.getPackets().sendIComponentText(275, 26, "<str>Here's an example - ::recov2 Charlotte");
		} else {
			player.getPackets().sendIComponentText(275, 23, "");
			player.getPackets().sendIComponentText(275, 24, "<col=ff0000>Name of first pet</col>");
			player.getPackets().sendIComponentText(275, 25, "Command: ::recov2");
			player.getPackets().sendIComponentText(275, 26, "Here's an example - ::recov2 Charlotte");
		}
		player.getPackets().sendIComponentText(275, 27, "");
		if(player.recov3Set) {
			player.getPackets().sendIComponentText(275, 28, "<col=ff0000>SET</col>");
			player.getPackets().sendIComponentText(275, 29, "<str><col=ff0000>Year you started playing Rs</col>");
			player.getPackets().sendIComponentText(275, 30, "<str>Command: ::recov3");
			player.getPackets().sendIComponentText(275, 31, "<str>Here's an example - ::recov3 2013");
		} else {
			player.getPackets().sendIComponentText(275, 28, "");
			player.getPackets().sendIComponentText(275, 29, "<col=ff0000>Year you started playing Rs</col>");
			player.getPackets().sendIComponentText(275, 30, "Command: ::recov3");
			player.getPackets().sendIComponentText(275, 31, "Here's an example - ::recov3 2013");
		}
		for(int i=32;i<300;i++)
			player.getPackets().sendIComponentText(275, i, "");
	}
}
