package com.rs.game.communication.discord;


import com.cryo.DiscordBot;
import com.cryo.listener.CommandListener;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.content.DropSimulator;
import com.rs.utils.Colors;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

import sx.blah.discord.handle.obj.IMessage;

/**
 * 
 * @author paolo
 * handles the command which are typed in the discord chanel
 *
 */

public class DiscordInputCommands  extends CommandListener {

	/**
	 * super constructor
	 * @param bot
	 * @param commandPrefix
	 */
	public DiscordInputCommands(DiscordBot bot, String commandPrefix) {
		super(bot, commandPrefix);
	}

	/**
	 * all commands here
	 */
	@Override
	public void handleCommand(IMessage message, String command, String[] cmd) {
		cmd[0] = cmd[0].toLowerCase();
		String channelId = message.getChannel().getStringID();//.getID();
		switch(cmd[0]) {
		case "commands":
			//352173577618849812 spam id
			bot.deleteMessage(message);
			bot.sendMessage("352173577618849812", 
					"__**Commands**__ \n"
					+"You can use those commands by typing . followed by the command name. \n"
					+"__**Command List**__ \n"
					+ "**playersonline** : gives you the amount of players online. \n"
					+ "**playerlookup** : gives you stats of the certain players.\n"
					+ "**spotlight** : gives you the current spotlight skills.\n"
					+ "**getdrops [npc name]** : gives you the drops of that given npc. \n");
			break;
		case "playersonline":
			bot.deleteMessage(message);
			bot.sendMessage("352173577618849812", "There are : __**"+World.getPlayers().size()+"**__ players online.");
			break;
		case "spotlight":
			bot.deleteMessage(message);
			bot.sendMessage("352173577618849812", "The Spotlight skills are "+World.getSpotLightCombatSkillName()+" and "+World.getSpotLightSkillName());
			break;
		case "playerlookup":
			Player player = World.getPlayer(cmd[1]);
			if(player == null)
				player = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(cmd[1]));
			if(player == null){
				bot.sendMessage("352173577618849812", ""+cmd[1]+ " was not found in our database.");
				return;
			}
			StringBuilder sb = new StringBuilder("");
			sb.append("```");
			sb.append(" "+cmd[1]+"\n"
					+"Combat level: "+player.getSkills().getCombatLevel()+"\n"
					+"Total level: "+player.getSkills().getTotalLevel()+"\n \n");
			for(int i = 0 ; i < 25; i ++)
				sb.append(""+player.getSkills().SKILL_NAME[i] + " : "+player.getSkills().getLevel(i)+ "\n");
			bot.deleteMessage(message);
			sb.append("```");
			bot.sendMessage("352173577618849812", ""+sb);
			break;
		case "sendmessage":
			DiscordHandler.sendMessage("test");
			bot.sendMessage(channelId, cmd[1]);
			break;
		case "getdrops":
			StringBuilder npcNameSB = new StringBuilder(cmd[1]);
			if (cmd.length > 1) {
				for (int i = 2; i < cmd.length; i++) {
					npcNameSB.append(" ").append(cmd[i]);
				}
			}
			NPCDefinitions def1;
			String npcName = npcNameSB.toString().toLowerCase().replace("[", "(").replace("]", ")").replaceAll(",", "'");
			switch(npcName.toLowerCase()){
			//NPCDefinitions def ;
			case "dark lord":
			case "dark_lord":
				def1 = NPCDefinitions.getNPCDefinitions(19553);
				DropSimulator.sendNPCDrops(def1);
				return;
			case "blink":
				def1 = NPCDefinitions.getNPCDefinitions(12878);
				DropSimulator.sendNPCDrops(def1);
				return;
			}
			for (int i = 0; i < Utils.getNPCDefinitionsSize(); i++) {
				NPCDefinitions def = NPCDefinitions.getNPCDefinitions(i);
				if (def.name.toLowerCase().equalsIgnoreCase(npcName)) {
					bot.sendMessage(channelId, ""+DropSimulator.sendNPCDrops(def));
					return;
					}
				}
			bot.sendMessage(channelId, "Could not find any NPC by the name of ''" + npcName + "'' make sure you type it the right way.");
			
		}
	}

} 

