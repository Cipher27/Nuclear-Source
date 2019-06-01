package com.vision.rs.loader;

import java.util.HashMap;
import java.util.Iterator;

import com.jagex.fs.IndexDecompressor;
import com.vision.rs.DefinitionLoader;
import com.vision.rs.Language;
import com.vision.rs.definition.ItemDefinition;
import com.vision.rs.type.ScriptVariableDefinition;
import com.vision.rs.type.ScriptVariableLoader;

public class ItemLoader extends DefinitionLoader<ItemDefinition> {
	private HashMap<Integer, ItemDefinition> cache = new HashMap<>(100);
	private ScriptVariableLoader scriptVarLoader;

	public ItemLoader(ScriptVariableLoader scriptVarLoader, Language language,
			IndexDecompressor decompressor, boolean loadMemberData) {
		super(language, decompressor);
		this.loadMemberData = loadMemberData;
		this.scriptVarLoader = scriptVarLoader;
		loadAll();

		decompressor.reloadFiles();
		decompressor.reloadAllEntries();
	}

	@Override
	protected void cache(ItemDefinition definition) {
		cache.put(definition.getDefinitionId(), definition);
	}

	@Override
	protected ItemDefinition getCached(int definitionId) {
		return cache.get(definitionId);
	}

	@Override
	protected void afterParse(ItemDefinition definition) {
		definition.afterParse();
		if (definition.getNoteTemplate() != -1) {// Set Noted Item defaults.
			definition.setNoted(loadDefinition(definition.getNonNoted()));
		}
		if (definition.getLendTemplate() != -1) {// Set Lent Item defaults.
			definition.setLent(loadDefinition(definition.getUnlent()));
		}
		if (!loadMemberData && definition.isMembers()) {
			definition.setMembers();
			if (definition.getScriptData() != null) {
				Iterator<Integer> iterator = definition.getScriptData()
						.keySet().iterator();
				boolean hasScriptDataLeft = false;
				while (iterator.hasNext()) {
					ScriptVariableDefinition scriptVariable = scriptVarLoader
							.loadDefinition(iterator.next());
					if (scriptVariable != null && scriptVariable.isMembers()) {
						iterator.remove();
					} else {
						hasScriptDataLeft = true;
					}
				}
				if (!hasScriptDataLeft) {
					definition.removeScriptVariables();
				}
			}
		}
	}

	@Override
	protected ItemDefinition instanceDefinition() {
		return new ItemDefinition();
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
		loadAll();
	}

	@Override
	protected void restartCache(int newSize) {
		cache = new HashMap<>(newSize);
	}

	private void loadAll() {
		int equipCount = 0;
		for (int defId = 0; defId < definitionCount; defId++) {
			ItemDefinition def = loadDefinition(defId);
			if (def.getMaleModel1() != -1 || def.getMaleModel2() != -1) {
				def.setEquipIndex(equipCount++);
			}
		}
	}
}
