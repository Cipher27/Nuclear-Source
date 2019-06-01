package com.rs.game.npc.others;

import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

@SuppressWarnings("serial")
public class DarkLord extends NPC {


	public DarkLord(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setCapDamage(500);
		setLureDelay(3000);
		setForceTargetDistance(64);
		setForceFollowClose(false);
	}



	@Override
	public void sendDeath(Entity source) {
		super.sendDeath(source);
	}
}