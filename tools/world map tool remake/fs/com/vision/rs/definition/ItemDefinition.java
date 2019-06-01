package com.vision.rs.definition;

import java.util.HashMap;

import com.vision.rs.Definition;
import com.vision.rs.io.RSBuffer;
import com.vision.rs.loader.EnumLoader;

/**
 * Represents an item definition.
 * 
 * @author Teemu Uusitalo
 */
public class ItemDefinition extends Definition {
	private HashMap<Integer, Object> scriptData;
	private String[] inventoryOptions;
	private String[] groundOptions;

	private boolean stackable;
	private boolean members;
	private String name;

	private int noteTemplate = -1;
	private int nonNoted = -1;

	private int lendTemplate = -1;
	private int unlent = -1;

	private int equipIndex = -1;
	private int maleModel1 = -1;
	private int maleModel2 = -1;

	private boolean canExchange = false;
	private int price = 0;
	private int equipSlot = -1;
	private int equipmentFlags;

	public ItemDefinition() {
		groundOptions = new String[5];
		inventoryOptions = new String[5];
		groundOptions[2] = "Take";
		inventoryOptions[4] = "Drop";
		stackable = false;
		members = false;
	}

	public void setMembers() {
		groundOptions = new String[5];
		inventoryOptions = new String[5];
		groundOptions[2] = "Take";
		inventoryOptions[4] = "Drop";
		canExchange = false;
	}

	public void setNoted(ItemDefinition base) {
		stackable = true;
		name = base.name;
		members = base.members;
		price = base.price;
	}

	public void setLent(ItemDefinition base) {
		groundOptions = base.groundOptions;
		inventoryOptions = new String[5];
		inventoryOptions[4] = "Discard";

		scriptData = base.scriptData;
		maleModel1 = base.maleModel1;
		maleModel2 = base.maleModel2;

		members = base.members;
		name = base.name;
		price = 0;

		for (int i = 0; i < 4; i++) {
			inventoryOptions[i] = base.inventoryOptions[i];
		}
	}

	@Override
	protected void parseOpcode(int opcode, RSBuffer data) {
		if (opcode >= 30 && opcode < 35) {
			groundOptions[opcode - 30] = data.getJagString();
		} else if (opcode >= 35 && opcode < 40) {
			inventoryOptions[opcode - 35] = data.getJagString();
		} else if (opcode >= 100 && opcode < 110) {
			data.getUShort();// Stack def ID
			data.getUShort();// Stack count for new id
		} else {
			switch (opcode) {
			case 1:
				data.getShort();// Model?
				break;
			case 2:
				name = data.getJagString();
				break;
			case 3:// Was item description!
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				data.getShort();
				break;
			case 11:
				stackable = true;
				break;
			case 12:
				price = data.getUInt();
				break;
			case 16:
				members = true;
				break;
			case 23:// Male model 1
				maleModel1 = data.getUShort();
				break;
			case 25:// Male model 2
				maleModel2 = data.getUShort();
				break;
			case 24:// Female model 1??
			case 26:// Female model 2??
				data.getShort();
				break;
			case 40:
			case 41:
				int skipLen = data.getU();
				for (int i = 0; i < skipLen; i++) {
					data.getShort();
					data.getShort();
				}
				break;
			case 42:
				skipLen = data.getU();
				for (int i = 0; i < skipLen; i++) {
					data.getU();
				}
				break;
			case 65:
				canExchange = true;
				break;
			case 78:
			case 79:
			case 90:
			case 91:
			case 92:
			case 93:
			case 94:
			case 95:
				data.getShort();
				break;
			case 96:
				data.getU();
				break;
			case 97:
				nonNoted = data.getUShort();
				break;
			case 98:
				noteTemplate = data.getUShort();
				break;
			case 110:
			case 111:
			case 112:
				data.getShort();
				break;
			case 113:
			case 114:
			case 115:
				data.get();
				break;
			case 121:// Unlent item ID
				unlent = data.getUShort();
				break;
			case 122:// Lend template ID
				lendTemplate = data.getUShort();
				break;
			case 125:
			case 126:
			case 127:
			case 128:
			case 129:
			case 130:
				data.get24BitInt();
				break;
			case 132:
				skipLen = data.getU();
				for (int i = 0; i < skipLen; i++) {
					data.getShort();
				}
				break;
			case 249:
				int entryCount = data.getU();
				scriptData = new HashMap<>(entryCount);
				for (int ix = 0; ix < entryCount; ix++) {
					boolean str = data.getU() == 1;
					int scriptVarId = data.get24BitUInt();
					if (str) {
						scriptData.put(scriptVarId, data.getJagString());
					} else {
						scriptData.put(scriptVarId, data.getUInt());
					}
				}
				break;
			default:
				System.out.println("Unhandled opcode! " + opcode);
				break;
			}
		}
	}

	public int getEquipSlot() {
		return equipSlot;
	}

	public void afterParse() {
		setEquipSlot();
		/**
		 * Load more data here... :)
		 */
	}

	private void setEquipSlot() {
		if (this.name != null) {
			String name = this.name.toLowerCase();
			if (name.contains("ring") || name.contains("brace")) {
				equipSlot = 12;
				return;
			}
			if (name.contains("bolt") || name.contains("arrow")) {
				equipSlot = 13;
				return;
			}
			if (maleModel1 != -1 && maleModel2 != -1) {
				if (scriptData != null) {
					if (scriptData.get(EnumLoader.WEAPON_TYPE) != null) {
						switch ((Integer) scriptData
								.get(EnumLoader.WEAPON_TYPE)) {
						case 7:
						case 9:
						case 21:
							equipmentFlags = 0x1;
							break;
						}
						equipSlot = 3;
						return;
					}
				}
				if (name.contains("(u)") || name.contains("broken")
						|| name.contains("picture") || name.contains("stock")
						|| name.contains("limbs") || name.contains(" lvl ")
						|| name.contains("skullball")
						|| name.contains("fungicide spray")
						|| name.contains("logs")
						|| name.contains("destabiliser")
						|| name.contains("sketch") || name.contains("bucket")
						|| name.contains("lyre") || name.contains("horn")) {
					return;
				}
				if (name.contains("sickle") || name.contains("attractor")
						|| name.contains("repeller") || name.contains("staff")
						|| name.contains("barrier generator")
						|| name.contains("locator")
						|| name.contains("lily of the valley")
						|| name.contains("scythe")
						|| name.contains("ghost buster 500")
						|| name.contains("clay harpoon")
						|| name.contains("clay hammer")
						|| name.contains("clay needle")
						|| name.contains("fletching knife")) {
					equipSlot = 3;
					return;
				}
				if (name.contains("shield") || name.contains("book")
						|| name.contains("bug lantern")
						|| name.contains("defender")
						|| name.equals("toktz-ket-xil")
						|| name.equals("stone of power")
						|| name.contains("toy kite") || name.contains("wand")
						|| name.contains("satchel")) {
					equipSlot = 5;
					return;
				}
				if (name.contains("cape") || name.contains("cloak")
						|| name.contains("accumulator")
						|| name.contains("orb of oculus")) {
					equipSlot = 1;
					return;
				}
				if (name.contains("amulet") || name.contains("pendant")
						|| name.contains("void seal")
						|| name.contains("necklace") || name.contains("symbol")
						|| name.contains("logo") || name.contains("stole")
						|| name.contains("scarf")
						|| name.equals("beads of the dead")) {
					equipSlot = 2;
					return;
				}
				if (name.contains("legs") || name.contains("skirt")
						|| name.contains("chaps") || name.contains("robe")
						| name.contains("leggings") || name.contains("bottom")
						|| name.contains("trousers") || name.contains("shorts")
						|| name.contains("slacks") || name.contains("tasset")
						|| name.contains("pant") || name.contains("kilt")
						|| name.contains("tights") || name.contains("cuisse")) {
					equipSlot = 7;
					return;
				}
				if (name.contains("body") || name.contains("top")
						|| name.contains("shirt") || name.contains("apron")
						|| name.contains("armour") || name.contains("jacket")
						|| name.contains("plate") || name.contains("hauberk")
						|| name.contains("blouse") || name.contains("brassard")) {
					equipSlot = 4;
					if (name.contains("plate") || name.contains("dragon chain")
							|| name.contains("hauberk")) {
						equipmentFlags |= 0x1;
					}
					return;
				}
				if (name.contains("gloves") || name.contains("vambraces")
						|| name.contains("gauntlets")) {
					equipSlot = 9;
					return;
				}
				if (name.contains("helm") || name.contains("coif")
						|| name.contains("afro") || name.contains("hat")
						|| name.contains("boater") || name.contains("head")
						|| name.contains("crown") || name.contains("hood")
						|| name.contains("cap") || name.contains("wig")
						|| name.contains("mask") || name.contains("eyepatch")
						|| name.contains("tiara") || name.contains("bandana")
						|| name.contains("headdress") || name.contains("mitre")
						|| name.contains("cavalier") || name.contains("beret")
						|| name.contains("sallet") || name.contains("goggles")
						|| name.contains("ears") || name.contains("snelm")
						|| name.contains("lizard skull")
						|| name.contains("antlers")) {
					equipSlot = 0;
					if (name.contains("full helm")) {
						equipmentFlags |= 0x2;
					}
					if (name.contains("helm") || name.contains("afro")
							|| name.contains("coif")) {
						equipmentFlags |= 0x1;
					}
					return;
				}
				if (name.contains("boots") || name.contains("sandals")
						|| name.contains("shoes")) {
					equipSlot = 10;
					return;
				}
			}
		}
		equipSlot = -1;
	}

	public String getDescription() {
		if (name != null) {
			char chr = name.toLowerCase().charAt(0);
			switch (chr) {
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
			case 'y':
				return "An " + name + ".";
			default:
				return "A " + name + ".";
			}
		}
		return "An item.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + " (" + getDefinitionId() + ")";
	}

	/**
	 * Removes the script variables.
	 */
	public void removeScriptVariables() {
		scriptData = null;
	}

	/**
	 * @return the equipIndex
	 */
	public int getEquipIndex() {
		return equipIndex;
	}

	/**
	 * @param equipIndex
	 *            the new equip index.
	 */
	public void setEquipIndex(int equipIndex) {
		this.equipIndex = equipIndex;
	}

	/**
	 * @return the name of this item.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return true, if this item is members only.
	 */
	public boolean isMembers() {
		return members;
	}

	/**
	 * @return true if this item can hold an amount bigger than one in an
	 *         regular inventory.
	 */
	public boolean isStackable() {
		return stackable;
	}

	/**
	 * @return the groundOptions
	 */
	public String[] getGroundOptions() {
		return groundOptions;
	}

	/**
	 * @return the inventoryOptions
	 */
	public String[] getInventoryOptions() {
		return inventoryOptions;
	}

	/**
	 * @return the scriptData
	 */
	public HashMap<Integer, Object> getScriptData() {
		return scriptData;
	}

	/**
	 * @return the actual (unnoted) item id.
	 */
	public int getNonNoted() {
		return nonNoted;
	}

	/**
	 * @return the note template id.
	 */
	public int getNoteTemplate() {
		return noteTemplate;
	}

	/**
	 * @return the unlent definition id.
	 */
	public int getUnlent() {
		return unlent;
	}

	/**
	 * @return the lend template id.
	 */
	public int getLendTemplate() {
		return lendTemplate;
	}

	public boolean canExchange() {
		return canExchange;
	}

	public int getPrice() {
		return price;
	}

	/**
	 * @return the maleModel1
	 */
	public int getMaleModel1() {
		return maleModel1;
	}

	/**
	 * @return the maleModel2
	 */
	public int getMaleModel2() {
		return maleModel2;
	}

	/**
	 * @return the appearance flags.
	 */
	public int getEquipmentFlags() {
		return equipmentFlags;
	}
}
