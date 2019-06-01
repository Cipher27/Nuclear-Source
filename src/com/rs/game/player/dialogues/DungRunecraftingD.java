package com.rs.game.player.dialogues;

import com.rs.game.player.dialogues.Dialogue;

public class DungRunecraftingD extends Dialogue {

	public static final int[][] RUNES = { { 17780, 17781, 17782, 17783 }, { 17784, 17785, 17786, 17787 }, { 17788, 17789, 17790, 17791, 17792 }, { 16997, 17001, 17005, 17009, 17013, 16999, 17003, 17007, 17011, 17015 } };

	@Override
	public void start() {
		int type = (int) this.parameters[0];
		sendRCOptions(type);
	}

	private void sendRCOptions(int type) {
		if (type == 0) {
			sendOptionsDialogue("What would you like to make?", "Runes", "Staves");
			stage =  1;
		} else if (stage ==1){
			
		}
		
		stage = (byte) (type + 1);
	}

	@Override
	public void run(int interfaceId, int componentId) {
	
	}

	@Override
	public void finish() {

	}
}
