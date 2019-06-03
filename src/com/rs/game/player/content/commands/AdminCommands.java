package com.rs.game.player.content.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rs.Launcher;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.Region;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.communication.discord.DiscordHandler;
import com.rs.game.item.DegradeAbleItem;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.minigames.FightPits;
import com.rs.game.minigames.clanwars.ClanWars;
import com.rs.game.minigames.clanwars.WallHandler;
import com.rs.game.minigames.warbands.Warbands;
import com.rs.game.minigames.warbands.Warbands.WarbandEvent;
import com.rs.game.npc.NPC;
import com.rs.game.npc.others.Bork;
import com.rs.game.player.ClueScrolls;
import com.rs.game.player.Player;
import com.rs.game.player.PublicChatMessage;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.Types;
import com.rs.game.player.achievements.impl.SlayerDummyAchievement;
import com.rs.game.player.actions.divination.HarvestWisp;
import com.rs.game.player.actions.slayer.SlayerTasks;
import com.rs.game.player.content.ClanCapeCustomizer;
import com.rs.game.player.content.Highscores;
import com.rs.game.player.content.LegendaryPet;
import com.rs.game.player.content.LividFarmHandler;
import com.rs.game.player.content.PollManager;
import com.rs.game.player.content.RecipesHandler;
import com.rs.game.player.content.Slayer.SlayerTask;
import com.rs.game.player.content.SpanStore;
import com.rs.game.player.content.SpringCleaner;
import com.rs.game.player.content.XPWell;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.game.player.content.ports.Voyages.Regions;
import com.rs.game.player.content.ports.crew.Captain;
import com.rs.game.player.content.ports.crew.Crew;
import com.rs.game.player.content.thieving.pyramidplunder.Game;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.PestInvasion;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.events.DeathEvent;
import com.rs.game.player.cutscenes.Cutscene;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.quest.impl.HalloweenEvent;
import com.rs.game.player.robot.Robot;
import com.rs.game.server.fameHall.HallOfFame;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.world.GlobalBossCounter;
import com.rs.game.world.GlobalCapeCounter;
import com.rs.game.world.GlobalItemCounter;
import com.rs.game.world.RecordHandler;
import com.rs.utils.Colors;
import com.rs.utils.IPBanL;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public class AdminCommands {

	@SuppressWarnings("resource")
	public static boolean processAdminCommand(final Player player, String[] cmd, boolean console, boolean clientCommand) {

		if (clientCommand) {
			switch (cmd[0]) {
			case "tele":
				cmd = cmd[1].split(",");
				int plane = Integer.valueOf(cmd[0]);
				int x = Integer.valueOf(cmd[1]) << 6 | Integer.valueOf(cmd[3]);
				int y = Integer.valueOf(cmd[2]) << 6 | Integer.valueOf(cmd[4]);
				player.setNextWorldTile(new WorldTile(x, y, plane));
				return true;
			}
		} else {
			String name;
			Player target;
			WorldObject object;
			Player target1;
			switch (cmd[0]) {

			case "spawnzombies":
				ArrayList<WorldTile> locations = new ArrayList<WorldTile>();
				for (int x = player.getX() - 30; x < player.getX() + 30; x++) {
					for (int y = player.getY() - 30; y < player.getY() + 30; y++)
						locations.add(new WorldTile(x, y, 0));
				}
				for (WorldTile loc : locations) {
					if (!World.canMoveNPC(loc.getPlane(), loc.getX(), loc.getY(), 1))
						continue;
					World.spawnNPC(73, loc, -1, true, true, false);
				}
			case "pettt":
				player.legendaryPet = new LegendaryPet(17780, player);
				player.getLegendaryPet().spawnPet();
				return true;

			case "envolve":
				player.getLegendaryPet().envolve();
				return true;

			case "hofx":
				HallOfFame.firstFightKiln[0] = player.getDisplayName();
				HallOfFame.firstFightKiln[1] = java.time.LocalDate.now().toString();
				return true;

			case "givedtokens":
			Player targetDonator = World.getPlayerByDisplayName(cmd[1]);
			targetDonator.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + Integer.parseInt(cmd[2]));
			targetDonator.sm(Integer.parseInt(cmd[2])+" points given.");
			player.sm("Ok.");
			return true;
			
			case "sendgfx":
				World.sendGraphics(null, new Graphics(Integer.parseInt(cmd[1]), 0, Integer.parseInt(cmd[2])), player.getWorldTile());
				return true;
			
			case "warbands":
				int random = Utils.random(WarbandEvent.values().length);
				Warbands.warband = new Warbands(random);
				return true;
			
			case "discordt":
				DiscordHandler.sendMessage("Test achievement");
				return true;
			case "giveitem":
			Player targetPlayer = World.getPlayerByDisplayName(cmd[1]);
			targetPlayer.getInventory().addItem(new Item(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3])));
			targetPlayer.sm("Item given.");
			player.sm("Ok.");
			return true;
			
			case "giveslayerpoints":
				Player target5 = World.getPlayer(cmd[1]);
				target5.getPointsManager().setSlayerPoints(target5.getPointsManager().getSlayerPoints()+ Integer.parseInt(cmd[2]));
				return true;
				
			case "givepowertokens":
				Player target6 = World.getPlayer(cmd[1]);
				target6.getPointsManager().setPowerTokens(target6.getPointsManager().getPowerTokens()+ Integer.parseInt(cmd[2]));
				return true;
			case "resetrecord":
			RecordHandler.getRecord().reset();
			RecordHandler.save();
			return true;
			case "ac":
							player.getAchievementManager().displayInformation(player);
		
			/*case 8: // easy
				player.getAchievementManager().displayAchievements(Types.EASY);
				break;
			case 10: // medium
				player.getAchievementManager().displayAchievements(Types.MEDIUM);
				break;
			case 12: // hard
				player.getAchievementManager().displayAchievements(Types.HARD);*/
			return true;
			case "ac1":
				player.getAchievementManager().displayAchievements(Types.EASY);
			return true;
			case "ac2":
				player.getAchievementManager().sendTestInterface(Types.EASY);
				return true;
			case "ac3:":
			player.getAchievementManager().notifyUpdate(SlayerDummyAchievement.class);
			return true;
			case "spawnt:":
				player.getLegendaryPet().setPetLevel(45);
				return true;
			case "testec":
				RecipesHandler.sendInterface(player);
				return true;

			case "tourn":
				player.getDialogueManager().startDialogue("Korvak");
				return true;
			case "artis":
				World.sendWorldMessageAll("" + World.artisanBonusExp);
				return true;
			case "curses":
				player.getPrayer().setPrayerBook(true);
				return true;
			case "testpvpbot":
				String usernameb = Robot.createName();
				Player robot = new Robot("abc123", player, "iksdeee", 2, 765, 545);
				robot.getSkills().passLevels(player);
				robot.setNextWorldTile(new WorldTile(player.getX(), player.getY(), player.getPlane()));
				robot.getAppearence().generateAppearenceData();
				// robot.script =
				// Information.CHEAT_SWITCH_TYPE[combatIndex][typeIndex];
				// robot.script.init(robot);
				return true;

			case "testpvpbot2":
				Player robot2 = new Robot("abc123");
				robot2.setNextWorldTile(new WorldTile(player.getX(), player.getY(), player.getPlane()));
				// robot.script =
				// Information.CHEAT_SWITCH_TYPE[combatIndex][typeIndex];
				// robot.script.init(robot);
				return true;
			case "testdung":
				player.getDialogueManager().startDialogue("DungeonEnter");
				return true;

			case "addblocktask":
				player.getBlockedSlayerTasks().add(0, player.currentSlayerTask.getTask().simpleName);
				return true;

			case "elitedung":
				player.eliteDungeon = true;
				return true;

			case "addperkpoint":
				player.getPointsManager().setPerkPoints(500);
				return true;
			case "springxd":
				SpringCleaner.convertItem(player, new Item(1163, 1));
				return true;
			case "elitedung2":
				player.eliteDungeon = false;
				return true;
			case "addblocktask2":
				player.getBlockedSlayerTasks().add("xd");
				return true;

			case "emptyblock":
				player.getBlockedSlayerTasks().clear();
				return true;

			case "pptest":
				Game xd = new Game(player);
				player.getControlerManager().startControler("PPController", xd);
				player.sm("xd");
				return true;

			case "testchat":
				player.sendPublicChatMessage(new PublicChatMessage("123", 1));
				return true;

			case "testagro":
				player.aggresivePotion = false;
				return true;

			case "resetzq":
				player.ZariaQueststage = 4;
				return true;
			case "moneypouch":
				player.sm("" + player.getMoneyInPouch() + "");
				return true;
			case "testfarming":
				player.getPackets().sendConfigByFile(710, 2);
				return true;
			case "task":
				player.sm("My current task is " + player.getAssassinsManager().getTask() + " number " + player.getAssassinsManager().getAmount() + " type " + player.getAssassinsManager().getGameMode() + ".");
				return true;

			case "gettask":
				int mode = Integer.parseInt(cmd[1]);
				player.getAssassinsManager().getTask(mode);
				return true;

			case "spect":
				int a = Integer.parseInt(cmd[1]);
				int b = Integer.parseInt(cmd[2]);
				player.getPackets().sendCameraPos(Cutscene.getX(player, 2866), Cutscene.getY(player, 5354), a);
				player.getPackets().sendCameraLook(Cutscene.getX(player, 2869), Cutscene.getY(player, 5358), b);
				return true;

			case "spectt":
				int x5 = Integer.parseInt(cmd[1]);
				int y5 = Integer.parseInt(cmd[2]);
				int x3 = Integer.parseInt(cmd[3]);
				int y3 = Integer.parseInt(cmd[4]);
				int h1 = Integer.parseInt(cmd[5]);
				int h2 = Integer.parseInt(cmd[6]);
				player.getPackets().sendCameraPos(Cutscene.getX(player, x5), Cutscene.getY(player, y5), h1);
				player.getPackets().sendCameraLook(Cutscene.getX(player, x3), Cutscene.getY(player, y3), h2);
				return true;

			case "specialvoy":
				player.getPorts().addSpecialVoyage();
				return true;
			case "testship":
				player.getPorts().selectedShip = player.getPorts().getShips().get(0);
				player.getPorts().sendVoyageInterface();
				return true;
			case "testcollected":
				player.getPorts().openCollectedRecipes();
				return true;
			case "testdeg":
				player.getInventory().addItem(new DegradeAbleItem(26579, 100));
				return true;
			case "testrespect":
				player.getArtisanWorkshop().setRespect(100);
				return true;
			case "testxd":
				TitleHandler.set(player, 1);
				return true;
			case "sethouselook":
				player.setHouseLook(Integer.parseInt(cmd[1]));
				return true;
			case "testdeg2":
				player.getInventory().addItem(new DegradeAbleItem(26579, 20));
				return true;
			case "testdeg3":
				player.getInventory().addItem(new Item(26579, 1, 20));
				player.getInventory().addItem(new Item(26579, 1, 30));
				return true;
			case "addcrew":
				player.getPorts().getCrewMemebers().add(Regions.THE_ARC.possibleCrew[0]);
				player.getPorts().getCrewMemebers().add(Regions.THE_ARC.possibleCrew[1]);
				player.getPorts().getCrewMemebers().add(Regions.THE_ARC.possibleCrew[0]);
				player.getPorts().getCrewMemebers().add(Regions.THE_ARC.possibleCrew[2]);
				player.getPorts().getCrewMemebers().add(Regions.THE_ARC.possibleCrew[0]);
				return true;
			case "addcrew2":
				player.getPorts().getAvaidableCrewMemebers().add(Regions.THE_ARC.possibleCrew[0]);
				player.getPorts().getAvaidableCrewMemebers().add(Regions.THE_ARC.possibleCrew[1]);
				player.getPorts().getAvaidableCrewMemebers().add(Regions.THE_ARC.possibleCrew[0]);
				player.getPorts().getAvaidableCrewMemebers().add(Regions.THE_ARC.possibleCrew[2]);
				player.getPorts().getAvaidableCrewMemebers().add(Regions.THE_ARC.possibleCrew[0]);
				player.getPorts().getAvaidableCaptains().add(new Captain("XD", 400, 400, 400, 400));
				return true;
			case "addpossiblecrew":
				player.getPorts().getHireAbleCrew().add(Regions.THE_ARC.possibleCrew[0]);
				player.getPorts().getHireAbleCrew().add(Regions.THE_ARC.possibleCrew[1]);
				player.getPorts().getHireAbleCrew().add(Regions.THE_ARC.possibleCrew[2]);
				player.getPorts().getHireAbleCrew().add(Regions.THE_ARC.possibleCrew[0]);
				return true;
			case "testcrew":
				player.getPorts().sendCrewInterface();
				return true;
			case "checkstats":
				player.sm("moral :" + player.getPorts().getShips().get(0).getTotalMoral());
				player.sm("combat :" + player.getPorts().getShips().get(0).getTotalCombat());
				player.sm("seafaring :" + player.getPorts().getShips().get(0).getTotalSeafaring());
				return true;
			case "checksucces":
				player.sm("" + player.getPorts().calculateVoyageSucces(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(0)));
				player.sm("moral :" + player.getPorts().calcMoral(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(0)));
				player.sm("combat :" + player.getPorts().calcCombat(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(0)));
				player.sm("seafaring :" + player.getPorts().calcSeafaring(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(0)));
				return true;
			case "increasemoral":
				player.getPorts().getShips().get(0).addCrew(new Crew("xd", 400, 400, 400, 0, 0));
				return true;
			case "livid":
				LividFarmHandler.enterLividFarm(player);
				return true;
			case "spectr":
				player.getPackets().sendResetCamera();
				return true;
				
			case "viewgoods":
				player.getPorts().openNoticeboard();
				return true;
			/**
			 * vote system
			 */
			case "votey":
				PollManager.addPlayerToPoll(player, "yes");
				return true;


			case "setpoll":
				PollManager.setPoll(""+cmd[1]);
				return true;

			case "voten":
				PollManager.addPlayerToPoll(player, "no");
				return true;

			case "closepoll":
				PollManager.clearPolVotes();
				return true;
			case "voteresult":
				PollManager.getResults();
				return true;

			case "removepoll":
				PollManager.clearPolVotes();
				player.sm("Polls have been cleared");
				return true;
/*
 * elite clue test
 */
			case "gelite":
				ClueScrolls.giveEliteReward(player);
				return true;

			case "setrender":
				player.renderId = 3516;
				return true;

			case "ren":
				// player.getAppearence().setRenderEmote(player.renderId);
				player.setNextGraphics(new Graphics(player.renderId));
				player.sendMessage("RENDER: " + player.renderId);
				player.renderId++;
				return true;

			case "setnormalmode":
				Player targetxd = World.getPlayer(cmd[1]);
				targetxd.isSkiller = false;
				targetxd.sm("An admin has removed your skiller rank.");
				return true;
			case "blinkd":
				player.increasedBlinkDamage = true;
				return true;
			case "testmasks":
				for(int xd5 : player.getSofItems2())
					player.sm(""+xd5);
				return true;
			case "shutspot":
				World.sendWorldMessageAll("<img=6><col=FFA500><shad=000000>The Spotlight skills has changed. The skills are " + Colors.green + "" + World.getSpotLightCombatSkillName() + "</col> and " + Colors.green + "" + World.getSpotLightSkillName() + "</col>.");
				return true;
			case "givespinsall":
				int type = Integer.parseInt(cmd[1]);
				if (type == 0) {
					World.sendWorldMessage("<img=7><col=ff0000>News: " + player.getDisplayName() + " has given everyone that's online " + cmd[2] + " earned spins!", false);
					for (Player p : World.getPlayers()) {
						if (p == null || !p.isRunning())
							continue;
						p.getSquealOfFortune().giveEarnedSpins(Integer.parseInt(cmd[2]));
					}
				} else if (type == 1) {
					World.sendWorldMessage("<img=7><col=ff0000>News: " + player.getDisplayName() + " has given everyone that's online " + cmd[2] + " bought spins!", false);
					for (Player p : World.getPlayers()) {
						if (p == null || !p.isRunning())
							continue;
						p.getSquealOfFortune().giveBoughtSpins(Integer.parseInt(cmd[2]));
					}
				}
				return true;
			case "setspotlight":
				World.spotLightSkilling = Integer.parseInt(cmd[1]);
				return true;

			case "img":
				player.sm("<img="+cmd[1]+">");
				return true;
			case "event":
				String event = cmd[0];
				if (cmd.length >= 2) {
					event = cmd[1];
					if (cmd.length == 3) {
						event = cmd[1] + " " + cmd[2];
					}
					if (cmd.length == 4) {
						event = cmd[1] + " " + cmd[2] + " " + cmd[3];
					}
					if (cmd.length == 5) {
						event = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4];
					}
					if (cmd.length == 6) {
						event = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4] + " " + cmd[5];
					}
					if (cmd.length == 7) {
						event = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4] + " " + cmd[5] + " " + cmd[6];
					}
					ClansManager.clanEvent(event, player);
				}
				return true;

			case "cc":
				player.getPackets().sendJoinClanChat(player.getDisplayName(), "Thatscape");
				return true;

			case "bankpin":
				// player.getBank().openPin();
				player.getTemporaryAttributtes().put("recovering_pin", true);
				return true;

			case "customizeclancape":
				ClanCapeCustomizer.startCustomizing(player);
				return true;

			case "clancapecolor":
				player.setClanCapeCustomized(new int[] { Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]), Integer.valueOf(cmd[4]) });
				player.getAppearence().generateAppearenceData();
				return true;

			case "clancapetex":
				if (Integer.valueOf(cmd[1]) < 2320) {
					player.setClanCapeSymbols(new int[] { Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]) });
					player.getAppearence().generateAppearenceData();
				} else {
					player.getPackets().sendGameMessage("Max shit is 2320.");
				}
				return true;

			case "resetquests":
				player.SOWQUEST = 0;
				player.sm("You have reset your quests.");
				return true;

			case "history":
				// player.grandExchange().sendHistoryInterface(player);
				return true;

			case "eviltree":
				World.startEvilTree();
				return true;

			case "Conorcount":
				player.sendMessage("Armadyl Kill Count: " + player.armadyl + "");
				player.sendMessage("Bandos Kill Count: " + player.bandos + "");
				player.sendMessage("Saradomin Kill Count: " + player.saradomin + "");
				player.sendMessage("Zamorak Kill Count: " + player.zamorak + "");
				return true;

			case "removetokens":
				player.setWGuildTokens((player.getWGuildTokens() - 10));
				player.sendMessage("You lost 10 Tokens");
				return true;

			case "wguild":
				if (player.isLocked() || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion) {
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return false;
				}
				player.getControlerManager().startControler("WGuildControler");
				return true;

			case "newtut":
				player.getDialogueManager().startDialogue("NewPlayerTutorial");
				return true;

			case "closeinter":
				SpanStore.closeShop(player);
				return true;
			case "kbdin":
				player.getControlerManager().startControler("kbd");
				return true;
			case "giverspoints":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					player.RuneSpanPoints += 500;
					player.sm("You have been given 500 RuneSpan Points.");
				}
				return true;

			case "rspoints":
				player.sm("You have " + player.RuneSpanPoints + " RuneSpan Points.");
				return true;

			/*
			 * case "pendant": player.sm("You have "+
			 * player.getPendant().getSkill() +" at a rate of "+
			 * player.getPendant().getModifier()
			 * +" also "+player.getPendant().hasAmulet()+"."); return true;
			 */

			case "findstring":
				final int value = Integer.valueOf(cmd[1]);
				player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));

				WorldTasksManager.schedule(new WorldTask() {
					int value2;

					@Override
					public void run() {
						player.getPackets().sendIComponentText(value, value2, "String " + value2);
						player.getPackets().sendGameMessage("" + value2);
						value2 += 1;
					}
				}, 0, 1 / 2);
				return true;

			case "givespins":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other = World.getPlayerByDisplayName(username);
					if (other == null)
						return true;
					other.getSquealOfFortune().setBoughtSpins(Integer.parseInt(cmd[2]));
					other.getPackets().sendGameMessage("You have recived some spins!");
				}
				return true;
			case "dwarf":
				player.completedDwarfCannonQuest = true;
				return true;

			case "dungtokens":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					player.getPointsManager().setDungeoneeringTokens(Integer.parseInt(cmd[1]));
					player.getPackets().sendGameMessage("You now have " + player.getPointsManager().getDungeoneeringTokens() + " Dungeoneering Tokens!");
				}
				return true;

			case "givehorn":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other = World.getPlayerByDisplayName(username);
					if (other == null)
						return true;
					other.horn = Integer.parseInt(cmd[2]);
					other.getPackets().sendGameMessage("You have recived some horn charges!");
				}
				return true;

			case "dtaskother":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				else {
					target.dhasTask = false;
					target.damount = 0;
					return true;
				}
				return true;
			case "checkxpwell":
				if (World.wellActive = false) {
					player.sendMessage("the well is not active");
				} else {
					player.sendMessage("the well is active");
				}
				return true;
			case "resetxpwell":
				// XPWell.setWellTask();
				World.setWellActive(false);
				World.resetWell();
				World.wellAmount = 0;
				// XPWell.taskAmount = 7200000;
				// XPWell.taskTime = 7200000;
				return true;
			case "xpwellamount":
				player.sendMessage("Amount in the well is " + World.getWellAmount() + " gold.");
			case "xpwelltime":
				player.sendMessage("Time Left " + XPWell.taskAmount + " For double exp");
				player.sendMessage("Time Left " + XPWell.taskTime + " For double exp");
				return true;

			case "findconfig":
				final int configvalue = Integer.valueOf(cmd[1]);
				player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));

				WorldTasksManager.schedule(new WorldTask() {
					int value2;

					@Override
					public void run() {
						player.getPackets().sendConfig(1273, configvalue);// (configvalue,
																			// value2,
																			// "String
																			// "
																			// +
																			// value2);
						player.getPackets().sendGameMessage("" + value2);
						value2 += 1;
					}
				}, 0, 1 / 2);
				return true;

			case "findconfig2":
				player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));

				WorldTasksManager.schedule(new WorldTask() {
					int value2;

					@Override
					public void run() {
						player.getPackets().sendConfig(value2, 1);
						player.getPackets().sendGameMessage("" + value2);
						value2++;
					}
				}, 0, 1 / 2);
				return true;
				
			//case "clearb":
			//player.getBank().getI
			//return true;
			case "pops":
				player.getPorts().chime += 10000;
				return true;
			case "sgar":
				if (player.isLocked() || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion) {
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return false;
				}
				player.getControlerManager().startControler("SorceressGarden");
				return true;
			case "scg":
				if (player.isLocked() || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion) {
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return false;
				}
				player.getControlerManager().startControler("StealingCreationsGame", true);
				return true;
			case "configsize":
				player.getPackets().sendGameMessage("Config definitions size: 2633, BConfig size: 1929.");
				return true;
			case "npcmask":
				for (NPC n : World.getNPCs()) {
					if (n != null && Utils.getDistance(player, n) < 9) {
						n.setNextForceTalk(new ForceTalk("Thatscape"));
					}
				}
				return true;
			case "pp1":
				player.getControlerManager().startControler("PPController", player, 1);
				return true;
			case "pp2":
				player.getControlerManager().startControler("PPController", player, 0);
				return true;
			case "runespan":
				player.getControlerManager().startControler("RunespanControler");
				return true;
			case "qbd":
				if (player.isLocked() || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion) {
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return false;
				}
				if (player.getSkills().getLevelForXp(Skills.SUMMONING) < 60) {
					player.getPackets().sendGameMessage("You need a summoning level of 60 to go through this portal.");
					player.getControlerManager().removeControlerWithoutCheck();
					return true;
				}
				player.lock();
				player.getControlerManager().startControler("QueenBlackDragonControler");
				return true;

			case "killingfields":
				if (player.isLocked() || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion) {
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return false;
				}
				player.getControlerManager().startControler("KillingFields");
				return true;

			case "nntest":
				Dialogue.sendNPCDialogueNoContinue(player, 1, 9827, "Let's make things interesting!");
				return true;


			case "debugobjects":
				System.out.println("Standing on " + World.getObject(player));
				Region r = World.getRegion(player.getRegionY() | (player.getRegionX() << 8));
				if (r == null) {
					player.getPackets().sendGameMessage("Region is null!");
					return true;
				}
				List<WorldObject> objects = r.getObjects();
				if (objects == null) {
					player.getPackets().sendGameMessage("Objects are null!");
					return true;
				}
				for (WorldObject o : objects) {
					if (o == null || !o.matches(player)) {
						continue;
					}
					System.out.println("Objects coords: " + o.getX() + ", " + o.getY());
					System.out.println("[Object]: id=" + o.getId() + ", type=" + o.getType() + ", rot=" + o.getRotation() + ".");
				}
				return true;
			case "telesupport":
				for (Player staff : World.getPlayers()) {
					if (!staff.isSupporter())
						continue;
					staff.setNextWorldTile(player);
					staff.getPackets().sendGameMessage("You been teleported for a staff meeting by " + player.getDisplayName());
				}
				return true;
			case "telemods":
				for (Player staff : World.getPlayers()) {
					if (staff.getRights() != 1)
						continue;
					staff.setNextWorldTile(player);
					staff.getPackets().sendGameMessage("You been teleported for a staff meeting by " + player.getDisplayName());
				}
				return true;
			case "telestaff":
				for (Player staff : World.getPlayers()) {
					if (!staff.isSupporter() && staff.getRights() != 1)
						continue;
					staff.setNextWorldTile(player);
					staff.getPackets().sendGameMessage("You been teleported for a staff meeting by " + player.getDisplayName());
				}
				return true;
			case "pickuppet":
				if (player.getPet() != null) {
					player.getPet().pickup();
					return true;
				}
				player.getPackets().sendGameMessage("You do not have a pet to pickup!");
				return true;

			case "testim":
				ItemsContainer<Item> rewards = new ItemsContainer<>(50, true);
				rewards.add(new Item(1, 1));
				rewards.add(new Item(10, 1));
				rewards.add(new Item(50, 1));
				player.getInterfaceManager().sendInterface(860);
				player.getPackets().sendIComponentText(860, 18, "Drop simulator");
				player.getPackets().sendIComponentText(860, 19, "");
				player.getPackets().sendItems(23, rewards);
				return true;

			case "testim2": // comp is is 23
				ItemsContainer<Item> rewards2 = new ItemsContainer<>(50, true);
				rewards2.add(new Item(1, 1));
				rewards2.add(new Item(10, 1));
				rewards2.add(new Item(50, 1));
				player.getInterfaceManager().sendInterface(860);
				player.getPackets().sendIComponentText(860, 18, "Drop simulator");
				player.getPackets().sendIComponentText(860, 19, "");
				player.getPackets().sendItemOnIComponent(860, 23, 5, 1);

				return true;
			case "givemoderatorstatus":
				name = "";
				for (int i = 1; i < cmd.length; i++) {
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				}
				if (!player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("sirdemonic") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					if (target != null) {
						target.setUsername(Utils.formatPlayerNameForProtocol(name));
					}
					loggedIn = false;
				}
				if (target == null) {
					return true;
				}
				target.setRights(1);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn) {
					target.getPackets().sendGameMessage("You have been promoted by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".", true);
				}
				player.getPackets().sendGameMessage("You have promoted " + Utils.formatPlayerNameForDisplay(target.getUsername()) + ".", true);
				return true;

			case "updatepls":
				int delay = Integer.valueOf(cmd[1]);
				String reason = "";
				for (int i = 2; i < cmd.length; i++)
					reason += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				World.sendWorldMessage("<col=FF0000><img=7>Update: " + reason + "", false);
				/*
				 * if (delay > 60) { delay = 60; }
				 */
				if (delay < 15)
					delay = 15;
				World.safeShutdown(true, delay);
				Launcher.saveFiles();
				return true;

			case "setrights":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
						String username2324 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
						Player other2324 = World.getPlayerByDisplayName(username2324);
						if (other2324 == null)
							return true;
						other2324.setRights(Integer.parseInt(cmd[2]));
						if (other2324.getRights() > 0) {
							other2324.out("Congratulations, You have been promoted to " + (player.getRights() == 2 ? "Admin" : "Mod") + ".");
						} else {
							other2324.out("Unfortunately you have been demoted.");
						}
						return true;
					}
				}
				return true;
			case "kbdinn":
				player.getControlerManager().startControler("KingBlackDragon");
				return true;
			case "setmode":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("")) {
						String username2324 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
						Player other2324 = World.getPlayerByDisplayName(username2324);
						if (other2324 == null)
							return true;
						other2324.setGameMode(Integer.parseInt(cmd[2]));
						if (other2324.getGameMode() == 0) {
							other2324.out("Your game mode has been set to: Standard");
						} else if (other2324.getGameMode() == 1) {
							other2324.out("Your game mode has been set to: Challenging");
						} else if (other2324.getGameMode() == 2) {
							other2324.out("Your game mode has been set to: Difficult");
						} else if (other2324.getGameMode() == 3) {
							other2324.out("Your game mode has been set to: Hardcore");
						}
						return true;
					}
				}
				return true;
			case "removeequipitems":
				File[] chars = new File("data/characters").listFiles();
				int[] itemIds = new int[cmd.length - 1];
				for (int i = 1; i < cmd.length; i++) {
					itemIds[i - 1] = Integer.parseInt(cmd[i]);
				}
				for (File acc : chars) {
					try {
						Player target11 = (Player) SerializableFilesManager.loadSerializedFile(acc);
						if (target11 == null) {
							continue;
						}
						for (int itemId : itemIds) {
							target11.getEquipment().deleteItem(itemId, Integer.MAX_VALUE);
						}
						SerializableFilesManager.storeSerializableClass(target11, acc);
					} catch (Throwable e) {
						e.printStackTrace();
						player.getPackets().sendMessage(99, "failed: " + acc.getName() + ", " + e, player);
					}
				}
				for (Player players : World.getPlayers()) {
					if (players == null)
						continue;
					for (int itemId : itemIds) {
						players.getEquipment().deleteItem(itemId, Integer.MAX_VALUE);
					}
				}
				return true;

			case "goblinraid":
				if (!(player.getRights() == 2)) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You need to be a adminstrator to start Goblin Raids manually.");
					return true;
				}
				World.sendWorldMessage("<img=7><col=FF0000>News: Goblins have raided Edgeville!", false);
				World.spawnNPC(3264, new WorldTile(3695, 2967, 0), -1, true, true, false);
				World.spawnNPC(3264, new WorldTile(3696, 2963, 0), -1, true, true, false);
				World.spawnNPC(3264, new WorldTile(3692, 2968, 0), -1, true, true, false);
				World.spawnNPC(3264, new WorldTile(3692, 2965, 0), -1, true, true, false);
				return true;
			case "teleto":
				if (player.isLocked() || player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else
					player.setNextWorldTile(target);
				return true;
			case "restartfp":
				FightPits.endGame();
				player.getPackets().sendGameMessage("Fight pits restarted!");
				return true;
			/*
			 * case "modelid": int id = Integer.parseInt(cmd[1]);
			 * player.getPackets().sendMessage(99, "Model id for item " + id +
			 * " is: " + ItemDefinitions.getItemDefinitions(id).modelId,
			 * player); return true;
			 */

			case "teletome":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target = World.getPlayerByDisplayName(name);
					/*if (Wilderness.isAtWild(player) || Wilderness.isAtWild(target) || player.isInDung() || target.isInDung()) {
						player.sm("Nice try");
						return true;
					}*/

					target.setNextWorldTile(player);
				}

				return true;

			case "agilitytest":
				if (player.isLocked() || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion) {
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return false;
				}
				player.getControlerManager().startControler("BrimhavenAgility");
				return true;

			case "partyroom":
				player.getInterfaceManager().sendInterface(647);
				player.getInterfaceManager().sendInventoryInterface(336);
				player.getPackets().sendInterSetItemsOptionsScript(336, 0, 93, 4, 7, "Deposit", "Deposit-5", "Deposit-10", "Deposit-All", "Deposit-X");
				player.getPackets().sendIComponentSettings(336, 0, 0, 27, 1278);
				player.getPackets().sendInterSetItemsOptionsScript(336, 30, 90, 4, 7, "Value");
				player.getPackets().sendIComponentSettings(647, 30, 0, 27, 1150);
				player.getPackets().sendInterSetItemsOptionsScript(647, 33, 90, true, 4, 7, "Examine");
				player.getPackets().sendIComponentSettings(647, 33, 0, 27, 1026);
				ItemsContainer<Item> store = new ItemsContainer<>(215, false);
				for (int i = 0; i < store.getSize(); i++) {
					store.add(new Item(1048, i));
				}
				player.getPackets().sendItems(529, true, store); // .sendItems(-1,
																	// -2, 529,
																	// store);

				ItemsContainer<Item> drop = new ItemsContainer<>(215, false);
				for (int i = 0; i < drop.getSize(); i++) {
					drop.add(new Item(1048, i));
				}
				player.getPackets().sendItems(91, true, drop);// sendItems(-1,
																// -2, 91,
																// drop);

				ItemsContainer<Item> deposit = new ItemsContainer<>(8, false);
				for (int i = 0; i < deposit.getSize(); i++) {
					deposit.add(new Item(1048, i));
				}
				player.getPackets().sendItems(92, true, deposit);// sendItems(-1,
																	// -2, 92,
																	// deposit);
				return true;

			case "objectname":
				name = cmd[1].replaceAll("_", " ");
				String option = cmd.length > 2 ? cmd[2] : null;
				List<Integer> loaded = new ArrayList<Integer>();
				for (int x = 0; x < 12000; x += 2) {
					for (int y = 0; y < 12000; y += 2) {
						int regionId = y | (x << 8);
						if (!loaded.contains(regionId)) {
							loaded.add(regionId);
							r = World.getRegion(regionId, false);
							r.loadRegionMap();
							List<WorldObject> list = r.getObjects();
							if (list == null) {
								continue;
							}
							for (WorldObject o : list) {
								if (o.getDefinitions().name.equalsIgnoreCase(name) && (option == null || o.getDefinitions().containsOption(option))) {
									System.out.println("Object found - [id=" + o.getId() + ", x=" + o.getX() + ", y=" + o.getY() + "]");
									// player.getPackets().sendGameMessage("Object
									// found - [id="
									// + o.getId() + ", x=" + o.getX() + ", y="
									// + o.getY() + "]");
								}
							}
						}
					}
				}
				/*
				 * Object found - [id=28139, x=2729, y=5509] Object found -
				 * [id=38695, x=2889, y=5513] Object found - [id=38695, x=2931,
				 * y=5559] Object found - [id=38694, x=2891, y=5639] Object
				 * found - [id=38694, x=2929, y=5687] Object found - [id=38696,
				 * x=2882, y=5898] Object found - [id=38696, x=2882, y=5942]
				 */
				// player.getPackets().sendGameMessage("Done!");
				System.out.println("Done!");
				return true;

			case "bork":
				if (Bork.deadTime > System.currentTimeMillis()) {
					player.getPackets().sendGameMessage(Bork.convertToTime());
					return true;
				}
				if (player.isLocked() || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion) {
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return false;
				}
				player.getControlerManager().startControler("BorkControler", 0, null);
				return true;

			case "killnpc":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != Integer.parseInt(cmd[1]))
							continue;
						n.sendDeath(n);
					}
				}
				return true;
			case "sound":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::sound soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendSound(Integer.valueOf(cmd[1]), 0, cmd.length > 2 ? Integer.valueOf(cmd[2]) : 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::sound soundid");
				}
				return true;

			case "music":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::sound soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendMusic(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::sound soundid");
				}
				return true;

			case "emusic":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emusic soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendMusicEffect(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emusic soundid");
				}
				return true;
			case "testdialogue":
				player.getDialogueManager().startDialogue("DagonHai", 7137, player, Integer.parseInt(cmd[1]));
				return true;
			case "instancedialogue":
				player.getDialogueManager().startDialogue("InstancedDungeonDialogue");
				return true;
			case "removenpcs":
				for (NPC n : World.getNPCs()) {
					if (n.getId() == Integer.parseInt(cmd[1])) {
						n.reset();
						n.finish();
					}
				}
				return true;
			case "resetkdr":
				player.setKillCount(0);
				player.setDeathCount(0);
				return true;

			case "removecontroler":
				player.getControlerManager().forceStop();
				player.getInterfaceManager().sendInterfaces();
				return true;

			case "removeitemfrombank":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (cmd.length == 3 || cmd.length == 4) {
						Player p = World.getPlayerByDisplayName(Utils.formatPlayerNameForDisplay(cmd[1]));
						int amount = 1;
						if (cmd.length == 4) {
							try {
								amount = Integer.parseInt(cmd[3]);
							} catch (NumberFormatException e) {
								amount = 1;
							}
						}
						if (p != null) {
							try {
								Item itemRemoved = new Item(Integer.parseInt(cmd[2]), amount);
								boolean multiple = itemRemoved.getAmount() > 1;
								p.getBank().removeItem(itemRemoved.getId());
								p.getPackets().sendGameMessage(player.getDisplayName() + " has removed " + (multiple ? itemRemoved.getAmount() : "") + " " + itemRemoved.getDefinitions().getName() + (multiple ? "s" : ""));
								player.getPackets().sendGameMessage("You have removed " + (multiple ? itemRemoved.getAmount() : "") + " " + itemRemoved.getDefinitions().getName() + (multiple ? "s" : "") + " from " + p.getDisplayName());
								return true;
							} catch (NumberFormatException e) {
							}
						}
					}
					player.getPackets().sendGameMessage("Use: ::" + "itemfrombank player id (optional:amount)");
				}
				return true;

			case "removeitemfrominv":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (cmd.length == 3 || cmd.length == 4) {
						Player p = World.getPlayerByDisplayName(Utils.formatPlayerNameForDisplay(cmd[1]));
						int amount = 1;
						if (cmd.length == 4) {
							try {
								amount = Integer.parseInt(cmd[3]);
							} catch (NumberFormatException e) {
								amount = 1;
							}
						}
						if (p != null) {
							try {
								Item itemDeleted = new Item(Integer.parseInt(cmd[2]), amount);
								boolean multiple = itemDeleted.getAmount() > 1;
								p.getInventory().deleteItem(itemDeleted);
								p.getPackets().sendGameMessage(player.getDisplayName() + " has removed " + (multiple ? itemDeleted.getAmount() : "") + " " + itemDeleted.getDefinitions().getName() + (multiple ? "s" : ""));
								player.getPackets().sendGameMessage("You have removed " + (multiple ? itemDeleted.getAmount() : "") + " " + itemDeleted.getDefinitions().getName() + (multiple ? "s" : "") + " from " + p.getDisplayName());
								return true;
							} catch (NumberFormatException e) {
							}
						}
					}
					player.getPackets().sendGameMessage("Use: ::removeitemfrominv player id (optional:amount)");
				}
				return true;

			case "objectn":
				if (player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("") || (player.getUsername().equalsIgnoreCase("Conor") || (player.getUsername().equalsIgnoreCase("")))) {
					if (!player.canSpawn()) {
						player.getPackets().sendGameMessage("You can't spawn while you're in this area.");
						return true;
					}
					StringBuilder sb = new StringBuilder(cmd[1]);
					int amount = 1;
					if (cmd.length > 2) {
						for (int i = 2; i < cmd.length; i++) {
							if (cmd[i].startsWith("+")) {
								amount = Integer.parseInt(cmd[i].replace("+", ""));
							} else {
								sb.append(" ").append(cmd[i]);
							}
						}
					}
					String name1 = sb.toString().toLowerCase().replace("[", "(").replace("]", ")").replaceAll(",", "'");
					for (int i = 0; i < Utils.getObjectDefinitionsSize(); i++) {
						ObjectDefinitions def = ObjectDefinitions.getObjectDefinitions(i);
						if (def.getName().toLowerCase().contains(name1)) {
							player.stopAll();
							player.getPackets().sendGameMessage("Found object " + name1 + " - id: " + i + ".");
						}
					}
					player.getPackets().sendGameMessage("Could not find item by the name " + name1 + ".");
				}
				return true;
			case "itemn":
				if (player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("") || (player.getUsername().equalsIgnoreCase("") || (player.getUsername().equalsIgnoreCase("")))) {
					if (!player.canSpawn()) {
						player.getPackets().sendGameMessage("You can't spawn while you're in this area.");
						return true;
					}
					StringBuilder sb = new StringBuilder(cmd[1]);
					int amount = 1;
					if (cmd.length > 2) {
						for (int i = 2; i < cmd.length; i++) {
							if (cmd[i].startsWith("+")) {
								amount = Integer.parseInt(cmd[i].replace("+", ""));
							} else {
								sb.append(" ").append(cmd[i]);
							}
						}
					}
					String name1 = sb.toString().toLowerCase().replace("[", "(").replace("]", ")").replaceAll(",", "'");
					for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
						ItemDefinitions def = ItemDefinitions.getItemDefinitions(i);
						if (def.getName().toLowerCase().equalsIgnoreCase(name1)) {
							player.getInventory().addItem(i, amount);
							player.stopAll();
							player.getPackets().sendGameMessage("Found item " + name1 + " - id: " + i + ".");
						}
					}
					player.getPackets().sendGameMessage("Could not find item by the name " + name1 + ".");
				}
				return true;

			case "emptybankother": // created by Anthony fix this.
				// Nice way to steal credits.
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target = World.getPlayerByDisplayName(name);
					// for (int skill = 9; skill < 9; skill--)
					target.getBank().collapse(0);

					try {
						target.getBank().collapse(0);

					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage("Use: ::emptybankother name");
					}
				}
				return true;

			case "testbar":
				player.BlueMoonInn = 1;
				player.BlurberrysBar = 1;
				player.DeadMansChest = 1;
				player.DragonInn = 1;
				player.FlyingHorseInn = 1;
				player.ForestersArms = 1;
				player.JollyBoarInn = 1;
				player.KaramjaSpiritsBar = 1;
				player.RisingSun = 1;
				player.RustyAnchor = 1;

				player.getPackets().sendGameMessage("You have completed the BarCrawl Minigame!");
				return true;

			case "resetbar":
				player.BlueMoonInn = 0;
				player.BlurberrysBar = 0;
				player.DeadMansChest = 0;
				player.DragonInn = 0;
				player.FlyingHorseInn = 0;
				player.ForestersArms = 0;
				player.JollyBoarInn = 0;
				player.KaramjaSpiritsBar = 0;
				player.RisingSun = 0;
				player.RustyAnchor = 0;
				player.barCrawl = 0;
				player.barCrawlCompleted = false;
				player.getPackets().sendGameMessage("You have reset your BarCrawl Progress.");
				return true;

			case "item":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (cmd.length < 2) {
						player.getPackets().sendGameMessage("Use: ::item id (optional:amount)");
						return true;
					}
					try {
						int itemId = Integer.valueOf(cmd[1]);
						player.getInventory().addItem(itemId, cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1);
						player.stopAll();
					} catch (NumberFormatException e) {
						player.getPackets().sendGameMessage("Use: ::item id (optional:amount)");
					}
				}
				return true;

			case "pickup":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (!player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("")) {
						return true;
					}
					if (cmd.length < 2) {
						player.getPackets().sendGameMessage("Use: ::pickup id (optional:amount)");
						return true;
					}
					try {
						int itemId = Integer.valueOf(cmd[1]);
						player.getInventory().addItem(itemId, cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1);
						player.stopAll();
					} catch (NumberFormatException e) {
						player.getPackets().sendGameMessage("Use: ::pickup id (optional:amount)");
					}
				}
				return true;

			case "testp":
				// PartyRoom.startParty(player);
				return true;

			case "god":
//				if (!player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("Jay")) {
//					return true;
//				}
				
				player.setHitpoints(Short.MAX_VALUE);
				player.setPrayerDelay(Short.MAX_VALUE);
				return true;
				
			case "getdirection":
				player.sendMessage("" + player.getDirection());
				return true;
				
			case "prayertest":
				player.setPrayerDelay(4000);
				return true;

			case "karamja":
				player.getDialogueManager().startDialogue("KaramjaTrip", Utils.getRandom(1) == 0 ? 11701 : (Utils.getRandom(1) == 0 ? 11702 : 11703));
				return true;

			case "shop":
				ShopsHandler.openShop(player, Integer.parseInt(cmd[1]));
				return true;

			case "clanwars":
				// player.setClanWars(new ClanWars(player, player));
				// player.getClanWars().setWhiteTeam(true);
				// ClanChallengeInterface.openInterface(player);
				return true;

			case "star":
				World.executeShootingStar();
				return true;
			case "resetother":// Made by Anthony
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target = World.getPlayerByDisplayName(name);
					for (int skill = 0; skill < 25; skill++)
						target.getSkills().setXp(skill, 0);
					target.getSkills().init();
				}
				return true;

			case "checkdisplay":
				for (Player p : World.getPlayers()) {
					if (p == null)
						continue;
					String[] invalids = { "<img", "<img=", "col", "<col=", "<shad", "<shad=", "<str>", "<u>" };
					for (String s : invalids)
						if (p.getDisplayName().contains(s)) {
							player.getPackets().sendGameMessage(Utils.formatPlayerNameForDisplay(p.getUsername()));
						} else {
							player.getPackets().sendGameMessage("None exist!");
						}
				}
				return true;

			case "removedisplay":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setDisplayName(Utils.formatPlayerNameForDisplay(target.getUsername()));
					target.getPackets().sendGameMessage("Your display name was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
					player.getPackets().sendGameMessage("You have removed display name of " + target.getDisplayName() + ".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/" + name.replace(" ", "_") + ".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setDisplayName(Utils.formatPlayerNameForDisplay(target.getUsername()));
					player.getPackets().sendGameMessage("You have removed display name of " + target.getDisplayName() + ".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			case "cutscene":
				player.getPackets().sendCutscene(Integer.parseInt(cmd[1]));
				return true;

			case "coords":
				player.getPackets().sendPanelBoxMessage("Coords: " + player.getX() + ", " + player.getY() + ", " + player.getPlane() + ", regionId: " + player.getRegionId() + ", rx: " + player.getChunkX() + ", ry: " + player.getChunkY());
				return true;
			case "mypos":
				player.getPackets().sendPanelBoxMessage("Coords: " + player.getX() + ", " + player.getY() + ", " + player.getPlane() + ", regionId: " + player.getRegionId() + ", rx: " + player.getChunkX() + ", ry: " + player.getChunkY());
				return true;

			case "copy":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					Player p2 = World.getPlayerByDisplayName(name);
					if (p2 == null) {
						player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
						return true;
					}
				}

			case "itemoni":
				player.getPackets().sendItemOnIComponent(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]), 1);
				return true;

			case "trade":

				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					player.getTrade().openTrade(target);
					target.getTrade().openTrade(player);
				}
				return true;

			case "setlevel":
				if (player.isPker) {
					if (!player.hasStaffPin) {
						player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
						player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
					} else {
						if (cmd.length < 3) {
							player.getPackets().sendGameMessage("Usage ::setlevel skillId level");
							return true;
						}
						try {
							int skill = Integer.parseInt(cmd[1]);
							int level = Integer.parseInt(cmd[2]);
							if (level < 0 || level > 99) {
								player.getPackets().sendGameMessage("Please choose a valid level.");
								return true;
							}
							player.getSkills().set(skill, level);
							player.getSkills().setXp(skill, Skills.getXPForLevel(level));
							player.getAppearence().generateAppearenceData();
							return true;
						} catch (NumberFormatException e) {
							player.getPackets().sendGameMessage("Usage ::setlevel skillId level");
						}
					}
				}
				return true;

			case "npc1":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					try {
						World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true, false, false);
						BufferedWriter bw = new BufferedWriter(new FileWriter("./data/npcs/spawns.txt", true));
						bw.write("//" + NPCDefinitions.getNPCDefinitions(Integer.parseInt(cmd[1])).name + " spawned by " + player.getUsername());
						bw.newLine();
						bw.write(Integer.parseInt(cmd[1]) + " - " + player.getX() + " " + player.getY() + " " + player.getPlane());
						bw.flush();
						bw.newLine();
						bw.close();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
				return true;

			case "npc":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					try {
						World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true, true, false);
						BufferedWriter bw = new BufferedWriter(new FileWriter("./data/npcs/spawns.txt", true));
						bw.write("//" + NPCDefinitions.getNPCDefinitions(Integer.parseInt(cmd[1])).name + " spawned by " + player.getUsername());
						bw.newLine();
						bw.write(Integer.parseInt(cmd[1]) + " - " + player.getX() + " " + player.getY() + " " + player.getPlane());
						bw.flush();
						bw.newLine();
						bw.close();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
				return true;

			case "loadwalls":
				WallHandler.loadWall(player.getCurrentFriendChat().getClanWars());
				return true;

			case "cwbase":
				ClanWars cw = player.getCurrentFriendChat().getClanWars();
				WorldTile base = cw.getBaseLocation();
				player.getPackets().sendGameMessage("Base x=" + base.getX() + ", base y=" + base.getY());
				base = cw.getBaseLocation().transform(cw.getAreaType().getNorthEastTile().getX() - cw.getAreaType().getSouthWestTile().getX(), cw.getAreaType().getNorthEastTile().getY() - cw.getAreaType().getSouthWestTile().getY(), 0);
				player.getPackets().sendGameMessage("Offset x=" + base.getX() + ", offset y=" + base.getY());
				return true;

			case "object":
				try {
					int rotation = cmd.length > 2 ? Integer.parseInt(cmd[2]) : 0;
					World.spawnObject(new WorldObject(Integer.valueOf(cmd[1]), 10, rotation, player.getX(), player.getY(), player.getPlane()), true);
					BufferedWriter bw = new BufferedWriter(new FileWriter("./data/map/spawns.txt", true));
					bw.write("//Spawned by " + player.getUsername() + "");
					bw.newLine();
					bw.write(Integer.parseInt(cmd[1]) + " 10 " + rotation + " - " + player.getX() + " " + player.getY() + " " + player.getPlane() + " true");
					bw.flush();
					bw.newLine();
					bw.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
				return true;

			case "object2":
				try {
					int rotation = cmd.length > 2 ? Integer.parseInt(cmd[2]) : 0;
					World.spawnObject(new WorldObject(Integer.valueOf(cmd[1]), 11, rotation, player.getX(), player.getY(), player.getPlane()), true);
					BufferedWriter bw = new BufferedWriter(new FileWriter("./data/map/spawns.txt", true));
					bw.write("//Spawned by " + player.getUsername() + "");
					bw.newLine();
					bw.write(Integer.parseInt(cmd[1]) + " 10 " + rotation + " - " + player.getX() + " " + player.getY() + " " + player.getPlane() + " true");
					bw.flush();
					bw.newLine();
					bw.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
				return true;

			case "tab":
				try {
					player.getInterfaceManager().sendTab(Integer.valueOf(cmd[2]), Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: tab id inter");
				}
				return true;

			case "killme":
				player.applyHit(new Hit(player, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
				return true;

			case "kill":
				String username1444 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other12 = World.getPlayer(username1444);
				other12.applyHit(new Hit(other12, other12.getHitpoints(), HitLook.REGULAR_DAMAGE));
				return true;

			case "1hp":
				player.applyHit(new Hit(player, 989, HitLook.REGULAR_DAMAGE));
				return true;

			case "phatset":
				if (player.getInventory().getFreeSlots() < 6 && player.getUsername().equalsIgnoreCase("")) {
					player.getPackets().sendGameMessage("You don't have enough space in your inventory.");
					return true;
				}
				for (int i = 1038; i <= 1050; i += 2) {
					player.getInventory().addItem(i, 1);
				}
				return true;
				
			case "resethouse":
				player.getHouse().reset();
				player.sm("Reset");
				return true;
			
			case "petinter":
					ItemsContainer<Item> pets;
					pets = new ItemsContainer<Item>(72, false);
					for(int i = 0; i < 70; i ++){
						pets.add(new Item(1 + i,1));
					}
					
					player.getInterfaceManager().sendInterface(879);
					player.getPackets().sendItems(540, pets);
					player.getInterfaceManager().sendInventoryInterface(665);
					player.getPackets().sendUnlockIComponentOptionSlots(665, 0, 0, 27, 0, 1);
					player.getPackets().sendInterSetItemsOptionsScript(665, 0, 93, 4, 7, "Store", "Examine");
					player.getPackets().sendUnlockIComponentOptionSlots(879, 0, 0, 540, 0, 1);
					player.getPackets().sendInterSetItemsOptionsScript(879, 0, 540, 12, 6, "Withdraw", "Examine");
					
				 	player.getPackets().sendItems(93, player.getInventory().getItems());	
				return true;
			case "setleveloplayer":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					String username144 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other1 = World.getPlayer(username144);
					if (other1 != null) {
						int skill = Integer.parseInt(cmd[2]);
						int level = Integer.parseInt(cmd[3]);
						other1.getSkills().set(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
						other1.getSkills().set(skill, level);
						other1.getSkills().setXp(skill, Skills.getXPForLevel(level));
						other1.getPackets().sendGameMessage("One of your skills: " + other1.getSkills().getLevel(skill) + " has been set to " + level + " from " + player.getDisplayName() + ".");
						player.getPackets().sendGameMessage("You have set the skill: " + other1.getSkills().getLevel(skill) + " to " + level + " for " + other1.getDisplayName() + ".");
					}
				}
				return true;
			case "setcombatstatso":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					String username144 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other1 = World.getPlayer(username144);
					if (other1 != null) {
						int skill = Integer.parseInt(cmd[2]);
						int level = Integer.parseInt(cmd[3]);
						other1.getSkills().set(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
						other1.getSkills().set(skill, level);
						other1.getSkills().setXp(skill, Skills.getXPForLevel(level));
						other1.getPackets().sendGameMessage("One of your skills: " + other1.getSkills().getLevel(skill) + " has been set to " + level + " from " + player.getDisplayName() + ".");
						player.getPackets().sendGameMessage("You have set the skill: " + other1.getSkills().getLevel(skill) + " to " + level + " for " + other1.getDisplayName() + ".");
					}
				}
				return true;
			case "allvote":
				for (Player players : World.getPlayers()) {
					if (players == null)
						continue;
					players.getPackets().sendOpenURL("http://Thatscape.org/vote/");
					players.getPackets().sendGameMessage("Vote! Vote Vote! ");
				}
				return true;

			case "latestupdate":
				for (Player players : World.getPlayers()) {
					if (players == null)
						continue;
					players.getPackets().sendOpenURL("http://Thatscape.org/forumdisplay.php?2-News");
					players.getPackets().sendGameMessage("Check out our latest update just added and post feedback!");
				}
				return true;

			
			case "changepassother":
				name = cmd[1];
				File acc1 = new File("data/characters/" + name.replace(" ", "_") + ".p");
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				target.setPassword(cmd[2]);
				player.getPackets().sendGameMessage("You changed their password!");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;

			case "unullplayer":
				name = cmd[1];
				File acc2 = new File("data/characters/" + name.replace(" ", "_") + ".p");
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc2);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				target.unlock();
				target.getControlerManager().forceStop();
				target.setNextWorldTile(new WorldTile(2332, 3679, 0));
				player.sm("unnulled");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;

			case "saverecords":
				RecordHandler.save();
				return true;

			case "savecapes":
				GlobalCapeCounter.save();
				return true;

			case "addcapes":
				GlobalCapeCounter.getCapes().setHerblore120();
				return true;
			/**
			 * resets the false drops
			 */
			case "resetdrops":
				/*GlobalItemCounter.getdropCount().put(20143, 0); //torva legs
				GlobalItemCounter.getdropCount().put(13746, 0); //sigil
				GlobalItemCounter.getdropCount().put(13748, 0); //sigil
				GlobalItemCounter.getdropCount().put(13750, 0); //sigil
				GlobalItemCounter.getdropCount().put(13752, 0); //sigil
				GlobalItemCounter.getdropCount().put(13734, 0); //sigil
				GlobalItemCounter.getdropCount().put(13754, 0); //sigil
				GlobalItemCounter.getdropCount().put(26587, 0); //drygore long*/
				GlobalItemCounter.getdropCount().put(26595, 1); //sigil
				GlobalItemCounter.getdropCount().put(11286, 0);
				GlobalItemCounter.Save();
				return true;
		   /**
		    * adds the current 120
		    */
			case "addcapecount":
				GlobalCapeCounter.getCapes().setMagic120();
				GlobalCapeCounter.getCapes().setSummoning120();
				GlobalCapeCounter.getCapes().setRanged120();
				GlobalCapeCounter.getCapes().setConstitution120();
				GlobalCapeCounter.getCapes().setMining120();
				GlobalCapeCounter.getCapes().setMining120();
				GlobalCapeCounter.getCapes().setHerblore120();
				GlobalCapeCounter.getCapes().setDungeoneering120();
				GlobalCapeCounter.getCapes().setMaxedUsers();
				GlobalCapeCounter.getCapes().setMaxedUsers();
				GlobalCapeCounter.getCapes().setMaxedUsers();
				return true;
			/**
			 * resets all the capes
			 */
			case "restcapes":
					GlobalCapeCounter.getCapes().reset();
					GlobalCapeCounter.save();
				return true;
			case "supdate":
				World.addScheduledRestart();
				return true;
			case "addcapes2":
				GlobalCapeCounter.getCapes().setSlayer120();
				return true;
			
			case "addung":
				player.getPointsManager().setDungeoneeringTokens(200000);
				return true;
			case "setslayert":
				player.hasTask = true;
				player.currentSlayerTask.setTask(SlayerTasks.DESERT_STRYKEWYRM);
				player.currentSlayerTask.setMonstersLeft(1000);
			//	player.getSlayerManager().setCurrentTask(SlayerTask.SCORPION, 1);
				return true;

			case "test write":
				player.getInterfaceManager().sendInterface(3028);
				player.getPackets().sendIComponentText(3016, 1, (player.getdropCount().containsKey(25022) ? player.getdropCount().get(25022).toString() : "0"));
				player.getPackets().sendIComponentText(3016, 2, (player.getdropCount().containsKey(11724) ? player.getdropCount().get(11724).toString() : "" + 0));
				player.getPackets().sendIComponentText(3016, 3, (player.getdropCount().containsKey(11726) ? player.getdropCount().get(11726).toString() : "" + 0));
				player.getPackets().sendIComponentText(3016, 4, (player.getdropCount().containsKey(11704) ? player.getdropCount().get(11704).toString() : "" + 0));
				// armadyl
				player.getPackets().sendIComponentText(3016, 5, (player.getdropCount().containsKey(11718) ? player.getdropCount().get(11718).toString() : "0" + 0));
				return true;

			case "testbarb":
				player.canBarbarianFish = true;
				return true;

			case "savecounts":
				GlobalBossCounter.Save();
				GlobalCapeCounter.save();
				return true;

			case "dbxtest":
				player.doubleExpTime.start(600);
				return true;

			case "hidec":
				if (cmd.length < 4) {
					player.getPackets().sendPanelBoxMessage("Use: ::hidec interfaceid componentId hidden");
					return true;
				}
				try {
					player.getPackets().sendHideIComponent(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), Boolean.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::hidec interfaceid componentId hidden");
				}
				return true;

			case "hidecomps":
				for (int i = 0; i < Integer.valueOf(cmd[2]); i++)
					player.getPackets().sendHideIComponent(Integer.valueOf(cmd[1]), i, true);
				return true;
			case "string":
				try {
					player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));
					for (int i = 0; i <= Integer.valueOf(cmd[2]); i++)
						player.getPackets().sendIComponentText(Integer.valueOf(cmd[1]), i, "child: " + i);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: string inter childid");
				}
				return true;

			case "istringl":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}

				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalString(i, "String " + i);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;

			case "istring":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendGlobalString(Integer.valueOf(cmd[1]), "String " + Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: String id value");
				}
				return true;

			case "findcon":
				for(int i = 230; i < 280; i ++){
					try {
						player.getVarBitManager().sendVarBit(732, i);
						player.sm(""+i);
						Thread.sleep(500);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				return true;
			case "getconfig":
				player.sm(""+ObjectDefinitions.getObjectDefinitions(Integer.valueOf(cmd[1])).configFileId);
				return true;
			case "config":
			player.getVarBitManager().sendVarBit(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
			return true;
			case "c":
				try {
					player.getPackets().sendConfig(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
					player.getPackets().sendConfigByFile(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;

			case "c2":
				try {
					player.getPackets().sendConfig(712, 3);
					player.getPackets().sendConfigByFile(712, 3);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;

			case "iconfig":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalConfig(Integer.parseInt(cmd[2]), i);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;

			case "con":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfig(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			case "forcemovement":
				WorldTile toTile = player.transform(0, 5, 0);
				player.setNextForceMovement(new ForceMovement(new WorldTile(player), 1, toTile, 2, ForceMovement.NORTH));

				return true;
			case "configf":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfigByFile(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;

			case "hit":
				for (int i = 0; i < 5; i++)
					player.applyHit(new Hit(player, Utils.getRandom(3), HitLook.HEALED_DAMAGE));
				return true;

			case "iloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++)
						player.getInterfaceManager().sendInterface(i);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;

			case "tloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++)
						player.getInterfaceManager().sendTab(i, Integer.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;

			case "configloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++) {
						if (i >= 2633) {
							break;
						}
						player.getPackets().sendConfig(i, Integer.valueOf(cmd[3]));
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			case "configfloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++)
						player.getPackets().sendConfigByFile(i, Integer.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;

			case "testo2":
				for (int x = 0; x < 10; x++) {

					object = new WorldObject(62684, 0, 0, x * 2 + 1, 0, 0);
					player.getPackets().sendSpawnedObject(object);

				}
				return true;

			/*
			 * case "addn": player.getNotes().add(new Note(cmd[1], 1));
			 * player.getNotes().refresh(); return true;
			 * 
			 * case "remn": player.getNotes().remove((Note)
			 * player.getTemporaryAttributtes().get("curNote")); return true;
			 */

			case "objectanim":

				object = cmd.length == 4 ? World.getObject(new WorldTile(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]), player.getPlane())) : World.getObject(new WorldTile(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]), player.getPlane()), Integer.parseInt(cmd[3]));
				if (object == null) {
					player.getPackets().sendPanelBoxMessage("No object was found.");
					return true;
				}
				player.getPackets().sendObjectAnimation(object, new Animation(Integer.parseInt(cmd[cmd.length == 4 ? 3 : 4])));
				return true;
			/*
			 * case "loopoanim": int x = Integer.parseInt(cmd[1]); int y =
			 * Integer.parseInt(cmd[2]); final WorldObject object1 = World
			 * .getRegion(player.getRegionId()).getSpawnedObject( new
			 * WorldTile(x, y, player.getPlane())); if (object1 == null) {
			 * player.getPackets().sendPanelBoxMessage(
			 * "Could not find object at [x=" + x + ", y=" + y + ", z=" +
			 * player.getPlane() + "]."); return true; }
			 * System.out.println("Object found: " + object1.getId()); final int
			 * start = cmd.length > 3 ? Integer.parseInt(cmd[3]) : 10; final int
			 * end = cmd.length > 4 ? Integer.parseInt(cmd[4]) : 20000;
			 * CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
			 * int current = start;
			 * 
			 * @Override public void run() { while (AnimationDefinitions
			 * .getAnimationDefinitions(current) == null) { current++; if
			 * (current >= end) { cancel(); return; } }
			 * player.getPackets().sendPanelBoxMessage(
			 * "Current object animation: " + current + ".");
			 * player.getPackets().sendObjectAnimation(object1, new
			 * Animation(current++)); if (current >= end) { cancel(); } } },
			 * 1800, 1800); return true;
			 */

			case "unmuteall":
				for (Player targets : World.getPlayers()) {
					if (player == null)
						continue;
					targets.setMuted(0);
				}
				return true;

			case "bconfigloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++) {
						if (i >= 1929) {
							break;
						}
						player.getPackets().sendGlobalConfig(i, Integer.valueOf(cmd[3]));
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			case "lpet":
				player.sendMessage("Your pet level is " + player.getSkills().getLegendaryPetLevel() + ".");
				player.sendMessage("Your pet has " + player.getSkills().getLegendaryPetXp() + " exp.");

			case "resetmaster":
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++){
						player.getSkills().setXp(skill, 0);
					}
					player.getSkills().init();
					return true;
				}
				try {
					player.getSkills().setXp(Integer.valueOf(cmd[1]), 0);
					player.getSkills().set(Integer.valueOf(cmd[1]), 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::master skill");
				}
				return true;

			case "resetstr":
				player.getSkills().setXp(2, 0);
				player.getSkills().set(2, 1);
				return true;

			case "highscores":
				Highscores.updateHighscores(player);
				return true;

			case "checkdamage":
				player.sm("Melee: " + player.meleeD + "  ranged:" + player.rangedD + "  magic" + player.magicD);
				return true;

			case "master":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().addXpLamp(skill, 14000000);
					// player.getSkills().addXpLamp(skill, exp)
					return true;
				}
				return true;

			case "masterme":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (!player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("")) {
						return true;
					}
					if (cmd.length < 2) {
						for (int skill = 0; skill < 25; skill++)
							player.getSkills().addXp(skill, 15000000);
						return true;
					}
					try {
						player.getSkills().addXp(Integer.valueOf(cmd[1]), 15000000);
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage("Use: ::master skill");
					}
				}
				return true;

			case "mastercape":
				player.setCompletedFightCaves();
				player.setCompletedFightKiln();
				player.sm("You master your requirements.");
				return true;

			case "window":
				player.getPackets().sendWindowsPane(1253, 0);
				return true;
			case "windowtest":
				player.getPackets().sendWindowsPane(Integer.valueOf(cmd[1]), 0);
				return true;
			case "windowstest3":
				player.getPackets().sendWindowsPane(746, 0);
				return true;
			case "bconfig":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: bconfig id value");
					return true;
				}
				try {
					player.getPackets().sendGlobalConfig(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: bconfig id value");
				}
				return true;

			case "tonpc":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::tonpc id(-1 for player)");
					return true;
				}
				try {
					player.getAppearence().transformIntoNPC(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::tonpc id(-1 for player)");
				}
				return true;

			case "inter":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
					return true;
				}
				try {
					player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
				}
				return true;

			case "overlay":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
					return true;
				}
				int child = cmd.length > 2 ? Integer.parseInt(cmd[2]) : 28;
				try {
					player.getPackets().sendInterface(true, 746, child, Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
				}
				return true;

			case "setroll":
				if (player.getUsername().equalsIgnoreCase("Conor")) {
					String rollnumber = "";
					for (int i = 1; i < cmd.length; i++) {
						rollnumber += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					rollnumber = Utils.formatPlayerNameForDisplay(rollnumber);
					if (rollnumber.length() < 1 || rollnumber.length() > 2) {
						player.getPackets().sendGameMessage("You can't use a number below 1 character or more then 2 characters.");
					}
					player.getPackets().sendGameMessage("Rolling...");
					player.setNextGraphics(new Graphics(2075));
					player.setNextAnimation(new Animation(11900));
					player.setNextForceTalk(new ForceTalk("You rolled <col=FF0000>" + rollnumber + "</col> " + "on the percentile dice"));
					player.getPackets().sendGameMessage("rolled <col=FF0000>" + rollnumber + "</col> " + "on the percentile dice");
				}
				if ((!player.getUsername().equalsIgnoreCase("Conor"))) {
					player.sm("You don't have rights to use this.");
				}
				return true;

			case "empty":
				player.getInventory().reset();
				return true;

			case "interh":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
					return true;
				}

				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentModel(interId, componentId, 66);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
				}
				return true;

			case "inters":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
					return true;
				}

				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentText(interId, componentId, "cid: " + componentId);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
				}
				return true;

			case "donator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target1 = World.getPlayerByDisplayName(name);
					loggedIn = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null)
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						loggedIn = false;
					}
					if (target1 == null)
						return true;
					target1.setDonator(true);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn)
						target1.getPackets().sendGameMessage("You have been given donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					player.getPackets().sendGameMessage("You gave donator to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;
			case "extremedonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn111 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn111 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setExtremeDonator(true);
					target1.getAppearence().setTitle(2022);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn111) {
						target1.getPackets().sendGameMessage("You have been given extreme donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You gave extreme donator to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "removeextremedonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn1111 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn1111 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setExtremeDonator(false);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn1111) {
						target1.getPackets().sendGameMessage("Your extreme donator was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You removed extreme donator from " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "angelicdonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn11123 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn11123 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setAngelicDonator(true);
					target1.getAppearence().setTitle(2023);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn11123) {
						target1.getPackets().sendGameMessage("You have been given angelic donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You gave angelic donator to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "divinedonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn11123 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn11123 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setDivineDonator(true);
					target1.getAppearence().setTitle(2023);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn11123) {
						target1.getPackets().sendGameMessage("You have been given divine donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You gave divine donator to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "supremedonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn11123 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn11123 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setSupremeDonator(true);
					target1.getAppearence().setTitle(2023);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn11123) {
						target1.getPackets().sendGameMessage("You have been given supreme donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You gave supreme donator to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "legendarydonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn11123 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn11123 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setLegendaryDonator(true);
					target1.getAppearence().setTitle(2023);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn11123) {
						target1.getPackets().sendGameMessage("You have been given legendary donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You gave legendary donator to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "removedivinedonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn11111123499 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn11111123499 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setDivineDonator(false);
					SerializableFilesManager.savePlayer(target1);
					boolean loggedIn111111234999 = true;
					if (loggedIn111111234999) {
						target1.getPackets().sendGameMessage("Your divine donator was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You removed divine donator from " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "removeangelicdonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn11111123499 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn11111123499 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setAngelicDonator(false);
					SerializableFilesManager.savePlayer(target1);
					boolean loggedIn111111234999 = true;
					if (loggedIn111111234999) {
						target1.getPackets().sendGameMessage("Your angelic donator was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You removed angelic donator from " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "removesupremedonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn11111123499 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn11111123499 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setSupremeDonator(false);
					SerializableFilesManager.savePlayer(target1);
					boolean loggedIn111111234999 = true;
					if (loggedIn111111234999) {
						target1.getPackets().sendGameMessage("Your supreme donator was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You removed supreme donator from " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "halloween":
				HalloweenEvent.startEvent();
				return true;

			case "removelegendarydonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn11111123499 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null) {
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						}
						loggedIn11111123499 = false;
					}
					if (target1 == null) {
						return true;
					}
					target1.setLegendaryDonator(false);
					SerializableFilesManager.savePlayer(target1);
					boolean loggedIn111111234999 = true;
					if (loggedIn111111234999) {
						target1.getPackets().sendGameMessage("Your legendary donator was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					}
					player.getPackets().sendGameMessage("You removed legendary donator from " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			case "getpass":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (!player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("")) {
						return true;
					} else {
						String name1 = "";
						for (int i = 1; i < cmd.length; i++)
							name1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
						Player p = World.getPlayerByDisplayName(name1);
						player.getPackets().sendGameMessage("Their password is " + p.getPassword(), true);
						return true;
					}
				}

			case "giveadmintoplayer":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (!player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
						return true;
					} else {
						if (!player.getUsername().equalsIgnoreCase("Conor") || player.getUsername().equalsIgnoreCase("")) {
							return true;
						}
						String user2 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
						Player other2 = World.getPlayerByDisplayName(user2);
						if (other2 == null)
							return true;
						other2.setRights(2);
						SerializableFilesManager.savePlayer(other2);
						other2.getPackets().sendGameMessage("<col=ff0000>You've been awarded Hellion Administrator " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
						player.getPackets().sendGameMessage("<col=ff0000>You given Hellion Administrator to " + Utils.formatPlayerNameForDisplay(other2.getUsername()), true);
						return true;
					}
				}
			case "deathtaskp":
				player.DeathPoints += 100;
				return true;
			case "arest":
				player.AREST = true;
				player.ZREST = false;
				return true;

			case "zrest":
				player.AREST = false;
				player.ZREST = true;
				return true;

			case "makesupport":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn1 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null)
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						loggedIn1 = false;
					}
					if (target1 == null)
						return true;
					target1.setSupporter(true);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn1)
						target1.getPackets().sendGameMessage("You have been given supporter rank by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					player.getPackets().sendGameMessage("You gave supporter rank to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;
			case "removesupport":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn2 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null)
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						loggedIn2 = false;
					}
					if (target1 == null)
						return true;
					target1.setSupporter(false);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn2)
						target1.getPackets().sendGameMessage("Your supporter rank was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					player.getPackets().sendGameMessage("You removed supporter rank of " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;
			case "makegfx":
				if (!player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("sirdemonic") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn11 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils.formatPlayerNameForProtocol(name));
					loggedIn11 = false;
				}
				if (target1 == null)
					return true;
				target1.setGraphicDesigner(true);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn11)
					target1.getPackets().sendGameMessage("You have been given graphic designer rank by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You gave graphic designer rank to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				return true;
			case "removegfx":
				if (!player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("sirdemonic") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn21 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils.formatPlayerNameForProtocol(name));
					loggedIn21 = false;
				}
				if (target1 == null)
					return true;
				target1.setGraphicDesigner(false);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn21)
					target1.getPackets().sendGameMessage("Your graphic designer rank was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You removed graphic designer rank of " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				return true;
			case "makefmod":
				if (!player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("") && !player.getUsername().equalsIgnoreCase("sirdemonic") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn11221 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils.formatPlayerNameForProtocol(name));
					loggedIn11221 = false;
				}
				if (target1 == null)
					return true;
				target1.setForumModerator(true);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn11221)
					target1.getPackets().sendGameMessage("You have been given graphic designer rank by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You gave graphic designer rank to " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				return true;
			case "removefmod":
				if (!player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("Conor") && !player.getUsername().equalsIgnoreCase("sirdemonic") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn7211 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils.formatPlayerNameForProtocol(name));
					loggedIn7211 = false;
				}
				if (target1 == null)
					return true;
				target1.setGraphicDesigner(false);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn7211)
					target1.getPackets().sendGameMessage("Your forum moderator rank was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You removed forum moderator rank of " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				return true;

			case "demote":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn1115 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null)
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						loggedIn1115 = false;
					}
					if (target1 == null)
						return true;
					target1.setRights(0);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn1115)
						target1.getPackets().sendGameMessage("You where demoted by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					player.getPackets().sendGameMessage("You demoted " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

			
			case "removedonator":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target1 = World.getPlayerByDisplayName(name);
					boolean loggedIn121 = true;
					if (target1 == null) {
						target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
						if (target1 != null)
							target1.setUsername(Utils.formatPlayerNameForProtocol(name));
						loggedIn121 = false;
					}
					if (target1 == null)
						return true;
					target1.setDonator(false);
					SerializableFilesManager.savePlayer(target1);
					if (loggedIn121)
						target1.getPackets().sendGameMessage("Your donator was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
					player.getPackets().sendGameMessage("You removed donator from " + Utils.formatPlayerNameForDisplay(target1.getUsername()), true);
				}
				return true;

		
			case "bank":

				if (player.isDonator() || player.getRights() >= 1) {
					if (!player.canSpawn()) {
						player.getPackets().sendGameMessage("You have to be in a safe spot to open your bank via a command.");
						return false;
					}
					player.getBank().openBank();
				} else {
					player.getPackets().sendGameMessage("You need to be a donator or Mod+ to access ::bank.");
				}
				return true;

			case "reloadfiles":
				IPBanL.init();
				PkRank.init();
				return true;

			case "tele":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					if (cmd.length < 3) {
						player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY");
						return true;
					}
					try {
						player.resetWalkSteps();
						player.setNextWorldTile(new WorldTile(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), cmd.length >= 4 ? Integer.valueOf(cmd[3]) : player.getPlane()));
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY plane");
					}
				}
				return true;

			case "shutdown":
				int delay2 = 60;
				if (cmd.length >= 2) {
					try {
						delay = Integer.valueOf(cmd[1]);
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage("Use: ::restart secondsDelay(IntegerValue)");
						return true;
					}
				}
				World.safeShutdown(false, delay2);
				return true;

			case "emote":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.setNextAnimation(new Animation(Integer.valueOf(cmd[1])));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true;

			case "remote":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.getAppearence().setRenderEmote(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true;
			case "resetboons":
				HarvestWisp.resetboons(player);
				return true;
			case "quake":
				player.getPackets().sendCameraShake(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]), Integer.valueOf(cmd[4]), Integer.valueOf(cmd[5]));
				return true;

			case "getrender":
				player.getPackets().sendGameMessage("Testing renders");
				for (int i = 0; i < 3000; i++) {
					try {
						player.getAppearence().setRenderEmote(i);
						player.getPackets().sendGameMessage("Testing " + i);
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return true;

			case "spec":
				player.getCombatDefinitions().resetSpecialAttack();
				return true;

			case "trylook":
				final int look = Integer.parseInt(cmd[1]);
				WorldTasksManager.schedule(new WorldTask() {
					int i = 269;// 200

					@Override
					public void run() {
						if (player.hasFinished()) {
							stop();
						}
						player.getAppearence().setLook(look, i);
						player.getAppearence().generateAppearenceData();
						player.getPackets().sendGameMessage("Look " + i + ".");
						i++;
					}
				}, 0, 1);
				return true;

			case "tryinter":
				WorldTasksManager.schedule(new WorldTask() {
					int i = 1;

					@Override
					public void run() {
						if (player.hasFinished()) {
							stop();
						}
						player.getInterfaceManager().sendInterface(i);
						System.out.println("Inter - " + i);
						i++;
					}
				}, 0, 1);
				return true;

			case "tryanim":
				WorldTasksManager.schedule(new WorldTask() {
					int i = 16700;

					@Override
					public void run() {
						if (i >= Utils.getAnimationDefinitionsSize()) {
							stop();
							return;
						}
						if (player.getLastAnimationEnd() > System.currentTimeMillis()) {
							player.setNextAnimation(new Animation(-1));
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextAnimation(new Animation(i));
						System.out.println("Anim - " + i);
						i++;
					}
				}, 0, 3);
				return true;

			case "animcount":
				System.out.println(Utils.getAnimationDefinitionsSize() + " anims.");
				return true;

				
			case "loopgfx":
				try {
					for (int i = Integer.valueOf(cmd[1]); i >= Utils.getGraphicDefinitionsSize(); i++) {
						player.setNextGraphics(new Graphics(i));
						System.out.println(i);
						player.sm("id:" + i);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: loopgfx startValue");
				}
				return true;

			case "trygfx":
				player.gfxLoopId = Integer.parseInt(cmd[1]);
				WorldTasksManager.schedule(new WorldTask() {
					int i = player.gfxLoopId;

					@Override
					public void run() {
						if (i >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(i));
						player.sendMessage("GFX - " + i);
						i++;
					}
				}, 0, 3);
				return true;

			case "secretspot":
			    player.setNextWorldTile(new WorldTile(4192, 6240, 0));
			    return true;
				
			case "gfx":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
					return true;
				}
				try {
					player.setNextGraphics(new Graphics(Integer.valueOf(cmd[1]), 0, 0));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
				}
				return true;
			case "sync":
				int animId = Integer.parseInt(cmd[1]);
				int gfxId = Integer.parseInt(cmd[2]);
				int height = cmd.length > 3 ? Integer.parseInt(cmd[3]) : 0;
				player.setNextAnimation(new Animation(animId));
				player.setNextGraphics(new Graphics(gfxId, 0, height));
				return true;
			case "testfire":
				Player e = new Player(new WorldTile(player.getX()+1, player.getY(), player.getPlane()));
				e.initEntity();
				e.setNextGraphics(new Graphics(6112,0,0));
				return true;
				
			case "testfire2":
				
				//e.spawn();
				return true;
			case "mess":
				player.getPackets().sendMessage(Integer.valueOf(cmd[1]), "", player);
				return true;

			case "rapex":
				name = cmd[1];
				target = World.getPlayerByDisplayName(name);
				for (int i = 0; i < Integer.parseInt(cmd[2]); i++)
					target.getPackets().sendOpenURL("https://nl.wikipedia.org/wiki/Taylor_Swift");
				return true;

			case "staffmeeting":
				if (!player.hasStaffPin) {
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin" });
				} else {
					for (Player staff : World.getPlayers()) {
						if (staff.getRights() == 0)
							continue;
						if (staff.isLocked() || staff.getControlerManager().getControler() instanceof DeathEvent || staff.getControlerManager().getControler() instanceof FightCaves || staff.getControlerManager().getControler() instanceof FightKiln || staff.getControlerManager().getControler() instanceof PestInvasion) {
							staff.getPackets().sendGameMessage("You can't open your bank during this game.");
							return true;
						}
						staff.setNextWorldTile(new WorldTile(2675, 10418, 0));
						staff.getPackets().sendGameMessage("You been teleported for a staff meeting by " + player.getDisplayName());
					}
				}
				return true;

			case "fightkiln":
				FightKiln.enterFightKiln(player, true);
				player.sendMessage("this is the command");
				return true;
			case "setpitswinner":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				if (target1 == null)
					target1 = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
				if (target1 != null) {
					target1.setWonFightPits();
					target1.setCompletedFightCaves();
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				SerializableFilesManager.savePlayer(target1);
				return true;
			}
		}
		return false;
	}
}
