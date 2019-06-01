package com.rs.cores.mysql.impl;

import com.rs.Settings;
import com.rs.cores.mysql.Database;
import com.rs.game.WorldConstants;

public class CapeCounting implements Runnable {

	@Override
	public void run() {
		try {
			Database db = new Database(Settings.DB_HOST, Settings.DB_USER, Settings.DB_PASS, Settings.DB_NAME);  
			
			if (!db.init()) {
				System.err.println("Failed connecting to database!");
				return;
			}
			db.executeUpdate("UPDATE skillcape_count SET maxed = "+WorldConstants.getMaxedUsers()+"");
			db.executeUpdate("UPDATE skillcape_count SET attack = "+WorldConstants.attack120+"");
			db.executeUpdate("UPDATE skillcape_count SET strength = "+WorldConstants.strength120+"");
			db.executeUpdate("UPDATE skillcape_count SET defence = "+WorldConstants.defence120+"");
			db.executeUpdate("UPDATE skillcape_count SET ranged = "+WorldConstants.ranged120+"");
			db.executeUpdate("UPDATE skillcape_count SET prayer = "+WorldConstants.prayer120+"");
			db.executeUpdate("UPDATE skillcape_count SET magic = "+WorldConstants.magic120+"");
			db.executeUpdate("UPDATE skillcape_count SET runecrafting = "+WorldConstants.runecrafting120+"");
			db.executeUpdate("UPDATE skillcape_count SET construction = "+WorldConstants.construction120+"");
			db.executeUpdate("UPDATE skillcape_count SET dungeoneering = "+WorldConstants.dungeoneering120+"");
			db.executeUpdate("UPDATE skillcape_count SET hitpoints = "+WorldConstants.constitution120+"");
			db.executeUpdate("UPDATE skillcape_count SET agility = "+WorldConstants.agility120+"");
			db.executeUpdate("UPDATE skillcape_count SET herblore = "+WorldConstants.herblore120+"");
			db.executeUpdate("UPDATE skillcape_count SET thieving = "+WorldConstants.thieving120+"");
			db.executeUpdate("UPDATE skillcape_count SET crafting = "+WorldConstants.crafting120+"");
			db.executeUpdate("UPDATE skillcape_count SET fletching = "+WorldConstants.fletching120+"");
			db.executeUpdate("UPDATE skillcape_count SET slayer = "+WorldConstants.slayer120+"");
			db.executeUpdate("UPDATE skillcape_count SET hunter = "+WorldConstants.hunter120+"");
			db.executeUpdate("UPDATE skillcape_count SET mining = "+WorldConstants.mining120+"");
			db.executeUpdate("UPDATE skillcape_count SET smithing = "+WorldConstants.smithing120+"");
			db.executeUpdate("UPDATE skillcape_count SET fishing = "+WorldConstants.fishing120+"");
			db.executeUpdate("UPDATE skillcape_count SET cooking = "+WorldConstants.cooking120+"");
			db.executeUpdate("UPDATE skillcape_count SET firemaking = "+WorldConstants.firemaking120+"");
			db.executeUpdate("UPDATE skillcape_count SET woodcutting = "+WorldConstants.woodcutting120+"");
			db.executeUpdate("UPDATE skillcape_count SET farming = "+WorldConstants.farming120+"");
			db.executeUpdate("UPDATE skillcape_count SET  summoning = "+WorldConstants.summoning120+"");
			System.out.println("DB: skillcape count loaded");
		} catch (Exception e) {
			System.err.println("Failed loading cape counting information...");
		}
	}
}
