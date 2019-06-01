package com.rs.game.player.robot.scripts.combat;

import com.rs.game.Entity;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.robot.scripts.Default;
import com.rs.net.decoders.handlers.ButtonHandler;

/**
 * @author Taht one guy
 */
public class RegularMage extends Default {

	private final int MELEE = 0, RANGE = 1, MAGE = 2;

	@Override
	public int[][] getSetId() {
		return new int[][] {
				{ 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41,
						42, 43, 44, 45, 46, 47, 48 }, {} };
	}

	public int getSpecialId() {
		return 1215;
	}

	public int[] getWeapons() {
		return new int[] { 4151, 4587, 4886, 21371 };
	}

	@Override
	public void processCombat() {
		switchPrayers(robot.getAttackedBy());
		if (!robot.getCombatDefinitions().isUsingSpecialAttack()) {
			if (robot.getCombatDefinitions().getSpecialAttackPercentage() >= PlayerCombat
					.getSpecialAmmount(getSpecialId())) {
				if (getAttackedBy().getHitpoints() <= 400) {
					if (robot.getEquipment().getWeaponId() != getSpecialId())
						ButtonHandler.sendWear(robot, robot.getInventory()
								.getItems().lookupSlot(getSpecialId()),
								getSpecialId());
					robot.getCombatDefinitions().switchUsingSpecialAttack();
					wait(3);
					return;
				}
			}
		}
		if (robot.getEquipment().getWeaponId() == getSpecialId()
				&& !robot.getCombatDefinitions().isUsingSpecialAttack()) {
			for (int i = 0; i < getWeapons().length; i++) {
				ButtonHandler.sendWear(robot, robot.getInventory().getItems()
						.lookupSlot(getWeapons()[i]), getWeapons()[i]);
			}
		}
		// if (robot.getLastVeng()-1000 < Utils.currentTimeMillis())
		// Magic.processLunarSpell(robot, 37, -1);
	}

	@Override
	public void processMovement() {
		// if frozen and food is less than 10 teleport
		// if teleblocked/20+wild attempt to run south
		// if 0 food attempt to run
	}

	private void switchPrayers(Entity target) {
		if (target instanceof Player) {
			final Player player = (Player) target;
			final int mageAtt = player.getCombatDefinitions().getBonuses()[3];
			final int rangeAtt = player.getCombatDefinitions().getBonuses()[4];
			int meleeAtt = player.getCombatDefinitions().getBonuses()[0];
			for (int i = 1; i < 3; i++) {
				if (player.getCombatDefinitions().getBonuses()[i] > meleeAtt)
					meleeAtt = player.getCombatDefinitions().getBonuses()[i];
			}
			final int attackType = mageAtt >= rangeAtt && mageAtt >= meleeAtt ? MAGE
					: (rangeAtt >= mageAtt && rangeAtt >= meleeAtt ? RANGE
							: (meleeAtt >= mageAtt && meleeAtt >= rangeAtt ? MELEE
									: MELEE));
			final int prayerActive = (player.getPrayer().usingPrayer(0, 17) || player
					.getPrayer().usingPrayer(1, 7)) ? MAGE : (player
					.getPrayer().usingPrayer(0, 18) || player.getPrayer()
					.usingPrayer(1, 8)) ? RANGE
					: (player.getPrayer().usingPrayer(0, 19) || player
							.getPrayer().usingPrayer(1, 9)) ? MAGE : -1;
			if (prayerActive >= 0) {
				final int prayer = robot.getPrayer().isAncientCurses() ? attackType == MAGE ? 7
						: attackType == RANGE ? 8 : attackType == MELEE ? 9 : 9
						: attackType == MAGE ? 17 : attackType == RANGE ? 18
								: attackType == MELEE ? 19 : 19;
				if (!robot.getPrayer().usingPrayer(prayer))
					robot.getPrayer().switchPrayer(prayer);
				return;
			}
			final int smiteSoulSplit = robot.getPrayer().isAncientCurses() ? 18
					: 24;
			if (!robot.getPrayer().usingPrayer(smiteSoulSplit))
				robot.getPrayer().switchPrayer(smiteSoulSplit);
		}
	}
}
