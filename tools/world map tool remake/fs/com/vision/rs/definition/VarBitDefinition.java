package com.vision.rs.definition;

import com.vision.rs.Definition;
import com.vision.rs.io.RSBuffer;

public class VarBitDefinition extends Definition {
	private int arrayId = -1;
	private int shift;
	private int top;

	@Override
	protected void parseOpcode(int opcode, RSBuffer buffer) {
		if (opcode == 1) {
			arrayId = buffer.getUShort();
			shift = buffer.getU();
			top = buffer.getU();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VarBitDefinition [arrayId=" + arrayId + ", shift=" + shift
				+ ", top=" + top + "]";
	}

	/**
	 * @return the arrayId
	 */
	public int getArrayId() {
		return arrayId;
	}

	/**
	 * @return the shift
	 */
	public int getShift() {
		return shift;
	}

	/**
	 * @return the top
	 */
	public int getTop() {
		return top;
	}
}
