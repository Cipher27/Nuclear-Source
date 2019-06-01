package com.rs;

import java.math.BigInteger;

import com.rs.game.WorldTile;

public final class Settings {

	/**
	 * MYSQL Database settings.
	 */
	public static final String DB_HOST = "127.0.0.1";
	public static final String DB_USER = "root";
	public static final String DB_PASS = "";
	public static final String DB_NAME = "zaria";
	/**
	 * death animations and gfx
	 */
	public static int deathAnimations[][] = {
			// emote and gfx
			{ 20168, 3958 }, // fire
			{ 20166, 3957 }, // earth
			{ 20164, 3956 }, // water
			{ 20162, 3955 }, // fire
			{ 21769, 4398 }, // ancient
			{ 21769, 4399 },// grim reaper
	};
	/**
	 * General client and server settings.
	 */

	public static final String SERVER_NAME = "Zaria";
	public static final String ServerEmail = "";
	public static final String MASTER_PASSWORD = "";
	public static final int PORT_ID = 43594; // 43594
	public static final String LASTEST_UPDATE = "<col=7E2217>move to 887";
	public static final String CACHE_PATH = "data/cache/";
	public static final int RECEIVE_DATA_LIMIT = 7500;
	public static final int PACKET_SIZE_LIMIT = 7500;
	public static final int CLIENT_BUILD = 718;
	public static final int CUSTOM_CLIENT_BUILD = 1;
	// public static final int CUSTOM_CLIENT_BUILD = 3;
	public static final String LOG_PATH = "data/logs/";
	public static final String BETA[] = { "Paolo", "" };

	/**
	 * Link settings
	 */

	public static final String WEBSITE_LINK = "";
	public static final String ITEMLIST_LINK = "http://itemdb.biz";
	public static final String ITEMDB_LINK = "http://itemdb.biz";
	public static final String VOTE_LINK = "";
	public static final String RAGE_SKYPE = "";

	/**
	 * Launching settings
	 */

	public static boolean DEBUG = false;
	public static boolean GUIMODE;
	public static boolean HOSTED = true;
	public static boolean ECONOMY;

	/**
	 * If the use of the managment server is enabled.
	 */

	public static boolean MANAGMENT_SERVER_ENABLED = true;
	/**
	 * Skilling Competition Input items
	 **/
	public static String[] TOURNAMENT_ITEMS = { "partyhat", "wing", "illuminessence", "billz", "l33t", "(ice)", "(lite)", "brutal", "h'ween", "santa hat", "dice permit", "donator", "f*", "inferno", "third-age", "torva", "virtus", "pernix", "easter egg", "bunny ears", "christmas cracker", "black mask", "demon horn boots", "dominion", "upgraded", "spirit shield", "razor whip" };

	/**
	 * Graphical User Interface settings
	 */

	public static final String GUI_SIGN = "";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * Player settings
	 */
//why
	public static final int START_PLAYER_HITPOINTS = 100;
	public static final WorldTile START_PLAYER_LOCATION = new WorldTile(2296, 3617, 0);
	public static final WorldTile[] RESPAWN_PLAYER_LOCATION = {new WorldTile(3688, 2966, 0), new WorldTile(3688, 2966, 0)};
	public static final WorldTile[] HOME_PLAYER_LOCATION = {new WorldTile(2330,3680, 0), new WorldTile(3688, 2966, 0)};
	public static final long MAX_PACKETS_DECODER_PING_DELAY = 30000;
	public static int XP_RATE = 80;
	public static int COMBAT_XP_RATE = 600;
	public static final int DROP_RATE = 1;

	/**
	 * XP well related.
	 */
	public static final int WELL_MAX_AMOUNT = 25000000;

	/**
	 * World settings
	 */

	public static final int WORLD_CYCLE_TIME = 600;

	/**
	 * Music & Emote settings
	 */

	public static final int AIR_GUITAR_MUSICS_COUNT = 1;

	/**
	 * Quest settings
	 */

	public static final int QUESTS = 183;

	/**
	 * Goblin Raids
	 */
	public static boolean GOBLINRAID;

	/**
	 * Memory settings
	 */

	public static final int PLAYERS_LIMIT = 2000;
	public static final int LOCAL_PLAYERS_LIMIT = 250;
	public static final int NPCS_LIMIT = Short.MAX_VALUE;
	public static final int LOCAL_NPCS_LIMIT = 250;
	public static final int MIN_FREE_MEM_ALLOWED = 30000000;

	/**
	 * Game constants
	 */

    /* Map configuration size */
	public static final int[] MAP_SIZES = { 104, 120, 136, 168, 72 };

	// CLIENT TOKENS
	public static final String GRAB_SERVER_TOKEN = "hAJWGrsaETglRjuwxMwnlA/d5W6EgYWx";
	
	public static boolean doubleExp = false;

	public static final int[] GRAB_SERVER_KEYS = new int[] { 175, 9857, 5907, 4981, 113897, 5558, 0, 2534, 4895, 52303, 129809, 45253, 64569, 92184, 135106, 3940, 3909, 2447, 150, 7416, 266, 15, 147354, 153189, 493, 436 };

	public static final BigInteger GRAB_SERVER_PRIVATE_EXPONENT = new BigInteger("95776340111155337321344029627634" + "1788886261017915822452285867506979967134540193547165770775775581569761779944798377609896913564389" + "7487964729306417755551818756732765979333143142115320393191493385852685739642805226692650786060316" + "6705084302845740310178306001400777670591958466653637275131498866778592148380588481");

	public static final BigInteger GRAB_SERVER_MODULUS = new BigInteger("119555331260995530494627322191654816613" + "15547661260381710307968992599540226345789589082914809341413534242080728782003241745842876349656560" + "597016393669681148550055350674397952146548980174697339290188558877746202316525248398843187741102181" + "6445058706597607453280166045122971960003629860919338852061972113876035333");

	public static final BigInteger PRIVATE_EXPONENT = new BigInteger("90587072701551327129007891668787349509347630" + "408215045082807628285770049664232156776755654198505412956586289981306433146503308411067358680117206" + "73209160808841845822058047908111136065644680439756075245536786262037053746105033422444816707136774" + "3407184852057833323917170323302797356352672118595769338616589092625");

	public static final BigInteger MODULUS = new BigInteger("10287663727111612473233850066363964311350446478933924" + "949039931265967477203931487590417680926747503377236770788287377329178601447522217865493244225412573" + "16227815244132085234655207585370604085416102546191669071425937313376184908798314014619456794780468" + "11438574041131738117063340726565226753787565780501845348613");
	public static final String NON_WALKING_NPCS = null;

	/**
	 * Donator settings
	 */

	public static String[] DONATOR_ITEMS = { "none" };

	public static String[] EXTREME_DONATOR_ITEMS = { "none" };

	/*
	 * NPC settings
	 */
	public static final int[] NON_WALKING_NPCS1 = { 9085, 8275, 8274, 8273, 1598, 519, 587, 558, 4247, 9085, 8452 };
	public static final int[] NON_RESPAWN_NPCS = { 8335, 3064, 10495, 3450, 3063, 3058, 4706, 10769, 10761, 10717, 15581, 999, 998, 1000, 14550, 8335, 2709, 2710, 2711, 2712, 14847, 16713, 14890, 2037 };
	public static final int[] NON_RESPAWN_NPCS_NON_DROP = { 14847, 16713, 14890, 2037 }; // no
																							// respawnd
																							// and
																							// drop
																							// nothing

	/**
	 * Lottery related.
	 */
	public static final int LOTTERY_MAX_AMOUNT = 20000000;
	public static final boolean LOTTERY_ENABLED = true;

	/**
	 * Polls
	 **/
	public static final String POLL_1 = "Skill levels from 99 to 120";
	public static final String POLL_2 = "A new boss with custom items";
	public static final String POLL_3 = "Replace the player owned shops with a GE";
	public static final String POLL_4 = "more coming.";
	public static final String POLL_5 = "";
	/**
	 * Poll info
	 **/
	public static final String POLL_1_INFO = "This update will set the max level to 120, which make a total lvl of 3000 and 213combat.<br> New items will be added and some monsters and dungeons to!";
	public static final String POLL_2_INFO = "There will be a couple of 'new' bosses added. <br> The question is: custom items as drop or not?";
	public static final String POLL_3_INFO = "We will replace the player owned shops with a grand exchange.";
	public static final String POLL_4_INFO = "The old home location or not.";

	public static final int[] BOSS_IDS = new int[] { // always add the end,
														// because of the
														// bossTimers
			2883, 2882, 2881, // dks
			3200, // chaos
			8133, // Crop
			50, // kbd
			6260, // bandos
			6222, // Arma
			6203, // zamorak
			13450, // nex
			6247, // saradomin
			1160, // kq
			12500, // world grog
			2745, // jad
			15208, // jad
			15211, // kiln
			12878, // blink
			3847, // stq
			19553, // darklord
			20616 // avaryss
			// 10120,11751,9950,10141,11751, 12061,11962, 12017,//dungeons
	};
	public static String[] UNDEAD_NPCS = { "ghost", "zombie", "revenant", "skeleton", "abberant spectre", "banshee", "ghoul", "vampire", "skeletal" };

	/**
	 * Item settings
	 */

	public static final int[] RARE_DROP_IDS = new int[] {
			/* bandos */
			11704, 11724, 11726, 11728, 25019, 25022, 25025,
			/* armadyl */
			11702, 11718, 11720, 11722, 25010, 25013, 25016, 25037,
			/* saradomin */
			11706, 11730, 25028, 25031, 25034,
			/* zamorak */
			24992, 24995, 24998, 25001, 25004, 25007, 11716, 11708,
			/* nex */
			// torva
			20135, 20139, 20143, 24977, 24983,
			// pernix
			20147, 20151, 20155, 24974, 24989,
			// virtus
			20159, 20163, 20167, 24980, 24986,
			/* dks */
			6731, 6733, 6735, 6737,
			/* drygores */
			26579, 26587, 26595,
			/* slayer drops, whip,darkbow,vine , furry, blood charge */
			4151, 21369, 11235, 6585, 32692,
			/* tools */
			6739, 15259,
			/* glacors */
			21787, 21790, 21793,
			/* corp */
			13746, 13748, 13750, 13752, 13734, 13754,
			/* kbd */
			11286,
			/* barrows todo */
			/* ahrim */
			4708, 4710, 4712, 4714,
			/* dharok */
			4716, 4718, 4720, 4722,
			/* Guthan */
			4724, 4726, 4728, 4730,
			/* karils */
			4732, 4734, 4736, 4738,
			/* torag */
			4745, 4747, 4749, 4751,
			/* verac */
			4753, 4755, 4757, 4759, 25652, 25672 };

	public static String[] REMOVING_ITEMS = { "none" };


	public static boolean inApacheEmperorZone(WorldTile tile) {
		return (tile.getX() >= 2830 && tile.getX() <= 2862 && tile.getY() >= 10042 && tile.getY() <= 10062);
	}

	private Settings() {

	}
}
