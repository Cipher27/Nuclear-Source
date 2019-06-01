package com.vision.rs.definition;

import com.vision.rs.io.RSBuffer;

public class WorldMapEntry {
	// private int entryId;

	public WorldMapEntry(int entryId) {
		// this.entryId = entryId;
	}

	public void parseHeader(RSBuffer buffer) {
		String gameIdentifier = buffer.getJagString();
		String trimmedName = buffer.getJagString();
		int locationCalc = buffer.getUInt();
		buffer.getUInt();
		boolean showOnMap = buffer.getU() == 1;
		buffer.getU();
		buffer.getU();
		System.out.println(gameIdentifier + " " + trimmedName + " "
				+ locationCalc + " " + showOnMap);
	}
}
