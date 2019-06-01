package com.rs.game.player.dialogues;

public class OldSchoolD extends Dialogue {

	int npcId = 549;

	@Override
	public void start() {
		sendNPCDialogue(npcId, 9827, "Hey "+player.getDisplayName()+", I've have something you might like" );
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == 1){
		sendPlayerDialogue(9827,"What do you mean?");
		stage =2;
		} else if (stage ==2){
		sendNPCDialogue(npcId, 9827, "I can convert armor into their old versions" );
		stage = 3;	
		}else if(stage == 3){
		sendPlayerDialogue(9827,"Do you mean the pre-eoc looks?");
		stage = 4;
		}else if (stage ==4){
		sendNPCDialogue(npcId, 9827, "Yes sir, but unfortionally I can only change some sets since they are really hard to get." );
		stage = 5;	
		}else if(stage == 5){
		sendPlayerDialogue(9827,"Which items can you convert?");
		stage = 6;
		}else if (stage ==6){
		sendNPCDialogue(npcId, 9827, "I can convert the following items: Rune/adamant/mithril/iron/steel full helm,platebody,platelegs and kiteshield. Also the full dragon set and the nex armours. More will be added soon." );
		stage = 7;	
		}else if (stage ==7){
		sendNPCDialogue(npcId, 9827, "Just use the item on me and I'll convert it for you." );
		stage = 8;	
		}else if(stage == 8){
		sendPlayerDialogue(9827,"Okay thanks dude.");
		stage = 10;
		}
		else if (stage == 10)
		end();
	
	}

	@Override
	public void finish() {

	}

}
