package com.rs.tools;

import java.io.IOException;

import com.rs.cache.Cache;
import com.rs.cache.loaders.ObjectDefinitions;

/**
 * 
 * @author paolo
 * a basic object editor without gui
 */
public class ObjectEditor {
	
	public static void main(String[] args) throws IOException{
		Cache.init();
		ObjectDefinitions defs = ObjectDefinitions.getObjectDefinitions(99952);
		defs.options[0] = "Mine";
		defs.name = "Concentrated runite rock";
		Cache.STORE.getIndexes()[16].putFile(defs.getArchiveId(), defs.id & 0xff, defs.encode());
		System.out.println("Worked");
	}

}
