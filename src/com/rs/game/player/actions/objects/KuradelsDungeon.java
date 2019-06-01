package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.NewForceMovement;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.agility.Agility;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * this class handles every object from the slayer tower
 * @author paolo
 *
 */
public class KuradelsDungeon {
	
	public static void RunWall(Player player, WorldObject object){
		if (!Agility.hasLevel(player, 90))
				return;
			player.lock();
			final WorldTile tile = new WorldTile(1641, player.getY() > 5260 ? 5260 : 5268, 0);
			WorldTasksManager.schedule(new WorldTask() {

				int ticks = -1;

				@Override
				public void run() {
					ticks++;
					if (ticks == 0)
						player.setNextFaceWorldTile(object);
					else if (ticks == 1) {
						player.setNextAnimation(new Animation(10738));
						player.setNextForceMovement(new NewForceMovement(player, 2, tile, 5, Utils.getAngle(object.getX() - player.getX(), object.getY() - player.getY())));
					} else if (ticks == 3)
						player.setNextWorldTile(tile);
					else if (ticks == 4) {
						player.getPackets().sendGameMessage("Your feet skid as you land floor.");
						player.unlock();
						stop();
						return;
					}
				}
			}, 0, 0);
			return;
		}
	public static void climbWall(Player player, WorldObject object){
		if (!Agility.hasLevel(player, 86))
				return;
			player.getPackets().sendGameMessage("You climb the low wall...", true);
			player.lock(3);
			player.setNextAnimation(new Animation(4853));
			final WorldTile toTile = new WorldTile(object.getX(), player.getY() < object.getY() ? object.getY() + 1 : object.getY() - 1, object.getPlane());
			player.setNextForceMovement(new ForceMovement(player, 0, (toTile == new WorldTile(1632,5292,0) || toTile == new WorldTile(1634,5292,0) ? new WorldTile(1633,5292,0) : toTile), 2, player.getY() < object.getY() ? ForceMovement.NORTH : ForceMovement.SOUTH));
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					player.setNextWorldTile((toTile == new WorldTile(1632,5292,0) || toTile == new WorldTile(1634,5292,0) ? new WorldTile(1633,5292,0) : toTile));
				}
			}, 1);
			return;
	}
}
