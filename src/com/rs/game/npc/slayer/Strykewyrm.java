package com.rs.game.npc.slayer;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

@SuppressWarnings("serial")
public class Strykewyrm extends NPC {

	public static void handleStomping(final Player player, final NPC npc) {
		if (npc.isCantInteract())
			return;
		if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
			if (player.getAttackedBy() != npc
					&& player.getAttackedByDelay() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage(
						"You are already in combat.");
				return;
			}
			if (npc.getAttackedBy() != player
					&& npc.getAttackedByDelay() > Utils.currentTimeMillis()) {
				if (npc.getAttackedBy() instanceof NPC) {
					npc.setAttackedBy(player);
				} else {
					player.getPackets().sendGameMessage(
							"That npc is already in combat.");
					return;
				}
			}
		}
		switch (npc.getId()) {
		case 20629:
			if (!hasLevel(player, npc, 94))
				return;
		case 9462:// ice
			if (!hasLevel(player, npc, 93))
				return;
		case 9464:
			if (!hasLevel(player, npc, 77))
				return;
		case 9466:
			if (!hasLevel(player, npc, 73))
				return;
			//boolean noTask = true;
			player.lock();
			player.setNextAnimation(new Animation(4278));
			/*final SlayerManager manager = player.getSlayerManager();
			if (manager != null) {
				if (manager.getSlayerTask() != null) {
					if(manager.npcIsTask(npc.getName()))
						noTask = false;
				}
			}

			if (noTask) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						player.getPackets().sendGameMessage(
								"Nothing seems to happen.");
						player.setNextAnimation(new Animation(857));
						player.unlock();
					}
				}, 1);
				return;
			}*/
			npc.setCantInteract(true);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					npc.setNextAnimation(new Animation(12795));
						if (npc.getId() == 9462)
							npc.transformIntoNPC(9463);
						else if (npc.getId() == 9464)
							npc.transformIntoNPC(9465);
						else if (npc.getId() == 9466)
							npc.transformIntoNPC(9467);
						else if (npc.getId() == 20629)
							npc.transformIntoNPC(20630);
					npc.setNextGraphics(new Graphics(npc.getId() == 9463 ? 2318
							: npc.getId() == 9465 ? 2316 : 2317));
					npc.setTarget(player);
					npc.setAttackedBy(player);
					npc.setCantInteract(false);
					player.unlock();
					stop();
				}
			}, 1, 2);
		}
	}

	private static boolean hasLevel(Player player, NPC npc, int skill) {
		NPCDefinitions defs = NPCDefinitions.getNPCDefinitions(npc.getId() + 1);
		if (player.getSkills().getLevelForXp(18) < skill) {
			player.getPackets().sendGameMessage(
					"You need a Slayer level of " + skill + " to attack a "
							+ defs.getName() + ".");
			return false;
		}
		return true;
	}

	private int stompId;

	public Strykewyrm(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, true);
		stompId = id;
	}

	@Override
	public void processNPC() {
		super.processNPC();
		if (getId() != stompId && !isCantInteract() && !isUnderCombat()) {
			setNextAnimation(new Animation(12796));
			setCantInteract(true);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					transformIntoNPC(getId() - 1);
					setCantInteract(false);
				}
			}, 1);
		}
	}

	@Override
	public void reset() {
		setNPC(stompId);
		super.reset();
	}

	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		resetWalkSteps();
		getCombat().removeTarget();
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(defs.getDeathEmote()));
					drop();//cheap slayer fix
				} else if (loop >= defs.getDeathDelay()) {
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
}