package com.rs.game.player.dialogues;


public class RunespanStoreD extends Dialogue {

	public RunespanStoreD() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Runespan points shop", 
		        "Blood ethereal set (30k)",
				"Law ethereal set (25k)",
				"Death ethereal set (30k)",
				"Omni-tiara (15k)",
				"Massive pouch (25k)");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Animation Settings",
						"Cooking Animations.",
						"Mining Animations.",
						"Fletching Animations.",
						"Firemaking Animations",
						"Nevermind.");
				stage = 13;
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Teleport Options",
						"Gnome Copter",
						"Demon",
						"Sky Jump",
						"Unicorn",
						"Magic Poof");
				stage = 5;
			}
			
			/**
			 * Options
			 */
			
		} else if (stage == 13) {
				if (componentId == OPTION_1) { 
					sendOptionsDialogue("Cooking Animations",
							"I want to use the samurai animations.", "I want to use the arcane animations.","I want to use the old cooking animations.",
						 "Cancel.");
					stage = 18;
				} else if (componentId == OPTION_2) {
					sendOptionsDialogue("Mining Animations",
							"I want to use new mining animations.", "I want to use old mining animations.",
						 "Cancel.");
					stage = 19;
				} else if (componentId == OPTION_3) {
					sendOptionsDialogue("Fletching Animations",
							"I want to use new fletching animations.", "I want to use old fletching animations.",
						 "Cancel.");
					stage = 20;
				}  else if (componentId == OPTION_4) {
					sendOptionsDialogue("Firemaking Animations",
							"I want to use partyhard firamking animations.", "I want to use old firemaking animation.",
						 "Cancel.");
					stage = 22;
				}	else if (componentId == OPTION_5) {
					player.getInterfaceManager().closeChatBoxInterface();
				}	

		
		/**
		 * Text
		 */
		
	}else if (stage == 22) {
				if (componentId == OPTION_1) {
					player.PartyhatFiremaking = true;
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("<col=008000>Game will display now the new firemaking animations.");
				} else if (componentId == OPTION_2) { 
					player.PartyhatFiremaking = false;
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("<col=008000>Game will display you old firemaking animations.");
				}  else if (componentId == OPTION_3) {
					player.getInterfaceManager().closeChatBoxInterface();
				}	

	} else if (stage == 20) {
				if (componentId == OPTION_1) {
					player.KarateFletching = true;
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("<col=008000>Game will display now new fletching animations.");
				} else if (componentId == OPTION_2) { 
					player.KarateFletching = false;
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("<col=008000>Game will display you old fletching animations.");
				}  else if (componentId == OPTION_3) {
					player.getInterfaceManager().closeChatBoxInterface();
				}	

	} else if (stage == 19) {
					if (componentId == OPTION_1) {
						player.ChillBlastMining = true;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display now new mining animations.");
					} else if (componentId == OPTION_2) { 
						player.ChillBlastMining = false;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display you old mining animations.");
					}  else if (componentId == OPTION_3) {
						player.getInterfaceManager().closeChatBoxInterface();
					}	
	
	} else if (stage == 18) {
					if (componentId == OPTION_1) {
						player.SamuraiCooking = true;
						player.ArcaneCooking = false;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display now you samurai cooking animations.");
					} else if (componentId == OPTION_2) {
						player.SamuraiCooking = false;
						player.ArcaneCooking = true;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display you arcane cooking animations.");
					}  else if (componentId == OPTION_3) {
						player.SamuraiCooking = false;
						player.ArcaneCooking = false;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display the normal cooking animations.");
			}
					
	} else if (stage == 5) {
		if (componentId == OPTION_1) {
			player.Ass = false;
			player.Gnome = true;
			player.Demon = false;
			player.Pony = false;
			player.SuperJump = false;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Gnome Copter teleport.");
		} else if (componentId == OPTION_2) { 
			player.Ass = false;
			player.Gnome = false;
			player.Demon = true;
			player.Pony = false;
			player.SuperJump = false;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Demon teleport.");
		}  else if (componentId == OPTION_3) {
			player.Ass = false;
			player.Gnome = false;
			player.Demon = false;
			player.Pony = false;
			player.SuperJump = true;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Sky Jump teleport.");
			player.getInterfaceManager().closeChatBoxInterface();
		} else if (componentId == OPTION_4) {
			player.Ass = false;
			player.Gnome = false;
			player.Demon = false;
			player.Pony = true;
			player.SuperJump = false;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Unicorn teleport.");
		} else if (componentId == OPTION_5) {
			player.Ass = true;
			player.Gnome = false;
			player.Demon = false;
			player.Pony = false;
			player.SuperJump = false;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Magic Poof teleport.");
			}
		}
	}

@Override
public void finish() {
	}
}