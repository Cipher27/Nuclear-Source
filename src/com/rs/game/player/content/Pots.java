package com.rs.game.player.content;

import java.util.concurrent.TimeUnit;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.item.Item;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.controlers.CrucibleControler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public final class Pots {
	
	public static int VIAL = 229, FLASK = 23191;

	public static enum Pot {

		ATTACK_POTION(new int[] { 2428, 121, 123, 125 }, Effects.ATTACK_POTION),

		STRENGTH_POTION(new int[] { 113, 115, 117, 119 }, Effects.STRENGTH_POTION),

		DEFENCE_POTION(new int[] { 2432, 133, 135, 137 }, Effects.DEFENCE_POTION),

		RANGE_POTION(new int[] { 2444, 169, 171, 173 }, Effects.RANGE_POTION),

		MAGIC_POTION(new int[] { 3040, 3042, 3044, 3046 }, Effects.MAGIC_POTION),

		MAGIC_FLASK(new int[] { 23423, 23425, 23427, 23429, 23431, 23433 },
				Effects.MAGIC_POTION),
		ANTI_POISION(new int[] { 2446, 175, 177, 179 }, Effects.ANTIPOISON),

		PRAYER_POTION(new int[] { 2434, 139, 141, 143 }, Effects.PRAYER_POTION),
		
		AGILITY_POTION(new int[] {3032,3034,3036,3038}, Effects.AGILITY_POTION),
		
		CRAFTING_POTION(new int[] {14838,14840,14842,14844}, Effects.CRAFTING_POTION),
		
		FLETCHING_POTION(new int[] {14846,14848,14850,14852}, Effects.FLETCHING_POTION),
		
		HUNTER_POTION(new int[] {9998,10000,10002,10004}, Effects.HUNTER_POTION),
		
		COMBAT_POTION(new int[] {9739,9741,9743,9745}, Effects.COMBAT_POTION),

		SUPER_ATT_POTION(new int[] { 2436, 145, 147, 149 }, Effects.SUPER_ATT_POTION),

		SUPER_STR_POTION(new int[] { 2440, 157, 159, 161 }, Effects.SUPER_STR_POTION),

		SUPER_DEF_POTION(new int[] { 2442, 163, 165, 167 }, Effects.SUPER_DEF_POTION),

		ENERGY_POTION(new int[] { 3008, 3010, 3012, 3014}, Effects.ENERGY_POTION),

		SUPER_ENERGY(new int[] { 3016, 3018, 3020, 3022}, Effects.SUPER_ENERGY),

		EXTREME_ATT_POTION(new int[] { 15308, 15309, 15310, 15311 }, Effects.EXTREME_ATT_POTION),

		EXTREME_STR_POTION(new int[] { 15312, 15313, 15314, 15315 }, Effects.EXTREME_STR_POTION),

		EXTREME_DEF_POTION(new int[] { 15316, 15317, 15318, 15319 }, Effects.EXTREME_DEF_POTION),

		EXTREME_MAGE_POTION(new int[] { 15320, 15321, 15322, 15323 }, Effects.EXTREME_MAG_POTION),

		EXTREME_RANGE_POTION(new int[] { 15324, 15325, 15326, 15327 }, Effects.EXTREME_RAN_POTION),

		SUPER_RESTORE_POTION(new int[] { 3024, 3026, 3028, 3030 }, Effects.SUPER_RESTORE),

		SARADOMIN_BREW(new int[] { 6685, 6687, 6689, 6691 }, Effects.SARADOMIN_BREW),
		
		SUPER_SARADOMIN_BREW(new int[] { 28191, 28193, 28195, 28197 }, Effects.SUPER_SUPERSARADOMIN_BREW),

		RECOVER_SPECIAL(new int[] { 15300, 15301, 15302, 15303 }, Effects.RECOVER_SPECIAL),

		SUPER_PRAYER(new int[] { 15328, 15329, 15330, 15331 }, Effects.SUPER_PRAYER),

		OVERLOAD(new int[] { 15332, 15333, 15334, 15335 }, Effects.OVERLOAD),

		ANTI_FIRE(new int[] { 2452, 2454, 2456, 2458 }, Effects.ANTI_FIRE),
		
		SUPER_ANTI_FIRE(new int[] { 15304, 15305, 15306, 15307 }, Effects.SUPER_ANTI_FIRE),

		SUMMONING_POTION(new int[] { 12140, 12142, 12144, 12146 }, Effects.SUMMONING_POT),
		
		SUMMONING_FLASK(new int[] { 23621, 23623, 23625, 23627, 23629, 23631 }, Effects.SUMMONING_POT),

		SANFEW_SERUM(new int[] { 10925, 10927, 10929, 10931 }, Effects.SANFEW_SERUM),

		PRAYER_RENEWAL(new int[] {21630, 21632, 21634, 21636}, Effects.PRAYER_RENEWAL),

		PRAYER_RENEWAL_FLASK(new int[] {23609, 23611, 23613, 23615, 23617, 23619}, Effects.PRAYER_RENEWAL),


		/*JUJU_MINING_FLASK(new int[] { 23131, 23132, 23133, 23134, 23135, 23136 },
				null),

		JUJU_COOKING_FLASK(new int[] { 23137, 23138, 23139, 23140, 23141, 23142 },
				null),

		JUJU_FARMING_FLASK(new int[] { 23143, 23144, 23145, 23146, 23147, 23148 },
				null),

		JUJU_WOODCUTTING_FLASK(new int[] { 23149, 23150, 23151, 23152, 23153, 23154 },
				null),

		JUJU_FISHING_FLASK(new int[] { 23155, 23156, 23157, 23158, 23159, 23160 },
				null),

		JUJU_HUNTER_FLASK(new int[] { 23161, 23162, 23163, 23164, 23165, 23166 },
				null),

		SCENTLESS_FLASK(new int[] { 23167, 23168, 23169, 23170, 23171, 23172 },
				null),

		SARADOMINS_BLESSING_FLASK(new int[] { 23173, 23174, 23175, 23176, 23177, 23178 },
				null),

		GUTHIXS_GIFT_FLASK(new int[] { 23179, 23180, 23181, 23182, 23183, 23184 },
				null),

		ZAMORAKS_FAVOUR_FLASK(new int[] { 23185, 23186, 23187, 23188, 23189, 23190 },
				null),*/

		ATTACK_FLASK(new int[] { 23195, 23197, 23199, 23201, 23203, 23205 }, Effects.ATTACK_POTION),

		STRENGTH_FLASK(new int[] { 23207, 23209, 23211, 23213, 23215, 23217 }, Effects.STRENGTH_POTION),

		RESTORE_FLASK(new int[] { 23219, 23221, 23223, 23225, 23227, 23229 }, Effects.RESTORE_POTION),

		DEFENCE_FLASK(new int[] { 23231, 23233, 23235, 23237, 23239, 23241 }, Effects.DEFENCE_POTION),

		PRAYER_FLASK(new int[] { 23243, 23245, 23247, 23249, 23251, 23253 }, Effects.PRAYER_POTION),

		SUPER_ATTACK_FLASK(new int[] { 23255, 23257, 23259, 23261, 23263, 23265 }, Effects.SUPER_ATT_POTION),

		FISHING_FLASK(new int[] { 23267, 23269, 23271, 23273, 23275, 23277 }, Effects.FISHING_POTION),

		SUPER_STRENGTH_FLASK(new int[] { 23279, 23281, 23283, 23285, 23287, 23289 }, Effects.SUPER_STR_POTION),

		SUPER_DEFENCE_FLASK(new int[] { 23291, 23293, 23295, 23297, 23299, 23301 }, Effects.SUPER_DEF_POTION),

		RANGING_FLASK(new int[] { 23303, 23305, 23307, 23309, 23311, 23313 }, Effects.RANGE_POTION),

		ANTIPOISON_FLASK(new int[] { 23315, 23317, 23319, 23321, 23323, 23325 }, Effects.ANTIPOISON),

		SUPER_ANTIPOISON_FLASK(new int[] { 23327, 23329, 23331, 23333, 23335, 23337 }, Effects.SUPER_ANTIPOISON),

		/*ZAMORAK_BREW_FLASK(new int[] { 23339, 23341, 23343, 23345, 23347, 23349 },
				null),*/

		SARADOMIN_BREW_FLASK(new int[] { 23351, 23353, 23355, 23357, 23359, 23361 }, Effects.SARADOMIN_BREW),

		ANTIFIRE_FLASK(new int[] { 23363, 23365, 23367, 23369, 23371, 23373 }, Effects.ANTI_FIRE),

		ENERGY_FLASK(new int[] { 23375, 23377, 23379, 23381, 23383, 23385 }, Effects.ENERGY_POTION),

		SUPER_ENERGY_FLASK(new int[] { 23387, 23389, 23391, 23393, 23395, 23397 }, Effects.SUPER_ENERGY),

		SUPER_RESTORE_FLASK(new int[] { 23399, 23401, 23403, 23405, 23407, 23409 }, Effects.SUPER_RESTORE),

		AGILITY_FLASK(new int[] { 23411, 23413, 23415, 23417, 23419, 23421 },
				Effects.AGILITY_POTION),

		HUNTER_FLASK(new int[] { 23435, 23437, 23439, 23441, 23443, 23445 },
				Effects.HUNTER_POTION),

		COMBAT_FLASK(new int[] { 23447, 23449, 23451, 23453, 23455, 23457 },
				Effects.COMBAT_POTION),

		CRAFTING_FLASK(new int[] { 23459, 23461, 23463, 23465, 23467, 23469 },
				Effects.CRAFTING_POTION),

		FLETCHING_FLASK(new int[] { 23471, 23473, 23475, 23477, 23479, 23481 },
				Effects.FLETCHING_POTION),

		RECOVER_SPECIAL_FLASK(new int[] { 23483, 23484, 23485, 23486, 23487, 23488 }, Effects.RECOVER_SPECIAL),

			SUPER_ANTIFIRE_FLASK(new int[] { 23489, 23490, 23491, 23492, 23493, 23494 }, Effects.SUPER_ANTI_FIRE),

		EXTREME_ATTACK_FLASK(new int[] { 23495, 23496, 23497, 23498, 23499, 23500 }, Effects.EXTREME_ATT_POTION),

		EXTREME_STRENGTH_FLASK(new int[] { 23501, 23502, 23503, 23504, 23505, 23506 }, Effects.EXTREME_STR_POTION),

		EXTREME_DEFENCE_FLASK(new int[] { 23507, 23508, 23509, 23510, 23511, 23512 }, Effects.EXTREME_DEF_POTION),

		EXTREME_MAGIC_FLASK(new int[] { 23513, 23514, 23515, 23516, 23517, 23518 }, Effects.EXTREME_MAG_POTION),

		EXTREME_RANGING_FLASK(new int[] { 23519, 23520, 23521, 23522, 23523, 23524 }, Effects.EXTREME_RAN_POTION),

		SUPER_PRAYER_FLASK(new int[] { 23525, 23526, 23527, 23528, 23529, 23530 }, Effects.SUPER_PRAYER),

		OVERLOAD_FLASK(new int[] { 23531, 23532, 23533, 23534, 23535, 23536 }, Effects.OVERLOAD),
		
BEER(new int[] { 1917, 1919 }, Effects.BEER),
		
		JUG(new int[] { 1993, 1935 }, Effects.WINE),
		/**
		 * Start barbarian mix potion
		 **/
		  //start mixPotion
		ATTACK_MIX(new int[] { 11429, 11431 }, Effects.ATTACK_MIX),

		ANTIPOISON_MIX(new int[] { 11433, 11435 }, Effects.ANTIPOISON_MIX),

		/* RELICYMS_MIX(new int[] { 11437, 11439 }, Effects.RELICYMS_MIX), */

		STRENGTH_MIX(new int[] { 11443, 11441 }, Effects.STRENGTH_MIX),

		COMBAT_MIX(new int[] { 11445, 11447 }, Effects.COMBAT_MIX),

		RESTORE_MIX(new int[] { 11449, 11451 }, Effects.RESTORE_MIX),

		DEFENCE_MIX(new int[] { 11457, 11459 }, Effects.DEFENCE_MIX),

		AGILITY_MIX(new int[] { 11461, 11463 }, Effects.AGILITY_MIX),

		PRAYER_MIX(new int[] { 11465, 11467 }, Effects.PRAYER_MIX),

		SUPER_ATTACK_MIX(new int[] { 11469, 11471 }, Effects.SUPER_ATTACK_MIX),

		SUPER_ANTIPOISON_MIX(new int[] { 11473, 11475 }, Effects.SUPER_ANTIPOISON_MIX),

		FISHING_MIX(new int[] { 11477, 11479 }, Effects.FISHING_MIX),

		SUPER_STRENGTH_MIX(new int[] { 11485, 11487 }, Effects.SUPER_STRENGTH_MIX),

		MAGIC_ESSENCE_MIX(new int[] { 11489, 11491 }, Effects.MAGIC_MIX),

		SUPER_RESTORE_MIX(new int[] { 11493, 11495 }, Effects.SUPER_RESTORE_MIX),

		SUPER_DEFENCE_MIX(new int[] { 11497, 11499 }, Effects.SUPER_DEFENCE_MIX),

		SUPER_ANTIPOISON_PLUS_MIX(new int[] { 11501, 11503 }, Effects.SUPER_ANTIPOISON_MIX),

		ANTIFIRE_MIX(new int[] { 11505, 11507 }, Effects.ANTIFIRE_MIX),

		RANGING_MIX(new int[] { 11509, 11511 }, Effects.RANGING_MIX),

		MAGIC_MIX(new int[] { 11513, 11515 }, Effects.MAGIC_MIX),

		HUNTING_MIX(new int[] { 11517, 11519 }, Effects.HUNTING_MIX),

		ZAMORAK_MIX(new int[] { 11521, 11523 }, Effects.ZAMORAK_MIX),
		
		//end 

		/*	RELICYMS_BALM_FLASK(new int[] { 23537, 23539, 23541, 23543, 23545, 23547 },
				null),

		SERUM_207_FLASK(new int[] { 23549, 23550, 23551, 23552, 23553, 23554 },
				null),

		GUTHIX_BALANCE_FLASK(new int[] { 23555, 23557, 23559, 23561, 23563, 23565 },
				null),*/

		SANFEW_SERUM_FLASK(new int[] { 23567, 23569, 23571, 23573, 23575, 23577 }, Effects.SANFEW_SERUM),

		/*ANTIPOISON_PLUS_FLASK(new int[] { 23579, 23581, 23583, 23585, 23587, 23589 },
				null),

		ANTIPOISON_PLUS_PLUS_FLASK(new int[] { 23591, 23593, 23595, 23597, 23599, 23601 },
				null),

		SERUM_208_FLASK(new int[] { 23603, 23604, 23605, 23606, 23607, 23608 },
				null)
*/
    
        
      //overload en renwal
        HOLY_OVERLOAD(new int[] { 33246,  33244,  33242,  33240,  33238,  33236}, Effects.HOLY_OVERLOAD),
		//prayer renewall en antifire
		BRIGHTFIRE(new int[] { 33174,33172,33170,33168,33166,33164}, Effects.BRIGHTFIRE),
		//overload ant antifire
		SEARING_OVERLOAD(new int[] {33258,  33256, 33254, 33252, 33250, 33248},Effects.SEARING_OVERLOAD),
		//better overload
		SUPREME_OVERLOAD(new int[] {33210,33208,33206,33204,33202,33200},Effects.OVERLOAD2),
		//every potion combined
		SALVE_OVERLOAD(new int[] { 33198,33196,33194,33192,33190,33188},Effects.SALVE_OVERLOAD),
		
		LUCKY_POTION(new int[] { 1548, -1},Effects.LUCKY_POTION);
        
   
		/*SUPPER_OVL(new int[] { 23633, 23634, 23635, 23636, 23637, 23638 },
				 Effects.OVERLOAD2);*/

		public int[] id;
		public Effects effect;

		private Pot(int[] id, Effects effect) {
			this.id = id;
			this.effect = effect;
		}
		
		public boolean isFlask() {
			return getMaxDoses() == 6;
		}

		public boolean isPotion() {
			return getMaxDoses() == 4;
		}

		public int getMaxDoses() {
			return id.length;
		}

		public int getIdForDoses(int doses) {
			return id[getMaxDoses() - doses];
		}	
	}
	
	public static String getOverloadTimeleft(Player player) {
		int minutes = player.getOverloadDelay() / 60;
		int seconds = (player.getOverloadDelay() / 1000 * 60);
		String secondsMessage = (seconds != 1 ? seconds + " seconds" : "second");
		String minutesMessage = (minutes != 1 ? minutes + " minutes" : "minute");
		return (minutes > 0 ? minutesMessage + " and " : "") + secondsMessage;
	}

	public static String getPoisonImmunityTimeLeft(Player player) {
		long minutes = TimeUnit.MILLISECONDS.toMinutes(player.getPoisonImmune() - Utils.currentTimeMillis());
		long seconds = TimeUnit.MILLISECONDS.toSeconds(player.getPoisonImmune() - Utils.currentTimeMillis());
		String secondsMessage = (seconds != 1 ? seconds + " seconds" : "second");
		String minutesMessage = (minutes != 1 ? minutes + " minutes" : "minute");
		return (minutes > 0 ? minutesMessage + " and " : "") + secondsMessage;
	}

	public enum Effects {
		ATTACK_POTION(Skills.ATTACK) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		AGILITY_POTION(Skills.AGILITY) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		CRAFTING_POTION(Skills.CRAFTING) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		COMBAT_POTION(Skills.ATTACK, Skills.STRENGTH) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		FLETCHING_POTION(Skills.FLETCHING) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		HUNTER_POTION(Skills.HUNTER) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		//mixPotion
		ATTACK_MIX(Skills.ATTACK) {
			@Override
			public void extra(Player player) {
				player.heal(Utils.random(30, 60));
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},

		ANTIPOISON_MIX() {
			@Override
			public void extra(Player player) {
				player.heal(200);
				player.addPoisonImmune(180000);
				player.getPackets().sendGameMessage("You are now immune to poison.");
			}
		},

		STRENGTH_MIX(Skills.STRENGTH) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},

		COMBAT_MIX(Skills.ATTACK, Skills.STRENGTH) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},

		RESTORE_MIX(Skills.ATTACK, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE, Skills.PRAYER) {
			@Override
			public void extra(Player player) {
				player.heal(200);
				player.getPrayer().restorePrayer((int) ((int) (player.getSkills().getLevelForXp(Skills.PRAYER) * 0.33 * 10) * player.getAuraManager().getPrayerPotsRestoreMultiplier()));
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}
		},


		DEFENCE_MIX(Skills.DEFENCE) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},

		AGILITY_MIX(Skills.AGILITY) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (level + 3);
			}
		},

		FISHING_MIX(Skills.FISHING) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (level + 3);
			}
		},

		HUNTING_MIX(Skills.HUNTER) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (level + 3);
			}
		},

		PRAYER_MIX() {
			@Override
			public void extra(Player player) {
				player.heal(200);
				player.getPrayer().restorePrayer((int) (Utils.random(70, 310) * player.getAuraManager().getPrayerPotsRestoreMultiplier()));
			}
		},

		SUPER_ATTACK_MIX(Skills.ATTACK) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},

		SUPER_DEFENCE_MIX(Skills.DEFENCE) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},

		SUPER_STRENGTH_MIX(Skills.STRENGTH) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},

		SUPER_RESTORE_MIX(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.MAGIC, Skills.RANGE, Skills.AGILITY, Skills.COOKING, Skills.CRAFTING, Skills.FARMING, Skills.FIREMAKING, Skills.FISHING, Skills.FLETCHING, Skills.HERBLORE, Skills.MINING, Skills.RUNECRAFTING, Skills.SLAYER, Skills.SMITHING, Skills.THIEVING, Skills.WOODCUTTING, Skills.SUMMONING) {
			@Override
			public void extra(Player player) {
				player.heal(200);
				player.getPrayer().restorePrayer((int) ((int) (player.getSkills().getLevelForXp(Skills.PRAYER) * 0.33 * 10) * player.getAuraManager().getPrayerPotsRestoreMultiplier()));
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}

		},

		ANTIFIRE_MIX() {
			@Override
			public void extra(final Player player) {
				player.heal(200);
				player.addFireImmune(360000);
				final long current = player.getFireImmune();
				player.getPackets().sendGameMessage("You are now immune to dragonfire.");
				WorldTasksManager.schedule(new WorldTask() {
					boolean stop = false;

					@Override
					public void run() {
						if (current != player.getFireImmune()) {
							stop();
							return;
						}
						if (!stop) {
							player.getPackets().sendGameMessage("<col=480000>Your antifire potion is about to run out...</col>");
							stop = true;
						} else {
							stop();
							player.getPackets().sendGameMessage("<col=480000>Your antifire potion has ran out...</col>");
						}
					}
				}, 500, 100);
			}
		},

		MAGIC_MIX(Skills.MAGIC) {
			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return level + 5;
			}
		},

		SUPER_ANTIPOISON_MIX() {
			@Override
			public void extra(Player player) {
				player.heal(200);
				player.addPoisonImmune(360000);
				player.getPackets().sendGameMessage("You are now immune to poison.");
			}
		},

		RANGING_MIX(Skills.RANGE) {

			@Override
			public void extra(Player player) {
				player.heal(200);
			}

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				final int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.1));
			}
		},


		ZAMORAK_MIX(Skills.ATTACK) {
			int toAdd;

			@Override
			public void extra(Player player) {
				player.heal(200);
				toAdd = (player.getSkills().getLevelForXp(Skills.ATTACK));
				player.getSkills().set(Skills.ATTACK, toAdd);
			}

		},
		//start normal pots
		FISHING_POTION(Skills.FISHING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (level + 3);
			}
		},
		ZAMORAK_BREW(Skills.ATTACK) {
			int toAdd;
			@Override
			public void extra(Player player) {
				toAdd = (player.getSkills().getLevelForXp(Skills.ATTACK));
				player.getSkills().set(Skills.ATTACK, toAdd);
			}

		},
		SANFEW_SERUM(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE,
				Skills.MAGIC, Skills.RANGE, Skills.AGILITY, Skills.COOKING,
				Skills.CRAFTING, Skills.FARMING, Skills.FIREMAKING,
				Skills.FISHING, Skills.FLETCHING, Skills.HERBLORE,
				Skills.MINING, Skills.RUNECRAFTING, Skills.SLAYER,
				Skills.SMITHING, Skills.THIEVING, Skills.WOODCUTTING,
				Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}

			@Override
			public void extra(Player player) {
				player.getPrayer().restorePrayer(
						(int) ((int) (player.getSkills().getLevelForXp(
								Skills.PRAYER) * 0.33 * 10) * player
								.getAuraManager()
								.getPrayerPotsRestoreMultiplier()));
				player.addPoisonImmune(180000);
				//TODO DISEASE HEALING
			}

		},
		SUMMONING_POT(Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int restore = (int) (Math.floor(player.getSkills().getLevelForXp(Skills.SUMMONING) * 0.25) + 7);
				if(actualLevel + restore > realLevel)
					return realLevel;
				return actualLevel + restore;
			}
			@Override
			public void extra(Player player) {
				Familiar familiar = player.getFamiliar();
				if(familiar != null)
					familiar.restoreSpecialAttack(15);
			}
		},
		ANTIPOISON() {
			@Override
			public void extra(Player player) {
				player.addPoisonImmune(180000);
				player.antiPoisonTimer.start(300);
				player.getPackets().sendGameMessage(
						"You are now immune to poison.");
			}
		},
		SUPER_ANTIPOISON() {
			@Override
			public void extra(Player player) {
				player.addPoisonImmune(360000);
				player.antiPoisonTimer.start(1000);
				player.getPackets().sendGameMessage(
						"You are now immune to poison.");
			}
		},
		LUCKY_POTION() {
			@Override
			public void extra(Player player) {
				player.luckPotionTimer.start(3600); //1hour
				player.getPackets().sendGameMessage(
						"You now have the effect of a ring of wealth.");
			}
		},
		BEER(Skills.ATTACK, Skills.STRENGTH) {

			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				if (skillId == Skills.STRENGTH) {
					int boost = (int) (realLevel * 0.07);
					if (actualLevel > realLevel)
						return actualLevel;
					if (actualLevel + boost > realLevel)
						return realLevel;
					return actualLevel + boost;
				} else {
					return (int) (actualLevel * 0.90);
				}
			}

			@Override
			public void extra(Player player) {
				player.sendMessage("You drink the beer. You feel slightly reinvigorated...");
				player.sendMessage("...and slightly dizzy too.");
			}
		},
		WINE(Skills.ATTACK) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				return (int) (actualLevel * 0.90);
			}

			@Override
			public void extra(Player player) {
				player.sendMessage("You drink the wine. You feel slightly reinvigorated...");
				player.sendMessage("...and slightly dizzy too.");
				player.heal(70);
			}
		},
		ENERGY_POTION() {
			@Override
			public void extra(Player player) {
				int restoredEnergy = player.getRunEnergy() + 20;
				player.setRunEnergy(restoredEnergy > 100 ? 100 : restoredEnergy);
			}
		},
		SUPER_ENERGY() {
			@Override
			public void extra(Player player) {
				int restoredEnergy = player.getRunEnergy() + 40;
				player.setRunEnergy(restoredEnergy > 100 ? 100 : restoredEnergy);
			}
		},
        SUPER_ANTI_FIRE() {
            @Override
            public void extra(final Player player) {
                    player.addSuperFireImmune(360000);
                    player.antiFire.start(300);
                    final long current = player.getSuperFireImmune();
                    player.getPackets().sendGameMessage(
                                    "You are now completely immune to dragonfire.");
                    WorldTasksManager.schedule(new WorldTask() {
                            boolean stop = false;

                            @Override
                            public void run() {
                                    if (current != player.getSuperFireImmune()) {
                                            stop();
                                            return;
                                    }
                                    if (!stop) {
                                            player.getPackets()
                                                            .sendGameMessage(
                                                                            "<col=480000>Your super antifire potion is about to run out...</col>");
                                            stop = true;
                                    } else {
                                            stop();
											player.getPackets().sendHideIComponent(3004, 3, true);
											player.getPackets().sendIComponentText(3016, 8, "");
                                            player.getPackets()
                                                            .sendGameMessage(
                                                                            "<col=480000>Your super antifire potion has ran out...</col>");
                                    }
                            }
                    }, 500, 100);
            }
    },
		ANTI_FIRE() {
			@Override
			public void extra(final Player player) {
				player.addFireImmune(360000);
				final long current = player.getFireImmune();
				player.getPackets().sendGameMessage( "You are now immune to dragonfire.");
				WorldTasksManager.schedule(new WorldTask() {
					boolean stop = false;
					@Override
					public void run() {
						if (current != player.getFireImmune()) {
							stop();
							return;
						}
						if (!stop) {
							player.getPackets().sendGameMessage("<col=480000>Your antifire potion is about to run out...</col>");
							stop = true;
						} else {
							stop();
							player.getPackets().sendGameMessage("<col=480000>Your antifire potion has ran out...</col>");
						}		
					}
				}, 500, 100);
			}
		},
		STRENGTH_POTION(Skills.STRENGTH) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		DEFENCE_POTION(Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		RANGE_POTION(Skills.RANGE) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.1));
			}
		},
		MAGIC_POTION(Skills.MAGIC) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return level + 5;
			}
		},
		PRAYER_POTION() {
			@Override
			public void extra(Player player) {
				player.getPrayer()
				.restorePrayer(
						(int) ((int) (Math.floor(player.getSkills()
								.getLevelForXp(Skills.PRAYER) * 2.5) + 70) * player
								.getAuraManager()
								.getPrayerPotsRestoreMultiplier()));
			}
		},
		SUPER_STR_POTION(Skills.STRENGTH) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},
		SUPER_DEF_POTION(Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},
		SUPER_ATT_POTION(Skills.ATTACK) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},
		EXTREME_STR_POTION(Skills.STRENGTH) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler
						|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.22));
			}
		},
		EXTREME_DEF_POTION(Skills.DEFENCE) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler || FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.22));
			}
		},
		EXTREME_ATT_POTION(Skills.ATTACK) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler || FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.22));
			}
		},
		EXTREME_RAN_POTION(Skills.RANGE) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler || FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 4 + (Math.floor(realLevel / 5.2)));
			}
		},
		EXTREME_MAG_POTION(Skills.MAGIC) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return level + 7;
			}
		},
		RECOVER_SPECIAL() {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				Long time = (Long) player.getTemporaryAttributtes().get(
						"Recover_Special_Pot");
				if (time != null && Utils.currentTimeMillis() - time < 30000) {
					player.getPackets().sendGameMessage(
							"You may only use this pot every 30 seconds.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(Player player) {
				player.getTemporaryAttributtes().put("Recover_Special_Pot",
						Utils.currentTimeMillis());
				player.getCombatDefinitions().restoreSpecialAttack(25);
			}
		},
		SARADOMIN_BREW("You drink some of the foul liquid.", Skills.ATTACK,
				Skills.DEFENCE, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE) {

			@Override
			public boolean canDrink(Player player) {
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				if (skillId == Skills.DEFENCE) {
					int boost = (int) (realLevel * 0.25);
					int level = actualLevel > realLevel ? realLevel
							: actualLevel;
					return level + boost;
				} else {
					return (int) (actualLevel * 0.90);
				}
			}

			@Override
			public void extra(Player player) {
				int hitpointsModification = (int) (player.getMaxHitpoints() * 0.15);
				player.heal(hitpointsModification + 20, hitpointsModification);
			}
		},
		
		SUPER_SUPERSARADOMIN_BREW("You drink some of the foul liquid.", Skills.ATTACK,
				Skills.DEFENCE, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE) {

			@Override
			public boolean canDrink(Player player) {
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				if (skillId == Skills.DEFENCE) {
					int boost = (int) (realLevel * 0.40);
					int level = actualLevel > realLevel ? realLevel
							: actualLevel;
					return level + boost;
				} else {
					return (int) (actualLevel * 0.90);
				}
			}

			@Override
			public void extra(Player player) {
				int hitpointsModification = (int) (player.getMaxHitpoints() * 0.30);
				player.heal(hitpointsModification + 20, hitpointsModification);
			}
		},
		
		
		BRIGHTFIRE() {
		
		@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler || FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(Player player) {
				player.setPrayerRenewalDelay(501);
			    player.renewalTimer.start(300);
			    player.antiFire.start(300);
				player.addSuperFireImmune(360000);
				player.sm("You are now immune to dragonfire.");
			}
		},
		HOLY_OVERLOAD() {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
						if (player.getOverloadDelay() > 0) {
					player.getPackets().sendGameMessage(
							"You may only use this potion every five minutes.");
					return false;
				}
				if (player.getHitpoints() <= 500 || player.getOverloadDelay() > 480) {
					player.getPackets()
					.sendGameMessage(
							"You need more than 500 life points to survive the power of  holy overload.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(final Player player) {
				player.setOverloadDelay(501);
				player.overLoadTimer.start(300);
				player.renewalTimer.start(300);
				player.setPrayerRenewalDelay(501);
				WorldTasksManager.schedule(new WorldTask() {
					int count = 4;

					@Override
					public void run() {
						if (count == 0)
							stop();
						player.setNextAnimation(new Animation(3170));
						player.setNextGraphics(new Graphics(560));
						player.applyHit(new Hit(player, 100,
								HitLook.REGULAR_DAMAGE, 0));
						count--;
					}
				}, 0, 2);
			}
		},
		SALVE_OVERLOAD() {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
						if (player.getOverloadDelay() > 0) {
					player.getPackets().sendGameMessage(
							"You may only use this potion every five minutes.");
					return false;
				}
				if (player.getHitpoints() <= 500 || player.getOverloadDelay() > 480) {
					player.getPackets()
					.sendGameMessage(
							"You need more than 500 life points to survive the power of  salve overload.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(final Player player) {
				player.setOverloadDelay(501);
				player.setPrayerRenewalDelay(501);
				player.addSuperFireImmune(360000);
				player.addPoisonImmune(360000);
				player.renewalTimer.start(300);
				player.antiFire.start(300);
				player.overLoadTimer.start(300);
				player.antiPoisonTimer.start(300);
				player.sm("You are now imunne to dragon breath and poison.");
				player.getPrayer().restorePrayer((int) ((int) (Math.floor(player.getSkills().getLevelForXp(Skills.PRAYER) * 2.5) + 70) * player.getAuraManager().getPrayerPotsRestoreMultiplier()));
				WorldTasksManager.schedule(new WorldTask() {
					int count = 4;

					@Override
					public void run() {
						if (count == 0)
							stop();
						player.setNextAnimation(new Animation(3170));
						player.setNextGraphics(new Graphics(560));
						player.applyHit(new Hit(player, 100,
								HitLook.REGULAR_DAMAGE, 0));
						count--;
					}
				}, 0, 2);
			}
		},
		SEARING_OVERLOAD() {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
						if (player.getOverloadDelay() > 0) {
					player.getPackets().sendGameMessage(
							"You may only use this potion every five minutes.");
					return false;
				}
				if (player.getHitpoints() <= 500 || player.getOverloadDelay() > 480) {
					player.getPackets()
					.sendGameMessage(
							"You need more than 500 life points to survive the power of overload.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(final Player player) {
				player.setOverloadDelay(501);
				player.addSuperFireImmune(360000);
				player.antiFire.start(300);
				player.overLoadTimer.start(300);
				WorldTasksManager.schedule(new WorldTask() {
					int count = 4;
					@Override
					public void run() {
						if (count == 0)
							stop();
						player.setNextAnimation(new Animation(3170));
						player.setNextGraphics(new Graphics(560));
						player.applyHit(new Hit(player, 100,
								HitLook.REGULAR_DAMAGE, 0));
						count--;
					}
				}, 0, 2);
			}
		},

		OVERLOAD() {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
						if (player.getOverloadDelay() > 0) {
					player.getPackets().sendGameMessage(
							"You may only use this potion every five minutes.");
					return false;
				}
				if (player.getHitpoints() <= 500 || player.getOverloadDelay() > 480) {
					player.getPackets()
					.sendGameMessage(
							"You need more than 500 life points to survive the power of overload.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(final Player player) {
				player.setOverloadDelay(501);
				player.overLoadTimer.start(300);
				WorldTasksManager.schedule(new WorldTask() {
					int count = 4;

					@Override
					public void run() {
						if (count == 0)
							stop();
						player.setNextAnimation(new Animation(3170));
						player.setNextGraphics(new Graphics(560));
						player.applyHit(new Hit(player, 100,
								HitLook.REGULAR_DAMAGE, 0));
						count--;
					}
				}, 0, 2);
			}
		},
		OVERLOAD2() {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				
				if (player.getHitpoints() <= 900 || player.getOverloadDelay() > 910) {
					player.getPackets()
					.sendGameMessage(
							"You need more than 900 life points to survive the power of the super overload.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(final Player player) {
				player.setOverloadDelay(701);
				player.addFireImmune(360000);
				player.setPrayerRenewalDelay(501);
				player.getPackets().sendGameMessage("mhhh, it tasts like a combination of antifire and prayerrenwal.");
				WorldTasksManager.schedule(new WorldTask() {
					int count = 3;

					@Override
					public void run() {
						if (count == 0)
							stop();
						player.setNextAnimation(new Animation(3170));
						player.setNextGraphics(new Graphics(560));
						player.setNextGraphics(new Graphics(2011));
						player.applyHit(new Hit(player, 200,
								HitLook.REGULAR_DAMAGE, 0));
						count--;
					}
				}, 0, 2);
			}
		},
		SUPER_PRAYER() {
			@Override
			public void extra(Player player) {
				
				player.getPrayer().restorePrayer(
						(int) ((int) (70 + (player.getSkills().getLevelForXp(
								Skills.PRAYER) * 3.43)) * player
								.getAuraManager()
								.getPrayerPotsRestoreMultiplier()));
			}
		},
		PRAYER_RENEWAL() {
			@Override
			public void extra(Player player) {
				player.setPrayerRenewalDelay(501);
				player.renewalTimer.start(300);
			}
		},
		SUPER_RESTORE(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE,
				Skills.MAGIC, Skills.RANGE, Skills.AGILITY, Skills.COOKING,
				Skills.CRAFTING, Skills.FARMING, Skills.FIREMAKING,
				Skills.FISHING, Skills.FLETCHING, Skills.HERBLORE,
				Skills.MINING, Skills.RUNECRAFTING, Skills.SLAYER,
				Skills.SMITHING, Skills.THIEVING, Skills.WOODCUTTING,
				Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}

			@Override
			public void extra(Player player) {
				player.getPrayer().restorePrayer(
						(int) ((int) (player.getSkills().getLevelForXp(
								Skills.PRAYER) * 0.33 * 10) * player
								.getAuraManager()
								.getPrayerPotsRestoreMultiplier()));
			}

		}, RESTORE_POTION(Skills.ATTACK, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE, Skills.PRAYER) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}
		};

		private int[] affectedSkills;
		private String drinkMessage;

		private Effects(int... affectedSkills) {
			this(null, affectedSkills);
		}

		private Effects(String drinkMessage, int... affectedSkills) {
			this.drinkMessage = drinkMessage;
			this.affectedSkills = affectedSkills;
		}

		public int getAffectedSkill(Player player, int skillId,
				int actualLevel, int realLevel) {
			return actualLevel;
		}

		public boolean canDrink(Player player) {
			return true;
		}

		public void extra(Player player) {
			// usualy unused
		}
	}

	public static Pot getPot(int id) {
		for (Pot pot : Pot.values())
			for (int potionId : pot.id) {
				if (id == potionId)
					return pot;
			}
		return null;
	}

	public static int getDoses(Pot pot, Item item) {
		for (int i = pot.id.length - 1; i >= 0; i--) {
			if (pot.id[i] == item.getId())
				return pot.id.length - i;
		}
		return 0;
	}

	public static int mixPot(Player player, Item fromItem, Item toItem, int fromSlot, int toSlot, boolean single) {
		if (single) {
			if (fromItem.getId() == VIAL || toItem.getId() == VIAL) {
				Pot pot = getPot(fromItem.getId() == VIAL ? toItem.getId() : fromItem.getId());
				if (pot == null || pot.isFlask())
					return -1;
				int doses = getDoses(pot, fromItem.getId() == VIAL ? toItem : fromItem);
				if (doses == 1) {
					player.getInventory().switchItem(fromSlot, toSlot);
					if (single)
						player.sendMessage("You pour from one container into the other.");
					return 1;
				}
				int vialDoses = doses / 2;
				doses -= vialDoses;
				player.getInventory().getItems().set(fromItem.getId() == VIAL ? toSlot : fromSlot, new Item(pot.getIdForDoses(doses), 1));
				player.getInventory().getItems().set(fromItem.getId() == VIAL ? fromSlot : toSlot, new Item(pot.getIdForDoses(vialDoses), 1));
				player.getInventory().refresh(fromSlot);
				player.getInventory().refresh(toSlot);
				if (single)
					player.sendMessage("You split the potion between the two vials.");
				return 2;
			}
		}
		Pot pot = getPot(fromItem.getId());
		if (pot == null)
			return -1;
		int doses2 = getDoses(pot, toItem);
		if (doses2 == 0 || doses2 == pot.getMaxDoses()) // not same pot type or
			// full already
			return -1;
		int doses1 = getDoses(pot, fromItem);
		doses2 += doses1;
		doses1 = doses2 > pot.getMaxDoses() ? doses2 - pot.getMaxDoses() : 0;
		doses2 -= doses1;
		if (doses1 == 0 && pot.isFlask())
			player.getInventory().deleteItem(fromSlot, fromItem);
		else {
			player.getInventory().getItems().set(fromSlot, new Item(doses1 > 0 ? pot.getIdForDoses(doses1) : VIAL, 1));
			player.getInventory().refresh(fromSlot);
		}
		player.getInventory().getItems().set(toSlot, new Item(pot.getIdForDoses(doses2), 1));
		player.getInventory().refresh(toSlot);
		if (single)
			player.sendMessage("You pour from one container into the other" + (pot.isFlask() && doses1 == 0 ? " and the glass shatters to pieces." : "."));
		return 3;
	}

	public static boolean emptyPot(Player player, Item item, int slot) {
		Pot pot = getPot(item.getId());
		if (pot == null || pot.isFlask())
			return false;
		item.setId(VIAL);
		player.getInventory().refresh(slot);
		player.sendMessage("You empty the vial.");
		return true;
	}

	public static boolean pot(Player player, Item item, int slot) {
		Pot pot = getPot(item.getId());
		if (pot == null)
			return false;
		if (player.getPotDelay() > Utils.currentTimeMillis())
			return true;
		if (!player.getControlerManager().canPot(pot))
			return true;
		if (!pot.effect.canDrink(player))
			return true;
		player.addPotDelay(1075);
		pot.effect.extra(player);
		int dosesLeft = getDoses(pot, item) - 1;
		if (dosesLeft == 0 && pot.isFlask())
			player.getInventory().deleteItem(slot, item);
		else {
			player.getInventory().getItems().set(slot, new Item(
					dosesLeft > 0 ? pot.getIdForDoses(dosesLeft) : pot.isPotion() ? VIAL : getReplacedId(pot), 1));
			player.getInventory().refresh(slot);
		}
		for (int skillId : pot.effect.affectedSkills)
			player.getSkills().set(skillId, pot.effect.getAffectedSkill(player, skillId,
					player.getSkills().getLevel(skillId), player.getSkills().getLevelForXp(skillId)));
		player.setNextAnimation(new Animation(18000));
		player.getPackets().sendSound(4580, 0, 1);
		if (pot.isFlask() || pot.isPotion()) {
			player.sendMessage(pot.effect.drinkMessage != null ? pot.effect.drinkMessage
					: "You drink some of your "
							+ item.getDefinitions().getName().toLowerCase().replace(" (1)", "").replace(" (2)", "")
									.replace(" (3)", "").replace(" (4)", "").replace(" (5)", "").replace(" (6)", "")
							+ "."
					);
			player.sendMessage(dosesLeft == 0
					? "You have finished your " + (pot.isFlask() ? "flask and the glass shatters to pieces."
							: pot.isPotion() ? "potion." : item.getName().toLowerCase() + ".")
					: "You have " + dosesLeft + " dose of potion left.");
		}
		return true;
	}

	@SuppressWarnings("incomplete-switch")
	private static int getReplacedId(Pot pot) {
		switch (pot) {
		case JUG:
			return 1935;
		case BEER:
			return 1919;
		}
		return 0;
	}

	public static void resetOverLoadEffect(Player player) {
		if (!player.isDead()) {
			int actualLevel = player.getSkills().getLevel(Skills.ATTACK);
			int realLevel = player.getSkills().getLevelForXp(Skills.ATTACK);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.ATTACK, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.STRENGTH);
			realLevel = player.getSkills().getLevelForXp(Skills.STRENGTH);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.STRENGTH, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.DEFENCE);
			realLevel = player.getSkills().getLevelForXp(Skills.DEFENCE);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.DEFENCE, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.MAGIC);
			realLevel = player.getSkills().getLevelForXp(Skills.MAGIC);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.MAGIC, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.RANGE);
			realLevel = player.getSkills().getLevelForXp(Skills.RANGE);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.RANGE, realLevel);
			player.heal(500);
		}
		player.setOverloadDelay(0);
		player.getPackets()
				.sendGameMessage("<col=480000>The effects of overload have worn off and you feel normal again.");
	}

	public static void applyOverLoadEffect(Player player) {
		if (player.getControlerManager().getControler() instanceof Wilderness
				|| FfaZone.isOverloadChanged(player)) {
			int actualLevel = player.getSkills().getLevel(Skills.ATTACK);
			int realLevel = player.getSkills().getLevelForXp(Skills.ATTACK);
			int level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.ATTACK, (int) (level + 5 + (realLevel * 0.15)));

			actualLevel = player.getSkills().getLevel(Skills.STRENGTH);
			realLevel = player.getSkills().getLevelForXp(Skills.STRENGTH);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.STRENGTH, (int) (level + 5 + (realLevel * 0.15)));

			actualLevel = player.getSkills().getLevel(Skills.DEFENCE);
			realLevel = player.getSkills().getLevelForXp(Skills.DEFENCE);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.DEFENCE, (int) (level + 5 + (realLevel * 0.15)));

			actualLevel = player.getSkills().getLevel(Skills.MAGIC);
			realLevel = player.getSkills().getLevelForXp(Skills.MAGIC);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.MAGIC, level + 5);

			actualLevel = player.getSkills().getLevel(Skills.RANGE);
			realLevel = player.getSkills().getLevelForXp(Skills.RANGE);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.RANGE, (int) (level + 5 + (realLevel * 0.1)));
		} else {
			int actualLevel = player.getSkills().getLevel(Skills.ATTACK);
			int realLevel = player.getSkills().getLevelForXp(Skills.ATTACK);
			int level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.ATTACK, (int) (level + 5 + (realLevel * 0.22)));

			actualLevel = player.getSkills().getLevel(Skills.STRENGTH);
			realLevel = player.getSkills().getLevelForXp(Skills.STRENGTH);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.STRENGTH, (int) (level + 5 + (realLevel * 0.22)));

			actualLevel = player.getSkills().getLevel(Skills.DEFENCE);
			realLevel = player.getSkills().getLevelForXp(Skills.DEFENCE);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.DEFENCE, (int) (level + 5 + (realLevel * 0.22)));

			actualLevel = player.getSkills().getLevel(Skills.MAGIC);
			realLevel = player.getSkills().getLevelForXp(Skills.MAGIC);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.MAGIC, level + 7);

			actualLevel = player.getSkills().getLevel(Skills.RANGE);
			realLevel = player.getSkills().getLevelForXp(Skills.RANGE);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.RANGE, (int) (level + 4 + (Math.floor(realLevel / 5.2))));
		}
	}
	
	public static void decantPotsInv(Player player) {
		int count = 0;
		outLoop: for (int fromSlot = 0; fromSlot < 28; fromSlot++) {
			Item fromItem = player.getInventory().getItem(fromSlot);
			if (fromItem == null)
				continue outLoop;
			innerLoop: for (int toSlot = 0; toSlot < 28; toSlot++) {
				Item toItem = player.getInventory().getItem(toSlot);
				if (toItem == null || fromSlot == toSlot)
					continue innerLoop;
				if (mixPot(player, fromItem, toItem, fromSlot, toSlot, false) != -1) {
					count++;
					break innerLoop;
				}
			}
		}

		if (count != 0) {
			for (Item item : player.getInventory().getItems().getItems()) {
				if (item == null || item.getId() != VIAL)
					continue;
				player.getInventory().deleteItem(item);
				player.getInventory().getItems().shift();
				player.getInventory().addItem(item);
			}
			player.getInventory().refresh();
		}
	}
}
