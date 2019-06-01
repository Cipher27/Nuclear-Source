package com.rs.game.npc.house;

import java.util.concurrent.TimeUnit;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.GameEngine;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Bank;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Sawmill;
import com.rs.game.player.content.Sawmill.Plank;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.construction.House.RoomReference;
import com.rs.game.player.content.construction.HouseConstants.Builds;
import com.rs.game.player.content.construction.HouseConstants.Room;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

@SuppressWarnings("serial")
public class Guard extends NPC {

	private House house;

	public Guard(int id, House house, WorldObject object) {
		super(id, object, -1, true, false);
		setDirection(Utils.getAngle(Utils.ROTATION_DIR_Y[object.getRotation()], Utils.ROTATION_DIR_X[object.getRotation()]));
		setForceAgressive(true);
		setForceTargetDistance(7);
		this.house = house;
	}

	@Override
	public void processNPC() {
		Entity target = getCombat().getTarget();
		if (target != null && !withinDistance(target, 7)) {
			getCombat().reset();
			resetCombat();
			WorldTile tile = getRespawnTile();
			addWalkSteps(tile.getX(), tile.getY());
			setNextFaceEntity(null);
		}
		super.processNPC();
	}

	@Override
	public void sendDeath(final Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
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
					reset();
					setNextFaceWorldTile(getRespawnTile());
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
		GameEngine.get().getSlowExecutor().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					if (!house.isLoaded())
						return;
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 600, TimeUnit.MILLISECONDS);
	}

}
