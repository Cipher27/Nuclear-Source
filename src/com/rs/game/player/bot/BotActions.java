package com.rs.game.player.bot;

import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerFollow;

public class BotActions {
	
	public static void follow(){
		Player bot = World.getPlayerByDisplayName("botxd");
		Player owner = World.getPlayerByDisplayName("paolo");
		bot.getActionManager().setAction(new PlayerFollow(owner));
		return;
	}
	
	public static void unfollow(){
		Player bot = World.getPlayerByDisplayName("botxd");
		bot.setNextFaceEntity(null);
		return;
	}
	
	public static void shout(String message){
		Player bot = World.getPlayerByDisplayName("botxd");
		bot.setNextForceTalk(new ForceTalk(""+message));
		return;
	}
	
	public static void teleport(){
		Player bot = World.getPlayerByDisplayName("Botxd");
		bot.setNextWorldTile(new WorldTile(2311,3680,0));
		//Magic.sendCustomTeleportSpell3(bot, 1,12, new WorldTile(2311,3680,0));
		return;
	}
}