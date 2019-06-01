package com.rs.cores.mysql;

import com.rs.Settings;

public class DatabaseLoader implements Runnable {

	@Override
	public void run() {
		try {
			Database db = new Database(Settings.DB_HOST, Settings.DB_USER, Settings.DB_PASS, Settings.DB_NAME);  
			
			if (!db.init()) {
				System.err.println("Failed connecting to database!");
				return;
			}
			
			
			System.out.println("Loaded information from database!");
			db.destroyAll();
		} catch (Exception e) {
			System.err.println("Failed loading database information..");
		}
	}
}
