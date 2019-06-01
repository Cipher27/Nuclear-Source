package com.rs.game.player.dialogues.home;

import com.rs.game.player.dialogues.Dialogue;



public abstract class GlassMachine_broken extends Dialogue {


    @Override
    public void start() {
	sendPlayerDialogue(9775, "Mhhhh, it looks like it's broken. I should tell the server manager about it.");
    }

    @Override
    public void finish() {

    }
}
