package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.fightkiln.HarAken;
import com.rs.game.npc.fightkiln.HarAkenTentacle;
import com.rs.game.npc.godwars.zaros.NexMinion;
import com.rs.game.npc.pest.PestPortal;
import com.rs.game.npc.qbd.QueenBlackDragon;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.content.perks.Perks;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.utils.Utils;

public final class Combat {
	
	/**
	 * checks if the player is protected
	 * @param target
	 * @return
	 */
	public static boolean hasAntiDragProtection(Entity target) {
		if (target instanceof NPC)
			return false;
		final Player p2 = (Player) target;
		final int shieldId = p2.getEquipment().getShieldId();
		if (shieldId == 11283 || shieldId == 11284) {
			if (p2.dfsCharges < 50) {
				p2.setNextAnimation(new Animation(6695));
				p2.setNextGraphics(new Graphics(1160+(p2.dfsCharges / 15)));
				p2.dfsCharges++;
				p2.getCombatDefinitions().refreshBonuses();
				p2.sm("Your dragonfire shield now has "+p2.dfsCharges+" charges.");
				if (shieldId == 11284) {
					p2.getEquipment().wieldOneItem(Equipment.SLOT_SHIELD, new Item(11283, 1));
				}
			}
		}
		return shieldId == 1540 || shieldId == 16079 || shieldId == 16933 || shieldId == 16934 || 
				shieldId == 11283 || shieldId == 11284 || shieldId == 33975;
	}
	
    private boolean forceCheckClipAsRange(Entity target) {
	return target instanceof PestPortal || target instanceof NexMinion || target instanceof HarAken || target instanceof HarAkenTentacle || target instanceof QueenBlackDragon;
    }

	public static int getSlayerLevelForNPC(int id) {
		switch (id) {
		case 9463:
			return 93;
case 14696:
			return 95;
case 14697:
			return 95;
case 14698:
			return 95;
case 14699:
			return 95;
case 13822:
			return 91;
case 2783:
			return 90;

case 14688:
			return 88;
case 14689:
			return 88;
case 13821:
			return 86;
case 1615:
			return 85;
case 6278:
			return 83;
case 6221:
			return 83;
case 6231:
			return 83;
case 6257:
			return 83;
case 14700:
			return 82;
case 14701:
			return 82;
case 13820:
			return 80;
case 1613:
			return 80;
case 9172:
			return 78;
case 9465:
			return 77;
case 1610:
			return 75;
case 9467:
			return 73;
case 3068:
			return 72;
case 3069:
			return 72;
case 3070:
			return 72;
case 3071:
			return 72;
case 1608:
			return 70;
case 1609:
			return 70;
case 6219:
			return 68;
case 6229:
			return 68;
case 6255:
			return 68;
case 6277:
			return 68;
case 1624:
			return 65;
case 10700:
			return 63;
case 6276:
			return 63;
case 6256:
			return 63;
case 6230:
			return 63;
case 6220:
			return 63;
case 1604:
			return 60;
case 1605:
			return 60;
case 1606:
			return 60;
case 1607:
			return 60;
case 4353:
			return 58;
case 4354:
			return 58;
case 4355:
			return 58;
case 4356:
			return 58;
case 4357:
			return 58;
case 3346:
			return 57;
case 3347:
			return 57;
case 6296:
			return 56;
case 6285:
			return 56;
case 6286:
			return 56;
case 6287:
			return 56;
case 6288:
			return 56;
case 6289:
			return 56;
case 6290:
			return 56;
case 6291:
			return 56;
case 6292:
			return 56;
case 6293:
			return 56;
case 6294:
			return 56;
case 6295:
			return 56;
case 1623:
			return 55;
case 1637:
			return 52;
case 1638:
			return 52;
case 1639:
			return 52;
case 1640:
			return 52;
case 1641:
			return 52;
case 1642:
			return 52;
case 7462:
			return 50;
case 7463:
			return 50;
case 1618:
			return 50;
case 1643:
			return 45;
case 1644:
			return 45;
case 1645:
			return 45;
case 1646:
			return 45;
case 1647:
			return 45;
case 1616:
			return 40;
case 1617:
			return 40;
case 114:
			return 32;
case 7823:
			return 35;
case 3201:
			return 37;
case 3202:
			return 37;
case 3153:
			return 33;
case 1633:
			return 30;
case 1634:
			return 30;
case 1635:
			return 30;
case 1636:
			return 30;
case 1620:
			return 25;
case 2804:
			return 22;
case 2805:
			return 22;
case 2806:
			return 22;
case 1631:
			return 20;
case 1632:
			return 20;
case 1831:
			return 17;
case 1612:
			return 15;
case 1600:
			return 10;
case 1601:
			return 10;
case 1602:
			return 10;
case 1603:
			return 10;
case 1832:
			return 7;
case 1648:
			return 5;
case 1649:
			return 5;
case 1650:
			return 5;
case 1651:
			return 5;
case 1652:
			return 5;
case 1653:
			return 5;
case 1654:
			return 5;
case 1655:
			return 5;
case 1656:
			return 5;
case 1657:
			return 5;

		default:
			return 0;
		}
	}


/**
 * Gets the defence combat animation to show.
 * @param target The target animating.
 * @return the animation id as Integer.
 */
public static int getDefenceEmote(Entity target) {
	if (target instanceof NPC) {
		NPC n = (NPC) target;
		return n.getCombatDefinitions().getDefenceEmote();
	} else { 
		Player p = (Player) target;
		if (p.getEquipment().getShieldId() != -1)
			return 18346;
		Item weapon = p.getEquipment().getItem(Equipment.SLOT_WEAPON);
		if (weapon == null)
			return 18346;
		int emote = weapon.getDefinitions().getCombatOpcode(2917);
		return emote == 0 ? 18346 : emote;
	}
}

	private Combat() {
	}

	public static boolean hasAntiDragProtection(Entity target, boolean increment) {
		if (target == null || !(target instanceof Player))
			return false;
		final Player player = (Player) target;
		final int shieldId = player.getEquipment().getShieldId();
		if (shieldId != -1) {
			final ItemDefinitions defs = ItemDefinitions
					.getItemDefinitions(shieldId);
			if (defs != null) {
				if (defs.getName().toLowerCase().contains("dragonfire shield")) {
					if (increment) {
						player.setNextAnimation(new Animation(6695));
						player.setNextGraphics(new Graphics(1164));
						player.DFS++;
					}
					return true;
				}
			}
		}
		return false;
	}

	public static boolean hasDragonFire(Entity target) {
		if (target == null || !(target instanceof Player))
			return false;
		final Player player = (Player) target;
		if (player.DFS <= 0)
			return false;
		return hasAntiDragProtection(player, false);
		/*
		 * if (hasAntiDragProtection(player, false)) {
		 * player.setNextAnimation(new Animation(6696)); player.DFS--; return
		 * true; } return false;
		 */
	}
	
	 public static boolean hasDFS(Player player) {
			int shieldId = player.getEquipment().getShieldId();
			return shieldId == 1540 || shieldId == 11283 || shieldId == 11284 || shieldId == 25558
					 || shieldId == 25559 || shieldId == 25561 || shieldId == 25562 || shieldId == 16933 
					 || player.getPerkHandler().perks.contains(Perk.DRAGONFIRE_PERK);
			
		}

	public static final String getProtectMessage(Player player) {
		boolean hasFireImmune = player.getFireImmune() > Utils.currentTimeMillis() && player.getFireImmune() != 0;
		boolean hasFirePrayerProtection = player.getPrayer().usingPrayer(0, 17) || player.getPrayer().usingPrayer(1, 17);
		if (player.getPerkHandler().perks.contains(Perk.DRAGONFIRE_PERK))
			return "Your dragon trainer perk fully absorbs the heat of the dragon's breath!";
		if (hasFireImmune && hasFirePrayerProtection)
			return "Your prayer and potion fully absorbs the heat of the dragon's breath!";
		if (hasDFS(player) && hasFirePrayerProtection)
			return "Your prayer and shield fully absorbs the heat of the dragon's breath!";
		if (hasFireImmune && hasDFS(player))
			return "Your potion and shield fully absorbs the heat of the dragon's breath!";
		if (hasDFS(player))
			return "Your shield absorbs some of the dragon's breath!";
		if (hasFireImmune)
			return "Your potion absorbs some of the dragon's breath!";
		if (hasFirePrayerProtection)
			return "Your prayer absorbs some of the dragon's breath!";
		return null;
	}

	public static boolean hasAvas(Player player) {
		int capeId = player.getEquipment().getCapeId();
		return capeId == 10498 || capeId == 10499 || capeId == 20068 || 
				capeId == 20769 || capeId == 20771 || capeId == 31610 ||
				capeId == 32152 || capeId == 32153 || player.getPerkHandler().perks.contains(Perk.RANGED_PERK);
	}
}
