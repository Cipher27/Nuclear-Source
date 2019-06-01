package com.vision.rs.definition;

import java.util.Arrays;
import java.util.HashMap;

import com.vision.rs.Definition;
import com.vision.rs.io.RSBuffer;

public class CharacterDefinition extends Definition {
	private HashMap<Integer, Object> scriptData;
	private boolean[] optionMember = new boolean[5];
	private String[] clickOptions = new String[5];
	private String name;
	private int combatLevel;

	/**
	 * Variable definition ids.
	 */
	private int bitVariableId = -1;
	private int variableId = -1;
	private int[] variableDefinitionIds;

	private int size;
	private int movementFlags = 0;
	private int animGroupId = -1;
	private int defaultOrientation = 4;
	private int headIcon = -1;
	private boolean canOrientate;

	@Override
	protected void parseOpcode(int opcode, RSBuffer rsBuffer) {
		if (opcode >= 30 && opcode < 35) {
			clickOptions[opcode - 30] = rsBuffer.getJagString();
			return;
		} else if (opcode >= 150 && opcode < 155) {
			clickOptions[opcode - 150] = rsBuffer.getJagString();
			getOptionMember()[opcode - 150] = true;
			if (!loader.loadMemberData()) {
				clickOptions[opcode - 150] = null;
			}
			return;
		}
		switch (opcode) {
		case 1:// Models?
		case 60:
		case 160:
			int length = rsBuffer.getU();
			for (int i = 0; i < length; i++) {
				rsBuffer.getUShort();
			}
			break;
		case 2:
			name = rsBuffer.getJagString();
			break;
		case 3:
			System.out.println(
					"Description for npc " + name + " ID: " + getDefinitionId() + " : " + rsBuffer.getJagString());
			break;
		case 17:
			rsBuffer.getUShort();
			rsBuffer.getUShort();
			rsBuffer.getUShort();
			rsBuffer.getUShort();
			break;
		case 23:
		case 25:
			rsBuffer.getUShort();
			rsBuffer.getU();
			break;
		case 125:// Default orientation! g1
			setDefaultOrientation(rsBuffer.getU());
			break;
		case 19:
		case 100:
		case 101:
		case 128:
		case 140:
		case 163:
		case 165:
		case 168:
			rsBuffer.getU();
			break;
		case 119:
			movementFlags = rsBuffer.getU();
			break;
		case 12:
			size = rsBuffer.getU();
			break;
		case 40:
		case 41:// Unknown
			length = rsBuffer.getU();
			for (int i = 0; i < length; i++) {
				rsBuffer.getUShort();
				rsBuffer.getUShort();
			}
			break;
		case 42:
			length = rsBuffer.getU();
			for (int i = 0; i < length; i++) {
				rsBuffer.getU();
			}
			break;
		case 93:// Sets a boolean
		case 111:// IDK^
		case 99:// IDK ^
		case 109:// IDK^
		case 141:// IDK^
		case 143:// IDK^
		case 107:// Disable right-clicking.
			break;
		case 95:
			combatLevel = rsBuffer.getUShort();
			break;
		case 77:
			rsBuffer.getUShort();
			rsBuffer.getUShort();
			length = rsBuffer.getU();
			for (int i = 0; i <= length; i++) {
				rsBuffer.getUShort();
			}
			break;
		case 127:// Render group ID
			setAnimGroupId(rsBuffer.getUShort());
			break;
		case 103:// Orientation related. g2
			canOrientate = (rsBuffer.getUShort() << 3) != 0;
			break;
		case 102:// prayer headicon
			setHeadIcon(rsBuffer.getUShort());
			if (getHeadIcon() == 65535) {
				setHeadIcon(-1);
			}
			break;
		case 13:
		case 14:
		case 15:
		case 16:
		case 97:
		case 98:
		case 110:
		case 112:
		case 122:
		case 123:
		case 137:
		case 138:
		case 139:
		case 142:
		case 171:
		case 213:
		case 224:
			rsBuffer.getUShort();
			break;
		case 106:
		case 118:
			bitVariableId = rsBuffer.getUShort();
			if (bitVariableId == 65535) {
				bitVariableId = -1;
			}
			variableId = rsBuffer.getUShort();
			if (variableId == 65535) {
				variableId = -1;
			}
			int defaultDef = -1;
			if (opcode == 118) {
				defaultDef = rsBuffer.getUShort();
				if (defaultDef == 65535) {
					defaultDef = -1;
				}
			}
			int defc = rsBuffer.getU();
			variableDefinitionIds = new int[defc + 2];
			for (int dIx = 0; dIx <= defc; dIx++) {
				variableDefinitionIds[dIx] = rsBuffer.getUShort();
				if (variableDefinitionIds[dIx] == 65535) {
					variableDefinitionIds[dIx] = -1;
				}
			}
			variableDefinitionIds[defc + 1] = defaultDef;
			break;
		case 113:
		case 164:
			rsBuffer.getUShort();
			rsBuffer.getUShort();
			break;
		case 114:
		case 115:
			rsBuffer.getU();
			rsBuffer.getU();
			break;
		case 121:
			int len = rsBuffer.getU();
			for (int k3 = 0; len > k3; k3++) {
				rsBuffer.getU();// Id?
				int ai[] = new int[3];
				ai[0] = rsBuffer.get();
				ai[1] = rsBuffer.get();
				ai[2] = rsBuffer.get();
			}
			break;
		case 134:
			rsBuffer.getUShort();
			rsBuffer.getUShort();
			rsBuffer.getUShort();
			rsBuffer.getUShort();
			rsBuffer.getU();
			break;
		case 135:
		case 136:
			rsBuffer.getUShort();
			rsBuffer.getU();
			break;
		case 155:
			rsBuffer.getU();
			rsBuffer.getU();
			rsBuffer.getU();
			rsBuffer.getU();
			break;
		case 158:
		case 159:
		case 162:
			break;
		case 249:
			int entryCount = rsBuffer.getU();
			scriptData = new HashMap<>(entryCount);
			for (int ix = 0; ix < entryCount; ix++) {
				boolean str = rsBuffer.getU() == 1;
				int scriptVarId = rsBuffer.get24BitUInt();
				if (str) {
					scriptData.put(scriptVarId, rsBuffer.getJagString());
				} else {
					scriptData.put(scriptVarId, rsBuffer.getUInt());
				}
			}
			break;
		default:
			System.out.println("Panic!! Unknown opcode!! " + opcode);
			break;
		}
	}

	@Override
	public String toString() {
		String result = "CharacterDefinition " + getDefinitionId() + ": ";
		if (name != null)
			result += name;
		if (combatLevel != 0)
			result += "(lvl: " + combatLevel + ")";
		if (clickOptions != null) {
			for (int i = 0; i < clickOptions.length; i++) {
				if (clickOptions[i] != null) {
					result += " option " + i + ": " + clickOptions[i];
				}
			}
		}
		if (bitVariableId != -1) {
			result += " bitvarp: " + bitVariableId;
		}
		if (variableId != -1) {
			result += " varp: " + variableId;
		}
		if (variableDefinitionIds != null) {
			result += " VarDef: " + Arrays.toString(variableDefinitionIds);
		}
		return result;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the scriptData
	 */
	public HashMap<Integer, Object> getScriptData() {
		return scriptData;
	}

	/**
	 * @return the clickOptions
	 */
	public String[] getClickOptions() {
		return clickOptions;
	}

	/**
	 * @return the combatLevel
	 */
	public int getCombatLevel() {
		return combatLevel;
	}

	public int getSize() {
		return size;
	}

	public int getMovementFlags() {
		return movementFlags;
	}

	/**
	 * @return the bitVariableId
	 */
	public int getBitVariableId() {
		return bitVariableId;
	}

	/**
	 * @return the variableId
	 */
	public int getVariableId() {
		return variableId;
	}

	/**
	 * @return the variableDefinitionIds
	 */
	public int[] getVariableDefinitionIds() {
		return variableDefinitionIds;
	}

	public boolean[] getOptionMember() {
		return optionMember;
	}

	public int getAnimGroupId() {
		return animGroupId;
	}

	public void setAnimGroupId(int animGroupId) {
		this.animGroupId = animGroupId;
	}

	public int getDefaultOrientation() {
		if (!canOrientate) {
			return -1;
		}
		return defaultOrientation;
	}

	public void setDefaultOrientation(int defaultOrientation) {
		this.defaultOrientation = defaultOrientation;
	}

	public int getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(int headIcon) {
		this.headIcon = headIcon;
	}
}
