package com.vision.rs.loader;

import java.util.HashMap;

import com.jagex.fs.IndexDecompressor;
import com.vision.rs.DefinitionLoader;
import com.vision.rs.Language;
import com.vision.rs.definition.EnumDefinition;

public class EnumLoader extends DefinitionLoader<EnumDefinition> {
	private HashMap<Integer, EnumDefinition> cache = new HashMap<>();

	public EnumLoader(Language language, IndexDecompressor decompressor) {
		super(language, decompressor);
	}

	@Override
	protected void cache(EnumDefinition definition) {
		cache.put(definition.getDefinitionId(), definition);
	}

	@Override
	protected EnumDefinition getCached(int definitionId) {
		return cache.get(definitionId);
	}

	@Override
	protected void afterParse(EnumDefinition definition) {
		// Do nada
	}

	@Override
	protected EnumDefinition instanceDefinition() {
		return new EnumDefinition();
	}

	@Override
	protected int getFileId(int definitionId) {
		return definitionId >>> 8;
	}

	@Override
	protected int getMemberCount() {
		return 256;
	}

	@Override
	protected void clearCache() {
		cache.clear();
	}

	@Override
	protected void restartCache(int newSize) {
		cache = new HashMap<>(newSize);
	}

	public static final int RENDER_PACK_DEFINE = 644;
	public static final int WEAPON_TYPE = 686;
	public static final int SPECIAL_ATTACK = 687;
}
