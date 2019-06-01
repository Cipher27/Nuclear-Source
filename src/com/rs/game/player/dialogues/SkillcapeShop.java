package com.rs.game.player.dialogues;

import com.rs.game.player.content.custom.MasterCapesInterface;
import com.rs.utils.ShopsHandler;

public class SkillcapeShop extends Dialogue {
	

	@Override
	public void start() {
		sendOptionsDialogue("Select an Option", "Normal skill capes",
				"Master skill capes");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
			
			ShopsHandler.openShop(player, 52);
			end();
		  } else if (componentId == OPTION_2) {
			MasterCapesInterface.CapesInterface(player);
			end();
	 		}
		}
	}

	@Override
	public void finish() {

	}

}
