package com.rs.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;

public class WriteFile {
	private String path;
	private boolean append_to_file = false;
	
	public WriteFile(String file_path) {
		path = file_path;
	}
	
	public WriteFile(String file_path, boolean append_value) {
		path = file_path;
		append_to_file = append_value;
	}
	
	public static void writePlayerLog(String message, String file, Player player) {
		if(file.toLowerCase().contains("donation") )
			return;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			String file_name = "C:/data/logs/"+player.getUsername()+"/"+file+".txt";
			String folder_name = "C:/data/logs/"+player.getUsername();
			File folder = new File(folder_name);
			if(!folder.exists())
				folder.mkdir();
			WriteFile data = new WriteFile(file_name, true);
			data.writeToFile("["+dateFormat.format(date)+" "+player.getSession().getIP()+"] "+message);
			writeGlobalLog(message, file, file);
		} catch (IOException e) {
			
		}
	}
	
	public static void writeTradeLog(ItemsContainer<Item> itemsGiven, ItemsContainer<Item> itemsTaken, Player partner, Player trader) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			String file = partner.getDisplayName().toUpperCase()+" & "+trader.getDisplayName().toUpperCase();
			String file_name = "C:/data/logs/"+partner.getUsername()+"/trades/"+file+".txt";
			String folder_name = "C:/data/logs/"+partner.getUsername()+"/trades";
			File folder = new File(folder_name);
			if(!folder.exists())
				folder.mkdir();
			WriteFile data = new WriteFile(file_name, true);
			data.writeToFile("["+dateFormat.format(date)+" "+partner.getSession().getIP()+"] "+partner.getUsername().toUpperCase()+" TOOK ITEMS:");
			for(Item itemAtIndex : itemsTaken.toArray()) {
				if(itemAtIndex != null) {
					data.writeToFile("Took item: "+ItemDefinitions.getItemDefinitions(itemAtIndex.getId()).getName()+" ("+itemAtIndex.getId()+") x "+itemAtIndex.getAmount());
				}
			}
			data.writeToFile("["+dateFormat.format(date)+" "+partner.getSession().getIP()+"] "+partner.getUsername().toUpperCase()+" GAVE ITEMS:");
			for(Item itemAtIndex : itemsGiven.toArray()) {
				if(itemAtIndex != null) {
					data.writeToFile("Gave item: "+ItemDefinitions.getItemDefinitions(itemAtIndex.getId()).getName()+" ("+itemAtIndex.getId()+") x "+itemAtIndex.getAmount());
				}
			}
		} catch (IOException e) {
			
		}
	}
	
	public static double GOLD_SINK_SAVE = 0;
	
	public static void saveGoldSink() {
		writeGlobalLog(""+GOLD_SINK_SAVE, "goldsink", "goldsink", false);
	}
	
	public static void writeGlobalLog(String message, String file, String type) {
		writeGlobalLog(message, file, type, false);
	}
	
	public static void writeGlobalLog(String message, String file, String type, boolean addToGoldSink) {
		if(addToGoldSink) {
			GOLD_SINK_SAVE += Double.parseDouble(message);
			return;
		}
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			String file_name = "C:/data/GL/"+file+".txt";
			WriteFile data = new WriteFile(file_name, true);
			data.writeToFile("["+dateFormat.format(date)+"]" +message);
			System.out.println(type+" Log Written!");
			String[] NEWS = {"bans", "unbans", "setrights", "permits", "achievements", "loot", "donations", "tournaments", "capeach", "lottery" };
			for(int k=0;k<NEWS.length;k++) {
				if(file.toLowerCase().equals(NEWS[k].toLowerCase())) {
					file_name = "C:/data/GL/News/"+file+".txt";
					data = new WriteFile(file_name, true);
					data.writeToFile("["+dateFormat.format(date)+"]" +message);
				}
			}
		} catch (IOException e) {
			System.out.println(type+" Log Failed.");
		}
	}
	
	public void writeToFile(String textLine) throws IOException {
		FileWriter write = new FileWriter(path, append_to_file);
		PrintWriter print_line = new PrintWriter(write);
		
		print_line.printf("%s" + "%n", textLine);
		
		print_line.close();
	}
}