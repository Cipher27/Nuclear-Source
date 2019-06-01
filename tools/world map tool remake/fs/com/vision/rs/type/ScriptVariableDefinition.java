package com.vision.rs.type;

import com.vision.rs.Definition;
import com.vision.rs.io.RSBuffer;

public class ScriptVariableDefinition extends Definition {
	private boolean members = true;
	private int intVar;
	private String strVar;

	@Override
	protected void parseOpcode(int opcode, RSBuffer rsBuffer) {
		switch (opcode) {
		case 1:
			rsBuffer.get();// CP1252 var... Unknown
			break;
		case 2:
			intVar = rsBuffer.getUInt();
			break;
		case 4:
			members = false;
			break;
		case 5:
			strVar = rsBuffer.getJagString();
			break;
		default:
			break;
		}
	}

	/**
	 * @return the members
	 */
	public boolean isMembers() {
		return members;
	}

	/**
	 * @return the strVar
	 */
	public String getString() {
		return strVar;
	}

	/**
	 * @return the intVar
	 */
	public int getInt() {
		return intVar;
	}
}
