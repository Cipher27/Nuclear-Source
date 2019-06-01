package com.vision.rs;

import com.jagex.fs.IndexDecompressor;
import com.jagex.fs.compression.Decompressor;
import com.vision.rs.io.RSBuffer;

/**
 * Represents a definition loader from the cache.
 * 
 * @author Teemu Uusitalo.
 *
 * @param <D>
 *            the definition this loader loads.
 */
public abstract class DefinitionLoader<D extends Definition> {
	protected IndexDecompressor decompressor;
	protected int definitionCount;

	protected boolean loadMemberData;
	protected Language language;

	// private GameType type;

	/**
	 * Constructs a new definition loader.
	 * 
	 * @param type
	 *            the current gametype.
	 * @param languageId
	 *            the current language id.
	 * @param decompressor
	 *            the decompressor.
	 * @see GameType
	 * @see Decompressor
	 */
	public DefinitionLoader(Language language, IndexDecompressor decompressor) {
		this.decompressor = decompressor;
		this.language = language;
		// this.type = type;
		this.definitionCount = 0;
		if (decompressor != null) {
			int lastFileId = decompressor.getFileCount() - 1;
			int memberCount = decompressor.getMemberCount(lastFileId);
			definitionCount = (getMemberCount() * lastFileId) + memberCount;
		}
	}

	/**
	 * @param definitionId
	 *            the file index of the definition to load.
	 */
	@SuppressWarnings("unchecked")
	public D loadDefinition(int definitionId) {
		D definition = getCached(definitionId);
		if (definition != null) {
			return definition;
		}
		byte[] data = decompressor.getFile(getFileId(definitionId),
				getMemberId(definitionId));
		definition = instanceDefinition();
		definition.setDefinitionId(definitionId);
		definition.setLoader((DefinitionLoader<Definition>) this);
		if (data != null) {
			definition.parse(new RSBuffer(data));
		}
		afterParse(definition);
		cache(definition);
		return definition;
	}

	/**
	 * Caches a definition.
	 * 
	 * @param definition
	 *            the definition to cache.
	 */
	protected abstract void cache(D definition);

	/**
	 * Gets a definition from the cache.
	 * 
	 * @param definitionId
	 *            the id of the definition to get from the cache
	 * @return the cached definition.
	 */
	protected abstract D getCached(int definitionId);

	/**
	 * Here we execute actions that should be executed after file loading.
	 * 
	 * @param definition
	 *            the definition to handle after-parse actions.
	 */
	protected abstract void afterParse(D definition);

	public int getDefinitionCount() {
		return definitionCount;
	}

	protected abstract D instanceDefinition();

	/**
	 * Gets a file id for the given definition.
	 * 
	 * @param definitionId
	 *            the definition id to get the file id for.
	 * @return the file id.
	 */
	protected abstract int getFileId(int definitionId);

	/**
	 * Gets a member id for the given definition id.
	 * 
	 * @param definitionId
	 *            the definition id to get the member id for.
	 * @return the member id.
	 */
	protected int getMemberId(int definitionId) {
		return (definitionId & (getMemberCount() - 1));
	}

	/**
	 * @return the member count in one file.
	 */
	protected abstract int getMemberCount();

	/**
	 * Clears the definition cache.
	 */
	protected abstract void clearCache();

	/**
	 * Restarts the cache with a new capacity.
	 * 
	 * @param newSize
	 *            the new size of the cache.
	 */
	protected abstract void restartCache(int newSize);

	public boolean loadMemberData() {
		return loadMemberData;
	}

	public void loadMembersOnlyData(boolean loadMemberData) {
		if (loadMemberData != this.loadMemberData) {
			this.loadMemberData = loadMemberData;
			clearCache();
		}
	}
}
