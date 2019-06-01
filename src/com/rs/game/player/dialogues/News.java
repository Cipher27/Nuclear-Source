package com.rs.game.player.dialogues;

import com.rs.utils.Colors;

/**
 * 
 * @author paolo
 *
 */

public class News extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What would you like to do?", 
				"Riddle Messages "+(player.riddleMessages ? Colors.green+"Enabled." : Colors.red+"Disabled."),
				"99 Messages "+(player.inform99s ? Colors.green+"Enabled." : Colors.red+"Disabled."), 
				"Achievement Messages "+(player.showAchievementMessage ? Colors.green+"Enabled." : Colors.red+"Disabled."),
				"Drop messages",
				"Pet messages",
				"Cancel");

	}

	@Override
	public void run(int interfaceId, int option) {
		if (stage == 1) {
			if(option == OPTION_1){
				player.riddleMessages = !player.riddleMessages;
				sendNPCDialogue(7909, 9827, "Riddle messages have been "+(!player.riddleMessages ? Colors.red+"Disabled." : Colors.green+"Enabled."));
				 stage = -1;
				} else if (option == OPTION_2){
					player.inform99s = !player.inform99s;
					sendNPCDialogue(7909, 9827, "Level-up messages "+(!player.inform99s ? Colors.red+"Disabled." : Colors.green+"Enabled."));
					 stage = -1;
				} else if(option == OPTION_3){
					player.showAchievementMessage = !player.showAchievementMessage;
					sendNPCDialogue(7909, 9827, "Level-up messages "+(!player.showAchievementMessage ? Colors.red+"Disabled." : Colors.green+"Enabled."));
					 stage = -1;
				}
			} else if(stage == -1)
				end();
		}
		@Override
		public void finish() {
		}
				}
