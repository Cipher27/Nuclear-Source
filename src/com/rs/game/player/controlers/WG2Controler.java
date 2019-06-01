package com.rs.game.player.controlers;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;

public class WG2Controler extends Controler {
	
	public static void dropOffhand(Player player, NPC npc) {
		int itemId = 25692;
		for(int i = 0; i < OFFHANDS.length; i++) {
			if(player.getInventory().containsItem(OFFHANDS[i], 1) || player.getBank().containsItem(OFFHANDS[i], 1)) {
				if(OFFHANDS[i] == 25707) {
					itemId = 25707;
				} else {
					itemId = OFFHANDS[i + 1];
				}
			}
		}
		World.addGroundItem(new Item(itemId, 1), new WorldTile(
				npc.getCoordFaceX(npc.getSize()), npc.getCoordFaceY(npc.getSize()), npc.getPlane()), player,
				false, 180, true);
	}
	
	public static int[] OFFHANDS = { 25692, 25694, 25696, 25698, 25700, 25702, 25704, 25707 };

	
	
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}
