package com.rs.game.player.actions.slayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class Slayer {

    Player player;

    
    public static void assignTask(Player player, SlayerMaster master) {
        player.hasTask = true;
        checkLevel(player, master);
    }

    public static void checkLevel(Player player, SlayerMaster master) {
        SlayerTaskHandler tasks = player.currentSlayerTask;
        List<SlayerTasks> possibleTasks = new ArrayList<SlayerTasks>();
        for (SlayerTasks task : SlayerTasks.values()) {
            if (task.type == master.type) {
                possibleTasks.add(task);
            }
        }
        if (possibleTasks.isEmpty()) {
            return;
        }
        for (int i = 0; i < 10; i++) {
            SlayerTasks task = possibleTasks.get(i);
            Collections.shuffle(possibleTasks);
            if (player.getSkills().getLevel(18) < task.level) {
                Collections.shuffle(possibleTasks);
            } else {
                tasks.setTask(task);
                player.setSlayerMaster(master.masterID);
                tasks.setMonstersLeft(Utils.random(tasks.getTask().min, tasks.getTask().max));
            }
        }if(player.getBlockedSlayerTasks().contains(tasks.getTask().simpleName) || tasks.getTask() == SlayerTasks.AIRUT && player.eliteDungeon == false ||player.currentSlayerTask.getTask() == SlayerTasks.EDIMMU && player.eliteDungeon == false ||player.currentSlayerTask.getTask() == SlayerTasks.AUTOMATOM && player.eliteDungeon == false){
     	   Slayer.assignTask(player, master);
        }
    }
}
