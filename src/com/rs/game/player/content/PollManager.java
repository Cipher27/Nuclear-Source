package com.rs.game.player.content;

import java.util.ArrayList;
import java.util.Collections;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Colors;

/**
 * 
 * @author paolo
 *
 */
public class PollManager {
	
	//@ Paolo, ingame polls
	
	//poll variables
	private static String poll_description = "";
	static ArrayList<Player> players = new ArrayList<>();
	static ArrayList<String> answers = new ArrayList<>();
	public static boolean isPoll;
	/**
	 * get the current pol
	 * @param
	 * @return
	 */
	public static String getPoll(){	
		return poll_description;
		
	}/**
	 * sets the current spol
	 * @param poll
	 * @return
	 */
	public static void setPoll(String poll){
		isPoll = true;
		poll_description = poll;
		sendPoll();
	}
	
	public static void sendPoll(){
		World.sendWorldMessage("<col=4253f4>===================================",false);
		World.sendWorldMessage("New poll: "+getPoll(),false);
		World.sendWorldMessage("Type ::votey for yes, and ::voten for no.",false);
		World.sendWorldMessage("<col=4253f4>===================================",false);
	}
	//adds the player to the arraylist
	public static void addPlayerToPoll(Player player, String answer){
		if (!isPoll) {
			player.sm("There's no active poll.");
			return;	
		} if (players.contains(player)){
			player.sm("You've already voted");
			return;
		}
		if(answer == "yes"){
			answers.add("yes");
			player.sm("You have successfully voted yes.");
			players.add(player);
		} else if( answer == "no"){
			answers.add("no");
			players.add(player);
			player.sm("You have successfully voted no.");
		}
	}
	/*
      gives the results
	*/
	public static void getResults(){
		int totalVotes = answers.size();
		int yes = Collections.frequency(answers, "yes");;
		int no = Collections.frequency(answers, "no");;
		World.sendWorldMessage("Total votes:" +totalVotes,false);
		World.sendWorldMessage(Colors.green+"Yes votes: "+yes,false);
		World.sendWorldMessage(Colors.red+"No votes: "+no,false);
		
	}
	/**
	 * to clear the votes of a certain poll
	 * @param pollId
	 */
	
	public static void clearPolVotes(){
	isPoll = false;
	players.clear();
	answers.clear();
	}
    
}