package com.rs.game.player.dialogues;

public class SkillerCape extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Select an option.",
				"I would like to see the requirements.", "I think im worth it.", "Never mind.");
				stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
	if (stage == -1) {
		if (componentId == OPTION_1) {
		sendOptionsDialogue("Select an option.",
		"Skiller cape",
		"Extreme Skiller cape",
		"Back");
		stage = 2;
		} 
		/*	player.getInterfaceManager().sendInterface(275);
		    player.getPackets().sendIComponentText(275, 1, "Skiller Cape Requirements");
		    player.getPackets().sendIComponentText(275, 10, "");
		    player.getPackets().sendIComponentText(275, 11, "<u>You can access this page by using the command ::skill</u>");
		    player.getPackets().sendIComponentText(275, 12, "");
		    player.getPackets().sendIComponentText(275, 13, "");
		    player.getPackets().sendIComponentText(275, 14, "");
		    player.getPackets().sendIComponentText(275, 15, "");
		    player.getPackets().sendIComponentText(275, 16, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		    player.getPackets().sendIComponentText(275, 17, "You are level " + player.getSkills().getLevel(Skills.COOKING) + "/99 in cooking.");
		    player.getPackets().sendIComponentText(275, 18, "You are level " + player.getSkills().getLevel(Skills.WOODCUTTING) + "/99 in woodcutting.");
		    player.getPackets().sendIComponentText(275, 19, "You are level " + player.getSkills().getLevel(Skills.FLETCHING) + "/99 in fletching.");
		    player.getPackets().sendIComponentText(275, 20, "You are level " + player.getSkills().getLevel(Skills.FISHING) + "/99 in fishing.");
		    player.getPackets().sendIComponentText(275, 21, "You are level " + player.getSkills().getLevel(Skills.FIREMAKING) + "/99 in firemaking.");
		    player.getPackets().sendIComponentText(275, 22, "You are level " + player.getSkills().getLevel(Skills.CRAFTING) + "/99 in crafting.");
		    player.getPackets().sendIComponentText(275, 23, "You are level " + player.getSkills().getLevel(Skills.SMITHING) + "/99 in smithing.");
		    player.getPackets().sendIComponentText(275, 24, "You are level " + player.getSkills().getLevel(Skills.MINING) + "/99 in mining.");
		    player.getPackets().sendIComponentText(275, 25, "You are level " + player.getSkills().getLevel(Skills.HERBLORE) + "/99 in herblore.");
		    player.getPackets().sendIComponentText(275, 26, "You are level " + player.getSkills().getLevel(Skills.AGILITY) + "/99 in agility.");
		    player.getPackets().sendIComponentText(275, 27, "You are level " + player.getSkills().getLevel(Skills.THIEVING) + "/99 in thieving.");
		    player.getPackets().sendIComponentText(275, 28, "You are level " + player.getSkills().getLevel(Skills.SLAYER) + "/99 in slayer.");
		    player.getPackets().sendIComponentText(275, 29, "You are level " + player.getSkills().getLevel(Skills.FARMING) + "/99 in farming.");
		    player.getPackets().sendIComponentText(275, 30, "You are level " + player.getSkills().getLevel(Skills.RUNECRAFTING) + "/99 in runecrafting.");
		    player.getPackets().sendIComponentText(275, 31, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		    player.getPackets().sendIComponentText(275, 32, "");
		    player.getPackets().sendIComponentText(275, 33, "You must have mined 1000 ore of any kind: " +player.minedore+ "/1000");
		    player.getPackets().sendIComponentText(275, 34, "You have caught " +player.fished+ "/1500 fish");
		    player.getPackets().sendIComponentText(275, 36, "You have burned: " +player.burntLogs+ "/1500 logs.");
		    player.getPackets().sendIComponentText(275, 37, "You have cut: " +player.wclogs+ "/2000 logs.");
		    player.getPackets().sendIComponentText(275, 39, "You have craft: "+player.cutDiamonds+ "/1500 diamond gems.");
		   player.getPackets().sendIComponentText(275, 38, "You must have stolen: " +player.Tstalss+ "/2000 stalls.");
		    player.getPackets().sendIComponentText(275, 35, "You must have completed: " +player.laps+ "/500 laps in total.");
			player.getPackets().sendIComponentText(275, 40, "You must have craft "+player.Ubow+"/1500 (u) bows");
			player.getPackets().sendIComponentText(275, 41, "You must have smith "+player.randomsmith+"/750 random items.");
			player.getPackets().sendIComponentText(275, 42, "");
		    player.getPackets().sendIComponentText(275, 43, "");
		    player.getPackets().sendIComponentText(275, 44, "");
		    player.getPackets().sendIComponentText(275, 45, "");
		    player.getPackets().sendIComponentText(275, 46, "");
		    player.getPackets().sendIComponentText(275, 47, "");
		    player.getPackets().sendIComponentText(275, 48, "");
		    player.getPackets().sendIComponentText(275, 49, "");
		    player.getPackets().sendIComponentText(275, 50, "");
		    for (int i = 51; i < 300; i++)
		    player.getPackets().sendIComponentText(275, i, "");
				end();*/
		//	}
		if (componentId == OPTION_2) {
		
		} else if (componentId == OPTION_3) {
		end ();
		}
	} if (stage ==2) {
	if (componentId == OPTION_1){
	
		} }
	}

	

	@Override
	public void finish() {

	}

}
