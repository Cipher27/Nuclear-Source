package com.vision.rs.type;

import java.util.HashMap;

import com.jagex.fs.IndexDecompressor;
import com.vision.rs.DefinitionLoader;
import com.vision.rs.Language;

public class ScriptVariableLoader extends
		DefinitionLoader<ScriptVariableDefinition> {
	private HashMap<Integer, ScriptVariableDefinition> cache;

	public ScriptVariableLoader(Language language,
			IndexDecompressor decompressor) {
		super(language, decompressor);
		restartCache(2048);
	}

	@Override
	protected void cache(ScriptVariableDefinition definition) {
		cache.put(definition.getDefinitionId(), definition);
	}

	@Override
	protected ScriptVariableDefinition getCached(int definitionId) {
		return cache.get(definitionId);
	}

	@Override
	protected void afterParse(ScriptVariableDefinition definition) {

	}

	@Override
	protected ScriptVariableDefinition instanceDefinition() {
		return new ScriptVariableDefinition();
	}

	@Override
	protected int getFileId(int definitionId) {
		return 11;
	}

	// Dank mon idk
	@Override
	protected int getMemberCount() {
		return 2048;
	}

	@Override
	protected void clearCache() {
		cache.clear();
	}

	@Override
	protected void restartCache(int newSize) {
		cache = new HashMap<>(newSize);
	}
}
