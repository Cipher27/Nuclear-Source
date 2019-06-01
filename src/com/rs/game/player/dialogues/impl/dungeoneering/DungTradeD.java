package com.rs.game.player.dialogues.impl.dungeoneering;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Skills;
import com.rs.game.player.content.dungeoneering.DungeonRewardShop;
import com.rs.game.player.content.dungeoneering.DungeonRewardShop.DungeonReward;
import com.rs.game.player.dialogues.Dialogue;
public class DungTradeD extends Dialogue {

    DungeonReward item;

	@Override
	public void finish() { }

	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == 1){
			sendOptions("Select an option", "View Shop", "Nothing");
			stage = 2;
		}else if(stage == 2){
			if(componentId == OPTION_1){
				DungeonRewardShop.openRewardsShop(player);
				end();
			} else {
				end();
			}
		}
	}

	@Override
	public void start() {
		sendNPCDialogue(9711,9728,"What can I do for you sir?");
		stage = 1;
	}
}