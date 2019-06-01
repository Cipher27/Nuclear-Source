package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class AutomatonCombat extends CombatScript {

    @Override
    public Object[] getKeys() {
	return new Object[] {16907};
    }
    
    
    
    
    @Override
    public int attack(final NPC npc, final Entity target) {
	NPCCombatDefinitions def = npc.getCombatDefinitions();
	int random = Utils.random(20);
	if (random == 1) {
	   npc.setNextAnimation(new Animation(19817));
			final WorldTile npcLocation = new WorldTile(npc);
			final WorldTile playerLocation = new WorldTile(target);
			npc.setNextGraphics(new Graphics(3861));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;
				@Override
				public void run() {
					Player player = (Player) target;
						if(target.withinDistance(npcLocation, 4))  {
							delayHit(npc, 0, player, new Hit(npc, Utils.random(120,380), HitLook.REGULAR_DAMAGE));	
						}
					if(count++ == 4) {
						stop();
						return;
					}
				}
			}, 0, 0);
	}else {
	    delayHit(npc, 0, target, getMeleeHit(npc, getRandomMaxHit(npc, npc.getMaxHit(), def.getAttackStyle(), target)));
	npc.setNextAnimation(new Animation(def.getAttackEmote()));
	return def.getAttackDelay();
	}
	return def.getAttackDelay();
    }
}
