package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;

public class BalanceElementalCombat extends CombatScript {
	
	
	private boolean melee = false;
	private boolean ranged = true;
	private boolean magic = false;

    @Override
    public Object[] getKeys() {
	return new Object[] { 8283 };
    }
     
    @Override
    public int attack(NPC npc, Entity target) {
	NPCCombatDefinitions def = npc.getCombatDefinitions();
	int random = Utils.random(10);
	if (random == 1) { //melee transformation
	if (melee == false && ranged == true && magic == false) {
	npc.setNextNPCTransformation(8282);
	//npc.transformIntoNPC(8282);
	npc.setNextAnimation(new Animation(10678));
	melee = true;
	ranged = false;
	magic = false;
	} if (ranged == false && melee == true && magic == false) {
	npc.setNextNPCTransformation(8283);
	npc.setNextAnimation(new Animation(10677));
	melee = false;
	ranged = true;
	magic = false;	
	}
	} else {
	if (melee == true){
	npc.setNextAnimation(new Animation(10669));
	delayHit(npc,14,target,getMeleeHit(npc, Utils.random(320)));	
	}if (ranged == true){
	npc.setNextAnimation(new Animation(10681));
	delayHit(npc,14,target,getRangeHit(npc, Utils.random(320)));		
	}if (magic == true){
	World.sendProjectile(npc,target,2718, 40, 40, 50, 50, 0, 0);
    delayHit(npc,14,target,getMagicHit(npc, Utils.random(50)));	
	}
	}
	return def.getAttackDelay();
    }

}
