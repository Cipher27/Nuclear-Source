package com.rs.game.player;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.player.PendantManager.Pendants;
import com.rs.game.player.content.BrawlingGManager.BrawlingGloves;
import com.rs.game.player.content.Tournaments;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.dialogues.worldwide.LevelUp;
import com.rs.utils.ChatColors;
import com.rs.utils.TournamentsR;
import com.rs.utils.Utils;

public final class Skills implements Serializable {

	private static final long serialVersionUID = -7086829989489745985L;

	public static final double MAXIMUM_EXP = 200000000;
	public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2, HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6, COOKING = 7, WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11, CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15, AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19, RUNECRAFTING = 20, CONSTRUCTION = 22, HUNTER = 21, SUMMONING = 23, DUNGEONEERING = 24;

	public static final int LegendaryPet = 0, LegendaryPet_CALL = 1, FINAL_BLOW = 2, SWIFT_SPEED = 3, STEALTH_MOVES = 4;

	public static final String[] SKILL_NAME_LegendaryPet = { "LegendaryPet", "Divination", "Final Blow", "Swift Speed", "Stealth Moves" };

	public static final String[] SKILL_NAME = { "Attack", "Defence", "Strength", "Constitution", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction", "Summoning", "Dungeoneering" };

	public static boolean isCombatSkill(int skillId) {
		switch (skillId) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 23:
			return true;
		}
		return false;
	}

	public short LegendaryPetLevel[];
	public short level[];
	private double LegendaryPetXp[];
	private double xp[];
	private double[] xpTracks;
	private boolean[] trackSkills;
	private byte[] trackSkillsIds;
	private boolean xpDisplay, xpPopup;

	private transient int currentCounter;
	private transient Player player;

	public void passLevels(Player p) {
		this.level = p.getSkills().level;
		this.xp = p.getSkills().xp;
	}

	public void setLegendaryPet() {
		LegendaryPetLevel = new short[5];
		LegendaryPetXp = new double[5];
	}

	public Skills() {
		LegendaryPetLevel = new short[5];
		LegendaryPetXp = new double[5];
		level = new short[25];
		xp = new double[25];
		for (int i = 0; i < level.length; i++) {
			level[i] = 1;
			xp[i] = 0;
		}
		level[3] = 10;
		xp[3] = 1184;
		level[HERBLORE] = 3;
		xp[HERBLORE] = 250;
		xpPopup = true;
		xpTracks = new double[3];
		trackSkills = new boolean[3];
		trackSkillsIds = new byte[3];
		trackSkills[0] = true;
		for (int i = 0; i < trackSkillsIds.length; i++)
			trackSkillsIds[i] = 30;

	}

	public void sendXPDisplay() {
		for (int i = 0; i < trackSkills.length; i++) {
			player.getPackets().sendConfigByFile(10444 + i, trackSkills[i] ? 1 : 0);
			player.getPackets().sendConfigByFile(10440 + i, trackSkillsIds[i] + 1);
			refreshCounterXp(i);
		}
	}

	public void setupXPCounter() {
		player.getInterfaceManager().sendXPDisplay(1214);
	}

	public void refreshCurrentCounter() {
		player.getPackets().sendConfig(2478, currentCounter + 1);
	}

	public void setCurrentCounter(int counter) {
		if (counter != currentCounter) {
			currentCounter = counter;
			refreshCurrentCounter();
		}
	}

	public void switchTrackCounter() {
		trackSkills[currentCounter] = !trackSkills[currentCounter];
		player.getPackets().sendConfigByFile(10444 + currentCounter, trackSkills[currentCounter] ? 1 : 0);
	}

	public void resetCounterXP() {
		xpTracks[currentCounter] = 0;
		refreshCounterXp(currentCounter);
	}

	public void setCounterSkill(int skill) {
		xpTracks[currentCounter] = 0;
		trackSkillsIds[currentCounter] = (byte) skill;
		player.getPackets().sendConfigByFile(10440 + currentCounter, trackSkillsIds[currentCounter] + 1);
		refreshCounterXp(currentCounter);
	}

	public void refreshCounterXp(int counter) {

		player.getPackets().sendConfig(counter == 0 ? 1801 : 2474 + counter, (int) (xpTracks[counter] * 10));
	}

	public void handleSetupXPCounter(int componentId) {
		if (componentId == 18)
			player.getInterfaceManager().sendXPDisplay();
		else if (componentId >= 22 && componentId <= 24)
			setCurrentCounter(componentId - 22);
		else if (componentId == 27)
			switchTrackCounter();
		else if (componentId == 61)
			resetCounterXP();
		else if (componentId >= 31 && componentId <= 57)
			if (componentId == 33)
				setCounterSkill(4);
			else if (componentId == 34)
				setCounterSkill(2);
			else if (componentId == 35)
				setCounterSkill(3);
			else if (componentId == 42)
				setCounterSkill(18);
			else if (componentId == 49)
				setCounterSkill(11);
			else
				setCounterSkill(componentId >= 56 ? componentId - 27 : componentId - 31);

	}

	public void restoreSummoning() {
		level[23] = (short) getLevelForXp(23);
		refresh(23);
	}

	public void sendInterfaces() {
		if (xpDisplay)
			player.getInterfaceManager().sendXPDisplay();
		if (xpPopup)
			player.getInterfaceManager().sendXPPopup();
	}

	public void switchXPDisplay() {
		xpDisplay = !xpDisplay;
		if (xpDisplay)
			player.getInterfaceManager().sendXPDisplay();
		else
			player.getInterfaceManager().closeXPDisplay();
	}

	public void switchXPPopup() {
		xpPopup = !xpPopup;
		player.getPackets().sendGameMessage("XP pop-ups are now " + (xpPopup ? "en" : "dis") + "abled.");
		if (xpPopup)
			player.getInterfaceManager().sendXPPopup();
		else
			player.getInterfaceManager().closeXPPopup();
	}

	public void restoreSkills() {
		for (int skill = 0; skill < level.length; skill++) {
			level[skill] = (short) getLevelForXp(skill);
			refresh(skill);
		}
	}

	public void setPlayer(Player player) {
		this.player = player;
		// temporary
		if (xpTracks == null) {
			xpPopup = true;
			xpTracks = new double[3];
			trackSkills = new boolean[3];
			trackSkillsIds = new byte[3];
			trackSkills[0] = true;
			for (int i = 0; i < trackSkillsIds.length; i++)
				trackSkillsIds[i] = 30;
		}
	}

	public short[] getLevels() {
		return level;
	}

	public double[] getXp() {
		return xp;
	}
	
	

	public int getLegendaryPetLevel(int skill) {
		return LegendaryPetLevel[skill];
	}

	public int getLevel(int skill) {
		return level[skill];
	}

	public double getLegendaryPetXp(int skill) {
		return LegendaryPetXp[skill];
	}

	public double getXp(int skill) {
		return xp[skill];
	}

	public int getTotalLevel1(Player player) {
		int totallevel = 0;
		for (int i = 0; i <= 24; i++) {
			totallevel += player.getSkills().getLevelForXp(i);
		}
		return totallevel;
	}

	public String getTotalXp1(Player player) {
		double totalxp = 0;
		for (double xp : player.getSkills().getXp()) {
			totalxp += xp;
		}
		NumberFormat formatter = new DecimalFormat("#######");
		return formatter.format(totalxp);
	}

	public boolean hasRequiriments(int... skills) {
		for (int i = 0; i < skills.length; i += 2) {
			int skillId = skills[i];
			if (skillId == CONSTRUCTION || skillId == FARMING)
				continue;
			int skillLevel = skills[i + 1];
			if (getLevelForXp(skillId) < skillLevel)
				return false;

		}
		return true;
	}

	public int getCombatLevel() {
		int attack = getLevelForXp(0);
		int defence = getLevelForXp(1);
		int strength = getLevelForXp(2);
		int hp = getLevelForXp(3);
		int prayer = getLevelForXp(5);
		int ranged = getLevelForXp(4);
		int magic = getLevelForXp(6);
		int combatLevel = 3;
		combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.25) + 1;
		double melee = (attack + strength) * 0.325;
		double ranger = Math.floor(ranged * 1.5) * 0.325;
		double mage = Math.floor(magic * 1.5) * 0.325;
		if (melee >= ranger && melee >= mage) {
			combatLevel += melee;
		} else if (ranger >= melee && ranger >= mage) {
			combatLevel += ranger;
		} else if (mage >= melee && mage >= ranger) {
			combatLevel += mage;
		}
		return combatLevel;
	}

	public void set(int skill, int newLevel) {
		level[skill] = (short) newLevel;
		refresh(skill);
	}

	public void LegendaryPetSet(int skill, int newLevel) {
		LegendaryPetLevel[skill] = (short) newLevel;
	}

	public int drainLevel(int skill, int drain) {
		int drainLeft = drain - level[skill];
		if (drainLeft < 0) {
			drainLeft = 0;
		}
		level[skill] -= drain;
		if (level[skill] < 0) {
			level[skill] = 0;
		}
		refresh(skill);
		return drainLeft;
	}

	public int getCombatLevelWithSummoning() {
		return getCombatLevel() + getSummoningCombatLevel();
	}

	public int getSummoningCombatLevel() {
		return getLevelForXp(Skills.SUMMONING) / 8;
	}

	public void drainSummoning(int amt) {
		int level = getLevel(Skills.SUMMONING);
		if (level == 0)
			return;
		set(Skills.SUMMONING, amt > level ? 0 : level - amt);
	}

	public static int getXPForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXp(int skill) {
		double exp = xp[skill];
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= (skill == DUNGEONEERING ? 120 : 99); lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if ((output - 1) >= exp) {
				return lvl;
			}
		}
		return skill == DUNGEONEERING ? 120 : 99;
	}

	public int getLegendaryPetLevelForXp(int skill) {
		double exp = LegendaryPetXp[skill];
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if ((output - 1) >= exp) {
				return lvl;
			}
		}
		return 99;
	}

	public void init() {
		for (int skill = 0; skill < level.length; skill++)
			refresh(skill);
		sendXPDisplay();
	}

	public void refresh(int skill) {
		player.getPackets().sendSkillLevel(skill);
	}

	/*
	 * if(componentId == 33) setCounterSkill(4); else if(componentId == 34)
	 * setCounterSkill(2); else if(componentId == 35) setCounterSkill(3); else
	 * if(componentId == 42) setCounterSkill(18); else if(componentId == 49)
	 * setCounterSkill(11);
	 */

	public int getCounterSkill(int skill) {
		switch (skill) {
		case ATTACK:
			return 0;
		case STRENGTH:
			return 1;
		case DEFENCE:
			return 4;
		case RANGE:
			return 2;
		case HITPOINTS:
			return 5;
		case PRAYER:
			return 6;
		case AGILITY:
			return 7;
		case HERBLORE:
			return 8;
		case THIEVING:
			return 9;
		case CRAFTING:
			return 10;
		case MINING:
			return 12;
		case SMITHING:
			return 13;
		case FISHING:
			return 14;
		case COOKING:
			return 15;
		case FIREMAKING:
			return 16;
		case WOODCUTTING:
			return 17;
		case SLAYER:
			return 19;
		case FARMING:
			return 20;
		case CONSTRUCTION:
			return 21;
		case HUNTER:
			return 22;
		case SUMMONING:
			return 23;
		case DUNGEONEERING:
			return 24;
		case MAGIC:
			return 3;
		case FLETCHING:
			return 18;
		case RUNECRAFTING:
			return 11;
		default:
			return -1;
		}

	}

	public void addLegendaryPetXp(double exp) {
		int oldLevel = getLegendaryPetLevelForXp(1);
		LegendaryPetXp[1] += exp;
		if (LegendaryPetXp[1] > MAXIMUM_EXP) {
			LegendaryPetXp[1] = MAXIMUM_EXP;
		}
		int newLevel = getLegendaryPetLevelForXp(1);
		int levelxd = newLevel - oldLevel;
		if (newLevel > oldLevel) {
			LegendaryPetLevel[1] += levelxd;
			player.getDialogueManager().startDialogue("LegendaryPetLevelUp", 1);
		}
	}

	public int getLegendaryPetLevel() {
		return LegendaryPetLevel[1];
	}

	public double getLegendaryPetXp() {
		return LegendaryPetXp[1];
	}

	BrawlingGloves gloves;

	public void addXp(int skill, double exp) {
		double test = exp *(player.isDonator() ? Settings.XP_RATE + 1 : Settings.XP_RATE);
		int regular = (int) exp * Settings.XP_RATE;
		gloves = player.getBGloves().getGloves();
		player.getControlerManager().trackXP(skill, (int) exp);
		if (player.isXpLocked())
			return;
		if (player.isSkiller && (skill == ATTACK || skill == STRENGTH || skill == DEFENCE || skill == HITPOINTS || skill == RANGE || skill == MAGIC)) {
			return;
		}
		if (skill != ATTACK && skill != DEFENCE && skill != STRENGTH && skill != MAGIC && skill != RANGE && skill != HITPOINTS)
			exp *= player.isDonator() ? Settings.XP_RATE + 1 : Settings.XP_RATE;
		else
			exp *= player.isDonator() ? Settings.COMBAT_XP_RATE + 1 : Settings.COMBAT_XP_RATE;
		if (gloves != null) {
			if (skill == gloves.getSkillID()) {
				boolean isAtDeepWild = (Wilderness.isAtWild(player) && Wilderness.getWildLevel(player) > 46);
				exp *= isAtDeepWild ? 3 : 2;

				player.getBGloves().GLOVE_EXP[skill] -= isAtDeepWild ? exp / 3 : exp / 2;

				if (player.getBGloves().GLOVE_EXP[skill] <= 0) {
					player.getBGloves().GLOVE_EXP[skill] = 0;
					player.getEquipment().deleteItem(gloves.getItemID(), 1);
					player.getEquipment().refresh(Equipment.SLOT_HANDS);
					player.getAppearence().generateAppearenceData();
					player.sm("<col=" + ChatColors.RED + ">Your " + gloves.getItemName() + " has ran out of bonus xp and turned into dust.");
				}
			}
		}
		

		if (player.getEquipment().getBootsId() == 30920 && player.getSilverhawkFeathers() > 0)
			player.silverhawkBoots();
/*		if (player.getLegendaryPet().hasLegendaryPetSpawned) {
			player.sm("exp gained :" + exp + "total exp: " + player.getLegendaryPet().getPetTotalExp());
			player.getLegendaryPet().addExp(exp);
		}*/
		int random = Utils.random(10);
		if (random < 4 && !(skill == ATTACK || skill == STRENGTH || skill == DEFENCE || skill == HITPOINTS || skill == RANGE || skill == MAGIC)) {
			player.getPointsManager().setPowerTokens(player.getPointsManager().getPowerTokens() + Utils.random(5, 10));

		}
		if (player.getPrizedPendants().hasAmulet()) {
			Pendants pent = player.getPrizedPendants().forId(skill);
			if(pent.skill == skill){
			if (pent != null) {
				if (player.getPrizedPendants().handelExp(skill, exp))
					exp *= 2;
				else {
					exp += player.getPrizedPendants().getExpLeft(pent);
					player.getPrizedPendants().deletePendant();
					}
				}
			}

		}
		int random2 = Utils.random(10);
		if (random2 > 8 && player.getEquipment().getGlovesId() == 28014 && !(skill == ATTACK || skill == STRENGTH || skill == DEFENCE || skill == HITPOINTS || skill == RANGE || skill == MAGIC)) {
			player.handelGoldGloves();
		}
		Tournaments ts = new Tournaments();
		if (ts.getSkill() == skill && ts.active() /* && validTournXp */) {
			if (player.tournamentKey == ts.getKey()) {
				player.tournamentXp += (int) exp;
			} else {
				player.tournamentXp = 0;
				player.tournamentKey = ts.getKey();
			}
			TournamentsR.checkRank(player);
		}
		player.getControlerManager().trackXP(skill, (int) exp);
		/* spotlight exp boosts */
		if (World.getSpotLightSkill() == skill || World.getSpotLightCombatSkill() == skill) {
			exp *= 1.5;
		}
		

		if (player.getEquipment().getShieldId() == 15440) {
			if (player.horn > 0) {
				exp *= 2;
				player.horn--;
			} else {
				player.getInventory().deleteItem(15440, 1);
				player.getInventory().addItem(15439, 1);
				player.getPackets().sendGameMessage("Your penance horn is out of charges and does no longer give a bonus.");
			}
		}
		if (player.hasAgileSet(player) && (skill == AGILITY))
			exp *= 1.2;
		if (World.doubleexp || !player.doubleExpTime.finished() || Settings.doubleExp)
			exp *= 2;
		if (World.moreprayer && skill == PRAYER)
			exp *= 2;
		
		if (player.getAuraManager().usingWisdom())
			exp *= 1.025;
		if (World.getPlayers().size() >= 20 && World.getPlayers().size() < 30)
			exp *= 1;
		else if (World.getPlayers().size() >= 30)
			exp *= 2;

		/*if (player.getGameMode() == 0) { // Regular players
			exp *= 1;
			if (player.isAngelicDonator())
				exp *= 1.0;
			else if (player.isDivineDonator())
				exp *= 1.0;
			else if (player.isSupremeDonator())
				exp *= 1.0;
			else if (player.isLegendaryDonator())
				exp *= 1.2;
			else if (player.isExtremeDonator())
				exp *= 1.1;
			else if (player.isDonator())
				exp *= 1.0;

		} */
		int boosted_exp = (int) exp;
		if(exp > test) 
		player.getPackets().sendConfig(2044, (boosted_exp - regular) * 10);
		int oldLevel = getLevelForXp(skill);
		int oldXP = (int) xp[skill];
		xp[skill] += exp;
		for (int i = 0; i < trackSkills.length; i++) {
			if (trackSkills[i]) {
				if (trackSkillsIds[i] == 30 || (trackSkillsIds[i] == 29 && (skill == Skills.ATTACK || skill == Skills.DEFENCE || skill == Skills.STRENGTH || skill == Skills.MAGIC || skill == Skills.RANGE || skill == Skills.HITPOINTS)) || trackSkillsIds[i] == getCounterSkill(skill)) {
					xpTracks[i] += exp;
					refreshCounterXp(i);
				}
			}
		}

		if (xp[skill] > MAXIMUM_EXP) {
			xp[skill] = MAXIMUM_EXP;
		}
		if (oldXP < 104273167 && xp[skill] >= 104273167) {
			LevelUp.send104m(player, skill);
		}
		if (oldXP < 200000000 && xp[skill] == 200000000) {
			LevelUp.send200m(player, skill);
		}
		int newLevel = getLevelForXp(skill);
		int levelDiff = newLevel - oldLevel;
		if (newLevel > oldLevel) {
			level[skill] += levelDiff;
			player.getDialogueManager().startDialogue("LevelUp", skill);
			if (skill == SUMMONING || (skill >= ATTACK && skill <= MAGIC)) {
				player.getAppearence().generateAppearenceData();
				if (skill == HITPOINTS)
					player.heal(levelDiff * 10);
				else if (skill == PRAYER)
					player.getPrayer().restorePrayer(levelDiff * 10);
			}
		}
		refresh(skill);

		

	}

	public void addSkillXpRefresh(int skill, double xp) {
		this.xp[skill] += xp;
		level[skill] = (short) getLevelForXp(skill);
	}

	public static int getTotalLevel(Player player) {
		int totallevel = 0;
		for (int i = 0; i <= 24; i++) {
			totallevel += player.getSkills().getLevelForXp(i);
		}
		return totallevel;
	}

	public long getTotalXp() {
		long totalxp = 0;
		for (double xp : getXp()) {
			totalxp += xp;
		}
		return totalxp;
	}

	public void resetSkillNoRefresh(int skill) {
		xp[skill] = 0;
		level[skill] = 1;
	}

	public void setXp(int skill, double exp) {
		xp[skill] = exp;
		refresh(skill);
	}

	public int getTotalLevel() {
		int totalLevel = 0;
		for (int i = 0; i < level.length; i++) {
			totalLevel += getLevelForXp(i);
		}
		return totalLevel;
	}

	public double addXpLamp(int skill, double exp) {
		player.getControlerManager().trackXP(skill, (int) exp);
		if (player.isXpLocked())
			return 0;
		exp *= 1;
		int oldLevel = getLevelForXp(skill);
		int oldXP = (int) xp[skill];
		xp[skill] += exp;
		for (int i = 0; i < trackSkills.length; i++) {
			if (trackSkills[i]) {
				if (trackSkillsIds[i] == 30 || (trackSkillsIds[i] == 29 && (skill == Skills.ATTACK || skill == Skills.DEFENCE || skill == Skills.STRENGTH || skill == Skills.MAGIC || skill == Skills.RANGE || skill == Skills.HITPOINTS)) || trackSkillsIds[i] == getCounterSkill(skill)) {
					xpTracks[i] += exp;
					refreshCounterXp(i);
				}
			}
		}

		if (xp[skill] > MAXIMUM_EXP) {
			xp[skill] = MAXIMUM_EXP;
		}
		if (oldXP < 104273167 && xp[skill] >= 104273167) {
			LevelUp.send104m(player, skill);
		}
		if (oldXP < 200000000 && xp[skill] == 200000000) {
			LevelUp.send200m(player, skill);
		}
		int newLevel = getLevelForXp(skill);
		int levelDiff = newLevel - oldLevel;
		if (newLevel > oldLevel) {
			level[skill] += levelDiff;
			player.getDialogueManager().startDialogue("LevelUp", skill);
			if (skill == SUMMONING || (skill >= ATTACK && skill <= MAGIC)) {
				player.getAppearence().generateAppearenceData();
				if (skill == HITPOINTS)
					player.heal(levelDiff * 10);
				else if (skill == PRAYER)
					player.getPrayer().restorePrayer(levelDiff * 10);
			}

		}
		refresh(skill);
		return exp;
	}

	public int getHighestSkillLevel() {
		int maxLevel = 1;
		for (int skill = 0; skill < level.length; skill++) {
			int level = getLevelForXp(skill);
			if (level > maxLevel)
				maxLevel = level;
		}
		return maxLevel;
	}

}