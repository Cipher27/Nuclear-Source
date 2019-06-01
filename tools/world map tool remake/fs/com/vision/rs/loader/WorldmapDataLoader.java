package com.vision.rs.loader;

import java.util.HashMap;

import com.jagex.fs.IndexDecompressor;
import com.vision.rs.definition.WorldMapEntry;
import com.vision.rs.io.RSBuffer;

public class WorldmapDataLoader {
	public HashMap<Integer, WorldMapEntry> worldMapData = new HashMap<>();

	public WorldmapDataLoader(IndexDecompressor decompressor) {
		// this.decompressor = decompressor;
		int detailId = decompressor.getFileId("details");
		int[] entries = decompressor.getEntries(detailId);
		for (int i = 0; i < entries.length; i++) {
			WorldMapEntry entry = getWorldMapEntry(entries[i],
					decompressor.getFile(detailId, entries[i]));
			worldMapData.put(entries[i], entry);
		}
	}

	private WorldMapEntry getWorldMapEntry(int entryId, byte[] data) {
		RSBuffer buffer = new RSBuffer(data);
		WorldMapEntry entry = new WorldMapEntry(entryId);
		entry.parseHeader(buffer);
		return entry;
	}
}
