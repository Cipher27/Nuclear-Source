package com.rs.game.player.dialogues;

public class DTClaimRewards extends Dialogue {

	@Override
	public void start() {
		sendDialogue( "You have a Dominion Factor of "
				+ player.getDominionTower().getDominionFactor() + ".");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if(player.getDominionTower().getDominionFactor() <= 0){
			player.sm("You need more than 0 more factor points to claim a reward.");
			end();
		}
		if (stage == -1) {
			sendOptionsDialogue("If you claim your rewards your progress will be reset.",
					"Claim Rewards", 
					"Cancel");
			stage = 0;
		} else if (stage == 0) {
			if (componentId == OPTION_1){
				player.getDominionTower().openRewardsChest();
				end();
			} else
			end();
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
