package com.vision.rs;

import com.jagex.fs.FileSystem;
import com.jagex.fs.IndexDecompressor;
import com.vision.rs.io.RSBuffer;
import com.vision.rs.loader.CharacterDefinitionLoader;
import com.vision.rs.loader.EnumLoader;
import com.vision.rs.loader.ItemLoader;
import com.vision.rs.loader.TrackDefinitionLoader;
import com.vision.rs.loader.VarbitLoader;
import com.vision.rs.type.ScriptVariableLoader;

public class DefinitionManager extends FileSystem {
	public static final int LOAD_ENUMS = 1;

	private VarbitLoader varbitLoader;
	private byte[] reference;

	private ScriptVariableLoader scriptVarLoader;
	private ItemLoader itemloader;
	private EnumLoader enumLoader;
	private TrackDefinitionLoader trackdefLoader;
	private CharacterDefinitionLoader characterLoader;

	public DefinitionManager(boolean loadMembers, Language language, String cachePath, boolean oldFormat)
			throws Exception {
		super(cachePath);
		reference = getReference();
		if (!oldFormat) {
			enumLoader = new EnumLoader(language, getDecompressor(17));
			enumLoader = new EnumLoader(language, getDecompressor(17));
			scriptVarLoader = new ScriptVariableLoader(language, getDecompressor(11));
			itemloader = new ItemLoader(scriptVarLoader, language, getDecompressor(19), loadMembers);
			varbitLoader = new VarbitLoader(language, getDecompressor(22));
			trackdefLoader = new TrackDefinitionLoader(enumLoader);
		}
		characterLoader = new CharacterDefinitionLoader(language, getDecompressor(oldFormat ? 2 : 18), oldFormat);
		/**
		 * Suggest cleanup, the filesystem loads files into a cache on the
		 * corresponding index, so they need to be wiped out, after they have
		 * been deleted. :)
		 */

		// for (int i = 0; i < 100; i++) {
		// System.gc();
		// }
	}

	public byte[] getReference() {
		if (reference != null)
			return reference;
		RSBuffer buffer = new RSBuffer(getIndexCount() * 8);
		for (int index = 0; index < getIndexCount(); index++) {
			IndexDecompressor decompr = getDecompressor(index);
			while (!decompr.referenceLoaded())
				continue;
			buffer.putInt(decompr.getChecksum());
			buffer.putInt(decompr.getVersion());
		}
		return buffer.flip().toArray();
	}

	public VarbitLoader getVarBitLoader() {
		return varbitLoader;
	}

	public ItemLoader getItemLoader() {
		return itemloader;
	}

	public CharacterDefinitionLoader getCharLoader() {
		return characterLoader;
	}

	/**
	 * @return the enumLoader.
	 */
	public EnumLoader getEnumLoader() {
		return enumLoader;
	}

	/**
	 * @return the trackdefLoader
	 */
	public TrackDefinitionLoader getTrackdefLoader() {
		return trackdefLoader;
	}

	public IndexDecompressor getDecompr(int indexId) {
		return getDecompressor(indexId);
	}
}
