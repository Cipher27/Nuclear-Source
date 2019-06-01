package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
import com.rs.utils.Colors;

public class HelpBot {
	
	public static String[][] wittyResponseData = {
		{"penis", "The word penis is an odd thing to ask a server database.."},
		{"whore", "A study in 2009 showed the estimate of 400,000 prostitutes in Germany."},
		{"idiot", "If anyones an idiot it's the one talking to a database."},
		{"suck", "I'm not in the mood for sucking."},
		{"dick", "No you."},
		{"swag", "Really ...?"},
		{"yolo", "Really ...?"},
		{"***", "Why would you ask me such a thing? Ofc I wan't ***!"},
		{"**** you", "The good ol' ****aroo."},
		{"stupid bot", "Oh, if you're much smarter, how come you don't have my job?"},
	};

	public static String[][] knowledgeData = {
		{"skype", "We don't use skype, join our discord instead!"},
		{"your name", "Whats a name?"},
		{"money making", "The easy achievements are a great way to make starter money."},
		{"forum", "You can find our forum at by clicking the quest tab'"},
		{"website", "You can find our website at www.Hellion718.com"},
		{"rule", "You can find a list of our rules by typing '::rules'"},
		{"command", "You can find a list of our commands by typing '::commands'"},
		{"report", "Report all griefers! '::report cheater reason'"},
		{"scam", "Report all griefers! '::report cheater reason'"},
		{"lure", "Report all griefers! '::report cheater reason'"},
		{"hack", "Report all griefers! '::report cheater reason'"},
		{"bug", "Report all bugs! '::bug 'description'. If you need assistance contact any mod."},
		{"glitch", "Report all bugs! '::bug 'description' If you need assistance contact any mod."},
		{"dupe", "Report all bugs! '::bug 'description' If you need assistance contact any mod."},
		{"stuck", "If you are stuck, please contact a Player Moderator."},
		{"clan", "You can make a clan by clicking on the 'join clanchat' button."},
		{"webclient", "There's no webclient at the moment"},
		{"contact", "You can contact player moderators via discord or ingame via PM."},
		{"jail", "If you are jailed you will have to do your penalty time."},
		{"donate", "You can donate towards the server to help us grow! More info on forum!"},
		{"vote", "You can vote for the server by typing ::vote and claim by typing ::check"},
		{"time", "I'm not a clock."},
		{"power tokens", "Use these tokens to buy special combat/skilling items."},
		{"gwd", "gwd means godwars dungeon, here you can find 4 god bosses."},
		{"godwars", "One of the biggest dungeons with god bosses."},
		{"training locations", "Use the portal at home for teleports to training locations, rock crabs are a good begin."},
		{"quest", "There are 2 quests ingame, you can look them up at the quest guide."},
	};
	
	public static void checkForAnswers(Player p, String question){
		boolean matched = false;
		int maxAnswers = 3;
		int loopCount = 0;
		p.sendMessage("Asking the database about: "+(question));
		for (int i = 0; i < wittyResponseData.length; i++) {
			if(question.toLowerCase().contains(wittyResponseData[i][0])){
				p.sendMessage("<col=800000>Help Bot: </col>"+wittyResponseData[i][1]);
				break; //To answer only to the given words
			}
		}
		for (int i = 0; i < knowledgeData.length; i++) {
			if(maxAnswers < loopCount) return;
			if(question.toLowerCase().contains(knowledgeData[i][0])){
				p.sendMessage(Colors.red+"Help Bot:"+Colors.white+knowledgeData[i][1]);
				matched = true;
				loopCount++;
				//return;
			}
		}
		if(!matched){
			p.sendMessage("<col=800000>Help Bot: Sorry, I did not find a match in our database for that question.");
		}
	}
}