package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.content.Combat;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.utils.Utils;

public class LeatherDragonCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { "Green dragon", "Blue dragon", "Red dragon",
				"Black dragon", 742, 14548, 19109,18100};
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int distanceX = target.getX() - npc.getX();
		int distanceY = target.getY() - npc.getY();
		int size = npc.getSize();
		if (distanceX > size || distanceX < -1 || distanceY > size || distanceY < -1)
			return 0;
		if (Utils.getRandom(3) != 0) {
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			
			int randomHit = getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MELEE, target);
			Hit hit = getMeleeHit(npc, randomHit);
			
			delayHit(npc, 0, target, hit);
		} else {
			int damage = Utils.getRandom(650);
			npc.setNextAnimation(new Animation(12259));
			npc.setNextGraphics(new Graphics(1, 0, 100));
			
			final Player player = target instanceof Player ? (Player) target : null;
			String type = "fiery";
			int protection = usingAntiFireShield(player, type) + usingProtectFromMagic(player, type) + usingAntiFirePotion(player, type);
			if(player.getPerkHandler().perks.contains(Perk.DRAGONFIRE_PERK)){
				player.sm("Your Dragonfire perk absorbs all of the damage.");
				protection = 3;
			}
			switch(protection) {
				case 0:
					player.sm("You are hit by the dragon's "+type+" breath!");
					break;
				case 1:
					damage /= 6;
					break;
				case 2:
					damage /= 12;
					break;
				case 3:
					damage = 0;
					break;
			}
			if (player.getSuperFireImmune() > Utils.currentTimeMillis())
				damage = 0;
			delayHit(npc, 1, target, getRegularHit(npc, damage));
		}
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
			boolean prot = player.getFireImmune() > Utils.currentTimeMillis() || player.getSuperFireImmune() > Utils.currentTimeMillis();
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
