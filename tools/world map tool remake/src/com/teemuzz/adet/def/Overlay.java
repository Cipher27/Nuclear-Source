package com.teemuzz.adet.def;

import com.vision.rs.Definition;
import com.vision.rs.io.RSBuffer;

public class Overlay extends Definition {
	public int color = 0xAAaaAA;

	@Override
	protected void parseOpcode(int opcode, RSBuffer rsBuffer) {
		switch (opcode) {
		case 1:
			color = rsBuffer.get24BitUInt();
			break;
		case 5:
		case 10:
			break;
		case 12:
			break;
		case 2:
		case 11:
		case 14:
		case 16:
			rsBuffer.getU();
			break;
		case 3:
			rsBuffer.getUShort();
			break;
		case 8:
		case 15:
			rsBuffer.getUShort();
			break;
		case 7:
			rsBuffer.get24BitUInt();
			break;
		case 13:
			rsBuffer.get24BitUInt();
			break;
		default:
			break;
		}
		// 54 wildy volcano walls
		// 111 is water!
		// 131 mage arena wall?
		// color = 0;
		if (getDefinitionId() == 151) {// Cheaphaxing lava.
			color = 0xBB0000;
		}
		// } else
		// if (getDefinitionId() >= 30 || getDefinitionId() <= 145)
	}
}
