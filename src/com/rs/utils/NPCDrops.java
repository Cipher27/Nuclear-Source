package com.rs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.rs.game.npc.Drop;

public class NPCDrops {

	private final static String PACKED_PATH = "data/npcs/packedDrops.d";
	private final static String UNPACKED_PATH = "data/npcs/unpackedDrops.txt";
	private static HashMap<Integer, Drop[]> npcDrops;

	public static final void init() {
		loadPackedNPCDrops();
	}

	public static Drop[] getDrops(int npcId) {
		return npcDrops.get(npcId);
	}

	private Map<Integer, ArrayList<Drop>> dropMapx = null;

	public Map<Integer, ArrayList<Drop>> getDropArray() {

		if (dropMapx == null)
			dropMapx = new LinkedHashMap<Integer, ArrayList<Drop>>();
		// dropMapx = new LinkedHashMap<Integer, ArrayList<Drop>>();
		for (int i : npcDrops.keySet()) {
			int npcId = i;
			ArrayList<Drop> temp = new ArrayList<Drop>();
			for (Drop mainDrop : npcDrops.get(npcId)) {
				temp.add(mainDrop);
			}
			dropMapx.put(i, temp);
		}

		return dropMapx;
	}

	public void insertDrop(int npcID, Drop d) {
		loadPackedNPCDrops();
		Drop[] oldDrop = npcDrops.get(npcID);
		if (oldDrop == null) {
			npcDrops.put(npcID, new Drop[] { d });
		} else {
			int length = oldDrop.length;
			Drop destination[] = new Drop[length + 1];
			System.arraycopy(oldDrop, 0, destination, 0, length);
			destination[length] = d;
			npcDrops.put(npcID, destination);
		}
	}

	private static void readUnpackedNPCDrops() {
		try {
			//Read the unpacked drops.
			npcDrops = new HashMap<Integer, Drop[]>();
			File f = new File(UNPACKED_PATH);
			if (!f.exists())
				throw new IllegalStateException("Unpacked NPC drops (" + UNPACKED_PATH + ") does not exist.");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			int line_num = 0;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("//"))
					continue;
				if (!line.startsWith("<npc id="))
					throw new IllegalStateException("Expecting NPC object at line: " + line_num);
				int itemId, npcId, min, max;
				double rate;
				ArrayList<Drop> drops = new ArrayList<Drop>();
				npcId = Integer.parseInt(StringUtils.substringBetween(line, "<npc id=", ">"));
				line = br.readLine();
				line_num++;
				if (!line.startsWith("\t<drop>"))
					throw new IllegalStateException("Expecting <drop> object at line: " + line_num);
				while (!(line = br.readLine()).startsWith("\t</drop>")) {
					try {
					line_num++;
					if (!line.startsWith("\t\t<item>")) 
						throw new IllegalStateException("Expecting <item> line (tabs, no spaces!) : " + line_num);
					itemId = Integer.parseInt(StringUtils.substringBetween(line, "<item>", "</item>"));
					line = br.readLine();
					line_num++;
					if (!line.startsWith("\t\t<rate>")) 
						throw new IllegalStateException("Expecting <rate> line (tabs, no spaces!) : " + line_num);
					rate = Double.parseDouble(StringUtils.substringBetween(line, "<rate>", "</rate>"));
					line = br.readLine();
					line_num++;
				    if (!line.startsWith("\t\t<minamount>")) 
				    	throw new IllegalStateException("Expecting <minamount> line (tabs, no spaces!) : " + line_num);
				    
					min = Integer.parseInt(StringUtils.substringBetween(line, "<minamount>", "<minamount>"));
					line = br.readLine();
					line_num++;
					if (!line.startsWith("\t\t<maxamount>")) 
						throw new IllegalStateException("Expecting <maxamount> line (tabs, no spaces!) : " + line_num);
					max = Integer.parseInt(StringUtils.substringBetween(line, "<maxamount>", "</maxamount>"));
					Drop drop = new Drop(itemId, rate, min, max, false);
					drops.add(drop);
				    } catch (Exception e) {
				    	e.printStackTrace();
				    	System.out.println("LINENUM: " + line_num);
				    	System.out.println("LINE CONTENT: " + line);
				    	return;
				    }
				}
				Drop[] dropsArray = new Drop[drops.size()];
				for (int z = 0; z < drops.size(); z++)
					dropsArray[z] = drops.get(z);
				npcDrops.put(npcId, dropsArray);
				br.readLine(); //skip close npc line
				line_num++;
			}
			br.close();
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}
	
	private static void loadPackedNPCDrops() {
		try {
			readUnpackedNPCDrops();
//			RandomAccessFile in = new RandomAccessFile(PACKED_PATH, "r");
//			FileChannel channel = in.getChannel();
//			ByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0,
//					channel.size());
//			int dropSize = buffer.getShort() & 0xffff;
//			npcDrops = new HashMap<Integer, Drop[]>(dropSize);
//			for (int i = 0; i < dropSize; i++) {
//				int npcId = buffer.getShort() & 0xffff;
//				Drop[] drops = new Drop[buffer.getShort() & 0xffff];
//				for (int d = 0; d < drops.length; d++) {
//					if (buffer.get() == 0)
//						drops[d] = new Drop(buffer.getShort() & 0xffff,
//								buffer.getDouble(), buffer.getInt(),
//								buffer.getInt(), false);
//					else
//						drops[d] = new Drop(0, 0, 0, 0, true);
//
//				}
//				npcDrops.put(npcId, drops);
//			}
//			channel.close();
//			in.close();
//			System.out.println("Real npcDrops: " + npcDrops.size());
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public HashMap<Integer, Drop[]> getDropMap() {
		return npcDrops;
	}
}