package com.rs.game.player.content.herbhabit;

import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
/**
 * 
 * @author paolo
 *
 */

public class HerbloreHabitat  {
		
	
	public static void handelJadinkoEvenet(){
		
	}
	
	public static void climbableVine(Player player, WorldObject object){
		final WorldTile toTile = (player.getY() == 2915 ? new WorldTile(2950,2917,0) : player.getY() == 2917 ? new WorldTile(2950,2915,0) : new WorldTile(0,0,0));
		WorldTasksManager.schedule(new WorldTask() {
			int timer;
			@Override
			public void run() {
				if (timer == 0) {
					player.lock(3);
					player.setNextAnimation(new Animation(839));
					
				}
				else if (timer == 1) {
					if(player.getY() == 2915){
					player.setNextForceMovement(new ForceMovement(player, 0, new WorldTile(player.getX(),2917,0), 1, ForceMovement.NORTH));
					player.setNextWorldTile(new WorldTile(player.getX(),2917,0));
					} else if(player.getY() == 2917){
						player.setNextForceMovement(new ForceMovement(player, 0, new WorldTile(player.getX(),2915,0), 1, ForceMovement.SOUTH));
						player.setNextWorldTile(new WorldTile(player.getX(),2915,0));
					}else if(player.getY() == 2902){
						player.setNextForceMovement(new ForceMovement(player, 0, new WorldTile(player.getX(),2900,0), 1, ForceMovement.SOUTH));
						player.setNextWorldTile(new WorldTile(player.getX(),2900,0));
					}else if(player.getY() == 2900){
						player.setNextForceMovement(new ForceMovement(player, 0, new WorldTile(player.getX(),2902,0), 1, ForceMovement.NORTH));
						player.setNextWorldTile(new WorldTile(player.getX(),2902,0));
					}
				
				}
				timer ++;
			}
			
		
	
		}, 0, 1); 
	}

}
