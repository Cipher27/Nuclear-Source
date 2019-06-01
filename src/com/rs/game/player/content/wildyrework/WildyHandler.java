package com.rs.game.player.content.wildyrework;

import java.util.Random;
import java.util.TimerTask;

import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.WildernessArtefacts;
import com.rs.game.player.content.custom.ActivityHandler;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class WildyHandler {
    public static boolean looted;
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
	
	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		//wildy chest
		case 35470:	
			return true;
		default:
		return false;
		}
	}
	//todo
	public static void generatePoints(Player player) {
	
	}
	public static void handleShred(Player player){
		int[] fishes = {360,372,364,378,384,390,396,15271,15271,390,15271,346,342,350,354};
		 int rand = Utils.random(fishes.length);
		if(player.getInventory().containsItem(20429,1)){
			player.getInventory().deleteItem(20429,1);
			player.getInventory().addItem(fishes[rand],Utils.random(3));
			player.getInventory().addItem(fishes[rand],Utils.random(3));
			WildernessArtefacts.handelShred(player);
		}
	}
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 35470) {
		OpenChest(player, object);
		}
	}
	public static boolean isNpc(final NPC npc) {
		switch (npc.getId()) {
		//Fishing spot
		case 14907:	
			return true;
		default:
		return false;
		}
	}
	public static void HandleNpc(Player player, final NPC npc) {
		final int id = npc.getId();
		if (id == 35470) {
		}
	}
	
	
	public static void OpenChest(Player player, final WorldObject object) {
	World.sendWorldMessage("<img=7><col=ff0000>[Wildy event] "+player.getDisplayName()+" has looted the chest.", false);
    player.sm("You received a special reward and 5 wildy tokens, you have "+player.getPointsManager().getWildernessTokens()+" points.");	
	player.wildyChest++;
	player.getPointsManager().setWildernessTokens(player.getPointsManager().getWildernessTokens() + 5);
	WildyChest.Chest(player, object);
	World.removeObject(object);
	ActivityHandler.setCurrentWildyEvent("The chest has been looted, better luck next time.");
	looted = true;
	return;
	}
	
	
	public static boolean restart = false;
	public static void startEvent() {
	    int pick = new Random().nextInt(Locations.values().length);
		final Locations spawns = Locations.values()[pick];
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int loop;
			@Override
			public void run() {
				try {
					if (loop < 2) {
					} else {
					cancel();
					}
					loop++;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 3600000);//3600000
		if(restart){
		looted = false;
		ActivityHandler.setCurrentWildyEvent(""+spawns.getHint()+"");
		final WorldObject chest = new WorldObject(35470, 10, 0, spawns.getTile().getX(), spawns.getTile().getY(), 0);	
		World.sendWorldMessage("<img=7><col=ff0000>[Wildy event] </col>A rare chest has been spawned, be quick before it's looted.", true);
		World.sendWorldMessage("<img=7><col=ff0000>[Location tip]: </col>"+spawns.getHint()+".", false);
		World.spawnTemporaryObject(chest, 3500000, true);
		} else {
			restart = true;
			ActivityHandler.setCurrentWildyEvent("The chest will spawn soon.");
		}
	} 

}