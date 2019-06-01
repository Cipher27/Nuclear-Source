package com.rs.game.npc.combat.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
import com.rs.game.npc.others.NecroLord;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class NecrolordCombat extends CombatScript {
	
	/*
	 *
	 */

	public static final String[] ATTACKS = new String[] {
		 "Everyone has some darkness inside",
		"Have you ever tried to forgive her?", "Darkness is what keeps me life"
	};
	
	@Override
	public Object[] getKeys() {
		return new Object[] {11751};
	}
	 @Override
    public int attack(NPC npc, final Entity target) {
	final NecroLord boss = (NecroLord) npc;
	int damage;
	if (Utils.random(10) == 0 && boss.getSkeletonAmount() < 15) {
	    final int skeletonCount = 3;
	    final List<WorldTile> projectileTile = new LinkedList<WorldTile>();
	    WorldTasksManager.schedule(new WorldTask() {
		int cycles;

		@Override
		public void run() {
		    cycles++;

		    if (cycles == 2) {
			for (int i = 0; i < skeletonCount; i++) {
			    WorldTile tile = Utils.getFreeTile(
				    boss.getTile(),
				    4);
			    projectileTile.add(tile);
			    World.sendProjectile(boss, tile, 2590, 65, 0, 30, 0, 16, 0);
			}
		    } else if (cycles == 4) {
			for (WorldTile tile : projectileTile){
				if(tile.getX() > 3471)
					tile = new WorldTile(3471 , tile.getX(), 0);
				if(tile.getX() < 3460)
					tile = new WorldTile(3460 , tile.getX(), 0);
				if(tile.getY() > 3737)
					tile = new WorldTile(tile.getX() , 3737, 0);
				if(tile.getY() < 3725)
					tile = new WorldTile(3725 , tile.getX(), 0);
				
			    boss.addSkeleton(tile);
			}
			stop();
			return;
		    }
		}
	    }, 0, 0);
	}

	final int attack = Utils.random(5);
	switch (attack) {
	case 0:// main attack
	case 1:
	    npc.setNextAnimation(new Animation(14209));
	    npc.setNextGraphics(new Graphics(2716));
	    World.sendProjectile(npc, target, 2721, 38, 18, 50, 50, 0, 0);
	    delayHit(npc, 1, target, getMagicHit(npc, getRandomMaxHit(npc, npc.getMaxHit(), NPCCombatDefinitions.MAGE, target)));
	    target.setNextGraphics(new Graphics(2726, 75, 80));
	    break;
	case 2:
	case 3:
	    final WorldTile tile = new WorldTile(target);
	    npc.setNextAnimation(new Animation(attack == 2 ? 710 : 729));
	    npc.setNextGraphics(new Graphics(attack == 2 ? 177 : 167, 0, 65));
	    World.sendProjectile(npc, tile, attack == 2 ? 178 : 168, 40, 18, 55, 70, 5, 0);
	    WorldTasksManager.schedule(new WorldTask() {
		@Override
		public void run() {
		    for (Entity t : boss.getPossibleTargets()) {
			int damage = getRandomMaxHit(boss, boss.getMaxHit(), NPCCombatDefinitions.MAGE, t);
			if (!t.withinDistance(tile, 1))
			    continue;
			if (damage > 0) {
			    if (attack == 2)
				t.addFreezeDelay(8);
			    else {
				if (t instanceof Player) {
				    Player p2 = (Player) t;
				    p2.getPackets().sendGameMessage("You feel weary.");
				    p2.setRunEnergy((int) (p2.getRunEnergy() * .5));
				}
				t.applyHit(new Hit(boss, Utils.random(boss.getMaxHit()) + 1, HitLook.MAGIC_DAMAGE));
			    }
			    t.setNextGraphics(new Graphics(attack == 2 ? 179 : 169, 60, 65));
			}
		    }
		}
	    }, 1);

	    break;
	case 4:
		damage =  (int) (target.getHitpoints() * 0.25);
		npc.setNextAnimation(new Animation(14209));	
		npc.setNextForceTalk(new ForceTalk("I love it when you have so much health!"));
		target.applyHit(new Hit(target, damage, HitLook.REFLECTED_DAMAGE));
		npc.applyHit(new Hit(target, damage/2, HitLook.HEALED_DAMAGE));
		target.setNextGraphics(new Graphics(376));
		break;
	}
	return Utils.random(2) == 0 ? 4 : 5;
    }

	/*@Override
	public int attack(final NPC npc, Entity target) {
		final Player player = (Player) target;
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.random(10);
		int damage;
	//	Hit= hit damage;
		int pick = new Random().nextInt( NPC.Locations.values().length); //to grab a random location
		final NPC.Locations spawns =  NPC.Locations.values()[pick]; //location for the spawns
		//mage attack, spawns skelettons.
		if(attackStyle == 0) { 
			damage =  (int) (target.getHitpoints() * 0.25);
		npc.setNextAnimation(new Animation(14209));	
		npc.setNextForceTalk(new ForceTalk("I love it when you have so much health!"));
		target.applyHit(new Hit(target, damage, HitLook.REFLECTED_DAMAGE));
		npc.applyHit(new Hit(target, damage/2, HitLook.HEALED_DAMAGE));
		target.setNextGraphics(new Graphics(376));
		}
			else if(attackStyle == 1) {
			if (npc.withinDistance(target, 2)) {
			npc.setNextAnimation(new Animation(13044));
				damage = Utils.random(180);
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				delayHit(npc, 0, target, getMeleeHit(npc, damage));
			} else {
		  npc.setNextAnimation(new Animation(14209));
		  target.getPoison().makePoisoned(100);
		  npc.setNextForceTalk(new ForceTalk("Here some poison!")); 
		  }
		}else if(attackStyle == 2) {
			npc.setNextAnimation(new Animation(14210));
			String name = "";
			if (target instanceof Player)
			name = ((Player) target).getDisplayName();
			npc.setNextForceTalk(new ForceTalk("Kill "+name+ " my skeletons!"));
			World.sendProjectile(npc, new WorldTile(spawns.getTile().getX(), spawns.getTile().getY(), 0), 1520, 35, 5, 5, 10, 0, 0);
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;
				@Override
				public void run() {
					if(count++ == 3) {
					World.sendGraphics(npc, new Graphics(1513),  new WorldTile(spawns.getTile().getX(), spawns.getTile().getY(), 0));
					World.spawnNPC(2037, new WorldTile(spawns.getTile().getX(), spawns.getTile().getY(), 0), -1, true, true, true);
						stop();
					player.sm("He spawned some help.");
						return;
					}
				}
			}, 0, 0);
		}else {
		npc.setNextAnimation(new Animation(14209));
		World.sendProjectile(npc, target, defs.getAttackProjectile(), 41, 16, 41, 35, 16, 0);
		npc.setNextGraphics(new Graphics(5238));
		damage = Utils.random(260);
		delayHit(npc, 2, target, getMagicHit(npc, damage));
		//TeleportAway(npc, target);	 need to redo this
		}
		return 4;
	}
	
	private void TeleportAway(NPC npc, Entity target) {
		if (Utils.getRandom(1) == 0) {
		WorldTile teleTile = target;
		for (int trycount = 0; trycount < 10; trycount++) {
			teleTile = new WorldTile(target, 2);
			if (World.canMoveNPC(target.getPlane(), teleTile.getX(),
					teleTile.getY(), target.getSize()));
			continue;
		}
		target.setNextWorldTile(teleTile);
		target.setNextGraphics(new Graphics(409));
		} else {
			WorldTile teleTile = npc;
			for (int trycount = 0; trycount < 10; trycount++) {
				teleTile = new WorldTile(npc, 2);
				if (World.canMoveNPC(npc.getPlane(), teleTile.getX(),
						teleTile.getY(), npc.getSize()));
				continue;
			}
			npc.setNextWorldTile(teleTile);
			npc.setNextGraphics(new Graphics(409));
		}
	}*/
	

}
