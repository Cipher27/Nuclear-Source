package com.rs.game.player.content.agility;

import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.NewForceMovement;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Woodcutting;
import com.rs.game.player.actions.Woodcutting.TreeDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class ShortCuts {
	
	/**
	 * @author Sam Bartlett 
	 * 
	 * @param player
	 * @param object
	 * 
	 * Credits to the Matrix II team for some of their short-cuts. 
	 */
	
	/**
	 * Made by hc747 and omar
	 */
	public static void HandleFreminikShortcuts(final Player player, final WorldObject object, final boolean toFurther) {
		if (!Agility.hasLevel(player, 81) || player.isLocked())
			return;
			player.faceObject(object);
			player.lock();
			final boolean squeeze = object.getId() == 77052;
			WorldTile baseTile = 
			(squeeze ? (toFurther ? new WorldTile(2735,10008,0) : new WorldTile(2730, 10008, 0)) : (toFurther ? new WorldTile(2774, 10002, 0) : new WorldTile(2769, 10002, 0)));
			if (player != baseTile) {
				player.addWalkSteps(baseTile.getX(), baseTile.getY(), 3, false);
			}
			WorldTasksManager.schedule(new WorldTask() {
			int ticks;
			Animation emote = new Animation(squeeze ? 15542 : 15461);
			WorldTile tile = (toFurther ? (squeeze ? new WorldTile(2730, 10008, 0) : new WorldTile(object)) : new WorldTile(object.getX() + (squeeze ? 4 : 5), object.getY(), object.getPlane()));
			@Override
			public void run() {
				if (ticks == 0) {
					player.setNextAnimation(emote);
				} else if (ticks == (squeeze ? 1 : 3)) {
					player.setNextAnimation(new Animation(-1));
					player.setNextWorldTile(tile);
					player.unlock();
					this.stop();
				}
				ticks++;
			}
		}, 0, 1);
	}
	
	public static void whirlpool(final Player player, final WorldObject object) {
		WorldTasksManager.schedule(new WorldTask() {
			
			int ticks;
			
			@Override
			public void run() {
				if (player.getX() != 2512 || player.getY() != 3516) {
					player.addWalkStep(2512, 3516, player.getX(), player.getY(), true);
				}
				if (ticks == 1) {
					player.setDirection(ForceMovement.SOUTH);
					player.setNextForceMovement(new ForceMovement(object, 1, ForceMovement.SOUTH));
				}
				ticks++;
			}
		}, 0, 1);
	}
	
	public static void KalphiteKingTunnel(final Player player, final WorldObject object) {
    	if (player.getSkills().getLevel(Skills.AGILITY) < 90) {
			player.getPackets().sendGameMessage("You need an agility level of 90 to use this obstacle.", true);
			return;
		    }
		    int x = player.getX() == 2961 ? 2967 : 2961;
		    WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
			    player.setNextAnimation(new Animation(10580));
			}
		    }, 0);
		    player.setNextForceMovement(new ForceMovement(new WorldTile(x, 1659, 0), 3, player.getX() == 2961 ? 1 : 3));
		    player.useStairs(-1, new WorldTile(x, 1659, 0), 3, 4);

		}
	
	public static void SlayerTowerBalance(final Player player) {
 		final boolean running = player.getRun();
 		player.setRunHidden(false);
 		player.lock(6);
 		WorldTasksManager.schedule(new WorldTask() {
				int stage;
				 @Override
				public void run() {
		if(stage == 0) {
 			   player.addWalkSteps(3416, 3551, 2, false);
 			  player.getAppearence().setRenderEmote(155);
			}
			if (stage == 6) {
			player.unlock();
			player.getAppearence().setRenderEmote(-1);
			player.setRunHidden(running);
			stop();
			}
			 stage++;
		 }
	 }, 0, 0);
		}
     public static void SlayerTowerBalanceBack(final Player player) {
	 		final boolean running = player.getRun();
	 		player.setRunHidden(false);
	 		player.lock(6);
	 		player.addWalkSteps(3416, 3544, 2, false);
	 			WorldTasksManager.schedule(new WorldTask() {
	 				int stage;
	 				 @Override
	 				public void run() {
	 		if(stage == 0) {
	 			player.addWalkSteps(3416, 3544, 2, false);
				player.getAppearence().setRenderEmote(155);
			}
			if (stage == 6) {
			player.unlock();
			player.getAppearence().setRenderEmote(-1);
			player.setRunHidden(running);
			stop();
			}
			 stage++;
		 }
	 }, 0, 0);
		}
     
	
	public static void vines(final Player player, final WorldObject object) {
		WorldTasksManager.schedule(new WorldTask() {
			int ticks;
			@Override
			public void run() {
				if (ticks == 0) {
					player.getActionManager()
					.setAction(
							new Woodcutting(object,
									TreeDefinitions.VINES));
				} else if (ticks > player.getActionManager().getActionDelay() && ticks <= 6) {
					if (player.getX() == 2691 && player.getY() == 9564) {
                		player.addWalkSteps(2689, 9564, 3, false);
                		stop();
						return;
					}
					else if (player.getX() == 2689 && player.getY() == 9564) {
						player.addWalkSteps(2691, 9564, 3, false);
						stop();
						return;
                    }
					else if (player.getX() == 2683 && player.getY() == 9568) {
						player.addWalkSteps(2683, 9570, 3, false);   
						stop();
                    	return;
					}
					else if (player.getX() == 2683 && player.getY() == 9570) {
                    	player.addWalkSteps(2683, 9568, 3, false);  
                    	stop();
                    	return;
                    }
					else if (player.getX() == 2672 && player.getY() == 9499) {
						player.addWalkSteps(2674, 9499, 3, false);  
						stop();
						return;
					}
					else if (player.getX() == 2674 && player.getY() == 9499) {
						player.addWalkSteps(2672, 9499, 3, false);  
						stop();
						return;
					}
					else if (player.getX() == 2674 && player.getY() == 9479) {
						player.addWalkSteps(2676, 9479, 3, false);  
						stop();
						return;
					}
					else if (player.getX() == 2676 && player.getY() == 9479) {
						player.addWalkSteps(2674, 9479, 3, false);  
						stop();
						return;
					}
					else if (player.getX() == 2693 && player.getY() == 9482) {
						player.addWalkSteps(2695, 9482, 3, false);  
						stop();
						return;
					}
					else if (player.getX() == 2695 && player.getY() == 9482) {
						player.addWalkSteps(2693, 9482, 3, false);  
						stop();
						return;
					}
				}
				ticks++;
			}
		}, 0, 1);
	}
	
	public static void GEShortuct(final Player player, final WorldObject object) { 
		if (!Agility.hasLevel(player, 21))
			return;
		    WorldTasksManager.schedule(new WorldTask() {

			int ticks = 0;
			int id = object.getId();

			@Override
			public void run() {
			    boolean withinGE = id == 9312;
			    WorldTile tile = withinGE ? new WorldTile(3139, 3516, 0) : new WorldTile(3143, 3514, 0);
			    player.lock();
			    ticks++;
			    if (ticks == 1) {
				player.setNextAnimation(new Animation(2589));
				player.setNextForceMovement(new ForceMovement(object, 1, withinGE ? ForceMovement.WEST : ForceMovement.EAST));
			    } else if (ticks == 3) {
				player.setNextWorldTile(new WorldTile(3141, 3515, 0));
				player.setNextAnimation(new Animation(2590));
			    } else if (ticks == 5) {
				player.setNextAnimation(new Animation(2591));
				player.setNextWorldTile(tile);
			    } else if (ticks == 6) {
				player.setNextWorldTile(new WorldTile(tile.getX() + (withinGE ? -1 : 1), tile.getY(), tile.getPlane()));
				player.unlock();
				stop();
			    }
			}
		    }, 0, 0);
	}
		    public static void TaverlyTunnel(final Player player, final WorldObject object) {
		    	if (player.getSkills().getLevel(Skills.AGILITY) < 70) {
					player.getPackets().sendGameMessage("You need an agility level of 70 to use this obstacle.", true);
					return;
				    }
				    int x = player.getX() == 2886 ? 2892 : 2886;
				    WorldTasksManager.schedule(new WorldTask() {

					@Override
					public void run() {
					    player.setNextAnimation(new Animation(10580));
					}
				    }, 0);
				    player.setNextForceMovement(new ForceMovement(new WorldTile(x, 9799, 0), 3, player.getX() == 2886 ? 1 : 3));
				    player.useStairs(-1, new WorldTile(x, 9799, 0), 3, 4);
	
	}
		     public static void StrangeFloorTaverly(final Player player, final WorldObject object) {
		    	 if (!Agility.hasLevel(player, 80))
		 			return;
		 		    final boolean isRunning = player.getRun();
		 		    final boolean isSouth = player.getY() > 9813;
		 		    final WorldTile tile = isSouth ? new WorldTile(2878, 9812, 0) : new WorldTile(2881, 9814, 0);
		 		    player.setRun(true);
		 		    player.addWalkSteps(isSouth ? 2881 : 2877, isSouth ? 9814 : 9812);
		 		    WorldTasksManager.schedule(new WorldTask() {
		 			int ticks = 0;

		 			@Override
		 			public void run() {
		 			    ticks++;
		 			    if (ticks == 2)
		 				player.setNextFaceWorldTile(object);
		 			    else if (ticks == 3) {
		 				player.setNextAnimation(new Animation(1995));
		 				player.setNextForceMovement(new NewForceMovement(player, 0, tile, 4, Utils.getFaceDirection(object.getX() - player.getX(), object.getY() - player.getY())));
		 			    } else if (ticks == 4)
		 				player.setNextAnimation(new Animation(1603));
		 			    else if (ticks == 7) {
		 				player.setNextWorldTile(tile);
		 				player.setRun(isRunning);
		 				stop();
		 				return;
		 			    }
		 			}
		 		    }, 0, 0);
		 		    
		     }
		     public static void BrimhavenBlockBalance(final Player player, final WorldObject object) {
		    	  final int id = object.getId();
		    	  boolean back = id == 77573;
				    player.lock(4);
				    final WorldTile tile = back ? new WorldTile(2687, 9506, 0) : new WorldTile(2682, 9506, 0);
				    final boolean isRun = player.isRunning();
				    player.setRun(false);
				    player.addWalkSteps(tile.getX(), tile.getY(), -1, false);
				    WorldTasksManager.schedule(new WorldTask() {

					@Override
					public void run() {
					    player.setRun(isRun);
					}
				    }, 4);
		     }
		     public static void BrimhavenSteppingStones(final Player player, final WorldObject object) {
		    	 player.lock(1);
				    player.setNextAnimation(new Animation(741));
				    player.setNextForceMovement(new NewForceMovement(player, 0, object, 1, Utils.getFaceDirection(object.getX() - player.getX(), object.getY() - player.getY())));
				    WorldTasksManager.schedule(new WorldTask() {

					@Override
					public void run() {
					    player.setNextWorldTile(object);
					}
				    });
		     }
		     public static void EdgevilleDungeonMonkeyBars(final Player player, final WorldObject object) {
		    	 final boolean isNorth = player.getY() > 9964;
				    final WorldTile tile = new WorldTile(player.getX(), player.getY() + (isNorth ? -7 : 7), 0);
				    player.setNextAnimation(new Animation(745));
				    player.setNextForceMovement(new ForceMovement(player, 1, tile, 5, isNorth ? ForceMovement.SOUTH : ForceMovement.NORTH));
				    WorldTasksManager.schedule(new WorldTask() {
					int ticks = 0;

					@Override
					public void run() {
					    ticks++;
					    if (ticks > 1)
						player.setNextAnimation(new Animation(744));
					    if (ticks == 5) {
						player.setNextWorldTile(tile);
						stop();
						return;
					    }
					}
				    }, 0, 0);    
		     }
		     public static void passGiantBoulder(Player player, WorldObject object, boolean liftBoulder) {
		    		if (player.getSkills().getLevelForXp(liftBoulder ? Skills.STRENGTH : Skills.AGILITY) < 60) {
		    		    player.getPackets().sendGameMessage("You need a " + (liftBoulder ? "Strength" : "Agility") + " of 60 in order to " + (liftBoulder ? "lift" : "squeeze past") + " this boulder.");
		    		    return;
		    		}
		    		if (liftBoulder)
		    		    World.sendObjectAnimation(object, new Animation(318));
		    		final boolean isReturning = player.getY() >= 3709;
		    		int baseAnimation = liftBoulder ? 3725 : 3466;
		    		player.useStairs(isReturning ? baseAnimation-- : baseAnimation, new WorldTile(player.getX(), player.getY() + (isReturning ? -4 : 4), 0), liftBoulder ? 10 : 5, liftBoulder ? 11 : 6, null);
		    	    }
		     
		     public static void CoalTruckLogWalk(final Player player) {
		 		if (player.getX() != 2603 || player.getY() != 3477)
		 		    return;
		 		final boolean running = player.getRun();
		 		player.setRunHidden(false);
		 		player.lock(6);
		 		player.addWalkSteps(2598, 3477, -1, false);
		 		player.getPackets().sendGameMessage("You walk carefully across the slippery log...", true);
		 		WorldTasksManager.schedule(new WorldTask() {
		 		    boolean secondloop;

		 		    @Override
		 		    public void run() {
		 			if (!secondloop) {
		 			    secondloop = true;
		 			    player.getAppearence().setRenderEmote(155);
		 			} else {
		 			    player.getAppearence().setRenderEmote(-1);
		 			    player.setRunHidden(running);
		 			    player.getSkills().addXp(Skills.AGILITY, 7.5);
		 			    player.getPackets().sendGameMessage("... and make it safely to the other side.", true);
		 			    stop();
		 			}
		 		    }
		 		}, 0, 6);
		 	    }
		     public static void CoalTruckLogWalkBack(final Player player) {
			 		if (player.getX() != 2598 || player.getY() != 3477)
			 		    return;
			 		final boolean running = player.getRun();
			 		player.setRunHidden(false);
			 		player.lock(6);
			 		player.addWalkSteps(2603, 3477, -1, false);
			 		player.getPackets().sendGameMessage("You walk carefully across the slippery log...", true);
			 		WorldTasksManager.schedule(new WorldTask() {
			 		    boolean secondloop;

			 		    @Override
			 		    public void run() {
			 			if (!secondloop) {
			 			    secondloop = true;
			 			    player.getAppearence().setRenderEmote(155);
			 			} else {
			 			    player.getAppearence().setRenderEmote(-1);
			 			    player.setRunHidden(running);
			 			    player.getSkills().addXp(Skills.AGILITY, 7.5);
			 			    player.getPackets().sendGameMessage("... and make it safely to the other side.", true);
			 			    stop();
			 			}
			 		    }
			 		}, 0, 6);
		     }
}