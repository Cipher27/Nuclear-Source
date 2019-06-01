package com.rs.game.npc.others;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

@SuppressWarnings("serial")
public class NecroLord extends NPC {

    private int resetTicks;
    private List<NecrolordSkeletton> skeletons;

    public NecroLord(int id, WorldTile tile, int mapAreaNameHash,boolean canBeAttackFromOutOfArea, boolean spawned) {
    	super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setCapDamage(500);
		setLureDelay(3000);
		setForceTargetDistance(64);
		setForceFollowClose(false);
	skeletons = new CopyOnWriteArrayList<NecrolordSkeletton>();
    }

    @Override
    public void processNPC() {
	super.processNPC();
	if (!isUnderCombat() && skeletons != null && skeletons.size() > 0) {
	    resetTicks++;
	    if (resetTicks == 50) {
		resetSkeletons();
		resetTicks = 0;
		return;
	    }
	}
    }

    @Override
    public double getMagePrayerMultiplier() {
	return 0.6;
    }

    public void addSkeleton(WorldTile tile) {
    NecrolordSkeletton npc = new NecrolordSkeletton( 2037,tile, mapAreaNameHash,true,true);
	npc.setForceAgressive(true);
	skeletons.add(npc);
	World.sendGraphics(npc, new Graphics(2399), tile);
    }

    public void resetSkeletons() {
	for (NecrolordSkeletton skeleton : skeletons)
	    skeleton.sendDeath(this);
	skeletons.clear();
    }

    public void removeSkeleton(NecrolordSkeletton sk) {
	skeletons.remove(sk);
    }
    
    public int getSkeletonAmount(){
    	return skeletons.size();
    }


    @Override
    public void sendDeath(Entity source) {
	super.sendDeath(source);
	resetSkeletons();
    }
}
