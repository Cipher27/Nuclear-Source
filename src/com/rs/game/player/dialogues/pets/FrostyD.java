package com.rs.game.player.dialogues.pets;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class FrostyD extends Dialogue {

    private int npcId;
    int random = Utils.random(4);
    @Override public void start() { 
 sendNPCDialogue(npcId,9827, "I'm cold.");
 stage = 1;  
}
@Override 
public void run(int interfaceId, int componentId){ 
if (stage == 1){ 
sendPlayerDialogue(9827,"You're a frost dragon. Aren't you used to it by now?");
stage = 2;
}
else if (stage == 2){ 
 sendNPCDialogue(npcId,9827,"There's no need to bring that into it.");
stage = 3;
}
else if (stage == 3){ 
sendPlayerDialogue(9827,"There is! You're made of frost - of course you're cold!");
stage = 4;
}
else if (stage == 4){ 
 sendNPCDialogue(npcId,9827,"That hurts my feelings. I'm as warm-hearted as any other dragon.");
stage = 5;
}
}

    @Override
    public void finish() {

    }

}
