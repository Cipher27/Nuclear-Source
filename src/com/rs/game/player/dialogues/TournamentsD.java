package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Tournaments;

public class TournamentsD extends Dialogue {

	Player owner;
	Item reward;
	int time;
	String name;
	int skill;
	
	Tournaments ts = new Tournaments();
	
	byte START = 0;

	@Override
	public void start() {
		owner = player;
		reward = new Item(owner.giveItemId, owner.giveItemAmount);
		time = player.setTime;
		skill = player.setSkill;
		stage = START;
		sendEntityDialogue2(SEND_4_TEXT_CHAT,
								new String[] { "<col=ff0000>Tournaments</col>",
									"To make a tournament, please configure it.",
											"You must select the time (minutes),",
											"You must type the reward you'd like to give",
											"You must type the skill that the tournament"}, IS_NPC, -1, 9827);
	}

	@Override
	public void run(int interfaceId, int c) {
		if(stage == START) {
			stage++;
			sendDialogue2(SEND_4_OPTIONS, new String[] { "<col=ff0000>Configuration</col>",
				"Tournament Duration <col=ff0000>"+player.setTime+":00</col>", "Winner's Reward <col=ff0000>"+ItemDefinitions.getItemDefinitions(player.giveItemId).getName()+" x"+player.giveItemAmount+"</col>", 
					"Skill Selection <col=ff0000>"+Skills.SKILL_NAME[player.setSkill]+"</col>", "Start!" });
		} else if(stage == 1) {
			if(c == 1) {
				stage = 2;
				sendDialogue2(SEND_5_OPTIONS, new String[] { "<col=ff0000>Configuration</col>",
					"5:00 Minutes", "10:00 Minutes", "15:00 Minutes", "20:00 Minutes", "30:00 Minutes" });
			} else if(c == 2) {
				stage = 3;
				end();
				player.getAttributes().put("tourn_item", true);
				player.getPackets().sendInputLongTextScript("Which item would you like to reward?");
			} else if(c == 3) {
				stage = 3;
				end();
				player.getAttributes().put("tourn_skill", true);
				player.getPackets().sendInputLongTextScript("Choose an skill.");
			} else if(c == 4) {
				if(player.setTime == 0) {
					stage = 3;
					sendEntityDialogue2(SEND_1_TEXT_CHAT,
								new String[] { "<col=ff0000>Tournaments</col>",
									"Please select a valid duration."}, IS_NPC, -1, 9827);
					return;
				}
				if(player.setSkill == 0) {
					stage = 3;
					sendEntityDialogue2(SEND_1_TEXT_CHAT,
								new String[] { "<col=ff0000>Tournaments</col>",
									"Please select a valid skill."}, IS_NPC, -1, 9827);
					return;
				}
				if(player.giveItemId == 0 || player.giveItemAmount == 0) {
					stage = 3;
					sendEntityDialogue2(SEND_1_TEXT_CHAT,
								new String[] { "<col=ff0000>Tournaments</col>",
									"Please check the reward."}, IS_NPC, -1, 9827);
					return;
				}
				if(ts.active()) {
					stage = 3;
					sendEntityDialogue2(SEND_1_TEXT_CHAT,
								new String[] { "<col=ff0000>Tournaments</col>",
									"There's currently already a tournament running."}, IS_NPC, -1, 9827);
					return;
				}
				if(!player.getInventory().containsItem(reward.getId(), reward.getAmount())) {
					stage = 3;
					sendEntityDialogue2(SEND_1_TEXT_CHAT,
								new String[] { "<col=ff0000>Tournaments</col>",
									"Could not locate reward item(s) in your inventory."}, IS_NPC, -1, 9827);
					return;
				}
				if(player.setSkill == Skills.CONSTRUCTION || player.setSkill == Skills.HUNTER || player.setSkill == Skills.SUMMONING || player.setSkill == Skills.FARMING) {
					stage = 3;
					sendEntityDialogue2(SEND_1_TEXT_CHAT,
								new String[] { "<col=ff0000>Tournaments</col>",
									Skills.SKILL_NAME[player.setSkill]+" Is Not A Valid Skill."}, IS_NPC, -1, 9827);
					return;
				}
				for(int i=0;i<Settings.TOURNAMENT_ITEMS.length;i++) {
					if(ItemDefinitions.getItemDefinitions(reward.getId()).getName().toLowerCase().contains(Settings.TOURNAMENT_ITEMS[i].toLowerCase())) {
						player.getInventory().deleteItem(reward.getId(), reward.getAmount());
						new Tournaments(player, reward, player.setTime, player.getDisplayName(), player.setSkill);
						end();
						return;
					} else {
						stage = 3;
						sendEntityDialogue2(SEND_1_TEXT_CHAT,
								new String[] { "<col=ff0000>Tournaments</col>",
									"This item cannot be rewarded in a tournament."}, IS_NPC, -1, 9827);
					}
				}
			} else
				end();
		} else if(stage == 2) {
			if(c == 1) {
				time = 5;
				owner.setTime = 5;
			} else if(c == 2) {
				time = 10;
				owner.setTime = 10;
			} else if(c == 3) {
				time = 15;
				owner.setTime = 15;
			} else if(c == 4) {
				time = 20;
				owner.setTime = 20;
			} else if(c == 5) {
				time = 30;
				owner.setTime = 30;
			}
			stage = START;
			sendDialogue2(SEND_4_OPTIONS, new String[] { "<col=ff0000>Configuration</col>",
				"Tournament Duration <col=ff0000>"+player.setTime+":00</col>", "Winner's Reward <col=ff0000>"+ItemDefinitions.getItemDefinitions(player.giveItemId).getName()+" x"+player.giveItemAmount+"</col>", 
					"Skill Selection <col=ff0000>"+Skills.SKILL_NAME[player.setSkill]+"</col>", "Start!" });
		} else if(stage == 3) {
			stage = START;
			sendDialogue2(SEND_4_OPTIONS, new String[] { "<col=ff0000>Configuration</col>",
				"Tournament Duration <col=ff0000>"+player.setTime+":00</col>", "Winner's Reward <col=ff0000>"+ItemDefinitions.getItemDefinitions(player.giveItemId).getName()+" x"+player.giveItemAmount+"</col>", 
					"Skill Selection <col=ff0000>"+Skills.SKILL_NAME[player.setSkill]+"</col>", "Start!" });
		}
	}

	@Override
	public void finish() {
		
	}

}


//sendDialogue2(SEND_5_OPTIONS, new String[] { "option",
			//	"" });