package com.rs.game.player.dialogues.impl.pop.crew;

import com.rs.game.player.content.ports.Voyages;
import com.rs.game.player.content.ports.Voyages.Voyage;
import com.rs.game.player.content.ports.ship.Ship;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Colors;
import com.rs.utils.Utils;


public class shipWarningD extends Dialogue {

	Ship ship;
	Voyage voy;

	@Override
	public void start() {
		ship = (Ship)parameters[0];
		voy = (Voyage)parameters[1];
		sendOptions(Colors.red+"Your ship has less than 50% chance to complete this mission succesfull, are you sure?","Yes", "No");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
		if(componentId == OPTION_1){
			player.getPorts().firstShipTime = Utils.currentTimeMillis();
			player.getPorts().firstShipReward = false;
			player.getPorts().sendShipVoyage(ship, voy);
			player.getPorts().firstShipVoyage = Voyages.getTime(voy);
			player.closeInterfaces();
			player.getDialogueManager().startDialogue("SimpleMessage", "Your ship has been send.");
			stage = 99;
		} else
			end();
		break;
		case 99:
			end();
			break;
		}
	}

	@Override
	public void finish() {
	}
}