package com.rs.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.rs.utils.Logger;

/**
 * @author Harrison
 * @version 1.0
 * @category Used to parse missing item definitions information from a .txt file, dumped from an #839 cache.
 * The information parsed provides information pertaining, but not limited to, combat animations, etc.
 */

public class MapInformationParser {
	
	private static final HashMap<Integer,HashMap<Object,Object>> DATA_MAP = new HashMap<Integer,HashMap<Object,Object>>();
	
	public static void init() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("./data/gsmapdump.txt"));
		String line;
		Object key = null, value = null, script = null;
		while((line = reader.readLine()) != null) {
			String intval = line.substring(7, line.indexOf(" "));
			try {
				script = Integer.valueOf(intval);
			} catch (NumberFormatException e) {
				Logger.handle(e);
			}
			DATA_MAP.put((Integer)script,new HashMap<Object,Object>());
			String lineData = line.substring(line.indexOf("{")+1, line.indexOf("}"));
			String[] data = lineData.split(", ");
			for (int i = 0; i < data.length; i++) {
				String[] mapdata = data[i].split("=");
				if (mapdata.length != 2)
					continue;
				key = mapdata[0];
				value = mapdata[1];
				if (key != null && value != null) {
					HashMap<Object,Object> map = DATA_MAP.get(script);
					if (map != null)
						map.put(key, value);
				}
			}
		}
		reader.close();
		System.err.println("Successfully loaded the datamap - size: "+DATA_MAP.size());
	}
	
	public static HashMap<Integer, HashMap<Object,Object>> getMap() {
		return DATA_MAP;
	}
	
	
	/**
	 * Prevents instantiation
	 */
	private MapInformationParser() {
		
	}
	
}