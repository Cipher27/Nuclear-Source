package com.rs.game.player.actions.slayer;

import java.io.Serializable;

import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.achievements.impl.SlayerDummyAchievement;
import com.rs.game.player.achievements.impl.SlayerMasterAchievement;
import com.rs.game.player.content.SlayerMasks.Masks;
import com.rs.game.server.fameHall.HallOfFame;
import com.rs.game.world.RecordHandler;

/**
 * Slayer is a members-only skill that allows players to kill monsters which are
 * often otherwise immune to damage. Slayer was introduced on 26 January 2005.
 * Players get a Slayer task from one of seven Slayer Masters, and players gain
 * Slayer experience for killing monsters that they are assigned.
 *
 * @author Emperial
 *
 */
public class SlayerTaskHandler implements Serializable {

    /**
     *
     */
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2393380182432742835L;
	/**
     * The players current assigned task
     */
    private SlayerTasks currentTask;
    /*
     * Player level for slayer task
     */
    private SlayerTasks level;
    /**
     * The monsters left.
     */
    private int monstersLeft = -1;

    /**
     * Slayer Level for Task
     */
    public SlayerTasks getLevel() {
        return level;
    }

    public void setLevel(SlayerTasks lvl) {
        level = lvl;
    }
    
	public int getTaskAmount() {
		return monstersLeft;
	}

	public void decreaseAmount() {
		monstersLeft--;
	}

    /**
     * The slayer task
     *
     * @return
     */
    public SlayerTasks getTask() {
        return currentTask;
    }

    /**
     * The monsters left to kill
     */
    public int getTaskMonstersLeft() {
        return monstersLeft;
    }

    /**
     * Sets the player current task
     *
     * @param task
     */
    public void setTask(SlayerTasks task) {
        currentTask = task;
    }

    /**
     * Sets monsters left to kill
     *
     * @param i
     */
    public void setMonstersLeft(int i) {
        monstersLeft = i;
    }
    

    /**
     * Called on npc death if part of task.
     */
    public void onMonsterDeath(Player player, NPC n) {
    	int dubbel = (World.slayerpoints ? 2 : 1);
    	Masks mask = Masks.forId(player.getEquipment().getHatId());
    	int multi = (mask != null && mask.task == player.currentSlayerTask.getTask() ? (n.getCombatDefinitions().getHitpoints()/10)/2 : 0);
        player.getSkills().addXp(Skills.SLAYER, (n.getCombatDefinitions().getHitpoints()/10) + multi);
        monstersLeft--;
        int[] checkpoints = new int[]{1, 2, 3, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
        for (int i : checkpoints) {
            if (monstersLeft == i) {
                player.getPackets().sendGameMessage(
                        "You're doing great, Only " + monstersLeft + " "
                        + getTask().simpleName + " left to slay.");
                player.hasTask = true;
            }
        }
        if (monstersLeft < 1) {
            player.setSlayerTaskAmount(player.getSlayerTaskAmount() + 1);
            int[] get50Points = new int[]{10, 20, 30, 40, 60, 70, 80, 90, 110, 120, 130, 140, 150, 160, 170,
                180, 190, 210, 220, 230, 240, 260, 270, 280, 290, 310, 320, 330, 340, 350, 360, 370, 380, 390, 410, 420,
                430, 440, 460, 470, 480, 490};
            int[] get100Points = new int[]{50, 150, 250, 350, 450, 550};
            int[] get500Points = new int[]{100, 200, 300, 400, 500, 600};
            int[] get10Points = new int[]{10, 20, 30, 40, 60, 70, 80, 90, 110, 120, 130, 140, 150, 160, 170,
                180, 190, 210, 220, 230, 240, 260, 270, 280, 290, 310, 320, 330, 340, 350, 360, 370, 380, 390, 410, 420,
                430, 440, 460, 470, 480, 490, 50, 150, 250, 350, 450, 550, 100, 200, 300, 400, 500, 600};
            for (int ii : get50Points) {
                if (player.getSlayerTaskAmount() == ii) {
                	
						player.slayerTasks ++;
						if(HallOfFame.first100SlayerTasks[0] == null && player.slayerTasks >= 100){
    					HallOfFame.first100SlayerTasks[0] = player.getDisplayName();
    					HallOfFame.first100SlayerTasks[1] = java.time.LocalDate.now().toString();
    					player.isInHOF = true;
    					HallOfFame.save();
    					player.sm("Congratz you are now in the hall of fame.");
    				}
						RecordHandler.getRecord().handelSlayerTasks(player);
						player.getAchievementManager().notifyUpdate(SlayerDummyAchievement.class);
						player.getAchievementManager().notifyUpdate(SlayerMasterAchievement.class);
                        player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() + 125 *dubbel);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive "+125*dubbel+" Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                }
            }
            for (int iii : get100Points) {
                if (player.getSlayerTaskAmount() == iii) {
					player.slayerTasks ++;
					if(HallOfFame.first100SlayerTasks[0] == null && player.slayerTasks >= 100){
    					HallOfFame.first100SlayerTasks[0] = player.getDisplayName();
    					HallOfFame.first100SlayerTasks[1] = java.time.LocalDate.now().toString();
    					player.isInHOF = true;
    					HallOfFame.save();
    					player.sm("Congratz you are now in the hall of fame.");
    				}
					RecordHandler.getRecord().handelSlayerTasks(player);
					player.getAchievementManager().notifyUpdate(SlayerDummyAchievement.class);
					player.getAchievementManager().notifyUpdate(SlayerMasterAchievement.class);
                    player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() + 250*dubbel);
                    player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive "+250*dubbel+" Slayer Points!");
                    player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                    player.hasTask = false;
                }
            }
            for (int iiii : get500Points) {
                if (player.getSlayerTaskAmount() == iiii) {
                	
						player.slayerTasks ++;
						if(HallOfFame.first100SlayerTasks[0] == null && player.slayerTasks >= 100){
    					HallOfFame.first100SlayerTasks[0] = player.getDisplayName();
    					HallOfFame.first100SlayerTasks[1] = java.time.LocalDate.now().toString();
    					player.isInHOF = true;
    					HallOfFame.save();
    					player.sm("Congratz you are now in the hall of fame.");
    				}
						RecordHandler.getRecord().handelSlayerTasks(player);
						player.getAchievementManager().notifyUpdate(SlayerDummyAchievement.class);
						player.getAchievementManager().notifyUpdate(SlayerMasterAchievement.class);
                       player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() + 500*dubbel);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive "+500*dubbel+" Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                }
            }
            for (int iiiii : get10Points) {
                if (player.getSlayerTaskAmount() != iiiii && player.hasTask == true) {
					
						player.slayerTasks ++;
						if(HallOfFame.first100SlayerTasks[0] == null && player.slayerTasks >= 100){
    					HallOfFame.first100SlayerTasks[0] = player.getDisplayName();
    					HallOfFame.first100SlayerTasks[1] = java.time.LocalDate.now().toString();
    					player.isInHOF = true;
    					HallOfFame.save();
    					player.sm("Congratz you are now in the hall of fame.");
    				}
						RecordHandler.getRecord().handelSlayerTasks(player);
						player.getAchievementManager().notifyUpdate(SlayerDummyAchievement.class);
						player.getAchievementManager().notifyUpdate(SlayerMasterAchievement.class);
                       player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() + 18*dubbel);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive "+18*dubbel+" Slayer Points! you now have "+player.getPointsManager().getSlayerPoints()+" slayer points.");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                }
            }
        }
    }

}