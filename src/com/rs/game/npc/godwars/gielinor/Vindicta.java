package com.rs.game.npc.godwars.gielinor;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.minigames.GodWarsBosses;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.MapAreas;
import com.rs.utils.Utils;

@SuppressWarnings("serial")
public class Vindicta extends NPC {

	
	final public static int PHASE_ONE = 0, PHASE_TWO = 1, PHASE_THREE = 2, PHASE_FOUR = 3;
	final public static int NORTH_SOUTH = 0, EAST_WEST = 1, SW_TO_NE = 2, NW_TO_SE = 3;
	public static final int ARENA_X_START = 3088;
	public static final int ARENA_X_END = 3110;
	public static final int ARENA_Y_START = 6866;
	public static final int ARENA_Y_END = 6894;
	private int phaseNum;
	private int p1_attackCounter = 0;
	private int lastUsedWall = -1;
	private boolean fightStart = true;
	private boolean isTransforming = false;
	
	public Vindicta(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		phaseNum = PHASE_ONE;
		setNextAnimation(new Animation(28257));
		setNextGraphics(new Graphics(6114));
	}

	@Override
	public ArrayList<Entity> getPossibleTargets() {
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>();
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playerIndexes = World.getRegion(regionId)
					.getPlayerIndexes();
			if (playerIndexes != null) {
				for (int npcIndex : playerIndexes) {
					Player player = World.getPlayers().get(npcIndex);
					if (player == null
							|| player.isDead()
							|| player.hasFinished()
							|| !player.isRunning()
							|| !player.withinDistance(this, 64)
							|| ((!isAtMultiArea() || !player.isAtMultiArea())
									&& player.getAttackedBy() != this && player
									.getAttackedByDelay() > System
									.currentTimeMillis())
							|| !clipedProjectile(player, false))
						continue;
					possibleTarget.add(player);
				}
			}
		}
		return possibleTarget;
	}
	
	
	/*
	 * gotta override else setRespawnTask override doesnt work
	 */
	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		phaseNum = PHASE_ONE;
		p1_attackCounter = 0;
		
		resetWalkSteps();
		getCombat().removeTarget();
		setNextAnimation(null);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(defs.getDeathEmote()));
				} else if (loop >= defs.getDeathDelay()) {
					drop();
					reset();
					setLocation(getRespawnTile());
					finish();
					setRespawnTask();
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	
	@Override
	public void setRespawnTask() {
		if (!hasFinished()) {
			reset();
			setLocation(getRespawnTile());
			finish();
		}
		final NPC npc = this;
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				setFinished(false);
				World.addNPC(npc);
				npc.setLastRegionId(0);
				World.updateEntityRegion(npc);
				loadMapRegions();
				checkMultiArea();
				GodWarsBosses.respawnBandosMinions();
			}
		}, getCombatDefinitions().getRespawnDelay() * 600,
				TimeUnit.MILLISECONDS);
	}

	
	
	public int getPhase() {
		// TODO Auto-generated method stub
		return phaseNum;
	}

	public int getAttackCounter() {
		return p1_attackCounter;
	}

	public void setAttackCounter(int p1_attackCounter) {
		this.p1_attackCounter = p1_attackCounter;
	}

	public int getLastUsedWall() {
		return lastUsedWall;
	}

	public void setLastUsedWall(int lastUsedWall) {
		this.lastUsedWall = lastUsedWall;
	}

	public boolean isFightStart() {
		return fightStart;
	}

	public void setFightStart(boolean fightStart) {
		this.fightStart = fightStart;
	}

	public void setPhase(int phaseTwo) {
		// TODO Auto-generated method stub
		phaseNum = phaseTwo;
	}

	public void isTransforming(boolean b) {
		isTransforming  = b;
	}

	public boolean isTransforming() {
		// TODO Auto-generated method stub
		return isTransforming;
	}
}
