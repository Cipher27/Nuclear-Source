package com.rs.game.player.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.rs.game.item.Item;
import com.rs.utils.Logger;

/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public class SetManager {

	private static SetManager INSTANCE = new SetManager();

	public static SetManager getSingleton() {
		return INSTANCE;
	}

	public static List<String> strings = new ArrayList<String>();
	public static List<Set> list = new ArrayList<Set>();
	private static final String path = "./data/setList.ini";
	private static File map = new File(path);
	private static int readType;
	private static int id;
	private static Set newset = new Set();
	
	private static int NEWSET = 0;
	private static int INVENTORY = 1;
	private static int EQUIPMENT = 2;

	public static void init() {
		try {
			//Logger.log("SetManager", "Loading sets.");
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(map));
			String s;
			while ((s = reader.readLine()) != null) {
				strings.add(s);
				read(s);
			}
			Logger.log("SetManager", "Loaded "+ list.size() +" sets.");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private static void read(String r) {
		if (r.equals("equipment start")) {
			readType = EQUIPMENT;
			id = 0;
		} else if (r.equals("inventory start")) {
			readType = INVENTORY;
			id = 0;
		} else if (r.equals("set end")) {
			readType = NEWSET;
			list.add(newset);
			newset = new Set();
			id = 0;
		} else if (readType == EQUIPMENT) {
			String[] l = r.split(",");
			newset.addEquip(new Item(Integer.valueOf(l[0]), Integer.valueOf(l[1])), id);
			id++;
		} else if (readType == INVENTORY) {
			String[] l = r.split(",");
			newset.addInv(new Item(Integer.valueOf(l[0]), Integer.valueOf(l[1])), id);
			id++;
		}
	}

	public void addToList(List<String> s, Item[] i, Item[] i2) {
		for (String z : s)
		strings.add(z);
		list.add(new Set(i, i2));
		save();
	}
	
	public void addToList(Set set) {
		list.add(newset);
	}
	
	private void save() {
	BufferedWriter bf;
		try {
			clearMapFile();
			bf = new BufferedWriter(new FileWriter(path, true));
			for (String stuff : strings) {
				bf.write(stuff);
				bf.newLine();
			}
			bf.flush();
			bf.close();
		} catch (IOException e) {
			System.err.println("Error saving nonApproved!");
		}
	}
	
	private void clearMapFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(map);
			writer.print("");
			writer.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}