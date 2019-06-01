package com.rs.game.npc.combat.impl;

import java.util.Random;
import java.util.TimerTask;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.godwars.gielinor.Vindicta;
import com.rs.utils.Utils;

public class VindictaCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] {22320,22321,22322,22323};
	}
	
	private void hurricaneAttack(Vindicta v, Random r) { 
		//spin attack
		v.setNextAnimation(new Animation(28256));
		v.setNextGraphics(new Graphics(6111));
		for (Entity t : v.getPossibleTargets()) {
			if (t.withinDistance(v.getTile(), 2)) {
				int damage = r.nextInt(120);
				delayHit(v, 2, t, getRegularHit(v, damage));
				damage = r.nextInt(120);
				delayHit(v, 2, t, getRegularHit(v, damage));
			}
		}
		v.setP1attackCounter(v.getP1attackCounter() + 1);
	}
	
	
	private void autoAttack(Vindicta v, Entity target, Random r) {
		int[] anims = { v.getCombatDefinitions().getAttackEmote() }; //28258
		v.setNextAnimation(new Animation(anims[r.nextInt(anims.length)]));
		int damage = getRandomMaxHit(v, v.getCombatDefinitions().getMaxHit(), NPCCombatDefinitions.MELEE, target);
		delayHit(v, 2, target, getMeleeHit(v, damage));
		v.setP1attackCounter(v.getP1attackCounter() + 1);
	}
	
	private void meleeAndRangeAttack(Vindicta v, Entity target, Random r) {
		int[] anims = { 28259 }; //28258
		v.setNextAnimation(new Animation(anims[r.nextInt(anims.length)]));
		for (Entity t : v.getPossibleTargets()) {
			if (!t.withinDistance(v.getTile(), 2)) {
				System.out.println("entity shot");
				delayHit(v,1,t,getRangeHit(v,getRandomMaxHit(v, 380,NPCCombatDefinitions.RANGE, t)));
				World.sendProjectile(v, t, 6115, 20, 0, 10, 10, 0, 0);
			}
		}
		int damage = getRandomMaxHit(v, v.getCombatDefinitions().getMaxHit(), NPCCombatDefinitions.MELEE, target);
		delayHit(v, 2, target, getMeleeHit(v, damage));
		v.setP1attackCounter(v.getP1attackCounter() + 1);
	}
	
	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		Random r = new Random();
		Vindicta v = (Vindicta) npc;
		switch (v.getPhase()) {
			case Vindicta.PHASE_ONE:
				if (v.isFightStart()) {
					System.out.println("is: " + v.getP1attackCounter());
					//if start of fight, 2 auto attacks and a hurricane.
					if (v.getP1attackCounter() < 2 ) {
						//2 auto attacks before spin
						autoAttack(v, target, r);
					} else if (v.getP1attackCounter() == 2) {
						hurricaneAttack(v, r);
						v.setFightStart(false);
						v.setP1attackCounter(0);
					}
				} else {
					System.out.println("isnt: " + v.getP1attackCounter());

					//if not start of fight, 1 auto, 1 auto + range if not melee distance, 1 auto.
					if (v.getP1attackCounter() <= 1 || v.getP1attackCounter() == 3 || (v.getP1attackCounter() >= 5 && v.getP1attackCounter() <=7))
						//2 auto attacks before spin
						autoAttack(v, target, r);
					else if (v.getP1attackCounter() == 2)
						//slam target, hit anyone else out of range
						meleeAndRangeAttack(v, target, r);
					else if (v.getP1attackCounter() == 4) {
						// dragon fire
						
						//28260
						//NORTH_SOUTH_LINE
						// ymax 6894, down by 2 to 6866
						
						//28260
						//EAST_WEST_LINE
						// xmin 3088, up by 2 to 3110
						System.out.println("vindy direction: " + v.getDirection());
						v.setNextAnimation(new Animation(28260));
						int luck = r.nextInt(100);
						if (luck < 25)
							spawnNSwalls(v, target);
						else if (luck < 50)
							spawnEWwalls(v, target);
						else if (luck < 100)
							attemptDiagonalWalls(v, target);

						v.setP1attackCounter(v.getP1attackCounter() + 1);
					} else if (v.getP1attackCounter() == 8) {
						//spin attack
						hurricaneAttack(v, r);
						v.setP1attackCounter(0);
					}
					break;
			}
		}

		
		return defs.getAttackDelay();
	}
	

	private void attemptDiagonalWalls(Vindicta v, Entity target) {
		int[] line_start_coords = checkSWtoNEDiagonals(target);
		if (line_start_coords != null) {
			spawnSWtoNEDiagonal(line_start_coords[1], target.getPlane(), target, v);
			v.setLastUsedWall(Vindicta.SW_TO_NE);
			sendDamageEvent(v, target, line_start_coords);
			return;
		}
		line_start_coords = checkNWtoSEDiagonals(target);
		if (line_start_coords != null) {
			spawnNWtoSEDiagonal(line_start_coords[1], target.getPlane(), target, v);
			v.setLastUsedWall(Vindicta.NW_TO_SE);
			sendDamageEvent(v, target, line_start_coords);
		}
		System.out.println("Unable to spawn diag.");
		Random r = new Random();
		if (r.nextBoolean()) 
			spawnNSwalls(v, target);
		else 
			spawnEWwalls(v, target);
	}

	private void sendDamageEvent(Vindicta v, Entity target, int[] start_point) {
		System.out.println("Last FW: "  + v.getLastUsedWall());
		System.out.println("targets: " + v.getPossibleTargets().toString());
		for (Entity t : v.getPossibleTargets()) {
			if (v.getLastUsedWall() == Vindicta.NORTH_SOUTH) {
			    final int targetInitialX = target.getX();
			    CoresManager.fastExecutor.schedule(new TimerTask() {
					int loop = 0;
					@Override
					public void run() {
						
						if (t.getX() == targetInitialX) {
								int hit;
								//String message = FireBreathAttack.getProtectMessage(target);
								//if (message == null) {
								hit = Utils.random(75, 100);
								t.applyHit(new Hit(v, hit, HitLook.REGULAR_DAMAGE));
							}
						if (loop >= 85)
							cancel();
						loop++;
						}
					} , 0, 333);
			    } else if (v.getLastUsedWall() == Vindicta.EAST_WEST) {
				    final int targetInitialY = target.getY();
				    CoresManager.fastExecutor.schedule(new TimerTask() {
				    	int loop = 0;
						@Override
						public void run() {
							if (t.getY() == targetInitialY) {
									int hit;
									//String message = FireBreathAttack.getProtectMessage(target);
									hit = Utils.random(75, 100);
									t.applyHit(new Hit(v, hit, HitLook.REGULAR_DAMAGE));
								}
							if (loop >= 85)
								cancel();
							loop++;
							}
						} , 0, 333);
			    } else if (v.getLastUsedWall() == Vindicta.NW_TO_SE) {
				    CoresManager.fastExecutor.schedule(new TimerTask() {
				    	int loop = 0;
				    	int y = start_point[1];
				    	boolean skipIncreaseY = false;
						@Override
						public void run() {
							for (int x = Vindicta.ARENA_X_START; x <= Vindicta.ARENA_X_END; x++) {
								if (t.getX() == x && t.getY() == y) {
									int hit;
									//String message = FireBreathAttack.getProtectMessage(target);
									hit = Utils.random(75, 100);
									t.applyHit(new Hit(v, hit, HitLook.REGULAR_DAMAGE));
								}

								if (!skipIncreaseY)
									y++;
								skipIncreaseY = !skipIncreaseY;
							}
							y = start_point[1];
							if (loop >= 85)
								cancel();
							loop++;
							}
						} , 0, 333);
			    } else if (v.getLastUsedWall() == Vindicta.SW_TO_NE) {
				    CoresManager.fastExecutor.schedule(new TimerTask() {
				    	int loop = 0;
				    	boolean skipIncreaseY = false;
				    	int y = start_point[1];
						@Override
						public void run() {
							for (int x = Vindicta.ARENA_X_START; x <= Vindicta.ARENA_X_END; x++) {
								if (t.getX() == x && t.getY() == y) {
									int hit;
									//String message = FireBreathAttack.getProtectMessage(target);
									hit = Utils.random(75, 100);
									t.applyHit(new Hit(v, hit, HitLook.REGULAR_DAMAGE));
								}
								if (!skipIncreaseY)
									y++;
								skipIncreaseY = !skipIncreaseY;
							}
							y = start_point[1];
							if (loop >= 85)
								cancel();
							loop++;
							}
						} , 0, 333);
			    }
			}
	}

	private void spawnEWwalls(Vindicta v, Entity target) {
		for (int x = 3088; x < 3112; x+=2)
			World.sendGraphicsWider(null, new Graphics(6112,0,0), new WorldTile(x, target.getY(), target.getPlane()));
		spawnGorvek(v, target, -1);
		v.setLastUsedWall(Vindicta.EAST_WEST);
		sendDamageEvent(v, target, null);
	}
	
	private void spawnNSwalls(Vindicta v, Entity target) {
		for (int y = 6864; y < 6898; y+=2)
			World.sendGraphicsWider(null, new Graphics(6112,0,0), new WorldTile(target.getX(), y, target.getPlane()));
		spawnGorvek(v, target, -1);
		v.setLastUsedWall(Vindicta.NORTH_SOUTH);
		sendDamageEvent(v, target, null);
	}
	
	private int[] checkSWtoNEDiagonals(Entity t) {
			for (int starting_y = Vindicta.ARENA_Y_START; starting_y <= Vindicta.ARENA_Y_START + 16; starting_y+=2) {
				boolean skipIncreaseY = false;
				int line_y = starting_y;
				for (int x = Vindicta.ARENA_X_START; x <= Vindicta.ARENA_X_END; x++) {
					if (t.getX() == x && t.getY() == line_y) 
						return new int[] { x, starting_y };
					if (!skipIncreaseY) {
						line_y++;
					}
					skipIncreaseY = !skipIncreaseY;
				}
			}
			return null;
	}
	
	private void spawnSWtoNEDiagonal(int start_y, int z, Entity target, Vindicta v) {
		boolean skipIncreaseY = false;
		spawnGorvek(v, target, start_y);
		for (int x = Vindicta.ARENA_X_START; x <= Vindicta.ARENA_X_END; x++) {
			World.sendGraphicsWider(null, new Graphics(6112), new WorldTile(x, start_y, z));
			if (!skipIncreaseY)
				start_y++;
			skipIncreaseY = !skipIncreaseY;
		}
	}	
	
	private int[] checkNWtoSEDiagonals(Entity t) {
		for (int starting_y = Vindicta.ARENA_Y_START + 10; starting_y <= Vindicta.ARENA_Y_END; starting_y+=2) {
			boolean skipDecreaseY = false;
			int line_y = starting_y;
			for (int x = Vindicta.ARENA_X_START; x <= Vindicta.ARENA_X_END; x++) {
				if (t.getX() == x && t.getY() == line_y) 
					return new int[] { x, starting_y };
				if (!skipDecreaseY) {
					line_y--;
				}
				skipDecreaseY = !skipDecreaseY;
			}
		}
		return null;
	}
	
	private void spawnNWtoSEDiagonal(int start_y, int z, Entity target, Vindicta v) {
		boolean skipDecreaseY = false;
		spawnGorvek(v, target, start_y);
		for (int x = Vindicta.ARENA_X_START; x <= Vindicta.ARENA_X_END; x++) {
			World.sendGraphicsWider(null, new Graphics(6112), new WorldTile(x, start_y, z));
			if (!skipDecreaseY)
				start_y--;
			skipDecreaseY = !skipDecreaseY;
		}
	}
	
	private void spawnGorvek(Vindicta v, Entity target, int start_y) {
	CoresManager.fastExecutor.schedule(new TimerTask() {
		int loop = 0;
		@Override
		public void run() {
			NPC gorvek = new NPC(22321, new WorldTile(target.getX(), 6894, target.getPlane()), 0, false);
			switch (v.getLastUsedWall()) {
			case Vindicta.EAST_WEST:
				if (target.getY() >= 6879) {
					gorvek.setDirection(0);
					gorvek.getWorldTile().setWorldTile(target.getX(), 6864, target.getPlane());;
				} else {
					gorvek.setDirection(8192);
					gorvek.getWorldTile().setWorldTile(target.getX(), 6864, target.getPlane());;
				}
				break;
			case Vindicta.NORTH_SOUTH:
				if (target.getY() >= 6879) {
					gorvek.setDirection(0);
					gorvek.getWorldTile().setWorldTile(target.getX(), 6864, target.getPlane());;
				} else {
					gorvek.setDirection(8192);
					gorvek.getWorldTile().setWorldTile(target.getX(), 6864, target.getPlane());;
				}
				break;
			case Vindicta.NW_TO_SE:
				gorvek.setDirection(10240);
				gorvek.getWorldTile().setWorldTile(Vindicta.ARENA_X_START, start_y, target.getPlane());;
				break;
			case Vindicta.SW_TO_NE:
				gorvek.setDirection(6114);
				break;
			}

			gorvek.spawn();
			if (loop == 0)	
				gorvek.setNextAnimation(new Animation(28264));
			else
				cancel();
			loop++;
			}
		} , 0, 1);
	}
	
}
