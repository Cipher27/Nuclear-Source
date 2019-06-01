package com.rs.game.player.content.wildyrework;

import java.util.Random;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.content.custom.ActivityHandler;

public class WildyWyrm {

	public enum Locations {
		KBD_LADDER(new WorldTile(3014, 3843, 0), "If I use this ladder, will it bring me to the kbd cave then?"),
		MAGIC_ARENA(new WorldTile(3106, 3933, 0), "Looks like a Arena or something?"),
		PIRATE_HOUSE(new WorldTile(3041, 3953, 0), "Are those pirates ?!?"),
		AGILITY_COURS(new WorldTile(2996, 3941, 0), "Let's train some agility"),
		BLACKSALAMANDER(new WorldTile(3301, 3668, 0), "Don't forget your net traps."),
		RED_DRAGONS(new WorldTile(3226, 3833, 0), "Do not forget your anti-fire shield for those red dragons."),
		COSMIC_ALTAR(new WorldTile(3062, 3589, 0), "Let's craft some cosmic runes!"),
		DARK_WARRIORS_CASTLE(new WorldTile(3020, 3631, 0), "Those mighty dark warriors."),
		DAEMONHEIM(new WorldTile(3365, 3616, 0), "Looks like the entrance of Daemonheim."),
		RUNITE_ROCKS(new WorldTile(3061, 3886, 0), "I wonder if these spiders like the runite rocks.");
		
	
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
	
	public static void startEvent() {
	    int pick = new Random().nextInt(Locations.values().length);
		final Locations spawns = Locations.values()[pick];
	    ActivityHandler.setWildySkillingEvent("<col= 0099cc>Wildy Wyrm: </col>" +spawns.getHint()+"");
		World.spawnNPC(22160, new WorldTile(spawns.getTile().getX(), spawns.getTile().getY(), 0), -1, true, true,false);
        World.sendWorldMessage("<img=7><col=ff0000>[Wildy event] </col>A rare Wildy Wyrm has been spotted by Darius, be quick before someone else kills it. ", true);
		World.sendWorldMessage("<img=7><col=ff0000>[Location tip]: </col>"+spawns.getHint()+".", false);

		return;
	} 

}