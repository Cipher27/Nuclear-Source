package com.vision.rs.loader;

import java.util.HashMap;

import com.jagex.fs.IndexDecompressor;
import com.vision.rs.DefinitionLoader;
import com.vision.rs.Language;
import com.vision.rs.definition.VarBitDefinition;

public class VarbitLoader extends DefinitionLoader<VarBitDefinition> {
	private HashMap<Integer, VarBitDefinition> cache = new HashMap<>();

	public VarbitLoader(Language language, IndexDecompressor decompressor) {
		super(language, decompressor);
		while (!decompressor.fullyLoaded()) {
			try {
				Thread.sleep(150L);
			} catch (InterruptedException e) {
			}
		}
		for (int defId = 0; defId < definitionCount; defId++) {
			loadDefinition(defId);
		}
		decompressor.reloadFiles();
		decompressor.reloadAllEntries();
	}

	@Override
	protected void cache(VarBitDefinition definition) {
		cache.put(definition.getDefinitionId(), definition);
	}

	@Override
	protected VarBitDefinition getCached(int definitionId) {
		return cache.get(definitionId);
	}

	@Override
	protected void afterParse(VarBitDefinition definition) {

	}

	@Override
	protected VarBitDefinition instanceDefinition() {
		return new VarBitDefinition();
	}

	@Override
	protected int getFileId(int definitionId) {
		return definitionId >>> 10;
	}

	@Override
	protected int getMemberCount() {
		return 1024;
	}

	@Override
	protected void clearCache() {
		cache.clear();
	}

	@Override
	protected void restartCache(int newSize) {
		cache = new HashMap<>();
	}
}
