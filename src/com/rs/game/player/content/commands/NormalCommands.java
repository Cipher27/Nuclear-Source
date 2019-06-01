package com.rs.game.player.content.commands;

import com.motiservice.vote.*;
import com.everythingrs.donate.Donation;
import com.motiservice.Motivote;
import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.FarmingManager;
import com.rs.game.player.Player;
import com.rs.game.player.achievements.impl.CrystalChestAchievement;
import com.rs.game.player.achievements.impl.EasyVoteAchievement;
import com.rs.game.player.achievements.impl.SafetyAchievement;
import com.rs.game.player.content.PollManager;
import com.rs.game.player.content.RiddleHandler;
import com.rs.game.player.content.custom.HelpBot;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.DungeonControler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.utils.Colors;
import com.rs.utils.Donations;
import com.rs.utils.Utils;

public class NormalCommands {
	
	public static final Motivote MOTIVOTE = new Motivote("Zaria", "540ab36679b708ed2017e1d7f6a33ed0");
	
	public static boolean processNormalCommand(Player player, String[] cmd,
			boolean console, boolean clientCommand) {
			String message;
			String message1;
			Player target;
			String pass;
			switch (cmd[0]) {
			
			case "claimvote":
				String username = player.getDisplayName();
				Result r2;
				try {
					r2 = MOTIVOTE.redeem(SearchField.USER_NAME, username);
					if (r2.success()) {
						player.getAchievementManager().notifyUpdate(EasyVoteAchievement.class);
						int total = r2.votes().size();
						player.sm("Thanks for voting, you received "+total+" vote points.");
						player.getPointsManager().setVoteTokens(player.getPointsManager().getVoteTokens() + total);
					
					} else {
						player.sm("No vote found.");
					}
				} catch (Exception e1) {
				
					player.sm("No vote found.");
				}
				
				break;
				
			case "addslayermasks":
				for(int i = 0; i < player.getInventory().getItems().getSize(); i ++){
					if(!player.getSofItems2().contains(player.getInventory().getItems().get(i).getId())){
					player.getSofItems2().add(player.getInventory().getItems().get(i).getId());
					player.sm(""+player.getInventory().getItems().get(i).getName());
					}
					
				}
				return true;
			case "reward":
    						try {
    							if(Integer.parseInt(cmd[1]) != 1){
    								player.sm("This id has no reward.");
    								return false;
    							}
    							int id = 1;
    							String playerName = player.getUsername();
    							final String request = com.everythingrs.vote.Vote.validate("30wtnf0kxrahmrgcg7uru4ygb9yad8o8xeuijy23dtua3wn4s4io0s0wiwc3be8t9owl3rk65hfr", playerName, id);
    							String[][] errorMessage = {
    									{ "error_invalid", "There was an error processing your request." },
    									{ "error_non_existent_server", "This server is not registered at EverythingRS." },
    									{ "error_invalid_reward", "The reward you're trying to claim doesn't exist." },
    									{ "error_non_existant_rewards", "This server does not have any rewards set up yet." },
    									{ "error_non_existant_player", "There is not record of user " + playerName + " make sure to vote first." },
    									{ "not_enough", "You do not have enough vote points to recieve this item." } };
    							for (String[] message2 : errorMessage) {
    								if (request.equalsIgnoreCase(message2[0])) {
    									player.sm((message2[1]));
    									return false;
    								}
    							}
    							if (request.startsWith("complete")) {
    								player.getPointsManager().setVoteTokens(player.getPointsManager().getVoteTokens() +1);
    								player.sm("You have recieved 1 vote point, thanks for voting.");
    
    							}
    						} catch (Exception e) {
    							player.sm(("Our API services are currently offline. We are working on bringing it back up."));
    							e.printStackTrace();
    						}
    				//	}
    				//}.start();
    			//}
    			return true;
			
			//Credits to mateo from rune-server for this
			case "itemlookup":
				StringBuilder itemNameSB = new StringBuilder(cmd[1]);
				if (cmd.length > 1) {
					for (int i = 2; i < cmd.length; i++) {
						itemNameSB.append(" ").append(cmd[i]);
					}
				}
				String itemName = itemNameSB.toString().toLowerCase().replace("[", "(").replace("]", ")").replaceAll(",", "'");
				for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
					ItemDefinitions def = ItemDefinitions.getItemDefinitions(i);
					if (def.getName().toLowerCase().equalsIgnoreCase(itemName)) {
						player.stopAll();
						player.getInterfaceManager().sendItemDrops(def);
						return true;
					}
				}
				player.sendMessage("Could not find any item by the name of ''" + itemName + "''.");
				return true;
			case "dailytask":
			player.sm("Daily Task: <col=ffffff>"+(player.getDailyTask() != null ? player.getDailyTask().reformatTaskName(player.getDailyTask().getName()) : "Null ?")+"</col><br>");
			player.sm("Amount: <col=ffffff>"+(player.getDailyTask() != null ? player.getDailyTask().getAmountCompleted()+"/"+player.getDailyTask().getTotalAmount() : "Null; tell Hc747")+"</col><br><br>");
			return true;
			case "commands":
			player.sm("Look in the quest tab for all the commands.");
			return true;
			
			case "claim":
				new Thread() {
					public void run() {
						try {
							Donation[] donations = Donation.donations("30wtnf0kxrahmrgcg7uru4ygb9yad8o8xeuijy23dtua3wn4s4io0s0wiwc3be8t9owl3rk65hfr", player.getDisplayName());
							if (donations.length == 0) {
								player.sendMessage("You currently don't have any items waiting. You must donate first!");
								return;
							}
							if (donations[0].message != null) {
								player.sendMessage(donations[0].message);
								return;
							}
							for (Donation donate : donations) {
								player.sm("id: "+donate.product_id);
								switch(donate.product_id){
								case 1:
									player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 10);
									player.setDonator(true);
							    break;
								case 2:
									player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 20);
									player.setExtremeDonator(true);
							    break;
								}
							}
							player.sendMessage("Thank you for donating!");
						} catch (Exception e) {
							player.sendMessage("Api Services are currently offline. Please check back shortly");
							e.printStackTrace();
						}	
					}
				}.start();
			return true;
			case "spotlight":
			player.sm("<img=6><col=FFA500><shad=000000>The Spotlight skills are "+Colors.green+""+World.getSpotLightCombatSkillName()+"</col> and "+Colors.green+""+World.getSpotLightSkillName()+"</col>.");
			return true;
			case "npclookup":
				StringBuilder npcNameSB = new StringBuilder(cmd[1]);
				if (cmd.length > 1) {
					for (int i = 2; i < cmd.length; i++) {
						npcNameSB.append(" ").append(cmd[i]);
					}
				}
				String npcName = npcNameSB.toString().toLowerCase().replace("[", "(").replace("]", ")").replaceAll(",", "'");
				NPCDefinitions def1;
				switch(npcName.toLowerCase()){
				//NPCDefinitions def ;
				case "dark lord":
				case "dark_lord":
					def1 = NPCDefinitions.getNPCDefinitions(19553);
					player.stopAll();
					player.getInterfaceManager().sendNPCDrops(def1);
				return true;
				case "blink":
					def1 = NPCDefinitions.getNPCDefinitions(12878);
					player.stopAll();
					player.getInterfaceManager().sendNPCDrops(def1);
					return true;
				//}
					
				}
				for (int i = 0; i < Utils.getNPCDefinitionsSize(); i++) {
					NPCDefinitions def = NPCDefinitions.getNPCDefinitions(i);
					try {
						if (def.name.toLowerCase().equalsIgnoreCase(npcName)) {
							player.stopAll();
							player.getInterfaceManager().sendNPCDrops(def);
							return true;
							}
						
					} catch (Exception e) {
				    	System.out.println("NPC Def not found for index: " + i);
				    	player.sendMessage("Could not find any NPC by the name of ''" + npcName + "'' make sure you type it the right way.");
						return true;
					}
				}
				


			
				
		     case "askbot":
			 String question = "";
			 for (int i = 1; i < cmd.length; i++) {
					question += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				}
			 HelpBot.checkForAnswers(player, question);
			 return true;

			case "empty":
				player.getDialogueManager().startDialogue("EmptyConfirm");
				return true; 
			
			case "resetfarming":
				player.farmingManager = new FarmingManager();
				return true;
			case "recov1":
			String answer = "";
			 for (int i = 1; i < cmd.length; i++) {
					answer += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				}
			if(answer == "")  
				player.sm("Recovery questions not set, usage ;;recov1 answer");
			else{
				player.recov1 = answer;
				player.recov1Set = true;
				player.getAchievementManager().notifyUpdate(SafetyAchievement.class);
				player.sm("Recovery question set, make sure you set them all. Do not forget this, write it down somewhere : "+answer+".");
			}
			return true;
			
			case "recov2":
				 answer = "";
				 for (int i = 1; i < cmd.length; i++) {
						answer += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
				if(answer == "")  
					player.sm("Recovery questions not set, usage ;;recov2 answer");
				else{
					player.recov2 = answer;
					player.recov2Set = true;
					player.getAchievementManager().notifyUpdate(SafetyAchievement.class);
					player.sm("Recovery question set, make sure you set them all. Do not forget this, write it down somewhere : "+answer+".");
				}
				return true;
			case "recov3":
				 answer = "";
				 for (int i = 1; i < cmd.length; i++) {
						answer += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
				if(answer == "")  
					player.sm("Recovery questions not set, usage ;;recov3 answer");
				else{
					player.recov3 = answer;
					player.recov3Set = true;
					player.getAchievementManager().notifyUpdate(SafetyAchievement.class);
					player.sm("Recovery question set, make sure you set them all. Do not forget this, write it down somewhere : "+answer+".");
				}
				return true;

		
			

			
			case "donate":
				player.sm("Use the ;;claim command to claim your donation.");
				player.getPackets().sendOpenURL("adding soon");
				return true; 
	
			case "hideyell":
				player.setYellOff(!player.isYellOff());
				player.getPackets().sendGameMessage("You have turned " +(player.isYellOff() ? "off" : "on") + " yell.");
				return true;
			case "votey":
				PollManager.addPlayerToPoll(player, "yes");
				return true;
			

			case "voten":
				PollManager.addPlayerToPoll(player, "no");
				return true;
				
			case "event":
				if (World.bandos == true) {
				player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Bandos! No Kill Count required!");
				} else if (World.armadyl == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Armadyl! No Kill Count required!");
				} else if (World.zamorak == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Zamorak! No Kill Count required!");	
				} else if (World.saradomin == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Saradomin! No Kill Count required!");	
				} else if (World.dungeoneering == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Group Dungeoneering event! Come explore Dungeons with Double EXP and Tokens!");	
				} else if (World.cannonball == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] The furnaces have improved, take advantage to make 2x the amount of Cannonballs!");	
				} else if (World.doubleexp == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Enjoy some Double Exp in every skill! Take advantage to max out!");	
				} else if (World.nex == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Nex! Get your torva now!");	
				} else if (World.sunfreet == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Sunfreet! Get your riches now!");	
				} else if (World.corp == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Corp! Get your sigils now!");	
				} else if (World.doubledrops == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Double Drops are now on, take advantage and gain double the wealth!");	
				} else if (World.slayerpoints == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Gain double the amount of slayer points when completing a task!");	
				} else if (World.moreprayer = true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Gain double the amount Prayer EXP when burying or using an altar!");	
				} else if (World.quadcharms == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Monsters are now dropping quadruple the amounts of charms!");	
				} else {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Unfortunately, there are no events occuring this hour!");	
				}
				return true; 
				
		
			case "home":
                Magic.sendArcaneTeleportSpell(player, 0, 0, new WorldTile(2337,3689, 0));
                return true; 
			
			
			case "qhome":
			if (!player.getControlerManager().processItemTeleport(player.getTile()))
					return false;
			if(Wilderness.isAtWild(player)){
				player.sm("You can't use this teleport in the wilderness.");
				return true;
			}
			if(player.getControlerManager().getControler() instanceof DungeonControler){
					player.sm("You can't teleport.");
					return false;
			}
			if(player.quickTeleports > 0 ||player.getRights() == 2 || player.isLegendaryDonator()){
				player.resetReceivedDamage();
				player.getControlerManager().magicTeleported(3);
                Magic.sendCustomTeleportSpell(player, 0, 0, new WorldTile(1632,6176, 0));
				player.quickTeleports--;
				player.sm("You have "+player.quickTeleports+" left for today.");
			}else
				player.sm("You don't have any quick teleports left for today.");
                return true;
                
			
			case "printpass":
				player.sendMessage("you pass is "+player.instancePass+ ".");
		
		
			case "changepass":
                message1 = "";
                for (int i = 1; i < cmd.length; i++) {
                    message1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                if (message1.length() > 15 || message1.length() < 5) {
                    player.getPackets().sendGameMessage(
                            "You cannot set your password to over 15 chars.");
                    return true;
                }
                player.setPassword(cmd[1]);
                player.getPackets().sendGameMessage(
                        "You changed your password! Your password is " + cmd[1]
                        + ".");
                return true;

       		
				
			case "players":
				player.getInterfaceManager().sendInterface(275);
                int number = 0;
                for (int i = 0; i < 100; i++) {
                    player.getPackets().sendIComponentText(275, i, "");
                }
                for (Player p5 : World.getPlayers()) {
                    if (p5 == null) {
                        continue;
                    }
                    number++;
                    String titles = "";
                    if (!(p5.isDonator()) || !p5.isExtremeDonator()) {
                        titles = "<col=000000>[Player]";
                    }
                    if (p5.isVeteran() ) {
                        titles = "<col=23238E><shad=ffffff>[Veteran]";
                    }
                    if (p5.isDonator()) {
                        titles = "<col=008000>[Donator]";
                    }
                    if (p5.isExtremeDonator()) {
                        titles = "<col=FF0000>[Extreme Donator]";
                    }
                    if (p5.isLegendaryDonator()) {
                        titles = "<col=0066CC>[Legendary Donator]";
                    }
                    if (p5.isSupremeDonator()) {
                    	titles = "<col=ffa34c>[Supreme Donator]";
                    }
                    if (p5.isDivineDonator()) {
                    	titles = "<col=6C21ED>[Divine Donator]";
                    }
                    if (p5.isAngelicDonator()) {
                    	titles = "<col=ffffff>[Angelic Donator]";
                    }
                    
                    if (p5.isSupporter()) {
                        titles = "<col=00ff48>[Support]<img=14>";
                    }
                    if (p5.getRights() == 1) {
                        titles = "<col=bcb8b8>[Moderator]<img=0>";
                    }
                    if (p5.isAnthonyRank == true) {
                        titles = "<col=996633>(Anthony Rank) </col>";
                    }
                    if (p5.getRights() == 2) {
                        titles = "<col=ff1d1d>[Admin]";
                    }
                  
          
                    
                    if (p5.getDisplayName().equalsIgnoreCase("")) {
                        titles = "<col=8A2BE2>[Developer]<img=1>";
                    }
                    if (p5.getDisplayName().equalsIgnoreCase("Zero_Gravity")) {
                        titles = "<col=0000FF>[Main Owner]<img=1>";
                    }
                   
                    if (p5.getDisplayName().equalsIgnoreCase("")) {
                        titles = "<col=0000FF>[The Vegetable]<img=1>";
                    }
					if (p5.getDisplayName().equalsIgnoreCase("") && (p5.getRights() == 2)) {
                        titles = "<img=1> <col=0000FF>Developer]";
                    }
               
                    player.getPackets().sendIComponentText(275, (12 + number), titles + "" + p5.getDisplayName());
                }
                player.getPackets().sendIComponentText(275, 1, "Thatscape Players");
                player.getPackets().sendIComponentText(275, 10, " ");
                player.getPackets().sendIComponentText(275, 11, "Players Online: " + (number));
                player.getPackets().sendIComponentText(275, 12, " ");
                player.getPackets().sendGameMessage(
                        "There are currently " + (World.getPlayers().size())
                        + " players playing " + Settings.SERVER_NAME
                        + ".");
                return true;
                     
			case "yell":
				if (player.isDonator() || player.getUsername().equalsIgnoreCase("Zero_Gravity") || player.isExtremeDonator() || player.isLegendaryDonator() || player.getRights() == 1 || player.getRights() == 2 || player.isSupporter()) {
				message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Commands.sendYell(player, Utils.fixChatMessage(message), false);
				return true; 
				} else {
					player.getPackets().sendGameMessage("You must be a donator or staff to use this command, instead talk in the Hellion Friends Chat.");
				//s	player.getPackets().sendGameMessage("You must have a total level of over 450 to yell.");
					return true;
				}
				
			case "answer":
				if (cmd.length >= 2) {
					 answer = cmd[1];
					if (cmd.length == 3) {
						answer = cmd[1] + " " + cmd[2];
					}
					if (cmd.length == 4) {
						answer = cmd[1] + " " + cmd[2] + " " + cmd[3];
					}
					if (cmd.length == 5) {
						answer = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4];
					}
					if (cmd.length == 6) {
						answer = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4] + " " + cmd[5];
					}
					RiddleHandler.verifyAnswer(player, answer);
				} else {
					player.getPackets().sendGameMessage(
							"Syntax is ::" + cmd[0] + " <answer input>.");
				}
				return true;
                
            case "vote":
			    player.sm("To claim your vote type ;;claimvote");
                player.getPackets().sendOpenURL("coming soon");
                return true;
               
            case "discord":
            	 player.getPackets().sendOpenURL("discord");
            	return true; // https://discord.gg/XxKXEc9
                
		


			}
		return true;
	}

}
