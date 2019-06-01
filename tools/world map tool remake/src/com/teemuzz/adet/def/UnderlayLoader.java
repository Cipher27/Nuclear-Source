package com.teemuzz.adet.def;

import java.util.HashMap;

import com.jagex.fs.IndexDecompressor;
import com.vision.rs.DefinitionLoader;
import com.vision.rs.Language;

public class UnderlayLoader extends DefinitionLoader<Underlay> {
	public UnderlayLoader(Language language, IndexDecompressor decompressor) {
		super(language, decompressor);
		for (int i = 0; i < 150; i++) {
			loadDefinition(i);
		}
	}

	private HashMap<Integer, Underlay> underlays = new HashMap<>();

	@Override
	protected void cache(Underlay definition) {
		underlays.put(definition.getDefinitionId(), definition);
	}

	@Override
	protected Underlay getCached(int definitionId) {
		return underlays.get(definitionId);
	}

	@Override
	protected void afterParse(Underlay definition) {

	}

	@Override
	protected Underlay instanceDefinition() {
		return new Underlay();
	}

	@Override
	protected int getFileId(int definitionId) {
		return 1;
	}

	@Override
	protected int getMemberCount() {
		return 256;
	}

	@Override
	protected void clearCache() {

	}

	@Override
	protected void restartCache(int newSize) {

	}
}
