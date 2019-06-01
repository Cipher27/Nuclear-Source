package com.rs.game.player.robot.methods;

import java.util.List;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.player.content.Foods.Food;
import com.rs.game.player.content.Magic;
import com.rs.game.player.robot.Robot;
import com.rs.game.route.pathfinder.PathFinder;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;


/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public class Pickup extends Method {
	
	public Pickup(Robot robot) {
		super(robot);
		stage = Stage.Finished;
	}
	
	@Override
	public void process() {
		pickup();
	}
	
	public int checkPickup;
	
	public void pickup() {
		if (getScript().combat.stage == Stage.Looking
				|| stage == Stage.Looking
				|| stage == Stage.Running) {
			if (stage == Stage.Looking) {
				getScript().prayer.stage = Stage.Protecting;
				getScript().combat.stage = Stage.Finished;
				pickUpStuff(getScript().combat.deathTile);
				//pause(15 + Utils.random(5));
				return;
			}
			if (checkPickup++ > 5) {
				checkPickup = 0;
			} else {
				return;
			}
			List<FloorItem> items = World.getRegion(robot.getRegionId()).getGroundItems();
			if (items == null)
				return;
			for (final FloorItem item : items) {
				if (item == null)
					return;
				if (!item.isInvisible()) {
					if ((item.getDefinitions().getValue() * item.getAmount()) > 100000) {
						if (robot.getInventory().getFreeSlots() == 0) {
							boolean hasFreeSpace = false;
							for (int slot = 0; slot < robot.getInventory().getItems().getItems().length; slot++) {
								Item toDrop = robot.getInventory().getItem(slot);
								if (Food.forId(toDrop.getId()) != null) {
									World.addGroundItem(toDrop, new WorldTile(robot), robot, false, 180, true);
									robot.getInventory().deleteItem(slot, toDrop);
									hasFreeSpace = true;
									break;
								}
							}
							if (!hasFreeSpace)
								return;
						}
						stage = Stage.Running;
						robot.addWalkSteps(item.getTile());
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								World.removeGroundItem(robot, item);
								stage = Stage.Finished;
								checkPickup = 0;
							}
						}, 2);
						break;
					}
				}
			}
		}
	}
	
	public void pickUpStuff(final WorldTile tile) {
		//needsToPray = true;
		//robot.getPrayer().switchPrayer(9);
		if (tile == null) {
			getScript().prayer.stage = Stage.Running;
			getScript().foodsAndPotions.stage = Stage.Finished;
			getScript().combat.stage = Stage.Looking;
			stage = Stage.Finished;
			return;
		}
		if (!tile.withinDistance(robot, 10)) {
			if (tile.getY() < 3510 && !Magic.useTeleTab(robot, tile)) {
				getScript().prayer.stage = Stage.Running;
				getScript().foodsAndPotions.stage = Stage.Finished;
				getScript().combat.stage = Stage.Looking;
				stage = Stage.Finished;
				return;
			}
		}
		pause(15);
		robot.setAttackedByDelay(Utils.currentTimeMillis() + 13000);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				robot.setFreezeDelay(0);
				robot.stopAll(true);
				PathFinder.simpleWalkTo(robot, tile);
				// robot.addWalkSteps(tile.getX(), tile.getY(),
				// tile.getPlane());
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						robot.stopAll(true);
						PathFinder.simpleWalkTo(robot, tile);
						// robot.addWalkSteps(tile.getX(), tile.getY(),
						// tile.getPlane());
					}
				}, 2);
				
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						final List<FloorItem> items = World.getRegion(
								tile.getRegionId()).getGroundItems();
						if (items != null) {
							for (final FloorItem item : items) {
								if (item == null || item.getOwner() == null)
									continue;
								// NOBODY WILL KNOW
								//if (item.getWorldTile().getX() != tile.getX()
								//		|| item.getWorldTile().getY() != tile.getY())
								//	continue;
								if (!item.getOwner()
										.equals(robot.getUsername())) {
									continue;
								}
								if ((item.getDefinitions().getValue() * item
										.getAmount()) <= 38000
								// && Food.forId(item.getId()) == null
								) {// if it's not worth a lot and it's not food
									continue;
								}
								boolean hasFreeSpace = true;
								if (robot.getInventory().getFreeSlots() == 0) {
									hasFreeSpace = false;
									for (int slot = 0; slot < robot
											.getInventory().getItems()
											.getItems().length; slot++) {
										final Item toDrop = robot
												.getInventory().getItem(slot);
										if ((toDrop.getDefinitions().getValue() * toDrop
												.getAmount()) <= 12000) {
											World.addGroundItem(toDrop,
													new WorldTile(robot),
													robot, false, 180, true);
											robot.getInventory().deleteItem(
													slot, toDrop);
											hasFreeSpace = true;
											break;
										}
									}
								}
								if (!hasFreeSpace)
									break;
								World.removeGroundItem(robot, item);
							}
						}
						// FINISH PICKING THINGS UP
						getScript().prayer.stage = Stage.Running;
						getScript().foodsAndPotions.stage = Stage.Finished;
						getScript().combat.stage = Stage.Looking;
						stage = Stage.Finished;
						//pause(6 + Utils.random(5));
					}
				}, 4);
			}
		}, 10);
	}
}
