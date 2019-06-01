package com.rs.game.player.dialogues.impl.pop.crew;

import com.rs.game.player.content.ports.crew.Crew;
import com.rs.game.player.dialogues.Dialogue;


public class RemoveCrewD extends Dialogue {

	Crew crew;

	@Override
	public void start() {
		crew = (Crew) parameters[0];
		sendOptions("Are you sure you want to remove "+crew.getName()+" for ever?","Yes remove him", "No I don't want to remove him.");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
		if(componentId == OPTION_1){
			player.getPorts().getCrewMemebers().remove(crew);
			player.getDialogueManager().startDialogue("SimpleMessage", "Succesfully removed");
			player.getPorts().sendCrewInterface();
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