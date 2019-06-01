package com.rs.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

public class ObjectSpawning {

	/**
	 * Contains the custom npc spawning
	 */

	public static void spawnNPCS() {
		//Rodiks Zone
		World.spawnObject(new WorldObject(782, 10, 0, 4700, 4704, 1), true);//Bank Booth
		World.spawnObject(new WorldObject(782, 10, 0, 4708, 4704, 1), true);//Bank Booth
		World.spawnObject(new WorldObject(409, 10, 2, 4703, 4716, 1), true);//Alter
	//Bank booths @ home
		World.spawnObject(new WorldObject(782, 10, 0, 3223, 3217, 0), true);//staircase

		//Ladder
		World.spawnObject(new WorldObject(45784, 10, 0, 3680, 4940, 0), true);//staircase
		
		World.spawnObject(new WorldObject(23585, 10, 0, 2826, 2998, 0), true);//staircase
		
		World.spawnObject(new WorldObject(4500, 10, 3, 3077, 4234, 0), true);//tunnel
		
		
		
		World.spawnObject(new WorldObject(1, 10, 0, 2502, 3496, 0), true);//le crate
		
		
		//World.spawnObject(new WorldObject(2473, 10, 0, 4625, 5454, 3), true);//Portal
		
		World.spawnObject(new WorldObject(2620, 10, 0, 2805, 3444, 0), true);//le crate
		//lever
		World.spawnObject(new WorldObject(3241, 10, 0, 2448, 9717, 0), true);//lever
//pass
	
		World.spawnObject(new WorldObject(1, 10, 0, 2495, 9713, 0), true);//Crate
		//boat in morytania
		World.spawnObject(new WorldObject(17955, 10, 0, 3523, 3169, 0), true);//boat
		World.spawnObject(new WorldObject(17955, 10, 3, 3593, 3178, 0), true);//boat
		World.spawnObject(new WorldObject(12798, 10, 3, 3494, 3211, 0), true);//Bank booth
		
		//Zogre training grounds
		
		World.spawnObject(new WorldObject(6881, 10, 0, 2456, 3047, 0), true);//barricade
		
		//ladder in morytania
		World.spawnObject(new WorldObject(12907, 10, 1, 3589, 3173, 0), true);//ladder
		//key statue
		World.spawnObject(new WorldObject(18046, 10, 2, 3641, 3304, 0), true);//statue with key
		
		
		//Donator zone
	
		World.spawnObject(new WorldObject(782, 10, 2, 2832, 3869, 0), true);//Bank Booth
		World.spawnObject(new WorldObject(782, 10, 2, 2833, 3869, 0), true);//Bank Booth
		
		
		
	
		/**End of Home**/
		/**Mining area**/
		World.spawnObject(new WorldObject(2213, 10, 1, 3298, 3307, 0), true);//bank booth
		/**End of Mining area**/
		
		/**End of Farming Area**/
		
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
					World.spawnNPC(npc.getId(), spawnLoc, -1, true, false,false);
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