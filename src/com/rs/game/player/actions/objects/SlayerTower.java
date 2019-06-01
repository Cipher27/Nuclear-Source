package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * this class handles every object from the slayer tower
 * @author paolo
 *
 */
public class SlayerTower {
	
	public static void OpenPorticlus(Player player, WorldObject object){
		WorldTasksManager.schedule(new WorldTask() {
			int timer;
			@Override
			public void run() {
				if (timer == 0) {
					player.lock(3);
					player.getPackets().sendObjectAnimation(object,new Animation(19706));
					
				}
				else if (timer == 1) {
					player.addWalkSteps(player.getX(), (player.getY() == 3538 ? player.getY()+2 : player.getY()-2), 0, false);
				
				}
				else if (timer == 2) {
					
					player.getPackets().sendObjectAnimation(object,new Animation(19707));
				}
				timer ++;
			}
			
		
	
		}, 0, 1); 
	}
	
	public static void CrossPlank(Player player, WorldObject object){
		player.lock(4);
		final WorldTile toTile = new WorldTile(2536, 3553, 3);
		player.setNextForceMovement(new ForceMovement(player, 1, toTile, 3, ForceMovement.EAST));
		player.setNextAnimation(new Animation(16079));
		player.getAppearence().setRenderEmote(330);
		WorldTasksManager.schedule(new WorldTask() {
		    @Override
		    public void run() {
			player.setNextWorldTile(toTile);
			player.getSkills().addXp(Skills.AGILITY, 15);
			player.setNextAnimation(new Animation(-1));
			stop();
		    }

		}, 2);
	}
	/**
	 * handles statues movement, todo
	 * @param player
	 * @param object
	 */
	public void handelStatues(Player player, WorldObject object){
		
	}
	/**
	 * slayer tower crossing
	 * @param player
	 */
	public static void crossPlank(Player player){
		if (player.getY() == 3551 || player.getY() == 3550) {
			final boolean running = player.getRun();
	 		player.setRunHidden(false);
	 		player.lock(6);
	 		WorldTasksManager.schedule(new WorldTask() {
					int stage;
					 @Override
					public void run() {
			if(stage == 0) {
	 			   player.addWalkSteps(3416, 3551, 2, false);
	 			  player.getAppearence().setRenderEmote(155);
				}
				if (stage == 6) {
				player.unlock();
				player.getAppearence().setRenderEmote(-1);
				player.setRunHidden(running);
				stop();
				}
				 stage++;
			 }
		 }, 0, 0);
		}
		if (player.getY() == 3544 || player.getY() == 3545) {
			final boolean running = player.getRun();
	 		player.setRunHidden(false);
	 		player.lock(6);
	 		player.addWalkSteps(3416, 3544, 2, false);
	 			WorldTasksManager.schedule(new WorldTask() {
	 				int stage;
	 				 @Override
	 				public void run() {
	 		if(stage == 0) {
	 			player.addWalkSteps(3416, 3544, 2, false);
				player.getAppearence().setRenderEmote(155);
			}
			if (stage == 6) {
			player.unlock();
			player.getAppearence().setRenderEmote(-1);
			player.setRunHidden(running);
			stop();
			}
			 stage++;
		 }
	 }, 0, 0);
		}
	}
	public static boolean isObject(WorldObject object){
		int id = object.getId();
		switch(id){
		case 82728: //Gate
		case 82605: //plank
		case 82668: //sw stairs level 1
		case 82669:
		case 102293: // entrance hole
		case 82666: //sw stairs level 0
		case 82488: //normal stairs
		case 82490:
		case 82489:
		case 82491:
		case 82615: //stone plaque
			return true;
		}
		return false;
	}
	/**
	 * handles the action of each object
	 * @param player
	 * @param object
	 */
	public static void handelObject(Player player, WorldObject object){
		switch(object.getId()){
		case 82728:
			OpenPorticlus(player,object);
			break;
		case 82605:
			crossPlank(player);
			break;
		case 82668: //stairs down
		case 82669:
			player.setNextWorldTile(new WorldTile(player.getX(),player.getY(), player.getPlane() -1));
			break;
		case 82666://Stairs up
			player.setNextWorldTile(new WorldTile(player.getX(),player.getY(), player.getPlane() +1));
			break;
		case 102293:
			player.sm("The door seems locked.");
			break;
		case 82490:
			player.setNextWorldTile(new WorldTile(player.getX() +4, player.getY(), player.getPlane() -1));
			break;
		case 82488:
			player.setNextWorldTile(new WorldTile(player.getX() -4, player.getY(), player.getPlane() +1));
			break;
		case 82489:
			player.setNextWorldTile(new WorldTile(player.getX() +4, player.getY(), player.getPlane() +1));
			break;
		case 82491:
			player.setNextWorldTile(new WorldTile(player.getX() -4, player.getY(), player.getPlane() -1));
			break;
		case 82615:
			player.getDialogueManager().startDialogue("SimpleMessage", "I, Viggora, dedicate this fortress to the service of my lord. May it stand forever as a testament to the skill and craftsmanship of the humans in his service.");
			break;
		}
	}
}
