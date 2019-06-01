package com.rs.game.player.content.custom;

import com.rs.game.player.Player;

/**
 * 
 * @author Paolo
 *
 */
public class BossDrops {
     
	
	public static int AchievementBoard = 583;

	public static void BossDropsInterface(Player player) {
			/*
			 * Bosses
			 */
			/*for (int i = 10; i < 100; i++) {
			player.getPackets().sendIComponentSprite(AchievementBoard,i, 13919);
		     }*/
			player.getPackets().sendIComponentSprite(AchievementBoard,56, 13919);
			player.getPackets().sendIComponentSprite(AchievementBoard,52, 13925);
			player.getPackets().sendIComponentSprite(AchievementBoard,51, 13928);
			player.getPackets().sendIComponentSprite(AchievementBoard,50, 13934);
			player.getInterfaceManager().sendInterface(AchievementBoard);
			/**
			  * First row
			  **/
			player.getPackets().sendIComponentText(AchievementBoard, 14, "Bosses drop tables Xd");
			player.getPackets().sendIComponentText(AchievementBoard, 50, "Bal'lak the pumler");
			player.getPackets().sendIComponentText(AchievementBoard, 51, "Blink");
			player.getPackets().sendIComponentText(AchievementBoard, 52, "Commander Zilyana");
			player.getPackets().sendIComponentText(AchievementBoard, 53, "Corporeal beast");
			/**
			  * Second row
			  **/
			player.getPackets().sendIComponentText(AchievementBoard, 54, "Cursebearer");
			player.getPackets().sendIComponentText(AchievementBoard, 55, "Dks");
			player.getPackets().sendIComponentText(AchievementBoard, 56, "General Graador");
			player.getPackets().sendIComponentText(AchievementBoard, 57, "Glacors");
			/**
			  * Third row
			  **/
			player.getPackets().sendIComponentText(AchievementBoard, 58, "Gluttonous");
			player.getPackets().sendIComponentText(AchievementBoard, 59, "Kalphite queen");
			player.getPackets().sendIComponentText(AchievementBoard, 71, "King black dragon");
			player.getPackets().sendIComponentText(AchievementBoard, 60, "Kree'arra");
		    /**
			  * Forth row
			  **/
			player.getPackets().sendIComponentText(AchievementBoard, 61, "Kril'Tsutsaroth");
			player.getPackets().sendIComponentText(AchievementBoard, 62, "Party demon");
			player.getPackets().sendIComponentText(AchievementBoard, 64, "Pest queen");
			player.getPackets().sendIComponentText(AchievementBoard, 63, "Necrolord");
			/**
			  * Fith row
			  **/
			player.getPackets().sendIComponentText(AchievementBoard, 65, "Nex");
			player.getPackets().sendIComponentText(AchievementBoard, 70, "Queen black dragon");
			player.getPackets().sendIComponentText(AchievementBoard, 66, "Skelet trio");
			player.getPackets().sendIComponentText(AchievementBoard, 67, "Sunfreet");
			/**
			  * 6th row
			  **/
			player.getPackets().sendIComponentText(AchievementBoard, 68, "Tds");
			player.getPackets().sendIComponentText(AchievementBoard, 69, "Vorago");
			player.getPackets().sendIComponentText(AchievementBoard, 72, "Wildy worm");
			player.getPackets().sendIComponentText(AchievementBoard, 73, "World-gorger");
			/** 
			  * 7th row
			  **/
			player.getPackets().sendIComponentText(AchievementBoard, 80, "YK'lagor");
			player.getPackets().sendIComponentText(AchievementBoard, 74, "");
			player.getPackets().sendIComponentText(AchievementBoard, 75, "");
			player.getPackets().sendIComponentText(AchievementBoard, 76, "");
			/**
			  * 8th row
			  **/
			player.getPackets().sendIComponentText(AchievementBoard, 77, "");
			player.getPackets().sendIComponentText(AchievementBoard, 78, "");
			player.getPackets().sendIComponentText(AchievementBoard, 79, "");
			player.getPackets().sendIComponentText(AchievementBoard, 81, "");
	
	}
	
	public static void Ballak(Player player) {
			
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "");
			player.getInterfaceManager().sendInterface(538);
	} 
	public static void Blink(Player player) {
	       
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "");
			player.getInterfaceManager().sendInterface(538);
	} 
	
	public static void CommanderZilyana(Player player) {

			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "saradomin hilt,saradomin murmur,dominion staff,armadyl crossbow,frozen key");
			player.getInterfaceManager().sendInterface(538);
	} 

	public static void CorporealBeast(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "sigil,elixir,spirit shield,iban staff,wand of trechary");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Cursebearer(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Dks(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "seercrull,berzerker ring,seers ring,archers ring");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void GeneralGraador(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "bandos hilt,bandos tasset,bandos chestplate,bandos helmet,bandos warshield,bandos boots,dominion sword,frozen key");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Glacors(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "ragefire boots,steadfast boots,glavien boots,armadyl staff");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Gluttonous(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "scythe,pumpkin");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void KalphiteQueen(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "dragon chainbody,dragon boots,onyx bolts e x100,fighter torso,dragon bolts e x300");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void KingBlackDragon(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "dragon fullhelm,dragonfire shield");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void KreeArra(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "armadyl hilt,armadyl helmet,armadyl chainskirt,armadyl chestplate,armadyl boots,armadyl gloves,dominion crossbow,armadyl buckler,frozen key");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void KrilTsutsaroth(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "zamorak hilt,sub gown,sub robe,frozen key");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void PartyDemon(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "primal helmet,primal platebody,primal platelegs,primal boots,primal gloves,primal shield,primal rapier,primal longsword,primal maul,disk of returning, half jugs of wine,blue hween,red hween,green hween,pumpkin");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void PestQueen(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "void robe,void ranged robe,");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Necrolord(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "kalphite platebody,kalphite platelegs,kalphite helmet,kalphite boots,kalphite gloves,guy fawks helm");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Nex(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "pernix cowl,pernix body,pernix chaps,pernix vamps,pernix boots,zayrte bow,torva helmet,torva platebody,torva platelegs,torva boots,torva gloves,virtus mask,virtus robetope,virtus robelegs,virtus gloves,virtus boots,virtus wand");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void QueenBlackDragon(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "royal corssbow,royal body,royal cowl,royal legs,royal vamps");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void SkeletTrio(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "gilded dragon platelegs,gilded dragon chain,gilded helm,dragon pickaxe,sacred items,");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Sunfreet(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "20m gp,offhands primal,elite clue,bezerker ring b,archers ring b,seers ring b,");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Tds(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "dragon claws, dragon limbs");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void Vorago(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "totem tickets,frostbones x400,mystery box");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void WildyWorm(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "Elite void, korasi, Fury, Black (g)");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void WorldGorger(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "dragon platebody,dragon fullhelm,dragon kiteshield,divine shield,spectral shield");
			player.getInterfaceManager().sendInterface(538);
	}
	public static void YkLagor(Player player) {
	
			player.getPackets().sendIComponentText(538, 5, " rare drops.");
	        player.getPackets().sendIComponentText(538, 6, "special items yummy");
			player.getInterfaceManager().sendInterface(538);
	}
	
	 public static void handleButtons(Player player, int componentId) {
		  if (componentId == 50) {	
		        Ballak(player);
		        }
		  if (componentId == 51) {	
		        Blink(player);
		        }
		  if (componentId == 52) {	
		       CommanderZilyana(player);
		        }
	        if (componentId == 53) {	
	        	CorporealBeast(player);
	        }
			if (componentId == 54) {	
	        	Cursebearer(player);
	        }
			if (componentId == 55) {	
	        	Dks(player);
	        }
			if (componentId == 56) {	
	        	GeneralGraador(player);
	        }
			if (componentId == 57) {	
	        	Glacors(player);
	        }
			if (componentId == 58) {	
	        	Gluttonous(player);
	        }
			if (componentId == 59) {	
	        	KalphiteQueen(player);
	        }
			if (componentId == 71) {	
	        	KingBlackDragon(player);
	        }
			if (componentId == 60) {	
	        	KreeArra(player);
	        }
			if (componentId == 61) {	
	        	KrilTsutsaroth(player);
	        }
			if (componentId == 62) {	
	        	PartyDemon(player);
	        }
			if (componentId == 64) {	
	        	PestQueen(player);
	        }
			if (componentId == 63) {	
	        	Necrolord(player);
	        }
			if (componentId == 65) {	
	        	Nex(player);
	        }
			if (componentId == 70) {	
	        	QueenBlackDragon(player);
	        }
			if (componentId == 66) {	
	        	SkeletTrio(player);
	        }
			if (componentId == 67) {	
	        	Sunfreet(player);
	        }
			if (componentId == 68) {	
	        	Tds(player);
	        }
			if (componentId == 70) {	
	        	QueenBlackDragon(player);
	        }
			if (componentId == 69) {	
	        	Vorago(player);
	        }
			if (componentId == 72) {	
	        	WildyWorm(player);
	        }
			if (componentId == 73) {	
	        	WorldGorger(player);
	        }
			if (componentId == 80) {	
	        	YkLagor(player);
	        }
			
	 }

}