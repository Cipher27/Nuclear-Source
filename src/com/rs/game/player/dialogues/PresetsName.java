package com.rs.game.player.dialogues;
 
import com.rs.game.item.Item;
import com.rs.game.player.content.presets.PresetSetups;
 
public class PresetsName extends Dialogue {
 
        private String name;
 
        @Override
        public void start() {
                name = (String) parameters[0];
                sendDialogue("Do you wish to continue to save your equipment, inventory and prayers as "
                                + name + "?");
        }
 
        @Override
        public void run(int interfaceId, int componentId) {
        	switch (stage) {
        	case -1:
        		 stage = 0;
                 sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes, please.",
                                 "No, nevermind.");
                 break;
        	case 0:
        		switch (componentId) {
        		case OPTION_1:
                    Item inventory[] = player.getInventory().getItems().getItemsCopy();
                    Item equipment[] = player.getEquipment().getItems().getItemsCopy();
                    PresetSetups set = new PresetSetups(
                                    name,
                                    equipment,
                                    inventory,
                                    player.getCombatDefinitions().spellBook,
                                    player.getPrayer().isAncientCurses() ? 1 : 0
                                    );
                    end();
                    if (player.getPresetSetups().size() >= MAX) {
                    	player.sm("You are only able to have a maximum of "+MAX+".");
                    	return;
                    }
                    if (player.getPresetSetupByName(name) != null) {
                    	player.sm("You already have a setup with this name stored.");
                    	return;
                    }
                    if (player.getPresetSetups().add(set))
                    //if (player.addPresetSetup(player, set))
                           player.getDialogueManager().startDialogue(
                                         "SimpleMessage",
                                          "Your equipment, inventory, stats and prayers have been saved with the name "
                                                            + name + ", you can load it at anytime by typing ::loadgear "
                                                           + name + ".");
        			break;
        			default:
        				end();
        					break;
        		}
        		break;	
        	}
        }
        
        private static final int MAX = 10;
        /*private int getMaxAmount(Player player) {
        	return player.isExtremeDonator() ? 10 : player.isDonator() ? 5 : 3;
        }*/
 
        @Override
        public void finish() {
        }
 
}