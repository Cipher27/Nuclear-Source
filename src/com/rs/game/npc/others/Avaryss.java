package com.rs.game.npc.others;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

@SuppressWarnings("serial")
public class Avaryss extends NPC {


	public Avaryss(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setCapDamage(700);
		setLureDelay(3000);
		setForceTargetDistance(64);
		setForceFollowClose(false);
	}

    public boolean hitReflect = false;
	public boolean second = false;
	public static boolean healHit = false;
	public boolean hardHit = false;
	public int healAmount = 0;

	@Override
	public void processNPC() {
		super.processNPC();
		if (isDead())
			return;
		if(!isUnderCombat())
			return;
		int maxhp = getMaxHitpoints();
		if (maxhp > getHitpoints() && getPossibleTargets().isEmpty())
			setHitpoints(maxhp);
		if(Utils.random(250) <= 3 && !healHit && !second){ //will turn passive and will get healed by every hit he receives 
			healHit = true;
			 setNextForceTalk(new ForceTalk("Hit me my dear"));
			WorldTasksManager.schedule(new WorldTask() {
				int loop;

				@Override
				public void run() {
					setNextAnimation(new Animation(25719));
					 if(loop == 4){
						hardHit = true;
						applyHit(new Hit(null, healAmount, HitLook.HEALED_DAMAGE));
						healHit = false;
						stop();
					}
						
					loop++;
				}
			}, 0, 1); 
		}if(Utils.random(250) <= 3 && !healHit  && second){ //will give all damage to the tank
			healHit = true;
			 setNextForceTalk(new ForceTalk("Hit me my dear"));
			WorldTasksManager.schedule(new WorldTask() {
				int loop;

				@Override
				public void run() {
					setNextAnimation(new Animation(25719));
					 if(loop == 5){
						healHit = false;
						setNextForceTalk(new ForceTalk("KAMBOOOM"));
						healAmount = 0;
						stop();
					}
						
					loop++;
				}
			}, 0, 1); 
		}
	}

	@Override
	public void sendDeath(Entity source) {
		if(getId() == 20616){ //transforms into another stage 
			setCantInteract(true);
			second = true;
			hitReflect = true;
			WorldTasksManager.schedule(new WorldTask() {
				int loop;

				@Override
				public void run() {
					if (loop == 0) {
						setNextAnimation(new Animation(25718));
					} else if (loop == 1){
						transformIntoNPC(20612);
					    setNextForceTalk(new ForceTalk("You were so close my dear"));
					} else if(loop == 3){
						WorldTasksManager.schedule(new WorldTask() {

							@Override
							public void run() {
								reset();
								setCantInteract(false);
							}
							
						}, 5);
						stop();
					}
						
					loop++;
				}
			}, 0, 1);
		} else 
		super.sendDeath(source);
	}
	
	/**
	 * handles when the npc get hit
	 * stage 1: heal for a certain amount
	 * stage 2: deflects part of the damage taken
	 */
	@Override
	public void handleIngoingHit(Hit hit) {
		Entity source = hit.getSource();
		if(hardHit){
			if (source != null) {
				if (healAmount > 0)
			source.applyHit(new Hit(this, healAmount,HitLook.REFLECTED_DAMAGE));
			healAmount = 0;
			hardHit = false;
			}
		}
		if(healHit){
				healAmount += hit.getDamage();
					
		}
		if (hitReflect) {
			if (source != null) {
				int deflectedDamage = (int) (hit.getDamage() * 0.25);
				hit.setDamage((int) (hit.getDamage() * source.getMeleePrayerMultiplier()));
				if (deflectedDamage > 0)
					source.applyHit(new Hit(this, deflectedDamage,
							HitLook.REFLECTED_DAMAGE));
			}
		}
		super.handleIngoingHit(hit);
	}


}