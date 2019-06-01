package com.rs.game.player;

import java.io.Serializable;
import java.util.Arrays;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.io.OutputStream;
import com.rs.utils.Logger;
import com.rs.utils.Utils;


public class Appearence implements Serializable {

	private static final long serialVersionUID = 7655608569741626586L;

	private transient int renderEmote;
	private int title;
	private int[] lookI;
	private byte[] colour;
	private boolean male;
	private transient boolean glowRed;
	private transient byte[] appeareanceData;
	private transient byte[] md5AppeareanceDataHash;
	private transient short transformedNpcId;
	private transient boolean hidePlayer;

	private transient Player player;

	public Appearence() {
		male = true;
		renderEmote = -1;
		title = -1;
		resetAppearence();
	}

	public void passAppearance(Player player) {
		for (int i = 0; i < lookI.length; i++)
			lookI[i] = player.getAppearence().getLookI()[i];
		for (int i = 0; i < colour.length; i++)
			colour[i] = player.getAppearence().getColour()[i];
		male = player.getAppearence().male;
	}

	public void inBubble(boolean inBubble, int id) {
		transformedNpcId = (short) id;
		hidePlayer = inBubble;
	}

	public boolean isNPC() {
		return transformedNpcId != -1;
	}

	public void setGlowRed(boolean glowRed) {
		this.glowRed = glowRed;
		generateAppearenceData();
	}

	public void setPlayer(Player player) {
		this.player = player;
		transformedNpcId = -1;
		// renderEmote = isMale() ? 2699 : 2703;
		renderEmote = -1;
		if (lookI == null)
			resetAppearence();
	}

	public void transformIntoNPC(int id) {
		transformedNpcId = (short) id;
		generateAppearenceData();
	}

	public void switchHidden() {
		hidePlayer = !hidePlayer;
		generateAppearenceData();
	}

	public void setHidden(boolean hidePlayer, boolean... update) {
		this.hidePlayer = hidePlayer;
		if (update.length == 0 || (update.length == 1 && update[0]))
			generateAppearenceData();
	}

	public boolean isHidden() {
		return hidePlayer;
	}

	public boolean isGlowRed() {
		return glowRed;
	}

	public int getSkullId() {
		if (hidePlayer)
			return -1;
		return (/*player.isIronMan() ? 7 :*/ player.getGameMode() == 3 ? 16 : player.getGameMode() == 2 ? 2 : player.getGameMode() == 1 ? 3 : player.getGameMode() == 0 ? -1 : -1);
	}

	public void setBootsColor(int color) {
		colour[3] = (byte) color;
	}

	public void setBootsStyle(int i) {
		lookI[6] = i;
	}
	
	public String getTitles(int id, Player player){
		switch(id){
		case 0:
			return "Yt 'Haar";
		case 11:
			return "the Maxed";
		}
		return ""+player.getDisplayName();
	}

	public void generateAppearenceData() {
		OutputStream stream = new OutputStream();
		int flag = 0;

		if (!male) {
			flag |= 0x1;
		}

		if (transformedNpcId >= 0 && NPCDefinitions.getNPCDefinitions(transformedNpcId).aBoolean3190) {
			flag |= 0x2;
		}

		if (title != 0)
		 {
			flag |= title >= 32 && title <= 37 || title == 43 || title == 40 || title >= 59 && title <= 67 || title >= 45 && title >= 47 || title >= 51 ? 0x80 : 0x40; // after/before
		}
		stream.writeByte(flag);
		if (title != 0) {
			String titleName = "";

			//ClientScriptMap.getMap(male ? 1093 : 3872).getStringValue(title);
			stream.writeGJString(titleName);
		}

		stream.writeByte(player.hasSkull() ? player.getSkullId() : getSkullId());
		stream.writeByte(player.getPrayer().getPrayerHeadIcon()); // prayer icon
		stream.writeByte(hidePlayer ? 1 : 0);

		// npc
		if (transformedNpcId >= 0) {
			stream.writeShort(-1); // 65535 tells it a npc
			stream.writeShort(transformedNpcId);
			stream.writeByte(0);

		} else {
			// player
			Item[] cosmetics = player.getEquipment().getCosmeticItems().getItems();

			boolean[] hiddenIndex = player.getEquipment().getHiddenSlots();
			for (int index = 0; index < 4; index++) {
				Item item = player.getEquipment().getItems().get(index);
				if (glowRed) {
					if (index == 0) {
						stream.writeShort(0x4000 + 2910);
						continue;
					}
					if (index == 1) {
						stream.writeShort(0x4000 + 14641);
						continue;
					}
				}
				
				if (item == null) {
					if(cosmetics[index] != null)
						stream.writeShort(16384 + cosmetics[index].getId());
					else
						stream.writeByte(0);
				} else {
					if(cosmetics[index] != null)
						stream.writeShort(16384 + cosmetics[index].getId());
					else
						stream.writeShort(16384 + item.getId());
				}
			}
		
			
			item = player.getEquipment().getItems().get(Equipment.SLOT_CHEST);
			if(item == null){
				stream.writeShort(0x100 + lookI[2]);
			} else {
				if(cosmetics[Equipment.SLOT_CHEST] != null && player.getEquipment().isCanDisplayCosmetic())
					stream.writeShort(0x4000 + cosmetics[Equipment.SLOT_CHEST].getId());
				else
				stream.writeShort(0x4000 + item.getId());
			}
			///stream.writeShort(item == null ? 0x100 + lookI[2] : 0x4000 + item.getId());
			item = player.getEquipment().getItems().get(Equipment.SLOT_SHIELD);
			if (item == null) {
				stream.writeByte(0);
			} else {
				if(cosmetics[Equipment.SLOT_SHIELD] != null && player.getEquipment().isCanDisplayCosmetic())
					stream.writeShort(0x4000 + cosmetics[Equipment.SLOT_SHIELD].getId());
				else
				stream.writeShort(0x4000 + item.getId());
			}
			item = player.getEquipment().getItems().get(Equipment.SLOT_CHEST);
			if (item == null || !Equipment.hideArms(item)) {
				//if(cosmetics[3] != null){
				//	stream.writeShort(0x100 + cosmetics[3].getId());
				//} else 
					stream.writeShort(0x100 + lookI[3]);
			} else {
			
				stream.writeByte(0);
			}
			item = player.getEquipment().getItems().get(Equipment.SLOT_LEGS);
			if(item == null){
				if(glowRed)
					stream.writeShort(0x4000 + 2908);
				else
					stream.writeShort(0x100 + lookI[5]);
				
				 
			} else {
				if(glowRed)
					stream.writeShort(0x4000 + 2908);
				else
					stream.writeShort(0x4000 + item.getId());
				
			}
			//stream.writeShort(glowRed ? 0x4000 + 2908 : item == null ? 0x100 + lookI[5] : 0x4000 + item.getId());
			item = player.getEquipment().getItems().get(Equipment.SLOT_HAT);
			if (!glowRed && (item == null || !Equipment.hideHair(item))) {
				stream.writeShort(0x100 + lookI[0]);
			} else {
				stream.writeByte(0);
			}
			item = player.getEquipment().getItems().get(Equipment.SLOT_HANDS);
			stream.writeShort(glowRed ? 0x4000 + 2912 : item == null ? 0x100 + lookI[4] : 0x4000 + item.getId());
			item = player.getEquipment().getItems().get(Equipment.SLOT_FEET);
			stream.writeShort(glowRed ? 0x4000 + 2904 : item == null ? 0x100 + lookI[6] : 0x4000 + item.getId());
			item = player.getEquipment().getItems().get(male ? Equipment.SLOT_HAT : Equipment.SLOT_CHEST);
			if (item == null || male && Equipment.showBear(item)) {
				stream.writeShort(0x100 + lookI[1]);
			} else {
				stream.writeByte(0);
			}
			item = player.getEquipment().getItems().get(Equipment.SLOT_AURA);
			if (item == null) {
				stream.writeByte(0);
			} else {
				stream.writeShort(0x4000 + item.getId());
			}
			stream.writeByte(0);
			stream.writeByte(0);
			stream.writeByte(0);
			int pos = stream.getOffset();
			stream.writeShort(0);
			int hash = 0;
			int slotFlag = -1;
			for (int slotId = 0; slotId < player.getEquipment().getItems().getSize(); slotId++) {
				if (Equipment.DISABLED_SLOTS[slotId] != 0) {
					continue;
				}
				slotFlag++;
				if (slotId == Equipment.SLOT_HAT) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == 20768 || hatId == 20770 || hatId == 20772) {
						ItemDefinitions defs = ItemDefinitions.getItemDefinitions(hatId - 1);
						if (hatId == 20768 && Arrays.equals(player.getMaxedCapeCustomized(), defs.originalModelColors) || (hatId == 20770 || hatId == 20772) && Arrays.equals(player.getCompletionistCapeCustomized(), defs.originalModelColors)) {
							continue;
						}
						hash |= 1 << slotFlag;
						stream.writeByte(0x4); // modify 4 model colors
						int[] hat = hatId == 20768 ? player.getMaxedCapeCustomized() : player.getCompletionistCapeCustomized();
						int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
						stream.writeShort(slots);
						for (int i = 0; i < 4; i++) {
							stream.writeShort(hat[i]);
						}
					}
				} else if (slotId == Equipment.SLOT_WEAPON) {
					int weaponId = player.getEquipment().getWeaponId();
					if (weaponId == 20709) {
						ClansManager manager = player.getClanManager();
						if (manager == null) {
							continue;
						}
						int[] colors = manager.getClan().getMottifColors();
						ItemDefinitions defs = ItemDefinitions.getItemDefinitions(20709);
						boolean modifyColor = !Arrays.equals(colors, defs.originalModelColors);
						int bottom = manager.getClan().getMottifBottom();
						int top = manager.getClan().getMottifTop();
						if (bottom == 0 && top == 0 && !modifyColor) {
							continue;
						}
						hash |= 1 << slotFlag;
						stream.writeByte((modifyColor ? 0x4 : 0) | (bottom != 0 || top != 0 ? 0x8 : 0));
						if (modifyColor) {
							int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
							stream.writeShort(slots);
							for (int i = 0; i < 4; i++) {
								stream.writeShort(colors[i]);
							}
						}
						if (bottom != 0 || top != 0) {
							int slots = 0 | 1 << 4;
							stream.writeByte(slots);
							stream.writeShort(ClansManager.getMottifTexture(top));
							stream.writeShort(ClansManager.getMottifTexture(bottom));
						}

					}
				} else if (slotId == Equipment.SLOT_CAPE) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767 || capeId == 20769 || capeId == 20771 || capeId == 32152 || capeId == 32153) {
						ItemDefinitions defs = ItemDefinitions.getItemDefinitions(capeId);
						if (capeId == 20767 && Arrays.equals(player.getMaxedCapeCustomized(), defs.originalModelColors) || (capeId == 20769 || capeId == 20771 || capeId == 32152 || capeId == 32153) && Arrays.equals(player.getCompletionistCapeCustomized(), defs.originalModelColors)) {
							continue;
						}
						hash |= 1 << slotFlag;
						stream.writeByte(0x4); // modify 4 model colors
						int[] cape = capeId == 20767 ? player.getMaxedCapeCustomized() : player.getCompletionistCapeCustomized();
						int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
						stream.writeShort(slots);
						for (int i = 0; i < 4; i++) {
							stream.writeShort(cape[i]);
						}
					} else if (capeId == 20708) {
						ClansManager manager = player.getClanManager();
						if (manager == null) {
							continue;
						}
						int[] colors = manager.getClan().getMottifColors();
						ItemDefinitions defs = ItemDefinitions.getItemDefinitions(20709);
						boolean modifyColor = !Arrays.equals(colors, defs.originalModelColors);
						int bottom = manager.getClan().getMottifBottom();
						int top = manager.getClan().getMottifTop();
						if (bottom == 0 && top == 0 && !modifyColor) {
							continue;
						}
						hash |= 1 << slotFlag;
						stream.writeByte((modifyColor ? 0x4 : 0) | (bottom != 0 || top != 0 ? 0x8 : 0));
						if (modifyColor) {
							int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
							stream.writeShort(slots);
							for (int i = 0; i < 4; i++) {
								stream.writeShort(colors[i]);
							}
						}
						if (bottom != 0 || top != 0) {
							int slots = 0 | 1 << 4;
							stream.writeByte(slots);
							stream.writeShort(ClansManager.getMottifTexture(top));
							stream.writeShort(ClansManager.getMottifTexture(bottom));
						}

					}
				} else if (slotId == Equipment.SLOT_AURA) {
					int auraId = player.getEquipment().getAuraId();
					if (auraId == -1 || !player.getAuraManager().isActivated()) {
						continue;
					}
					ItemDefinitions auraDefs = ItemDefinitions.getItemDefinitions(auraId);
					if (auraDefs.getMaleWornModelId1() == -1 || auraDefs.getFemaleWornModelId1() == -1) {
						continue;
					}
					hash |= 1 << slotFlag;
					stream.writeByte(0x1); // modify model ids
					int modelId = player.getAuraManager().getAuraModelId();
					stream.writeBigSmart(modelId); // male modelid1
					stream.writeBigSmart(modelId); // female modelid1
					if (auraDefs.getMaleWornModelId2() != -1 || auraDefs.getFemaleWornModelId2() != -1) {
						int modelId2 = player.getAuraManager().getAuraModelId2();
						stream.writeBigSmart(modelId2);
						stream.writeBigSmart(modelId2);
					}
				}
			}
			int pos2 = stream.getOffset();
			stream.setOffset(pos);
			stream.writeShort(hash);
			stream.setOffset(pos2);
		}

		for (byte element : colour) {
			// colour length 10
			stream.writeByte(element);
		}

		stream.writeShort(getRenderEmote());
		stream.writeString(player.getDisplayName());
		boolean pvpArea = World.isPvpArea(player);
		stream.writeByte(pvpArea ? player.getSkills().getCombatLevel() : player.getSkills().getCombatLevelWithSummoning());
		stream.writeByte(pvpArea ? player.getSkills().getCombatLevelWithSummoning() : 0);
		stream.writeByte(-1); // higher level acc name appears in front :P
		stream.writeByte(transformedNpcId >= 0 ? 1 : 0); // to end here else id
		// need to send more
		// data
		if (transformedNpcId >= 0) {
			NPCDefinitions defs = NPCDefinitions.getNPCDefinitions(transformedNpcId);
			stream.writeShort(defs.anInt876);
			stream.writeShort(defs.anInt842);
			stream.writeShort(defs.anInt884);
			stream.writeShort(defs.anInt875);
			stream.writeByte(defs.anInt875);
		}

		stream.writeByte(0);

		// done separated for safe because of synchronization
		byte[] appeareanceData = new byte[stream.getOffset()];
		System.arraycopy(stream.getBuffer(), 0, appeareanceData, 0, appeareanceData.length);
		byte[] md5Hash = Utils.encryptUsingMD5(appeareanceData);
		this.appeareanceData = appeareanceData;
		md5AppeareanceDataHash = md5Hash;
	}

	private ItemModify[] generateItemModify(Item[] items, Item[] cosmetics) {
		ItemModify[] modify = new ItemModify[15];
		for (int slotId = 0; slotId < modify.length; slotId++) {
		/*	if ((slotId == Equipment.SLOT_WEAPON || slotId == Equipment.SLOT_SHIELD) && player.getEquipment().getCosmeticItems().getItems() == cosmetics) {
				Item item = items[slotId];
				if (item != null) {
					int modelId = items[slotId].getDefinitions().getSheatheModelId();
					setItemModifyModel(items[slotId], slotId, modify, modelId, modelId, -1, -1, -1, -1);
				}
			} */
			if (items[slotId] != null && items[slotId] == cosmetics[slotId]) {
				int id = cosmetics[slotId] == null ? -1 : cosmetics[slotId].getId();
				if (id == 32152 || id == 32153 || id == 20768 || id == 20770 || id == 20772 ||id == 32151|| id == 20767 || id == 20769 || id == 20771)
					setItemModifyColor(items[slotId], slotId, modify, id == 32151 || id == 20768 || id == 20767 ? player.getMaxedCapeCustomized() : player.getCompletionistCapeCustomized());
				else {
					int[] colors = new int[4];
					colors[0] = player.getEquipment().getCostumeColor();
					colors[1] = colors[0] + 12;
					colors[2] = colors[1] + 12;
					colors[3] = colors[2] + 12;
					setItemModifyColor(items[slotId], slotId, modify, colors);
				}
			} else {
				int id = items[slotId] == null ? -1 : items[slotId].getId();
				if (id == 32152 || id == 32153 ||id == 32151|| id == 20768 || id == 20770 || id == 20772 || id == 20767 || id == 20769 || id == 20771)
					setItemModifyColor(items[slotId], slotId, modify, id == 32151 || id == 20768 || id == 20767 ? player.getMaxedCapeCustomized() : player.getCompletionistCapeCustomized());
				else if (id == 20708 || id == 20709) {
					ClansManager manager = player.getClanManager();
					if (manager == null)
						continue;
					int[] colors = manager.getClan().getMottifColors();
					setItemModifyColor(items[slotId], slotId, modify, colors);
					setItemModifyTexture(items[slotId], slotId, modify, new short[] { (short) ClansManager.getMottifTexture(manager.getClan().getMottifTop()), (short) ClansManager.getMottifTexture(manager.getClan().getMottifBottom()) });
				} else if (player.getAuraManager().isActivated() && slotId == Equipment.SLOT_AURA) {
					int auraId = player.getEquipment().getAuraId();
					if (auraId == -1)
						continue;
					int modelId = player.getAuraManager().getAuraModelId();
					int modelId2 = player.getAuraManager().getAuraModelId2();
					setItemModifyModel(items[slotId], slotId, modify, modelId, modelId, modelId2, modelId2, -1, -1);
				}
			}
		}
		return modify;
	}

	private void setItemModifyModel(Item item, int slotId, ItemModify[] modify, int maleModelId1, int femaleModelId1, int maleModelId2, int femaleModelId2, int maleModelId3, int femaleModelId3) {
		if (item == null)
			return;
		ItemDefinitions defs = item.getDefinitions();
		if (defs.getMaleWornModelId1() == -1 || defs.getFemaleWornModelId1() == -1)
			return;
		if (modify[slotId] == null)
			modify[slotId] = new ItemModify();
		modify[slotId].maleModelId1 = maleModelId1;
		modify[slotId].femaleModelId1 = femaleModelId1;
		if (defs.getMaleWornModelId2() != -1 || defs.getFemaleWornModelId2() != -1) {
			modify[slotId].maleModelId2 = maleModelId2;
			modify[slotId].femaleModelId2 = femaleModelId2;
		}
		if (defs.getMaleWornModelId3() != -1 || defs.getFemaleWornModelId3() != -1) {
			modify[slotId].maleModelId2 = maleModelId3;
			modify[slotId].femaleModelId2 = femaleModelId3;
		}
	}

	private void setItemModifyTexture(Item item, int slotId, ItemModify[] modify, short[] textures) {
		ItemDefinitions defs = item.getDefinitions();
		if (defs.originalTextureColors == null || defs.originalTextureColors.length != textures.length)
			return;
		if (Arrays.equals(textures, defs.originalTextureColors))
			return;
		if (modify[slotId] == null)
			modify[slotId] = new ItemModify();
		modify[slotId].textures = textures;
	}

	private void setItemModifyColor(Item item, int slotId, ItemModify[] modify, int[] colors) {
		ItemDefinitions defs = item.getDefinitions();
		if (defs.originalModelColors == null || defs.originalModelColors.length != colors.length)
			return;
		if (Arrays.equals(colors, defs.originalModelColors))
			return;
		if (modify[slotId] == null)
			modify[slotId] = new ItemModify();
		modify[slotId].colors = colors;
	}

	private static class ItemModify {

		private int[] colors;
		private short[] textures;
		private int maleModelId1;
		private int femaleModelId1;
		private int maleModelId2;
		private int femaleModelId2;
		private int maleModelId3;
		private int femaleModelId3;

		private ItemModify() {
			maleModelId1 = femaleModelId1 = -1;
			maleModelId2 = femaleModelId2 = -2;
			maleModelId3 = femaleModelId3 = -2;
		}
	}

	/**
	 * The player's body looks.
	 */
	private int[] bodyStyle;
	/**
	 * The cosmetic items
	 */
	private Item[] cosmeticItems;
	/**
	 * The player's body color
	 */
	private byte[] bodyColors;
	/**
	 * The appearance block
	 */
	private transient byte[] appearanceBlock;
	/**
	 * The encyrpted appearance block
	 */
	private transient byte[] encyrptedAppearanceBlock;
	/**
	 * The NPC the player is transformed into
	 */
	@SuppressWarnings("unused")
	private transient short asNPC;
	/**
	 * If we should show the player's skill level rather then combat level
	 */
	private boolean showSkillLevel;

	private Item item;

	/**
	 * Loads this player's appearance to a buffer and is sent to the client
	 * within a packet containing information on how this player should be
	 * viewed as graphically
	 */

	/**
	 * Writes the player's equipment appearance
	 *
	 * @param stream
	 *            The stream to write data on
	 */

	/**
	 * Resets the appearance flags
	 */
	public void resetAppearance() {
		bodyStyle = new int[7];
		bodyColors = new byte[10];
		if (cosmeticItems == null)
			cosmeticItems = new Item[14];
		setMale();
	}

	public int getSize() {
		if (transformedNpcId >= 0)
			return NPCDefinitions.getNPCDefinitions(transformedNpcId).size;
		return 1;
	}

	public int getTransformedNpcId() {
		return transformedNpcId;
	}

	public void setRenderEmote(int id) {
		// if (id == -1)
		// id = isMale() ? 2699 : 2703;
		this.renderEmote = id;
		generateAppearenceData();
	}

	public int getRenderEmote() {
		String weaponName = ItemDefinitions.getItemDefinitions(player.getEquipment().getWeaponId()).getName().toLowerCase();
		if (renderEmote >= 0)
			return renderEmote;
		if (transformedNpcId >= 0)
			return NPCDefinitions.getNPCDefinitions(transformedNpcId).renderEmote;
		if (!player.getCombatDefinitions().isCombatStance() && transformedNpcId < 0) {
			if (weaponName.contains("2h") || weaponName.contains("godsword") || weaponName.contains("saradomin sword"))
				return 2695;// hold 2h weapons
			else if (weaponName.contains("staff") || weaponName.contains("noxious scythe"))
				return 2699;
			else if (player.getEquipment().getWeaponId() == 4084)
				return 1119;
			else
				return 2912;
			//return 2699; //default;
		}
		return player.getEquipment().getWeaponStance();
	}
	/*public int getRenderEmote() {
		Item[] cosmetics = player.getEquipment().getCosmeticItems().getItems();
		String weaponName = ItemDefinitions.getItemDefinitions(player.getEquipment().getWeaponId()).getName().toLowerCase();

		if (renderEmote >= 0)
			return renderEmote;
		if (transformedNpcId >= 0)
			return NPCDefinitions.getNPCDefinitions(transformedNpcId).renderEmote;
		/*if(player.isSheathing() && !player.isUnderCombat() && !(player.getActionManager().getAction() instanceof PlayerCombat))
			return 2699;
		if ((cosmetics[Equipment.SLOT_WEAPON] != null && player.getEquipment().isCanDisplayCosmetic())) {
			return player.getEquipment().getWeaponRenderEmote();
		} else {
			if (weaponName.contains("2h") || weaponName.contains("godsword") || weaponName.contains("saradomin sword"))
				return 2695;// hold 2h weapons
			else if (weaponName.contains("staff") || weaponName.contains("noxious scythe"))
				return 2699;
			else if (player.getEquipment().getWeaponId() == 4084)
				return 1119;
			else
				return 2912;
		}
		
		 * if (renderEmote >= 0) return renderEmote; if (transformedNpcId >= 0)
		 * return
		 * NPCDefinitions.getNPCDefinitions(transformedNpcId).renderEmote;
		 * return player.getEquipment().getWeaponRenderEmote();
		 
	}*/

	public void resetAppearence() {
		lookI = new int[7];
		colour = new byte[10];
		male();
	}

	public int[] getLookI() {
		return lookI;
	}

	public byte[] getColour() {
		return colour;
	}

	public void male() {
		lookI[0] = 3; // Hair
		lookI[1] = 14; // Beard
		lookI[2] = 18; // Torso
		lookI[3] = 26; // Arms
		lookI[4] = 34; // Bracelets
		lookI[5] = 38; // Legs
		lookI[6] = 42; // Shoes

		colour[2] = 16;
		colour[1] = 16;
		colour[0] = 3;
		male = true;
	}

	public void female() {
		lookI[0] = 48; // Hair
		lookI[1] = 57; // Beard
		lookI[2] = 57; // Torso
		lookI[3] = 65; // Arms
		lookI[4] = 68; // Bracelets
		lookI[5] = 77; // Legs
		lookI[6] = 80; // Shoes

		colour[2] = 16;
		colour[1] = 16;
		colour[0] = 3;
		male = false;
	}

	public byte[] getAppeareanceData() {
		return appeareanceData;
	}

	public byte[] getMD5AppeareanceDataHash() {
		return md5AppeareanceDataHash;
	}

	public boolean isMale() {
		return male;
	}

	public void setLook(int i, int i2) {
		lookI[i] = i2;
	}

	public void setColor(int i, int i2) {
		colour[i] = (byte) i2;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public void setHairStyle(int i) {
		lookI[0] = i;
	}

	public void setTopStyle(int i) {
		lookI[2] = i;
	}

	public int getTopStyle() {
		return lookI[2];
	}

	public void setArmsStyle(int i) {
		lookI[3] = i;
	}

	public void setWristsStyle(int i) {
		lookI[4] = i;
	}

	public void setLegsStyle(int i) {
		lookI[5] = i;
	}

	public int getHairStyle() {
		return lookI[0];
	}

	public void setBeardStyle(int i) {
		lookI[1] = i;
	}

	public int getBeardStyle() {
		return lookI[1];
	}

	public void setFacialHair(int i) {
		lookI[1] = i;
	}

	public int getFacialHair() {
		return lookI[1];
	}

	public void setSkinColor(int color) {
		colour[4] = (byte) color;
	}

	public int getSkinColor() {
		return colour[4];
	}

	public void setHairColor(int color) {
		colour[0] = (byte) color;
	}

	public void setTopColor(int color) {
		colour[1] = (byte) color;
	}

	public void setLegsColor(int color) {
		colour[2] = (byte) color;
	}

	public int getHairColor() {
		return colour[0];
	}

	public void setTitle(int title) {
		if (title < 0)
			title = 1;
		this.title = title;
		generateAppearenceData();
	}

	public void setFixTitle(int title) {
		if (title <= 0)
			title = 1;
		this.title = title;
	}

	public void setMale() {
		bodyStyle[0] = 3;
		bodyStyle[1] = 14;
		bodyStyle[2] = 18;
		bodyStyle[3] = 26;
		bodyStyle[4] = 34;
		bodyStyle[5] = 38;
		bodyStyle[6] = 42;

		bodyColors[2] = 16;
		bodyColors[1] = 16;
		bodyColors[0] = 3;
		male = true;
	}

	/**
	 * Returns the loaded appearance block
	 *
	 * @return The appearance block
	 */
	public byte[] getAppearanceBlock() {
		return appearanceBlock;
	}

	/**
	 * Returns the loaded encrypted appearance block
	 *
	 * @return The encrypted appearance block
	 */
	public byte[] getEncryptedAppearanceBlock() {
		return encyrptedAppearanceBlock;
	}

	/**
	 * Sets the player's body style
	 *
	 * @param i
	 *            The slot
	 * @param i2
	 *            The style
	 */
	public void setBodyStyle(int i, int i2) {
		bodyStyle[i] = i2;
	}

	/**
	 * Sets the player's body color
	 *
	 * @param i
	 *            The slot
	 * @param i2
	 *            The color
	 */
	public void setBodyColor(int i, int i2) {
		bodyColors[i] = (byte) i2;
	}

	/**
	 * Sets the player's leg style
	 *
	 * @param i
	 *            The style to set
	 */
	public void setShoeStyle(int i) {
		bodyStyle[6] = i;
	}

	/**
	 * Sets a specified slot as cosmetic
	 *
	 * @param item
	 *            The cosmetic item
	 * @param slot
	 *            The slot to set
	 */
	public void setCosmetic(Item item, int slot) {
		cosmeticItems[slot] = item;
	}

	/**
	 * Returns the cosmetic item corresponding to the specified slot
	 *
	 * @param slot
	 *            The slot to get
	 * @return The cosmetic item
	 */
	public Item getCosmeticItem(int slot) {
		return cosmeticItems[slot];
	}

	public void setLooks(short[] look) {
		for (byte i = 0; i < this.bodyStyle.length; i = (byte) (i + 1))
			if (look[i] != -1)
				this.bodyStyle[i] = look[i];
	}

	public void copyColors(short[] colors) {
		for (byte i = 0; i < this.bodyColors.length; i = (byte) (i + 1))
			if (colors[i] != -1)
				this.bodyColors[i] = (byte) colors[i];
	}

	public void print() {
		for (int i = 0; i < bodyStyle.length; i++) {
			System.out.println("look[" + i + " ] = " + bodyStyle[i] + ";");
			Logger.logMessage("look[" + i + " ] = " + bodyStyle[i] + ";");
		}
		for (int i = 0; i < bodyColors.length; i++) {
			System.out.println("colour[" + i + " ] = " + bodyColors[i] + ";");
			Logger.logMessage("colour[" + i + " ] = " + bodyColors[i] + ";");
		}
	}

	/**
	 * Toggles showing skills levels.
	 */

	/**
	 * If we are showing the skill level as apposed to the combat level
	 *
	 * @return True if so; false otherwise
	 */
	public boolean isShowSkillLevel() {
		return showSkillLevel;
	}

	/**
	 * Sets if we should show the skill level
	 *
	 * @param showSkillLevel
	 *            If we should show the skill level
	 */
	public void setShowSkillLevel(boolean showSkillLevel) {
		this.showSkillLevel = showSkillLevel;
	}

	/**
	 * Retruns the title
	 *
	 * @return The title
	 */
	public int getTitle() {
		return title;
	}

	public void writeClanVexilliumData(OutputStream stream) {
		int flag = 0;
		flag |= 0x4; // modify colors
		flag |= 0x8; // modify textures
		stream.writeByte(flag);

		int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
		stream.writeShort(slots);

		for (int i = 0; i < 4; i++) {
			stream.writeShort(player.getClanCapeCustomized()[i]);
		}

		slots = 0 | 1 << 4;
		stream.writeByte(slots);

		for (int i = 0; i < 2; i++) {
			stream.writeShort(player.getClanCapeSymbols()[i]);
		}
	}

	public void writeClanCapeData(OutputStream stream) {
		int flag = 0;
		flag |= 0x4; // modify colors
		flag |= 0x8; // modify textures
		stream.writeByte(flag);

		int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
		stream.writeShort(slots);

		for (int i = 0; i < 4; i++) {
			stream.writeShort(player.getClanCapeCustomized()[i]);
		}

		slots = 0 | 1 << 4;
		stream.writeByte(slots);

		for (int i = 0; i < 2; i++) {
			stream.writeShort(player.getClanCapeSymbols()[i]);
		}
	}
}