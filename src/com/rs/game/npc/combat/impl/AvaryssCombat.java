package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.others.Avaryss;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class AvaryssCombat extends CombatScript {

   @Override
	public Object[] getKeys() {
		//return new Object[] { 22453,22454, };
		return new Object[] { 20612,20614,20616};
	}
	
	//emotes
	/*
	attack 1 arm = 25721
	attack 2 arms = 25720
	25719 flying
	25718 death 
	*/
	private static int amount = 0;

	@Override
	public int attack(final NPC npc, final Entity target) {
		final Player player = (Player) target;
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if(npc.isCantInteract() == false && Avaryss.healHit == false){
			if (Utils.getRandom(20) <= 2) { //random talkings
				switch (Utils.getRandom(2)) {
				case 0:
					npc.setNextForceTalk(new ForceTalk("Why did he leave me?"));
					break;
				case 1:
					npc.setNextForceTalk(new ForceTalk(""));
					break;
				case 2:
					npc.setNextForceTalk(new ForceTalk(""));
					break;
				}
			}
			if(amount == 5){
				amount = 0;
				npc.setNextAnimation(new Animation(25720));
				Hit hit = getMeleeHit(npc, getRandomMaxHit(npc,npc.getCombatDefinitions().getMaxHit(),NPCCombatDefinitions.MELEE, target));
				player.applyHit(new Hit(npc,npc.getCombatDefinitions().getMaxHit() + Utils.random(200),HitLook.CRITICAL_DAMAGE));
				sendSoulSplit(hit,npc, target); //heals him
			} else {
			npc.setNextAnimation(new Animation(25721));
			Hit hit = getMeleeHit(npc, getRandomMaxHit(npc,npc.getCombatDefinitions().getMaxHit(),NPCCombatDefinitions.MELEE, target));
			delayHit(npc, 0, target, hit);
			player.getPrayer().drainPrayer((Math.round(Utils.random(10,50)))); //drains prayer every hit
			sendSoulSplit(hit,npc, target); //heals him
			amount++;
				}
		}
		return defs.getAttackDelay();
	
	}
    

	protected void sendSoulSplit(Hit hit, NPC npc, Entity target) {
		    if (target instanceof Player)
				((Player) target).sendSoulSplit(hit, npc);
		}
}
