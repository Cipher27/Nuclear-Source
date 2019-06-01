package com.rs.game.player.robot;


import java.util.List;

import com.rs.Settings;
import com.rs.cache.loaders.ClientScriptMap;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.PlayerFollow;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.robot.NewRobotScript;
import com.rs.game.player.robot.RobotScript;
import com.rs.game.player.robot.Set;
import com.rs.game.player.robot.SetManager;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.GameSession;
import com.rs.net.Session;
import com.rs.net.encoders.WorldPacketsEncoder;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

import org.jboss.netty.channel.Channel;


/**
 * @author Taht one guy
 * @author Danny
 */

public class Robot extends Player {

	public static class RobotGameSession extends GameSession {

		/**
		 * Represents the {@link Player} to connect for the session.
		 */
		private final Player player;

		/**
		 * Constructs a new {@code GameSession} {@link Object}.
		 * 
		 * @param channel
		 *            The connected {@link Channel} to construct.
		 * @param The
		 *            {@link Player} to construct.
		 */
		public RobotGameSession(Channel channel, Player player) {
			super(channel, player);
			this.player = player;
			setRobotPackets(player);
		}

		/*@Override
		public void channelRead(Object message) {

		}

		@Override
		public void disconnect() {
			player.finish();
		}

		@Override
		public String getIPAddress() {
			return "127.0.0.1";
		} */

		/*@Override
		public void processPacket(RS2PacketBuilder builder) {

		}*/

		/*
		 * @Override public void setRobotPackets(Player player) {
		 * TRANSMITTER.setPackets(new GamePacketEncoder(player)); }
		 */

		/*
		 * @Override public Channel getChannel() { return channel; }
		 */
	}
	
	public static int index = 0;
	
	public static String createName(String... names) {
		String numbers = "";
		if (names.length > 0) {
			final int rand = 1 + Utils.getRandom(1);
			for (int x = 0; x < rand; x++) {
				numbers = numbers + "" + Utils.getRandom(9);
			}
		}
		if (index >= names2.length) {
			index = 0;
		}
		String name = names2[index++/*Utils.getRandom(names2.length - 1)*/];
		if (names.length > 0) {
			name = names[0];
		}
		final String finishedname = name + numbers;
		if (World.getPlayerByDisplayName(finishedname) != null)
			return createName(finishedname);
		return finishedname;
	}

	public static int[] toIntArray(List<Integer> integerList) {
		final int[] intArray = new int[integerList.size()];
		for (int i = 0; i < integerList.size(); i++) {
			intArray[i] = integerList.get(i);
		}
		return intArray;
	}

	private static final long serialVersionUID = 2011932556974180375L;

	public transient RobotScript script;
	
	public transient NewRobotScript newScript;

	/*
	 * public Robot(String password, WorldTile tile, String username) {
	 * super(tile); dummyMode = true;
	 * setHitpoints(Settings.START_PLAYER_HITPOINTS); //if (mySlayerTask ==
	 * null) // mySlayerTask = new com.rs.slayer.SlayerList.SlayerTask();
	 * this.password = password; appearence = new Appearence(); inventory = new
	 * Inventory(); equipment = new Equipment(); skills = new Skills();
	 * combatDefinitions = new CombatDefinitions(); prayer = new Prayer(); bank
	 * = new Bank(); controlerManager = new ControlerManager(); //tabManager =
	 * new TabManager(); musicsManager = new MusicsManager(); emotesManager =
	 * new EmotesManager(); friendsIgnores = new FriendsIgnores(); dominionTower
	 * = new DominionTower(); charges = new ChargesManager(); auraManager = new
	 * AuraManager(); runEnergy = 100; allowChatEffects = true; mouseButtons =
	 * true; //acceptAid = true; killedBarrowBrothers = new boolean[6];
	 * SkillCapeCustomizer.resetSkillCapes(this); ownedObjectsManagerKeys = new
	 * LinkedList<String>(); init(username, displayMode, screenWidth,
	 * screenHeight); start(); }
	 */

	public transient int combatLevel;

	/*
	 * public void init(com.anarchy.comwork.attachment.session.Session session,
	 * Channel channel, String username, int displayMode, int screenWidth, int
	 * screenHeight, MachineInformation machineInformation, IsaacKeyPair
	 * isaacKeyPair) { super(session, channel, username, displayMode,
	 * screenWidth, screenHeight, machineInformation, isaacKeyPair);
	 * //this.setSession(null); //getSession().setRobotPackets(this); pendants =
	 * new PrizedPendants(); if (assassin != 1) { assassin = 1;
	 * getSkills().setAssassin(); for (int i = 0; i < 5; i++) {
	 * getSkills().assassinSet(i, 1); } } if (pinpinpin != 1) { pinpinpin = 1;
	 * bankpins = new int[] { 0, 0, 0, 0 }; confirmpin = new int[] { 0, 0, 0, 0
	 * }; openBankPin = new int[] { 0, 0, 0, 0 }; changeBankPin = new int[] { 0,
	 * 0, 0, 0 }; } if (slayerManager == null) { slayerManager = new
	 * SlayerManager(); } if (squealOfFortune == null) { squealOfFortune = new
	 * SquealOfFortune(); } if (geManager == null) { geManager = new
	 * GrandExchangeManager(); } if (dominionTower == null) { dominionTower =
	 * new DominionTower(); } if (pin == null) { pin = new BankPin(); } if
	 * (realQuestManager == null) { this.realQuestManager = new
	 * com.anarchy.game.content.quests.QuestManager(); } if (skillTasks == null)
	 * { skillTasks = new SkillerTasks(); } skillTasks.setPlayer(this); if
	 * (customisedShop == null) { customisedShop = new CustomisedShop(this); }
	 * if (toolbelt == null) { toolbelt = new Toolbelt(); } if (roomReference ==
	 * null) { roomReference = new RoomReference(); } if (conObjectsToBeLoaded
	 * == null) { conObjectsToBeLoaded = new ArrayList<WorldObject>(); } if
	 * (house == null) { house = new House(); } if (bossCounter == null)
	 * bossCounter = new BossKillCounter(); bossCounter.setPlayer(this); // if
	 * (rooms == null) { rooms = new ArrayList<RoomReference>(); rooms.add(new
	 * RoomReference(Room.GARDEN, 4, 4, 0, 0)); rooms.add(new
	 * RoomReference(Room.PARLOUR, 5, 5, 0, 0)); rooms.add(new
	 * RoomReference(Room.KITCHEN, 3, 5, 0, 3)); rooms.add(new
	 * RoomReference(Room.PORTALROOM, 3, 3, 0, 2)); rooms.add(new
	 * RoomReference(Room.SKILLHALL1, 5, 4, 0, 0)); rooms.add(new
	 * RoomReference(Room.QUESTHALL1, 5, 3, 0, 0)); rooms.add(new
	 * RoomReference(Room.GAMESROOM, 6, 3, 0, 1)); rooms.add(new
	 * RoomReference(Room.BOXINGROOM, 6, 2, 0, 1)); rooms.add(new
	 * RoomReference(Room.BEDROOM, 6, 4, 0, 0)); rooms.add(new
	 * RoomReference(Room.DININGROOM, 4, 5, 0, 0)); rooms.add(new
	 * RoomReference(Room.WORKSHOP, 4, 3, 0, 0)); rooms.add(new
	 * RoomReference(Room.CHAPEL, 2, 4, 0, 3)); rooms.add(new
	 * RoomReference(Room.STUDY, 4, 3, 0, 3)); rooms.add(new
	 * RoomReference(Room.COSTUMEROOM, 4, 2, 0, 2)); rooms.add(new
	 * RoomReference(Room.THRONEROOM, 5, 2, 0, 2)); rooms.add(new
	 * RoomReference(Room.FANCYGARDEN, 3, 4, 0, 3)); if (auraManager == null) {
	 * auraManager = new AuraManager(); } if (foodBag == null) foodBag = new
	 * FoodBag(); if (randomEventManager == null) { randomEventManager = new
	 * RandomEventManager(); } if (questManager == null) { questManager = new
	 * QuestManager(); } if (DwarfCannon == null) { DwarfCannon = new
	 * DwarfCannon(this); } if (moneyPouch == null) { moneyPouch = new
	 * MoneyPouch(); } if (playerData == null) { playerData = new PlayerData();
	 * } if (fairyRing == null) { fairyRing = new FairyRing(this); } if (types
	 * == null) { types = new boolean[3]; } if (gems == null) { gems = new
	 * int[4]; } if (petManager == null) { petManager = new PetManager(); } if
	 * (activatedLodestones == null) { activatedLodestones = new boolean[16]; }
	 * if (lodeStone == null) { lodeStone = new LodeStone(); } if (assassins ==
	 * null) { assassins = new Assassins(); } if (cachedChatMessages == null) {
	 * cachedChatMessages = new ArrayList<>(); } if (farmings == null) {
	 * farmings = new Farmings(); } if (slayerTask == null) { slayerTask = new
	 * SlayerTask(); } this.setSession(null); this.username = username;
	 * this.displayMode = displayMode; this.screenWidth = screenWidth;
	 * this.screenHeight = screenHeight; pin = new BankPin(); setAlchDelay(0);
	 * // afkTimer = Utils.currentTimeMillis() + (1*60*1000); // afkTime(); if
	 * (notes == null) { notes = new Notes(); } if (pnotes == null) { pnotes =
	 * new ArrayList<Note>(30); } notes.setPlayer(this); pin.setPlayer(this);
	 * pendants = null; pendants = new PrizedPendants(); varsManager = new
	 * VarsManager(this); squealOfFortune.setPlayer(this); spinsManager = new
	 * SpinsManager(this); loyaltyManager = new LoyaltyManager(this);
	 * interfaceManager = new InterfaceManager(this);
	 * moneyPouch.setPlayer(this); interfaceManager = new
	 * InterfaceManager(this); dialogueManager = new DialogueManager(this);
	 * hintIconsManager = new HintIconsManager(this); priceCheckManager = new
	 * PriceCheckManager(this); ectophial = new Ectophial(this);
	 * toolbelt.setPlayer(this); localPlayerUpdate = new
	 * LocalPlayerUpdate(this); localNPCUpdate = new LocalNPCUpdate(this);
	 * actionManager = new ActionManager(this); cutscenesManager = new
	 * CutscenesManager(this); lodeStone = new LodeStone(); trade = new
	 * Trade(this); dungeoneeringParty = new DungeoneeringParty(this);
	 * antidupeManager.setPlayer(this); priceManager.setPlayer(this);
	 * pkManager.setPlayer(this); appearence.setPlayer(this); if
	 * (getAppearence().getTitle() <= 0) { getAppearence().setFixTitle(1); }
	 * inventory.setPlayer(this); equipment.setPlayer(this);
	 * skills.setPlayer(this); randomEventManager.setPlayer(this);
	 * house.setPlayer(this); assassins.setPlayer(this);
	 * lodeStone.setPlayer(this); geManager.setPlayer(this);
	 * combatDefinitions.setPlayer(this); prayer.setPlayer(this);
	 * bank.setPlayer(this); //foodBag.setPlayer(this);
	 * controlerManager.setPlayer(this); musicsManager.setPlayer(this);
	 * emotesManager.setPlayer(this); friendsIgnores.setPlayer(this);
	 * dominionTower.setPlayer(this); farmings.initializePatches();
	 * auraManager.setPlayer(this); charges.setPlayer(this);
	 * lodeStone.setPlayer(this); questManager.setPlayer(this);
	 * petManager.setPlayer(this); slayerManager.setPlayer(this);
	 * pendants.setPlayer(this); // assassins.setPlayer(this);
	 * setDirection(Utils.getFaceDirection(0, -1)); temporaryMovementType = -1;
	 * logicPackets = new ConcurrentLinkedQueue<LogicPacket>(); initEntity();
	 * packetsDecoderPing = Utils.currentTimeMillis(); if
	 * (World.containsPlayer(getUsername())) forceLogout(true);
	 * World.addPlayer(this); World.updateEntityRegion(this); treeDamage = 0;
	 * isLighting = false; isChopping = false; isRooting = false; // Do not
	 * delete >.>, useful for security purpose. this wont waste that // much
	 * space.. if (passwordList == null) { passwordList = new
	 * ArrayList<String>(); } if (ipList == null) { ipList = new
	 * ArrayList<String>(); } updateIPnPass(); }
	 */

	/*
	 * public WorldTile getRespawnpoint() { //sendMessage("Home: "+home);
	 * switch(home) { case 0://lumb case 1: return new WorldTile(
	 * 3220+Utils.getRandom(3), 3221+Utils.getRandom(3), 0);
	 * 
	 * case 2://varrock return new WorldTile( 3209+Utils.getRandom(3),
	 * 3421+Utils.getRandom(3), 0);
	 * 
	 * case 3://falador return new WorldTile( 2967+Utils.getRandom(3),
	 * 3338+Utils.getRandom(3), 0);
	 * 
	 * case 4://edgeville return new WorldTile( 3079+Utils.getRandom(3),
	 * 3475+Utils.getRandom(3), 0); } return new WorldTile(
	 * 3220+Utils.getRandom(3), 3221+Utils.getRandom(3), 0); }
	 * 
	 * public WorldTile getRespawnpoint(int i) { //sendMessage("Home: "+home);
	 * switch(i) { case 0://lumb case 1: return new WorldTile(
	 * 3220+Utils.getRandom(3), 3221+Utils.getRandom(3), 0);
	 * 
	 * case 2://varrock return new WorldTile( 3209+Utils.getRandom(3),
	 * 3421+Utils.getRandom(3), 0);
	 * 
	 * case 3://falador return new WorldTile( 2967+Utils.getRandom(3),
	 * 3338+Utils.getRandom(3), 0);
	 * 
	 * case 4://edgeville return new WorldTile( 3079+Utils.getRandom(3),
	 * 3475+Utils.getRandom(3), 0); case 5://rimmington return new WorldTile(
	 * 2954+Utils.getRandom(3), 3216+Utils.getRandom(3), 0); case 6://draynor
	 * return new WorldTile( 3090+Utils.getRandom(3), 3235+Utils.getRandom(3),
	 * 0); case 7://cammy return new WorldTile( 2756+Utils.getRandom(3),
	 * 3477+Utils.getRandom(3), 0); case 8://summoning town return new
	 * WorldTile( 2929+Utils.getRandom(3), 3454+Utils.getRandom(3), 0); case
	 * 9://ardougne return new WorldTile( 2640+Utils.getRandom(3),
	 * 3281+Utils.getRandom(3), 0); case 10://yanille return new WorldTile(
	 * 2603+Utils.getRandom(3), 3095+Utils.getRandom(3), 0); case 11://canifis
	 * return new WorldTile( 3488+Utils.getRandom(3), 3481+Utils.getRandom(3),
	 * 0); case 12://al khahard return new WorldTile( 3268+Utils.getRandom(3),
	 * 3150+Utils.getRandom(3), 0);
	 * 
	 * case 13://relleka return new WorldTile( 2631+Utils.getRandom(3),
	 * 3669+Utils.getRandom(3), 0); case 14://bandit camp return new WorldTile(
	 * 3173+Utils.getRandom(3), 2993+Utils.getRandom(3), 0); case 15://brimhaven
	 * return new WorldTile( 2758+Utils.getRandom(3), 3176+Utils.getRandom(3),
	 * 0); case 16://trollheim return new WorldTile( 2890+Utils.getRandom(3),
	 * 3671+Utils.getRandom(3), 0);
	 * 
	 * } return new WorldTile( 3220+Utils.getRandom(3), 3221+Utils.getRandom(3),
	 * 0); }
	 */
	
	@Override
	public String toString() {
		return this.getDisplayName();
	}

	public boolean dummyMode, followMode, cutsceneMode;

	public String dummyMessage = "";

	public int dummyTimer = 0;

	public String[] messages = { 
			"We have dungeoneering!",
			"Working pest control.", "visit us at Anarchyscape.com",
			"I go to anarchyscape.com to play!",
			"I just think the players are so nice.", "I really like the eco!",
			"Anarchy Is The Bomb!", "This game is the best!", "Hi youtube",
			"Everyone should join", "Come and play!",
			"We need more people to play Stealing Creation",
			"Why are we yelling?", "Everyone should join", "Come and play!",
			"We have Stealing Creation!", "We have Minigames!", "We have Nex!",
			"We have Clan Wars!", "We have Dungeoneering!",
			"I want more people to play", "This server is really fun",
			"I think everyone should join", "This game is really cool",
			"This is Anarchy!", "We need more players on Anarchy",
			"This is the best server ever!", };

	public static String[] names2 = new String[] { /*"Ghaysh",*/ "Cier", "Adam Bomb",
			"Hinech", /*"Kimdra",*/ "Therria", /*"Issfach", */"Vesaru", "Imyorm",
			/*"Lerlore", "Naroth", "Vesyd",*/ "Achvor", /*"Serird", "Denec",*/ "Guam",
			"Perad", "Enpero", "Lodyn", "Orite", "Pyvor", "Tomkin", "Nysoph",
			"Nysbani", "Kelperi", "Hinel", "Dagar", /*"Perbver",*/ "Waros",
			/*"Tasoy", "Chother", "Ataugh",*/ "Emchae", "Radpera", "Kimhat",
			"Rynot", /*"Tyum", "Orine",*/ "Nysentho", "Rynuk", "Numor", /*"Chiend",*/
			"Nalest", /*"Trahin", "Tiay", "Tiaskeli", "Belarda", "Darot",*/
			"Tindtin", /*"Aneng",*/ "Perbur", "Noas", "Dynnon", "Quazild", "Imano",
			/*"Aughsay", "Veryn", "Chetech", "Belunde", "Tyess", "Ingtonu",
			"Lorkal", "Deldynu",*/ "Aruk", /*"Aughild", "Yerurna", "Banormi",*/
			"Vorthero", "Lokin", /*"Warand", "Tainys",*/ "Enddra", "Torum",
			/*"Worend", "Omser",*/ "Irusk", "Eldisso", /*"Echtia",*/ "Imsrry",
			"Risis", "Gartar", "Blidra", "Ades", "Itor", "Aang", "Aaron", 
			"Abyss Wolf", "Ace", "Aciddrop", "Air rune", "Akios", "Breezy", 
			"Chaotik", "Hit my veng", "Go hard", "Lilith", "Shiro", "The arrow", 
			"Sinister", "Millenium", "Z slayer", "Mills2bills", "Ags killer",
			"I am skill", "I am pro", 
			
			"adamthegunnr", "adredor", "africa", "ags", "akara", "alexandeno", "allan", 
			"alltimenewb", "alwayshobo", "andi", "andreww", "angelumlucis", "anischa", "anti_oxide", 
			"anti_social", "anyone", "an_idiot", "apleos_x", "arbitrage", "arcaine", "arcane_pkz", 
			"arcticfuzz", "aroo", "arpy", "arra", "arras", "asa", "aschwin", "asdasd", "asschug", 
			"as_i_die", "atomic", "aud_ltd", "aura_elison", "aurora", "autism", "avani", "ayrtonator", 
			"azelxd", "aziz", "a_d_d_i_c_t", "a_ninja", "a_theive", "a_white_wolf", 
			"baby_troll", "baemaria", "balistica", "barry", "baze_jimpvp", "beady_eye", 
			"beauty", "belligerence", "bestfoirever", "bet", "bigjellyroll", "bigmakki", 
			"big_aaron", "bikuned", "bing", "blackvenom", "blaiine", "blakk", "blues", "blue_king", "boboke", 
			"born_skilled", "bradx", "brainstormz", "braxton", "brewski", "bubbleman", "bulbasaur", "burninskull", 
			"burt", "buttwholes", "b_r_o", "cam", "cameldoe", "cannoner", "casino", "cbreeze", "cg_tygaa", "chaos_bolz", 
			"charityy", "chills", "chilly", "chimerica", "choo_choo", "chuckdiddy", "cjzz", "clipzhd", "clive", 
			"clowdless", "cocs", "cody", "computerfox", "copen", "corza", "craftybear", "crash", "crazyjew", 
			"crazykid", "crispy_bacon", "cristo", "crooks", "crossfade", "crushed", "cults", "cya_hick", "dank_kust", 
			"darkrai", "darkxfaith", "darren", "daugvardas", "dave_reborn", "davidasd", "days", "da_vinci", 
			"dclaw_rusher", "dday", "deadbodysex", "deadbodysexx", "deez", "deformed", "deidara", "delgadoplays", 
			"demonazazel", "denbyy", "dependonme", "derase", "devilhachiez", "devilz", "de_kat", "digid", "discey", 
			"divine", "divine_exile", "doitright", "dongoniron", "donkey_kong", "dortmund", "dougiefresh", "downedash", 
			"dragonovic", "dragonslain", "dream_ghost", "dropdead", "dubstep_cam", "dudaa", "duffmanek_cz", "dumsin", 
			"dungeoneer", "dunkey", "durrand", "dyllon", "earthblood", "easily_done", "egg", "elch_reh", "elfcitykid", 
			"elias", "elkian", "elvernew", "el_pugo", "energiakana", "erlospure", "erthrax", "er_vii", "esdasf", "eskild", 
			"etienne", "exe", "exige", "exilion", "eyzo", "fact", "fatal_k_o", "fatnerd", "faytal", 
			"fellhammer", "firemaguire", "fiske_mster", "fitemeirl", "flee", "flyinglotus", "foreverbaked", 
			"forgotten", "forsakenrage", "frau_hase", "freaqshow", "freax", "freefall", "freshmeat", 
			"frozenarrowz", "future", "fuzzrowdah", "garen", "garth_tyson", "geechizay", "geekyfratboy", 
			"gennosuke", "ghostlyboy", "gifted", "gilbertaw", "gimmeyophat", "gimp", "gingerjoe", "gioia", 
			"givemeitems", "gladiak", "goal", "goi", "goldminer", "goochey", "google", "greggers", "greystorm", 
			"grover", "gstar", "g_r_a_m_a_s", "hail", "hairy_beaver", "halloot", "hash", "hatchetmane", "havel", 
			"heavymind", "hex", "hidingblues", "hiphopjess", "hulkbuster", 
			"hydra", "hydro", "hype", "h_a_z_a_r_d", "iamcool", "ichigo", "ifishforfun", 
			"illuminatic", "im_batman", "im_erin", "im_fluffy", "im_new", "index", "indecies", 
			"interesting", "iprestige", 
			"i_adam", "i_am_jad", "i_sin_i", "i_skills_i", "jacae", "jacob", "jadelie", "jaden", "jaja", 
			"jakester", "janneli", "jazz", "jeffrey", "jemppunen", "jesbro", "jet_skyz", 
			"jhoss", "jim_sauce", "jinx", "joshua", "josh_mchuge", "juicy_chops", "justintime", "justvintage", 
			"just_no", "jwzjwzjwz", "j_o_h_n", "kado", "kaioshina", "kaleem", "kanye", "kiana", 
			"kid_ink", "king_trap", "kinship", "k_o", "krazee", "krongheit", "kryptek", 
			"kushlife", "kylix", "legendofrush", "legionhorror", "legolas", "leona", "leprechaun", 
			"let_it_rain", "lifepast", "liger", "lime", "linen_panda", 
			"loading", "lordoki", "lottery", "love_is_life", "mad_mark", "mageman", 
			"malfoy", "malicious", "mario", "masonxrocks", "mattchew", "mauzze", "maxd_zerker", 
			"max_g", "mega", "merker", "moneyfarmer", "money_bags", "monkeyking", 
			"moon_jynx", "morvran", "mosh", "mow", "mrbubbles", "mrdutchmann", "mr_buzz", 
			"mr_skills", "mvp", "nautilus", "neon_nex", "olaf", "oldecho", "olivpvp", "ollie", "one", 
			"onyx", 
			"ozone", "paradise", "partyhats", 
			"pker", "pk_snail", "poopy_pants", "poseidon", "pow", "press_play", "pricemaker", "prock", 
			"proskill", "pvm_boss", "pvm_much", "qet", "qi", "raptor", "ravenftw", "rawkstarz", "raw_business", 
			"reptile", "rex", "ridiculous", "rip_u_son", "ritz", 
			"root", "rusty_nails", "santa", "sarah", "seismic", "shawn", "shay", "sheele", 
			"sicmypvpness", "sir_lucas", "sleepy", "sneakyking", "spiritual", "spooky", "squirly", "stancelife", "stanmore", "stumpy", 
			"swerve", "sycz", "team_wtf", "thebigchief", "thecoolman", "theskillerer", "thunder", 
			"tropicals", "true_flow", "truffels", "tumelight", "turtle", "tuske", "twig", "twisted", "twix", 
			"tyrant", "tyrone", "unicorns",  "varrock", "vector", "villain", "vinicius", "violetta", 
			"viradic", "viscosity", "vixen", "vixxy", "vlad", "void", "wael_cyclon", "war_machine", 
			"weak_sauce", "weezy_x", "whisph", "wind", "wisely_done", "won", "woz", "wreckxdaz", 
			"wring", "xelyformas", "xfrac", "xil", "xorrex", "xpurt", "xxmasterxx", "xxningaxx", 
			"yankee", "yasha", "yato", "yungmoney", "yung_towl", "yuno", "yuuki", "y_o_l_o", 
			"zadkiel", "zalt", "zamorak_soul", "zatoran", "zay", "zegokans", "zekko", 
			"zeleroth", "zerief", "zerk_pkerz", "zerreri", "zexo", "zezotic", "zhyrs", 
			"zig", "zimba", "zmo", "zo", "zoids", "zokai", "zombiemaster", "zork",
	};
	
	// creates Player and saved classes
	public Robot(String password, WorldTile tile, String username,
			int displayMode, int screenWidth, int screenHeight,
			boolean... script) {
		super(password);
		setMoneyInPouch(1);
		setXpLocked(true);
		if (Utils.getRandom(2) == 1)
			appearence.setMale(false);
		//starter = 1;
		starterstage = 3;
		// if (script.length != 0)
		// this.script = new AdvancedRobotScript(new CombatController(), new
		// ActionController(), new MovementController(), new
		// InventoryController());
		//init(null, null, username, displayMode, screenWidth, screenHeight,
		//		null, null);
		start();
		
		heal(890);
		prayer.restorePrayer(980);
		refreshHitPoints();
		prayer.refreshPrayerPoints();
		prayer.setPrayerBook(true);
		prayer.switchSettingQuickPrayer();
		prayer.switchPrayer(0);
		prayer.switchPrayer(5);
		prayer.switchPrayer(18);
		prayer.switchPrayer(19);
		prayer.switchSettingQuickPrayer();
		setLocation(tile);
	}
	
	public Robot(String password) {
		super(password);
		final String username = Utils.formatPlayerNameForProtocol(Robot.createName());
		this.setUsername(username);
		
		setMoneyInPouch(1);
		setXpLocked(true);
		
		if (Utils.getRandom(2) == 1)
			appearence.setMale(false);
		
		//starter = 1;
		starterstage = 3;
		
	//	init(null, null, username, displayMode, screenWidth, screenHeight,
	//			null, null);
		start();
		//prayer.setPrayerBook(true);
		setNextWorldTile(new WorldTile(2500, 2500, 0));
		for(int skill = 0; skill < 25; skill++) {
			getSkills().setXp(skill, Skills.getXPForLevel(99));
		}
		heal(890);
		prayer.restorePrayer(980);
		PlayerLook.setGender(this, Utils.getRandom(3) > 1);
		PlayerLook.setDesign(this, Utils.getRandom(6), Utils.getRandom(2));
		PlayerLook.setSkin(this, Utils.getRandom(9));
		getAppearence().setHairStyle(
				(int) ClientScriptMap.getMap(
						getAppearence().isMale() ? 2339 : 2342)
						.getKeyForValue(Utils.getRandom(10)));
		getAppearence().setHairColor(
				ClientScriptMap.getMap(2345).getIntValue(Utils.getRandom(10)));
		
		getCombatDefinitions().setAutoRelatie(true);
		getAppearence().generateAppearenceData();
		reset();
	}
	
	public WorldTile spawnTile;
	public boolean botMode;
	
	public int setId;
	public int emoteId;
	public boolean walk;
	public String message;
	
	public Robot(String password, int setId, int emote, boolean walk, String message) {
		this(password);
		this.setId = setId;
		this.emoteId = emote;
		this.walk = walk;
		this.message = message;
		
		this.spawnTile = getWorldTile();
		this.botMode = true;
		
		final Set set = SetManager.list.get(setId);
		final Item[] items2 = set.getEquip();
		for (int o = 0; o < items2.length; o++) {
			if (items2[o] == null || items2[o].getId() <= 1)
				continue;
			getEquipment().wieldOneItem(o, items2[o]);
		}
		getAppearence().generateAppearenceData();
	}
	
	@Override
	public WorldPacketsEncoder getPackets() {
		return getSession().getTransmitter().getPackets();
	}

	@Override
	public RobotScript getScript() {
		return this.script;
	}

	public void init(Session session, String string) {
		this.setUsername(string);
		this.session = session;
		// packetsDecoderPing = System.currentTimeMillis();
		// World.addPlayer(this);
		// World.updateEntityRegion(this);
		if (Settings.DEBUG)
			Logger.log(this, new StringBuilder("Inited Player: ")
			.append(string).append(", pass: ").append(password)
			.toString());
	}

	@Override
	public boolean isRobot() {
		return true;
	}

	@Override
	public void loadMapRegions() {
		super.loadMapRegions();
	}

	@Override
	public void logout(boolean lobby) {
		if (!running)
			return;
		running = false;
	}

	// lets leave welcome screen and start playing
	// @Override
	// public void run() {
	// super.run();
	/*
	 * lastIP = getSession().getIPAddress(); interfaceManager.sendInterfaces();
	 * getPackets().sendRunEnergy(); getPackets().sendItemsLook();
	 * refreshAcceptAid(); refreshAllowChatEffects(); refreshMouseButtons();
	 * refreshPrivateChatSetup(); refreshOtherChatsSetup();
	 * sendRunButtonConfig(); geManager.init();
	 * realQuestManager.setPlayer(this);
	 * realQuestManager.unlockQuestTabOptions();
	 * realQuestManager.sendQuestInfo(); getFarmings().updateAllPatches(this);
	 * donatorTill = 0; extremeDonatorTill = 0; legendaryDonatorTill = 0;
	 * supremeDonatorTill = 0; divineDonatorTill = 0; angelicDonatorTill = 0;
	 * demonicDonatorTill = 0; heroicDonatorTill = 0;
	 * getDwarfCannon().lostCannon(); getDwarfCannon().lostGoldCannon();
	 * getDwarfCannon().lostRoyalCannon(); sendDefaultPlayersOptions(); if
	 * (skull != 5) { skull = 5; setSkullMode(true); } SoulWarsLocation();
	 * checkMultiArea(); checkSmokeyArea(); checkDesertArea();
	 * checkMorytaniaArea(); checkSinkArea(); inventory.init();
	 * equipment.init(); skills.init(); combatDefinitions.init(); prayer.init();
	 * friendsIgnores.init(); refreshHitPoints(); prayer.refreshPrayerPoints();
	 * getPoison().refresh(); getPackets().sendConfig(281, 1000);
	 * getPackets().sendConfig(1160, -1); getPackets().sendConfig(1159, 1);
	 * getPackets().sendGameBarStages(); musicsManager.init();
	 * emotesManager.refreshListConfigs(); questManager.init();
	 * sendUnlockedObjectConfigs(); if (familiar != null) {
	 * familiar.respawnFamiliar(this); } else if (pet != null) {
	 * petManager.init(); } else if (slave != null) { NPC npc = new
	 * NPC(slave.getCaptured().getId(), new WorldTile( this.getX(), this.getY(),
	 * this.getPlane()), slave .getCaptured().getMapAreaNameHash(),
	 * slave.getCaptured() .canBeAttackFromOutOfArea()); String name; name =
	 * slave.getName(); BeastOfBurden bob = null; bob = slave.getBurden(); if
	 * (bob == null) { bob = new BeastOfBurden(10); } slave = new Slaves(this,
	 * npc); slave.captureSlave(); slave.getCaptured().setOwner(this);
	 * slave.setName(name); slave.setBurden(bob);
	 * slave.getBurden().setEntitys(this, slave.getCaptured());
	 * World.addNPC(slave.getCaptured()); } running = true; updateMovementType =
	 * true; appearence.generateAppearenceData(); controlerManager.login();
	 * getLodeStones().checkActivation(); getLoyaltyManager().startTimer();
	 * OwnedObjectManager.linkKeys(this);
	 * 
	 * house.init(); treeDamage = 0; isLighting = false; isChopping = false;
	 * isRooting = false; used1 = false; finalblow = false; used2 = false;
	 * //REWRITE YO SHIT //mate swiftness = false; used3 = false; stealth =
	 * false; used4 = false; startpin = false; openPin = false;
	 * squealOfFortune.giveDailySpins(); openedCoffin = false; spawnedGhost =
	 * false;
	 * 
	 * toolbelt.init(); warriorCheck(); moneyPouch.init(); hasStaffPin = false;
	 */

	// missing unlock all emotes
	// }
	
	@Override
	public void sendDeath(final Entity source) {
		if (script != null)
			script.setAttackedBy(null);
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
								if (player == null || !player.hasStarted() || player.isDead() || player.hasFinished() || !player.withinDistance(this, 1) || !player.isCanPvp() || !target.getControlerManager().canHit(player)) {
									continue;
								}
								player.applyHit(new Hit(target, Utils.getRandom((int) (skills.getLevelForXp(Skills.PRAYER) * 2.5)), HitLook.REGULAR_DAMAGE));
							}
						}
						List<Integer> npcsIndexes = World.getRegion(regionId).getNPCsIndexes();
						if (npcsIndexes != null) {
							for (int npcIndex : npcsIndexes) {
								NPC npc = World.getNPCs().get(npcIndex);
								if (npc == null || npc.isDead() || npc.hasFinished() || !npc.withinDistance(this, 1) || !npc.getDefinitions().hasAttackOption() || !target.getControlerManager().canHit(npc)) {
									continue;
								}
								npc.applyHit(new Hit(target, Utils.getRandom((int) (skills.getLevelForXp(Skills.PRAYER) * 2.5)), HitLook.REGULAR_DAMAGE));
							}
						}
					}
				} else {
					if (source != null && source != this && !source.isDead() && !source.hasFinished() && source.withinDistance(this, 1)) {
						source.applyHit(new Hit(target, Utils.getRandom((int) (skills.getLevelForXp(Skills.PRAYER) * 2.5)), HitLook.REGULAR_DAMAGE));
					}
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
										if (player == null || !player.hasStarted() || player.isDead() || player.hasFinished() || !player.isCanPvp() || !player.withinDistance(target, 2) || !target.getControlerManager().canHit(player)) {
											continue;
										}
										player.applyHit(new Hit(target, Utils.getRandom((skills.getLevelForXp(Skills.PRAYER) * 3)), HitLook.REGULAR_DAMAGE));
									}
								}
								List<Integer> npcsIndexes = World.getRegion(regionId).getNPCsIndexes();
								if (npcsIndexes != null) {
									for (int npcIndex : npcsIndexes) {
										NPC npc = World.getNPCs().get(npcIndex);
										if (npc == null || npc.isDead() || npc.hasFinished() || !npc.withinDistance(target, 2) || !npc.getDefinitions().hasAttackOption() || !target.getControlerManager().canHit(npc)) {
											continue;
										}
										npc.applyHit(new Hit(target, Utils.getRandom((skills.getLevelForXp(Skills.PRAYER) * 3)), HitLook.REGULAR_DAMAGE));
									}
								}
							}
						} else {
							if (source != null && source != target && !source.isDead() && !source.hasFinished() && source.withinDistance(target, 2)) {
								source.applyHit(new Hit(target, Utils.getRandom((skills.getLevelForXp(Skills.PRAYER) * 3)), HitLook.REGULAR_DAMAGE));
							}
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
		boolean canDie = !dummyMode && !followMode;
		if (canDie && !controlerManager.sendDeath()) {
			return;
		}
		lock(7);
		stopAll();
		if (familiar != null) {
			familiar.sendDeath(this);
		}
		@SuppressWarnings("unused")
		final WorldTile deathTile = new WorldTile(this);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			
			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					reset();
				} else if (loop == 3) {
					setNextWorldTile(getHome());
				} else if (loop == 4) {
					getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	
	@Override
	public void processEntity() {
		super.processEntity();
		if (newScript != null) {
			newScript.process();
			return;
		}
		if (botMode) {
			getEmotesManager().useBookEmote(emoteId);
			setNextForceTalk(new ForceTalk(message));
			if (walk && !hasWalkSteps() && Utils.random(10) == 0) {
				addWalkSteps(spawnTile.transform(2, 2, 0));
			}
			return;
		}
		if (dummyMode) {
			System.out.println(username+", "+dummyTimer+", "+getX()+", "+getY()+", "+getPlane());
			switch (dummyTimer) {
				case 0:
					if (Utils.getRandom(2) != 0) {
						final Set set = SetManager.list.get(Utils.getRandom(300));
						final Item[] items2 = set.getEquip();
						for (int o = 0; o < items2.length; o++) {
							if (items2[o] == null || items2[o].getId() <= 1)
								continue;
							// player.getEquipment().getItems().set(o, items2[o]);
							// player.getEquipment().refresh(o);
							getEquipment().wieldOneItem(o, items2[o]);
						}
						getAppearence().generateAppearenceData();
						final int index = Utils.getRandom(messages.length - 1);
						dummyMessage = messages[index];
					}
					break;
				case 3:
					/*
					 * case 4: case 5: case 6: case 7: case 8: case 9:
					 */
				case 10:
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
				case 16:
				case 17:
				case 18:
				case 19:
				case 20:
				case 21:
				case 22:
				case 23:
					if (Utils.getRandom(2) == 1)
						getEmotesManager().useBookEmote(Utils.getRandom(35));
					if (Utils.getRandom(5) == 1)
						setNextForceTalk(new ForceTalk(dummyMessage));
					break;
				case 24:
					if (Utils.getRandom(2) == 1) {
						setFinished(true);
						World.updateEntityRegion(this);
						World.removePlayer(this);
					}
					break;
				case 27:
					if (Utils.getRandom(2) == 1) {
						setFinished(true);
						World.updateEntityRegion(this);
						World.removePlayer(this);
					}
					break;
				case 30:
					setFinished(true);
					World.updateEntityRegion(this);
					World.removePlayer(this);
					break;
			}
			dummyTimer++;
			return;
		} else if (followMode) {
			if (followPlayer == null || followPlayer.hasFinished()) {
				forceLogout();
			} else {
				if (Utils.random(100) == 0 && getUsername().contains("brendie")) {
					setNextForceTalk(new ForceTalk(strings[Utils.random(strings.length)]));
				}
				boolean following = this.getActionManager().getAction() instanceof PlayerFollow;
				boolean withinDistance = this.withinDistance(followPlayer);
				if (!following && withinDistance) {
					getActionManager().setAction(new PlayerFollow(followPlayer));
				} else if (!withinDistance) {
					setNextWorldTile(followPlayer);
				}
			}
			if (!getRun())
				setRun(true);
			return;
		} else if (cutsceneMode) {
			return;
		}
		if (getScript() == null) {
			script = getRandomScript();
			getScript().init(this);
		}
		getScript().process();
	}
	
	public void ft(String message) {
		if (!message.contains(" "))
			message.toUpperCase();
		else
			message = message.substring(0, 1).toUpperCase() + message.substring(1);
		this.setNextForceTalk(new ForceTalk(message));
	}
	
	public String[] strings = {
			"Sparkfear is a noob.", 
			"Chatty for demote.", 
			"Drummonster for demote.", 
			"Charity for demote", 
			"Taht for promotion", 
			"Pinky cat for demote.",
			"Dematt for demote.", 
			"Dave for admin.", 
			"Free blowjobs 4 days.", 
			"Buying crack.", 
			"Buying $100 donations.", 
			"Giving away free candy at my house!", 
			"Buying cocaine.", 
			"I'm beginning to think he was just using me for sex.", 
			"I had a friend that got pinkeye from that.", 
			"The difference between me and a lesbian is... about three drinks.", 
			"I am only doing this to pay my way through college!", 
			"Once I get my degree I am going to stop and get a real job.", 
			"NO WAY! I dont do any drugs!", 
			"Dont tell anybody but I only do this kind of extra stuff with you.", 
			"That guy gave me a buck, and all I had to do was rub my tits on his face!", 
			"How do you like your eggs?", 
			"I'm not a cop.", 
			"Nachos are really nice, but I need to make some money tonight.", 
			"I don't understand you. I'm being real nice here!", 
			"You cant do that here!", 
			"Brendie for admin.", 
			"Elveroff is Anarchy.", 
			"Something smells fishy.", 
			"I can feel the sparks igniting.", 
			"Chinese Charity is on the case.", 
			"Inspector Charity is on the case.", 
			"Fellows be Yellow m8.", 
			"Corp just gets harder and harder.", 
			"Hop hop hop.", 
			"I have all 99s. You Jelly?", 
			"You guys got nothin' on me.", 
			"You guys are noooooooooobs.", 
			"Let's ramp it up, guys!", 
			"Hey guys! Call me at 555-5555!", 
			"I'm pretty much free any time.", 
			"Spark is on fire!", 
			"Things are heating up!", 
			"Woah bro. I'm gonna have to stop you there.", 
			"You can find me at the corner of Edveville.", 
			"I agree 100%", 
			"I can't stop", 
	};
	
	public Player followPlayer;

	public void realFinish() {
		if (hasFinished())
			return;

		// new Highscores(this).submit();

		stopAll();
		controlerManager.logout(); // checks what to do on before logout for
		cutscenesManager.logout();
		DwarfCannon.removeDwarfCannon();
		// login
		running = false;
		house.finish();
		// friendsIgnores.sendFriendsMyStatus(false);
//	/	GrandExchange.unlinkOffers(this);
		// if (currentFriendChat != null)
		// currentFriendChat.leaveChat(this, true);
		// if (clanManager != null)
		// clanManager.disconnect(this, false);
		// if (guestClanManager != null)
		// guestClanManager.disconnect(this, true);
		if (familiar != null && !familiar.isFinished())
			familiar.dissmissFamiliar(true);
		else if (pet != null)
			pet.pickup();
		if (slayerManager.getSocialPlayer() != null)
			slayerManager.resetSocialGroup(true);
		setFinished(true);
		World.updateEntityRegion(this);
		World.removePlayer(this);
	}

	@Override
	public void refreshSpawnedItems() {

	}

	@Override
	public void refreshSpawnedObjects() {

	}

	@Override
	public void reset() {
		super.reset();
		refreshHitPoints();
		hintIconsManager.removeAll();
		skills.restoreSkills();
		combatDefinitions.resetSpecialAttack();
		prayer.reset();
		combatDefinitions.resetSpells(true);
		resting = false;
		skullDelay = 0;
		foodDelay = 0;
		potDelay = 0;
		poisonImmune = 0;
		fireImmune = 0;
		// getTemporaryAttributtes().put("LAST_VENG", (Long)0);
		castedVeng = false;
		setRunEnergy(100);
		setRun(true);
		appearence.generateAppearenceData();
		// removecontroler
	}

	/*
	 * @Override public Session getSession() { return new GameSession(null,
	 * this) {
	 * 
	 * @Override public void getIPAddress() {
	 * 
	 * } } }
	 */

	//@Override
	//public void sendDeath(final Entity source) {
	//	super.sendDeath(source);
	//	script.setAttackedBy(null);
		/*
		 * if (source instanceof Player && (((Player)source).scriptEnabled ||
		 * ((Player)source).isRobot())) { if (Utils.getRandom(1) == 0)
		 * ((Player)source).sendPublicChatMessage(new PublicChatMessage(Utils
		 * .fixChatMessage(Utils.getRandom(1) == 0 ? "Good fight" :
		 * (Utils.getRandom(1) == 0 ? "gf" : "gg")) , 0)); if (source instanceof
		 * Robot) { ((Robot)source).script.setAttackedBy(null);
		 * ((Robot)source).script.pickUpStuff(new WorldTile(getX(), getY(), 0));
		 * } else { ((Player)source).script.setAttackedBy(null);
		 * ((Player)source).script.pickUpStuff(new WorldTile(getX(), getY(),
		 * 0)); } }
		 */
	//}

	@Override
	public void setAttackedBy(final Entity source) {
		super.setAttackedBy(source);
		if (script != null)
			script.setAttackedBy(source);
	}

	public void setAttackedBy2(final Entity source) {
		super.setAttackedBy(source);
		// script.setAttackedBy(source);//already done
	}

	// now that we inited we can start showing game
	@Override
	public void start() {
		loadMapRegions();
		started = true;
		run();
		if (isDead())
			sendDeath(null);
	}
}