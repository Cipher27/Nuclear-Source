package com.rs.cores.mysql.impl;

import java.sql.ResultSet;

import com.rs.Settings;
import com.rs.cores.mysql.Database;

public class GetValues implements Runnable {
	 
	
//works
	@Override
	public void run() {
		try {
			Database db = new Database(Settings.DB_HOST, Settings.DB_USER, Settings.DB_PASS, Settings.DB_NAME);  
			
			if (!db.init()) {
				System.err.println("Can't init!");
				return;
			}
			ResultSet rs = db.executeQuery("SELECT MAX FROM special_capes");
			 while(rs.next()){
             //WorldConstants.se  = rs.getInt("MAX");
			 }
			System.out.println("values loaded:");
		} catch (Exception e) {
			System.err.println("Failed loading database information..");
		}
	}
}
