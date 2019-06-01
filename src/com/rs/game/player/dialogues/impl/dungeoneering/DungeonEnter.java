package com.rs.game.player.dialogues.impl.dungeoneering;

import com.rs.game.player.dialogues.Dialogue;

public class DungeonEnter extends Dialogue {

    @Override
    public void start() {
	sendOptionsDialogue("","Small", "Medium", "Large");
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (componentId == OPTION_3){
		player.getDungManager().formParty();
	    player.getDungManager().setDificulty(3);
	    player.getDungManager().selectComplexity(6);
	    player.getDungManager().setSize(0);
	    player.getDungManager().selectFloor(52);
	    player.getDungManager().enterDungeon(true);
	end();
	}
    }

    @Override
    public void finish() {

    }

}
