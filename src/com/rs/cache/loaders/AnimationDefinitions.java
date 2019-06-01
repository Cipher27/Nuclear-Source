package com.rs.cache.loaders;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.rs.cache.Cache;
import com.rs.io.InputStream;

public class AnimationDefinitions {

	public int anInt2136;
	public int anInt2137;
	public int[] anIntArray2139;
	public int anInt2140;
	public boolean aBoolean2141 = false;
	public int anInt2142;
	public int leftHandItem;
	public int rightHandItem = -1;
	public int[][] handledSounds;
	public boolean[] aBooleanArray2149;
	public int[] anIntArray2151;
	public boolean aBoolean2152;
	public int[] anIntArray2153;
	public int anInt2155;
	public boolean aBoolean2158;
	public boolean aBoolean2159;
	public int anInt2162;
	public int anInt2163;

	// added
	public int[] soundMinDelay;
	public int[] soundMaxDelay;
	public int[] anIntArray1362;
	public boolean effect2Sound;
	public int id;

	public HashMap<Integer, Object> clientScriptData;

	private static final ConcurrentHashMap<Integer, AnimationDefinitions> animDefs = new ConcurrentHashMap<Integer, AnimationDefinitions>();

	public static final AnimationDefinitions getAnimationDefinitions(int emoteId) {
		try {
			AnimationDefinitions defs = animDefs.get(emoteId);
			if (defs != null)
				return defs;
			byte[] data = Cache.STORE.getIndexes()[20].getFile(emoteId >>> 7, emoteId & 0x7f);
			defs = new AnimationDefinitions();
			defs.id = emoteId;
			if (data != null)
				defs.readValueLoop(new InputStream(data));
			defs.method2394();
			animDefs.put(emoteId, defs);
			return defs;
		}
		catch (Throwable t) {
			return null;
		}
	}

	private void readValueLoop(InputStream stream) {
		for (;;) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			readValues(stream, opcode);
		}
	}

	public int getEmoteTime() {
		if (anIntArray2153 == null)
			return 0;
		int ms = 0;
		for (int i : anIntArray2153)
			ms += i;
		return ms * 10;
	}

	public int getEmoteClientCycles() {
		if (anIntArray2153 == null)
			return 0;
		int r = 0;
		for (int i = 0; i < anIntArray2153.length - 3; i++) {
			r += anIntArray2153[i];
		}
		return r;
	}
	


	private void readValues(InputStream buffer, int opcode) {
		if (opcode == 1) {
			int i_3_ = buffer.readUnsignedShort();
			anIntArray2153 = new int[i_3_];
			for (int i_4_ = 0; i_4_ < i_3_; i_4_++)
				anIntArray2153[i_4_] = buffer.readUnsignedShort();
			anIntArray2139 = new int[i_3_];
			for (int i_5_ = 0; i_5_ < i_3_; i_5_++)
				anIntArray2139[i_5_] = buffer.readUnsignedShort();
			for (int i_6_ = 0; i_6_ < i_3_; i_6_++)
				anIntArray2139[i_6_] = (buffer.readUnsignedShort() << 16) + anIntArray2139[i_6_];
		}
		else if (opcode == 2)
			anInt2136 = buffer.readUnsignedShort();
		else if (5 == opcode) {
			anInt2142 = buffer.readUnsignedByte();
		} else if (opcode == 6)
			rightHandItem = buffer.readUnsignedShort();
		else if (opcode == 7)
			leftHandItem = buffer.readUnsignedShort();
		else if (8 == opcode)
			anInt2155 = buffer.readUnsignedByte();
		else if (9 == opcode)
			anInt2140 = buffer.readUnsignedByte();
		else if (opcode == 10)
			anInt2162 = buffer.readUnsignedByte();
		else if (11 == opcode)
			anInt2155 = buffer.readUnsignedByte();
		else if (12 == opcode) {
			int i_7_ = buffer.readUnsignedByte();
			anIntArray2151 = new int[i_7_];
			for (int i_8_ = 0; i_8_ < i_7_; i_8_++)
				anIntArray2151[i_8_] = buffer.readUnsignedShort();
			for (int i_9_ = 0; i_9_ < i_7_; i_9_++)
				anIntArray2151[i_9_] = (buffer.readUnsignedShort() << 16) + anIntArray2151[i_9_];
		}
		else if (13 == opcode) {
			int i_10_ = buffer.readUnsignedShort();
			handledSounds = new int[i_10_][];
			for (int i_11_ = 0; i_11_ < i_10_; i_11_++) {
				int i_12_ = buffer.readUnsignedByte();
				if (i_12_ > 0) {
					handledSounds[i_11_] = new int[i_12_];
					handledSounds[i_11_][0] = buffer.read24BitInt();
					for (int i_13_ = 1; i_13_ < i_12_; i_13_++)
						handledSounds[i_11_][i_13_] = buffer.readUnsignedShort();
				}
			}
		}
		else if (opcode == 14) {
			aBoolean2158 = true;
		} else if (opcode == 15) {
			aBoolean2159 = true;
		} else if (16 != opcode && opcode != 18) {
			if (opcode == 19) {
				if (null == anIntArray1362) {
					anIntArray1362 = new int[handledSounds.length];
					for (int i_14_ = 0; i_14_ < handledSounds.length; i_14_++)
						anIntArray1362[i_14_] = 255;
				}
				anIntArray1362[buffer.readUnsignedByte()] = buffer.readUnsignedByte();
			}
			else if (20 == opcode) {
				if (soundMaxDelay == null || null == soundMinDelay) {
					soundMaxDelay = new int[handledSounds.length];
					soundMinDelay = new int[handledSounds.length];
					for (int i_15_ = 0; i_15_ < handledSounds.length; i_15_++) {
						soundMaxDelay[i_15_] = 256;
						soundMinDelay[i_15_] = 256;
					}
				}
				int i_16_ = buffer.readUnsignedByte();
				soundMaxDelay[i_16_] = buffer.readUnsignedShort();
				soundMinDelay[i_16_] = buffer.readUnsignedShort();
			}
			else if (22 == opcode)
				buffer.readUnsignedByte();
			else if (23 == opcode)
				buffer.readUnsignedShort();
			else if (24 == opcode)
				buffer.readUnsignedShort();
			else if (opcode == 249) {
				int length = buffer.readUnsignedByte();
				if (clientScriptData == null)
					clientScriptData = new HashMap<Integer, Object>(length);
				for (int index = 0; index < length; index++) {
					boolean stringInstance = buffer.readUnsignedByte() == 1;
					int key = buffer.read24BitInt();
					Object value = stringInstance ? buffer.readString() : buffer.readInt();
					clientScriptData.put(key, value);
				}
			}
		}
	}

	public void method2394() {
		if (anInt2140 == -1) {
			if (aBooleanArray2149 == null)
				anInt2140 = 0;
			else
				anInt2140 = 2;
		}
		if (anInt2162 == -1) {
			if (aBooleanArray2149 == null)
				anInt2162 = 0;
			else
				anInt2162 = 2;
		}
	}

	public AnimationDefinitions() {
		anInt2136 = 99;
		leftHandItem = -1;
		anInt2140 = -1;
		aBoolean2152 = false;
		anInt2142 = 5;
		aBoolean2159 = false;
		anInt2163 = -1;
		anInt2155 = 2;
		aBoolean2158 = false;
		anInt2162 = -1;
	}

}
