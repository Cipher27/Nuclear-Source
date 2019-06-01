package com.vision.rs.definition;

import java.util.HashMap;

import com.vision.rs.Definition;
import com.vision.rs.io.RSBuffer;

public class EnumDefinition extends Definition {
	private HashMap<Integer, Object> data;
	private String defaultString;
	private int defaultInteger;
	private int length;

	public EnumDefinition() {
		defaultString = "null";
	}

	public int getEntryCount() {
		return length;
	}

	public String getStringValue(int identifier) {
		if (data != null) {
			if (data.get(identifier) != null) {
				return (String) data.get(identifier);
			}
		}
		return defaultString;
	}

	public int getIntValue(int identifier) {
		if (data != null) {
			if (data.get(identifier) != null) {
				return (int) data.get(identifier);
			}
		}
		return defaultInteger;
	}

	@Override
	protected void parseOpcode(int opcode, RSBuffer rsBuffer) {
		switch (opcode) {
		case 1:
		case 2:
			rsBuffer.getU();
			break;
		case 3:
			defaultString = rsBuffer.getJagString();
			break;
		case 4:
			defaultInteger = rsBuffer.getUInt();
			break;
		case 5:
		case 6:
			length = rsBuffer.getUShort();
			data = new HashMap<>(length);
			boolean str = opcode == 5;
			for (int i = 0; i < length; i++) {
				int key = rsBuffer.getUInt();
				if (str) {
					data.put(key, rsBuffer.getJagString());
				} else {
					data.put(key, rsBuffer.getUInt());
				}
			}
			break;
		default:
			break;
		}
	}
}
