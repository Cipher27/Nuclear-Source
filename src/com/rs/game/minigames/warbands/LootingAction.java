package com.rs.game.minigames.warbands;

import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Action;
import com.rs.game.player.controlers.Wilderness;
import com.rs.utils.Utils;

public class LootingAction extends Action {

	private WorldObject object;

	public LootingAction(WorldObject object) {
		this.object = object;
	}

	public static boolean loot(Player player, WorldObject object) {
		if (object == null || player == null) {
			return false;
		}
		player.getActionManager().setAction(new LootingAction(object));
		return true;
	}

	@Override
	public boolean start(Player player) {
		return checkAll(player);
	}

	@Override
	public boolean process(Player player) {
		return checkAll(player);
	}
	
	public boolean checkAll(final Player player) {
		if (Warbands.warband == null || player == null || player.isDead() || !player.hasStarted() || player.hasFinished() || player.isLocked())
			return false;
		/*if (!(player.getControlerManager().getControler() instanceof Wilderness))
			return false;*/
		if (!Warbands.warband.isInArea(object) || !Warbands.warband.objectHasResources(object) || Warbands.warband.resources <= 0)
			return false;
		if (Warbands.warband.charges > 0) {
			player.getPackets().sendGameMessage("The obelisk needs to be completely discharged prior to doing this.");
			return false;
		}
		if (Warbands.warband.remainingOccupants > 0) {
			player.getPackets().sendGameMessage("Some of the camps occupants are still alive, they need to be slain prior to doing this.");
			return false;
		}
		if (player.getInventory().getFreeSlots() <= 0) {
			player.sm("You don't have enough space to do this.");
			return false;
		}
		player.resetWalkSteps();
		player.faceObject(object);
		return true;
	}

	@Override
	public int processWithDelay(final Player player) {
		if (Warbands.warband == null || Warbands.warband.resources <= 0 || player.getInventory().getFreeSlots() <= 0)
			return -1;
		if (player.getAttackedByDelay() + 3000 > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage("You cannot loot the crates whilst in combat.");
			return -1;
		}
		if (Warbands.warband.decreaseRemainingResources(player, object))
			return 3;
		return -1;
	}
	
	@Override
	public void stop(Player player) {
		setActionDelay(player, 3);
	}
}
