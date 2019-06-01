package com.rs.tools;

import java.io.IOException;

import com.rs.cache.Cache;
import com.rs.cache.loaders.NPCDefinitions;

/**
 * 
 * @author paolo
 * a basic object editor without gui
 */
public class NpcEditor {
	
	public static void main(String[] args) throws IOException{
		Cache.init();
	    NPCDefinitions defs = NPCDefinitions.getNPCDefinitions(20616);
	    NPCDefinitions defs2 = NPCDefinitions.getNPCDefinitions(13448);
	    defs.headIcons = 20;
	    defs.name = "Avaryss";
		Cache.STORE.getIndexes()[18].putFile(defs.getArchiveId(), defs.id & 0xff, defs.encode());
		System.out.println("Worked");
		System.out.println(""+defs2.headIcons);
	}

}
