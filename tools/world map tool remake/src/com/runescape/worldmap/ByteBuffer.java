package com.runescape.worldmap;
public class ByteBuffer extends Node_Sub1 {
	public byte buffer[];
	public int offset;

	public ByteBuffer() {

	}

	public ByteBuffer(byte buffer[]) {
		this.buffer = buffer;
		offset = 0;
	}

	public byte g1() {
		return buffer[offset++];
	}

	public int g1_u() {
		return buffer[offset++] & 0xff;
	}

	public int g2_u() {
		offset += 2;
		return ((buffer[offset - 2] & 0xff) << 8) + (buffer[offset - 1] & 0xff);
	}

	public int g3_u() {
		offset += 3;
		return ((buffer[offset - 3] & 0xff) << 16) + ((buffer[offset - 2] & 0xff) << 8) + (buffer[offset - 1] & 0xff);
	}

	public int g4_u() {
		offset += 4;
		return ((buffer[offset - 4] & 0xff) << 24) + ((buffer[offset - 3] & 0xff) << 16)
				+ ((buffer[offset - 2] & 0xff) << 8) + (buffer[offset - 1] & 0xff);
	}

	public String gjstr() {
		int i = offset;
		while (buffer[offset++] != 10)
			;
		return new String(buffer, i, offset - i - 1);
	}
}
