package com.rs.game.player.content;

import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

/**
 *   
 * @author paolo
 * boss pet unlocking stuff, improve if you can. I bet you can
 *
 */

public class BossPetHandler {
	/**
	 * enum of all the bosses and their pet Id
	 */
public enum BossPets {
		
		BANDOS(6260,20532),
		NEX(13450,20534),
		ARMADYL(6222,20530),
		ZAMORAK(6203,20531),
		KBD(50,20544),
		QBD(15509,20551),
		KILN(15211,20540),
		CHAOS(3200,20537),
		MOLE(3340,20539),
		CORP(8133,20538),
		STQ(3847,20587),
		KALPHITE_QUEEN(1160,20543),
		SARADOMIN(6247,20533),
		DARK_LORD(19553,20268),
		DKS_SUPREME(2881,20554),
		DKS_REX(2883,20553),
		DKS_PRIME(2882,20552),
		BLINK(12878,14967);
		
		
		int bossId, petId;
		
		/**
		 * used for looping thru the enum and get the boss hit pet Id or otherwise
		 * @param npc
		 * @return
		 */
		public static int findBossId(int npc) {
			for (BossPets boss : BossPets.values()) {
				if (boss.petId == npc) {
					return boss.bossId;
				}
			}
			return 1;
		}
		
		public static int findPetId(int npc) {
			for (BossPets boss : BossPets.values()) {
				if (boss.bossId == npc) {
					return boss.petId;
				}
			}
			return 1;
		}
		
		public static BossPets forId(int npc) {
			for (BossPets boss : BossPets.values()) {
				if (boss.bossId == npc) {
					return boss;
				}
			}
			return null;
		}
		
		private BossPets(int boss, int pet){
			this.bossId = boss;
			this.petId = pet;
		}
	}
	
	/**
	 * checks if it's a boss pet , not used atm
	 * @param npcId
	 * @return
	 */
	public static boolean isBossPet(int npcId){
		if(npcId >= 20530 && npcId <= 20580 ) //TODO customs bosses like darklord
			return true;
		return false;
	}
	
	/**
	 * unlocks the pet if not unlocked yet.
	 * @param player
	 * @param npc
	 * @return
	 */
	private static boolean unlockPet(Player player, NPC npc){
		BossPets pet =  BossPets.forId(npc.getId());
		if(player.collectedPets.contains(pet))
			return false;
		player.collectedPets.add(pet);
		player.sm("You have unlocked the "+npc.getName()+" pet. Check at the pet house to summon it.");
		return true;
		
	}
	/**
	 * checks if the player has the right chance to unlock
	 * @param player
	 * @param npc
	 * @return
	 */
	public static void check(Player player, NPC npc)
	{
		int mutli =  (player.getBossCount().get(npc.getId()));
		double random = Utils.random(1); //Even with less than 100 kills you have a chance
		if(mutli/100 + random >= Utils.random(250)){
			unlockPet(player,npc);
		}
	}
}