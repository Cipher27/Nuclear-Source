package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Misc;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.TournamentsR;
import com.rs.utils.Utils;
import com.rs.utils.WriteFile;

public class Tournaments {

	public static Player owner;
	public static Item reward;
	public static int time = -1;
	public static int timeLapsed;
	public static String name;
	public static int skill;
	public static String leaderName;
	public static int key;
	
	public Tournaments(final Player owner, final Item reward, final int time, final String name, final int skill) {
		message(name+"'s "+Skills.SKILL_NAME[skill]+" tournament will start in 2 minutes. Prize: "+ItemDefinitions.getItemDefinitions(reward.getId()).getName()+" x"+reward.getAmount()+"!");
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 15;
			@Override
			public void run() {
				Tournaments.owner = owner;
				Tournaments.reward = reward;
				Tournaments.time = time;
				Tournaments.name = name;
				Tournaments.skill = skill;
				Tournaments.leaderName = "";
				Tournaments.timeLapsed = 0;
				Tournaments.key = Misc.random(10000000);
				World.competitionStarted = true;
				message(name+" Has Created A "+Skills.SKILL_NAME[skill]+" Tournament!</col>");
				message("Duration: "+time+":00 - Prize: "+ItemDefinitions.getItemDefinitions(reward.getId()).getName()+" x"+reward.getAmount()+"</col>");
				WriteFile.writePlayerLog(owner.getDisplayName()+" created a tournament. Time: "+time+":00 - Prize: "+ItemDefinitions.getItemDefinitions(reward.getId()).getName()+" x"+reward.getAmount()+" - Skill: "+Skills.SKILL_NAME[skill], "tournaments", owner);
				stop();
			}
		}, 120, 120);
	}
	
	public Tournaments() {
		
	}
	
	public void tick() {
		if(getTime() <= -1)
			return;
		timeLapsed++;
		if(getTimeRemaining() == 6 || getTimeRemaining() == 11 || getTimeRemaining() == 16 || getTimeRemaining() == 21) {
			message(name+"'s "+Skills.SKILL_NAME[skill]+" Tournament Is Still Running!");
			message("The Current Leader Is: "+Utils.formatPlayerNameForDisplay(getLeader())+" - Time Remaining: "+getTimeRemaining()+":00 - Prize: "+ItemDefinitions.getItemDefinitions(reward.getId()).getName()+" x"+reward.getAmount()+"!");
		}
		if(getTimeRemaining() == 0) {
			if(!leaderName.toLowerCase().equals("nobody"))
				message(name+"'s "+Skills.SKILL_NAME[skill]+" Tournament Is Over! The Winner Is "+Utils.formatPlayerNameForDisplay(leaderName)+". he Wins: "+ItemDefinitions.getItemDefinitions(reward.getId()).getName()+" x"+reward.getAmount()+"!");
			else
				message(name+"'s "+Skills.SKILL_NAME[skill]+" Tournament Is Over! Nobody Has Won The Tournament.");
			sendRewardToLeader();
			resetTournament();
			World.competitionStarted = false;
		}
	}
	
	public void forceEnd() {
		Tournaments.time = 0;
		message("The tournament has been force ended");
		World.competitionStarted = false;
		tick();
	}
	
	public void sendRewardToLeader() {
		String name = Utils.formatPlayerNameForProtocol(Tournaments.leaderName);
		if(name.equals("nobody"))
			return;
		Player winner = World.getPlayerByDisplayName(name);
		if(winner == null) {
			winner = SerializableFilesManager.loadPlayer(name);
			if(winner != null)
				winner.setUsername(name);
			winner.collectItemId = reward.getId();
			winner.collectItemAmount = reward.getAmount();
			WriteFile.writePlayerLog(winner.getDisplayName()+" won a tournament. Time: "+getLapsedTime()+":00 - Prize: "+ItemDefinitions.getItemDefinitions(reward.getId()).getName()+" x"+reward.getAmount()+" - Skill: "+Skills.SKILL_NAME[skill], "tournaments", winner);
			SerializableFilesManager.savePlayer(winner);
		} else {
			WriteFile.writePlayerLog(winner.getDisplayName()+" won a tournament. Time: "+getLapsedTime()+":00 - Prize: "+ItemDefinitions.getItemDefinitions(reward.getId()).getName()+" x"+reward.getAmount()+" - Skill: "+Skills.SKILL_NAME[skill], "tournaments", winner);
			winner.sm("You've won the tournament!");
			winner.sm("You've won: "+ItemDefinitions.getItemDefinitions(reward.getId()).getName()+" x"+reward.getAmount()+"!");
			if(winner.getInventory().getFreeSlots() >= reward.getAmount()) {
				winner.getInventory().addItem(reward);
			} else {
				winner.getBank().addItem(reward.getId(), reward.getAmount(), false);
				winner.sm("Your prize(s) have been added to your bank.");
			}
		}
	}
	
	public void resetTournament() {
		Tournaments.owner = null;
		Tournaments.reward = null;
		Tournaments.time = -1;
		Tournaments.name = "";
		Tournaments.skill = -1;
		Tournaments.leaderName = "";
		Tournaments.timeLapsed = 0;
		Tournaments.key = 0;
		TournamentsR.reset();
	}
	
	public String getLeader() {
		return TournamentsR.getLeader();
	}
	
	public boolean active() {
		return time > 0;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public Item getReward() {
		return reward;
	}
	
	public int getTime() {
		return time;
	}
	
	public int getLapsedTime() {
		return timeLapsed;
	}
	
	public int getKey() {
		return key;
	}
	
	public int getTimeRemaining() {
		return getTime() - getLapsedTime();
	}
	
	public String getTournamentName() {
		return name;
	}
	
	public int getSkill() {
		return skill;
	}
	
	public void message(String message) {
		for(Player players : World.getPlayers()) {
			if(players == null)
				continue;
			players.getPackets().sendGameMessage("<col=ff0000><img=5>"+message);
		}
	}
	
}