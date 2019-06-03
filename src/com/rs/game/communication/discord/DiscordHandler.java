package com.rs.game.communication.discord;

import com.cryo.DiscordBot;
import com.cryo.DiscordSettings;

/**
 * 
 * @author paolo
 *
 */
public class DiscordHandler {

	public static DiscordBot bot;
	
	/**
	 * launches the bot
	 */
	public static   void init(){
//		bot = new DiscordBot(DiscordSettings.build("MzU5MzM0MzAxMTU1OTgzMzYy.DKP4jA.j6vnKCVvQl40K-6l08uqfId4RKg"));
//		bot.run();
//		bot.registerCommandListener(new DiscordInputCommands(bot, ".")); 
	}
	/*
	 * sends a message to the discord
	 */
	public static void sendMessage(String input){
		bot.sendMessage("356889862236012548",input);
	}
	
	
	public static DiscordBot getBot() {
		return bot;
	}
	public static void setBot(DiscordBot bot) {
		DiscordHandler.bot = bot;
	}

}
