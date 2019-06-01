package com.rs.game.player.dialogues.quests.pvmQuest;

import com.rs.game.player.dialogues.Dialogue;

public class PvmMasterQuest extends Dialogue {

    private int npcId = 14850;


    @Override
    public void start() {
	/*if (player.BandosKills >=50 && player.ZamyKills >= 50 && player.SaraKills >= 50 && player.ArmaKills >= 50 && player.TDKills >= 10 && player.KBDKills >= 50 && player.completedPvmQuest == false) {
	sendNPCDialogue(npcId, ANGRY_FACE, "Why do I always get the dummy's for my mission, god dammit Paolo...");
   }
 else if (player.BandosKills >=50 && player.ZamyKills >= 50 && player.SaraKills >= 50 && player.ArmaKills >= 50 && player.TDKills >= 10 && player.KBDKills >= 50 && player.completedPvmQuest == true) {
	sendNPCDialogue(npcId, HAPPY_FACE, "Ow hey bud, wanne go back to the dungeon ?");
   }else if (player.BandosKills < 50 && player.ZamyKills < 50 && player.SaraKills < 50 && player.ArmaKills < 50 && player.TDKills < 10 && player.KBDKills < 50&& player.completedPvmQuest == false) {
   sendNPCDialogue(npcId, SHAKING_NO_FACE, "Sorry, but you need to have more Pvm experience to join my mission.");
   player.getPackets().sendGameMessage("<col=ff0000>You need 50 Bandos<br><col=ff0000>             50 Zamorak<br><col=ff0000>             50 Armadyl<br><col=ff0000>             10 Tormented demon<br><col=ff0000>             50 King black dragon kills.");
   }*/
	}
	

    @Override
    public void run(int interfaceId, int componentId) {
/*	stage++;
	if (stage == 0)
	if (player.BandosKills >=50 && player.ZamyKills >= 50 && player.SaraKills >= 50 && player.ArmaKills >= 50 && player.TDKills >= 10 && player.KBDKills >= 50 && player.completedPvmQuest == false)  {
	    sendPlayerDialogue(CONFUSED, "Excuse me?");
	}
	if (player.BandosKills >=50 && player.ZamyKills >= 50 && player.SaraKills >= 50 && player.ArmaKills >= 50 && player.TDKills >= 10 && player.KBDKills >= 50 && player.completedPvmQuest == true) { 
    player.getDialogueManager().startDialogue("PvmMasterQuest3");
	}
	if (player.BandosKills < 50 && player.ZamyKills < 50 && player.SaraKills < 50 && player.ArmaKills < 50 && player.TDKills < 10 && player.KBDKills < 50&& player.completedPvmQuest == false) {
	end();
	}else if (stage == 1)
	    sendNPCDialogue(npcId, 9780, "Never mind, I guess that one extra loss won't matter. Are you ready to go? ");
	else if (stage == 2) 
	    sendPlayerDialogue(CONFUSED, "Some information would be nice...");
   else if (stage == 3)
	    sendNPCDialogue(npcId, SAD_FACE, "Uggh, right... It's a big monster, It killed a lot of our people. try to listen to what he says before he does an attack.");
   else if (stage == 4) 
	    sendPlayerDialogue(CONFUSED, "Well thanks...");
    else if (stage == 5) 
	 sendNPCDialogue(npcId, SCARED, "Ow yeah, you also have to pay 1million for the travel.");
	else if (stage == 6) 
	    sendPlayerDialogue(CONFUSED, "Wooow, why did I even take this job...");
    else if (stage == 7) {
	sendOptionsDialogue("Are you ready?","Yes","No.");
	stage = 9;
	}
//	else if (stage ==9) {
	if (componentId == OPTION_1) {
	Magic.sendCustomTeleportSpell2(player, 0, 0, new WorldTile(2770, 3821, 0));
	end();
	}
	if (componentId == OPTION_2) {
	end();
	}*/
    }

    @Override
    public void finish() {

    }
}
