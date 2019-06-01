package com.rs.game.player.dialogues;

import com.rs.game.player.content.items.BossLootBoxes;

public class BossLootBoxesD extends Dialogue {

	int type ;
	@Override
	public void start() {
		type = (Integer) parameters[0];
			sendOptionsDialogue(
			"Select an option",
			"Kalphite king",
			"Corporeal Beast",
			"Nex",
			"Blink",
			"Dark lord");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if(componentId == OPTION_1) {
				if(type == 1)
				BossLootBoxes.handelSmallBox(player,16697);
				else
					BossLootBoxes.handelBigBox(player,16697);	
				end();
			}else if(componentId == OPTION_2) {
				if(type == 1)
				BossLootBoxes.handelSmallBox(player,8133);
				else
					BossLootBoxes.handelBigBox(player,8133);
				end();
			}else if(componentId == OPTION_3) {
				if(type == 1)
				BossLootBoxes.handelSmallBox(player,13450);
				else 
					BossLootBoxes.handelBigBox(player,13450);	
				end();
				}
			else if(componentId == OPTION_4) {
				if(type == 1)
				BossLootBoxes.handelSmallBox(player,12878);
				else 
					BossLootBoxes.handelBigBox(player,12878);	
				end();
			} else if(componentId == OPTION_5) {
				if(type == 1)
				BossLootBoxes.handelSmallBox(player,19553);
				else 
					BossLootBoxes.handelBigBox(player,19553);	
				end();
				}
		} 
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
