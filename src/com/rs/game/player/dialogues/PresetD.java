package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.player.LendingManager;
import com.rs.game.player.content.presets.PresetSetups;

public class PresetD extends Dialogue {
	public int npcId  = 1;

	@Override
	public void start() {
		sendOptionsDialogue("What would you like to do?",
					"Load a preset.",
					"view preset.",
					"Change name of a preset.",
					"save current loadout.",
					"Nothing");
					stage = 10;
			}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 10) {
			 if (componentId == OPTION_1) {
			 sendOptionsDialogue("Which one would you like to load?",
					""+player.preset1+"",
					""+player.preset2+"",
					""+player.preset3+"",
					""+player.preset4+"",
					""+player.preset5+"");
			   stage = 11;
			 } else if (componentId == OPTION_2) {
				 sendOptionsDialogue("Which one would you like to view?",
							""+player.preset1+"",
							""+player.preset2+"",
							""+player.preset3+"",
							""+player.preset4+"",
							""+player.preset5+"");
				 stage = 12;
			 } else if (componentId == OPTION_3) {
				 sendOptionsDialogue("Which one would you like to change?",
							""+player.preset1+"",
							""+player.preset2+"",
							""+player.preset3+"",
							""+player.preset4+"",
							""+player.preset5+"");
				 stage = 13;
			 }  else if (componentId == OPTION_4) {
				 sendOptionsDialogue("Which one would you like to save it?",
							""+player.preset1+"",
							""+player.preset2+"",
							""+player.preset3+"",
							""+player.preset4+"",
							""+player.preset5+"");
				 stage = 14;
			 } else if (componentId == OPTION_5) {
				end(); 
			 }
			 //loads them
		}else if (stage == 11) {
			 if (componentId == OPTION_1) {
				 PresetSetups set = player.getPresetSetupByName(player.presetone);
				 PresetSetups.giveSet(player, set);
				end();
			 } else if (componentId == OPTION_2) {
				 PresetSetups set = player.getPresetSetupByName(player.presettwo);
				 PresetSetups.giveSet(player, set);
				end();
			 }else if (componentId == OPTION_3) {
				 PresetSetups set = player.getPresetSetupByName(player.presetthree);
				 PresetSetups.giveSet(player, set);
				end();
			 }else if (componentId == OPTION_4) {
				 if (player.isDonator()) {
					 PresetSetups set = player.getPresetSetupByName(player.presetfour);
					 PresetSetups.giveSet(player, set);
				end();
				 }else {
				sendNPCDialogue(npcId, 9827, "You need to be a donator to use a 4th slot.");	
				stage = 20;
				}
			 }else if (componentId == OPTION_5) {
				 if (player.isDonator()) {
					 PresetSetups set = player.getPresetSetupByName(player.presetfive);
					 PresetSetups.giveSet(player, set);
				end();
			 } else {
				sendNPCDialogue(npcId, 9827, "You need to be a donator to use a 5th slot.");	
				stage = 20;
				}
			 }
			//removes them
		}else if (stage == 12) {
			 if (componentId == OPTION_1) {
				 PresetSetups set = player.getPresetSetupByName(player.presetone);
				PresetSetups.showSet(player,set);
				end();
			 } else if (componentId == OPTION_2) {
				player.removePresetSetup(player.presettwo);
				end();
			 }else if (componentId == OPTION_3) {
				player.removePresetSetup(player.presetthree);
				end();
			 }else if (componentId == OPTION_4) {
				 if (player.isDonator()) {
				player.removePresetSetup(player.presetfour);
				end();
				 }else {
				sendNPCDialogue(npcId, 9827, "You need to be a donator to use a 4th slot.");	
				stage = 20;
				}
			 }else if (componentId == OPTION_5) {
				 if (player.isDonator()) {
				player.removePresetSetup(player.presetfive);
				end();
				 }else {
				sendNPCDialogue(npcId, 9827, "You need to be a donator to use a 5th slot.");	
				stage = 20;
				}
			 }
			//Change
		}else if (stage == 13) {
			 if (componentId == OPTION_1) {
				 player.getAttributes().put("preset1", true);
				 player.getPackets().sendInputLongTextScript("Enter the name you would like to give it.");
			 } else if (componentId == OPTION_2) {
				 player.getAttributes().put("preset2", true);
				 player.getPackets().sendInputLongTextScript("Enter the name you would like to give it.");
			 }else if (componentId == OPTION_3) {
			player.getPackets().sendInputNameScript("Enter the name you would like to give it.");
			player.getAttributes().put("preset3", true);
			 }else if (componentId == OPTION_4) {
				 if (player.isDonator()) {
				 player.getAttributes().put("preset4", true);
				 player.getPackets().sendInputLongTextScript("Enter the name you would like to give it.");
				  }else {
					 stage = 20;
				sendNPCDialogue(npcId, 9827, "You need to be a donator to use a 4th slot.");	
				}
			 }else if (componentId == OPTION_5) {
				 if (player.isDonator()) {
				 player.getAttributes().put("preset5", true);
				 player.getPackets().sendInputLongTextScript("Enter the name you would like to give it.");
				  }else {
					  stage = 20;
				sendNPCDialogue(npcId, 9827, "You need to be a donator to use a 5th slot.");	
				}
			 }
			//stores them
		}else if (stage == 14) {
			 if (componentId == OPTION_1) {
				if(player.getPresetSetupByName(player.presetone) != null)
				 player.removePresetSetup(player.presetone);
				 Item inventory[] = player.getInventory().getItems().getItemsCopy();
                 Item equipment[] = player.getEquipment().getItems().getItemsCopy();
                 PresetSetups set = new PresetSetups(
                                 player.presetone,
                                 equipment,
                                 inventory,
                                 player.getCombatDefinitions().spellBook,
                                 player.getPrayer().isAncientCurses() ? 1 : 0
                                 );
				player.getPresetSetups().add(set); 
				sendNPCDialogue(npcId, 9827, "Succesfully stored your preset");
				end();
			 } else if (componentId == OPTION_2) {
				 if(player.getPresetSetupByName(player.presettwo) != null)
					 player.removePresetSetup(player.presettwo);
				 Item inventory[] = player.getInventory().getItems().getItemsCopy();
                 Item equipment[] = player.getEquipment().getItems().getItemsCopy();
                 PresetSetups set = new PresetSetups(
                                 player.presettwo,
                                 equipment,
                                 inventory,
                                 player.getCombatDefinitions().spellBook,
                                 player.getPrayer().isAncientCurses() ? 1 : 0
                                 );
				player.getPresetSetups().add(set); 
				sendNPCDialogue(npcId, 9827, "Succesfully stored your preset");
				end();
			 }else if (componentId == OPTION_3) {
				 if(player.getPresetSetupByName(player.presetthree) != null)
					 player.removePresetSetup(player.presetthree);
				 Item inventory[] = player.getInventory().getItems().getItemsCopy();
                 Item equipment[] = player.getEquipment().getItems().getItemsCopy();
                 PresetSetups set = new PresetSetups(
                                 player.presetthree,
                                 equipment,
                                 inventory,
                                 player.getCombatDefinitions().spellBook,
                                 player.getPrayer().isAncientCurses() ? 1 : 0
                                 );
				player.getPresetSetups().add(set);  
				sendNPCDialogue(npcId, 9827, "Succesfully stored your preset");
				end();
			 }else if (componentId == OPTION_4) {
				 if (player.isDonator()) {
					 if(player.getPresetSetupByName(player.presetfour) != null)
						 player.removePresetSetup(player.presetfour);
					 Item inventory[] = player.getInventory().getItems().getItemsCopy();
	                 Item equipment[] = player.getEquipment().getItems().getItemsCopy();
	                 PresetSetups set = new PresetSetups(
	                                 player.presetfour,
	                                 equipment,
	                                 inventory,
	                                 player.getCombatDefinitions().spellBook,
	                                 player.getPrayer().isAncientCurses() ? 1 : 0
	                                 );
				player.getPresetSetups().add(set);  
				sendNPCDialogue(npcId, 9827, "Succesfully stored your preset");
				end(); 
				 }else {
					 stage = 20;
					sendNPCDialogue(npcId, 9827, "You need to be a donator to use a 4th slot.");
				}
			 }else if (componentId == OPTION_5) {
				  if (player.isDonator()) {
					  if(player.getPresetSetupByName(player.presetfive) != null)
							 player.removePresetSetup(player.presetfive);
					  Item inventory[] = player.getInventory().getItems().getItemsCopy();
		                 Item equipment[] = player.getEquipment().getItems().getItemsCopy();
		                 PresetSetups set = new PresetSetups(
		                                 player.presetfive,
		                                 equipment,
		                                 inventory,
		                                 player.getCombatDefinitions().spellBook,
		                                 player.getPrayer().isAncientCurses() ? 1 : 0
		                                 );
				player.getPresetSetups().add(set); 
				sendNPCDialogue(npcId, 9827, "Succesfully stored your preset");
				end();
				 }else {
					 stage = 20;
				sendNPCDialogue(npcId, 9827, "You need to be a donator to use a 5th slot.");	
				}
			 }
			
		} else if (stage == 20) {
			end();
		}
			else if (stage == 1) {
			stage = 2;
			sendNPCDialogue(npcId, 9827, "This is a branch of the Bank of "
					+ Settings.SERVER_NAME + ". We have",
			"branches in many towns." );
		} else if (stage == 2) {
			stage = 3;
			sendOptionsDialogue("What would you like to say?",
					"And what do you do?",
					"Didnt you used to be called the Bank of Varrock?");
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				stage = 4;
				sendPlayerDialogue( 9827, "And what do you do?" );
			} else if (componentId == OPTION_2) {
				stage = 5;
				sendPlayerDialogue( 9827, "Didnt you used to be called the Bank of Varrock?" );
			} else
				end();
		} else if (stage == 4) {
			stage = -2;
			sendNPCDialogue(npcId, 9827, "We will look after your items and money for you.",
					"Leave your valuables with us if you want to keep them",
					"safe.");
		} else if (stage == 5) {
			stage = -2;
			sendNPCDialogue(npcId, 9827, "Yes we did, but people kept on coming into our",
					"signs were wrong. They acted as if we didn't know",
					"what town we were in or something.");
		} else if (stage == 6) {
			stage = 7;
			sendOptionsDialogue("What would you like to say?",
					"Yes",
					"No");
		} else if (stage == 7) {
			if (componentId == OPTION_1) {
				LendingManager.process();
			} else if (componentId == OPTION_2) {
				end();
			} else
				end();
		} else
			end();
	}

	@Override
	public void finish() {

	}

}
