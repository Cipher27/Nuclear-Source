package com.rs.game.player.dialogues.home;

import com.rs.game.player.dialogues.Dialogue;

public class SkillerMaster extends Dialogue {
	
	private int npcId = 12;

 
	@Override
	public void start() {
		sendNPCDialogue(npcId, NORMAL,"Hallo "+player.getDisplayName()+ ", I heard that you love challenges?");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Choose an option.",
					"How can you help me?",
					"Show me my progress.",
					"Claim rewards",
					"No thank you.");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				sendNPCDialogue(
						npcId,
						9827,
						"Alright! I can make skilling and pvming even more fun, basicly you just have to do some tasks which can you give you some awesome rewards." );
				stage = 2;
			} else if (componentId == OPTION_2) {
			sendOptionsDialogue("Choose an option.",
					"Easy tasks.",
					"Medium tasks.",
					"Hard tasks.",
					"Elite tasks.",
					"Back.");
			stage = 7;
			} else if (componentId == OPTION_3) {
			sendNPCDialogue(
						npcId,
						9827,
						"Your rewards well be put in your bank automaticly." );
				stage = 100;
			} else if (componentId == OPTION_4) {
				end();
			}
			else if (componentId == OPTION_5) {
			end();
			}
		} else if (stage == 2) {
		sendPlayerDialogue(9827, "Mhhhh, sound pretty good actually.");	
		stage = -1;
		}
		else if (stage == 6) {
		sendPlayerDialogue(9827, "Ow sweat, Paolo did a great job!");
		stage = -1;
		}
		 else if (stage == 7) {
		if (componentId == OPTION_1) {
		player.getAchievementManager().sendTestInterface(com.rs.game.player.achievements.Types.EASY);
		end();
		}
		else if (componentId == OPTION_2) {
			player.getAchievementManager().sendTestInterface(com.rs.game.player.achievements.Types.MEDIUM);
		end();
		}
		else if (componentId == OPTION_3) {
			player.getAchievementManager().sendTestInterface(com.rs.game.player.achievements.Types.HARD);
			end();
		}
		else if (componentId == OPTION_4) {
			player.getAchievementManager().sendTestInterface(com.rs.game.player.achievements.Types.ELITE);
			end();
		}
		if (componentId == OPTION_5) {
		stage = -1;
		}
		} else if (stage == 100)
			end();
		 
	}

	@Override
	public void finish() {

	}
}