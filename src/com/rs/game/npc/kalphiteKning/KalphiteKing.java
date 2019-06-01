package com.rs.game.npc.kalphiteKning;

import java.util.ArrayList;
import java.util.Iterator;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;



@SuppressWarnings("serial")
public class KalphiteKing extends NPC {
	
	/*
	 * What was wrong and what has been done (x)
	 * Asked this to both streamers and a couple of friends who has done kk a lot
	 * 3 attacks then special(x)
	 * start at ANY colour rather then always melee(x)
	 * dig resets the atks to special(x)
	 * 
	 * there's a couple more, but ill fix those later on
	 * 
	 * charge only N, E, S, or W.
	 * 
	 * */
	
	private ArrayList<WorldTile> minionSpawns;
	private ArrayList<NPC> minions;
	private long lastSwitch;
	private int movesUntilSpecial;
	private int spawnCount;
	private boolean usedImmortality;
	private boolean useStunDartNext;
	private int specialMovesUse;
	private boolean specificSpecial;
	private WorldTile startingWorldTile;
	
	public KalphiteKing(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setLureDelay(Integer.MAX_VALUE);
		setRun(true);
		setNoDistanceCheck(true);
		setIntelligentRouteFinder(true);
		setForceTargetDistance(64);
		start();

	}
	
	public void resetMovesUntilSpecial() {
		movesUntilSpecial = /*Utils.random(1, 5)*/ 3;
	}
	
	public boolean isSpecificSpecial() {
		return specificSpecial;
	}
	
	public int getSpecialMovesUse() {
		return specialMovesUse;
	}
	
	public void setSpecificSpecial(boolean specificSpecial) {
		this.specificSpecial = specificSpecial;
	}
	
	public void setSpecialMovesUse(int specialMovesUse) {
		this.specialMovesUse = specialMovesUse;
	}
	
	
	@Override
	public void spawn() {
		super.spawn();
		start();
	}
	
	@Override
	public void sendDeath(final Entity source) {
		if(!usedImmortality && Utils.random(100) < 25) { //15% chance using immortality :p
			setCantInteract(true);
			setHitpoints((int) (getMaxHitpoints() * 0.40));
			usedImmortality = true;
			setNextAnimation(new Animation(19483));
			setCantInteract(false);
			return;
		}
		setNextNPCTransformation(16697);
		killMinions();
		super.sendDeath(source);
	}
	
	//removes spawned minions
	private void killMinions(){
		for (Iterator<NPC> iter = minions.iterator(); iter.hasNext();) {
			NPC npc = iter.next();
			npc.finish();
			iter.remove();
		}
	}
	
	private void start() {
		spawnCount = 0;
		auraTicks = 0;
		usedImmortality = false;
		specialMovesUse = 0;
		specificSpecial = false;
		resetSwitch();
		getOutsideEarth(null);
		resetMovesUntilSpecial();
		setDirection(Utils.getAngle(0, -1));
		minions = new ArrayList<NPC>();
		minionSpawns = new ArrayList<WorldTile>();
		getMinionSpawnWorldTiles();
		startingWorldTile = this.getWorldTile();
		//switchPhase();
	}
	
	private void getMinionSpawnWorldTiles(){
		for(int i = 0; i < 6; i++) {
			WorldTile WorldTile = new WorldTile(this.getX()-14 + Utils.random(5,15), this.getY()+ Utils.random(5,20)-16, this.getPlane());
			minionSpawns.add(WorldTile);
		}
	}
	
	public boolean canSpecial() {
		return movesUntilSpecial == 0;
	}
	
	public void reduceMovesUntilSpecial() {
		if(auraTicks == 0)
			movesUntilSpecial--;
	}
	
	private boolean canSwitch() {
		return lastSwitch + 60000 <= Utils.currentTimeMillis();
	}
	//120000 - 2 mins
	//60000 - 1 min
	private void resetSwitch() {
		lastSwitch = Utils.currentTimeMillis();
	}
	
	
	private int getHPPercentage() {
		return getHitpoints() * 100 / getMaxHitpoints();
	}
	@Override
	public void processNPC() {
		if (isDead())
			return;
		if(canSwitch()) 
			switchPhase();
		if((getHPPercentage() < 75 && spawnCount < 1)
				|| (getHPPercentage() < 25 && spawnCount < 2))
			battleCry();
		if(auraTicks == 1) {
			auraTicks = 0;
			if(!attackedSomeone)
				heal(2400);
		}else if (auraTicks > 0){
			auraTicks--;
			setNextGraphics(new Graphics(siphon ? 3737: 3736));
		}
		

		for (Iterator<NPC> iter = minions.iterator(); iter.hasNext();) {
			NPC npc = iter.next();
			if(npc.hasFinished())
				iter.remove();
		}
		//dmg for being under
		if(!isCantInteract() && this.getCombat().getCombatDelay() == 0) {
			//dmg for being under
			getPossibleTargets().stream().filter(t -> Utils.colides(this, t)).forEachOrdered(t -> {
				t.applyHit(new Hit(this, Utils.random(650) + 1, HitLook.REGULAR_DAMAGE));
			});
		}
		super.processNPC();
	}
	
	
	
	private void battleCry() {
		spawnCount++;
		setNextAnimation(new Animation(19462));
		//battle cry
//if nearby when he uses it u get dmg
//stuns fo 3 sec
		getPossibleTargets().stream().filter(t -> Utils.isOnRange(this, t, 3)).forEachOrdered(t -> { //if nearby when he uses it u get dmg
			t.applyHit(new Hit(this, Utils.random(600) + 1, HitLook.REGULAR_DAMAGE));
			setFreezeDelay(3); //stuns fo 3 sec
		});
		if(minions.isEmpty()){
			WorldTasksManager.schedule(new WorldTask() {
	// 2963 1749
				//spawn 2977 1765
				//14 16
				@Override
				public void run() {
				//	for(int i = 0; i < 6; i++) {
					for (WorldTile WorldTile : minionSpawns) {
						NPC minion = World.spawnNPC(16706, WorldTile, -1, true, true,false);
						//KalphiteKingMinion minion = 
						//minion.setTarget(getPossibleTargets().get(Utils.random(getPossibleTargets().size())));
						assert minion != null;
						minion.setForceAgressive(true);
						minion.setForceMultiArea(true);
						minion.setForceTargetDistance(64);
						minion.setNextAnimation(new Animation(19492));
						minion.setNextGraphics(new Graphics(3748));
						minion.getPossibleTargets().stream().filter(target -> Utils.colides(minion, target)).forEachOrdered(target -> target.applyHit(new Hit(minion, Utils.random(400, 401), HitLook.MELEE_DAMAGE)));
						minions.add(minion);
					}
				}
				
			}, 5);
		}
	}
	
	
	/*
	 * randomly switches every 2min
	 */
	private void switchPhase() {
		int currentPhase = getCurrentPhase();
		int nextPhase = (currentPhase + Utils.random(2) + 1) % 3;
		setNextNPCTransformation(16697+nextPhase);
		setNextGraphics(new Graphics(nextPhase == 0 ? 3750 : nextPhase == 1 ? 3749 : 3751));
		resetSwitch();
		resetMovesUntilSpecial();
		setNoDistanceCheck(true);
		if(this.getId() == 16697){
			setForceFollowClose(true);
		}else{
			setForceFollowClose(false);
		}
		if(getId() == 16699)
			setUseStunDartNext(true);
	}
	
	public void setUseStunDartNext(boolean useStunDartNext) {
		this.useStunDartNext = useStunDartNext;
	}
	
	public boolean isUseStunDartNext() {
		return useStunDartNext;
	}
	
	private int getCurrentPhase() {
		return getId() - 16697;
	}
	
	@Override
	public void reset() {
		//setNPC(16697+Utils.random(3));
		super.reset();
	}
	
	private boolean siphon;
	private int auraTicks;
	private boolean attackedSomeone;
	
	public void useAura() {
		siphon = Utils.random(2) == 1;
		attackedSomeone = false;
		auraTicks = 17;
	}
	
	@Override
	public void handleIngoingHit(Hit hit) {
		if (auraTicks > 0 && siphon)
			hit.setHealHit();
		else{
			Entity source = hit.getSource();
			if(source != null) {
				//if (Misc.random(20) == 1)
					//useBarricade();
			}
		}
		Entity source = hit.getSource();
		if(source instanceof Player){
			Player player = (Player) source;
			if(player.getEquipment().getWeaponId() == 10581){
				if(Utils.random(100) <= 3){
					hit.setDamage(hit.getDamage() * 3);
				}else{
					hit.setDamage((int)(hit.getDamage() * 1.33));
				}
			}
				
		}
		super.handleIngoingHit(hit);
	}
	
	@SuppressWarnings("unused")
	private void useBarricade() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop < 18) {
						setNextAnimation(new Animation(19455));
						setNextGraphics(new Graphics(3741));
				}
				loop++;
			}
		}, 0, 1);
	}
	
	@Override
	public Hit handleOutgoingHit(Hit hit, Entity target) {
		if (auraTicks > 0) {
			attackedSomeone = true;
			if(!siphon)
				heal(hit.getDamage());
		}
		return hit;
	}
	
	public void dig(final Entity target) {
		resetMovesUntilSpecial();
		setNextAnimation(new Animation(19453));
		setNextGraphics(new Graphics(3746));
		setCantInteract(true);
		WorldTasksManager.schedule(new WorldTask() {

			boolean part1 = true;
			
			@Override
			public void run() {
				if(part1) {
					setFinished(true);
					part1 = false;
				}
				else {
					stop();

					//if instance spawned be sure that the player didnt leave or the boss would tp outside
					//if npc dont make it tp under to be sure it doesnt bug (shouldnt happen anyway)
					if (target instanceof Player) {
						if(target.withinDistance(startingWorldTile, 75)){
							WorldTile loc = new WorldTile(target.getX() - (getSize() / 2), target.getY() - (getSize() / 2), target.getPlane());
							if (World.isFloorFree(loc.getPlane(), loc.getX(), loc.getY()))
								setNextWorldTile(loc);
						} else{
							setNextWorldTile(startingWorldTile);
						}
					}
					setFinished(false);
					getOutsideEarth(target);
				}
			}
		}, 6, 5);
		
	}
	
	private void getOutsideEarth(final Entity target) {
		setNextAnimation(new Animation(19451));
		setNextGraphics(new Graphics(3745));
		setCantInteract(true);
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				setCantInteract(false);
				if(target != null)
					setTarget(target);
				getCombat().setCombatDelay(5);
				setNextAnimation(new Animation(-1));
				for (Entity entity : getPossibleTargets()) {
					if (Utils.colides(KalphiteKing.this, entity)) {
						entity.applyHit(new Hit(KalphiteKing.this, Utils.random(300, 600), HitLook.REGULAR_DAMAGE));
						if (entity instanceof Player)
							entity.setNextAnimation(new Animation(10070));
					}
				}			
			}
			
		}, 5);
	}
	
	
	
	
}
