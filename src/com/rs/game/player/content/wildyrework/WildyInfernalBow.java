package com.rs.game.player.content.wildyrework;

import java.util.Random;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.ActivityHandler;

public class WildyInfernalBow {
	
	private static Player bowOwner;

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
	
	
	public static void resetBow(){
		setBowOwner(null);
		startEvent();
	}
	
	public static void claimBow(Player player, WorldObject object){
		player.setSkullInfiniteDelay(0);
		setBowOwner(player);
		World.removeObject(object);
		ActivityHandler.setWildySkillingEvent("<col=0099cc>Infernal bow: </col>"+player.getDisplayName()+" has claimed the bow.");
		World.sendWorldMessage("<img=7><col=ff0000>[Wildy event] </col>"+player.getDisplayName()+" has claimed the bow, kill him for everything he has on him.", true);
	    player.getInventory().addItem(24474,1);
	}
	
		
	public static void startEvent() {
		
	    int pick = new Random().nextInt(Locations.values().length);
		final Locations spawns = Locations.values()[pick];
		ActivityHandler.setWildySkillingEvent("<col=0099cc>Infernal bow: </col>" +spawns.getHint()+"");
		final WorldObject bow = new WorldObject(35280, 10, 0, spawns.getTile().getX()+2, spawns.getTile().getY(), 0);
		World.spawnObject(bow, true);	
        World.sendWorldMessage("<img=7><col=ff0000>[Wildy event] </col>The Infernal bow has been respawned.", true);
		World.sendWorldMessage("<img=7><col=ff0000>[Location tip]: </col>"+spawns.getHint()+".", false);
	} 
	
	public static void setBowOwner(Player player){
		bowOwner = player;
	}
	
	public Player getBowOwner(){
		return bowOwner;
	}

}