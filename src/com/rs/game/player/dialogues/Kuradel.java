/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.actions.slayer.Slayer;
import com.rs.game.player.actions.slayer.SlayerMaster;
import com.rs.game.player.content.SlayerMasks;
import com.rs.game.player.content.SlayerMasks.Masks;
import com.rs.utils.ShopsHandler;

public class Kuradel extends Dialogue {
    /**
     * Starts the dialogue
     */
	Masks mask;
    @Override
    public void start() {
    	
  	  mask = Masks.forId(player.getEquipment().getHatId());
        npcId = 9085;
        sendEntityDialogue((short) 241, new String[]{NPCDefinitions.getNPCDefinitions(npcId).name, "Hello, brave warrior. What would you like?"}, (byte) 1, npcId, 9827);
    }

    /**
     * Runs the dialogue
     */
    @Override
    public void run(int interfaceId, int componentId) {
		/**
		 * start, todo other options when no task
		 */
		if (stage == -1){
            sendOptionsDialogue("What would you like to say?", "I would like a slayer task.", "What is my current slayer task?", "Can I cancel my current task?","Can I buy some stuff from you ?","Nothing.");
            stage = 0;
		}
         /**
          * task giving
          */
		else if (stage == 0){
            if (componentId == OPTION_1) {
            	 if (player.hasTask == false) {
       			  if (mask != null && !player.slayerTasksGiven.contains(mask)){
       				 sendOptionsDialogue("Select an option", "Yes assigne me "+SlayerMasks.getNpcName(mask)+" as a slayer task.", "No give me a random task.");
       			 stage = 12;
       			}  else {
       				   if(player.getSkills().getCombatLevel() <= 50){
                               Slayer.assignTask(player, SlayerMaster.TURAEL);
                               sendNPCDialogue(9085,9827,"Your slayer task is to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
                               stage = 7;
       				   } else  if(player.getSkills().getCombatLevel() > 50 && player.getSkills().getCombatLevel() <= 100){
                               Slayer.assignTask(player, SlayerMaster.CHAELDAR);
                               sendNPCDialogue(9085,9827,"Your slayer task is to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
                               stage = 7;
       				   }
       				   else{
       					    Slayer.assignTask(player, SlayerMaster.KURADEL);
                               sendNPCDialogue(9085,9827,"Your slayer task is to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
                          stage = 7;
       				   }
       			}
       				   } else if (player.hasTask == true){
       					sendNPCDialogue(9085,9827, "You already have a slayer task! ", "You need to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
                            stage = 7;
       					}
            } else if (componentId == OPTION_2) {
                if (player.hasTask == true) {
                    sendNPCDialogue(9085,9827, "You have a short memory, don't you?", "You need to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
               stage = 7;
			   } else {
                    sendNPCDialogue(9085,9827,"Foolish warrior. You don't have a slayer task!");
               stage = 7;
			   }
                stage = -1;
            } else if (componentId == OPTION_3) {
		    sendNPCDialogue(9085,9827,"This will cost you 250k.");
			stage = 2;
            } else if (componentId == OPTION_4) {
		   ShopsHandler.openShop(player, 29);
           end();
            } else if (componentId == OPTION_5) {
		    end();
            }
		}
		else if (stage == 2){
		sendOptionsDialogue("Choose an option.","Yes I don't mind spending money.", "I keep my slayer task.");
		stage = 3;
    }
		/**
		 * stage 3; task skipping
		 */
		else if(stage  == 3){
		if (componentId == OPTION_1) {
	    if(player.hasTask == true){
			if (player.getInventory().containsItem(995, 250000)) {
			   player.getInventory().removeItemMoneyPouch(995,250000);
			player.hasTask = false;
			sendNPCDialogue(9085,9827, "Your task has been succesfully cancled.");
			stage = -1;
			} else {
				sendNPCDialogue(9085,9827, "You dont have 250k gold, come back later!");
				stage = 7;
			}
	    } else {
	    	sendNPCDialogue(9085,9827, "You don't have a task to cancel.");
			stage = 7;	
	    }
		}
		else if (componentId == OPTION_2) {
          end();
            }
		}
	    /**
	     * task giving
	     */
		else if (stage == 12){
        if (componentId == OPTION_1) {
        	SlayerMasks.assignTask(mask, player);
        	player.slayerTasksGiven.add(mask);
        	sendNPCDialogue(9085,9827,"Your slayer task is to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
        	stage = 7;
	    } else if (componentId == OPTION_2) {
	    	Slayer.assignTask(player, SlayerMaster.KURADEL);//9085
	    	sendNPCDialogue(9085,9827,"Your slayer task is to kill " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName);
             stage = 7;
	    }
			}else if(stage == 7 ){

        	end();
        }
    }

    @Override
    public void finish() {
    }
    /**
     * Declares the npc ID
     */
    private int npcId;
}
