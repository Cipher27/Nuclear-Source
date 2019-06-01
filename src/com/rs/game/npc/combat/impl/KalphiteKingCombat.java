package com.rs.game.npc.combat.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.NewForceMovement;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.kalphiteKning.KalphiteKing;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;


public class KalphiteKingCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 16697, 16698, 16699 };
	}

	//GFX 3745
	
	private void sendStandardAttack(final NPC npc, final Entity target) {
		KalphiteKing king = (KalphiteKing) npc;
		switch(npc.getId()) { //standard
			case 16697: //melee //DONE
				npc.setNextAnimation(new Animation(19449));
				if(Utils.random(10) == 0) { //blind
					WorldTasksManager.schedule(new WorldTask() {

						@Override
						public void run() {
							target.applyHit(new Hit(target, Utils.random(700), HitLook.MELEE_DAMAGE));
						}
					});
				}else{
					delayHit(npc, 0, target, getMeleeHit(npc, getMaxHit(npc, NPCCombatDefinitions.MELEE, target)));
				}
				break;
			case 16698: //mage yelllow-green orb(mage) //DONE
				
				final boolean twoOrbs = Utils.random(2) == 1; //50% chance two orbs
				//25% chance combust as 50% / 50%
				
				npc.setNextAnimation(new Animation(19448)); //4
				npc.setNextGraphics(new Graphics(3742));
				for (Entity t : npc.getPossibleTargets()) {
					final WorldTile tile = new WorldTile(t);

					World.sendProjectile(npc, tile, 3743, 100, 30, 80, 1, 16, 0);
					if(twoOrbs)
						World.sendProjectile(npc, tile.transform(0, -1, 0), 3743, 100, 30, 80, 1, 16, 0);
					WorldTasksManager.schedule(new WorldTask() {

						@Override
						public void run() {
							World.sendGraphics(npc, new Graphics(3743), tile); //TODO correct gfx here for ball in floor
							WorldTasksManager.schedule(new WorldTask() {
								@Override
								public void run() {
									World.sendGraphics(npc, new Graphics(3752), tile);
									for (Entity t : npc.getPossibleTargets()) {
										if (t.withinDistance(tile, 2)) {
											if(twoOrbs) {
												t.applyHit(new Hit(npc, Utils.random(600) + 1, HitLook.MAGIC_DAMAGE));
												t.applyHit(new Hit(npc, Utils.random(600) + 1, HitLook.MAGIC_DAMAGE));
											}else{
												if(Utils.random(2) == 1) 
													t.applyHit(new Hit(npc, Utils.random(600) + 1, HitLook.MAGIC_DAMAGE));
												else {
													t.applyHit(new Hit(npc, Utils.random(500), HitLook.MAGIC_DAMAGE, 10));
													t.setNextGraphics(new Graphics(3574));
												}
											}
										}
									}
								}
							}, 2);
						}
					}, Utils.projectileTimeToCycles(1) - 1);
				}
				break;
			case 16699: //range //DONE
				npc.setNextAnimation(new Animation(19450));
				for (final Entity t : npc.getPossibleTargets()) {
					World.sendProjectile(npc, t, 0, 3747, 30, 30, 25, 2, 0);
					if(!king.isUseStunDartNext() && Utils.random(10) == 0) { //blind
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								t.applyHit(new Hit(npc, Utils.random(500), HitLook.RANGE_DAMAGE, 10));
								t.setNextGraphics(new Graphics(3574));
							}
						}, Utils.projectileTimeToCycles(25) - 1);
					}else {
						int delay = Utils.projectileTimeToCycles(25) - 1;
						delayHit(npc, delay, t, getRangeHit(npc, Utils.random(500)/*getMaxHit(npc, NPCCombatDefinitions.RANGE, t)*/));
						if(king.isUseStunDartNext()) {
/*							WorldTasksManager.schedule(new WorldTask() {

								@Override
								public void run() {
									if(t instanceof Player) 
										((Player)t).getPackets().sendGameMessage("You've been prevented from moving.");
									t.setFreezeDelay(11);
								}
							}, 25);*/
						}
					}
				}
				king.setUseStunDartNext(false);
				break;
		}
	}
	


	@Override
	public int attack(final NPC npc, final Entity target) {
		final KalphiteKing king = (KalphiteKing) npc;
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if(king.canSpecial()) {
			king.resetMovesUntilSpecial();
			
			boolean specificSpecial = Utils.random(5)  >= 2;
			boolean dig = Utils.random(5) == 1;
			
			if(king.getSpecialMovesUse() == 2 && king.isSpecificSpecial() == specificSpecial) 
				specificSpecial = !specificSpecial;
			
			if(specificSpecial == king.isSpecificSpecial()) {
				king.setSpecialMovesUse(king.getSpecialMovesUse()+1);
			}else{
				king.setSpecialMovesUse(1); //as this one counts too
				king.setSpecificSpecial(specificSpecial);
			}
				
			if(dig) {
				king.dig(target);
				return defs.getAttackDelay();
			}
			
			if(specificSpecial) { //more common 
				switch(npc.getId()) { //standard
					case 16697: //melee quake (melee) //DONE
						king.setNextAnimation(new Animation(19435));
						king.setNextGraphics(new Graphics(3734));
						for (Entity t : npc.getPossibleTargets()) {
							if(Utils.isOnRange(king, t, 3)) {
								Hit hit = getMeleeHit(npc, getMaxHit(npc, NPCCombatDefinitions.MELEE, t));
								delayHit(npc, 1, t, hit);
								if(t instanceof Player) 
									((Player) t).getSkills().drainLevel(Skills.DEFENCE, hit.getDamage() / 200);
							}
						}
						break;
					case 16698: //mage blue orb(mage) //DONE
						npc.setNextAnimation(new Animation(19448));
						npc.setNextGraphics(new Graphics(3757));
						for (final Entity t : npc.getPossibleTargets()) {
							World.sendProjectile(npc, t, 3758, 100, 30, 80, 2, 16, 0);
							t.setNextGraphics(new Graphics(3759, 2, 0));
							
							int delay =  Utils.projectileTimeToCycles(2) - 1;
							delayHit(npc, delay, t, getMagicHit(npc, getMaxHit(npc, NPCCombatDefinitions.MAGE, t)));
	/*						WorldTasksManager.schedule(new WorldTask() {

								@Override
								public void run() {
									if(t instanceof Player) 
										((Player)t).getPackets().sendGameMessage("You've been prevented from moving.");
									t.setFreezeDelay(11);
								}
							}, delay);*/
						}
						return 17;
						//break;
					case 16699: //range //incendiary shot (range) / stun dart (after incendiary shot / phase changing to range)
						List<Entity> list = king.getPossibleTargets();
						Collections.shuffle(list);
						int c = 0;
						for(Entity t : list) {
							if(c++ == 3)
								break;
							delayHit(npc, 0, t, getRangeHit(npc, getMaxHit(npc, NPCCombatDefinitions.RANGE, t)));
							t.setNextGraphics(new Graphics(3522));
						}
						king.setUseStunDartNext(true);
						sendStandardAttack(npc, target);
						break;
				}
			}else{
				int attack = Utils.random(4);
				if((attack == 0 && king.getId() == 16698) ||
						(attack == 1 && king.getId() == 16699)) 
					attack = 2;
				
				switch(attack) {
					case 0: //Beetlejuice (melee + range) //DONE
//						king.setNextAnimation(new Animation(19464));
//						king.setNextGraphics(new Graphics(3738));
//						World.sendProjectile(npc, target, 3739, 58, 30, 25, 75, 10, 0);
//						target.setNextGraphics(new Graphics(3740, 75, 0));	
//						if(target instanceof Player) {
//							((Player)target).lock(17);
//							((Player)target).stopAll();
//							((Player)target).setNextAnimation(new Animation(-1)); //to stop abilities emotes
//							((Player)target).getPackets().sendGameMessage("The Kalphite King has imobilised you while preparing for a powerful attack. You are unable to move.");
//						}
//						WorldTasksManager.schedule(new WorldTask() {
//							@Override
//							public void run() {
//							//	Entity t = king.getCombat().getTarget();
//								if(king.getCombat().getTarget() != target)
//									return;
//								target.applyHit(new Hit(king, 800, HitLook.REGULAR_DAMAGE));
//							}
//						}, 16);
//						return 17; //add more delay for this one or else it will remove ability
//						//break;
					case 1://charge Charge (melee + mage)
						final byte[] dirs = Utils.getDirection(npc.getDirection()); //gets the di x/y for the angle
						
						WorldTile lastTile = null;
						int distance;
						for (distance = 1; distance < 10; distance++) {
							WorldTile nextTile = new WorldTile(new WorldTile(king.getX() + (dirs[0] * distance), king.getY() + (dirs[1] * distance), king.getPlane()));
							if (!World.isFloorFree(nextTile.getPlane(), nextTile.getX(), nextTile.getY(), king.getSize())) 
								break; //found last tile(as cant move this means the one before this)
							lastTile = nextTile;
						}
						if(lastTile == null || distance <= 2) { //do sweep instead
							king.setNextAnimation(new Animation(19447));
							king.setNextGraphics(new Graphics(3735));
							for(Entity t : king.getPossibleTargets()) {
								if(!Utils.isOnRange(king, t, 1))
									continue;
								delayHit(npc, 0, t, getRegularHit(npc, Utils.random(500)+250));
							}
						}else{
							king.setNextAnimation(new Animation(19457));
							final int maxStep = distance / 2;
							king.setCantInteract(true);
							king.setNextAnimation(new Animation(maxStep + 19456));
							//distance / 2 as kk runs meaning 2 steps per tick
							int totalTime = distance/2;
							final WorldTile firstTile = new WorldTile(king);
							int dir = king.getDirection();
							king.setNextForceMovement(new NewForceMovement(firstTile, 5, lastTile, totalTime + 5, dir));
							final WorldTile tpTile = lastTile;
							final ArrayList<Entity> targets = king.getPossibleTargets();
							WorldTasksManager.schedule(new WorldTask() {

								int step = 0;
								@Override
								public void run() {
									if(step == maxStep-1) {
										king.setCantInteract(false);
										king.setTarget(target);
										stop();
										return;
									}
									if (step == 1)
										king.setNextWorldTile(tpTile);
									WorldTile kingTile = new WorldTile(firstTile.getX() + (dirs[0] * step * 2), firstTile.getY() + (dirs[1] * step * 2), king.getPlane());
									int leftSteps = (maxStep - step) + 1;
									for (Entity t : targets) {
										if (!(t instanceof Player))
											continue;
										final Player player = (Player) t;
										if (player.isLocked())
											continue;
										//u got caught :L
										if (Utils.colides(kingTile, t, king.getSize(), 1)) {

											WorldTile lastTileForP = null;
											int stepCount = 0;
											for (int thisStep = 1; thisStep <= leftSteps; thisStep++) {
												WorldTile nextTile = new WorldTile(new WorldTile(player.getX() + (dirs[0] * thisStep * 2), player.getY() + (dirs[1] * thisStep * 2), player.getPlane()));
												if (!World.isFloorFree(nextTile.getPlane(), nextTile.getX(), nextTile.getY()))
													break; //found last tile(as cant move this means the one before this)
												lastTileForP = nextTile;
												stepCount = thisStep;
											}
											if (lastTileForP == null) //this player didnt get caught by 1step
												continue;
											player.setNextForceMovement(new NewForceMovement(player, 0, lastTileForP, stepCount, Utils.getAngle(firstTile.getX() - player.getX(), firstTile.getY() - player.getY())));
											player.setNextAnimation(new Animation(10070));
											player.lock(stepCount + 1);
											delayHit(npc, 0, t, getRegularHit(npc, Utils.random(180, 600)));
											final WorldTile lastTileForP_T = lastTileForP;

											WorldTasksManager.schedule(new WorldTask() {
												@Override
												public void run() {
													player.setNextWorldTile(lastTileForP_T);
													player.faceEntity(king);
												}
											}, 0);
										}
									}
									step++;
								}
							}, 3, 0);
							
							
							
						}
						return 17;
/*					case 2: //dig //DONE // dig isnt a special atk. its a normal atk.
						king.dig(target);
						break;*/
					case 3: //healing aura //DONE
						king.useAura();
						break;
				}
			}
			return defs.getAttackDelay();
		}else{
			if(npc.getId() == 16697 && !Utils.isOnRange(npc, target, 0))
				return 0;
			king.reduceMovesUntilSpecial();
			sendStandardAttack(npc, target);
		}
		return defs.getAttackDelay();
	}
}
