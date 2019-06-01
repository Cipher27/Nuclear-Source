package com.rs.game.npc.combat.impl;

import java.util.ArrayList;
import java.util.TimerTask;

import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

/**
 * 
 * @author Paolo
 *
 */

public class VoragoCombat extends CombatScript {
/**
  * the object is the same as the npc id replace the 
  **/
	@Override
	public Object[] getKeys() {
		return new Object[] { 14416 };
	}

	public boolean spawnLRC = false;

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int size = npc.getSize();
		final ArrayList<Entity> possibleTargets = npc.getPossibleTargets();
		boolean stomp = false;
		for (Entity t : possibleTargets) {
			int distanceX = t.getX() - npc.getX();
			int distanceY = t.getY() - npc.getY();
			if (distanceX < size && distanceX > -1 && distanceY < size
					&& distanceY > -1) {
				stomp = true;
				delayHit(
						npc,
						0,
						t,
						getRegularHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, t)));
			}
		}

		
		if (Utils.getRandom(45) == 0) { // Vorago's Heal
			npc.heal(450);
			npc.setNextAnimation(new Animation(20382));
			npc.setNextForceTalk(new ForceTalk("I love healing!"));
		}
		if (target.withinDistance(npc) && Utils.getRandom(2) == 1) {// Melee Attack
			npc.setNextAnimation(new Animation(20363));
			target.setNextGraphics(new Graphics(1834));
			delayHit(
					npc,
					0,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, Utils.random(400),
									NPCCombatDefinitions.MELEE, target)));

		}
			if (Utils.getRandom(8) == 0) { 
		// else {// magic
			for (Entity t : npc.getPossibleTargets()) {
				npc.setNextAnimation(new Animation(20356));
				delayHit(
						npc,
						0,
						t,
						getRegularHit(
								npc,
								getRandomMaxHit(npc, 500,
										NPCCombatDefinitions.SPECIAL, t)));
				World.sendProjectile(npc, t, 3096, 41, 16, 41, 35, 16, 0);
			}
		}
		if (Utils.getRandom(15) == 0) {// Huge Ranged Attack
			for (Entity t : npc.getPossibleTargets()) {
				npc.setNextAnimation(new Animation(20369));
				npc.setNextForceTalk(new ForceTalk(
						"FACE THE WRATH OF THE EARTH SHATTERING AROUND YOU!"));
				t.setNextGraphics(new Graphics(2941));
				npc.setNextGraphics(new Graphics(3263));
			//	t.playSound(1097, 1);
				Player player = (Player) t;
				player.getPackets()
						.sendGameMessage(
								"<col=FF5000>WARNING: Vorago has broken off pieces of himself. Protect missiles now!</col>");
				CoresManager.fastExecutor.schedule(new TimerTask() {
					@Override
					public void run() {
						for (Entity t : npc.getPossibleTargets()) {
							delayHit(
									npc,
									1,
									t,
									getRangeHit(
											npc,
											getRandomMaxHit(npc, 10000,
													NPCCombatDefinitions.RANGE,
													t)));
							World.sendProjectile(npc, target, 2954, 41, 16, 41,
									35, 16, 0);
						}
					}
				}, 5000);
			}
		} else if (Utils.getRandom(20) == 0) {// Huge Mage Attack
			for (Entity t : npc.getPossibleTargets()) {
				npc.setNextAnimation(new Animation(20355));
				npc.setNextForceTalk(new ForceTalk(
						"THE GROUND WILL CONSUME YOU!"));
				t.setNextGraphics(new Graphics(3228));
			//	t.playSound(1097, 1);
				Player player = (Player) t;
				player.getPackets()
						.sendGameMessage(
								"<col=FF5000>WARNING: Vorago is channeling a large magic attack. Protect magic now!</col>");
				CoresManager.fastExecutor.schedule(new TimerTask() {
					@Override
					public void run() {
						for (Entity t : npc.getPossibleTargets()) {
							npc.setNextGraphics(new Graphics(2963));
							//t.playSound(1342, 1);
							delayHit(
									npc,
									1,
									t,
									getMagicHit(
											npc,
											getRandomMaxHit(npc, Utils.random(1200),
													NPCCombatDefinitions.MAGE,
													t)));
							World.sendProjectile(npc, target, 2939, 41, 16, 41,
									35, 16, 0);
						}
					}
				}, 5000);
			}
		}
		return defs.getAttackDelay();
	}
}
