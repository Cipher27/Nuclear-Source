package com.rs.game.minigames.warbands;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;


public class DischargeObeliskAction extends Action {
	
	private WorldObject object;

	public DischargeObeliskAction(WorldObject object) {
		this.object = object;
	}

	public static boolean siphon(Player player, WorldObject object) {
		if (object == null || player == null) {
			return false;
		}
		player.getActionManager().setAction(new DischargeObeliskAction(object));
		return true;
	}

	@Override
	public boolean start(Player player) {
		if(checkAll(player)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean process(Player player) {
		return checkAll(player);
	}
	
	public boolean checkAll(final Player player) {
		if (Warbands.warband == null || player == null || player.isDead() || !player.hasStarted() || player.hasFinished()) {
			return false;
		}
		if (!Warbands.warband.isInArea(object)) {
			player.getPackets().sendGameMessage("This obelisk is carrying no charge.");
			return false;
		}
		if (!(player.getControlerManager().getControler() instanceof Wilderness) || player.isLocked()) {
			return false;
		}
		if (Warbands.warband != null && Warbands.warband.charges <= 0) {
			return false;
		}
		player.resetWalkSteps();
		player.faceObject(object);
		return true;
	}

	@Override
	public int processWithDelay(final Player player) {
		if (Warbands.warband == null || Warbands.warband.charges <= 0)
			return -1;
		if (player.getAttackedByDelay() + 3000 > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage("You cannot discharge the obelisk whilst in combat.");
			return -1;
		}
		final boolean toObelisk = Warbands.warband != null && Warbands.warband.charges >= 40;
		if (Warbands.warband != null) {
			Warbands.warband.charges--;
			if (Warbands.warband.charges == 40 || Warbands.warband.charges == 5 ||Warbands.warband.charges == 0)
				incrementStage(player, Warbands.warband.charges);
		} else
			return -1;
		player.setNextAnimation(new Animation(16596));
		player.setNextFaceWorldTile(object);
		World.sendProjectile(object, (toObelisk ? object : player), (toObelisk ? player : object), 3060, 31, 35, 35, 0, 2, 0);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.setNextGraphics(new Graphics(3062));
				player.getSkills().addXp((toObelisk ? Skills.PRAYER : Skills.SUMMONING), 26);
			}
		}, 1);
	return 1;
	}
	
	@Override
	public void stop(Player player) {
		player.setNextAnimation(new Animation(16599));
		setActionDelay(player, 3);
	}
	
	public void incrementStage(Player player, int charge) {
		if (charge == 40)
			Warbands.sendLocalPlayerMessage(player, "<col=0080FF><img=4>Warbands: The obelisk has been interrupted. Reinforcements are now being summoned.");
		else if (charge == 5) {
			if (Warbands.warband != null) {
				if (Warbands.warband.remainingOccupants > 0) {
					Warbands.warband.spawnReinforcements();
					Warbands.sendLocalPlayerMessage(player, "<col=0080FF><img=4>Warbands: Reinforcements have arrived. Keep pushing!");
				}
			}
		} else if (charge == 0) {
			Warbands.sendLocalPlayerMessage(player, "<col=0080FF><img=4>Warbands: The obelisk has fallen. Harvest the rewards!");
		}
	}
}
