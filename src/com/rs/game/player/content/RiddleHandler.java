
package com.rs.game.player.content;

import java.util.Random;

import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.world.RecordHandler;
import com.rs.utils.Misc;

/**
 * @Author: paolo
 */
public class RiddleHandler {
	
	
	
	private static String puzzles [][] = { 
			  {"Figure out this anagram: 'tcas'", "cast"},
			  {"Figure out this anagram: 'gdos'", "gods"},
			  {"Figure out this anagram: 'oomn'", "moon"},
			  {"Figure out this anagram: 'ungryh'", "hungry"}, };
	
	private static String riddles [][] = { 
		  {"what comes once in a minute and twice in a moment, but never in a thousand years?", "m"},
		  {"If you say my name is disappear, who am I?", "silence"},
		  {"What has only two words, but thousands of letters?", "post office"},
		  {"It can be cracked, it can be made, it can be told, it can be played. What is it?", "jokes"},
		  {"I go in hard. Come out soft and you blow me hard. What am I?", "gum"},
		  {"What belongs to you but others use more than you?", "your name", "name"},
		  {"What gets whiter the dirtier it gets?", "chalkboard"}, 
		  {"The more you take, the more you leave behind, what am I?", "footsteps"}, 
		  {"A seven letter word containing thousands of letters", "mailbox"}, 
		  {"I'm not alive, but i grow. I don't have lungs but I need air. I don't have a mouth but water kills me. What am i ?", "fire"},
		  {"I'm better than god, more evil than the devil. The poor people have me, the rich people want me, and if you eat me you'll die. What am i?", "nothing"}};	
	
	private static String categories [][][] = { riddles, puzzles};
	
	public static int questionid = -1;
	public static int round = 0;
	public static boolean victory = false;
	public static int answers = 0;
	public static int category;

	public static void handleSpecialRewards(Player player){
		int amount = player.getPointsManager().getTotalRiddles();
		Item serjeant[] = {new Item(15021),new Item(15022),new Item(15023),new Item(15024),new Item(15025),new Item(15026)};
		Item commander[] = {new Item(15027),new Item(15028),new Item(15029),new Item(15030),new Item(15031),new Item(15032)};
		Item warChief[] = {new Item(15033),new Item(15034),new Item(15035),new Item(15036),new Item(15037),new Item(15038)};
		Item lordMarshal[] = {new Item(15039),new Item(15040),new Item(15041),new Item(15042),new Item(15043),new Item(15044)};
		switch(amount){
		case 100:
			player.sm("<col= 68f442>You have earned a set for solving 100 riddles. Next set will be at 100.");
			player.getBank().addItems(serjeant, true);
			break;
		case 300:
			player.sm("<col= 68f442>You have earned a set for solving 300 riddles.Next set will be at 200.");
			player.getBank().addItems(commander, true);
			break;
		case 500:
			player.sm("<col= 68f442>You have earned a set for solving 500 riddles.Next set will be at 1500.");
			player.getBank().addItems(warChief, true);
			break;
		case 1500:
			player.sm("<col= 68f442>You have earned a set for solving 1500 riddles.");
			player.getBank().addItems(lordMarshal, true);
			break;
		}
	}
	
	public static void Run() {
		category = Misc.random(0, 2);
		int rand = RandomQuestion(category);
		questionid = rand;
		answers = 0;
		victory = false;
		String title = "Riddle me this:";
		for(Player participant : World.getPlayers()) {
			if(participant == null)
				continue;
			if(!participant.riddleMessages)
				continue;
				participant.hasAnswered = false;
				participant.getPackets().sendGameMessage("<col=56A5EC>["+title+"] "+categories[category][rand][0]+"</col>");
		}
	}
	
	public static void sendRoundWinner(String winner, Player player) {
		for(Player participant : World.getPlayers()) {
			if(participant == null)
				continue;
			if(!player.riddleMessages)
				continue;
			if (answers <= 5) {
				answers++;
				if (answers == 5)
					victory = true;
				player.getPointsManager().setRiddelTokens(player.getPointsManager().getRiddelTokens() +1);
				player.getPointsManager().addTotelRiddles();
				handleSpecialRewards(player);
				RecordHandler.getRecord().handelTrivia(player);
				player.getPackets().sendGameMessage("<col=56A5EC>[Riddle] "+winner+", you now have "+player.getPointsManager().getRiddelTokens()+" Riddle Points.</col>");
				player.hasAnswered = true;
				World.sendWorldRiddle("<col=56A5EC>[Winner] <col=FF0000>"+ winner +"</col><col=56A5EC> answered the question correctly ("+answers+"/5)!</col>", false);
				return;
			}
		}
	}
	
	public static void verifyAnswer(final Player player, String answer) {
		if(victory) {
			player.getPackets().sendGameMessage("That round has already been won, wait for the next round.");
		} else if (player.hasAnswered) {
			player.getPackets().sendGameMessage("You have already answered this question.");
		} else if(categories[category][questionid][1].equalsIgnoreCase(answer)) {
			round++;
			sendRoundWinner(player.getDisplayName(), player);
		} else {
			player.getPackets().sendGameMessage("That answer wasn't correct, please try it again.");
		}
	}
	
	public static int RandomQuestion(int i) {
		int random = 0;
		Random rand = new Random();
		random = rand.nextInt(categories[i].length);
		return random;
	}
	
}
