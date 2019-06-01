package com.rs.game.npc.others;

import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.content.dungeoneering.DungeonManager;

@SuppressWarnings("serial")
public class NecrolordSkeletton extends NPC {

    private NecroLord boss;

    public NecrolordSkeletton(int id, WorldTile tile, int mapAreaNameHash,boolean canBeAttackFromOutOfArea, boolean spawned) {
    super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
    }

    @Override
    public double getMeleePrayerMultiplier() {
	return 0.6;
    }

    @Override
    public void drop() {

    }

    @Override
    public int getMaxHit() {
	return super.getMaxHit() * 2;
    }

    @Override
    public void sendDeath(Entity source) {
	super.sendDeath(source);
	((NecroLord)(source)).removeSkeleton(this);
    }
}
