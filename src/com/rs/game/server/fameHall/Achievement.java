package com.rs.game.server.fameHall;

import java.util.Date;

public class Achievement {
	
	private String PlayerName;
	private Date Time;
	private String Description;
	
	/**
	 * constructor
	 * @param name
	 * @param time
	 * @param desc
	 */
	public Achievement(String name, Date time,String desc){
		this.PlayerName = name;
		this.Time = time;
		this.Description = desc;
	}

}
