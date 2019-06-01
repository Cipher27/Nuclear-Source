package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class pvmInformation extends Dialogue {

    private int npcId = 8452;

    @Override
    public void start() {
	npcId = (Integer) parameters[0];
	sendNPCDialogue(npcId, 9827, "Well hello there adventure, can I help you?");
	    stage = -1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == -1) {
	    	sendOptionsDialogue("Choose an option", 
			"Show me the drop table.",
			"Drop Simulator",
			"How can I know which item I can get from a monster?");
			stage = 0;
	    }
	 else if (stage == 0) {
	     if (componentId == OPTION_1) {
		sendNPCDialogue(npcId, 9827, "You can do this very easily yourself with a command named npclookup. Example: ::npclookup Nex");
	   stage =1 ;
	    }else if (componentId == OPTION_2) {
		player.getTemporaryAttributtes().put("npcName_enter",  Boolean.TRUE);
		player.getPackets().sendRunScript(108, new Object[] { "Enter the npc id"});
		end();
	    }else if (componentId == OPTION_3) {
		sendNPCDialogue(npcId, 9827, "It's verry simpel, there's an ingame command for named itemlookup. Example:  ::itemlookup bones");
	   stage =1 ;
	   }
		
	}  else if (stage == 1){
		end();
	}
    }

    @Override
    public void finish() {

    }

}
