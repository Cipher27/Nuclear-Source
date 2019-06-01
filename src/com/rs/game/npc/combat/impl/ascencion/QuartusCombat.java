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
public class QuartusCombat extends CombatScript{
	public float damageBuff;
	int Stage;

	@Override
	public Object[] getKeys() {
		return new Object[] { 17152 };
	}
public void checkHp(final NPC npc, final Player p) {
	WorldTile npcTile = new WorldTile(npc.getX(), npc.getY(), 1);
	if(npc.getHitpoints() > 4500) { // above 75% 
		  p.quartusStage = 0;
	}
	if(npc.getHitpoints() <= 4500 && p.quartusStage == 0) { // 75% 
		p.setNextWorldTile(npcTile);
		p.quartusStage = 1;
	}
	if(npc.getHitpoints() <= 3000 && p.quartusStage == 1) { // 50% 
		p.setNextWorldTile(npcTile);
		p.quartusStage = 2;
	}
	if(npc.getHitpoints() <= 1500 && p.quartusStage == 2) {  // 25% 
		p.setNextWorldTile(npcTile);
		p.quartusStage = 3;
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
					checkHp(npc, p);
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
	                		int y;;
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


