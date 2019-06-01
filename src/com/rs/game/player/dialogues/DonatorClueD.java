package com.rs.game.player.dialogues;

import com.rs.game.player.ClueScrolls;


public class DonatorClueD extends Dialogue {

	private int npcId = 14643;

	@Override
	public void start() {
		if(player.isDonator() == false){
			end();
		}
	sendDialogue("As a donator you can claim one free elite clue scroll reward each day.");
	stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Would you like to know yours?", 
			        "Yes",
					"No");
			stage = 1;
			
		} else if (stage == 1) {
			 if (componentId == OPTION_1) {
				 if(player.dailyClueReward == 1){
			ClueScrolls.giveEliteReward(player);
			player.dailyClueReward = 0;
				 }else
			player.sm("You've already claimed your daily reward.");
		end();
			} else if (componentId == OPTION_2) {
				end();
			} 
		} 
  }
	@Override
	public void finish() {

	}
}