package com.rs.game.npc.others;

import com.rs.game.Entity;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

@SuppressWarnings("serial")
public class Dummy extends NPC {

	

	public Dummy(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
	}

	@Override
	public void handleIngoingHit(Hit hit) {
		int damage = hit.getDamage();
		if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
			if (damage > 0) {
				damage *= (int) 1.75;
			}
		}
		hit.setDamage(damage);
		super.handleIngoingHit(hit);
	}

	@Override
	public void sendDeath(Entity source) {
		super.sendDeath(source);
	}
}