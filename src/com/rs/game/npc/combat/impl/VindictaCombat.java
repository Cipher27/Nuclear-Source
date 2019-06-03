package com.rs.game.npc.combat.impl;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
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
				delayHit(v, 1, t, getRegularHit(v, damage));
				damage = r.nextInt(120);
				delayHit(v, 2, t, getRegularHit(v, damage));
			}
		}
		v.setAttackCounter(v.getAttackCounter() + 1);
	}
	
	private void autoRange(Vindicta v, Random r) {
		int[] anims = { 28274 }; //28258
		v.setNextAnimation(new Animation(anims[r.nextInt(anims.length)]));
		for (Entity t : v.getPossibleTargets()) {
			if (t instanceof Player) {
				Player p = (Player) t;
				if (p.getPrayer().isRangeProtecting()) {
					delayHit(v,1,t,getRangeHit(v, (int) (p.getRangePrayerMultiplier() * Utils.next(189, 395))));
					p.setNextGraphics(new Graphics(6113));
				} else {
					delayHit(v,1,t,getRangeHit(v, Utils.next(189, 395)));
					p.setNextGraphics(new Graphics(6113));
				}
			} else {
				delayHit(v,1,t,getRangeHit(v, Utils.next(189, 395)));
				t.setNextGraphics(new Graphics(6113));
			}
		}
		v.setAttackCounter(v.getAttackCounter() + 1);
	}
	
	private void autoAttack(Vindicta v, Entity target, Random r) {
		int[] anims = { v.getCombatDefinitions().getAttackEmote() }; //28258
		v.setNextAnimation(new Animation(anims[r.nextInt(anims.length)]));
		Player p;
		if (target instanceof Player) {
			p = (Player) target;
				if (p.getPrayer().isMeleeProtecting()) {
					int damage = Utils.next(0, 300);
					damage *= p.getMeleePrayerMultiplier();
					delayHit(v, 1, target, getMeleeHit(v, damage));
				} else {
					int damage = getRandomMaxHit(v, 300, NPCCombatDefinitions.MELEE, target);
					delayHit(v, 1, target, getMeleeHit(v, damage));
				}
		} else {
			int damage = getRandomMaxHit(v, v.getCombatDefinitions().getMaxHit(), NPCCombatDefinitions.MELEE, target);
			delayHit(v, 1, target, getMeleeHit(v, damage));
		}
		v.setAttackCounter(v.getAttackCounter() + 1);
	}
	
	private void meleeAndRangeAttack(Vindicta v, Entity target, Random r) {
		int[] anims = { 28259 }; //28258
		v.setNextAnimation(new Animation(anims[r.nextInt(anims.length)]));
		for (Entity t : v.getPossibleTargets()) {
			if (!t.withinDistance(v.getTile(), 2)) {
				System.out.println("entity shot");
				delayHit(v,1,t,getRangeHit(v, Utils.next(189, 395)));
				t.setNextGraphics(new Graphics(6113));
			} else if (t.withinDistance(v.getTile(), 2) && !((Player)t).getUsername().equalsIgnoreCase(((Player)target).getUsername())) {
				((Player)t).setNextGraphics(new Graphics(6109));
				int damage = getRandomMaxHit(v, v.getCombatDefinitions().getMaxHit(), NPCCombatDefinitions.MELEE, t);
				delayHit(v, 1, t, getMeleeHit(v, damage));
				
			}
		}
		int damage = getRandomMaxHit(v, v.getCombatDefinitions().getMaxHit(), NPCCombatDefinitions.MELEE, target);
		delayHit(v, 1, target, getMeleeHit(v, damage));
		v.setAttackCounter(v.getAttackCounter() + 1);
	}
	
	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		Random r = new Random();
		Vindicta v = (Vindicta) npc;
		//System.out.println("Distance from player: ");
		if (v.getPhase() == Vindicta.PHASE_ONE && v.getHitpoints() < 10000) {
			v.setPhase(Vindicta.PHASE_TWO);
			v.isTransforming(true);
			WorldTasksManager.schedule(new WorldTask() {
				int loop = 0;
				@Override
				public void run() {
					if (loop == 0) {
						v.transformIntoNPC(22322);
						v.setNextAnimation(new Animation(28263));
					} else {
						stop();
					}
					loop++;
				}
			}, 0, 0);
			v.isTransforming(false);
			v.setAttackCounter(0);
		}
				
		switch (v.getPhase()) {
			case Vindicta.PHASE_ONE:
				if (v.isFightStart()) {
					System.out.println("is: " + v.getAttackCounter());
					//if start of fight, 2 auto attacks and a hurricane.
					if (v.getAttackCounter() < 2 ) {
						//2 auto attacks before spin
						autoAttack(v, target, r);
					} else if (v.getAttackCounter() == 2) {
						hurricaneAttack(v, r);
						v.setFightStart(false);
						v.setAttackCounter(0);
					}
				} else {
					System.out.println("isnt: " + v.getAttackCounter());

					//if not start of fight, 1 auto, 1 auto + range if not melee distance, 1 auto.
					if (v.getAttackCounter() < 1 || v.getAttackCounter() == 2 || (v.getAttackCounter() >= 4 && v.getAttackCounter() <=6))
						//2 auto attacks before spin
						autoAttack(v, target, r);
					else if (v.getAttackCounter() == 1)
						//slam target, hit anyone else out of range
						meleeAndRangeAttack(v, target, r);
					else if (v.getAttackCounter() == 3) {
						// dragon fire
						System.out.println("vindy direction: " + v.getDirection());
						v.setNextAnimation(new Animation(28260));
						ArrayList<Entity> targets = v.getPossibleTargets();
						if (targets.size() >= 3) {
							Entity unlucky = targets.remove(r.nextInt(targets.size()));
							this.attemptDiagonalWalls(v, unlucky, false);
							unlucky = targets.remove(r.nextInt(targets.size()));
							this.attemptDiagonalWalls(v, unlucky, false);
						} else {
							int luck = r.nextInt(100);
							if (luck < 25)
								spawnNSwalls(v, target, true, false);
							else if (luck < 50)
								spawnEWwalls(v, target, true, false);
							else if (luck < 100)
								attemptDiagonalWalls(v, target, false);
						}

						v.setAttackCounter(v.getAttackCounter() + 1);
					} else if (v.getAttackCounter() == 7) {
						//spin attack
						hurricaneAttack(v, r);
						v.setAttackCounter(0);
					}
			}
				break;
			case Vindicta.PHASE_TWO:
				if (v.getAttackCounter() == 0 || v.getAttackCounter() == 2)  {
					autoAttack(v, target, r);
				} else if (v.getAttackCounter() == 1 || v.getAttackCounter() == 3) {
					autoRange(v, r);
				} else {
					WorldTile corner;
					for (int x = 0; x < Vindicta.P2_CORNERS.length; x++) {
						for (Entity t : v.getPossibleTargets()) {
//							if (exactly45DegreesFromCorner(t, Vindicta.P2_CORNERS[x])) {
//								spawn45DegreeP2line(Vindicta.P2_CORNERS[x], t, v);
//								break;
//							}
							if (Math.abs(t.getX() - Vindicta.P2_CORNERS[x].getX()) <= 2) {
								v.setTarget(t);
								this.sendVindictaToCorner(v, t, Vindicta.P2_CORNERS[x]);
								CoresManager.fastExecutor.schedule(new TimerTask() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										spawnNSwalls(v, t, false, true);
										cancel();
									}
								}, 3000);
								break;
							} else if (Math.abs(t.getY() - Vindicta.P2_CORNERS[x].getY()) <= 2) {
								v.setTarget(t);
								this.sendVindictaToCorner(v, t, Vindicta.P2_CORNERS[x]);
								CoresManager.fastExecutor.schedule(new TimerTask() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										spawnEWwalls(v, t, false, true);
										cancel();
									}
								}, 3000);
								break;
								
							} else {
								while ((corner = Vindicta.P2_CORNERS[r.nextInt(Vindicta.P2_CORNERS.length)]).withinDistance(target, 5)) {
									corner = Vindicta.P2_CORNERS[r.nextInt(Vindicta.P2_CORNERS.length)];
								}
								sendVindictaToCorner(v, target, corner);
								break;
							}
						}
					}
					v.setAttackCounter(0);
				}
				break;
		}

		
		return defs.getAttackDelay();
	}
	

	private void spawn45DegreeP2line(WorldTile corner, Entity t, Vindicta v) {
		// TODO Auto-generated method stub
		WorldTile temp = corner;
		if (temp == Vindicta.P2_CORNERS[0]) {
			for (int x = temp.getX(); x <= Vindicta.ARENA_X_END; x++) {
				World.sendGraphicsWider(null, new Graphics(6164), new WorldTile(x, temp.getY(), temp.getPlane()));
				temp.set(new WorldTile(temp.getX(), temp.getY()+1, temp.getPlane()));
			}
		} else if (temp == Vindicta.P2_CORNERS[1]) {
			for (int x = temp.getX(); x <= Vindicta.ARENA_X_END; x++) {
				World.sendGraphicsWider(null, new Graphics(6164), new WorldTile(x, temp.getY(), temp.getPlane()));
				temp.set(new WorldTile(temp.getX(), temp.getY()-1, temp.getPlane()));
			}
		} else if (temp == Vindicta.P2_CORNERS[2]) {
			for (int x = temp.getX(); x <= Vindicta.ARENA_X_END; x++) {
				World.sendGraphicsWider(null, new Graphics(6164), new WorldTile(x, temp.getY(), temp.getPlane()));
				temp.set(new WorldTile(temp.getX(), temp.getY()+1, temp.getPlane()));
			}
		} else if (temp == Vindicta.P2_CORNERS[3]) {
			for (int x = temp.getX(); x > Vindicta.ARENA_X_START; x--) {
				World.sendGraphicsWider(null, new Graphics(6164), new WorldTile(x, temp.getY(), temp.getPlane()));
				temp.set(new WorldTile(temp.getX(), temp.getY()+1, temp.getPlane()));
			}
		}
		v.setLastUsedWall(Vindicta.FORTY_FIVE_DEGREE);
		sendDamageEvent(v, t, null, true);
	}

	private boolean exactly45DegreesFromCorner(Entity t, WorldTile corner) {
		if (corner == Vindicta.P2_CORNERS[0]) {
			//sw
			WorldTile temp = corner;
			for (int x = temp.getX(); x <= Vindicta.ARENA_X_END; x++) {
				if (t.getX() == x && t.getY() == temp.getY()) {
					return true;
				}
				temp.set(new WorldTile(temp.getX(), temp.getY()+1, temp.getPlane()));
			}
		}
		if (corner == Vindicta.P2_CORNERS[1]) {
			//nw
			WorldTile temp = corner;
			for (int x = temp.getX(); x <= Vindicta.ARENA_X_END; x++) {
				if (t.getX() == x && t.getY() == temp.getY()) {
					return true;
				}
				temp.set(new WorldTile(temp.getX(), temp.getY()-1, temp.getPlane()));
			}
		}
		if (corner == Vindicta.P2_CORNERS[2]) {
			//ne
			WorldTile temp = corner;
			for (int x = temp.getX(); x >= Vindicta.ARENA_X_START; x--) {
				if (t.getX() == x && t.getY() == temp.getY()) {
					return true;
				}
				temp.set(new WorldTile(temp.getX(), temp.getY()-1, temp.getPlane()));
			}
		}
		if (corner == Vindicta.P2_CORNERS[3]) {
			//se
			WorldTile temp = corner;
			for (int x = temp.getX(); x > Vindicta.ARENA_X_START; x--) {
				if (t.getX() == x && t.getY() == temp.getY()) {
					return true;
				}
				temp.set(new WorldTile(temp.getX(), temp.getY()+1, temp.getPlane()));
			}
		}
		return false;
	}

	private void attemptDiagonalWalls(Vindicta v, Entity target, boolean lastLonger) {
		int[] line_start_coords = checkSWtoNEDiagonals(target);
		Random r = new Random();
		if (r.nextBoolean()) {
			if (line_start_coords != null) {
				spawnSWtoNEDiagonal(line_start_coords[1], target.getPlane(), target, v);
				v.setLastUsedWall(Vindicta.SW_TO_NE);
				sendDamageEvent(v, target, line_start_coords, lastLonger);
				return;
			}
			line_start_coords = checkNWtoSEDiagonals(target);
			if (line_start_coords != null) {
				spawnNWtoSEDiagonal(line_start_coords[1], target.getPlane(), target, v);
				v.setLastUsedWall(Vindicta.NW_TO_SE);
				sendDamageEvent(v, target, line_start_coords, lastLonger);
				return;
			}
		} else {
			line_start_coords = checkNWtoSEDiagonals(target);
			if (line_start_coords != null) {
				spawnNWtoSEDiagonal(line_start_coords[1], target.getPlane(), target, v);
				v.setLastUsedWall(Vindicta.NW_TO_SE);
				sendDamageEvent(v, target, line_start_coords, lastLonger);
				return;
			}
			line_start_coords = checkSWtoNEDiagonals(target);
			if (line_start_coords != null) {
				spawnSWtoNEDiagonal(line_start_coords[1], target.getPlane(), target, v);
				v.setLastUsedWall(Vindicta.SW_TO_NE);
				sendDamageEvent(v, target, line_start_coords, lastLonger);
				return;
			}
		}
		System.out.println("Unable to spawn diag.");
		if (r.nextBoolean()) 
			spawnNSwalls(v, target, true, lastLonger);
		else 
			spawnEWwalls(v, target, true, lastLonger);
	}

	private void sendDamageEvent(Vindicta v, Entity target, int[] start_point, boolean lastLonger) {
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
						if (loop >= (lastLonger ? 170 : 85))
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
							if (loop >= (lastLonger ? 170 : 85))
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
							if (loop >= (lastLonger ? 170 : 85))
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
							if (loop >= (lastLonger ? 170 : 85))
								cancel();
							loop++;
							}
						} , 0, 333);
			    } else if (v.getLastUsedWall() == Vindicta.FORTY_FIVE_DEGREE) {
				    CoresManager.fastExecutor.schedule(new TimerTask() {
				    	int loop = 0;
				    	int y = start_point[1];
						@Override
						public void run() {
							for (int x = start_point[0]; x <= Vindicta.ARENA_X_END; x++) {
								if (t.getX() == x && t.getY() == y) {
									int hit;
									//String message = FireBreathAttack.getProtectMessage(target);
									hit = Utils.random(75, 100);
									t.applyHit(new Hit(v, hit, HitLook.REGULAR_DAMAGE));
								}
								y++;
							}
							if (loop >= (lastLonger ? 170 : 85))
								cancel();
							loop++;
							}
						} , 0, 333);
			    }
			}
	}

	private void spawnEWwalls(Vindicta v, Entity target, boolean spawnGorvek, boolean lastLonger) {
		if (lastLonger) {
			for (int x = 3088; x < 3112; x+=2)
				World.sendGraphicsWider(null, new Graphics(6164,0,0), new WorldTile(x, target.getY(), target.getPlane()));
			v.setLastUsedWall(Vindicta.EAST_WEST);
		} else {
			for (int x = 3088; x < 3112; x+=2)
				World.sendGraphicsWider(null, new Graphics(6112,0,0), new WorldTile(x, target.getY(), target.getPlane()));
			v.setLastUsedWall(Vindicta.EAST_WEST);
		}
		sendDamageEvent(v, target, null, lastLonger);
		if (spawnGorvek)
			spawnGorvek(v, target, -1);
	}
	
	private void spawnNSwalls(Vindicta v, Entity target, boolean spawnGorvek, boolean lastLonger) {
		if (lastLonger) {
			for (int y = 6864; y < 6898; y+=2)
				World.sendGraphicsWider(null, new Graphics(6164,0,0), new WorldTile(target.getX(), y, target.getPlane()));
			v.setLastUsedWall(Vindicta.NORTH_SOUTH);
		} else {
			for (int y = 6864; y < 6898; y+=2)
				World.sendGraphicsWider(null, new Graphics(6112,0,0), new WorldTile(target.getX(), y, target.getPlane()));
			v.setLastUsedWall(Vindicta.NORTH_SOUTH);
		}
		sendDamageEvent(v, target, null, lastLonger);
		if (spawnGorvek)
			spawnGorvek(v, target, -1);
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
	WorldTasksManager.schedule(new WorldTask() {
		int loop = 0;
		NPC gorvek;
		@Override
		public void run() {
			if (gorvek == null) {
				switch (v.getLastUsedWall()) {
				case Vindicta.EAST_WEST:
					if (target.getX() <= 3099) {
						gorvek = new NPC(22323 , new WorldTile (3088, target.getY()+2, target.getPlane()), 0, true);
						gorvek.setDirection(12228);
					} else {
						gorvek = new NPC(22323 , new WorldTile (3112, target.getY()-2, target.getPlane()), 0, true);
						gorvek.setDirection(4096);
					}
					break;
				case Vindicta.NORTH_SOUTH:
					if (target.getY() >= 6879) {
						gorvek = new NPC(22323, new WorldTile(target.getX(), 6894, target.getPlane()), 0, true);
						gorvek.setDirection(0);
					} else {
						gorvek = new NPC(22323 , new WorldTile (target.getX(), 6864, target.getPlane()), 0, true);
						gorvek.setDirection(8192);
					}
					break;
				case Vindicta.NW_TO_SE:
					if (target.getX() < 3099) {
						gorvek = new NPC(22323 , new WorldTile (Vindicta.ARENA_X_START, start_y, target.getPlane()), 0, true);
						gorvek.setDirection(14336);
					} else {
						gorvek = new NPC(22323, new WorldTile(Vindicta.ARENA_X_END, start_y + 10, target.getPlane()), 0, true);
						gorvek.setDirection(6114);
					}
					break;
				case Vindicta.SW_TO_NE:
					if (target.getX() <=3099) {
						gorvek = new NPC(22323 , new WorldTile (Vindicta.ARENA_X_START, start_y, target.getPlane()), 0, true);
						gorvek.setDirection(10240);
					} else {
						gorvek = new NPC(22323, new WorldTile(Vindicta.ARENA_X_END, start_y + 16, target.getPlane()), 0, true);
						gorvek.setDirection(2048);
					}

					break;
				}
				gorvek.spawn();
				System.out.println("Gorvek spawned X: " + gorvek.getX() + ", Y: " + gorvek.getY() + ", Z: " + gorvek.getPlane());
			}
			if (loop == 0) {
				gorvek.setNextAnimation(new Animation(28264));
				System.out.println("Anim sent");
			} else {
				gorvek.finish();
				World.removeNPC(gorvek);
				System.out.println("gorvek removed");
				stop();
			}
			loop++;

		}
			
	}, 0, 1);
}
	
	private void sendVindictaToCorner(Vindicta v, Entity t, WorldTile corner_tile) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			@Override
			public void run() {
				if (loop == 0) {
					v.setNextAnimation(new Animation(28275));
				} else if (loop == 1) {
					v.setLocation(corner_tile);
					v.setNextWorldTile(corner_tile);
					v.faceEntity(t);
				} else if (loop ==2) {
					v.setNextAnimation(new Animation(28276));
					//v.setNextGraphics(new Graphics(6118));
				} else if (loop ==3) {
					v.setNextAnimation(new Animation(28277));
					//v.setNextGraphics(new Graphics(6119));
				} else {
					//v.setForceTargetDistance(3);
					stop();
				}
				loop++;
			}
		}, 0, 0);
	}
	
}
