package com.rs.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.Launcher;
import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.Hit.HitLook;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.map.MapUtils;
import com.rs.game.map.MapUtils.Structure;
import com.rs.game.minigames.GodWarsBosses;
import com.rs.game.minigames.PuroPuro;
import com.rs.game.minigames.WarriorsGuild;
import com.rs.game.minigames.ZarosGodwars;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.RequestController;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.minigames.warbands.Warbands;
import com.rs.game.minigames.warbands.Warbands.WarbandEvent;
import com.rs.game.mysql.DatabaseManager;
import com.rs.game.npc.NPC;
import com.rs.game.npc.corp.CorporealBeast;
import com.rs.game.npc.dragons.KingBlackDragon;
import com.rs.game.npc.dragons.RuneDragon;
import com.rs.game.npc.glacor.Glacor;
import com.rs.game.npc.godwars.GodWarMinion;
import com.rs.game.npc.godwars.GodwarsInstanceBoss;
import com.rs.game.npc.godwars.armadyl.GodwarsArmadylFaction;
import com.rs.game.npc.godwars.armadyl.KreeArra;
import com.rs.game.npc.godwars.bandos.GeneralGraardor;
import com.rs.game.npc.godwars.bandos.GodwarsBandosFaction;
import com.rs.game.npc.godwars.gielinor.Vindicta;
import com.rs.game.npc.godwars.saradomin.CommanderZilyana;
import com.rs.game.npc.godwars.saradomin.GodwarsSaradominFaction;
import com.rs.game.npc.godwars.zammorak.GodwarsZammorakFaction;
import com.rs.game.npc.godwars.zammorak.KrilTstsaroth;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.npc.godwars.zaros.NexMinion;
import com.rs.game.npc.kalph.KalphiteQueen;
import com.rs.game.npc.kalphiteKning.KalphiteKing;
import com.rs.game.npc.nomad.FlameVortex;
import com.rs.game.npc.nomad.Nomad;
import com.rs.game.npc.others.AbyssalDemon;
import com.rs.game.npc.others.Avaryss;
import com.rs.game.npc.others.Bork;
import com.rs.game.npc.others.DarkLord;
import com.rs.game.npc.others.Elemental;
import com.rs.game.npc.others.HunterNetNPC;
import com.rs.game.npc.others.HunterTrapNPC;
import com.rs.game.npc.others.Leeuni;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.others.Lucien;
import com.rs.game.npc.others.MasterOfFear;
import com.rs.game.npc.others.MercenaryMage;
import com.rs.game.npc.others.NecroLord;
import com.rs.game.npc.others.Revenant;
import com.rs.game.npc.others.SeaTrollQueen;
import com.rs.game.npc.others.TormentedDemon;
import com.rs.game.npc.others.Werewolf;
import com.rs.game.npc.slayer.GanodermicBeast;
import com.rs.game.npc.slayer.Gargoyle;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.hunter.TrapAction.HunterNPC;
import com.rs.game.player.actions.objects.EvilTree;
import com.rs.game.player.actions.runecrafting.SiphonActionNodes;
import com.rs.game.player.content.GuessTheCode;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.LivingRockCavern;
import com.rs.game.player.content.Lottery;
import com.rs.game.player.content.PenguinEvent;
import com.rs.game.player.content.PresentsManager;
import com.rs.game.player.content.RiddleHandler;
import com.rs.game.player.content.Tournaments;
import com.rs.game.player.content.XPWell;
//import com.rs.game.player.content.GrandExchange.Offers;
import com.rs.game.player.content.botanybay.BotanyBay;
import com.rs.game.player.content.custom.ActivityHandler;
import com.rs.game.player.content.shooting.ShootingStar;
import com.rs.game.player.content.wildyrework.WildyInfernalBow;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.route.Flags;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.world.GlobalBossCounter;
import com.rs.game.world.GlobalCapeCounter;
import com.rs.game.world.GlobalItemCounter;
import com.rs.game.world.RecordHandler;
import com.rs.utils.AntiFlood;
import com.rs.utils.Colors;
import com.rs.utils.IPBanL;
import com.rs.utils.KillStreakRank;
import com.rs.utils.Logger;
import com.rs.utils.Misc;
import com.rs.utils.PkRank;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public final class World {

	public static Object fucker;
	public static int wellAmount;
	public static boolean wellActive = false;
	public static int exiting_delay;
	public static long exiting_start;
	private static DatabaseManager database = new DatabaseManager();
	// bosstimers
	public static String[] TimedBosses;
	public static long[] FastestTime;
	public static String[] playerLeader;
	// botnaybay
	private static BotanyBay botanyBay;

	// tournaments
	public static boolean competitionStarted;

	public static DatabaseManager database() {
		return database;
	}

	public static int getWellAmount() {
		return wellAmount;
	}

	public static void addWellAmount(String displayName, int amount) {
		wellAmount += amount;
		sendWorldMessage("<col=FF0000>" + displayName + " has contributed " + NumberFormat.getNumberInstance(Locale.US).format(amount) + " GP to the XP well! Progress now: " + ((World.getWellAmount() * 100) / Settings.WELL_MAX_AMOUNT) + "%!", false);
	}

	private static void setWellAmount(int amount) {
		wellAmount = amount;
	}

	public static void resetWell() {
		wellAmount = 0;
		wellActive = false;
		sendWorldMessage("<col=FF0000>The XP well has been reset!", false);
	}

	public static boolean isWellActive() {
		return wellActive;
	}

	public static void setWellActive(boolean wellActive) {
		World.wellActive = wellActive;
	}

	public static void loadWell() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("./data/well/data.txt"));
		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] args = line.split(" ");
			if (args[0].contains("true")) {
				World.setWellActive(true);
				XPWell.taskTime = Integer.parseInt(args[1]);
				XPWell.taskAmount = Integer.parseInt(args[1]);
				XPWell.setWellTask();
			} else {
				setWellAmount(Integer.parseInt(args[1]));
			}
		}
	}

	private static final EntityList<Player> players = new EntityList<Player>(Settings.PLAYERS_LIMIT);

	private static final EntityList<NPC> npcs = new EntityList<NPC>(Settings.NPCS_LIMIT);
	private static final Map<Integer, Region> regions = Collections.synchronizedMap(new HashMap<Integer, Region>());

	// private static final Object lock = new Object();

	public static final boolean containsObjectWithId(WorldTile tile, int id) {
		return getRegion(tile.getRegionId()).containsObjectWithId(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), id);
	}

	public static final WorldObject getObjectWithId(WorldTile tile, int id) {
		return getRegion(tile.getRegionId()).getObjectWithId(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), id);
	}

	public static NPC getNPC(int npcId) {
		for (NPC npc : getNPCs()) {
			if (npc.getId() == npcId) {
				return npc;
			}
		}
		return null;
	}

	/**
	 * boss timers
	 * 
	 * @throws IOException
	 */
	public static void loadBossTimes() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/bosstimer/players.txt"));
		String line = "";
		long value = 0;
		int index = 0;
		while ((line = br.readLine()) != null) {
			playerLeader[index] = line;
			index++;
		}
		index = 0;
		br.close();
		br = new BufferedReader(new FileReader("data/bosstimer/times.txt"));
		while ((line = br.readLine()) != null) {
			value = Long.parseLong(line.trim());
			FastestTime[index] = value;
			index++;
		}
		br.close();
	}

	/**
	 * saves the boss timer
	 * 
	 * @throws IOException
	 */
	public static void saveBossTimes() throws IOException {
		BufferedWriter bf = new BufferedWriter(new FileWriter("data/bosstimer/players.txt"));
		for (int i = 0; i <= 18; i++) {
			bf.write(playerLeader[i]);
			bf.newLine();

		}
		bf.close();
		bf = new BufferedWriter(new FileWriter("data/bosstimer/times.txt"));
		for (int i = 0; i <= 18; i++) {
			String temp = "" + FastestTime[i];
			bf.write(temp);
			bf.newLine();
		}
		bf.close();
	}

	public static void setTimedBosses() {
		TimedBosses[0] = "Dagannoth Rex";
		TimedBosses[1] = "Dagannoth Prime";
		TimedBosses[2] = "Dagannoth Supreme";
		TimedBosses[3] = "Chaos Elemental";
		TimedBosses[4] = "Corporeal Beast";
		TimedBosses[5] = "King Black Dragon";
		TimedBosses[6] = "Kree'arra";
		TimedBosses[7] = "K'ril Tsutsaroth";
		TimedBosses[8] = "Nex";
		TimedBosses[9] = "Commander Zilyana";
		TimedBosses[10] = "Kalphite Queen";
		TimedBosses[11] = "World-gorger";
		TimedBosses[12] = "TzTok-Jad";
		TimedBosses[13] = "Tokhaar-jad";
		TimedBosses[14] = "Har-Aken";
		TimedBosses[15] = "Blink";
		TimedBosses[16] = "Sea Troll Queen";
		TimedBosses[17] = "The Dark Lord";
	}

	public static final Player get(int index) {
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.definition().index() == index)
				return player;
		}
		return null;
	}

	public static void spinPlate(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (player.spinTimer > 0) {
						player.spinTimer--;
					}
					if (player.spinTimer == 1) {
						if (Misc.random(2) == 1) {
							player.setNextAnimation(new Animation(1906));
							player.getInventory().deleteItem(4613, 1);
							addGroundItem(new Item(4613, 1), new WorldTile(player.getX(), player.getY(), player.getPlane()), player, false, 180, true);
						}
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1000);
	}

	public static void addTime(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					player.onlinetime++;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 60000);
	}

	public static void startSmoke(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (player.getEquipment().getHatId() == 4164 || player.getEquipment().getHatId() == 13277 || player.getEquipment().getHatId() == 13263 || player.getEquipment().getHatId() == 14636 || player.getEquipment().getHatId() == 14637 || player.getEquipment().getHatId() == 15492 || player.getEquipment().getHatId() == 15496 || player.getEquipment().getHatId() == 15497 || player.getEquipment().getHatId() == 22528 || player.getEquipment().getHatId() == 22530 || player.getEquipment().getHatId() == 22532 || player.getEquipment().getHatId() == 22534 || player.getEquipment().getHatId() == 22536 || player.getEquipment().getHatId() == 22538 || player.getEquipment().getHatId() == 22540 || player.getEquipment().getHatId() == 22542 || player.getEquipment().getHatId() == 22544 || player.getEquipment().getHatId() == 22546 || player.getEquipment().getHatId() == 22548 || player.getEquipment().getHatId() == 22550) {
						player.getPackets().sendGameMessage("Your equipment protects you from the smoke.");
					} else {
						if (player.getHitpoints() > 120) {
							player.applyHit(new Hit(player, 120, HitLook.REGULAR_DAMAGE));
							player.getPackets().sendGameMessage("You take damage from the smoke.");
						}
					}
					if (!player.isAtSmokeyArea()) {
						cancel();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 12000);
	}

	public static boolean killedTree;
	public static boolean treeEvent;

	public static void startEvilTree() {
		WorldObject evilTree = new WorldObject(11922, 10, 0, 2456, 2835, 0);
		final WorldObject deadTree = new WorldObject(12715, 10, 0, 2456, 2835, 0);
		spawnObject(evilTree, true);
		EvilTree.health = 5000;
		treeEvent = true;
		killedTree = false;
		ActivityHandler.setEvilTree("An Evil Tree has appeared nearby Moblishing Armies, talk to a Spirit Tree to reach it!");
		sendWorldMessage("<img=6><col=FFA500><shad=000000>An Evil Tree has appeared nearby Moblishing Armies, talk to a Spirit Tree to reach it!", false);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (EvilTree.health <= 0) {
						spawnTemporaryObject(deadTree, 600000, true);
						killedTree = true;
						ActivityHandler.setEvilTree("No evil tree, you've to wait until the next event.");
						sendWorldMessage("<img=6><col=FFA500><shad=000000>The Evil Tree has been defeated!", false);
						executeTree();
						cancel();
						WorldTasksManager.schedule(new WorldTask() {
							int loop = 0;

							@Override
							public void run() {
								if (loop == 600) {
									treeEvent = false;
									killedTree = false;
									cancel();
								}
								loop++;
							}
						}, 0, 1);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1000);
	}

	public static void executeTree() {
		final int time = Misc.random(3600000, 7200000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int loop = 0;

			@Override
			public void run() {
				try {
					if (loop == time) {
						startEvilTree();
						cancel();
					}
					loop++;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1);
	}
	/**
	 * shooting star handler
	 */
	public static ShootingStar shootingStar;
	
	public static void executeShootingStar() {
		int time = 9500000;
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				shootingStar = new ShootingStar();
			}
		}, 0, time);
	}

	/**
	 * Saves all the files that needs to be written to files
	 */
	public static void saveFiles() {
		final int time = 3600000;
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int loop = 0;

			@Override
			public void run() {
				try {
					if (loop == time) {
						GlobalBossCounter.Save();
						GlobalItemCounter.Save();
						GlobalCapeCounter.save();
						RecordHandler.save();
						Logger.log("System : World Saved...", true);
						cancel();
					}
					loop++;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1);
	}

	public static void startDesert(final Player player) {
		int mili = 90000;
		if ((player.getEquipment().getHatId() == 6382 && (player.getEquipment().getChestId() == 6384 || player.getEquipment().getChestId() == 6388) && (player.getEquipment().getLegsId() == 6390 || player.getEquipment().getLegsId() == 6386)) || (player.getEquipment().getChestId() == 1833 && player.getEquipment().getLegsId() == 1835 && player.getEquipment().getBootsId() == 1837) || ((player.getEquipment().getHatId() == 6392 || player.getEquipment().getHatId() == 6400) && (player.getEquipment().getChestId() == 6394 || player.getEquipment().getChestId() == 6402) && (player.getEquipment().getLegsId() == 6396 || player.getEquipment().getLegsId() == 6398 || player.getEquipment().getLegsId() == 6404 || player.getEquipment().getLegsId() == 6406)) || (player.getEquipment().getChestId() == 1844 && player.getEquipment().getLegsId() == 1845 && player.getEquipment().getBootsId() == 1846)) {
			mili = 120000;
		}
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (!player.isAtDesertArea()) {
						return;
					}
					for (int i = 0; i <= 28; i++) {
						evaporate(player);
					}
					if (player.isAtDesertArea()) {
						if (player.getInventory().containsItem(1823, 1)) {
							player.setNextAnimation(new Animation(829));
							player.getInventory().deleteItem(1823, 1);
							player.getInventory().addItem(1825, 1);
							player.getPackets().sendGameMessage("You drink from the waterskin.");
						} else if (player.getInventory().containsItem(1825, 1)) {
							player.setNextAnimation(new Animation(829));
							player.getInventory().deleteItem(1825, 1);
							player.getInventory().addItem(1827, 1);
							player.getPackets().sendGameMessage("You drink from the waterskin.");
						} else if (player.getInventory().containsItem(1827, 1)) {
							player.setNextAnimation(new Animation(829));
							player.getInventory().deleteItem(1827, 1);
							player.getInventory().addItem(1829, 1);
							player.getPackets().sendGameMessage("You drink from the waterskin.");
						} else if (player.getInventory().containsItem(1829, 1)) {
							player.setNextAnimation(new Animation(829));
							player.getInventory().deleteItem(1829, 1);
							player.getInventory().addItem(1831, 1);
							player.getPackets().sendGameMessage("You drink from the waterskin.");
						} else if (player.getInventory().containsItem(6794, 1)) {
							player.setNextAnimation(new Animation(829));
							player.getInventory().deleteItem(6794, 1);
							player.getPackets().sendGameMessage("You eat one of your choc-ices.");
							player.heal(70);
						} else {
							int damage = 50;
							if (player.getEquipment().getShieldId() == 18346) {
								player.applyHit(new Hit(player, (damage - 50), HitLook.REGULAR_DAMAGE));
							} else {
								player.applyHit(new Hit(player, damage, HitLook.REGULAR_DAMAGE));
							}
							player.getPackets().sendGameMessage("You take damage from the desert heat.");
						}
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, mili);
	}

	public static void evaporate(final Player player) {
		if (player.getInventory().containsItem(227, 1)) {
			player.getInventory().deleteItem(227, 1);
			player.getInventory().addItem(229, 1);
		} else if (player.getInventory().containsItem(1921, 1)) {
			player.getInventory().deleteItem(1921, 1);
			player.getInventory().addItem(1923, 1);
		} else if (player.getInventory().containsItem(1929, 1)) {
			player.getInventory().deleteItem(1929, 1);
			player.getInventory().addItem(1925, 1);
		} else if (player.getInventory().containsItem(1937, 1)) {
			player.getInventory().deleteItem(1937, 1);
			player.getInventory().addItem(1935, 1);
		} else if (player.getInventory().containsItem(4458, 1)) {
			player.getInventory().deleteItem(4458, 1);
			player.getInventory().addItem(1980, 1);
		}

	}

	private static void addTournamentTick() {
		final Tournaments ts = new Tournaments();
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					ts.tick();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 62, TimeUnit.SECONDS);
	}

	public static final void init() {
		// addLogicPacketsTask();
		WildyInfernalBow.startEvent();
		// spawnStar();
		ServerMessages();
		raidCall();
		executeShootingStar();
		addTournamentTick();
		// sendLottery(); currently disabled
		WildyHandler();
		WildyCrate();
		startEvilTree();
		// bossRaid();
		//penguinHS();
		autoEvent();
		SiphonActionNodes.init();
		addTriviaBotTask();
		addRestoreRunEnergyTask();
		addDrainPrayerTask();
		addRestoreHitPointsTask();
		addRestoreSkillsTask();
		addRestoreSpecialAttackTask();
		addRestoreShopItemsTask();
		addSummoningEffectTask();
		addOwnedObjectsTask();
		LivingRockCavern.init();
		addListUpdateTask();
		WarriorsGuild.init();
		ImpSpawn();
		saveFiles();
		presentsGiveAway();
		PuroPuro.initPuroImplings();
		// CrystalTree(); todo
		executeSpotLightSkill();
		executeDonatorDivine();
		artisanWorkShopBonusExp();
	    addWarbandsEventManager(); //not done yet
	    addScheduledRestart();
		if (TimedBosses == null) {
			TimedBosses = new String[40];
			setTimedBosses();
		}
		if (playerLeader == null)
			playerLeader = new String[40];
		if (FastestTime == null)
			FastestTime = new long[40];
		try {
			loadBossTimes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new GuessTheCode();
	}
/**
 * handles warbands event
 */
	public static void addWarbandsEventManager() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if (Warbands.warband == null) {
					int random = Utils.random(WarbandEvent.values().length);
					if (WarbandEvent.getEvent(random) != null)
						Warbands.warband = new Warbands(random);
					return;
				}
				Warbands.warband.finish();
			}
		}, 20, 180, TimeUnit.MINUTES);
	}
	
	/**
	 * restarts the server every 20hour
	 */
	
	private static boolean hasRestart = false;
	public static void addScheduledRestart() {
		if(!hasRestart){
			hasRestart = true;
			return;
		}
		int time = 72000000;
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				World.sendWorldMessageAll(Colors.red+"<img=5>The server will automaticly restart in 20 minutes, don't do anything risky.");
				World.safeShutdown(true, 1200);
			}
		}, 0, time);
	}

	/*
	 * private static void addLogicPacketsTask() {
	 * CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
	 * 
	 * @Override public void run() { for(Player player : World.getPlayers()) {
	 * if(!player.hasStarted() || player.hasFinished()) continue;
	 * player.processLogicPackets(); } }
	 * 
	 * }, 300, 300); }
	 */

	public static List<WorldTile> restrictedTiles = new ArrayList<WorldTile>();

	public static void deleteObject(WorldTile tile) {
		restrictedTiles.add(tile);
	}

	private static void addOwnedObjectsTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					OwnedObjectManager.processAll();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
	}

	public static int star = 0;

	private static final void addListUpdateTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead() || !player.isRunning())
							continue;
						player.getPackets().sendIComponentText(751, 16, "Players Online: <col=00FF00>" + getPlayers().size());

						/*
						 * for (Item item :
						 * player.getInventory().getItems().toArray()) { if
						 * (item == null) continue;
						 * player.getInventory().deleteItem(item);
						 * player.setNextWorldTile(new WorldTile(3450, 3718,
						 * 0)); } for (Item item :
						 * player.getEquipment().getItems().toArray()) { if
						 * (item == null) continue;
						 * player.getEquipment().deleteItem(item.getId(),
						 * item.getAmount()); } if (player.getFamiliar() !=
						 * null) { if (player.getFamiliar().getBob() != null)
						 * player.getFamiliar().getBob().getBeastItems().clear()
						 * ; player.getFamiliar().dissmissFamiliar(false); }
						 */
						// player.setInDung(false);

					}

				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 10);
	}

	public enum ARTISAN_TYPES {
		HELM, CHEST, GLOVES, BOOTS
	};

	public static ARTISAN_TYPES artisanBonusExp;

	public static void artisanWorkShopBonusExp() {
		NPC suak = getNPC(6654);
		int time = 36000;
		CoresManager.fastExecutor.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					if (suak == null) {
						spawnNPC(6654, new WorldTile(3060, 3339, 0), -1, true);
					}
					ARTISAN_TYPES[] values2 = ARTISAN_TYPES.values();
					artisanBonusExp = values2[Utils.random(0, values2.length)];
					suak.setNextForceTalk(new ForceTalk("Smith " + artisanBonusExp));
				} catch (Throwable e) {
					World.sendWorldMessageAll("" + e);
					Logger.handle(e);
				}
			}
		}, 0, time);
		World.sendWorldMessageAll("" + artisanBonusExp);
	}

	// sportlight Skills

	public static int spotLightSkilling;
	private static int lastSpotLightSkill;

	private static int lastCombatSkill;
	private static int spotLightCombat;

	public static int getSpotLightSkill() {
		return spotLightSkilling;
	}

	public static int getSpotLightCombatSkill() {
		return spotLightCombat;
	}

	/**
	 * returns the value of the combat spotlight skill
	 * 
	 * @return
	 */
	public static int pickCombatSpotLightSkill() {
		lastCombatSkill = spotLightCombat;
		int skillId = Utils.random(0, 6);
		if (skillId == lastCombatSkill)
			return (skillId == 6 ? skillId - 1 : skillId + 1);
		return skillId;
	}

	public static int pickSkillinbSpotLightSkill() {
		lastSpotLightSkill = spotLightSkilling;
		int skillId = Utils.random(7, 25);
		if (skillId == lastSpotLightSkill)
			return (lastSpotLightSkill == 7 ? skillId + 1 : skillId - 1);
		return skillId;
	}

	public static String getSpotLightSkillName() {
		return Skills.SKILL_NAME[spotLightSkilling];
	}

	public static String getSpotLightCombatSkillName() {
		return Skills.SKILL_NAME[spotLightCombat];
	}

	public static void executeSpotLightSkill() {
		int time = 3600000;
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				// raid_active = false;
				try {
					spotLightCombat = pickCombatSpotLightSkill();
					spotLightSkilling = pickSkillinbSpotLightSkill();
					sendWorldMessageAll("<img=6><col=FFA500><shad=000000>The Spotlight skills have changed. The skills are " + Colors.green + "" + getSpotLightCombatSkillName() + "</col> and " + Colors.green + "" + getSpotLightSkillName() + "</col>.");
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);
	}
	/**
	 * array of possible locations
	 */
	private static int divineLocations[] = {90229,90230,90231,87273,87272,87271,87280,87280,87282,87281,87279,87278,57287,87269,87268,87267,87266};
	/**
	 * spawns every hour a new divine location
	 * 
	 */
	public static void executeDonatorDivine() {
		int time = 3600000;
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				
				try {
					final WorldObject node = new WorldObject(divineLocations[Utils.random(0,divineLocations.length)], 10, 0, 1828,5837 , 0);
					final WorldObject node2 = new WorldObject(divineLocations[Utils.random(0,divineLocations.length)], 10, 0, 2147, 5538, 3);
					final WorldObject node3 = new WorldObject(divineLocations[Utils.random(0,divineLocations.length)], 10, 0, 2146, 5538, 3);
					World.spawnObject(node, true);	
					World.spawnObject(node2, true);	
					World.spawnObject(node3, true);	
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);
	}


	// Automated Events
	public static boolean bandos;
	public static boolean armadyl;
	public static boolean zamorak;
	public static boolean saradomin;
	public static boolean dungeoneering;
	public static boolean cannonball;
	public static boolean doubleexp;
	public static boolean nex;
	public static boolean sunfreet;
	public static boolean corp;
	public static boolean doubledrops;
	public static boolean slayerpoints;
	public static boolean moreprayer;
	public static boolean quadcharms;

	public static void autoEvent() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				bandos = false;
				armadyl = false;
				zamorak = false;
				saradomin = false;
				dungeoneering = false;
				cannonball = false;
				doubleexp = false;
				nex = false;
				sunfreet = false;
				corp = false;
				doubledrops = false;
				slayerpoints = false;
				moreprayer = false;
				quadcharms = false;
				try {
					int event = Misc.random(225);
					if (event >= 1 && event <= 15) {
						bandos = true;
						ActivityHandler.setworldEvent("Mass Bossing event at Bandos! No Kill Count required!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Bandos! No Kill Count required!", false);
					} else if (event >= 16 && event <= 30) {
						armadyl = true;
						ActivityHandler.setworldEvent("Mass Bossing event at Armadyl! No Kill Count required!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Armadyl! No Kill Count required!", false);
					} else if (event >= 31 && event <= 45) {
						zamorak = true;
						ActivityHandler.setworldEvent("Mass Bossing event at Zamorak! No Kill Count required!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Zamorak! No Kill Count required!", false);
					} else if (event >= 46 && event <= 60) {
						saradomin = true;
						ActivityHandler.setworldEvent("Mass Bossing event at Saradomin! No Kill Count required!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Saradomin! No Kill Count required!", false);
					} else if (event >= 61 && event <= 65) {
						dungeoneering = true;
						ActivityHandler.setworldEvent("Group Dungeoneering event! Come explore Dungeons with Double EXP and Tokens!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Group Dungeoneering event! Come explore Dungeons with Double EXP and Tokens!", false);
					} else if (event >= 66 && event <= 75) {
						cannonball = true;
						ActivityHandler.setworldEvent("The furnaces have improved, take advantage to make 2x the amount of Cannonballs!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] The furnaces have improved, take advantage to make 2x the amount of Cannonballs!", false);
					} else if (event == 76 || event == 80) {
						doubleexp = true;
						ActivityHandler.setworldEvent("Enjoy some Double Exp in every skill! Take advantage to max out!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Enjoy some Double Exp in every skill! Take advantage to max out!", false);
					} else if (event >= 81 && event <= 100) {
						nex = true;
						ActivityHandler.setworldEvent("Mass Bossing event at Nex! Get your torva now!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Nex! Get your torva now!", false);
					} else if (event >= 121 && event <= 145) {
						corp = true;
						ActivityHandler.setworldEvent("Mass Bossing event at Corp! Get your sigils now!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Corp! Get your sigils now!", false);
					} else if (event == 146) {
						doubledrops = true;
						ActivityHandler.setworldEvent("Double Drops are now on, take advantage and gain double the wealth!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Double Drops are now on, take advantage and gain double the wealth!", false);
					} else if (event >= 147 && event <= 160) {
						slayerpoints = true;
						ActivityHandler.setworldEvent("Gain double the amount of slayer points when completing a task!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Gain double the amount of slayer points when completing a task!", false);
					} else if (event >= 181 && event <= 190) {
						quadcharms = true;
						ActivityHandler.setworldEvent("Monsters are now dropping double the amounts of charms!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Monsters are now dropping double the amount of charms!", false);
					} else {
						ActivityHandler.setworldEvent("Unfortunately, there are no events occuring this hour!");
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Unfortunately, there are no events occuring this hour!", false);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 3600000);
	}

	public static void killRaid() {
		for (NPC n : World.getNPCs()) {
			if (n == null || (n.getId() != 3064 && n.getId() != 10495 && n.getId() != 3450 && n.getId() != 3063 && n.getId() != 3058 && n.getId() != 4706 && n.getId() != 10769 && n.getId() != 10761 && n.getId() != 10717 && n.getId() != 15581 && n.getId() != 999 && n.getId() != 998 && n.getId() != 1000 && n.getId() != 14550 && n.getId() != 8335 && n.getId() != 2709 && n.getId() != 2710 && n.getId() != 2711 && n.getId() != 2712))
				continue;
			n.sendDeath(n);
		}
	}

	public static void presentsGiveAway() {
		int time = Misc.random(3600000, 5000000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					PresentsManager.givePresents();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);

	}

	// raid shout
	public static void raidCall() {
		int time = Misc.random(3600, 7200);
		final int random = Utils.random(10);
		final NPC guardians = World.getNPC(13457);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (random <= 2) {
						guardians.setNextForceTalk(new ForceTalk("The war is on! All forces to the area!"));
					} else if (random > 2 && random <= 5) {
						guardians.setNextForceTalk(new ForceTalk("This home is the only place we've left. I won't give it up!"));
					} else {
						guardians.setNextForceTalk(new ForceTalk("Is that all you've got, filty monkeys!"));
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);

	}

	private static boolean raid_active;

	public static boolean hasraid_active() {
		return raid_active;
	}

	public static boolean setraid_active() {
		return raid_active = true;
	}

	public static boolean removeraid_active() {
		return raid_active = false;
	}

	public static void bossRaid() {
		int time = Misc.random(3600000, 7200000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				// raid_active = false;
				try {
					int raid = Misc.random(7);
					if (raid == 1) {
						setraid_active();
						killRaid();
						ActivityHandler.setHomeRaids("Demons Raid: Kill them for some exp tokens/power crystals and demon armour.");
						spawnNPC(3064, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(10495, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(10495, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(10495, new WorldTile(3664, 3047, 0), -1, true, true, false);
						World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Champion of all Demons has appeared just outside of home! Fight them for Demon Flesh Armor and more!", false);
					} else if (raid == 2) {
						killRaid();
						setraid_active();
						ActivityHandler.setHomeRaids("Jogres Raid: Kill them for some exp tokens/power crystals and skilling supplies!");
						spawnNPC(3063, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(3450, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(3450, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(3450, new WorldTile(3664, 3047, 0), -1, true, true, false);
						World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Champion of all Jogres has appeared just outside of home! Fight him for Trading Sticks, Bone Masses and more!", false);
					} else if (raid == 3) {
						killRaid();
						setraid_active();
						ActivityHandler.setHomeRaids("Giants Raid: Kill them for some exp tokens/power crystals and defenders!");
						spawnNPC(3058, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(4706, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(10769, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(10717, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(10761, new WorldTile(3664, 3047, 0), -1, true, true, false);
						World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Champion of all Giants has appeared just outside of home! Fight him for Defenders, Masses of Charms and more!", false);
					} else if (raid == 4) {
						killRaid();
						setraid_active();
						ActivityHandler.setHomeRaids("Party Demon: Kill them for some exp tokens/power crystals and prayer exp!.");
						spawnNPC(15581, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(999, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(998, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(1000, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(14550, new WorldTile(3664, 3047, 0), -1, true, true, false);
						World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Vicious Party Demon has appeared just outside of home! Fight him for Demon Slayer Armor, Dragon Weapons and more!", false);
					} else if (raid == 5) {
						killRaid();
						setraid_active();
						ActivityHandler.setHomeRaids("The Ultimate Mercenary Mage : Kill them for some exp tokens/power crystals and a change on a cylestical staf.");
						setraid_active();
						spawnNPC(8335, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(2712, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(2710, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(2711, new WorldTile(3664, 3047, 0), -1, true, true, false);
						spawnNPC(2709, new WorldTile(3664, 3047, 0), -1, true, true, false);
						World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Ultimate Mercenary Mage has appeared just outside of home! Fight him for Infinity Sets, Masses of Runes and more!", false);
					} else {
						removeraid_active();
						ActivityHandler.setHomeRaids("Our home is safe for this hour.");
						World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] Luckily, our home is well protected and there are no current attacks!", false);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);
	}

	public static void CrystalTree() {
		int time = Misc.random(3600000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				// raid_active = false;
				try {
					sendWorldMessage("The crystal tree has re-spawned his roots, go quick before they are chopped, talk to the wily manager for a quick teleport.", false);
					World.spawnObject(new WorldObject(87536, 10, 0, 2952, 3915, 0), true);
					World.spawnObject(new WorldObject(87536, 10, 0, 2950, 3915, 0), true);
					World.spawnObject(new WorldObject(87536, 10, 0, 2954, 3912, 0), true);
					World.spawnObject(new WorldObject(87536, 10, 0, 2952, 3911, 0), true);
					World.spawnObject(new WorldObject(87536, 10, 0, 2950, 3910, 0), true);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);
	}

	public static void penguinHS() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
						players.penguin = false;
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8104)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8105)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8107)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8108)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8109)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8110)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 14766)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 14415)
							continue;
						n.sendDeath(n);
					}
					PenguinEvent.startEvent();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 3600000);
	}

	public static final int[] IMPS = {};

	public static void ImpSpawn() {
		int time = Misc.random(3600000, 7200000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				// raid_active = false;
				try {
					int spawn = Misc.random(3);
					if (spawn == 1) {
						spawnNPC(7903, new WorldTile(3702, 2969, 0), -1, true, true, false);
						spawnNPC(6054, new WorldTile(3694, 2996, 0), -1, true, true, false);
						spawnNPC(1035, new WorldTile(3657, 2992, 0), -1, true, true, false);
						spawnNPC(6054, new WorldTile(3679, 3027, 0), -1, true, true, false);
					} else if (spawn == 2) {
						spawnNPC(6054, new WorldTile(3694, 2996, 0), -1, true, true, false);
						spawnNPC(1035, new WorldTile(3657, 2992, 0), -1, true, true, false);
						spawnNPC(7903, new WorldTile(1649, 5267, 0), -1, true, true, false);
					} else if (spawn == 3) {
						spawnNPC(7903, new WorldTile(1649, 5267, 0), -1, true, true, false);
						spawnNPC(7903, new WorldTile(1606, 5278, 0), -1, true, true, false);
						spawnNPC(7903, new WorldTile(3702, 2969, 0), -1, true, true, false);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);
	}

	public static void killImps() {
		for (NPC n : World.getNPCs()) {
			if (n == null || (n.getId() != 3064 && n.getId() != 10495 && n.getId() != 3450 && n.getId() != 3063 && n.getId() != 3058 && n.getId() != 4706 && n.getId() != 10769 && n.getId() != 10761 && n.getId() != 10717 && n.getId() != 15581 && n.getId() != 999 && n.getId() != 998 && n.getId() != 1000 && n.getId() != 14550 && n.getId() != 8335 && n.getId() != 2709 && n.getId() != 2710 && n.getId() != 2711 && n.getId() != 2712))
				continue;
			n.sendDeath(n);
		}
	}

	public static void ServerMessages() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int message = Utils.random(1, 10);

			@Override
			public void run() {
				if (message == 1) {
					World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Do not ask for staff, staff ranks are earned!", true);
					System.out.println("[ServerMessages] Server Message sent successfully.");
				}
				if (message == 2) {
					// World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Register
					// on the forums at
					// http://"+Settings.SERVER_NAME+"'.com/forums. You get
					// rewarded for posting!", false);
					System.out.println("[ServerMessages] Server Message sent successfully.");
				}
				if (message == 3) {
					World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Enjoy our new Automated Events system!", false);
					System.out.println("[ServerMessages] Server Message sent successfully.");
				}
				if (message == 4) {
					World.sendWorldMessage("<img=6><col=FFA500><shad=000000>To answer trivia questions, do ::answer (answer)!", false);
					System.out.println("[ServerMessages] Server Message sent successfully.");
				}
				if (message == 5) {
					World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Did you know that you can toggle roofs in your client settings?", true);
					System.out.println("[ServerMessages] Server Message sent successfully.");
				}
				if (message == 6) {
					World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Don't forget to ::vote twice a day for epic rewards!", true);
					System.out.println("[ServerMessages] Server Message sent successfully.");
				}
				if (message == 7) {
					World.sendWorldMessage("<img=6><col=FFA500><shad=000000>If there are 15+ online there is automatic double exp, so get advertising!", true);
					System.out.println("[ServerMessages] Server Message sent successfully.");
				}
				if (message == 8) {
					World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Did you know that there's a change from every monster to get a statuette drop, if you are in the wilderness.", false);
					System.out.println("[ServerMessages] Server Message sent successfully.");
					message = 0;
				}
				message++;
			}
		}, 0, 9000000);
	}

	public static void crashedStar() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					star = 0;
					World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Falador!", false);
					World.spawnObject(new WorldObject(38660, 10, 0, 3028, 3365, 0), true);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1200000);
	}
	/*
	 * public static void spawnStar() { WorldTasksManager.schedule(new
	 * WorldTask() { int loop;
	 * 
	 * @Override public void run() { if (loop == 1200) { star = 0;
	 * ShootingStar.spawnRandomStar(); } loop++; } }, 0, 1); }
	 */

	public static void removeStarSprite(final Player player) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 50) {
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8091)
							continue;
						n.sendDeath(n);
					}
				}
				loop++;
			}
		}, 0, 1);
	}

	private static void addRestoreShopItemsTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					ShopsHandler.restoreShops();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30, TimeUnit.SECONDS);
	}

	/**
	 * Lobby Stuff
	 */

	private static final EntityList<Player> lobbyPlayers = new EntityList<Player>(Settings.PLAYERS_LIMIT);

	public static final Player getLobbyPlayerByDisplayName(String username) {
		String formatedUsername = Utils.formatPlayerNameForDisplay(username);
		for (Player player : getLobbyPlayers()) {
			if (player == null) {
				continue;
			}
			if (player.getUsername().equalsIgnoreCase(formatedUsername) || player.getDisplayName().equalsIgnoreCase(formatedUsername)) {
				return player;
			}
		}
		return null;
	}

	public static final EntityList<Player> getLobbyPlayers() {
		return lobbyPlayers;
	}

	public static void addPlayer(Player player) {
		if (World.containsPlayer(player.getUsername())) {
			player.forceLogout();
			return;
		}
		if (!player.isRobot()) {
			AntiFlood.add(player.getSession().getIP());
		}
		players.add(player);
		for (final Player p : getPlayers()) {
			if (p == null || p.isDead() || !p.isRunning())
				continue;
			p.getPackets().sendIComponentText(751, 16, "Players Online: <col=00FF00>" + getPlayers().size());
		}
	}

	/*
	 * public static final void addPlayer(Player player) { players.add(player);
	 * if (World.containsLobbyPlayer(player.getUsername())) {
	 * World.removeLobbyPlayer(player);
	 * AntiFlood.remove(player.getSession().getIP()); }
	 * AntiFlood.add(player.getSession().getIP()); }
	 */
	/**
	 * adss a player to the lobby , unused
	 * 
	 * @param player
	 */
	public static final void addLobbyPlayer(Player player) {
		lobbyPlayers.add(player);
		AntiFlood.add(player.getSession().getIP());
	}

	/**
	 * checks if a certain player is in the lobby
	 * 
	 * @param username
	 * @return
	 */
	public static final boolean containsLobbyPlayer(String username) {
		for (Player p2 : lobbyPlayers) {
			if (p2 == null) {
				continue;
			}
			if (p2.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * removes the player from the lobby
	 * 
	 * @param player
	 */
	public static void removeLobbyPlayer(Player player) {
		for (Player p : lobbyPlayers) {
			if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
				if (player.getCurrentFriendChat() != null) {
					player.getCurrentFriendChat().leaveChat(player, true);
				}
				lobbyPlayers.remove(p);
			}
		}
		AntiFlood.remove(player.getSession().getIP());
	}

	public static void removePlayer(Player player) {
		for (Player p : players) {
			if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
				players.remove(p);
			}
		}
		AntiFlood.remove(player.getSession().getIP());
	}

	private static final void addSummoningEffectTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.getFamiliar() == null || player.isDead() || !player.hasFinished())
							continue;
						if (player.getFamiliar().getOriginalId() == 6814) {
							player.heal(20);
							player.setNextGraphics(new Graphics(1507));
						}
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 15, TimeUnit.SECONDS);
	}

	private static final void addRestoreSpecialAttackTask() {

		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead() || !player.isRunning())
							continue;
						player.getCombatDefinitions().restoreSpecialAttack();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30000);
	}

	private static boolean checkAgility;
	public static Object deleteObject;

	private static final void addTriviaBotTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					RiddleHandler.Run();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 400000);
	}

	private static final void addRestoreRunEnergyTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead() || !player.isRunning() || (checkAgility && player.getSkills().getLevel(Skills.AGILITY) < 70))
							continue;
						player.restoreRunEnergy();
					}
					checkAgility = !checkAgility;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1000);
	}

	private static final void addDrainPrayerTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead() || !player.isRunning())
							continue;
						player.getPrayer().processPrayerDrain();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 600);
	}

	private static final void addRestoreHitPointsTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead() || !player.isRunning())
							continue;
						player.restoreHitPoints();
					}
					for (NPC npc : npcs) {
						if (npc == null || npc.isDead() || npc.hasFinished())
							continue;
						npc.restoreHitPoints();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 6000);
	}

	/**
	 * Wildy activity rework.
	 **/
	private static boolean wildy_event;

	public static boolean haswildy_event() {
		return wildy_event;
	}

	public boolean iswildy_event() {
		return wildy_event;
	}

	public static void setwildy_event() {
		wildy_event = true;
	}

	public static void removewildy_event() {
		wildy_event = false;
	}

	public static void WildyHandler() {
		/*
		 * final int time = Misc.random(3600000, 7200000);
		 * CoresManager.fastExecutor.schedule(new TimerTask() {
		 * 
		 * @Override public void run() { try { int spanws = Misc.random(6); if
		 * (spanws == 1) { setwildy_event(); WildyRuniteOre.startEvent(); } else
		 * if (spanws == 2) { setwildy_event(); WildyLavaShark.startEvent(); }
		 * else if (spanws == 3) { setwildy_event();
		 * WildyBloodTrees.startEvent(); } else if (spanws == 4) {
		 * setwildy_event(); } else if (spanws == 5) { setwildy_event();
		 * WildyWyrm.startEvent(); } else { ActivityHandler.
		 * setWildySkillingEvent("<col=0099cc>No event this hour."); World.
		 * sendWorldMessage("<img=7><col=ff0000>[Wildy event] It looks like Darius has found nothing in the wilderness this hour."
		 * , false); } } catch (Throwable e) { Logger.handle(e); } } }, 0,
		 * time);
		 */
	}

	public static void WildyCrate() {
		final int time = Misc.random(3600000, 7200000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					com.rs.game.player.content.wildyrework.WildyHandler.startEvent();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);
	}

	public static void RemoveWildyBoss() {
		for (NPC n : World.getNPCs()) {
			if (n == null || (n.getId() != 3064 && n.getId() != 10495 && n.getId() != 3450 && n.getId() != 3063 && n.getId() != 3058 && n.getId() != 4706 && n.getId() != 10769 && n.getId() != 10761 && n.getId() != 10717 && n.getId() != 15581 && n.getId() != 999 && n.getId() != 998 && n.getId() != 1000 && n.getId() != 14550 && n.getId() != 8335 && n.getId() != 2709 && n.getId() != 2710 && n.getId() != 2711 && n.getId() != 2712))
				continue;
			n.sendDeath(n);
			removewildy_event();
		}
	}

	private static final void addRestoreSkillsTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || !player.isRunning())
							continue;
						int ammountTimes = player.getPrayer().usingPrayer(0, 8) ? 2 : 1;
						if (player.isResting())
							ammountTimes += 1;
						boolean berserker = player.getPrayer().usingPrayer(1, 5);
						for (int skill = 0; skill < 25; skill++) {
							if (skill == Skills.SUMMONING)
								continue;
							for (int time = 0; time < ammountTimes; time++) {
								int currentLevel = player.getSkills().getLevel(skill);
								int normalLevel = player.getSkills().getLevelForXp(skill);
								if (currentLevel > normalLevel) {
									if (skill == Skills.ATTACK || skill == Skills.STRENGTH || skill == Skills.DEFENCE || skill == Skills.RANGE || skill == Skills.MAGIC) {
										if (berserker && Utils.getRandom(100) <= 15)
											continue;
									}
									player.getSkills().set(skill, currentLevel - 1);
								} else if (currentLevel < normalLevel)
									player.getSkills().set(skill, currentLevel + 1);
								else
									break;
							}
						}
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30000);

	}

	public static final Map<Integer, Region> getRegions() {
		// synchronized (lock) {
		return regions;
		// }
	}

	public static final Region getRegion(int id) {
		return getRegion(id, false);
	}

	public static final Region getRegion(int id, boolean load) {
		// synchronized (lock) {
		Region region = regions.get(id);
		if (region == null) {
			region = new Region(id);
			regions.put(id, region);
		}
		if (load)
			region.checkLoadMap();
		return region;
		// }
	}

	public static final void addNPC(NPC npc) {
		npcs.add(npc);
	}

	public static final void removeNPC(NPC npc) {
		npcs.remove(npc);
	}

	public static final NPC spawnNPC(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned, boolean checkGodwars) {
		NPC n = null;
		if (id == 5079)
			n = new HunterTrapNPC(HunterNPC.GREY_CHINCHOMPA, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5080)
			n = new HunterTrapNPC(HunterNPC.RED_CHINCHOMPA, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5081)
			n = new HunterTrapNPC(HunterNPC.FERRET, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6916)
			n = new HunterTrapNPC(HunterNPC.GECKO, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 7272)
			n = new HunterTrapNPC(HunterNPC.MONKEY, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 7272)
			n = new HunterTrapNPC(HunterNPC.RACCOON, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5073)
			n = new HunterTrapNPC(HunterNPC.CRIMSON_SWIFT, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5075)
			n = new HunterTrapNPC(HunterNPC.GOLDEN_WARBLER, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5076)
			n = new HunterTrapNPC(HunterNPC.COPPER_LONGTAIL, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5074)
			n = new HunterTrapNPC(HunterNPC.CERULEAN_TWITCH, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5072)
			n = new HunterTrapNPC(HunterNPC.TROPICAL_WAGTAIL, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 7031)
			n = new HunterTrapNPC(HunterNPC.WIMPY_BIRD, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 7010)
			n = new HunterTrapNPC(HunterNPC.GRENWALL, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 7012)
			n = new HunterTrapNPC(HunterNPC.PAWYA, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5088)
			n = new HunterTrapNPC(HunterNPC.BARB_TAILED_KEBBIT, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 13131)
			n = new HunterTrapNPC(HunterNPC.DRACONIC_JADINKP, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 13119)
			n = new HunterTrapNPC(HunterNPC.COMMON_JADINKO, id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 5117 || id == 5116 || id == 5114 || id == 5115)
			n = new HunterNetNPC(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 5533 && id <= 5558)
			n = new Elemental(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 3847)
			n = new SeaTrollQueen(id, tile, mapAreaNameHash, true, spawned);
		else if (id == 1610)
			n = new Gargoyle(id, tile, mapAreaNameHash, true);
		else if (id == 21136)
			n = new RuneDragon(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 14301)
			n = new Glacor(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
		else if (id == 7134)
			n = new Bork(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 16697 || id == 16698 || id == 16699)
			n = new KalphiteKing(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 17154 || id == 17151 || id == 17149 || id == 17153 || id == 17152 || id ==17150 || id == 17146)
			n = new NPC(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, true);
		else if (id >= 6026 && id <= 6045)
			n = new Werewolf(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 9441)
			n = new FlameVortex(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 8832 && id <= 8834)
			n = new LivingRock(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 13465 && id <= 13481)
			n = new Revenant(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 1158 || id == 1160)
			n = new KalphiteQueen(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 8528 && id <= 8532)
			n = new Nomad(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 6210 && id <= 6221)
			n = new GodwarsZammorakFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 6254 && id <= 6259)
			n = new GodwarsSaradominFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 22320) {
			System.out.println("Vindy spawned");
	    	n = new Vindicta(id, tile, mapAreaNameHash, true, spawned);
		}
	    else if (id >= 6229 && id <= 6246)
			n = new GodwarsArmadylFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
	    else if (id >= 6268 && id <= 6284)
			n = new GodwarsBandosFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6261 || id == 6263 || id == 6265)
			if (!checkGodwars)
				n = GodWarsBosses.graardorMinions[(id - 6261) / 2] = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
			else
				n = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6260)
			if (!checkGodwars)
				n = new GeneralGraardor(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
			else
				n = new GodwarsInstanceBoss(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6222)
			if (!checkGodwars)
				n = new KreeArra(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
			else
				n = new GodwarsInstanceBoss(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6223 || id == 6225 || id == 6227)
			if (!checkGodwars)
				n = GodWarsBosses.armadylMinions[(id - 6223) / 2] = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
			else
				n = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6203)
			if (!checkGodwars)
				n = new KrilTstsaroth(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
			else
				n = new GodwarsInstanceBoss(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6204 || id == 6206 || id == 6208)
			if (!checkGodwars)
				n = GodWarsBosses.zamorakMinions[(id - 6204) / 2] = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
			else
				n = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 50 || id == 2642)
			n = new KingBlackDragon(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 19553)
			n = new DarkLord(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 11751)
			n = new NecroLord(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 9462 && id <= 9467)
			n = new Strykewyrm(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
		else if (id == 6248 || id == 6250 || id == 6252)
			n = GodWarsBosses.commanderMinions[(id - 6248) / 2] = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6248 || id == 6250 || id == 6252)
			if (!checkGodwars)
				n = GodWarsBosses.commanderMinions[(id - 6248) / 2] = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
			else
				n = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6247)
			if (!checkGodwars)
				n = new CommanderZilyana(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
			else
				n = new GodwarsInstanceBoss(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6247)
			n = new CommanderZilyana(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 8133)
			n = new CorporealBeast(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 13447)
			n = ZarosGodwars.nex = new Nex(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 20616)// || id == 20612)
			n = new Avaryss(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 13451)
			n = ZarosGodwars.fumus = new NexMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 13452)
			n = ZarosGodwars.umbra = new NexMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 13453)
			n = ZarosGodwars.cruor = new NexMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 13454)
			n = ZarosGodwars.glacies = new NexMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 14256)
			n = new Lucien(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 8335)
			n = new MercenaryMage(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 8349 || id == 8450 || id == 8451)
			n = new TormentedDemon(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 15149)
			n = new MasterOfFear(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 14696)
			n = new GanodermicBeast(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 14847)
			n = new com.rs.game.npc.others.Dummy(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 1615)
			n = new AbyssalDemon(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
		else if (id == 13216)
			n = new Leeuni(id, tile, mapAreaNameHash, true);
		else
			n = new NPC(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		return n;
	}

	public static final NPC spawnNPC(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		return spawnNPC(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, false, false);
	}

	/*
	 * check if the entity region changed because moved or teled then we update
	 * it
	 */
	public static final void updateEntityRegion(Entity entity) {
		if (entity.hasFinished()) {
			if (entity instanceof Player)
				getRegion(entity.getLastRegionId()).removePlayerIndex(entity.getIndex());
			else
				getRegion(entity.getLastRegionId()).removeNPCIndex(entity.getIndex());
			return;
		}
		int regionId = entity.getRegionId();
		if (entity.getLastRegionId() != regionId) { // map region entity at
			if (entity instanceof Player) {
				if (entity.getLastRegionId() > 0)
					getRegion(entity.getLastRegionId()).removePlayerIndex(entity.getIndex());
				Region region = getRegion(regionId);
				region.addPlayerIndex(entity.getIndex());
				Player player = (Player) entity;
				int musicId = region.getMusicId();
				if (musicId != -1)
					player.getMusicsManager().checkMusic(musicId);
				player.getControlerManager().moved();
				if (player.hasStarted())
					checkInstancesAtMove(player, true);
			} else {
				if (entity.getLastRegionId() > 0)
					getRegion(entity.getLastRegionId()).removeNPCIndex(entity.getIndex());
				getRegion(regionId).addNPCIndex(entity.getIndex());
			}
			entity.checkMultiArea();
			entity.setLastRegionId(regionId);
		} else {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				player.getControlerManager().moved();
				if (player.hasStarted())
					checkInstancesAtMove(player, true);
			}
			entity.checkMultiArea();
		}
	}

	private static void checkInstancesAtMove(Player player, boolean isInstance) {
		if (player.getControlerManager().getControler() == null) {
			String control = null;
			if (!(player.getControlerManager().getControler() instanceof RequestController) && RequestController.inWarRequest(player))
				control = "clan_wars_request";
			else if (FfaZone.inArea(player))
				control = "clan_wars_ffa";
			if (player.getRegionId() == 13626)
				control = "Kalaboss";
			if (control != null && isInstance)
				player.getControlerManager().startControler(control);
		}
	}

	private static void checkControlersAtMove(Player player) {
		if (!(player.getControlerManager().getControler() instanceof RequestController) && RequestController.inWarRequest(player))
			player.getControlerManager().startControler("clan_wars_request");
		else if (DuelControler.isAtDuelArena(player))
			player.getControlerManager().startControler("DuelControler");
		else if (FfaZone.inArea(player))
			player.getControlerManager().startControler("clan_wars_ffa");
	}

	/*
	 * checks clip
	 */
	public static boolean canMoveNPC(int plane, int x, int y, int size) {
		for (int tileX = x; tileX < x + size; tileX++)
			for (int tileY = y; tileY < y + size; tileY++)
				if (getMask(plane, tileX, tileY) != 0)
					return false;
		return true;
	}

	/*
	 * checks clip
	 */
	public static boolean isNotCliped(int plane, int x, int y, int size) {
		for (int tileX = x; tileX < x + size; tileX++)
			for (int tileY = y; tileY < y + size; tileY++)
				if ((getMask(plane, tileX, tileY) & 2097152) != 0)
					return false;
		return true;
	}

	public static int getMask(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return -1;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		return region.getMask(tile.getPlane(), baseLocalX, baseLocalY);
	}

	public static void setMask(int plane, int x, int y, int mask) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		region.setMask(tile.getPlane(), baseLocalX, baseLocalY, mask);
	}

	public static int getRotation(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return 0;
		// int baseLocalX = x - ((regionId >> 8) * 64);
		// int baseLocalY = y - ((regionId & 0xff) * 64);
		// return region.getRotation(tile.getPlane(), baseLocalX, baseLocalY);
		return 0;
	}

	private static int getClipedOnlyMask(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return -1;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		return region.getMaskClipedOnly(tile.getPlane(), baseLocalX, baseLocalY);
	}

	public static final boolean checkProjectileStep(int plane, int x, int y, int dir, int size) {
		int xOffset = Utils.DIRECTION_DELTA_X[dir];
		int yOffset = Utils.DIRECTION_DELTA_Y[dir];
		/*
		 * int rotation = getRotation(plane,x+xOffset,y+yOffset); if(rotation !=
		 * 0) { dir += rotation; if(dir >= Utils.DIRECTION_DELTA_X.length) dir =
		 * dir - (Utils.DIRECTION_DELTA_X.length-1); xOffset =
		 * Utils.DIRECTION_DELTA_X[dir]; yOffset = Utils.DIRECTION_DELTA_Y[dir];
		 * }
		 */
		if (size == 1) {
			int mask = getClipedOnlyMask(plane, x + Utils.DIRECTION_DELTA_X[dir], y + Utils.DIRECTION_DELTA_Y[dir]);
			if (xOffset == -1 && yOffset == 0)
				return (mask & 0x42240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (mask & 0x60240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (mask & 0x40a40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (mask & 0x48240000) == 0;
			if (xOffset == -1 && yOffset == -1) {
				return (mask & 0x43a40000) == 0 && (getClipedOnlyMask(plane, x - 1, y) & 0x42240000) == 0 && (getClipedOnlyMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == 1 && yOffset == -1) {
				return (mask & 0x60e40000) == 0 && (getClipedOnlyMask(plane, x + 1, y) & 0x60240000) == 0 && (getClipedOnlyMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == -1 && yOffset == 1) {
				return (mask & 0x4e240000) == 0 && (getClipedOnlyMask(plane, x - 1, y) & 0x42240000) == 0 && (getClipedOnlyMask(plane, x, y + 1) & 0x48240000) == 0;
			}
			if (xOffset == 1 && yOffset == 1) {
				return (mask & 0x78240000) == 0 && (getClipedOnlyMask(plane, x + 1, y) & 0x60240000) == 0 && (getClipedOnlyMask(plane, x, y + 1) & 0x48240000) == 0;
			}
		} else if (size == 2) {
			if (xOffset == -1 && yOffset == 0)
				return (getClipedOnlyMask(plane, x - 1, y) & 0x43a40000) == 0 && (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4e240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (getClipedOnlyMask(plane, x + 2, y) & 0x60e40000) == 0 && (getClipedOnlyMask(plane, x + 2, y + 1) & 0x78240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (getClipedOnlyMask(plane, x, y - 1) & 0x43a40000) == 0 && (getClipedOnlyMask(plane, x + 1, y - 1) & 0x60e40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (getClipedOnlyMask(plane, x, y + 2) & 0x4e240000) == 0 && (getClipedOnlyMask(plane, x + 1, y + 2) & 0x78240000) == 0;
			if (xOffset == -1 && yOffset == -1)
				return (getClipedOnlyMask(plane, x - 1, y) & 0x4fa40000) == 0 && (getClipedOnlyMask(plane, x - 1, y - 1) & 0x43a40000) == 0 && (getClipedOnlyMask(plane, x, y - 1) & 0x63e40000) == 0;
			if (xOffset == 1 && yOffset == -1)
				return (getClipedOnlyMask(plane, x + 1, y - 1) & 0x63e40000) == 0 && (getClipedOnlyMask(plane, x + 2, y - 1) & 0x60e40000) == 0 && (getClipedOnlyMask(plane, x + 2, y) & 0x78e40000) == 0;
			if (xOffset == -1 && yOffset == 1)
				return (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4fa40000) == 0 && (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4e240000) == 0 && (getClipedOnlyMask(plane, x, y + 2) & 0x7e240000) == 0;
			if (xOffset == 1 && yOffset == 1)
				return (getClipedOnlyMask(plane, x + 1, y + 2) & 0x7e240000) == 0 && (getClipedOnlyMask(plane, x + 2, y + 2) & 0x78240000) == 0 && (getClipedOnlyMask(plane, x + 1, y + 1) & 0x78e40000) == 0;
		} else {
			if (xOffset == -1 && yOffset == 0) {
				if ((getClipedOnlyMask(plane, x - 1, y) & 0x43a40000) != 0 || (getClipedOnlyMask(plane, x - 1, -1 + (y + size)) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 0) {
				if ((getClipedOnlyMask(plane, x + size, y) & 0x60e40000) != 0 || (getClipedOnlyMask(plane, x + size, y - (-size + 1)) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x, y - 1) & 0x43a40000) != 0 || (getClipedOnlyMask(plane, x + size - 1, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x, y + size) & 0x4e240000) != 0 || (getClipedOnlyMask(plane, x + (size - 1), y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x - 1, y - 1) & 0x43a40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + (-1 + sizeOffset)) & 0x4fa40000) != 0 || (getClipedOnlyMask(plane, sizeOffset - 1 + x, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x + size, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + size, sizeOffset + (-1 + y)) & 0x78e40000) != 0 || (getClipedOnlyMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x - 1, y + size) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0 || (getClipedOnlyMask(plane, -1 + (x + sizeOffset), y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x + size, y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0 || (getClipedOnlyMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			}
		}
		return true;
	}

	public static final boolean checkWalkStep(int plane, int x, int y, int dir, int size) {
		int xOffset = Utils.DIRECTION_DELTA_X[dir];
		int yOffset = Utils.DIRECTION_DELTA_Y[dir];
		int rotation = getRotation(plane, x + xOffset, y + yOffset);
		if (rotation != 0) {
			for (int rotate = 0; rotate < (4 - rotation); rotate++) {
				int fakeChunckX = xOffset;
				int fakeChunckY = yOffset;
				xOffset = fakeChunckY;
				yOffset = 0 - fakeChunckX;
			}
		}

		if (size == 1) {
			int mask = getMask(plane, x + Utils.DIRECTION_DELTA_X[dir], y + Utils.DIRECTION_DELTA_Y[dir]);
			if (xOffset == -1 && yOffset == 0)
				return (mask & 0x42240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (mask & 0x60240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (mask & 0x40a40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (mask & 0x48240000) == 0;
			if (xOffset == -1 && yOffset == -1) {
				return (mask & 0x43a40000) == 0 && (getMask(plane, x - 1, y) & 0x42240000) == 0 && (getMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == 1 && yOffset == -1) {
				return (mask & 0x60e40000) == 0 && (getMask(plane, x + 1, y) & 0x60240000) == 0 && (getMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == -1 && yOffset == 1) {
				return (mask & 0x4e240000) == 0 && (getMask(plane, x - 1, y) & 0x42240000) == 0 && (getMask(plane, x, y + 1) & 0x48240000) == 0;
			}
			if (xOffset == 1 && yOffset == 1) {
				return (mask & 0x78240000) == 0 && (getMask(plane, x + 1, y) & 0x60240000) == 0 && (getMask(plane, x, y + 1) & 0x48240000) == 0;
			}
		} else if (size == 2) {
			if (xOffset == -1 && yOffset == 0)
				return (getMask(plane, x - 1, y) & 0x43a40000) == 0 && (getMask(plane, x - 1, y + 1) & 0x4e240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (getMask(plane, x + 2, y) & 0x60e40000) == 0 && (getMask(plane, x + 2, y + 1) & 0x78240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (getMask(plane, x, y - 1) & 0x43a40000) == 0 && (getMask(plane, x + 1, y - 1) & 0x60e40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (getMask(plane, x, y + 2) & 0x4e240000) == 0 && (getMask(plane, x + 1, y + 2) & 0x78240000) == 0;
			if (xOffset == -1 && yOffset == -1)
				return (getMask(plane, x - 1, y) & 0x4fa40000) == 0 && (getMask(plane, x - 1, y - 1) & 0x43a40000) == 0 && (getMask(plane, x, y - 1) & 0x63e40000) == 0;
			if (xOffset == 1 && yOffset == -1)
				return (getMask(plane, x + 1, y - 1) & 0x63e40000) == 0 && (getMask(plane, x + 2, y - 1) & 0x60e40000) == 0 && (getMask(plane, x + 2, y) & 0x78e40000) == 0;
			if (xOffset == -1 && yOffset == 1)
				return (getMask(plane, x - 1, y + 1) & 0x4fa40000) == 0 && (getMask(plane, x - 1, y + 1) & 0x4e240000) == 0 && (getMask(plane, x, y + 2) & 0x7e240000) == 0;
			if (xOffset == 1 && yOffset == 1)
				return (getMask(plane, x + 1, y + 2) & 0x7e240000) == 0 && (getMask(plane, x + 2, y + 2) & 0x78240000) == 0 && (getMask(plane, x + 1, y + 1) & 0x78e40000) == 0;
		} else {
			if (xOffset == -1 && yOffset == 0) {
				if ((getMask(plane, x - 1, y) & 0x43a40000) != 0 || (getMask(plane, x - 1, -1 + (y + size)) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 0) {
				if ((getMask(plane, x + size, y) & 0x60e40000) != 0 || (getMask(plane, x + size, y - (-size + 1)) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == -1) {
				if ((getMask(plane, x, y - 1) & 0x43a40000) != 0 || (getMask(plane, x + size - 1, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == 1) {
				if ((getMask(plane, x, y + size) & 0x4e240000) != 0 || (getMask(plane, x + (size - 1), y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == -1) {
				if ((getMask(plane, x - 1, y - 1) & 0x43a40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x - 1, y + (-1 + sizeOffset)) & 0x4fa40000) != 0 || (getMask(plane, sizeOffset - 1 + x, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == -1) {
				if ((getMask(plane, x + size, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x + size, sizeOffset + (-1 + y)) & 0x78e40000) != 0 || (getMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == 1) {
				if ((getMask(plane, x - 1, y + size) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0 || (getMask(plane, -1 + (x + sizeOffset), y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 1) {
				if ((getMask(plane, x + size, y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0 || (getMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			}
		}
		return true;
	}

	public static final boolean containsPlayer(String username) {
		for (Player p2 : players) {
			if (p2 == null)
				continue;
			if (p2.getUsername().equals(username))
				return true;
		}
		return false;
	}

	public static Player getPlayer(String username) {
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.getUsername().equals(username))
				return player;
		}
		return null;
	}

	public static Player getPlayer(Player username) {
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.getUsername().equals(username))
				return player;
		}
		return null;
	}

	public static final Player getPlayerByDisplayName(String username) {
		String formatedUsername = Utils.formatPlayerNameForDisplay(username);
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.getUsername().equalsIgnoreCase(formatedUsername) || player.getDisplayName().equalsIgnoreCase(formatedUsername))
				return player;
		}
		return null;
	}

	public static final EntityList<Player> getPlayers() {
		return players;
	}

	public static final EntityList<NPC> getNPCs() {
		return npcs;
	}

	private World() {

	}

	public static final void safeShutdown(final boolean restart, int delay) {
		if (exiting_start != 0)
			return;
		exiting_start = Utils.currentTimeMillis();
		exiting_delay = delay;
		for (Player player : World.getPlayers()) {
			if (player == null || !player.hasStarted() || player.hasFinished())
				continue;
			player.getPackets().sendSystemUpdate(delay);
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					for (Player player : World.getPlayers()) {
						if (player == null || !player.hasStarted())
							continue;
						player.realFinish();

						player.getControlerManager().removeControlerWithoutCheck();

						IPBanL.save();
						PkRank.save();
						KillStreakRank.save();
						
					}
					GlobalBossCounter.Save();
					GlobalItemCounter.Save();
					GlobalCapeCounter.save();
					RecordHandler.save();
					// Offers.saveOffers();
					if (restart)
						Launcher.restart();
					else
						Launcher.shutdown();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, delay, TimeUnit.SECONDS);
	}

	/*
	 * by default doesnt changeClipData
	 */
	public static final void spawnTemporaryObject(final WorldObject object, long time) {
		spawnTemporaryObject(object, time, false);
	}

	public static final void spawnTemporaryDivineObject(final WorldObject object, long time, final Player player) {
		spawnTemporaryDivineObject(object, time, player, false);
	}

	public static final void spawnTemporaryDivineObject(final WorldObject object, long time, final Player player, final boolean clip) {
		spawnObject(object);
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					if (!World.isSpawnedObject(object))
						return;
					removeObject(object);
					player.divine = 0;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		}, time, TimeUnit.MILLISECONDS);
	}

	public static final void spawnTemporaryObject(final WorldObject object, long time, final boolean clip) {
		spawnObject(object);
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					if (!World.isSpawnedObject(object))
						return;
					removeObject(object);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		}, time, TimeUnit.MILLISECONDS);
	}

	public static final boolean isSpawnedObject(WorldObject object) {
		return getRegion(object.getRegionId()).getSpawnedObjects().contains(object);
	}

	public static final boolean removeTemporaryObject(final WorldObject object, long time, final boolean clip) {
		removeObject(object);
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawnObject(object);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		}, time, TimeUnit.MILLISECONDS);
		return true;
	}

	public static final void removeObject(WorldObject object, boolean clip) {
		getRegion(object.getRegionId()).removeObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion());
	}

	public static final WorldObject getObject(WorldTile tile) {
		return getRegion(tile.getRegionId()).getStandartObject(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion());
	}

	public static final WorldObject getObject(WorldTile tile, int type) {
		return getRegion(tile.getRegionId()).getObjectWithType(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), type);
	}

	public static final void spawnObject(WorldObject object, boolean clip) {
		getRegion(object.getRegionId()).spawnObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion(), false);
	}

	public static final void spawnObjectSpawns(WorldObject object, boolean clip) {
		getRegion(object.getRegionId()).spawnObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion(), true);
	}

	/*
	 * public static final void addGroundItem(final Item item, final WorldTile
	 * tile, final Player owner/* null for default , boolean invisible, long
	 * hiddenTime/* default 3 minutes ) { addGroundItem(item, tile, owner,
	 * invisible, hiddenTime, 2, 150); }
	 * 
	 * public static final FloorItem addGroundItem(final Item item, final
	 * WorldTile tile, final Player owner/* null for default , boolean
	 * invisible, long hiddenTime/* default 3 minutes , int type) { return
	 * addGroundItem(item, tile, owner, invisible, hiddenTime, type, 150); }
	 */

	/*
	 * public static final FloorItem addGroundItem(final Item item, final
	 * WorldTile tile, final Player owner, boolean invisible, long hiddenTime/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * , int type, final int publicTime) { if (type != 2) { if ((type == 0 &&
	 * !ItemConstants.isTradeable(item)) || type == 1 &&
	 * ItemConstants.isDestroy(item)) {
	 * 
	 * int price = item.getDefinitions().getValue(); if (price <= 0) return
	 * null; item.setId(995); item.setAmount(price); } } final FloorItem
	 * floorItem = new FloorItem(item, tile, owner, owner != null, invisible);
	 * final Region region = getRegion(tile.getRegionId());
	 * region.getGroundItemsSafe().add(floorItem); if (invisible) { if (owner !=
	 * null) owner.getPackets().sendGroundItem(floorItem); // becomes visible
	 * after x time if (hiddenTime != -1) {
	 * CoresManager.slowExecutor.schedule(new Runnable() {
	 * 
	 * @Override public void run() { try { turnPublic(floorItem, publicTime); }
	 * catch (Throwable e) { Logger.handle(e); } } }, hiddenTime,
	 * TimeUnit.SECONDS); } } else { // visible int regionId =
	 * tile.getRegionId(); for (Player player : players) { if (player == null ||
	 * !player.hasStarted() || player.hasFinished() || player.getPlane() !=
	 * tile.getPlane() || !player.getMapRegionsIds().contains(regionId))
	 * continue; player.getPackets().sendGroundItem(floorItem); } // disapears
	 * after this time if (publicTime != -1) removeGroundItem(floorItem,
	 * publicTime); } return floorItem; }
	 */

	/*
	 * public static final void turnPublic(FloorItem floorItem, int publicTime)
	 * { if (!floorItem.isInvisible()) return; int regionId =
	 * floorItem.getTile().getRegionId(); final Region region =
	 * getRegion(regionId); if
	 * (!region.getGroundItemsSafe().contains(floorItem)) return; //Player
	 * realOwner = floorItem.hasOwner() ? World.getPlayer(floorItem.getOwner())
	 * : null; if (!ItemConstants.isTradeable(floorItem)) {
	 * region.getGroundItemsSafe().remove(floorItem); if (realOwner != null) {
	 * if (realOwner.getMapRegionsIds().contains(regionId) &&
	 * realOwner.getPlane() == floorItem.getTile().getPlane())
	 * realOwner.getPackets().sendRemoveGroundItem(floorItem); } return; }
	 * floorItem.setInvisible(false); for (Player player : players) { // if
	 * (player == null || player == realOwner || !player.hasStarted() ||
	 * player.hasFinished() || player.getPlane() !=
	 * floorItem.getTile().getPlane() ||
	 * !player.getMapRegionsIds().contains(regionId)) //continue;
	 * player.getPackets().sendGroundItem(floorItem); } // disapears after this
	 * time if (publicTime != -1) removeGroundItem(floorItem, publicTime); }
	 */

	public static final void addGroundItem(final Item item, final WorldTile tile) {
		final FloorItem floorItem = new FloorItem(item, tile, null, false, false);
		final Region region = getRegion(tile.getRegionId());
		region.forceGetFloorItems().add(floorItem);
		int regionId = tile.getRegionId();
		for (Player player : players) {
			if (player == null || !player.hasStarted() || player.hasFinished() || player.getPlane() != tile.getPlane() || !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendGroundItem(floorItem);
		}
	}

	public static final void addGroundItem(final Item item, final WorldTile tile, final Player owner, final boolean underGrave, long hiddenTime, boolean invisible) {
		addGroundItem(item, tile, owner, underGrave, hiddenTime, invisible, false, 180);
	}

	public static final void addGroundItem(final Item item, final WorldTile tile, final Player owner, final boolean underGrave, long hiddenTime, boolean invisible, boolean intoGold) {
		addGroundItem(item, tile, owner, underGrave, hiddenTime, invisible, intoGold, 180);
	}

	public static final void addGroundItem(final Item item, final WorldTile tile,
			final Player owner/* null for default */, final boolean underGrave,
			long hiddenTime/* default 3minutes */, boolean invisible, boolean intoGold, final int publicTime) {
		final FloorItem floorItem = new FloorItem(item, tile, owner, owner == null ? false : underGrave, invisible);
		final Region region = getRegion(tile.getRegionId());
		region.forceGetFloorItems().add(floorItem);
		if (invisible && hiddenTime != -1) {
			if (owner != null)
				owner.getPackets().sendGroundItem(floorItem);
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						if (!region.forceGetFloorItems().contains(floorItem))
							return;
						int regionId = tile.getRegionId();
						if (underGrave || !ItemConstants.isTradeable(floorItem) || item.getName().contains("nosz")) {
							region.forceGetFloorItems().remove(floorItem);
							if (owner != null) {
								if (owner.getMapRegionsIds().contains(regionId) && owner.getPlane() == tile.getPlane())
									owner.getPackets().sendRemoveGroundItem(floorItem);
							}
							return;
						}

						floorItem.setInvisible(false);
						for (Player player : players) {
							if (player == null || player == owner || !player.hasStarted() || player.hasFinished() || player.getPlane() != tile.getPlane() || !player.getMapRegionsIds().contains(regionId))
								continue;
							player.getPackets().sendGroundItem(floorItem);
						}
						removeGroundItem(floorItem, publicTime);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, hiddenTime, TimeUnit.SECONDS);
			return;
		}
		int regionId = tile.getRegionId();
		for (Player player : players) {
			if (player == null || !player.hasStarted() || player.hasFinished() || player.getPlane() != tile.getPlane() || !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendGroundItem(floorItem);
		}
		removeGroundItem(floorItem, publicTime);
	}

	@Deprecated
	public static final void addGroundItemForever(Item item, final WorldTile tile) {
		int regionId = tile.getRegionId();
		final FloorItem floorItem = new FloorItem(item, tile, true);
		final Region region = getRegion(tile.getRegionId());
		region.getGroundItemsSafe().add(floorItem);
		for (Player player : players) {
			if (player == null || !player.hasStarted() || player.hasFinished() || player.getPlane() != floorItem.getTile().getPlane() || !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendGroundItem(floorItem);
		}
	}

	public static final void updateGroundItem(Item item, final WorldTile tile, final Player owner) {
		final FloorItem floorItem = World.getRegion(tile.getRegionId()).getGroundItem(item.getId(), tile, owner);
		if (floorItem == null) {
			addGroundItem(item, tile, owner, true, 360);
			return;
		}
		floorItem.setAmount(floorItem.getAmount() + item.getAmount());
		owner.getPackets().sendRemoveGroundItem(floorItem);
		owner.getPackets().sendGroundItem(floorItem);

	}

	private static final void removeGroundItem(final FloorItem floorItem, long publicTime) {
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					int regionId = floorItem.getTile().getRegionId();
					Region region = getRegion(regionId);
					if (!region.getGroundItemsSafe().contains(floorItem))
						return;
					region.getGroundItemsSafe().remove(floorItem);
					for (Player player : World.getPlayers()) {
						if (player == null || !player.hasStarted() || player.hasFinished() || player.getPlane() != floorItem.getTile().getPlane() || !player.getMapRegionsIds().contains(regionId))
							continue;
						player.getPackets().sendRemoveGroundItem(floorItem);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, publicTime, TimeUnit.SECONDS);
	}

	public static final boolean removeGroundItem(Player player, FloorItem floorItem) {
		return removeGroundItem(player, floorItem, true);
	}

	public static final boolean removeGroundItem(Player player, final FloorItem floorItem, boolean add) {
		int regionId = floorItem.getTile().getRegionId();
		Region region = getRegion(regionId);
		if (!region.getGroundItemsSafe().contains(floorItem))
			return false;
		if (player.getInventory().getFreeSlots() == 0 && (!floorItem.getDefinitions().isStackable() || !player.getInventory().containsItem(floorItem.getId(), 1))) {
			player.getPackets().sendGameMessage("Not enough space in your inventory.");
			return false;
		}
		region.getGroundItemsSafe().remove(floorItem);
		if (add)
			player.getInventory().addItemMoneyPouch(new Item(floorItem.getId(), floorItem.getAmount()));
		if (floorItem.isInvisible()) {
			player.getPackets().sendRemoveGroundItem(floorItem);
			return true;
		} else {
			for (Player p2 : World.getPlayers()) {
				if (p2 == null || !p2.hasStarted() || p2.hasFinished() || p2.getPlane() != floorItem.getTile().getPlane() || !p2.getMapRegionsIds().contains(regionId))
					continue;
				p2.getPackets().sendRemoveGroundItem(floorItem);
			}
			if (floorItem.isForever()) {
				CoresManager.slowExecutor.schedule(new Runnable() {
					@Override
					public void run() {
						try {
							addGroundItemForever(floorItem, floorItem.getTile());
						} catch (Throwable e) {
							Logger.handle(e);
						}
					}
				}, 60, TimeUnit.SECONDS);
			}
			return true;
		}
	}

	public static final void sendObjectAnimation(WorldObject object, Animation animation) {
		sendObjectAnimation(null, object, animation);
	}

	public static final void sendObjectAnimation(Entity creator, WorldObject object, Animation animation) {
		if (creator == null) {
			for (Player player : World.getPlayers()) {
				if (player == null || !player.hasStarted() || player.hasFinished() || !player.withinDistance(object))
					continue;
				player.getPackets().sendObjectAnimation(object, animation);
			}
		} else {
			for (int regionId : creator.getMapRegionsIds()) {
				List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
				if (playersIndexes == null)
					continue;
				for (Integer playerIndex : playersIndexes) {
					Player player = players.get(playerIndex);
					if (player == null || !player.hasStarted() || player.hasFinished() || !player.withinDistance(object))
						continue;
					player.getPackets().sendObjectAnimation(object, animation);
				}
			}
		}
	}

	public static final void sendGraphics(Entity creator, Graphics graphics, WorldTile tile) {
		if (creator == null) {
			for (Player player : World.getPlayers()) {
				if (player == null || !player.hasStarted() || player.hasFinished() || !player.withinDistance(tile))
					continue;
				player.getPackets().sendGraphics(graphics, tile);
			}
		} else {
			for (int regionId : creator.getMapRegionsIds()) {
				List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
				if (playersIndexes == null)
					continue;
				for (Integer playerIndex : playersIndexes) {
					Player player = players.get(playerIndex);
					if (player == null || !player.hasStarted() || player.hasFinished() || !player.withinDistance(tile))
						continue;
					player.getPackets().sendGraphics(graphics, tile);
				}
			}
		}
	}
	
	public static final void sendGraphicsWider(Entity creator, Graphics graphics, WorldTile tile) {
		if (creator == null) {
			for (Player player : World.getPlayers()) {
				if (player == null || !player.hasStarted() || player.hasFinished() || !player.withinWiderDistance(tile))
					continue;
				player.getPackets().sendGraphics(graphics, tile);
			}
		} else {
			for (int regionId : creator.getMapRegionsIds()) {
				List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
				if (playersIndexes == null)
					continue;
				for (Integer playerIndex : playersIndexes) {
					Player player = players.get(playerIndex);
					if (player == null || !player.hasStarted() || player.hasFinished() || !player.withinWiderDistance(tile))
						continue;
					player.getPackets().sendGraphics(graphics, tile);
				}
			}
		}
	}


	public static final void sendProjectile(Entity shooter, WorldTile startTile, WorldTile receiver, int gfxId, int startHeight, int endHeight, int speed, int delay, int curve, int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, startTile, receiver, gfxId, startHeight, endHeight, speed, delay, curve, startDistanceOffset, 1);
			}
		}
	}

	public static final void sendProjectile(WorldTile shooter, Entity receiver, int gfxId, int startHeight, int endHeight, int speed, int delay, int curve, int startDistanceOffset) {
		for (int regionId : receiver.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, shooter, receiver, gfxId, startHeight, endHeight, speed, delay, curve, startDistanceOffset, 1);
			}
		}
	}

	public static final void sendProjectile(Entity shooter, WorldTile receiver, int gfxId, int startHeight, int endHeight, int speed, int delay, int curve, int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, shooter, receiver, gfxId, startHeight, endHeight, speed, delay, curve, startDistanceOffset, shooter.getSize());
			}
		}
	}

	public static final void sendProjectile(Entity shooter, Entity receiver, int gfxId, int startHeight, int endHeight, int speed, int delay, int curve, int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, startHeight, endHeight, speed, delay, curve, startDistanceOffset, size);
			}
		}
	}

	public static final boolean isMultiArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		int regionId = tile.getRegionId();
		return (destX >= 3462 && destX <= 3511 && destY >= 9481 && destY <= 9521 && tile.getPlane() == 0) // kalphite
																											// queen
																											// lair
				|| (destX >= 4540 && destX <= 4799 && destY >= 5052 && destY <= 5183 && tile.getPlane() == 0) // thzaar
																												// city
				|| (destX >= 1721 && destX <= 1791 && destY >= 5123 && destY <= 5249) // mole
				|| (destX >= 3029 && destX <= 3374 && destY >= 3759 && destY <= 3903)// wild
				|| (destX >= 2250 && destX <= 2280 && destY >= 4670 && destY <= 4720) 
				|| (destX >= 1363 && destX <= 1385 && destY >= 6613 && destY <= 6635)// Blink
				|| (destX >= 2517 && destX <= 2538 && destY >= 5227 && destY <= 5243)// Yk
				|| (destX >= 3198 && destX <= 3380 && destY >= 3904 && destY <= 3970) 
				|| (destX >= 3530 && destX <= 3575 && destY >= 9480 && destY <= 9520)// vorago
				|| (destX >= 3191 && destX <= 3326 && destY >= 3510 && destY <= 3759) 
				|| (destX >= 2987 && destX <= 3006 && destY >= 3912 && destY <= 3937) 
				|| (destX >= 2894 && destX <= 2948 && destY >= 4423 && destY <= 4479) 
				|| (destX >= 2245 && destX <= 2295 && destY >= 4675 && destY <= 4720) 
				//|| (destX >= 2450 && destX <= 3520 && destY >= 9450 && destY <= 9550) //this one
				|| (destX >= 3006 && destX <= 3071 && destY >= 3602 && destY <= 3710) 
				|| (destX >= 3134 && destX <= 3192 && destY >= 3519 && destY <= 3646) 
				|| (destX >= 2815 && destX <= 2966 && destY >= 5240 && destY <= 5375)// wild
				|| (destX >= 2840 && destX <= 2950 && destY >= 5190 && destY <= 5230) // godwars
				|| (destX >= 3547 && destX <= 3555 && destY >= 9690 && destY <= 9699) // zaros
				|| (destX >= 3239 && destX <= 3256 && destY >= 9353 && destY <= 9376) // world
																						// gorg
				// godwars
				|| KingBlackDragon.atKBD(tile) // King Black Dragon lair
				|| TormentedDemon.atTD(tile) // Tormented demon's area
				|| Bork.atBork(tile) // Bork's area
				|| (destX >= 2970 && destX <= 3000 && destY >= 4365 && destY <= 4400)// corp
				|| (destX >= 3195 && destX <= 3327 && destY >= 3520 && destY <= 3970 
				|| (destX >= 2376 && 5127 >= destY && destX <= 2422 && 5168 <= destY)) 
				|| (destX >= 2374 && destY >= 5129 && destX <= 2424 && destY <= 5168) // pits
				|| (destX >= 3653 && destY >= 3023 && destX <= 3678 && destY <= 3062) // boss
																						// raids
				|| (destX >= 3460 && destY >= 3724 && destX <= 3472 && destY <= 3738) // Skeleten
																						// trio
				|| (destX >= 3460 && destY >= 3725 && destX <= 3471 && destY <= 3737) // Necrolord
				|| (destX >= 2959 && destY >= 1744 && destX <= 2992 && destY <= 1777) // kalphite king
				|| (destX >= 2622 && destY >= 5696 && destX <= 2573 && destY <= 5752) // torms
				|| (destX >= 2368 && destY >= 3072 && destX <= 2431 && destY <= 3135) // castlewars
				|| (destX >= 3780 && destY >= 3542 && destX <= 3834 && destY <= 3578) // Sunfreet
				// out
				|| (destX >= 2365 && destY >= 9470 && destX <= 2436 && destY <= 9532) // castlewars
				|| (destX >= 2948 && destY >= 5537 && destX <= 3071 && destY <= 5631) // Risk
				// ffa.
				|| (destX >= 2756 && destY >= 5537 && destX <= 2879 && destY <= 5631) // Safe
																						// ffa
				|| (destX >= 1490 && destX <= 1515 && destY >= 4696 && destY <= 4714) // chaos
																						// dwarf
																						// battlefield
				|| (destX >= 3333 && destX <= 3383 && destY >= 9345 && destY <= 9450) // Smokey
																						// well
																						// 1
				|| (destX >= 3072 && destX <= 3136 && destY >= 4287 && destY <= 4416) // Smokey
																						// well
																						// 2
				|| (destX >= 3140 && destX <= 3331 && destY >= 5441 && destY <= 5568) // CHAOS
																						// TUNNELS
				|| (destX >= 1986 && destX <= 2045 && destY >= 4162 && destY <= 4286) || regionId == 16729 // glacors
				|| (destX >= 3261 && destX <= 3329 && destY >= 4287 && destY <= 4416) // smokey
																						// well
																						// 3
				|| (destX >= 1610 && destX <= 1636 && destY >= 4561 && destY <= 4585) // Boss
																						// Raids
				|| (destX >= 2690 && destX <= 2814 && destY >= 9092 && destY <= 9148) // monkeys
				|| (destX >= 2445 && destX <= 2551 && destY >= 10117 && destY <= 10167) // dags
				|| (destX >= 2445 && destX <= 2551 && destY >= 10117 && destY <= 10167) // sea
				|| (tile.getX() >= 3011 && tile.getX() <= 3132 && tile.getY() >= 10052 && tile.getY() <= 10175 && (tile.getY() >= 10066 || tile.getX() >= 3094)) // fortihrny
																																									// dungeon

		;
		// in

		// multi
	}

	// Dreadnaut Minigame - items are safe
	public static boolean isMiniGame(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return (destX >= 3154 && destX <= 3185 && destY >= 9755 && destY <= 9775); // DreadnautRoom
	}

	public static boolean isRestrictedCannon(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return ((destX >= 2250 && destX <= 2302 && destY >= 4673 && destY <= 4725) // King
																					// Black
																					// Dragon
				|| (destX >= 3082 && destX <= 3122 && destY >= 5512 && destY <= 5560) // Bork
				|| (destX >= 3462 && destX <= 3512 && destY >= 9473 && destY <= 9525) // Kalphite
																						// Queen
				|| (destX >= 2894 && destX <= 2948 && destY >= 4423 && destY <= 4479) // Dagganoth
																						// Kings
				|| (destX >= 2574 && destX <= 2634 && destY >= 5692 && destY <= 5759) // Tormented
																						// Demons
				|| (destX >= 2969 && destX <= 3005 && destY >= 4357 && destY <= 4405) // Corporeal
																						// Beast
				|| (destX >= 2898 && destX <= 2946 && destY >= 5181 && destY <= 5226) // Nex
				|| (destX >= 2822 && destX <= 2936 && destY >= 5238 && destY <= 5379) // God
																						// Wars
				|| (destX >= 2943 && destX <= 3561 && destY >= 3521 && destY <= 4052) // Wilderness
				|| (destX >= 2518 && destX <= 2543 && destY >= 5226 && destY <= 5241) // Yklagor
				|| (destX >= 2844 && destX <= 2868 && destY >= 9625 && destY <= 9650) // Sunfreet
				|| (destX >= 1365 && destX <= 1387 && destY >= 6613 && destY <= 6636) // Blink
				|| (destX >= 3154 && destX <= 3185 && destY >= 9755 && destY <= 9775)) // DreadnautRoom
		;
	}

	public static final boolean isMorytaniaArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return ((destX >= 3423 && destX <= 3463 && destY >= 3205 && destY <= 3268) // haunted
																					// mine
				|| (destX >= 3464 && destX <= 3722 && destY >= 3160 && destY <= 3602) // Rest
																						// of
																						// mory
				|| (destX >= 3413 && destX <= 3463 && destY >= 3206 && destY <= 3346) // Snail
																						// maze
				|| (destX >= 3399 && destX <= 3463 && destY >= 3347 && destY <= 3450) // Part
																						// of
																						// swamp
				|| (destX >= 3413 && destX <= 3463 && destY >= 3451 && destY <= 3467) // Part
																						// of
																						// swamp
				|| (destX >= 3420 && destX <= 3463 && destY >= 3468 && destY <= 3607) // Part
																						// of
																						// swamp
				|| (destX >= 3398 && destX <= 3463 && destY >= 3508 && destY <= 3607) // Part
																						// of
																						// upper
																						// part
				|| (destX >= 3409 && destX <= 3419 && destY >= 3499 && destY <= 3507) // Part
																						// of
																						// upper
																						// part
				|| (destX >= 3397 && destX <= 3660 && destY >= 9607 && destY <= 9852) // morytania
																						// underground
				|| (destX >= 3466 && destX <= 3588 && destY >= 9857 && destY <= 9978) // werewolf
																						// agil,
																						// experiments
				|| (destX >= 3643 && destX <= 3728 && destY >= 9847 && destY <= 9935) // Ectofun
				|| (destX >= 2254 && destX <= 2287 && destY >= 5142 && destY <= 5162) // Drakan
																						// tomb
				|| (destX >= 2686 && destX <= 2818 && destY >= 4416 && destY <= 4606) // haunted
																						// mine
																						// floors
				|| (destX >= 3106 && destX <= 3218 && destY >= 4540 && destY <= 4680)) // Tarn's
																						// lair
		;
	}

	public static final boolean isSmokeyArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return ((destX >= 3199 && destX <= 3330 && destY >= 9341 && destY <= 9406) // Smoke
																					// dungeon
				|| (destX >= 3333 && destX <= 3383 && destY >= 9345 && destY <= 9450) // Smokey
																						// well
																						// 1
				|| (destX >= 3072 && destX <= 3136 && destY >= 4287 && destY <= 4416) // Smokey
																						// well
																						// 2
				|| (destX >= 3261 && destX <= 3329 && destY >= 4287 && destY <= 4416)) // Smokey
																						// well
																						// 3
		;
	}
	public static final boolean isDesertArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return ((destX <= 3294 && destX >= 3133 && destY <= 3132 && destY >= 2614) || (destX <= 3566 && destX >= 3295 && destY <= 3115 && destY >= 2585) || (destX <= 3512 && destX >= 3315 && destY <= 3132 && destY >= 3110) || (destX <= 3355 && destX >= 3327 && destY <= 3156 && destY >= 3131));
	}

	public static final boolean isSinkArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return ((destX <= 1613 && destX >= 1534 && destY <= 4425 && destY >= 4346));
	}

	public static final boolean isPvpArea(WorldTile tile) {
		return (Wilderness.isAtWild(tile));
	}

	public static void destroySpawnedObject(WorldObject object, boolean clip) {
		getRegion(object.getRegionId()).removeObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion());
	}

	public static void destroySpawnedObject(WorldObject object) {
		getRegion(object.getRegionId()).removeObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion());
	}

	public static final void spawnTempGroundObject(final WorldObject object, final int replaceId, long time) {
		spawnObject(object);
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					removeObject(object);
					addGroundItem(new Item(replaceId), object, null, false, 180);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, time, TimeUnit.MILLISECONDS);
	}

	public static void sendWorldMessage(String message, boolean forStaff) {
		for (Player p : World.getPlayers()) {
			if (p == null || !p.isRunning() || (forStaff && p.getRights() == 0))
				continue;
			p.getPackets().sendGameMessage(message);
		}
	}
	
	public static void sendWorldRiddle(String message, boolean forStaff) {
		for (Player p : World.getPlayers()) {
			if (p == null || !p.isRunning() || (forStaff && p.getRights() == 0))
				continue;
			if(!p.riddleMessages)
			p.getPackets().sendGameMessage(message);
		}
	}
	
	public static void sendWorldMessageLevelUp(String message, boolean forStaff) {
		for (Player p : World.getPlayers()) {
			if (p == null || !p.isRunning() || (forStaff && p.getRights() == 0) )
				continue;
			if(p.inform99s)
			p.getPackets().sendGameMessage(message);
		}
	}

	public static void sendWorldMessageAll(String message) {
		for (Player p : World.getPlayers()) {
			if (p == null)
				continue;
			p.getPackets().sendGameMessage(message);
		}
	}

	public static final void sendProjectile(WorldObject object, WorldTile startTile, WorldTile endTile, int gfxId, int startHeight, int endHeight, int speed, int delay, int curve, int startOffset) {
		for (Player pl : players) {
			if (pl == null || !pl.withinDistance(object, 20))
				continue;
			pl.getPackets().sendProjectile(null, startTile, endTile, gfxId, startHeight, endHeight, speed, delay, curve, startOffset, 1);
		}
	}

	public static void deleteObject(int i, int j, boolean b) {
		// TODO Auto-generated method stub

	}

	public static void spawnObject(int i, WorldTile worldTile, int j, boolean b) {
		// TODO Auto-generated method stub

	}

	public static final void turnPublic(FloorItem floorItem, int publicTime) {
		if (!floorItem.isInvisible())
			return;
		int regionId = floorItem.getTile().getRegionId();
		final Region region = getRegion(regionId);
		if (!region.getGroundItemsSafe().contains(floorItem))
			return;
		Player realOwner = floorItem.hasOwner() ? World.getPlayer(floorItem.getOwner()) : null;
		if (!ItemConstants.isTradeable(floorItem)) {
			region.getGroundItemsSafe().remove(floorItem);
			if (realOwner != null) {
				if (realOwner.getMapRegionsIds().contains(regionId) && realOwner.getPlane() == floorItem.getTile().getPlane())
					realOwner.getPackets().sendRemoveGroundItem(floorItem);
			}
			return;
		}
		floorItem.setInvisible(false);
		for (Player player : players) {
			if (player == null || player == realOwner || !player.hasStarted() || player.hasFinished() || player.getPlane() != floorItem.getTile().getPlane() || !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendGroundItem(floorItem);
		}
		// disapears after this time
		if (publicTime != -1)
			removeGroundItem(floorItem, publicTime);
	}

	public static final void addGroundItem(final Item item, final WorldTile tile, final Player owner, boolean invisible, long hiddenTime) {
		addGroundItem(item, tile, owner, invisible, hiddenTime, 2, 150);
	}

	public static final FloorItem addGroundItem(final Item item, final WorldTile tile,
			final Player owner/*
								 * null for default
								 */, boolean invisible,
			long hiddenTime/*
							 * default 3 minutes
							 */, int type) {
		return addGroundItem(item, tile, owner, invisible, hiddenTime, type, 150);
	}

	/*
	 * type 0 - gold if not tradeable type 1 - gold if destroyable type 2 - no
	 * gold
	 */
	public static final FloorItem addGroundItem(final Item item, final WorldTile tile, final Player owner, boolean invisible, long hiddenTime, int type, final int publicTime) {
		final FloorItem floorItem = new FloorItem(item, tile, owner, false, invisible);
		final Region region = getRegion(tile.getRegionId());
		if (type == 1) {
			if (ItemConstants.isTradeable(item) || ItemConstants.turnCoins(item))
				region.getGroundItemsSafe().add(floorItem);
			if (invisible) {
				if (owner != null) {
					if (ItemConstants.isTradeable(item) || ItemConstants.turnCoins(item))
						owner.getPackets().sendGroundItem(floorItem);
				}
				if (hiddenTime != -1) {
					CoresManager.slowExecutor.schedule(new Runnable() {
						@Override
						public void run() {
							try {
								turnPublic(floorItem, publicTime);
							} catch (Throwable e) {
								Logger.handle(e);
							}
						}
					}, hiddenTime, TimeUnit.SECONDS);
				}
			} else {
				int regionId = tile.getRegionId();
				for (Player player : players) {
					if (player == null || player.hasFinished() || player.getPlane() != tile.getPlane() || !player.getMapRegionsIds().contains(regionId))
						continue;
					player.getPackets().sendGroundItem(floorItem);
				}
				if (publicTime != -1)
					removeGroundItem(floorItem, publicTime);
			}
		} else {
			region.getGroundItemsSafe().add(floorItem);
			if (invisible) {
				if (owner != null) {
					owner.getPackets().sendGroundItem(floorItem);
				}
				if (hiddenTime != -1) {
					CoresManager.slowExecutor.schedule(new Runnable() {
						@Override
						public void run() {
							try {
								turnPublic(floorItem, publicTime);
							} catch (Throwable e) {
								Logger.handle(e);
							}
						}
					}, hiddenTime, TimeUnit.SECONDS);
				}
			} else {
				int regionId = tile.getRegionId();
				for (Player player : players) {
					if (player == null || player.hasFinished() || player.getPlane() != tile.getPlane() || !player.getMapRegionsIds().contains(regionId) || !ItemConstants.isTradeable(item))
						continue;
					player.getPackets().sendGroundItem(floorItem);
				}
				if (publicTime != -1)
					removeGroundItem(floorItem, publicTime);
			}
		}
		return floorItem;
	}

	public static final void spawnObject(WorldObject object) {
		getRegion(object.getRegionId()).spawnObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion(), false);
	}

	public static final void removeObject(WorldObject object) {
		getRegion(object.getRegionId()).removeObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion());
	}

	public static final WorldObject getObjectWithSlot(WorldTile tile, int slot) {
		return getRegion(tile.getRegionId()).getObjectWithSlot(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), slot);
	}

	/**
	 * lottery
	 **/
	public static int lotteryAmount;

	public static void lotterynews() {
		if (lotteryAmount <= 2500000)
			sendWorldMessage("[<col=ff0000>LOTTERY</col>] The Pot is now <col=66cd00><shad=000000>" + NumberFormat.getNumberInstance(Locale.US).format(lotteryAmount) + "!</col></shad> Join The Lottery now and become the Lucky Winner!", false);
		else if (lotteryAmount <= 5000000)
			sendWorldMessage("[<col=ff0000>LOTTERY</col>] The Pot is now <col=fff000><shad=000000>" + NumberFormat.getNumberInstance(Locale.US).format(lotteryAmount) + "!</col></shad> Join The Lottery now and become the Lucky Winner!", false);
		else if (lotteryAmount <= 7500000)
			sendWorldMessage("[<col=ff0000>LOTTERY</col>] The Pot is now <col=f9882b><shad=000000>" + NumberFormat.getNumberInstance(Locale.US).format(lotteryAmount) + "!</col></shad> Join The Lottery now and become the Lucky Winner!", false);
		else if (lotteryAmount <= 10000000)
			sendWorldMessage("[<col=ff0000>LOTTERY</col>] The Pot is now <col=ff0000><shad=000000>" + NumberFormat.getNumberInstance(Locale.US).format(lotteryAmount) + "!</col></shad> Join The Lottery now and become the Lucky Winner!", false);
		return;
	}

	public static void sendLottery() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				lotterynews();
			}
		}, 4, 6, TimeUnit.MINUTES);
	}

	@SuppressWarnings({ "resource", "unused" })
	public static void loadLottery() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("./data/lottery/data.txt"));
		String line = null;
		int count = 0;
		while ((line = reader.readLine()) != null) {
			String[] args = line.split(" ");
			if (count == 0) {
				setLotteryAmount(Integer.parseInt(line));
			} else {
				Lottery.playerList.add(line);
			}
			count++;
		}
	}

	public static int getLotteryAmount() {
		return lotteryAmount;
	}

	public static void setLotteryAmount(int lotteryAmount) {
		World.lotteryAmount = lotteryAmount;
	}

	static Player player;

	public static void addLotteryAmount(String displayName, int amount) {
		lotteryAmount += amount;
		Player plr = getPlayerByDisplayName(displayName);
		plr.getPackets().sendGameMessage("You have entered into the Lottery! The Pot is now <col=ff0000>" + NumberFormat.getNumberInstance(Locale.US).format(World.getLotteryAmount()) + "</col> GP!");
	}

	/**
	 * Double exp bought by players.
	 **/
	private static long boughtExp;

	public static long getboughtExp() {
		return boughtExp;
	}

	public static void setboughtExp(long boughtExp) {
		World.boughtExp = boughtExp;
	}

	// for the player who bought it
	public static String EventPlayer = "";

	public static String getEventPlayer() {
		return EventPlayer;
	}

	public void setEventPlayer(String EventPlayer) {
		World.EventPlayer = EventPlayer;
	}

	public static final WorldObject getStandardObject(WorldTile tile) {
		return getRegion(tile.getRegionId()).getStandartObject(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion());
	}

	public static boolean isTileFree(int plane, int x, int y, int size) {
		for (int tileX = x; tileX < x + size; tileX++)
			for (int tileY = y; tileY < y + size; tileY++)
				if (!isFloorFree(plane, tileX, tileY) || !isWallsFree(plane, tileX, tileY))
					return false;
		return true;
	}

	public static boolean isFloorFree(int plane, int x, int y, int size) {
		for (int tileX = x; tileX < x + size; tileX++)
			for (int tileY = y; tileY < y + size; tileY++)
				if (!isFloorFree(plane, tileX, tileY))
					return false;
		return true;

	}

	public static boolean isFloorFree(int plane, int x, int y) {
		return (getMask(plane, x, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ)) == 0;
	}
	/*
	 * 
	 * public static boolean isFloorFree(int plane, int x, int y) { return
	 * (getMask(plane, x, y) & (Flags.FLOOR_BLOCKSWALK |
	 * Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ)) == 0; }
	 */

	public static boolean isWallsFree(int plane, int x, int y) {
		return (getMask(plane, x, y) & (Flags.CORNEROBJ_NORTHEAST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_SOUTHEAST | Flags.CORNEROBJ_SOUTHWEST | Flags.WALLOBJ_EAST | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST)) == 0;
	}

	public static void sendElementalProjectile(Player player2, Entity target, int i) {
		// TODO Auto-generated method stub

	}

	public static final void sendElementalProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, shooter, receiver, gfxId, 43, 34, distance < 2 ? 51 : distance == 2 ? 56 : 61, 51, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendCBOWProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getX(), shooter.getY(), shooter.getPlane()), receiver, gfxId, 43, 34, distance > 2 ? 61 : 51, 41, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendCBOWSwiftProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43 - 5, 34 - 5, distance > 2 ? 61 : 51, 41, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendFastBowProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, shooter, receiver, gfxId, 43, 34, distance < 2 ? 51 : distance == 2 ? 56 : 61, 41, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendLeechProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 35, 35, distance < 2 ? 21 : 26, 5, 0, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendSlowBowSwiftProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43 - 5, 34 - 5, distance > 2 ? 51 : distance == 2 ? 56 : 61, 21, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendFastBowSwiftProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43 - 5, 34 - 5, distance < 2 ? 51 : distance == 2 ? 56 : 61, 41, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendThrowSwiftProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43 - 5, 34 - 5, 42, 32, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendCannonProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 18, 18, distance < 2 ? 51 : distance == 2 ? 56 : 61, 41, 0, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendThrowProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43, 34, 42, 32, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendProjectile(Entity shooter, Entity receiver, int gfxId, int startHeight, int endHeight, int speed, int delay, int curve) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, startHeight, endHeight, speed, delay, curve, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendSOAProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 0, 0, distance < 4 ? 51 : 61, 51, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendSlowBowProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43, 34, distance < 2 ? 41 : distance == 2 ? 46 : 51, 41, 16, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendSlowBow2Projectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43, 34, distance < 2 ? 41 : distance == 2 ? 46 : 51, 61, 16, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendSoulsplitProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 0, 0, distance < 4 ? 26 : 31, 41, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendMSBProjectile(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43, 34, distance < 2 ? 51 : distance == 2 ? 56 : 61, 31, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void sendMSBProjectile2(Entity shooter, Entity receiver, int gfxId) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null || !player.hasStarted() || player.hasFinished() || (!player.withinDistance(shooter) && !player.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				int distance = Utils.getDistance(shooter, receiver);
				int startOffsetDistance = distance > 2 ? 0 : 11;
				int startOffsetDistance2 = 0;
				player.getPackets().sendProjectile(receiver, new WorldTile(shooter.getCoordFaceX(size), shooter.getCoordFaceY(size), shooter.getPlane()), receiver, gfxId, 43, 34, distance < 2 ? 51 : distance == 2 ? 56 : 61, 56, 6, receiver instanceof Player ? startOffsetDistance : startOffsetDistance, size);
			}
		}
	}

	public static final void updateGroundItem(Item item, final WorldTile tile, final Player owner, final int time, final int type) {
		final FloorItem floorItem = World.getRegion(tile.getRegionId()).getGroundItem(item.getId(), tile, owner);
		if (floorItem == null) {
			if (item.getAmount() != 1 && !item.getDefinitions().isStackable() && !item.getDefinitions().isNoted()) {
				for (int i = 0; i < item.getAmount(); i++) {
					addGroundItem(new Item(item.getId(), 1), tile, owner, true, time, type);
				}
				return;
			} else {
				addGroundItem(item, tile, owner, true, time, type);
				return;
			}
		}
		if (floorItem.getDefinitions().isStackable() || floorItem.getDefinitions().isNoted()) {
			if (floorItem.getAmount() + item.getAmount() < 0 || floorItem.getAmount() + item.getAmount() > Integer.MAX_VALUE) {
				int totalAmount = Integer.MAX_VALUE - floorItem.getAmount();
				floorItem.setAmount(Integer.MAX_VALUE);
				item.setAmount(item.getAmount() - totalAmount);
				addGroundItem(item, tile, owner, true, time, type);
				owner.getPackets().sendRemoveGroundItem(floorItem);
				owner.getPackets().sendGroundItem(floorItem);
			} else
				floorItem.setAmount(floorItem.getAmount() + item.getAmount());
			owner.getPackets().sendRemoveGroundItem(floorItem);
			owner.getPackets().sendGroundItem(floorItem);
		} else {
			if (item.getAmount() != 1) {
				for (int i = 0; i < item.getAmount(); i++) {
					addGroundItem(new Item(item.getId(), 1), tile, owner, true, time, type);
				}
				return;
			} else {
				addGroundItem(item, tile, owner, true, time, type);
				return;
			}
		}
	}

	public static final void addGroundItem(final Item item, final WorldTile tile, int publicTime) {
		addGroundItem(item, tile, null, false, -1, 2, publicTime);
	}

	public static List<Player> getLocalPlayers(WorldTile location) {
		List<Player> localPlayers = new ArrayList<Player>();
		for (Player n : getPlayers())
			if (n != null)
				if (!localPlayers.contains(n))
					if (n.withinDistance(location, 14))
						localPlayers.add(n);
		return localPlayers;
	}

	/**
	 * 
	 * @param player
	 * @return
	 */
	public static boolean isAtKuradalsDungeon(Player player) {
		int destX = player.getX(), destY = player.getY();
		return /* South West */(destX >= 1591 && destY >= 5241 &&
		/* North East */destX <= 1672 && destY <= 5337);
	}

	/**
	 * send a lootbeam at the coords where the npc diedededed
	 * 
	 * @param player
	 * @param tile
	 */
	public static final void sendLootbeam(Player player, WorldTile tile) {
		if (player == null || player.hasFinished() || !player.withinDistance(tile))
			return;
		player.getPackets().sendGraphics(new Graphics(player.lootbeamId), tile);
		player.sendMessage(Colors.orange + "<shad=000000>A beam shines over one of your items.");
	}

	public static boolean isRegionLoaded(int regionId) {
		Region region = getRegion(regionId);
		if (region == null)
			return false;
		return region.getLoadMapStage() == 2;
	}

	public static final WorldObject getObjectWithType(WorldTile tile, int type) {
		return getRegion(tile.getRegionId()).getObjectWithType(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), type);
	}

	/*
	 * used for dung
	 */
	public static final boolean removeGroundItem(final FloorItem floorItem) {
		int regionId = floorItem.getTile().getRegionId();
		Region region = getRegion(regionId);
		if (!region.getGroundItemsSafe().contains(floorItem))
			return false;
		region.getGroundItemsSafe().remove(floorItem);
		for (Player player : World.getPlayers()) {
			if (player == null || !player.hasStarted() || player.hasFinished() || !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendRemoveGroundItem(floorItem);
		}
		return true;
	}

	public static void executeAfterLoadRegion(final int regionId, final Runnable event) {
		executeAfterLoadRegion(regionId, 0, event);
	}

	public static void executeAfterLoadRegion(final int regionId, long startTime, final Runnable event) {
		executeAfterLoadRegion(regionId, startTime, 10000, event);
	}

	public static void executeAfterLoadRegion(final int fromRegionX, final int fromRegionY, final int toRegionX, final int toRegionY, long startTime, final long expireTime, final Runnable event) {
		final long start = Utils.currentTimeMillis();
		for (int x = fromRegionX; x <= toRegionX; x++) {
			for (int y = fromRegionY; y <= toRegionY; y++) {
				int regionId = MapUtils.encode(Structure.REGION, x, y);
				World.getRegion(regionId, true); // forces check load if not
				// loaded
			}
		}
		GameEngine.get().getFastExecutor().schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					for (int x = fromRegionX; x <= toRegionX; x++) {
						for (int y = fromRegionY; y <= toRegionY; y++) {
							int regionId = MapUtils.encode(Structure.REGION, x, y);
							if (!World.isRegionLoaded(regionId) && Utils.currentTimeMillis() - start < expireTime)
								return;
						}
					}
					event.run();
					cancel();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}

		}, startTime, 600);
	}

	/*
	 * TODO make this use code from above to save lines lo, they do same
	 */
	public static void executeAfterLoadRegion(final int regionId, long startTime, final long expireTime, final Runnable event) {
		final long start = Utils.currentTimeMillis();
		World.getRegion(regionId, true); // forces check load if not loaded
		CoresManager.fastExecutor.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					if (!World.isRegionLoaded(regionId) && Utils.currentTimeMillis() - start < expireTime)
						return;
					event.run();
					cancel();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}

		}, startTime, 600);
	}

	public static final void spawnObjectTemporary(final WorldObject object, long time, final boolean checkObjectInstance, boolean checkObjectBefore) {
		final WorldObject before = checkObjectBefore ? World.getObjectWithType(object, object.getType()) : null;
		spawnObject(object);
		GameEngine.get().getSlowExecutor().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					if (checkObjectInstance && World.getObjectWithId(object, object.getId()) != object)
						return;
					if (before != null)
						spawnObject(before);
					else
						removeObject(object); // this method allows to remove
					// object with just tile and type
					// actualy so the removing object
					// may be diferent and still gets
					// removed
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		}, time, TimeUnit.MILLISECONDS);
	}

	public static final void spawnObjectTemporary(final WorldObject object, long time) {
		spawnObject(object);
		GameEngine.get().getSlowExecutor().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					if (!World.isSpawnedObject(object))
						return;
					removeObject(object);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		}, time, TimeUnit.MILLISECONDS);
	}

	public static BotanyBay getBotanyBay() {
		return botanyBay;
	}


}
