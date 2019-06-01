package com.rs.game.player.dialogues.achievements;

import com.rs.game.player.dialogues.Dialogue;

public class Achievements extends Dialogue {
	

	@Override
	public void start() {
	sendOptionsDialogue("Choose an option.",
					"Easy tasks.",
					"Medium tasks.",
					"Hard tasks.",
					"Elite tasks.",
					"Back.");
			stage = 7;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		/*if (stage == 7) {
		if (componentId == OPTION_1) {
		AchievementsHandler.sendEasyTasks(player);
		end();
		}
		if (componentId == OPTION_2) {
		AchievementsHandler.sendMediumTasks(player);
		end();
		}
		if (componentId == OPTION_3) {
		AchievementsHandler.sendHardTasks(player);
		end();
		}
		if (componentId == OPTION_4) {
		AchievementsHandler.sendEliteTasks(player);
		end();
		}
		if (componentId == OPTION_5) {
		stage = -1;
		}
		}*/
		 
	}

	@Override
	public void finish() {

	}
}