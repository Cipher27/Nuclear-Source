package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.content.Combat;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.utils.Utils;

public class KingBlackDragonCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 50 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.getRandom(5);
		final int size = npc.getSize();
		final Player player = target instanceof Player ? (Player) target : null;
		int	damage = Utils.getRandom(650);
		String type = "fiery";
		if (attackStyle == 0) {
			final int distanceX = target.getX() - npc.getX();
			final int distanceY = target.getY() - npc.getY();
			if (distanceX > size || distanceX < -1 || distanceY > size
					|| distanceY < -1)
				attackStyle = Utils.getRandom(4) + 1;
			else {
				delayHit(
						npc,
						0,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, target)));
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				return defs.getAttackDelay();
			}
		} else if (attackStyle == 1 || attackStyle == 2) {
			type = "fiery";
			World.sendProjectile(npc, target, 393, 34, 16, 30, 35, 16, 0);
			npc.setNextAnimation(new Animation(17786));
		} else if (attackStyle == 3) {
			type = "poisonous";
			if (Utils.getRandom(2) == 0)
				target.getPoison().makePoisoned(80);
			World.sendProjectile(npc, target, 394, 34, 16, 30, 35, 16, 0);
			npc.setNextAnimation(new Animation(17785));
		} else if (attackStyle == 4) {
			type = "freezing";
			if (Utils.getRandom(2) == 0)
				target.addFreezeDelay(15000);
			World.sendProjectile(npc, target, 395, 34, 16, 30, 35, 16, 0);
			npc.setNextAnimation(new Animation(17783));
		} else {
			type = "shocking";
			World.sendProjectile(npc, target, 396, 34, 16, 30, 35, 16, 0);
			npc.setNextAnimation(new Animation(17784));
		}
		int protection = usingAntiFireShield(player, type) + usingProtectFromMagic(player, type) + usingAntiFirePotion(player, type);
		if(player.getPerkHandler().perks.contains(Perk.DRAGONFIRE_PERK)){
			player.sm("Your Dragonfire perk absorbs all of the damage.");
			protection = 3;
		}
		switch(protection) {
			case 0:
				if (player != null)
					player.sm("You are hit by the dragon's "+type+" breath!");
				break;
			case 1:
				damage /= 2;
				break;
			case 2:
				damage /= 4;
				break;
			default:
				damage = 0;
				break;
		}
		delayHit(npc, 2, target, getRegularHit(npc, damage));
		return defs.getAttackDelay();
	}
	
	public int usingProtectFromMagic(Player player, String breath) {
		if (player != null) {
			boolean prot = player.getPrayer().usingPrayer(0, 17) || player.getPrayer().usingPrayer(1, 7);
			if (prot) {
				if(!player.getPerkHandler().perks.contains(Perk.DRAGONFIRE_PERK))
				player.sm("Your potion absorbs most of the dragon's "+breath+" breath!");
				return 1;
			}
		}
		return 0;
	}
	
	public int usingAntiFirePotion(Player player, String breath) {
		if (player != null) {
			if (player.getSuperFireImmune() > Utils.currentTimeMillis())
				return 2;
			boolean prot = player.getFireImmune() > Utils.currentTimeMillis();
			if (prot) {
				if(!player.getPerkHandler().perks.contains(Perk.DRAGONFIRE_PERK))
				player.sm("Your potion absorbs most of the dragon's "+breath+" breath!");
				return 1;
			}
		}
		return 0;
	}
	
	public int usingAntiFireShield(Player player, String breath) {
		if (player != null) {
			boolean prot = Combat.hasAntiDragProtection(player);
			if (prot) {
				if(!player.getPerkHandler().perks.contains(Perk.DRAGONFIRE_PERK))
				player.sm("Your shield absorbs most of the dragon's "+breath+" breath!");
				return 1;
			}
		}
		return 0;
	}
}
