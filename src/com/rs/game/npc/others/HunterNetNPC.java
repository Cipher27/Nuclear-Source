package com.rs.game.npc.others;

import java.util.List;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.player.OwnedObjectManager.ConvertEvent;
import com.rs.game.player.Player;
import com.rs.game.player.actions.hunter.NetTrapAction.HunterNPC;
import com.rs.game.player.content.Hunter;
import com.rs.game.player.content.Hunter.DynamicFormula;
import com.rs.utils.Utils;

@SuppressWarnings("serial")
public class HunterNetNPC extends NPC {

	public HunterNetNPC(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
	}

	@Override
	public void processNPC() {
		super.processNPC();
		List<WorldObject> objects = World.getRegion(getRegionId())
				.getSpawnedObjects();
		if (objects != null) {
			final HunterNPC info = HunterNPC.forId(getId());
			int objectId = info.getEquipment().getObjectId();
			for (WorldObject object : objects) {
				if (object.getId() == objectId) {
					if (this.withinDistance(object, 4)) {
						if (Utils.random(10) == 0) {
							this.addWalkSteps(object.getX(), object.getY());
						}
					}
				}
			}
			for (final WorldObject object : objects) {
				if (object.getId() == objectId) {
					if (object.getX() != this.getX())
						continue;
					if (object.getY() != this.getY())
						continue;
					this.setFreezeDelay(4000);
					if (OwnedObjectManager.convertIntoObject(
							object,
							new WorldObject(-1, object.getType(), object
									.getRotation(), this.getX(), this.getY(),
									this.getPlane()), new ConvertEvent() {
								@Override
								public boolean canConvert(final Player player) {
									if (player == null
											|| player.getLockDelay() > Utils
													.currentTimeMillis()) {
										return false;
									}
									
									if (Hunter.isSuccessful(player,
											info.getLevel(),
											new DynamicFormula() {
												@Override
												public int getExtraProbablity(
														Player player) {
													// bait here
													return 1;
												}
											})) {
									//	player.setHunterDelay(2);
										finish();
										WorldObject tree = null;
										if (object.getRotation() == 0) {
											tree = new WorldObject(getTreeId(),
													object.getType(), object
															.getRotation(),
													object.getX(), object
															.getY() - 1, object
															.getPlane());
										}
										if (object.getRotation() == 1) {
											tree = new WorldObject(getTreeId(),
													object.getType(), object
															.getRotation(),
													object.getX() - 1, object
															.getY(), object
															.getPlane());
										}
										if (object.getRotation() == 2) {
											tree = new WorldObject(getTreeId(),
													object.getType(), object
															.getRotation(),
													object.getX(), object
															.getY() + 1, object
															.getPlane());
										}
										if (object.getRotation() == 3) {
											tree = new WorldObject(getTreeId(),
													object.getType(), object
															.getRotation(),
													object.getX() + 1, object
															.getY(), object
															.getPlane());
										}
										int rotation = object.getRotation();
										if (rotation == 3) {
											rotation = 0;
										}
										if (rotation == 2) {
											rotation = 0;
										}
										/*
										 * if
										 * (OwnedObjectManager.convertIntoObject
										 * (tree, new WorldObject(info.
										 * getSuccessfulTransformObjectId(),
										 * object.getType(), rotation,
										 * tree.getX(), tree.getY(),
										 * tree.getPlane()), new ConvertEvent()
										 * {
										 * 
										 * @Override public boolean canConvert(
										 * Player player) { if (player == null)
										 * return false; return true; }
										 * 
										 * }))
										 */
										OwnedObjectManager.replaceObject(
												player,
												tree,
												new WorldObject(
														info.getSuccessfulTransformObjectId(),
														tree.getType(),
														rotation, tree.getX(),
														tree.getY(), tree
																.getPlane()),
												300000);
										OwnedObjectManager.removeObject(player,
												object);
										// OwnedObjectManager.addOwnedObjectManager(player,
										// new WorldObject[] { new
										// WorldObject(info.getSuccessfulTransformObjectId(),
										// tree.getType(), rotation,
										// tree.getX(), tree.getY(),
										// tree.getPlane()) }, new long[] {
										// 300000 });
										setNextAnimation(info
												.getSuccessCatchAnim());
										return true;
									} else {
									//	player.setHunterDelay(2);
										WorldObject tree = null;
										if (object.getRotation() == 0) {
											tree = new WorldObject(getTreeId(),
													object.getType(), object
															.getRotation(),
													object.getX(), object
															.getY() - 1, object
															.getPlane());
										}
										if (object.getRotation() == 1) {
											tree = new WorldObject(getTreeId(),
													object.getType(), object
															.getRotation(),
													object.getX() - 1, object
															.getY(), object
															.getPlane());
										}
										if (object.getRotation() == 2) {
											tree = new WorldObject(getTreeId(),
													object.getType(), object
															.getRotation(),
													object.getX(), object
															.getY() + 1, object
															.getPlane());
										}
										if (object.getRotation() == 3) {
											tree = new WorldObject(getTreeId(),
													object.getType(), object
															.getRotation(),
													object.getX() + 1, object
															.getY(), object
															.getPlane());
										}
										OwnedObjectManager.removeObject(player,
												object);
										OwnedObjectManager.removeObject(player,
												tree);
										World.addGroundItem(new Item(954, 1),
												object, player, true, 120);
										World.addGroundItem(new Item(303, 1),
												object, player, true, 120);
										return false;
									}
								}
							})) {
						setRespawnTask(); // auto finishes
					}
				}
			}
		}
	}

	public int getTreeId() {
		if (getId() == 5117)
			return 19678;
		if (getId() == 5116) // black
			return 19670;
		if (getId() == 5114) // orange
			return 19650;
		if (getId() == 5115) // red
			return 19662;
		return 0;
	}
}