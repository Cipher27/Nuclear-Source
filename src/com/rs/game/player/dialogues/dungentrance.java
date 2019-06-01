package com.rs.game.player.dialogues;

public class dungentrance extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Dungeoneering Options",
				"Small Dungeon (~10 Rooms)",
				"Medium Dungeon (~30 Rooms)",
				"Large Dungeon (~50 Rooms)",
				"Nevermind");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		
	}

	@Override
	public void finish() {

	}

}
