package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
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
import com.rs.utils.Utils;

public class WorldGorgerCombat extends CombatScript {
	
	/*
	 * @Paolo, 
	 1 attack: sends a gfx where you have to stand on, otherwise you'll get damage and gorger will get healed
	 2 attack: sends a gfx where you can't stanf on otherwise you'll get huge amount of damage
	 3 attack: send a project, when you walk pas the projectile or in his trail it will deal damage
	 4 attack: normal attack with melee
	 5 attack: will heal him and damages you with magic damage
	 6 attack: same as the 5th.
	 */

	public static final String[] ATTACKS = new String[] {
		 "Give me some of your lifes!",
		"Why do you even try ?", "Paolo learned me this one!"
	};
	
	@Override
	public Object[] getKeys() {
		return new Object[] {12500};
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		final Player player = (Player) target;
		int attackStyle = Utils.random(6);
		if(attackStyle == 0) {
			npc.setNextAnimation(new Animation(14897));
			final WorldTile center = new WorldTile(npc);
			final WorldTile playerLocation = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(3414), center);
			npc.setNextForceTalk(new ForceTalk("I'm taking your soul!"));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;
				@Override
				public void run() {
					 //lets just loop all players for massive moves
						if(player == null || player.isDead() || player.hasFinished())
							return;
						if(!player.withinDistance(center, 1)) {
							delayHit(npc, 0, player, new Hit(npc, Utils.random(100), HitLook.REGULAR_DAMAGE));
							npc.applyHit(new Hit(player, 20, HitLook.HEALED_DAMAGE));
						}if(player.withinDistance(center, 1)) {
							World.sendGraphics(npc, new Graphics(-1), playerLocation);
							player.setNextGraphics(new Graphics(111));
		                    player.applyHit(new Hit(player, 50, HitLook.HEALED_DAMAGE));
						}
					if(count++ == 4) {
						stop();
						return;
					}
				}
			}, 0, 0);
		}
			else if(attackStyle == 1) {
			npc.setNextAnimation(new Animation(14893));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(5280), center);
			npc.setNextForceTalk(new ForceTalk("MOVE! before I kill you!"));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;
				@Override
				public void run() {
					
						if(player == null || player.isDead() || player.hasFinished())
							return;
						if(player.withinDistance(center, 1)) {
							delayHit(npc, 0, player, new Hit(npc, Utils.random(200), HitLook.REGULAR_DAMAGE));
						}
					
					if(count++ == 10) {
						stop();
						return;
					}
			}
				
			}, 0, 0);
		}else if(attackStyle == 2) {
			npc.setNextAnimation(new Animation(14894));
			final int dir = Utils.random(Utils.DIRECTION_DELTA_X.length);
			final WorldTile center = new WorldTile(npc.getX() + Utils.DIRECTION_DELTA_X[dir] * 5, npc.getY() + Utils.DIRECTION_DELTA_Y[dir] * 5, 0);
			npc.setNextForceTalk(new ForceTalk("I think it's time to clean my room!"));
		//	player.sm("<col=fffff>Don't walk in the trail of those clouds.");
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;
				@Override
				public void run() {
						if(Utils.DIRECTION_DELTA_X[dir] == 0) {
							if(player.getX() != center.getX())
								return;
						}
						if(Utils.DIRECTION_DELTA_Y[dir] == 0) {
							if(player.getY() != center.getY())
								return;
						}
						if(Utils.DIRECTION_DELTA_X[dir] != 0) {
							if(Math.abs(player.getX() - center.getX()) > 5)
								return;
						}
						if(Utils.DIRECTION_DELTA_Y[dir] != 0) {
							if(Math.abs(player.getY() - center.getY()) > 5)
								return;
						}
						delayHit(npc, 0, player, new Hit(npc, Utils.random(150), HitLook.REGULAR_DAMAGE));
					
					if(count++ == 5) {
						stop();
						return;
					}
				}
			}, 0, 0);
			World.sendProjectile(npc, center, 3433, 35, 35, 5, 35, 0, 0);
		}else if(attackStyle == 3) {
			npc.setNextAnimation(new Animation(14892));
			npc.setNextForceTalk(new ForceTalk(ATTACKS[Utils.random(ATTACKS.length)]));
			target.applyHit(new Hit(target, Utils.random(250), HitLook.MELEE_DAMAGE));
		}else if(attackStyle == 4) {
			npc.setNextAnimation(new Animation(14892));
			target.applyHit(new Hit(target, Utils.random(150), HitLook.MAGIC_DAMAGE));
			World.sendProjectile(npc, target, 3433, 15, 15, 45, 35, 0, 0);
			target.setNextGraphics(new Graphics(3434));
			npc.applyHit(new Hit(target, Utils.random(200), HitLook.HEALED_DAMAGE));
		}else if(attackStyle == 5) {
			npc.setNextAnimation(new Animation(14892));
			target.applyHit(new Hit(target, Utils.random(250), HitLook.MELEE_DAMAGE));
			npc.setNextForceTalk(new ForceTalk(ATTACKS[Utils.random(ATTACKS.length)]));
		}
		return 6;
	}

}
