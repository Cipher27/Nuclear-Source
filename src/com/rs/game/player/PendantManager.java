package com.rs.game.player;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.Settings;
import com.rs.cache.loaders.ClientScriptMap;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author paolo
 *
 */
public class PendantManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7894811769924585760L;

	public enum Pendants {

		ATTACK_PENDANT(24714, Skills.ATTACK), STRENGTH_PENDANT(24716, Skills.STRENGTH), DEFENCE_PENDANT(24718, Skills.DEFENCE), RANGING_PENDANT(24720, Skills.RANGE), MAGIC_PENDANT(24722, Skills.MAGIC), PRAYER_PENDANT(24724, Skills.PRAYER), RUNECRAFTING_PENDANT(24726, Skills.RUNECRAFTING), CONSTRUCTION_PENDANT(24728, Skills.CONSTRUCTION), DUNGEONEERING_PENDANT(24730, Skills.DUNGEONEERING), CONSTITUTION_PENDANT(24732, Skills.HITPOINTS), AGILITY_PENDANT(24734, Skills.AGILITY), HERBLORE_PENDANT(24736, Skills.HERBLORE), THIEVERY_PENDANT(24738, Skills.THIEVING), CRAFTING_PENDANT(24740, Skills.CRAFTING), FLETCHING_PENDANT(24742, Skills.FLETCHING), SLAYING_PENDANT(24744, Skills.SLAYER), HUNTING_PENDANT(24746, Skills.HUNTER), MINING_PENDANT(24748, Skills.MINING), SMITHING_PENDANT(24750, Skills.SMITHING), FISHING_PENDANT(24752, Skills.FISHING), COOKING_PENDANT(24754, Skills.COOKING), FIREMAKING_PENDANT(24756, Skills.FIREMAKING), WOODCUTTING_PENDANT(24758, Skills.WOODCUTTING), FARMING_PENDANT(24760, Skills.FARMING), SUMMONING_PENDANT(24762, Skills.SUMMONING);

		int id;
		int skill;

		Pendants(int id, int skill) {
			this.id = id;
			this.skill = skill;
		}

		public int getSkillId() {
			return skill;
		}
	}

	/**
	 * gives the active pendant exp based on the players level don't care the
	 * layout, no formula so had to copy everything
	 * 
	 * @return
	 */

	public double givePendantExp(int skillId) {
	//	player.sm("" + player.getSkills().getLevel(skillId));
		switch (player.getSkills().getLevel(skillId)) {
		case 1:
			return 300;
		case 34:
			return 2684.4;
		case 67:
			return 10891.2;
		case 2:
			return 331.2;
		case 35:
			return 2798.4;
		case 68:
			return 11419.2;
		case 3:
			return 369.6;
		case 36:
			return 2920.8;
		case 69:
			return 11856;
		case 4:
			return 408;
		case 37:
			return 3048;
		case 70:
			return 12445.2;
		case 5:
			return 447.6;
		case 38:
			return 3177.6;
		case 71:
			return 12926.4;
		case 6:
			return 499.2;
		case 39:
			return 3319.2;
		case 72:
			return 13484.4;
		case 7:
			return 590.4;
		case 40:
			return 3458.4;
		case 73:
			return 14143.2;
		case 8:
			return 609.6;
		case 41:
			return 3609.6;
		case 74:
			return 14793.6;
		case 9:
			return 932.4;
		case 42:
			return 3765.6;
		case 75:
			return 15426;
		case 10:
			return 736.8;
		case 43:
			return 3926.4;
		case 76:
			return 16029.6;
		case 11:
			return 816;
		case 44:
			return 4096.8;
		case 77:
			return 16776;
		case 12:
			return 902.4;
		case 45:
			return 4269.6;
		case 78:
			return 17504.4;
		case 13:
			return 986.4;
		case 46:
			return 4459.2;
		case 79:
			return 18202.8;
		case 14:
			return 1099.2;
		case 47:
			return 4658.4;
		case 80:
			return 19104;
		case 15:
			return 1209.6;
		case 48:
			return 4860;
		case 81:
			return 19996.8;
		case 16:
			return 1255.2;
		case 49:
			return 5064;
		case 82:
			return 20868;
		case 17:
			return 1315.2;
		case 50:
			return 5284.8;
		case 83:
			return 21704.4;
		case 18:
			return 1368;
		case 51:
			return 5511.6;
		case 84:
			return 22857.6;
		case 19:
			return 1430.4;
		case 52:
			return 5760;
		case 85:
			return 23608.8;
		case 20:
			return 1488;
		case 53:
			return 5997.6;
		case 86:
			return 24158.4;
		case 21:
			return 1557.6;
		case 54:
			return 6261.6;
		case 87:
			return 25802.4;
		case 22:
			return 1617.6;
		case 55:
			return 6537.6;
		case 88:
			return 26844;
		case 23:
			return 1689.6;
		case 56:
			return 6825.6;
		case 89:
			return 28428;
		case 24:
			return 1764;
		case 57:
			return 7128;
		case 90:
			return 29383.2;
		case 25:
			return 1843.2;
		case 58:
			return 7420.8;
		case 91:
			return 30967.2;
		case 26:
			return 1915.2;
		case 59:
			return 7759.2;
		case 92:
			return 31749.6;
		case 27:
			return 1945.2;
		case 60:
			return 8084.4;
		case 93:
			return 33256.8;
		case 28:
			return 1987.2;
		case 61:
			return 8436;
		case 94:
			return 34732.8;
		case 29:
			return 2174.4;
		case 62:
			return 8810.4;
		case 95:
			return 36156;
		case 30:
			return 2270.4;
		case 63:
			return 9174;
		case 96:
			return 38709.6;
		case 31:
			return 2367.6;
		case 64:
			return 9621.6;
		case 97:
			return 40068;
		case 32:
			return 2467.2;
		case 65:
			return 10118.4;
		case 98:
		case 99:
			return 41289.6;
		case 33:
			return 2572.8;
		case 66:
			return 10423.2;
		}

		return 0;
	}

	public Pendants forId(int itemId) {
		for (Pendants pendant : Pendants.values()) {
			if (itemId == pendant.skill || player.getEquipment().getAmuletId() == itemId) {
				return pendant;
			}
		}
		return null;
	}

	public Pendants forItem(int itemId, boolean prized) {
		for (Pendants pendant : Pendants.values()) {
			if (itemId == (prized ? pendant.id : pendant.id - 1)) {
				return pendant;
			}
		}
		return null;
	}
	/**
	 * returns pendantItem based on skill id
	 * @param skillId
	 * @return
	 */
	public PendantItem forPendantItem(int skillId) {
		for (PendantItem pent : pendants) {
			if (pent.getPent().getSkillId() == skillId)
				return pent;
		}
		return null;
	}

	/**
	 * checks if the player has the pendant equiped
	 * 
	 * @return true if so
	 */
	public boolean hasAmulet() {
		boolean prized = ItemDefinitions.getItemDefinitions(player.getEquipment().getAmuletId()).name.contains("Prized");

		Pendants pendant = forItem(player.getEquipment().getAmuletId(),prized);
		if (pendant != null) {
			return true;
		}
		return false;
	}
	
	public boolean isAmulet(int item) {
		boolean prized = ItemDefinitions.getItemDefinitions(player.getEquipment().getAmuletId()).name.contains("Prized");

		Pendants pendant = forItem(item,prized);
		if (pendant != null) {
			return true;
		}
		return false;
	}

	/**
	 * gets the skill of the current equipped amulet
	 * 
	 * @return skill Id , -1 if null
	 */
	public int getSkill() {
		Pendants pendant = forId(player.getEquipment().getAmuletId());
		if (pendant != null) {
			return pendant.getSkillId();
		}
		return -1;
	}

	/**
	 * checks if the player can activate that certain pendant (only 1 of each
	 * skill)
	 * 
	 * @param skillId
	 * @return
	 */
	public boolean canPendant(int skillId) {
		for (PendantItem pent : pendants) {
			if (pent.getPent().getSkillId() == skillId)
				return false;
		}
		return true;
	}

	/**
	 * returns exp left
	 * 
	 * @param pent
	 * @return
	 */
	public double getExpLeft(Pendants pent) {
		for (PendantItem pent2 : pendants) {
			if (pent2.getPent().id == pent.id)
				return pent2.exp;
		}
		return 0;
	}

	/**
	 * returns pendant
	 * 
	 * @param pent
	 * @return
	 */
	public PendantItem getPendant(Pendants pent) {
		for (PendantItem pent2 : pendants) {
			if (pent2.getPent().id == pent.id)
				return pent2;
		}
		return null;
	}

	/**
	 * activates the pendant
	 * 
	 * @param pent
	 * @param skillId
	 */
	public void activatePendant(Pendants pent, int skillId, boolean prized) {
		//player.sm("Exp given: " + givePendantExp(skillId));
		pendants.add(new PendantItem(pent, (prized ? givePendantExp(skillId) * 2 : givePendantExp(skillId)) * Settings.XP_RATE/2));
		player.getInventory().deleteItem(new Item((prized ? 25469 : 25468), 1));
		player.getInventory().addItem(new Item((prized ? pent.id : pent.id - 1)));
	}

	/**
	 * deletes the pendant when it runs out of exp.
	 */
	public void deletePendant() {
		
		boolean prized = ItemDefinitions.getItemDefinitions(player.getEquipment().getAmuletId()).name.contains("Prized");

		Pendants pendant = forItem(player.getEquipment().getAmuletId(),prized);
		if(pendant != null){
		for (PendantItem pent2 : pendants) {
			if(pent2.getPent().getSkillId() == pendant.getSkillId()){
				pendants.remove(pent2);
				return;
				}
			}
		}
		player.getEquipment().deleteItem(new Item(pendant.id));
		player.getAppearence().generateAppearenceData();
		player.sm("Your pendant has run out of energy and turned into an depleted pendant.");
		player.getEquipment().getItems().set(Equipment.SLOT_AMULET, (prized ? new Item(24712) : new Item(24711)));
	}
	/**
	 * destroys pendant
	 * @param itemId
	 */
	public void destroyPendant(int itemId){
		boolean prized = ItemDefinitions.getItemDefinitions(player.getEquipment().getAmuletId()).name.contains("Prized");

		Pendants pendant = forItem(itemId,prized);
		if(pendant != null){
		for (PendantItem pent2 : pendants) {
			if(pent2.getPent().getSkillId() == pendant.getSkillId()){
				pendants.remove(pent2);
				return;
				}
			}
		}
	}

	/**
	 * handles exp decreasing
	 * 
	 * @param skillId
	 * @param exp
	 * @return
	 */
	public boolean handelExp(int skillId, double exp) {
		Pendants pent = forId(skillId);
		if (exp  < (getExpLeft(forId(skillId)))) {
			getPendant(pent).exp -= exp;
			return true;
		}
		return false;
	}

	/**
	 * player stuff
	 */
	private Player player;

	public void setPlayer(Player p) {
		this.player = p;
	}

	// list of active pendants (player can only 1 have)
	public ArrayList<PendantItem> pendants;

	public PendantManager() {
		this.pendants = new ArrayList<PendantItem>();
	}

	private final int[] DIALOGUE_INTERFACE_C2S = new int[] { Skills.ATTACK, Skills.MAGIC, Skills.MINING, Skills.WOODCUTTING, Skills.AGILITY, Skills.FLETCHING, Skills.THIEVING, Skills.STRENGTH, Skills.RANGE, Skills.SMITHING, Skills.FIREMAKING, Skills.HERBLORE, Skills.SLAYER, Skills.CONSTRUCTION, Skills.DEFENCE, Skills.PRAYER, Skills.FISHING, Skills.CRAFTING, Skills.FARMING, Skills.HUNTER, Skills.SUMMONING, Skills.HITPOINTS, Skills.DUNGEONEERING, Skills.COOKING, Skills.RUNECRAFTING };

	/**
	 * sends the skill picking interface
	 * 
	 * @param player
	 * @param slot
	 * @param id
	 */
	public void openSelectableDialog(final Player player, final int slot, final int id) {
		player.getDialogueManager().startDialogue(new Dialogue() {
			private int selectedSkill = -1;

			@Override
			public void start() {
				if (player.hasHouse) //player needs a house before you can use this lamp
				player.getPackets().sendGlobalConfig(738, 1);
				else
				player.getPackets().sendGlobalConfig(738, 0);
				player.getInterfaceManager().sendInterface(1263);
				player.getPackets().sendGlobalString(358, "What sort of XP would you like?");
				sendSelectedSkill();
				player.getPackets().sendHideIComponent(1263, 11, true);
				player.getPackets().sendHideIComponent(1263, 10, true);
				player.getPackets().sendHideIComponent(1263, 9, true);
				player.getPackets().sendHideIComponent(1263, 8, true);
				player.getPackets().sendGlobalConfig(1797, 0); // selectable
																// lamps don't
																// show xp
				player.getPackets().sendGlobalConfig(1798, 1); // minimum level
																// of 1 to show
				player.getPackets().sendGlobalConfig(1799, id);

				for (int i = 13; i < 38; i++)
					player.getPackets().sendUnlockIComponentOptionSlots(1263, i, -1, 0, 0);

			}

			@Override
			public void run(int interfaceId, int componentId) {
				if (player.isSkiller() && Skills.isCombatSkill(selectedSkill)) {
					player.sm("You do this as a skiller");
					return;
				}
				if (componentId >= 13 && componentId <= 37) {
					int skill = DIALOGUE_INTERFACE_C2S[componentId - 13];
					if (skill != Skills.CONSTRUCTION) {
						selectedSkill = skill;
						sendSelectedSkill();
						player.getPackets().sendUnlockIComponentOptionSlots(1263, 43, -1, 0, true, 0); // not
																										// the
																										// right
																										// way
																										// but
																										// eh
					}
				} else if (componentId == 43 && selectedSkill != -1) {
					if (!player.getInventory().containsItem(id, 1)) {
						end();
						return;
					}
					if(selectedSkill == Skills.ATTACK || selectedSkill == Skills.DEFENCE || selectedSkill == Skills.STRENGTH|| selectedSkill == Skills.MAGIC|| selectedSkill == Skills.RANGE || selectedSkill == Skills.CONSTRUCTION){
						player.getInterfaceManager().closeScreenInterface();
						player.getDialogueManager().startDialogue("SimpleMessage", "Combat skills have been disabled.");
						return;
					}
					Pendants pent = forId(selectedSkill);
					if (canPendant(selectedSkill)) {
						boolean prized = ItemDefinitions.getItemDefinitions(id).name.contains("Prized");
						activatePendant(pent, selectedSkill, prized);
						player.getInterfaceManager().removeScreenInterface();
						player.getInterfaceManager().closeScreenInterface();
						sendDialogue("<col=0000ff>You transformed your pendant!");
					} else {
						sendDialogue("<col=0000ff>You already have an pendant of this skill active. Finish that pendant before activating a new one.");
					}
				} else if (componentId == 44 || componentId == 7) {
					end();
				}
			}

			@Override
			public void finish() {
				if (player.getInterfaceManager().containsScreenInter())
					player.getInterfaceManager().removeScreenInterface();
			}

			private void sendSelectedSkill() {
				ClientScriptMap map = ClientScriptMap.getMap(681);
				if (selectedSkill == map.getDefaultIntValue()) {
					player.getPackets().sendGlobalConfig(1796, map.getDefaultIntValue());
					return;
				}

				long key = map.getKeyForValue(selectedSkill);
				player.getPackets().sendGlobalConfig(1796, (int) key);
			}

		});

	}

	/**
	 * pendant Item class
	 * 
	 * @author paolo
	 *
	 */
	@SuppressWarnings("unused")
	private class PendantItem implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2739440471980564700L;
		private Pendants pent;
		private Double exp;

		public PendantItem(Pendants pent, Double exp) {
			this.pent = pent;
			this.exp = exp;

		}

		/**
		 * @return the pent
		 */
		public Pendants getPent() {
			return pent;
		}

		/**
		 * @param pent
		 *            the pent to set
		 */
		public void setPent(Pendants pent) {
			this.pent = pent;
		}

		/**
		 * @return the exp
		 */
		public Double getExp() {
			return exp;
		}

		/**
		 * @param exp
		 *            the exp to set
		 */
		public void setExp(Double exp) {
			this.exp = exp;
		}
	}

}
