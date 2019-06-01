package com.rs.game.player.dialogues.impl.pop.crew;

import com.rs.game.player.content.ports.crew.Crew;
import com.rs.game.player.dialogues.Dialogue;


public class HireCrewD extends Dialogue {

	Crew crew;
	int index;

	@Override
	public void start() {
		crew = (Crew) parameters[0];
		index = (int) parameters[1];
		sendOptions("Do you want to hire this crew member?","Yes", "No");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
		if(componentId == OPTION_1){
			if(player.getPorts().chime >= crew.getPrice()){
			player.getPorts().getAvaidableCrewMemebers().add(crew);
			player.getPorts().getCrewMemebers().add(crew);
			player.getPorts().reroll(index);
			player.getPorts().chime -= crew.getPrice();
			player.getDialogueManager().startDialogue("SimpleMessage", "Succesfully hired.");
			//player.getPorts().sendCrewInterface();
			stage = 99;
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "You don't have enough chrimes to hire them.");
			}
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