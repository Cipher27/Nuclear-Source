package com.rs.game.npc.qbd;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.Player;
import com.rs.game.player.content.Combat;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * Represents a default fire breath attack.
 * @author Emperor
 *
 */
public final class FireBreathAttack implements QueenAttack {

	/**
	 * The animation of the attack.
	 */
	private static final Animation ANIMATION = new Animation(16721);
	
	/**
	 * The graphic of the attack.
	 */
	 
	private static final Graphics GRAPHIC = new Graphics(3143);
	
	@Override
	public int attack(final QueenBlackDragon npc, final Player victim) {
		npc.setNextAnimation(ANIMATION);
		npc.setNextGraphics(GRAPHIC);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				super.stop();
				/*String message = getProtectMessage(victim);
				int hit;
				if (message != null) {
					hit = Utils.random(60 + Utils.random(150), message.contains("prayer") ? 460 : 235);
					victim.getPackets().sendGameMessage(message);
				} if (victim.getSuperFireImmune() > Utils.currentTimeMillis()) {
					victim.getPackets().sendGameMessage("Your potion absorbs the dragon's breath!");
					hit = 0;
				} else {
					hit = Utils.random(400, 710);
					victim.getPackets().sendGameMessage("You are horribly burned by the dragon's breath!");
				}*/
				int hit = Utils.random(400, 710);
				int protection = usingAntiFireShield(victim) + usingProtectFromMagic(victim) + usingAntiFirePotion(victim);
				switch(protection) {
					case 0:
						victim.sm("You are horribly burned by the dragon's breath!");
						break;
					default:
						victim.sm(getProtectMessage(victim));
						hit /= (protection + 1);
						break;
				}
				victim.setNextAnimation(new Animation(Combat.getDefenceEmote(victim)));
				victim.applyHit(new Hit(npc, hit, HitLook.REGULAR_DAMAGE));
			}		
		}, 1);
		return Utils.random(4, 15); //Attack delay seems to be random a lot.
	}

	@Override
	public boolean canAttack(QueenBlackDragon npc, Player victim) {
		return true;
	}

	/**
	 * Gets the dragonfire protect message.
	 * @param player The player.
	 * @return The message to send, or {@code null} if the player was unprotected.
	 */
	public static String getProtectMessage(Player player) {
		if(player.getPerkHandler().perks.contains(Perk.DRAGONFIRE_PERK))
			return "Your Dragonfire perk absorbs  most of the dragon's breath!";
		if (Combat.hasAntiDragProtection(player)) {
			return "Your shield absorbs most of the dragon's breath!";
		}
		if (player.getSuperFireImmune() > Utils.currentTimeMillis())
			return "Your potion absorbs most of the dragon's breath!";
		if (player.getFireImmune() > Utils.currentTimeMillis()) {
			return "Your potion absorbs most of the dragon's breath!";
		}
		if (player.getPrayer().usingPrayer(0, 17)
				|| player.getPrayer().usingPrayer(1, 7)) {
			return "Your prayer absorbs most of the dragon's breath!";
		}
		return null;
	}
	
	public int usingProtectFromMagic(Player player) {
		if (player != null) {
			boolean prot = player.getPrayer().usingPrayer(0, 17) || player.getPrayer().usingPrayer(1, 7);
			if (prot) {
				player.sm("Your potion absorbs most of the dragon's breath!");
				return 1;
			}
		}
		return 0;
	}
	
	public int usingAntiFirePotion(Player player) {
		if (player != null) {
			if (player.getSuperFireImmune() > Utils.currentTimeMillis())
				return 2;
			boolean prot = player.getFireImmune() > Utils.currentTimeMillis();
			if (prot) {
				player.sm("Your potion absorbs most of the dragon's breath!");
				return 1;
			}
		}
		return 0;
	}
	
	public int usingAntiFireShield(Player player) {
		if (player != null) {
			boolean prot = Combat.hasAntiDragProtection(player);
			if (prot) {
				player.sm("Your shield absorbs most of the dragon's breath!");
				return 1;
			}
		}
		return 0;
	}
}