package com.rs.game.npc.combat.impl.eliteTrio;

import com.rs.game.Entity;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;

public class WightHunter extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] {22447};
	}

	@Override
	public int attack(NPC npc, Entity target) {
	//	return defs.getAttackDelay();
		return 1;
	}
}
