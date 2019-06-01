package com.rs.game.player.dialogues;

public class HiddenTrapdoorD extends Dialogue {

	private int npcId = 515;

	@Override
	public void start() {
	sendNPCDialogue(npcId, 9827, "Go away, it's to dangerous for you here!" );
	stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		sendPlayerDialogue(9827, "what do you mean?" );
		stage = 2;
		}
		else if (stage == 2) {
		sendNPCDialogue(npcId, 9827, "Not enough time to explain, bring me some resources and you can get in." );
	    stage = 3;	
		} else if (stage == 3) {
		sendPlayerDialogue(9827, "what kind of resources?" );
		stage = 4;
		}else if (stage == 4) {
		sendNPCDialogue(npcId, 9827, "50 super anti-fires(3), 35 overloads (4), 250 rocktails, 30 turtoise pouches, whip,anti-dragonshield, dragon chainboy and dragon platelegs" );
	    stage = 5;	
		}else if (stage == 5) {
		sendPlayerDialogue(9827, "I'll be as fast as possible!" );
		stage = 6;
		}else if (stage == 6) {
		sendNPCDialogue(npcId, 9827, "I count on you! " );
	    stage = 7;	
		}else if (stage == 7) {
		end();
		}
	}

	@Override
	public void finish() {

	}

}
