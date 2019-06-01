package com.vision.rs.loader;

import java.util.HashMap;

import com.jagex.fs.IndexDecompressor;
import com.vision.rs.DefinitionLoader;
import com.vision.rs.Language;
import com.vision.rs.definition.CharacterDefinition;

public class CharacterDefinitionLoader extends
		DefinitionLoader<CharacterDefinition> {
	private HashMap<Integer, CharacterDefinition> cache = new HashMap<>();
	private boolean oldFormat;

	public CharacterDefinitionLoader(Language language,
			IndexDecompressor decompressor, boolean oldFormat) {
		super(language, decompressor);
		this.oldFormat = oldFormat;
	}

	/**
	 * @return the oldFormat
	 */
	public boolean isOldFormat() {
		return oldFormat;
	}

	@Override
	protected void cache(CharacterDefinition definition) {
		cache.put(definition.getDefinitionId(), definition);
	}

	@Override
	protected CharacterDefinition getCached(int definitionId) {
		return cache.get(definitionId);
	}

	@Override
	protected void afterParse(CharacterDefinition definition) {

	}

	@Override
	protected CharacterDefinition instanceDefinition() {
		return new CharacterDefinition();
	}

	@Override
	protected int getFileId(int definitionId) {
		if (oldFormat)
			return 9;
		return definitionId >>> 7;
	}

	@Override
	protected int getMemberCount() {
		if (oldFormat)
			return 16383;
		return 128;
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
