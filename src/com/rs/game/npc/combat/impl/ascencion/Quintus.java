package com.rs.game.npc.combat.impl.ascencion;

import java.util.ArrayList;
import java.util.Random;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;


/**
 * @author Nosz
 * 			
 * 
 * 
 */
public class Quintus extends CombatScript{
	public float damageBuff;
	int Stage;
	int stageOneCompleted;
	
	
	public static void destroyLazors(final NPC npc,final Player p, int object) {
	    for (int c = 0; c < 20; c++) {
		World.spawnObject(new WorldObject(object, 5, 0, p.quintusStageOneX, p.quintusStageOneY+ c, 1), true);
	    	}
	    for (int d = 0; d < 20; d++) {
		World.spawnObject(new WorldObject(object, 5, 1, p.quintusStageOneX, p.quintusStageOneY-d, 1), true);
	    }
	    for (int a = 0; a < 20; a++) {
		World.spawnObject(new WorldObject(object, 5, 1, p.quintusStageTwoX+a, p.quintusStageTwoY, 1), true);
		}
	    for (int f = 0; f < 20; f++) {
		World.spawnObject(new WorldObject(object, 5, 1, p.quintusStageTwoX-f, p.quintusStageTwoY, 1), true);
		}
	    for (int b = 0; b < 20; b++) {
		World.spawnObject(new WorldObject(object, 5, 0, p.quintusStageThreeX, p.quintusStageThreeY+b, 1), true);
		}
	    for (int g = 0; g < 20; g++) {
		World.spawnObject(new WorldObject(object, 5, 0, p.quintusStageThreeX, p.quintusStageThreeY-g, 1), true);
	    	}
	}
	

	public void spawnLazors(final NPC npc,final Player p, int object) {
		if(npc.getHitpoints() > 4500) { // makes sure the player cant find a way to skip stages
			p.quintusStage = 0;	
		}
	if(npc.getHitpoints() <= 4500 && p.quintusStage == 0) { // 75% 
			p.quintusStageOneX = p.getX();
			p.quintusStageOneY = p.getY();
			for (int i = 0; i < 20; i++) {	
			World.spawnObject(new WorldObject(object, 5, 0, p.getX(), p.getY()+i, 1), true);
			World.spawnObject(new WorldObject(object, 5, 0, p.getX(), p.getY()-i, 1), true);
			checkCoords(p);
			p.quintusStage = 1;	
		}
	}
	if(npc.getHitpoints() <= 3000 && p.quintusStage == 1 ) { // 50% 
			p.quintusStageTwoY= p.getY();
			p.quintusStageTwoX = p.getX();
			for (int i = 0; i < 20; i++) {	
			World.spawnObject(new WorldObject(object, 5, 1, p.getX()-i, p.getY(), 1), true);
			World.spawnObject(new WorldObject(object, 5, 1, p.getX()+i, p.getY(), 1), true);	
			p.quintusStage = 2;
		}
	}
	if(npc.getHitpoints() <= 1500 && p.quintusStage == 2 ) {  // 25% 
			p.quintusStageThreeX = p.getX();
			p.quintusStageThreeY = p.getY();
			for (int i = 0; i < 20; i++) {	
			World.spawnObject(new WorldObject(object, 5, 0, p.getX(), p.getY()-i, 1), true);
			World.spawnObject(new WorldObject(object, 5, 0, p.getX(), p.getY()+i, 1), true);
			p.quintusStage = 3;
			}
		}
	}
	public void checkCoords(final Player p) {
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time++;
		    	if(time > 1) {	    		
		if(p.getX() == p.quintusStageOneX && p.quintusStage >= 1) {
			p.applyHit(new Hit(p, 45, HitLook.MAGIC_DAMAGE, 0));
			return;
		}
		if(p.getY() == p.quintusStageTwoY && p.quintusStage >= 2) {
			p.applyHit(new Hit(p, 45, HitLook.MAGIC_DAMAGE, 0));
			return;
		}
		if(p.getX() == p.quintusStageThreeX && p.quintusStage == 3) {
			p.applyHit(new Hit(p, 45, HitLook.MAGIC_DAMAGE, 0));
			return;
		}
		if(p.inInstancedDungeon == false) {
			stop();
			}
		}
	  }
	}, 0, 0);
	}
	@Override
	public Object[] getKeys() {
		return new Object[] { 17153 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final ArrayList<Entity> possibleTargets = npc.getPossibleTargets();
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();


		if(npc.getPossibleTargets().size() != 0){ 
			for (final Entity t : possibleTargets) {
				if (t instanceof Player) {
					final Player p = (Player) t;
					if(npc.getHitpoints() > 4500) { // 75% 
						  p.quintusStage = 0;
					}
					spawnLazors(npc, p, 84675);
	                  WorldTasksManager.schedule(new WorldTask() {// NORMAL ATTACK
                		Random rn = new Random();
                		int max = 100;
                		int min = 75;
                		int range = max- min + 1;
                		int randomNum =  rn.nextInt(range) + min;
							private int gameTick;
	                        @Override
	                        public void run() {
	                        	gameTick ++;         	
	                        	if(gameTick == 1){                        		
	                        		npc.setNextAnimation(new Animation(20277));
	                        		npc.setNextGraphics(new Graphics(3975));
	                        		
	                        	}
	                        	if(gameTick == 2){
	                        		  World.sendProjectile(npc, t, 3978, 10, 6, 40, 5, 0, 0);
	                        		  p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
	
	                        	}
	                        }

	                    }, 0, 0);

	                  WorldTasksManager.schedule(new WorldTask() { // AOE SPELL
	                		int x;
	                		int y;
	                  		int max = 300;
	                		int min = 100;
	                		Random rn = new Random();
	                		int range = max- min + 1;
	                		int randomNum =  rn.nextInt(range) + min;
							private int gameTick;
	                        @Override
	                        public void run() {
	                        	gameTick ++;
	                        	if(gameTick == 1){
	                        		npc.setNextAnimation(new Animation(20277));
	                        		x = p.getX();
	            					 y = p.getY();
	
	                        	}
	                        	if(gameTick == 4) {
	                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(x, y, 1));
	                        	}
	                      
	                        	if(gameTick == 5) {
	                           if(target.getX() == x && target.getY() == y){
	                        	   p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
	                                }

	                            }
	                        }

	                    }, 0, 0);
				}
			}
		}		
		return defs.getAttackDelay();
	}
}


