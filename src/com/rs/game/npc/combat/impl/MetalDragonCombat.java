package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.content.Combat;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.utils.Utils;

public class MetalDragonCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { "Bronze dragon", "Iron dragon", "Steel dragon",
				"Mithril dragon"};
	}

	@Override
	public int attack(NPC npc, Entity target) {
		NPCCombatDefinitions defs = npc.getCombatDefinitions();
		final Player player = target instanceof Player ? (Player) target : null;
		int damage = Utils.getRandom(650);
		int delay = 2;
		npc.setForceFollowClose(false);
		switch (Utils.getRandom(4)) {
		case 0:
			if (npc.withinDistance(player, 1, npc.getSize(), npc.getSize())) {
				damage = getRandomMaxHit(npc, defs.getMaxHit(),
						NPCCombatDefinitions.MELEE, target);
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				delayHit(npc, 0, target, getMeleeHit(npc, damage));
				return defs.getAttackDelay();
			} else {
				if(npc.getId() == 19109)
					npc.setNextAnimation(new Animation(14244));
				else
					npc.setNextAnimation(new Animation(13160));
				World.sendProjectile(npc, target, 393, 28, 16, 35, 35, 16, 0);
			}
		break;
		case 1:
			if (npc.withinDistance(player, 1, npc.getSize(), npc.getSize())) {
				delay = 1;
				if(npc.getId() == 19109)
					npc.setNextAnimation(new Animation(14245));
				else
					npc.setNextAnimation(new Animation(13164));
				npc.setNextGraphics(new Graphics(2465));
			} else {
				if(npc.getId() == 19109)
					npc.setNextAnimation(new Animation(14244));
				else
				npc.setNextAnimation(new Animation(13160));
				World.sendProjectile(npc, target, 393, 28, 16, 35, 35, 16, 0);
			}
		break;
	}
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
	delayHit(npc, delay, target, getRegularHit(npc, damage));
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