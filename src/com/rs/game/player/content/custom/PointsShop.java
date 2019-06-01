package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
/**
 * 
 * @author Paolo
 *
 */
public class PointsShop {
     
	
	public static void ShopInterface(Player player) {
	
	/**
	 * Top Part / menu buttons / Tabs
	 */
	player.getPackets().sendIComponentText(473, 31, "Points Shop");
	player.getPackets().sendIComponentText(473, 227, "<col=B00000><shad=FF0000>Vote");
	player.getPackets().sendIComponentText(473, 224, "<col=B00000><shad=FF0000>Monster");
	player.getPackets().sendIComponentText(473, 221, "<col=B00000><shad=FF0000>Contract");
	player.getPackets().sendIComponentText(473, 36,  "<col=B00000><shad=FF0000>Trivia");
/**
 * First Tab
 */
	/*
	 * Button box 1
	 */
	player.getInterfaceManager().sendInterface(473);
	player.getPackets().sendItemOnIComponent(473, 43, 24154, 1);
	player.getPackets().sendItemOnIComponent(473, 44, 24154, 5);
	player.getPackets().sendIComponentText(473, 46,"");
	player.getPackets().sendIComponentText(473, 45, "  <shad=FF0000>1 vote Points");
	/*
	 * Button box 2
	 */
	player.getPackets().sendItemOnIComponent(473, 57, 7478, 1);
	player.getPackets().sendIComponentText(473, 58,"A custom title");
	player.getPackets().sendIComponentText(473, 60, "");
	player.getPackets().sendIComponentText(473, 59, "  <shad=FF0000>10 Points");
	/*
	 * Button box 3
	 */
	player.getPackets().sendItemOnIComponent(473, 50, 11021, 1);
	player.getPackets().sendIComponentText(473, 51,"Full chicken suit.");
	player.getPackets().sendIComponentText(473, 53, "");
	player.getPackets().sendIComponentText(473, 52, "  <shad=FF0000>20 Points");
	/*
	 * Button box 4
	 */
	player.getPackets().sendItemOnIComponent(473, 64, 989, 1);
	player.getPackets().sendItemOnIComponent(473, 65, 989, 5);
	player.getPackets().sendIComponentText(473, 67, "");
	player.getPackets().sendIComponentText(473, 66, "  <shad=FF0000>1 Points");
	/**
	 * First Tab 2
	 */
	//   Monster points
	/*
	 * Button Box 1
	 */
	player.getPackets().sendItemOnIComponent(473, 126, 5604, 1);
	player.getPackets().sendIComponentText(473, 127, "Charm magnet");
	player.getPackets().sendIComponentText(473, 128, "collects charms");
	player.getPackets().sendIComponentText(473, 130, "750 monster points");
	/*
	 * Button Box 2
	 */
	player.getPackets().sendItemOnIComponent(473, 98, 11664, 1);
	player.getPackets().sendIComponentText(473, 99, "Void Range set");
	player.getPackets().sendIComponentText(473, 100, "1000 monster points");
	player.getPackets().sendIComponentText(473, 102, "");
	/*
	 * Button Box 3
	 */
	player.getPackets().sendItemOnIComponent(473, 119, 11663, 1);
	player.getPackets().sendIComponentText(473, 120, "Void Mage set");
	player.getPackets().sendIComponentText(473, 121, "1000 monster points");
	player.getPackets().sendIComponentText(473, 123, "");
	/*
	 * Button Box 4
	 */
	player.getPackets().sendItemOnIComponent(473, 91, 8839, 1);
	player.getPackets().sendIComponentText(473, 92, "Void Melee set");
	player.getPackets().sendIComponentText(473, 93, "1000 monster points");
	player.getPackets().sendIComponentText(473, 95, "");
	/*
	 * Button box 5
	 */
	player.getPackets().sendItemOnIComponent(473, 112, 28783, 1);
	player.getPackets().sendIComponentText(473, 113, "Greater Demon Armor Set");
	player.getPackets().sendIComponentText(473, 115, "<col=FF0000> + 1000 monster Kills");
	player.getPackets().sendIComponentText(473, 114, "20 PvP Points + 550k Pvp Tokens");
	player.getPackets().sendIComponentText(473, 116, "");
	/*
	 * Button box 6
	 */
	player.getPackets().sendItemOnIComponent(473, 85, 25386, 1);
	player.getPackets().sendIComponentText(473, 86, "K'ril's Godcrusher Armor Set");
	player.getPackets().sendIComponentText(473, 88, "<col=FF0000>5 Tasks completed + 2000 monster Kills");
	player.getPackets().sendIComponentText(473, 87, "38 PvP Points + 550k Pvp Tokens");
	player.getPackets().sendIComponentText(473, 116, "");
	/*
	 * Button box 7
	 */
	player.getPackets().sendItemOnIComponent(473, 105, 26182, 1);
	player.getPackets().sendIComponentText(473, 106, "TokHaar Warlord Armor Set");
	player.getPackets().sendIComponentText(473, 108, "<col=FF0000>6 Tasks completed + 2500 monster Kills");
	player.getPackets().sendIComponentText(473, 107, "45 PvP Points + 500k Pvp Tokens");
	player.getPackets().sendIComponentText(473, 109, "");
	/*
	 * Button box 8
	 */
	player.getPackets().sendItemOnIComponent(473, 79, 28792, 1);
	player.getPackets().sendIComponentText(473, 80, "Kalphite Armor Set");
	player.getPackets().sendIComponentText(473, 82, "<col=FF0000>10 Tasks completed + 3000 monster Kills");
	player.getPackets().sendIComponentText(473, 81, "50 PvP Points + 500k Pvp Tokens");
	/**
	 * First Tab 3
	 */
	/*
	 * Button box 1
	 */
	player.getPackets().sendItemOnIComponent(473, 144, 28813, 1);
	player.getPackets().sendItemOnIComponent(473, 145, 28813, 1);
	player.getPackets().sendIComponentText(473, 147, "+153 Str Bonus");
	player.getPackets().sendIComponentText(473, 146, "<shad=FF0000>UNKNOWN PvP Points");
	/*
	 * Button box 2
	 */
	player.getPackets().sendItemOnIComponent(473, 136, 28818, 1);
	player.getPackets().sendItemOnIComponent(473, 139, 28818, 1);
	player.getPackets().sendIComponentText(473, 141, "+165 Str Bonus");
	player.getPackets().sendIComponentText(473, 140, "<shad=FF0000>UNKNOWN PvP Points");
	
	/**
	 * Trivia Tab
	 */
	/*
	 * Button box 1
	 */
	player.getPackets().sendItemOnIComponent(473, 217, 24154, 1);
	player.getPackets().sendItemOnIComponent(473, 218, 24154, 5);
	player.getPackets().sendIComponentText(473, 220, "");
	player.getPackets().sendIComponentText(473, 219, "<col=B00000>50 Trivia Points");
	/*
	 * Button box 2
	 */
	player.getPackets().sendItemOnIComponent(473, 181, 13879, 1);
	player.getPackets().sendItemOnIComponent(473, 182, 26783, 20);
	player.getPackets().sendIComponentText(473, 185, "");
	player.getPackets().sendIComponentText(473, 184, "");
	player.getPackets().sendIComponentText(473, 183, "<col=B00000>7 PvP Points");
	/*
	 * Button box 3
	 */
	player.getPackets().sendItemOnIComponent(473, 204, 13887, 1);
	player.getPackets().sendIComponentText(473, 205, "Vesta's Armor Set");
	player.getPackets().sendIComponentText(473, 207, "");
	player.getPackets().sendIComponentText(473, 206, "35 PvP Points");
	/*
	 * Button box 4
	 */
	player.getPackets().sendItemOnIComponent(473, 210, 13884, 1);
	player.getPackets().sendIComponentText(473, 211, "Statius's Armor Set");
	player.getPackets().sendIComponentText(473, 213, "");
	player.getPackets().sendIComponentText(473, 214, "");
	player.getPackets().sendIComponentText(473, 212, "35 PvP Points");
	/*
	 * Button box 5
	 */
	player.getPackets().sendItemOnIComponent(473, 198, 13870, 1);
	player.getPackets().sendIComponentText(473, 199, "Morrigan's Armor Set");
	player.getPackets().sendIComponentText(473, 201, "");
	player.getPackets().sendIComponentText(473, 200, "20 PvP Points");
	/*
	 * Button box 6
	 */
	player.getPackets().sendItemOnIComponent(473, 192, 13858, 1);
	player.getPackets().sendIComponentText(473, 193, "Zuriel's Armor Set");
	player.getPackets().sendIComponentText(473, 195, "");
	player.getPackets().sendIComponentText(473, 194, "20 PvP Points");
	/**
	 * Bottem Part
	 */
	//player.getPackets().sendIComponentText(473, 194, "you have <col=B00000><shad=FF0000>" +player.dragonpoints + "</col> Dragon Points.");
	player.getPackets().sendIComponentText(473, 5, "                                            You have <col=B00000></col></shad> Pvm Points.");
	player.getPackets().sendIComponentText(473, 6, "                                             ");
	player.getPackets().sendIComponentText(473, 7, "");
	player.getPackets().sendIComponentText(473, 8, "");
	player.getPackets().sendIComponentText(473, 9, "");
	player.getPackets().sendIComponentText(473, 10, "");
	player.getPackets().sendIComponentText(473, 11, "");
	player.getPackets().sendIComponentText(473, 12, "");
				
				
				
		}
				
				
				/*end*/

	 public static void handleButtons(Player player, int componentId) {
		  if (componentId == 49) {	

		        }
		  if (componentId == 57) {	

		        }
		  if (componentId == 53) {	

		        }
	        if (componentId == 61) {	

	        }
	 }

}