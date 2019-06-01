package com.teemuzz.adet.def;

import java.util.HashMap;

import com.jagex.fs.IndexDecompressor;
import com.vision.rs.DefinitionLoader;
import com.vision.rs.Language;

public class OverlayLoader extends DefinitionLoader<Overlay> {
	public OverlayLoader(Language language, IndexDecompressor decompressor) {
		super(language, decompressor);
		for (int i = 0; i < 255; i++) {
			loadDefinition(i);
		}
	}

	private HashMap<Integer, Overlay> overlays = new HashMap<>();

	
	@Override
	protected void cache(Overlay definition) {
		overlays.put(definition.getDefinitionId(), definition);
	}

	@Override
	protected Overlay getCached(int definitionId) {
		return overlays.get(definitionId);
	}

	@Override
	protected void afterParse(Overlay definition) {

	}

	@Override
	protected Overlay instanceDefinition() {
		return new Overlay();
	}

	@Override
	protected int getFileId(int definitionId) {
		return 4;
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
