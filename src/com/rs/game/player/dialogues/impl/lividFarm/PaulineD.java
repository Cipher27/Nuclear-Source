package com.rs.game.player.dialogues.impl.lividFarm;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class PaulineD extends Dialogue {

	int npcId = 13619 ;

	@Override
	public void start() {
		sendNpc(npcId,"Hello stranger, what can I do for you ?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			sendOptions("Current points : "+player.getPointsManager().getLividPoints()+"", "what is this place ?","Show me the reward shop","Nothing.");
			stage = 0;
			break;
		case 0:
			if(componentId == OPTION_1){
				sendNpc(npcId,"This is livid farm, a garden where you can earn magic and farming exp without wasting seeds.");
				stage = 100;
			} else if(componentId == OPTION_2){
				ShopsHandler.openShop(player, 221);
				end();
			}else
				end();
			break;
		case 100:
			end();
			break;
		}
	}

	@Override
	public void finish() {
	}
}