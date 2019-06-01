package com.rs.game.player.content.ports;

import java.io.Serializable;

import com.rs.game.player.content.ports.crew.Crew;

/**
 * handles the voyages
 * @author paolo
 *
 */
public class Voyages implements Serializable {
	
	/**
	 * ser id
	 */
	private static final long serialVersionUID = 5508022266349249258L;
	/*
	 * type of rewards
	 */
	public enum RewardTypes  {NORMAL,RECIPE,TRADE_GOODS};
	/*
	 * all the regions
	 */
	public enum Regions {
		THE_ARC("", new Crew[] { 
				new Crew("Travelling Drunk",70,0,0,0,27265,0),
				new Crew("Stowaway",0,70,0,0,27266,0),
				new Crew("Smuggler",0,0,70,0,27267,0), 
				new Crew("Varrock Chef",150,0,0,0,27268,110),
				new Crew("Brimhaven Pirate",0,150,0,0,27269,110),
				new Crew("Catherby Fisherman",0,0,150,0,27270,110),
				new Crew("Bamboo Golow",60,100,0,0,27273,120),
				new Crew("First Mate",50,50,50,0,27274,100),
				new Crew("Cyclops",100,150,0,0,27275,250)
				
				}),
		THE_SKULL("", new Crew[] { 
				new Crew("Eastern Bannerman",350,0,0,0,27265,0),
				new Crew("Eastern Musketeer",0,350,0,0,27266,0),
				new Crew("Eastern Guide",0,0,350,0,27267,0), 
				new Crew("Exploding Golem",170,200,0,0,27268,110),
				new Crew("Easter Overseer",120,120,120,0,27269,110),
				new Crew("Siren Whalerider",0,200,350,0,27270,110),
				}),
		THE_HOOK("", new Crew[] { new Crew("",0,0,0,0,0)}),
		THE_SCYTHE("", new Crew[] { new Crew("",0,0,0,0,0)}),
		THE_BOWL("", new Crew[] { new Crew("",0,0,0,0,0)}),
		THE_PINCERS("", new Crew[] { new Crew("",0,0,0,0,0)}),
		THE_LOOP("", new Crew[] { new Crew("",0,0,0,0,0)}),
		THE_SHIELD("", new Crew[] { new Crew("",0,0,0,0,0)}),
		SPECIAL("", new Crew[] { new Crew("",0,0,0,0,0)});
		String name;
		public Crew possibleCrew[];
		 private Regions(String name, Crew crews[]){
			this.name = name;
			this.possibleCrew = crews;
		}
		 
		};
	/*
	 * enum of all the voyages
	 */
	public enum Voyage {
		//the arc
		CRACKED_SKULL("A Cracked Skull",50,Regions.THE_ARC,250,250,0,RewardTypes.NORMAL),
		DARING_RAID("A Daring Raid",80,Regions.THE_ARC,0,400,0,RewardTypes.NORMAL),
		FEAT_OF_SEAMANSHIP("A Feat of Seamanship",100,Regions.THE_ARC,150,150,150,RewardTypes.NORMAL),
		FRIEND("A Friend Indeed",60,Regions.THE_ARC,0,0,430,RewardTypes.NORMAL),
		NEW_HOME("A New Home",100,Regions.THE_ARC,500,0,0,RewardTypes.NORMAL),
		EXPLORE("Explore the Seas",90,Regions.THE_ARC,0,0,450,RewardTypes.NORMAL),
		FIRST("First Contact",100,Regions.THE_ARC,500,0,500,RewardTypes.NORMAL),
		SPIRITS("High Spirits",100,Regions.THE_ARC,700,500,0,RewardTypes.NORMAL),
		HOWDY_PILGRIMS("Howdy Pilgrims",120,Regions.THE_ARC,0,500,500,RewardTypes.NORMAL),
		IN_DIRE_NEED("In Dire Need",100,Regions.THE_ARC,500,0,500,RewardTypes.NORMAL),
		ISLAND_EXPLORER("Island explorer",300,Regions.THE_ARC,0,750,0,RewardTypes.NORMAL),
		SUPPLY_AND_DEMAND("Supply And Demand",80,Regions.THE_ARC,300,300,300,RewardTypes.NORMAL),
		TO_THE_RESCUE("To The Rescue",85,Regions.THE_ARC,100,600,0,RewardTypes.NORMAL),
		GOOD_DEAL("Good Deal",285,Regions.THE_ARC,100,200,200,RewardTypes.NORMAL),
		X_MARKS_THE_SPOT("Howdy Pilgrims",250,Regions.THE_ARC,350,350,350,RewardTypes.NORMAL),
		CHRIME_COLLECTOR("Chrime collector",350,Regions.THE_ARC,100,600,100,RewardTypes.NORMAL),
		//the skull
		DARING_DAY("A Daring Day",350,Regions.THE_SKULL,0,1200,0,RewardTypes.NORMAL),
		DARING_RAID_2("A Daring Raid",250,Regions.THE_SKULL,200,1000,0,RewardTypes.NORMAL),
		FEAT_OF_SEAMANSHIP2("A Feat of Seamanship",300,Regions.THE_SKULL,600,600,600,RewardTypes.NORMAL),
		FRIEND2("A Friend Indeed",280,Regions.THE_SKULL,0,0,1300,RewardTypes.NORMAL),
		NEW_HOME2("A New Home",320,Regions.THE_SKULL,1200,0,0,RewardTypes.NORMAL),
		EXPLORE2("Explore the Seas",90,Regions.THE_SKULL,800,0,800,RewardTypes.NORMAL),
		//special voyages
		THE_FORGOTTEN_SCROLlS("The Forgotten Scroll",1000,Regions.SPECIAL,0,0,0,RewardTypes.RECIPE),
		CAN_I_EAT_IT("Can I Eat It?",450,Regions.SPECIAL,0,0,0,RewardTypes.TRADE_GOODS),
		TRANSMUTE_THE_SALVAGE("Transmute The Salvage",450,Regions.SPECIAL,0,0,0,RewardTypes.TRADE_GOODS),
		A_ARCANE_BOUNTY("A Arcane Bounty",450,Regions.SPECIAL,0,0,0,RewardTypes.TRADE_GOODS),
		A_JOINT_ACQUISITION("A Joint Acquisition",450,Regions.SPECIAL,0,0,0,RewardTypes.TRADE_GOODS);
		private String name;
		private int chrimes;
		private Regions land;
		private int moral;
		private int combat;
		private int seafaring;
		private RewardTypes type;
		
		private Voyage(String name,int chrime,Regions land,int moral,int combat,int seafaring,RewardTypes type){
			this.setName(name);
			this.setChrimes(chrime);
			this.setMoral(moral);
			this.setCombat(combat);
			this.setSeafaring(seafaring);
			this.setLand(land);
			this.setType(type);
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the moral
		 */
		public int getMoral() {
			return moral;
		}

		/**
		 * @param moral the moral to set
		 */
		public void setMoral(int moral) {
			this.moral = moral;
		}

		/**
		 * @return the combat
		 */
		public int getCombat() {
			return combat;
		}

		/**
		 * @param combat the combat to set
		 */
		public void setCombat(int combat) {
			this.combat = combat;
		}

		/**
		 * @return the seafaring
		 */
		public int getSeafaring() {
			return seafaring;
		}

		/**
		 * @param seafaring the seafaring to set
		 */
		public void setSeafaring(int seafaring) {
			this.seafaring = seafaring;
		}

		/**
		 * @return the type
		 */
		public RewardTypes getType() {
			return type;
		}

		/**
		 * @param type the type to set
		 */
		public void setType(RewardTypes type) {
			this.type = type;
		}

		/**
		 * @return the land
		 */
		public Regions getLand() {
			return land;
		}

		/**
		 * @param land the land to set
		 */
		public void setLand(Regions land) {
			this.land = land;
		}

		/**
		 * @return the chrimes
		 */
		public int getChrimes() {
			return chrimes;
		}

		/**
		 * @param chrimes the chrimes to set
		 */
		public void setChrimes(int chrimes) {
			this.chrimes = chrimes;
		}
		
		
	}
	
	public static int countRegionVoyages(Regions reg){
		int count = 0;
		for(Voyage voy : Voyage.values()){
			if(voy.getLand() == reg)
				count ++;
		}
		return count;
	}
	/**
	 * gets the duration of the voyage based on the region
	 * @param voy
	 * @return
	 */
	public static int getTime(Voyage voy){
		switch(voy.getLand()){
		case THE_ARC:
			return 60 * 33 * 1000;  //22min
		case THE_SKULL:
			return 60 * 80 * 1000; //80 min
		case THE_HOOK:
			return 60 * 160 * 1000; //160 min
		case THE_SCYTHE:
			return 60 * 320 * 1000; 
		case THE_BOWL:
			return 60 * 640 * 1000; 
		case THE_PINCERS: 
			return 60 * 800 * 1000; 
		case THE_LOOP:
			return 60 * 906 * 1000; 
		case THE_SHIELD:
			return 60 * 1066 * 1000; 
		case SPECIAL:
			return 60 * 600 * 1000;
		}
		return 0;
	}
	
	public static int getTime(Regions reg){
		switch(reg){
		case THE_ARC:
			return 60 * 33 * 1000;  //22min
		case THE_SKULL:
			return 60 * 80 * 1000; //80 min
		case THE_HOOK:
			return 60 * 160 * 1000; //160 min
		case THE_SCYTHE:
			return 60 * 320 * 1000; 
		case THE_BOWL:
			return 60 * 640 * 1000; 
		case THE_PINCERS: 
			return 60 * 800 * 1000; 
		case THE_LOOP:
			return 60 * 906 * 1000; 
		case THE_SHIELD:
			return 60 * 1066 * 1000; 
		case SPECIAL:
			return 60 * 600 * 1000;
		}
		return 0;
	}
   
}