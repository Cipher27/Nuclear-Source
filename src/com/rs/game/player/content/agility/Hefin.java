package com.rs.game.player.content.agility;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.cutscenes.Cutscene;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class Hefin {
	
	public static WorldTile getWorldTile(Player player, int mapX, int mapY) {
		return new WorldTile(player.getX() , player.getY() , 1);
	}
	public static void merge(final Player player) {
		final WorldTile TurnTile1 = new WorldTile(2187, 3444, 2);
		final WorldTile NextTile = new WorldTile(2176, 3400, 1);
		if(player.getSkills().getLevel(Skills.AGILITY) <= 77){
			player.sendMessage("You need atleast level 77 agility to jump on this.");
			return;
		}
		if(player.heffinStage == 5) {
		player.getPackets().sendGameMessage("You merge with the light creature and complete the Hefin Agility Course.", true);
		player.setNextFaceWorldTile(TurnTile1);
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	 player.heffinStage = 0;
		    	time ++;
			if (time == 1) {
			    player.setNextAnimation(new Animation(17893));
			    player.completedHeffinCourse = true;
			}
			if(time == 4) {
				player.setNextWorldTile(NextTile);
				player.completedHeffinCourse = true;
			}
			if(time == 5) {
				player.setNextAnimation(new Animation(17894));
				addCompletedExp(player);
			    stop();
			}
		    }
			}, 0, 0);
		}
	}
	public static void slideDown(final Player player, final WorldObject object) {
		final WorldTile TurnTile1 = new WorldTile(2187, 3444, 2);
		final WorldTile NextTile = new WorldTile(2187, 3415, 2);
		if(player.getSkills().getLevel(Skills.AGILITY) <= 77){
			player.sendMessage("You need atleast level 77 agility to jump on this.");
			return;
		}
		if(player.heffinStage == 4) {
		player.getPackets().sendGameMessage("You slide down the Zip Line.", true);
		player.setNextFaceWorldTile(TurnTile1);
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(TurnTile1);
			    player.setNextAnimation(new Animation(25016));
			}
			if(time == 3) {
				player.setNextFaceWorldTile(TurnTile1);
					player.getPackets().sendCameraLook(Cutscene.getX(player, 2186),
						Cutscene.getY(player, 3424), 16800);
					player.getPackets().sendCameraPos(Cutscene.getX(player, 2180),
							Cutscene.getY(player, 3422), 17000);
			}
			if(time == 10) {
				player.setNextFaceWorldTile(TurnTile1);
				player.setNextWorldTile(NextTile);
				player.setNextAnimation(new Animation(-1));
				player.getPackets().sendResetCamera();
				addExp(player);
				player.heffinStage = 5;
			    stop();
			}
		    }
		}, 0, 0);
		}
	}
	public static void vault(final Player player, final WorldObject object) {
		final boolean running = player.getRun();
		final WorldTile NextTile = new WorldTile(2187, 3443, 2);
		if(player.getSkills().getLevel(Skills.AGILITY) <= 77){
			player.sendMessage("You need atleast level 77 agility to jump on this.");
			return;
		}
		if(player.heffinStage == 3) {
		player.getPackets().sendGameMessage("You vault around the roof.", true);
		player.setRunHidden(false);
		player.lock(8);
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
			    player.faceObject(object);
			    player.setNextAnimation(new Animation(25015));
			}
			if(time == 6) {
					player.getPackets().sendCameraLook(Cutscene.getX(player, 2186),
						Cutscene.getY(player, 3448), 18500);
					player.getPackets().sendCameraPos(Cutscene.getX(player, 2183),
							Cutscene.getY(player, 3435), 19000);
			}
			if(time == 16) {
				player.setNextWorldTile(NextTile);
				player.setNextAnimation(new Animation(-1));
				player.getPackets().sendResetCamera();
			    player.setRunHidden(running);
			    addExp(player);
			    stop();
			    player.heffinStage = 4;
			}
		    }
		}, 0, 0);
		}
	}
	public static void scale(final Player player, final WorldObject object) {
		final boolean running = player.getRun();
		final WorldTile NextTile = new WorldTile(2177, 3448, 2);
		if(player.getSkills().getLevel(Skills.AGILITY) <= 77){
			player.sendMessage("You need atleast level 77 agility to jump on this.");
			return;
		}
		if(player.heffinStage == 2) {
		player.getPackets().sendGameMessage("You scale the cathedral.", true);
		player.setRunHidden(false);
		player.lock(8);
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
			    player.faceObject(object);
			    player.setNextAnimation(new Animation(25014));
			}
			if(time == 5) {
					player.getPackets().sendCameraLook(Cutscene.getX(player, 2165),
						Cutscene.getY(player, 3448), 13000);
					player.getPackets().sendCameraPos(Cutscene.getX(player, 2155),
							Cutscene.getY(player, 3451), 21000);
			}
			if(time == 16) {
				player.setNextWorldTile(NextTile);
				//player.setNextFaceWorldTile(TurnTile);
				player.setNextAnimation(new Animation(-1));
				player.getPackets().sendResetCamera();
			    player.setRunHidden(running);
			    addExp(player);
			    stop();
			    player.heffinStage = 3;
			}
		    }
		}, 0, 0);
		}
	}
	public static void traverse(final Player player, final WorldObject object) {
		final boolean running = player.getRun();
		final WorldTile NextTile = new WorldTile(2171, 3437, 1);
		if(player.getSkills().getLevel(Skills.AGILITY) <= 77){
			player.sendMessage("You need atleast level 77 agility to jump on this.");
			return;
		}
		if(player.heffinStage == 1) {
		player.getPackets().sendGameMessage("You traverse the cliff.", true);
		player.setRunHidden(false);
		player.lock(8);
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
			    player.faceObject(object);
			    player.setNextAnimation(new Animation(25011));
			}
			if(time == 5) {
					player.getPackets().sendCameraLook(Cutscene.getX(player, 2175),
							Cutscene.getY(player, 3418), 3222);
					player.getPackets().sendCameraPos(Cutscene.getX(player, 2175),
							Cutscene.getY(player, 3418), 3222);
			}
			if(time == 10) {
				player.setNextWorldTile(NextTile);
				player.setNextAnimation(new Animation(24533));
				player.getPackets().sendResetCamera();
				addExp(player);
			    player.setRunHidden(running);
			    stop();
			    player.heffinStage = 2;
			}
		    }
		}, 0, 0);
		}
	}
	public static void leapAcross(final Player player, final WorldObject object) {
		final boolean running = player.getRun();
		final WorldTile NextTile = new WorldTile(2180, 3419, 1);
		final WorldTile TurnTile = new WorldTile(2177, 3405, 1);
		if(player.getSkills().getLevel(Skills.AGILITY) <= 77){
			player.sendMessage("You need atleast level 77 agility to jump on this.");
			return;
		}
		player.getPackets().sendGameMessage("You leap across the walkway.", true);
		player.setNextFaceWorldTile(TurnTile);
		player.setRunHidden(false);
		player.lock(8);
		WorldTasksManager.schedule(new WorldTask() {
			int time;
			boolean ready = false;
		    @Override
		    public void run() {
		    if (player.getX() == 2177 && player.getY() == 3402 && time == 0){
		    		ready = true;
		    	} else  if(player.getX() != 2177 && player.getY() != 3402) {
		    		player.addWalkSteps(2177, 3402);
		    		player.setNextFaceWorldTile(TurnTile);
		    		ready = false;
		    		time = 0;
		    	}
		    	time ++;
			if (time == 1 && ready == true) {
				player.setNextFaceWorldTile(TurnTile);
			    player.setNextAnimation(new Animation(24587));
			}
			if(time == 3) {
				ready = false;
				player.getPackets().sendCameraLook(Cutscene.getX(player, 2177),
						Cutscene.getY(player, 3408), 3417);
				player.getPackets().sendCameraPos(Cutscene.getX(player, 2177),
						Cutscene.getY(player, 3408), 3417);
			}
			if (time == 5){
				player.setNextWorldTile(NextTile);
				player.setNextAnimation(new Animation(-1));
			    player.setRunHidden(running);
				player.getPackets().sendResetCamera();
				addExp(player);
				player.heffinStage = 1;
			    stop();
			}
		    }
		
		}, 0, 1);
	}
	public static void addCompletedExp(final Player player) { // Exp for completing the whole course ( changes based on agility level )
			if(player.getSkills().getLevel(Skills.AGILITY) >= 77  && player.getSkills().getLevel(Skills.AGILITY) <= 82) {
				player.getSkills().addXp(Skills.AGILITY, 440);	
			} else if (player.getSkills().getLevel(Skills.AGILITY) >= 82  && player.getSkills().getLevel(Skills.AGILITY) <= 87) {
				player.getSkills().addXp(Skills.AGILITY, 550);	
			} else if (player.getSkills().getLevel(Skills.AGILITY) >= 87  && player.getSkills().getLevel(Skills.AGILITY) <= 92) {
				player.getSkills().addXp(Skills.AGILITY, 660);	
			} else if (player.getSkills().getLevel(Skills.AGILITY) >= 92  && player.getSkills().getLevel(Skills.AGILITY) <= 97) {
				player.getSkills().addXp(Skills.AGILITY, 740);	
			} else if (player.getSkills().getLevel(Skills.AGILITY) >= 97) {
				player.getSkills().addXp(Skills.AGILITY, 830);	
			}

	}
	public static void addExp(final Player player) {	// Exp for completing each stage( changes based on agility level )
		if(player.getSkills().getLevel(Skills.AGILITY) >= 77  && player.getSkills().getLevel(Skills.AGILITY) <= 82) { 
			player.getSkills().addXp(Skills.AGILITY, 44);	
		} else if (player.getSkills().getLevel(Skills.AGILITY) >= 82  && player.getSkills().getLevel(Skills.AGILITY) <= 87) {
			player.getSkills().addXp(Skills.AGILITY, 55);	
		} else if (player.getSkills().getLevel(Skills.AGILITY) >= 87  && player.getSkills().getLevel(Skills.AGILITY) <= 92) {
			player.getSkills().addXp(Skills.AGILITY, 66);	
		} else if (player.getSkills().getLevel(Skills.AGILITY) >= 92  && player.getSkills().getLevel(Skills.AGILITY) <= 97) {
			player.getSkills().addXp(Skills.AGILITY, 74);	
		} else if (player.getSkills().getLevel(Skills.AGILITY) >= 97) {
			player.getSkills().addXp(Skills.AGILITY, 83);	
		}
	}
}
