package com.rs.game.npc.others;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class Leeuni extends NPC {

	public Leeuni(int id, WorldTile tile, int mapAreaNameHash,boolean spawned) {
		super(id, tile, mapAreaNameHash, true, spawned);
		setCapDamage(700);
		//setLureDelay(3000);
		//setForceTargetDistance(64);
		//setForceFollowClose(false);
	}

	@Override
	public void processNPC() {
		super.processNPC();
		if (isDead())
			return;
		/*if(!isUnderCombat())
			super.sendDeath(this);*/
		int maxhp = getMaxHitpoints();
		if (maxhp > getHitpoints() && getPossibleTargets().isEmpty())
			super.sendDeath(this);
	}
	
	
	@Override
	public void sendDeath(Entity source) {
		
		resetWalkSteps();
		for (Entity e : getPossibleTargets()) {
			if (e instanceof Player) {
				final Player player = (Player) e;
				Player killer1 = getMostDamageReceivedSourcePlayer();
				killer1.getInventory().addItem(new Item(1));
				
				killer1.getDialogueManager().startDialogue("SimplePlayerMessage", "Ooh look he had a toolkit on him");
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						player.stopAll();
					}
				}, 8);
			}
		}
		getCombat().removeTarget();
		setNextAnimation(new Animation(getCombatDefinitions().getDeathEmote()));
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				drop();
				reset();
				setLocation(getRespawnTile());
				finish();
				if (!isSpawned())
					setRespawnTask();
				stop();
			}
			
		}, 4);
	}
}
