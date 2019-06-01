package com.rs.game.player.actions.slayer;

/**
 * This is a list of assignable tasks
 * 
 * @author Emperial
 * 
 */
public enum SlayerTasks {
	/**
	 * TURAEL TASKS
	 */
        CAVEBUGS("Cave Bugs", TaskSet.TURAEL, 7, 20, 40, "Cave bug"),
        CHICKENS("Chickens", TaskSet.TURAEL, 1, 20, 40, "Chicken"), 
        COWS("Cows", TaskSet.TURAEL, 1, 25, 60, "Cow calf", "Cow"), 
        GOBLINS("Goblins", TaskSet.TURAEL, 1, 20, 40, "Goblin", "Cave goblin guard"), 
        CRAWLING_HANDS("Crawling hands", TaskSet.TURAEL, 5, 20, 60,"Zombie hand", "Skeletal hand", "Crawling Hand"), 
        SCORPIONS("Scorpions", TaskSet.TURAEL, 1, 20, 40, "Scorpion", "King scorpion", "Poison Scorpion", "Pit Scorpion", "Khazard scorpion", "Grave scorpion"),
        WOLVES("Wolves", TaskSet.TURAEL, 1, 20, 40, "Ice wolf", "Wolf", "Big wolf", "Big Wolf", "White Wolf"),
        BIRDS("Birds", TaskSet.TURAEL, 1, 20, 40, "Chicken", "Duck", "Terrorbird", "Chompy Bird", "Jubbly Bird"),
        MEN("Men and woman", TaskSet.TURAEL, 1, 10, 20, "Woman", "Man"), 
        ABOM("Gelatinous Abominations", TaskSet.TURAEL, 1, 5, 15, "Gelatinous Abomination", "Magic Stick"), 
		/*
	 * Chaeldar Tasks
	 */
	 
	ABBYSPEC2("Aberrant spectres", TaskSet.CHAELDAR, 60, 20, 40, "Aberrant spectre"), 
	BANSHEES3("Banshees", TaskSet.CHAELDAR, 15, 20, 40, "Banshee", "banshee", "Mighty banshee", "Mighty_banshee"), 
	Basilisks("Basilisks", TaskSet.CHAELDAR, 40, 20, 40, "Basilisk"), 
	BloodVelds("Bloodvelds", TaskSet.CHAELDAR, 50, 20, 40, "Bloodveld"), 
	Blue_Dragon("Blue Dragons", TaskSet.CHAELDAR, 1, 20, 40, "Blue dragon"), 
	Bronze_Dragon("Bronze Dragons", TaskSet.CHAELDAR, 11, 20, 40, "Bronze dragon"), 
	CC("Cave Crawlers", TaskSet.CHAELDAR, 10, 20, 40, "Cave crawler"), 
	CH("Crawling Hands", TaskSet.CHAELDAR, 5, 20, 40, "Crawling hand", "Zombie hand", "Skeletal hand"), 
	Dags("Dagganoths", TaskSet.CHAELDAR, 1, 20, 40, "Dagannoth", "Dagannoth Mother", "Dagannoth guardian", "Dagannoth spawn", "Dagannoth Prime", "Dagannoth Supreme", "Dagannoth Rex"), 
	FG("Fire giants", TaskSet.CHAELDAR, 1, 20, 40, "Fire giant"), 
	FUNG("Fungal Mages", TaskSet.CHAELDAR, 1, 20, 40, "Fungal mage", "Fungal magi"), 
	Garg("Gargoyles", TaskSet.CHAELDAR, 75, 20, 40, "Gargoyle"), 
	Grots2("Grotworms", TaskSet.CHAELDAR, 1, 20, 40, "Young grotworm", "Grotworm", "Mature grotworm"),
	JS("Jungle Strykewyrms", TaskSet.CHAELDAR, 73, 20, 40, "Jungle strykewyrm"), 
	IMages("Infernal mages", TaskSet.CHAELDAR, 45, 20, 40, "Infernal Mage", "Infernal_Mage"), 
	JELLY("Jellies", TaskSet.CHAELDAR, 52, 20, 40, "Jelly"), 
	Kalphite2("Kalphites", TaskSet.CHAELDAR, 1, 20, 40, "Exiled kalphite queen", "Kalphite queen", "Kalphite larva", "Kalphite worker", "Kalphite soldier", "Kalphite guardian"), 
	LDemon("Lesser Demons", TaskSet.CHAELDAR, 1, 20, 40, "Lesser demon"), Turoth("Turoths", TaskSet.CHAELDAR, 55, 20, 40, "Turoth"), ANKOU2("Ankou", TaskSet.CHAELDAR, 1, 30, 40, "Ankou"), 
	OGRES("Ogres", TaskSet.CHAELDAR, 1, 20, 30, "Ogre", "Zogre", "Skogre", "Jogre"), 
	COCKROACHES("Cockroaches", TaskSet.CHAELDAR, 1, 20, 30, "Cockroach drone", "Cockroach worker", "Cockroach soldier", "Cockroach_soldier", "Cockroach_worker", "Cockroach_drone"), 
        /*
         * Kuradel
         */
        BANSHEES("Banshees", TaskSet.KURADEL, 15, 20, 40, "Banshee", "banshee", "Mighty banshee", "Mighty_banshee"), 
        ABBYSPEC("Aberrant spectres", TaskSet.KURADEL, 60, 50, 80, "Aberrant spectre"),
		//CELESTIAL_DRAGONS("Celestial dragons", TaskSet.KURADEL, 75, 20, 60, "Celestial dragons"),
		WILDYWYRM("Wildywyrms", TaskSet.KURADEL, 92, 20, 60, "WildyWyrm"),
        ABYSSAL_DEMON("Abyssal demons", TaskSet.KURADEL, 85, 50, 80, "Abyssal demon"),
        BLACK_DEMON("Black demons", TaskSet.KURADEL, 1, 50, 80, "Black demon"),
        BLACK_DRAGON("Black dragons", TaskSet.KURADEL, 1, 40, 70, "Black dragon"),
        BLUE_DRAGON("Blue Dragons", TaskSet.KURADEL, 1, 20, 40, "Blue dragon"),
        BLOODVELD("Bloodvelds", TaskSet.KURADEL, 50, 40, 70, "Bloodveld"),
        DAGGANOTHS("Dagannoths", TaskSet.KURADEL, 1, 70, 150, "Dagannoth", "Dagannoth Mother", "Dagannoth guardian", "Dagannoth spawn", "Dagannoth Prime", "Dagannoth Supreme", "Dagannoth Rex"),
        DARK_BEAST("Dark beasts", TaskSet.KURADEL, 90, 20, 40, "Dark beast"),
        DESERT_STRYKEWYRM("Desert strykewyrms", TaskSet.KURADEL, 77, 20, 40, "Desert strykewyrm"),
        DUST_DEVIL("Dust devils", TaskSet.KURADEL, 65, 20, 40, "Dust devil"),
        FIRE_GIANT("Fire giants", TaskSet.KURADEL, 1, 20, 40, "Fire giant"),
        GCreatures2("Ganodermic beasts", TaskSet.KURADEL, 95, 20, 40, "Ganodermic beast"),
        GARGOYLES("Gargoyles", TaskSet.KURADEL, 75, 60, 120, "Gargoyle"),
        GREATER_DEMON("Greater demons", TaskSet.KURADEL, 1, 60, 120, "Greater demon"),
        GRIFALOPINE("Grifalopines", TaskSet.KURADEL, 88, 20, 40, "Grifalopine"),
        GRIFALOROO("Grifaloroos", TaskSet.KURADEL, 82, 20, 40, "Grifaloroo"),
        GROTWORM("Grotworms", TaskSet.KURADEL, 1, 20, 40, "Young grotworm", "Grotworm", "Mature grotworm"),
        HELLHOUNDS("Hellhounds", TaskSet.KURADEL, 1, 80, 170, "Hellhound"), 
        ICE_STRYKEWYRM("Ice strykewyrms", TaskSet.KURADEL, 93, 20, 40, "Ice strykewyrm"),
        IRON_DRAGON("Iron dragons", TaskSet.KURADEL, 1, 20, 40, "Iron dragon"),
        LIVING_ROCK_CREATURE("Living rock creatures", TaskSet.KURADEL, 1, 20, 40, "Living rock protector", "Living rock striker", "Living rock patriarch"),
        MITHRIL_DRAGON("Mithril dragons", TaskSet.KURADEL, 1, 20, 40, "Mithril dragon"),
        MUTATED_JADINKO("Mutated jadinkos", TaskSet.KURADEL, 80, 60, 120, "Mutated jadinko baby", "Mutated jadinko guard", "Mutated jadinko male"),
        NECHRYEALS("Nechryaels", TaskSet.KURADEL, 80, 20, 40, "Nechryael"),
        SKELETAL_WYVERN("Skeletal Wyverns", TaskSet.KURADEL, 72, 20, 40, "Skeletal Wyvern"),
        SPIRITUAL_MAGE("Spiritual Mages", TaskSet.KURADEL, 83, 20, 40, "Spiritual mage"),
        STEEL_DRAGON("Steel Dragons", TaskSet.KURADEL, 1, 20, 40, "Steel dragon"),
        ANKOU("Ankous", TaskSet.KURADEL, 1, 40, 45, "Ankou"),
        WATERFIEND("Waterfiends", TaskSet.KURADEL, 1, 70, 150, "Waterfiend"),
		AIRUT("Airuts", TaskSet.KURADEL, 91, 50, 91, "Airut"),
		EDIMMU("Ediummus", TaskSet.KURADEL,91, 50, 91, "Edimmu"),
		AUTOMATOM("Automaton Guardians", TaskSet.KURADEL, 20, 50, 91, "Automaton guardian","Automaton_guardian");
      
		
		
        
	private SlayerTasks(String simpleName, TaskSet type, int level, int min, int max,
			String... monsters) {
		this.type = type;
		this.slayable = monsters;
		this.simpleName = simpleName;
                this.level = level;
		this.min = min;
		this.max = max;
        }

	/**
	 * A simple name for the task
	 */
	public String simpleName;

	/**
	 * The task set
	 */
	public TaskSet type;
	/**
	 * The monsters that will effect this task
	 */
	public String[] slayable;
	/**
	 * The minimum amount of monsters the player may be assigned to kill
	 */
	public int min;
	/**
	 * The maximum amount of monsters the player may be assigned to kill
	 */
	public int max;
        
        /*
         * Slayer level for monsters
         */
        public int level;
        
        public int getLevel() {
            return level;
        }
}
