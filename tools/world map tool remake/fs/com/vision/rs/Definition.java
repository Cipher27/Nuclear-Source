package com.vision.rs;

import com.vision.rs.io.RSBuffer;

/**
 * Represents a definition.
 * 
 * @author Teemu Uusitalo
 */
public abstract class Definition {
	protected DefinitionLoader<?> loader;
	private int definitionId;

	/**
	 * parses this definition.
	 * 
	 * @param rsBuffer
	 *            the data to parse this definition from.
	 */
	public void parse(RSBuffer rsBuffer) {
		int opcode;
		while ((opcode = rsBuffer.getU()) != 0) {
			parseOpcode(opcode, rsBuffer);
		}
	}

	/**
	 * @param loader
	 *            the loader of this definition.
	 */
	public void setLoader(DefinitionLoader<Definition> loader) {
		this.loader = loader;
	}

	/**
	 * Parses an piece of this definition based on the opcode.
	 * 
	 * @param opcode
	 *            the opcode to parse
	 * @param rsBuffer
	 *            the buffer containing this definition
	 */
	protected abstract void parseOpcode(int opcode, RSBuffer rsBuffer);

	/**
	 * @return the definitionId.
	 */
	public int getDefinitionId() {
		return definitionId;
	}

	/**
	 * Set the definitionId.
	 * 
	 * @param definitionId
	 */
	public void setDefinitionId(int definitionId) {
		this.definitionId = definitionId;
	}
}
