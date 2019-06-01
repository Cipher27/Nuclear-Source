package com.rs.game.minigames.warbands;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class WarbandsRewards extends Dialogue {

    @Override
    public void start() {
    	sendPlayerDialogue(9827,"Hello");
    }
    boolean selectedSkill;
    int skill;

    @Override
    public void run(int interfaceId, int componentId) {
    	switch (stage) {
    	case 0:
    		end();
    		break;
    	case -1:
    		sendEntityDialogue(SEND_1_TEXT_CHAT,
    				new String[] { "Quercus",
    						"Hmm? Oh, pardon me, human. I was tracking the movements of warbands out in the Wilderness." }, IS_NPC, 17065, 9827);
    		stage = 1;
    		break;
    	case 1:
    		sendOptionsDialogue("What would you like to ask?", "Warbands? What do you mean?", "Talk about current event.", "Talk about rewards.", "Nothing, actually.");
    		stage = 2;
    		break;
    	case 2:
    		switch (componentId) {
    		case OPTION_1:
    			sendEntityDialogue(SEND_NO_CONTINUE_1_TEXT_CHAT,
        				new String[] { "Quercus",
        						"Warbands are a wilderness activity that occur hourly, here at Hyperion. The objective of the minigame is to siphon the obelisk in the center of the camp undetected, then once having done so, loot the resources from within the camp." }, IS_NPC, 17065, 9827);
    			stage = 3;
    			break;
    		case OPTION_2:
    			if (Warbands.warband == null)
    				sendEntityDialogue(SEND_1_TEXT_CHAT,
    	    				new String[] { "Quercus",
    	    						"I sense no camps in the Wilderness at this time. Camps usually occur every 3 hours..." }, IS_NPC, 17065, 9827);
    			else
    				sendEntityDialogue(SEND_1_TEXT_CHAT,
    						new String[] { "Quercus",
        							"There is currently a"+(Warbands.warband.getWarbandsEventType().startsWith("A") ? "n " : " ")+Warbands.warband.getWarbandsEventType()+" warband located somewhere in between levels "+Warbands.getWildLevel(Warbands.warband.base[2])+" and "+Warbands.getWildLevel(Warbands.warband.base[3])+" of the wilderness!" }, IS_NPC, -1, 9827);
    			stage = 0;
    			break;
    		case OPTION_3:
    			sendEntityDialogue(SEND_NO_CONTINUE_1_TEXT_CHAT,
        				new String[] { "Quercus",
        						"I can exchange your warbands resources for experience in their respective skills, what would you like me to do this?" }, IS_NPC, 17065, 9827);
    			stage = 8;
    			break;
    		case OPTION_4:
    			sendEntityDialogue(SEND_NO_CONTINUE_1_TEXT_CHAT,
        				new String[] { "Quercus",
        						"Fairwell then." }, IS_NPC, -1, 9827);
    			stage = 0;
    			break;
    		}
    		break;
    	case 3:
    		sendEntityDialogue(SEND_1_TEXT_CHAT,
    				new String[] { "Quercus",
    						"With this taking place in the wilderness, it is important to note that other players may try and attack you, and that whilst carrying resources, you are unable to teleport and may be attacked by anyone of any combat level." }, IS_NPC, 17065, 9827);
    		stage = 4;
    		break;
    	case 4:
    		sendEntityDialogue(SEND_NO_CONTINUE_1_TEXT_CHAT,
    				new String[] { "Quercus",
    						"Is there anything else you'd like to discuss?" }, IS_NPC,17065, 9827);
    		stage = 5;
    		break;
    	case 5:
    		sendOptionsDialogue("Would you like to further discuss warbands?","Yes.", "No.");
    		stage = 6;
    		break;
    	case 6:
    		switch (componentId) {
    		case OPTION_1:
    			sendOptionsDialogue("What would you like to ask?", "Warbands? What do you mean?", "Talk about current event.", "Talk about rewards.", "Nothing, actually.");
        		stage = 2;
    			break;
    		case OPTION_2:
    			sendEntityDialogue(SEND_NO_CONTINUE_1_TEXT_CHAT,
        				new String[] { "Quercus",
        						"Fairwell then." }, IS_NPC, 17065, 9827);
    			stage = 0;
    			break;
    		}
    		break;
    	case 7:
    		sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Experience.", "Gold.");
    		stage = 8;
    		break;
    	case 8:
    		final boolean toGold = componentId == OPTION_2;
    		if (!toGold && !selectedSkill) {
    			sendOptionsDialogue("Which skill would you like to earn experience in?","Farming.","Construction.","Mining.","Smithing.","Herblore.");
    			stage = 9;
    			return;
    		}
    		int amount = player.getInventory().getAmountOf(getItem(skill).getId());
    		Item item = getItem(skill);
    		player.getInventory().deleteItem(item.getId(),amount);
    		if (toGold || skill == 0)
				player.getInventory().addItem(new Item(995, 25000 * amount));
			else
				player.getSkills().addXp(skill, getExpModifier(player, skill) * amount);
    		/*for (int i = 0; i < 28; i++) {
    			if (player.getInventory().getItem(i) == null)
    				continue;
    				if (player.getInventory().getItem(i) == getItem(skill)) {
    					player.getInventory().deleteItem(player.getInventory().getItem(i));
    					if (toGold || skill == 0)
    						player.getInventory().addItem(new Item(995, 25000));
    					else
    						player.getSkills().addXp(skill, getExpModifier(player, skill));
    					amount++;
    					continue;
    				}
    		}*/
    		if (amount > 0)
				sendEntityDialogue(SEND_NO_CONTINUE_1_TEXT_CHAT,
        				new String[] { "Quercus",
        						"Your resources have been converted into "+(toGold ? Utils.getFormattedNumber(25000*amount)+" coins" : "experience")+"."}, IS_NPC, 17065, 9827);
			else
				sendEntityDialogue(SEND_NO_CONTINUE_1_TEXT_CHAT,
        				new String[] { "Quercus",
        						"You had no resources to convert." }, IS_NPC, 17065, 9827);
			stage = 0;
    		break;
    	case 9:
    		switch (componentId) {
    		case OPTION_1:
    			skill = Skills.FARMING;
    			break;
    		case OPTION_2:
    			skill = Skills.CONSTRUCTION;
    			break;
    		case OPTION_3:
    			skill = Skills.MINING;
    			break;
    		case OPTION_4:
    			skill = Skills.SMITHING;
    			break;
    		case OPTION_5:
    			skill = Skills.HERBLORE;
    			break;
    		}
    		if (skill != 0)
    			selectedSkill = true;
    		sendOptionsDialogue("Are you sure?","Yes.","No.");
    		stage = 8;
    		break;
    		
    	}
    }
    
    public static Item getItem(int skill){
    	if(skill == Skills.FARMING)
			return new Item(27639);
		else if(skill == Skills.HERBLORE)
			return new Item(27637);
		else if(skill == Skills.MINING)
			return new Item(27640);
		else if(skill == Skills.SMITHING)
			return new Item(27638);
		else if(skill == Skills.CONSTRUCTION)
			return new Item(27636);
    	return null;
    }
    
    private static final int getExpModifier(Player player, int skill) {
    	int level = player.getSkills().getLevelForXp(skill);
    	return (int)(((level * (level - 2)/2)+50)/Settings.XP_RATE*2.5);
    }

    @Override
    public void finish() {

    }

}
