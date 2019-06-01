package com.rs.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

public class NPCSpawning {

	/**
	 * Contains the custom npc spawning
	 */

	public static void spawnNPCS() {
		

		for(int i = 0; i < 10; i++){
		World.deleteObject(new WorldTile(2553, 2852 + i, 0));
		}	
		for(int i = 0; i < 10; i++){
		World.deleteObject(new WorldTile(2556, 2853 + i, 0));
		}
		World.deleteObject(new WorldTile(2554, 2855, 0));
		for(int i = 0; i < 8; i++){
		World.deleteObject(new WorldTile(2554, 2857 + i, 0));
		}
		for(int i = 0; i < 10; i++){
		World.deleteObject(new WorldTile(2558, 2856 + i, 0));
		}
		for(int i = 0; i < 10; i++){
		World.deleteObject(new WorldTile(2558, 2851 + i, 0));
		}
		for(int i = 0; i < 10; i++){
		World.deleteObject(new WorldTile(2559, 2850 + i, 0));
		}		
		for(int i = 0; i < 10; i++){
		World.deleteObject(new WorldTile(2549, 2855 + i, 0));
		}
		
		 //end
		World.deleteObject(new WorldTile(2536, 2851, 0));
		World.deleteObject(new WorldTile(2534, 2850, 0));	
		World.deleteObject(new WorldTile(2579, 2851, 0));
		World.deleteObject(new WorldTile(2553, 2833, 0));
		World.deleteObject(new WorldTile(2551, 2834, 0));
		World.deleteObject(new WorldTile(2550, 2834, 0));
		World.deleteObject(new WorldTile(2549, 2834, 0));		
		World.deleteObject(new WorldTile(2548, 2856, 0));
		World.deleteObject(new WorldTile(2551, 2856, 0));
		World.deleteObject(new WorldTile(2545, 2853, 0));
		World.deleteObject(new WorldTile(2545, 2851, 0));
		World.deleteObject(new WorldTile(2545, 2856, 0));
		World.deleteObject(new WorldTile(2546, 2857, 0));
		World.deleteObject(new WorldTile(2543, 2850, 0));
		World.deleteObject(new WorldTile(2544, 2849, 0));
		World.deleteObject(new WorldTile(2545, 2849, 0));
		World.deleteObject(new WorldTile(2545, 2848, 0));
		World.deleteObject(new WorldTile(2539, 2835, 0));
		World.deleteObject(new WorldTile(2538, 2835, 0));
		World.deleteObject(new WorldTile(2540, 2840, 0));
		World.deleteObject(new WorldTile(2538, 2840, 0));
		World.deleteObject(new WorldTile(2540, 2841, 0));
		World.deleteObject(new WorldTile(2537, 2842, 0));
		World.deleteObject(new WorldTile(2540, 2842, 0));
		World.deleteObject(new WorldTile(2536, 2841, 0));
		World.deleteObject(new WorldTile(2533, 2835, 0));
		World.deleteObject(new WorldTile(2534, 2836, 0));
		World.deleteObject(new WorldTile(2534, 2838, 0));
		World.deleteObject(new WorldTile(2530, 2834, 0));
		World.deleteObject(new WorldTile(2529, 2833, 0));
		World.deleteObject(new WorldTile(2567, 2842, 0));
		World.deleteObject(new WorldTile(2566, 2840, 0));
		World.deleteObject(new WorldTile(2564, 2841, 0));
		World.deleteObject(new WorldTile(2562, 2840, 0));
		World.deleteObject(new WorldTile(2561, 2839, 0));
		World.deleteObject(new WorldTile(2561, 2841, 0));
		World.deleteObject(new WorldTile(2602, 2844, 0));
		World.deleteObject(new WorldTile(2601, 2845, 0));
		World.deleteObject(new WorldTile(2602, 2845, 0));
		World.deleteObject(new WorldTile(2603, 2846, 0));
		World.deleteObject(new WorldTile(2565, 2855, 0));
		World.deleteObject(new WorldTile(2565, 2856, 0));
		World.deleteObject(new WorldTile(2562, 2853, 0));
		World.deleteObject(new WorldTile(2571, 2854, 0));
		World.deleteObject(new WorldTile(2571, 2852, 0));
		World.deleteObject(new WorldTile(2573, 2853, 0));
		World.deleteObject(new WorldTile(2570, 2858, 0));
		World.deleteObject(new WorldTile(2571, 2859, 0));
		World.deleteObject(new WorldTile(2572, 2859, 0));
		World.deleteObject(new WorldTile(2573, 2859, 0));
		World.deleteObject(new WorldTile(2573, 2855, 0));
		World.deleteObject(new WorldTile(2573, 2856, 0));
		World.deleteObject(new WorldTile(2552, 2852, 0));	
		World.deleteObject(new WorldTile(2552, 2855, 0));
		World.deleteObject(new WorldTile(2594, 2844, 0));
		World.deleteObject(new WorldTile(2592, 2844, 0));
		World.deleteObject(new WorldTile(2599, 2847, 0));
		World.deleteObject(new WorldTile(2600, 2847, 0));
		World.deleteObject(new WorldTile(2599, 2840, 0));
		World.deleteObject(new WorldTile(2599, 2844, 0));
		World.deleteObject(new WorldTile(2599, 2847, 0));
		World.deleteObject(new WorldTile(2599, 2845, 0));
		World.deleteObject(new WorldTile(2573, 2840, 0));
		World.deleteObject(new WorldTile(2576, 2840, 0));
		World.deleteObject(new WorldTile(2579, 2838, 0));
		World.deleteObject(new WorldTile(2582, 2838, 0));
		//lamps
		World.deleteObject(new WorldTile(2568, 2854, 0));
	    World.deleteObject(new WorldTile(2568, 2857, 0));
		//elite trio plant spawn 
		for(int i = 0; i <= 19; i ++)
		World.spawnObject(new WorldObject(67315, 10,0,2848,3532+i, 3), true); 
		// More Deletes To Come! Once We Get More Players Ill Make The Home Bigger :D
		/*Barbarian Fishing*/ //fix this.
		World.spawnNPC(8647, new WorldTile(2500, 3509, 0), -1, true);
		World.spawnNPC(8647, new WorldTile(2502, 3515, 0), -1, true);
		
		World.spawnNPC(3019, new WorldTile(2500, 3507, 0), -1, true);
		World.spawnNPC(3019, new WorldTile(2504, 3497, 0), -1, true);
		
		World.spawnNPC(317, new WorldTile(2506, 3493, 0), -1, true);
		World.spawnNPC(317, new WorldTile(2500, 3509, 0), -1, true);
		//boss test
		World.spawnObject(new WorldObject(94515, 10,3,3259, 2730, 0), true); 
		World.spawnObject(new WorldObject(94515, 10,1,3240, 2726, 0), true); 
		//Darkmeyer Boss Vanstrom Klause
		World.spawnNPC(14176, new WorldTile(3620, 3342, 0), -1, true);
		//home shops
		//home portal
		World.spawnObject(new WorldObject(42611, 10,0,2341, 3700, 0), true); 
		//slayer master
		World.spawnNPC(9085, new WorldTile(3671, 2976, 0), -1, true);
		//decanter
		World.spawnNPC(6524, new WorldTile(2330, 3686, 0), -1, true);
		//wildy handler
		World.spawnNPC(70267, new WorldTile(2322,3683, 0), -1, true);
		//Fish shop
		World.spawnNPC(558, new WorldTile(3675, 2970, 0), -1, true);
		//skiller npc
		World.spawnNPC(12, new WorldTile(2351, 3683, 0), -1, true);
		//estate agent
		World.spawnNPC(4247, new WorldTile(3690, 2980, 0), -1, true);
		//herb shop
		World.spawnNPC(587, new WorldTile(3671, 2970, 0), -1, true);
		//runes shop
		World.spawnNPC(12192, new WorldTile(3677,2968, 0), -1, true);
		//runes shop
		World.spawnNPC(13172, new WorldTile(3675,2966, 0), -1, true);
		//range shop
		World.spawnNPC(15151, new WorldTile(3676,2970, 0), -1, true);
		//broll shop
		World.spawnNPC(563, new WorldTile(3674,2970, 0), -1, true);
		//consum shop
		World.spawnNPC(11254, new WorldTile(3672,2970, 0), -1, true);
		//armour shop
		World.spawnNPC(12306, new WorldTile(3673,2971, 0), -1, true);
		//weapon shop
		World.spawnNPC(13464, new WorldTile(3673,2971, 0), -1, true);
		//skilling store
		World.spawnNPC(519, new WorldTile(3677, 2967, 0), -1, true); 
		//prestige guy
	//	World.spawnNPC(19455, new WorldTile(2354, 3680, 0), -1, true); 
		//custom shop
		World.spawnNPC(13295, new WorldTile(2340, 3687, 0), -1, true); 
		//warbands
		World.spawnNPC(17065, new WorldTile(3134,3519, 0), -1, true); 
		//skiller rack
		//World.spawnObject(new WorldObject(18769, 10, 1,2351 ,3682 , 0), true);
		//red sandstone rock
		World.spawnObject(new WorldObject(2330, 10, 0,2339, 3660, 0), true);
		//hidden trapdoor
		World.spawnObject(new WorldObject(75936, 10, 2,2316, 3666 , 0), true);
		//smithing area
		World.spawnObject(new WorldObject(87353, 10, 0,2316, 3682 , 0), true);
		World.spawnObject(new WorldObject(45311, 10, 0,2316, 3684 , 0), true);
		World.spawnObject(new WorldObject(81727, 10, 0,2317, 3686 , 0), true);
		World.spawnObject(new WorldObject(81727, 10, 0,2320, 3686 , 0), true);
		World.spawnObject(new WorldObject(15468, 10, 0,2321, 3689 , 0), true);
		World.spawnObject(new WorldObject(76289, 10, 0,2316, 3689, 0), true);
		World.spawnObject(new WorldObject(76289, 10, 1,2321, 3684, 0), true);
		//removing
		World.spawnObject(new WorldObject(123, 10, 0,2320, 3684 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2320, 3682 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2320, 3680 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2320, 3688 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2316, 3686 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2316, 3680 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2328, 3670 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2330, 3670 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2328, 3667 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2320, 3682 , 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2320, 3684, 0), true);
		World.spawnObject(new WorldObject(123, 10, 0,2327, 3670, 0), true);
		//world gorg
		World.spawnObject(new WorldObject(98891, 10, 1,3239, 9362 , 0), true);
		//zaria quest
		World.spawnObject(new WorldObject(91986, 10, 0,3803, 3531 , 0), true);
		World.spawnObject(new WorldObject(82987, 10, 0,3807, 3569 , 0), true);
		World.spawnObject(new WorldObject(83634, 10, 0,3497, 3569 , 0), true);
		World.spawnNPC(14392, new WorldTile(3799,3533, 0), -1, true);
		World.spawnNPC(14392, new WorldTile(3501,3568, 0), -1, true);
		World.spawnNPC(14256, new WorldTile(2984,9632, 0), -1, true);
		//suak artisan workshop
		World.spawnNPC(6654, new WorldTile(3060,3339, 0), -1, true);
		/**
		 *prestige guild
		 */
		//prestige barrier
		World.spawnObject(new WorldObject(52761, 10, 0,3658, 2974 , 0), true);
		World.spawnObject(new WorldObject(74531, 10, 0,3653 , 2972, 0), true);
		//plant
		World.spawnObject(new WorldObject(74533, 10, 0,3653 , 2974, 0), true);
		//pvm master 
		World.spawnNPC(14850, new WorldTile(3676, 2991, 0), -1, true);
		//papa
		World.spawnNPC(3122, new WorldTile(2951,2936, 0), -1, true);
		//max
		World.spawnNPC(3373, new WorldTile(2352,3702, 0), -1, true);
		//smithing area
		World.spawnNPC(14643, new WorldTile(2321, 3683, 0), -1, true);
		//pirate Quest npc
		World.spawnNPC(7856, new WorldTile(2356,3698, 0), -1, true);
		//hefin creature
		World.spawnNPC(20274, new WorldTile(2187,3414,2), -1, true);
		//Devin
		World.spawnNPC(3825, new WorldTile(3047,9839, 0), -1, true);
		//World.spawnObject(new WorldObject(66133, 10, 0,2965,9812, 0), true);
		World.spawnObject(new WorldObject(80326, 10, 0,2967,9812, 0), true);
		//tent
		World.spawnObject(new WorldObject(35039, 10,0,2306,3657, 0), true); 
		//Campfire
		World.spawnObject(new WorldObject(28486, 10,0,2307,3661, 0), true); 
		//master capes shop
		World.spawnNPC(564, new WorldTile(3693, 2974, 0), -1, true);
		//bonefire
		World.spawnObject(new WorldObject(70765, 10,0,3686,2966, 0), true); 
		//depebox
		World.spawnObject(new WorldObject(26969, 10,2,3672,2965, 0), true); 
		//pest minigame
		World.spawnNPC(3801, new WorldTile(3666, 2976, 0), -1, true);
		//oldschool
		//World.spawnNPC(549, new WorldTile(2354, 3698, 0), -1, true);
		//slave npc
		World.spawnNPC(4988, new WorldTile(3667, 2993, 0), -1, true);
		//vote store
		World.spawnNPC(14945, new WorldTile(3671, 2966, 0), -1, true);
		//spirit tree
		World.spawnObject(new WorldObject(68973, 10, 0,2335 , 3650 , 0), true);
		//donor rock
		World.spawnObject(new WorldObject(99952, 10, 0,1826,5836, 0), true);
		//specatete orb
		World.spawnObject(new WorldObject(83632, 10, 0,2348 , 3694, 0), true);
		//small obelisk
		World.spawnObject(new WorldObject(94313, 10, 0,2339 , 3695 , 0), true);
		//pet house
		World.spawnObject(new WorldObject(44829, 10, 1,2354, 3692 , 0), true);
		//big obelisk
		World.spawnObject(new WorldObject(94230, 10, 0,3652, 2997 , 0), true);
		//summ obelisk
		World.spawnObject(new WorldObject(28716, 10, 0,2315 , 3695, 0), true);
		//flask machine
		World.spawnObject(new WorldObject(67968, 10, 0,2347 , 3661, 0), true);
		//bank chest at skilling area
		World.spawnObject(new WorldObject(92225, 10, 0,3657 , 2994, 0), true);
		//anvil
		World.spawnObject(new WorldObject(88490, 10, 0,3651 , 2991, 0), true);
		//holiday portal
		World.spawnObject(new WorldObject(92695, 10, 2,2312, 3675, 0), true);
		//prayer altar   //92627
		World.spawnObject(new WorldObject(409, 10, 2,2329 , 3685, 0), true);
		//crystal chest   //92627
		World.spawnObject(new WorldObject(92627, 10, 2,2331, 3686, 0), true);
		//max cape
		World.spawnObject(new WorldObject(2562, 10, 1, 2351 , 3681, 0), true);
		//actiivity board
		World.spawnObject(new WorldObject(43767, 10, 2, 2329 , 3694, 0), true);
		//record board
		 World.spawnObject(new WorldObject(9140, 10,3,2333, 3688, 0), true); 
		//effigy table
		World.spawnObject(new WorldObject(89713, 10,0,2354, 3678, 0), true); 
		 //lotalty tent
		 World.spawnObject(new WorldObject(11448, 10,0,2319, 3699, 0), true); 
		//poh portal
		 World.spawnObject(new WorldObject(15482, 10,2,2333, 3663, 0), true); 
		 //zaia house layout
		 World.spawnObject(new WorldObject(95832, 10,0,2329, 3671, 0), true); 
		 World.spawnObject(new WorldObject(72400, 10,0,2325, 3672, 0), true); 
		 World.spawnObject(new WorldObject(72400, 10,0,2327, 3672, 0), true); 
		 World.spawnObject(new WorldObject(71924, 10,2,2329, 3666, 0), true); 
		 World.spawnObject(new WorldObject(96196, 10,1,2333, 3666, 0), true); 
		  World.spawnObject(new WorldObject(102063, 10,0,2325, 3666, 0), true); 
		   World.spawnObject(new WorldObject(123, 10,0,2330, 3670, 0), true); 
		   World.spawnObject(new WorldObject(123, 10,0,2328, 3670, 0), true); 
		   World.spawnObject(new WorldObject(123, 10,0,2328, 3666, 0), true); 
		   World.spawnObject(new WorldObject(123, 10,0,2330, 3666, 0), true); 
		 //   World.spawnObject(new WorldObject(96200, 10,0,2332,3701, 0), true); 
		   
		 //exit portal
		 //poh portal
		// World.spawnObject(new WorldObject(97184, 10,1,2351, 3678, 0), true); 
		 //xuan
		// World.spawnNPC(13727, new WorldTile(3690,2974, 0), -1, true);//general
		 //defence ranger
		 //bank booths xx
		 World.spawnObject(new WorldObject(76274, 10, 1,2331 , 3691 , 0), true);
		 World.spawnObject(new WorldObject(76274, 10, 1,2330 , 3691 , 0), true);
		 World.spawnObject(new WorldObject(76274, 10, 1,2329 , 3691 , 0), true);
		 World.spawnObject(new WorldObject(76274, 10, 1,2328 , 3691 , 0), true);
		 World.spawnObject(new WorldObject(76274, 10, 1,2327 , 3691 , 0), true);
		 
		 //thieving stall
		  World.spawnObject(new WorldObject(7053, 10, 2,2329 , 3673 , 0), true);
		  World.spawnObject(new WorldObject(34385, 10, 0,2332 ,3673, 0), true);
		 //farmer
		  World.spawnNPC(758, new WorldTile(2328, 3673, 0), -1, true);
		 //rodeck
		  World.spawnNPC(7909, new WorldTile(2328, 3686, 0), -1, true);
		 //sdoor at prehistorical npc
		World.spawnObject(new WorldObject(55764, 10, 1,215 , 5519 , 0), true);
		World.spawnObject(new WorldObject(42611, 10, 0,1816 , 5850 , 0), true);
		 //player manager
		World.spawnNPC(13457, new WorldTile(3661, 3006, 0), -1, true);
		World.spawnNPC(13457, new WorldTile(3648, 3005, 0), -1, true);
		World.spawnNPC(13457, new WorldTile(3695, 3006, 0), -1, true);
		World.spawnNPC(13457, new WorldTile(3708, 3005, 0), -1, true);
		World.spawnNPC(15033, new WorldTile(3675, 3004, 0), 1, true); //wounded soldier
		World.spawnNPC(13932, new WorldTile(3677, 3004, 0), -1, true);//general
		
		//hiddentrapdoor cave 
		World.spawnNPC(515, new WorldTile(1394,4586, 0), -1, true);//general
		World.spawnNPC(19109, new WorldTile(1382,4589, 0), -1, true);//dragon
		World.spawnNPC(19109, new WorldTile(1373,4584, 0), -1, true);//dragon
		World.spawnNPC(19109, new WorldTile(1369,4572, 0), -1, true);//dragon
		World.spawnNPC(19109, new WorldTile(1376,4577, 0), -1, true);//dragon$
		//zaria
	    World.spawnNPC(14392, new WorldTile(2332,3668, 0), -1, true);
		//summ
		World.spawnNPC(6988, new WorldTile(2315,3693, 0), -1, true);
		//soldiers
		World.spawnNPC(13457, new WorldTile(2342,3654, 0), -1, true);
		
		World.spawnNPC(13457, new WorldTile(2345,3654, 0), -1, true);
		//home skilling
		World.spawnNPC(7045, new WorldTile(2351,3703, 0), -1, true);
		//wildy
		World.spawnNPC(14907, new WorldTile(3333,3930, 0), -1, true);
		World.spawnNPC(14907, new WorldTile(3335,3927, 0), -1, true);
		//starter intro
		World.spawnNPC(6334, new WorldTile(2292,3626, 0), -1, true);//boy
		World.spawnNPC(5985, new WorldTile(2909,3942, 0), -1, true);//sister
		World.spawnNPC(18497, new WorldTile(2913,3944, 1), -1, true);//ranger
		World.spawnNPC(18497, new WorldTile(2908,3942, 1), -1, true);//ranger
		World.spawnNPC(18497, new WorldTile(2919,3942, 1), -1, true);//ranger
		World.spawnObject(new WorldObject(41858, 10, 1,2289 , 3618 , 0), true);
		World.spawnObject(new WorldObject(41858, 10, 1,2295 , 3614 , 0), true);
		World.spawnObject(new WorldObject(41858, 10, 1,2297 , 3616 , 0), true);
		World.spawnObject(new WorldObject(41859, 10, 1,2286 , 3627 , 0), true);
		World.spawnObject(new WorldObject(41859, 10, 1,2288 , 3621 , 0), true);
		World.spawnObject(new WorldObject(41860, 10, 1,2287 , 3624 , 0), true);
		World.spawnObject(new WorldObject(41859, 10, 1,2287 , 3630 , 0), true);
		World.spawnObject(new WorldObject(41859, 10, 1,2288 , 3631 , 0), true);
		
		/*object at home**/
		World.deleteObject(new WorldTile(2327, 3803, 0));
		World.deleteObject(new WorldTile(2327, 3806, 0));
		World.deleteObject(new WorldTile(2329, 3804, 0));
		World.deleteObject(new WorldTile(2329, 3805, 0));
		World.deleteObject(new WorldTile(2323, 3810, 0));
		World.deleteObject(new WorldTile(2334, 3799, 0));
		World.deleteObject(new WorldTile(2342, 3807, 0));
		World.deleteObject(new WorldTile(2344, 3809, 0));
		World.deleteObject(new WorldTile(2318, 3805, 0));
		//elder tree
		 World.spawnObject(new WorldObject(87508, 10,0,3118,3789, 0), true); 
		//crystal tree
		World.spawnObject(new WorldObject(87533, 10,0,2950,3912, 0), true); 
		World.spawnObject(new WorldObject(123, 10,0,2948,3912, 0), true); 
		World.deleteObject(new WorldTile(86, 367, 0));
		//Donator Zone 
		World.spawnNPC(5445, new WorldTile(1811,5846, 0), -1, true);//ranger
		World.spawnNPC(9234, new WorldTile(1808,5846,0), -1, true);//ranger
		World.spawnNPC(19301, new WorldTile(1814, 5849, 0), -1, true);
		World.spawnObject(new WorldObject(2938, 10, 3,1807, 5844, 0), true);
		World.spawnObject(new WorldObject(4878, 10, 1,1829, 5842, 0), true);
		World.spawnObject(new WorldObject(76531, 10, 1, 1821, 5848, 0), true);
		
		//bonestaak donor island home
		World.spawnObject(new WorldObject(24733, 10, 1, 2354, 3689, 0), true); 
		World.spawnObject(new WorldObject(99952, 10, 0, 2148, 5537, 3), true); 
		World.spawnObject(new WorldObject(88490, 10, 1, 2142, 5544, 3), true); 
		/*
		 *Item Spawning below
		 *
		 */
		
		//World.addGroundItem(new Item(995, 500), new WorldTile(2965, 3380, 0), null, true, 180,true);
	}

	/**
	 * The NPC classes.
	 */
	private static final Map<Integer, Class<?>> CUSTOM_NPCS = new HashMap<Integer, Class<?>>();

	public static void npcSpawn() {
		int size = 0;
		boolean ignore = false;
		try {
			for (String string : FileUtilities
					.readFile("data/npcs/spawns.txt")) {
				if (string.startsWith("//") || string.equals("")) {
					continue;
				}
				if (string.contains("/*")) {
					ignore = true;
					continue;
				}
				if (ignore) {
					if (string.contains("*/")) {
						ignore = false;
					}
					continue;
				}
				String[] spawn = string.split(" ");
				@SuppressWarnings("unused")
				int id = Integer.parseInt(spawn[0]), x = Integer
						.parseInt(spawn[1]), y = Integer.parseInt(spawn[2]), z = Integer
						.parseInt(spawn[3]), faceDir = Integer
						.parseInt(spawn[4]);
				NPC npc = null;
				Class<?> npcHandler = CUSTOM_NPCS.get(id);
				if (npcHandler == null) {
					npc = new NPC(id, new WorldTile(x, y, z), -1, true, false);
				} else {
					npc = (NPC) npcHandler.getConstructor(int.class)
							.newInstance(id);
				}
				if (npc != null) {
					WorldTile spawnLoc = new WorldTile(x, y, z);
					npc.setLocation(spawnLoc);
					World.spawnNPC(npc.getId(), spawnLoc, -1, true, false, false);
					size++;
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		System.err.println("Loaded " + size + " custom npc spawns!");
	}

}