package com.rs.game.player.dialogues.interfaces.panel;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;



public class HomeTeleportD extends Dialogue {
	//Begin van de dialogue
	@Override
	public void start() {
		if(player.isAtHome()){
		    sendOptionsDialogue("Home Teleports", "Bank", "Shops", "Altar", "Portal","Nothing");
			stage = 1; 
		} else{
			Magic.sendArcaneTeleportSpell(player, 0, 0, new WorldTile(1632,6176, 0));
			end();
		}
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		if(componentId == OPTION_1){
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2329, 3689, 0));
		end();
		}
		if(componentId == OPTION_2){
		 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2340, 3675, 0));
		 end();
		}
		if(componentId == OPTION_3){
		 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2330, 3684, 0));
		 end();
		}
		if(componentId == OPTION_4) {
		 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2342, 3699, 0));  
		 end();
		}
		else if (componentId == OPTION_5) {
				end();
			
			}
		}
		 
}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	} 
		


	

}