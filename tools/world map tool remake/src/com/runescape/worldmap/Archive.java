package com.runescape.worldmap;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Archive {

	public Archive(byte data[]) {
		decodeData(data);
	}

	public void decodeData(byte data[]) {
		ByteBuffer vector = new ByteBuffer(data);
		int unpackedLength = vector.g3_u();
		int packedLength = vector.g3_u();
		if (packedLength != unpackedLength) {
			byte unpackedData[] = new byte[unpackedLength];
			Class4.unpack(unpackedData, unpackedLength, data, packedLength, 6);
			data = unpackedData;
			vector = new ByteBuffer(data);
			unpacked = true;
		} else {
			this.data = data;
			unpacked = false;
		}
		size = vector.g2_u();
		storedHashes = new int[size];
		fileSizes = new int[size];
		fileLengths = new int[size];
		startPosition = new int[size];
		int currentPos = vector.offset + size * 10;
		for (int index = 0; index < size; index++) {
			storedHashes[index] = vector.g4_u();
			fileSizes[index] = vector.g3_u();
			fileLengths[index] = vector.g3_u();
			startPosition[index] = currentPos;
			currentPos += fileLengths[index];
		}
	}

	public byte[] getFile(String name, byte destination[]) {
		int nameHash = 0;
		name = name.toUpperCase();
		for (int j = 0; j < name.length(); j++)
			nameHash = (nameHash * 61 + name.charAt(j)) - 32;

		for (int index = 0; index < size; index++)
			if (storedHashes[index] == nameHash) {
				if (destination == null)
					destination = new byte[fileSizes[index]];
				if (!unpacked) {
					Class4.unpack(destination, fileSizes[index], data,
							fileLengths[index], startPosition[index]);
				} else {
					for (int l = 0; l < fileSizes[index]; l++)
						destination[l] = data[startPosition[index] + l];

				}
				return destination;
			}
		return null;
	}

	public byte data[];
	public int size;
	public int storedHashes[];
	public int fileSizes[];
	public int fileLengths[];
	public int startPosition[];
	public boolean unpacked;
}
