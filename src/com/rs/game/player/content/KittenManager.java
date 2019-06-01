package com.rs.game.player.content;

import java.util.Random;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;

public class KittenManager {

	public enum Locations {
		CASTLE_WARS(new WorldTile(2442, 3093, 0), "Let's play some castle wars!"),
		BARBARIAN_OUTPOST(new WorldTile(2530, 3554, 0), "I wonder what the agility level of these barbarians is..."),
		CHAMPIONS_GUILD(new WorldTile(2845, 3538, 2), "How would a kitten look like with a defender?"),
		BARROWS(new WorldTile(3565, 3309, 0), "It's so sad to see these 5 brothers lay here in their graves");
		
	
		private WorldTile tile;
		private String hint;
	
		private Locations(WorldTile tile, String hint) {
			this.tile = tile;
			this.hint = hint;
		}
		
		public WorldTile getTile() {
			return tile;
		}
		
		public String getHint() {
			return hint;
		}
		
		
	}
	
	public void removeGertrude(NPC npc,Player player){
	 World.sendWorldMessage("<img=7>Gertrude has been found by "+player.getDisplayName()+" . Better luck next time!", true);	
	 World.removeNPC(npc);
	}
	
	
	public static void startEvent() {
	    int pick = new Random().nextInt(Locations.values().length);
		final Locations spawns = Locations.values()[pick];
        World.sendWorldMessage("<img=7><col=ff0000>[Cat event] </col>Getruden has appeared on the server, find here quick to unlock a kitten. ", true);
		World.sendWorldMessage("<img=7><col=ff0000>[Location tip]: </col>"+spawns.getHint()+".", false);
		return;
	} 

}