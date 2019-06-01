package com.rs.game.npc.combat.impl.ascencion;

import java.util.ArrayList;
import java.util.Random;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
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
public class SextusCombat extends CombatScript{
	public float damageBuff;
	int Stage;
	 boolean firstloop = false;
	@Override
	public Object[] getKeys() {
		return new Object[] { 17154 };
	}
	public void checkHitpoints(final NPC npc, final Player p) {
			if(npc.getHitpoints() > 4500) { // 75% 
				p.sextusStage = 0;
		}
			if(npc.getHitpoints() <= 4500 && p.sextusStage == 0) { // 75% 
				p.sextusStage = 1;
		}
			if(npc.getHitpoints() <= 3000 && p.sextusStage == 1) { // 50% 
				p.sextusStage = 2;
		}
			if(npc.getHitpoints() <= 1500 && p.sextusStage == 2) {  // 25% 
				p.sextusStage = 3;
		}
	}
	@Override
	public int attack(final NPC npc, final Entity target) {
		final ArrayList<Entity> possibleTargets = npc.getPossibleTargets();
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if(npc.getPossibleTargets().size() != 0){ 
			for (final Entity t : possibleTargets) {
				if (t instanceof Player) {
					final Player p = (Player) t;
					checkHitpoints(npc, p);
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
	                		int stage2X;
	                		int stage2Y;
	                		int stage3X;
	                		int stage3Y;
	                		int stage4X;
	                		int stage4Y;
	                 		int max = 300;
	                		int min = 100;
	                		Random rn = new Random();
	                		int range = max- min + 1;
	                		int randomNum =  rn.nextInt(range) + min;
							private int gameTick;			
	                        @Override 
	                        public void run() {
	                        	gameTick ++;
	                        	if (p.sextusStage == 0) {
	 
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
	                        	// stage 2
	                        	if(p.sextusStage == 1) {
	  
	                            	if(gameTick == 1){
		                        		npc.setNextAnimation(new Animation(20277));
		                        		x = p.getX();
		            					 y = p.getY();
		            					
										if(firstloop == false) {
											stage2X = x;
											stage2Y = y;
											
		            					 }
		
		                        	}
		                        	if(gameTick == 4) {
		                        		
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(x, y, 1));	
		                        	}
		                        	if(gameTick == 5) {
		                           if(target.getX() == x && target.getY() == y){
		                        	   p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		                                }

		                            }
		                        	if(gameTick == 7) {
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(stage2X, stage2Y, 1));
		                        	}
		                        	if(gameTick == 8) {
		                        	     if(target.getX() == stage2X && target.getY() == stage2Y){
		                        	    	  p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		 		                                }
		                        		}
	                        	}
	                        	//stage 3
	                        	if(p.sextusStage == 2) {
	                            	if(gameTick == 1){
		                        		npc.setNextAnimation(new Animation(20277));
		                        		x = p.getX();
		            					 y = p.getY();
		            					
										if(firstloop == false) {
											stage2X = x;
											stage2Y = y;					
		            					 }
		                        	}
		                        	if(gameTick == 4) {
		                        		
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(x, y, 1));
		                        		
		                        	}
		                        	if(gameTick == 5) {
		                           if(target.getX() == x && target.getY() == y){
		                        	   p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		                                }
		                            }
		                        	if(gameTick == 7) {
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(stage2X, stage2Y, 1));
		                        		stage3X = stage2X;
		                        		stage3Y = stage2Y;
		                        	}
		                        	if(gameTick == 8) {
		                        	     if(target.getX() == stage2X && target.getY() == stage2Y){
		                        	    	  p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		 		                                }
		                        	}
		                           	if(gameTick == 9) {
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(stage3X, stage3Y, 1));
		                        		stage4X = stage3X;
		                        		stage4Y = stage3Y;
		                        	}
		                        	if(gameTick == 10) {
		                        	     if(target.getX() == stage3X && target.getY() == stage3Y){
		                        	    	  p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		 		                                }
		                        	}
	                        	}
	                        	//stage 4
	                        	if(p.sextusStage == 3) {
	                            	if(gameTick == 1){
		                        		npc.setNextAnimation(new Animation(20277));
		                        		x = p.getX();
		            					 y = p.getY();
		            					
										if(firstloop == false) {
											stage2X = x;
											stage2Y = y;
		            					 }
		                        	}
		                        	if(gameTick == 4) {
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(x, y, 1));
		                        	}
		                      
		                        	if(gameTick == 5) {
		                           if(target.getX() == x && target.getY() == y){
		                        	   p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		                                }

		                            }
		                        	if(gameTick == 7) {
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(stage2X, stage2Y, 1));
		                        		stage3X = stage2X;
		                        		stage3Y = stage2Y;
		                        	}
		                        	if(gameTick == 8) {
		                        	     if(target.getX() == stage2X && target.getY() == stage2Y){
		                        	    	  p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		 		                                }
		                        	}
		                           	if(gameTick == 9) {
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(stage3X, stage3Y, 1));
		                        		stage4X = stage3X;
		                        		stage4Y = stage3Y;
		                        	}
		                        	if(gameTick == 10) {
		                        	     if(target.getX() == stage3X && target.getY() == stage3Y){
		                        	    	  p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		 		                                }
		                        	}
		                           	if(gameTick == 11) {
		                        		World.sendGraphics(null, new Graphics(3974), new WorldTile(stage4X, stage4Y, 1));
		                        	}
		                        	if(gameTick == 12) {
		                        	     if(target.getX() == stage4X && target.getY() == stage4Y){
		                        	    	  p.applyHit(new Hit(p, randomNum, HitLook.MAGIC_DAMAGE, 0));
		 		                                }
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


