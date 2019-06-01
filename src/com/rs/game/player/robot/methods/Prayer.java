package com.rs.game.player.robot.methods;

import com.rs.game.Entity;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.robot.Robot;

/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public class Prayer extends Method {
	
	public Prayer(Robot robot) {
		super(robot);
		stage = Stage.Running;
	}
	
	@Override
	public void process() {
		checkPrayers();
	}
	
	public void checkPrayers() {
		boolean praying = 
				getScript().combat.stage == Stage.Fighting
				|| getScript().combat.stage == Stage.Running
				|| getScript().pickup.stage == Stage.Running
				|| robot.getAttackedByDelay() > System.currentTimeMillis()
				|| stage == Stage.Protecting;
		boolean inCombatZone = robot.isCanPvp();
		if (!inCombatZone) {
			praying = false;
		} else {
			if (!robot.getPrayer().usingPrayer(0)) // protect item
				robot.getPrayer().switchPrayer(0);
		}
		if (!praying) {
			robot.getPrayer().closeAllPrayers();
			return;
		}
		if (robot.getPrayer().getPrayerpoints() < scalePrayer(300)) {
				/*robot.getPrayer().outOfPrayer()*/
			getScript().combat.setStage(Stage.Running);
			return;
		}
		boolean usingCurses = robot.getPrayer().isAncientCurses();
		boolean ranging = PlayerCombat.isRanging(robot) != 0;
		boolean usingRange = ranging;
		int spellId = robot.getCombatDefinitions().getSpellId();
		if (spellId < 1 && PlayerCombat.hasPolyporeStaff(robot)) {
		    spellId = 65535;
		}
		if (spellId < 1 && PlayerCombat.hasLightningStaff(robot)) {
		    spellId = 65534;
		}
		boolean usingMage = spellId > 0;
		boolean usingMelee = !ranging && !usingMage;
		if (usingCurses) {
			if (!robot.getPrayer().usingPrayer(0)) // protect item
				robot.getPrayer().switchPrayer(0);
			if (!robot.getPrayer().usingPrayer(5)) // berserker
				robot.getPrayer().switchPrayer(5);
			if (usingMelee) {
				if (!robot.getPrayer().usingPrayer(19)) // turmoil
					robot.getPrayer().switchPrayer(19);
			} else {
				if (!robot.getPrayer().usingPrayer(11)) // leech
					robot.getPrayer().switchPrayer(11);
				if (!robot.getPrayer().usingPrayer(12)) // leech
					robot.getPrayer().switchPrayer(12);
				if (!robot.getPrayer().usingPrayer(13)) // leech
					robot.getPrayer().switchPrayer(13);
			}
			if (shouldProtect()) {
				protect();
			} else {
				if (!robot.getPrayer().usingPrayer(18)) // soul split
					robot.getPrayer().switchPrayer(18);
			}
		} else {
			if (usingMelee) {
				if (robot.getSkills().getLevel(Skills.DEFENCE) >= 70) {
					prayerLoop(MELEE_PRAYERS);
				} else {
					prayerLoop(DEFENCE_PRAYERS);
					prayerLoop(STRENGTH_PRAYERS);
					prayerLoop(ATTACK_PRAYERS);
				}
			} else if (usingRange) {
				prayerLoop(RANGE_PRAYERS);
			} else if (usingMage) {
				prayerLoop(MAGIC_PRAYERS);
			}
			int headPrayer = 24;
			turnOnPrayer(headPrayer);//smite
			turnOnPrayer(10);//protect item
			if (shouldProtect()) {
				protect();
			} else {
				turnOnPrayer(headPrayer);//smite
			}
		}
		//if (robot.getControlerManager().getControler() instanceof Wilderness && robot.getPrayer().getPrayerpoints() == 0)
		//	getScript().combat.setStage(Stage.Running);
	}
	
	public int scalePrayer(int prayer) {
		int level = robot.getSkills().getLevelForXp(5);
		return prayer * (99 / level);
	}
	
	public void prayerLoop(int[] prayerIds) {
		boolean usingPrayer = false;
		for(int prayerId : prayerIds) {
			if (robot.getPrayer().usingPrayer(prayerId))
				usingPrayer = true;
		}
		if (!usingPrayer) {
			for(int prayerId : prayerIds) {
				if (robot.getPrayer().canUsePrayer(prayerId))
					robot.getPrayer().switchPrayer(prayerId);
				break;
			}
		}
	}
	
	public void turnOnPrayer(int prayerId) {
		if (!robot.getPrayer().usingPrayer(prayerId))
			if (robot.getPrayer().canUsePrayer(prayerId))
				robot.getPrayer().switchPrayer(prayerId);
	}
	
	public final static int[] MAGIC_PRAYERS = new int[] {
		29, 21, 12, 4
	};
	
	public final static int[] RANGE_PRAYERS = new int[] {
		28, 20, 11, 3
	};
	
	public final static int[] MELEE_PRAYERS = new int[] {
		27, 25
	};
	
	public final static int[] DEFENCE_PRAYERS = new int[] {
		8, 5, 1
	};
	
	public final static int[] STRENGTH_PRAYERS = new int[] {
		9, 6, 2
	};
	
	public final static int[] ATTACK_PRAYERS = new int[] {
		10, 7, 3
	};
	
	public boolean shouldProtect() {
		int MELEE = 0, RANGE = 1, MAGE = 2;
		if (stage == Stage.Protecting)
			return true;
		if (getScript().combat.stage == Stage.Running)
			return true;
		if (!(getScript().combat.target instanceof Player))
			return false;
		Player target = (Player) getScript().combat.target;
		final int prayerActive = (target.getPrayer().usingPrayer(0, 17) || target
				.getPrayer().usingPrayer(1, 7)) ? MAGE : (target
				.getPrayer().usingPrayer(0, 18) || target.getPrayer()
				.usingPrayer(1, 8)) ? RANGE
				: (target.getPrayer().usingPrayer(0, 19) || target
						.getPrayer().usingPrayer(1, 9)) ? MELEE : -1;
		if (prayerActive >= 0)
			return true;
		return false;
	}
	
	public void protect() {
		int MELEE = 0, RANGE = 1, MAGE = 2;
		Entity entity = getScript().combat.target;
		Player target = null;
		if (!(entity instanceof Player)) {
			entity = robot.getAttackedBy();
			if (!(entity instanceof Player)) {
				int prayer = robot.getPrayer().isAncientCurses() ? 9 : 19;
				if (!robot.getPrayer().usingPrayer(prayer))
					robot.getPrayer().switchPrayer(prayer);
				return;
			}
		}
		target = (Player) entity;
		@SuppressWarnings("unused")
		final int mageDef = target.getCombatDefinitions().getBonuses()[CombatDefinitions.MAGIC_DEF] * 2;
		@SuppressWarnings("unused")
		final int rangeDef = target.getCombatDefinitions().getBonuses()[CombatDefinitions.RANGE_DEF];
		int meleeDef = target.getCombatDefinitions().getBonuses()[5];// 5,6,7
		for (int i = 6; i < 8; i++) {
			if (target.getCombatDefinitions().getBonuses()[i] > meleeDef)
				meleeDef = target.getCombatDefinitions().getBonuses()[i];
		}
		final int mageAtt = (int) (target.getCombatDefinitions().getBonuses()[CombatDefinitions.MAGIC_ATTACK] * 1.3);
		final int rangeAtt = target.getCombatDefinitions().getBonuses()[CombatDefinitions.RANGE_ATTACK];
		int meleeAtt = (target.getCombatDefinitions().getBonuses()[0]/* * 1.3*/);
		for (int i = 1; i < 3; i++) {
			if ((target.getCombatDefinitions().getBonuses()[i]/* * 1.3*/) > meleeAtt)
				meleeAtt = (target.getCombatDefinitions()
						.getBonuses()[i]/* * 1.3*/);
		}
		
		boolean ranging = PlayerCombat.isRanging(target) != 0;
		boolean usingRange = ranging;
		int spellId = target.getCombatDefinitions().getSpellId();
		if (spellId < 1 && PlayerCombat.hasPolyporeStaff(target)) {
		    spellId = 65535;
		}
		if (spellId < 1 && PlayerCombat.hasLightningStaff(target)) {
		    spellId = 65534;
		}
		boolean usingMage = spellId > 0;
		@SuppressWarnings("unused")
		boolean usingMelee = !ranging && !usingMage;
		
		final int attackType = usingMage || (mageAtt >= rangeAtt && mageAtt >= meleeAtt) ? MAGE
				: (usingRange || (rangeAtt >= mageAtt && rangeAtt >= meleeAtt) ? RANGE
						: MELEE);
		
		int prayer = robot.getPrayer().isAncientCurses() ? 
				attackType == MAGE ? 7 : attackType == RANGE ? 8 : attackType == MELEE ? 9 : 9 : 
				attackType == MAGE ? 17 : attackType == RANGE ? 18 : attackType == MELEE ? 19 : 19;
		if (!robot.getPrayer().usingPrayer(prayer))
			robot.getPrayer().switchPrayer(prayer);
	}
}
