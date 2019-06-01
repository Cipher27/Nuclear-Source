package com.rs.game.player.dialogues;

public class CompCape extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue( "Choose an option", "Max cape requirements.",
					"Completionist requirements.", "Completionist (t) requirements.","Nothing.");
					stage  = 1;
						}

	@Override
	public void run(int interfaceId, int componentId) {
         if (stage == 1) {
			if (componentId == OPTION_1) {
				player.maxCapeInterface = 0;
				 player.getInterfaceManager().sendInterface(3015);
				 for (int i = 3; i <9 ; i++)
					  player.getPackets().sendIComponentText(3015, i, "       ");
				player.getPackets().sendIComponentText(3015, 9, "You must have 99 in every skill.");
				 for (int i2 = 10; i2 < 17 ; i2++)
					  player.getPackets().sendIComponentText(3015, i2, "");
				  end();
			} if (componentId == OPTION_2) {
				player.maxCapeInterface = 1;
			 player.getInterfaceManager().sendInterface(3015);
			 player.getPackets().sendIComponentText(3015, 1, "Completionist requirements");
					  player.getPackets().sendIComponentText(3015, 3, "<img=7>You must have maxed out levels.");
					  player.getPackets().sendIComponentText(3015, 4, "<img=7>Complete easy,medium and hard taks.");
					  player.getPackets().sendIComponentText(3015, 5, "<img=7>Complete the fight caves.");
					  player.getPackets().sendIComponentText(3015, 6, "<img=7>Complete the Pest Invasion mini-game.");
					  player.getPackets().sendIComponentText(3015, 7, "<img=7>Complete all quests.");
					  player.getPackets().sendIComponentText(3015, 8, "<img=7>Unlock every slayer reward.");
					  player.getPackets().sendIComponentText(3015, 9, "<img=7>Claim every tokhar-kal cape.");
					   for (int i = 9 ; i<17;i++)
						  player.getPackets().sendIComponentText(3015, i, "  ");
					 end();
			} if (componentId == OPTION_3) {
				player.maxCapeInterface = 2;
			 player.getInterfaceManager().sendInterface(3015);
			 player.getPackets().sendIComponentText(3015, 1, "Completionist (t) requirements");
					  player.getPackets().sendIComponentText(3015, 3, "<img=7>You must have maxed out levels.");
					  player.getPackets().sendIComponentText(3015, 4, "<img=7>Everything from the comp cape.");
					  player.getPackets().sendIComponentText(3015, 5, "<img=7>Complete all in-game achievements");
					  player.getPackets().sendIComponentText(3015, 6, "<img=7>Complete the Boss Caves.");
					  player.getPackets().sendIComponentText(3015, 7, "<img=7>Unlock every Recipe.");
					  player.getPackets().sendIComponentText(3015, 8, "<img=7>Unlock every in-game perk.");
					  player.getPackets().sendIComponentText(3015, 9, "<img=7>Complete wildy tasks.");
					  player.getPackets().sendIComponentText(3015, 10, "<img=7>Complete dungeoneering tasks.");
					  player.getPackets().sendIComponentText(3015, 11, "<img=7>Buy every dung item.");
					  player.getPackets().sendIComponentText(3015, 12, "<img=7>Create all crystal items "+player.crystalItems.size()+"/7");
					  player.getPackets().sendIComponentText(3015, 13, "<img=7>Fully upgraded player owned ports");
					   player.getPackets().sendIComponentText(3015, 14,"<img=7>Unlock all Artisan rewards");
					  for (int i = 15 ; i<17;i++)
						  player.getPackets().sendIComponentText(3015, i, "         ");
					 end();
			} if (componentId == OPTION_4) 
				end();
		} 
	}

	@Override
	public void finish() {

	}
}
