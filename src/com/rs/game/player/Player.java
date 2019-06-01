package com.rs.game.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.runetoplist.VoteChecker;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.cores.mysql.impl.Highscores;
import com.rs.event.SecondsTimer;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.content.custom.playershop.CustomisedShop;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.WarriorsGuild;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.WarControler;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelRules;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.npc.others.GraveStone;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.achievements.AchievementManager;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.mining.Mining;
import com.rs.game.player.actions.mining.Mining.RockDefinitions;
import com.rs.game.player.actions.slayer.SlayerTaskHandler;
import com.rs.game.player.bot.SlaveTrips;
import com.rs.game.player.content.ArtisanWorkshop;
import com.rs.game.player.content.Assassins;
import com.rs.game.player.content.BankPin;
import com.rs.game.player.content.BossPetHandler.BossPets;
import com.rs.game.player.content.BossTimerManager;
import com.rs.game.player.content.BrawlingGManager;
import com.rs.game.player.content.DailyTasks;
import com.rs.game.player.content.DailyTasks.Taskss;
import com.rs.game.player.content.Deaths;
import com.rs.game.player.content.Deaths.Tasks;
import com.rs.game.player.content.DwarfCannon;
import com.rs.game.player.content.Ectophial;
import com.rs.game.player.content.EmailManager;
import com.rs.game.player.content.FairyRing;
import com.rs.game.player.content.FoodBag;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.JoinInstance;
import com.rs.game.player.content.LegendaryPet;
import com.rs.game.player.content.LodeStone;
import com.rs.game.player.content.NetWealth;
import com.rs.game.player.content.Notes;

import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.PointsManager;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.PrayerBooks;
import com.rs.game.player.content.RecipesHandler;
import com.rs.game.player.content.ReportAbuse;
import com.rs.game.player.content.Reputation;
import com.rs.game.player.content.RouteEvent;
import com.rs.game.player.content.SandwichLady;
import com.rs.game.player.content.SecuritySystem;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SkillingUrnsManager;
import com.rs.game.player.content.SlayerMasks.Masks;
import com.rs.game.player.content.SquealOfFortune;
//import com.rs.game.player.content.bot.BotList;
import com.rs.game.player.content.botanybay.BotanyBay;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.construction.HouseLocation;
import com.rs.game.player.content.construction.Room;
import com.rs.game.player.content.construction.RoomReference;
import com.rs.game.player.content.construction.ServantType;
import com.rs.game.player.content.contracts.Contract;
import com.rs.game.player.content.contracts.ContractHandler;
import com.rs.game.player.content.dungeoneering.DungManager;
import com.rs.game.player.content.grandExchange.GrandExchange;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.content.perks.PerkManager;
import com.rs.game.player.content.pet.PetManager;
import com.rs.game.player.content.ports.PlayerOwnedPort;
import com.rs.game.player.content.presets.PresetSetups;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.controlers.CorpBeastControler;
import com.rs.game.player.controlers.CrucibleControler;
import com.rs.game.player.controlers.DTControler;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.GodWars;
import com.rs.game.player.controlers.InstancedBossControler.Difficulty;
import com.rs.game.player.controlers.InstancedBossControler.Instance;
import com.rs.game.player.controlers.NomadsRequiem;
import com.rs.game.player.controlers.QueenBlackDragonController;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.ZGDControler;
import com.rs.game.player.controlers.castlewars.CastleWarsPlaying;
import com.rs.game.player.controlers.castlewars.CastleWarsWaiting;
import com.rs.game.player.controlers.fightpits.FightPitsArena;
import com.rs.game.player.controlers.pestcontrol.PestControlGame;
import com.rs.game.player.controlers.pestcontrol.PestControlLobby;
import com.rs.game.player.robot.Robot;
import com.rs.game.player.robot.Robot.RobotGameSession;
import com.rs.game.player.robot.RobotScript;
import com.rs.game.player.robot.scripts.combat.AncientHybrid;
import com.rs.game.player.robot.scripts.combat.AncientMage;
import com.rs.game.player.robot.scripts.combat.AncientTribrid;
import com.rs.game.player.robot.scripts.combat.Melee;
import com.rs.game.player.robot.scripts.combat.Range;
import com.rs.game.player.robot.scripts.combat.RegularMage;
import com.rs.game.player.robot.scripts.combat.RegularTribrid;
import com.rs.game.server.fameHall.HallOfFame;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.Session;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.net.encoders.WorldPacketsEncoder;
import com.rs.utils.IsaacKeyPair;
import com.rs.utils.KillStreakRank;
import com.rs.utils.Logger;
import com.rs.utils.MachineInformation;
import com.rs.utils.Misc;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public class Player extends Entity {

	private static final long serialVersionUID = 2011932556974180375L;
	/*
	 * consts
	 */
	public static final int TELE_MOVE_TYPE = 127, WALK_MOVE_TYPE = 1, RUN_MOVE_TYPE = 2;


	/**
	 * start skilling fields
	 */
    //prayer 
	public boolean isBuying;
	//fishing
	public boolean canBarbarianFish;
	// farming
	public FarmingManager farmingManager;
	//smithing
	// artisant
	private ArtisanWorkshop artisan;
	/*
	 * Construction
	 */
	protected House house;
	private JoinInstance joininstance;
	private transient RoomReference roomReference;

	public boolean inRing;
	public boolean hasHouse;
	private boolean hasBeenToHouse = false;
	private boolean buildMode;
	private boolean hasConfirmedRoomDeletion = false;
	private int houseX;
	private int houseY;
	private int[] boundChuncks;
	private List<WorldObject> conObjectsToBeLoaded;
	private List<RoomReference> rooms;
	private int place;
	private HouseLocation portalLocation;
	private ServantType butler;
	/**
	 * end skilling fields
	 */
	/**
	 * start item fields
	 */
	/* Urns */
	private SkillingUrnsManager urns;
    //pendants
	public PendantManager pendants;
	/* Brawling gloves */
	private BrawlingGManager brawlingGloves;
	// slayer upgrades
	public int slayerhelmLevel = 1;
	public boolean hasSlayerTabs = false;
	/**
	 * end item fields
	 */
	/**
	 * start quest vars
	 */
	/**
	 * Dwarf Cannon quest
	 */
	public int fixedRailings = 0;
	public boolean fixedRailing1 = false;
	public boolean fixedRailing2 = false;
	public boolean fixedRailing3 = false;
	public boolean fixedRailing4 = false;
	public boolean fixedRailing5 = false;
	public boolean fixedRailing6 = false;

	public boolean completedRailingTask = false;
	public boolean spokeToNu = false;
	public boolean completedDwarfCannonQuest = false;

	/**
	 * Quest Points
	 */
	public int questPoints = 0;
	/**
	 * end quest vars
	 */
	public int pirateTalk = 0;
	public transient boolean secondary;
	public transient boolean needsToExitCombat;// used for exiting combat
	public transient VarBitManager VBM;
	private DungManager dungManager;
	// Pking Server
	public boolean isPker;
	public int chosePker;
	public byte highestKillstreak;
	public byte killstreak;

	private boolean allowsProfanity;
	// joining
	public String joinName;

	public int ecoReset1;
	public int clueChance;
	public boolean finishedClue;
	private Reputation rep;
	// points
	public PointsManager pointsManager;
    //achievements
	private AchievementManager achievementManager;
	// legendary pet
	public LegendaryPet legendaryPet;
	
	// for reading the rules
	public boolean readRules;
	public boolean acceptedRules;
	// Orb
	public WorldTile orbLocation;
	public int orbCharges;
	// dungeoneering
	
	// used for item counting check
	public boolean viewGlobalCount = false;
	// npc inform99s
	public boolean shownPvmInfo = true;
	public boolean showPotionTimers = true;
    //pouch stuff
	public int moneyPouchTrade;
	public boolean addedFromPouch;
	// gravestone
	private int gStone;
	private GraveStone graveStone;

	private transient VarsManager varsManager;

	private PlayerDefinition definition;
	//ge tutor
	public boolean tutored = false;
	//not used
	private String referral;
	private boolean profanityFilter;
	//player notes
    //idk??
	private boolean verboseShopDisplayMode;
	// zaria quest
	public int ZariaQueststage = 0;
	public boolean completedZariaPartI = false;
	// Penguin
	public boolean penguin;
	public int penguinpoints;
	// GRAND EXCHANGE not used
	private GrandExchangeManager geManager;
	public int moneyFix;
	//pyra;id plunder
	public boolean pyramidReward;
	//boss timrs
	private transient BossTimerManager bossTimerManager;
	private LoyaltyPointsManager loyalty;
	
    //toolbelt
	private Toolbelt newToolbelt;
    //assasin skill, not used atm
	public boolean used1;
	public boolean finalblow;
	public boolean used2;
	public boolean swiftness;
	public boolean used3;
	public boolean stealth;
	public boolean used4;
    //rev dungeon
	private long forinthyRepel;
	// Ooglog Baths
	public boolean inBath;
	public boolean bandosBath;
	public boolean runBath;
	public boolean healthBath;
	// Dung Bags
	public int emeralds;
	public int sapphires;
	public int rubies;
	public int diamonds;
	public int coal;
	public int gembagspace;
	// halloday event items
	public String sendNoteTo;
	/** Charming imp **/
	public boolean[] selectedCharms;
	public boolean charmedPet;
	// Evil Tree
	public int treeDamage = 0;
	public boolean isChopping = false;
	public boolean isLighting = false;
	public boolean isRooting = false;
	public int onlinetime;
	/**
	 * presets
	 */
	public String preset1 = "preset 1";
	public String preset2 = "preset 2";
	public String preset3 = "preset 3";
	public String preset4 = "donators only.";
	public String preset5 = "donators only.";
	public String presetone = "preset 1";
	public String presettwo = "preset 2";
	public String presetthree = "preset 3";
	public String presetfour = "preset 4";
	public String presetfive = "preset 5";
	// Lending
	public boolean isLendingItem = false;
	public int lendMessage;
	public int RG = 0;
	public int christmas = 0;
	public boolean snowrealm;
	public int VS = 0;
	public int DS = 0;
	public int EC = 0;
	public boolean bowl;
	public boolean bomb;
	public boolean pot;
	public boolean silk;
	public boolean patched;
	public boolean boughtship;
	public boolean rubberTube;
	public boolean key;
	public boolean fishfood;
	public boolean poison;
	public boolean spade;
	public int RM = 0;
	public int crystalcharges;
    //bot
	protected boolean isBot;
	protected String botOwner;
	protected List<String> botOwners = new LinkedList<>();
    //pest control
	public boolean isInPestControlLobby;
	public boolean isInPestControlGame;
	public int pestDamage;
	public boolean brim = false;
	// reaper
	private ContractHandler cHandler;
	private Contract cContracts;
	// Imp Catcher Quest
	public boolean startedImpCatcher = false;
	public boolean inProgressImpCatcher = false;
	public boolean completedImpCatcher = false;
	// Juju potions
	private transient int jujuMining = 0;
	private transient int jujuFarming = 0;
	private transient int jujuFishing = 0;
	private transient int jujuWoodcutting = 0;
	private transient int jujuScentless = 0;
	private transient int jujuGod = 0;
	// Castle Wars
	private boolean capturedCastleWarsFlag;
	private int finishedCastleWars;
    //join date
	public static String joinDate;
	// Lost City
	public int lostCity = 0;
	public boolean spokeToWarrior = false;
	public boolean spokeToShamus = false;
	public boolean spokeToMonk = false;
	public boolean recievedRunes = false;
	// GameMode settings
	// GameMode settings
	public int gameMode = 0;
	public boolean choseGameMode;
	protected boolean skullMode;
	protected int skull;

	public boolean getSkullMode() {
		return skullMode;
	}

	
	// Internships
	public boolean quickWork;
	public int iainAmount = 0;
	public int iainId = 0;
	public boolean iainTask;
	public int smithAmount = 0;
	public int smithId = 0;
	public boolean smithTask;
	public int priestAmount = 0;
	public int priestId = 0;
	public boolean priestTask;
	public int julianAmount = 0;
	public int julianId = 0;
	public boolean julianTask;
	//clan capes
	private int[] clanCapeCustomized;
	private int[] clanCapeSymbols;
	// wilderness upgrades
	public boolean increasedBlinkDamage = false;
	public boolean quickShred = false;
	//spinner
	public int spinTimer = 0;

	// Mac Banning
	private InetAddress connected;
	private String macAddress;
	//gold gloves
	private int goldGlovesCharges = 100;
	// paoloscape minigame
	public int Nstage1 = 0;
	public int Nstage2 = 0;
	public int Nstage3 = 0;

	// Comp Cape
	public List<Integer> dungItems = new ArrayList<Integer>();
	public List<Integer> crystalItems = new ArrayList<Integer>();
	public List<BossPets> collectedPets = new ArrayList<BossPets>();
	// God Wars
	public int armadyl = 0;
	public int bandos = 0;
	public int saradomin = 0;
	public int zamorak = 0;

	// Cooks Assistant Quest
	public boolean startedCooksAssistant = false;
	public boolean inProgressCooksAssistant = false;
	public boolean completedCooksAssistantQuest = false;
	public boolean hasGrainInHopper = false;

	// Gertrude's Cat Quest

	public boolean completedGertCat = false;
	public int gertCat = 0;

	// Drakan Charges
	public int drakanCharges;

	// Penance Horn
	public int horn;

	// AFK
	public long afkTimer = 0;

	private List<String> cachedChatMessages;
	private long lastChatMessageCache;

	// Stones
	private boolean[] activatedLodestones;
	private LodeStone lodeStone;

	
	// PVP
	public int Killstreak = 0;
	private static Item pvpRandomCommon[] = { new Item(554, 50), new Item(555, 50), new Item(556, 50), new Item(557, 50), new Item(558, 50), new Item(559, 50), new Item(560, 50), new Item(561, 50), new Item(562, 50), new Item(563, 50), new Item(564, 50), new Item(565, 50), new Item(566, 50), new Item(12158, 50), new Item(12159, 50), new Item(12160, 50), new Item(12161, 50), new Item(12162, 50), new Item(12163, 50), new Item(12164, 50), new Item(12165, 50), new Item(12166, 50), new Item(12167, 50), new Item(12168, 50), new Item(15272, 5), new Item(2434, 3) };
	private static Item pvpRandomUncommon[] = { new Item(11694, 1), new Item(11696, 1), new Item(11698, 1), new Item(11700, 1) };
	private static Item pvpRandomRare[] = { new Item(20135, 1), new Item(20139, 1), new Item(20143, 1), new Item(24977, 1), new Item(18349, 1), new Item(18351, 1), new Item(18353, 1), new Item(24352, 1), new Item(14484, 1), new Item(18786, 1), new Item(24992, 1), new Item(24995, 1), new Item(24998, 1), new Item(25001, 1), new Item(25004, 1) };
	private static Item pvpRandomExtremelyRare[] = { new Item(16955, 1), new Item(15773, 1), new Item(16425, 1), new Item(16909, 1), new Item(10348, 1), new Item(18357, 1), new Item(21777, 1), new Item(25037, 1) };

	public static Item pvpRandom() {
		int chance = Misc.random(300);
		if (chance <= 4)
			return pvpRandomExtremelyRare[Misc.random(pvpRandomExtremelyRare.length)];
		else if (chance >= 5 && chance <= 25)
			return pvpRandomRare[Misc.random(pvpRandomRare.length)];
		else if (chance >= 26 && chance <= 150)
			return pvpRandomUncommon[Misc.random(pvpRandomUncommon.length)];
		else
			return pvpRandomCommon[Misc.random(pvpRandomCommon.length)];
	}
	
	//game modes
	protected boolean ironMan;

	public boolean isIronMan() {
		return ironMan && getGameMode() == 3;
	}

	public void setIronMan(boolean b) {
		ironMan = b;
	}


	// Troll
	public boolean trollReward;
	private int trollsToKill;
	private int trollsKilled;
    //staff security
	public boolean hasStaffPin;

	// Random Events
	public int tX;
	public int tY;
	public int tH;
	public boolean saved;

	public void saveLoc(int x, int y, int h) {
		this.tX = x;
		this.tY = y;
		this.tH = h;
	}

	public int gotreward = 0;

	// Halloween
	public int cake = 0;
	public int dust1 = 0;
	public int dust2 = 0;
	public int dust3 = 0;
	public int drink = 0;
	public int doneevent = 0;
	public int talked = 0;
	/**
	 * Smoking Kills Quest
	 *
	 */
	public int sKQuest = 0;
	public boolean completedSmokingKillsQuest = false;


	public boolean isAnthonyRank = false;

	private transient boolean listening;

	// Comp cape
	public static int isMaxed1 = 1;
	public static int isCompletionist1 = 1;

	// kiln cape
	public int magicD;
	public int rangedD;
	public int meleeD;

	// Warriors Guild
	private boolean inClops;
	private int wGuildTokens;
	private double[] warriorPoints;

	// mastercapes ability's
	public int hpMasterCape = 10;
	public int prayerMasterCape = 4;
	public int fishMasterCape = 4;
	public int smithMasterCape = 4;
	public int wcMasterCape = 4;
	public int mineMasterCape = 4;
	public int cookMasterCape = 4;
	// for master thievs
	public int getCaught = 0;

	public int dailyClueReward = 1;

	/**
	 * handles the daily reset of the players, spins etc
	 */
	public void dailyreset() {
		if (isDonator()) {
			if(isSupremeDonator()){
				quickTeleports = 10000000;
				dailyDonatorTokens +=20;
			}
			else if(isDivineDonator()){
				quickTeleports = 10000000;
				dailyDonatorTokens +=10;
			}
			else if(isLegendaryDonator()){
				quickTeleports = 10000000;
				dailyDonatorTokens +=5;
			 }else if(isExtremeDonator()){
				quickTeleports = 250;
				dailyDonatorTokens +=2;
			}else{ 
				quickTeleports = 100;
			dailyDonatorTokens++;
			}
			dailyClueReward = 1;
		} else {
			quickTeleports = 25;
		}
		getPorts().dailyReset();
		slayerMasksTeleports.clear();
		slayerTasksGiven.clear();
		resetRedStone(this);
		hpMasterCape = 10;
		prayerMasterCape = 4;
		fishMasterCape = 4;
		smithMasterCape = 4;
		wcMasterCape = 4;
		mineMasterCape = 4;
		cookMasterCape = 4;
		daysOnline++;
		setPAdamage(0);
	}
	/**
	 * handles gold gloves giving gold
	 */
	public void handelGoldGloves() {
		if (goldGlovesCharges == 0)
			return;
		if (getEquipment().getGlovesId() != 28014)
			return;
		goldGlovesCharges--;
		getInventory().addItem(995, Utils.random(10000, 50000));
		sm("Your samid's gloves earned you some money.");

	}

	// Sword of Wiseman Quest
	public int SOWQUEST;
    /*
     * resets their skills
     */
	public void resetPlayer() {
		for (int skill = 0; skill < 25; skill++)
			getSkills().setXp(skill, 1);
		getSkills().init();
	}
	
	public String getMacAddress() {
		try {
			InetAddress address = connected;
			NetworkInterface net = NetworkInterface.getByInetAddress(address);

			byte[] mac = net.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i > mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			macAddress = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			sm("There was an error grabbing players MAC address.");
		}
		return macAddress;
	}
	
	public static void getDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		joinDate = dateFormat.format(cal.getTime());

	}

	public void resetPlayer2() {
		for (int skill = 0; skill < 25; skill++)
			// getSkills().setXp(skill, 1);
			getSkills().set(skill, 1);
		getSkills().setXp(3, 1154);
		getSkills().set(3, 10);
		getSkills().init();
	}

	public boolean Prestige1;

	public int prestigeTokens = 0; // prestige points.

	public boolean isPrestige1() {
		return Prestige1;
	}

	public void setPrestige1() {
		if (!Prestige1) {
			Prestige1 = true;
		}
	}

	public void gpay(Player player, String username) {
		try {
			username = username.replaceAll(" ", "_");
			String secret = "abc9b5b2ac6417c808a4cd6b04d65ec0"; // YOUR SECRET
																// KEY!
			URL url = new URL("http://app.gpay.io/api/runescape/" + username + "/" + secret);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String results = reader.readLine();
			if (results.toLowerCase().contains("!error:")) {
				Logger.log(this, "[GPAY]" + results);
			} else {
				String[] ary = results.split(",");
				for (int i = 0; i < ary.length; i++) {
					switch (ary[i]) {
					case "0":
						player.sm("Donation not found!");
						break;
					case "20614":
						player.setDonator(true);
						player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 10);
						player.sm("Thank you for donating!");
						SerializableFilesManager.savePlayer(player);
						break;
					case "20615":
						player.setExtremeDonator(true);
						player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 20);
						player.sm("Thank you for donating!");
						SerializableFilesManager.savePlayer(player);
						break;
					case "20616":
						player.setLegendaryDonator(true);
						player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 50);
						player.sm("Thank you for donating!");
						SerializableFilesManager.savePlayer(player);
						break;
					case "20617":
						player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 10);
						player.sm("Thank you for donating!");
						SerializableFilesManager.savePlayer(player);
						break;
					case "20618":
						player.getPointsManager().setDonatorTokens(player.getPointsManager().getDonatorTokens() + 100);
						player.sm("Thank you for donating!");
						SerializableFilesManager.savePlayer(player);
						break;
					}
				}
			}
		} catch (IOException e) {

		}
	}

	public void setCompletedPrestigeOne() {
		if (getEquipment().wearingArmour()) {
			getPackets().sendGameMessage("<col=ff0000>You must remove your amour before you can prestige.");
			if (prestigeTokens == 0) {
				prestigeTokens++;
				if (gameMode == 3) {
					getInventory().addItem(29968, 25);
				} else if (gameMode == 2) {
					getInventory().addItem(29968, 10);
				} else if (gameMode == 1) {
					getInventory().addItem(29968, 3);
				} else if (gameMode == 0) {
					getInventory().addItem(29968, 1);
				}
				resetPlayer();
				resetPlayer2();
				resetHerbXp();
				Prestige1 = false;
				setNextAnimation(new Animation(1914));
				setNextGraphics(new Graphics(1762));
				getPackets().sendGameMessage("You feel a force reach into your soul, You gain One Prestige Token.");
				World.sendWorldMessage("<img=7><col=ff0000>News: " + getDisplayName() + " has just prestiged! he has now prestiged " + prestigeTokens + " times.", false);
				if (prestigeTokens == 5) {
					getPackets().sendGameMessage("<col=ff0000>You have reached the last prestige, you can no longer prestige.");
				}
			}
		}
	}

	public int getPrestigeLevel() {
		return prestigeTokens;
	}

	public void prestigeShops() {
		if (prestigeTokens == 0) {
			getPackets().sendGameMessage("You need to have prestiged to gain access to this shop.");
		} else if (prestigeTokens == 1) {
			ShopsHandler.openShop(this, 69);
		}
	}

	public void nothing() {
		getPackets().sendGameMessage("You have completed all the prestiges.");
	}

	public void setCompletedPrestige2() {
		if (prestigeTokens >= 1) {
			resetCbXp();
			resetCbSkills();
			resetSummon();
			resetSummonXp();
			prestigeTokens++;
			getInventory().addItem(29968, 1);
			Prestige1 = false;
			setNextAnimation(new Animation(1914));
			setNextGraphics(new Graphics(1762));
			getPackets().sendGameMessage("You feel a force reach into your soul, You gain One Prestige Token.");
			World.sendWorldMessage("<img=7><col=ff0000>News: " + getDisplayName() + " has just prestiged! he has now prestiged " + prestigeTokens + " times.", false);
		}
	}

	public void resetCbXp() {
		for (int skill = 0; skill < 7; skill++)
			getSkills().setXp(skill, 1);
		// getSkills().set(skill, 1);
		getSkills().init();
	}

	public void resetHerbXp() {
		getSkills().set(15, 3);
		getSkills().setXp(15, 174);
	}

	public void resetSummon() {
		getSkills().set(23, 1);
		getSkills().init();
	}

	public void resetSummonXp() {
		getSkills().setXp(23, 1);
		getSkills().init();
	}

	public void resetCbSkills() {
		for (int skill = 0; skill < 7; skill++)
			getSkills().set(skill, 1);
		getSkills().setXp(3, 1154);
		getSkills().set(3, 10);
		getSkills().init();
	}

	public boolean isAtHome = isAtHome();

	/**
	 * To check if the players location
	 **/

	public boolean isAtHome() {
		return (getX() >= 2303 && getX() <= 2364 && getY() <= 3706 && getY() >= 3649);
	}

	public boolean isAtGorger() {
		return (getX() >= 3443 && getX() <= 3456 && getY() <= 3754 && getY() >= 3738);
	}

	public boolean isAtGodwars() {
		return (getX() >= 2824 && getX() <= 2939 && getY() <= 5368 && getY() >= 5237);
	}

	public boolean isAtNex() {
		return (getX() >= 2899 && getX() <= 2942 && getY() <= 5220 && getY() >= 5187);
	}

	public int assassin;
	// player examine
	private static String Examined = "";
	// new slayer drops
	public boolean hasNewSlayerDrops = false;
	public boolean canAddBonecrusher = false;
	public boolean canAddCoinAccumulator = false;
	public boolean canAddHerbicide = false;
	public boolean canAddRockhammer = false;
	// Start Comp Cape

	public int penguins;
	public int sinkholes;
	public int totalTreeDamage;
	public int barrowsLoot;
	public int domCount;
	public int castleWins;
	public int trollWins;
	public int implingCount;
	public int pestWins;
	/**
	 ** counts
	 **/
	// misc
	public int voteCount;
	public int spinsCount;
	public int crystalChest;
	public int clueScrolls;
	public int barrowsChests;
	// prayer
	public int dragonbonesBurried;
	public int bonesBurried;
	// craft
	public int cutDiamonds;
	public int cutOnyxs;
	// slayer
	public int slayerTasks;
	public int HardcoreSlayerTasks;
	// hunter
	public int grenwalls;
	public int trapCatches;
	// smithing
	public int cannonBall;
	// mining
	public int runiteOre;
	public int MithrilOre;
	// fishing
	public int rockTails;
	public int monkfish;
	public int LavaShark;
	public int sharks;
	public int fished;
	// cooking
	public int cookedFish;
	public int CookRocktails;
	// firmaking
	public int burntLogs;
	public int Willowsburned;
	// woodcutting
	public int choppedIvy;
	// farming
	public int harvestedTrees;
	public int magicTreesHarvest;
	// summ
	public int infusedPouches;
	// dung
	public int completedDungeons;
	// agility
	public int advancedagilitylaps;
	public int NormalGnome;
	public int AdvGnome;
	public int NormalBurthope;
	public int NormalBarb;
	public int laps;
	// fletching
	public int bowsFletched;
	// thieving
	public int heroSteals;
	public int masterThieves;
	// wildy
	public int WildyP;
	public int wildyChest;
	// runecrafting
	public int RunespanSiphon;
	public int bloodRunesCrafted;

	public boolean hasRequirements() {
		return false;
	}

	public boolean hasCompletedFightCaves() {
		return completedFightCaves;
	}

	public boolean hasCompletedPestInvasion() {
		return completedPestInvasion;
	}

	public boolean hasCompletedFightKiln() {
		return completedFightKiln;
	}

	// End Comp Cape

	public void prestige() {
		if (getSkills().getLevel(Skills.ATTACK) >= 99 && getSkills().getLevel(Skills.STRENGTH) >= 99 && getSkills().getLevel(Skills.DEFENCE) >= 99 && getSkills().getLevel(Skills.RANGE) >= 99 && getSkills().getLevel(Skills.MAGIC) >= 99 && getSkills().getLevel(Skills.PRAYER) >= 99 && getSkills().getLevel(Skills.HITPOINTS) >= 99 && getSkills().getLevel(Skills.COOKING) >= 99 && getSkills().getLevel(Skills.WOODCUTTING) >= 99 && getSkills().getLevel(Skills.FLETCHING) >= 99 && getSkills().getLevel(Skills.FISHING) >= 99 && getSkills().getLevel(Skills.FIREMAKING) >= 99 && getSkills().getLevel(Skills.CRAFTING) >= 99 && getSkills().getLevel(Skills.SMITHING) >= 99 && getSkills().getLevel(Skills.MINING) >= 99 && getSkills().getLevel(Skills.HERBLORE) >= 99 && getSkills().getLevel(Skills.AGILITY) >= 99 && getSkills().getLevel(Skills.THIEVING) >= 99 && getSkills().getLevel(Skills.SLAYER) >= 99 && getSkills().getLevel(Skills.FARMING) >= 99 && getSkills().getLevel(Skills.HUNTER) >= 99 && getSkills().getLevel(Skills.RUNECRAFTING) >= 99 && getSkills().getLevel(Skills.CONSTRUCTION) >= 99 && getSkills().getLevel(Skills.SUMMONING) >= 99 && getSkills().getLevel(Skills.DUNGEONEERING) >= 99) {
			setPrestige1();
		}
	}

	// money pouch
	public int money1 = 0;

	public int noob = 0;

	// Player Owned Shops
	private CustomisedShop customShop;

	public CustomisedShop getPlayerShop() {
		return customShop;
	}

	public void setPlayerShop(CustomisedShop customisedShop) {
		this.customShop = customisedShop;
	}

	protected SlayerManager slayerManager;

	private Assassins assassins;
	private Deaths deaths;
	// box search
	public int searchBox = 0;//

	public int getSearchBox() {
		return searchBox;
	}

	public void addSearchBox() {
		searchBox += 1;
	}

	public boolean spinningWheel = false;
	public boolean monsterPageOne = true;
	public boolean monsterPageTwo = false;

	public boolean strongHoldOne = false;
	public boolean strongHoldTwo = false;
	public boolean strongHoldThree = false;
	public boolean strongHoldFour = false;

	public boolean strongHoldChestOne = false;
	public boolean strongHoldChestTwo = false;
	public boolean strongHoldChestThree = false;
	public boolean strongHoldChestFour = false;

	public boolean cockRoachShortcut = false;
	public boolean cockRoachLever = false;
	public boolean cockRoachChest = false;

	public boolean hasAgileSet(Player player) {
		if (player.getEquipment().getChestId() == 14936 && player.getEquipment().getLegsId() == 14936)
			return true;
		return false;
	}

	/**
	 * Barbarian Training!
	 */
	// barcrawl

	// each bar for barcrawl
	public int BlueMoonInn = 0;
	public int BlurberrysBar = 0;
	public int DeadMansChest = 0;
	public int DragonInn = 0;
	public int FlyingHorseInn = 0;
	public int ForestersArms = 0;
	public int JollyBoarInn = 0;
	public int KaramjaSpiritsBar = 0;
	public int RisingSun = 0;
	public int RustyAnchor = 0;

	public int barCrawl = 0;
	public int killedByAdam = 0;

	public boolean barCrawlCompleted = false;

	public void finishBarCrawl() {
		if (BlueMoonInn == 1 && BlurberrysBar == 1 && DeadMansChest == 1 && DragonInn == 1 && FlyingHorseInn == 1 && ForestersArms == 1 && JollyBoarInn == 1 && KaramjaSpiritsBar == 1 && RisingSun == 1 && RustyAnchor == 1) {

			barCrawlCompleted = true;
		} else
			barCrawlCompleted = false;
	}

	// Clans
	private transient ClansManager clanManager, guestClanManager;
	private int clanChatSetup;
	private String clanName;// , guestClanChat;
	private int guestChatSetup;
	private boolean connectedClanChannel;
	// trivia
	public boolean hasAnswered;

	private ArrayList<String> blockedSlayerTasks = new ArrayList<String>();

	// Rfd
	public boolean rfd1, rfd2, rfd3, rfd4, rfd5 = false;

	// starter
	public int starter = 0;

	public void refreshMoneyPouch() {
		// getPackets().sendConfig(1438, (money >> 16) | (money >> 8) & money);
		getPackets().sendRunScript(5560, money);
	}

	public int starterstage = 0;

	// Achievement System
	public boolean ToggleSystem = true;
	public int MiningAchievement;
	public int IvyAchievement;
	public int SiphonAchievement;
	public int BurnAchievement;

	// Teleports
	public boolean Ass;
	public boolean Gnome;
	public boolean Demon;
	public boolean Pony;
	public boolean SuperJump;

	// News
	public boolean setnews = true;

	public boolean deathShop = false;
	public int DeathPoints = 0;
	public int damount = 0;
	public Tasks deathTask = null;
	public boolean dhasTask = false;
	// slayer
	public boolean hasTask = false;
	public int slayerTaskAmount = 0;


	/**
	 * message filters
	 */
	public boolean riddleMessages = true;
	public boolean inform99s = true;
	// ClueScrolls
	public int cluenoreward;
	public int clueLevel;

	// LatestUpdate
	public boolean completed = true;

	// Tutorial
	public int nextstage = 0;

	// Squeal
	public int Rewards;
	private SquealOfFortune squealOfFortune;

	// spirit gems
	public int spiritSapphire; // 10
	private int spiritEmerald;// 20
	private int spiritRuby;// 30
	private int spiritDiamond;// 40
	private int spiritDragonstone;// 50
	private int spiritOnyx;// 60



	public void setSpiritGemCharges(int gem) {
		if (gem == 30139) {
			spiritSapphire = 10;
		}
		if (gem == 30140) {
			spiritEmerald = 20;
		}
		if (gem == 30141) {
			spiritRuby = 30;
		}
		if (gem == 30142) {
			spiritDiamond = 40;
		}
		if (gem == 30143) {
			spiritDragonstone = 50;
		}
		if (gem == 30144) {
			spiritOnyx = 60;
		}
	}

	public void handleSpiritGems(int amount, int charmId) {
		if (spiritSapphire > 0 && getInventory().containsItem(30453, 1)) {
			if (spiritSapphire < amount / 5) {
				spiritSapphire = 0;
				getInventory().deleteItem(30453, 1);
				sm("Your spirit gem has vanished.");
			} else {
				spiritSapphire -= amount;
				sm("Your spirit sapphire saved you " + amount + " charms.");
			}
		}
	}

	public int getSpiritGemCharges() {
		return spiritSapphire + spiritEmerald + spiritRuby + spiritDiamond + spiritDragonstone + spiritOnyx;
	}

	public boolean hasSpiritGemActive = false;
	// RuneSpan Points
	public int RuneSpanPoints;

	// Animation Settings
	public boolean SamuraiCooking;
	public boolean ArcaneCooking;
	public boolean PartyhatFiremaking;
	public boolean ChillBlastMining;
	public boolean KarateFletching;

	// slayer masters
	private boolean talkedWithKuradal;
	private boolean talkedWithSpria;
	private boolean talkedWithMazchna;

	// Completionist Cape
	public int isCompletionist = 1;
	public int isMaxed = 1;

	// transient stuff
	@SuppressWarnings("unused")
	private long lastLoggedIn;
	public double moneyInPouch;
	private MoneyPouch moneyPouch;
	private static final int lastlogged = 0;
	protected static final Player Player = null;
	// public static final int bloodcharges = 0;
	public int DeathTask = 0;
	protected transient String username;
	protected transient DwarfCannon DwarfCannon;
	private transient int cannonBalls;
	protected transient Session session;
	private transient Ectophial ectophial;
	private transient ClueScrolls ClueScrolls;
	private transient SpinsManager spinsManager;
	private transient LoyaltyManager loyaltyManager;
	private transient boolean clientLoadedMapRegion;
	protected transient int displayMode;
	protected transient int screenWidth;
	protected transient int screenHeight;
	private transient InterfaceManager interfaceManager;
	private transient EmailManager emailManager;
	private transient ReportAbuse reportAbuse;
	private transient DialogueManager dialogueManager;
	protected transient HintIconsManager hintIconsManager;
	private transient ActionManager actionManager;
	protected transient CutscenesManager cutscenesManager;
	private transient PriceCheckManager priceCheckManager;
	private transient CoordsEvent coordsEvent;
	private transient FriendChatsManager currentFriendChat;
	private transient Trade trade;
	private transient DuelRules lastDuelRules;
	private transient IsaacKeyPair isaacKeyPair;
	protected transient Pet pet;
	public transient Player divines;
	public int divine;
	//private transient ShootingStar ShootingStar;
	// used for boss kill timers
	public long[] FastestTime;
	// used for packets logic
	private transient ConcurrentLinkedQueue<LogicPacket> logicPackets;

	// used for update
	private transient LocalPlayerUpdate localPlayerUpdate;
	private transient LocalNPCUpdate localNPCUpdate;

	private int temporaryMovementType;
	private boolean updateMovementType;

	// player stages
	protected transient boolean started;
	protected transient boolean running;
	private transient long stopDelay;
	private transient long packetsDecoderPing;
	protected transient boolean resting;
	private transient boolean canPvp;
	private transient boolean cantTrade;
	public transient long lockDelay; // used for doors and stuff like that
	protected transient long foodDelay;
	protected transient long potDelay;
	private transient long boneDelay;
	private transient long ashDelay;
	private transient Runnable closeInterfacesEvent;
	private transient long lastPublicMessage;
	public transient long polDelay;
	private transient List<Integer> switchItemCache;
	private transient boolean disableEquip;
	private transient MachineInformation machineInformation;
	private transient boolean spawnsMode;
	public transient boolean castedVeng;
	private transient boolean invulnerable;
	private transient double hpBoostMultiplier;
	private transient boolean largeSceneView;
	/**
	 * Lootbeam wealth
	 **/
	public int setLootBeam;
	// kelly stuff
	protected String password;
	private int rights;
	public PlayerData playerData;
	private String displayName;
	private String lastIP;
	@SuppressWarnings("unused")
	private long creationDate;
	protected Appearence appearence;
	private Inventory inventory;
	private Equipment equipment;
	protected Skills skills;
	protected CombatDefinitions combatDefinitions;
	protected Prayer prayer;
	private Bank bank;
	private FoodBag foodBag;
	public boolean isInFoodBag;
	protected ControlerManager controlerManager;
	private MusicsManager musicsManager;
	private EmotesManager emotesManager;
	private FriendsIgnores friendsIgnores;
	private FairyRing fairyRing;
	private DominionTower dominionTower;
	protected Familiar familiar;
	private AuraManager auraManager;
	private PetManager petManager;
	private byte runEnergy;
	private boolean allowChatEffects;
	private boolean mouseButtons;
	private int privateChatSetup;
	private int friendChatSetup;
	protected int skullDelay;
	private int skullId;
	private boolean forceNextMapLoadRefresh;
	protected long poisonImmune;
	private long luckEffect;
	protected long fireImmune;
	private long superFireImmune;
	private boolean killedQueenBlackDragon;
	private int runeSpanPoints;

	private int lastBonfire;
	public int legendaryPetBankCooldown;
	private int[] pouches;
	private long displayTime;
	private long muted;
	private long jailed;
	private long banned;
	private boolean inDung;
	private boolean permBanned;
	private boolean filterGame;
	private boolean xpLocked;
	private boolean yellOff;
	// game bar status
	private int publicStatus;
	private int clanStatus;
	private int tradeStatus;
	private int assistStatus;
    /**
     * donator stuff
     */
	private boolean donator;
	private boolean extremeDonator;
	private boolean legendaryDonator;
	private boolean supremeDonator;
	private boolean divineDonator;
	private boolean angelicDonator;

	/**
	 * playtime
	 */
	public long onlineTime;

	void startCounting() {

		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				if (!hasStarted() || hasFinished()) {
					stop();
					return;
				}
				if (onlineTime % 3600 == 0)
					getLoyaltyPTSManager().addPoints();
				onlineTime++;
			}

		}, 0, 1);

	}

	public boolean ZREST;
	public boolean AREST;

	// Recovery ques. & ans.
	private String recovQuestion;
	private String recovAnswer;

	private String lastMsg;

	// Used for storing recent ips and password
	private ArrayList<String> passwordList = new ArrayList<String>();
	private ArrayList<String> ipList = new ArrayList<String>();

	// honor
	private int killCount, deathCount;
	private ChargesManager charges;
	// barrows
	private boolean[] killedBarrowBrothers;
	private int hiddenBrother;
	private int barrowsKillCount;
	public int pestPoints;

	// Squeal
	public int spins;
	@SuppressWarnings("unused")
	private int freeSpins = 0;

	// Bank Pins
	private BankPin pin;
	public boolean bypass;
	private int pinpinpin;
	public boolean setPin = false;
	public boolean openPin = false;
	public boolean startpin = false;
	private int[] bankpins = new int[] { 0, 0, 0, 0 };
	private int[] confirmpin = new int[] { 0, 0, 0, 0 };
	private int[] openBankPin = new int[] { 0, 0, 0, 0 };
	private int[] changeBankPin = new int[] { 0, 0, 0, 0 };

	public int daysOnline;

	public void checkLoyaltyCrown() {
		if (daysOnline < 7) {
			sm("You need to login in total for 7 days before you can claim the first crown");
		} else if (daysOnline >= 7 && daysOnline < 14) {
			getInventory().addItem(29108, 1);
		} else if (daysOnline >= 14 && daysOnline < 21) {
			getInventory().addItem(29110, 1);
		} else if (daysOnline >= 21 && daysOnline < 28) {
			getInventory().addItem(29112, 1);
		} else if (daysOnline >= 28 && daysOnline < 35) {
			getInventory().addItem(29114, 1);
		} else if (daysOnline >= 35 && daysOnline < 42) {
			getInventory().addItem(29116, 1);
		} else if (daysOnline >= 42 && daysOnline < 49) {
			getInventory().addItem(29118, 1);
		} else if (daysOnline >= 49 && daysOnline < 56) {
			getInventory().addItem(29120, 1);
		} else if (daysOnline >= 63 && daysOnline < 70) {
			getInventory().addItem(29122, 1);
		} else if (daysOnline >= 70 && daysOnline < 77) {
			getInventory().addItem(29124, 1);
		} else if (daysOnline >= 77) {
			getInventory().addItem(29126, 1);
		}
	}
	

	public void refreshSqueal() {
		getPackets().sendConfigByFile(11026, getSpins());
	}


	/**
	 * Red stone mining limit.
	 **/
	public int redstone;
	public int redStoneMined;

	// checks the players stones mined
	public void CheckRedStoneAmount(Player player, final WorldObject object) {
		if (redstone >= 200) {
			sm("The ore is empty for today, tomorrow you can mine again 200 stones.");
		} else {
			player.getActionManager().setAction(new Mining(object, RockDefinitions.SAND_STONE));
			return;
		}

	}

	// call the methode to reset the redstone.
	public void resetRedStone(Player player) {
		this.redstone = 0;

	}


	/**
	 * returns if the player is able to claim the final boss titel
	 * 
	 * @return true if can claim.
	 */
	public boolean isFinalBoss() {
		int amount = 0;
		boolean not100 = false; // if he has one less than 100
		for (Integer key : getBossCount().keySet()) { // loop throu list
			if (getBossCount().get(key) < 100)
				not100 = true; // set true if so
			amount += getBossCount().get(key);
		}
		if (amount > 2000 && !not100) {
			if (HallOfFame.firstFinalBoss[0] == null) {
				HallOfFame.firstFinalBoss[0] = getDisplayName();
				HallOfFame.firstFinalBoss[1] = java.time.LocalDate.now().toString();
				isInHOF = true;
				HallOfFame.save();
				sm("Congratz you are now in the hall of fame.");
			}
			return true;
		}
		return false;
	}
	// Start Comp Cape

	// Flask
	/*
	 * Checks Max Cape Requirements.
	 */
	public boolean isMaxed() {
		for (int i = 0; i < 24;) {
			if (this.getSkills().getLevel(i) >= 99) {
				setCompletedMax();
				return true;
			} else {
				getPackets().sendGameMessage("You need to have completed all the requirements to wear the Max cape.");
				return false;
			}
		}
		return false;
	}

	public void setCompletedMax() {
		if (!completedMax) {
			completedMax = true;
		}
	}

	public boolean completedMax;

	/*
	 * Checks Completionist Cape Requirements.
	 */
	public boolean completedCompletionistCape() {
		for (int i = 0; i < 23; ) {
			if (getSkills().getLevel(i) >= 99 && getSkills().getLevel(24) >= 120 
					&& isCompletedFightCaves() 
					&& isCompletedFightKiln() 
					&& hasCompletedPestInvasion()) {
				setCompletedComp();
				return true;
			} else {
				getPackets().sendGameMessage("You need to have completed all the requirements to wear the completionist cape.");
				return false;
			}
		}
		return false;
	}

	public void setCompletedComp() {
		if (!completedComp) {
			completedComp = true;
		}
	}

	private boolean completedComp;

	public boolean isCompletedComp() {
		return completedComp;
	}


	private boolean completedTrim;

	public boolean isCompletedTrim() {
		return completedTrim;
	}


	// keepsakeboxes
	public int helmId = -1;
	public int bodyId = -1;
	public int legId = -1;

	public String helmName = "nothing";
	public String bodyName = "nothing";
	public String legName = "nothing";

	public int cosmeticHelm;
	public int cosmeticBody;
	public int cosmeticLegs;
	public int cosmeticGloves;
	public int cosmeticBoots;

	public boolean hasCosmeticSetActive = false;
	// keepsakeboxes
	private boolean iskeepsakebox;

	public boolean iskeepsakebox() {
		return iskeepsakebox;
	}

	public void setkeepsakebox(boolean value) {
		this.iskeepsakebox = value;
	}

	// keepsakeboxes
	private boolean iskeepsakebody;

	public boolean iskeepsakebody() {
		return iskeepsakebody;
	}

	public void setkeepsakebody(boolean value) {
		this.iskeepsakebody = value;
	}

	// keepsakeleges
	private boolean iskeepsakeleg;

	public boolean iskeepsakeleg() {
		return iskeepsakeleg;
	}

	public void setkeepsakeleg(boolean value) {
		this.iskeepsakeleg = value;
	}

	// private mod
	public boolean privateMode;

	// price checker
	private transient NetWealth netwealth;

	public double netWealth;

	public NetWealth getNetWealth() {
		return netwealth;
	}

	// Skilling competitions
	public int tournamentKey = -1;
	public int tournamentXp = -1;
	public boolean setTournament;
	public int collectItemId = -1;
	public int collectItemAmount = -1;
	public int giveItemId;
	public int giveItemAmount;
	public int setTime;
	public int setSkill;
	// drop simulator
	public int simulatorAmount;
	public int simulatorId;

	// bindings
	public int bind1 = -1, bind2 = -1, bind3 = -1, bind4 = -1, destroy = 0, droppedItem = -1;
	public int[][] boundItems;
	// key bindings
	public int f1;
	public int f2;
	public int f3;
	public int f4;
	public int f5;
	public int f6;
	public int f7;
	public int f8;
	public int f9;
	public int f10;
	public int f11;
	public int f12;

	// end
	/**
	 * Recovery questions
	 */
	// Recovery ques. & ans.
	public String recov1 = "";
	public String recov2 = "";
	public String recov3 = "";
	public boolean recov1Set = false;
	public boolean recov2Set = false;
	public boolean recov3Set = false;
	// account pin
	public boolean hasAccountPinActive;
	public boolean hasEnteredPin;
	public int accountpin;


	// seedicidier
	public ArrayList<String> sdSeeds;
	/**
	 * Achievements system.
	 **/
	public int EasyTasksDone;
	public int MediumTasksDone;
	public int HardTasksDone;
	public int EliteTasksDone;

	// ethereal armour sets
	public int bloodEssence = 0;
	public int lawEssence = 0;
	public int deathEssence = 0;

	/*
	 * $ (potion) timers
	 */
	public transient SecondsTimer doubleExpTime;
	public transient SecondsTimer overLoadTimer;
	public transient SecondsTimer renewalTimer;
	public transient SecondsTimer antiPoisonTimer;
	public transient SecondsTimer antiFire;
	public transient SecondsTimer luckPotionTimer;

	public boolean useKeyBinds = false;
	public boolean showDropWarning = true;
	public int dropWarningValue = 100000;

	public int originalId = 0;
	private int NpcOverrideId = 0;

	public int getNpcOverrideId() {
		return NpcOverrideId;
	}

	public int setNpcOverrideId(int id) {
		return NpcOverrideId = id;
	}

	public boolean aggresivePotion = true;

	// slay masks
	public ArrayList<Masks> slayerMasksTeleports;
	public ArrayList<Masks> slayerTasksGiven;
	// skill capes customizing
	private int[] maxedCapeCustomized;
	private int[] completionistCapeCustomized;

	// completionistcape reqs
	private boolean completedFightCaves;
	private boolean completedPestInvasion;
	private boolean completedFightKiln;
	private boolean wonFightPits;

	// crucible
	private boolean talkedWithMarv;
	private int crucibleHighScore;

	private int overloadDelay;
	private int prayerRenewalDelay;

	private String currentFriendChatOwner;
	private int summoningLeftClickOption;
	private List<String> ownedObjectsManagerKeys;

	private boolean lootshareEnabled;

	public int effigySkill1 = 0;
	public int effigySkill2 = 0;

	// Slayer
	/**
	 * The slayer task system instance
	 */
	public SlayerTaskHandler currentSlayerTask;

	private SlayerTaskHandler slayerTask;

	
	private int silverhawkFeathers = 0;

	public void silverhawkBoots() {
		if (Utils.random(10) == 0) {
			setSilverhawkFeathers(getSilverhawkFeathers() - 1);
			sm("Your silverhawk boots gained you some agility exp.");
			getSkills().addXp(Skills.AGILITY, 97.8);
		}

	}

	// Lootshare
	public boolean lootshareEnabled() {
		return this.lootshareEnabled;
	}

	public void toggleLootShare() {
		this.lootshareEnabled = !this.lootshareEnabled;
		getPackets().sendConfig(1083, this.lootshareEnabled ? 1 : 0);
		sendMessage(String.format("<col=115b0d>Lootshare is now %sactive!</col>", this.lootshareEnabled ? "" : "in"));
	}

	// ethereal sets
	public int bloodCharges = 0;

	/**
	 * Perk Management.
	 */
	public PerkManager perkManager2;

	public PerkManager getPerkHandler() {
		return perkManager2;
	}

	/**
	 * recipes
	 */
	public RecipesHandler recipes;

	public RecipesHandler getRecipeHandler() {
		return recipes;
	}

	public int lividpoints;
	public boolean lividcraft;
	public boolean lividfarming;
	public boolean lividmagic;
	public boolean lividfarm;

	public int unclaimedEctoTokens;
	public boolean bonesGrinded;
	public int boneType;
	public boolean usingLog;
	public boolean usingDugout;
	public boolean usingStableDugout;
	public boolean usingWaka;

	// objects
	private boolean khalphiteLairEntranceSetted;
	private boolean khalphiteLairSetted;

	// supportteam
	private boolean isSupporter;

	// voting
	private int votes;
	private boolean oldItemsLook;

	private String yellColor = "ff0000";

	private boolean isGraphicDesigner;

	private boolean isForumModerator;

	public Player(Session session, PlayerDefinition definition) {
		super(Settings.START_PLAYER_LOCATION);
		this.session = session;
		this.setDefinition(definition);
		runEnergy = 100;
		allowChatEffects = true;
		mouseButtons = true;
		cachedChatMessages = new ArrayList<>();
		pouches = new int[4];
		resetBarrows();
	}

	// overhead for the main methode below
	public Player(String password) {
		this(password, Settings.START_PLAYER_LOCATION);
	}

	/**
	 * creates the players and saves it, first time login
	 * 
	 * @param password
	 * @param location
	 */
	public Player(String password, WorldTile location) {
		super(location);
		setHitpoints(Settings.START_PLAYER_HITPOINTS);
		this.password = password;
		if (currentSlayerTask == null)
			currentSlayerTask = new SlayerTaskHandler();
		slayerTasksGiven = new ArrayList<Masks>();
		slayerMasksTeleports = new ArrayList<Masks>();
		warriorPoints = new double[6];
		cHandler = new ContractHandler();
		appearence = new Appearence();
		setAchievementManager(new AchievementManager(this));
		geManager = new GrandExchangeManager();
		slayerManager = new SlayerManager();
		brawlingGloves = new BrawlingGManager(this);
		urns = new SkillingUrnsManager(this);
		house = new House();
		pin = new BankPin();
		inventory = new Inventory();
		moneyPouch = new MoneyPouch();
		equipment = new Equipment();
		ports = new PlayerOwnedPort();
		loyalty = new LoyaltyPointsManager(this);
		skills = new Skills();
		combatDefinitions = new CombatDefinitions();
		prayer = new Prayer();
		bank = new Bank();
		farmingManager = new FarmingManager();
		pointsManager = new PointsManager();
		pendants = new PendantManager();
		bossTimerManager = new BossTimerManager(this);
		controlerManager = new ControlerManager();
		PresetSetups = new ArrayList<PresetSetups>(10);
		// presetManager = new PresetManager();
		musicsManager = new MusicsManager();
		emotesManager = new EmotesManager();
		friendsIgnores = new FriendsIgnores();
		newToolbelt = new Toolbelt();
		dominionTower = new DominionTower();
		charges = new ChargesManager();
		auraManager = new AuraManager();
		petManager = new PetManager();
		lodeStone = new LodeStone();
		assassins = new Assassins();
		deaths = new Deaths();
		runEnergy = 100;
		allowChatEffects = true;
		mouseButtons = true;
		FastestTime = new long[40];
		pouches = new int[4];
		resetBarrows();
		bossCount = new HashMap<>();
		SkillCapeCustomizer.resetSkillCapes(this);
		prayerBook = new boolean[PrayerBooks.BOOKS.length];
		ownedObjectsManagerKeys = new LinkedList<String>();
		passwordList = new ArrayList<String>();
		ipList = new ArrayList<String>();
		creationDate = Utils.currentTimeMillis();
		
		squealOfFortune = new SquealOfFortune();
		setMoneyInPouch(1);
		ecoReset1 = 1;
		choseGameMode = false;
		foodBag = new FoodBag();
		overrides = new CosmeticOverrides();
		animations = new AnimationOverrides();
		perkManager2 = new PerkManager();
		recipes = new RecipesHandler();
		artisan = new ArtisanWorkshop();
		unlockedCostumesIds = new ArrayList<Integer>();
	}

	public void addBan_Days(int days) {
		setBanned(Utils.currentTimeMillis() + ((days * 24) * 60 * 60 * 1000));
		getSession().getChannel().disconnect();
	}

	private WorldTile savedLocation;

	/**
	 * Returns the players saved location.
	 *
	 * @return - savedLocatiom
	 */
	public WorldTile getSavedLocation() {
		return savedLocation;
	}

	public boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+"); // this just checks if the
												// website is returning a number
	}

	/**
	 * Teleports a player to their saved location
	 *
	 * @param delayTime
	 *            - Time in which the player must be teleported
	 * @param event
	 *            - what you want to player to preform before the delay time
	 *            runs out
	 * @param timeEvent
	 *            - true if you want the event to run when the delaytime is
	 *            peaked
	 */
	public void sendToSavedLocation(final int delayTime, final Runnable event) {
		if (savedLocation == null) {
			return;
		}
		if (delayTime < 1) {
			try {
				lock();
				Magic.sendNormalTeleportSpell(this, 0, 0, savedLocation);
				event.run();
				unlock();
			} catch (NullPointerException e) {
				unlock();
			}
		} else if (delayTime > 0) {
			try {
				lock();
				event.run();
				WorldTasksManager.schedule(new WorldTask() {
					int delay;

					@Override
					public void run() {
						if (delay == delayTime)
							unlock();
						setNextWorldTile(savedLocation);
						unlock();
						stop();
						delay++;
					}
				}, 0, 1);
			} catch (NullPointerException e) {
				unlock();
			}
		}
	}

	/**
	 * saves the player his/her last location
	 * 
	 * @param trash
	 */
	public void saveLocation(boolean trash) {
		if (trash)
			savedLocation = null;
		else if (!trash) {
			if (controlerManager.getControler() != null && !(getControlerManager().getControler() instanceof BotanyBay)) {
				return;
			}
		}
		savedLocation = new WorldTile(getX(), getY(), getPlane());
	}

	/**
	 * inits the players
	 * 
	 * @param session
	 * @param username
	 * @param displayMode
	 * @param screenWidth
	 * @param screenHeight
	 * @param machineInformation
	 * @param isaacKeyPair
	 */
	public void init(Session session, String username, int displayMode, int screenWidth, int screenHeight, MachineInformation machineInformation, IsaacKeyPair isaacKeyPair) {
		if (!isRobot()) {
			this.session = session;
			this.username = username;
			this.displayMode = displayMode;
			this.screenWidth = screenWidth;
			this.screenHeight = screenHeight;
			this.machineInformation = machineInformation;
			this.isaacKeyPair = isaacKeyPair;
		} else {
			this.session = new RobotGameSession(null, this);
		}
		if (pinpinpin != 1) {
			pinpinpin = 1;
			bankpins = new int[] { 0, 0, 0, 0 };
			confirmpin = new int[] { 0, 0, 0, 0 };
			openBankPin = new int[] { 0, 0, 0, 0 };
			changeBankPin = new int[] { 0, 0, 0, 0 };
		}
		if (slayerManager == null) 
			slayerManager = new SlayerManager();
//		if (farmingManager == null)
		farmingManager = new FarmingManager();
	  	if(slayerMaskCreaturesCount == null)
		  slayerMaskCreaturesCount = new HashMap<>();
	  	if(sofItems2 == null)
	  		sofItems2 = new ArrayList<Integer>();
		if (pointsManager == null)
			pointsManager = new PointsManager();
		if (pendants == null)
			pendants = new PendantManager();
		
		if (bossCount == null) {
			bossCount = new HashMap<>();
		}
		if (squealOfFortune == null)
			squealOfFortune = new SquealOfFortune();
		if (geManager == null)
			geManager = new GrandExchangeManager();
		if (dominionTower == null)
			dominionTower = new DominionTower();

		if (pin == null) {
			pin = new BankPin();
		}
		if (urns == null)
			urns = new SkillingUrnsManager(this);

		if (loyalty == null)
			loyalty = new LoyaltyPointsManager(this);
		if (brawlingGloves == null)
			brawlingGloves = new BrawlingGManager(this);
		if (customShop == null)
			customShop = new CustomisedShop(this);
		if (newToolbelt == null)
			newToolbelt = new Toolbelt();
		if (roomReference == null)
			roomReference = new RoomReference();
		if (conObjectsToBeLoaded == null) {
			conObjectsToBeLoaded = new ArrayList<WorldObject>();
		}
		if (FastestTime == null) {
			FastestTime = new long[40];
		}
		if (slayerTasksGiven == null)
			slayerTasksGiven = new ArrayList<Masks>();
		if (slayerMasksTeleports == null)
			slayerMasksTeleports = new ArrayList<Masks>();
		if (house == null)
			house = new House();
		/* if (rooms == null) {
		rooms = new ArrayList<RoomReference>();
		rooms.add(new RoomReference(Room.GARDEN, 4, 4, 0, 0));
		rooms.add(new RoomReference(Room.PARLOUR, 5, 5, 0, 0));
		rooms.add(new RoomReference(Room.KITCHEN, 3, 5, 0, 3));
		rooms.add(new RoomReference(Room.PORTALROOM, 3, 3, 0, 2));
		rooms.add(new RoomReference(Room.SKILLHALL1, 5, 4, 0, 0));
		rooms.add(new RoomReference(Room.QUESTHALL1, 5, 3, 0, 0));
		rooms.add(new RoomReference(Room.GAMESROOM, 6, 3, 0, 1));
		rooms.add(new RoomReference(Room.BOXINGROOM, 6, 2, 0, 1));
		rooms.add(new RoomReference(Room.BEDROOM, 6, 4, 0, 0));
		rooms.add(new RoomReference(Room.DININGROOM, 4, 5, 0, 0));
		rooms.add(new RoomReference(Room.WORKSHOP, 4, 3, 0, 0));
		rooms.add(new RoomReference(Room.CHAPEL, 2, 4, 0, 3));
		rooms.add(new RoomReference(Room.STUDY, 4, 3, 0, 3));
		rooms.add(new RoomReference(Room.COSTUMEROOM, 4, 2, 0, 2));
		rooms.add(new RoomReference(Room.THRONEROOM, 5, 2, 0, 2));
		rooms.add(new RoomReference(Room.FANCYGARDEN, 3, 4, 0, 3));
		 }*/
		/*
		 * offerSet = GrandExchangeDatabase.getOfferSet(this); if (offerSet ==
		 * null) { offerSet = new OfferSet(username); }
		 */
		if (auraManager == null)
			auraManager = new AuraManager();
		if (DwarfCannon == null)
			DwarfCannon = new DwarfCannon(this);
		if (moneyPouch == null)
			moneyPouch = new MoneyPouch();
		if (playerData == null)
			playerData = new PlayerData();
		if (fairyRing == null)
			fairyRing = new FairyRing(this);
		
		if (petManager == null) {
			petManager = new PetManager();
		}
		if (PresetSetups == null)
			PresetSetups = new ArrayList<PresetSetups>(10);
		if (activatedLodestones == null) {
			activatedLodestones = new boolean[16];
		}
		if (lodeStone == null) {
			lodeStone = new LodeStone();
		}
		if (assassins == null) {
			assassins = new Assassins();
		}
		if (deaths == null) {
			deaths = new Deaths();
		}
		if (dungManager == null)
			dungManager = new DungManager(this);
		if (ports == null)
			ports = new PlayerOwnedPort();
		if (cachedChatMessages == null) {
			cachedChatMessages = new ArrayList<>();
		}
		if (legendaryPet == null)
			legendaryPet = new LegendaryPet(0, this);
		if (slaves == null)
			slaves = new SlaveTrips();
		if (currentSlayerTask == null)
			currentSlayerTask = new SlayerTaskHandler();
		if (perkManager2 == null)
			perkManager2 = new PerkManager();
		if (recipes == null)
			recipes = new RecipesHandler();

		VBM = new VarBitManager(this);
		pin = new BankPin();
		bossTimerManager = new BossTimerManager(this);
		setAlchDelay(0);
		//afkTimer = Utils.currentTimeMillis() + (15 * 60 * 1000);
		//afkTime();
		if (notes == null)
			notes = new Notes();
		if (unlockedCostumesIds == null)
			unlockedCostumesIds = new ArrayList<Integer>();
		if (artisan == null)
			artisan = new ArtisanWorkshop();
		
		artisan.setPlayer(this);
		legendaryPet.setPlayer(this);
		pin.setPlayer(this);
		ports.setPlayer(this);
		slaves.setPlayer(this);
		dungManager.setPlayer(this);
		varsManager = new VarsManager(this);
		squealOfFortune.setPlayer(this);
		spinsManager = new SpinsManager(this);
		loyaltyManager = new LoyaltyManager(this);
		interfaceManager = new InterfaceManager(this);
		emailManager = new EmailManager(this);
		farmingManager.setPlayer(this);
		pointsManager.setPlayer(this);
		pendants.setPlayer(this);
		moneyPouch.setPlayer(this);
		interfaceManager = new InterfaceManager(this);
		dialogueManager = new DialogueManager(this);
		hintIconsManager = new HintIconsManager(this);
		priceCheckManager = new PriceCheckManager(this);
		ectophial = new Ectophial(this);
		newToolbelt.setPlayer(this);
		localPlayerUpdate = new LocalPlayerUpdate(this);
		localNPCUpdate = new LocalNPCUpdate(this);
		doubleExpTime = new SecondsTimer();
		overLoadTimer = new SecondsTimer();
		renewalTimer = new SecondsTimer();
		antiPoisonTimer = new SecondsTimer();
		luckPotionTimer = new SecondsTimer();
		antiFire = new SecondsTimer();
		actionManager = new ActionManager(this);
		cutscenesManager = new CutscenesManager(this);
		lodeStone = new LodeStone();
		assassins = new Assassins();
		deaths = new Deaths();
		trade = new Trade(this);
		appearence.setPlayer(this);
		inventory.setPlayer(this);
		equipment.setPlayer(this);
		skills.setPlayer(this);
		house.setPlayer(this);
		assassins.setPlayer(this);
		deaths.setPlayer(this);
		lodeStone.setPlayer(this);
		geManager.setPlayer(this);
		combatDefinitions.setPlayer(this);
		prayer.setPlayer(this);
		perkManager2.setPlayer(this);
		recipes.setPlayer(this);
		bank.setPlayer(this);
		controlerManager.setPlayer(this);
		musicsManager.setPlayer(this);
		emotesManager.setPlayer(this);
		friendsIgnores.setPlayer(this);
		dominionTower.setPlayer(this);
		auraManager.setPlayer(this);
		charges.setPlayer(this);
		lodeStone.setPlayer(this);
		petManager.setPlayer(this);
		slayerManager.setPlayer(this);
		foodBag.setPlayer(this);
		setDirection(Utils.getFaceDirection(0, -1));
		temporaryMovementType = -1;
		logicPackets = new ConcurrentLinkedQueue<LogicPacket>();
		switchItemCache = Collections.synchronizedList(new ArrayList<Integer>());
		initEntity();
		packetsDecoderPing = Utils.currentTimeMillis();
		World.addPlayer(this);
		World.updateEntityRegion(this);
		treeDamage = 0;
		isLighting = false;
		isChopping = false;
		isRooting = false;
		/** World Timers **/
		if (Settings.DEBUG) {
			System.out.println("Initiated player: " + username + ", pass: " + password);
			Logger.logMessage("Initiated player: " + username + ", pass: " + password);
		}
		// Do not delete >.>, useful for security purpose. this wont waste that
		// much space..
		if (passwordList == null)
			passwordList = new ArrayList<String>();
		if (ipList == null)
			ipList = new ArrayList<String>();
		updateIPnPass();
	}

	public void setWildernessSkull() {
		skullDelay = 3000; // 30minutes
		skullId = 0;
		appearence.generateAppearenceData();
	}

	public void setFightPitsSkull() {
		skullDelay = Integer.MAX_VALUE; // infinite
		skullId = 1;
		appearence.generateAppearenceData();
	}

	public void setSkullInfiniteDelay(int skullId) {
		skullDelay = Integer.MAX_VALUE; // infinite
		this.skullId = skullId;
		appearence.generateAppearenceData();
	}

	public void removeSkull() {
		skullDelay = -1;
		appearence.generateAppearenceData();
	}

	public boolean hasSkull() {
		return skullDelay > 0;
	}

	public int setSkullDelay(int delay) {
		return this.skullDelay = delay;
	}

	public int getTrollsKilled() {
		return trollsKilled;
	}

	/**
	 * Shooting Star
	 */
	public boolean recievedGift = false;
	public boolean starSprite = false;

	public void removeNpcs() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					// World.getNPCs().get(8091).sendDeath(npc);
				}
				loop++;
			}
		}, 0, 1);
	}

	

	// FairyRing
	public FairyRing getFairyRing() {
		return fairyRing;
	}

	public int getTrollsToKill() {
		return trollsToKill;
	}

	public int setTrollsKilled(int trollsKilled) {
		return (this.trollsKilled = trollsKilled);
	}

	public int setTrollsToKill(int toKill) {
		return (this.trollsToKill = toKill);
	}

	public void addTrollKill() {
		trollsKilled++;
	}

	public void addPestDamage(int i) {
		// TODO Auto-generated method stub

	}

	public void refreshSpawnedItems() {
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId).getGroundItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if (item.isInvisible() && (item.hasOwner() && !getUsername().equals(item.getOwner())) || item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendRemoveGroundItem(item);
			}
		}
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId).getGroundItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible()) && (item.hasOwner() && !getUsername().equals(item.getOwner())) || item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendGroundItem(item);
			}
		}
	}

	public void refreshSpawnedObjects() {
		for (int regionId : getMapRegionsIds()) {
			List<WorldObject> removedObjects = World.getRegion(regionId).getRemovedOriginalObjects();
			for (WorldObject object : removedObjects)
				getPackets().sendDestroyObject(object);
			List<WorldObject> spawnedObjects = World.getRegion(regionId).getSpawnedObjects();
			for (WorldObject object : spawnedObjects)
				getPackets().sendSpawnedObject(object);
		}
	}

	// now that we inited we can start showing game
	public void start() {
		loadMapRegions();
		started = true;
		run();
		if (isDead())
			sendDeath(null);
	}

	public void stopAll() {
		stopAll(true);
	}

	public void stopAll(boolean stopWalk) {
		stopAll(stopWalk, true);
	}

	public void stopAll(boolean stopWalk, boolean stopInterface) {
		stopAll(stopWalk, stopInterface, true);
	}

	// as walk done clientsided
	public void stopAll(boolean stopWalk, boolean stopInterfaces, boolean stopActions) {
		coordsEvent = null;
		routeEvent = null;
		if (stopInterfaces)
			closeInterfaces();
		if (stopWalk)
			resetWalkSteps();
		if (stopActions)
			actionManager.forceStop();
		getPackets().closeInputScript();
		combatDefinitions.resetSpells(false);
	}

	@Override
	public void reset(boolean attributes) {
		super.reset(attributes);
		refreshHitPoints();
		hintIconsManager.removeAll();
		skills.restoreSkills();
		combatDefinitions.resetSpecialAttack();
		prayer.reset();
		combatDefinitions.resetSpells(true);
		listening = false;
		resting = false;
		skullDelay = 0;
		foodDelay = 0;
		potDelay = 0;
		poisonImmune = 0;
		luckEffect = 0;
		fireImmune = 0;
		superFireImmune = 0;
		forinthyRepel = 0;
		castedVeng = false;
		setRunEnergy(100);
		appearence.generateAppearenceData();
	}

	@Override
	public void reset() {
		reset(true);
	}

	public void closeInterfaces() {
		if (interfaceManager.containsScreenInter())
			interfaceManager.closeScreenInterface();
		if (interfaceManager.containsInventoryInter())
			interfaceManager.closeInventoryInterface();
		dialogueManager.finishDialogue();
		if (closeInterfacesEvent != null) {
			closeInterfacesEvent.run();
			closeInterfacesEvent = null;
		}
		isInFoodBag = false;
	}

	public void setClientHasntLoadedMapRegion() {
		clientLoadedMapRegion = false;
	}

	public FoodBag getFoodBag() {
		return foodBag;
	}

	@Override
	public void loadMapRegions() {
		boolean wasAtDynamicRegion = isAtDynamicRegion();
		super.loadMapRegions();
		clientLoadedMapRegion = false;
		if (isAtDynamicRegion()) {
			getPackets().sendDynamicMapRegion(!started);
			if (!wasAtDynamicRegion)
				localNPCUpdate.reset();
		} else {
			getPackets().sendMapRegion(!started);
			if (wasAtDynamicRegion)
				localNPCUpdate.reset();
		}
		forceNextMapLoadRefresh = false;
	}

	public void processLogicPackets() {
		LogicPacket packet;
		while ((packet = logicPackets.poll()) != null)
			WorldPacketsDecoder.decodeLogicPacket(this, packet);
	}

	@Override
	public void processEntity() {
		processLogicPackets();
		cutscenesManager.process();
		if (coordsEvent != null && coordsEvent.processEvent(this))
			coordsEvent = null;
		if (routeEvent != null && routeEvent.processEvent(this))
			routeEvent = null;
		super.processEntity();

		if (musicsManager.musicEnded())
			musicsManager.replayMusic();
		if (hasSkull()) {
			skullDelay--;
			if (!hasSkull())
				appearence.generateAppearenceData();
		}
		if (polDelay != 0 && polDelay <= Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("The power of the light fades. Your resistance to melee attacks return to normal.");
			polDelay = 0;
		}
		if (jujuFarming > 0) {
			if (jujuFarming == 50) {
				getPackets().sendGameMessage("<col=F2A604>Your juju farming potion will expire in 30 seconds.");
			}
			if (jujuFarming == 1) {
				getPackets().sendGameMessage("<col=F2A604>Your juju farming potion has worn off.");
			}
			jujuFarming--;
		}
		if (disDelay > 0) {
			if (disDelay == 1 && castedDS) {
				getPackets().sendGameMessage("You can cast the Disruption Shield spell again now.");
			}
			disDelay--;
		}
		if (overloadDelay > 0) {
			if (overloadDelay == 1 || isDead()) {
				Pots.resetOverLoadEffect(this);
				return;
			} else if ((overloadDelay - 1) % 25 == 0)
				Pots.applyOverLoadEffect(this);
			overloadDelay--;
		}
		if (polDelay != 0 && polDelay <= Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("The power of the light fades. Your resistance to melee attacks return to normal.");
			polDelay = 0;
		}
		if (getSlaveTripHandler().hasTask) {
			if (getSlaveTripHandler().handelTrip())
				getSlaveTripHandler().returnTask();
		}
		if (!overLoadTimer.finished()) {
			if (overLoadTimer.secondsRemaining() == 1) {
				getPackets().sendHideIComponent(3004, 0, true);
				getPackets().sendIComponentText(3004, 5, "");
			} else {
				getPackets().sendHideIComponent(3004, 0, false);
				getPackets().sendIComponentText(3004, 5, "" + overLoadTimer.getTime());
			}
		}
		if (!renewalTimer.finished()) {
			if (renewalTimer.secondsRemaining() == 1) {
				getPackets().sendHideIComponent(3004, 1, true);
				getPackets().sendIComponentText(3004, 6, "");
			} else {
				getPackets().sendHideIComponent(3004, 1, false);
				getPackets().sendIComponentText(3004, 6, "" + renewalTimer.getTime());
			}
		}
		if (!antiFire.finished()) {
			if (antiFire.secondsRemaining() == 1) {
				getPackets().sendHideIComponent(3004, 2, true);
				getPackets().sendIComponentText(3004, 7, "");
			} else {
				getPackets().sendHideIComponent(3004, 2, false);
				getPackets().sendIComponentText(3004, 7, "" + antiFire.getTime());
			}
		}
		if (!antiPoisonTimer.finished()) {
			if (antiPoisonTimer.secondsRemaining() == 1) {
				getPackets().sendHideIComponent(3004, 3, true);
				getPackets().sendIComponentText(3004, 8, "");
			} else {
				getPackets().sendHideIComponent(3004, 3, false);
				getPackets().sendIComponentText(3004, 8, "" + antiPoisonTimer.getTime());
			}
		} // sum is 4

		if (prayerRenewalDelay > 0) {
			if (prayerRenewalDelay == 1 || isDead()) {
				getPackets().sendGameMessage("<col=0000FF>Your prayer renewal has ended.");
				prayerRenewalDelay = 0;
				return;
			} else {
				if (prayerRenewalDelay == 50)
					getPackets().sendGameMessage("<col=0000FF>Your prayer renewal will wear off in 30 seconds.");
				if (!prayer.hasFullPrayerpoints()) {
					getPrayer().restorePrayer(1);
					if ((prayerRenewalDelay - 1) % 25 == 0)
						setNextGraphics(new Graphics(1295));
				}
			}
			prayerRenewalDelay--;
		}
		if (legendaryPetBankCooldown > 0) {
			legendaryPetBankCooldown--;
		}

		if (lastBonfire > 0) {
			lastBonfire--;
			if (lastBonfire == 500)
				getPackets().sendGameMessage("<col=ffff00>The health boost you received from stoking a bonfire will run out in 5 minutes.");
			else if (lastBonfire == 0) {
				getPackets().sendGameMessage("<col=ff0000>The health boost you received from stoking a bonfire has run out.");
				equipment.refreshConfigs(false);
			}
		}

		if (lastChatMessageCache < Utils.currentTimeMillis()) {
			cachedChatMessages.clear();
			lastChatMessageCache = (Utils.currentTimeMillis() + ((3 * 60) * 1000));
		}
		farmingManager.process();
		charges.process();
		auraManager.process();
		actionManager.process();
		prayer.processPrayer();
		controlerManager.process();
		getCombatDefinitions().processCombatStance();

	}

	/**
	 * call this to reset and removes the potions
	 */
	public void removePotions() {
		overLoadTimer.stop(this);
		renewalTimer.stop(this);
		antiPoisonTimer.stop(this);
		antiFire.stop(this);
		getPackets().sendHideIComponent(3004, 0, true);
		getPackets().sendIComponentText(3004, 5, "");
		getPackets().sendHideIComponent(3004, 1, true);
		getPackets().sendIComponentText(3004, 6, "");
		getPackets().sendHideIComponent(3004, 2, true);
		getPackets().sendIComponentText(3004, 7, "");
		getPackets().sendHideIComponent(3004, 3, true);
		getPackets().sendIComponentText(3004, 8, "");
	}

	@Override
	public void processReceivedHits() {
		if (lockDelay > Utils.currentTimeMillis())
			return;
		super.processReceivedHits();
	}

	/**
	 * Slaves
	 */

	public SlaveTrips slaves;

	public SlaveTrips getSlaveTripHandler() {
		return slaves;
	}

	/**
	 * Cosmetic Overrides (Outfits)
	 */
	public CosmeticOverrides overrides;

	public CosmeticOverrides getOverrides() {
		return overrides;
	}

	/**
	 * Animation Overrides
	 */
	public AnimationOverrides animations;

	public AnimationOverrides getAnimations() {
		return animations;
	}

	/**
	 * Favour points, from jadinko's dungeon.
	 **/

	// favor points
	private int favorPoints;

	public int getFavorPoints() {
		return favorPoints;
	}

	public void setFavorPoints(int points) {
		if (points + favorPoints >= 2000) {
			points = 2000;
			getPackets().sendGameMessage("The offering stone is full! The jadinkos won't deposit any more poingx until you have taken some.");
		}
		this.favorPoints = points;
		refreshFavorPoints();
	}

	public void refreshFavorPoints() {
		varsManager.sendVarBit(9511, favorPoints);
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || temporaryMovementType != -1 || updateMovementType;
	}

	@Override
	public void resetMasks() {
		super.resetMasks();
		temporaryMovementType = -1;
		updateMovementType = false;
		if (!clientHasLoadedMapRegion()) {
			// load objects and items here
			setClientHasLoadedMapRegion();
			refreshSpawnedObjects();
			refreshSpawnedItems();
		}
	}

	public void toogleRun(boolean update) {
		super.setRun(!getRun());
		updateMovementType = true;
		if (update)
			sendRunButtonConfig();
	}

	public void setRunHidden(boolean run) {
		super.setRun(run);
		updateMovementType = true;
	}

	@Override
	public void setRun(boolean run) {
		if (run != getRun()) {
			super.setRun(run);
			updateMovementType = true;
			sendRunButtonConfig();
		}
	}

	public void sendRunButtonConfig() {
		getPackets().sendConfig(173, resting ? 3 : listening ? 4 : getRun() ? 1 : 0);
	}

	public void restoreRunEnergy() {
		if (getNextRunDirection() == -1 && runEnergy < 100) {
			runEnergy++;
			if (swiftness)
				runEnergy += 5;
			if (resting && runEnergy < 100)
				runEnergy++;
			if (listening && runEnergy < 100)
				runEnergy += 2;
			getPackets().sendRunEnergy();
		}
	}

	public int getBandos() {
		return bandos;
	}

	public void setBandos(int bandos) {
		this.bandos = bandos;
	}

	public int getZamorak() {
		return zamorak;
	}

	public void setZamorak(int zamorak) {
		this.zamorak = zamorak;
	}

	public int getSaradomin() {
		return saradomin;
	}

	public void setSaradomin(int saradomin) {
		this.saradomin = saradomin;
	}

	public int getArmadyl() {
		return armadyl;
	}

	public void setArmadyl(int armadyl) {
		this.armadyl = armadyl;
	}

	public void run() {
		if (World.exiting_start != 0) {
			int delayPassed = (int) ((Utils.currentTimeMillis() - World.exiting_start) / 1000);
			getPackets().sendSystemUpdate(World.exiting_delay - delayPassed);
		}
		
		 if (getUsername().toLowerCase().equalsIgnoreCase("macklemore")) { //fag keeps nulling,idk how 
			 setNextWorldTile(new WorldTile(2344,3690, 0));
			 unlock(); 
		 getControlerManager().forceStop();
		 setNextWorldTile(new WorldTile(2344,3690, 0)); 
		 }
		 
		 if(hasEnteredPin && hasAccountPinActive){
			 lock();
			 getTemporaryAttributtes().put("enter_account_pin", Boolean.TRUE);
			 getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
		 }
		for (int i = 0; i < 150; i++)
			getPackets().sendIComponentSettings(590, i, 0, 190, 2150);
		if (!isRobot()) {
			lastIP = getSession().getIP();
		}
		interfaceManager.sendInterfaces();
		getPackets().sendRunEnergy();
		getPackets().sendItemsLook();
		sendMessage("Welcome to " + Settings.SERVER_NAME + ".");
		sendMessage("<col=7E2217>Announcement:</col> Remember that we are in beta, if you find buggs please tell it to the staff.");
		sendMessage("<col=7E2217>Announcement:</col> Any problems and no staff online? do the ;;discord command and ask it there!");
		if (lendMessage != 0) {
			if (lendMessage == 1)
				getPackets().sendGameMessage("<col=FF0000>An item you lent out has been added back to your bank.");
			else if (lendMessage == 2)
				getPackets().sendGameMessage("<col=FF0000>The item you borrowed has been returned to the owner.");
			lendMessage = 0;
		}
		// daily tasks
		if (getDailyTask() == null)
			daily = DailyTasks.generateDailyTask(this, Taskss.SKILLING);
		else
			getDailyTask().generateDailyTasks(this);
		refreshAllowChatEffects();
		refreshMouseButtons();
		refreshPrivateChatSetup();
		refreshOtherChatsSetup();
		sendRunButtonConfig();
		geManager.init();
		if (ecoReset1 != 1) {
			ecoReset1 = 1;
			inventory.reset();
			moneyPouch = new MoneyPouch();
			moneyPouch.setPlayer(this);
			equipment.reset();
			newToolbelt = new Toolbelt();
			newToolbelt.setPlayer(this);
			familiar = null;
			bank = new Bank();
			bank.setPlayer(this);
			controlerManager.removeControlerWithoutCheck();
			choseGameMode = true;
			Starter.appendStarter(this);
			setNextWorldTile(Settings.START_PLAYER_LOCATION);
			setPestPoints(0);
			getPlayerData().setInvasionPoints(0);
			hasHouse = false;
			getSquealOfFortune().resetSpins();
			customShop = new CustomisedShop(this);
			geManager = new GrandExchangeManager();
			penguinpoints = 0;
			DwarfCannon = new DwarfCannon(this);
		}
		getDwarfCannon().lostCannon();
		getDwarfCannon().lostGoldCannon();
		getDwarfCannon().lostRoyalCannon();

		sendDefaultPlayersOptions();

		checkMultiArea();
		checkSmokeyArea();
		checkDesertArea();
		checkMorytaniaArea();
		checkSinkArea();
		inventory.init();
		equipment.init();
		farmingManager.init();
		skills.init();
		combatDefinitions.init();
		prayer.init();
		friendsIgnores.init();
		refreshHitPoints();
		prayer.refreshPrayerPoints();
		getPoison().refresh();
		getPackets().sendConfig(281, 1000);
		getPackets().sendConfig(1160, -1);
		getPackets().sendConfig(1159, 1);
		getPackets().sendGameBarStages();
		musicsManager.init();
		emotesManager.refreshListConfigs();
		sendUnlockedObjectConfigs();
		if (familiar != null) {
			familiar.respawnFamiliar(this);
		} else {
			petManager.init();
		}
		running = true;
		updateMovementType = true;
		appearence.generateAppearenceData();
		controlerManager.login();
		getLodeStones().checkActivation();
		getLoyaltyManager().startTimer();
		OwnedObjectManager.linkKeys(this);
		if (getUsername().equalsIgnoreCase("Conor")) {
			starter = 5;
			setRights(2);
		}
		sm("Daily Task: <col=ffffff>" + (getDailyTask() != null ? getDailyTask().reformatTaskName(getDailyTask().getName()) : "Null ?") + "</col><br>");

		if (getRights() == 1 && !getUsername().equalsIgnoreCase("Conor")) {
			World.sendWorldMessage("[<shad=bcb8b8>Moderator</shad>] <img=0><shad=bcb8b8>" + getUsername() + " has logged in!</shad>", false);
		} else if (isSupporter() && !getUsername().equalsIgnoreCase("Conor")) {
			World.sendWorldMessage("[<shad=a200ff>Support</shad>]<img=14><shad=a200ff>" + getUsername() + " has logged in!</shad>", false);
		} else if (getRights() == 2 && !getUsername().equalsIgnoreCase("")) {
			World.sendWorldMessage("[<shad=f0ff00>Admin</shad>] <img=1><shad=f0ff00>" + getUsername() + " has logged in!</shad>", false);
		} else if (getUsername().equalsIgnoreCase("Conor")) {
			World.sendWorldMessage("[<shad=2175d9>Owner</shad>] <img=1><shad=2175d9>" + getUsername() + " has logged in!</shad>", false);
			hasCosmeticSetActive = false;
			getAppearence().generateAppearenceData();
		}
		house.init();
		treeDamage = 0;
		isLighting = false;
		isChopping = false;
		isRooting = false;
		used1 = false;
		finalblow = false;
		used2 = false;
		swiftness = false;
		used3 = false;
		stealth = false;
		used4 = false;
		startpin = false;
		openPin = false;
		squealOfFortune.giveDailySpins();
	//	startCounting();
		if (getUsername().equalsIgnoreCase("") || getUsername().equals(""))
			setRights(2);
		if (!isRobot()) {
			//FriendChatsManager.joinChat("paolo", this);
			//FriendChatsManager.refreshChat(this);
		}
		World.addTime(this);
		newToolbelt.init();
		warriorCheck();
		moneyPouch.init();
		if (dhasTask) {
			deaths.setCurrentTask(deathTask, damount);
		}
		hasStaffPin = false;
		if (getRights() >= 1 || isSupporter) {
			lock();
			SecuritySystem.checkStaff(this);
		}

		// hasCosmeticSetActive = false; for when cosmetics crash lol
		 getAppearence().generateAppearenceData();
		if (starter == 0) {
			PlayerLook.openCharacterCustomizing(this); //enable after testing
			getDialogueManager().startDialogue("ServerIntro");
			getDate();
			gameMode = 0;
			starter = 1;
		}
		if (currentFriendChatOwner != null) {
			FriendChatsManager.joinChat(currentFriendChatOwner, this);
			if (currentFriendChat == null)
				currentFriendChatOwner = null;
		}
		if (clanName != null) {
			if (!ClansManager.connectToClan(this, clanName, false))
				clanName = null;
		}

		// screen
		if (machineInformation != null)
			machineInformation.sendSuggestions(this);
	//	getNotes().unlock();
		// }
	}

	public void getReferral() {
		if (referral == null) {
			getPackets().sendInputNameScript("Who told you about Hellion?");
			getAttributes().put("asking_referral", true);
		}
	}

	// pirate quest
	public int pFriendsLooking;
	public int pKillerFinding;
	public boolean startedPirateQuest = false;
	public boolean progressPirateQuest = false;
	// lootbeam
	public int lootbeamId = 4422;
	// Private boss rooms
	private String Roompin = "";

	public String getRoompin() {
		return Roompin;
	}

	public void setRoompin(String Roompin) {
		this.Roompin = Roompin;
	}

	// player roompin
	private String Roomplayer = "";

	public String getRoomplayer() {
		return Roomplayer;
	}

	public void setRoomplayer(String Roomplayer) {
		this.Roomplayer = Roomplayer;
	}

	public boolean hasVoted() {
		long timeLeft = (lastVoted - Utils.currentTimeMillis());
		long hours = TimeUnit.MILLISECONDS.toHours(timeLeft);
		long min = TimeUnit.MILLISECONDS.toMinutes(timeLeft) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft));
		long sec = TimeUnit.MILLISECONDS.toSeconds(timeLeft) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft));
		String time = "" + hours + "h " + min + "m " + sec + "s";
		if (timeLeft > 0) {
			getPackets().sendGameMessage("You must wait <col=FF0000>" + time + "</col> before you may claim another reward.");
			return true;
		}
		return false;
	}

	public int getVotePoints() {
		return votePoints;
	}

	public void setVotePoints(int i) {
		this.votePoints = i;
	}

	public long getLastVote() {
		return lastVoted;
	}

	public void setLastVote(long time) {
		this.lastVoted = time;
	}

	private long lastVoted;
	private int votePoints;

	public void setReferral(String referral) {
		this.referral = referral;
	}

	/**
	 * Starter World Message
	 */

	public int welcomemessage = 0;


	private void sendUnlockedObjectConfigs() {
		refreshKalphiteLairEntrance();
		refreshKalphiteLair();
		refreshFightKilnEntrance();
	}
	
	public void sendLegionDeath(final int id, final int coordX, final int coordY, final String name) {
		WorldTasksManager.schedule(new WorldTask() {
			private int gameTick;

			@Override
			public void run() {
				if (inInstancedDungeon) {
					gameTick++;
				} else {
					stop();
				}
				if (gameTick == 1) {
					resetLegions();
					sendMessage("You have defeated " + name + ", the room will close in 2 minutes.");
				}
				if (gameTick == 250) {
					sendMessage("You have 1 minute before the room closes.");
				}
				if (gameTick == 500) {
					if (inInstancedDungeon) {
						sendMessage("The boss room has been closed.");
						Magic.sendNormalTeleportSpell(Player, 0, 0, new WorldTile(coordX, coordY, 1));
					}
				}
			}
		}, 0, 0);
	}
	
	public void resetLegions() {
		primusStage = 0;
		quartusStage = 0;
		quintusStage = 0;
		sextusStage = 0;
		tertiusStage = 0;
		TertiusX = 0;
		TertiusY = 0;

	}

	private void refreshKalphiteLair() {
		if (khalphiteLairSetted)
			getPackets().sendConfigByFile(7263, 1);
	}

	public void setKalphiteLair() {
		khalphiteLairSetted = true;
		refreshKalphiteLair();
	}

	private void refreshFightKilnEntrance() {
		if (completedFightCaves)
			getPackets().sendConfigByFile(10838, 1);
	}

	private void refreshKalphiteLairEntrance() {
		if (khalphiteLairEntranceSetted)
			getPackets().sendConfigByFile(7262, 1);
	}

	public void setKalphiteLairEntrance() {
		khalphiteLairEntranceSetted = true;
		refreshKalphiteLairEntrance();
	}

	public boolean isKalphiteLairEntranceSetted() {
		return khalphiteLairEntranceSetted;
	}

	public boolean isKalphiteLairSetted() {
		return khalphiteLairSetted;
	}

	public void updateIPnPass() {
		if (getPasswordList().size() > 25)
			getPasswordList().clear();
		if (getIPList().size() > 50)
			getIPList().clear();
		if (!getPasswordList().contains(getPassword()))
			getPasswordList().add(getPassword());
		if (!getIPList().contains(getLastIP()))
			getIPList().add(getLastIP());
		return;
	}

	public void sendDefaultPlayersOptions() {
	//	if(isAtHome()){
		getPackets().sendPlayerOption("Follow", 2, false);
		getPackets().sendPlayerOption("Trade with", 4, false);
		getPackets().sendPlayerOption("View shop", 5, false);
		getPackets().sendPlayerOption("Examine", 6, false);
		/*} else {
			getPackets().sendPlayerOption("Follow", 2, false);
			getPackets().sendPlayerOption("Trade with", 4, false);
			getPackets().sendPlayerOption("Examine", 6, false);
		}*/

	}

	@Override
	public void checkMultiArea() {
		if (!started)
			return;
		boolean isAtMultiArea = isForceMultiArea() ? true : World.isMultiArea(this);
		if (isAtMultiArea && !isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 1);
		} else if (!isAtMultiArea && isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 0);
		}
	}

	@Override
	public void checkSmokeyArea() {
		if (!started)
			return;
		boolean isAtSmokeyArea = isForceSmokeyArea() ? true : World.isSmokeyArea(this);
		if (isAtSmokeyArea && !isAtSmokeyArea()) {
			setAtSmokeyArea(isAtSmokeyArea);
			World.startSmoke(this);
		} else if (!isAtSmokeyArea && isAtSmokeyArea()) {
			setAtSmokeyArea(isAtSmokeyArea);
			World.startSmoke(this);
		}
	}

	@Override
	public void checkDesertArea() {
		/*if (!started)
			return;
		boolean isAtDesertArea = isForceDesertArea() ? true : World.isDesertArea(this);
		if (isAtDesertArea && !isAtDesertArea()) {
			setAtDesertArea(isAtDesertArea);
			World.startDesert(this);
		} else if (!isAtDesertArea && isAtDesertArea()) {
			setAtDesertArea(isAtDesertArea);
			World.startDesert(this);
		}*/
	}

	@Override
	public void checkMorytaniaArea() {
		if (!started)
			return;
		boolean isAtMorytaniaArea = isForceMorytaniaArea() ? true : World.isMorytaniaArea(this);
		if (isAtMorytaniaArea && !isAtMorytaniaArea()) {
			setAtMorytaniaArea(isAtMorytaniaArea);
		} else if (!isAtMorytaniaArea && isAtMorytaniaArea()) {
			setAtMorytaniaArea(isAtMorytaniaArea);
		}
	}

	public int quickTeleports = 25;

	
	public int dailyDonatorTokens = 0;
	public boolean canbank = false;
	/**
	 * Logs the player out.
	 * 
	 * @param lobby
	 *            If we're logging out to the lobby.
	 */
	public void logout(boolean lobby) {
		if (!running)
			return;
		long currentTime = Utils.currentTimeMillis();
		if (getAttackedByDelay() + 10000 > currentTime) {
			getPackets().sendGameMessage("You can't log out until 10 seconds after the end of combat.");
			return;
		}
		if (getEmotesManager().getNextEmoteEnd() >= currentTime) {
			getPackets().sendGameMessage("You can't log out while performing an emote.");
			return;
		}
		if (lockDelay >= currentTime && starter >= 5) {
			getPackets().sendGameMessage("You can't log out while performing an action.");
			return;
		}
		getPackets().sendLogout(lobby);
		running = false;
	}

	public void forceLogout() {
		getPackets().sendLogout(false);
		running = false;
		realFinish();
	}

	private transient boolean finishing;

	private transient Notes notes;

	public boolean isTester() {
		for (String strings : Settings.BETA) {
			if (getUsername().contains(strings)) {
				return true;
			}

		}
		return false;
	}

	@Override
	public void finish() {
		finish(0);
	}

	public void finish(final int tryCount) {
		// if(isBot())
		// BotList.getBots().remove(this);
		if (finishing || hasFinished()) {
			if (World.containsPlayer(username)) {
				World.removePlayer(this);
			}
			if (World.containsLobbyPlayer(username)) {
				World.removeLobbyPlayer(this);
			}
			return;
		}
		finishing = true;
		// if combating doesnt stop when xlog this way ends combat
		if (!World.containsLobbyPlayer(username)) {
			stopAll(false, true, !(actionManager.getAction() instanceof PlayerCombat));
		}
		long currentTime = Utils.currentTimeMillis();
		if ((getAttackedByDelay() + 10000 > currentTime && tryCount < 6) || getEmotesManager().getNextEmoteEnd() >= currentTime || lockDelay >= currentTime) {
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						packetsDecoderPing = Utils.currentTimeMillis();
						finishing = false;
						finish(tryCount + 1);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 10, TimeUnit.SECONDS);
			return;
		}
		realFinish();
	}

	public void realFinish() {
		if (hasFinished()) {
			return;
		}
		dungManager.finish();
		// DwarfCannon.removeDwarfCannon();
		bypass = false;
		house.finish();
		GrandExchange.unlinkOffers(this);
		if (!World.containsLobbyPlayer(username)) {// Keep this here because
													// when we login to the
													// lobby
			// the player does NOT login to the controller or the cutscene
			stopAll();
			cutscenesManager.logout();
			controlerManager.logout(); // checks what to do on before logout for
		}
		if (slayerManager.getSocialPlayer() != null)
			slayerManager.resetSocialGroup(true);
		// login
		running = false;
		friendsIgnores.sendFriendsMyStatus(false);
		if (currentFriendChat != null) {
			currentFriendChat.leaveChat(this, true);
		}
		if (clanManager != null)
			clanManager.disconnect(this, false);
		if (guestClanManager != null)
			guestClanManager.disconnect(this, true);
		if (familiar != null && !familiar.isFinished()) {
			familiar.dissmissFamiliar(true);
		} else if (pet != null) {
			pet.finish();
		}

		setFinished(true);
		session.setDecoder(-1);
		this.lastLoggedIn = System.currentTimeMillis();
		SerializableFilesManager.savePlayer(this);
		if (World.containsLobbyPlayer(username)) {
			World.removeLobbyPlayer(this);
		}
		World.updateEntityRegion(this);
		if (World.containsPlayer(username) && getRights() != 2 && getSkills().getTotalLevel() > 450) { // Admins
																	// don't
																	// belong
																	// there
																	// lmao xD
	//		com.everythingrs.hiscores.Hiscores.update("30wtnf0kxrahmrgcg7uru4ygb9yad8o8xeuijy23dtua3wn4s4io0s0wiwc3be8t9owl3rk65hfr",  "Normal Mode", username, getRights(), getSkills().getLevels(), "");

			new Thread(new Highscores(this)).start();
		}
		if (World.containsPlayer(username)) {
			World.removePlayer(this);
		}
		if (Settings.DEBUG) {
			Logger.log(this, "Finished Player: " + username + ", pass: " + password);
		}
	}

	public void afkTime() {
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				if (afkTimer < Utils.currentTimeMillis()) {
				//	logout(false);
				}
				afkTime();
			}
		}, 1, TimeUnit.MINUTES);
	}

	@Override
	public boolean restoreHitPoints() {
		boolean update = super.restoreHitPoints();
		if (update) {
			if (prayer.usingPrayer(0, 9))
				super.restoreHitPoints();
			if (resting || listening)
				super.restoreHitPoints();
			refreshHitPoints();
		}
		return update;
	}

	public boolean isListening() {
		return listening;
	}

	public void refreshHitPoints() {
		if (lendMessage != 0) {
			if (lendMessage == 1)
				getPackets().sendGameMessage("<col=FF0000>An item you lent out has been added back to your bank.");
			else if (lendMessage == 2)
				getPackets().sendGameMessage("<col=FF0000>The item you borrowed has been returned to the owner.");
			lendMessage = 0;
		}
		getPackets().sendConfigByFile(7198, getHitpoints());
	}

	@Override
	public void removeHitpoints(Hit hit) {
		super.removeHitpoints(hit);
		refreshHitPoints();
	}

	@Override
	public int getMaxHitpoints() {
		return skills.getLevel(Skills.HITPOINTS) * 10 + equipment.getEquipmentHpIncrease();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<String> getPasswordList() {
		return passwordList;
	}

	public ArrayList<String> getIPList() {
		return ipList;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	public int getRights() {
		return rights;
	}

	public int getGameMode() {
		return gameMode;
	}

public boolean showIcon = true;
	
	public int getMessageIcon() {
		if(!showIcon)
			return  -1;
		if(getRights() == 2 || getRights() == 1){
			return  getRights();
		}else if(IronMan)
			return  13;
		else if(isDivineDonator())
			return 14;
		else if(isLegendaryDonator()){
			return 10;
		}else if(isExtremeDonator())
			return 8;
		else if (isDonator())
			return 11;
		else 
			return -1;
	}

	public VarsManager getVarsManager() {
		return varsManager;
	}

	public WorldPacketsEncoder getPackets() {
		if (session == null)
			this.session = new RobotGameSession(null, this);
		return session.getWorldPackets();
	}

	public boolean hasStarted() {
		return started;
	}

	public boolean isRunning() {
		return running;
	}

	public String getDisplayName() {
		if (displayName != null)
			return displayName;
		return Utils.formatPlayerNameForDisplay(username);
	}

	public String getPrestigeIcon() {
		if (getPrestigeLevel() == 0)
			return "";
		if (getPrestigeLevel() == 1)
			return "<col=0A7079>I</col>";
		if (getPrestigeLevel() == 2)
			return "<col=0A7079>II</col>";
		if (getPrestigeLevel() == 3)
			return "<col=0A7079>III</col>";
		if (getPrestigeLevel() == 4)
			return "<col=0A7079>IV</col>";
		if (getPrestigeLevel() == 5)
			return "<col=0A7079>V</col>";
		if (getPrestigeLevel() == 6)
			return "<col=0A7079>VI</col>";
		if (getPrestigeLevel() == 7)
			return "<col=0A7079>VII</col>";
		if (getPrestigeLevel() == 8)
			return "<col=0A7079>VII</col>";
		if (getPrestigeLevel() == 9)
			return "<col=0A7079>IX</col>";
		if (getPrestigeLevel() == 10)
			return "<col=0A7079>X</col>";
		return "";

	}

	public boolean hasDisplayName() {
		return displayName != null;
	}

	public Appearence getAppearence() {
		return appearence;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public int getTemporaryMoveType() {
		return temporaryMovementType;
	}

	public void setTemporaryMoveType(int temporaryMovementType) {
		this.temporaryMovementType = temporaryMovementType;
	}

	public LocalPlayerUpdate getLocalPlayerUpdate() {
		return localPlayerUpdate;
	}

	public LocalNPCUpdate getLocalNPCUpdate() {
		return localNPCUpdate;
	}

	public int getDisplayMode() {
		return displayMode;
	}

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}

	public Ectophial getEctophial() {
		return ectophial;
	}

	public ClueScrolls getClueScrolls() {
		return ClueScrolls;
	}

	public void setPacketsDecoderPing(long packetsDecoderPing) {
		this.packetsDecoderPing = packetsDecoderPing;
	}

	public long getPacketsDecoderPing() {
		return packetsDecoderPing;
	}

	public Session getSession() {
		return session;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setListening(boolean listening) {
		this.listening = listening;
		sendRunButtonConfig();
	}

	public boolean clientHasLoadedMapRegion() {
		return clientLoadedMapRegion;
	}

	public void setClientHasLoadedMapRegion() {
		clientLoadedMapRegion = true;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Skills getSkills() {
		return skills;
	}

	public byte getRunEnergy() {
		return runEnergy;
	}

	public void drainRunEnergy() {
		setRunEnergy(runEnergy - 1);
	}

	public void setRunEnergy(double runEnergy) {
		this.runEnergy = (byte) runEnergy;
		getPackets().sendRunEnergy();
	}

	public boolean isResting() {
		return resting;
	}

	public void setResting(boolean resting) {
		this.resting = resting;
		sendRunButtonConfig();
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public void setCoordsEvent(CoordsEvent coordsEvent) {
		this.coordsEvent = coordsEvent;
	}

	public DialogueManager getDialogueManager() {
		return dialogueManager;
	}

	public static VoteChecker voteChecker = new VoteChecker("ragevote.db.8679617.hostedresource.com", "ragevote", "ragevote", "Blist9559!");

	public SpinsManager getSpinsManager() {
		return spinsManager;
	}

	public LoyaltyManager getLoyaltyManager() {
		return loyaltyManager;
	}

	public int getkilledByAdam() {
		return killedByAdam;
	}

	public void warningLog(Player player) {
		if (player.getRights() >= 2)
			return;
		if (player.isPker)
			return;
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "items/warning.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + " " + Calendar.getInstance().getTimeZone().getDisplayName() + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has a large amount of cash on their account!");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public static void tradeLog(Player player, int i, int amount, Player target) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "items/trades.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + " " + Calendar.getInstance().getTimeZone().getDisplayName() + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " traded " + amount + " of the ID " + i + " with " + target.getUsername() + "");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public static void dropLog(Player player, int i) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "items/drops.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + " " + Calendar.getInstance().getTimeZone().getDisplayName() + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has dropped the item" + i + "");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public void shopLog(Player player, int i, int a, boolean selling) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "items/shop.txt", true));
			if (selling) {
				bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + " " + Calendar.getInstance().getTimeZone().getDisplayName() + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has sold " + a + " of the item " + i + " to the store.");
			} else {
				bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + " " + Calendar.getInstance().getTimeZone().getDisplayName() + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has bought " + a + " of the item " + i + " to the store.");
			}
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public void deathLog(Player player, int i, int a, String name) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "items/deaths.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + " " + Calendar.getInstance().getTimeZone().getDisplayName() + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has lost " + a + " of the item " + name + "(" + i + ") when killed.");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public void npcLog(Player player, int i, int a, String name, String npc, int n) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "items/npcdrops.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + " " + Calendar.getInstance().getTimeZone().getDisplayName() + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has recieved " + a + " of the item " + name + "(" + i + ") from the NPC " + npc + "(" + n + ").");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public int getLividPoints() {
		return lividpoints;
	}

	public void setLividPoints(int lividpoints) {
		this.lividpoints = lividpoints;
	}

	public CombatDefinitions getCombatDefinitions() {
		return combatDefinitions;
	}

	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}

	private boolean isAssassin;

	public boolean isAssassin() {
		return isAssassin;
	}

	public void setAssassin(boolean value) {
		this.isAssassin = value;
	}
    /*
     * 
     */
	public void sendSoulSplit(final Hit hit, final Entity user) {
		final Player target = this;
		if (hit.getDamage() > 0) {
			World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
		}
		if (user instanceof Player && ((Player) user).getEquipment().getAmuletId() == 31875 && Misc.random(1, 2) == 1)
			user.heal(hit.getDamage() / Misc.random(2, 4));
		else
			user.heal(hit.getDamage() / 5);
		prayer.drainPrayer(hit.getDamage() / 5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				setNextGraphics(new Graphics(2264));
				if (hit.getDamage() > 0) {
					World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0, 0);
				}
			}
		}, 0);
	}

	// Random Events

	public static void RandomEventTeleportPlayer(final Player player, final int x, final int y, final int z) {
		player.setNextAnimation(new Animation(2140));
		player.setNextForceTalk(new ForceTalk("ARGHHHHHHHHH!"));
		player.getControlerManager().startControler("RandomEvent");
		player.setInfiniteStopDelay();
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.resetStopDelay();
				player.setNextWorldTile(new WorldTile(x, y, z));
			}
		}, 1);
	}

	public static void QuizEventTeleportPlayer(final Player player, final int x, final int y, final int z) {
		player.setNextAnimation(new Animation(2140));
		player.setNextForceTalk(new ForceTalk("ARGHHHHHHHHH!"));
		player.getControlerManager().startControler("QuizEvent");
		player.setInfiniteStopDelay();
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.resetStopDelay();
				player.setNextWorldTile(new WorldTile(x, y, z));
			}
		}, 1);
	}

	public long getTeleBlockImmune() {
		Long teleimmune = (Long) getTemporaryAttributtes().get("TeleBlockedImmune");
		if (teleimmune == null)
			return 0;
		return teleimmune;
	}

	public long getChargeDelay() {
		Long charge = (Long) getTemporaryAttributtes().get("Charge");
		if (charge == null)
			return 0;
		return charge;
	}

	/**
	 * Presets
	 */
	private List<PresetSetups> PresetSetups;

	public List<PresetSetups> getPresetSetups() {
		return PresetSetups;
	}

	public boolean addPresetSetup(Player player, PresetSetups setup) {
		if (getPresetSetupByName(setup.getName()) != null) {
			getDialogueManager().startDialogue("SimpleMessage", "A setup with that name already exists.");
			return false;
		} /*
			 * else if (this.getPresetSetups().size() >= this.MAX_SETUPS) {
			 * getDialogueManager().startDialogue("SimpleMessage",
			 * "You may only have a maximum of "+MAX_SETUPS+" setups."); return
			 * false; }
			 */
		PresetSetups.add(setup);
		return true;
	}

	public PresetSetups getPresetSetupByName(String name) {
		for (PresetSetups sets : PresetSetups) {
			if (sets == null)
				continue;
			if (sets.getName().equalsIgnoreCase(name))
				return sets;
		}
		return null;
	}

	public boolean removePresetSetup(String name) {
		PresetSetups set = getPresetSetupByName(name);
		if (set == null) {
			getDialogueManager().startDialogue("SimpleMessage", "You do not have a setup saved as: " + name + ".");
			return false;
		}
		PresetSetups.remove(set);
		getPackets().sendGameMessage("Removed preset: " + name + ".");
		return true;
	}

	public void randomevent(final Player p) {
		int random = 0;
		random = Misc.random(400);
		if (random == 1) {
			;
			// p.saveLoc(p.getX(), p.getY(), p.getPlane());
			// RandomEventTeleportPlayer(p, 2918, 4597, 0);
		} else if (random == 2) {
			// final NPC evilchicken = findNPC(3375);
			// World.spawnNPC(3375, new WorldTile(p.getWorldTile()), -1, true);
			// evilchicken.setTarget(p);
		} else if (random == 3) {
			SandwichLady.getInstance().start(p);
		}
	}

	public static NPC findNPC(int id) {
		for (NPC npc : World.getNPCs()) {
			if (npc == null || npc.getId() != id)
				continue;
			return npc;
		}
		return null;
	}

	;

	public void addStopDelay(long delay) {
		stopDelay = Utils.currentTimeMillis() + (delay * 600);
	}

	public DwarfCannon getDwarfCannon() {
		return DwarfCannon;
	}

	/**
	 * Dwarf Cannon
	 */
	public Object getDwarfCannon;

	public boolean hasLoadedCannon = false;

	public boolean isShooting = false;

	public boolean hasSetupCannon = false;

	public boolean hasSetupGoldCannon = false;

	public boolean hasSetupRoyalCannon = false;

	public void setInfiniteStopDelay() {
		stopDelay = Long.MAX_VALUE;
	}

	public void resetStopDelay() {
		stopDelay = 0;
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (hit.getLook() != HitLook.MELEE_DAMAGE && hit.getLook() != HitLook.RANGE_DAMAGE && hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		if (invulnerable) {
			hit.setDamage(0);
			return;
		}
		if (auraManager.usingPenance()) {
			int amount = (int) (hit.getDamage() * 0.2);
			if (amount > 0)
				prayer.restorePrayer(amount);
		}
		Entity source = hit.getSource();
		if (source == null)
			return;
		if (source instanceof NPC) {
			NPC npc = (NPC) source;
			if (npc != null && npc.isRevenantNPC(npc.getName())) {
				if (getForinthyRepel() >= (Utils.currentTimeMillis() + (59 * 1000 * 60))) {
					hit.setDamage(0);
					return;
				}
			}
		}
		if (polDelay > Utils.currentTimeMillis())
			hit.setDamage((int) (hit.getDamage() * 0.5));
		if (prayer.hasPrayersOn() && hit.getDamage() != 0) {
			if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				if (prayer.usingPrayer(0, 17))
					hit.setDamage((int) (hit.getDamage() * source.getMagePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 7)) {
					int deflectedDamage = source instanceof Nex ? 0 : (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source.getMagePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage, HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2228));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				if (prayer.usingPrayer(0, 18))
					hit.setDamage((int) (hit.getDamage() * source.getRangePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 8)) {
					int deflectedDamage = source instanceof Nex ? 0 : (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source.getRangePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage, HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2229));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				if (prayer.usingPrayer(0, 19))
					hit.setDamage((int) (hit.getDamage() * source.getMeleePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 9)) {
					int deflectedDamage = source instanceof Nex ? 0 : (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source.getMeleePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage, HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2230));
						setNextAnimation(new Animation(12573));
					}
				}
			}
		}
		if (hit.getDamage() >= 200) {
			if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				int reducedDamage = hit.getDamage() * combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS] / 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage, HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				int reducedDamage = hit.getDamage() * combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS] / 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage, HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				int reducedDamage = hit.getDamage() * combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS] / 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage, HitLook.ABSORB_DAMAGE));
				}
			}
		}
		int shieldId = equipment.getShieldId();
		if (shieldId == 13742) { // elsyian
			if (Utils.getRandom(100) <= 70)
				hit.setDamage((int) (hit.getDamage() * 0.75));
		} else if (shieldId == 13740) { // divine
			int drain = (int) (Math.ceil(hit.getDamage() * 0.3) / 2);
			if (prayer.getPrayerpoints() >= drain) {
				hit.setDamage((int) (hit.getDamage() * 0.70));
				prayer.drainPrayer(drain);
			}
		} else if (shieldId == 29957) { // scarlet
			int drain = (int) (Math.ceil(hit.getDamage() * 0.3) / 2);
			if (prayer.getPrayerpoints() >= drain) {
				hit.setDamage((int) (hit.getDamage() * 0.70));
				prayer.drainPrayer(drain);
			}
			source.applyHit(new Hit(this, (int) (hit.getDamage() * 0.4), HitLook.REFLECTED_DAMAGE));
			heal((int) (hit.getDamage() * 0.25), 1);
		}
		if (castedVeng && hit.getDamage() >= 4) {
			castedVeng = false;
			setNextForceTalk(new ForceTalk("Taste vengeance!"));
			source.applyHit(new Hit(this, (int) (hit.getDamage() * 0.75), HitLook.REGULAR_DAMAGE));
		}
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (p2.prayer.hasPrayersOn()) {
				if (p2.prayer.usingPrayer(0, 24)) { // smite
					int drain = hit.getDamage() / 4;
					if (drain > 0)
						prayer.drainPrayer(drain);
				} else {
					if (hit.getDamage() == 0)
						return;
					if (!p2.prayer.isBoostedLeech()) {
						if (hit.getLook() == HitLook.MELEE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 19)) {
								if (Utils.getRandom(4) == 0) {
									p2.prayer.increaseTurmoilBonus(this);
									p2.prayer.setBoostedLeech(true);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 1)) { // sap att
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(0)) {
										p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your sap curse has no effect.", true);
									} else {
										p2.prayer.increaseLeechBonus(0);
										p2.getPackets().sendGameMessage("Your curse drains Attack from the enemy, boosting your Attack.", true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2214));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2215, 35, 35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2216));
										}
									}, 1);
									return;
								}
							} else {
								if (p2.prayer.usingPrayer(1, 10)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(3)) {
											p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
										} else {
											p2.prayer.increaseLeechBonus(3);
											p2.getPackets().sendGameMessage("Your curse drains Attack from the enemy, boosting your Attack.", true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2231, 35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(new WorldTask() {
											@Override
											public void run() {
												setNextGraphics(new Graphics(2232));
											}
										}, 1);
										return;
									}
								}
								if (p2.prayer.usingPrayer(1, 14)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(7)) {
											p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
										} else {
											p2.prayer.increaseLeechBonus(7);
											p2.getPackets().sendGameMessage("Your curse drains Strength from the enemy, boosting your Strength.", true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2248, 35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(new WorldTask() {
											@Override
											public void run() {
												setNextGraphics(new Graphics(2250));
											}
										}, 1);
										return;
									}
								}

							}
						}
						if (hit.getLook() == HitLook.RANGE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 2)) { // sap range
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(1)) {
										p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your sap curse has no effect.", true);
									} else {
										p2.prayer.increaseLeechBonus(1);
										p2.getPackets().sendGameMessage("Your curse drains Range from the enemy, boosting your Range.", true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2217));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2218, 35, 35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2219));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 11)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(4)) {
										p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
									} else {
										p2.prayer.increaseLeechBonus(4);
										p2.getPackets().sendGameMessage("Your curse drains Range from the enemy, boosting your Range.", true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2236, 35, 35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2238));
										}
									});
									return;
								}
							}
						}
						if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 3)) { // sap mage
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(2)) {
										p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your sap curse has no effect.", true);
									} else {
										p2.prayer.increaseLeechBonus(2);
										p2.getPackets().sendGameMessage("Your curse drains Magic from the enemy, boosting your Magic.", true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2220));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2221, 35, 35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2222));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 12)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(5)) {
										p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
									} else {
										p2.prayer.increaseLeechBonus(5);
										p2.getPackets().sendGameMessage("Your curse drains Magic from the enemy, boosting your Magic.", true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2240, 35, 35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2242));
										}
									}, 1);
									return;
								}
							}
						}

						// overall

						if (p2.prayer.usingPrayer(1, 13)) { // leech defence
							if (Utils.getRandom(10) == 0) {
								if (p2.prayer.reachedMax(6)) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
								} else {
									p2.prayer.increaseLeechBonus(6);
									p2.getPackets().sendGameMessage("Your curse drains Defence from the enemy, boosting your Defence.", true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2244, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2246));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 15)) {
							if (Utils.getRandom(10) == 0) {
								if (getRunEnergy() <= 0) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
								} else {
									p2.setRunEnergy(p2.getRunEnergy() > 90 ? 100 : p2.getRunEnergy() + 10);
									setRunEnergy(p2.getRunEnergy() > 10 ? getRunEnergy() - 10 : 0);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2256, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2258));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 16)) {
							if (Utils.getRandom(10) == 0) {
								if (combatDefinitions.getSpecialAttackPercentage() <= 0) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
								} else {
									p2.combatDefinitions.restoreSpecialAttack();
									combatDefinitions.desecreaseSpecialAttack(10);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2252, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2254));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 4)) { // sap spec
							if (Utils.getRandom(10) == 0) {
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2223));
								p2.prayer.setBoostedLeech(true);
								if (combatDefinitions.getSpecialAttackPercentage() <= 0) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your sap curse has no effect.", true);
								} else {
									combatDefinitions.desecreaseSpecialAttack(10);
								}
								World.sendProjectile(p2, this, 2224, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2225));
									}
								}, 1);
								return;
							}
						}
					}
				}
			}
		} else {
			NPC n = (NPC) source;
			if (n.getId() == 13448)
				sendSoulSplit(hit, n);
		}
	}

	public boolean donatorRespawn = false;
	public boolean donatorDivineRespawn = false;
	@Override
	public void sendDeath(final Entity source) {
		if (getEquipment().getAuraId() == 29290) {
			getEquipment().deleteItem(29290, 1);
			sm("<col=FFFFF>Your sign of life lost all its power and turned into dust.");
			heal(250);
			return;
		}
		if (prayer.hasPrayersOn() && getTemporaryAttributtes().get("startedDuel") != Boolean.TRUE) {
			if (prayer.usingPrayer(0, 22)) {
				setNextGraphics(new Graphics(437));
				final Player target = this;
				if (isAtMultiArea()) {
					for (int regionId : getMapRegionsIds()) {
						List<Integer> playersIndexes = World.getRegion(regionId).getPlayerIndexes();
						if (playersIndexes != null) {
							for (int playerIndex : playersIndexes) {
								Player player = World.getPlayers().get(playerIndex);
								if (player == null || !player.hasStarted() || player.isDead() || player.hasFinished() || !player.withinDistance(this, 1) || !player.isCanPvp() || !target.getControlerManager().canHit(player))
									continue;
								player.applyHit(new Hit(target, Utils.getRandom((int) (skills.getLevelForXp(Skills.PRAYER) * 2.5)), HitLook.REGULAR_DAMAGE));
							}
						}
						List<Integer> npcsIndexes = World.getRegion(regionId).getNPCsIndexes();
						if (npcsIndexes != null) {
							for (int npcIndex : npcsIndexes) {
								NPC npc = World.getNPCs().get(npcIndex);
								if (npc == null || npc.isDead() || npc.hasFinished() || !npc.withinDistance(this, 1) || !npc.getDefinitions().hasAttackOption() || !target.getControlerManager().canHit(npc))
									continue;
								npc.applyHit(new Hit(target, Utils.getRandom((int) (skills.getLevelForXp(Skills.PRAYER) * 2.5)), HitLook.REGULAR_DAMAGE));
							}
						}
					}
				} else {
					if (source != null && source != this && !source.isDead() && !source.hasFinished() && source.withinDistance(this, 1))
						source.applyHit(new Hit(target, Utils.getRandom((int) (skills.getLevelForXp(Skills.PRAYER) * 2.5)), HitLook.REGULAR_DAMAGE));
				}
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						World.sendGraphics(target, new Graphics(438), new WorldTile(target.getX() - 1, target.getY(), target.getPlane()));
						World.sendGraphics(target, new Graphics(438), new WorldTile(target.getX() + 1, target.getY(), target.getPlane()));
						World.sendGraphics(target, new Graphics(438), new WorldTile(target.getX(), target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438), new WorldTile(target.getX(), target.getY() + 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438), new WorldTile(target.getX() - 1, target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438), new WorldTile(target.getX() - 1, target.getY() + 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438), new WorldTile(target.getX() + 1, target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438), new WorldTile(target.getX() + 1, target.getY() + 1, target.getPlane()));
					}
				});
			} else if (prayer.usingPrayer(1, 17)) {
				World.sendProjectile(this, new WorldTile(getX() + 2, getY() + 2, getPlane()), 2260, 24, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2, getY(), getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2, getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX() - 2, getY() + 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2, getY(), getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2, getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX(), getY() + 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX(), getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				final Player target = this;
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						setNextGraphics(new Graphics(2259));

						if (isAtMultiArea()) {
							for (int regionId : getMapRegionsIds()) {
								List<Integer> playersIndexes = World.getRegion(regionId).getPlayerIndexes();
								if (playersIndexes != null) {
									for (int playerIndex : playersIndexes) {
										Player player = World.getPlayers().get(playerIndex);
										if (player == null || !player.hasStarted() || player.isDead() || player.hasFinished() || !player.isCanPvp() || !player.withinDistance(target, 2) || !target.getControlerManager().canHit(player))
											continue;
										player.applyHit(new Hit(target, Utils.getRandom((skills.getLevelForXp(Skills.PRAYER) * 3)), HitLook.REGULAR_DAMAGE));
									}
								}
								List<Integer> npcsIndexes = World.getRegion(regionId).getNPCsIndexes();
								if (npcsIndexes != null) {
									for (int npcIndex : npcsIndexes) {
										NPC npc = World.getNPCs().get(npcIndex);
										if (npc == null || npc.isDead() || npc.hasFinished() || !npc.withinDistance(target, 2) || !npc.getDefinitions().hasAttackOption() || !target.getControlerManager().canHit(npc))
											continue;
										npc.applyHit(new Hit(target, Utils.getRandom((skills.getLevelForXp(Skills.PRAYER) * 3)), HitLook.REGULAR_DAMAGE));
									}
								}
							}
						} else {
							if (source != null && source != target && !source.isDead() && !source.hasFinished() && source.withinDistance(target, 2))
								source.applyHit(new Hit(target, Utils.getRandom((skills.getLevelForXp(Skills.PRAYER) * 3)), HitLook.REGULAR_DAMAGE));
						}

						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 2, getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 2, getY() - 2, getPlane()));

						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 2, getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 2, getY() - 2, getPlane()));

						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX(), getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX(), getY() - 2, getPlane()));

						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 1, getY() + 1, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 1, getY() - 1, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 1, getY() + 1, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 1, getY() - 1, getPlane()));
					}
				});
			}
		}
		setNextAnimation(new Animation(-1));
		if (!controlerManager.sendDeath())
			return;
		lock(7);
		stopAll();
		if (familiar != null)
			familiar.sendDeath(this);
		// final WorldTile deathTile = new WorldTile(this);

		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					int getDeathAnimation = Utils.random(Settings.deathAnimations.length);
					setNextAnimation(new Animation(Settings.deathAnimations[getDeathAnimation][0]));
					setNextGraphics(new Graphics(Settings.deathAnimations[getDeathAnimation][1]));
				} else if (loop == 1) {
					getPackets().sendGameMessage("Oh dear, you have died.");
					removePotions();
				} else if (loop == 3) {
					// controlerManager.startControler("DeathEvent", deathTile,
					// hasSkull());
				} else if (loop == 4) {
					killstreak = 0;
					getPackets().sendMusicEffect(90);
					reset();
					if(donatorRespawn)
						setNextWorldTile(new WorldTile(new WorldTile(1809,5844,0)));
					else if (donatorDivineRespawn)
						setNextWorldTile(new WorldTile(new WorldTile(2148,5530,3)));
					else
						setNextWorldTile(new WorldTile(Settings.HOME_PLAYER_LOCATION[0]));
					setNextAnimation(new Animation(-1));
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}

	@SuppressWarnings("unused")
	private boolean hasdied;

	

	public void increaseKillCount(Player killed) {
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		killed.deathCount++;
		killstreak += 1;
		if (killstreak > highestKillstreak)
			highestKillstreak = killstreak;
		KillStreakRank.checkRank(this);
		getInventory().addItem(12852, 1);
		getPackets().sendGameMessage("<col=ff0000>Your kill streak has increased, you now are on a streak of " + killstreak + " kills.");
		if (killstreak >= 5) {
			getPackets().sendGameMessage("<col=ff0000>You have earned an upgrade token for your high killstreak.");
			getInventory().addItem(29932, 1);
		}
		if (killstreak >= 10) {
			getPackets().sendGameMessage("<col=ff0000>You have reached an INSANE killstreak! Claim your reward via ::capes");
		}
		for (Player players : World.getPlayers()) {
			if (players == null)
				continue;
			if (killstreak == 2) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On A <col=ff0000>" + killstreak + "</col> killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 3) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 4) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 5) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 6) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 7) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 8) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 9) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 10 && highestKillstreak > killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 10 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>10</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 11) {
				players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 12) {
				players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 13) {
				players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 14) {
				players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 15) {
				players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 16) {
				players.getPackets().sendGameMessage("Bow Down To " + getDisplayName() + " Because They're On A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 17) {
				players.getPackets().sendGameMessage("Bow Down To " + getDisplayName() + " Because They're On A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 18) {
				players.getPackets().sendGameMessage("Bow Down To " + getDisplayName() + " Because They're On A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 19) {
				players.getPackets().sendGameMessage("Bow Down To " + getDisplayName() + " Because They're On A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak >= 20) {
				players.getPackets().sendGameMessage("All Hail " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 20 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Hit Their <col=ff0000>20th</col> Killstreak - Go Claim Your Award On The Forums! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>20</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 50 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Hit Their <col=ff0000>50th</col> Killstreak - Go Claim Your Award On The Forums! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>50</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 30 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>30</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 40 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>40</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 60 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>60</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 70 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>70</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 80 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>80</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 90 && highestKillstreak < killstreak) {
				players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>90</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		}
		PkRank.checkRank(killed);
		killCount++;
		getPackets().sendGameMessage("<col=ff0000>You have killed " + killed.getDisplayName() + ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}

	public void increaseKillCountSafe(Player killed) {
		PkRank.checkRank(killed);
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		killCount++;
		getPackets().sendGameMessage("<col=ff0000>You have killed " + killed.getDisplayName() + ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}

	public void sendRandomJail(Player p) {
		p.resetWalkSteps();
		switch (Utils.getRandom(3)) {
		case 0:
			p.setNextWorldTile(new WorldTile(3111, 3516, 0));
			break;
		case 1:
			p.setNextWorldTile(new WorldTile(3108, 3515, 0));
			break;
		case 2:
			p.setNextWorldTile(new WorldTile(3110, 3513, 0));
			break;
		case 3:
			p.setNextWorldTile(new WorldTile(3108, 3511, 0));
			break;
		}
	}

	@Override
	public int getSize() {
		return appearence.getSize();
	}

	public boolean isCanPvp() {
		return canPvp;
	}

	public void setCanPvp(boolean canPvp) {
		this.canPvp = canPvp;
		appearence.generateAppearenceData();
		getPackets().sendPlayerOption(canPvp ? "Attack" : "null", 1, true);
		getPackets().sendPlayerUnderNPCPriority(canPvp);
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public long getLockDelay() {
		return lockDelay;
	}

	public boolean isLocked() {
		return lockDelay >= Utils.currentTimeMillis();
	}

	public void lock() {
		lockDelay = Long.MAX_VALUE;
	}

	public void lock(long time) {
		lockDelay = Utils.currentTimeMillis() + (time * 600);
	}

	public void unlock() {
		lockDelay = 0;
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay, int totalDelay) {
		useStairs(emoteId, dest, useDelay, totalDelay, null);
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay, int totalDelay, final String message) {
		stopAll();
		lock(totalDelay);
		if (emoteId != -1)
			setNextAnimation(new Animation(emoteId));
		if (useDelay == 0)
			setNextWorldTile(dest);
		else {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (isDead())
						return;
					setNextWorldTile(dest);
					if (message != null)
						getPackets().sendGameMessage(message);
				}
			}, useDelay - 1);
		}
	}

	public Bank getBank() {
		return bank;
	}

	public ControlerManager getControlerManager() {
		return controlerManager;
	}

	public void switchMouseButtons() {
		mouseButtons = !mouseButtons;
		refreshMouseButtons();
	}

	public void switchAllowChatEffects() {
		allowChatEffects = !allowChatEffects;
		refreshAllowChatEffects();
	}

	public void refreshAllowChatEffects() {
		getPackets().sendConfig(171, allowChatEffects ? 0 : 1);
	}

	public void refreshMouseButtons() {
		getPackets().sendConfig(170, mouseButtons ? 0 : 1);
	}

	public void refreshPrivateChatSetup() {
		getPackets().sendConfig(287, privateChatSetup);
	}

	public void refreshOtherChatsSetup() {
		getVarsManager().setVarBit(9188, friendChatSetup);
		getVarsManager().setVarBit(3612, clanChatSetup);
		getVarsManager().forceSendVarBit(9191, guestChatSetup);
	}

	public void setPrivateChatSetup(int privateChatSetup) {
		this.privateChatSetup = privateChatSetup;
	}

	public void setFriendChatSetup(int friendChatSetup) {
		this.friendChatSetup = friendChatSetup;
	}

	public int getPrivateChatSetup() {
		return privateChatSetup;
	}

	public boolean isForceNextMapLoadRefresh() {
		return forceNextMapLoadRefresh;
	}

	public void setForceNextMapLoadRefresh(boolean forceNextMapLoadRefresh) {
		this.forceNextMapLoadRefresh = forceNextMapLoadRefresh;
	}

	public FriendsIgnores getFriendsIgnores() {
		return friendsIgnores;
	}

	/*
	 * do not use this, only used by pm
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public void out(String text) {
		getPackets().sendGameMessage(text);
	}

	public void out(String text, int delay) {
		out(text);
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void addPotDelay(long time) {
		potDelay = time + Utils.currentTimeMillis();
	}

	public long getPotDelay() {
		return potDelay;
	}

	public void addFoodDelay(long time) {
		foodDelay = time + Utils.currentTimeMillis();
	}

	public long getFoodDelay() {
		return foodDelay;
	}

	public long getBoneDelay() {
		return boneDelay;
	}

	public long getAshDelay() {
		return ashDelay;
	}

	public void addBoneDelay(long time) {
		boneDelay = time + Utils.currentTimeMillis();
	}

	public void addAshDelay(long time) {
		ashDelay = time + Utils.currentTimeMillis();
	}

	public void addPoisonImmune(long time) {
		poisonImmune = time + Utils.currentTimeMillis();
		getPoison().reset();
	}

	public long getPoisonImmune() {
		return poisonImmune;
	}

	public void addFireImmune(long time) {
		fireImmune = time + Utils.currentTimeMillis();
	}

	public void addSuperFireImmune(long time) {
		superFireImmune = time + Utils.currentTimeMillis();
	}

	public long getFireImmune() {
		return fireImmune;
	}

	public long getSuperFireImmune() {
		return superFireImmune;
	}

	@Override
	public void heal(int ammount, int extra) {
		super.heal(ammount, extra);
		refreshHitPoints();
	}

	public MusicsManager getMusicsManager() {
		return musicsManager;
	}

	public HintIconsManager getHintIconsManager() {
		return hintIconsManager;
	}

	public boolean isCastVeng() {
		return castedVeng;
	}

	public void setCastVeng(boolean castVeng) {
		this.castedVeng = castVeng;
	}

	public int getKillCount() {
		return killCount;
	}

	public int getBarrowsKillCount() {
		return barrowsKillCount;
	}

	public int setBarrowsKillCount(int barrowsKillCount) {
		return this.barrowsKillCount = barrowsKillCount;
	}

	public int setKillCount(int killCount) {
		return this.killCount = killCount;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public int setDeathCount(int deathCount) {
		return this.deathCount = deathCount;
	}

	public void setCloseInterfacesEvent(Runnable closeInterfacesEvent) {
		this.closeInterfacesEvent = closeInterfacesEvent;
	}

	public void setSpins(int spins) {
		this.spins = spins;
	}

	public int getSpins() {
		return spins;
	}

	// slayer masters

	public void setSlayerTaskAmount(int amount) {
		this.slayerTaskAmount = amount;
	}

	public int getSlayerTaskAmount() {
		return slayerTaskAmount;
	}

	public int master;

	public int money;

	public boolean isBurying;

	public int getSlayerMaster() {
		return master;
	}

	public void setSlayerMaster(int masta) {
		this.master = masta;
	}

	public boolean isTalkedWithKuradal() {
		return talkedWithKuradal;
	}

	public void increaseKillCount1(Player killed) {
		killed.deathCount++;
		PkRank.checkRank(killed);
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		getInventory().addItem(12852, 15);
		killCount++;
		getPackets().sendGameMessage("<col=ff0000>You have slayed " + killed.getDisplayName() + ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}

	public void setTalkedWithKuradal() {
		talkedWithKuradal = true;
	}

	public void falseWithKuradal() {
		talkedWithSpria = false;
	}

	public boolean isTalkedWithSpria() {
		return talkedWithSpria;
	}

	public void setTalkedWithSpria() {
		talkedWithSpria = true;
	}

	public void falseWithSpria() {
		talkedWithSpria = false;
	}

	public boolean isTalkedWithMazchna() {
		return talkedWithMazchna;
	}

	public void setTalkedWithMazchna() {
		talkedWithMazchna = true;
	}

	public void falseWithMazchna() {
		talkedWithMazchna = false;
	}

	// end of slayer masters

	public void setkilledByAdam(int killedByAdam) {
		this.killedByAdam = killedByAdam;
	}

	public long getMuted() {
		return muted;
	}

	public void setMuted(long muted) {
		this.muted = muted;
	}

	public long getJailed() {
		return jailed;
	}

	public void setJailed(long jailed) {
		this.jailed = jailed;
	}

	public boolean isPermBanned() {
		return permBanned;
	}

	public void setPermBanned(boolean permBanned) {
		this.permBanned = permBanned;
	}

	public void setInDung(boolean inDung) {
		this.inDung = inDung;
	}

	public boolean isInDung() {
		return inDung;
	}

	public long getBanned() {
		return banned;
	}

	public void setBanned(long banned) {
		this.banned = banned;
	}

	public ChargesManager getCharges() {
		return charges;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean[] getKilledBarrowBrothers() {
		return killedBarrowBrothers;
	}

	public void setHiddenBrother(int hiddenBrother) {
		this.hiddenBrother = hiddenBrother;
	}

	public int getHiddenBrother() {
		return hiddenBrother;
	}

	public void resetBarrows() {
		hiddenBrother = -1;
		killedBarrowBrothers = new boolean[7]; // includes new bro for future
												// use
		barrowsKillCount = 0;
	}

	public void addForinthyRepel(long repel) {
		forinthyRepel = repel + Utils.currentTimeMillis();
	}

	public long getForinthyRepel() {
		return forinthyRepel < 0 ? forinthyRepel = 0 : forinthyRepel;
	}

	public void setForinthyRepel(long repel) {
		this.forinthyRepel = repel;
	}

	public void processForinthryBrace() {
		addForinthyRepel(3600000);
		final long current = getForinthyRepel();
		getPackets().sendGameMessage("Revenants shall not be aggressive towards you for the next hour.");
		WorldTasksManager.schedule(new WorldTask() {
			boolean stop = false;

			@Override
			public void run() {
				if (current != getForinthyRepel()) {
					stop();
					return;
				}
				if (!stop) {
					getPackets().sendGameMessage("<col=480000>Your protection provided by your forinthry bracelet is almost over...</col>");
					stop = true;
				} else {
					stop();
					getPackets().sendGameMessage("<col=480000>Your protection provided by your forinthry bracelet has ran out...</col>");
				}
			}
		}, 500, 100);

	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public boolean isDonator() {
		return isExtremeDonator() || isLegendaryDonator() || isSupremeDonator() || isAngelicDonator() || isDivineDonator() || donator ;
	}

	public boolean isExtremeDonator() {
		return extremeDonator || isLegendaryDonator() || isSupremeDonator() || isAngelicDonator() || isDivineDonator();
	}

	public boolean isLegendaryDonator() {
		return legendaryDonator || isSupremeDonator() || isAngelicDonator() || isDivineDonator();
	}

	public boolean isSupremeDonator() {
		return supremeDonator || isAngelicDonator() || isDivineDonator();
	}

	public boolean isAngelicDonator() {
		return angelicDonator;
	}

	public boolean isDivineDonator() {
		return divineDonator || isAngelicDonator() ;
	}

	public boolean isExtremePermDonator() {
		return extremeDonator;
	}

	public boolean isSupremePermDonator() {
		return supremeDonator;
	}

	public boolean isAngelicPermDonator() {
		return angelicDonator;
	}

	public boolean isDivinePermDonator() {
		return divineDonator;
	}

	public boolean isLegendaryPermDonator() {
		return legendaryDonator;
	}

	public void setExtremeDonator(boolean extremeDonator) {
		this.extremeDonator = extremeDonator;
	}

	public void setSupremeDonator(boolean supremeDonator) {
		this.supremeDonator = supremeDonator;
	}

	public void setLegendaryDonator(boolean legendaryDonator) {
		this.legendaryDonator = legendaryDonator;
	}

	public void setDivineDonator(boolean divineDonator) {
		this.divineDonator = divineDonator;
	}

	public void setAngelicDonator(boolean angelicDonator) {
		this.angelicDonator = angelicDonator;
	}

	public boolean isGraphicDesigner() {
		return isGraphicDesigner;
	}

	public boolean isForumModerator() {
		return isForumModerator;
	}

	public void setGraphicDesigner(boolean isGraphicDesigner) {
		this.isGraphicDesigner = isGraphicDesigner;
	}

	public void setForumModerator(boolean isForumModerator) {
		this.isForumModerator = isForumModerator;
	}

	public Player setBoneDelay(long time) {
		boneDelay = time;
		return this;
	}

	public void setDonator(boolean donator) {
		this.donator = donator;
	}

	public String emailSecurityCode;
	public boolean hasEnteredEmail;
	public boolean hasEmailRecovery;
	// account security
	private String emailAdress;

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	private String TemporaryEmail;

	public String getTemporaryEmail() {
		return TemporaryEmail;
	}

	public void setTemporaryEmail(String TemporaryEmail) {
		this.TemporaryEmail = TemporaryEmail;
	}

	public String getRecovQuestion() {
		return recovQuestion;
	}

	public void setRecovQuestion(String recovQuestion) {
		this.recovQuestion = recovQuestion;
	}

	public String getRecovAnswer() {
		return recovAnswer;
	}

	public void setRecovAnswer(String recovAnswer) {
		this.recovAnswer = recovAnswer;
	}

	public SlayerManager getSlayerManager() {
		return slayerManager;
	}

	public Assassins getAssassinsManager() {
		return assassins;
	}

	public Deaths getDeathsManager() {
		return deaths;
	}

	public transient long alchDelay = 0;
	public int dailyamount;
	public boolean dailyhasTask;

	public int Oreid;
	public int TASK;
	public int setskillbox;
	public int TASKID;
	public boolean hasdaily;
	public boolean givendaily;
	public int skillingtask;
	public boolean getStarter;
	public boolean DIVINECHOPPING;
	public boolean HarvestingEnriched;
	public boolean createdFlickeringBoon;
	public boolean createdBrightBoon;
	public boolean createdGlowingBoon;
	public boolean createdSparklingBoon;
	public boolean createdGleamingBoon;
	public boolean createdVibrantBoon;
	public boolean createdLustrousBoon;
	public boolean createdBrilliantBoon;
	public boolean createdRadiantBoon;
	public boolean createdLuminousBoon;
	public boolean createdIncandescentBoon;
	public int renderId;
	public boolean spawned;
	public boolean locked;
	public Player instancepartner;
	public String instancePass;
	public int playersInInstance;
	public Player newOwner;
	public boolean inInstancedDungeon;
	public int MaxPlayers;
	public int maxPlayer;
	public String sessionowner;
	public int playerAmount;
	public Difficulty difficulty;
	public Instance instance;
	public boolean IronMan = false;
	public boolean isSkiller = false;

	public boolean isSkiller() {
		return isSkiller;
	}

	public void setAlchDelay(long delay) {
		alchDelay = delay + Utils.currentTimeMillis();
	}

	public boolean canAlch() {
		return alchDelay < Utils.currentTimeMillis();
	}

	public String getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}

	public int[] getPouches() {
		return pouches;
	}

	public EmotesManager getEmotesManager() {
		return emotesManager;
	}

	public String getLastIP() {
		return lastIP;
	}

	/**
	 * Lobby Stuff
	 */

	public void init(Session session, String string, IsaacKeyPair isaacKeyPair) {
		username = string;
		this.session = session;
		this.isaacKeyPair = isaacKeyPair;
		World.addLobbyPlayer(this);// .addLobbyPlayer(this);
		if (lodeStone == null) {
			lodeStone = new LodeStone();
		}
		if (activatedLodestones == null) {
			activatedLodestones = new boolean[16];
		}
		if (Settings.DEBUG) {
			Logger.log(this, new StringBuilder("Lobby Inited Player: ").append(string).append(", pass: ").append(password).toString());
		}
	}

	public void startLobby(Player player) {
		player.sendLobbyConfigs(player);
		friendsIgnores.setPlayer(this);
		friendsIgnores.init();
		player.getPackets().sendFriendsChatChannel();
		friendsIgnores.sendFriendsMyStatus(true);
	}

	public void sendLobbyConfigs(Player player) {
		for (int i = 0; i < Utils.DEFAULT_LOBBY_CONFIGS.length; i++) {
			int val = Utils.DEFAULT_LOBBY_CONFIGS[i];
			if (val != 0) {
				player.getPackets().sendConfig(i, val);
			}
		}
	}

	public boolean takeMoney(int amount) {
		if (inventory.getNumerOf(995) >= amount) {
			inventory.removeItemMoneyPouch(995, amount);
			return true;
		} else {
			return false;
		}
	}

	public String getLastHostname() {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(getLastIP());
			String hostname = addr.getHostName();
			return hostname;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PriceCheckManager getPriceCheckManager() {
		return priceCheckManager;
	}

	public void setPestPoints(int pestPoints) {
		this.pestPoints = pestPoints;
	}

	public int getPestPoints() {
		return pestPoints;
	}

	public boolean isUpdateMovementType() {
		return updateMovementType;
	}

	public long getLastPublicMessage() {
		return lastPublicMessage;
	}

	public void setLastPublicMessage(long lastPublicMessage) {
		this.lastPublicMessage = lastPublicMessage;
	}

	public CutscenesManager getCutscenesManager() {
		return cutscenesManager;
	}

	public void kickPlayerFromFriendsChannel(String name) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.kickPlayerFromChat(this, name);
	}

	public void sendFriendsChannelMessage(ChatMessage message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendMessage(this, message);
	}

	public void sendFriendsChannelQuickMessage(QuickChatMessage message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendQuickMessage(this, message);
	}

	public void sendPublicChatMessage(PublicChatMessage message) {
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playersIndexes = World.getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player p = World.getPlayers().get(playerIndex);
				if (p == null || !p.hasStarted() || p.hasFinished() || p.getLocalPlayerUpdate().getLocalPlayers()[getIndex()] == null)
					continue;
				p.getPackets().sendPublicMessage(this, message);
			}
		}
	}

	public void sendDiceRoll(String message) {
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playersIndexes = World.getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player p = World.getPlayers().get(playerIndex);
				if (p == null || !p.hasStarted() || p.hasFinished() || p.getLocalPlayerUpdate().getLocalPlayers()[getIndex()] == null)
					continue;
				p.sendMessage(message);
			}
		}
	}

	public int[] getCompletionistCapeCustomized() {
		return completionistCapeCustomized;
	}

	public void setCompletionistCapeCustomized(int[] skillcapeCustomized) {
		this.completionistCapeCustomized = skillcapeCustomized;
	}

	public int[] getMaxedCapeCustomized() {
		return maxedCapeCustomized;
	}

	public void setMaxedCapeCustomized(int[] maxedCapeCustomized) {
		this.maxedCapeCustomized = maxedCapeCustomized;
	}

	public void setSkullId(int skullId) {
		this.skullId = skullId;
	}

	public int getSkullId() {
		return skullId;
	}

	public boolean isFilterGame() {
		return filterGame;
	}

	public void setFilterGame(boolean filterGame) {
		this.filterGame = filterGame;
	}

	public void addLogicPacketToQueue(LogicPacket packet) {
		for (LogicPacket p : logicPackets) {
			if (p.getId() == packet.getId()) {
				logicPackets.remove(p);
				break;
			}
		}
		logicPackets.add(packet);
	}

	public DominionTower getDominionTower() {
		return dominionTower;
	}

	public void setJujuMiningBoost(int boost) {
		jujuMining = boost;
	}

	public boolean hasJujuMiningBoost() {
		return jujuMining > 1;
	}

	public void setJujuGodBoost(int boost) {
		jujuGod = boost;
	}

	public boolean hasJujuGodBoost() {
		return jujuGod > 1;
	}

	public void setJujuFarmingBoost(int boost) {
		jujuFarming = boost;
	}

	public boolean hasJujuFarmingBoost() {
		return jujuFarming > 1;
	}

	public void setPrayerRenewalDelay(int delay) {
		this.prayerRenewalDelay = delay;
	}

	public int getOverloadDelay() {
		return overloadDelay;
	}

	public void setOverloadDelay(int overloadDelay) {
		this.overloadDelay = overloadDelay;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTeleBlockDelay(long teleDelay) {
		getTemporaryAttributtes().put("TeleBlocked", teleDelay + Utils.currentTimeMillis());
	}

	public String getTeleBlockTimeleft() {
		long minutes = TimeUnit.MILLISECONDS.toMinutes(getTeleBlockDelay() - Utils.currentTimeMillis());
		long seconds = TimeUnit.MILLISECONDS.toSeconds(getTeleBlockDelay() - Utils.currentTimeMillis());
		String secondsMessage = (seconds != 1 ? seconds + " seconds" : "second");
		String minutesMessage = (minutes != 1 ? minutes + " minutes" : "minute");
		return (minutes > 0 ? minutesMessage : secondsMessage);
	}

	public void setTeleBlockImmune(long teleblockImmune) {
		getTemporaryAttributtes().put("TeleBlockedImmune", teleblockImmune + Utils.currentTimeMillis());
	}

	public Entity frozenBy;

	public Entity setFrozenBy(Entity target) {
		return frozenBy = target;
	}

	public Entity getFrozenBy() {
		return frozenBy;
	}

	public long getTeleBlockDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("TeleBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public void setPrayerDelay(long teleDelay) {
		getTemporaryAttributtes().put("PrayerBlocked", teleDelay + Utils.currentTimeMillis());
		prayer.closeAllPrayers();
	}

	public long getPrayerDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("PrayerBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public Familiar getFamiliar() {
		return familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public FriendChatsManager getCurrentFriendChat() {
		return currentFriendChat;
	}

	public void setCurrentFriendChat(FriendChatsManager currentFriendChat) {
		this.currentFriendChat = currentFriendChat;
	}

	public String getCurrentFriendChatOwner() {
		return currentFriendChatOwner;
	}

	public void setCurrentFriendChatOwner(String currentFriendChatOwner) {
		this.currentFriendChatOwner = currentFriendChatOwner;
	}

	public int getSummoningLeftClickOption() {
		return summoningLeftClickOption;
	}

	public void setSummoningLeftClickOption(int summoningLeftClickOption) {
		this.summoningLeftClickOption = summoningLeftClickOption;
	}

	public boolean canSpawn() {
		if (Wilderness.isAtWild(this) || getControlerManager().getControler() instanceof FightPitsArena || getControlerManager().getControler() instanceof CorpBeastControler || getControlerManager().getControler() instanceof PestControlLobby || getControlerManager().getControler() instanceof PestControlGame || getControlerManager().getControler() instanceof ZGDControler || getControlerManager().getControler() instanceof GodWars || getControlerManager().getControler() instanceof DTControler || getControlerManager().getControler() instanceof DuelArena || getControlerManager().getControler() instanceof CastleWarsPlaying || getControlerManager().getControler() instanceof CastleWarsWaiting || getControlerManager().getControler() instanceof FightCaves || getControlerManager().getControler() instanceof FightKiln || FfaZone.inPvpArea(this) || getControlerManager().getControler() instanceof NomadsRequiem || getControlerManager().getControler() instanceof QueenBlackDragonController || getControlerManager().getControler() instanceof WarControler) {
			return false;
		}
		if (getControlerManager().getControler() instanceof CrucibleControler) {
			CrucibleControler controler = (CrucibleControler) getControlerManager().getControler();
			return !controler.isInside();
		}
		return true;
	}

	public long getPolDelay() {
		return polDelay;
	}

	public void setTreeDamage(int damage) {
		treeDamage = damage;
		return;
	}

	public void addPolDelay(long delay) {
		polDelay = delay + Utils.currentTimeMillis();
	}

	public void setPolDelay(long delay) {
		this.polDelay = delay;
	}

	public List<Integer> getSwitchItemCache() {
		return switchItemCache;
	}

	public AuraManager getAuraManager() {
		return auraManager;
	}

	public boolean allowsProfanity() {
		return allowsProfanity;
	}

	public void switchProfanityFilter() {
		profanityFilter = !profanityFilter;
		refreshProfanityFilter();
	}

	public void refreshProfanityFilter() {
		getVarsManager().sendVarBit(8780, profanityFilter ? 0 : 1);
	}

	public void allowsProfanity(boolean allowsProfanity) {
		this.allowsProfanity = allowsProfanity;
	}

	public int getMovementType() {
		if (getTemporaryMoveType() != -1)
			return getTemporaryMoveType();
		return getRun() ? RUN_MOVE_TYPE : WALK_MOVE_TYPE;
	}

	public List<String> getOwnedObjectManagerKeys() {
		if (ownedObjectsManagerKeys == null) // temporary
			ownedObjectsManagerKeys = new LinkedList<String>();
		return ownedObjectsManagerKeys;
	}

	public boolean hasInstantSpecial(final int weaponId) {
		switch (weaponId) {
		case 4153:
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
		case 1377:
		case 13472:
		case 35:// Excalibur
		case 8280:
		case 14632:
			return true;
		default:
			return false;
		}
	}

	public void performInstantSpecial(final int weaponId) {
		if (weaponId == -1)
			return;
		final ItemDefinitions defs = ItemDefinitions.getItemDefinitions(weaponId);
		if (defs == null)
			return;
		int specAmt = defs.getSpecialAttackAmount() / 10;
		if (combatDefinitions.hasRingOfVigour())
			specAmt *= 0.9;
		if (combatDefinitions.getSpecialAttackPercentage() < specAmt) {
			getPackets().sendGameMessage("You don't have enough power left.");
			combatDefinitions.desecreaseSpecialAttack(0);
			return;
		}
		if (this.getSwitchItemCache().size() > 0) {
			ButtonHandler.submitSpecialRequest(this);
			return;
		}
		switch (weaponId) {
		case 4153:
			PlayerCombat.handleGraniteMaul(this, specAmt);
			break;
		case 1377:
		case 13472:
			setNextAnimation(new Animation(1056));
			setNextGraphics(new Graphics(246));
			setNextForceTalk(new ForceTalk("Raarrrrrgggggghhhhhhh!"));
			int defence = (int) (skills.getLevelForXp(Skills.DEFENCE) * 0.90D);
			int attack = (int) (skills.getLevelForXp(Skills.ATTACK) * 0.90D);
			int range = (int) (skills.getLevelForXp(Skills.RANGE) * 0.90D);
			int magic = (int) (skills.getLevelForXp(Skills.MAGIC) * 0.90D);
			int strength = (int) (skills.getLevelForXp(Skills.STRENGTH) * 1.2D);
			skills.set(Skills.DEFENCE, defence);
			skills.set(Skills.ATTACK, attack);
			skills.set(Skills.RANGE, range);
			skills.set(Skills.MAGIC, magic);
			skills.set(Skills.STRENGTH, strength);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 35:// Excalibur
		case 8280:
		case 14632:
			setNextAnimation(new Animation(1168));
			setNextGraphics(new Graphics(247));
			setNextForceTalk(new ForceTalk("For " + Settings.SERVER_NAME + "!"));
			final boolean enhanced = weaponId == 14632;
			skills.set(Skills.DEFENCE, enhanced ? (int) (skills.getLevelForXp(Skills.DEFENCE) * 1.15D) : (skills.getLevel(Skills.DEFENCE) + 8));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 5;

				@Override
				public void run() {
					if (isDead() || hasFinished() || getHitpoints() >= getMaxHitpoints()) {
						stop();
						return;
					}
					heal(enhanced ? 80 : 40);
					if (count-- == 0) {
						stop();
						return;
					}
				}
			}, 4, 2);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
			setNextAnimation(new Animation(12804));
			setNextGraphics(new Graphics(2319));// 2320
			setNextGraphics(new Graphics(2321));
			addPolDelay(60000);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		}
	}

	public void setDisableEquip(boolean equip) {
		disableEquip = equip;
	}

	public boolean isEquipDisabled() {
		return disableEquip;
	}

	public void addDisplayTime(long i) {
		this.displayTime = i + Utils.currentTimeMillis();
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public int getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(int publicStatus) {
		this.publicStatus = publicStatus;
	}

	public boolean isFilteringProfanity() {
		return profanityFilter;
	}

	public int[] getClanCapeCustomized() {
		return clanCapeCustomized;
	}

	public void setClanCapeCustomized(int[] clanCapeCustomized) {
		this.clanCapeCustomized = clanCapeCustomized;
	}

	public int[] getClanCapeSymbols() {
		return clanCapeSymbols;
	}

	public void setClanCapeSymbols(int[] clanCapeSymbols) {
		this.clanCapeSymbols = clanCapeSymbols;
	}

	public int getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public int getAssistStatus() {
		return assistStatus;
	}

	public void setAssistStatus(int assistStatus) {
		this.assistStatus = assistStatus;
	}

	public boolean isSpawnsMode() {
		return spawnsMode;
	}

	public void setSpawnsMode(boolean spawnsMode) {
		this.spawnsMode = spawnsMode;
	}

	public Notes getNotes() {
		return notes;
	}

	public int getCannonBalls() {
		return cannonBalls;
	}

	public void addCannonBalls(int cannonBalls) {
		this.cannonBalls += cannonBalls;
	}

	public void removeCannonBalls() {
		this.cannonBalls = 0;
	}

	public int getHouseX() {
		return houseX;
	}

	public void setHouseX(int houseX) {
		this.houseX = houseX;
	}

	public int getHouseY() {
		return houseY;
	}

	public void setHouseY(int houseY) {
		this.houseY = houseY;
	}

	public boolean hasBeenToHouse() {
		return hasBeenToHouse;
	}

	public void setBeenToHouse(boolean flag) {
		hasBeenToHouse = flag;
	}

	public int[] getBoundChuncks() {
		return boundChuncks;
	}

	public void setBoundChuncks(int[] boundChuncks) {
		this.boundChuncks = boundChuncks;
	}

	public List<WorldObject> getConObjectsToBeLoaded() {
		return conObjectsToBeLoaded;
	}

	public boolean isBuildMode() {
		return buildMode;
	}

	public void setBuildMode(boolean buildMode) {
		this.buildMode = buildMode;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public List<RoomReference> getRooms() {
		return rooms;
	}

	public RoomReference getCurrentRoom() {
		for (RoomReference r : rooms) {
			if (r.getX() == this.getX() && r.getY() == this.getY()) {
				return r;
			}
		}
		return null;
	}

	public House getHouse() {
		return house;
	}

	public JoinInstance getJoinInstance() {
		return joininstance;
	}

	public Player getinstancepartner() {
		return instancepartner;
	}

	public int getRoomX() {
		return Math.round(getXInRegion() / 8);
	}

	public int getRoomY() {
		return Math.round(getYInRegion() / 8);
	}

	public RoomReference getRoomReference() {
		return roomReference;
	}

	public HouseLocation getHouseLocation() {
		return portalLocation;
	}

	public void setHouseLocation(HouseLocation h) {
		this.portalLocation = h;
	}

	public boolean isHasConfirmedRoomDeletion() {
		return hasConfirmedRoomDeletion;
	}

	public void setHasConfirmedRoomDeletion(boolean hasConfirmedRoomDeletion) {
		this.hasConfirmedRoomDeletion = hasConfirmedRoomDeletion;
	}

	public ServantType getButler() {
		return butler;
	}

	public void setButler(ServantType butler) {
		this.butler = butler;
	}

	public RoomReference getRoomFor(int x, int y) {
		for (RoomReference r : this.getRooms()) {
			if (r.getX() == x && r.getY() == y) {
				return r;
			}
		}
		return null;
	}

	public SquealOfFortune getSquealOfFortune() {
		return squealOfFortune;
	}

	public IsaacKeyPair getIsaacKeyPair() {
		return isaacKeyPair;
	}

	public boolean isCompletedFightCaves() {
		return completedFightCaves;
	}

	public void setCompletedFightCaves() {
		if (!completedFightCaves) {
			completedFightCaves = true;
			refreshFightKilnEntrance();
		}
	}

	public boolean isCompletedFightKiln() {
		return completedFightKiln;
	}

	public void setCompletedFightKiln() {
		completedFightKiln = true;
	}

	public void setCompletedPestInvasion() {
		completedPestInvasion = true;
	}

	public boolean isWonFightPits() {
		return wonFightPits;
	}

	public void setWonFightPits() {
		wonFightPits = true;
	}

	public boolean isBot() {
		return isBot;
	}

	public void setBot(boolean isBot) {
		this.isBot = isBot;
	}

	public boolean isCantTrade() {
		return cantTrade;
	}

	public void setCantTrade(boolean canTrade) {
		this.cantTrade = canTrade;
	}

	public String getYellColor() {
		return yellColor;
	}

	public void setYellColor(String yellColor) {
		this.yellColor = yellColor;
	}

	/**
	 * Gets the pet.
	 * 
	 * @return The pet.
	 */
	public Pet getPet() {
		return pet;
	}

	/**
	 * Sets the pet.
	 * 
	 * @param pet
	 *            The pet to set.
	 */
	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public boolean isSupporter() {
		return isSupporter;
	}

	public void setSupporter(boolean isSupporter) {
		this.isSupporter = isSupporter;
	}

	public SlayerTaskHandler getSlayerTask() {
		return currentSlayerTask;
	}

	public void setSlayerTask(SlayerTaskHandler slayerTask) {
		this.currentSlayerTask = slayerTask;
	}

	/**
	 * Gets the petManager.
	 * 
	 * @return The petManager.
	 */
	public PetManager getPetManager() {
		return petManager;
	}

	/**
	 * Sets the petManager.
	 * 
	 * @param petManager
	 *            The petManager to set.
	 */
	public void setPetManager(PetManager petManager) {
		this.petManager = petManager;
	}

	public boolean isXpLocked() {
		return xpLocked;
	}

	public void setXpLocked(boolean locked) {
		this.xpLocked = locked;
	}

	public int getLastBonfire() {
		return lastBonfire;
	}

	public void setLastBonfire(int lastBonfire) {
		this.lastBonfire = lastBonfire;
	}

	public boolean isYellOff() {
		return yellOff;
	}

	public void setYellOff(boolean yellOff) {
		this.yellOff = yellOff;
	}

	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	public double getHpBoostMultiplier() {
		return hpBoostMultiplier;
	}

	public void setHpBoostMultiplier(double hpBoostMultiplier) {
		this.hpBoostMultiplier = hpBoostMultiplier;
	}

	public void sm(String message) {
		if (message == null)
			return;
		getPackets().sendGameMessage(message);

	}

	public static void mutes(Player player, String message) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "punishments/mutes.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has muted " + message);
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public static void bans(Player player, String message) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "punishments/bans.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has banned " + message);
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public static void jails(Player player, String message) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "punishments/jails.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has jailed " + message);
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	public static void ipbans(Player player, String message) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "punishments/ipbans.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has ipbanned " + message);
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	/**
	 * Gets the killedQueenBlackDragon.
	 * 
	 * @return The killedQueenBlackDragon.
	 */
	public boolean isKilledQueenBlackDragon() {
		return killedQueenBlackDragon;
	}

	/**
	 * Sets the killedQueenBlackDragon.
	 * 
	 * @param killedQueenBlackDragon
	 *            The killedQueenBlackDragon to set.
	 */
	public void setKilledQueenBlackDragon(boolean killedQueenBlackDragon) {
		this.killedQueenBlackDragon = killedQueenBlackDragon;
	}

	public boolean hasLargeSceneView() {
		return largeSceneView;
	}

	public void setLargeSceneView(boolean largeSceneView) {
		this.largeSceneView = largeSceneView;
	}

	public boolean isOldItemsLook() {
		return oldItemsLook;
	}

	public void switchItemsLook() {
		oldItemsLook = !oldItemsLook;
		getPackets().sendItemsLook();
	}

	public BossTimerManager getBossTimerManager() {
		return bossTimerManager;
	}

	/**
	 * @return the runeSpanPoint
	 */
	public int getRuneSpanPoints() {
		return runeSpanPoints;
	}

	/**
	 * @param runeSpanPoint
	 *            the runeSpanPoint to set
	 */
	public void setRuneSpanPoint(int runeSpanPoints) {
		this.runeSpanPoints = runeSpanPoints;
	}

	/**
	 * Adds points
	 * 
	 * @param points
	 */
	public void addRunespanPoints(int points) {
		this.runeSpanPoints += points;
	}

	/**
	 * Warriors Guild Stuff
	 */

	public int getWGuildTokens() {
		return wGuildTokens;
	}

	public void setWGuildTokens(int tokens) {
		wGuildTokens = tokens;
	}

	public boolean inClopsRoom() {
		return inClops;
	}

	public void setInClopsRoom(boolean in) {
		inClops = in;
	}

	public DuelRules getLastDuelRules() {
		return lastDuelRules;
	}

	public void setLastDuelRules(DuelRules duelRules) {
		this.lastDuelRules = duelRules;
	}

	public boolean isTalkedWithMarv() {
		return talkedWithMarv;
	}

	public void setTalkedWithMarv() {
		talkedWithMarv = true;
	}

	public int getCrucibleHighScore() {
		return crucibleHighScore;
	}

	public void increaseCrucibleHighScore() {
		crucibleHighScore++;
	}

	public int getLastLoggedIn() {
		return lastlogged;
	}

	/**
	 * MoneyPouch Stuff
	 */

	public double getMoneyInPouch() {
		return moneyInPouch;
	}

	public void setMoneyInPouch(int totalCash) {
		moneyInPouch = totalCash;
	}

	public void addMoneyToPouch(int add) {
		moneyInPouch += add;
	}

	public void removeMoneyFromPouch(int remove) {
		moneyInPouch -= remove;
	}

	public void moneyPouchPow(int power) {
		moneyInPouch = Math.pow(moneyInPouch, power);
	}

	public MoneyPouch getMoneyPouch() {
		return moneyPouch;
	}

	public void sendMessage(String message) {
		getPackets().sendGameMessage(message);
	}

	public void setWeapon(int itemId) {
		getEquipment().deleteItem(itemId, 1);
		getEquipment().getItems().set(Equipment.SLOT_WEAPON, new Item(itemId, 1));
		getEquipment().refresh(Equipment.SLOT_WEAPON);
		getAppearence().generateAppearenceData();
	}

	public void setShield(int itemId) {
		getEquipment().getItems().set(Equipment.SLOT_SHIELD, new Item(itemId, 1));
		getEquipment().refresh(Equipment.SLOT_SHIELD);
		getAppearence().generateAppearenceData();
	}

	public void updateSheathing() {
		getEquipment().refresh(Equipment.SLOT_SHIELD);
		getEquipment().refresh(Equipment.SLOT_WEAPON);
		getAppearence().generateAppearenceData();
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}

	public boolean isVeteran() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addGEAttribute(String string, int myBox) {
		// TODO Auto-generated method stub

	}

	public Object getGEAttribute(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeGEAttribute(String string) {
		// TODO Auto-generated method stub

	}

	public LodeStone getLodeStones() {
		return lodeStone;
	}

	public ReportAbuse getReportAbuse() {
		return reportAbuse;
	}

	public boolean[] getActivatedLodestones() {
		return activatedLodestones;
	}

	public List<String> getCachedChatMessages() {
		return cachedChatMessages;
	}

	public void setCachedChatMessages(List<String> cachedChatMessages) {
		this.cachedChatMessages = cachedChatMessages;
	}

	public Toolbelt getToolbelt() {
		return newToolbelt;
	}

	/*
	 * default items on death, now only used for wilderness
	 */
	public void sendItemsOnDeath(Player killer, boolean dropItems) {
		Integer[][] slots = GraveStone.getItemSlotsKeptOnDeath(this, true, dropItems, getPrayer().isProtectingItem());
		sendItemsOnDeath(killer, new WorldTile(this), new WorldTile(this), true, slots);
	}

	/*
	 * default items on death, now only used for wilderness
	 */
	public void sendItemsOnDeath(Player killer) {
		if (rights == 2)
			return;
		charges.die();
		auraManager.removeAura();
		CopyOnWriteArrayList<Item> containedItems = new CopyOnWriteArrayList<Item>();
		for (int i = 0; i < 14; i++) {
			if (equipment.getItem(i) != null && equipment.getItem(i).getId() != -1 && equipment.getItem(i).getAmount() != -1)
				containedItems.add(new Item(equipment.getItem(i).getId(), equipment.getItem(i).getAmount()));
		}
		for (int i = 0; i < 28; i++) {
			if (inventory.getItem(i) != null && inventory.getItem(i).getId() != -1 && inventory.getItem(i).getAmount() != -1)
				containedItems.add(new Item(getInventory().getItem(i).getId(), getInventory().getItem(i).getAmount()));
		}
		if (containedItems.isEmpty())
			return;
		int keptAmount = 0;
		if (!(controlerManager.getControler() instanceof CorpBeastControler) && !(controlerManager.getControler() instanceof CrucibleControler)) {
			keptAmount = hasSkull() ? 0 : 3;
			if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0))
				keptAmount++;
		}
		if (donator && Utils.random(2) == 0)
			keptAmount += 1;
		if (getInventory().contains(28742) || getEquipment().getWeaponId() == 28742)
			keptAmount = 0;
		CopyOnWriteArrayList<Item> keptItems = new CopyOnWriteArrayList<Item>();
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = item.getDefinitions().getValue();
				if (price >= lastItem.getDefinitions().getValue()) {
					lastItem = item;
				}
			}
			keptItems.add(lastItem);
			containedItems.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		inventory.reset();
		equipment.reset();
		for (Item item : keptItems) {
			if (item.getId() == -1)
				continue;
			getInventory().addItem(item);
		}

		for (Item item : containedItems) {
			World.addGroundItem(item, getLastWorldTile(), killer, true, 2000000000);
		}
	}

	public void sendItemsOnDeath(Player killer, WorldTile deathTile, WorldTile respawnTile, boolean wilderness, Integer[][] slots) {
		if (World.isMiniGame(deathTile)) {
			// this.setNextWorldTile(new WorldTile(2649, 9393, 0));
			sendMessage("Since you died in the minigame area you will not lose your items");
			Nstage1 = 0;
			Nstage2 = 0;
			Nstage3 = 0;
		} else {
			charges.die(slots[1], slots[3]); // degrades droped and lost items
												// only
			auraManager.removeAura();
			Item[][] items = GraveStone.getItemsKeptOnDeath(this, slots);
			inventory.reset();
			equipment.reset();
			appearence.generateAppearenceData();
			for (Item item : items[0])
				inventory.addItemDrop(item.getId(), item.getAmount(), respawnTile);
			if (items[1].length != 0) {
				if (wilderness) {
					for (Item item : items[1])
						World.addGroundItem(item, deathTile, killer == null ? this : killer, true, 60, 0);
				} else
					new GraveStone(this, deathTile, items[1]);
			}
		}
	}

	public int getGraveStone() {
		return gStone;
	}

	public void setGraveStone(int graveStone) {
		this.gStone = graveStone;
	}

	public void setClanChatSetup(int clanChatSetup) {
		this.clanChatSetup = clanChatSetup;
	}

	public void setGuestChatSetup(int guestChatSetup) {
		this.guestChatSetup = guestChatSetup;
	}

	public void kickPlayerFromClanChannel(String name) {
		if (clanManager == null)
			return;
		clanManager.kickPlayerFromChat(this, name);
	}

	public void sendClanChannelMessage(ChatMessage message) {
		if (clanManager == null)
			return;
		clanManager.sendMessage(this, message);
	}

	public void sendGuestClanChannelMessage(ChatMessage message) {
		if (guestClanManager == null)
			return;
		guestClanManager.sendMessage(this, message);
	}

	public void sendClanChannelQuickMessage(QuickChatMessage message) {
		if (clanManager == null)
			return;
		clanManager.sendQuickMessage(this, message);
	}

	public void sendGuestClanChannelQuickMessage(QuickChatMessage message) {
		if (guestClanManager == null)
			return;
		guestClanManager.sendQuickMessage(this, message);
	}

	public int getClanStatus() {
		return clanStatus;
	}

	public void setClanStatus(int clanStatus) {
		this.clanStatus = clanStatus;
	}

	public ClansManager getClanManager() {
		return clanManager;
	}

	public void setClanManager(ClansManager clanManager) {
		this.clanManager = clanManager;
	}

	public ClansManager getGuestClanManager() {
		return guestClanManager;
	}

	public void setGuestClanManager(ClansManager guestClanManager) {
		this.guestClanManager = guestClanManager;
	}

	public String getClanName() {
		return clanName;
	}

	public void setClanName(String clanName) {
		this.clanName = clanName;
	}

	public boolean isConnectedClanChannel() {
		return connectedClanChannel;
	}

	public void setConnectedClanChannel(boolean connectedClanChannel) {
		this.connectedClanChannel = connectedClanChannel;
	}

	public GrandExchangeManager getGeManager() {
		return geManager;
	}

	public double[] getWarriorPoints() {
		return warriorPoints;
	}

	public void setWarriorPoints(int index, double pointsDifference) {
		warriorPoints[index] += pointsDifference;
		if (warriorPoints[index] < 0) {
			Controler controler = getControlerManager().getControler();
			if (controler == null || !(controler instanceof WarriorsGuild))
				return;
			WarriorsGuild guild = (WarriorsGuild) controler;
			guild.inCyclopse = false;
			setNextWorldTile(WarriorsGuild.CYCLOPS_LOBBY);
			warriorPoints[index] = 0;
		} else if (warriorPoints[index] > 65535)
			warriorPoints[index] = 65535;
		refreshWarriorPoints(index);
	}

	public void refreshWarriorPoints(int index) {
		varsManager.sendVarBit(index + 8662, (int) warriorPoints[index]);
	}

	private void warriorCheck() {
		if (warriorPoints == null || warriorPoints.length != 6)
			warriorPoints = new double[6];
	}

	public boolean containsOneItem(int... itemIds) {
		if (getInventory().containsOneItem(itemIds))
			return true;
		if (getEquipment().containsOneItem(itemIds))
			return true;
		Familiar familiar = getFamiliar();
		if (familiar != null && ((familiar.getBob() != null && familiar.getBob().containsOneItem(itemIds) || familiar.isFinished())))
			return true;
		return false;
	}

	public int getFinishedCastleWars() {
		return finishedCastleWars;
	}

	public boolean isCapturedCastleWarsFlag() {
		return capturedCastleWarsFlag;
	}

	public void setCapturedCastleWarsFlag() {
		capturedCastleWarsFlag = true;
	}

	public void increaseFinishedCastleWars() {
		finishedCastleWars++;
	}

	@Override
	public WorldTile getTile() {
		return new WorldTile(getX(), getY(), getPlane());
	}

	public void setVerboseShopDisplayMode(boolean verboseShopDisplayMode) {
		this.verboseShopDisplayMode = verboseShopDisplayMode;
		refreshVerboseShopDisplayMode();
	}

	public void refreshVerboseShopDisplayMode() {
		varsManager.sendVarBit(11055, verboseShopDisplayMode ? 0 : 1);
	}

	public BankPin getBankPin() {
		return pin;
	}

	public boolean getSetPin() {
		return setPin;
	}

	public boolean getOpenedPin() {
		return openPin;
	}

	public int[] getPin() {
		return bankpins;
	}

	public int[] getConfirmPin() {
		return confirmpin;
	}

	public int[] getOpenBankPin() {
		return openBankPin;
	}

	public int[] getChangeBankPin() {
		return changeBankPin;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void resetToolbelt() {
		newToolbelt = new Toolbelt();
		newToolbelt.setPlayer(this);
	}

	public void ecoReset() {
		inventory.reset();
		moneyPouch = new MoneyPouch();
		moneyPouch.setPlayer(this);
		equipment.reset();
		newToolbelt = new Toolbelt();
		newToolbelt.setPlayer(this);
		familiar = null;
		bank = new Bank();
		bank.setPlayer(this);
		controlerManager.removeControlerWithoutCheck();
		choseGameMode = true;
		Starter.appendStarter(this);
		setNextWorldTile(Settings.START_PLAYER_LOCATION);
		setPestPoints(0);
		getPlayerData().setInvasionPoints(0);
		hasHouse = false;
		getSquealOfFortune().resetSpins();
		customShop = new CustomisedShop(this);
		geManager = new GrandExchangeManager();
	}

	@Override
	public void faceObject(WorldObject object) {
		ObjectDefinitions objectDef = object.getDefinitions();
		setNextFaceWorldTile(new WorldTile(object.getCoordFaceX(objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()), object.getCoordFaceY(objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()), object.getPlane()));
	}

	/**
	 * used for bossing count
	 **/
	public HashMap<Integer, Integer> bossCount = new HashMap<>();

	public HashMap<Integer, Integer> getBossCount() {
		return bossCount;
	}

	/**
	 * used for item drop count
	 **/
	public HashMap<Integer, Integer> dropCount = new HashMap<>();

	public HashMap<Integer, Integer> getdropCount() {
		return bossCount;
	}

	/**
	 * used for slayer creatures count
	 **/
	public HashMap<String, Integer> slayerCreaturesCount = new HashMap<>();

	public HashMap<String, Integer> getSlayerCreaturesCount() {
		return slayerCreaturesCount;
	}
	/**
	 * slayer mask counts
	 */
	public HashMap<String, Integer> slayerMaskCreaturesCount;

	public HashMap<String, Integer> getSlayerMaskCreaturesCount() {
		return slayerMaskCreaturesCount;
	}
	
	public List<Integer> sofItems2;
	
	public List<Integer> getSofItems2(){
		return sofItems2;
	}

	public boolean completedPvmQuest = false;
	// prehistorical npc dagame
	private int PAdamage;

	public void setPAdamage(int PAdamage) {
		this.PAdamage = PAdamage;
	}

	public int getPAdamage() {
		return PAdamage;
	}

	/**
	 * House looks
	 **/
	private int HouseLook = 5;
	public int DFS;
	public boolean hiddenTrapdoor;

	private int bloodcharges = 0;

	public int getHouseLook() {
		return HouseLook;
	}

	public void setHouseLook(int HouseLook) {
		this.HouseLook = HouseLook;
	}
	// slayer masters

	public int getSilverhawkFeathers() {
		return silverhawkFeathers;
	}

	public void setSilverhawkFeathers(int silverhawkFeathers) {
		this.silverhawkFeathers = silverhawkFeathers;
	}

	public int getBloodcharges() {
		return bloodcharges;
	}

	public void setBloodcharges(int bloodcharges) {
		Player.bloodcharges = bloodcharges;
	}

	public VarBitManager getVarBitManager() {
		return VBM;
	}

	private boolean[] prayerBook;

	public boolean[] getPrayerBook() {
		return prayerBook;
	}

	public PlayerDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(PlayerDefinition definition) {
		this.definition = definition;
	}

	public void handleProtectPrayers(final Hit hit) {
		Entity source = hit.getSource();
		Player p2 = (Player) source;
		if (PlayerCombat.fullVeracsEquipped(p2) && Utils.getRandom(5) == 0)
			return;
		if (prayer.hasPrayersOn() && hit.getDamage() != 0) {
			if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				if (prayer.usingPrayer(0, 17)) {
					hit.setDamage((int) (hit.getDamage() * source.getMagePrayerMultiplier()));
				} else if (prayer.usingPrayer(1, 7)) {
					int deflectedDamage = source instanceof Nex ? 0 : (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source.getMagePrayerMultiplier()));
					if (Utils.getRandom(2) <= 1 && hit.getDamage() > 10) {
						source.applyHit(new Hit(this, deflectedDamage, HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2228));
						setNextAnimationNoPriority(new Animation(12573), this);
					}
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				if (prayer.usingPrayer(0, 18)) {
					hit.setDamage((int) (hit.getDamage() * source.getRangePrayerMultiplier()));
				} else if (prayer.usingPrayer(1, 8)) {
					int deflectedDamage = source instanceof Nex ? 0 : (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source.getRangePrayerMultiplier()));
					if (Utils.getRandom(2) <= 1 && hit.getDamage() > 10) {
						source.applyHit(new Hit(this, deflectedDamage, HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2229));
						setNextAnimationNoPriority(new Animation(12573), this);
					}
				}

			} else if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				if (prayer.usingPrayer(0, 19)) {
					hit.setDamage((int) (hit.getDamage() * source.getMeleePrayerMultiplier()));
				} else if (prayer.usingPrayer(1, 9)) {
					int deflectedDamage = source instanceof Nex ? 0 : (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source.getMeleePrayerMultiplier()));
					if (Utils.getRandom(2) <= 1 && hit.getDamage() > 10) {
						source.applyHit(new Hit(this, deflectedDamage, HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2230));
						setNextAnimationNoPriority(new Animation(12573), this);
					}
				}
			}
		}
	}

	public int recoilHits;

	public int getRecoilHits() {
		return recoilHits;
	}

	public void setRecoilHits(int recoilHits) {
		this.recoilHits = recoilHits;
	}

	private int damage;

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	private int ep;

	public int getEp() {
		return ep;
	}

	public void setEp(int ep) {
		this.ep = ep;
	}

	public boolean isInCombat(int milliseconds) {
		return getAttackedByDelay() + milliseconds > Utils.currentTimeMillis();
	}

	public Entity setTargetName(Player player) {
		return combatTarget = player;
	}

	public Entity combatTarget;

	public Entity getTarget() {
		return combatTarget;
	}

	public boolean usingDisruption;
	public int disDelay;
	public boolean hasUsedDS;
	public boolean castedDS;
	public transient long teleportDelay;

	public boolean familiarAutoAttack = false;
	public int storedScrolls;

	private transient long familiarDelay;

	public void addFamiliarDelay(long time) {
		familiarDelay = time + Utils.currentTimeMillis();
	}

	public long getFamiliarDelay() {
		return familiarDelay;
	}

	public boolean hasUsedDS() {
		return hasUsedDS;
	}

	public void setDS(boolean hasUsedDS) {
		this.hasUsedDS = hasUsedDS;
	}
	
	public double getXPRate() {
		double rate = 0;
		switch (gameMode) {
		case 0:
			return rate;
		}
		if (ironMan)
			return rate;
		if (isHeroicDonator())
			rate += 6.0;
		else if (isDemonicDonator())
			rate += 4.0;
		else if (isAngelicDonator())
			rate += 2.0;
		else if (isDivineDonator())
			rate += 1.5;
		else if (isSupremeDonator())
			rate += 1;
		else if (isLegendaryDonator())
			rate += 0.75;
		else if (isExtremeDonator())
			rate += 0.4;
		else if (isDonator())
			rate += 0.2;
		return rate * (1 / ((getGameMode() * 0.2) + 1));
	}

	private boolean isDemonicDonator() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isHeroicDonator() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCastDS() {
		return castedDS;
	}

	public void setCastDS(boolean castedDS) {
		this.castedDS = castedDS;
	}

	public void publicChat(String message) {
		sendPublicChatMessage(new PublicChatMessage(message, 0));
	}

	public void publicChat(String message, int effects) {
		sendPublicChatMessage(new PublicChatMessage(message, effects));
	}

	public void setContract(Contract contract) {
		this.cContracts = contract;
	}

	public Contract getContract() {
		return cContracts;
	}

	public ContractHandler getCHandler() {
		return cHandler;
	}

	private int ReaperPoints;

	public int getReaperPoints() {
		return ReaperPoints;
	}

	public void setReaperPoints(int ReaperPoints) {
		this.ReaperPoints = ReaperPoints;
	}

	private int totalkills;

	public int getTotalKills() {
		return totalkills;
	}

	public void setTotalKills(int totalkills) {
		this.totalkills = totalkills;
	}

	private int totalcontract;
	public boolean eliteDungeon;

	public int getTotalContract() {
		return totalcontract;
	}

	public void setTotalContract(int totalcontract) {
		this.totalcontract = totalcontract;
	}

	/**
	 * Used for checking if the player has money.
	 * 
	 * @param amount
	 *            the Amount to check for.
	 * @return if the player has the required amount either in their money pouch
	 *         or their inventory.
	 */
	public boolean hasMoney(int amount) {
		int money = getInventory().getNumerOf(995) + getMoneyPouch().getTotal();
		return money >= amount;
	}

	public boolean isUnderCombat(int tryCount) {
		return (getAttackedByDelay() + 10000 > Utils.currentTimeMillis());
	}

	public List<String> getBlockedSlayerTasks() {
		return blockedSlayerTasks;
	}

	public void setBlockedSlayerTasks(ArrayList<String> blockedSlayerTasks) {
		this.blockedSlayerTasks = blockedSlayerTasks;
	}

	public SecondsTimer getDoubleExpTime() {
		return doubleExpTime;
	}

	public void setDoubleExpTime(SecondsTimer doubleExpTime) {
		this.doubleExpTime = doubleExpTime;
	}

	private transient RouteEvent routeEvent;

	public void setRouteEvent(RouteEvent routeEvent) {
		this.routeEvent = routeEvent;
	}

	/**
	 * Player Owned Port.
	 */
	public PlayerOwnedPort ports;

	public PlayerOwnedPort getPorts() {
		return ports;
	}

	/**
	 * Checks if the Player has the item.
	 * 
	 * @param item
	 *            The item to check.
	 * @return if has item or not.
	 */
	public boolean hasItem(Item item) {
		if (getInventory().contains(item))
			return true;
		if (getEquipment().getItemsContainer().contains(item))
			return true;
		if (getBank().getItem(item.getId()) != null)
			return true;
		return false;
	}

	/**
	 * handles the frozen key
	 */
	private byte frozenKeyCharges;

	public byte getFrozenKeyCharges() {
		return frozenKeyCharges;
	}

	public void setFrozenKeyCharges(byte charges) {
		this.frozenKeyCharges = charges;
	}

	/**
	 * for player owned shops
	 */
	private long shopVault;

	public long getVault() {
		return shopVault;
	}

	public void setVault(long value) {
		shopVault = value;
	}

	public PlayerDefinition definition() {
		return this.getDefinition();
	}

	/**
	 * Daily tasks
	 */
	private DailyTasks daily;

	public DailyTasks getDailyTask() {
		return daily;
	}

	public DailyTasks setDailyTask(DailyTasks daily) {
		return this.daily = daily;
	}

	public boolean completedDaily;
	public int dailyDate;

	public void setDailyDate(int date) {
		this.dailyDate = date;
	}

	public int getDailyDate() {
		return dailyDate;
	}

	/**
	 * Used for daily tasks task filtering
	 */
	public boolean settings[] = new boolean[24];
	public boolean setSettings;
	public int toggledAmount = 0;

	/**
	 * dungeoneering
	 * 
	 * @return
	 */
	public DungManager getDungManager() {
		return dungManager;
	}

	public void setDungManager(DungManager dungManager) {
		this.dungManager = dungManager;
	}

	public transient boolean cantWalk;
	public int hofPage;

	public boolean isCantWalk() {
		return cantWalk;
	}

	public void setCantWalk(boolean cantWalk) {
		this.cantWalk = cantWalk;
	}

	@Override
	public boolean canMove(int dir) {
		// TODO Auto-generated method stub
		return false;
	}

	private ArrayList<Integer> unlockedCostumesIds;

	public boolean isLockedCostume(int itemId) {
		return !unlockedCostumesIds.contains(itemId);
	}

	public ArrayList<Integer> getUnlockedCostumesIds() {
		return unlockedCostumesIds;
	}

	private boolean showSearchOption;

	public boolean isShowSearchOption() {
		return showSearchOption;
	}

	public void setShowSearchOption(boolean showSearchOption) {
		this.showSearchOption = showSearchOption;
	}

	// robot stuff
	/*
	 * Robot Stuff
	 */
	public Player(WorldTile tile) {// for robot
		super(tile);
	}

	public boolean isRobot() {
		return false;
	}

	public boolean isScriptingEnabled() {
		return false;
	}

	public RobotScript script;

	public RobotScript getScript() {
		return null;
	}

	public RobotScript getRandomScript() {
		int rnd = Utils.getRandom(100);
		if (rnd < 10)// 10%
			return new AncientHybrid();
		else if (rnd < 20)// 10%
			return new AncientMage();
		else if (rnd < 30)// 10%
			return new AncientTribrid();
		else if (rnd < 40)// 10%
			return new RegularMage();
		else if (rnd < 50)// 10%
			return new RegularTribrid();
		else if (rnd < 60)// 10%
			return new Range();
		else
			// 40%
			return new Melee();
	}

	public transient HashMap<Integer, int[]> cheatSwitch;

	public void enableCheatSwitch() {
		cheatSwitch = new HashMap<>();
		Item[] equipmentItems = getEquipment().getItems().getItemsCopy();
		List<Integer> toSwitch = new ArrayList<>();
		for (int i = 0; i < equipmentItems.length; i++) {
			if (equipmentItems[i] == null)
				continue;
			toSwitch.add(equipmentItems[i].getId());
		}
		cheatSwitch.put(1, Robot.toIntArray(toSwitch));
		toSwitch.clear();
		int type = 2;
		Item[] inventoryItems = getInventory().getItems().getItemsCopy();
		for (int i = 0; i < inventoryItems.length; i++) {
			if (inventoryItems[i] == null)
				continue;
			if (Foods.Food.forId(inventoryItems[i].getId()) != null) {
				cheatSwitch.put(type++, Robot.toIntArray(toSwitch));
				toSwitch.clear();
				if (type == 8)
					break;
				continue;
			}
			toSwitch.add(inventoryItems[i].getId());
		}
	}

	public int[] donators = new int[] { 24950, 24954, 29840, 24952, 24953, 29841, 29842 };

	public boolean brokeRecord;
	public boolean isInHOF;
	public boolean isComunityHelper;
	public boolean redStoner;
	public int timerPage;
	public int maxCapeInterface;
	public boolean showAchievementMessage;
	/*
	 * ascencion
	 */
	public int primusStage;
	public int quartusStage;
	public int quintusStageOneX;
	public int quintusStageOneY;
	public int quintusStage;
	public int quintusStageTwoY;
	public int quintusStageTwoX;
	public int quintusStageThreeX;
	public int quintusStageThreeY;
	public int sextusStage;
	public int tertiusStage;
	public int TertiusX;
	public int TertiusY;
	public boolean canCraftSlayerHelmtet;
	public int heffinStage;
	public boolean completedHeffinCourse;
	public int dfsCharges;
	public boolean locationToggle;


	public static void cheatPk(Player player, int key) {
		Item[] items2 = null;
		int[] switchitems = null;
		if (player.cheatSwitch == null)
			return;
		switch (key) {
		case 1:
			items2 = player.getInventory().getItems().getItemsCopy();
			Magic.setCombatSpell(player, 23);
			switchitems = new int[] { 1 };
			for (int id : switchitems) {
				for (int i = 0; i < items2.length; i++) {
					if (items2[i] == null)
						continue;
					for (int switchitem : player.cheatSwitch.get(id))
						if (items2[i].getId() == switchitem)
							ButtonHandler.sendWear(player, i, switchitem);
				}
			}
			if (player.getAttackedBy() != null) {
				player.stopAll(false);
				player.getActionManager().setAction(new PlayerCombat(player.getAttackedBy()));
			}
			return;
		case 2:
			items2 = player.getInventory().getItems().getItemsCopy();
			switchitems = new int[] { 2, 3, 6 };
			for (int id : switchitems) {
				for (int i = 0; i < items2.length; i++) {
					if (items2[i] == null)
						continue;
					for (int switchitem : player.cheatSwitch.get(id))
						if (items2[i].getId() == switchitem)
							ButtonHandler.sendWear(player, i, switchitem);
				}
			}
			break;
		case 3:
			items2 = player.getInventory().getItems().getItemsCopy();
			player.getCombatDefinitions().resetSpells(true);
			switchitems = new int[] { 2, 3, 7 };
			for (int id : switchitems) {
				for (int i = 0; i < items2.length; i++) {
					if (items2[i] == null)
						continue;
					for (int switchitem : player.cheatSwitch.get(id))
						if (items2[i].getId() == switchitem)
							ButtonHandler.sendWear(player, i, switchitem);
				}
			}
			break;
		case 4:
			items2 = player.getInventory().getItems().getItemsCopy();
			player.getCombatDefinitions().resetSpells(true);
			switchitems = new int[] { 2, 4 };
			for (int id : switchitems) {
				for (int i = 0; i < items2.length; i++) {
					if (items2[i] == null)
						continue;
					for (int switchitem : player.cheatSwitch.get(id))
						if (items2[i].getId() == switchitem)
							ButtonHandler.sendWear(player, i, switchitem);
				}
			}
			player.getCombatDefinitions().switchUsingSpecialAttack();
			break;
		case 5:
			items2 = player.getInventory().getItems().getItemsCopy();
			player.getCombatDefinitions().resetSpells(true);
			switchitems = new int[] { 5, 6 };
			for (int id : switchitems) {
				for (int i = 0; i < items2.length; i++) {
					if (items2[i] == null)
						continue;
					for (int switchitem : player.cheatSwitch.get(id))
						if (items2[i].getId() == switchitem)
							ButtonHandler.sendWear(player, i, switchitem);
				}
			}
			break;
		case 6:
			items2 = player.getInventory().getItems().getItemsCopy();
			player.stopAll(false);
			for (int i = 0; i < items2.length; i++) {
				if (items2[i] == null)
					continue;
				if (items2[i].getId() == 15272) {
					Foods.eat(player, items2[i], i);
					break;
				}
			}
			for (int i = 0; i < items2.length; i++) {
				if (items2[i] == null)
					continue;
				if (items2[i].getId() >= 6685 && items2[i].getId() <= 6691) {
					Pots.pot(player, items2[i], i);
					break;
				}
			}
			return;
		}
		player.getCombatDefinitions().resetSpells(true);
		if (player.getAttackedBy() != null) {
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerCombat(player.getAttackedBy()));
		}
	}

	public long getDropWealth() {
		if (rights >= 2) {
			return 0;
		}
		ArrayList<Item> containedItems = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			Item item = inventory.getItem(i);
			if (item != null)
				containedItems.add(item);
		}
		for (int i = 0; i < 28; i++) {
			Item item = inventory.getItem(i);
			if (item != null)
				containedItems.add(item);
		}
		if (containedItems.isEmpty()) {
			return 0;
		}
		int keptAmount = 3;
		if (hasSkull()) {
			keptAmount = 0;
		}
		if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0)) {
			keptAmount++;
		}
		ArrayList<Item> keptItems = new ArrayList<>();
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = item.getDefinitions().getValue();
				if (price >= lastItem.getDefinitions().getValue()) {
					lastItem = item;
				}
			}
			keptItems.add(lastItem);
			containedItems.remove(lastItem);
			lastItem = new Item(1, 1);
		}

		long riskAmount = 0;
		for (Item item : containedItems) {
			riskAmount += (item.getDefinitions().getValue() * item.getAmount());
		}
		return riskAmount;
	}

	/**
	 * Assuming one item will be kept
	 */
	public static long getDropWealth(Player player) {
		/*
		 * if (rights >= 2) { return 0; }
		 */
		ArrayList<Item> containedItems = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			Item item = player.getEquipment().getItem(i);// getCharges().itemOnDeathItem(equipment.getItem(i),
			// true);
			if (item != null)
				containedItems.add(item);
		}
		for (int i = 0; i < 28; i++) {
			Item item = player.getInventory().getItem(i);// getCharges().itemOnDeathItem(inventory.getItem(i),
			// true);
			if (item != null)
				containedItems.add(item);
		}
		if (containedItems.isEmpty()) {
			return 0;
		}
		int keptAmount = 1;
		// if (player.hasSkull()) {
		// keptAmount = 0;
		// }
		// if (player.getPrayer().usingPrayer(0, 10)
		// || player.getPrayer().usingPrayer(1, 0)) {
		// keptAmount++;
		// }
		ArrayList<Item> keptItems = new ArrayList<>();
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = item.getDefinitions().getValue();
				if (price >= lastItem.getDefinitions().getValue()) {
					lastItem = item;
				}
			}
			keptItems.add(lastItem);
			containedItems.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		long riskAmount = 0;
		for (Item item : containedItems) {
			riskAmount += (item.getDefinitions().getValue() * item.getAmount());
		}
		return riskAmount;
	}

	public String lastChatMessage() {
		return getLastMsg();
	}

	public WorldTile getHome() {
		return Settings.HOME_PLAYER_LOCATION[0];
	}

	/*
	 * default items on death, for robots in the wilderness
	 */
	public void sendRobotLoots(Player killer) {
		if (killer == null) {
			return;
		}
		if (rights >= 2 || killer.getRights() >= 2) {
			return;
		}
		charges.die();
		auraManager.removeAura();
		inventory.reset();
		equipment.reset();
		Item[] loots = { pvpRandomCommon[Misc.random(0, pvpRandomCommon.length - 1)], pvpRandomCommon[Misc.random(0, pvpRandomCommon.length - 1)], pvpRandomCommon[Misc.random(0, pvpRandomCommon.length - 1)] };
		for (Item item : loots) {
			World.addGroundItem(item, getLastWorldTile(), killer, true, 600000);// 10
			// Minutes
		}
	}

	/**
	 * @return the examined
	 */
	public static String getExamined() {
		return Examined;
	}

	/**
	 * @param examined the examined to set
	 */
	public void setExamined(String examined) {
		Examined = examined;
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay, int totalDelay, final String message, final boolean resetAnimation) {
		stopAll();
		lock(totalDelay);
		if (emoteId != -1)
			setNextAnimation(new Animation(emoteId));
		if (useDelay == 0)
			setNextWorldTile(dest);
		else {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (isDead())
						return;
					if (resetAnimation)
						setNextAnimation(new Animation(-1));
					setNextWorldTile(dest);
					if (message != null)
						getPackets().sendGameMessage(message);
				}
			}, useDelay - 1);
		}
	}
	
	public SkillingUrnsManager getUrns() {
		return urns;
	}


	/**
	 * @return the rep
	 */
	public Reputation getRep() {
		return rep;
	}

	/**
	 * @param rep the rep to set
	 */
	public void setRep(Reputation rep) {
		this.rep = rep;
	}
	/**
	 * @return the achievementManager
	 */
	public AchievementManager getAchievementManager() {
		return achievementManager;
	}

	/**
	 * @param achievementManager
	 *            the achievementManager to set
	 */
	public void setAchievementManager(AchievementManager achievementManager) {
		this.achievementManager = achievementManager;
	}
	
	public PendantManager getPrizedPendants() {
		return pendants;
	}
	
	public BrawlingGManager getBGloves() {
		return brawlingGloves;
	}
	
	public LegendaryPet getLegendaryPet() {
		return legendaryPet;
	}
	
	
	public PointsManager getPointsManager() {
		return pointsManager;
	}
	
	public ArtisanWorkshop getArtisanWorkshop() {
		return artisan;
	}
	
	public FarmingManager getFarmingManager() {
		return farmingManager;
	}

	public LoyaltyPointsManager getLoyaltyPTSManager() {
		return loyalty;
	}
	
	public int getSapphireCharges() {
		return spiritSapphire;
	}

	public int getEmeraldCharges() {
		return spiritEmerald;
	}

	public int getRubyCharges() {
		return spiritEmerald;
	}

	public int getDiamondCharges() {
		return spiritDiamond;
	}

	public int getDragonstoneCharges() {
		return spiritDragonstone;
	}

	public int getOnyxCharges() {
		return spiritOnyx;
	}
	
	public int getAccountPin() {
		return accountpin;
	}

	public void setAccountPin(int accountpin) {
		this.accountpin = accountpin;
	}
	
	/**
	 * @return the task
	 */

	public SlayerTaskHandler getTask() {
		return slayerTask;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(SlayerTaskHandler task) {
		this.slayerTask = task;
	}

	public int recoveryOption;
	public String recoveryAnswer;
	public boolean isIronMan;
	protected int Rewardpoints;
	public int inter813Stage;
	public int gfxLoopId;

	public void setRecovery(int option, String string) {
		recoveryOption = option;
		recoveryAnswer = string;
	}
	
	





}